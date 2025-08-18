package com.quiz.controllers;

import com.quiz.services.QuizService;
import com.quiz.entities.Quiz;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping
    public ResponseEntity<Quiz> create(@RequestBody Quiz quiz) {
        Quiz createdQuiz = this.quizService.addQuiz(quiz);
        return ResponseEntity.ok(createdQuiz);
    }

    @GetMapping
    public ResponseEntity<?> get() {
        try {
            List<Quiz> quizzes = quizService.get();
            return ResponseEntity.ok(quizzes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal Error: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        try {
            Quiz quiz = quizService.get(id);
            return ResponseEntity.ok(quiz);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error fetching quiz: " + e.getMessage());
        }
    }
}

