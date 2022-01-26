package guru.springframework.sdjpaintro.jpa.domain;

import java.util.Objects;
import java.util.StringJoiner;

import javax.persistence.*;

/**
 * For the Spring Data JPA only examples.
 */
@Entity
public class AuthorJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    public AuthorJpa() {
    }

    public AuthorJpa(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorJpa authorJpa = (AuthorJpa) o;
        return Objects.equals(id, authorJpa.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AuthorJpa.class.getSimpleName() + "[", "]")
            .add("id=" + id)
            .add("firstName='" + firstName + "'")
            .add("lastName='" + lastName + "'")
            .toString();
    }
}
