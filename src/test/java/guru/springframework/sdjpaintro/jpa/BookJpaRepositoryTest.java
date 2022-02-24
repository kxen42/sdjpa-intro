package guru.springframework.sdjpaintro.jpa;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import guru.springframework.sdjpaintro.jpa.repository.BookJpaRepository;

/** Testing Repository directly to demo nullable handling. */
@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"guru.springframework.sdjpaintro.jpa.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookJpaRepositoryTest {

  @Autowired BookJpaRepository repository;

  @Test
  @DisplayName("Default Behavior With package-info.java Now Throws Exception")
  void readByTitle_EmptyResult() {
    assertThrows(
        EmptyResultDataAccessException.class,
        () -> {
          repository.readByTitle("Bogus");
        });
  }

  @Test
  @DisplayName("Nullable Parameter Returns Null")
  void getByTitle_NullParam() {
    assertNull(repository.getByTitle(null));
  }

  @Test
  @DisplayName("Nullable Return Does Not Throw Exception")
  void getByTitle_NullReturn() {
    assertDoesNotThrow(
        () -> {
          repository.getByTitle("Bogus");
        });
  }
}
