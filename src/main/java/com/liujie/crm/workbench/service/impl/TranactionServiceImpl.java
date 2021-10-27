package com.liujie.crm.workbench.service.impl;

import com.liujie.crm.settings.domain.User;
import com.liujie.crm.workbench.dao.ActivityDao;
import com.liujie.crm.workbench.dao.ContactsDao;
import com.liujie.crm.workbench.dao.TranDao;
import com.liujie.crm.workbench.domain.Contacts;
import com.liujie.crm.workbench.service.ActivityService;
import com.liujie.crm.workbench.service.ClueService;
import com.liujie.crm.workbench.service.TranactionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TranactionServiceImpl implements TranactionService {
    @Resource
    private TranDao dao;

    @Resource
    private ContactsDao contactsDao;
    @Resource
    private ActivityDao activityDao;

    @Override
    public List<User> getUserNames() {
        List<User> userNames = activityDao.getUserNames();
        return userNames;
    }

    @Override
    public List<Contacts> getContactList(String name) {
        return contactsDao.getContactList(name);
    }
}
