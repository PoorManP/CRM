package com.liujie.crm.workbench.service;

import com.liujie.crm.workbench.domain.DicValue;

import java.util.List;
import java.util.Map;

public interface DicService {
    public Map<String,List<DicValue>> getAll();
}
