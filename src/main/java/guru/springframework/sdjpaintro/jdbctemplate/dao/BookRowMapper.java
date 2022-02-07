package guru.springframework.sdjpaintro.jdbctemplate.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import guru.springframework.sdjpaintro.jdbctemplate.domain.BookJdbcTemplate;

public class BookRowMapper implements RowMapper<BookJdbcTemplate> {
  @Override
  public BookJdbcTemplate mapRow(ResultSet rs, int rowNum) throws SQLException {
    BookJdbcTemplate book = new BookJdbcTemplate();
    book.setAuthorId(rs.getLong("author_id"));
    book.setId(rs.getLong("id"));
    book.setIsbn(rs.getString("isbn"));
    book.setPublisher(rs.getString("publisher"));
    book.setTitle(rs.getString("title"));
    return book;
  }
}
