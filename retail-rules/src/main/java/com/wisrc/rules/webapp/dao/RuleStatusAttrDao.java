package com.wisrc.rules.webapp.dao;

import com.wisrc.rules.webapp.entity.RuleStatusAttrEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RuleStatusAttrDao {
    @Select("SELECT status_cd, status_desc FROM rule_status_attr ")
    List<RuleStatusAttrEntity> logisticsRuleStatus() throws Exception;
}
