package com.example.goodnight_backend;

import java.sql.Date;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Mood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Long quoteId;
    private String mood;
    private LocalDateTime timestamp;


    public Mood(String Mood, Long quoteID, LocalDateTime timestamp) {
        this.mood = mood;
        this.quoteId = quoteID;
        this.timestamp = timestamp;
    }


}
