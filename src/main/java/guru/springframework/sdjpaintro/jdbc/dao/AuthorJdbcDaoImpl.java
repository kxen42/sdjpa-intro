package guru.springframework.sdjpaintro.jdbc.dao;

import java.sql.*;

import javax.sql.DataSource;

import org.springframework.stereotype.Component;

import guru.springframework.sdjpaintro.jdbc.domain.AuthorJdbc;

/**
 * From
 * https://github.com/springframeworkguru/sdjpa-jdbc/blob/author-prepared-statement/src/main/java/guru/springframework/jdbc/dao/AuthorDao.java
 *
 * <p>See Ken Kousen's JdbcPersonDao
 * https://github.com/kousen/Advanced_Java/blob/master/src/main/java/database/jdbc/JdbcPersonDAO.java
 */
@Component
public class AuthorJdbcDaoImpl implements AuthorJdbcDao {

  private final DataSource source;

  // modern Spring autowires this for you
  public AuthorJdbcDaoImpl(DataSource source) {
    this.source = source;
  }

  @Override
  public AuthorJdbc getById(Long id) {

    AuthorJdbc found = null;

    try (Connection connection = source.getConnection();
        PreparedStatement ps =
            connection.prepareStatement("SELECT * FROM author_jdbc where id = ?"); ) {

      ps.setLong(1, id);

      // using Kousen's example for ResultSet handling
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        found = getAuthorJdbcFromRS(rs); // work SOP
      }
      rs.close();

    } catch (SQLException e) {
      e.printStackTrace();
            e.printStackTrace();
    }

    return found;
  }

  /**
   * Using John Thompson's solution
   *
   * @param firstName
   * @param lastName
   * @return found AuthorJdbc POJO
   */
  @Override
  public AuthorJdbc findAuthorJdbcByName(String firstName, String lastName) {

    AuthorJdbc found = null;

    try (Connection connection = source.getConnection();
        PreparedStatement ps =
            connection.prepareStatement(
                "SELECT * FROM author_jdbc where first_name = ? and last_name = ?"); ) {

      ps.setString(1, firstName);
      ps.setString(2, lastName);
      ResultSet rs = ps.executeQuery();

      if (rs.next()) {
        found = getAuthorJdbcFromRS(rs); // work SOP
      }
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return found;
  }

  /**
   * Using John Thompson's solution with my changes to use try-with-resources. The DB autogenerates
   * the identity.
   */
  @Override
  public AuthorJdbc saveNewAuthorJdbc(AuthorJdbc author) {

    AuthorJdbc saved = null;
    try (Connection connection = source.getConnection();
        PreparedStatement ps =
            connection.prepareStatement(
                "INSERT INTO  author_jdbc (first_name, last_name) VALUES (?, ?)"); ) {

      // relies on table DDL to auto_increment id
      ps.setString(1, author.getFirstName());
      ps.setString(2, author.getLastName());
      ps.execute();

      // don't trust the ResultSet from running the insert
      // Using MySql function LAST_INSERT_ID()
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery("SELECT LAST_INSERT_ID()");
      if (rs.next()) {
        Long savedId = rs.getLong(1);
        saved = this.getById(savedId); // work SOP
      }
      statement.close(); // Kousen and Thompson leaves it like this, it is not in its own try-block
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return saved;
  }

  /**
   * Ken Kousen's solution *
   * https://github.com/kousen/Advanced_Java/blob/master/src/main/java/database/jdbc/JdbcPersonDAO.java
   *
   * @param author
   * @return last generated PK
   */
  @Override
  public Integer saveNewAuthorJdbcKousen(AuthorJdbc author) {
    int generatedKey = 0;

    try (Connection connection = source.getConnection();
        PreparedStatement ps =
            connection.prepareStatement(
                "INSERT INTO  author_jdbc (first_name, last_name) VALUES (?, ?)",
                Statement.RETURN_GENERATED_KEYS); ) {

      ps.setString(1, author.getFirstName());
      ps.setString(2, author.getLastName());

      int uc = ps.executeUpdate();
      if (uc != 1) throw new SQLException("No rows saved");

      try (ResultSet keys = ps.getGeneratedKeys()) {
        if (keys.next()) generatedKey = keys.getInt(1);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return generatedKey;
  }

  @Override
  public AuthorJdbc updateAuthorJdbc(AuthorJdbc author) {

    try (Connection connection = source.getConnection();
        PreparedStatement ps =
            connection.prepareStatement(
                "UPDATE author_jdbc set first_name = ?, last_name = ? where author_jdbc.id = ?"); ) {

      ps.setString(1, author.getFirstName());
      ps.setString(2, author.getLastName());
      ps.setLong(3, author.getId());
      ps.execute();

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return this.getById(author.getId());
  }

  @Override
  public void deleteAuthorJdbcById(Long id) {
    try (Connection connection = source.getConnection();
        PreparedStatement ps =
            connection.prepareStatement("DELETE FROM author_jdbc WHERE author_jdbc.id = ?"); ) {

      ps.setLong(1, id);
      ps.execute();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  // use this for code commonality & DRY
  private AuthorJdbc getAuthorJdbcFromRS(ResultSet resultSet) throws SQLException {
    AuthorJdbc author = new AuthorJdbc();
    author.setId(resultSet.getLong("id"));
    author.setFirstName(resultSet.getString("first_name"));
    author.setLastName(resultSet.getString("last_name"));

    return author;
  }
}
