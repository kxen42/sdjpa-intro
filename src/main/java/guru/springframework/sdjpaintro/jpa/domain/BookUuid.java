/**
 * BookUuid
 * Copyright (c) 2022, FastBridge Learning LLC
 * Created on January 18, 2022
 */
package guru.springframework.sdjpaintro.jpa.domain;

import java.util.Objects;
import java.util.StringJoiner;
import java.util.UUID;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

/**
 * Demo UUID REF 4122 PK
 */
@Entity
public class BookUuid {

    // this will be null before the entity is saved
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "varbinary(16)", updatable = false, nullable = false)
    private UUID id;

    private String title;
    private String isbn;
    private String publisher;

    public BookUuid() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
        BookUuid that = (BookUuid) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", BookUuid.class.getSimpleName() + "[", "]")
            .add("id=" + id)
            .add("title='" + title + "'")
            .add("isbn='" + isbn + "'")
            .add("publisher='" + publisher + "'")
            .toString();
    }
}

