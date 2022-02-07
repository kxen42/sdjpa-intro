package guru.springframework.sdjpaintro.jpa.dao;

import guru.springframework.sdjpaintro.jpa.domain.BookJpa;

public interface BookJpaDao {

  BookJpa getById(Long id);

  BookJpa getBookJpaByName(String name);

  BookJpa saveNewBookJpa(BookJpa book);

  BookJpa updateBookJpa(BookJpa book);

  void deleteBookJpaById(Long id);
}
