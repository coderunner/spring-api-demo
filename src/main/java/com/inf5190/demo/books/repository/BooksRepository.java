package com.inf5190.demo.books.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import com.inf5190.demo.books.model.Book;
import com.inf5190.demo.books.model.BookField;
import com.inf5190.demo.books.model.OrderDirection;
import com.inf5190.demo.utils.IdGenerator;

/**
 * L'annotation @Repository indique à Spring que cette classe est utilisée pour accéder aux données.
 * 
 * Spring va créer une instance (singleton) injectable dans les autres composants (voir
 * BooksController).
 */
@Repository
public class BooksRepository {

    private final IdGenerator idGenerator;

    private final Map<Long, Book> books;

    /**
     * Ici, on s'amuse avec l'injection de dépendance de spring.
     * 
     * L'annotation @Autowired indique à Spring d'injecter un Bean pour les différents paramètres du
     * constructeur.
     * 
     * Dans ce cas-ci, elle n'est pas nécessaire, car il n'y a qu'un constructeur.
     * 
     * L'annotation @Qualifier permet de nommer l'instance du Bean qu'on désire s'il y a plusieurs
     * Bean du même type qui sont définis.
     */
    @Autowired
    public BooksRepository(@Qualifier(value = "atomicIdGenerator") IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
        this.books = new ArrayList<Book>(
                Arrays.asList(new Book(idGenerator.nextId(), "Fictions", "Jorge Luis Borges"),
                        new Book(idGenerator.nextId(), "Contes", "Hans Christian Andersen"),
                        new Book(idGenerator.nextId(), "L'étranger", "Albert Camus"),
                        new Book(idGenerator.nextId(), "Médée", "Euripide"),
                        new Book(idGenerator.nextId(), "Les âmes mortes", "Nicolas Gogol"),
                        new Book(idGenerator.nextId(), "Iliade", "Homère"),
                        new Book(idGenerator.nextId(), "Ulysse", "James Joyce"),
                        new Book(idGenerator.nextId(), "Dune", "Frank Herbert"))).stream()
                                .collect(Collectors.toMap(Book::id, Function.identity()));
    }

    public Collection<Book> getAllBooks(BookField field, OrderDirection direction, long offset,
            int count) {
        Comparator<Book> bookComparator = getComparator(field);
        return this.books.values().stream().sorted(
                direction == OrderDirection.ASC ? bookComparator : bookComparator.reversed())
                .skip(offset).limit(count).toList();

    }

    public Optional<Book> getBook(long id) {
        return Optional.ofNullable(this.books.get(id));
    }

    public Book addBook(Book newBook) {
        final long id = this.idGenerator.nextId();
        final Book book = new Book(id, newBook.title(), newBook.author());
        this.books.put(id, book);
        return book;
    }

    public void deleteBook(long id) {
        this.books.remove(id);
    }

    public Optional<Book> update(long id, Book update) {
        if (this.books.containsKey(id)) {
            final Book updatedBook = new Book(id, update.title(), update.author());
            this.books.put(id, updatedBook);
            return Optional.of(updatedBook);
        }
        return Optional.empty();
    }

    private Comparator<Book> getComparator(BookField field) {
        return new Comparator<Book>() {

            @Override
            public int compare(Book b0, Book b1) {
                return switch (field) {
                    case ID -> Long.compare(b0.id(), b1.id());
                    case TITLE -> b0.title().compareTo(b1.title());
                    case AUTHOR -> b0.author().compareTo(b1.author());
                    default -> throw new RuntimeException();
                };
            }

        };
    }

}
