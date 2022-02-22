package guru.springframework.sdjpaintro.hibernate.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
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
}
