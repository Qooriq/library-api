package com.java.akdev.libraryapi.repository;

import com.java.akdev.libraryapi.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    Book findByIsbn(String isbn);
}
