package com.rookies3.myspringbootlab.controller.dto;

import com.rookies3.myspringbootlab.entity.Book;
import com.rookies3.myspringbootlab.entity.BookDetail;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

public class BookDTO {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request{
        @NotBlank(message = "Book title is required")
        @Size(max = 100, message = "Book title cannot exceed 100 characters")
        private String title;
        @NotBlank(message = "Book author is required")
        @Size(max = 100, message = "Book author cannot exceed 100 characters")
        private String author;
        @NotBlank(message = "Book ISBN is required")
        @Size(max = 30, message = "Book ISBN cannot exceed 30 characters")
        private String isbn;
        private Integer price;
        private LocalDate publishDate;
        private BookDetailDTO detailRequest;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BookDetailDTO{
        private Long id;
        @NotBlank(message = "Book description is required")
        private String description;
        @NotBlank(message = "Book language is required")
        private String language;
        @NotBlank(message = "Book page count is required")
        private Integer pageCount;
        @NotBlank(message = "Book publisher is required")
        private String publisher;
        private String coverImageUrl;
        @NotBlank(message = "Book edition is required")
        private String edition;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response{
        private Long id;
        private String title;
        private String author;
        private String isbn;
        private Integer price;
        private LocalDate publishDate;
        private BookDetailResponse detail;

        public static Response fromEntity(Book book){
        BookDetailResponse detailResponse = book.getBookDetail() != null
                ? BookDetailResponse.builder()
                .id(book.getBookDetail().getId())
                .description(book.getBookDetail().getDescription())
                .language(book.getBookDetail().getLanguage())
                .pageCount(book.getBookDetail().getPageCount())
                .publisher(book.getBookDetail().getPublisher())
                .coverImageUrl(book.getBookDetail().getCoverImageUrl())
                .edition(book.getBookDetail().getEdition())
                .build()
            : null;

        return Response.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .price(book.getPrice())
                .publishDate(book.getPublishDate())
                .detail(detailResponse)
                .build();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BookDetailResponse{
        private Long id;
        private String description;
        private String language;
        private Integer pageCount;
        private String publisher;
        private String coverImageUrl;
        private String edition;
    }
}