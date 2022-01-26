package guru.springframework.sdjpaintro.introduction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.sdjpaintro.introduction.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
