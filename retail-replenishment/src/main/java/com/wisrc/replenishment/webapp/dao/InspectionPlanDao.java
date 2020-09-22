package com.wisrc.replenishment.webapp.dao;

import com.wisrc.replenishment.webapp.dao.sql.InspectionPlanSQL;
import com.wisrc.replenishment.webapp.entity.InspectionPlanEntity;
import com.wisrc.replenishment.webapp.query.DeleteInspectionPlan;
import com.wisrc.replenishment.webapp.query.GetInspectionPlanByPlanIdsQuery;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
import java.util.List;

@Mapper
public interface InspectionPlanDao {
    int using = 0;
    int delete = 1;

    @SelectProvider(type = InspectionPlanSQL.class, method = "getInspectionPlanByPlanIds")
    List<InspectionPlanEntity> getInspectionPlanByPlanIds(GetInspectionPlanByPlanIdsQuery inspectionPlanQuery) throws Exception;

    @Insert("INSERT INTO inspection_plan(logistics_plan_id, commodity_id, delivery_plan_date, delivery_plan_quantity, inspection_traffic_day, sales_start_time, " +
            "sales_end_time, sales_demand_quantity, create_user, create_time, modify_user, modify_time, delete_status) VALUES(#{logisticsPlanId}, #{commodityId}, #{deliveryPlanDate}" +
            ", #{deliveryPlanQuantity}, #{inspectionTrafficDay}, #{salesStartTime}, #{salesEndTime}, #{salesDemandQuantity}, #{createUser}, #{createTime}, #{modifyUser}, #{modifyTime}, " +
            "#{deleteStatus})")
    void saveInspectionPlan(InspectionPlanEntity inspectionPlanEntity) throws Exception;

    @Update("UPDATE inspection_plan SET delivery_plan_date = #{deliveryPlanDate}, delivery_plan_quantity = #{deliveryPlanQuantity}, inspection_traffic_day = #{inspectionTrafficDay}, " +
            " modify_user = #{modifyUser}, modify_time =  #{modifyTime} WHERE logistics_plan_id = #{logisticsPlanId}")
    void editInspectionPlan(InspectionPlanEntity inspectionPlanEntity) throws Exception;

    @Select("SELECT logistics_plan_id FROM inspection_plan WHERE commodity_id = #{commodityId} AND delete_status = " + using)
    List<String> inspectionPlanIdByMskuId(@Param("commodityId") String commodityId) throws Exception;

    @Update("UPDATE inspection_plan SET delete_status = 1, modify_user = #{modifyUser}, modify_time = #{modifyTime} WHERE logistics_plan_id = #{logisticsPlanId}")
    void deleteInspectionPlan(DeleteInspectionPlan deleteInspectionPlan) throws Exception;

    @Select("SELECT logistics_plan_id, delivery_plan_date, delivery_plan_quantity, inspection_traffic_day, sales_start_time, sales_end_time, sales_demand_quantity " +
            "FROM inspection_plan WHERE commodity_id = #{commodityId} AND delete_status = " + using)
    List<InspectionPlanEntity> inspectionPlanByMskuId(@Param("commodityId") String commodityId) throws Exception;

    @SelectProvider(type = InspectionPlanSQL.class, method = "mskuInSalesEndTime")
    List<String> mskuInSalesEndTime(@Param("deliveryPlanEndDate") Date deliveryPlanEndDate, @Param("deliveryPlanStartDate") Date deliveryPlanStartDate) throws Exception;

    @Select("SELECT sales_end_time FROM inspection_plan AS ip WHERE delete_status = " + using + " AND delivery_plan_date < NOW() AND commodity_id = #{commodityId} AND NOT EXISTS ( " +
            "SELECT 1 FROM inspection_plan WHERE delete_status = " + using + " AND delivery_plan_date < NOW() AND commodity_id = ip.commodity_id AND delivery_plan_date > ip.delivery_plan_date)")
    Date getSalesLaseEndTime(@Param("commodityId") String commodityId) throws Exception;
}
