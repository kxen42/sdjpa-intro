package guru.springframework.sdjpaintro.jpa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.ActiveProfiles;

import guru.springframework.sdjpaintro.jpa.dao.BookJpaDao;
import guru.springframework.sdjpaintro.jpa.domain.BookJpa;
import guru.springframework.sdjpaintro.jpa.repository.BookJpaRepository;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"guru.springframework.sdjpaintro.jpa.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookJpaDaoIntegrationTest {

  @Autowired BookJpaDao bookDao;
  BookJpa initialBook;

  @Autowired BookJpaRepository repository;

  @BeforeEach
  void init() {
    initialBook = new BookJpa();
    initialBook.setIsbn("1234");
    initialBook.setPublisher("Self");
    initialBook.setTitle("my book");

    repository.saveAndFlush(initialBook);
  }

  @Test
  void testDeleteBookJpa() {

    bookDao.deleteBookJpaById(initialBook.getId());

    // John Thompson doesn't use the repo so he does this
    assertThrows(
        JpaObjectRetrievalFailureException.class,
        () -> {
          bookDao.getById(initialBook.getId());
        });

    // if you include the repository you can do this
    // don't use both because execution order matters
    // assertThat(repository.existsById(initialBook.getId())).isFalse();
  }

  @Test
  void updateBookJpaTest() {

    BookJpa original = bookDao.saveNewBookJpa(initialBook);
    System.out.println("BookJpaDaoIntegrationTest.updateBookJpaTest original=" + original);

    original.setPublisher("Dewey, Cheatham, and Howe");
    original.setTitle("New BookJpa");
    bookDao.updateBookJpa(original);

    BookJpa updated = bookDao.getById(original.getId());
    assertThat(updated.getTitle()).isEqualTo("New BookJpa");
    assertThat(updated.getPublisher()).isEqualTo("Dewey, Cheatham, and Howe");
    System.out.println("BookJpaDaoIntegrationTest.updateBookJpaTest updated=" + updated);
  }

  @Test
  void testSaveBookJpa() {
    BookJpa newBook = new BookJpa();
    newBook.setIsbn("7890");
    newBook.setPublisher("University Press");
    newBook.setTitle("The Drunk Parrots of Latin");

    BookJpa saved = bookDao.saveNewBookJpa(newBook);

    assertThat(saved).isNotNull();
    System.out.println("BookJpaDaoIntegrationTest.testSaveBookJpa saved=" + saved);
  }

  @Test
  void findingGetBookJpaByName() {

    BookJpa found = bookDao.getBookJpaByName(initialBook.getTitle());
    assertThat(found).isNotNull();
    assertThat(found.getId()).isNotNull();
    System.out.println("BookJpaDaoIntegrationTest.testGetBookJpaByName found=" + found);
  }

  @Test
  void testGetBookJpaById() {
    BookJpa book = bookDao.getById(initialBook.getId());
    assertThat(book.getId()).isNotNull();
    System.out.println("BookJpaDaoIntegrationTest.testGetBookJpa book=" + book);
  }

  @Test
  void findBookByTitle() {
    BookJpa found = bookDao.findBookByTitle(initialBook.getTitle());
    assertThat(found).isNotNull();
  }
}
