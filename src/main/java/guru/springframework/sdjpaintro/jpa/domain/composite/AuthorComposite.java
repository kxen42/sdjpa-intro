package guru.springframework.sdjpaintro.jpa.domain.composite;

import java.util.StringJoiner;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

/**
 * Demo composite key
 */
@Entity
@IdClass(NameId.class)
public class AuthorComposite {

    @Id
    private String firstName;
    @Id
    private String lastName;
    private String twitter;

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

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    // NO eqauls or hashCode here


    @Override
    public String toString() {
        return new StringJoiner(", ", AuthorComposite.class.getSimpleName() + "[", "]")
            .add("firstName='" + firstName + "'")
            .add("lastName='" + lastName + "'")
            .add("twitter='" + twitter + "'")
            .toString();
    }
}
