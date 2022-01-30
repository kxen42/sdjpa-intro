/** AuthorJpaDaoImpl Copyright (c) 2022, FastBridge Learning LLC Created on January 25, 2022 */
package guru.springframework.sdjpaintro.jpa.dao;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import guru.springframework.sdjpaintro.jpa.domain.AuthorJpa;
import guru.springframework.sdjpaintro.jpa.repository.AuthorJpaRepository;

/** Use DAO to decouple other classes from JPA layer. */
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
    // the Cool stuff is here
    return authorRepository.findAuthorJpaByFirstNameAndLastName(firstName, lastName);
  }

  @Override
  public AuthorJpa optionalFindJpaByName(String lastName) {
    // this is just for show, obviously lastname isn't uniq
    return authorRepository
        .findAuthJpaByLastName(lastName)
        .orElseThrow(EntityNotFoundException::new);
  }

  @Override
  public AuthorJpa saveNewAuthorJpa(AuthorJpa author) {
    return authorRepository.save(author);
  }

  /*
   * NOTE::: using Transactional because by default each repository methods runs in its own Tx
   * This method will be using multiple repo methods and we want them in a single Tx
   */
  @Transactional
  @Override
  public AuthorJpa updateAuthorJpa(AuthorJpa author) {
    // You don't know if the author argument detached, you want the attached entity
    AuthorJpa fetched = authorRepository.getById(author.getId());

    // This is a job for ModelMapper
    fetched.setFirstName(author.getFirstName());
    fetched.setLastName(author.getLastName());
    return authorRepository.save(fetched);
  }

  @Override
  public void deleteAuthorJpaById(Long id) {
    authorRepository.deleteById(id);
  }
}
