package com.wisrc.sys.webapp.dao.sql;

import com.wisrc.sys.webapp.entity.AccountEntity;
import org.apache.ibatis.jdbc.SQL;

public class SysUserInfoSQL {
    public static String getUserInfo(AccountEntity accountEntity) {
        return new SQL() {{
            SELECT("user_id,user_name,status_cd,create_user,create_time,phone_number,qq,weixin,email,telephone_number,employee_id,employee_name,employee_status_cd,worktile_id,position_cd as positionId,position_name,position_parent_cd,position_status_cd,dept_cd as departmentId,dept_name,dept_parent_cd,dept_status_cd");
            FROM("v_sys_user_info");
            if (accountEntity.getUserId() != null) {
                WHERE("user_id like concat('%', #{userId}, '%')");
            }
            if (accountEntity.getUserName() != null) {
                WHERE("user_name like concat('%', #{userName}, '%')");
            }
            if (accountEntity.getStatusCd() != null) {
                WHERE("status_cd = #{statusCd}");
            }
            if (accountEntity.getDeptCd() != null) {
                WHERE("dept_cd in " + accountEntity.getDeptCd());
            }
            WHERE("user_id <> 'admin'");
            ORDER_BY("create_time desc");
        }}.toString();
    }

    public static String getUserEmployee(String userIdList) {
        return new SQL() {{
            SELECT("user_id, user_name, employee_id, employee_name, position_cd");
            FROM("v_account_employee_info");
            if (userIdList != null) {
                WHERE("user_id in " + userIdList);
            }
        }}.toString();
    }
}
