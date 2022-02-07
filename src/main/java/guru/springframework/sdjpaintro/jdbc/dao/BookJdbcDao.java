package guru.springframework.sdjpaintro.jdbc.dao;

import guru.springframework.sdjpaintro.jdbc.domain.BookJdbc;

public interface BookJdbcDao {

  BookJdbc getById(Long id);

  BookJdbc findBookJdbcByTitle(String title);

  BookJdbc saveNewBookJdbc(BookJdbc book);

  BookJdbc updateBookJdbc(BookJdbc book);

  void deleteBookJdbcById(Long id);
}
