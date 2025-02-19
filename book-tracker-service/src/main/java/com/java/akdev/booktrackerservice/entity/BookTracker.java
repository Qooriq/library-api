package com.java.akdev.booktrackerservice.entity;

import com.java.akdev.booktrackerservice.enumeration.BookTrackerStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"book", "user"})
@Table(name = "book_tracker")
public class BookTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "isbn")
    private String isbn;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "book_status")
    private BookTrackerStatus bookTrackerStatus;

    @Column(name = "took_at")
    private LocalDateTime tookAt;

    @Column(name = "return_before")
    private LocalDateTime returnBefore;

    @Column(name = "took_by")
    private String username;
}
