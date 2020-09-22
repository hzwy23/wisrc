package com.wisrc.supplier.webapp.dao;

import com.wisrc.supplier.webapp.entity.SupplierAnnex;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SupplierDocumentDao {

    // 供应商附件 查询
    @Select("SELECT * FROM supplier_annex WHERE sid = #{sId}")
    List<SupplierAnnex> getSupplierAnnex(String sId);

    // 供应商附件 添加
    @Insert("INSERT INTO supplier_annex (sId,type,name,path) VALUES (#{sId},#{type},#{name},#{path})")
    boolean addSupplierAnnex(SupplierAnnex annex);

    // 供应商附件 更新
    @Update("UPDATE supplier_annex SET name=#{name} WHERE id=#{id}")
    boolean setSupplierAnnex(@Param("name") String name, @Param("id") Integer id);

    // 供应商附件 删除
    @Delete("DELETE FROM supplier_annex WHERE id=#{id}")
    boolean delSupplierAnnex(Integer id);

}
