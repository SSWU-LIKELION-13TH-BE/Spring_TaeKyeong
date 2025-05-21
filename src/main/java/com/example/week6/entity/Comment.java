package com.example.week6.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", unique = true, nullable = false)
    private Long commentId;

    @Column(length = 500, nullable = false)
    private String content;

    @Column(length = 15, nullable = false)
    private String writer;

    private LocalDate postDate;

    @PrePersist
    protected void onCreate() {
        this.postDate = LocalDate.now();
    }
}
