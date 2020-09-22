package com.wisrc.sys.webapp.dao;

import com.wisrc.sys.webapp.dao.sql.SysUserInfoSQL;
import com.wisrc.sys.webapp.entity.AccountEmployeeEntity;
import com.wisrc.sys.webapp.entity.SysUserInfoEntity;
import com.wisrc.sys.webapp.vo.user.UserDetailsVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SysUserInfoDao {
    int deleteNumber = 2;

    @Select("SELECT * FROM sys_user_info")
    List<SysUserInfoEntity> getSysUserInfo() throws Exception;

    @Update("update sys_user_info set worktile_id = #{worktileId} where user_id = #{userId}")
    void bindWorktileId(SysUserInfoEntity ele);

    @Select("select worktile_id from sys_user_info where user_id = #{userId}")
    String getWorktileIdByUserId(String userId);

    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "userName", column = "user_name"),
            @Result(property = "statusCd", column = "status_cd"),
            @Result(property = "createUser", column = "create_user"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "phoneNumber", column = "phone_number"),
            @Result(property = "telephoneNumber", column = "telephone_number"),
            @Result(property = "worktileId", column = "worktile_id"),
            @Result(property = "employeeId", column = "employee_id"),
            @Result(property = "deptId", column = "dept_cd"),
            @Result(property = "deptName", column = "dept_name"),
            @Result(property = "positionId", column = "position_cd"),
            @Result(property = "positionName", column = "position_name")
    })
    @Select("SELECT user_id,user_name,status_cd,create_time,create_user,phone_number,qq,weixin,email,worktile_id,telephone_number,employee_id,employee_name,employee_status_cd,position_cd,position_name,position_parent_cd,position_status_cd,dept_cd,dept_name,dept_parent_cd, dept_status_cd,executive_director_attr from v_sys_user_info where user_id = #{userId}")
    UserDetailsVO findById(String userId);

    @Select("<script> "
            + "SELECT * FROM sys_user_info WHERE status_cd &lt;&gt; " + deleteNumber
            + "<if test = 'userFindKey!=null'>"
            + " AND (user_id LIKE #{userFindKey} OR user_name LIKE #{userFindKey}) "
            + "</if>"
            + " Limit #{currentPage}, #{pageSize}"
            + "</script>"
    )
    List<SysUserInfoEntity> getSysUserInfoPage(@Param("userFindKey") String userFindKey,
                                               @Param("currentPage") Integer currentPage,
                                               @Param("pageSize") Integer pageSize
    ) throws Exception;

    @Insert({
            "INSERT INTO sys_user_inf (user_id, user_name, status_cd, create_user, create_time, phone_number, qq, weixin, email, worktile_id, telephone_number, employee_id, position_id, department_id)",
            "VALUES(#{userId}, #{userName}, #{statusCd}, #{createUser}, #{createTime}, #{phoneNumber}, #{qq}, #{weixin}, #{email}, #{jobNumber}, #{telephoneNumber}, #{employeeId}, #{positionId}, #{departmentId})"
    })
    void saveSysUserInfo(SysUserInfoEntity sysUserInfoEntity) throws Exception;

    @Update({
            "UPDATE sys_user_info SET",
            "user_name = #{userName}, phone_number = #{phoneNumber}, qq = #{qq}, weixin = #{weixin}, email = #{email}, worktile_id = #{worktileId}, telephone_number = #{telephoneNumber}, employee_id = #{employeeId}, position_id= #{positionId}, department_id= #{departmentId}",
            "WHERE user_id = #{userId}"
    })
    void editSysUserInfo(SysUserInfoEntity sysUserInfoEntity) throws Exception;

    @Select("<script> "
            + "SELECT COUNT(*) FROM sys_user_info WHERE status_cd &lt;&gt; " + deleteNumber
            + "<if test = 'userFindKey!=null'>"
            + " AND (user_id LIKE #{userFindKey} OR user_name LIKE #{userFindKey}) "
            + "</if>"
            + "</script>")
    Integer getSysUserCount(String userFindKey) throws Exception;

    @Select("SELECT * FROM sys_user_info WHERE user_id = #{userId}")
    SysUserInfoEntity getSysUserInfoByUserId(String roleId) throws Exception;

    @Update("UPDATE sys_user_info SET status_cd = 2 WHERE user_id = #{userId}")
    void deleteSysUserInfoByUserId(String userId) throws Exception;

    @Update("update sys_user_info set user_name = #{userName} where user_id = #{userId}")
    void updateName(@Param("userId") String userId, @Param("userName") String userName);

    @Update("update sys_user_info set phone_number = #{phoneNumber} where user_id = #{userId}")
    void updatePhone(@Param("userId") String userId, @Param("phoneNumber") String phoneNumber);

    @Update("update sys_user_info set qq = #{qq} where user_id = #{userId}")
    void updateQQ(@Param("userId") String userId, @Param("qq") String qq);

    @Update("update sys_user_info set weixin = #{weixin} where user_id = #{userId}")
    void updateWeixin(@Param("userId") String userId, @Param("weixin") String weixin);

    @Update("update sys_user_info set email = #{email} where user_id = #{userId}")
    void updateEmail(@Param("userId") String userId, @Param("email") String email);

    @Update("update sys_user_info set telephone_number = #{telephone} where user_id = #{userId}")
    void updateTelephone(@Param("userId") String userId, @Param("telephone") String telephone);

    @SelectProvider(type = SysUserInfoSQL.class, method = "getUserEmployee")
    List<AccountEmployeeEntity> getUserEmployeeList(String useridList);

}
