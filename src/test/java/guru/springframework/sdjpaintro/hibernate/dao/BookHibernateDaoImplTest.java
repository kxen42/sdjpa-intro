package guru.springframework.sdjpaintro.hibernate.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import guru.springframework.sdjpaintro.hibernate.domain.BookHibernate;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"guru.springframework.sdjpaintro.hibernate.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookHibernateDaoImplTest {

  @Autowired BookHibernateDao bookDao;
  // I experience with real testing is that you don't use code that you're testing to get the
  // results of other tests
  @Autowired EntityManagerFactory emf;

  @Nested
  @DisplayName("Fetching data - primed DB")
  class ReadQuerys {
    @Test
    void testGetBookByName() {
      BookHibernate book = bookDao.findBookByTitle("Clean Code");
      assertThat(book).isNotNull();
    }

    @Test
    void testGetBook() {
      BookHibernate book = bookDao.getById(3L);
      assertThat(book.getId()).isNotNull();
    }

    @Test
    void findAll() {
      // running the query multiple times is efficient, but I don't care about that now
      List<BookHibernate> all = bookDao.findAll();
      assertThat(all.size()).isGreaterThanOrEqualTo(2);

      all.forEach(System.out::println);
    }

    @Test
    void findByIsbn() {
      BookHibernate found = bookDao.findByIsbn("978-1617294945");
      assertThat(found.getTitle()).isEqualTo("Spring in Action, 5th Edition");
    }

    @Test
    void findBookByTitleCriteria() {
      BookHibernate book = bookDao.findBookByTitleCriteria("Clean Code");
      assertThat(book).isNotNull();
    }

    @Test
    void findBookByTitleNative() {
      BookHibernate book = bookDao.findBookByTitleNative("Clean Code");
      assertThat(book).isNotNull();
    }

    @Test
    void findAllBooksSortByTitleAscending() {
      List<BookHibernate> books =
          bookDao.findAllBooksSortByTitle(PageRequest.of(0, 10, Sort.by(Sort.Order.asc("title"))));
      assertThat(books).isNotNull().hasSize(5);
      assertThat(books.get(0).getTitle()).isEqualTo("Clean Code");
    }

    @Test
    void findAllBooksSortByTitleDescending() {
      List<BookHibernate> books =
          bookDao.findAllBooksSortByTitle(PageRequest.of(0, 10, Sort.by(Sort.Order.desc("title"))));
      assertThat(books).isNotNull().hasSize(5);
      assertThat(books.get(0).getTitle()).isEqualTo("Spring in Action, 6th Edition");
    }

    @Test
    void findAllBooksPageable() {
      List<BookHibernate> books = bookDao.findAllBooks(PageRequest.of(0, 10));
      assertThat(books).isNotNull().hasSize(5);
    }

    @Test
    void findAllBooksPageableLongToIntConversion() {
      assertThrows(
          ArithmeticException.class,
          () -> {
            bookDao.findAllBooks(new TestingPageable());
          });
    }

    @Test
    void findAllBooksWithSizeAndOffset() {
      List<BookHibernate> books = bookDao.findAllBooks(5, 0);
      assertThat(books).isNotNull().hasSize(5);
    }

    @Test
    void findAllBooksNoArgs() {
      List<BookHibernate> books = bookDao.findAllBooks();
      assertThat(books).isNotNull().isNotEmpty();
    }
  }

  @Nested
  @DisplayName("Insert, Update, Delete")
  class DataManipulation {

    @Test
    void testSaveBook() {
      LocalTime lt = LocalTime.now();
      BookHibernate book = new BookHibernate("sed & awk", lt.toString(), "O'Reilly");
      BookHibernate saved = bookDao.saveNewBook(book);

      assertThat(saved).isNotNull();
      System.out.println("DataManipulation.testSaveBook saved:" + saved);

      EntityManager em = emf.createEntityManager();
      BookHibernate checkSaved = em.find(BookHibernate.class, saved.getId());
      em.close();
      assertThat(checkSaved).isNotNull();
    }

    @Test
    void deleteBook() {
      LocalTime lt = LocalTime.now();
      BookHibernate book = new BookHibernate("Perl", lt.toString(), "O'Reilly");
      BookHibernate saved = bookDao.saveNewBook(book);

      assertThat(saved).isNotNull();
      System.out.println("DataManipulation.deleteBook saved:" + saved);
      EntityManager em = emf.createEntityManager();
      BookHibernate checkSaved = em.find(BookHibernate.class, saved.getId());
      em.close();
      assertThat(checkSaved).isNotNull();

      bookDao.deleteBookById(saved.getId());

      em = emf.createEntityManager();
      BookHibernate checkDelete = em.find(BookHibernate.class, saved.getId());
      em.close();
      assertThat(checkDelete).isNull();
    }

    @Test
    void updateBook() {
      LocalTime junk = LocalTime.now();
      BookHibernate book = new BookHibernate("Ada", junk.toString(), "McGraw Hill");
      BookHibernate saved = bookDao.saveNewBook(book);
      System.out.println("DataManipulation.updateBook saved: " + saved);
      assertThat(saved.getAuthorId()).isNull();

      saved.setAuthorId(1L);
      BookHibernate updated = bookDao.updateBook(saved);
      assertThat(updated.getAuthorId()).isOne();

      EntityManager em = emf.createEntityManager();
      BookHibernate checkUpdate = em.find(BookHibernate.class, updated.getId());
      em.close();
      assertThat(checkUpdate.getAuthorId()).isOne();
      System.out.println("DataManipulation.updateBook checkUpdate: " + checkUpdate);
    }

    @Test
    void updatePrice() {
      LocalTime junk = LocalTime.now();
      BookHibernate book = new BookHibernate("Ada", junk.toString(), "McGraw Hill");
      BookHibernate saved = bookDao.saveNewBook(book);
      System.out.println("DataManipulation.updateBook saved: " + saved);

      BookHibernate updated = bookDao.updatePrice(saved.getId(), "19.99");
      // I want to see if the transition sticks
      assertThat(updated.getPrice()).isEqualTo("19.99");

      EntityManager em = emf.createEntityManager();
      BookHibernate checkUpdate = em.find(BookHibernate.class, updated.getId());
      em.close();
      assertThat(updated.getPrice()).isEqualTo("19.99");
      System.out.println("DataManipulation.updateBook checkUpdate: " + checkUpdate);
    }
  }

  class TestingPageable implements Pageable {

    @Override
    public boolean isPaged() {
      return Pageable.super.isPaged();
    }

    @Override
    public boolean isUnpaged() {
      return Pageable.super.isUnpaged();
    }

    @Override
    public int getPageNumber() {
      return 0;
    }

    @Override
    public int getPageSize() {
      return 5;
    }

    // Expect this to throw Exception
    @Override
    public long getOffset() {
      return Long.MAX_VALUE;
    }

    @Override
    public Sort getSort() {
      return null;
    }

    @Override
    public Sort getSortOr(Sort sort) {
      return Pageable.super.getSortOr(sort);
    }

    @Override
    public Pageable next() {
      return null;
    }

    @Override
    public Pageable previousOrFirst() {
      return null;
    }

    @Override
    public Pageable first() {
      return null;
    }

    @Override
    public Pageable withPage(int pageNumber) {
      return null;
    }

    @Override
    public boolean hasPrevious() {
      return false;
    }

    @Override
    public Optional<Pageable> toOptional() {
      return Pageable.super.toOptional();
    }
  }
}
