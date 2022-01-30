package guru.springframework.sdjpaintro.jpa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.ActiveProfiles;

import guru.springframework.sdjpaintro.jpa.dao.AuthorJpaDao;
import guru.springframework.sdjpaintro.jpa.domain.AuthorJpa;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"guru.springframework.sdjpaintro.jpa.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorDaoIntegrationTest {

  @Autowired AuthorJpaDao authorDao;

  @Test
  void testGetById() {
    AuthorJpa author = authorDao.getById(1L);

    assertThat(author).isNotNull();
    System.out.println("AuthorDaoIntegrationTest.testGetById " + author);
  }

  @Test
  void testFindAuthorJpaByName() {
    AuthorJpa newAuthor = new AuthorJpa("Erma", "Bombeck");
    authorDao.saveNewAuthorJpa(newAuthor);
    System.out.println("AuthorDaoIntegrationTest.testFindAuthorJpaByName " + newAuthor);

    AuthorJpa fetched = authorDao.findAuthorJpaByName("Erma", "Bombeck");
    assertThat(fetched).isNotNull();

    System.out.println("AuthorDaoIntegrationTest.testFindAuthorJpaByName " + fetched);
  }

  @Test
  void optionalFindAuthoJpaByName() {
    AuthorJpa newAuthor = new AuthorJpa("Erma", "Bombeck");
    authorDao.saveNewAuthorJpa(newAuthor);

    assertThrows(
        EntityNotFoundException.class,
        () -> {
          AuthorJpa author = authorDao.optionalFindJpaByName("Bogus");
        });

    assertDoesNotThrow(
        () -> {
          AuthorJpa author = authorDao.optionalFindJpaByName("Bombeck");
        });
  }

  @Test
  void testSaveNewAuthorJpa() {
    AuthorJpa newAuthor = new AuthorJpa("Frank", "Herbert");
    AuthorJpa saved = authorDao.saveNewAuthorJpa(newAuthor);
    assertThat(saved).isNotNull();
    System.out.println("AuthorDaoIntegrationTest.testSaveNewAuthorJpa " + saved);
  }

  @Test
  void testUpdateAuthorJpa() {
    AuthorJpa newAuthor = new AuthorJpa("Joe", "Tentpeg");
    AuthorJpa saved = authorDao.saveNewAuthorJpa(newAuthor);
    System.out.println("AuthorDaoIntegrationTest.testUpdateAuthorJpa " + saved);

    saved.setLastName("Doe");
    AuthorJpa updated = authorDao.updateAuthorJpa(saved);

    assertThat(updated.getLastName()).isEqualTo("Doe");
    System.out.println("AuthorDaoIntegrationTest.testUpdateAuthorJpa " + updated);
  }

  @Test
  void testDeleteAuthorJpaById() {
    AuthorJpa newAuthor = new AuthorJpa("Mark", "Twain");
    AuthorJpa saved = authorDao.saveNewAuthorJpa(newAuthor);
    System.out.println("AuthorDaoIntegrationTest.testDeleteAuthorJpaById " + saved);
    assertThat(saved).isNotNull();

    authorDao.deleteAuthorJpaById(saved.getId());

    assertThrows(
        JpaObjectRetrievalFailureException.class,
        () -> {
          AuthorJpa deleted = authorDao.getById(saved.getId());
        });
  }
}
