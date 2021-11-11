package com.liujie.crm.workbench.service;

import com.liujie.crm.settings.domain.User;
import com.liujie.crm.workbench.domain.Contacts;
import com.liujie.crm.workbench.domain.Tran;
import com.liujie.crm.workbench.domain.TranHistory;

import java.util.List;
import java.util.Map;

public interface TranactionService {
    List<User> getUserNames();

    List<Contacts> getContactList(String name);

    boolean save(Tran tran);

    Tran queryById(String id);

    List<TranHistory> queryHistoryById(String tranId);

    boolean changeStage(Tran tran);

    Map<String, Object> getChars();
}
