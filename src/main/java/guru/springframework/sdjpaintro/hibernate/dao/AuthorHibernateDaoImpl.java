package guru.springframework.sdjpaintro.hibernate.dao;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.*;
import javax.persistence.criteria.*;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import guru.springframework.sdjpaintro.hibernate.domain.AuthorHibernate;

/**
 * Using Hibernate DAO pattern rather than Repository pattern. The AuthorHibernate is a
 * Spring @Entity (javax.persistence.Entity) implemented by Hibernate
 */
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
    TypedQuery<AuthorHibernate> query =
        getEntityManager()
            .createQuery(
                "SELECT a FROM AuthorHibernate a "
                    + "WHERE a.firstName=:first_name AND a.lastName = :last_name",
                AuthorHibernate.class);
    query.setParameter("first_name", firstName);
    query.setParameter("last_name", lastName);
    return query.getSingleResult();
  }

  // Caution: When Hibernate is being lazy
  // Hibernate might not persist an object right away, later he'll cover working with Tx
  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW) /* Without this the test fails */
  public AuthorHibernate saveNewAuthor(AuthorHibernate author) {
    // joinTransaction will be in sync with Spring Tx unless it's joining a Hibernate Tx that you
    // created
    EntityManager em = getEntityManager();

    // needed for flush()
    // throws exception if Tx does not exist
    em.joinTransaction();
    System.out.println(
        "AuthorHibernateDaoImpl.saveNewAuthor em.isJoinedToTransaction():"
            + em.isJoinedToTransaction());

    em.persist(author);

    // My attempt for find out why test fails
    // MySql function
    BigInteger createdId =
        (BigInteger) em.createNativeQuery("SELECT LAST_INSERT_ID()").getSingleResult();
    System.out.println("AuthorHibernateDaoImpl.saveNewAuthor SELECT LAST_INSERT_ID():" + createdId);

    // will force the persistence action to happen
    // has to be done in a transaction, the Tx is acquired above by joinTransaction()
    em.flush();

    // Hibernate sets the ID when the persistence action happens, return the object passed to
    // persist().
    AuthorHibernate newAuthor = em.find(AuthorHibernate.class, createdId.longValue());
    em.close(); // close EntityManager or risk running out of connections

    return newAuthor;
  }

  // Caution: When Hibernate is being lazy
  // Hibernate might not persist an object right away, later he'll cover working with Tx
  @Override
  public AuthorHibernate alternateSaveNewAuthor(AuthorHibernate author) {
    // Caution: using begin/end of Hibernate Tx manager won't be in sync with Spring Boot Tx manager
    // - test stays in MySQL
    EntityManager em = getEntityManager();
    em.getTransaction().begin();
    em.persist(author);
    em.flush(); // do it NOW!
    em.getTransaction().commit();
    em.close();
    // Hibernate added ID
    return author;
  }

  @Override
  public AuthorHibernate updateAuthor(AuthorHibernate author) {
    EntityManager em = getEntityManager();
    em.getTransaction().begin();
    AuthorHibernate merged = em.merge(author);
    em.flush();
    em.getTransaction().commit();
    em.close();
    return merged;
  }

  @Override
  public void deleteAuthorById(Long id) {
    EntityManager em = getEntityManager();
    em.getTransaction().begin();
    AuthorHibernate author = em.find(AuthorHibernate.class, id);
    em.remove(author);
    em.flush(); // do it NOW! don't linger in the cache "push it out of the cache"
    em.getTransaction().commit();
    em.close();
  }

  @Override
  public List<AuthorHibernate> findAll() {
    EntityManager em = getEntityManager();
    try {
      return em.createNamedQuery("find_all", AuthorHibernate.class).getResultList();
    } finally {
      em.close();
    }
  }

  @Override
  public List<AuthorHibernate> findByLastName(String lastName) {
    EntityManager em = getEntityManager();
    try {
      return em.createNamedQuery("find_by_lastname", AuthorHibernate.class)
          .setParameter("last_name", lastName)
          .getResultList();
    } finally {
      em.close();
    }
  }

  @Override
  public List<AuthorHibernate> listAuthorByLastNameLike(String pattern) {
    EntityManager em = getEntityManager();
    try {
      // he's going to enforce the type with the cast
      Query query =
          em.createQuery("SELECT a FROM AuthorHibernate a WHERE a.lastName LIKE :lastname");
      query.setParameter("lastname", pattern);
      return query.getResultList();
    } finally {
      em.close();
    }
  }

  @Override
  public AuthorHibernate findAuthorByNameCriteria(String firstName, String lastName) {
    EntityManager em = getEntityManager();

    try {
      CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
      CriteriaQuery<AuthorHibernate> criteriaQuery =
          criteriaBuilder.createQuery(AuthorHibernate.class);

      Root<AuthorHibernate> root = criteriaQuery.from(AuthorHibernate.class);

      ParameterExpression<String> firstNameParam = criteriaBuilder.parameter(String.class);
      ParameterExpression<String> lastNameParam = criteriaBuilder.parameter(String.class);

      Predicate firstNamePred = criteriaBuilder.equal(root.get("firstName"), firstNameParam);
      Predicate lastNamePred = criteriaBuilder.equal(root.get("lastName"), lastNameParam);

      criteriaQuery.select(root).where(criteriaBuilder.and(firstNamePred, lastNamePred));

      TypedQuery<AuthorHibernate> typedQuery = em.createQuery(criteriaQuery);
      typedQuery.setParameter(firstNameParam, firstName);
      typedQuery.setParameter(lastNameParam, lastName);

      return typedQuery.getSingleResult();
    } finally {
      em.close();
    }
  }

  @Override
  public AuthorHibernate findAuthorByNameNative(String firstName, String lastName) {
    EntityManager em = getEntityManager();
    try {
      // createNativeQuery does not create a TypedQuery
      // the type you give is used to map the values to the object properties
      // otherwise; you get an Object[]
      return (AuthorHibernate)
          em.createNativeQuery(
                  "SELECT * FROM author_hibernate WHERE first_name=:first_name AND last_name=:last_name",
                  AuthorHibernate.class)
              .setParameter("first_name", firstName)
              .setParameter("last_name", lastName)
              .getSingleResult();
    } finally {
      em.close();
    }
  }

  private EntityManager getEntityManager() {
    return emf.createEntityManager();
  }
}
