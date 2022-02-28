package guru.springframework.sdjpaintro.hibernate.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Pageable;

import guru.springframework.sdjpaintro.hibernate.domain.BookHibernate;

/*
Purposely using EntityManager and try-finally to close to do this old school
 */
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

  @Override
  public BookHibernate findBookByTitleCriteria(String title) {
    EntityManager em = getEntityManager();

    try {
      CriteriaBuilder cb = em.getCriteriaBuilder();
      CriteriaQuery<BookHibernate> cr = cb.createQuery(BookHibernate.class);
      Root<BookHibernate> root = cr.from(BookHibernate.class);

      cr.select(root).where(cb.equal(root.get("title"), title));
      return em.createQuery(cr).getSingleResult();
    } finally {
      em.close();
    }
  }

  @Override
  public BookHibernate findBookByTitleNative(String title) {
    EntityManager em = getEntityManager();

    try {

      return (BookHibernate)
          em.createNativeQuery(
                  "SELECT * FROM book_hibernate WHERE title=:title", BookHibernate.class)
              .setParameter("title", title)
              .getSingleResult();
    } finally {
      em.close();
    }
  }

  @Override
  public List<BookHibernate> findAllBooksSortByTitle(Pageable pageable) {
    EntityManager em = getEntityManager();
    try {
      String sql =
          "SELECT b FROM BookHibernate b order by title "
              + pageable.getSort().getOrderFor("title").getDirection().name();

      TypedQuery<BookHibernate> query =
          em.createQuery(sql, BookHibernate.class)
              .setFirstResult(pageable.getPageNumber())
              .setMaxResults(pageable.getPageSize());

      return query.getResultList();
    } finally {
      em.close();
    }
  }

  /*
  Math.toIntExact he uses this as the safest way to shorten a long to an int.
  It throws an ArithmeticException if the value overflows an int.
  If you just assign a long to an int, your results will be unpredictable if it overflows the int.

  Without implementing my own Pageable impl I can't test this use. It could be done in Groovy.
  */
  @Override
  public List<BookHibernate> findAllBooks(Pageable pageable) {
    return this.findAllBooks(pageable.getPageSize(), Math.toIntExact(pageable.getOffset()));
  }

  @Override
  public List<BookHibernate> findAllBooks(int pageSize, int offset) {
    EntityManager em = getEntityManager();
    try {
      TypedQuery<BookHibernate> query =
          em.createQuery("SELECT b FROM BookHibernate b", BookHibernate.class)
              .setFirstResult(offset)
              .setMaxResults(pageSize);

      return query.getResultList();
    } finally {
      em.close();
    }
  }

  @Override
  public List<BookHibernate> findAllBooks() {
    // I'm too lazy to change tha name
    return this.findAll();
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
