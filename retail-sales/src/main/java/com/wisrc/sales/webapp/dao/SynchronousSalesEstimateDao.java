package com.wisrc.sales.webapp.dao;

import com.wisrc.sales.webapp.dao.sql.SynchronousSalesEstimateSql;
import com.wisrc.sales.webapp.enity.EstimateApprovalEnity;
import com.wisrc.sales.webapp.enity.EstimateDetailEnity;
import com.wisrc.sales.webapp.enity.EstimateEnity;
import com.wisrc.sales.webapp.enity.RemarkEnity;
import com.wisrc.sales.webapp.query.GetEstimateQuery;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface SynchronousSalesEstimateDao {
    @Insert("insert into sale_estimate_detail_remark (estimate_id,estimate_detail_id, remark, employee_id, create_time, create_user, estimate_date,update_flag,effective_date) values " +
            "(#{estimateId},#{estimateDetailId},#{remark},#{employeeId},#{createTime},#{createUser},#{estimateDate},#{updateFlag},#{effectiveDate})")
    void insertRemarkEnity(RemarkEnity remarkEnity);

    @Insert("insert into sales_estimate (estimate_id, commodity_id, shop_id, msku_id, create_user, create_time, modify_user, modify_time, charge_employee_id,update_flag,shop_seller_id, effective_date) values " +
            "(#{estimateId},#{commodityId},#{shopId},#{mskuId},#{createUser},#{createTime},#{createUser},#{createTime},#{chargeEmployeeId},#{updateFlag},#{shopSellerId}, #{effectiveDate})")
    void insertEstimateEnity(EstimateEnity estimateEnity);

    @Insert("insert into sales_estimate_detail_info (uuid, estimate_id, estimate_date, estimate_number,update_flag,effective_date) values (#{uuid}, #{estimateId}, #{estimateDate}, #{estimateNumber},#{updateFlag}, #{effectiveDate})")
    void insertEstimateDetailEnity(EstimateDetailEnity estimateDetailEnity);

    @Insert(" INSERT INTO sales_estimate_approval_info\n" +
            "(\n" +
            "  uuid,\n" +
            "  estimate_id,\n" +
            "  direct_approv_status,\n" +
            "  manager_approv_status,\n" +
            "  plan_depart_approv_status,\n" +
            "  direct_approv_remark,\n" +
            "  manager_approv_remark,\n" +
            "  plan_depart_approv_remark,\n" +
            "  update_flag,\n" +
            "  effective_date\n" +
            ") VALUES (\n" +
            "  #{uuid},\n" +
            "  #{estimateId},\n" +
            "  #{directApprovStatus},\n" +
            "  #{managerApprovStatus},\n" +
            "  #{planDepartApprovStatus},\n" +
            "  #{directApprovRemark},\n" +
            "  #{managerApprovRemark},\n" +
            "  #{planDepartApprovRemark},\n" +
            "  #{updateFlag},\n" +
            "  #{effectiveDate}\n" +
            ") ")
    void insertEstimateApprovalEnity(EstimateApprovalEnity estimateApprovalEnity);

    @Delete(" delete from sales_estimate where update_flag = #{updateFlag} ")
    void deleteEstimateUpdateFlag(int updateFlag);

    @Delete(" delete from sales_estimate where update_flag = #{updateFlag} and estimate_id in  ${estimateIds} ")
    void deleteEstimateUpdateFlagSingle(Map map);

    @Delete(" delete from sales_estimate_approval_info where update_flag = #{updateFlag} ")
    void deleteEstimateApprovalUpdateFlag(int updateFlag);

    @Delete(" delete from sales_estimate_approval_info where update_flag = #{updateFlag} and estimate_id in  ${estimateIds} ")
    void deleteEstimateApprovalUpdateFlagSingle(Map map);

    @Delete(" delete from sales_estimate_detail_info where update_flag = #{updateFlag} ")
    void deleteEstimateDetailUpdateFlag(int updateFlag);

    @Delete(" delete from sales_estimate_detail_info where update_flag = #{updateFlag} and estimate_id in  ${estimateIds} ")
    void deleteEstimateDetailUpdateFlagSingle(Map map);

    @Delete(" delete from sale_estimate_detail_remark where update_flag = #{updateFlag} ")
    void deleteRemarkUpdateFlag(int updateFlag);

    @Delete(" delete from sale_estimate_detail_remark where update_flag = #{updateFlag} and estimate_id in  ${estimateIds} ")
    void deleteRemarkUpdateFlagSingle(Map map);

    @Update(" update sales_estimate set update_flag = 1 where update_flag = #{updateFlag} ")
    void updateEstimateUpdateFlag(int updateFlag);

    @Update(" update sales_estimate set update_flag = 1 where update_flag = #{updateFlag} and estimate_id in  ${estimateIds}   ")
    void updateEstimateUpdateFlagSingle(Map map);

    @Update(" update sales_estimate_approval_info set update_flag = 1 where update_flag = #{updateFlag} ")
    void updateEstimateApprovalUpdateFlag(int updateFlag);

    @Update(" update sales_estimate_approval_info set update_flag = 1 where update_flag = #{updateFlag} and estimate_id in  ${estimateIds} ")
    void updateEstimateApprovalUpdateFlagSingle(Map map);

    @Update(" update sales_estimate_detail_info set update_flag = 1 where update_flag = #{updateFlag} ")
    void updateEstimateDetailUpdateFlag(int updateFlag);

    @Update(" update sales_estimate_detail_info set update_flag = 1 where update_flag = #{updateFlag} and estimate_id in  ${estimateIds} ")
    void updateEstimateDetailUpdateFlagSingle(Map map);

    @Update(" update sale_estimate_detail_remark set update_flag = 1 where update_flag = #{updateFlag} ")
    void updateRemarkUpdateFlag(int updateFlag);

    @Update(" update sale_estimate_detail_remark set update_flag = 1 where update_flag = #{updateFlag} and estimate_id in  ${estimateIds} ")
    void updateRemarkUpdateFlagSingle(Map map);

    //利用update_flag=1和商品码commodityId唯一 找到上次的主键集合
    @Select(" SELECT estimate_id as estimateId\n" +
            " FROM sales_estimate \n" +
            " WHERE update_flag = 1" +
            " and commodity_id IN ${commodityIds} ")
    List<String> getLast(Map map);

    @SelectProvider(type = SynchronousSalesEstimateSql.class, method = "getEstimateEnity")
    List<EstimateEnity> getEstimateEnity(GetEstimateQuery getEstimateQuery);

    @Update("UPDATE sales_estimate_approval_info SET expiration_date = #{expirationDate}, modify_time = #{expirationDate}, modify_user = #{modify_user} WHERE estimate_id = #{estimateId} AND expiration_date > #{expirationDate}")
    void updateApprovalExpiration(@Param("estimateId") String estimateId, @Param("expirationDate") String expirationDate, @Param("userId") String userId);

    @SelectProvider(type = SynchronousSalesEstimateSql.class, method = "getApprovalByIds")
    List<EstimateApprovalEnity> getApprovalByIds(@Param("estimateIds") List estimateIds);

    @Update("UPDATE sales_estimate_detail_info SET expiration_date = #{expirationDate} WHERE uuid = #{estimateDetailId} AND expiration_date > #{expirationDate}")
    void updateDetailExpiration(@Param("estimateDetailId") String estimateDetailId, @Param("expirationDate") String expirationDate);

    @SelectProvider(type = SynchronousSalesEstimateSql.class, method = "getDetailByIds")
    List<EstimateDetailEnity> getDetailByIds(@Param("estimateIds") List estimateIds);

    @Update("UPDATE sale_estimate_detail_remark SET expiration_date = #{expirationDate} WHERE estimate_detail_id = #{estimateDetailId} AND expiration_date > #{expirationDate}")
    void updateRemarkExpiration(@Param("estimateDetailId") String estimateDetailId, @Param("expirationDate") String expirationDate);

    @SelectProvider(type = SynchronousSalesEstimateSql.class, method = "getRemarkByIds")
    List<RemarkEnity> getRemarkByIds(@Param("estimateIds") List estimateIds);
}
