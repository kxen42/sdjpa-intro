package guru.springframework.sdjpaintro.jpa.repository;

import java.util.List;
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

  // Using Named Parameters
  // https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.named-parameters
  @Query("FROM BookJpa b WHERE b.title=:title")
  BookJpa findBookByTitleWithQueryNamedParam(@Param("title") String title);

  @Query(value = "SELECT * FROM book_jpa WHERE title = :title", nativeQuery = true)
  BookJpa findBookByTitleNativeQuery(@Param("title") String title);

  /*
   Referencing a Named Query in a Spring Data JPA repository
   https://thorben-janssen.com/spring-data-jpa-named-queries/#Referencing_a_Named_Query_in_a_Spring_Data_JPA_repository
   Instead using the @NamedQuery in a DAO where you have to work with the EntityManager, you can
   use them in the JpaRepository.

  Spring Data JPA takes care of that if you reference a named query in your repository definition. Doing that is
  extremely simple if you follow Spring Data’s naming convention. The name of your query has to start with the name of
  your entity class, followed by “.” and the name of your repository method.
   */
  BookJpa jpaNamed(@Param("title") String title);

  List<BookJpa> findAll();
}
