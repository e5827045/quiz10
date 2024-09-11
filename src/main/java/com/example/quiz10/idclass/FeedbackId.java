package com.example.quiz10.idclass;

import java.io.Serializable;

public class FeedbackId implements Serializable {
    private  int quId;
    private  int quizId;
    private String email;

    public int getQuId() {
        return quId;
    }

    public void setQuId(int quId) {
        this.quId = quId;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
