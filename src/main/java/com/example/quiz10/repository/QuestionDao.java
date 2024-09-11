package com.example.quiz10.repository;

import com.example.quiz10.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {
    public boolean existsByIdInAndPublishedTrueAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            List<Integer> QuizIdList, LocalDate now1, LocalDate now2);

    //問卷名稱為模糊比對 因此使用Containing
    public List<Question> findByNameContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqual(
        String quizName,LocalDate startDate,LocalDate endDATE

    );


}
