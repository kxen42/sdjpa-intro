package guru.springframework.sdjpaintro.jdbctemplate.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import guru.springframework.sdjpaintro.jdbctemplate.domain.AuthorJdbcTemplate;

public class AuthorExtractor implements ResultSetExtractor<AuthorJdbcTemplate> {
  // this is a very simple example - look for a better example for the real world
  @Override
  public AuthorJdbcTemplate extractData(ResultSet rs) throws SQLException, DataAccessException {
    return new AuthorBookRowMapper().mapRow(rs, 0);
  }
}
