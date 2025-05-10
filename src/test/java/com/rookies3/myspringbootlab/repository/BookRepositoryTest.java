package com.rookies3.myspringbootlab.repository;

import com.rookies3.myspringbootlab.entity.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class BookRepositoryTest {
    @Autowired
    BookRepository bookRepository;

    @Test
    @Rollback(value = false)
    void testCreateBook(){
        Book book1 = new Book();
        Book book2 = new Book();
        book1.setTitle("스프링 부트 입문");
        book1.setAuthor("홍길동");
        book1.setIsbn("9788956746425");
        book1.setPrice(30000);
        book1.setPublishDate(LocalDate.of(2025,5,7));
        book2.setTitle("JPA 프로그래밍");
        book2.setAuthor("박둘리");
        book2.setIsbn("9788956746432");
        book2.setPrice(35000);
        book2.setPublishDate(LocalDate.of(2025,4,30));
        Book saveBook1 = bookRepository.save(book1);
        Book saveBook2 = bookRepository.save(book2);
        assertThat(saveBook1).isNotNull();
        assertThat(saveBook1.getTitle()).isEqualTo("스프링 부트 입문");

    }

    @Test
    @Rollback(value = false)
    void testFindByIsbn(){
        Book book = bookRepository.findByIsbn("9788956746425")
                .orElseThrow(()->new RuntimeException("Book Not Found"));
        assertThat(book.getTitle()).isEqualTo("스프링 부트 입문");
        assertThat(book.getAuthor()).isEqualTo("홍길동");
    }

    @Test
    @Rollback(value = false)
    void testFindByAuthor(){
        List<Book> books = bookRepository.findByAuthor("박둘리");
        assertThat(books.get(0).getIsbn()).isEqualTo("9788956746432");
        assertThat(books.get(0).getPrice()).isEqualTo(35000);
    }

    @Test
    @Rollback(value = false)
    void testUpdateBook(){
        Book book = bookRepository.findByIsbn("9788956746425")
                .orElseThrow(()->new RuntimeException("Book Not Found"));
        book.setPrice(27000);
        bookRepository.save(book);
        assertThat(book.getPrice()).isEqualTo(27000);
    }

    @Test
    @Rollback(value = false)
    void testDeleteBook(){
        Book book = bookRepository.findByIsbn("9788956746425")
                .orElseThrow(()->new RuntimeException("Book Not Found"));
        bookRepository.delete(book);
    }
}