package com.wisrc.merchandise.dao;

import com.wisrc.merchandise.dao.sql.MskuSalesPlanSQL;
import com.wisrc.merchandise.entity.MskuSalesDefineEntity;
import com.wisrc.merchandise.entity.MskuSalesPlanEntity;
import com.wisrc.merchandise.entity.MskuSalesPlanPageEntity;
import com.wisrc.merchandise.query.DistinctSalesDefineQuery;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MskuSalesPlanDao {
    @Select("SELECT plan_id FROM msku_sales_define WHERE id = #{id} AND msku_status_cd <> 3")
    List<String> getPlanEditId(@Param("id") String id);

    @Select("SELECT MAX(plan_id + 0)+1 as id FROM msku_sales_plan")
    int getNewId();

    @Insert("INSERT INTO msku_sales_plan (plan_id, expected_daily_sales, guide_price, sales_status_cd, start_date, expiry_date) " +
            "VALUES (#{planId}, #{expectedDailySales}, #{guidePrice}, #{salesStatusCd}, #{startDate}, #{expiryDate})")
    void saveSalesPlan(MskuSalesPlanEntity mskuSalesPlanEntity) throws Exception;

    @Insert("INSERT INTO msku_sales_define (plan_id, id, msku_status_cd) VALUES (#{planId}, #{id}, #{mskuStatusCd})")
    void savePlanDefine(MskuSalesDefineEntity mskuSalesDefineEntity) throws Exception;

    @Update("UPDATE msku_sales_plan SET expected_daily_sales = #{expectedDailySales}, guide_price = #{guidePrice}, sales_status_cd = #{salesStatusCd}, " +
            "start_date = #{startDate}, expiry_date = #{expiryDate} WHERE plan_id = #{planId}")
    void editMskuSalesPlan(MskuSalesPlanEntity mskuSalesPlanEntity) throws Exception;

    @Update("UPDATE msku_sales_define SET msku_status_cd = #{mskuStatusCd} WHERE plan_id = #{planId}")
    void deleteMskuSalesPlan(MskuSalesDefineEntity mskuSalesDefineEntity) throws Exception;

    @Update("<script> "
            + "UPDATE msku_sales_define SET msku_status_cd = 3 WHERE plan_id IN "
            + "<foreach item='planId' index='index' collection='planIds' open='(' separator=',' close=')'>"
            + "#{planId}"
            + "</foreach>"
            + "</script>"
    )
    void deleteMskuSalesPlanInPlanId(@Param("planIds") List planIds) throws Exception;

    @Update("UPDATE msku_sales_define SET msku_status_cd = #{mskuStatusCd} WHERE id = #{id}")
    void deleteMskuSalesPlanById(MskuSalesDefineEntity mskuSalesDefineEntity) throws Exception;

    // 去重获取msku数据的数量
    @SelectProvider(type = MskuSalesPlanSQL.class, method = "distinctSalesDefine")
    List<String> distinctSalesDefine(DistinctSalesDefineQuery distinctSalesDefineQuery) throws Exception;

    @Select("SELECT msp.plan_id, sales_status_cd, expected_daily_sales, guide_price, start_date, expiry_date, msd.id FROM msku_sales_plan AS msp " +
            " LEFT JOIN msku_sales_define AS msd ON msd.plan_id = msp.plan_id WHERE msd.id = #{id} ")
    List<MskuSalesPlanPageEntity> getSalesPlanById(@Param("id") String id) throws Exception;

    @SelectProvider(type = MskuSalesPlanSQL.class, method = "getSalesPlanByIds")
    List<MskuSalesPlanPageEntity> getSalesPlanByIds(@Param("ids") List<String> ids) throws Exception;

    @Select("SELECT * FROM msku_sales_define WHERE id = (SELECT id FROM msku_sales_define WHERE plan_id = #{planId})")
    List<MskuSalesDefineEntity> getMskuAllPlanByPlanId(@Param("planId") String planId) throws Exception;
}
