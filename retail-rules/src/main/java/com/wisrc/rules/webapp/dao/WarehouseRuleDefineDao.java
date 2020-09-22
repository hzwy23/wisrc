package com.wisrc.rules.webapp.dao;

import com.wisrc.rules.webapp.dao.sql.WarehouseRuleDefineSql;
import com.wisrc.rules.webapp.entity.GetWarehouseRule;
import com.wisrc.rules.webapp.entity.WarehouseRuleDefineEntity;
import com.wisrc.rules.webapp.entity.WarehouseRulePageEntity;
import com.wisrc.rules.webapp.query.WarehouseRuleDefineQuery;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface WarehouseRuleDefineDao {
    @Insert("INSERT INTO warehouse_rule_define(rule_id, rule_name, priority_number, warehouse_id, remark, status_cd, start_date, end_date, modify_user, modify_time, create_user, create_time) " +
            "VALUES(#{ruleId}, #{ruleName}, #{priorityNumber}, #{warehouseId}, #{remark}, #{statusCd}, #{startDate}, #{endDate}, #{modifyUser}, #{modifyTime}, #{createUser}, #{createTime})")
    void saveWarehouseRule(WarehouseRuleDefineEntity warehouseRuleDefine) throws Exception;

    @SelectProvider(type = WarehouseRuleDefineSql.class, method = "warehouseRulePage")
    List<WarehouseRulePageEntity> warehouseRulePage(WarehouseRuleDefineQuery warehouseRuleDefineQuery) throws Exception;

    @Update("UPDATE warehouse_rule_define SET rule_name = #{ruleName}, priority_number = #{priorityNumber}, warehouse_id = #{warehouseId}, remark = #{remark}, start_date = #{startDate}, " +
            " end_date = #{endDate}, modify_user = #{modifyUser}, modify_time = #{modifyTime} WHERE rule_id = #{ruleId}")
    void editWarehouseRule(WarehouseRuleDefineEntity warehouseRuleDefine) throws Exception;

    @Select("SELECT rule_name, priority_number, warehouse_id, remark, start_date, end_date FROM warehouse_rule_define WHERE rule_id = #{ruleId}")
    GetWarehouseRule getWarehouseRule(@Param("ruleId") String ruleId) throws Exception;

    @Select("SELECT status_cd FROM warehouse_rule_define WHERE rule_id = #{ruleId}")
    Integer getStatus(@Param("ruleId") String ruleId) throws Exception;

    @Update("UPDATE warehouse_rule_define SET start_date = #{startDate}, end_date = #{endDate}, status_cd = #{statusCd} WHERE rule_id = #{ruleId}")
    void ruleSwitch(WarehouseRuleDefineEntity warehouseRule) throws Exception;
}
