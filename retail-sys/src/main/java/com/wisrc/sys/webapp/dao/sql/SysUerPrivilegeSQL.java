package com.wisrc.sys.webapp.dao.sql;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class SysUerPrivilegeSQL {
    public static String getSysUserPrivilege(@Param("roleId") final String roleId) {
        return new SQL() {{
            SELECT("spi.privilege_cd, spi.privilege_name");
            FROM("sys_user_privilege AS sup");
            LEFT_OUTER_JOIN("sys_privileges_info AS spi ON spi.privilege_cd = sup.privilege_cd");
            WHERE("sup.role_id = #{roleId}");
        }}.toString();
    }


    /**
     * @param userId          账号过滤
     * @param commodityIdList 商品ID列表过滤
     * @param roleIdList      角色列表过滤
     * @param privilegeCdList 权限代码过滤
     * @param deptCd          部门编码过滤
     * @param positionCdList  岗位ID列表过滤
     * @param employeeIdList  员工ID列表过滤
     */
    public static String searchUserCommodityPrivilege(@Param("userId") String userId,
                                                      @Param("commodityIdList") String commodityIdList,
                                                      @Param("roleIdList") String roleIdList,
                                                      @Param("privilegeCdList") String privilegeCdList,
                                                      @Param("deptCd") String deptCd,
                                                      @Param("positionCdList") String positionCdList,
                                                      @Param("employeeIdList") String employeeIdList) {
        return new SQL() {{
            SELECT("distinct commodity_id");
            FROM("v_user_commodity_privilege");
            // 根据用户账号进行过滤
            if (userId != null && !userId.isEmpty()) {
                WHERE("user_id = #{userId}");
            }
            // 根据商品ID进行过滤
            if (commodityIdList != null && !commodityIdList.isEmpty()) {
                WHERE("commodity_id in " + commodityIdList);
            }
            // 根据角色进行过滤
            if (roleIdList != null && !roleIdList.isEmpty()) {
                WHERE("role_id in " + roleIdList);
            }
            // 根据权限代码过滤
            if (privilegeCdList != null && !privilegeCdList.isEmpty()) {
                WHERE("privilege_cd in " + privilegeCdList);
            }
            // 获取这个部门的MSKU授权信息
            if (deptCd != null && !deptCd.isEmpty()) {
                WHERE("dept_cd = #{deptCd}");
            }
            // 根据岗位进行搜索
            if (positionCdList != null && !positionCdList.isEmpty()) {
                WHERE("position_cd in " + positionCdList);
            }
            // 根据员工进行搜索
            if (employeeIdList != null && !employeeIdList.isEmpty()) {
                WHERE("employee_id in " + employeeIdList);
            }
        }}.toString();
    }
}
