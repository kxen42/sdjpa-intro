package guru.springframework.sdjpaintro.introduction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.sdjpaintro.introduction.domain.composite.AuthorComposite;
import guru.springframework.sdjpaintro.introduction.domain.composite.NameId;

/**
 * Demo composite key
 */
public interface AuthorCompositeRepository extends JpaRepository<AuthorComposite, NameId> {
}
