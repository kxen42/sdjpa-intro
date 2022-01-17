package guru.springframework.sdjpaintro.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import guru.springframework.sdjpaintro.domain.Book;
import guru.springframework.sdjpaintro.repositories.BookRepository;

@Profile({"local", "default"})
@Component
public class DataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;

    public DataInitializer(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @SuppressWarnings("java:S106") // just blow off the warnings about System.out DON'T DO THIS IN PROD
    @Override
    public void run(String... args) throws Exception {

        bookRepository.deleteAll();

        Book bookDDD = new Book(null, "Domain Driven Design", "123", "RandomHouse");

        System.out.println("DataInitializer Id: " + bookDDD.getId());

        Book savedDDD = bookRepository.save(bookDDD);

        System.out.println("DataInitializer Id: " + savedDDD.getId());

        Book bookSIA = new Book(null, "Spring In Action", "234234", "OReilly");
        Book savedSIA = bookRepository.save(bookSIA);

        System.out.println("DataInitializer");
        bookRepository.findAll().forEach(System.out::println);

    }
}
