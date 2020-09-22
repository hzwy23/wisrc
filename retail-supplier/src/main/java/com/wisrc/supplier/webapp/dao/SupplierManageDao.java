package com.wisrc.supplier.webapp.dao;

import com.wisrc.supplier.webapp.entity.Supplier;
import com.wisrc.supplier.webapp.entity.SupplierInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface SupplierManageDao {

    // 获取供应商列表
    @SelectProvider(type = SupplierSqlBuilder.class, method = "getSuppliers")
    List<Supplier> getSuppliers(@Param("supplierId") String supplierId, @Param("supplierName") String supplierName,
                                @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("status") Integer status);

    // 获取供应商最新编号
    @Select("SELECT supplierId FROM supplier_basic_info ORDER BY supplierId DESC LIMIT 1")
    List<String> getSupplierId();

    // 供应商基础 查询
    @Select("SELECT * FROM supplier_basic_info WHERE supplierId = #{supplierId}")
    List<Supplier> getSupplier(String sId);

    // 供应商基础 添加
    @Insert("INSERT INTO supplier_basic_info (supplierId,supplierName,contacts,telephone,status,mender,updateTime,createTime) VALUES (#{supplierId},#{supplierName},#{contacts},#{telephone},#{status},#{mender},#{updateTime},#{createTime})")
    boolean addSupplier(Supplier supplier);

    // 供应商基础 更新
    @Update("UPDATE supplier_basic_info SET supplierName=#{supplierName},contacts=#{contacts},telephone=#{telephone},mender=#{mender},updateTime=#{updateTime} WHERE supplierId=#{supplierId}")
    boolean setSupplier(Supplier supplier);

    // 供应商状态 更新
    @Update("UPDATE supplier_basic_info SET status = #{status} WHERE supplierId = #{supplierId}")
    boolean setSupplierStatus(@Param("supplierId") String supplierId, @Param("status") Integer status);

    // 供应商详情 查询
    @Select("SELECT * FROM supplier_details WHERE sId = #{sId}")
    List<SupplierInfo> getSupplierInfo(String sId);

    // 供应商详情 添加
    @Insert("INSERT INTO supplier_details (sId,taxpayerIC,cellphone,mailbox,qQ,webSite,paymentType,vatInvoice,taxPoint,drawback,companyNature,cCC,rD,registeredCapital,capacity,qualitySystem,mainCustomer,mainProducts,mainMarket,employees,factorySize,technicians,province,city,county,street) VALUES (#{sId},#{taxpayerIC},#{cellphone},#{mailbox},#{qQ},#{webSite},#{paymentType},#{vatInvoice},#{taxPoint},#{drawback},#{companyNature},#{cCC},#{rD},#{registeredCapital},#{capacity},#{qualitySystem},#{mainCustomer},#{mainProducts},#{mainMarket},#{employees},#{factorySize},#{technicians},#{province},#{city},#{county},#{street})")
    boolean addSupplierInfo(SupplierInfo info);

    // 供应商详情 更新
    @Update("UPDATE supplier_details SET taxpayerIC=#{taxpayerIC},cellphone=#{cellphone},mailbox=#{mailbox},qQ=#{qQ},webSite=#{webSite},paymentType=#{paymentType},vatInvoice=#{vatInvoice},taxPoint=#{taxPoint},drawback=#{drawback},companyNature=#{companyNature},cCC=#{cCC},rD=#{rD},registeredCapital=#{registeredCapital},capacity=#{capacity},qualitySystem=#{qualitySystem},mainCustomer=#{mainCustomer},mainProducts=#{mainProducts},mainMarket=#{mainMarket},employees=#{employees},factorySize=#{factorySize},technicians=#{technicians},province=#{province},city=#{city},county=#{county},street=#{street} WHERE sId=#{sId}")
    boolean setSupplierInfo(SupplierInfo supplierInfo);


    /**
     * 检查纳税人识别号是否存在
     *
     * @param supplierId 如果不为<code>null</code>，那么排除该供应商
     * @param taxpayerIC 需要查询的纳税人识别号
     * @return
     */
    @SelectProvider(type = SupplierSqlBuilder.class, method = "checkTaxpayerICUnique")
    int checkTaxpayerICUnique(@Param("supplierId") String supplierId,
                                  @Param("taxpayerIC") String taxpayerIC);
}
