package com.wisrc.purchase.webapp.dao;

import com.wisrc.purchase.webapp.dao.sql.SupplierDateOfferSQL;
import com.wisrc.purchase.webapp.entity.GetGeneralDelivery;
import com.wisrc.purchase.webapp.entity.SupplierDateOfferEntity;
import com.wisrc.purchase.webapp.entity.TeamStatusAttrEntity;
import com.wisrc.purchase.webapp.query.supplierOffer.SupplierOfferPageQuery;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SupplierDateOfferDao {
    /**
     * 通过条件查询供应商交期及报价信息
     *
     * @param employeeId
     * @param supplierId
     * @param skuId
     * @param statusCd
     * @return
     */
    @SelectProvider(type = SupplierDateOfferSQL.class, method = "findInfo")
    List<SupplierDateOfferEntity> findInfo(@Param("employeeId") final String employeeId,
                                           @Param("supplierId") final String supplierId,
                                           @Param("skuId") final String skuId,
                                           @Param("statusCd") final int statusCd);

    /**
     * ID查询信息
     *
     * @param supplierOfferId
     * @return
     */
    @Select("select supplier_offer_id,status_cd,employee_id,sku_id,supplier_id,first_delivery,general_delivery,unit_price_without_tax,minimum,haulage_days,delivery_part,delivery_optimize,remark,create_user,create_time,modify_user,modify_time,delete_status from supplier_date_offer_info where supplier_offer_id =#{supplierOfferId}")
    SupplierDateOfferEntity findInfoById(@Param("supplierOfferId") String supplierOfferId);

    /**
     * 根据供应商ID查询上一次选择供应商时候的运输时间-国内
     *
     * @param supplierId
     * @return
     */
    @Select("select supplier_offer_id,status_cd,employee_id,sku_id,supplier_id,first_delivery,general_delivery,unit_price_without_tax,minimum,haulage_days,delivery_part,delivery_optimize,remark,create_user,max(create_time) create_time,modify_user,modify_time,delete_status from supplier_date_offer_info where supplier_id =#{supplierId}")
    SupplierDateOfferEntity findInfoBySupplier(@Param("supplierId") String supplierId);

    /**
     * 修改全表信息
     *
     * @param supplierDateOfferEntity
     */
    @Update("update supplier_date_offer_info set employee_id=#{employeeId},sku_id =#{skuId},supplier_id=#{supplierId},first_delivery=#{firstDelivery},general_delivery=#{generalDelivery},unit_price_without_tax=#{unitPriceWithoutTax},minimum=#{minimum},haulage_days=#{haulageDays},delivery_part=#{deliveryPart},delivery_optimize=#{deliveryOptimize},remark=#{remark},create_user=#{createUser},create_time=#{createTime},modify_user=#{" +
            "modifyUser},modify_time=#{modifyTime},delete_status=#{deleteStatus} where supplier_offer_id =#{supplierOfferId}")
    void updateInfo(SupplierDateOfferEntity supplierDateOfferEntity);

    /**
     * 逻辑删除
     *
     * @param supplierDateOfferEntity
     */
    @Update("update supplier_date_offer_info set modify_user=#{modifyUser},modify_time=#{modifyTime},delete_status=#{deleteStatus} where supplier_offer_id =#{supplierOfferId}")
    void delInfo(SupplierDateOfferEntity supplierDateOfferEntity);

    /**
     * 变更采购员
     *
     * @param supplierDateOfferEntity
     */
    @Update("update supplier_date_offer_info set modify_user=#{modifyUser},modify_time=#{modifyTime},employee_id=#{employeeId} where supplier_offer_id =#{supplierOfferId}")
    void upEmployee(SupplierDateOfferEntity supplierDateOfferEntity);

    /**
     * 变更状态
     *
     * @param supplierDateOfferEntity
     */
    @Update("update supplier_date_offer_info set modify_user=#{modifyUser},modify_time=#{modifyTime},status_cd=#{statusCd} where supplier_offer_id =#{supplierOfferId}")
    void upStatus(SupplierDateOfferEntity supplierDateOfferEntity);

    /**
     * 变更运输时间
     *
     * @param supplierDateOfferEntity
     */
    @Update("update supplier_date_offer_info set modify_user=#{modifyUser},modify_time=#{modifyTime},haulage_days=#{haulageDays} where supplier_offer_id =#{supplierOfferId}")
    void upDate(SupplierDateOfferEntity supplierDateOfferEntity);

    /**
     * 新增信息
     *
     * @param supplierDateOfferEntity
     */
    @Insert("insert into supplier_date_offer_info(supplier_offer_id,status_cd,employee_id,sku_id,supplier_id,first_delivery,general_delivery,unit_price_without_tax,minimum,haulage_days,delivery_part,delivery_optimize,remark,create_user,create_time,modify_user,modify_time,delete_status) values (#{supplierOfferId},#{statusCd},#{employeeId},#{skuId},#{supplierId},#{firstDelivery},#{generalDelivery},#{unitPriceWithoutTax},#{minimum},#{haulageDays},#{deliveryPart},#{deliveryOptimize},#{remark},#{createUser},#{createTime},#{modifyUser},#{modifyTime},#{deleteStatus})")
    void insertInfo(SupplierDateOfferEntity supplierDateOfferEntity);

    /**
     * 删除信息
     *
     * @param supplierOfferId
     */
    @Delete("delete from supplier_date_offer_info where supplier_offer_id =#{supplierOfferId}")
    void deleteInfo(@Param("supplierOfferId") String supplierOfferId);

    /**
     * 查询数据库跟踪号最大的Id
     *
     * @return
     */
    @Select("SELECT MAX(SUBSTRING(supplier_offer_id,3,9)) supplierOfferId FROM supplier_date_offer_info")
    String findMaxId();

    /**
     * 查询状态码表
     *
     * @return
     */
    @Select("SELECT status_cd,status_desc FROM team_status_attr")
    List<TeamStatusAttrEntity> findTeamAttr();

    /**
     * 判断供应商交期及报价限制库存sku和供应商是否同时重复
     *
     * @param supplierId
     * @param skuId
     * @return
     */
    @Select("select count(*) from supplier_date_offer_info where sku_id =#{skuId} and supplier_id =#{supplierId} ")
    int findRepeat(@Param("supplierId") String supplierId, @Param("skuId") String skuId);

    @SelectProvider(type = SupplierDateOfferSQL.class, method = "getRecentDelivery")
    List<GetGeneralDelivery> getRecentDelivery(@Param("skuIds") List skuIds);

    /**
     * 通过条件查询供应商交期及报价信息
     *
     * @return
     */
    @SelectProvider(type = SupplierDateOfferSQL.class, method = "supplierOfferPage")
    List<SupplierDateOfferEntity> supplierOfferPage(SupplierOfferPageQuery queryPojo);
}
