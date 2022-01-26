package guru.springframework.sdjpaintro.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.sdjpaintro.jpa.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
