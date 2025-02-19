package com.java.akdev.booktrackerservice.repository;

import com.java.akdev.booktrackerservice.entity.BookTracker;
import com.java.akdev.booktrackerservice.enumeration.BookTrackerStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookTrackerRepository extends JpaRepository<BookTracker, Long> {

    Page<BookTracker> findAllByBookTrackerStatus(BookTrackerStatus bookTrackerStatus, Pageable pageable);

    List<BookTracker> findBookTrackerByIsbn(String isbn);
}
