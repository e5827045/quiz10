package com.example.quiz10.vo;

import java.time.LocalDate;

public class SearchReq {
    private String quizName;
    private LocalDate startDate;
    private LocalDate endDate;

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getQuizName() {
        return quizName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
