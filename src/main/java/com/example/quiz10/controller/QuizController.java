package com.example.quiz10.controller;

import com.example.quiz10.constants.ResMessage;
import com.example.quiz10.service.ifs.QuizService;
import com.example.quiz10.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@CrossOrigin
@RestController
public class QuizController {

    @Autowired
    private QuizService quizService;


    @PostMapping(value = "quiz/create")
    public BasicRes create(@RequestBody @Valid CreateUpdateReq req) {
        return quizService.create(req);
    }

    //登入成功後才能使用

    @PostMapping(value = "quiz/create_login")
    public BasicRes createLogin(@RequestBody @Valid CreateUpdateReq req, HttpSession session) {
        //在UserController的 login方法中若已登入成功則會透過名為"user_name"的key將req的name帶入session中
        //將資訊存入session方法為 session.setAttribute();
        //取出方法則為session.getAttribute()
        //key字串(user_name)須完全相符才能取出value值(name),若key值無法搜尋則對應value為null
        // 以下寫法若取出value為null時 在轉型成字串時會報錯(NullPointerException)
        //  String userName = (String) session.getAttribute("user_name");
        Object userNameObj = session.getAttribute("user_name");
        if (userNameObj==null){
            return new BasicRes(ResMessage.PLEASE_LOGIN_FIRST.getCode(),
                    ResMessage.PLEASE_LOGIN_FIRST.getMessage());
        }
        //若後續須使用SESSION中取出的值 ,可以將 userNameObj強制轉成字串
        //String userName =(String)userNameObj;
        return quizService.create(req);
    }



    @PostMapping(value = "quiz/update")
    public BasicRes update(@RequestBody @Valid CreateUpdateReq req) {

        return quizService.update(req);
    }

    @PostMapping(value = "quiz/delete")
    public BasicRes delete(@RequestBody @Valid DeleteReq req) {
        return quizService.delete(req);
    }

    @PostMapping(value = "quiz/search")
    public SearchRes search(@RequestBody SearchReq req) {

        return quizService.search(req);
    }

    @PostMapping(value = "quiz/fillin")
    public BasicRes fillin(@RequestBody @Valid FillInReq req) {
        return quizService.fillin(req);
    }

    //外部呼叫API 需使用jQuery的方式quiz/statistics?quizId= 問卷編號(要有問號)
    //quizId的命字要和參數方法中的名稱一樣
    //因為
    @PostMapping(value = "quiz/statistics")
    public StatisticsRes statistics(
            @RequestParam int quizId) {
        return quizService.statistics(quizId);
    }
    //外部呼叫API 需使用jQuery的方式quiz/statistics?quizId= 問卷編號(要有問號)
    //因 @RequestParam中的value是quiz_id所以呼叫路徑的問號後面為quizId
    //@RequestParamm中的required預設為 false
    // defaultValue 是指當參數沒給直時會社預設值
    @PostMapping(value = "quiz/statistics1")
    public StatisticsRes statistics1(
            @RequestParam(value = "quiz_id", required = false, defaultValue = "") int quizId) {
        return quizService.statistics(quizId);
    }//呼叫此API的URL為quiz/feedback?quizId =問卷編號
    @PostMapping(value = "quiz/feedback")
    public  FeedbackRes  feedback( @RequestParam  int quizId){
        return quizService.feedback(quizId);

    }


}
