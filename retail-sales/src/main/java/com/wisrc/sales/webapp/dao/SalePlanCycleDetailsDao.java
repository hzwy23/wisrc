package com.wisrc.sales.webapp.dao;

import com.wisrc.sales.webapp.dao.sql.SalePlanCycleDetailsQuery;
import com.wisrc.sales.webapp.vo.SelectSalePlanDetailVO;
import com.wisrc.sales.webapp.vo.UpdateSalePlanCycleDetailsVo;
import com.wisrc.sales.webapp.entity.SalePlanCycleDetailsEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SalePlanCycleDetailsDao {
    @Insert("insert into sale_plan_cycle_details (uuid, sale_plan_id,weight,plan_date, sale_cycle, cost_price, sale_price, day_sale_num, sale_time, sale_amount, " +
            "estimate_refundable_rate, commission_coefficient, commission, fulfillmentCost, marketing_cost, marketing_cost_ratio, test_cost, test_cost_ratio, " +
            "advertisement_cost, advertisement_cost_ratio, coupon_cost, coupon_cost_ratio, deal_cost, deal_cost_ratio, outside_promotion_cost, outside_promotion_cost_ratio, " +
            "first_unit_price, first_freight, breakage_cost, real_sale_amount, total_cost, gross_profit, gross_rate, day_gross_rate) values (#{uuid},#{salePlanId},#{weight},#{planDate}," +
            "#{saleCycle},#{costPrice},#{salePrice},#{daySaleNum},#{saleTime},#{saleAmount},#{estimateRefundableRate},#{commissionCoefficient}," +
            "#{commission},#{fulfillmentCost},#{marketingCost},#{marketingCostRatio},#{testCost},#{testCostRatio},#{advertisementCost},#{advertisementCostRatio}," +
            "#{couponCost},#{couponCostRatio},#{dealCost},#{dealCostRatio},#{outsidePromotionCost},#{outsidePromotionCostRatio}, " +
            "#{firstUnitPrice},#{firstFreight},#{breakageCost},#{realSaleAmount},#{totalCost},#{grossProfit},#{grossRate},#{dayGrossRate})")
    void add(SalePlanCycleDetailsEntity ele);

    @Select("select uuid, weight, plan_date, sale_cycle, cost_price, sale_price, day_sale_num, sale_time, sale_amount, estimate_refundable_rate, commission_coefficient, commission, fulfillmentCost, marketing_cost, marketing_cost_ratio, test_cost, test_cost_ratio, advertisement_cost, advertisement_cost_ratio, coupon_cost, coupon_cost_ratio, deal_cost, deal_cost_ratio, outside_promotion_cost, outside_promotion_cost_ratio, first_unit_price, first_freight, breakage_cost, real_sale_amount, total_cost, gross_profit, gross_rate, day_gross_rate, sale_plan_id, director_employee_id, charge_employee_id, commodity_id, shop_id, msku_id" +
            " from v_sale_plan_info_detail where sale_plan_id=#{salePlanId} and plan_date=#{planDate}")
    List<SelectSalePlanDetailVO> getDetail(@Param("salePlanId") String salePlanId, @Param("planDate") String planDate);

    @Select("select plan_date from v_sale_plan_info_detail where sale_plan_id=#{salePlanId} group by plan_date")
    List<String> getPlanDate(@Param("salePlanId") String salePlanId);

    @Update("update sale_plan_cycle_details set weight=#{weight},cost_price=#{costPrice},sale_price=#{salePrice},day_sale_num=#{daySaleNum},sale_time=#{saleTime},sale_amount=#{saleAmount}," +
            "estimate_refundable_rate=#{estimateRefundableRate},commission_coefficient=#{commissionCoefficient},commission=#{commission},fulfillmentCost=#{fulfillmentCost}," +
            "marketing_cost=#{marketingCost},marketing_cost_ratio=#{marketingCostRatio},test_cost=#{testCost},test_cost_ratio=#{testCostRatio},advertisement_cost=#{advertisementCost}," +
            "advertisement_cost_ratio=#{advertisementCostRatio},coupon_cost=#{couponCost},coupon_cost_ratio=#{couponCostRatio},deal_cost=#{dealCost},deal_cost_ratio=#{dealCostRatio}," +
            "outside_promotion_cost=#{outsidePromotionCost},outside_promotion_cost_ratio=#{outsidePromotionCostRatio},first_unit_price=#{firstUnitPrice},first_freight=#{firstFreight}," +
            "breakage_cost=#{breakageCost},real_sale_amount=#{realSaleAmount},total_cost=#{totalCost},gross_profit=#{grossProfit},gross_rate=#{grossRate},day_gross_rate=#{dayGrossRate} " +
            "where uuid =#{uuid}")
    void update(UpdateSalePlanCycleDetailsVo vo);

    @Update("update sale_plan_cycle_details set weight=#{weight},cost_price=#{costPrice},sale_price=#{salePrice},day_sale_num=#{daySaleNum},sale_time=#{saleTime},sale_amount=#{saleAmount}," +
            "estimate_refundable_rate=#{estimateRefundableRate},commission_coefficient=#{commissionCoefficient},commission=#{commission},fulfillmentCost=#{fulfillmentCost}," +
            "marketing_cost=#{marketingCost},marketing_cost_ratio=#{marketingCostRatio},test_cost=#{testCost},test_cost_ratio=#{testCostRatio},advertisement_cost=#{advertisementCost}," +
            "advertisement_cost_ratio=#{advertisementCostRatio},coupon_cost=#{couponCost},coupon_cost_ratio=#{couponCostRatio},deal_cost=#{dealCost},deal_cost_ratio=#{dealCostRatio}," +
            "outside_promotion_cost=#{outsidePromotionCost},outside_promotion_cost_ratio=#{outsidePromotionCostRatio},first_unit_price=#{firstUnitPrice},first_freight=#{firstFreight}," +
            "breakage_cost=#{breakageCost},real_sale_amount=#{realSaleAmount},total_cost=#{totalCost},gross_profit=#{grossProfit},gross_rate=#{grossRate},day_gross_rate=#{dayGrossRate} " +
            "where uuid =#{uuid}")
    void updateEntity(SalePlanCycleDetailsEntity vo);

    @Update("update sale_plan_cycle_details set weight=#{weight},cost_price=#{costPrice},sale_price=#{salePrice},day_sale_num=#{daySaleNum},sale_time=#{saleTime},sale_amount=#{saleAmount}," +
            "estimate_refundable_rate=#{estimateRefundableRate},commission_coefficient=#{commissionCoefficient},commission=#{commission},fulfillmentCost=#{fulfillmentCost}," +
            "marketing_cost=#{marketingCost},marketing_cost_ratio=#{marketingCostRatio},test_cost=#{testCost},test_cost_ratio=#{testCostRatio},advertisement_cost=#{advertisementCost}," +
            "advertisement_cost_ratio=#{advertisementCostRatio},coupon_cost=#{couponCost},coupon_cost_ratio=#{couponCostRatio},deal_cost=#{dealCost},deal_cost_ratio=#{dealCostRatio}," +
            "outside_promotion_cost=#{outsidePromotionCost},outside_promotion_cost_ratio=#{outsidePromotionCostRatio},first_unit_price=#{firstUnitPrice},first_freight=#{firstFreight}," +
            "breakage_cost=#{breakageCost},real_sale_amount=#{realSaleAmount},total_cost=#{totalCost},gross_profit=#{grossProfit},gross_rate=#{grossRate},day_gross_rate=#{dayGrossRate} " +
            "where uuid =#{uuid}")
    void updateTotal(SalePlanCycleDetailsEntity vo);

    @Select("select uuid, weight, plan_date, sale_cycle, cost_price, sale_price, day_sale_num, sale_time, sale_amount, estimate_refundable_rate, commission_coefficient, commission, fulfillmentCost, marketing_cost, marketing_cost_ratio, test_cost, test_cost_ratio, advertisement_cost, advertisement_cost_ratio, coupon_cost, coupon_cost_ratio, deal_cost, deal_cost_ratio, outside_promotion_cost, outside_promotion_cost_ratio, first_unit_price, first_freight, breakage_cost, real_sale_amount, total_cost, gross_profit, gross_rate, day_gross_rate, sale_plan_id, director_employee_id, charge_employee_id, commodity_id, shop_id, msku_id" +
            " from v_sale_plan_info_detail where sale_plan_id=#{salePlanId} and plan_date=#{planDate} and sale_cycle!=999")
    List<SelectSalePlanDetailVO> getDetailNoTotal(@Param("salePlanId") String salePlanId, @Param("planDate") String planDate);

    @Select("select uuid, weight, plan_date, sale_cycle, cost_price, sale_price, day_sale_num, sale_time, sale_amount, estimate_refundable_rate, commission_coefficient, commission, fulfillmentCost, marketing_cost, marketing_cost_ratio, test_cost, test_cost_ratio, advertisement_cost, advertisement_cost_ratio, coupon_cost, coupon_cost_ratio, deal_cost, deal_cost_ratio, outside_promotion_cost, outside_promotion_cost_ratio, first_unit_price, first_freight, breakage_cost, real_sale_amount, total_cost, gross_profit, gross_rate, day_gross_rate, sale_plan_id, director_employee_id, charge_employee_id, commodity_id, shop_id, msku_id" +
            " from v_sale_plan_info_detail where sale_plan_id=#{salePlanId} and sale_cycle!=999 order by plan_date desc,sale_cycle asc")
    List<SelectSalePlanDetailVO> getResult(@Param("salePlanId") String salePlanId);

    @Select("select * from v_sale_plan_info_detail where plan_date=#{planDate} and commodity_id = #{commodityId}")
    List<SelectSalePlanDetailVO> getRecord(@Param("planDate") String planDate, @Param("commodityId") String commodityId);

    @Select("SELECT sale_plan_id, plan_date FROM sale_plan_cycle_details AS a WHERE EXISTS (SELECT sale_plan_id FROM sale_plan_cycle_details " +
            "AS b WHERE a.sale_plan_id = b.sale_plan_id AND uuid = #{uuid}) GROUP BY plan_date ORDER BY plan_date DESC")
    List<SalePlanCycleDetailsEntity> getDateById(@Param("uuid") String uuid);

    @SelectProvider(type = SalePlanCycleDetailsQuery.class, method = "deleteDetail")
    void deleteDetail(@Param("salePlanId") String salePlanId, @Param("planDates") List planDates);
}
