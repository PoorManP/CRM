package com.liujie.crm.workbench.service;

import com.liujie.crm.vo.PaginationVo;
import com.liujie.crm.workbench.domain.Clue;
import com.liujie.crm.workbench.domain.ClueActivityRelation;
import com.liujie.crm.workbench.domain.ClueCondition;
import com.liujie.crm.workbench.domain.Tran;

import java.util.Map;

public interface ClueService {
    boolean save(Clue clue);

    PaginationVo getListByCondition(ClueCondition condition);

    boolean delList(String[] id);

    Clue getClueById(String id);

    Map<String, Boolean> associate(ClueActivityRelation clue);

    boolean convert(Tran t, String clueId, String name);
}
