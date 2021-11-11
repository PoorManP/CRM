package com.liujie.crm.workbench.dao;

import com.liujie.crm.workbench.domain.Tran;

import java.util.List;
import java.util.Map;

public interface TranDao {

    int save(Tran t);

    Tran queryById(String id);

    int changStage(Tran tran);

    int getTotal();

    List<Map<String, Object>> getChars();
}
