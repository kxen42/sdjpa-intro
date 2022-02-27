package guru.springframework.sdjpaintro.jdbctemplate.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import guru.springframework.sdjpaintro.jdbctemplate.domain.AuthorJdbcTemplate;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorJdbcTemplateDaoTest {

  @Autowired JdbcTemplate jdbcTemplate;
  AuthorJdbcTemplateDao authorDao;

  static final AuthorJdbcTemplate gaiman = new AuthorJdbcTemplate("Neil", "Gaiman", null);

  @BeforeEach
  void setup() {
    authorDao = new AuthorJdbcTemplateDaoImpl(jdbcTemplate);
  }

  @Nested
  @DisplayName("Read Operations")
  class PrePrimed {
    @Test
    void getById() {
      AuthorJdbcTemplate found = authorDao.getById(1L);
      assertThat(found).isNotNull();
      System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx");
      System.out.println("PrePrimed.getById " + found);
      System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx");
    }

    @Test
    void findAuthorByName() {
      AuthorJdbcTemplate found = authorDao.findAuthorByName("Craig", "Walls");
      assertThat(found).isNotNull();
      System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx");
      System.out.println("PrePrimed.findAuthorByName " + found);
      System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx");
    }

    @Test
    void findAuthorBookById() {
      AuthorJdbcTemplate found = authorDao.findAuthorBookById(1L);
      assertThat(found).isNotNull();
      System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx");
      System.out.println("PrePrimed.findAuthorBookById " + found);
      System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx");
    }

    @Test
    void findAllAuthorsByLastName() {
      List<AuthorJdbcTemplate> authors =
          authorDao.findAllAuthorsByLastName("Smith", PageRequest.of(0, 10));

      assertThat(authors).isNotNull().size().isEqualTo(10);
    }

    @Test
    void findAllAuthorsByLastNameDescendingOrderPage1() {
      List<AuthorJdbcTemplate> authors =
          authorDao.findAllAuthorsByLastName(
              "Smith", PageRequest.of(0, 10, Sort.by(Order.desc("firstname"))));

      assertThat(authors).isNotNull().size().isEqualTo(10);
      assertThat(authors.get(0).getFirstName()).isEqualTo("Yugal");
    }

    @Test
    void findAllAuthorsByLastNameDescendingOrder2() {
      // fetch page 1
      int pageIndex = 0;
      authorDao.findAllAuthorsByLastName(
          "Smith", PageRequest.of(pageIndex++, 10, Sort.by(Order.desc("firstname"))));
      // fetch page 2
      List<AuthorJdbcTemplate> authors =
          authorDao.findAllAuthorsByLastName(
              "Smith", PageRequest.of(pageIndex, 10, Sort.by(Order.desc("firstname"))));

      assertThat(authors).isNotNull().size().isEqualTo(10);
      assertThat(authors.get(0).getFirstName()).isEqualTo("Surendra");

      // My attempt to test result sorting
      List<AuthorJdbcTemplate> expected =
          authors.stream()
              .sorted((o1, o2) -> o2.getFirstName().compareTo(o1.getFirstName()))
              .collect(Collectors.toList());
      assertIterableEquals(authors, expected);
    }

    @Test
    void findAllAuthorsByLastNameAscendingOrder() {
      List<AuthorJdbcTemplate> authors =
          authorDao.findAllAuthorsByLastName(
              "Smith", PageRequest.of(0, 10, Sort.by(Order.asc("firstname"))));

      assertThat(authors).isNotNull().size().isEqualTo(10);
      assertThat(authors.get(0).getFirstName()).isEqualTo("Ahmed");

      // My attempt to test result sorting
      List<AuthorJdbcTemplate> expected =
          authors.stream()
              .sorted((o1, o2) -> o1.getFirstName().compareTo(o2.getFirstName()))
              .collect(Collectors.toList());
      assertIterableEquals(authors, expected);
    }

    @Test
    void findAllAuthorsByLastNameGetEmAllKirby() {
      List<AuthorJdbcTemplate> authors =
          authorDao.findAllAuthorsByLastName("Smith", PageRequest.of(0, 100));

      assertThat(authors).isNotNull().size().isEqualTo(40);
    }
  }

  @Nested
  @DisplayName("Insert, Update, Delete")
  // the Ids increase because the DB is using AUTO INCREMENT, when rollbacks occur ins the gaps can
  // appear in the IDs
  class DataMutation {
    @Test
    void saveNewAuthor() {
      assertThat(gaiman.getId()).isNull();
      AuthorJdbcTemplate saved = authorDao.saveNewAuthor(gaiman);
      assertThat(saved).isNotNull();
      assertThat(saved.getId()).isNotNull();
      System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx");
      System.out.println("Interdependent.saveNewAuthor " + saved);
      System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx");
    }

    @Transactional
    @Test
    void updateAuthor() {

      System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx");

      AuthorJdbcTemplate original = authorDao.saveNewAuthor(gaiman);
      // can't do this because it is detached, and doesn't have an id!
      // gaiman.setLastName("Armstrong");
      System.out.println("Interdependent.updateAuthor original: " + original);

      original.setLastName("Armstrong");
      AuthorJdbcTemplate updated = authorDao.updateAuthor(original);
      System.out.println("Interdependent.updateAuthor updated: " + updated);
      System.out.println("Interdependent.updateAuthor after update: " + original);

      assertThat(updated).isNotNull();
      assertThat(updated.getLastName()).isEqualTo("Armstrong");
      assertThat(original.getLastName()).isEqualTo("Armstrong");
      assertThat(updated).isEqualTo(original);

      System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx");
    }

    @Test
    void deleteAuthorJdbcTemplateById() {
      AuthorJdbcTemplate saved = authorDao.saveNewAuthor(gaiman);
      authorDao.deleteAuthorById(saved.getId());
      assertThrows(
          EmptyResultDataAccessException.class,
          () -> {
            authorDao.getById(gaiman.getId());
          });
    }
  }
}
