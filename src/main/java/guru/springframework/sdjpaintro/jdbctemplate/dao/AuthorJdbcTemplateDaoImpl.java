package guru.springframework.sdjpaintro.jdbctemplate.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import guru.springframework.sdjpaintro.jdbctemplate.domain.AuthorJdbcTemplate;

public class AuthorJdbcTemplateDaoImpl implements AuthorJdbcTemplateDao {

  private final JdbcTemplate jdbcTemplate;

  // the magic of Spring Boot configures this the JdbcTemplate for you
  public AuthorJdbcTemplateDaoImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public AuthorJdbcTemplate getById(Long id) {
    return jdbcTemplate.queryForObject(
        "SELECT * FROM author_jdbctemplate WHERE id = ?", getRowMapper(), id);
  }

  @Override
  public AuthorJdbcTemplate findAuthorByName(String firstName, String lastName) {
    return jdbcTemplate.queryForObject(
        "SELECT * FROM author_jdbctemplate WHERE first_name = ? and last_name = ?",
        getRowMapper(),
        firstName,
        lastName);
  }

  @Override
  public AuthorJdbcTemplate findAuthorBookById(Long id) {
    String sql =
        "SELECT author.id AS id, first_name, last_name, book.id AS book_id, book.isbn, book.publisher, book.title FROM author_jdbctemplate author\n"
            + "LEFT OUTER JOIN book_jdbctemplate book ON author.id = book.author_id WHERE author.id = ?";

    return jdbcTemplate.query(sql, new AuthorExtractor(), id);
  }

  @SuppressWarnings("squid:S1149")
  @Override
  public List<AuthorJdbcTemplate> findAllAuthorsByLastName(String lastName, Pageable pageable) {
    // must have thread safe string 'builder'
    StringBuffer sb = new StringBuffer();

    sb.append("SELECT * FROM author_jdbctemplate WHERE last_name = ? ");

    // The parameter name has to match Sort.by(Order.desc("firstname")). D'uh
    if (pageable.getSort().getOrderFor("firstname") != null) {
      sb.append("order by first_name ")
          .append(pageable.getSort().getOrderFor("firstname").getDirection().name());
    }

    sb.append(" limit ? offset ?");

    return jdbcTemplate.query(
        sb.toString(), getRowMapper(), lastName, pageable.getPageSize(), pageable.getOffset());
  }

  @Override
  public AuthorJdbcTemplate saveNewAuthor(AuthorJdbcTemplate author) {
    jdbcTemplate.update(
        "INSERT INTO author_jdbctemplate (first_name, last_name) VALUES (?,?)",
        author.getFirstName(),
        author.getLastName());

    // MySQL function
    Long createId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
    return this.getById(createId);
  }

  @Override
  public AuthorJdbcTemplate updateAuthor(AuthorJdbcTemplate author) {
    jdbcTemplate.update(
        "UPDATE author_jdbctemplate SET first_name = ?, last_name = ? WHERE id = ?",
        author.getFirstName(),
        author.getLastName(),
        author.getId());
    return this.getById(author.getId());
  }

  @Override
  public void deleteAuthorById(Long id) {
    jdbcTemplate.update("DELETE FROM author_jdbctemplate WHERE  id = ?", id);
  }

  // Program to the interface I suppose
  // This is a very lightweight object, so  you can new one up each time
  private RowMapper<AuthorJdbcTemplate> getRowMapper() {
    return new AuthorJdbcTemplateRowMapper();
  }
}
