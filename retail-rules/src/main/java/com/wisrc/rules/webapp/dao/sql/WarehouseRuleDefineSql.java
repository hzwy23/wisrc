package com.wisrc.rules.webapp.dao.sql;

import com.wisrc.rules.webapp.query.WarehouseRuleDefineQuery;
import org.apache.ibatis.jdbc.SQL;

public class WarehouseRuleDefineSql {
    static final int using = 1;

    public static String warehouseRulePage(WarehouseRuleDefineQuery warehouseRuleDefineQuery) {
        return new SQL() {{
            SELECT("rule_id", "rule_name", "priority_number", "warehouse_id", "status_desc", "modify_user", "modify_time");
            FROM("warehouse_rule_define AS wrd");
            LEFT_OUTER_JOIN("rule_status_attr AS rsa ON rsa.status_cd = wrd.status_cd");
            if (warehouseRuleDefineQuery.getRuleName() != null) {
                WHERE("rule_name LIKE concat('%',#{ruleName},'%')");
            }
            if (warehouseRuleDefineQuery.getWarehouseId() != null) {
                WHERE("warehouse_id = #{warehouseId}");
            }
            if (warehouseRuleDefineQuery.getModifyStartTime() != null) {
                WHERE("modify_time >= #{modifyStartTime}");
            }
            if (warehouseRuleDefineQuery.getModifyEndTime() != null) {
                WHERE("modify_time <= #{modifyEndTime}");
            }
        }}.toString();
    }
}
