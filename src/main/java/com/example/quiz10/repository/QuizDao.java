package com.example.quiz10.repository;

import com.example.quiz10.idclass.QuizId;
import com.example.quiz10.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizDao extends JpaRepository<Quiz, QuizId> {

    public void deleteByQuizId(int quizId);
    public List<Quiz> findByQuizId(int quizId);
    public List<Quiz> findById(int quizId);
    public List<Quiz> findByQuizIdIn(List<Integer> quizList);
    public List<Quiz> findByQuizIdAndTypeNot(int quizId, String selectType);

}
