package com.example.quiz10.idclass;

import java.io.Serializable;

public class QuizId implements Serializable {

    private int quizId;

    private int id;

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuizId() {
        return quizId;
    }

    public int getId() {
        return id;
    }

}
