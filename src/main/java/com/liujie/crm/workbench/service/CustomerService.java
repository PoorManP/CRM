package com.liujie.crm.workbench.service;

import com.liujie.crm.vo.PaginationVo;
import com.liujie.crm.workbench.domain.Customer;
import com.liujie.crm.workbench.domain.CustomerCondition;

import java.util.List;
import java.util.Map;

public interface CustomerService {

    PaginationVo getPageList(CustomerCondition condition);

    Map<String, Boolean> save(Customer customer);

    Map<String, Boolean> edit(Customer customer);

    List<String> getCustomerNames(String name);
}
