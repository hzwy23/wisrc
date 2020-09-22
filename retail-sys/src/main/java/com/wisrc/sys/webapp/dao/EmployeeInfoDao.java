package com.wisrc.sys.webapp.dao;

import com.wisrc.sys.webapp.dao.sql.SysEmployeeSQL;
import com.wisrc.sys.webapp.entity.*;
import org.apache.ibatis.annotations.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface EmployeeInfoDao {

    @SelectProvider(type = SysEmployeeSQL.class, method = "searchOperationEmployee")
    List<SysDeptOperationEmployeeEntity> findOperationEmployee(@Param("statusCd") Integer statusCd,
                                                               @Param("deptCd") String deptCd,
                                                               @Param("positionCd") String positionCd,
                                                               @Param("employeeIdList") String employeeIdList);

    @Insert("INSERT INTO sys_employee_info (employee_id, employee_name, status_cd, position_cd)\n" +
            "VALUES (#{employeeId}, #{employeeName}, #{statusCd}, #{positionCd})")
    void add(SysEmployeeInfoEntity sysEmployeeInfoEntity);

    @Update("UPDATE sys_employee_info SET employee_name = #{employeeName}, status_cd = #{statusCd}, position_cd = #{positionCd} WHERE employee_id = #{employeeId}")
    void update(SysEmployeeInfoEntity sysEmployeeInfoEntity);

    @Select(" <script>" +
            "  SELECT\n" +
            "  IF(a.employee_id IS NULL, '', a.employee_id)     AS employeeId,\n" +
            "  IF(a.employee_name IS NULL, '', a.employee_name) AS employeeName,\n" +
            "  IF(a.status_cd IS NULL, 1, a.status_cd)          AS statusCd,\n" +
            "  IF(b.position_cd IS NULL, '', b.position_cd)     AS positionCd,\n" +
            "  IF(b.position_name IS NULL, '', b.position_name) AS positionName,\n" +
            "  IF(c.dept_cd IS NULL, '', c.dept_cd)             AS deptCd,\n" +
            "  IF(c.dept_name IS NULL, '', c.dept_name)         AS deptName" +
            " FROM sys_employee_info a,\n" +
            "  sys_position_info b,\n" +
            "  sys_dept_info c\n" +
            "WHERE\n" +
            "  1 = 1\n" +
            "  <if test = 'employeeId!=null'>  " +
            "  AND a.employee_id LIKE concat('%', #{employeeId}, '%')\n" +
            "  </if>  " +
            "  <if test = 'employeeName!=null'>  " +
            "  AND a.employee_name LIKE concat('%', #{employeeName}, '%')\n" +
            "  </if>  " +
            "  AND a.position_cd = b.position_cd\n" +
            "  <if test = 'positionCd!=null'>  " +
            "  AND a.position_cd LIKE concat('%', #{positionCd}, '%')\n" +
            "  </if>  " +
            "  <if test = 'positionName!=null'>  " +
            "  AND b.position_name LIKE concat('%', #{positionName}, '%')\n" +
            "  </if>  " +
            "  AND b.dept_cd = c.dept_cd\n" +
            "  <if test = 'deptCd!=null'>  " +
            "  AND c.dept_cd LIKE concat('%', #{deptCd}, '%')\n" +
            "  </if>  " +
            "  <if test = 'deptName!=null'>  " +
            "  AND c.dept_name LIKE concat('%', #{deptName}, '%') " +
            "  </if>  " +
            "  <if test = 'statusCd!=null'>  " +
            "  AND a.status_cd = #{statusCd} " +
            "  </if>  " +
            " </script>")
    List<LinkedHashMap<String, Object>> find(GatherEntity gatherEntity);

    @Select(" SELECT\n" +
            "  a.employee_id as employeeId,\n" +
            "  a.employee_name as employeeName,\n" +
            "  a.status_cd as statusCd,\n" +
            "  b.position_cd as positionCd,\n" +
            "  b.position_name as positionName,\n" +
            "  c.dept_cd as deptCd,\n" +
            "  c.dept_name as deptName\n" +
            " FROM sys_employee_info a,\n" +
            "  sys_position_info b,\n" +
            "  sys_dept_info c\n" +
            " WHERE\n" +
            "  1 = 1\n" +
            "  AND  a.employee_id = #{employeeId}\n" +
            "  AND a.position_cd = b.position_cd\n" +
            "  AND b.dept_cd = c.dept_cd ")
    LinkedHashMap<String, Object> getEmployee(String employeeId);

    /**
     * （模糊查询）取出同名称简写的employeeId的降序集合
     *
     * @param rule
     * @return
     */
    @Select(" SELECT employee_id AS employeeId\n" +
            " FROM sys_employee_info \n" +
            " WHERE employee_id LIKE binary concat('', #{rule}, '%')\n" +
            " ORDER BY employee_id DESC")
    List<SysEmployeeInfoEntity> getMaxNum(String rule);

    @Update("UPDATE sys_employee_info SET status_cd = #{statusCd} WHERE employee_id = #{employeeId}")
    void changeStatus(Map<String, Object> map);

    @Select("SELECT dept_cd, dept_name, parent_cd FROM sys_dept_info")
    List<SysDeptInfoEntity> getSysDeptInfo();

    @Select("SELECT dept_cd, dept_name, parent_cd FROM sys_dept_info where dept_cd = #{deptCd}")
    SysDeptInfoEntity getSysDeptInfoById(String deptCd);

    @Select(" SELECT\n" +
            "  a.employee_id   AS employeeId,\n" +
            "  a.employee_name AS employeeName\n" +
            "FROM\n" +
            "  sys_employee_info a\n" +
            "WHERE a.employee_id NOT IN\n" +
            "      (\n" +
            "        SELECT b.employee_id\n" +
            "        FROM\n" +
            "          sys_user_info b\n" +
            "      ) ")
    List<Map> getUnAccount();

    @Select("SELECT MAX(RIGHT(employee_id, 5)) as id FROM sys_employee_info")
    String getMaxId();

    @Select(" SELECT\n" +
            "  a.employee_id   AS employeeId,\n" +
            "  a.employee_name AS employeeName,\n" +
            "  a.employee_id   AS employeeId,\n" +
            "  a.status_cd     AS statusCd\n" +
            " FROM sys_employee_info a " +
            " where a.employee_id = #{employeeId} ")
    SysEmployeeInfoEntity getById(String employeeId);

    @SelectProvider(type = SysEmployeeSQL.class, method = "searchCategory")
    List<VUserCategoryEntity> searchCategory(@Param("userId") String userId,
                                             @Param("employeeIdList") String employeeIdList,
                                             @Param("positionCd") String positionCd,
                                             @Param("upEmployeeId") String upEmployeeId,
                                             @Param("executiveDirectorId") String executiveDirectorId,
                                             @Param("upPositionCd") String upPositionCd);

    @Select("select distinct employee_id from v_user_category_info f where f.executive_director_id = #{employeeId}")
    String[] getEployee(@Param("employeeId") String employeeId);
}
