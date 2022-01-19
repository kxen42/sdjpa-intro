package guru.springframework.sdjpaintro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.sdjpaintro.domain.composite.AuthorComposite;
import guru.springframework.sdjpaintro.domain.composite.NameId;

/**
 * Demo composite key
 */
public interface AuthorCompositeRepository extends JpaRepository<AuthorComposite, NameId> {
}
