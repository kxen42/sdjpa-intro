package guru.springframework.sdjpaintro.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import guru.springframework.sdjpaintro.introduction.domain.AuthorUuid;
import guru.springframework.sdjpaintro.introduction.domain.Book;
import guru.springframework.sdjpaintro.introduction.domain.BookUuid;
import guru.springframework.sdjpaintro.introduction.repository.AuthorUuidRepository;
import guru.springframework.sdjpaintro.introduction.repository.BookRepository;
import guru.springframework.sdjpaintro.introduction.repository.BookUuidRepository;


@Profile({"local", "default"})
@Component
public class DataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final AuthorUuidRepository authorUuidRepository;
    private final BookUuidRepository bookUuidRepository;

    public DataInitializer(BookRepository bookRepository, AuthorUuidRepository authorUuidRepository, BookUuidRepository bookUuidRepository) {
        this.bookRepository = bookRepository;
        this.authorUuidRepository = authorUuidRepository;
        this.bookUuidRepository = bookUuidRepository;
    }

    @SuppressWarnings("java:S106") // don't want to bother with logging lib
    @Override
    public void run(String... args) throws Exception {

        bookRepository.deleteAll();
        authorUuidRepository.deleteAll();

        Book bookDDD = new Book(null, "Domain Driven Design", "123", "RandomHouse");
        Book savedDDD = bookRepository.save(bookDDD);

        Book bookSIA = new Book(null, "Spring In Action", "234234", "OReilly");
        Book savedSIA = bookRepository.save(bookSIA);

        bookRepository.findAll().forEach(System.out::println);

        AuthorUuid authorUuid = new AuthorUuid();
        authorUuid.setFirstName("Joe");
        authorUuid.setLastName("Buck");
        AuthorUuid savedAuthor = authorUuidRepository.save(authorUuid);
        System.out.println("Saved Author UUID: " + savedAuthor );

        BookUuid bookUuid = new BookUuid();
        bookUuid.setTitle("All About UUIDs");
        BookUuid savedBookUuid = bookUuidRepository.save(bookUuid);
        System.out.println("Saved Book UUID: " + savedBookUuid);
    }
}
