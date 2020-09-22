package com.wisrc.sys.webapp.dao;

import com.wisrc.sys.webapp.dao.sql.SysUserInfoSQL;
import com.wisrc.sys.webapp.entity.*;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface AccountInfoDao {
    String remove = "5";

    @SelectProvider(type = SysUserInfoSQL.class, method = "getUserInfo")
    List<SysUserInfoEntity> findUsers(AccountEntity accountEntity);

    @Select(" SELECT\n" +
            "  a.role_id as roleId,\n" +
            "  a.role_name as role_name,\n" +
            "  a.status_cd as statusCd,\n" +
            "  a.create_time as createTime,\n" +
            "  a.create_user as createUser,\n" +
            "  b.user_id as userId\n" +
            "FROM sys_role_info a\n" +
            "  LEFT JOIN sys_role_user b\n" +
            "    ON a.role_id = b.role_id\n" +
            "       AND b.user_id IN ${userIdPara}")
    List<RoleAndUserEntity> getRoleInfoList(Map<String, String> userIdPara);

    @Select(" SELECT\n" +
            "  a.employee_id   AS employeeId,\n" +
            "  a.employee_name AS employeeName,\n" +
            "  a.status_cd     AS statusCd,\n" +
            "  a.position_cd   AS positionCd\n" +
            "FROM sys_employee_info a\n" +
            "WHERE a.employee_id = #{employeeId} ")
    SysEmployeeInfoEntity getEmployee(String employeeId);

    @Insert("INSERT INTO sys_user_info(user_id, user_name, status_cd, create_user, create_time, phone_number, qq, weixin, email, worktile_id, telephone_number, employee_id) " +
            "VALUES(#{userId}, #{userName}, 1, #{createUser}, #{createTime}, #{phoneNumber}, #{qq}, #{weixin}, #{email}, #{worktileId}, #{telephoneNumber}, #{employeeId})")
    void insert(AccountEntity accountEntity);


    @Update("UPDATE sys_user_info SET user_name = #{userName}, phone_number = #{phoneNumber}, " +
            "qq = #{qq}, weixin = #{weixin}, email = #{email}, telephone_number = #{telephoneNumber}, employee_id = #{employeeId} WHERE user_id = #{userId}")
    void update(AccountEntity accountEntity);

    @Update(" UPDATE sys_user_info SET status_cd = #{statusCd}  WHERE user_id = #{userId} ")
    void restrict(AccountEntity accountEntity);

    @Select(" SELECT\n" +
            "  a.user_id          AS userId,\n" +
            "  a.user_name        AS userName,\n" +
            "  a.status_cd        AS statusCd,\n" +
            "  a.create_user      AS createUser,\n" +
            "  a.create_time      AS createTime,\n" +
            "  a.phone_number     AS phoneNumber,\n" +
            "  a.qq               AS qq,\n" +
            "  a.weixin           AS weixin,\n" +
            "  a.email            AS email,\n" +
            "  a.telephone_number AS telephoneNumber,\n" +
            "  a.employee_id      AS employeeId,\n" +
            "  b.employee_name    AS employeeName,\n" +
            "  c.position_name    AS positionName,\n" +
            "  d.dept_cd          AS deptCd,\n" +
            "  d.dept_name        AS deptName\n" +
            "FROM sys_user_info a,\n" +
            "  sys_employee_info b,\n" +
            "  sys_position_info c,\n" +
            "  sys_dept_info d\n" +
            "WHERE " +
            "    (a.del_flag is null or a.del_flag = 0) \n" +
            "    AND a.user_id = #{userId}\n" +
            "    AND a.employee_id = b.employee_id\n" +
            "    AND b.position_cd = c.position_cd\n" +
            "    AND c.dept_cd = d.dept_cd ")
    AccountEntity getUserById(AccountEntity accountEntity);

    /**
     * （模糊查询）取出同名称简写的employeeId的降序集合
     *
     * @param rule
     * @return
     */
    @Select(" SELECT user_id AS userId\n" +
            "FROM sys_user_info \n" +
            "WHERE user_id LIKE binary concat('', #{rule}, '%')\n" +
            "ORDER BY user_id DESC ")
    List<AccountEntity> getMaxId(String rule);

    @Select(" SELECT dept_cd AS deptCd, dept_name as deptName, parent_cd as parentCd FROM sys_dept_info ")
    List<SysDeptInfoEntity> findAllDept();

    @Select(" SELECT getDeptChildList( #{organizeId} ) from  sys_dept_info ")
    List<String> getDeptChildList(String organizeId);

    @Select(" SELECT COUNT(*) FROM sys_user_info ")
    Integer getMaxNumber();

    @Insert(" INSERT INTO sys_sec_user (user_id, password) VALUES (#{userId},#{password}) ")
    void insertPassWord(SysSecUserEntity sysSecUserEntity);
}
