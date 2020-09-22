package com.wisrc.shipment.webapp.dao;

import com.wisrc.shipment.webapp.dao.sql.ReplenishmentRemindSQL;
import com.wisrc.shipment.webapp.entity.FbaWarningTypeAttrEntity;
import com.wisrc.shipment.webapp.entity.FbaReplenishmentRemindEntity;
import com.wisrc.shipment.webapp.entity.FbaWarningDaysEntity;
import com.wisrc.shipment.webapp.entity.ProposalSchemeEntity;
import org.apache.ibatis.annotations.*;

import java.util.HashSet;
import java.util.List;

@Mapper
public interface ReplenishmentRemindDao {

    @SelectProvider(type = ReplenishmentRemindSQL.class, method = "selectRemind")
    List<FbaReplenishmentRemindEntity> selectRemind(@Param("shopId") String shopId,
                                                    @Param("warningType") Integer warningType, @Param("mskuIds") HashSet<String> mskuIds,
                                                    @Param("sort") String sort);

    @Select("SELECT msku_id, shop_id, replenishment_quantity, estimated_weight, estimated_volume FROM fba_replenishment_remind WHERE replenishment_id = #{replenishmentId}")
    FbaReplenishmentRemindEntity selectRemindById(String replenishmentId);

    @SelectProvider(type = ReplenishmentRemindSQL.class, method = "selectSchemed")
    List<ProposalSchemeEntity> selectSchemedById(@Param("uuid") String uuid, @Param("replenishmentId") String replenishmentId);

    @Select("SELECT warning_id, warning_days FROM fba_warning_days")
    List<FbaWarningDaysEntity> selectWarningDays();

    @Select("SELECT warning_type_cd, warning_type_desc FROM fba_warning_type_attr")
    List<FbaWarningTypeAttrEntity> selectWarningTypeAttr();

    @Update("UPDATE fba_warning_days SET warning_days = #{days} WHERE warning_id = #{warningId}")
    boolean updateWarningDaysById(@Param("warningId") String warningId, @Param("days") Integer days);

    @Update("UPDATE fba_replenishment_remind SET safe_stock_days = #{days} WHERE replenishment_id = #{replenishmentId}")
    boolean updateSafeDaysById(@Param("replenishmentId") String replenishmentId, @Param("days") Integer days);

}
