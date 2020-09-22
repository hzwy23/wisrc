package com.wisrc.sys.webapp.dao;

import com.wisrc.sys.webapp.entity.SysPrivilegesInfoEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AuthInfoInfoDao {

    /**
     * @return
     */
    @Select(" <script> " +
            " SELECT\n" +
            "  a.privilege_cd   AS privilegeCd,\n" +
            "  a.privilege_name AS privilegeName,\n" +
            "  a.privilege_type_attr," +
            "  a.status_cd AS statusCd,\n" +
            "  a.create_user AS createUser,\n" +
            "  DATE_FORMAT(a.create_time,'%Y-%m-%d %T') AS createTime\n" +
            " FROM sys_privileges_info a\n" +
            " WHERE\n" +
            "    (a.status_cd != 3 or a.status_cd is null) \n" +
            "  <if test = 'privilegeCd!=null'>" +
            "  AND a.privilege_cd LIKE concat('%', #{privilegeCd}, '%')\n" +
            "  </if>" +
            "  <if test = 'privilegeName!=null'>" +
            "  AND a.privilege_name LIKE concat('%', #{privilegeName}, '%') " +
            "  </if>" +
            "  <if test = 'privilegeTypeAttr != null'>" +
            "  AND a.privilege_type_attr = #{privilegeTypeAttr}" +
            "  </if>" +
            "  <if test = 'statusCd != null'>" +
            "  AND a.status_cd LIKE concat('%', #{statusCd}, '%') " +
            "  </if>" +
            "   ORDER BY a.create_time desc" +
            " </script>")
    List<SysPrivilegesInfoEntity> getAuthInfo(SysPrivilegesInfoEntity sysPrivilegesInfoEntity);

    @Insert("INSERT INTO sys_privileges_info(privilege_cd, privilege_name,create_user,create_time,status_cd,random_value, privilege_type_attr, modify_user, modify_time) VALUES(#{privilegeCd}, #{privilegeName},#{createUser},#{createTime},#{statusCd}, #{privilegeCd}, #{privilegeTypeAttr}, #{modifyUser}, #{modifyTime})")
    void add(SysPrivilegesInfoEntity sysPrivilegesInfoEntity);

    @Update("UPDATE sys_privileges_info SET status_cd = 3 WHERE privilege_cd = #{privilegeCd}")
    void delete(String privilegeCd);

    @Update("UPDATE sys_privileges_info SET privilege_name = #{privilegeName},status_cd = #{statusCd}, modify_time = #{modifyTime}, modify_user = #{modifyUser} WHERE privilege_cd = #{privilegeCd}")
    void update(SysPrivilegesInfoEntity sysPrivilegesInfoEntity);

    @Update("UPDATE sys_privileges_info SET status_cd = #{statusCd} WHERE privilege_cd = #{privilegeCd}")
    void restrict(SysPrivilegesInfoEntity entity);

    @Select("SELECT\n" +
            "  a.privilege_cd as privilegeCd,\n" +
            "  a.privilege_name as privilegeName,\n" +
            "  a.privilege_type_attr," +
            "  a.status_cd as statusCd,\n" +
            "  a.create_user as createUser,\n" +
            "  a.create_time as createTime\n" +
            "FROM sys_privileges_info a\n" +
            "WHERE a.privilege_cd = #{privilegeCd} " +
            "    and ((a.status_cd != 3 or a.status_cd is null)) \n"
    )
    SysPrivilegesInfoEntity getPrivilegesById(String authId);

    /**
     * （模糊查询）取出同名称简写的employeeId的降序集合
     *
     * @param rule
     * @return
     */
    @Select(" SELECT privilege_cd AS privilegeCd\n" +
            " FROM sys_privileges_info  \n" +
            " WHERE privilege_cd LIKE binary concat('', #{rule}, '%')\n" +
            " ORDER BY privilege_cd DESC ")
    List<SysPrivilegesInfoEntity> getMaxSize(String rule);

    @Delete(" delete from sys_user_privilege where privilege_cd = #{privilegeCd}")
    void deleteUser(String privilegeCd);

    @Delete(" delete from sys_privilege_shop where privilege_cd = #{privilegeCd}")
    void deleteShop(String privilegeCd);

    @Delete(" delete from sys_privilege_supplier where privilege_cd = #{privilegeCd}")
    void deleteSupplier(String authId);

    @Delete(" delete from sys_privilege_warehouse where privilege_cd = #{privilegeCd}")
    void deleteWarehohse(String authId);

    @Select(" SELECT\n" +
            "  a.privilege_cd   AS privilegeCd,\n" +
            "  a.privilege_name AS privilegeName,\n" +
            "  a.privilege_type_attr," +
            "  a.create_user    AS createUser,\n" +
            "  a.create_time    AS createTime\n" +
            " FROM sys_privileges_info a " +
            " where (a.status_cd != 3 or a.status_cd is null)")
    List<SysPrivilegesInfoEntity> findAll();

    @Select(" SELECT\n" +
            "  a.privilege_cd   AS privilegeCd,\n" +
            "  a.privilege_name AS privilegeName,\n" +
            "  a.privilege_type_attr," +
            "  a.create_user    AS createUser,\n" +
            "  a.create_time    AS createTime\n" +
            " FROM sys_privileges_info a " +
            " where (a.status_cd != 3 or a.status_cd is null) and privilege_type_attr = #{privilegeTypeAttr}")
    List<SysPrivilegesInfoEntity> findByTypeAttr(String privilegeTypeAttr);

    @Select("SELECT MAX(RIGHT(privilege_cd, 5)) as id FROM sys_privileges_info")
    String getMaxId();

    /**
     * 新增时根据权限名称查重
     *
     * @param privilegeName
     * @return
     */
    @Select("select count(*) from sys_privileges_info where privilege_name = #{privilegeName}")
    int checkout(@Param("privilegeName") String privilegeName);

    @Select("select privilege_name from sys_privileges_info where privilege_cd=#{privilegeCd}")
    String getPrivilegeName(@Param("privilegeCd") String privilegeCd);
}
