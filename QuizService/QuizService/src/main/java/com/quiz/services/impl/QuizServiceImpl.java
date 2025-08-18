// package com.quiz.services.impl;


// import com.quiz.entities.Quiz;
// import com.quiz.services.QuizService;
// import com.quiz.repositories.QuizRepository;
// import org.springframework.stereotype.Service;
// import java.util.List;
// import java.util.stream.Collector;
// import java.util.stream.Collectors;

// import com.quiz.services.QuestionClient;

// @Service
// public class QuizServiceImpl implements QuizService {
//     private final QuizRepository quizRepository;

//     private final QuestionClient questionClient;
//     public QuizServiceImpl(QuizRepository quizRepository, QuestionClient questionClient) {
//         this.quizRepository = quizRepository;
//         this.questionClient = questionClient;
//     }
//    @Override
//     public Quiz addQuiz(Quiz quiz) {
//         return quizRepository.save(quiz);
//     }



//     @Override
//     public List<Quiz> get() {
//      List<Quiz> quizzes = quizRepository.findAll();

//     List<Quiz> newQuizList= quizzes.stream().map(quiz ->{
//         quiz.setQuestions(questionClient.getQuestionOfQuiz(quiz.getId()));
//         return quiz;
//      }).collect(Collectors.toList());
//         return newQuizList;
//     }

//     @Override
//     public Quiz get(Long id) {
//        Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new RuntimeException("Quiz not found"));
//        quiz.setQuestions(questionClient.getQuestionOfQuiz(quiz.getId()));
//        return quiz;
//     }
// }


package com.quiz.services.impl;

import com.quiz.entities.Question;
import com.quiz.entities.Quiz;
import com.quiz.repositories.QuizRepository;
import com.quiz.services.QuestionClient;
import com.quiz.services.QuizService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final QuestionClient questionClient;

    public QuizServiceImpl(QuizRepository quizRepository, QuestionClient questionClient) {
        this.quizRepository = quizRepository;
        this.questionClient = questionClient;
    }

    @Override
    public Quiz addQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    @Override
    public List<Quiz> get() {
        List<Quiz> quizzes = quizRepository.findAll();

        return quizzes.stream().map(quiz -> {
            try {
                System.out.println("Fetching questions for quiz ID: " + quiz.getId());
                List<Question> questions = questionClient.getQuestionOfQuiz(quiz.getId());
                quiz.setQuestions(questions != null ? questions : Collections.emptyList());
            } catch (Exception e) {
                System.out.println("⚠️ Error fetching questions for quiz ID: " + quiz.getId());
                e.printStackTrace();
                quiz.setQuestions(Collections.emptyList());
            }
            return quiz;
        }).collect(Collectors.toList());
    }

    @Override
    public Quiz get(Long id) {
        Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new RuntimeException("Quiz not found"));

        try {
            List<Question> questions = questionClient.getQuestionOfQuiz(quiz.getId());
            quiz.setQuestions(questions != null ? questions : Collections.emptyList());
        } catch (Exception e) {
            System.out.println("⚠️ Error fetching questions for quiz ID: " + quiz.getId());
            e.printStackTrace();
            quiz.setQuestions(Collections.emptyList());
        }

        return quiz;
    }
}

