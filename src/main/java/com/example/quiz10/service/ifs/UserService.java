package com.example.quiz10.service.ifs;

import com.example.quiz10.vo.BasicRes;
import com.example.quiz10.vo.UserReq;

public interface UserService {
    public BasicRes login(UserReq req);

    public BasicRes register(UserReq req);

}
