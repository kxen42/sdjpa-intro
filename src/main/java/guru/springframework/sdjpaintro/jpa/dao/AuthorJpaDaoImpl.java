/**
 * AuthorJpaDaoImpl
 * Copyright (c) 2022, FastBridge Learning LLC
 * Created on January 25, 2022
 */
package guru.springframework.sdjpaintro.jpa.dao;

import org.springframework.stereotype.Component;

import guru.springframework.sdjpaintro.jpa.domain.AuthorJpa;
import guru.springframework.sdjpaintro.jpa.repository.AuthorJpaRepository;

/**
 * Use DAO to decouple other classes from JPA layer.
 */
@Component
public class AuthorJpaDaoImpl implements AuthorJpaDao {

    private final AuthorJpaRepository authorRepository;

    public AuthorJpaDaoImpl(AuthorJpaRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorJpa getById(Long id) {
        return authorRepository.getById(id);
    }

    @Override
    public AuthorJpa findAuthorJpaByName(String firstName, String lastName) {

        return null;
    }

    @Override
    public AuthorJpa saveNewAuthorJpa(AuthorJpa author) {
        return null;
    }

    @Override
    public AuthorJpa updateAuthorJpa(AuthorJpa author) {
        return null;
    }

    @Override
    public void deleteAuthorJpaById(Long id) {

    }
}
