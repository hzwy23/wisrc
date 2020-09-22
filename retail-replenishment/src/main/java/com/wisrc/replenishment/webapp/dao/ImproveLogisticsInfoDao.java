package com.wisrc.replenishment.webapp.dao;

import com.wisrc.replenishment.webapp.entity.ImproveLogisticsInfoEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ImproveLogisticsInfoDao {
    /**
     * 通过物流运单id修改物流单号、物流面单、检测报告
     *
     * @param ele
     */
    @Update("update waybill_info set logistics_track_url = #{logisticsTrackUrl},logistics_id=#{logisticsId},logistics_surface_url=#{logisticsSurfaceUrl},examining_report_url=#{examiningReportUrl},so_no_url = #{soNoUrl}, code_cd = #{codeCd} where waybill_id=#{waybillId}")
    void ImproveLogisticsInfo(ImproveLogisticsInfoEntity ele);


    @Select("select waybill_id, waybill_order_date, offer_id, logistics_id, sign_in_date, logistics_surface_url, examining_report_url, logistics_type_cd, remark, delete_status, delete_random, create_time, create_user, modify_time, modify_user  from waybill_info where waybill_id = #{waybillId}")
    ImproveLogisticsInfoEntity get(String waybillId);

    /**
     * 通过物流运单id删除物流运单信息
     *
     * @param waybillId
     */
    @Delete("delete from waybill_info where waybill_id=#{waybillId}")
    void delete(String waybillId);


}
