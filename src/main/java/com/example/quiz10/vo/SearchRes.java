package com.example.quiz10.vo;

import java.util.List;

public class SearchRes extends BasicRes {
    private List<QuizRes> quizResList;

    public SearchRes() {
    }

    public SearchRes(List<QuizRes> quizResList) {
        this.quizResList = quizResList;
    }

    public List<QuizRes> getQuizResList() {
        return quizResList;
    }

    public SearchRes(int code, String message,List<QuizRes> quizResList) {
        super(code, message);
        this.quizResList = quizResList;
    }
}
