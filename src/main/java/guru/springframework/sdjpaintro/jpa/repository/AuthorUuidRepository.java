package guru.springframework.sdjpaintro.jpa.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.sdjpaintro.jpa.domain.AuthorUuid;

/**
 * Demonstrate using a varchar for a UUID PK
 */
public interface AuthorUuidRepository extends JpaRepository<AuthorUuid, UUID> {
}
