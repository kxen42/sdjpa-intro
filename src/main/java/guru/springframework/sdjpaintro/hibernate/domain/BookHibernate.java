package guru.springframework.sdjpaintro.hibernate.domain;

import java.util.Objects;
import java.util.StringJoiner;

import javax.persistence.*;

import org.hibernate.annotations.NaturalId;

@Entity
public class BookHibernate {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;
  @NaturalId private String isbn;
  private String publisher;
  private Long authorId;
  private String price;

  public BookHibernate() {}

  public BookHibernate(String title, String isbn, String publisher) {
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

  public Long getAuthorId() {
    return authorId;
  }

  public void setAuthorId(Long authorId) {
    this.authorId = authorId;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  /*
  // Caution - this would be a disaster for JPA or Hibernate. See Hibernate docs or EverNote
  @Override
  public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookHibernate that = (BookHibernate) o;
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
    BookHibernate that = (BookHibernate) o;
    return isbn.equals(that.isbn);
  }

  @Override
  public int hashCode() {
    return Objects.hash(isbn);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", BookHibernate.class.getSimpleName() + "[", "]")
        .add("id=" + id)
        .add("title='" + title + "'")
        .add("isbn='" + isbn + "'")
        .add("publisher='" + publisher + "'")
        .add("authorId=" + authorId)
        .add("price='" + price + "'")
        .toString();
  }
}
