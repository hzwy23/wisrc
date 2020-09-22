package com.wisrc.sales.webapp.dao;

import com.wisrc.sales.webapp.dao.sql.EstimateSql;
import com.wisrc.sales.webapp.enity.*;
import com.wisrc.sales.webapp.vo.RemarkVo;
import com.wisrc.sales.webapp.query.GetEstimateApprovalQuery;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Mapper
public interface EstimateDao {
    @Insert("insert into sales_estimate (estimate_id, commodity_id, shop_id, msku_id, create_user, create_time, modify_user, modify_time, charge_employee_id,update_flag) values " +
            "(#{estimateId},#{commodityId},#{shopId},#{mskuId},#{createUser},#{createTime},#{createUser},#{createTime},#{chargeEmployeeId},#{updateFlag})")
    void savaEstimate(EstimateEnity estimateEnity);

    @Select("select estimate_id, estimate_id, commodity_id, shop_id, msku_id, create_user, create_time, modify_user, modify_time, charge_employee_id from sales_estimate where commodity_id=#{commodityId} and update_flag = 1")
    EstimateEnity getByCommodityId(String commodityId);

    @Select("select estimate_id, estimate_id, commodity_id, shop_id, msku_id, create_user, create_time, modify_user, modify_time, charge_employee_id from sales_estimate where estimate_id=#{estimateId} and update_flag = 1")
    EstimateEnity getByEstimateId(String estimateId);

    @Update("update sales_estimate set modify_user=#{createUser},modify_time=#{createTime} where estimate_id=#{estimateId}")
    void updateEstimate(EstimateEnity estimateEnity);

    @Select("select uuid, estimate_id, estimate_date, estimate_number from sales_estimate_detail_info  where uuid=#{uuid}")
    EstimateDetailEnity getEstimateDetailById(String uuid);

    @Update("update sales_estimate_detail_info set estimate_number=#{estimateNumber} where uuid=#{uuid}")
    void updateEstimateDetail(EstimateDetailEnity estimateDetailEnity);

    @Insert("insert into sales_estimate_detail_info (uuid, estimate_id, estimate_date, estimate_number,update_flag) values (#{uuid}, #{estimateId}, #{estimateDate}, #{estimateNumber},#{updateFlag})")
    void addEstimateDetail(EstimateDetailEnity estimateDetailEnity);

    @SelectProvider(type = EstimateSql.class, method = "getByCond")
    List<EstimateEnity> getByCond(@Param("shopId") String shopId, @Param("mskuId") String mskuId, @Param("comodityIds") String comodityIds, @Param("asOfDate") Timestamp asOfDate);

    @Select("select estimate_id, uuid, estimate_date, estimate_number from sales_estimate_detail_info where estimate_id=#{estimateId} and estimate_date>=#{beginDate} and estimate_date<=#{overDate} " +
            "and update_flag = 1 AND effective_date <= #{asOfDate} AND expiration_date > #{asOfDate}")
    List<EstimateDetailEnity> getEstimateDetailByEstimateId(@Param("estimateId") String estimateId, @Param("beginDate") Date beginDate, @Param("overDate") Date overDate, @Param("asOfDate") java.sql.Date asOfDate);

    @Update("update sales_estimate set modify_user = #{updateUser}, modify_time=#{currentDateTime}, update_employee_id=#{updateEmployeeId} where estimate_id=#{estimateId}")
    void updateEstimateTimeAndUser(@Param("updateUser") String updateUser, @Param("currentDateTime") String currentDateTime, @Param("estimateId") String estimateId, @Param("updateEmployeeId") String updateEmployeeId);

    @SelectProvider(type = EstimateSql.class, method = "getAllDetailByUUids")
    List<EstimateDetailEnity> getAllDetailByUUids(String uuids);

    @SelectProvider(type = EstimateSql.class, method = "getTotalNum")
    Integer getTotalNum(@Param("commodityId") String commodityId, @Param("startTime") String startTime, @Param("endTime") String endTime);

    @Insert("insert into sale_estimate_detail_remark (estimate_detail_id, remark, employee_id, create_time, create_user, estimate_date,update_flag) values " +
            "(#{estimateDetailId},#{remark},#{employeeId},#{createTime},#{createUser},#{estimateDate},#{updateFlag})")
    void insertRemark(RemarkEnity remarkEnity);

    @Select("select estimate_detail_id, remark, employee_id, create_time from sale_estimate_detail_remark where estimate_detail_id=#{uuid} and update_flag = 1 order by create_time desc")
    List<RemarkVo> getRemarkList(String uuid);

    @Insert("insert into sale_estimate_detail_remark (estimate_detail_id, remark, employee_id, create_time, create_user, estimate_date) values " +
            "(#{estimateDetailId},#{remark},#{employeeId},#{createTime},#{createUser},#{estimateDate})")
    void insertUpdateRemark(UpdateRemarkEnity updateRemarkEnity);

    @Select("select estimate_id  from sales_estimate where shop_id=#{shopId} and msku_id=#{mskuId} and update_flag = 1")
    String getByMskuAndShopId(@Param("mskuId") String mskuId, @Param("shopId") String shopId);

    @SelectProvider(type = EstimateSql.class, method = "getEstimateDetailByEstimateIdAndCond")
    List<EstimateDetailEnity> getEstimateDetailByEstimateIdAndCond(@Param("estimateId") String estimateId, @Param("startTime") String startTime, @Param("endTime") String endTime);

    @Select(" SELECT\n" +
            "  uuid,\n" +
            "  estimate_id,\n" +
            "  direct_approv_status,\n" +
            "  manager_approv_status,\n" +
            "  plan_depart_approv_status,\n" +
            "  direct_approv_remark,\n" +
            "  manager_approv_remark,\n" +
            "  plan_depart_approv_remark,\n" +
            "  update_flag,\n " +
            " MAX(expiration_date)" +
            " FROM sales_estimate_approval_info " +
            " where " +
//            "update_flag = 1 and " +
            " estimate_id in ${estimateIds} " +
            " AND effective_date <=  #{asOfDate} " +
            " AND expiration_date > #{asOfDate} " +
            " GROUP BY estimate_id")
    List<EstimateApprovalEnity> getEstimateApproval(GetEstimateApprovalQuery paraMap);
}
