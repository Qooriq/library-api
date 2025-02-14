package com.java.akdev.libraryapi.repository;

import com.java.akdev.libraryapi.entity.BookTracker;
import com.java.akdev.libraryapi.enumeration.BookTrackerStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookTrackerRepository extends JpaRepository<BookTracker, Long> {

    Page<BookTracker> findAllByBookTrackerStatus(BookTrackerStatus bookTrackerStatus, Pageable pageable);

    List<BookTracker> findBookTrackerByBookId(Long bookId);

    List<BookTracker> findByBookIsbn(String isbn);
}
