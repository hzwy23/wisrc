package com.wisrc.supplier.webapp.dao;

import com.wisrc.supplier.webapp.entity.SupplierAccount;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface SupplierAccountDao {

    // 获取供应商帐号列表
    @SelectProvider(type = SupplierSqlBuilder.class, method = "getSupplierAccounts")
    List<Map<String, Object>> getSupplierAccounts(@Param("supplierId") String supplierId, @Param("supplierName") String supplierName);

    // 供应商所有帐号
    @Select("SELECT * FROM supplier_account WHERE sId = #{sId}")
    List<SupplierAccount> getSupplierAccount(String sId);

    // 供应商帐号 添加
    @Insert("INSERT INTO supplier_account (sId,payee,bank,subbranch,account,type,diploma,auditStatus,mender,updateTime,createTime) VALUES (#{sId},#{payee},#{bank},#{subbranch},#{account},#{type},#{diploma},#{auditStatus},#{mender},#{updateTime},#{createTime})")
    boolean addSupplierAccount(SupplierAccount account);

    // 供应商帐号 更新
    @Update("UPDATE supplier_account SET payee=#{payee}, bank=#{bank}, subbranch=#{subbranch}, account=#{account}, type=#{type}, diploma=#{diploma}, auditStatus=#{auditStatus}, mender=#{mender}, updateTime=#{updateTime} WHERE id=#{id}")
    boolean setSupplierAccount(SupplierAccount account);

    // 供应商帐号 审核
    @Update("UPDATE supplier_account SET auditStatus=#{auditStatus}, auditInfo=#{auditInfo} WHERE id=#{id}")
    boolean verifySupplierAccount(@Param("id") Integer id, @Param("auditStatus") Integer auditStatus, @Param("auditInfo") String auditInfo);

    // 供应商帐号 删除
    @Delete("DELETE FROM supplier_account WHERE id=#{id}")
    boolean delSupplierAccount(Integer id);

}
