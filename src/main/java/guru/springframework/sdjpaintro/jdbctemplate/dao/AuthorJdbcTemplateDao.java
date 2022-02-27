package guru.springframework.sdjpaintro.jdbctemplate.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;

import guru.springframework.sdjpaintro.jdbctemplate.domain.AuthorJdbcTemplate;

public interface AuthorJdbcTemplateDao {
  AuthorJdbcTemplate getById(Long id);

  AuthorJdbcTemplate findAuthorByName(String firstName, String lastName);

  AuthorJdbcTemplate findAuthorBookById(Long id);

  AuthorJdbcTemplate saveNewAuthor(AuthorJdbcTemplate author);

  AuthorJdbcTemplate updateAuthor(AuthorJdbcTemplate author);

  void deleteAuthorById(Long id);

  List<AuthorJdbcTemplate> findAllAuthorsByLastName(String lastName, Pageable pageable);
}
