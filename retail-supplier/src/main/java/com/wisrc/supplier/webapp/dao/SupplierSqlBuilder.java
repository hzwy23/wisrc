package com.wisrc.supplier.webapp.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class SupplierSqlBuilder {

    // 获取供应商列表
    public String getSuppliers(@Param("supplierId") String supplierId, @Param("supplierName") String supplierName,
                               @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("status") Integer status) {
        return new SQL() {
            {
                SELECT("supplierId,supplierName,contacts,telephone,status,mender,updateTime,createTime");
                FROM("supplier_basic_info");
                ORDER_BY("supplierId DESC");
                if (supplierId != null) {
                    WHERE("supplierId LIKE #{supplierId}");
                }
                if (supplierName != null) {
                    WHERE("supplierName LIKE #{supplierName}");
                }
                if (startTime != null) {
                    WHERE("date(createTime) >= #{startTime}");
                }
                if (endTime != null) {
                    WHERE("date(createTime) <= #{endTime}");
                }
                if (status != null) {
                    WHERE("status = #{status}");
                }
            }
        }.toString();
    }

    // 获取供应商帐号列表
    public String getSupplierAccounts(@Param("supplierId") String supplierId,
                                      @Param("supplierName") String supplierName) {
        return new SQL() {
            {
                SELECT("supplierId,supplierName,supplierAccount.auditStatus,supplierAccount.mender,supplierAccount.updateTime");
                FROM("supplier_basic_info");
                LEFT_OUTER_JOIN("supplier_account supplierAccount ON supplierId = sId");
                ORDER_BY("supplierId DESC");
                if (supplierId != null) {
                    WHERE("supplierId LIKE #{supplierId}");
                }
                if (supplierName != null) {
                    WHERE("supplierName LIKE #{supplierName}");
                }
            }
        }.toString();
    }

    // 根据编号获取供应商列表
    public String getSuppliersById(@Param("supplierId") String[] supplierId) {
        return new SQL() {
            {
                SELECT("supplierId,supplierName,contacts,telephone,status,mender,updateTime,createTime");
                FROM("supplier_basic_info");
                ORDER_BY("supplierId DESC");
                for (String string : supplierId) {
                    OR();
                    WHERE("supplierId = '" + string.replaceAll("('|,)", "") + "'");
                }
            }
        }.toString();
    }

    // 根据条件模糊查询供应商帐号
    public String getSuppliersAccount(@Param("sId") String sId, @Param("auditStatus") Integer auditStatus,
                                      @Param("type") Integer type, @Param("bank") String bank, @Param("payee") String payee) {
        return new SQL() {
            {
                SELECT("id, sId, payee, bank, subbranch, account, type, diploma, auditStatus, auditInfo, mender, updateTime, createTime");
                FROM("supplier_account");
                ORDER_BY("sId DESC");
                if (sId != null) {
                    WHERE("sId LIKE #{sId}");
                }
                if (auditStatus != null) {
                    WHERE("auditStatus LIKE #{auditStatus}");
                }
                if (type != null) {
                    WHERE("type LIKE #{type}");
                }
                if (bank != null) {
                    WHERE("bank LIKE #{bank}");
                }
                if (payee != null) {
                    WHERE("payee LIKE #{payee}");
                }
            }
        }.toString();
    }

    /**
     * 检查纳税人识别号是否存在
     *
     * @param supplierId 如果不为<code>null</code>，那么排除该供应商
     * @param taxpayerIC 需要查询的纳税人识别号
     * @return
     */
    public String checkTaxpayerICUnique(@Param("supplierId") String supplierId,
                                        @Param("taxpayerIC") String taxpayerIC) {
        return new SQL() {
            {
                SELECT("count(1)");
                FROM("supplier_details");
                WHERE("taxpayerIC=#{taxpayerIC}");
                if (supplierId != null) {
                    WHERE("sId!=#{supplierId}");
                }
            }
        }.toString();
    }
}
