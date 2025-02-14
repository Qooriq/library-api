package com.java.akdev.libraryapi.entity;

import com.java.akdev.libraryapi.enumeration.BookStatus;
import com.java.akdev.libraryapi.enumeration.Genre;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"bookTracker"})
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

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "book_id")
    private List<BookTracker> bookTracker;
}
