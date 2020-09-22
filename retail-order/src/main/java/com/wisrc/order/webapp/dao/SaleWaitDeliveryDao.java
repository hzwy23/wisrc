package com.wisrc.order.webapp.dao;

import com.wisrc.order.webapp.entity.OrderBasicInfoEntity;
import com.wisrc.order.webapp.entity.OrderCommodityInfoEntity;
import com.wisrc.order.webapp.entity.OrderCustomerInfoEntity;
import com.wisrc.order.webapp.entity.OrderLogisticsInfo;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;


@Mapper
public interface SaleWaitDeliveryDao {
    //待配货订单流转成配货中订单
    @Update("update order_basic_info set status_cd = 4 where order_id=#{orderId}")
    void updateWaitStatus(@Param("orderId") String orderId);

    //修改订单商品数量
    @Update("update order_commodity_info set quantity = #{quantity},modify_user =#{modifyUser},modify_time=#{modifyTime} where uuid = #{uuid}")
    void updateNum(@Param("uuid") String uuid, @Param("quantity") int quantity, @Param("modifyUser") String modifyUser, @Param("modifyTime") Timestamp modifyTime);

    //逻辑删除商品
    @Update("update order_commodity_info set delete_status = 1,modify_user =#{modifyUser},modify_time=#{modifyTime} where uuid = #{uuid}")
    void delete(@Param("uuid") String uuid, @Param("modifyUser") String modifyUser, @Param("modifyTime") Timestamp modifyTime);

    //新增订单
    @Insert("insert into order_commodity_info (uuid, order_id, commodity_id, attribute_desc, unit_price, unit_price_currency, quantity, weight, warehouse_id, status_cd, create_time, create_user, modify_user, modify_time, delete_status) values " +
            "(#{uuid},#{orderId},#{commodityId},#{attributeDesc},#{unitPrice},#{unitPriceCurrency},#{quantity},#{weight},#{warehouseId},#{statusCd},#{createTime},#{createUser},#{modifyUser},#{modifyTime},#{deleteStatus})")
    void add(OrderCommodityInfoEntity entity);

    //查看商品详情信息
    @Select("select uuid, order_id, commodity_id, attribute_desc, unit_price, unit_price_currency, quantity, weight, warehouse_id, status_cd, create_time, create_user, modify_user, modify_time, delete_status " +
            "from order_commodity_info where uuid = #{uuid} and delete_status = 0")
    OrderCommodityInfoEntity getEntity(@Param("uuid") String uuid);

    //新增拆分销售客户信息
    @Insert("insert into order_customer_info (order_id, customer_id, consignee, zip_code, contact_one, contact_two, mailbox, country_cd, province_name, city_name, details_addr, buyer_message) values " +
            "(#{orderId},#{customerId},#{consignee},#{zipCode},#{contactOne},#{contactTwo},#{mailbox},#{countryCd},#{provinceName},#{cityName},#{detailsAddr},#{buyerMessage})")
    void addCustomer(OrderCustomerInfoEntity entity);

    @Update("update order_customer_info set customer_id=#{customerId},consignee=#{consignee},zip_code=#{zipCode},contact_one=#{contactOne},contact_two=#{contactTwo},mailbox=#{mailbox} " +
            "country_cd=#{countryCd},province_name=#{provinceName},city_name=#{cityName},details_addr=#{detailsAddr},buyer_message=#{buyerMessage} where order_id=#{orderId}")
    void updateCustomer(OrderCustomerInfoEntity entity);

    //查询拆分销售客户信息（通过ID）
    @Select("select order_id, customer_id, consignee, zip_code, contact_one, contact_two, mailbox, country_cd, province_name, city_name, details_addr, buyer_message from order_customer_info " +
            "where order_id=#{orderId}")
    OrderCustomerInfoEntity getCustomerById(@feign.Param("orderId") String orderId);

    //新增拆分销售订单信息
    @Insert("insert into order_basic_info (order_id, original_order_id, trade_number, status_cd, plat_id, shop_id, pay_status_cd, iso_currency_cd, original_currency_cd, amount_money, amount_money_currency, freight, freight_currency, return_amount, return_amount_currency, insurance_amount, insurance_amount_currency, platform_freight, platform_freight_currency, transfer_expense, transfer_expense_currency, other_expenses, other_expenses_currency, receipt_account, payment_date,  delete_status, create_user, create_time, modify_user, modify_time) values " +
            " (#{orderId},#{originalOrderId},#{tradeNumber},#{statusCd},#{platId},#{shopId},#{payStatusCd},#{isoCurrencyCd},#{originalCurrencyCd},#{amountMoney},#{amountMoneyCurrency},#{freight},#{freightCurrency},#{returnAmount},#{returnAmountCurrency},#{insuranceAmount},#{insuranceAmountCurrency},#{platformFreight},#{platformFreightCurrency},#{transferExpense},#{transferExpenseCurrency},#{otherExpenses},#{otherExpensesCurrency},#{receiptAccount},#{paymentDate},#{deleteStatus},#{createUser},#{createTime},#{modifyUser},#{modifyTime})")
    void addOrder(OrderBasicInfoEntity entity);

    //查询销售订单信息（通过ID）
    @Select("select order_id, original_order_id, trade_number, status_cd, plat_id, shop_id, pay_status_cd, iso_currency_cd, " +
            " original_currency_cd, amount_money, amount_money_currency, freight, freight_currency, return_amount, return_amount_currency, " +
            " insurance_amount, insurance_amount_currency, platform_freight, platform_freight_currency, transfer_expense, transfer_expense_currency," +
            " other_expenses, other_expenses_currency, receipt_account, payment_date, delete_status, create_user, create_time, modify_user, modify_time " +
            " from order_basic_info where order_id=#{orderId} and delete_status = 0")
    OrderBasicInfoEntity getOrderById(@Param("orderId") String orderId);

    //新增拆分后销售物流信息
    @Insert("insert into order_logistics_info (order_id, delivery_channel_cd, offer_id, logistics_id, logistics_cost, weight, delivery_date, delivery_remark) values " +
            "(#{orderId},#{deliveryChannelCd},#{offerId},#{logisticsId},#{logisticsCost},#{weight},#{deliveryDate},#{deliveryRemark})")
    void addLogistics(OrderLogisticsInfo entity);

    //查询销售物流信息（通过ID）
    @Select("select order_id, delivery_channel_cd, offer_id, logistics_id, logistics_cost, weight, delivery_date, delivery_remark from order_logistics_info " +
            "where order_id=#{orderId}")
    OrderLogisticsInfo getLogisticsById(@Param("orderId") String orderId);

    //批量作废与作废
    @Update("update order_basic_info set status_cd = 6,modify_user =#{modifyUser},modify_time=#{modifyTime} where order_id = #{orderId}")
    void invalid(@Param("statusCd") int statusCd, @Param("orderId") String orderId, @Param("modifyUser") String modifyUser, @Param("modifyTime") Timestamp modifyTime);


    @Select("SELECT MAX(CAST(SUBSTRING(order_id,  INSTR(order_id, '-') + 1) as UNSIGNED INTEGER)) order_id FROM order_basic_info where order_id like concat('%',#{orderId},'%')")
    String maxId(@Param("orderId") String orderId);

}


