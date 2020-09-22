package com.wisrc.replenishment.webapp.dao;

import com.wisrc.replenishment.webapp.entity.FbaMskuPackInfoEntity;
import com.wisrc.replenishment.webapp.vo.FbaMskuPackQueryVO;
import com.wisrc.replenishment.webapp.vo.waybill.WaybillMskuNumVO;
import com.wisrc.replenishment.webapp.vo.wms.InsertPackVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface FbaMskuPackInfoDao {

    @Select("select * from replenishment_msku_pack_info where replenishment_commodity_id=#{replenishmentCommodityId} and delete_status = 0")
    List<FbaMskuPackQueryVO> findByFbaCommId(String replenishmentCommodityId);

//    @Update("update replenishment_msku_pack_info set outer_box_specification_len=#{outerBoxSpecificationLen},set outer_box_specification_width=#{outerBoxSpecificationWidth}," +
//            "set outer_box_specification_height=#{outerBoxSpecificationHeight}, set packing_quantity=#{packingQuantity}, set number_of_boxes=#{numberOfBoxes}," +
//            "set packing_weight=#{packingWeight} where replenishment_commodity_id=#{replenishmentCommodityId}")
//    void updateFbaMskuPack(FbaMskuPackUpdateVO fbaMskuPackUpdateVO);

    @Update("update replenishment_msku_pack_info set delete_status=#{deleteStatus} where replenishment_commodity_id=#{replenishmentCommodityId}")
    void deleteFbaMskuPack(@Param("deleteStatus") int deleteStatus, @Param("replenishmentCommodityId") String replenishmentCommodityId);

    @Insert("insert into replenishment_msku_pack_info (uuid,replenishment_commodity_id,outer_box_specification_len,outer_box_specification_width,outer_box_specification_height,packing_quantity," +
            "number_of_boxes,packing_weight,replenishment_quantity,create_time,create_user,delete_status,is_standard) values (#{uuid},#{replenishmentCommodityId},#{outerBoxSpecificationLen},#{outerBoxSpecificationWidth},#{outerBoxSpecificationHeight}," +
            "#{packingQuantity},#{numberOfBoxes},#{packingWeight},#{replenishmentQuantity},#{createTime},#{createUser},#{deleteStatus},#{isStandard})")
    void saveFbaMskuPack(FbaMskuPackInfoEntity fbaMskuPackInfoEntity);

    @Insert("insert into replenishment_msku_pack_info (uuid,replenishment_commodity_id,outer_box_specification_len,outer_box_specification_width,outer_box_specification_height,packing_quantity," +
            " number_of_boxes,packing_weight,modify_time,modify_user,delete_status,replenishment_quantity,is_standard) values (#{uuid},#{replenishmentCommodityId},#{outerBoxSpecificationLen},#{outerBoxSpecificationWidth}," +
            " #{outerBoxSpecificationHeight},#{packingQuantity},#{numberOfBoxes},#{packingWeight},#{modifyTime},#{modifyUser},#{deleteStatus},#{replenishmentQuantity},#{isStandard})")
    void updateFbaMskuPack(FbaMskuPackInfoEntity fbaMskuPackInfoEntity);

    @Update("update replenishment_msku_pack_info set sign_in_quantity =#{signInQuantity} where uuid =#{uuid}")
    void updateSignInQuantity(WaybillMskuNumVO vo);

    @Insert("insert into replenishment_msku_pack_info (uuid,replenishment_commodity_id,outer_box_specification_len,outer_box_specification_width,outer_box_specification_height,packing_quantity," +
            " number_of_boxes,packing_weight,delete_status,replenishment_quantity,create_time,packing_number,delivery_number) values (#{uuid},#{replenishmentCommodityId},#{outerBoxSpecificationLen},#{outerBoxSpecificationWidth}," +
            " #{outerBoxSpecificationHeight},#{packingQuantity},#{numberOfBoxes},#{packingWeight},#{deleteStatus},#{replenishmentQuantity},#{createTime},#{packingNumber},#{deliveryNumber})")
    void updateFbaPack(InsertPackVO insertPackVO);

    @Select("select replenishment_commodity_id from replenishment_msku_pack_info where uuid =#{uuid} and delete_status=0")
    String getReplenishmentId(String uuid);

    @Select("select uuid,sign_in_quantity from replenishment_msku_pack_info where replenishment_commodity_id =#{replenishmentCommodityId} and delete_status=0")
    List<WaybillMskuNumVO> getDetail(@Param("replenishmentCommodityId") String replenishmentCommodityId);

    @Update("update replenishment_msku_info set sign_in_quantity=#{signQuantity} where replenishment_commodity_id=#{replenishmentId}")
    void updateMskuSignInQuantity(@Param("signQuantity") int signQuantity, @Param("replenishmentId") String replenishmentId);

    @Select("SELECT fba_replenishment_id FROM replenishment_msku_info WHERE replenishment_commodity_id = #{replenishmentCommodityId}")
    String getFbaIdByUuid(@Param("replenishmentCommodityId") String replenishmentCommodityId);

    @Update("update replenishment_msku_info set replenishment_quantity=#{replenishmentNum} where replenishment_commodity_id=#{repCommodityId}")
    void updateMskuReplenishment(@Param("replenishmentNum") int replenishmentNum, @Param("repCommodityId") String repCommodityId);
}
