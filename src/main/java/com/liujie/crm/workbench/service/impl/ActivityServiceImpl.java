package com.liujie.crm.workbench.service.impl;

import com.liujie.crm.settings.domain.User;
import com.liujie.crm.vo.PaginationVo;
import com.liujie.crm.workbench.dao.ActivityDao;
import com.liujie.crm.workbench.dao.ActivityRemarkDao;
import com.liujie.crm.workbench.dao.DicValueDao;
import com.liujie.crm.workbench.domain.Activity;
import com.liujie.crm.workbench.domain.ActivityRemark;
import com.liujie.crm.workbench.domain.DicValue;
import com.liujie.crm.workbench.service.ActivityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ActivityServiceImpl implements ActivityService {
    @Resource
    ActivityDao activityDao;


    @Resource
    ActivityRemarkDao remarkDao;

    @Resource
    DicValueDao dicValueDao;




    @Override
    public List<DicValue> dicFullApplication() {
        List<DicValue> list = dicValueDao.getList();

        return list;
    }

    @Override
    public List<User> getUserNames() {
        List<User> userNames = activityDao.getUserNames();
        if (userNames != null) {
            return userNames;
        }


        return null;
    }

    @Override
    public boolean saveActivityInfo(Activity activity) {
        if (activityDao.saveActivityInfo(activity) == 1) {
            return true;
        }
        return false;

    }

    @Override
    public PaginationVo pageList(Map<String, Object> map) {
//        取得totalb

//        System.out.println(" 进入实现类");
        int totalByCondition = activityDao.getTotalByCondition(map);


        //取得datalist

        List<Activity> listByCondition = activityDao.getListByCondition(map);

        PaginationVo vo = new PaginationVo();
        vo.setDataList(listByCondition);
        vo.setTotal(totalByCondition);
        return vo;
    }

    @Override
    public List<Activity> activityByName(String aname) {
        return activityDao.activityByName(aname);
    }

    @Override
    public boolean deleteAc(String[] id) {

        boolean flag = true;

        //获取要要删除的条数
        for (int i = 0; i < id.length; i++) {
            int count = remarkDao.deleteByAids(id[i]);

            //删除
            int delete = remarkDao.delete(id[i]);

            if (count == delete) {
                //删除市场
                int delete1 = activityDao.delete(id[i]);
                if (delete1 == 1) {
                    return flag;
                } else {
                    return false;
                }
            }else{
                return false;
            }
        }



        return !flag;
    }

    @Override
    public Activity dataById(String id) {
        return activityDao.dataById(id);
    }

    @Override
    public Map<String, Object> updateActivity(Activity activity) {

        Map<String, Object> map = new HashMap<>();
        int count = activityDao.updateActivity(activity);
        if (count == 1) {
            map.put("success", true);
            return map;
        } else {
            map.put("success", false);
        }

        return map;
    }

    @Override
    public Activity detail(String id) {
        Activity activity = activityDao.detailByid(id);

        return activity;
    }

    @Override
    public List<Activity> actitvi(String condition,String clueId) {
        List<Activity> list = activityDao.activity(condition,clueId);
        if (list != null) {
            return list;
        }

        return null;
    }

    @Override
    public Map<String, Boolean> delBund(String id) {
        int count = activityDao.delBund(id);
        Map<String, Boolean> map = new HashMap<>();
        if (count == 1) {
            map.put("success", true);
        } else {
            map.put("success", false);
        }

        return map;
    }

    @Override
    public List<Activity> showActiviList(String clueId) {
        List<Activity> list = activityDao.showActiviList(clueId);
        if (list != null) {
            return list;
        }
        return null;
    }

    @Override
    public List<ActivityRemark> remarkListByAid(String id) {
        List<ActivityRemark> activityRemarks = remarkDao.remarkListById(id);

        if (activityRemarks != null) {
            return activityRemarks;
        } else {
            return null;
        }
    }

    @Override
    public Map<String, Object> removeRemark(String id) {
        int count = remarkDao.removeById(id);

        Map<String, Object> map = new HashMap<>();
        if (count == 1) {
            map.put("success", true);
        } else {
            map.put("success", false);
        }

        return map;
    }

    @Override
    public Map<String, Object> saveRemark(ActivityRemark remark) {
        int count = remarkDao.saveRemark(remark);
        Map<String, Object> map = new HashMap<>();
        if (count == 1) {
            ActivityRemark data = remarkDao.remarkByid(remark.getId());
            map.put("success", true);
            map.put("ac", data);
            return map;
        }else{
            map.put("success", false);
        }
        return map;
    }

    @Override
    public Map<String, Object> updateRemark(String id, String noteContent) {
        int count = remarkDao.updateByid(id, noteContent);
        Map<String, Object> map = new HashMap<>();
        if (count == 1) {

            map.put("success", true);
            return map;
        } else {

            map.put("success", false);
            return map;
        }

    }
}
