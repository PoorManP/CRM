package com.liujie.crm.workbench.service.impl;

import com.liujie.crm.utils.DateTimeUtil;
import com.liujie.crm.utils.UUIDUtil;
import com.liujie.crm.vo.PaginationVo;
import com.liujie.crm.workbench.dao.*;
import com.liujie.crm.workbench.domain.*;
import com.liujie.crm.workbench.service.ClueService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
public class ClueServiceImpl implements ClueService {
    @Resource
    private ClueDao clueDao;

    @Resource
    private ClueActivityRelationDao clueActivityRelationDao;

    @Resource
    private ClueRemarkDao clueRemarkDao;

    @Resource
    private CustomerDao customerDao;

    @Resource
    private CustomerRemarkDao customerRemarkDao;

//    联系人相当的表

    @Resource
    private ContactsDao contactsDao;
    @Resource
    private ContactsRemarkDao contactsRemarkDao;

    @Resource
    private ContactsActivityRelationDao contactsActivityRelationDao;

//    交易相关表

    @Resource
    private TranDao tranDao;
    @Resource
    private TranHistoryDao tranHistoryDao;
    @Override
    public Map<String, Boolean> associate(ClueActivityRelation clue) {
        int count = clueActivityRelationDao.associate(clue);
        Map<String, Boolean> map = new HashMap<>();
        if (count == 1) {
            map.put("success", true);
        } else {
            map.put("success", false);
        }

        return map;
    }

