package guru.springframework.sdjpaintro.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.sdjpaintro.jpa.domain.composite.AuthorEmbedded;
import guru.springframework.sdjpaintro.jpa.domain.composite.NameId;

/**
 * Demo @EmbeddedId
 */
public interface AuthorEmbeddedRepository extends JpaRepository<AuthorEmbedded, NameId> {
}
