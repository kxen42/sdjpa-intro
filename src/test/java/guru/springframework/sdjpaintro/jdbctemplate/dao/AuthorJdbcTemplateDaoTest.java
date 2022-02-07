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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import guru.springframework.sdjpaintro.jdbctemplate.domain.AuthorJdbcTemplate;


@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = {"guru.springframework.sdjpaintro.jdbctemplate.dao"})
class AuthorJdbcTemplateDaoTest {

    @Autowired
    AuthorJdbcTemplateDao authorDao;

    static final AuthorJdbcTemplate gaiman = new AuthorJdbcTemplate("Neil", "Gaiman", null);

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
}

  @Nested
  @DisplayName("Insert, Update, Delete")
  // the Ids increase because the DB is using AUTO INCREMENT, when rollbacks occur ins the gaps can appear in the IDs
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
        authorDao.deleteAuthorJdbcTemplateById(saved.getId());
      assertThrows(
          EmptyResultDataAccessException.class,
          () -> {
            authorDao.getById(gaiman.getId());
          });
    }
    }
}