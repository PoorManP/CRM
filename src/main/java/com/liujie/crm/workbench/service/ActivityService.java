package com.liujie.crm.workbench.service;

import com.liujie.crm.settings.domain.User;
import com.liujie.crm.vo.PaginationVo;
import com.liujie.crm.workbench.domain.Activity;
import com.liujie.crm.workbench.domain.ActivityRemark;
import com.liujie.crm.workbench.domain.DicValue;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.util.List;
import java.util.Map;


public interface ActivityService {


    List<DicValue> dicFullApplication();
    List<User> getUserNames();

    boolean saveActivityInfo(Activity activity);

    PaginationVo pageList(Map<String, Object> map);

    boolean deleteAc(String[] id);

    Activity dataById(String id);

    Map<String, Object> updateActivity(Activity activity);

    Activity detail(String id);

    List<ActivityRemark> remarkListByAid(String id);

    Map<String, Object> removeRemark(String id);

    Map<String, Object> saveRemark(ActivityRemark remark);

    Map<String, Object> updateRemark(String id, String noteContent);

    List<Activity> showActiviList(String clueId);

    Map<String, Boolean> delBund(String id);

//    List<Activity> actitvi(String condition);

    List<Activity> actitvi(String condition, String clueId);

    List<Activity> activityByName(String aname);
}
