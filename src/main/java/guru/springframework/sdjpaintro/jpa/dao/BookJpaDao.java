package guru.springframework.sdjpaintro.jpa.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;

import guru.springframework.sdjpaintro.jpa.domain.BookJpa;

public interface BookJpaDao {

  BookJpa getById(Long id);

  BookJpa getBookJpaByName(String name);

  BookJpa saveNewBookJpa(BookJpa book);

  BookJpa updateBookJpa(BookJpa book);

  void deleteBookJpaById(Long id);

  BookJpa findBookByTitle(String title);

  List<BookJpa> findAllBooks(Integer size, Integer offset);

  List<BookJpa> findAllBooks();

  List<BookJpa> findAllBooks(Pageable pageable);
}
