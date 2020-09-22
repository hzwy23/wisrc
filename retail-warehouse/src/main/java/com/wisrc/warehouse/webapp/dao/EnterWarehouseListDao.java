package com.wisrc.warehouse.webapp.dao;

import com.wisrc.warehouse.webapp.entity.EnterWarehouseListEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EnterWarehouseListDao {

    @Insert("insert into enter_warehouse_list (uuid, enter_bill_id, sku_id, fn_sku_id, warehouse_position, enter_warehouse_num) values " +
            "(#{uuid},#{enterBillId},#{skuId},#{fnSkuId}, #{warehousePosition},#{enterWarehouseNum})")
    void add(EnterWarehouseListEntity listEntity);

    @Select("select uuid, enter_bill_id, sku_id, fn_sku_id, warehouse_position, enter_warehouse_num，actual_enter_warehouse_num from enter_warehouse_list where enter_bill_id=#{enterBillId}")
    List<EnterWarehouseListEntity> getCommodityList(@Param("enterBillId") String enterBillId);
}
