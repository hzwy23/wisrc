package com.wisrc.supplier.webapp.dao;

import com.wisrc.supplier.webapp.entity.Supplier;
import com.wisrc.supplier.webapp.entity.SupplierAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface SupplierCommonDao {

    // 批量获取供应商列表
    @SelectProvider(type = SupplierSqlBuilder.class, method = "getSuppliersById")
    List<Supplier> getSuppliersById(@Param("supplierId") String[] supplierId);

    // 根据条件模糊查询供应商帐号
    @SelectProvider(type = SupplierSqlBuilder.class, method = "getSuppliersAccount")
    List<SupplierAccount> getSupplierAccount(@Param("sId") String sId, @Param("auditStatus") Integer auditStatus,
                                             @Param("type") Integer type, @Param("bank") String bank, @Param("payee") String payee);

}
