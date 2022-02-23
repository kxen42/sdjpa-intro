package guru.springframework.sdjpaintro.hibernate.domain;

import java.util.Objects;
import java.util.StringJoiner;

import javax.persistence.*;

import org.hibernate.annotations.NaturalId;

// R.O.T. use NamedQueries so any DAO could use them.
@NamedQueries({
  @NamedQuery(name = "find_all", query = "FROM AuthorHibernate"),
  @NamedQuery(
      name = "find_by_lastname",
      query = "FROM AuthorHibernate a WHERE a.lastName = :last_name")
})
@Entity
public class AuthorHibernate {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NaturalId private String firstName;
  @NaturalId private String lastName;

  private String someMutableField;

  public AuthorHibernate() {}

  public AuthorHibernate(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.someMutableField = "ouch";
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

  public String getSomeMutableField() {
    return someMutableField;
  }

  public void setSomeMutableField(String someMutableField) {
    this.someMutableField = someMutableField;
  }

  /*
  // Caution - this would be a disaster for JPA or Hibernate. See Hibernate docs or EverNote
  @Override
  public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorHibernate that = (AuthorHibernate) o;
        return Objects.equals(id, that.id);
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
    AuthorHibernate that = (AuthorHibernate) o;
    return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstName, lastName);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", AuthorHibernate.class.getSimpleName() + "[", "]")
        .add("id=" + id)
        .add("firstName='" + firstName + "'")
        .add("lastName='" + lastName + "'")
        .add("someMutableField='" + someMutableField + "'")
        .toString();
  }
}
