package guru.springframework.sdjpaintro.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.sdjpaintro.jpa.domain.AuthorJpa;

// NOT using @Repository
public interface AuthorJpaRepository extends JpaRepository<AuthorJpa, Long> {
}
