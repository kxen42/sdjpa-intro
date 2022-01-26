package guru.springframework.sdjpaintro.jpa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
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
    }

    @Test
    void testFindAuthorJpaByName() {
        fail("red");
    }

    @Test
    void testSaveNewAuthorJpa() {
        fail("red");
    }

    @Test
    void testUpdateAuthorJpa() {
        fail("red");
    }

    @Test
    void testDeleteAuthorJpaById() {
        fail("red");
    }

}
