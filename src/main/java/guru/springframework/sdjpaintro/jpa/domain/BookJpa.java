package guru.springframework.sdjpaintro.jpa.domain;

import java.util.Objects;
import java.util.StringJoiner;

import javax.persistence.*;

/**
 * For the Spring Data JPA only examples.
 */
@Entity
public class BookJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long authorId;

    private String title;
    private String isbn;
    private String publisher;

    // required by Hibernate
    public BookJpa() {
    }

    public BookJpa(Long authorId, String title, String isbn, String publisher) {
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
        BookJpa bookJpa = (BookJpa) o;
        return Objects.equals(id, bookJpa.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", BookJpa.class.getSimpleName() + "[", "]")
            .add("id=" + id)
            .add("authorId=" + authorId)
            .add("title='" + title + "'")
            .add("isbn='" + isbn + "'")
            .add("publisher='" + publisher + "'")
            .toString();
    }
}
