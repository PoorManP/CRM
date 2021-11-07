package com.liujie.crm.workbench.service.impl;

import com.liujie.crm.settings.domain.User;
import com.liujie.crm.utils.DateTimeUtil;
import com.liujie.crm.utils.UUIDUtil;
import com.liujie.crm.workbench.dao.*;
import com.liujie.crm.workbench.domain.Contacts;
import com.liujie.crm.workbench.domain.Customer;
import com.liujie.crm.workbench.domain.Tran;
import com.liujie.crm.workbench.domain.TranHistory;
import com.liujie.crm.workbench.service.ActivityService;
import com.liujie.crm.workbench.service.ClueService;
import com.liujie.crm.workbench.service.TranactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TranactionServiceImpl implements TranactionService {
    @Resource
    private TranDao dao;

    @Resource
    private CustomerDao customerDao;
    @Resource
    private ContactsDao contactsDao;
    @Resource
    private ActivityDao activityDao;

    @Resource
    private TranHistoryDao historyDao;

    @Override
    public List<User> getUserNames() {
        List<User> userNames = activityDao.getUserNames();
        return userNames;
    }

    @Override
    public List<Contacts> getContactList(String name) {
        return contactsDao.getContactList(name);
    }


    @Override
    @Transactional
    public boolean save(Tran tran) {


        /*1
        * 第一步根据客户名称  在客户表进行精确查询
        * 如果有这个客户就取去id 没有的话就创建一个客户信息
        * 将新建的id取出封装到tran对象中
        *2
        * tran信息全了
        * 执行添加交易操作
        *
        *3
        * 添加交易历史表
        * */


        boolean flag = true;
//        1
        Customer customer = customerDao.queryByName(tran.getCustomerId());
        if(customer==null){
//            如果没有这个用户的话  则创建这个客户
            customer = new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setCreateTime(DateTimeUtil.getSysTime());
            customer.setName(tran.getCustomerId());
            customer.setCreateBy(tran.getCreateBy());
            customer.setContactSummary(tran.getContactSummary());

            int save = customerDao.save(customer);
            if(save!=1){
                flag = false;
            }
        }
        tran.setCustomerId(customer.getId());

        int save = dao.save(tran);
        if(save!=1){
            flag = false;
        }
        TranHistory tranHistory = new TranHistory();
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setExpectedDate(tran.getExpectedDate());
        tranHistory.setStage(tran.getStage());
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setCreateTime(tran.getCreateTime());
        tranHistory.setCreateBy(tran.getCreateBy());
        tranHistory.setTranId(tran.getId());

        int save1 = historyDao.save(tranHistory);
        if(save1!=1){
            flag = false;
        }


        return flag;
    }
}
