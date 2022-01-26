package guru.springframework.sdjpaintro.jpa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
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
public class DaoIntegrationTest {

    @Autowired
    AuthorJpaDao authorDao;

    @Test
    void testGetById() {
        AuthorJpa author = authorDao.getById(1L);

        assertThat(author).isNotNull();
        System.out.println("DaoIntegrationTest.testGetById " + author);
    }

    @Test
    void testFindAuthorJpaByName() {
        fail("red");
    }

    @Test
    void testSaveNewAuthorJpa() {
        AuthorJpa newAuthor = new AuthorJpa("Frank","Herbert");
        AuthorJpa saved = authorDao.saveNewAuthorJpa(newAuthor);
        assertThat(saved).isNotNull();
        System.out.println("DaoIntegrationTest.testSaveNewAuthorJpa " + saved );
    }

    @Test
    void testUpdateAuthorJpa() {
        fail("red");
    }

    @Test
    void testDeleteAuthorJpaById() {
        AuthorJpa newAuthor = new AuthorJpa("Mark", "Twain");
        AuthorJpa saved = authorDao.saveNewAuthorJpa(newAuthor);
        System.out.println("DaoIntegrationTest.testDeleteAuthorJpaById " + saved);
        assertThat(saved).isNotNull();

        authorDao.deleteAuthorJpaById(saved.getId());

        assertThrows(JpaObjectRetrievalFailureException.class, () -> {
            AuthorJpa deleted = authorDao.getById(saved.getId());
        });
    }

}
