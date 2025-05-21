package com.rookies3.myspringbootlab.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "publishers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter @Builder
@Setter
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "publisher_id")
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private LocalDate establishedDate;
    @Column(nullable = false)
    private String address;
    @OneToMany(
            mappedBy = "publisher",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book){
        books.add(book);
        book.setPublisher(this);
    }

    public void removeBook(Book book){
        books.remove(book);
        book.setPublisher(null);
    }
}
