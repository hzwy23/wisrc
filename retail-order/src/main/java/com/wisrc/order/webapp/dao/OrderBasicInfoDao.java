package com.wisrc.order.webapp.dao;


import com.wisrc.order.webapp.dao.sql.SaleOrderInfoSql;
import com.wisrc.order.webapp.entity.OrderBasicInfoEntity;
import com.wisrc.order.webapp.vo.OrderInfoVO;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
import java.util.List;

@Mapper
public interface OrderBasicInfoDao {

    @Select("select order_id, original_order_id, trade_number, status_cd, plat_id, shop_id,pay_status_cd, pay_type_cd, iso_currency_cd, original_currency_cd, amount_money, amount_money_currency, freight, freight_currency, return_amount, return_amount_currency,insurance_amount, insurance_amount_currency, platform_freight, platform_freight_currency, transfer_expense, transfer_expense_currency, other_expenses, other_expenses_currency, receipt_account, payment_date, manual_creation,create_user, create_time, modify_user, modify_time, delete_status from order_basic_info")
    List<OrderBasicInfoEntity> findAll();

    @Select("select order_id, original_order_id, trade_number, status_cd, plat_id, shop_id,pay_status_cd, pay_type_cd, iso_currency_cd, original_currency_cd, amount_money, amount_money_currency, freight, freight_currency, return_amount, return_amount_currency,insurance_amount, insurance_amount_currency, platform_freight, platform_freight_currency, transfer_expense, transfer_expense_currency, other_expenses, other_expenses_currency, receipt_account, payment_date, manual_creation,create_user, create_time, modify_user, modify_time, delete_status from order_basic_info where order_id = #{orderId}")
    OrderBasicInfoEntity findById(@Param("orderId") String orderId);

    @Insert("insert into order_basic_info (order_id, original_order_id, trade_number, status_cd, plat_id, shop_id,pay_status_cd, pay_type_cd, original_currency_cd, amount_money, amount_money_currency, freight, freight_currency, return_amount, return_amount_currency,insurance_amount, insurance_amount_currency, platform_freight, platform_freight_currency, transfer_expense, transfer_expense_currency, other_expenses, other_expenses_currency, receipt_account, payment_date, manual_creation,create_user, create_time, modify_user, modify_time, delete_status) values(#{orderId}, #{originalOrderId}, #{tradeNumber}, #{statusCd}, #{platId}, #{shopId}, #{payStatusCd}, #{payTypeCd}, #{originalCurrencyCd}, #{amountMoney}, #{amountMoneyCurrency}, #{freight}, #{freightCurrency}, #{returnAmount}, #{returnAmountCurrency}, #{insuranceAmount}, #{insuranceAmountCurrency}, #{platformFreight}, #{platformFreightCurrency}, #{transferExpense}, #{transferExpenseCurrency}, #{otherExpenses}, #{otherExpensesCurrency}, #{receiptAccount}, #{paymentDate}, #{manualCreation}, #{createUser}, #{createTime}, #{modifyUser}, #{modifyTime}, #{deleteStatus})")
    void insert(OrderBasicInfoEntity ele);

    @Update("update order_basic_info set delete_status = 1 where order_id = #{orderId}")
    void deleteLogic(@Param("orderId") String orderId);

    @Update("update order_basic_info set status_cd = #{statusCd} where order_id = #{orderId}")
    void changeStatus(@Param("orderId") String orderId, @Param("statusCd") int statusCd);

    @Update("update order_basic_info set original_order_id = #{originalOrderId}, trade_number = #{tradeNumber}, status_cd = #{statusCd}, plat_id = #{platId}, shop_id = #{shopId}, pay_status_cd = #{payStatusCd}, pay_type_cd = #{payTypeCd}, iso_currency_cd = #{isoCurrencyCd}, original_currency_cd = #{originalCurrencyCd}, amount_money = #{amountMoney}, amount_money_currency = #{amountMoneyCurrency}, freight = #{freight}, freight_currency = #{freightCurrency}, return_amount = #{returnAmount}, return_amount_currency = #{returnAmountCurrency}, insurance_amount = #{insuranceAmount}, insurance_amount_currency = #{insuranceAmountCurrency}, platform_freight = #{platformFreight}, platform_freight_currency = #{platformFreightCurrency}, transfer_expense = #{transferExpense}, transfer_expense_currency = #{transferExpenseCurrency}, other_expenses = #{otherExpenses}, other_expenses_currency = #{otherExpensesCurrency}, receipt_account = #{receiptAccount}, payment_date = #{paymentDate}, manual_creation = #{manualCreation}, modify_user = #{modifyUser}, modify_time = #{modifyTime} where order_id = #{orderId}")
    void update(OrderBasicInfoEntity ele);

    @Select("SELECT order_id FROM matrix_sales.sale_order_info order by create_time desc limit 1")
    String getSONo();


    @SelectProvider(type = SaleOrderInfoSql.class, method = "findByCond")
    List<OrderInfoVO> findByCond(@Param("orderId") String orderId,
                                 @Param("originalOrderId") String originalOrderId,
                                 @Param("platId") String platId,
                                 @Param("shopId") String shopId,
                                 @Param("createTime") Date createTime,
                                 @Param("exceptTypeCd") String exceptTypeCd,
                                 @Param("statusCd") int statusCd,
                                 @Param("comIds") String comIds,
                                 @Param("countryCd") String countryCd);

}
