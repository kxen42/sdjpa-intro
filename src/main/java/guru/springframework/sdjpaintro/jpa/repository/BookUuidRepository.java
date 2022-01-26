package guru.springframework.sdjpaintro.jpa.repository;

import guru.springframework.sdjpaintro.jpa.domain.BookUuid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Created by jt on 8/15/21.
 */
public interface BookUuidRepository extends JpaRepository<BookUuid, UUID> {
}
