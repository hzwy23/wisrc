package com.wisrc.order.webapp.dao;


import com.wisrc.order.webapp.dao.sql.SaleNvoiceOrderInfoSql;
import com.wisrc.order.webapp.entity.OrderNvoiceRelation;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderNvoiceRelationDao {

    @Select("select uuid, invoice_number, order_id, original_order_id, create_time, create_user from order_nvoice_relation where invoice_number = #{invoiceNumber}")
    List<OrderNvoiceRelation> findByInvoiceNumber(@Param("invoiceNumber") String invoiceNumber);

    @Select("select uuid, invoice_number, order_id, original_order_id, create_time, create_user from order_nvoice_relation where order_id = #{orderId}")
    List<OrderNvoiceRelation> findByOrderId(@Param("orderId") String orderId);

    @Delete("delete from order_nvoice_relation where uuid = #{uuid}")
    void deleteById(@Param("uuid") String uuid);

    @Delete("delete from order_nvoice_relation where order_id = #{orderId}")
    void deleteByOrderId(@Param("orderId") String orderId);

    @Delete("delete from order_nvoice_relation where invoice_number = #{invoiceNumber}")
    void deleteByOrderInvoiceNumber(@Param("invoiceNumber") String invoiceNumber);

    @Insert("INSERT INTO order_nvoice_relation(uuid, invoice_number, order_id, create_time, create_user) VALUES(#{uuid}, #{invoiceNumber}, #{orderId}, #{createTime}, #{createUser})")
    void saveSaleNvoiceOrderInfo(OrderNvoiceRelation saleNvoiceOrderInfoEntity) throws Exception;

    @SelectProvider(type = SaleNvoiceOrderInfoSql.class, method = "getOrderIdByInvoice")
    List<OrderNvoiceRelation> getOrderIdByInvoice(@Param("invoiceNumbers") List invoiceNumbers) throws Exception;

    @Insert("insert into order_nvoice_relation (uuid, invoice_number, order_id, original_order_id, create_time, create_user) values " +
            "(#{uuid},#{invoiceNumber},#{orderId},#{originalOrderId},#{createTime},#{createUser})")
    void add(OrderNvoiceRelation ele);

}
