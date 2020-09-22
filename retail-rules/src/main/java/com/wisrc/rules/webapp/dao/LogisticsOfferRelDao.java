package com.wisrc.rules.webapp.dao;

import com.wisrc.rules.webapp.entity.LogisticsOfferRelEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LogisticsOfferRelDao {
    @Insert("INSERT INTO logistics_offer_relation(uuid, rule_id, offer_id) VALUES(#{uuid}, #{ruleId}, #{offerId})")
    void saveSaleLogisticsOfferRel(LogisticsOfferRelEntity logisticsOfferRelEntity) throws Exception;

    @Select("SELECT offer_id FROM logistics_offer_relation WHERE rule_id = #{ruleId}")
    List<String> getOfferIdByRuleId(@Param("ruleId") String ruleId) throws Exception;

    @Delete("DELETE FROM logistics_offer_relation WHERE rule_id = #{ruleId} AND offer_id = #{offerId}")
    void deleteSaleLogisticsOfferRel(LogisticsOfferRelEntity logisticsOfferRelEntity) throws Exception;
}
