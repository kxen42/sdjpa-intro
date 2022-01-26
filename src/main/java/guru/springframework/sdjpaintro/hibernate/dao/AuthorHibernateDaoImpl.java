package guru.springframework.sdjpaintro.hibernate.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import guru.springframework.sdjpaintro.hibernate.domain.AuthorHibernate;

/**
 * Using Hibernate DAO pattern rather than Repository pattern.
 * The AuthorHibernate is a Spring @Entity (javax.persistence.Entity) implemented by Hibernate
 */
@Component
public class AuthorHibernateDaoImpl implements AuthorHibernateDao {

    private final EntityManagerFactory emf;

    // Spring Boot provides it, and Spring > 3 knows to wire this
    public AuthorHibernateDaoImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public AuthorHibernate getById(Long id) {
        return getEntityManager().find(AuthorHibernate.class, id);
    }

    @Override
    public AuthorHibernate findAuthorByName(String firstName, String lastName) {
        // just one of the many ways to do this
        TypedQuery<AuthorHibernate> query =  getEntityManager().createQuery("SELECT a FROM AuthorHibernate a " +
            "WHERE a.firstName=:first_name AND a.lastName = :last_name", AuthorHibernate.class);
        query.setParameter("first_name", firstName);
        query.setParameter("last_name", lastName);
        return query.getSingleResult();
    }

    // Caution: When Hibernate is being lazy
    // Hibernate might not persist an object right away, later he'll cover working with Tx
    @Override
    public AuthorHibernate saveNewAuthor(AuthorHibernate author) {
        // joinTransaction will be in sync with Spring Tx unless it's joining a Hibernate Tx that you created
        EntityManager em = getEntityManager();

        // needed for flush()
        // throws exception if Tx does not exist
        em.joinTransaction();

        em.persist(author);

        // will force the persistence action to happen
        // has to be done in a transaction, the Tx is acquired above by joinTransaction()
        em.flush();

        // Hibernate sets the ID when the persistence action happens, return the object passed to persist().
        return author;
    }

    // Caution: When Hibernate is being lazy
    // Hibernate might not persist an object right away, later he'll cover working with Tx
    @Override
    public AuthorHibernate alternateSaveNewAuthor(AuthorHibernate author) {
        // Caution: using begin/end of Hibernate Tx manager won't be in sync with Spring Boot Tx manager - test stays in MySQL
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(author);
        em.flush(); // do it NOW!
        em.getTransaction().commit();
        // Hibernate added ID
        return author;
    }

    @Override
    public AuthorHibernate updateAuthor(AuthorHibernate author) {
        return null;
    }

    @Override
    public void deleteAuthorById(Long id) {

    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
