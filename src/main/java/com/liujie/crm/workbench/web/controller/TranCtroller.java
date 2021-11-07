package com.liujie.crm.workbench.web.controller;

import com.liujie.crm.settings.dao.UserDao;
import com.liujie.crm.settings.domain.User;
import com.liujie.crm.settings.service.UserService;
import com.liujie.crm.utils.DateTimeUtil;
import com.liujie.crm.utils.UUIDUtil;
import com.liujie.crm.workbench.domain.Activity;
import com.liujie.crm.workbench.domain.Contacts;
import com.liujie.crm.workbench.domain.Tran;
import com.liujie.crm.workbench.domain.TranHistory;
import com.liujie.crm.workbench.service.ActivityService;
import com.liujie.crm.workbench.service.CustomerService;
import com.liujie.crm.workbench.service.TranactionService;
import javafx.scene.chart.ValueAxis;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.omg.CORBA.TRANSACTION_MODE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.swing.*;
import java.util.List;
import java.util.Map;

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
    public List<String> getCustomerList(String name) {

        List<String> list = customerService.getCustomerNames(name);

        return list;
    }

    @RequestMapping("/save.do")
    public ModelAndView save(Tran tran, HttpServletRequest request) {

        System.out.println("s=========================================.do");

        tran.setId(UUIDUtil.getUUID());

        User user = (User) (request.getSession().getAttribute("user"));
        tran.setCreateBy(user.getName());
        tran.setCreateTime(DateTimeUtil.getSysTime());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/workbench/transaction/index.jsp");
//        此时的contactid是名称

        System.out.println(tran.getMoney() + tran.getCreateBy() + tran.getStage());
        boolean flag = tranactionService.save(tran);


        return modelAndView;

    }

    @RequestMapping(value = "/detail.do")
    public ModelAndView detail(String id, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        Tran tran = tranactionService.queryById(id);

        modelAndView.addObject("tran", tran);

        Map<String, String> map = (Map<String, String>) request.getSession().getServletContext().getAttribute("stagePossibility");

        String possibility = map.get(tran.getStage());
        modelAndView.addObject("possibility", possibility);


        modelAndView.setViewName("forward:/workbench/transaction/detail.jsp");


        return modelAndView;

    }

    @RequestMapping(value = "/showHistoryList.do")
    @ResponseBody
    public List<TranHistory> showHistryList(String id, HttpServletRequest request){

        List<TranHistory> list = tranactionService.queryHistoryById(id);
        Map<String, String> map = (Map<String, String>) request.getSession().getServletContext().getAttribute("stagePossibility");

        for (TranHistory t : list) {
            String possibility = map.get(t.getStage());
            t.setPossibility(possibility);
        }

        return list;
    }


}
