package guru.springframework.sdjpaintro.jdbctemplate.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
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
    // Using standard SQL LIMIT-clause
    return jdbcTemplate.query(
        "SELECT * FROM book_jdbctemplate limit ? offset ?", getRowMapper(), size, offset);
  }

  @Override
  public List<BookJdbcTemplate> findAllBooks() {
    return jdbcTemplate.query("SELECT * FROM book_jdbctemplate", getRowMapper());
  }

  @Override
  public List<BookJdbcTemplate> findAllBooks(Pageable pageable) {
    // Using standard SQL LIMIT-clause
    return jdbcTemplate.query(
        "SELECT * FROM book_jdbctemplate limit ? offset ?",
        getRowMapper(),
        pageable.getPageSize(),
        pageable.getOffset());
  }

  @Override
  public List<BookJdbcTemplate> findAllBooksSortByTitle(Pageable pageable) {
    // This shows that with JdbcTemplate this can be a brittle mess
    // NPE will be thrown if 'title' doesn't exist
    // for every column in the order by requires more ugly code
    // order of columns is hard code
    String sql =
        "SELECT * FROM book_jdbctemplate ORDER BY title "
            + pageable.getSort().getOrderFor("title").getDirection().name()
            + " LIMIT ? OFFSET ?";

    System.out.println(
        "BookJdbcTemplateDaoImpl.findAllBooksSortByTitle look what I can do sql:" + sql);

    return jdbcTemplate.query(sql, getRowMapper(), pageable.getPageSize(), pageable.getOffset());
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
