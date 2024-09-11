package com.example.quiz10.vo;

import com.example.quiz10.entity.Feedback;

import java.util.List;

public class FeedbackRes extends BasicRes {
    private List<Feedback> feedbackList;

    public FeedbackRes() {
    }



    public FeedbackRes(int code, String message, List<Feedback> feedbackList) {
        super(code, message);
        this.feedbackList = feedbackList;
    }

    public List<Feedback> getFeedbackList() {
        return feedbackList;
    }

    public void setFeedbackList(List<Feedback> feedbackList) {
        this.feedbackList = feedbackList;
    }
}
