package guru.springframework.sdjpaintro.hibernate.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import guru.springframework.sdjpaintro.hibernate.domain.BookHibernate;

/*
Purposely using EntityManager and try-finally to close to do this old school
 */
@Component
public class BookHibernateDaoImpl implements BookHibernateDao {

  private final EntityManagerFactory emf;

  public BookHibernateDaoImpl(EntityManagerFactory emf) {
    this.emf = emf;
  }

  @Override
  public BookHibernate getById(Long id) {
    EntityManager em = getEntityManager();
    try {
      return em.find(BookHibernate.class, id);
    } finally {
      em.close();
    }
  }

  @Override
  public BookHibernate findBookByTitle(String title) {
    EntityManager em = getEntityManager();
    try {
      return em.createNamedQuery("find_by_title", BookHibernate.class)
          .setParameter("title", title)
          .getSingleResult();
    } finally {
      em.close();
    }
  }

  @Override
  public BookHibernate findByIsbn(String isbn) {
    EntityManager em = getEntityManager();
    try {
      TypedQuery<BookHibernate> query =
          em.createQuery("SELECT t FROM BookHibernate t WHERE t.isbn = :isbn", BookHibernate.class);
      query.setParameter("isbn", isbn);
      return query.getSingleResult();
    } finally {
      em.close();
    }
  }

  /** This one manages the transaction. */
  @Override
  public BookHibernate saveNewBook(BookHibernate book) {
    // managing the transaction because we're return the parameter back, and we must
    // make sure the ID was generated and assigned before we return book
    // the new book be attached, detached, etc.
    EntityManager em = getEntityManager();
    try {
      em.getTransaction().begin();
      em.persist(book);
      em.flush();
      em.getTransaction().commit();
      return book;
    } finally {
      em.close();
    }
  }

  @Override
  public BookHibernate updateBook(BookHibernate book) {
    EntityManager em = getEntityManager();
    try {
      em.getTransaction().begin();
      BookHibernate updated = em.merge(book);
      em.flush();
      em.getTransaction().commit();
      return updated;
    } finally {
      em.close();
    }
  }

  @Override
  public List<BookHibernate> findAll() {
    EntityManager em = getEntityManager();
    try {
      return em.createNamedQuery("book_find_all", BookHibernate.class).getResultList();
    } finally {
      em.close();
    }
  }

  @Override
  public void deleteBookById(Long id) {
    EntityManager em = getEntityManager();
    // remember - Hibernate Tx is in a different context from that of Spring
    try {
      em.getTransaction().begin();
      em.remove(em.find(BookHibernate.class, id));
      em.getTransaction().commit();
    } finally {
      em.close();
    }
  }

  @Override
  public BookHibernate updatePrice(Long id, String price) {
    // Try detach I saw in Baeldung
    EntityManager em = getEntityManager();
    try {
      BookHibernate book = em.find(BookHibernate.class, id);
      em.detach(book); // look what I can do!!!
      book.setPrice(price);
      em.getTransaction().begin();
      BookHibernate updated = em.merge(book);
      em.getTransaction().commit();
      em.detach(updated);
      return updated;
    } catch (Exception e) {
      // Yikes!!!
      e.printStackTrace();
    } finally {
      em.close();
    }
    return null;
  }

  private EntityManager getEntityManager() {
    return emf.createEntityManager();
  }
}
