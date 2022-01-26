package guru.springframework.sdjpaintro.jpa.dao;

import guru.springframework.sdjpaintro.jpa.domain.AuthorJpa;

/**
 * Use DAO to decouple other classes from JPA layer.
 */
public interface AuthorJpaDao {
    AuthorJpa getById(Long id);

    AuthorJpa findAuthorJpaByName(String firstName, String lastName);

    AuthorJpa saveNewAuthorJpa(AuthorJpa author);

    AuthorJpa updateAuthorJpa(AuthorJpa author);

    void deleteAuthorJpaById(Long id);
}
