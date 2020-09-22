package com.wisrc.rules.webapp.dao.sql;

import com.wisrc.rules.webapp.query.LogisticsRulePageQuery;
import org.apache.ibatis.jdbc.SQL;

public class SaleInvoiceLogisticsRuleSql {
    public static String logisticsRulePage(LogisticsRulePageQuery logisticsRulePageQuery) {
        return new SQL() {{
            SELECT("rule_id", "rule_name", "offer_id", "priority_number", "status_cd", "modify_user", "modify_time");
            FROM("logistics_invoice_rule_define AS silr");
            if (logisticsRulePageQuery.getRuleName() != null) {
                WHERE("rule_name LIKE concat('%',#{ruleName},'%')");
            }
            if (logisticsRulePageQuery.getOfferId() != null) {
                WHERE("offer_id = #{offerId}");
            }
            if (logisticsRulePageQuery.getModifyStartTime() != null) {
                WHERE("modify_time >= #{modifyStartTime}");
            }
            if (logisticsRulePageQuery.getModifyEndTime() != null) {
                WHERE("modify_time <= #{modifyEndTime}");
            }
        }}.toString();
    }
}
