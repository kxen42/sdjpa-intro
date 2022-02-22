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
    BookHibernate book = em.find(BookHibernate.class, id);
    em.close();
    return book;
  }

  @Override
  public BookHibernate findBookByTitle(String title) {
    EntityManager em = getEntityManager();
    TypedQuery<BookHibernate> query =
        em.createQuery("SELECT b FROM BookHibernate b where b.title = :title", BookHibernate.class);
    query.setParameter("title", title);
    BookHibernate book = query.getSingleResult();
    em.close();
    return book;
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
    em.getTransaction().begin();
    em.persist(book);
    em.flush();
    em.getTransaction().commit();
    em.close();
    return book;
  }

  @Override
  public BookHibernate updateBook(BookHibernate book) {
    EntityManager em = getEntityManager();
    em.getTransaction().begin();
    System.out.println("BookHibernateDaoImpl.updateBook book: " + book);
    BookHibernate updated = em.merge(book);

    em.flush();
    em.getTransaction().commit();
    em.close();
    System.out.println("BookHibernateDaoImpl.updateBook updated: " + updated);
    return updated;
  }

  @Override
  public List<BookHibernate> findAll() {
    EntityManager em = getEntityManager();
    List<BookHibernate> result =
        em.createQuery("SELECT h FROM BookHibernate h", BookHibernate.class).getResultList();

    em.close();
    return result;
  }

  @Override
  public void deleteBookById(Long id) {
    EntityManager em = getEntityManager();
    // remember - Hibernate Tx is in a different context from that of Spring
    em.getTransaction().begin();
    em.remove(em.find(BookHibernate.class, id));
    em.getTransaction().commit();
    em.close();
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
