package com.wisrc.sys.webapp.dao.sql;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class SysEmployeeSQL {
    public static final String search(@Param("ids") String ids) {
        return new SQL() {{
            SELECT("employee_id, employee_name, status_cd, position_cd");
            FROM("sys_employee_info");
            WHERE("employee_id in " + ids);
        }}.toString();
    }


    /**
     * @param statusCd       员工状态 1：正藏，2：离职
     * @param deptCd         部门编码
     * @param positionCd     岗位编码
     * @param employeeIdList 员工号列表
     */
    public static final String searchOperationEmployee(@Param("statusCd") Integer statusCd,
                                                       @Param("deptCd") String deptCd,
                                                       @Param("positionCd") String positionCd,
                                                       @Param("employeeIdList") String employeeIdList) {
        return new SQL() {{
            SELECT("dept_cd, dept_name, position_cd, position_name, employee_id, employee_name, status_cd");
            FROM("v_sys_operation_employee");
            if (statusCd != null) {
                WHERE("status_cd = #{statusCd}");
            }
            if (deptCd != null && !deptCd.isEmpty()) {
                WHERE("dept_cd = #{deptCd}");
            }
            if (positionCd != null && !positionCd.isEmpty()) {
                WHERE("position_cd = #{positionCd}");
            }
            if (employeeIdList != null && !employeeIdList.isEmpty()) {
                WHERE("employee_id in " + employeeIdList);
            }
        }}.toString();
    }

    public static String searchCategory(@Param("userId") String userId,
                                        @Param("employeeIdList") String employeeIdList,
                                        @Param("positionCd") String positionCd,
                                        @Param("upEmployeeId") String upEmployeeId,
                                        @Param("executiveDirectorId") String executiveDirectorId,
                                        @Param("upPositionCd") String upPositionCd) {
        return new SQL() {{
            SELECT("user_id, user_name, employee_id, employee_name, position_cd, position_name, executive_director_attr,up_position_cd, up_position_name, up_executive_director_attr, up_employee_id, up_employee_name, executive_director_id, executive_director_name");
            FROM("v_user_category_info");
            if (userId != null && !userId.isEmpty()) {
                WHERE("user_id = #{userId}");
            }
            if (employeeIdList != null && !employeeIdList.isEmpty()) {
                WHERE("employee_id in " + employeeIdList);
            }
            if (positionCd != null && !positionCd.isEmpty()) {
                WHERE("position_cd = #{positionCd}");
            }
            if (upEmployeeId != null && !upEmployeeId.isEmpty()) {
                WHERE("up_employee_id = #{upEmployeeId}");
            }
            if (executiveDirectorId != null && !executiveDirectorId.isEmpty()) {
                WHERE("executive_director_id = #{executiveDirectorDd}");
            }
            if (upPositionCd != null && !upPositionCd.isEmpty()) {
                WHERE("up_position_cd = #{upPositionCd}");
            }
        }}.toString();
    }
}
