package com.wisrc.sys.webapp.dao;

import com.wisrc.sys.webapp.entity.SysPrivilegeSupplierEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SysPrivilegeSupplierDao {
    @Select("SELECT * FROM sys_privilege_supplier")
    List<SysPrivilegeSupplierEntity> getSysPrivilegeSupplier() throws Exception;

    @Insert("INSERT INTO sys_privilege_supplier(uuid, privilege_cd, supplier_cd) VALUES(#{uuid}, #{privilegeCd}, #{supplierCd})")
    void saveSysPrivilegeSupplier(SysPrivilegeSupplierEntity sysPrivilegeSupplierEntity) throws Exception;

    @Update("UPDATE sys_privilege_supplier SET privilege_cd = #{privilegeCd}, supplier_cd = #{supplierCd} WHERE uuid = #{uuid}")
    void editSysPrivilegeSupplier(SysPrivilegeSupplierEntity sysPrivilegeSupplierEntity) throws Exception;
}
