package com.liujie.crm.workbench.service.impl;

import com.liujie.crm.workbench.dao.DicTypeDao;
import com.liujie.crm.workbench.dao.DicValueDao;
import com.liujie.crm.workbench.domain.DicType;
import com.liujie.crm.workbench.domain.DicValue;
import com.liujie.crm.workbench.service.DicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class DicServiceImpl implements DicService {

    @Resource
    DicValueDao dicValueDao;

    @Resource
    DicTypeDao dicTypeDao;
    @Override
    public Map<String, List<DicValue>> getAll() {
        Map<String, List<DicValue>> map = new HashMap<>();

        List<DicType> typeList = dicTypeDao.getTypeList();

        for (DicType dicType : typeList) {
            String code = dicType.getCode();
            List<DicValue> dicList =  dicValueDao.getListByCode(code);
            map.put(code, dicList);
        }

        return map;
    }
}
