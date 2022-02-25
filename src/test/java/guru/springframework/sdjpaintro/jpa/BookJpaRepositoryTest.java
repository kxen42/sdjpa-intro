package guru.springframework.sdjpaintro.jpa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import guru.springframework.sdjpaintro.jpa.domain.BookJpa;
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

  @Test
  @DisplayName("Demo Stream Query")
  void findAllByTitleNotNull() {
    // Using AtomicInteger because Functional Programming expects you to use Immutable objects
    AtomicInteger count = new AtomicInteger();
    repository
        .findAllByTitleNotNull()
        .forEach(
            book -> {
              count.incrementAndGet();
            });

    assertThat(count.get()).isGreaterThanOrEqualTo(5);
  }

  @Test
  @DisplayName("@Async Query Returning Future")
  void queryByTitle() throws ExecutionException, InterruptedException {
    Future<BookJpa> bookFuture = repository.queryByTitle("Clean Code");
    BookJpa book = bookFuture.get();

    assertNotNull(book);
  }

  @Test
  void findBookByTitleWithAnnotation() {
    BookJpa found = repository.findBookByTitleWithAnnotation("Clean Code");
    System.out.println("BookJpaRepositoryTest.findBookByTitleWithAnnotation found:" + found);
    assertNotNull(found);
  }

  @Test
  void findBookByTitleWithQueryNamedParam() {
    BookJpa found = repository.findBookByTitleWithQueryNamedParam("Clean Code");
    assertNotNull(found);
  }

  @Test
  void findBookByTitleNativeQuery() {
    BookJpa found = repository.findBookByTitleNativeQuery("Clean Code");
    assertNotNull(found);
  }

  @Test
  void findBookByTitleNamedQuery() {
    BookJpa found = repository.jpaNamed("Clean Code");
    assertNotNull(found);
  }
}
