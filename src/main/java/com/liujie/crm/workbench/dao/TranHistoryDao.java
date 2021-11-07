package com.liujie.crm.workbench.dao;


import com.liujie.crm.workbench.domain.TranHistory;

import java.util.List;

public interface TranHistoryDao {

    int save(TranHistory tranHistory);

    List<TranHistory> queryById(String tranId);
}
