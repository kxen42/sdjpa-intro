package guru.springframework.sdjpaintro.jpa.domain;

import java.util.Objects;
import java.util.StringJoiner;

import javax.persistence.*;

import org.hibernate.annotations.NaturalId;

/** For the Spring Data JPA only examples. */
@Entity
public class AuthorJpa {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // an attempt to update an @NaturalId results in
  //   org.springframework.orm.jpa.JpaSystemException: An immutable natural identifier of
  //   entity guru.springframework.sdjpaintro.jpa.domain.AuthorJpa was altered from `Tentpeg` to
  // `Doe`;
  //   nested exception is org.hibernate.HibernateException: An immutable natural identifier of
  //   entity guru.springframework.sdjpaintro.jpa.domain.AuthorJpa was altered from `Tentpeg` to
  // `Doe`

  @NaturalId private String firstName;
  @NaturalId private String lastName;
  private String profilePicture;

  public AuthorJpa() {}

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

  public String getProfilePicture() {
    return profilePicture;
  }

  public void setProfilePicture(String profilePicture) {
    this.profilePicture = profilePicture;
  }

  /*
  // Caution - this would be a disaster for JPA or Hibernate. See Hibernate docs or EverNote
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
     */

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AuthorJpa authorJpa = (AuthorJpa) o;
    return Objects.equals(firstName, authorJpa.firstName)
        && Objects.equals(lastName, authorJpa.lastName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstName, lastName);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", AuthorJpa.class.getSimpleName() + "[", "]")
        .add("id=" + id)
        .add("firstName='" + firstName + "'")
        .add("lastName='" + lastName + "'")
        .add("profilePicture='" + profilePicture + "'")
        .toString();
  }
}
