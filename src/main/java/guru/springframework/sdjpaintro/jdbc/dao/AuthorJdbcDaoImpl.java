package guru.springframework.sdjpaintro.jdbc.dao;

import java.sql.*;

import javax.sql.DataSource;

import org.springframework.stereotype.Component;

import guru.springframework.sdjpaintro.jdbc.domain.AuthorJdbc;

/**
 * From https://github.com/springframeworkguru/sdjpa-jdbc/blob/author-prepared-statement/src/main/java/guru/springframework/jdbc/dao/AuthorDao.java
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
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            connection = source.getConnection();
            ps = connection.prepareStatement("SELECT * FROM author_jdbc where id = ?");
            ps.setLong(1, id);
            resultSet = ps.executeQuery();

            if (resultSet.next()) {
                AuthorJdbc author = new AuthorJdbc();
                author.setId(id);
                author.setFirstName(resultSet.getString("first_name"));
                author.setLastName(resultSet.getString("last_name"));

                return author;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }

                if (ps != null) {
                    ps.close();
                }

                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public AuthorJdbc getByName(String firstName, String lastName) {

        try (Connection connection = source.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM author_jdbc where first_name = ? and last_name = ?");
        ) {

            ps.setString(1, firstName);
            ps.setString(2, lastName);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    AuthorJdbc author = new AuthorJdbc();
                    author.setId(resultSet.getLong("id"));
                    author.setFirstName(resultSet.getString("first_name"));
                    author.setLastName(resultSet.getString("last_name"));

                    return author;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
