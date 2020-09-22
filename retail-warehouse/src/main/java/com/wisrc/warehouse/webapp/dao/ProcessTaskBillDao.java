package com.wisrc.warehouse.webapp.dao;

import com.wisrc.warehouse.webapp.dao.sql.ProcessTaskBillSql;
import com.wisrc.warehouse.webapp.entity.ProcessTaskBillEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProcessTaskBillDao {
    @Insert("insert into process_task_bill(process_task_id, status_cd, process_date, process_later_sku, process_num, warehouse_id, batch, remark, create_user, create_time, fba_replenishment_id,random_value) values (#{processTaskId}, #{statusCd}, #{processDate}, #{processLaterSku}, #{processNum}, #{warehouseId}, #{batch}, #{remark}, #{createUser}, #{createTime},#{fbaReplenishmentId},#{randomValue})")
    void add(ProcessTaskBillEntity entity);

    @Select("select process_task_id,status_cd, process_date, process_later_sku, process_num, warehouse_id, batch, remark, create_user from process_task_bill")
    List<ProcessTaskBillEntity> findAll();

    @SelectProvider(type = ProcessTaskBillSql.class, method = "search")
    List<ProcessTaskBillEntity> search(@Param("processStartDate") String processStartDate,
                                       @Param("processEndDate") String processEndDate,
                                       @Param("processLaterSku") String processLaterSku,
                                       @Param("statusCd") int statusCd,
                                       @Param("warehouseId") String warehouseId,
                                       @Param("createUser") String createUser);

    @Select("select process_task_id,status_cd, process_date, process_later_sku, process_num, warehouse_id, batch, remark, create_user from process_task_bill where process_later_sku = #{processLaterSku}")
    List<ProcessTaskBillEntity> getAllByProcessLaterSku(@Param("processLaterSku") String processLaterSku);

    @Select("select process_task_id,status_cd, process_date, process_later_sku, process_num, warehouse_id, batch, remark, create_user,create_time, fba_replenishment_id from process_task_bill where process_task_id = #{processTaskId}")
    ProcessTaskBillEntity getByProcessTaskId(@Param("processTaskId") String processTaskId);

    //取消加工
    @Update("update process_task_bill set status_cd = 4 where process_task_id = #{processTaskId}")
    void update(@Param("processTaskId") String processTaskId);


    @Select("select process_task_id, status_cd, process_date, process_later_sku, process_num, warehouse_id, batch, remark, create_user, create_time, fba_replenishment_id  " +
            "from process_task_bill where fba_replenishment_id = #{fbaReplenishmentId}")
    List<ProcessTaskBillEntity> getTaskList(@Param("fbaReplenishmentId") String fbaReplenishmentId);

    @Select("SELECT * FROM process_task_bill WHERE fba_replenishment_id = #{fbaReplenishmentId}")
    List<ProcessTaskBillEntity> getStatusCdByfbaId(@Param("fbaReplenishmentId") String fbaReplenishmentId);

    @Select("select process_task_id from process_task_bill where random_value = #{randomValue}")
    String findProcessTaskIdByRandomValue(@Param("randomValue") String uuid);

    /**
     * 条件查询
     *
     * @param processStartDate
     * @param processEndDate
     * @param processLaterSku
     * @param statusCd
     * @param warehouseId
     * @param createUser
     * @param skuIds
     * @return
     */
    @SelectProvider(type = ProcessTaskBillSql.class, method = "searchByCond")
    List<ProcessTaskBillEntity> searchByCond(@Param("processStartDate") String processStartDate,
                                             @Param("processEndDate") String processEndDate,
                                             @Param("processLaterSku") String processLaterSku,
                                             @Param("statusCd") int statusCd,
                                             @Param("warehouseId") String warehouseId,
                                             @Param("createUser") String createUser,
                                             @Param("skuIds") List<String> skuIds);
}
