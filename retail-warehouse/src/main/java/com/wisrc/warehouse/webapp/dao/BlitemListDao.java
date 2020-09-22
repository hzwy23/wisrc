package com.wisrc.warehouse.webapp.dao;

import com.wisrc.warehouse.webapp.entity.BlitemListInfoEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BlitemListDao {

    @Select("select uuid,blitem_id,sku_id,warehouse_position,audit_stock_ago,audit_stock_back,diversity_reason,delete_status,create_user,create_time,modify_time,modify_user\n" +
            "  from blitem_listing_info\n" +
            "where delete_status = 0 and blitem_id = #{blitemId} ")
    List<BlitemListInfoEntity> findAllByBlitemId(@Param("blitemId") String blitemId);

    @Insert("insert into blitem_listing_info(uuid, blitem_id, sku_id, warehouse_position, audit_stock_ago, audit_stock_back, diversity_reason," +
            " delete_status, create_user, create_time, modify_time, modify_user) VALUES(#{uuid},#{blitemId},#{skuId},#{warehousePosition}," +
            "#{auditStockAgo},#{auditStockBack},#{diversityReason},#{deleteStatus},#{createUser},#{createTime},#{modifyTime},#{modifyUser})")
    void add(BlitemListInfoEntity entity);
}
