package com.liujie.crm.settings.web.controller;

import com.liujie.crm.exception.LoginExcetion;
import com.liujie.crm.settings.domain.User;
import com.liujie.crm.settings.service.UserService;
import com.liujie.crm.utils.MD5Util;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {


/*    @RequestMapping("/settings/user/login")
    public ModelAndView login() {


    }*/

    @Resource
    UserService userService;


    @RequestMapping(value = "/login.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> login(HttpServletResponse response, HttpServletRequest request) throws LoginExcetion, IOException {

//        System.out.println("登录操作");

        File file = new File("C:\\Users\\Animal\\Desktop\\" + request.getRemoteAddr());




        String loginAct = request.getParameter("loginAct");
        String loginPwd = request.getParameter("loginPwd");
        String ip = request.getRemoteAddr(); //获取ip地址

        loginPwd = MD5Util.getMD5(loginPwd); //将明文密码转换成md5格式

        User user = userService.login(loginAct, loginPwd, ip);

        Map<String, Object> map = new HashMap<>();
        //到这证明没抛异常
        map.put("success", true);


        request.getSession().setAttribute("user", user);

        if(!file.exists()){
            file.mkdir();
        }
        String absolutePath = file.getAbsolutePath();
        FileOutputStream out = new FileOutputStream(absolutePath+"//" + ((User) request.getSession().getAttribute("user")).getName(), true);
        User userA = (User) request.getSession().getAttribute("user");


        out.write(user.getName().getBytes());
        out.write(new Date().toString().getBytes());
        out.write("login".getBytes());

        File img = new File(absolutePath + "//img");
        if(!img.exists()){
            img.mkdir();

            FileImageInputStream in = new FileImageInputStream(new File("C:\\Users\\Animal\\Desktop\\2.jpg"));

            FileImageOutputStream imgout = new FileImageOutputStream(new File((img.getAbsolutePath()+"//info.png")));

            byte[] bytes = new byte[1024*1024];

            int count=0;

            while((count=in.read(bytes))!=-1){
                imgout.write(bytes, 0, count);
            }

            in.close();
            out.close();
            imgout.close();

        }

        return map;

    }



}
