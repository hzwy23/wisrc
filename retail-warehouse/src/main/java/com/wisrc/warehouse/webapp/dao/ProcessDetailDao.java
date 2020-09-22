package com.wisrc.warehouse.webapp.dao;

import com.wisrc.warehouse.webapp.entity.ProcessDetailEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProcessDetailDao {
    @Insert("insert into process_detail values (#{uuid}, #{processTaskId}, #{skuId}, #{unitNum}, #{totalAmount}, #{warehouseId}, #{batch})")
    void add(ProcessDetailEntity entity);

    @Select("select sku_id, unit_num, total_amount, warehouse_id, batch from process_detail where process_task_id = #{processTaskId}")
    List<ProcessDetailEntity> findByProcessTaskId(@Param("processTaskId") String processTaskId);

    @Select("UPDATE process_task_bill " +
            "SET status_cd = #{status} " +
            "WHERE process_task_id = #{processTaskId}")
    void changeStatus(@Param("processTaskId") String processTaskId,
                      @Param("status") int status);
}
