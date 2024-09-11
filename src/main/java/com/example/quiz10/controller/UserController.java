package com.example.quiz10.controller;

import com.example.quiz10.constants.ResMessage;
import com.example.quiz10.service.ifs.UserService;
import com.example.quiz10.vo.BasicRes;
import com.example.quiz10.vo.UserReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "user/register")
    public BasicRes register(@Valid @RequestBody UserReq req){
        return userService.register(req);
    }
    @PostMapping(value = "user/login")
    public BasicRes login(@Valid@RequestBody UserReq req, HttpSession session){
        //session預設時間為30分鐘，可透過以下方法設定時間長短，括弧中數字單位為秒
        //若數字為0或負數則session不會過期
        session.setMaxInactiveInterval(300);//300秒
        BasicRes res = userService.login(req);
        if(res.getCode()==200){
            //若登入成功把使用者ID暫存於session
            //每個client與server之間的session都不一樣
            session.setAttribute("user_name",req.getName());
        }
        return  res;



    }
    //因無req所以用GetMapping
    @GetMapping (value = "user/logout")
    public BasicRes logout(HttpSession session){
        //Logout要讓彼此之間通訊用的session失效(過期)
        session.invalidate();
        return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
    }
}
