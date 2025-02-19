package com.java.akdev.bookstorageservice.entity;

import com.java.akdev.bookstorageservice.enumeration.BookStatus;
import com.java.akdev.bookstorageservice.enumeration.Genre;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, name = "isbn")
    private String isbn;

    @Column(nullable = false, name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "genre")
    private Genre genre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "book_status")
    private BookStatus bookStatus;

    @Column(name = "description")
    private String description;

    @Column(nullable = false, name = "author")
    private String author;


}
