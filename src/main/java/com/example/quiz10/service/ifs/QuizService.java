package com.example.quiz10.service.ifs;

import com.example.quiz10.vo.*;


public interface QuizService {
    public BasicRes create(CreateUpdateReq req);

    public BasicRes update(CreateUpdateReq req);

    public BasicRes delete(DeleteReq req);

    public SearchRes search(SearchRes req);

    public SearchRes search(SearchReq req);

    public BasicRes fillin(FillInReq req);

    public StatisticsRes statistics(int quizId);

    public  FeedbackRes  feedback(int quizId);





}
