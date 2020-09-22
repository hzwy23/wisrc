package com.wisrc.rules.webapp.dao;

import com.wisrc.rules.webapp.dao.sql.SaleInvoiceLogisticsRuleSql;
import com.wisrc.rules.webapp.entity.LogisticsInvoiceRuleDefineEntity;
import com.wisrc.rules.webapp.entity.LogisticsRulePageEntity;
import com.wisrc.rules.webapp.query.LogisticsRulePageQuery;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LogisticsInvoiceRuleDao {
    @Insert("INSERT INTO logistics_invoice_rule_define(rule_id, rule_name, offer_id, priority_number, start_date, end_date, remark, status_cd, min_total_amount, max_total_amount, " +
            "total_amount_currency, min_weight, max_weight, modify_user, modify_time, create_user, create_time) VALUES(#{ruleId}, #{ruleName}, #{offerId}, #{priorityNumber}, #{startDate}, #{endDate}, #{remark}, #{statusCd}," +
            "#{minTotalAmount}, #{maxTotalAmount}, #{totalAmountCurrency}, #{minWeight}, #{maxWeight}, #{modifyUser}, #{modifyTime}, #{createUser}, #{createTime})")
    void saveLogisticsRule(LogisticsInvoiceRuleDefineEntity LogisticsInvoiceRuleDefineEntity) throws Exception;

    @SelectProvider(type = SaleInvoiceLogisticsRuleSql.class, method = "logisticsRulePage")
    List<LogisticsRulePageEntity> logisticsRulePage(LogisticsRulePageQuery logisticsRulePageQuery) throws Exception;

    @Select("SELECT rule_name, offer_id, priority_number, start_date, end_date, remark, min_total_amount, max_total_amount, total_amount_currency, min_weight, max_weight " +
            " FROM logistics_invoice_rule_define WHERE rule_id = #{ruleId}")
    LogisticsInvoiceRuleDefineEntity getSaleInvoiceLogisticsRule(@Param("ruleId") String ruleId) throws Exception;

    @Update("UPDATE logistics_invoice_rule_define SET rule_name = #{ruleName}, offer_id = #{offerId}, priority_number = #{priorityNumber}, start_date = #{startDate}, end_date = #{endDate}, " +
            "remark = #{remark}, min_total_amount = #{minTotalAmount}, max_total_amount = #{maxTotalAmount}, total_amount_currency = #{totalAmountCurrency}, min_weight = #{minWeight}, " +
            " max_weight =  #{maxWeight}, modify_user = #{modifyUser}, modify_time = #{modifyTime} WHERE rule_id = #{ruleId}")
    void editLogisticsRule(LogisticsInvoiceRuleDefineEntity LogisticsInvoiceRuleDefineEntity) throws Exception;

    @Update("UPDATE logistics_invoice_rule_define SET status_cd = #{statusCd}, start_date = #{startDate}, end_date = #{endDate} WHERE rule_id = #{ruleId}")
    void logisticsRuleSwitch(LogisticsInvoiceRuleDefineEntity LogisticsInvoiceRuleDefineEntity) throws Exception;

    @Select("SELECT status_cd FROM logistics_invoice_rule_define WHERE rule_id = #{ruleId}")
    Integer getRuleStatus(@Param("ruleId") String ruleId) throws Exception;
}
