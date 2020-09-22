package com.wisrc.rules.webapp.dao;

import com.wisrc.rules.webapp.entity.LogisticsWarehouseRelEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LogisticsWarehouseRelDao {
    @Insert("INSERT INTO logistics_warehouse_relation(uuid, rule_id, warehouse_id) VALUES(#{uuid}, #{ruleId} ,#{warehouseId})")
    void saveSaleLogisticsWarehouseRel(LogisticsWarehouseRelEntity logisticsWarehouseRelEntity) throws Exception;

    @Select("SELECT warehouse_id FROM logistics_warehouse_relation WHERE rule_id = #{ruleId}")
    List<String> getWarehouseByRuleId(@Param("ruleId") String ruleId) throws Exception;

    @Delete("DELETE FROM logistics_warehouse_relation WHERE rule_id = #{ruleId} AND warehouse_id = #{warehouseId}")
    void deleteSaleLogisticsWarehouseRel(LogisticsWarehouseRelEntity logisticsWarehouseRelEntity) throws Exception;
}
