package guru.springframework.sdjpaintro.introduction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.sdjpaintro.introduction.domain.BookNatural;

public interface BookNaturalRepository extends JpaRepository<BookNatural, String> {
}
