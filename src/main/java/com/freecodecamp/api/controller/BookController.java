package com.freecodecamp.api.controller;


import com.freecodecamp.api.exceptioins.BookNotFoundException;
import com.freecodecamp.api.model.Book;
import com.freecodecamp.api.model.Genre;
import com.freecodecamp.api.repos.BookRepository;
import com.freecodecamp.api.repos.GenreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class BookController {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    GenreRepo genreRepo;

    //GET ALL
    @GetMapping("/books")
    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    // GET ONE
    @GetMapping("/books/{id}")
    public Book getBookById(@PathVariable(value= "id") Long bookId)
        throws BookNotFoundException {
        return bookRepository.findById(bookId)
                .orElseThrow(()-> new BookNotFoundException(bookId));
    }

    //CREATE
    @PostMapping("/books")
    public Book createBook(@Valid @RequestBody Book book){
        Genre genre = new Genre();
        genre.setBook(book);
        book.setGenre(genre);

        return bookRepository.save(book);
    }

    //UPDATE
    @PutMapping("/books/{id}")
    public Book updateBook(@PathVariable(value="id") Long bookId,
                           @Valid @RequestBody Book bookDetails)
        throws BookNotFoundException {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(()-> new BookNotFoundException(bookId));

        book.setBook_name(bookDetails.getBook_name());
        book.setAuthor_name(bookDetails.getAuthor_name());

        Book updatedBook = bookRepository.save(book);

        return updatedBook;
    }


    //DELETE
    @DeleteMapping("/books/{id}")
    public ResponseEntity<?>
    deleteBook(@PathVariable(value="id") Long bookId)
        throws BookNotFoundException {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(()-> new BookNotFoundException(bookId));

        bookRepository.delete(book);
        return ResponseEntity.ok().build();
    }

}
