package guru.springframework.sdjpaintro.jdbctemplate.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

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

    // Indeed, in hi branch this part is absent
    // https://github.com/springframeworkguru/sdjpa-spring-data-jpa/blob/find-all-author-jdbc/src/main/java/guru/springframework/jdbc/dao/AuthorMapper.java
    // This causes AuthorJdbcTemplate.findAllAuthorsByLastName for fail with a
    // java.sql.SQLException: Column Index out of range, 4 > 3
    // Don't think this part was ever properly implemented. It never made sense to me
    // bad code    if (rs.getString("isbn") != null) {
    // bad code    author.setBooks(new ArrayList<>());
    // bad code   author.getBooks().add(mapBook(rs));
    // bad code   }
    // bad code   while (rs.next()) {
    // bad code   author.getBooks().add(mapBook(rs));
    // bad code   }

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
