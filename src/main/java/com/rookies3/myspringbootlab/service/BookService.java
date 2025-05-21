package com.rookies3.myspringbootlab.service;

import com.rookies3.myspringbootlab.controller.dto.BookDTO;
import com.rookies3.myspringbootlab.entity.Book;
import com.rookies3.myspringbootlab.entity.BookDetail;
import com.rookies3.myspringbootlab.exception.BusinessException;
import com.rookies3.myspringbootlab.exception.ErrorCode;
import com.rookies3.myspringbootlab.repository.BookRepository;
import com.rookies3.myspringbootlab.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    public List<BookDTO.Response> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(BookDTO.Response::fromEntity).collect(Collectors.toList());
    }

    public BookDTO.Response getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Book", "id", id));
        return BookDTO.Response.fromEntity(book);
    }

    public BookDTO.Response getBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Book", "ISBN", isbn));
        return BookDTO.Response.fromEntity(book);
    }

    public List<BookDTO.Response> getBooksByAuthor(String author) {
        List<Book> books = bookRepository.findByAuthorIgnoreCaseContaining(author);
        return books.stream()
                .map(BookDTO.Response::fromEntity).toList();
    }

    public List<BookDTO.Response> getBooksByTitle(String title) {
        List<Book> books = bookRepository.findByTitleIgnoreCaseContaining(title);
        return books.stream()
                .map(BookDTO.Response::fromEntity).toList();
    }

    public List<BookDTO.Response> getBooksByPublisherId(Long publisherId){
        if(!publisherRepository.existsById(publisherId)){
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,"Publisher","id",publisherId);
        }
        return bookRepository.findByPublisherId(publisherId).stream()
                .map(BookDTO.Response::fromEntity).toList();
    }

    @Transactional
    public BookDTO.Response createBook(BookDTO.Request request) {
        if (bookRepository.existsByIsbn(request.getIsbn())) {
            throw new BusinessException(ErrorCode.ISBN_DUPLICATE, request.getIsbn());
        }

        Book createdBook = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .price(request.getPrice())
                .publishDate(request.getPublishDate()).build();

        if (request.getDetailRequest() != null) {
            BookDetail bookDetail = BookDetail.builder()
                    .description(request.getDetailRequest().getDescription())
                    .language(request.getDetailRequest().getLanguage())
                    .pageCount(request.getDetailRequest().getPageCount())
                    .publisher(request.getDetailRequest().getPublisher())
                    .coverImageUrl(request.getDetailRequest().getCoverImageUrl())
                    .edition(request.getDetailRequest().getEdition())
                    .build();
            createdBook.setBookDetail(bookDetail);
        }
        Book savedBook = bookRepository.save(createdBook);
        return BookDTO.Response.fromEntity(savedBook);
    }

    @Transactional
    public BookDTO.Response updateBook(Long id, BookDTO.Request request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,"Book","id",id));

        if (!book.getIsbn().equals((request.getIsbn())) && bookRepository.existsByIsbn(request.getIsbn())){
            throw new BusinessException(ErrorCode.ISBN_DUPLICATE, request.getIsbn());
        }
        book.setPrice(request.getPrice());
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setPublishDate(request.getPublishDate());
        if (request.getDetailRequest() != null){
            BookDetail bookDetail = book.getBookDetail();

            if(bookDetail == null){
                bookDetail = new BookDetail();
                bookDetail.setBook(book);
                book.setBookDetail(bookDetail);
            }

            bookDetail.setDescription(request.getDetailRequest().getDescription());
            bookDetail.setEdition(request.getDetailRequest().getEdition());
            bookDetail.setLanguage(request.getDetailRequest().getLanguage());
            bookDetail.setPublisher(request.getDetailRequest().getPublisher());
            bookDetail.setPageCount(request.getDetailRequest().getPageCount());
            bookDetail.setCoverImageUrl(request.getDetailRequest().getCoverImageUrl());
        }
        Book updatedBook = bookRepository.save(book);
        return BookDTO.Response.fromEntity(updatedBook);
    }

    @Transactional
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,"Book","id",id));
        bookRepository.delete(book);
    }
}
