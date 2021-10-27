package com.liujie.crm.handller;

import com.liujie.crm.exception.LoginExcetion;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


//统一异常管理
@ControllerAdvice
public class UserExceptionHandller {

    @ExceptionHandler(LoginExcetion.class)
    @ResponseBody
    public Map loginz(Exception exception) {
        Map<String, Object> map = new HashMap<>();

        map.put("success", false);
        map.put("msg", exception.getMessage());

        System.out.println(exception.getMessage());
        return map;
    }
}
