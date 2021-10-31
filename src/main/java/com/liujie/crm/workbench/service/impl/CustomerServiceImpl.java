package com.liujie.crm.workbench.service.impl;

import com.liujie.crm.vo.PaginationVo;
import com.liujie.crm.workbench.dao.CustomerDao;
import com.liujie.crm.workbench.domain.Customer;
import com.liujie.crm.workbench.domain.CustomerCondition;
import com.liujie.crm.workbench.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;
    @Override
    public PaginationVo getPageList(CustomerCondition condition) {

        int total = customerDao.queryTotal(condition);

        List<Customer> list = customerDao.queryByCondition(condition);

        PaginationVo<Customer> paginationVo = new PaginationVo<>();

        paginationVo.setTotal(total);
        paginationVo.setDataList(list);

        return paginationVo;
    }

    @Override
    public Map<String, Boolean> save(Customer customer) {
        int count = customerDao.save(customer);
        Map<String, Boolean> map = new HashMap<>();
        if (count == 1) {
            map.put("success", true);
        } else {
            map.put("success", false);
        }

        return map;
    }

    @Override
    public Map<String, Boolean> edit(Customer customer) {
        int count = customerDao.edit(customer);
        Map<String, Boolean> map = new HashMap<>();
        if (count == 1) {
            map.put("success", true);
        } else {
            map.put("success", false);
        }

        return map;
    }

    @Override
    public List<String> getCustomerNames(String name) {
        List<String> list = customerDao.getCustomerNames(name);

        return list;
    }
}
