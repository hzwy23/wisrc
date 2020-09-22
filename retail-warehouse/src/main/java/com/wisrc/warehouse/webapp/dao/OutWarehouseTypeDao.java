package com.wisrc.warehouse.webapp.dao;

import com.wisrc.warehouse.webapp.entity.OutWarehouseTypeEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OutWarehouseTypeDao {

    @Insert("insert into out_warehouse_type_attr (type_name, create_user, create_time, delete_status) values " +
            "(#{typeName},#{createUser},#{createTime},#{deleteStatus})")
    void add(OutWarehouseTypeEntity entity);

    @Update("update out_warehouse_type_attr set type_name=#{typeName} where out_type_cd=#{outTypeCd}")
    void update(OutWarehouseTypeEntity entity);

    /**
     * 配置出库类型状态
     *
     * @param outTypeCd
     * @param deleteStatus <code>1</code>表示禁用，<code>0</code>表示启用
     */
    @Update("update out_warehouse_type_attr set delete_status=#{deleteStatus} where out_type_cd=#{outTypeCd}")
    void configStatus(@Param("outTypeCd") int outTypeCd, @Param("deleteStatus") int deleteStatus);

    @Select("select out_type_cd, type_name, create_user, create_time, delete_status from out_warehouse_type_attr")
    List<OutWarehouseTypeEntity> select();

    @Select("select max(out_type_cd) from out_warehouse_type_attr")
    int selectId();
}
