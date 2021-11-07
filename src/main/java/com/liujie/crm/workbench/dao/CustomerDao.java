package com.liujie.crm.workbench.dao;

import com.liujie.crm.workbench.domain.Customer;
import com.liujie.crm.workbench.domain.CustomerCondition;

import java.util.List;

public interface CustomerDao {

    Customer getCustomerByName(String company);

    int save(Customer customer);

    int queryTotal(CustomerCondition condition);

    List<Customer> queryByCondition(CustomerCondition condition);

    int edit(Customer customer);

    List<String> getCustomerNames(String name);

    Customer queryByName(String customerId);
}
