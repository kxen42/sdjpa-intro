package guru.springframework.sdjpaintro.jdbctemplate.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.jdbc.core.RowMapper;

import guru.springframework.sdjpaintro.jdbctemplate.domain.AuthorJdbcTemplate;
import guru.springframework.sdjpaintro.jdbctemplate.domain.BookJdbcTemplate;

/** Get the author with their books. */
public class AuthorBookRowMapper implements RowMapper<AuthorJdbcTemplate> {
  @Override
  public AuthorJdbcTemplate mapRow(ResultSet rs, int rowNum) throws SQLException {
    rs.next();

    AuthorJdbcTemplate author = new AuthorJdbcTemplate();
    author.setId(rs.getLong("id"));
    author.setFirstName(rs.getString("first_name"));
    author.setLastName(rs.getString("last_name"));

    if (rs.getString("isbn") != null) {
      author.setBooks(new ArrayList<>());
      author.getBooks().add(mapBook(rs));
    }

    while (rs.next()) {
      author.getBooks().add(mapBook(rs));
    }

    return author;
  }

  private BookJdbcTemplate mapBook(ResultSet rs) throws SQLException {
    BookJdbcTemplate book = new BookJdbcTemplate();
    book.setId(rs.getLong(4));
    book.setIsbn(rs.getString(5));
    book.setPublisher(rs.getString(6));
    book.setTitle(rs.getString(7));
    book.setAuthorId(rs.getLong(1));
    return book;
  }
}
