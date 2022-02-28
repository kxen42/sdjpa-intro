package guru.springframework.sdjpaintro.hibernate.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import guru.springframework.sdjpaintro.hibernate.domain.AuthorHibernate;

/**
 * Even though the AuthorHibernateDaoImpl is not a Component, leave the ComponentScan in so the
 * ApplicationContext isn't reloaded when other tests in this package use Springy things
 */
@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"guru.springframework.sdjpaintro.hibernate.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorHibernateDaoImplTest {

  AuthorHibernateDao authorDao;
  @Autowired EntityManagerFactory emf;

  @BeforeEach
  void setup() {
    authorDao = new AuthorHibernateDaoImpl(emf);
  }

  @Nested
  @DisplayName("Insert/Update/Delete")
  class MutationOperations {

    @Test
    void testDeleteAuthor() {
      AuthorHibernate author = new AuthorHibernate("j", "t");

      AuthorHibernate saved = authorDao.alternateSaveNewAuthor(author);
      System.out.println("MutationOperations.testDeleteAuthor saved:" + saved);

      Long id = saved.getId();
      ;
      authorDao.deleteAuthorById(id);

      // unlike JDBC this does not throw an exception
      EntityManager em = emf.createEntityManager();
      try {
        AuthorHibernate deleted = em.find(AuthorHibernate.class, id);
        assertThat(deleted).isNull();
      } finally {
        em.close();
      }
    }

    @Test
    void testUpdateAuthor() {
      AuthorHibernate author;
      EntityManager em = emf.createEntityManager();
      try {
        author = em.find(AuthorHibernate.class, 1L);
        System.out.println("MutationOperations.testUpdateAuthor checkSaved:" + author);
      } finally {
        em.close();
      }

      author.setFirstName("Gore");
      author.setLastName("Vidal");
      System.out.println(
          "MutationOperations.testUpdateAuthor change name pending update: " + author);

      // I added @NaturalId to the lastName
      System.out.println(
          "MutationOperations.testUpdateAuthor expected: \n"
              + "javax.persistence.PersistenceException: org.hibernate.HibernateException: An immutable natural identifier of entity guru.springframework.sdjpaintro.hibernate.domain.AuthorHibernate was altered from `V` to `Vidal`\n");

      assertThrows(
          PersistenceException.class,
          () -> {
            authorDao.updateAuthor(author);
          });
    }

    @Test
    void testSaveAuthor() {
      // Without the PropagationType.REQUIRES_NEW in the DAO I could not get this test to pass
      AuthorHibernate author = new AuthorHibernate();
      author.setFirstName("J.R.R.");
      author.setLastName("Tolkien");
      AuthorHibernate saved = authorDao.saveNewAuthor(author);

      assertThat(saved).isNotNull();
      assertThat(saved.getId()).isNotNull();
      System.out.println("MutationOperations.testSaveAuthor saved:" + saved);

      // Fails if you don't run query in its own Hibernate Tx
      EntityManager em = emf.createEntityManager();
      try {
        em.getTransaction().begin();
        AuthorHibernate checkSaved = em.find(AuthorHibernate.class, saved.getId());
        em.getTransaction().commit();
        System.out.println("MutationOperations.testSaveAuthor checkSaved:" + checkSaved);
        assertThat(checkSaved).isNull();
      } finally {
        em.close();
      }
    }

    @Test
    void testAlternateSaveAuthor() {
      AuthorHibernate author = new AuthorHibernate();
      author.setFirstName("Mark");
      author.setLastName("Twain");
      AuthorHibernate saved = authorDao.alternateSaveNewAuthor(author);

      assertThat(saved).isNotNull();
      assertThat(saved.getId()).isNotNull();
      System.out.println("MutationOperations.testAlternateSaveAuthor saved:" + saved);

      EntityManager em = emf.createEntityManager();
      try {
        AuthorHibernate checkSaved = em.find(AuthorHibernate.class, saved.getId());
        System.out.println("MutationOperations.testSaveAuthor checkSaved:" + checkSaved);
        assertThat(checkSaved).isNotNull();
      } finally {
        em.close();
      }
    }
  }

  @Nested
  @DisplayName("DAO Read Operations")
  class ReadOperations {
    // Using data that is known to exist in DB

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

    @Test
    void findAll() {
      assertThat(authorDao.findAll()).isNotEmpty();
      authorDao.findAll().forEach(System.out::println);
    }

    @Test
    void findByLastName() {
      assertThat(authorDao.findByLastName("Walls")).isNotEmpty();
    }

    @Test
    void listAuthorByLastNameLike() {
      List<AuthorHibernate> al = authorDao.listAuthorByLastNameLike("_a%");
      al.forEach(System.out::println);

      assertThat(al).isNotEmpty();

      List<AuthorHibernate> z = authorDao.listAuthorByLastNameLike("z%");
      z.forEach(System.out::println);

      assertThat(z).isEmpty();
    }

    @Test
    void findAuthorByNameCriteria() {
      AuthorHibernate found = authorDao.findAuthorByNameCriteria("Craig", "Walls");
      assertThat(found).isNotNull();
    }

    @Test
    void findAuthorByNameNative() {
      AuthorHibernate found = authorDao.findAuthorByNameNative("Craig", "Walls");
      assertThat(found).isNotNull();
    }

    @Test
    void findAllAuthorsByLastNamePaging() {
      // natural order from DB
      List<AuthorHibernate> authors = authorDao.findAllAuthorsByLastName("Smith", null);
      assertThat(authors).isNotNull().hasSize(40);

      System.out.println("ReadOperations.findAllAuthorsByLastName first Author: " + authors.get(0));
      assertThat(authors.get(0).getFirstName()).isEqualTo("Robert");
    }

    @Test
    void findAllAuthorsByLastNameWithoutSort() {
      // natural order from DB
      List<AuthorHibernate> authors =
          authorDao.findAllAuthorsByLastName("Smith", PageRequest.of(0, 10));
      assertThat(authors).isNotNull().hasSize(10);

      System.out.println("ReadOperations.findAllAuthorsByLastName first Author: " + authors.get(0));
      assertThat(authors.get(0).getFirstName()).isEqualTo("Robert");
    }

    @Test
    void findAllAuthorsByLastNameDescending() {
      List<AuthorHibernate> authors =
          authorDao.findAllAuthorsByLastName(
              "Smith", PageRequest.of(0, 10, Sort.by(Sort.Order.desc("firstname"))));
      assertThat(authors).isNotNull().hasSize(10);

      System.out.println("ReadOperations.findAllAuthorsByLastName first Author: " + authors.get(0));
      assertThat(authors.get(0).getFirstName()).isEqualTo("Yugal");
    }

    @Test
    void findAllAuthorsByLastNameAscending() {
      List<AuthorHibernate> authors =
          authorDao.findAllAuthorsByLastName(
              "Smith", PageRequest.of(0, 10, Sort.by(Sort.Order.asc("firstname"))));
      assertThat(authors).isNotNull().hasSize(10);

      System.out.println("ReadOperations.findAllAuthorsByLastName first Author: " + authors.get(0));
      assertThat(authors.get(0).getFirstName()).isEqualTo("Ahmed");
    }

    @Test
    void findAllAuthorsByLastNameTwoPages() {
      authorDao.findAllAuthorsByLastName(
          "Smith", PageRequest.of(0, 10, Sort.by(Sort.Order.asc("firstname"))));

      List<AuthorHibernate> authors =
          authorDao.findAllAuthorsByLastName(
              "Smith", PageRequest.of(1, 10, Sort.by(Sort.Order.asc("firstname"))));
      assertThat(authors).isNotNull().hasSize(10);

      System.out.println("ReadOperations.findAllAuthorsByLastName first Author: " + authors.get(0));
      assertThat(authors.get(0).getFirstName()).isEqualTo("Dinesh");
    }
  }
}
