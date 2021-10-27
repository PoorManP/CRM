package com.liujie.crm.workbench.dao;

import com.liujie.crm.workbench.domain.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationDao {

    int associate(ClueActivityRelation clue);

    List<ClueActivityRelation> queryById(String id);

    int delete(ClueActivityRelation c);
}
