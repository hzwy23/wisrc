package com.wisrc.merchandise.dao;

import com.wisrc.merchandise.dto.msku.SalesStatusAutoDto;
import com.wisrc.merchandise.entity.MskuExtInfoEntity;
import com.wisrc.merchandise.vo.outside.SkuInfoByMskuAndShopVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MskuExtInfoDao {
    @Select("<script> "
            + "Select * FROM msku_ext_info WHERE 1=1 "
            + "<foreach item='id' index='index' collection='mskuIds' open='AND id IN (' separator=',' close=')'>"
            + "#{id}"
            + "</foreach>"
            + "</script>")
    List<MskuExtInfoEntity> getMskuExtInfoByIds(@Param("mskuIds") List mskuIds) throws Exception;

    @Select("Select * FROM msku_ext_info WHERE id = #{id}")
    MskuExtInfoEntity getMskuExtInfoById(@Param("id") String id) throws Exception;

    @Insert("INSERT INTO msku_ext_info(id, asin, fn_sku_id, shelf_date, delivery_mode) VALUES(#{id}, #{asin}, #{fnSkuId}, #{shelfDate}, #{deliveryMode}) ")
    void saveMskuExtInfo(MskuExtInfoEntity mskuExtInfoEntity) throws Exception;

    @Update("UPDATE msku_ext_info SET asin = #{asin}, fn_sku_id = #{fnSkuId}, shelf_date = #{shelfDate}, delivery_mode = #{deliveryMode} WHERE id = #{id}")
    void editMskuExtInfo(MskuExtInfoEntity mskuExtInfoEntity) throws Exception;

    @Select("Select id FROM msku_ext_info WHERE delivery_mode = #{deliveryMode}")
    List<String> getMskuExtInfoByDeliveryMode(@Param("deliveryMode") Integer deliveryMode) throws Exception;

    @Select("SELECT fn_sku_id from msku_ext_info , msku_info WHERE msku_info.id = msku_ext_info.id AND msku_info.msku_id = #{mskuId} AND msku_info.shop_id = #{shopId}")
    String getFnCodeByMskuAndShop(@Param("mskuId")String mskuId,
                                  @Param("shopId")String shopId);

    @Select("SELECT sku_id from msku_ext_info a LEFT JOIN msku_info b ON a.id = b.id where a.fn_sku_id = #{fnSkuId}")
    String finSkuIdByFnskuId(@Param("fnSkuId") String fnSkuId);

    /**
     * 根据mksu和shopid查询查msku信息
     * @param mskuId
     * @param shopId
     * @return
     */
    @Select("SELECT commodity_id,msku_id,msku_name,sku_id,fn_sku_id,asin,employee_id AS charge_person_id,status_name as sale_status FROM v_msku_info where msku_id = #{mskuId} AND shop_id = #{shopId}")
    SkuInfoByMskuAndShopVo getMskuInfoByMskuIdAndShopId(@Param("mskuId") String mskuId,
                                                        @Param("shopId") String shopId);
}
