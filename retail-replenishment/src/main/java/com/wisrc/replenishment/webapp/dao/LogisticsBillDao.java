package com.wisrc.replenishment.webapp.dao;

import com.wisrc.replenishment.webapp.dao.sql.LogisticsBillSql;
import com.wisrc.replenishment.webapp.entity.*;
import com.wisrc.replenishment.webapp.query.logisticBill.FindDetailQuery;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface LogisticsBillDao {

    @Insert("insert into customs_clearance_invoice_info(waybill_id,sell_company_name,sell_contact,send_address,buy_company_name,vat_no,receive_address,invoice_remark,declare_kind_cnt,declare_quantity,declare_amount,delete_status) values(#{waybillId},#{sellCompanyName},#{sellContact},#{sendAddress},#{buyCompanyName},#{vatNo},#{receiveAddress},#{invoiceRemark},#{declareKindCnt},#{declareQuantity},#{declareAmount},#{deleteStatus})")
    void addBill(LogisticsBillEnity billEnity);

    @Insert("insert into customs_clearance_product_history(uuid,shop_id,msku_id,clearance_name,origin_place,property,use_way,declare_unit_price,create_time,create_user) values(#{uuid},#{shopId},#{mskuId},#{clearanceName},#{originPlace},#{property},#{useWay},#{declareUnitPrice},#{createTime},#{createUser})")
    void addHisProDetail(ProductDetailsEnity detailsEnity);

    @Select("select fba_replenishment_id from waybill_replenishment_rel where waybill_id = #{waybillId}")
    List<String> findByShipwWaybillId(String waybillId);

    @Update("update replenishment_msku_info set country_of_origin = #{originPlace},clearance_name=#{clearanceName}, texture_of_materia = #{property}, purpose_desc = #{userWay}, clearance_unit_price = #{declareUnitPrice}, clearance_subtotal = #{singleProductTotal} where fba_replenishment_id = #{replenishmentID} and msku_id = #{mskuId}")
    void updateMskInfo(Map map);

    @Select("select waybill_id,sell_company_name,sell_contact,send_address,buy_company_name,vat_no,receive_address,invoice_remark,declare_kind_cnt,declare_quantity,declare_amount from customs_clearance_invoice_info where waybill_id = #{waybillId} and delete_status = 0")
    LogisticsBillEnity findBillByWayBiilId(String waybillId);

    @Select("select shop_id,msku_id,clearance_name,origin_place,property,use_way,declare_unit_price,create_time,create_user from customs_clearance_product_history where shop_id = #{shopId} and msku_id = #{mskuId} order by create_time desc limit 0,1")
    ProductDetailsEnity findDatailByShopIdAndMskuID(@Param("shopId") String shopId, @Param("mskuId") String mskuId);

    @SelectProvider(type = LogisticsBillSql.class, method = "findDatail")
    List<ProductDetailsEnity> findDatail(FindDetailQuery findDetail);

    @Update("update customs_clearance_invoice_info set sell_company_name = #{sellCompanyName}, sell_contact = #{sellContact}, send_address = #{sendAddress}, buy_company_name = #{buyCompanyName}, vat_no = #{vatNo}, receive_address = #{receiveAddress}, invoice_remark = #{invoiceRemark}, declare_kind_cnt = #{declareKindCnt}, declare_quantity = #{declareQuantity}, declare_amount = #{declareAmount} where waybill_id = #{waybillId}")
    void updateBillEnity(LogisticsBillEnity billEnity);

    @Update("update customs_clearance_product_history set clearance_name = #{clearanceName}, origin_place = #{originPlace}, property = #{property}, use_way = #{useWay}, declare_unit_price = #{declareUnitPrice}, create_time = #{createTime}, create_user = #{createUser} where shop_id = #{shopId} and msku_id = #{mskuId}")
    void updateBillProductHis(ProductDetailsEnity pro);

    @Delete("delete from customs_clearance_invoice_info where waybill_id= #{wayBillId}")
    void deletByWayBillId(String wayBillId);

    @Delete("delete from customs_clearance_product_history where shop_id=#{shopId} and msku_id=#{mskuId}")
    void deleteClearanceProduct(@Param(value = "shopId") String shopId, @Param(value = "mskuId") String mskuId);

    @Select("select commodity_id, shop_id, msku_id,warehouse_id,replenishment_quantity from v_replenishment_commodity_info where fba_replenishment_id = #{repleId}")
    List<ProductDetailsEnity> findDatailByRepleId(String repleId);

    @Update("update waybill_info set is_lack_customs_clearance=#{isLackCustomsClearance} where waybill_id=#{waybillId} ")
    void updateWayBillInfo(@Param("isLackCustomsClearance") int isLackCustomsClearance, @Param("waybillId") String waybillId);

    @Select("SELECT sell_company_name, buy_company_name, sell_contact, send_address, buy_company_name, vat_no, receive_address, invoice_remark, declare_quantity, declare_amount, " +
            "create_time  FROM customs_clearance_invoice_info AS ccii LEFT JOIN waybill_info AS wi ON wi.waybill_id = ccii.waybill_id WHERE ccii.waybill_id = #{wayBillId}")
    WaybillExcelEntity getWaybillExcel(@Param("wayBillId") String wayBillId);

    @Select("SELECT rmi.replenishment_quantity, country_of_origin, texture_of_materia, purpose_desc, clearance_name, clearance_unit_price, clearance_subtotal, declare_unit_price, customs_number, rmi.msku_id, shop_id" +
            " FROM waybill_replenishment_rel AS wrr LEFT JOIN  replenishment_msku_info AS rmi ON wrr.fba_replenishment_id = rmi.fba_replenishment_id LEFT JOIN v_clearance_customer_number " +
            "AS vccn ON vccn.msku_id = rmi.msku_id  WHERE waybill_id = #{wayBillId} ")
    List<ClearanceExcelEntity> getClearanceExcel(@Param("wayBillId") String wayBillId);

    @Select("SELECT customs_number, declaration_elements, net_weight, gross_weight, packing_quantity, number_of_boxes, packing_weight, msku_unit_name, declare_unit_price, declare_subtotal " +
            " FROM waybill_replenishment_rel AS wrr LEFT JOIN replenishment_msku_info AS rmi ON wrr.fba_replenishment_id = rmi.fba_replenishment_id LEFT JOIN v_clearance_customer_number " +
            "AS vccn ON vccn.msku_id = rmi.msku_id LEFT JOIN replenishment_msku_pack_info AS rmpi ON rmpi.replenishment_commodity_id = rmi.replenishment_commodity_id LEFT JOIN declare_msk_unit_attr AS dmua " +
            "ON dmua.msku_unit_cd = rmi.msku_unit_cd WHERE waybill_id = #{wayBillId} AND wrr.delete_status = 0  AND rmpi.delete_status = 0"
    )
    List<ReplenishmentExcelEntity> getReplenishmentExcel(@Param("wayBillId") String wayBillId);

    @Select("select fba_replenishment_id from waybill_replenishment_rel where waybill_id = #{wayBillId}")
    String getFbareplenishimentIdByWayBillId(@Param("wayBillId") String wayBillId);

}





