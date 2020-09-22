package com.wisrc.sys.webapp.dao.sql;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class VSysUserMenusSQL {
    public static String auth(@Param("userId") String userId,
                              @Param("menuId") String menuId,
                              @Param("methodCd") Integer methodCd,
                              @Param("path") String path,
                              @Param("menuType") Integer menuType) {
        return new SQL() {{
            SELECT("user_id, menu_id, method_cd, path, menu_type");
            FROM("v_user_menus_info");
            if (userId != null && !userId.isEmpty()) {
                WHERE("user_id = #{userId}");
            }
            if (menuId != null && !menuId.isEmpty()) {
                WHERE("menu_id = #{menuId}");
            }
            if (methodCd != null) {
                WHERE("method_cd = #{methodCd}");
            }
            if (path != null && !path.isEmpty()) {
                WHERE("path = #{path}");
            }
            if (menuType != null) {
                WHERE("menu_type = #{menuType}");
            }
        }}.toString();
    }
}
