package guru.springframework.sdjpaintro.jdbc.domain;

import java.util.Objects;
import java.util.StringJoiner;

public class BookJdbc {

  private Long id;
  private String title;
  private String isbn;
  private String publisher;

  private AuthorJdbc author;

  public BookJdbc() {}

  public BookJdbc(String isbn, String title, String publisher) {
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

  public AuthorJdbc getAuthor() {
    return author;
  }

  public void setAuthor(AuthorJdbc author) {
    this.author = author;
  }

  // Caution - this would be a disaster for JPA or Hibernate. See Hibernate docs or EverNote
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BookJdbc bookJdbc = (BookJdbc) o;
    return Objects.equals(id, bookJdbc.id);
  }

  // Caution - this would be a disaster for JPA or Hibernate. See Hibernate docs or EverNote
  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", BookJdbc.class.getSimpleName() + "[", "]")
        .add("id=" + id)
        .add("title='" + title + "'")
        .add("isbn='" + isbn + "'")
        .add("publisher='" + publisher + "'")
        .add("author=" + author)
        .toString();
  }
}
