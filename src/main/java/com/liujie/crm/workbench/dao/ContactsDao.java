package com.liujie.crm.workbench.dao;

import com.liujie.crm.workbench.domain.Contacts;

import java.util.List;

public interface ContactsDao {

    int save(Contacts con);

    List<Contacts> getContactList(String name);
}
