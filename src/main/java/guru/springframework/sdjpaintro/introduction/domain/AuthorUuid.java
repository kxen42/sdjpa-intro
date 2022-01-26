package guru.springframework.sdjpaintro.introduction.domain;

import java.util.Objects;
import java.util.StringJoiner;
import java.util.UUID;

import javax.persistence.*;

import org.hibernate.annotations.Type;

/**
 * Demonstrate using a varchar for a UUID PK
 */
@Entity
public class AuthorUuid {

    // this will be null before the entity is saved
    // Using AUTO for convenience
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(length=36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;

    private String firstName;

    private String lastName;

    public AuthorUuid() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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
        AuthorUuid that = (AuthorUuid) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AuthorUuid.class.getSimpleName() + "[", "]")
            .add("id='" + id + "'")
            .add("firstName='" + firstName + "'")
            .add("lastName='" + lastName + "'")
            .toString();
    }
}
