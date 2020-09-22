package com.wisrc.rules.webapp.dao;

import com.wisrc.rules.webapp.entity.LogisticsLabelRelEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LogisticsLabelRelDao {
    @Insert("INSERT INTO logistics_label_relation(uuid, rule_id, label_cd) VALUES(#{uuid}, #{ruleId}, #{labelCd})")
    void saveSaleLogisticsLabelRel(LogisticsLabelRelEntity logisticsLabelRelEntity) throws Exception;

    @Select("SELECT label_cd FROM logistics_label_relation WHERE rule_id = #{ruleId}")
    List<Integer> getLabelByRuleId(@Param("ruleId") String ruleId) throws Exception;

    @Delete("DELETE FROM logistics_label_relation WHERE rule_id = #{ruleId} AND label_cd = #{labelCd}")
    void deleteSaleLogisticsLabelRel(LogisticsLabelRelEntity logisticsLabelRelEntity) throws Exception;
}
