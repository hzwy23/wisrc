package com.wisrc.purchase.webapp.dao;

import com.wisrc.purchase.webapp.dao.sql.OrderDetailsProductInfoSql;
import com.wisrc.purchase.webapp.entity.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface OrderDetailsProductInfoDao {
    /**
     * 通过订单号ID查询订单里相关的产品和相关信息
     *
     * @param orderId
     * @return
     */
    @Select("select id,order_id,sku_id,quantity,spare_rate,unit_price_without_tax,tax_rate,unit_price_with_tax,amount_without_tax,amount_with_tax,remark,delete_status from order_details_product_info where order_id = #{orderId}  ")
    List<OrderDetailsProductInfoEntity> findInfoById(@Param("orderId") String orderId);

    /**
     * 删除订单产品相关信息
     *
     * @return
     */
    @Delete("delete from order_details_product_info where id = #{id}")
    void delInfoById(@Param("id") String id);

    /**
     * 修改订单产品相关信息
     *
     * @param ele
     */
    @Update("update order_details_product_info set sku_id = #{skuId},quantity=#{quantity},spare_rate=#{spareRate},unit_price_without_tax=#{unitPriceWithoutTax},tax_rate=#{taxRate},unit_price_with_tax=#{unitPriceWithTax},amount_without_tax=#{amountWithoutTax},amount_with_tax=#{amountWithTax},remark=#{remark} where id=#{id}")
    void updateInfoById(OrderDetailsProductInfoEntity ele);

    /**
     * 新增订单产品相关信息
     *
     * @param ele
     */
    @Insert("insert into order_details_product_info (id,order_id,sku_id,quantity,spare_rate,unit_price_without_tax,tax_rate,unit_price_with_tax,amount_without_tax,amount_with_tax,remark,delete_status)" +
            "values (#{id},#{orderId},#{skuId},#{quantity},#{spareRate},#{unitPriceWithoutTax},#{taxRate},#{unitPriceWithTax},#{amountWithoutTax},#{amountWithTax},#{remark},0)")
    void addInfo(OrderDetailsProductInfoEntity ele);


    /**
     * 通过订单号里面对应的产品表的唯一ID查询装箱相关信息
     *
     * @param id
     * @return
     */
    @Select("select id,packing_rate,pack_long,pack_wide,pack_high,pack_volume,gross_weight from order_product_packing_info where id = #{id}  ")
    ProducPackingInfoEntity findPackInfo(@Param("id") String id);

    /**
     * 删除装箱相关信息
     *
     * @return
     */
    @Delete("delete from order_product_packing_info where id = #{id}")
    void delPackInfoById(@Param("id") String id);

    /**
     * 修改装箱相关信息
     *
     * @param ele
     */
    @Update("update order_product_packing_info set packing_rate = #{packingRate},pack_long=#{packLong},pack_wide=#{packWide},pack_high=#{packHigh},pack_volume=#{packVolume},gross_weight=#{grossWeight} where id=#{id}")
    void updatePackInfoById(ProducPackingInfoEntity ele);

    /**
     * 新增装箱相关信息
     *
     * @param ele
     */
    @Insert("insert into order_product_packing_info (id,packing_rate,pack_long,pack_wide,pack_high,pack_volume,gross_weight)" +
            "values (#{id},#{packingRate},#{packLong},#{packWide},#{packHigh},#{packVolume},#{grossWeight})")
    void addPackInfo(ProducPackingInfoEntity ele);


    /**
     * 通过订单号里面对应的产品表的唯一ID查询产品交货日期与数量相关信息
     *
     * @param id
     * @return
     */
    @Select("select uuid,id,delivery_time,number from order_product_delivery_info where id = #{id}  order by  delivery_time asc")
    List<ProductDeliveryInfoEntity> findDeliveryInfo(@Param("id") String id);

    /**
     * 删除交货日期与数量相关信息
     *
     * @return
     */
    @Delete("delete from order_product_delivery_info where id = #{id}")
    void delDeliveryById(@Param("id") String id);

    /**
     * 修改交货日期与数量相关信息
     *
     * @param ele
     */
    @Update("update order_product_delivery_info set delivery_time=#{deliveryTime},number=#{number} where uuid=#{uuid}")
    void updatDeliveryById(ProductDeliveryInfoEntity ele);

    /**
     * 新增交货日期与数量相关信息
     *
     * @param ele
     */
    @Insert("insert into order_product_delivery_info (uuid,id,delivery_time,number)" +
            "values (#{uuid},#{id},#{deliveryTime},#{number}) ")
    void addDelivery(ProductDeliveryInfoEntity ele);

    /**
     * 查询此订单下有多少条产品信息
     *
     * @param orderId
     * @return
     */
    @Select("select id from order_details_product_info where order_id = #{orderId}")
    List<String> idListById(@Param("orderId") String orderId);

    /**
     * 查询此订单下产品的SKU
     *
     * @param orderId
     * @return
     */
    @Select("select sku_id from order_details_product_info where order_id = #{orderId}")
    List<String> skuById(@Param("orderId") String orderId);

    /**
     * 查询此订单有无此产品
     *
     * @param id
     * @return
     */
    @Select("select count(*) from order_details_product_info where id = #{id}")
    int numById(@Param("id") String id);

    /**
     * 查询此订单此产品目前已经入库数量总和
     */
    @Select("SELECT quantity,sum(entry_num) as sumEntry FROM v_order_sku_quantity_entry  where order_sku_id = #{orderSkuId} and order_id= #{orderId} and delete_status=0")
    EntrySumNumEntity EntrySumNumEntity(@Param("orderSkuId") String orderSkuId, @Param("orderId") String orderId);

    /**
     * 查询此订单此产品目前已经退货数量总和
     */
    @Select("SELECT quantity,sum(return_quantity) as sumReturn FROM v_order_sku_quantity_return where order_sku_id = #{orderSkuId} and order_id= #{orderId} and delete_status=0")
    EntrySumNumReturn EntrySumNumReturn(@Param("orderSkuId") String orderSkuId, @Param("orderId") String orderId);

    /**
     * 查询此订单此产品目前已经拒收数量总和
     */
    @Select("SELECT quantity,sum(reject_quantity) as sumRejection FROM v_order_sku_quantity_rejection where order_sku_id=#{orderSkuId} and order_id= #{orderId} and delete_status=0")
    EntrySumNumRejection EntrySumNumRejection(@Param("orderSkuId") String orderSkuId, @Param("orderId") String orderId);

    /**
     * 中止产品信息
     */
    @Update("update order_details_product_info set delete_status=#{deleteStatus}  where id = #{id}")
    void updateProductStatus(@Param("id") String id, @Param("deleteStatus") int deleteStatus);

    @Select("select distinct sku_id, order_id, quantity from v_order_details_info where sku_id=#{skuId} and order_id=#{purchaseOrderId}")
    OrderDetailsProductInfoEntity getByOrderIdAndSkuId(@Param("skuId") String skuId, @Param("purchaseOrderId") String purchaseOrderId);

    @Update("UPDATE arrival_product_details_info SET receipt_quantity = #{quantity},receipt_spare_quantity = #{getSpareQuantity} WHERE arrival_id = #{arrivalId} AND sku_id = #{skuId}")
    void updateWmsData(@Param("arrivalId") String arrivalId,
                       @Param("skuId") String skuId,
                       @Param("quantity") Integer quantity,
                       @Param("getSpareQuantity") Integer getSpareQuantity);

    @Select("select amount_without_tax, amount_with_tax, order_id, sku_id, delete_status from order_details_product_info where sku_id=#{skuId} and order_id=#{tracingId}")
    OrderDetailsProductInfoEntity findInfoByIdAndSkuId(@Param("skuId") String skuId, @Param("tracingId") String tracingId);

    @Select("select order_id from order_details_product_info where id=#{id}")
    String getOrderId(String id);

    @SelectProvider(type = OrderDetailsProductInfoSql.class, method = "getQuantity")
    List<OrderDetailsProductInfoEntity> getQuantity(@Param("orderId") String orderId, @Param("skuId") List skuIds);
}
