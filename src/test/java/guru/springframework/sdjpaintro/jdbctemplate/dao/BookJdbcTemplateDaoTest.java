package guru.springframework.sdjpaintro.jdbctemplate.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import guru.springframework.sdjpaintro.jdbctemplate.domain.AuthorJdbcTemplate;
import guru.springframework.sdjpaintro.jdbctemplate.domain.BookJdbcTemplate;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookJdbcTemplateDaoTest {

  @Autowired JdbcTemplate jdbcTemplate;

  // purposely not using DAOs as @Compents just for grins & giggles
  BookJdbcTemplateDao bookDao;
  AuthorJdbcTemplateDao authorDao;

  @BeforeEach
  void setup() {
    // this doesn't waste connections because they use the jdbcTemplate where the connection stuff
    // is.
    bookDao = new BookJdbcTemplateDaoImpl(jdbcTemplate);
    authorDao = new AuthorJdbcTemplateDaoImpl(jdbcTemplate);
  }

  static final AuthorJdbcTemplate testAuthor = new AuthorJdbcTemplate("Neil", "Gaiman", null);
  static final BookJdbcTemplate testBook = new BookJdbcTemplate("Some Book", "1234-54", "Shady");

  @Nested
  @DisplayName("Read Operations")
  class PrePrimed {
    @Test
    void getById() {
      BookJdbcTemplate found = bookDao.getById(1L);
      assertThat(found).isNotNull();
      System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx");
      System.out.println("PrePrimed.getById " + found);
      System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx");
    }

    @Test
    void findBookByName() {
      BookJdbcTemplate found = bookDao.findBookByTitle("Spring in Action, 5th Edition");
      assertThat(found).isNotNull();
      System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx");
      System.out.println("PrePrimed.findBookByName " + found);
      System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx");
    }

    @Test
    void findAllBooksPaged() {
      System.out.println("\nxxxxxxxxxxxxxxxxxxxxxxxxx");
      System.out.println("PrePrimed.findAllBooksPaged page1 -----");
      List<BookJdbcTemplate> page1 = bookDao.findAllBooks(2, 0);

      assertThat(page1).isNotNull().size().isPositive().isLessThanOrEqualTo(2);
      page1.forEach(System.out::println);

      System.out.println("PrePrimed.findAllBooksPaged 2 -----");
      List<BookJdbcTemplate> page2 = bookDao.findAllBooks(2, 2);
      assertThat(page2).isNotNull().size().isPositive().isLessThanOrEqualTo(2);
      page2.forEach(System.out::println);

      System.out.println("PrePrimed.findAllBooksPaged page3 -----");
      List<BookJdbcTemplate> page3 = bookDao.findAllBooks(2, 4);
      assertThat(page3).isNotNull().size().isPositive().isLessThanOrEqualTo(2);
      page3.forEach(System.out::println);

      System.out.println("PrePrimed.findAllBooksPaged bogus -----");
      List<BookJdbcTemplate> bogus = bookDao.findAllBooks(2, 400);
      assertThat(bogus).isNotNull().isEmpty();
      System.out.println("\nxxxxxxxxxxxxxxxxxxxxxxxxx");
    }

    @Test
    void findAllBooksNotPaged() {
      List<BookJdbcTemplate> page = bookDao.findAllBooks();
      assertThat(page).isNotNull().size().isGreaterThanOrEqualTo(5);
      System.out.println("\nxxxxxxxxxxxxxxxxxxxxxxxxx");
      page.forEach(System.out::println);
      System.out.println("\nxxxxxxxxxxxxxxxxxxxxxxxxx");
    }
  }

  @Nested
  @DisplayName("Insert, Update, Delete")
  // the Ids increase because the DB is using AUTO INCREMENT, when rollbacks occur ins the gaps can
  // appear in the IDs
  class DataMutation {
    @Test
    void saveNewBook() {
      assertThat(testBook.getId()).isNull();
      BookJdbcTemplate saved = bookDao.saveNewBook(testBook);
      assertThat(saved).isNotNull();
      assertThat(saved.getId()).isNotNull();
      System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx");
      System.out.println("Interdependent.saveNewBook " + saved);
      System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx");
    }

    @Transactional
    @Test
    void updateBook() {

      System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx");
      // FK constraint in DB
      AuthorJdbcTemplate newAuthor = authorDao.saveNewAuthor(testAuthor);
      testBook.setAuthorId(newAuthor.getId());
      BookJdbcTemplate original = bookDao.saveNewBook(testBook);
      // can't do this because it is detached, and doesn't have an id!
      // testBook.setLastName("Armstrong");
      System.out.println("Interdependent.updateBook -> original: " + original);

      original.setPublisher("O'Reilly");
      BookJdbcTemplate updated = bookDao.updateBook(original);
      System.out.println("Interdependent.updateBook ->  updated: " + updated);
      System.out.println("Interdependent.updateBook -> original after update: " + original);

      assertThat(updated).isNotNull();
      assertThat(updated.getPublisher()).isEqualTo("O'Reilly");
      assertThat(original.getPublisher()).isEqualTo("O'Reilly");
      assertThat(updated).isEqualTo(original);

      System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx");
    }

    @Test
    void deleteBookJdbcTemplateById() {
      // FK constraint in DB
      AuthorJdbcTemplate newAuthor = authorDao.saveNewAuthor(testAuthor);
      testBook.setAuthorId(newAuthor.getId());
      BookJdbcTemplate saved = bookDao.saveNewBook(testBook);
      bookDao.deleteBookById(saved.getId());
      assertThrows(
          EmptyResultDataAccessException.class,
          () -> {
            bookDao.getById(testBook.getId());
          });
    }
  }
}
