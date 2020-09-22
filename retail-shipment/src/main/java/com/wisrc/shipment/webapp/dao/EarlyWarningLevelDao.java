package com.wisrc.shipment.webapp.dao;

import com.wisrc.shipment.webapp.entity.EarlyWarningLevelAttrEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EarlyWarningLevelDao {
    /**
     * (early_warning_level_cd != '1' and early_warning_level_cd != '2')的书是不能被删除的
     */
    @Delete(" DELETE FROM early_warning_level_attr\n" +
            " WHERE (early_warning_level_cd != '0' and early_warning_level_cd != '1' and early_warning_level_cd != '2')  ")
    void deleteSpecial();

    @Update(" UPDATE early_warning_level_attr\n" +
            " SET\n" +
            "  internal_processing_days     = #{internalProcessingDays},\n" +
            "  head_transportation_days     = #{headTransportationDays},\n" +
            "  signed_income_warehouse_days = #{signedIncomeWarehouseDays},\n" +
            "  aging_days                   = #{agingDays}\n" +
            " where early_warning_level_cd       = #{earlyWarningLevelCd} "
    )
    void update(EarlyWarningLevelAttrEntity entity);

    @Insert(" INSERT INTO early_warning_level_attr\n" +
            "(\n" +
            "  early_warning_level_cd,\n" +
            "  early_warning_level_desc,\n" +
            "  internal_processing_days,\n" +
            "  head_transportation_days,\n" +
            "  signed_income_warehouse_days,\n" +
            "  aging_days,\n" +
            "  enable_flag,\n" +
            "  create_time\n" +
            ") VALUES (\n" +
            "  #{earlyWarningLevelCd},\n" +
            "  #{earlyWarningLevelDesc},\n" +
            "  #{internalProcessingDays},\n" +
            "  #{headTransportationDays},\n" +
            "  #{signedIncomeWarehouseDays},\n" +
            "  #{agingDays},\n" +
            "  #{enableFlag},\n" +
            "  #{createTime}\n" +
            ") ")
    void insert(EarlyWarningLevelAttrEntity entity);

    @Select(" SELECT\n" +
            "  early_warning_level_cd,\n" +
            "  early_warning_level_desc,\n" +
            "  internal_processing_days,\n" +
            "  head_transportation_days,\n" +
            "  signed_income_warehouse_days,\n" +
            "  aging_days,\n" +
            "  enable_flag,\n" +
            "  create_time\n" +
            " FROM early_warning_level_attr ")
    List<EarlyWarningLevelAttrEntity> findAll();
}
