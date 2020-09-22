package com.wisrc.replenishment.webapp.dao;

import com.wisrc.replenishment.webapp.entity.GetWaybillException;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WaybillExceptionInfoDao {
    @Select("SELECT wei.exception_type_cd, wei.exception_type_desc AS other, weta.exception_type_desc FROM waybill_exception_info AS wei LEFT JOIN waybill_exception_type_attr AS weta " +
            "ON weta.exception_type_cd = wei.exception_type_cd WHERE waybill_id = #{waybillId}")
    List<GetWaybillException> getException(@Param("waybillId") String waybillId);
}
