package com.example.quiz10.vo;

import com.example.quiz10.entity.Question;
import com.example.quiz10.entity.Quiz;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
public class CreateUpdateReq extends Question {
@Valid
    private List<Quiz> quizList ;

    public void setQuizList(List<Quiz> quizList) {
        this.quizList = quizList;
    }

    public CreateUpdateReq() {
    }

    public List<Quiz> getQuizList() {
        return quizList;
    }

    public CreateUpdateReq(int id, String name, String description, LocalDate startDate, LocalDate endDate, boolean published) {
        super(id, name, description, startDate, endDate, published);
    }

    public CreateUpdateReq(String name, String description, LocalDate startDate, LocalDate endDate, boolean published) {
        super(name, description, startDate, endDate, published);
    }
}
