package guru.springframework.sdjpaintro.jdbc.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import guru.springframework.sdjpaintro.jdbc.domain.AuthorJdbc;

/**
 * NOTE: This is not rolling back the tests. With a straight JDBC connection, you have to manage
 * transactions. JDBC doesn't run in the Sp Tx context unlike JdbcTemplate or Sp. Data JPA.
 */
@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"guru.springframework.sdjpaintro.jdbc.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorJdbcDaoTest {

  @Autowired AuthorJdbcDao authorJdbcDao;

  @Test
  void getById() {
    AuthorJdbc found = authorJdbcDao.getById(1L);

    assertThat(found).isNotNull();
    System.out.println("\nxxxxxxxxxxxxxxxxxxxxxxxxx");
    System.out.println(found);
    System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx\n");
  }

  @Test
  void findAuthorJdbcByName() {
    AuthorJdbc found = authorJdbcDao.findAuthorJdbcByName("Joseph", "Campbell");

    assertThat(found).isNotNull();
    System.out.println("\nxxxxxxxxxxxxxxxxxxxxxxxxx");
    System.out.println(found);
    System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx\n");
  }

  @Test
  void saveNewAuthorJdbc() {
    AuthorJdbc author = new AuthorJdbc("Ken", "Kousen");
    AuthorJdbc saved = authorJdbcDao.saveNewAuthorJdbc(author);

    assertThat(saved).isNotNull();
    assertThat(saved.getId()).isNotNull();
    System.out.println("\nxxxxxxxxxxxxxxxxxxxxxxxxx");
    System.out.println(saved);
    System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx\n");
  }

  @Test
  void updateAuthorJdbc() {
    AuthorJdbc author = new AuthorJdbc("Dr.", "Seuss");
    AuthorJdbc saved = authorJdbcDao.saveNewAuthorJdbc(author);

    assertThat(saved).isNotNull();
    assertThat(saved.getId()).isNotNull();
    System.out.println("\nxxxxxxxxxxxxxxxxxxxxxxxxx");
    System.out.println(saved);

    saved.setFirstName("Thing1");
    saved.setLastName("Thing2");
    AuthorJdbc updated = authorJdbcDao.updateAuthorJdbc(saved);

    assertThat(updated.getFirstName()).isEqualTo("Thing1");
    assertThat(updated.getLastName()).isEqualTo("Thing2");

    System.out.println(updated);
    System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx\n");
  }

  @Test
  void deleteAuthorJdbcById() {
    AuthorJdbc author = new AuthorJdbc("Dr.", "Seuss");
    AuthorJdbc saved = authorJdbcDao.saveNewAuthorJdbc(author);

    assertThat(saved).isNotNull();
    assertThat(saved.getId()).isNotNull();
    System.out.println("\nxxxxxxxxxxxxxxxxxxxxxxxxx");
    System.out.println(saved);

    authorJdbcDao.deleteAuthorJdbcById(saved.getId());

    AuthorJdbc fetched = authorJdbcDao.getById(saved.getId());
    assertThat(fetched).isNull();

    System.out.println(fetched);
    System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx\n");
  }
}
