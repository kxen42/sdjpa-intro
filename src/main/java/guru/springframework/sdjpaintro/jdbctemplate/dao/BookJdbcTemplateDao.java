package guru.springframework.sdjpaintro.jdbctemplate.dao;

import guru.springframework.sdjpaintro.jdbctemplate.domain.BookJdbcTemplate;

public interface BookJdbcTemplateDao {
  BookJdbcTemplate getById(Long id);

  BookJdbcTemplate findBookByTitle(String title);

  BookJdbcTemplate saveNewBook(BookJdbcTemplate book);

  BookJdbcTemplate updateBook(BookJdbcTemplate book);

  void deleteBookById(Long id);
}
