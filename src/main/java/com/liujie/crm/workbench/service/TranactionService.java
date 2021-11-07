package com.liujie.crm.workbench.service;

import com.liujie.crm.settings.domain.User;
import com.liujie.crm.workbench.domain.Contacts;
import com.liujie.crm.workbench.domain.Tran;

import java.util.List;

public interface TranactionService {
    List<User> getUserNames();

    List<Contacts> getContactList(String name);

    boolean save(Tran tran);
}
