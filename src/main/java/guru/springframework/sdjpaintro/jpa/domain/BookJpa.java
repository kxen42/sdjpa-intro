package guru.springframework.sdjpaintro.jpa.domain;

import java.util.Objects;
import java.util.StringJoiner;

import javax.persistence.*;

import org.hibernate.annotations.NaturalId;

/** For the Spring Data JPA only examples. */
// This named query uses the Annotation-based configuration
// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.named-queries.annotation-based-configuration
// Queries annotated to the query method take precedence over queries defined using @NamedQuery or
// named queries declared in orm.xml.
@NamedQuery(name = "BookJpa.jpaNamed", query = "FROM BookJpa b where b.title = :title")
@Entity
public class BookJpa {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;
  @NaturalId private String isbn;
  private String publisher;

  // required by Hibernate
  public BookJpa() {}

  public BookJpa(String title, String isbn, String publisher) {
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

  /*
     // Caution - this would be a disaster for JPA or Hibernate. See Hibernate docs or EverNote
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
  */

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BookJpa bookJpa = (BookJpa) o;
    return isbn.equals(bookJpa.isbn);
  }

  @Override
  public int hashCode() {
    return Objects.hash(isbn);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", BookJpa.class.getSimpleName() + "[", "]")
        .add("id=" + id)
        .add("title='" + title + "'")
        .add("isbn='" + isbn + "'")
        .add("publisher='" + publisher + "'")
        .toString();
  }
}
