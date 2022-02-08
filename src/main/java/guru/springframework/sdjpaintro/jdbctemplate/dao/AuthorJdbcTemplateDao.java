package guru.springframework.sdjpaintro.jdbctemplate.dao;

import guru.springframework.sdjpaintro.jdbctemplate.domain.AuthorJdbcTemplate;

public interface AuthorJdbcTemplateDao {
  AuthorJdbcTemplate getById(Long id);

  AuthorJdbcTemplate findAuthorByName(String firstName, String lastName);

  AuthorJdbcTemplate findAuthorBookById(Long id);

  AuthorJdbcTemplate saveNewAuthor(AuthorJdbcTemplate author);

  AuthorJdbcTemplate updateAuthor(AuthorJdbcTemplate author);

  void deleteAuthorById(Long id);
}
