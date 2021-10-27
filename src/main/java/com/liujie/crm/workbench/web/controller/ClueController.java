package com.liujie.crm.workbench.web.controller;

import com.liujie.crm.settings.domain.User;
import com.liujie.crm.utils.DateTimeUtil;
import com.liujie.crm.utils.UUIDUtil;
import com.liujie.crm.vo.PaginationVo;
import com.liujie.crm.workbench.domain.*;
import com.liujie.crm.workbench.service.ActivityService;
import com.liujie.crm.workbench.service.ClueService;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/clue")
public class ClueController {


    @Resource
    ClueService clueService;

    @Autowired
    ActivityService activityService;

    @RequestMapping("/save.do")
    @ResponseBody
    public Map<String, Object> save(Clue clue, HttpServletRequest request) {


        clue.setCreateBy(((User) request.getSession().getAttribute("user")).getName());

        clue.setCreateTime(DateTimeUtil.getSysTime());
        clue.setId(UUIDUtil.getUUID());

        boolean save = clueService.save(clue);
        Map<String, Object> map = new HashMap<>();
        map.put("success", save);
        return map;
    }


    @RequestMapping("/getClueList.do")
    @ResponseBody
    public PaginationVo getListClue(ClueCondition condition) {

        int skipCount = (condition.getPageNo() - 1) * condition.getPageSize();
        condition.setSkipCount(Integer.valueOf(skipCount));


        System.out.println("---------------------------------->>>>" + condition);
        PaginationVo paginationVo = clueService.getListByCondition(condition);


        System.out.println(paginationVo);
        return paginationVo;
    }


    //删除列表
    @ResponseBody
    @RequestMapping("/del.do")
    public Map del(String[] id) {

        Map map = new HashMap();

        for (String str : id) {
            System.out.println(id);

        }
        boolean b = clueService.delList(id);
        map.put("success", b);
        return map;
    }


    @RequestMapping("/detail.do")
    public ModelAndView detail(String id) {

        ModelAndView mv = new ModelAndView();
        Clue clue = clueService.getClueById(id);
        mv.addObject("clue", clue);
        mv.setViewName("forward:/workbench/clue/detail.jsp");  //无视视力解析器
        return mv;
    }

    //
    @ResponseBody
    @RequestMapping(value = "/edit.do")
    public Clue editInfo(String id) {
        System.out.println(id);
        return clueService.getClueById(id);
    }


    @RequestMapping(value = "/showActiviList.do")
    @ResponseBody
    public List<Activity> showActiviList(String clueId) {
        List<Activity> ac = activityService.showActiviList(clueId);

        return ac;

    }

    @ResponseBody
    @RequestMapping(value = "/associate.do")
    public Map<String, Boolean> associate(ClueActivityRelation clue) {
        clue.setId(UUIDUtil.getUUID());
        Map<String, Boolean> map = clueService.associate(clue);

        return map;
    }


    @RequestMapping("/convert.do")
    public String convert(Convert convert, HttpServletRequest request) {

        System.out.println(request.getParameter("clueId")+"==============================>>>>>>");

        boolean flag = false;
        User user = (User)request.getSession().getAttribute("user");
/*\
        flag;
        clueId;
        money;
        tradeName;
        expectedDat
                staga;
        activityId;*/
        Tran t = null;
        if("a".equals(convert.getFlag())){
//            创建交易
            t = new Tran();
            String id = UUIDUtil.getUUID();

            t.setActivityId(convert.getActivityId());
            t.setCreateBy(user.getName());
            t.setCreateTime(DateTimeUtil.getSysTime());
            t.setId(id);
            t.setStage(convert.getStaga());
            t.setMoney(convert.getMoney());
            t.setName(convert.getTradeName());
            t.setExpectedDate(convert.getExpectedDate());
            flag = clueService.convert(t,convert.getClueId(),user.getName());

        }else{
            flag = clueService.convert(t,convert.getClueId(),user.getName());
        }

        if(flag){
//            /clue/convert.do/workbench/clue/index.jsp
            return "redirect:" +"/workbench/clue/index.jsp";
        }

        return "";
//        flag  clueId money  tradeName  expectedDate  staga  activityId
    }


}
