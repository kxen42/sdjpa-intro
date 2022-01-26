package guru.springframework.sdjpaintro.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.sdjpaintro.jpa.domain.composite.AuthorComposite;
import guru.springframework.sdjpaintro.jpa.domain.composite.NameId;

/**
 * Demo composite key
 */
public interface AuthorCompositeRepository extends JpaRepository<AuthorComposite, NameId> {
}
