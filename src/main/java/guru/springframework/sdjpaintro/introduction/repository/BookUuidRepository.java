package guru.springframework.sdjpaintro.introduction.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.sdjpaintro.introduction.domain.BookUuid;

/**
 * Created by jt on 8/15/21.
 */
public interface BookUuidRepository extends JpaRepository<BookUuid, UUID> {
}
