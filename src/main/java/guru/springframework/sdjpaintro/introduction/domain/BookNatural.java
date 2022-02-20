package guru.springframework.sdjpaintro.introduction.domain;

import java.util.Objects;
import java.util.StringJoiner;

import javax.persistence.Entity;
import javax.persistence.Id;

/** Demo a natural primary key. And a very bad one at that. */
@Entity
public class BookNatural {

  @Id
  // this will do nothing to prevent updates if you have a setter for title
  // @Column(nullable = false, updatable = false)
  private String title;

  private String isbn;
  private String publisher;

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

  // don't allow null title
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BookNatural that = (BookNatural) o;
    return title.equals(that.title);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", BookNatural.class.getSimpleName() + "[", "]")
        .add("title='" + title + "'")
        .add("isbn='" + isbn + "'")
        .add("publisher='" + publisher + "'")
        .toString();
  }
}
