package com.example.quiz10.entity;

import com.example.quiz10.idclass.QuizId;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "quiz")
@IdClass(value = QuizId.class)

public class Quiz {

   @Min(value = 0,message = "QuizId must be greater than 0")
    @Id
    @Column(name = "quiz_id")
    private int quizId;

    @Min(value = 1,message = "Ques Id must be greater than 0")
    @Id
    @Column(name = "id")
    private int id;

    @NotBlank(message = "Question cannot be null or empty")
    @Column(name = "qu")
    private String qu;

    @NotBlank(message = "Question type cannot be null or empty")
    @Column(name = "type")
    private String type;

    @Column(name = "necessary")
    private Boolean necessary;

    @Column(name = "options")
    private String options;

    public Quiz(int quizId, int id, String qu, String type, Boolean necessary, String options) {
        this.quizId = quizId;
        this.id = id;
        this.qu = qu;
        this.type = type;
        this.necessary = necessary;
        this.options = options;
    }



    public void setQu(String qu) {
        this.qu = qu;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setNecessary(Boolean necessary) {
        this.necessary = necessary;
    }

    public void setOptions(String options) {
        this.options = options;
    }



    public String getQu() {
        return qu;
    }

    public String getType() {
        return type;
    }

    public Boolean isNecessary() {
        return necessary;
    }

    public String getOptions() {
        return options;
    }

    public Quiz() {
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
