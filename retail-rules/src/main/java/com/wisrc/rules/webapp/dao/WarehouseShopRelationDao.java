package com.wisrc.rules.webapp.dao;

import com.wisrc.rules.webapp.entity.WarehouseShopRelationEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface WarehouseShopRelationDao {
    @Insert("INSERT INTO warehouse_shop_relation(uuid, rule_id, shop_id) VALUES(#{uuid}, #{ruleId}, #{shopId})")
    void saveWarehouseShopRelation(WarehouseShopRelationEntity warehouseShopRelation) throws Exception;

    @Select("SELECT shop_id FROM warehouse_shop_relation WHERE rule_id = #{ruleId} ")
    List<String> getShopRelByRuleId(@Param("ruleId") String ruleId) throws Exception;

    @Delete("DELETE FROM warehouse_shop_relation WHERE rule_id = #{ruleId} AND shop_id = #{shopId} ")
    void deleteWarehouseShopRelation(@Param("ruleId") String ruleId, @Param("shopId") String shopId) throws Exception;
}
