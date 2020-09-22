package com.wisrc.warehouse.webapp.dao;


import com.wisrc.warehouse.webapp.entity.WarehouseSeparateDetailsInfoEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface WarehouseSeparateDetailsInfoDao {

    /**
     * 通过仓库id获得分仓信息
     *
     * @param warehouseId
     * @return
     */
    @Select("select sub_warehouse_id, warehouse_id, separate_warehouse_name,  delete_status FROM warehouse_separate_details_info where warehouse_id = #{warehouseId} order by sub_warehouse_id desc")
    List<WarehouseSeparateDetailsInfoEntity> findByWareHouseId(@Param("warehouseId") String warehouseId);

    /**
     * 通过分仓id获得信息
     *
     * @param subWarehouseId
     * @return
     */
    @Select("select sub_warehouse_id, warehouse_id, separate_warehouse_name,  delete_status FROM warehouse_separate_details_info where sub_warehouse_id = #{subWarehouseId}")
    WarehouseSeparateDetailsInfoEntity findBySeparateWareHouseId(@Param("subWarehouseId") String subWarehouseId);

    @Select("select sub_warehouse_id from warehouse_separate_details_info where random_value = #{randomValue}")
    String findIdByRandomValue(String randomValue);

    @Select("select sub_warehouse_id, warehouse_id, separate_warehouse_name,  delete_status FROM warehouse_separate_details_info where warehouse_id = #{warehouseId} and delete_status = 0 order by sub_warehouse_id desc")
    List<WarehouseSeparateDetailsInfoEntity> findByIdExceptDelete(@Param("warehouseId") String warehouseId);

    @Update("update warehouse_separate_details_info set separate_warehouse_name = #{separateWarehouseName}, modify_time = #{modifyTime}, modify_user = #{modifyUser} where sub_warehouse_id = #{subWarehouseId}")
    void update(WarehouseSeparateDetailsInfoEntity ele);

    @Insert("insert into warehouse_separate_details_info(sub_warehouse_id, warehouse_id, separate_warehouse_name, delete_status, create_user,create_time, modify_user, modify_time, random_value) values(#{subWarehouseId}, #{warehouseId}, #{separateWarehouseName}, #{deleteStatus}, #{createUser}, #{createTime}, #{modifyUser}, #{modifyTime}, #{randomValue})")
    void insert(WarehouseSeparateDetailsInfoEntity ele);

    @Update("update warehouse_separate_details_info set delete_status = #{deleteStatus}, modify_user = #{modifyUser}, modify_time = #{modifyTime} where sub_warehouse_id = #{subWarehouseId}")
    void changeDeleteStauts(WarehouseSeparateDetailsInfoEntity ele);

    @Select("select max(sub_warehouse_id)  from warehouse_separate_details_info where warehouse_id = #{warehouseId}")
    String getMaxSubwarehouse(String warehouseId);
}
