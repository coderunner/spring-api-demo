package com.inf5190.demo.mcp;

import java.util.Collection;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;
import com.inf5190.demo.books.model.Book;
import com.inf5190.demo.books.model.BookField;
import com.inf5190.demo.books.repository.BooksRepository;

@Service
public class BooksTools {

    private BooksRepository repository;

    public BooksTools(BooksRepository repository) {
        this.repository = repository;
    }

    @Tool(name = "fx-lister-livres",
            description = "Obtenir la liste de tous les livres de ma collection personnelle")
    public Collection<Book> getBooks() {
        return repository.getAllBooks(BookField.id, null, 0L, 100);
    }

    @Tool(name = "fx-obtenir-livre", description = "Obtenir un livre par son identificateur")
    public Book getBookById(Long id) {
        return repository.getBook(id).orElse(null);
    }

    @Tool(name = "fx-ajouter-livre", description = "Ajouter un livre à ma collection")
    public void addBook(Book book) {
        repository.addBook(book);
    }
}
