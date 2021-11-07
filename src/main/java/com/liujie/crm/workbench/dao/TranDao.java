package com.liujie.crm.workbench.dao;

import com.liujie.crm.workbench.domain.Tran;

public interface TranDao {

    int save(Tran t);

    Tran queryById(String id);
}
