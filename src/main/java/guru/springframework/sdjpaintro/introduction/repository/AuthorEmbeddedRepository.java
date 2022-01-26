package guru.springframework.sdjpaintro.introduction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.sdjpaintro.introduction.domain.composite.AuthorEmbedded;
import guru.springframework.sdjpaintro.introduction.domain.composite.NameId;


/**
 * Demo @EmbeddedId
 */
public interface AuthorEmbeddedRepository extends JpaRepository<AuthorEmbedded, NameId> {
}
