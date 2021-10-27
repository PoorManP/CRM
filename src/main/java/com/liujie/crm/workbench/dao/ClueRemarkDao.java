package com.liujie.crm.workbench.dao;

import com.liujie.crm.workbench.domain.ClueRemark;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface ClueRemarkDao {

    List<ClueRemark> queryListById(String id);


    int delRemark(String clueId);

    int delete(ClueRemark clueRemark);
}
