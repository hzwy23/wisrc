package com.wisrc.order.webapp.dao;

import com.wisrc.order.webapp.dao.sql.SaleNvoiceInfoSql;
import com.wisrc.order.webapp.entity.GetNvoiceOrderEntity;
import com.wisrc.order.webapp.entity.NvoiceOrderEntity;
import com.wisrc.order.webapp.entity.OrderNvoiceInfo;
import com.wisrc.order.webapp.query.NvoiceOrderPageQuery;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderNvoiceInfoDao {
    @Select("select invoice_number,status_cd, wms_wave_number, create_time, create_user, total_weight, shipment_id, logistics_id,freight from order_nvoice_info")
    List<OrderNvoiceInfo> findAll();

    @Select("select invoice_number,status_cd, wms_wave_number, create_time, create_user, total_weight, shipment_id, logistics_id,freight from order_nvoice_info where invoice_number = #{invoiceNumber}")
    OrderNvoiceInfo findById(@Param("invoiceNumber") String invoiceNumber);

    @Update("update order_nvoice_info set status_cd = #{statusCd} where invoice_number = #{invoiceNumber}")
    void changeStatus(@Param("statusCd") int statusCd, @Param("invoiceNumber") String invoiceNumber);

    @Update("update order_nvoice_info set status_cd = #{statusCd}, wms_wave_number = #{wmsWaveNumber}, total_weight = #{totalWeight}, shipment_id = #{shipmentId}, logistics_id = #{logisticsId}, freight = #{freight} where invoice_number = #{invoiceNumber}")
    void update(OrderNvoiceInfo ele);

    @SelectProvider(type = SaleNvoiceInfoSql.class, method = "saleNvoicePage")
    List<NvoiceOrderEntity> saleNvoicePage(NvoiceOrderPageQuery nvoiceOrderPageQuery) throws Exception;

    @Select("SELECT sni.invoice_number, sisa.status_desc, sni.create_time, soi.order_id, soi.original_order_id, total_weight, sli.offer_id, sli.logistics_id, sli.logistics_cost " +
            " FROM order_nvoice_info AS sni LEFT JOIN order_nvoice_relation AS snoi ON snoi.invoice_number = sni.invoice_number LEFT JOIN order_basic_info AS soi ON soi.order_id = snoi.order_id " +
            " LEFT JOIN order_invoice_status_attr AS sisa ON sisa.status_cd = sni.status_cd LEFT JOIN order_logistics_info AS sli ON sli.order_id = soi.order_id WHERE sni.invoice_number = #{invoiceNumber}")
    GetNvoiceOrderEntity getSaleNvoice(@Param("invoiceNumber") String invoiceNumber) throws Exception;

    @Insert("INSERT INTO order_nvoice_info(invoice_number, status_cd, create_time, create_user, total_weight) VALUES(#{invoiceNumber}, #{statusCd}, #{createTime}, #{createUser}, #{totalWeight})")
    void saveSaleNvoice(OrderNvoiceInfo saleNvoiceInfoEntity) throws Exception;

    @SelectProvider(type = SaleNvoiceInfoSql.class, method = "saleNvoiceByOrder")
    List<NvoiceOrderEntity> saleNvoiceByOrder(@Param("orderIds") List orderIds) throws Exception;

    @Select("SELECT MAX(RIGHT(invoice_number, 6)) FROM order_nvoice_info WHERE invoice_number LIKE concat('%SP', #{dateId}, '%')  ")
    String getMaxId(@Param("dateId") String dateId) throws Exception;

    @Insert("insert into order_nvoice_info (invoice_number, status_cd, wms_wave_number, create_time, create_user, total_weight, shipment_id, logistics_id, freight) values " +
            "(#{invoiceNumber},#{statusCd},#{wmsWaveNumber},#{createTime},#{createUser},#{totalWeight},#{shipmentId},#{logisticsId},#{freight})")
    void add(OrderNvoiceInfo info);
}
