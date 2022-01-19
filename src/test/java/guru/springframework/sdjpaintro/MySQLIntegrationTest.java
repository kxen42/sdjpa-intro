package guru.springframework.sdjpaintro;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import guru.springframework.sdjpaintro.domain.AuthorUuid;
import guru.springframework.sdjpaintro.domain.BookUuid;
import guru.springframework.sdjpaintro.repositories.AuthorUuidRepository;
import guru.springframework.sdjpaintro.repositories.BookRepository;
import guru.springframework.sdjpaintro.repositories.BookUuidRepository;

/**
 * Created by jt on 7/4/21.
 */
@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"guru.springframework.sdjpaintro.bootstrap"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MySQLIntegrationTest {

    @Autowired
    BookRepository bookRepository;
    @Autowired
    AuthorUuidRepository authorUuidRepository;
    @Autowired
    BookUuidRepository bookUuidRepository;

    @Test
    void testMySQL() {
        long countBefore = bookRepository.count();
        assertThat(countBefore).isEqualTo(2);

    }

    @Test
    void testAuthUuid() {
        // prove ID auto generation & persistence
        AuthorUuid savedAu = authorUuidRepository.save(new AuthorUuid());
        assertThat(savedAu).isNotNull();
        assertThat(savedAu.getId()).isNotNull();

        // prove persistence
        AuthorUuid fetched = authorUuidRepository.getById(savedAu.getId());
        assertThat(fetched).isNotNull();
    }

    @Test
    void testBookUuid() {
        // prove ID auto generation & persistence
        BookUuid savedBu = bookUuidRepository.save(new BookUuid());
        assertThat(savedBu).isNotNull();
        assertThat(savedBu.getId()).isNotNull();

        // prove persistence
        BookUuid fetched = bookUuidRepository.getById(savedBu.getId());
        assertThat(fetched).isNotNull();
    }
}


