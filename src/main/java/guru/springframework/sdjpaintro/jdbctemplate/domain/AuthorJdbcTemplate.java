package guru.springframework.sdjpaintro.jdbctemplate.domain;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

import javax.persistence.*;

@Entity
@Table(name = "author_jdbctemplate")
public class AuthorJdbcTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;

    @Transient
    List<BookJdbcTemplate> books;

    public AuthorJdbcTemplate() {
    }

    public AuthorJdbcTemplate(String firstName, String lastName, List<BookJdbcTemplate> books) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.books = books;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<BookJdbcTemplate> getBooks() {
        return books;
    }

    public void setBooks(List<BookJdbcTemplate> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorJdbcTemplate that = (AuthorJdbcTemplate) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AuthorJdbcTemplate.class.getSimpleName() + "[", "]")
            .add("id=" + id)
            .add("firstName='" + firstName + "'")
            .add("lastName='" + lastName + "'")
            .add("books=" + books)
            .toString();
    }
}
