package guru.springframework.sdjpaintro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.sdjpaintro.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
