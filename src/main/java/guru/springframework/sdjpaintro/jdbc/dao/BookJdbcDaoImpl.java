package guru.springframework.sdjpaintro.jdbc.dao;

import java.sql.*;

import javax.sql.DataSource;

import org.springframework.stereotype.Component;

import guru.springframework.sdjpaintro.jdbc.domain.BookJdbc;

@Component
public class BookJdbcDaoImpl implements BookJdbcDao {

  private final DataSource source;
  private final AuthorJdbcDao authorDao;

  public BookJdbcDaoImpl(DataSource source, AuthorJdbcDao authorDao) {
    this.source = source;
    this.authorDao = authorDao;
  }

  @Override
  public BookJdbc getById(Long id) {
    BookJdbc found = null;

    try (Connection connection = source.getConnection();
        PreparedStatement ps =
            connection.prepareStatement("SELECT * FROM book_jdbc where id = ?"); ) {

      ps.setLong(1, id);

      // using Kousen's example for ResultSet handling
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        found = getBookJdbcFromRS(rs); // work SOP
      }
      rs.close();

    } catch (SQLException e) {
      e.printStackTrace();
      e.printStackTrace();
    }

    return found;
  }

  @Override
  public BookJdbc findBookJdbcByTitle(String title) {
    BookJdbc found = null;

    try (Connection connection = source.getConnection();
        PreparedStatement ps =
            connection.prepareStatement("SELECT * FROM book_jdbc where title = ?"); ) {

      ps.setString(1, title);
      ResultSet rs = ps.executeQuery();

      if (rs.next()) {
        found = getBookJdbcFromRS(rs); // work SOP
      }
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return found;
  }

  @Override
  public BookJdbc saveNewBookJdbc(BookJdbc book) {
    BookJdbc saved = null;
    try (Connection connection = source.getConnection();
        PreparedStatement ps =
            connection.prepareStatement(
                "INSERT INTO  book_jdbc (isbn, title, publisher, author_jdbc_id) VALUES (?, ?, ?, ?)"); ) {

      // relies on table DDL to auto_increment id
      ps.setString(1, book.getIsbn());
      ps.setString(2, book.getTitle());
      ps.setString(3, book.getPublisher());
      if (book.getAuthor() != null) {
        ps.setLong(4, book.getAuthor().getId());
      } else {
        ps.setNull(4, Types.BIGINT);
      }
      ps.execute();

      // don't trust the ResultSet from running the insert
      // Using MySql function LAST_INSERT_ID()
      Statement statement = connection.createStatement();
      ResultSet rs = statement.executeQuery("SELECT LAST_INSERT_ID()");
      if (rs.next()) {
        Long savedId = rs.getLong(1);
        saved = this.getById(savedId); // work SOP
      }
      statement.close(); // Kousen & Thompson leaves it like this, it is not in its own try-block
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return saved;
  }

  @Override
  public BookJdbc updateBookJdbc(BookJdbc book) {

    try (Connection connection = source.getConnection();
        PreparedStatement ps =
            connection.prepareStatement(
                "UPDATE book_jdbc set isbn = ?, title = ? , publisher = ?, author_jdbc_id = ?  where book_jdbc.id = ?"); ) {

      ps.setString(1, book.getIsbn());
      ps.setString(2, book.getTitle());
      ps.setString(3, book.getPublisher());
      if (book.getAuthor() != null) {
        ps.setLong(4, book.getAuthor().getId());
      } else {
        ps.setNull(4, Types.BIGINT);
      }
      ps.setLong(5, book.getId());
      ps.execute();

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return this.getById(book.getId());
  }

  @Override
  public void deleteBookJdbcById(Long id) {
    // assuming book table has cascade delete
    try (Connection connection = source.getConnection();
        PreparedStatement ps =
            connection.prepareStatement("DELETE FROM book_jdbc WHERE book_jdbc.id = ?"); ) {

      ps.setLong(1, id);
      ps.execute();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  // use this for code commonality & DRY
  private BookJdbc getBookJdbcFromRS(ResultSet resultSet) throws SQLException {
    BookJdbc book = new BookJdbc();
    book.setId(resultSet.getLong("id"));
    book.setIsbn(resultSet.getString("isbn"));
    book.setTitle(resultSet.getString("title"));
    book.setPublisher(resultSet.getString("publisher"));
    /* THIS IS NOT THE PROPER WAY TO DO THIS
    queries would need to be changed to do a join so you get the author too.
    This causes a second query to be run everytime you fetch a book. This is bad programming and sucks performance.
     */
    book.setAuthor(authorDao.getById(resultSet.getLong("author_jdbc_id")));
    return book;
  }
}
