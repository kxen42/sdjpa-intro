package guru.springframework.sdjpaintro.domain;

import java.util.Objects;
import java.util.StringJoiner;

import javax.persistence.*;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    // GenerationType.AUTO not recommended for production, UUIDs is preferable in our experience
    // this will be null before the entity is saved
    private Long id;

    private Long authorId;

    private String title;
    private String isbn;
    private String publisher;

    // required by Hibernate
    public Book() {
    }

    public Book(Long authorId, String title, String isbn, String publisher) {
        this.authorId = authorId;
        this.title = title;
        this.isbn = isbn;
        this.publisher = publisher;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Book.class.getSimpleName() + "[", "]")
            .add("id=" + id)
            .add("title='" + title + "'")
            .add("isbn='" + isbn + "'")
            .add("publisher='" + publisher + "'")
            .toString();
    }
}
