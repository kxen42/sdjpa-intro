package guru.springframework.sdjpaintro.jpa.repository;

import java.util.Optional;
import java.util.concurrent.Future;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Async;

import guru.springframework.sdjpaintro.jpa.domain.BookJpa;

public interface BookJpaRepository extends JpaRepository<BookJpa, Long> {

  Optional<BookJpa> findBookJpaByTitle(String title);

  BookJpa readByTitle(String title);

  @Nullable
  BookJpa getByTitle(@Nullable String title);

  Stream<BookJpa> findAllByTitleNotNull();

  @Async
  Future<BookJpa> queryByTitle(String title);

  // ?1 is positional parameter of 1
  // I was right - the Spring can't find the 'table' in the query, the ApplicationContext will not
  // load
  @Query("FROM BookJpa b WHERE b.title=?1")
  BookJpa findBookByTitleWithAnnotation(String title);

  @Query("FROM BookJpa b WHERE b.title=:title")
  BookJpa findBookByTitleWithQueryNamedParam(@Param("title") String title);

  @Query(value = "SELECT * FROM book_jpa WHERE title = :title", nativeQuery = true)
  BookJpa findBookByTitleNativeQuery(@Param("title") String title);
}
