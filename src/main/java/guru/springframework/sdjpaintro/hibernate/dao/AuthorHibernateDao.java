package guru.springframework.sdjpaintro.hibernate.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;

import guru.springframework.sdjpaintro.hibernate.domain.AuthorHibernate;

public interface AuthorHibernateDao {

  AuthorHibernate getById(Long id);

  AuthorHibernate findAuthorByName(String firstName, String lastName);

  List<AuthorHibernate> findAllAuthorsByLastName(String lastName, Pageable pageable);

  List<AuthorHibernate> findByLastName(String lastName);

  AuthorHibernate saveNewAuthor(AuthorHibernate author);

  AuthorHibernate alternateSaveNewAuthor(AuthorHibernate author);

  AuthorHibernate updateAuthor(AuthorHibernate author);

  void deleteAuthorById(Long id);

  List<AuthorHibernate> findAll();

  List<AuthorHibernate> listAuthorByLastNameLike(String pattern);

  AuthorHibernate findAuthorByNameCriteria(String firstName, String lastName);

  AuthorHibernate findAuthorByNameNative(String firstName, String lastName);
}
