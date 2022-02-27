package guru.springframework.sdjpaintro.jdbctemplate.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;

import guru.springframework.sdjpaintro.jdbctemplate.domain.BookJdbcTemplate;

public interface BookJdbcTemplateDao {
  BookJdbcTemplate getById(Long id);

  BookJdbcTemplate findBookByTitle(String title);

  BookJdbcTemplate saveNewBook(BookJdbcTemplate book);

  BookJdbcTemplate updateBook(BookJdbcTemplate book);

  void deleteBookById(Long id);

  List<BookJdbcTemplate> findAllBooks(Integer size, Integer offset);

  List<BookJdbcTemplate> findAllBooks();

  List<BookJdbcTemplate> findAllBooks(Pageable pageable);

  List<BookJdbcTemplate> findAllBooksSortByTitle(Pageable pageable);
}
