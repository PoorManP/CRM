package com.liujie.crm.workbench.dao;

import com.liujie.crm.settings.domain.User;
import com.liujie.crm.workbench.domain.Activity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ActivityDao {
    int getTotalByCondition(Map<String, Object> map);

    List<User> getUserNames();

    int saveActivityInfo(Activity activity);

    void getDataList();

    List<Activity> getListByCondition(Map<String, Object> map);

    int delete(String id);

    Activity dataById(String id);

    int updateActivity(Activity activity);

    Activity detailByid(String id);

    List<Activity> showActiviList(String clueId);

    int delBund(String id);

    List<Activity> activity(@Param("condition") String condition, @Param("clueId") String clueId);

    List<Activity> activityByName(String aname);
}
