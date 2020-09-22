package com.wisrc.shipment.webapp.service;

import com.wisrc.shipment.webapp.entity.*;
import com.wisrc.shipment.webapp.vo.LogisticsOfferVo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface LogisticsOfferBasisInfoService {

    /**
     * @param pageNum       页码
     * @param pageSize      每页条数
     * @param channelTypeCd 渠道类型ID
     * @param labelCd       报价标签ID
     * @param deleteStatus  状态ID
     * @param keyword       关键字
     * @return
     */
    LinkedHashMap findByCond(String pageNum, String pageSize, int channelTypeCd, String labelCd, int deleteStatus, String keyword, int offerTypeCd, String zipcode);

    /**
     * 根据主键ID查询详细信息
     *
     * @param offerId 主键ID
     * @return
     */
    LogisticsOfferBasisInfoEntity get(String offerId);

    /**
     * 修改物流报价信息
     * @param ele
     */

    /**
     * 根据主键ID逻辑删除物流报价信息
     *
     * @param offerId
     * @param userId
     */
    void delete(String offerId, String userId);

    /**
     * 根据渠道名查询
     *
     * @param channelName
     */
    List<LogisticsOfferBasisInfoEntity> findByChannelName(String channelName);

    /**
     * 根据物流商id逻辑删除报价单信息
     *
     * @param shipmentId
     * @param userId
     */
    void deleteByshipMentId(String shipmentId, String userId);

    /**
     * 新增fba物流商报价
     *
     * @param ele
     */
    void addFba(LogicOfferBasisInfoFbaEnity ele);

    /**
     * 新增小包物流商报价
     *
     * @param ele
     */
    void addLittle(LogicOfferBasisInfoLittilEnity ele);

    /**
     * 更新fba物流商报价
     *
     * @param ele
     */
    void updateFbaByFbaId(LogicOfferBasisInfoFbaEnity ele);

    /**
     * 更新小包物流商报价
     *
     * @param ele
     */
    void updateLittleById(LogicOfferBasisInfoLittilEnity ele);

    List<LogisticsOfferBasisInfoEntity> getShipmentPrice(String offerId) throws Exception;

    /**
     * 查询物流下的报价单
     *
     * @param shipMentId  物流商id
     * @param offerTypeCd 报价单类型
     */
    List<LogisticsOfferVo> findByShipmentId(String shipMentId, int offerTypeCd);


    /**
     * 批量查询物流下的报价单
     *
     * @param offerIds 报价单Id
     */
    List<LogisticsOfferBasisInfoEntity> getOfferList(String[] offerIds);

    /**
     * 批量查询物流商id
     *
     * @param offerIds 报价单Id
     */
    List<Map<String, String>> getShipmentIdByOfferIDs(String[] offerIds);

    /**
     * 获取所有物流渠道名
     *
     * @param offerTypeCd 报价类型
     */
    List<SimpleOfferBasisInfoEnity> getAllOffers(int offerTypeCd) throws Exception;

    /**
     * 根据offerId查询报价单状态
     *
     * @param offerId 报价类型
     */
    Integer getStatusByOfferId(String offerId);


    List<CarrierEnity> getAllCarriers(int offerTypeCd);


    List<ChannelNameEnity> getAllChannleName();

    LinkedHashMap findLogisticByFba(String pageNum, String pageSize, int channelTypeCd, String[] fbaReplenishmentId, int channleTypeCd, String labelcds, String keyword);
}
