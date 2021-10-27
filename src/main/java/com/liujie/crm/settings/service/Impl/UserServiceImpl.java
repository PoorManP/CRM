package com.liujie.crm.settings.service.Impl;

import com.liujie.crm.exception.LoginExcetion;
import com.liujie.crm.settings.dao.UserDao;
import com.liujie.crm.settings.domain.User;
import com.liujie.crm.settings.service.UserService;
import com.liujie.crm.utils.DateTimeUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
@Service
public class UserServiceImpl implements UserService {
    //在处理业务的时候 要与持久层交互
    @Resource
    UserDao dao;
    @Override
    public User login(String loginAct, String loginPwd, String ip) throws LoginExcetion {
        System.out.println("userService imp");
        User user = new User();

        user.setLoginAct(loginAct);
        user.setLoginPwd(loginPwd);

//        System.out.println(1111);
        User login = dao.login(user);
        System.out.println(login+"---->ss");


        if (login == null) {
            throw new LoginExcetion("账号密码错误");
        }
        //密码是正确的
        //需要验证中国星跳跃 的
        String expireTime = login.getExpireTime();
        String sysTime = DateTimeUtil.getSysTime();

        if (expireTime.compareTo(sysTime) < 0) {
            throw new LoginExcetion("账号已失效");
        }
        //判断锁定姿态

        String lockState = login.getLockState();
        if ("0".equals(lockState)) {
            throw new LoginExcetion("账号已锁定");
        }

        String allowIps = login.getAllowIps();

        if (allowIps != null && allowIps != "") {
            if (!allowIps.contains(ip)) {
                throw new LoginExcetion("ip受限");
            }
        }

        return login;
    }


}
