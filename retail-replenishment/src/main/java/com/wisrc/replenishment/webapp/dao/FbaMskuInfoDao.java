package com.wisrc.replenishment.webapp.dao;

import com.wisrc.replenishment.webapp.dao.sql.FbaMskuInfoSQL;
import com.wisrc.replenishment.webapp.entity.FbaMskuInfoEntity;
import com.wisrc.replenishment.webapp.entity.VReplenishmentMskuEntity;
import com.wisrc.replenishment.webapp.vo.FbaMskuInfoVO;
import com.wisrc.replenishment.webapp.vo.FbaNewMskuInfoVO;
import com.wisrc.replenishment.webapp.vo.fbaMskuInfoPortionVo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface FbaMskuInfoDao {
    /**
     * 添加补货单商品信息
     *
     * @param mskuInfoEntity
     */
    @Insert("insert into replenishment_msku_info (replenishment_commodity_id,fba_replenishment_id,replenishment_quantity,shop_id,commodity_id,msku_id,create_time,create_user,modify_user,modify_time," +
            "delete_status) values (#{replenishmentCommodityId},#{fbaReplenishmentId},#{replenishmentQuantity},#{shopId},#{commodityId},#{mskuId},#{createTime},#{createUser},#{modifyUser},#{modifyTime},#{deleteStatus})")
    void saveMskuInfo(FbaMskuInfoEntity mskuInfoEntity);

    /**
     * 删除补货单商品信息  修改删除表示状态 deleteStatus
     *
     * @param mskuInfoVO
     */
    @Update("update replenishment_msku_info set modify_user=#{modifyUser},modify_time=#{modifyTime},delete_status=#{deleteStatus} where fba_replenishment_id=#{fbaReplenishmentId}")
    void cancelMskuInfo(FbaMskuInfoVO mskuInfoVO);

    /**
     * 根据补货单Id查找详细商品信息
     *
     * @param fbaReplenishmentId
     * @return
     */
    @Select("select replenishment_commodity_id,fba_replenishment_id,msku_id,commodity_id,shop_id,replenishment_quantity,delivery_number,packing_number,sign_in_quantity,modify_user,modify_time,delete_status," +
            "declare_unit_price,msku_unit_cd,declaration_elements,sign_in_quantity from replenishment_msku_info where fba_replenishment_id=#{fbaReplenishmentId}")
    List<FbaMskuInfoVO> findMskuByReplenId(String fbaReplenishmentId);

    /**
     * 根据补货单Id集合查询详细商品信息
     *
     * @param fbaReplenishmentIds
     * @return
     */
    @SelectProvider(type = FbaMskuInfoSQL.class, method = "findMskuByIds")
    List<FbaMskuInfoVO> findMskuByIds(@Param("fbaReplenishmentIds") String[] fbaReplenishmentIds);

    /**
     * 根据补货单Id集合查询商品信息
     *
     * @param fbaReplenishmentIds
     * @return
     */
    @SelectProvider(type = FbaMskuInfoSQL.class, method = "getMskuInfo")
    List<FbaMskuInfoEntity> getMskuInfo(@Param("fbaReplenishmentIds") String[] fbaReplenishmentIds);

    /**
     * 改造后的查询详情
     *
     * @param fbaReplenishmentId
     * @return
     */
    @Select("select replenishment_commodity_id,fba_replenishment_id,msku_id,commodity_id,shop_id,replenishment_quantity,delivery_number,modify_user,modify_time,delete_status," +
            "declare_unit_price,msku_unit_cd,declaration_elements from replenishment_msku_info where fba_replenishment_id=#{fbaReplenishmentId}")
    List<FbaNewMskuInfoVO> findMskuByFbaId(String fbaReplenishmentId);

    /**
     * 根据商品ID集合查询补货单ID集合
     *
     * @param commodityIds
     * @return
     */
    @SelectProvider(type = FbaMskuInfoSQL.class, method = "findFbaByCommIdList")
    List<String> findFbaByCommIdList(@Param("commodityIds") String commodityIds);

    /**
     * 根据补货单ID查询商品ID集合
     *
     * @param fbaReplenishmentId
     * @return
     */
    @Select("select replenishment_commodity_id from replenishment_msku_info where fba_replenishment_id=#{fbaReplenishmentId}")
    List<String> findReplenishmentCommodityId(String fbaReplenishmentId);


    /**
     * 根据fbaid获取单对应的msk修改的信息
     */
    @Select("SELECT country_of_origin,texture_of_materia,purpose_desc,clearance_name FROM replenishment_msku_info WHERE fba_replenishment_id = #{fbaReplenishmentId} and msku_id = #{mskuId}")
    fbaMskuInfoPortionVo getInfoByFbaIdAndMskuId(@Param("fbaReplenishmentId") String fbaReplenishmentId, @Param("mskuId") String mskuId);

    /**
     * 通过商品库存编码取补货单ID，库存编码ID，发货数量，签收数量
     *
     * @param mskuId
     * @return
     */
    @Select("SELECT a.fba_replenishment_id,a.msku_id,a.delivery_number,a.sign_in_quantity from  replenishment_msku_info a where msku_id=#{mskuId}")
    List<FbaMskuInfoEntity> getFbaIdByMskuId(@Param("mskuId") String mskuId);

    /**
     * 通过补货单Id取待签收状态
     *
     * @param fbaReplenishmentId
     * @return
     */
    @Select("SELECT status_cd from fba_replenishment_info where fba_replenishment_id=#{fbaReplenishmentId}")
    int getSignStatusByFbaId(@Param("fbaReplenishmentId") String fbaReplenishmentId);


    /**
     * @param commodityIds 商品ID列表
     */
    @SelectProvider(type = FbaMskuInfoSQL.class, method = "findFbaByCommIdOnwayInfo")
    List<VReplenishmentMskuEntity> getMskuUnderway(@Param("commodityIds") String commodityIds);

}
