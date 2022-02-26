package guru.springframework.sdjpaintro.jdbctemplate.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import guru.springframework.sdjpaintro.jdbctemplate.domain.BookJdbcTemplate;

public class BookJdbcTemplateDaoImpl implements BookJdbcTemplateDao {

  private final JdbcTemplate jdbcTemplate;

  public BookJdbcTemplateDaoImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public BookJdbcTemplate getById(Long id) {
    return jdbcTemplate.queryForObject(
        "SELECT * FROM book_jdbctemplate WHERE id = ?", getRowMapper(), id);
  }

  @Override
  public BookJdbcTemplate findBookByTitle(String title) {
    return jdbcTemplate.queryForObject(
        "SELECT * FROM book_jdbctemplate WHERE title = ?", getRowMapper(), title);
  }

  @Override
  public List<BookJdbcTemplate> findAllBooks(Integer size, Integer offset) {
    return jdbcTemplate.query(
        "SELECT * FROM book_jdbctemplate limit ? offset ?", getRowMapper(), size, offset);
  }

  @Override
  public List<BookJdbcTemplate> findAllBooks() {
    return jdbcTemplate.query("SELECT * FROM book_jdbctemplate", getRowMapper());
  }

  @Override
  public BookJdbcTemplate saveNewBook(BookJdbcTemplate book) {
    jdbcTemplate.update(
        "INSERT INTO book_jdbctemplate (isbn, publisher, title, author_id) VALUES (?, ?, ?, ?)",
        book.getIsbn(),
        book.getPublisher(),
        book.getTitle(),
        book.getAuthorId());

    // MySql function
    Long createdId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

    return this.getById(createdId);
  }

  @Override
  public BookJdbcTemplate updateBook(BookJdbcTemplate book) {
    jdbcTemplate.update(
        "UPDATE book_jdbctemplate set isbn = ?, publisher = ?, title = ?, author_id = ? where id = ?",
        book.getIsbn(),
        book.getPublisher(),
        book.getTitle(),
        book.getAuthorId(),
        book.getId());

    return this.getById(book.getId());
  }

  @Override
  public void deleteBookById(Long id) {
    jdbcTemplate.update("DELETE FROM book_jdbctemplate WHERE id = ?", id);
  }

  private RowMapper<BookJdbcTemplate> getRowMapper() {
    return new BookRowMapper();
  }
}
