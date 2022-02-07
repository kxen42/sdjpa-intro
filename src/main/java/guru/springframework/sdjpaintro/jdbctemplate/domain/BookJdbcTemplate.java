package guru.springframework.sdjpaintro.jdbctemplate.domain;

import java.util.Objects;
import java.util.StringJoiner;

import javax.persistence.*;

@Entity
@Table(name = "book_jdbctemplate")
public class BookJdbcTemplate {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;
  private String isbn;
  private String publisher;
  private Long authorId;

  public BookJdbcTemplate() {}

  public BookJdbcTemplate(String title, String isbn, String publisher) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BookJdbcTemplate that = (BookJdbcTemplate) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", BookJdbcTemplate.class.getSimpleName() + "[", "]")
        .add("id=" + id)
        .add("title='" + title + "'")
        .add("isbn='" + isbn + "'")
        .add("publisher='" + publisher + "'")
        .add("authorId=" + authorId)
        .toString();
  }
}
