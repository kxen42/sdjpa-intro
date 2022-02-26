package guru.springframework.sdjpaintro.jpa.dao;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import guru.springframework.sdjpaintro.jpa.domain.BookJpa;
import guru.springframework.sdjpaintro.jpa.repository.BookJpaRepository;

@Component
public class BookJpaDaoImpl implements BookJpaDao {

  BookJpaRepository repository;

  public BookJpaDaoImpl(BookJpaRepository repository) {
    this.repository = repository;
  }

  @Override
  public BookJpa getById(Long id) {
    return repository.getById(id);
  }

  @Override
  public BookJpa getBookJpaByName(String name) {
    return repository.findBookJpaByTitle(name).orElseThrow(EntityNotFoundException::new);
  }

  @Override
  public BookJpa saveNewBookJpa(BookJpa book) {
    return repository.save(book);
  }

  @Transactional
  @Override
  public BookJpa updateBookJpa(BookJpa book) {
    BookJpa fetched = repository.getById(book.getId());
    fetched.setPublisher(book.getPublisher());
    fetched.setTitle(book.getTitle());
    return repository.save(fetched);
  }

  @Override
  public void deleteBookJpaById(Long id) {
    repository.deleteById(id);
  }

  @Override
  public BookJpa findBookByTitle(String title) {
    return repository.findBookJpaByTitle(title).orElseThrow(EntityNotFoundException::new);
  }

  @Override
  public List<BookJpa> findAllBooks(Integer size, Integer offset) {
    return null;
  }

  @Override
  public List<BookJpa> findAllBooks() {
    return repository.findAll();
  }

  @Override
  public List<BookJpa> findAllBooks(Pageable pageable) {
    return null;
  }
}
