package guru.springframework.sdjpaintro.jdbc.dao;

import guru.springframework.sdjpaintro.jdbc.domain.AuthorJdbc;

/**
 * From
 * https://github.com/springframeworkguru/sdjpa-jdbc/blob/author-prepared-statement/src/main/java/guru/springframework/jdbc/dao/AuthorDao.java
 */
public interface AuthorJdbcDao {

  AuthorJdbc getById(Long id);

  AuthorJdbc findAuthorJdbcByName(String firstName, String lastName);

  AuthorJdbc saveNewAuthorJdbc(AuthorJdbc author);

  AuthorJdbc updateAuthorJdbc(AuthorJdbc author);

  void deleteAuthorJdbcById(Long id);
}
