package com.quiz.entities;
import lombok.AllArgsConstructor;
import lombok.Getter;   
import lombok.NoArgsConstructor;
import lombok.Setter;
//import jakarta.persistence.Id;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    
    
    private int questionId;


    private String question;


    private Long quizId;
}
