package com.wisrc.shipment.webapp.dao;

import com.wisrc.shipment.webapp.dao.sql.SmartReplenishmentImplSql;
import com.wisrc.shipment.webapp.entity.DefaultSecurityAlertDaysInfoEntity;
import com.wisrc.shipment.webapp.entity.EarlyWarningLevelAttrEntity;
import com.wisrc.shipment.webapp.entity.SmartReplenishmentInfoEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface SmartReplenishmentImplDao {
    @Update(" UPDATE default_security_alert_days_info\n" +
            " SET\n" +
            "  early_warning_days          = #{earlyWarningDays},\n" +
            "  default_security_stock_days = #{defaultSecurityStockDays}\n" +
            " WHERE id = 1 ")
    void updateDefaultSecurityAlertDaysInfo(Map map);

    @Select(" SELECT\n" +
            "  id                          AS id,\n" +
            "  early_warning_days          AS earlyWarningDays,\n" +
            "  default_security_stock_days AS defaultSecurityStockDays\n" +
            " FROM default_security_alert_days_info\n" +
            " WHERE id = 1 ")
    DefaultSecurityAlertDaysInfoEntity getDefaultSecurityAlertDaysInfo();

    @Insert(" INSERT INTO smart_replenishment_info (\n" +
            "  uuid,\n" +
            "  id,\n" +
            "  shop_id,\n" +
            "  msku_id,\n" +
            "  fba_on_warehouse_stock_num,\n" +
            "  fba_on_way_stock_num,\n" +
            "  on_warehouse_available_day,\n" +
            "  on_way_available_day,\n" +
            "  all_available_day,\n" +
            "  safety_stock_days,\n" +
            "  sku_id,\n" +
            "  product_name,\n" +
            "  employee_name,\n" +
            "  sales_status_cd,\n" +
            "  sales_status_desc,\n" +
            "  picture,\n" +
            "  create_time,\n" +
            "  early_warning_level_cd,\n" +
            "  early_warning_level_desc,\n" +
            "  shop_name,\n" +
            "  msku_name,\n" +
            "  asin,\n" +
            "  msku_safety_stock_days\n" +
            ") VALUES\n" +
            "  (\n" +
            "    #{uuid},\n" +
            "    #{id},\n" +
            "    #{shopId},\n" +
            "    #{mskuId},\n" +
            "    #{fbaOnWarehouseStockNum},\n" +
            "    #{fbaOnWayStockNum},\n" +
            "    #{onWarehouseAvailableDay},\n" +
            "    #{onWayAvailableDay},\n" +
            "    #{allAvailableDay},\n" +
            "    #{safetyStockDays},\n" +
            "    #{skuId},\n" +
            "    #{productName},\n" +
            "    #{employeeName},\n" +
            "    #{salesStatusCd},\n" +
            "    #{salesStatusDesc},\n" +
            "    #{picture},\n" +
            "    #{createTime},\n" +
            "    #{earlyWarningLevelCd},\n" +
            "    #{earlyWarningLevelDesc},\n" +
            "    #{shopName},\n" +
            "    #{mskuName},\n" +
            "    #{asin},\n" +
            "    #{mskuSafetyStockDays}\n" +
            "  ) ")
    void insert(SmartReplenishmentInfoEntity entity);

    @SelectProvider(type = SmartReplenishmentImplSql.class, method = "fuzzyQuery")
    List<SmartReplenishmentInfoEntity> fuzzyQuery(@Param("shopId") String shopId, @Param("earlyWarningLevelDesc") String earlyWarningLevelDesc, @Param("salesStatusCd") Integer salesStatusCd, @Param("keyWords") String keyWords);

    //按照时效天数的顺序，时间的顺序排序的
    @Select(" SELECT\n" +
            "  early_warning_level_cd,\n" +
            "  early_warning_level_desc,\n" +
            "  internal_processing_days,\n" +
            "  head_transportation_days,\n" +
            "  signed_income_warehouse_days,\n" +
            "  aging_days,\n" +
            "  enable_flag,\n" +
            "  create_time\n" +
            "FROM early_warning_level_attr\n" +
            "ORDER BY aging_days ASC, create_time ASC"
    )
    List<EarlyWarningLevelAttrEntity> findWaring();

    @Select(" SELECT DISTINCT (early_warning_level_desc)\n" +
            "   FROM smart_replenishment_info " +
            " where create_time IN(SELECT max(create_time) FROM smart_replenishment_info) ")
    List<Map> getWaringLevel();


}
