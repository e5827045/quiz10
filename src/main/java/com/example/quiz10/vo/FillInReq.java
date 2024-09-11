package com.example.quiz10.vo;


import com.example.quiz10.entity.Feedback;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

public class FillInReq {
    @Valid
    @NotBlank
    private List<Feedback> feedbackList;

    public List<Feedback> getFeebackList() {
        return feedbackList;
    }

    public void setFeedbackList(List<Feedback> feebackList) {
        this.feedbackList = feebackList;
    }
}
