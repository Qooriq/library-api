package com.java.akdev.libraryapi.entity;

import com.java.akdev.libraryapi.enumeration.BookTrackerStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "book_status")
    private BookTrackerStatus bookTrackerStatus;

    @Column(name = "took_at")
    private LocalDateTime tookAt;

    @Column(name = "return_before")
    private LocalDateTime returnBefore;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "took_by")
    private User user;
}
