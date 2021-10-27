package com.liujie.crm.settings.service;

import com.liujie.crm.exception.LoginExcetion;
import com.liujie.crm.settings.domain.User;
import org.springframework.stereotype.Service;


public interface UserService {

    User login(String loginAct, String loginPwd, String ip) throws LoginExcetion;
}
