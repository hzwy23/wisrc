package com.wisrc.rules.webapp.dao;

import com.wisrc.rules.webapp.entity.LogisticsShopRelEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LogisticsShopRelDao {
    @Insert("INSERT INTO logistics_shop_relation(uuid, rule_id, shop_id) VALUES(#{uuid}, #{ruleId}, #{shopId})")
    void saveSaleLogisticsShopRel(LogisticsShopRelEntity logisticsShopRelEntity) throws Exception;

    @Select("SELECT shop_id FROM logistics_shop_relation WHERE rule_id = #{ruleId}")
    List<String> getShopByRuleId(@Param("ruleId") String ruleId) throws Exception;

    @Delete("DELETE FROM logistics_shop_relation WHERE rule_id = #{ruleId} AND shop_id = #{shopId}")
    void deleteSaleLogisticsShopRel(LogisticsShopRelEntity logisticsShopRelEntity) throws Exception;
}
