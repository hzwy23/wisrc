package com.wisrc.rules.webapp.dao;

import com.wisrc.rules.webapp.entity.WarehouseProductRelationEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface WarehouseProductRelationDao {
    @Insert("INSERT INTO warehouse_product_relation(uuid, rule_id, sku_id) VALUES(#{uuid}, #{ruleId}, #{skuId})")
    void saveWarehouseProductRelation(WarehouseProductRelationEntity warehouseProductRelation) throws Exception;

    @Select("SELECT sku_id FROM warehouse_product_relation WHERE rule_id = #{ruleId} ")
    List<String> getProdRelByRuleId(@Param("ruleId") String ruleId) throws Exception;

    @Delete("DELETE FROM warehouse_product_relation WHERE rule_id = #{ruleId} AND sku_id = #{skuId}")
    void deleteWarehouseProductRelation(@Param("ruleId") String ruleId, @Param("skuId") String skuId) throws Exception;
}
