package com.example.quiz10.repository;

import com.example.quiz10.entity.Feedback;
import com.example.quiz10.idclass.FeedbackId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackDao extends JpaRepository<Feedback, FeedbackId> {
    public boolean existsByQuizIdAndEmail(int quizId, String email);
    public List<Feedback> findByQuizIdAndQuIdIn(int quizId, List<Integer> quIdList);
    public List<Feedback> findByQuizId(int quizId);
}
