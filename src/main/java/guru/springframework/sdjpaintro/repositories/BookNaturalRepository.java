package guru.springframework.sdjpaintro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.sdjpaintro.domain.BookNatural;

public interface BookNaturalRepository extends JpaRepository<BookNatural, String> {
}
