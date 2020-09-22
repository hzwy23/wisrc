package com.wisrc.sys.webapp.dao;

import com.wisrc.sys.webapp.dao.sql.SysUerPrivilegeSQL;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Set;


@Mapper
public interface VUserCommodityPrivilegeDao {

    @SelectProvider(type = SysUerPrivilegeSQL.class, method = "searchUserCommodityPrivilege")
    Set<String> findUserCommodityPrivilege(@Param("userId") String userId,
                                           @Param("commodityIdList") String commodityIdList,
                                           @Param("roleIdList") String roleIdList,
                                           @Param("privilegeCdList") String privilegeCdList,
                                           @Param("deptCd") String deptCd,
                                           @Param("positionCdList") String positionCdList,
                                           @Param("employeeIdList") String employeeIdList);

    @Select("select commodity_id from v_user_commodity_privilege where employee_id = #{employeeId}")
    Set<String> getEmployeeAuth(@Param("employeeId") String employeeId);
}
