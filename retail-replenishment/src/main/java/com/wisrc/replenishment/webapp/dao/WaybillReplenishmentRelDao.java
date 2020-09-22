package com.wisrc.replenishment.webapp.dao;

import com.wisrc.replenishment.webapp.dao.sql.WaybillReplenishmentRelSql;
import com.wisrc.replenishment.webapp.entity.GetFbaByWaybill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface WaybillReplenishmentRelDao {
    @SelectProvider(type = WaybillReplenishmentRelSql.class, method = "getFbaByWaybill")
    List<GetFbaByWaybill> getFbaByWaybill(@Param("waybillIds") List waybillIds);
}
