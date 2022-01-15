package guru.springframework.sdjpaintro.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import guru.springframework.sdjpaintro.domain.Book;
import guru.springframework.sdjpaintro.repositories.BookRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;

    public DataInitializer(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @SuppressWarnings("java:S106") //this runs on command line - we don't need logging lib
    @Override
    public void run(String... args) throws Exception {
        Book bookDDD = new Book("Domain Driven Design", "123", "RandomHouse");

        System.out.println("DataInitializer Id: " + bookDDD.getId() );

        Book savedDDD = bookRepository.save(bookDDD);

        System.out.println("DataInitializer Id: " + savedDDD.getId() );

        Book bookSIA = new Book("Spring In Action", "234234", "OReilly");
        Book savedSIA = bookRepository.save(bookSIA);

        System.out.println("DataInitializer");
        bookRepository.findAll().forEach(System.out::println);

    }
}
