package com.example.goodnight_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/quotes")
@CrossOrigin(origins = "http://localhost:8080")
public class QuoteController {

    @Autowired
    private QuoteRepository quoteRepository;


    @PostMapping
    public Quote addQuote(@RequestBody Quote quote) {
        return quoteRepository.save(quote);
    }

    @GetMapping("/random")
    public Quote getRandomQuote() {
        List<Quote> quotes = quoteRepository.findAll();
        
        if (quotes.isEmpty()) {
            return null;
        }
        Random random = new Random();
        return quotes.get(random.nextInt(quotes.size()));
    }
}
