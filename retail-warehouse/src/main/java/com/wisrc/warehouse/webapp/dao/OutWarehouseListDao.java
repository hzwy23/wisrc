package com.wisrc.warehouse.webapp.dao;

import com.wisrc.warehouse.webapp.entity.OutWarehouseListEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OutWarehouseListDao {
    @Insert("insert into out_warehouse_list (uuid, out_bill_id, sku_id, fn_sku_id, warehouse_position, out_warehouse_num) values " +
            "(#{uuid},#{outBillId},#{skuId}, #{fnSkuId}, #{warehousePosition},#{outWarehouseNum})")
    void add(OutWarehouseListEntity entity);

    @Select("select uuid, out_bill_id, sku_id, fn_sku_id, warehouse_position, out_warehouse_num from out_warehouse_list where out_bill_id=#{outBillId}")
    List<OutWarehouseListEntity> getCommodityList(@Param("outBillId") String outBillId);
}
