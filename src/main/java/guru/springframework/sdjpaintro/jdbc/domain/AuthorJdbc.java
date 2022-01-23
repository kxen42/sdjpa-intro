package guru.springframework.sdjpaintro.jdbc.domain;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * From https://github.com/springframeworkguru/sdjpa-jdbc/blob/author-prepared-statement/src/main/java/guru/springframework/jdbc/dao/AuthorDao.java
 */
public class AuthorJdbc {
    private Long id;
    private String firstName;
    private String lastName;

    public AuthorJdbc() {
    }

    public AuthorJdbc(Long id, String firstName, String lastName) {
        this.id = id;
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
        AuthorJdbc that = (AuthorJdbc) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AuthorJdbc.class.getSimpleName() + "[", "]")
            .add("id=" + id)
            .add("firstName='" + firstName + "'")
            .add("lastName='" + lastName + "'")
            .toString();
    }
}
