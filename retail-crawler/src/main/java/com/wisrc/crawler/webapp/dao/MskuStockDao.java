package com.wisrc.crawler.webapp.dao;

import com.wisrc.crawler.webapp.entity.ShipmentStockEnity;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface MskuStockDao {

    @Insert("insert into msku_stock_info (seller_id, fnsku, data_time, reserved_customer_orders, reserved_fc_transfers, reserved_fc_processing, afn_fulfillable_quantity, afn_unsellable_quantity) values " +
            "(#{sellerId},#{fnsku},#{dataDt},#{reservedCustomerorders},#{reservedFcTransfers},#{reservedFcProcessing},#{afnFulfillableQuantity},#{afnUnsellableQuantity})")
    void insert(Map mskuStockMap);

    @Delete("delete from msku_stock_info where fnsku=#{fnsku} and seller_id=#{sellerId}")
    void delete(@Param("fnsku") String fnsku, @Param("sellerId") String sellerId);

    @Select("select seller_id, msku, data_time, reserved_customer_orders, reserved_fc_transfers, reserved_fc_processing, afn_fulfillable_quantity, afn_unsellable_quantity from msku_stock_info where data_time=#{dateTime}")
    List<ShipmentStockEnity> getAllStockByTime(String dateTime);
}
