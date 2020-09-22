package com.wisrc.sys.webapp.dao;

import com.wisrc.sys.webapp.entity.SysPositionInfoEntity;
import com.wisrc.sys.webapp.vo.position.PositionPageSelectorVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SysPositionInfoDao {
    String using = "4";
    String remove = "5";

    @Select("SELECT * FROM  sys_position_info")
    List<SysPositionInfoEntity> getSysPositionInfo() throws Exception;

    @Select("<script> "
            + "SELECT position_cd, position_name, parent_cd, dept_cd, executive_director_attr FROM  sys_position_info WHERE status_cd = 4 "
            + "<if test = 'positionCd!=null and positionCd!=\"\"'>"
            + " AND position_cd LIKE concat('%', #{positionCd}, '%') "
            + "</if>"
            + "<if test = 'positionName!=null and positionName!=\"\"'>"
            + " AND position_name LIKE concat('%', #{positionName}, '%') "
            + "</if>"
            + "<if test = 'deptNum!=null and deptNum!=\"\"'>"
            + " AND FIND_IN_SET(dept_cd,getDeptChildList(#{deptNum}))"
            + "</if>"
            + "</script>"
    )
    List<SysPositionInfoEntity> getSysPositionPage(@Param("positionCd") String positionCd, @Param("positionName") String positionName, @Param("deptNum") String deptNum) throws Exception;

    @Insert("INSERT INTO sys_position_info(position_cd, position_name, parent_cd, dept_cd, status_cd, executive_director_attr, random_value) VALUES(#{positionCd}, #{positionName}, #{parentCd}, #{deptCd}, #{statusCd}, #{executiveDirectorAttr}, #{positionCd})")
    void saveSysPositionInfo(SysPositionInfoEntity sysPositionInfoEntity) throws Exception;

    @Update("UPDATE sys_position_info SET position_name = #{positionName}, parent_cd = #{parentCd}, dept_cd = #{deptCd}, executive_director_attr = #{executiveDirectorAttr} WHERE position_cd = #{positionCd}")
    void editSysPositionInfo(SysPositionInfoEntity sysPositionInfoEntity) throws Exception;

    @Select("<script> "
            + "SELECT position_cd, position_name, executive_director_attr FROM  sys_position_info WHERE position_cd IN "
            + "<foreach item='positionCd' index='index' collection='positionCds' open=' (' separator=',' close=')'>"
            + "#{positionCd}"
            + "</foreach>"
            + "</script>"
    )
    List<SysPositionInfoEntity> getSysPositionBatch(@Param("positionCds") List positionCds) throws Exception;

    @Select("SELECT MAX(RIGHT(position_cd, 5)) as id FROM sys_position_info")
    String getMaxPositionId() throws Exception;

    @Select("SELECT * FROM  sys_position_info WHERE position_cd = #{positionCd}")
    SysPositionInfoEntity getSysPositionInfoById(@Param("positionCd") String positionCd) throws Exception;

    @Select("<script> "
            + "SELECT position_cd, position_name, a.parent_cd, a.dept_cd, a.executive_director_attr FROM sys_position_info AS a LEFT JOIN sys_dept_info AS sdi ON sdi.dept_cd = a.dept_cd WHERE a.status_cd != 5 "
            + "<if test = 'positionNow!=null'>"
            + " AND NOT EXISTS  (SELECT position_cd FROM sys_position_info AS b WHERE FIND_IN_SET(position_cd,getPositionChildList(#{positionNow})) AND a.position_cd = b.position_cd ) "
            + "</if>"
            + "<if test = 'jobFindKey!=null'>"
            + " AND (position_cd LIKE concat('%', #{jobFindKey}, '%') OR position_name LIKE concat('%', #{jobFindKey}, '%')  )"
            + "</if>"
            + "<if test = 'deptFindKey!=null'>"
            + " AND (sdi.dept_cd LIKE concat('%', #{deptFindKey}, '%') OR sdi.dept_name LIKE concat('%', #{deptFindKey}, '%')  )"
            + "</if>"
            + "</script>"
    )
    List<SysPositionInfoEntity> getPositionNoChild(PositionPageSelectorVo positionPageSelectorVo) throws Exception;

    @Update("UPDATE sys_position_info SET status_cd = " + remove + " WHERE position_cd = #{positionCd}")
    void deleteSysPositionInfo(@Param("positionCd") String positionCd) throws Exception;

    @Select("SELECT position_cd FROM sys_position_info WHERE status_cd = " + using + " AND parent_cd = #{positionCd}")
    List<SysPositionInfoEntity> getPositionParent(@Param("positionCd") String positionCd) throws Exception;

    @Select("SELECT position_cd FROM sys_position_info WHERE status_cd = " + using + " AND dept_cd = #{deptCd}")
    List<SysPositionInfoEntity> getPositionByDeptCd(@Param("deptCd") String deptCd) throws Exception;
}
