package guru.springframework.sdjpaintro.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.sdjpaintro.jpa.domain.AuthorJpa;

// NOT using @Repository
public interface AuthorJpaRepository extends JpaRepository<AuthorJpa, Long> {

  // IntelliJ Intelli Sense helps you with the names
  AuthorJpa findAuthorJpaByFirstNameAndLastName(String firstName, String lastName);

  // demonstrate Optional
  Optional<AuthorJpa> findAuthJpaByLastName(String lastName);
}
