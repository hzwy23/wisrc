package com.wisrc.rules.webapp.dao;

import com.wisrc.rules.webapp.entity.LogisticsProductRelEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LogisticsProductRelDao {
    @Insert("INSERT INTO logistics_product_relation(uuid, rule_id, sku_id) VALUES(#{uuid}, #{ruleId}, #{skuId})")
    void saveSaleInvoiceLogisticsProductRel(LogisticsProductRelEntity logisticsProductRelEntity) throws Exception;

    @Select("SELECT sku_id FROM logistics_product_relation WHERE rule_id = #{ruleId}")
    List<String> getProductByRuleId(@Param("ruleId") String ruleId) throws Exception;

    @Delete("DELETE FROM logistics_product_relation WHERE rule_id =  #{ruleId} AND sku_id = #{skuId}")
    void deleteSaleInvoiceLogisticsProductRel(LogisticsProductRelEntity logisticsProductRelEntity) throws Exception;
}
