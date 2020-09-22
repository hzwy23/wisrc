package com.wisrc.rules.webapp.dao;

import com.wisrc.rules.webapp.entity.WarehouseClassifyRelationEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface WarehouseClassifyRelationDao {
    @Insert("INSERT INTO warehouse_classify_relation(uuid, rule_id, classify_cd) VALUES(#{uuid}, #{ruleId}, #{classifyCd})")
    void saveWarehouseClassifyRelation(WarehouseClassifyRelationEntity WarehouseClassifyRelation) throws Exception;

    @Select("SELECT classify_cd FROM warehouse_classify_relation WHERE rule_id = #{ruleId}")
    List<String> getWarehouseClassifyRelByRuleId(@Param("ruleId") String ruleId) throws Exception;

    @Delete("DELETE FROM warehouse_classify_relation WHERE rule_id = #{ruleId} AND classify_cd = #{classifyCd} ")
    void deleteWarehouseClassifyRelation(@Param("ruleId") String ruleId, @Param("classifyCd") String classifyCd) throws Exception;
}
