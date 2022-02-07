package guru.springframework.sdjpaintro.jdbctemplate.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import guru.springframework.sdjpaintro.jdbctemplate.domain.AuthorJdbcTemplate;
import guru.springframework.sdjpaintro.jdbctemplate.domain.BookJdbcTemplate;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = {"guru.springframework.sdjpaintro.jdbctemplate.dao"})
class BookJdbcTemplateDaoTest {

  @Autowired BookJdbcTemplateDao bookDao;

  @Autowired AuthorJdbcTemplateDao authorDao;

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
