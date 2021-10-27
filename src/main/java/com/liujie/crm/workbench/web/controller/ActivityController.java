package com.liujie.crm.workbench.web.controller;


import com.liujie.crm.settings.domain.User;
import com.liujie.crm.utils.DateTimeUtil;
import com.liujie.crm.utils.UUIDUtil;
import com.liujie.crm.vo.PaginationVo;
import com.liujie.crm.workbench.domain.*;
import com.liujie.crm.workbench.service.ActivityService;
import org.omg.PortableInterceptor.ACTIVE;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class ActivityController {


    @Resource
    ActivityService activityService;

    @RequestMapping(value = "/getUserList.do")
    @ResponseBody
    public List<User> getUserList() {

        List<User> userNames = activityService.getUserNames();

        return userNames;
    }

    @RequestMapping(value = "/saveActivity.do")
    @ResponseBody
    public Map<String, Object> saveActivity(Activity activity, HttpServletRequest request) {

        System.out.println(request.getParameter("owner"));
        System.out.println("进入添加市场活动信息");
        String id = UUIDUtil.getUUID();

        String createTime = DateTimeUtil.getSysTime();

        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        activity.setId(id);

        activity.setCreateTime(createTime);
        activity.setCreateBy(createBy);

        Map<String, Object> map = new HashMap<>();


        System.out.println(activity);
        boolean b = activityService.saveActivityInfo(activity);
        map.put("success", b);

        return map;

    }

    @RequestMapping(value = "/pageList.do")
    @ResponseBody
    public PaginationVo pageList(Page page) {

//        System.out.println("list.do");
        /* System.out.println(page);*/
        int skipCount = (page.getPageNo() - 1) * page.getPageSize();

//        System.out.println(skipCount);
        Map<String, Object> map = new HashMap<>();
        map.put("name", page.getName());
        map.put("pageNo", page.getPageNo());
        map.put("pageSize", page.getPageSize());
        map.put("endDate", page.getEndDate());
        map.put("owner", page.getOwner());
        map.put("startDate", page.getStartDate());

        map.put("skipCount", skipCount);

        PaginationVo vo = activityService.pageList(map);

        System.out.println(vo);
        return vo;
    }

    @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public Map delete(String[] id) {
        boolean b = activityService.deleteAc(id);

        Map<String, Object> map = new HashMap<>();
        map.put("success", b);
        return map;
    }

    @RequestMapping(value = "/edit.do")
    @ResponseBody
    public Map edit(String id) {


        List<User> userNames = activityService.getUserNames();
        Activity activity = activityService.dataById(id);

        Map<String, Object> map = new HashMap<>();
        map.put("uList", userNames);
        map.put("a", activity);
//        System.out.println(id);

        return map;
    }

    @RequestMapping(value = "/update.do")
    @ResponseBody
    public Map update(Activity activity) {
        System.out.println(activity);
        Map<String, Object> map = activityService.updateActivity(activity);

        return map;
    }

    @RequestMapping(value = "/detail.do")
    public ModelAndView detail(String id) {

        ModelAndView mv = new ModelAndView();
        Activity activity = activityService.detail(id);

        mv.addObject("data", activity);
        System.out.println(activity + "details");

        mv.setViewName("forward:/workbench/activity/detail.jsp");

        //得到信息 请求转发


        return mv;
    }

    @RequestMapping(value = "/remarkList.do")
    @ResponseBody
    public List<ActivityRemark> remarkList(String id) {
        return activityService.remarkListByAid(id);
    }


    @RequestMapping(value = "/removeRemark.do")
    @ResponseBody
    public Map<String, Object> removeRemark(String id) {
        return activityService.removeRemark(id);
    }

    @RequestMapping(value = "saveRemarkBtn.do")
    @ResponseBody
    public Map saveRemarkBtn(ActivityRemark remark, HttpServletRequest request) {


        String id = UUIDUtil.getUUID();
        String createTime = DateTimeUtil.getSysTime();

        remark.setId(id);
        remark.setCreateTime(createTime);

        String createBy = ((User) request.getSession().getAttribute("user")).getName();

        remark.setCreateBy(createBy);

        String editFlaga = "0";
        remark.setEditFlag(editFlaga);

        Map<String, Object> map = activityService.saveRemark(remark);

        System.out.println(map + " ------------------------》》");

        return map;
    }

    @RequestMapping("/updateRemark.do")
    @ResponseBody
    public Map updateRemark(String id, String noteContext) {
        System.out.println(id + noteContext);
        Map<String, Object> map = activityService.updateRemark(id, noteContext);
        return map;
    }

    @RequestMapping("/getV")
    @ResponseBody
    public List<DicValue> getV() {
        List<DicValue> dicValues = activityService.dicFullApplication();

        return dicValues;
    }

    @ResponseBody
    @RequestMapping(value = "/delBund.do")
    public Map<String, Boolean> delBund(String id) {
        Map<String, Boolean> map = activityService.delBund(id);
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/activity.do")
    public List<Activity> activity(String condition,String clueId) {
        List<Activity> list = activityService.actitvi(condition,clueId);
        return list;
    }

    @RequestMapping(value = "/activityByName.do")
    @ResponseBody
    public List<Activity> activityByName(String aname) {
        List<Activity> list = activityService.activityByName(aname);
        return list;
    }
}
