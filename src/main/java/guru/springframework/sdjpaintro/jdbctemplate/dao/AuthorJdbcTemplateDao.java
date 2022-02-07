package guru.springframework.sdjpaintro.jdbctemplate.dao;

import guru.springframework.sdjpaintro.jdbctemplate.domain.AuthorJdbcTemplate;

public interface AuthorJdbcTemplateDao {
  AuthorJdbcTemplate getById(Long id);

  AuthorJdbcTemplate findAuthorByName(String firstName, String lastName);

  AuthorJdbcTemplate saveNewAuthor(AuthorJdbcTemplate author);

  AuthorJdbcTemplate updateAuthor(AuthorJdbcTemplate author);

  void deleteAuthorJdbcTemplateById(Long id);
}
