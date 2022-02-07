package guru.springframework.sdjpaintro.jdbctemplate.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import guru.springframework.sdjpaintro.jdbctemplate.domain.AuthorJdbcTemplate;

@Component
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
