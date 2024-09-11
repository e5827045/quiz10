package com.example.quiz10.service.impl;

import com.example.quiz10.constants.ResMessage;
import com.example.quiz10.entity.User;
import com.example.quiz10.repository.UserDao;
import com.example.quiz10.service.ifs.UserService;
import com.example.quiz10.vo.BasicRes;
import com.example.quiz10.vo.UserReq;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl  implements UserService {
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private UserDao userDao;

    @Override
    public BasicRes register(UserReq req) {
        //檢查帳號是否存在
        if (userDao.existsById(req.getName())){
            return new BasicRes(ResMessage.USER_NAME_EXISTED.getCode(),
                    ResMessage.USER_NAME_EXISTED.getMessage());
        }
        //把密碼加密
        String  encoderPwd =encoder.encode(req.getPwd());
        userDao.save(new User(req.getName(),encoderPwd));
        return new BasicRes(ResMessage.SUCCESS.getCode(),
                ResMessage.SUCCESS.getMessage());

    }
    @Override
    public BasicRes login(UserReq req) {
        //先用帳號去撈資料庫
        Optional<User> op = userDao.findById(req.getName());
        if (op.isEmpty()){
            return new BasicRes(ResMessage.USER_NAME_NOT_FOUND.getCode(),
                    ResMessage.USER_NAME_EXISTED.getMessage());
        }
        //把密碼解密並比對
        User user = op.get();
        if (!encoder.matches(req.getPwd(),user.getPwd())){
            return new BasicRes(ResMessage.PASSWORD_INCONSISTENT.getCode(),
                    ResMessage.PASSWORD_INCONSISTENT.getMessage());
        }

        return  new BasicRes(ResMessage.SUCCESS.getCode(),
                ResMessage.SUCCESS.getMessage());

    }
}
