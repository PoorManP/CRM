package com.liujie.crm.workbench.web.controller;

import com.liujie.crm.settings.dao.UserDao;
import com.liujie.crm.settings.domain.User;
import com.liujie.crm.settings.service.UserService;
import com.liujie.crm.workbench.domain.Activity;
import com.liujie.crm.workbench.domain.Contacts;
import com.liujie.crm.workbench.service.ActivityService;
import com.liujie.crm.workbench.service.CustomerService;
import com.liujie.crm.workbench.service.TranactionService;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/tran")
public class TranCtroller {
    @Resource
    private TranactionService tranactionService;

    @Resource
    private CustomerService customerService;
    @Autowired
    private ActivityService activityService;

    @RequestMapping("/getUserList.do")
    @ResponseBody
    public List<User> getUserList() {
        List<User> list = tranactionService.getUserNames();
        return list;
    }

    @RequestMapping("/getActitivtyList.do")
    public @ResponseBody
    List<Activity> getActivityList(String name) {
        return activityService.activityByName(name);
    }

    @RequestMapping(value = "/getContactList.do")
    public @ResponseBody
    List<Contacts> getContactList(String name) {
        List<Contacts> list = tranactionService.getContactList(name);

        return list;
    }

    @ResponseBody
    @RequestMapping("/getCustomerList.do")
    public List<String> getCustomerList(String name){

        List<String> list = customerService.getCustomerNames(name);

        return list;
    }


}
