package com.example;

import java.sql.Date;

import com.example.goodnight_backend.Quote;

import jakarta.persistence.Entity;

@Entity
public class Mood {

    private long id;
    private Quote quoteId;
    private String mood;
    private Date timestamp;

    
}
