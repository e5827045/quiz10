package com.example.quiz10.entity;


import com.example.quiz10.idclass.FeedbackId;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "feedback")
@IdClass(value = FeedbackId.class)
public class Feedback {

    @Min(value = 1,message = "QuizId must be greater than 0")
    @Id
    @Column(name = "qu_id")
    private  int quId;

    @Min(value = 1,message = "Ques Id must be greater than 0")
    @Id
    @Column(name = "quiz_id")
    private  int quizId;
            ;

    @NotBlank
    @Column(name = "name")
    private  String name;

    @Column(name = "phone")
    private  String phone;

    @NotBlank
    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "age")
    private  int age;

    @Column(name = "ans")
    private  String ans;

    @Column(name = "fillin_date_time")
    private LocalDateTime fillinDatetime;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public LocalDateTime getFillinDatetime() {
        return fillinDatetime;
    }

    public void setFillinDatetime(LocalDateTime fillinDatetime) {
        this.fillinDatetime = fillinDatetime;
    }

    public Feedback(LocalDateTime fillinDatetime, int quId, int quizId, String name, String phone, String email, int age, String ans) {
        this.quId = quId;
        this.quizId = quizId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.age = age;
        this.ans = ans;
        this.fillinDatetime = fillinDatetime;
    }

    public Feedback() {
    }
}
