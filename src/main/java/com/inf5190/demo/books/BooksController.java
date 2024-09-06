package com.inf5190.demo.books;

import java.util.Collection;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.inf5190.demo.books.model.Book;
import com.inf5190.demo.books.model.BookField;
import com.inf5190.demo.books.model.OrderDirection;
import com.inf5190.demo.books.repository.BooksRepository;

/**
 * L'annotation RestController indique à Spring que la classe qui suit est un controller REST
 */
@RestController
public class BooksController {
    private final BooksRepository repository;

    /**
     * Ici, comme la classe BooksRepository est annotée avec @Repository, Spring injecte l'instance
     * dans le constructeur.
     * 
     * Ce serait la même chose pour une classe annotée avec @Component ou @Service.
     */
    public BooksController(BooksRepository repository) {
        this.repository = repository;
    }

    /**
     * L'annotation @GetMapping indique à Spring que cette méthode doit être appelée sur un GET sur
     * le chemin d'accès /books.
     * 
     * Les annotations @RequestParam définissent les paramètres de requête qui sont possibles et
     * leur valeur par défaut.
     */
    @GetMapping("/books")
    public Collection<Book> getAll(
            @RequestParam(name = "orderBy", defaultValue = "id") BookField field,
            @RequestParam(name = "direction", defaultValue = "asc") OrderDirection direction,
            @RequestParam(name = "offset", defaultValue = "0") Long offset,
            @RequestParam(name = "count", defaultValue = "10") Integer count) {
        return repository.getAllBooks(field, direction, offset, count);
    }

    /**
     * Ici, on définit le comportement de la méthode POST.
     * 
     * L'annotation @RequestBody utilise les données du corps de la requête HTTP pour créer l'objet
     * du modèle.
     * 
     * La classe ResponseEntity vous permet plus de flexibilité dans la réponse. Par exemple, vous
     * pouvez controller le code de retour ou les en-têtes HTTP qui seront retournés (ex.
     * ResponseEntity.ok().header("name", "value").body(b)).
     */
    @PostMapping("/books")
    public ResponseEntity<Book> post(@RequestBody Book newBook) {
        Book book = repository.addBook(newBook);
        return ResponseEntity.ok().body(book);
    }

    /**
     * Ici, on définit le comportement de la méthode GET avec un chemin d'accès qui contient un id.
     * 
     * L'annotation @PathVariable récupère l'id du chemin d'accès.
     * 
     * Même chose pour les deux méthodes suivantes qui agissent sur PUT et DELETE.
     */
    @GetMapping("/books/{id}")
    public Optional<Book> get(@PathVariable("id") Long id) {
        return repository.getBook(id);
    }

    @PutMapping("/books/{id}")
    public Optional<Book> update(@PathVariable("id") Long id, @RequestBody Book update) {
        return repository.update(id, update);
    }

    @DeleteMapping("/books/{id}")
    public void delete(@PathVariable("id") Long id) {
        repository.deleteBook(id);
    }
}
