package guru.springframework.sdjpaintro.jdbc.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import guru.springframework.sdjpaintro.jdbc.domain.AuthorJdbc;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"guru.springframework.sdjpaintro.jdbc.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorJdbcDaoTest {

    @Autowired
    AuthorJdbcDao authorJdbcDao;

    @Test
    void getById() {
        AuthorJdbc author = authorJdbcDao.getById(1L);
        assertThat(author).isNotNull();
        System.out.println(author);
    }

    @Test
    void getByName() {
        AuthorJdbc author = authorJdbcDao.getByName("Joseph", "Campbell");
        assertThat(author).isNotNull();
        System.out.println(author);
    }
}