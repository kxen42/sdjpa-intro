package guru.springframework.sdjpaintro.hibernate.dao;

import java.util.List;
import java.util.stream.Stream;

import guru.springframework.sdjpaintro.hibernate.domain.AuthorHibernate;

public interface AuthorHibernateDao {

    AuthorHibernate getById(Long id);

    AuthorHibernate findAuthorByName(String firstName, String lastName);

    AuthorHibernate saveNewAuthor(AuthorHibernate author);

    AuthorHibernate alternateSaveNewAuthor(AuthorHibernate author);

    AuthorHibernate updateAuthor(AuthorHibernate author);

    void deleteAuthorById(Long id);

  Stream<AuthorHibernate> findAll();

  List<AuthorHibernate> listAuthorByLastNameLike(String pattern);
}
