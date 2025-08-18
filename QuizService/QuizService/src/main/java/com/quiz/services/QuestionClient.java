package com.quiz.services;
import com.quiz.entities.Question;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(url = "http://localhost:8082", value = "Question-Client")
public interface QuestionClient {





 @GetMapping("/questions/quiz/{quizId}")
    List<Question> getQuestionOfQuiz(@PathVariable("quizId") Long quizId);
}