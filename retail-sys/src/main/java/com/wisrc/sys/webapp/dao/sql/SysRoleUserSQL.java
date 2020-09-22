package com.wisrc.sys.webapp.dao.sql;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class SysRoleUserSQL {
    public static String search(@Param("userIds") String userIds) {
        return new SQL() {{
            SELECT("uuid,role_id,role_name,user_id");
            FROM("v_user_role_info");
            if (userIds == null) {
                WHERE("ser_id in " + userIds);
            }
        }}.toString();
    }
}
