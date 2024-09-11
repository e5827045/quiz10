package com.example.quiz10.vo;

import java.util.Map;

public class StatisticsVo {
    private int quId;
    private String qu;
    //           問題     問題編號
    private Map<String,Integer> optionCountMap;

    public StatisticsVo() {
    }

    public StatisticsVo(int quId, String qu, Map<String, Integer> optionCountMap) {
        this.quId = quId;
        this.qu = qu;
        this.optionCountMap = optionCountMap;
    }

    public int getQuId() {
        return quId;
    }

    public String getQu() {
        return qu;
    }

    public Map<String, Integer> getOptionCountMap() {
        return optionCountMap;
    }

    public void setQuId(int quId) {
        this.quId = quId;
    }

    public void setQu(String qu) {
        this.qu = qu;
    }

    public void setOptionCountMap(Map<String, Integer> optionCountMap) {
        this.optionCountMap = optionCountMap;
    }
}
