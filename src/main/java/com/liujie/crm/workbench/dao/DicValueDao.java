package com.liujie.crm.workbench.dao;

import com.liujie.crm.workbench.domain.DicValue;
import org.springframework.stereotype.Service;

import java.util.List;

public interface DicValueDao {
    public List<DicValue> getList();

    List<DicValue> getListByCode(String code);
}
