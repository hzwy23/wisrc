package com.wisrc.replenishment.webapp.dao;

import com.wisrc.replenishment.webapp.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WaybillInfoAttrDao {
    /**
     * 报关类型码表
     *
     * @return
     */
    @Select("select customs_type_cd,customs_type_name from declare_customs_type_attr")
    List<CustomsAttrEntity> findCustomsAttr();

    /**
     * 运单异常状态码表
     *
     * @return
     */
    @Select("select exception_type_cd,exception_type_desc from waybill_exception_type_attr")
    List<ExceptionTypeAttrEntity> findExceptionAttr();

    /**
     * 物流状态码表
     *
     * @return
     */
    @Select("select logistics_type_cd,logistics_type_name from logistics_type_attr")
    List<LogisticsTypeAttrEntity> findLogisticsAttr();

    /**
     * 重量类型码表
     *
     * @return
     */
    @Select("select weigh_type_cd,weigh_type_name from weight_type_attr")
    List<WeightTypeAttrEntity> findWeightAttr();


    /**
     * 报关资料产品单位码表
     *
     * @return
     */
    @Select("SELECT msku_unit_cd, msku_unit_name FROM declare_msk_unit_attr")
    List<DeclareMskuUnitAttrEntity> findUnitAttr();

    @Select("SELECT msku_unit_cd, msku_unit_name FROM declare_msk_unit_attr WHERE msku_unit_cd = #{unitCd}")
    List<DeclareMskuUnitAttrEntity> findUnitAttrById(@Param("unitCd") Integer unitCd);
}
