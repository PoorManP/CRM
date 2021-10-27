package com.liujie.crm.workbench.dao;

import com.liujie.crm.workbench.domain.ActivityRemark;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ActivityRemarkDao {
    void select();

    int deleteByAids(String s);

    int delete(String s);

    List<ActivityRemark> remarkListById(String id);

    int removeById(String id);

    int saveRemark(ActivityRemark remark);

    ActivityRemark remarkByid(String id);

    int updateByid(@Param("id") String id, @Param("noteContext") String noteContext);
}
