package com.wisrc.replenishment.webapp.dao;

import com.wisrc.replenishment.webapp.vo.CustomsProductListVO;
import com.wisrc.replenishment.webapp.vo.ReplenishShippingDataVO;
import org.apache.ibatis.annotations.*;


@Mapper
public interface ReplenishShippingDataDao {
    @Update("update replenishment_msku_info set delivery_number=#{deliveryNumber},packing_number=#{packingNumber} where replenishment_commodity_id=#{replenishmentCommodityId}")
    void updateReplenishmentDeliveryNumber(@Param("deliveryNumber") int deliveryNumber, @Param("packingNumber") int packingNumber, @Param("replenishmentCommodityId") String replenishmentCommodityId);

    @Update("update replenishment_msku_info set gross_weight=#{grossWeight},net_weight=#{netWeight},declare_unit_price=#{declareUnitPrice}, " +
            "msku_unit_cd=#{mskuUnitCd},declaration_elements=#{declarationElements},declare_subtotal=#{declareSubtotal} where replenishment_commodity_id=#{replenishmentCommodityId}")
    void updateCustomsMskuInfo(CustomsProductListVO ele);

    @Select("select * from  replenishment_msku_info where replenishment_commodity_id=#{replenishmentCommodityId} ")
    ReplenishShippingDataVO get(@Param(value = "replenishmentCommodityId") String replenishmentCommodityId);

    @Update("update replenishment_msku_pack_info set delete_status =1 where replenishment_commodity_id=#{replenishmentCommodityId}")
    void delete(@Param("replenishmentCommodityId") String replenishmentCommodityId);

    @Insert("insert into replenishment_msku_pack_info (uuid, replenishment_commodity_id, outer_box_specification_len, outer_box_specification_width, outer_box_specification_height, packing_quantity, number_of_boxes, packing_weight,delivery_number,packing_number,delete_status) values " +
            "(#{uuid},#{replenishmentCommodityId},#{outerBoxSpecificationLen},#{outerBoxSpecificationWidth},#{outerBoxSpecificationHeight},#{packingQuantity},#{numberOfBoxes},#{packingWeight},#{deliveryNumber},#{packingNumber},0)")
    void insertReplenishmentMskuInfo(ReplenishShippingDataVO vo);
}
