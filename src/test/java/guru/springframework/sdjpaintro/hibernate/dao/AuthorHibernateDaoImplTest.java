package guru.springframework.sdjpaintro.hibernate.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import guru.springframework.sdjpaintro.hibernate.domain.AuthorHibernate;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"guru.springframework.sdjpaintro.hibernate.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorHibernateDaoImplTest {

  @Autowired AuthorHibernateDao authorDao;

  //    @Autowired
  //    BookJpaDao bookDao;
  //
  //    @Test
  //    void testDeleteBook() {
  //        Book book = new Book();
  //        book.setIsbn("1234");
  //        book.setPublisher("Self");
  //        book.setTitle("my book");
  //        Book saved = bookDao.saveNewBook(book);
  //
  //        bookDao.deleteBookById(saved.getId());
  //
  //        Book deleted = bookDao.findById(saved.getId());
  //
  //        assertThat(deleted).isNull();
  //    }
  //
  //    @Test
  //    void updateBookTest() {
  //        Book book = new Book();
  //        book.setIsbn("1234");
  //        book.setPublisher("Self");
  //        book.setTitle("my book");
  //
  //        AuthorHibernate author = new AuthorHibernate();
  //        author.setId(3L);
  //
  //        book.setAuthor(author);
  //        Book saved = bookDao.saveNewBook(book);
  //
  //        saved.setTitle("New Book");
  //        bookDao.updateBook(saved);
  //
  //        Book fetched = bookDao.findById(saved.getId());
  //
  //        assertThat(fetched.getTitle()).isEqualTo("New Book");
  //    }
  //
  //    @Test
  //    void testSaveBook() {
  //        Book book = new Book();
  //        book.setIsbn("1234");
  //        book.setPublisher("Self");
  //        book.setTitle("my book");
  //
  //        AuthorHibernate author = new AuthorHibernate();
  //        author.setId(3L);
  //
  //        book.setAuthor(author);
  //        Book saved = bookDao.saveNewBook(book);
  //
  //        assertThat(saved).isNotNull();
  //    }
  //
  //    @Test
  //    void testGetBookByName() {
  //        Book book = bookDao.findBookByTitle("Clean Code");
  //
  //        assertThat(book).isNotNull();
  //    }
  //
  //    @Test
  //    void testGetBook() {
  //        Book book = bookDao.findById(3L);
  //
  //        assertThat(book.getId()).isNotNull();
  //    }

  @Test
  void testDeleteAuthor() {
    AuthorHibernate author = new AuthorHibernate();
    author.setFirstName("john");
    author.setLastName("t");

    AuthorHibernate saved = authorDao.saveNewAuthor(author);

    authorDao.deleteAuthorById(saved.getId());

    assertThrows(
        EmptyResultDataAccessException.class,
        () -> {
          AuthorHibernate deleted = authorDao.getById(saved.getId());
        });
  }

  @Test
  void testUpdateAuthor() {
    AuthorHibernate author = new AuthorHibernate();
    author.setFirstName("john");
    author.setLastName("t");

    AuthorHibernate saved = authorDao.saveNewAuthor(author);

    saved.setLastName("Thompson");
    AuthorHibernate updated = authorDao.updateAuthor(saved);

    assertThat(updated.getLastName()).isEqualTo("Thompson");
  }

  @Test
  void testSaveAuthor() {
    AuthorHibernate author = new AuthorHibernate();
    author.setFirstName("John");
    author.setLastName("Thompson");
    AuthorHibernate saved = authorDao.saveNewAuthor(author);

    assertThat(saved).isNotNull();
    assertThat(saved.getId()).isNotNull();
    System.out.println(author);
  }

  @Test
  void testAlternateSaveAuthor() {
    AuthorHibernate author = new AuthorHibernate();
    author.setFirstName("Mark");
    author.setLastName("Twain");
    AuthorHibernate saved = authorDao.alternateSaveNewAuthor(author);

    assertThat(saved).isNotNull();
    assertThat(saved.getId()).isNotNull();
    System.out.println(author);
  }

  @Test
  void testGetAuthorByName() {
    AuthorHibernate author = authorDao.findAuthorByName("Craig", "Walls");

    assertThat(author).isNotNull();
  }

  @Test
  void testGetAuthor() {
    AuthorHibernate author = authorDao.getById(1L);

    assertThat(author).isNotNull();
  }
}
