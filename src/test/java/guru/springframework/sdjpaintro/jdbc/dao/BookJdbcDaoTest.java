package guru.springframework.sdjpaintro.jdbc.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import guru.springframework.sdjpaintro.jdbc.domain.BookJdbc;

/**
 * NOTE: This is not rolling back the tests. With a straight JDBC connection, you have to manage
 * transactions. JDBC doesn't run in the Sp Tx context unlike JdbcTemplate or Sp. Data JPA.
 */
@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"guru.springframework.sdjpaintro.jdbc.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookJdbcDaoTest {

  @Autowired BookJdbcDao bookDao;

  static final BookJdbc book = new BookJdbc("09-89", "Some Book", "Some Publisher");

  @Test
  void getById() {
    // assumes DB is primed with at least on row
    BookJdbc found = bookDao.getById(1L);

    assertThat(found).isNotNull();
    System.out.println("\nxxxxxxxxxxxxxxxxxxxxxxxxx");
    System.out.println(found);
    System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx\n");
  }

  @Test
  void findBookJdbcByTitle() {
    // assumes DB is primed with at least on row
    BookJdbc foundById = bookDao.getById(1L);
    BookJdbc foundByTitle = bookDao.findBookJdbcByTitle(foundById.getTitle());

    assertThat(foundByTitle).isNotNull();
    System.out.println("\nxxxxxxxxxxxxxxxxxxxxxxxxx");
    System.out.println(foundByTitle);
    System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx\n");
  }

  @Nested
  @DisplayName("Insert, Update, Delete book in that order")
  @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
  class InsertUpdateDelete {

    @Test
    @Order(1)
    void saveNewBookJdbc() {
      System.out.println("InsertUpdateDelete.saveNewBookJdbc save this book: " + book);
      BookJdbc saved = bookDao.saveNewBookJdbc(book);

      // this is a kludge - don't do this for realsies
      book.setId(saved.getId());

      assertThat(saved).isNotNull();
      assertThat(saved.getId()).isNotNull();
      System.out.println("\nxxxxxxxxxxxxxxxxxxxxxxxxx saveNewBookJdbc");
      System.out.println("saved this book: " + saved);
      System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx");
    }

    @Test
    @Order(2)
    void updateBookJdbc() {
      System.out.println("\nxxxxxxxxxxxxxxxxxxxxxxxxx updateBookJdbc");
      System.out.println("update this book: " + book);
      BookJdbc found = bookDao.getById(book.getId());
      found.setTitle("new title");
      BookJdbc updated = bookDao.updateBookJdbc(found);

      assertThat(updated).isNotNull();
      assertThat(updated.getTitle()).isEqualTo("new title");
      System.out.println("updated this book: " + updated);
      System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx");
    }

    @Test
    @Order(3)
    void deleteBookJdbcById() {
      BookJdbc found = bookDao.getById(book.getId());
      System.out.println("\nxxxxxxxxxxxxxxxxxxxxxxxxx deleteBookJdbcById");
      System.out.println("delete this book" + found);

      bookDao.deleteBookJdbcById(found.getId());

      BookJdbc fetched = bookDao.getById(found.getId());
      assertThat(fetched).isNull();

      System.out.println(fetched);
      System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx");
    }
  }
}
