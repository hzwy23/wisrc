package com.wisrc.sys.webapp.dao;

import com.wisrc.sys.webapp.entity.SysDeptInfoEntity;
import com.wisrc.sys.webapp.vo.SysDeptOperationVO;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Mapper
public interface SysDeptInfoDao {
    String using = "4";
    String remove = "5";

    @Select("SELECT dept_cd, dept_name, parent_cd, dept_type_attr FROM sys_dept_info WHERE status_cd != " + remove)
    CopyOnWriteArrayList<SysDeptInfoEntity> getSysDeptInfo();

    @Select("SELECT dept_cd, dept_name, parent_cd,dept_type_attr FROM sys_dept_info WHERE status_cd != " + remove)
    List<SysDeptInfoEntity> getSysDeptInfoPage() throws Exception;

    @Insert("INSERT INTO sys_dept_info(dept_cd, dept_name, parent_cd, status_cd, dept_type_attr, random_value) VALUES(#{deptCd}, #{deptName}, #{parentCd}, #{statusCd}, #{deptTypeAttr}, #{deptCd})")
    void saveSysDeptInfo(SysDeptInfoEntity sysDeptInfoEntity) throws Exception;

    @Update("UPDATE sys_dept_info SET dept_name = #{deptName}, parent_cd = #{parentCd}, dept_type_attr = #{deptTypeAttr} WHERE dept_cd = #{deptCd}")
    void editSysDeptInfo(SysDeptInfoEntity sysDeptInfoEntity) throws Exception;

    @Select("SELECT MAX(RIGHT(dept_cd, 4)) as id FROM sys_dept_info")
    String getMaxId() throws Exception;

    @Select("SELECT dept_cd, dept_name, parent_cd, dept_type_attr FROM sys_dept_info WHERE dept_cd = #{deptCd}")
    SysDeptInfoEntity getDeptInfo(String deptCd) throws Exception;

    @Select("<script> "
            + "SELECT dept_cd, dept_name, dept_type_attr FROM sys_dept_info WHERE dept_cd IN "
            + "<foreach item='deptCd' index='index' collection='deptCds' open=' (' separator=',' close=')'>"
            + "#{deptCd}"
            + "</foreach>"
            + "</script>"
    )
    List<SysDeptInfoEntity> getSysDeptInfoBatch(@Param("deptCds") List deptCds) throws Exception;

    @Select("<script> "
            + "SELECT dept_cd FROM sys_dept_info WHERE status_cd != " + remove
            + "<if test = 'deptNow!=null'>"
            + " AND FIND_IN_SET(dept_cd,getDeptChildList(#{deptNow}))  "
            + "</if>"
            + "</script>"
    )
    List<String> getDeptChild(@Param("deptNow") String deptNow);

    @Select("SELECT dept_cd, dept_name, parent_cd, dept_type_attr FROM sys_dept_info WHERE status_cd != " + remove + " AND FIND_IN_SET(dept_cd,getDeptParentList(#{deptNow})) ")
    List<SysDeptInfoEntity> getDeptParentLine(@Param("deptNow") String deptNow) throws Exception;

    @Update("UPDATE sys_dept_info SET status_cd = " + remove + " WHERE dept_cd = #{deptCd}")
    void deleteSysDeptInfo(@Param("deptCd") String deptCd) throws Exception;

    @Select("SELECT dept_cd, dept_name, parent_cd, dept_type_attr FROM sys_dept_info WHERE parent_cd = #{deptCd} AND status_cd != " + remove)
    List<SysDeptInfoEntity> getDeptParent(@Param("deptCd") String deptCd) throws Exception;

    @Select("select t.dept_cd, t.dept_name from sys_dept_info t where t.dept_type_attr = 1 and t.status_cd = 4")
    List<SysDeptOperationVO> getOperationDept();
}
