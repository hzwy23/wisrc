package com.wisrc.warehouse.webapp.dao;

import com.wisrc.warehouse.webapp.entity.EnterWarehouseTypeEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EnterWarehouseTypeDao {
    @Insert("insert into enter_warehouse_type_attr (type_name, create_user, create_time, delete_status) values " +
            "(#{typeName}, #{createUser}, #{createTime},#{deleteStatus})")
    void add(EnterWarehouseTypeEntity entity);

    @Update("update enter_warehouse_type_attr set type_name=#{typeName} where enter_type_cd=#{enterTypeCd}")
    void update(EnterWarehouseTypeEntity entity);

    /**
     * 配置入库类型状态
     *
     * @param enterTypeCd
     * @param deleteStatus <code>1</code>表示禁用，<code>0</code>表示启用
     */
    @Update("update enter_warehouse_type_attr set delete_status=#{deleteStatus} where enter_type_cd=#{enterTypeCd}")
    void configStatus(@Param("enterTypeCd") int enterTypeCd, @Param("deleteStatus") int deleteStatus);

    @Select("select enter_type_cd, type_name, create_user, create_time, delete_status from enter_warehouse_type_attr")
    List<EnterWarehouseTypeEntity> select();
}
