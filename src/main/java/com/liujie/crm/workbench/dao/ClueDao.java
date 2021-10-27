package com.liujie.crm.workbench.dao;

import com.liujie.crm.workbench.domain.Clue;
import com.liujie.crm.workbench.domain.ClueActivityRelation;
import com.liujie.crm.workbench.domain.ClueCondition;

import java.util.List;

public interface ClueDao {


    int saveClue(Clue clue);

    List<Clue> getList(ClueCondition condition);

    int getCount(ClueCondition condition);

    int delList(String[] id);

    Clue getClueById(String id);

    Clue getInfoByid(Integer id);

    Clue queryById(String clueId);

    int delete(Clue clue);

//    int associate(ClueActivityRelation clue);
}
