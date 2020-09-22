package com.wisrc.warehouse.webapp.dao;

import com.wisrc.warehouse.webapp.dao.sql.WarehouseOutEnterInfoSQL;
import com.wisrc.warehouse.webapp.entity.WarehouseOutEnterInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface WarehouseOutEnterInfoDao {

    @SelectProvider(type = WarehouseOutEnterInfoSQL.class, method = "search")
    List<WarehouseOutEnterInfoEntity> search(@Param("documentType") String documentType,
                                             @Param("outEnterType") int outEnterType,
                                             @Param("warehouseId") String warehouseId,
                                             @Param("createTimeBegin") Timestamp createTimeBegin,
                                             @Param("createTimeEnd") Timestamp createTimeEnd,
                                             @Param("keyWord") String keyWord);

    @Select("select sku_id,sku_name,fn_sku_id, a.warehouse_id,b.warehouse_name,warehouse_position,enter_batch,production_batch,change_ago_num,change_num,change_later_num, " +
            " source_id,document_type,a.create_time,a.create_user,a.remark FROM out_enter_warehouse_water a left join warehouse_basis_info b " +
            " on a.warehouse_id = b.warehouse_id")
    List<WarehouseOutEnterInfoEntity> getWarehouseOutEnterInfoAll();


    /**
     * 根据skuId和仓库Id查询出入库流水
     *
     * @param skuId
     * @param warehouseId
     * @return
     */
    @Select("select sku_id,sku_name,fn_sku_id, a.warehouse_id,b.warehouse_name,warehouse_position,enter_batch,production_batch,change_ago_num,change_num,change_later_num, " +
            "source_id,document_type,a.create_time,a.create_user,a.remark FROM out_enter_warehouse_water a left join warehouse_basis_info b " +
            " on a.warehouse_id = b.warehouse_id")
    List<WarehouseOutEnterInfoEntity> getStockWater(@Param("skuId") String skuId, @Param("warehouseId") String warehouseId);
}
