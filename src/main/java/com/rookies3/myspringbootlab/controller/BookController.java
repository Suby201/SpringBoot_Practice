package com.rookies3.myspringbootlab.controller;

import com.rookies3.myspringbootlab.entity.Book;
import com.rookies3.myspringbootlab.exception.BusinessException;
import com.rookies3.myspringbootlab.repository.BookRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {
    private final BookRepository bookRepository;

    @PostMapping
    public Book create(@RequestBody Book book){
        return bookRepository.save(book);
    }

    @GetMapping
    public List<Book> getBooks(){
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id){
        Optional<Book> optionalBook = bookRepository.findById(id);
        return optionalBook.map(ResponseEntity::ok)
                .orElse(new ResponseEntity("Book Not Found", HttpStatus.NOT_FOUND));
    }

    @GetMapping("/isbn/{isbn}/")
    public Book getBookByEmail(@PathVariable String isbn){
        Optional<Book> optionalBook = bookRepository.findByIsbn(isbn);
        return optionalBook.orElseThrow(() -> new BusinessException("Book Not Found", HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetail){
        Optional<Book> optionalBook = bookRepository.findById(id);
        Book existBook =optionalBook.orElseThrow(() -> new BusinessException("Book Not Found", HttpStatus.NOT_FOUND));
        existBook.setPrice(bookDetail.getPrice());
        Book updatedBook =bookRepository.save(existBook);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id){
        Optional<Book> optionalBook = bookRepository.findById(id);
        Book existBook =optionalBook.orElseThrow(() -> new BusinessException("Book Not Found", HttpStatus.NOT_FOUND));
        bookRepository.delete(existBook);
        return ResponseEntity.ok("Book Successfully Deleted");
    }
}
