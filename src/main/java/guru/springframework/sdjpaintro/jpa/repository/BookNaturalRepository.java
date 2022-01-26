package guru.springframework.sdjpaintro.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.sdjpaintro.jpa.domain.BookNatural;

public interface BookNaturalRepository extends JpaRepository<BookNatural, String> {
}
