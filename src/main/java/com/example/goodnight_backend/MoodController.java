package com.example.goodnight_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/mood")
@CrossOrigin(origins = "http://localhost:8080")
public class MoodController {

    @Autowired
    private MoodRepository moodRepository;

    @PostMapping
    public ResponseEntity<Mood> addMood(@RequestBody Mood mood) {
        mood.setTimestamp(LocalDateTime.now());
        Mood savedMood = moodRepository.save(mood);
        return ResponseEntity.ok(savedMood);
    }
}