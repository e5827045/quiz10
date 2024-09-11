package com.example.quiz10.vo;

import java.util.List;
import java.util.Map;

public class StatisticsRes extends BasicRes{

    private  String quizName;
    private List<StatisticsVo>  statisticsList;
    public String getQuizName() {
        return quizName;

    }

    public StatisticsRes() {
    }

    public StatisticsRes(String quizName, List<StatisticsVo> statisticsList) {
        this.quizName = quizName;
        this.statisticsList = statisticsList;
    }

    public StatisticsRes(int code, String message) {
        super(code, message);
    }

    public StatisticsRes(int code, String message, String quizName, List<StatisticsVo> statisticsList) {
        super(code, message);
        this.quizName = quizName;
        this.statisticsList = statisticsList;
    }

    public List<StatisticsVo> getStatisticsList() {
        return statisticsList;
    }
}
