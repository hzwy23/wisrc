package com.wisrc.replenishment.webapp.service;


import com.wisrc.replenishment.webapp.entity.FbaReplenishmentInfoEntity;
import com.wisrc.replenishment.webapp.utils.Result;
import com.wisrc.replenishment.webapp.vo.*;
import com.wisrc.replenishment.webapp.vo.delivery.*;
import com.wisrc.replenishment.webapp.vo.wms.FbaPackingDataReturnVO;
import com.wisrc.replenishment.webapp.vo.wms.FbaReplenishmentOutReturnVO;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface FbaReplenishmentInfoService {

    LinkedHashMap findAll(String shopId, String warehouseId, Date createBeginTime, Date createEndTime, String statusCd, String shipmentId, String manager, String labelCd, String keyWord, String userId);

    LinkedHashMap findInfoByCond(int pageNum, int pageSize, String shopId, String warehouseId, Date createBeginTime, Date createEndTime, String statusCd, String shipmentId, String manager, String labelCd, String keyWord, String userId);

    FbaStatusNumberVO findStatusNumber();

    //勾选导出功能
    LinkedHashMap findInfoByCond(int pageNum, int pageSize, String shopId, String warehouseId, Date createBeginTime, Date createEndTime, String[] fbaIds, String statusCd, String shipmentId, String manager, String labelCd, String keyWord, String userId);

    FbaReplenishmentInfoVO findById(String fbaReplenishmentId);

    void saveInfo(FbaInfoAddVO infoVO, String userId);

    void updateStatus(String modifyUser, String modifyTime, int statusCd, String fbaReplenishmentId);

    void updateShipAndChannel(String shipmentId, String channelName, String fbaReplenishmentId);

    Result cancelReplen(String fbaReplenishmentId, String cancelReason, String userId);

    Result updateAmazonInfo(FbaAmazonVO amazonInfo);

    void updateMskuPackInfo(List<FbaMskuSpecificationVO> mskuSpecificationList, String userId);

    void updateRepQuantity(FbaRepQuantityVO[] fbaRepQuantitys, String modifyUser, Timestamp modifyTime);

    void updateStatusById(int statusCd, String fbaReplenishmentId);

    List<BillEstimateVO> findBillEst(String[] fbaReplenishmentId, String channelType);

    /*DeliveryConfirmVO*/DeliveryConSelectVO comfireDelivery(String fbaReplenishmentId);

    //单选物流交运
    DeliverySelectVO selectDelivery(String fbaReplenishmentId);

    //合并交运
    Map<String, String> mergeDelivery(String[] fbaIds);

    CalcuDeliveryVO calcuAmount(String offerId, String[] fbaIds, String channelTypeCd);

    DeliveryVO getfbaAndBillInfo(String[] fbaIds, String channelType);


    /**
     * 获取称重信息
     *
     * @param offerId       物流报价ID
     * @param fbaIds        FBA补货单号
     * @param channelTypeCd 渠道类型 1：空运，2：海运，3：陆运
     */
    DeliveryConSelectVO getFreightInfo(String offerId, String[] fbaIds, String channelTypeCd);


    Map check(String fbaReplenishmentId, String bacthNumber);

    FbaReplenishmentInfoEntity download(String fbaReplenishmentId);

    void changeReturn(FbaPackingDataReturnVO vo);

    void wmsReturnFbaDelivery(FbaReplenishmentOutReturnVO fbaReplenishmentOutReturnVO);

    List<FbaMskuVo> findbyidBatch(String[] fbaReplenishmentIds);
}
