package guru.springframework.sdjpaintro.hibernate.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import guru.springframework.sdjpaintro.hibernate.domain.AuthorHibernate;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"guru.springframework.sdjpaintro.hibernate.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorHibernateDaoImplTest {

  @Autowired AuthorHibernateDao authorDao;
  @Autowired EntityManagerFactory emf;

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
      AuthorHibernate deleted = em.find(AuthorHibernate.class, id);
      em.close();

      assertThat(deleted).isNull();
    }

    @Test
    void testUpdateAuthor() {
      AuthorHibernate author = new AuthorHibernate();
      author.setFirstName("Gore");
      author.setLastName("V");
      AuthorHibernate saved = authorDao.saveNewAuthor(author);
      System.out.println("MutationOperations.testUpdateAuthor test preparation saved: " + saved);
      EntityManager em = emf.createEntityManager();
      AuthorHibernate checkSaved = em.find(AuthorHibernate.class, saved.getId());
      System.out.println("MutationOperations.testUpdateAuthor checkSaved:" + checkSaved);
      assertThat(checkSaved).isNotNull();
      em.close();

      saved.setLastName("Vidal");
      System.out.println(
          "MutationOperations.testUpdateAuthor change name pending update: " + saved);

      // I added @NaturalId to the lastName
      System.out.println(
          "MutationOperations.testUpdateAuthor expected: \n"
              + "javax.persistence.PersistenceException: org.hibernate.HibernateException: An immutable natural identifier of entity guru.springframework.sdjpaintro.hibernate.domain.AuthorHibernate was altered from `V` to `Vidal`\n");
      assertThrows(
          PersistenceException.class,
          () -> {
            authorDao.updateAuthor(saved);
          });

      saved.setLastName("V");
      saved.setSomeMutableField("BOO");
      AuthorHibernate updated = authorDao.updateAuthor(saved);
      System.out.println(
          "MutationOperations.testUpdateAuthor change name pending update: " + saved);
      System.out.println("MutationOperations.testUpdateAuthor after update: " + updated);

      assertThat(updated.getSomeMutableField()).isEqualTo("BOO");
      em = emf.createEntityManager();
      AuthorHibernate checkUpdated = em.find(AuthorHibernate.class, saved.getId());
      System.out.println("MutationOperations.testUpdateAuthor checkUpdated:" + checkUpdated);
      assertThat(checkSaved).isNotNull();
      em.close();
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

      EntityManager em = emf.createEntityManager();
      AuthorHibernate checkSaved = em.find(AuthorHibernate.class, saved.getId());
      System.out.println("MutationOperations.testSaveAuthor checkSaved:" + checkSaved);
      assertThat(checkSaved).isNotNull();
      em.close();
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
      AuthorHibernate checkSaved = em.find(AuthorHibernate.class, saved.getId());
      System.out.println("MutationOperations.testSaveAuthor checkSaved:" + checkSaved);
      assertThat(checkSaved).isNotNull();
      em.close();
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
      assertThat(authorDao.findAll().count()).isGreaterThanOrEqualTo(3);
      authorDao.findAll().forEach(System.out::println);
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
  }
}
