package guru.springframework.sdjpaintro.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.sdjpaintro.jpa.domain.BookJpa;

public interface BookJpaRepository extends JpaRepository<BookJpa, Long> {

  Optional<BookJpa> findBookJpaByTitle(String title);
}
