package com.wisrc.purchase.webapp.dao;

import com.wisrc.purchase.webapp.entity.GetScheduledTime;
import com.wisrc.purchase.webapp.entity.GetSetting;
import com.wisrc.purchase.webapp.entity.PurchaseSettingEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface PurchaseSettingDao {
    @Select("SELECT stock_cycle, purchase_warm_day, datetime, ps.calculate_cycle_cd, ps.calculate_cycle_cd, calculate_cycle_desc, ps.calculate_cycle_week_cd, calculate_cycle_week_desc FROM purchase_setting AS ps " +
            "LEFT JOIN calculate_cycle_attr AS cca ON ps.calculate_cycle_cd = cca.calculate_cycle_cd LEFT JOIN calculate_cycle_week_attr AS ccwa ON ccwa.calculate_cycle_week_cd = ps.calculate_cycle_week_cd " +
            "WHERE id = 1")
    GetSetting getPurchaseSetting();

    @Select("SELECT calculate_cycle_cd, calculate_cycle_week_attr, datetime FROM purchase_setting AS ps LEFT JOIN calculate_cycle_week_attr AS ccwa ON ccwa.calculate_cycle_week_cd = ps.calculate_cycle_week_cd")
    GetScheduledTime getScheduledTime();

    @Update("UPDATE purchase_setting SET stock_cycle = #{stockCycle}, calculate_cycle_cd = #{calculateCycleCd}, purchase_warm_day = #{purchaseWarmDay}, datetime = #{datetime}, " +
            "calculate_cycle_week_cd = #{calculateCycleWeekCd,jdbcType=INTEGER} WHERE id = 1")
    void editPurchaseSetting(PurchaseSettingEntity purchaseSettingEntity);
}
