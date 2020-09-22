package com.wisrc.sys.webapp.dao;

import com.wisrc.sys.webapp.entity.GatherEntity;
import com.wisrc.sys.webapp.entity.SysPrivilegeSupplierEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PrivilegeSupplierDao {
    @Select(" SELECT\n" +
            "  aa.*,\n" +
            "  b.user_name AS userName\n" +
            "FROM\n" +
            "  (\n" +
            "    SELECT\n" +
            "      a.uuid         AS uuid,\n" +
            "      a.privilege_cd AS privilegeCd,\n" +
            "      a.supplier_cd  AS supplierCd,\n" +
            "      a.create_user  AS createUser,\n" +
            "      a.create_time  AS create_time\n" +
            "    FROM sys_privilege_supplier a\n" +
            "    WHERE a.privilege_cd = #{authId} \n" +
            "    ORDER BY a.create_time DESC\n" +
            "  )\n" +
            "    AS aa\n" +
            "  LEFT JOIN\n" +
            "  sys_user_info b ON\n" +
            "                    aa.createUser = b.user_id" +
            ""
    )
    List<GatherEntity> getPrivilegeSupplierAuth(String authId);

    @Insert("INSERT INTO sys_privilege_supplier(uuid, privilege_cd, supplier_cd,create_user,create_time) VALUES(#{uuid}, #{privilegeCd}, #{supplierCd}, #{createUser}, #{createTime})")
    void insert(SysPrivilegeSupplierEntity entity);

    @Delete("DELETE FROM sys_privilege_supplier WHERE privilege_cd = #{privilegeCd} and  supplier_cd = #{supplierCd}")
    void deletePrivilegeSupplierId(SysPrivilegeSupplierEntity entity);

    @Update("UPDATE sys_privilege_supplier SET create_user = #{createUser}, create_time = #{createTime} WHERE privilege_cd = #{privilegeCd} and  supplier_cd = #{supplierCd}")
    void update(SysPrivilegeSupplierEntity entity);
}
