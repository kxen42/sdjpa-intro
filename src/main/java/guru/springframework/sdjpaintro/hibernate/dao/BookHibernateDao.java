package guru.springframework.sdjpaintro.hibernate.dao;

import java.util.List;

import guru.springframework.sdjpaintro.hibernate.domain.BookHibernate;

public interface BookHibernateDao {

  BookHibernate getById(Long id);

  BookHibernate findBookByTitle(String title);

  BookHibernate findByIsbn(String isbn);

  BookHibernate saveNewBook(BookHibernate book);

  BookHibernate updateBook(BookHibernate book);

  void deleteBookById(Long id);

  List<BookHibernate> findAll();

  BookHibernate updatePrice(Long id, String price);

  BookHibernate findBookByTitleCriteria(String title);

  BookHibernate findBookByTitleNative(String title);
}