    @Transactional
    @Override
    public boolean convert(Tran t, String clueId, String createBy) {
        String sysTime = DateTimeUtil.getSysTime();

        boolean flag = true;
//        1)通过id查询线索详细信息
        Clue clue = clueDao.queryById(clueId);
//        2)通过线索对象提供的客户信息 当该客户不存在时，新建客户， 根据公司名称精确匹配 判断客户是否存在
        String company = clue.getCompany();
        Customer customer = customerDao.getCustomerByName(company);

//        customer为空没有这个用户新建一个
        if(customer==null){
            customer = new Customer();
            customer.setAddress(clue.getAddress());
            customer.setId(UUIDUtil.getUUID());
            customer.setContactSummary(clue.getContactSummary());
            customer.setCreateBy(createBy);
            customer.setCreateTime(DateTimeUtil.getSysTime());
            customer.setDescription(clue.getDescription());
            customer.setName(company);
            customer.setNextContactTime(clue.getNextContactTime());
            customer.setOwner(clue.getOwner());
            customer.setWebsite(clue.getWebsite());
            customer.setPhone(clue.getPhone());

            int count = customerDao.save(customer);
            if(count!=1){
                flag = false;
            }

        }
//        通过线索对象撮提取联系人信息
        Contacts con = new Contacts();
        con.setId(UUIDUtil.getUUID());
        con.setAddress(clue.getAddress());
        con.setAppellation(clue.getAppellation());
        con.setContactSummary(clue.getContactSummary());
        con.setCreateBy(createBy);
        con.setCreateTime(DateTimeUtil.getSysTime());
        con.setCustomerId(customer.getId());
        con.setDescription(clue.getDescription());
        con.setFullname(clue.getFullname());
        con.setSource(clue.getSource());
        con.setMphone(clue.getMphone());
        con.setEmail(clue.getEmail());
        con.setJob(clue.getJob());
        con.setNextContactTime(clue.getNextContactTime());
        con.setOwner(clue.getOwner());
//        添加联系人
        int count1 = contactsDao.save(con);
        if(count1==0){
            flag = false;
        }
//        con.getId 联系人id
//        4）线索备注转换到联系人备注和客户备注
        List<ClueRemark> clueRemarks = clueRemarkDao.queryListById(clue.getId());

        for (ClueRemark clueRemark:clueRemarks) {
//            拿到备注信息
            String noteContent = clueRemark.getNoteContent();
//            为客户添加备注信息
            CustomerRemark customerRemark = new CustomerRemark();
            customerRemark.setNoteContent(noteContent);
            customerRemark.setId(UUIDUtil.getUUID());
            customerRemark.setCreateBy(createBy);
            customerRemark.setCreateTime(DateTimeUtil.getSysTime());
            customerRemark.setEditFlag("0");
            customerRemark.setCustomerId(customer.getId());
            int cusCount = customerRemarkDao.save(customerRemark);
            if(cusCount!=1){
                flag = false;
            }
//            为联系人添加信息

            ContactsRemark contactsRemark = new ContactsRemark();
            contactsRemark.setNoteContent(noteContent);
            contactsRemark.setId(UUIDUtil.getUUID());
            contactsRemark.setCreateBy(createBy);
            contactsRemark.setCreateTime(DateTimeUtil.getSysTime());
            contactsRemark.setEditFlag("0");
            contactsRemark.setContactsId(con.getId());
            int conCount = contactsRemarkDao.save(contactsRemark);
            if(conCount!=1){
                flag = false;
            }

        }

//  线索和市场活动关系 转换到联系和市场活动关系
        List<ClueActivityRelation> clueActitvityR = clueActivityRelationDao.queryById(clue.getId());

//        和联系人做关联

        for (ClueActivityRelation r:clueActitvityR) {
            ContactsActivityRelation contactsActivityRelation = new ContactsActivityRelation();
            contactsActivityRelation.setId(UUIDUtil.getUUID());
            contactsActivityRelation.setActivityId(r.getActivityId());
            contactsActivityRelation.setContactsId(con.getId());
            int count = contactsActivityRelationDao.save(contactsActivityRelation);
            if(count!=1){
                flag = false;
            }
        }

//        如果有创建交易的需要求  创建交易
        if(t!=null){
//            创建交易
//            添加一些其它数据  可选项
            t.setSource(clue.getSource());
            t.setCustomerId(customer.getId());
            t.setContactsId(con.getId());
            t.setDescription(clue.getDescription());
            t.setContactSummary(clue.getContactSummary());
            t.setNextContactTime(clue.getNextContactTime());
            t.setOwner(clue.getOwner());
            t.setName(clue.getFullname());

            int count = tranDao.save(t);
            if(count!=1){
                flag = false;
            }

            //        添加交易历史
            /*
            * id
            stage
            money
            expectedDate
            createTime
            createBy
            tranId
            */
            TranHistory tranHistory = new TranHistory();
            tranHistory.setId(UUIDUtil.getUUID());
            tranHistory.setCreateBy(createBy);
            tranHistory.setCreateTime(DateTimeUtil.getSysTime());
            tranHistory.setMoney(t.getMoney());
            tranHistory.setTranId(t.getId());
            tranHistory.setStage(t.getStage());
            tranHistory.setExpectedDate(t.getExpectedDate());

            int count2 = 0;
            count2 = tranHistoryDao.save(tranHistory);
            if(count2!=1){
                flag = false;
            }


        }

//        删除线索备注
/*        int delCount = clueRemarkDao.delRemark(clue.getId());*/
        for (ClueRemark clueRemark:clueRemarks) {
            int count = clueRemarkDao.delete(clueRemark);
            if(count!=1){
                flag = false;
            }
        }

//        9删除线索和市场活动的关联关系

        for (ClueActivityRelation c:clueActitvityR) {
            int count = clueActivityRelationDao.delete(c);
            if(count!=1){
                flag = false;
            }
        }

//        10）删除线索
        int count3 = clueDao.delete(clue);
        if(count3!=1){
            flag = false;
        }




        return true;
    }

    @Override
    public boolean delList(String[] id) {
        int count = clueDao.delList(id);
        if (count != 0) {
            return true;
        }
        return false;
    }

    @Override
    public Clue getClueById(String id) {
        return clueDao.getClueById(id);
    }

    @Override
    public PaginationVo getListByCondition(ClueCondition condition) {
        List<Clue> list = clueDao.getList(condition);
        int count = clueDao.getCount(condition);

//        for (Clue clue : list) {
//            System.out.println("/????????????"+clue);
//        }
        PaginationVo vo = new PaginationVo();
        vo.setDataList(list);
        vo.setTotal(count);
        return vo;
    }

    @Override
    public boolean save(Clue clue) {

        if (clueDao.saveClue(clue) != 1) {
            return false;
        }
        return true;

    }
}
