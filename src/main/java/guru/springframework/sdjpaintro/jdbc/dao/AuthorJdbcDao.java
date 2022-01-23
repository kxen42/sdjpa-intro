package guru.springframework.sdjpaintro.jdbc.dao;

import guru.springframework.sdjpaintro.jdbc.domain.AuthorJdbc;

/**
 * From https://github.com/springframeworkguru/sdjpa-jdbc/blob/author-prepared-statement/src/main/java/guru/springframework/jdbc/dao/AuthorDao.java
 */
public interface AuthorJdbcDao {

        AuthorJdbc getById(Long id);
        AuthorJdbc getByName(String firstName, String lastName);
}
