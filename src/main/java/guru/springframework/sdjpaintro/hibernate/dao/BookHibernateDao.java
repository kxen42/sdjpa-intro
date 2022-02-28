package guru.springframework.sdjpaintro.hibernate.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;

import guru.springframework.sdjpaintro.hibernate.domain.BookHibernate;

public interface BookHibernateDao {

  BookHibernate getById(Long id);

  BookHibernate findBookByTitle(String title);

  BookHibernate findByIsbn(String isbn);

  BookHibernate findBookByTitleCriteria(String title);

  BookHibernate findBookByTitleNative(String title);

  List<BookHibernate> findAllBooksSortByTitle(Pageable pageable);

  List<BookHibernate> findAllBooks(Pageable pageable);

  List<BookHibernate> findAllBooks(int pageSize, int offset);

  List<BookHibernate> findAllBooks();

  List<BookHibernate> findAll();

  BookHibernate saveNewBook(BookHibernate book);

  BookHibernate updateBook(BookHibernate book);

  void deleteBookById(Long id);

  BookHibernate updatePrice(Long id, String price);
}
