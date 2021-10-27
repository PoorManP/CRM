package com.liujie.crm.workbench.web.controller;

import com.liujie.crm.settings.domain.User;
import com.liujie.crm.utils.DateTimeUtil;
import com.liujie.crm.utils.UUIDUtil;
import com.liujie.crm.vo.PaginationVo;
import com.liujie.crm.workbench.domain.Customer;
import com.liujie.crm.workbench.domain.CustomerCondition;
import com.liujie.crm.workbench.service.ActivityService;
import com.liujie.crm.workbench.service.CustomerService;
import javafx.scene.chart.ValueAxis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    @Autowired
    private ActivityService activityService;

    @RequestMapping("/getCustomerList.do")
    public @ResponseBody
    PaginationVo getCustomerList(CustomerCondition condition) {

        System.out.println(condition + "===================>>>>>>>");

        int skipCount = (condition.getPageNo() - 1) * condition.getPageSize();

        condition.setSkipCount(skipCount);

        PaginationVo<Customer> paginationVo = customerService.getPageList(condition);

        return paginationVo;


    }

    @RequestMapping(value = "/getUserList.do")
    public @ResponseBody
    List<User> getUserList() {
        return activityService.getUserNames();
    }

    @RequestMapping(value = "save.do")
    @ResponseBody
    public Map<String, Boolean> save(Customer customer, HttpServletRequest request) {

        customer.setId(UUIDUtil.getUUID());
        customer.setCreateTime(DateTimeUtil.getSysTime());
        User user = (User) (request.getSession().getAttribute("user"));
        customer.setCreateBy(user.getName());

        Map<String, Boolean> map = customerService.save(customer);

        return map;
    }

    @RequestMapping(value = "/modify.do")
    @ResponseBody
    public Map<String, Boolean> modify(Customer customer, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");


        customer.setEditBy(DateTimeUtil.getSysTime());
        customer.setEditBy(user.getName());

        return customerService.edit(customer);
    }

}
