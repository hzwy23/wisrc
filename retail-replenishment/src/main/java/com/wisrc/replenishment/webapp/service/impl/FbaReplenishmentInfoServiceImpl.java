package com.wisrc.replenishment.webapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.wisrc.replenishment.webapp.dao.*;
import com.wisrc.replenishment.webapp.entity.*;
import com.wisrc.replenishment.webapp.service.*;
import com.wisrc.replenishment.webapp.utils.*;
import com.wisrc.replenishment.webapp.vo.*;
import com.wisrc.replenishment.webapp.vo.delivery.*;
import com.wisrc.replenishment.webapp.vo.transferorder.TransferInfoReturnVo;
import com.wisrc.replenishment.webapp.vo.waybill.LogisticsTrackInfoVO;
import com.wisrc.replenishment.webapp.vo.wms.*;
import com.wisrc.replenishment.webapp.service.externalService.OperationService;

import java.math.RoundingMode;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class FbaReplenishmentInfoServiceImpl implements FbaReplenishmentInfoService {

    @Autowired
    private FbaReplenishmentInfoDao infoDao;
    @Autowired
    private FbaMskuInfoDao mskuInfoDao;
    @Autowired
    private FbaReplenishmentLabelRelDao labelRelDao;
    @Autowired
    private FbaMskuPackInfoDao fbaMskuPackInfoDao;
    @Autowired
    private FbaReplenishmentLabelAttrDao labelAttrDao;
    @Autowired
    private FbaMskuInfoService fbaMskuInfoService;
    @Autowired
    private FbaReplenishmentLabelRelService labelRelService;
    @Autowired
    private WaybillInfoDao waybillInfoDao;
    @Autowired
    private MskuInfoService mskuInfoService;
    @Autowired
    private MskuInfoListService mskuInfoListService;
    @Autowired
    private WarehouseInfoService warehouseInfoService;
    @Autowired
    private WarehouseListService warehouseListService;
    @Autowired
    private WaybillInfoService waybillInfoService;
    @Autowired
    private ReplenishmentShipmentListService replenishmentShipmentListService;
    @Autowired
    private ShipmentService shipmentService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private CrawlerService crawlerService;
    @Autowired
    private ProductListService productListService;
    @Autowired
    private MskuOutsideService mskuOutsideService;
    @Autowired
    private OperationService operationService;
    @Autowired
    private FbaReplenishmentPickupAttrService fbaReplenishmentPickupAttrService;
    @Autowired
    private FbaReplenishmentInfoDao fbaReplenishmentInfoDao;
    @Autowired
    private ReplenishShippingDataDao replenishShippingDataDao;
    @Autowired
    private ProductService productService;
    @Autowired
    private Gson gson;
    @Autowired
    private TransferService transferService;
    @Value("${boxInfoUrl}")
    private String boxInfoUrl;

    @Autowired
    private TransferDao transferDao;

    public LinkedHashMap findAll(String shopId, String warehouseId, java.sql.Date createBeginTime, java.sql.Date createEndTime, String statusCd, String shipmentId, String manager, String labelCd, String keyWord, String userId) {
        List<FbaQueryResultVO> infoVoList = findInfoByCond(null, null, null, null, null, null, null, null, null, userId);
        return PageData.pack(-1, -1, "fbaReplenmentInfoList", infoVoList);
    }

    public List<FbaQueryResultVO> findInfoByCond(String shopId, String warehouseId, java.sql.Date createBeginTime, java.sql.Date createEndTime, String statusCd, String shipmentId, String manager, String labelCd, String keyWord, String userId) {

        if (manager == null && labelCd == null && keyWord == null) {
            List<FbaReplenishmentInfoEntity> infoList = infoDao.findAll(createBeginTime, createEndTime, shopId, warehouseId, shipmentId, statusCd);
            List<FbaQueryResultVO> infoVoList = findQueryReult(infoList);
            return infoVoList;
        } else {
            String[] fbaQueryIds = getFbaIdsByCond(shipmentId, manager, labelCd, keyWord, userId);
            List<FbaReplenishmentInfoEntity> infoList = infoDao.findInfoByCond(createBeginTime, createEndTime, shopId, warehouseId, shipmentId, null, statusCd, fbaQueryIds);
            List<FbaQueryResultVO> infoVoList = findQueryReult(infoList);
            return infoVoList;
        }
    }

    @Override
    public LinkedHashMap findInfoByCond(int pageNum, int pageSize, String shopId, String warehouseId, java.sql.Date createBeginTime, java.sql.Date createEndTime, String statusCd, String shipmentId, String manager, String labelCd, String keyWord, String userId) {
        if (manager == null && labelCd == null && keyWord == null) {
            PageHelper.startPage(pageNum, pageSize);
            List<FbaReplenishmentInfoEntity> infoList = infoDao.findAll(createBeginTime, createEndTime, shopId, warehouseId, shipmentId, statusCd);
            PageInfo pageInfo = new PageInfo(infoList);
            List<FbaQueryResultVO> infoVoList = findQueryReult(infoList);
            return PageData.pack(pageInfo.getTotal(), pageInfo.getPages(), "fbaReplenmentInfoList", infoVoList);
        } else {
            String[] fbaQueryIds = getFbaIdsByCond(shipmentId, manager, labelCd, keyWord, userId);
            PageHelper.startPage(pageNum, pageSize);
            List<FbaReplenishmentInfoEntity> infoList = infoDao.findInfoByCond(createBeginTime, createEndTime, shopId, warehouseId, shipmentId, null, statusCd, fbaQueryIds);
            PageInfo pageInfo = new PageInfo(infoList);
            List<FbaQueryResultVO> infoVoList = findQueryReult(infoList);

            return PageData.pack(pageInfo.getTotal(), pageInfo.getPages(), "fbaReplenmentInfoList", infoVoList);
        }
    }

    public LinkedHashMap findInfoByCond(int pageNum, int pageSize, String shopId, String warehouseId, java.sql.Date createBeginTime, java.sql.Date createEndTime, String[] fbaIds, String statusCd, String shipmentId, String manager, String labelCd, String keyWord, String userId) {
        String[] fbaQueryIds = getFbaIdsByCond(shipmentId, manager, labelCd, keyWord, userId);
        PageHelper.startPage(pageNum, pageSize);
        List<FbaReplenishmentInfoEntity> infoList = infoDao.findInfoByCond(createBeginTime, createEndTime, shopId, warehouseId, shipmentId, fbaIds, statusCd, fbaQueryIds);
        PageInfo pageInfo = new PageInfo(infoList);
        List<FbaQueryResultVO> infoVoList = findQueryReult(infoList);

        return PageData.pack(pageInfo.getTotal(), pageInfo.getPages(), "fbaReplenmentInfoList", infoVoList);
    }

    public String[] getFbaIdsByCond(String shipmentId, String manager, String labelCd, String keyWord, String userId) {
        List<String> fbaIdList = new ArrayList<>();
        Set<String> commodityIdList = new HashSet<>();
        try {
            if (manager != null || keyWord != null) {
                Result mskuPageData = mskuOutsideService.getMskuPage(null, manager, null, keyWord, null, null, null, null, userId);
                HashMap mskuMap = (HashMap) mskuPageData.getData();
                List<HashMap> mskuList = (List) mskuMap.get("mskuList");
                for (HashMap map : mskuList) {
                    commodityIdList.add((String) map.get("id"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (labelCd != null) {
            List<String> fbaIdsByLabel = labelRelDao.findFbaIdByLabelCd(labelCd);
            for (String fbaIds : fbaIdsByLabel) {
                fbaIdList.add(fbaIds);
            }
        }

        if (shipmentId != null) {
            List<String> fbaIdByShipment = infoDao.findFbaIdByShipment(shipmentId);
            for (String fbaIds : fbaIdByShipment) {
                fbaIdList.add(fbaIds);
            }
        }

        try {
            if (keyWord != null) {
                Set<String> commList = mskuInfoListService.getCommitIdList(keyWord, keyWord, keyWord);
                for (String commid : commList) {
                    commodityIdList.add(commid);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (keyWord != null) {
            List<String> fbaByBatchAndId = infoDao.findFbaByBatchAndId(keyWord, keyWord);
            for (String fbaId : fbaByBatchAndId) {
                fbaIdList.add(fbaId);
            }
            List<String> fbaByWaybillId = infoDao.findFbaByWaybillId(keyWord);
            for (String fbaId : fbaByWaybillId) {
                fbaIdList.add(fbaId);
            }
        }

        String[] commodityIdArr = new String[commodityIdList.size()];
        commodityIdArr = commodityIdList.toArray(commodityIdArr);
        String commodityListStr = ArrayToInArguments.toInArgs(commodityIdArr);
        //commodityIdList.toArray(commodityIdArr);
        List<String> fbaByCommIdList = mskuInfoDao.findFbaByCommIdList(commodityListStr);

        for (String fbaId : fbaByCommIdList) {
            fbaIdList.add(fbaId);
        }

        String[] fbaIdArr = new String[fbaIdList.size()];
        fbaIdList.toArray(fbaIdArr);
        return fbaIdArr;
    }

    public List<FbaQueryResultVO> findQueryReult(List<FbaReplenishmentInfoEntity> infoList) {
        List<FbaQueryResultVO> infoVoList = new ArrayList<FbaQueryResultVO>();
        List<String> fbaIds = new ArrayList<>();//补货单id集合
        List<String> shopIds = new ArrayList<>();//店铺Id集合
        List<String> warehouseIds = new ArrayList<>();//发货仓Id集合
        List<String> commodityIds = new ArrayList<>();//商品Id集合
        List<String> offerIds = new ArrayList<>();//物流渠道报价Id集合
        Map warehouseNameList = new HashMap();
        Map shipmentList = new HashMap();
        Map mskuList = new HashMap();
        for (FbaReplenishmentInfoEntity fbaReplenishmentInfoEntity : infoList) {
            fbaIds.add(fbaReplenishmentInfoEntity.getFbaReplenishmentId());
            shopIds.add(fbaReplenishmentInfoEntity.getShopId());
            warehouseIds.add(fbaReplenishmentInfoEntity.getWarehouseId());
            offerIds.add(fbaReplenishmentInfoEntity.getOfferId());
        }
        List<FbaMskuInfoVO> mskuByIds = mskuInfoDao.findMskuByIds(fbaIds.toArray(new String[fbaIds.size()]));
        for (FbaMskuInfoVO vo : mskuByIds) {
            commodityIds.add(vo.getCommodityId());
        }
        try {
            mskuList = mskuInfoListService.getMskuInfoList(commodityIds);
        } catch (Exception e) {

        }
        Map productMap = new HashMap();
        try {
            productMap = productListService.getProductList((List<String>) mskuList.get("skuIds"));
        } catch (Exception e) {

        }
        try {
            //获取仓库名称集合
            warehouseNameList = warehouseListService.getWarehouseNameList(warehouseIds);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < infoList.size(); i++) {
            FbaReplenishmentInfoEntity fbaInfo = infoList.get(i);
            FbaQueryResultVO resultInfo = new FbaQueryResultVO();
            resultInfo.setShipmentId(fbaInfo.getShipmentId());
            String fbaId = fbaInfo.getFbaReplenishmentId();
            List<FbaMskuInfoVO> fbaMskuInfoList = fbaMskuInfoService.findMskuByReplenId(fbaId, mskuList, productMap);
            resultInfo.setMskuList(fbaMskuInfoList);
            Timestamp createDate = fbaInfo.getCreateTime();
            String batchNumber = fbaInfo.getBatchNumber();
            String warehouseName = (String) warehouseNameList.get(fbaInfo.getWarehouseId());
            int statusCd = fbaInfo.getStatusCd();
            String logisticsId = "";
            int logisticsTypeCd = 0;
            String shipChannelName = "";
            String wayBillId = infoDao.findWayBillIdByFbaId(fbaId);
            if (wayBillId != null && !wayBillId.isEmpty()) {
                WaybillInfoEntity wayBillInfo = waybillInfoDao.findInfoById(wayBillId);
                logisticsId = wayBillInfo.getLogisticsId();
                logisticsTypeCd = wayBillInfo.getLogisticsTypeCd();
                List<WaybillExceptionInfoEntity> excList = waybillInfoDao.findExcList(wayBillId);
                List<ExceptionTypeAttrEntity> excDescList = new ArrayList<ExceptionTypeAttrEntity>();
                if (excList != null && excList.size() > 0) {
                    for (int j = 0; j < excList.size(); j++) {
                        ExceptionTypeAttrEntity excEntity = new ExceptionTypeAttrEntity();
                        excEntity.setExceptionTypeCd(excList.get(j).getExceptionTypeCd());
                        excEntity.setExceptionTypeDesc(excList.get(j).getExceptionTypeDesc());
                        excDescList.add(excEntity);
                    }
                }
                resultInfo.setExceptionTypeDescList(excDescList);
            }
            String offerId = fbaInfo.getOfferId();
            resultInfo.setOfferId(offerId);
            //设置补货单标签信息
            List<FbaLabelVO> labelList = findLabelById(fbaId);

            resultInfo.setBatchNumber(batchNumber);
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (createDate != null) {
                resultInfo.setCreateDate(sdf.format(createDate).toString());
            }
            resultInfo.setFbaReplenishmentId(fbaId);
            resultInfo.setLabelList(labelList);
            resultInfo.setLogisticsId(logisticsId);
            resultInfo.setLogisticsTypeCd(logisticsTypeCd);
            resultInfo.setProvinceCode(fbaInfo.getCollectGoodsWarehouseId());
            resultInfo.setAmazonWarehouseAddress(infoList.get(i).getAmazonWarehouseAddress());
            resultInfo.setAmazonWarehouseZipcode(infoList.get(i).getAmazonWarehouseZipcode());
            resultInfo.setZipCode(fbaInfo.getAmazonWarehouseZipcode());
            resultInfo.setStatusCd(statusCd);
            resultInfo.setStatusCdName(infoDao.getStatusCdNameByCd(statusCd));
            resultInfo.setShipChannelName(shipChannelName);
            resultInfo.setWarehouseName(warehouseName);
            resultInfo.setPickupTypeCd(fbaInfo.getPickupTypeCd());
            infoVoList.add(resultInfo);
        }
        try {
            shipmentList = replenishmentShipmentListService.getShipmentList(offerIds);
        } catch (Exception e) {

        }

        for (FbaQueryResultVO resultVO : infoVoList) {
            Map shipmentMap = (Map) shipmentList.get(resultVO.getOfferId());
            String shipmentName = "";
            String channelName = "";
            if (shipmentMap != null) {
                shipmentName = (String) shipmentMap.get("shipmentName");
                channelName = (String) shipmentMap.get("channelName");
                resultVO.setShipChannelName(shipmentName + "-" + channelName);
            }
        }

        return infoVoList;
    }

    public List<FbaLabelVO> findLabelById(String fbaReplenishmentId) {
        List<FbaReplenishmentLabelRelEntity> labelEntityList = labelRelService.findLabelEntity(fbaReplenishmentId);
        List<FbaLabelVO> labelList = new ArrayList<>();
        for (FbaReplenishmentLabelRelEntity labelInfo : labelEntityList) {
            FbaLabelVO label = new FbaLabelVO();
            FbaReplenishmentLabelAttrEntity attrEntity = labelAttrDao.findByLabelCd(labelInfo.getLabelCd());
            label.setLabelColor(attrEntity.getLabelColor());
            label.setLabelName(labelInfo.getLabelName());
            label.setUuid(labelInfo.getUuid());
            label.setLabelCd(labelInfo.getLabelCd());
            labelList.add(label);

        }
        return labelList;
    }

    public FbaStatusNumberVO findStatusNumber() {
        FbaStatusNumberVO fbaNumber = new FbaStatusNumberVO();
        int amazomNumber = infoDao.getStatusNumber(0);
        int packNumber = infoDao.getStatusNumber(1);
        int deliveryNumber = infoDao.getStatusNumber(2);
        int signNumber = infoDao.getStatusNumber(3);
        int receiveNumber = infoDao.getStatusNumber(4);
        int cancelNumber = infoDao.getStatusNumber(5);
        int channelNumber = infoDao.getStatusNumber(6);
        fbaNumber.setAmazonNumber(amazomNumber);
        fbaNumber.setPackNumber(packNumber);
        fbaNumber.setDeliveryNumber(deliveryNumber);
        fbaNumber.setSignNumber(signNumber);
        fbaNumber.setReceiveNumber(receiveNumber);
        fbaNumber.setCancelNumber(cancelNumber);
        fbaNumber.setChannelNumber(channelNumber);
        return fbaNumber;
    }

    public FbaReplenishmentInfoVO findById(String fbaReplenishmentId) {
        FbaReplenishmentInfoEntity fbaReplenishmentInfoEntity = infoDao.findById(fbaReplenishmentId);

        FbaReplenishmentInfoVO fbarepInfo = new FbaReplenishmentInfoVO();
        //设置补货单基本信息
        fbarepInfo.setInfoEntity(fbaReplenishmentInfoEntity);
        //设置补货单标签信息
        List<FbaLabelVO> labelList = findLabelById(fbaReplenishmentId);
        fbarepInfo.setLabelList(labelList);
        //设置补货单创建时间信息
        Timestamp createTime = fbaReplenishmentInfoEntity.getCreateTime();
        fbarepInfo.setCreateDate(createTime);
        //商品信息
        List<FbaMskuInfoVO> fbaMskuInfoList = fbaMskuInfoService.findMskuByReplenId(fbaReplenishmentId);
        fbarepInfo.setMskuInfoList(fbaMskuInfoList);
        //发货仓信息 通过发货仓id查询发货仓名称
        String warehouseId = fbaReplenishmentInfoEntity.getWarehouseId();
        String warehouseName = "";
        try {
            Result wareResult = warehouseInfoService.getWarehouseName(warehouseId);
            if (200 == wareResult.getCode()) {
                LinkedHashMap data = (LinkedHashMap) wareResult.getData();
                if (data != null && !"".equals(data)) {
                    warehouseName = (String) data.get("warehouseName");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        fbarepInfo.setWarehouseName(warehouseName);
        fbarepInfo.setWarehouseName(warehouseName);
        fbarepInfo.setProvinceCode(fbaReplenishmentInfoEntity.getCollectGoodsWarehouseId());
        fbarepInfo.setZipCode(fbaReplenishmentInfoEntity.getAmazonWarehouseZipcode());
        //TODO 收货仓省份代码和邮编
       /* String provinceCode = "PHX3";
        String zipCode = "85043";
        fbarepInfo.setProvinceCode(provinceCode);
        fbarepInfo.setZipCode(zipCode);*/
        //通过店铺id获取店铺名称
        String shopName = "";
        String shopId = fbaReplenishmentInfoEntity.getShopId();
        Result shopResult = mskuInfoService.getShopName(shopId);
        if (200 == shopResult.getCode()) {
            LinkedHashMap data = (LinkedHashMap) shopResult.getData();
            if (data != null && !"".equals(data)) {
                shopName = (String) data.get("storeName");
            }
        }
        fbarepInfo.setShopName(shopName);
        String wayBillId = waybillInfoDao.findWaybillIdByFbaId(fbaReplenishmentId);
        if (wayBillId != null && !wayBillId.isEmpty()) {
            LogisticsTrackInfoVO logisticsTrackInfo = new LogisticsTrackInfoVO();
            List<FreightEstimateinfoEntity> freightInfo = waybillInfoDao.findFreightInfo(wayBillId);
            if (freightInfo.size() > 0) {
                logisticsTrackInfo.setEstimateDate(freightInfo.get(0).getEstimateDate());
            }
            WaybillInfoEntity wayBillInfo = waybillInfoDao.findInfoById(wayBillId);
            String logisticsId = wayBillInfo.getLogisticsId();
            List<LogisticsTrackInfoEntity> logisticsList = waybillInfoDao.findLogisticsList(logisticsId);
            logisticsTrackInfo.setLogisticsId(logisticsId);
            logisticsTrackInfo.setLogisticsInfoList(logisticsList);
            Date signInDate = wayBillInfo.getSignInDate();
            logisticsTrackInfo.setSignInDate(signInDate);
            logisticsTrackInfo.setCodeCd(wayBillInfo.getCodeCd());

            List<WaybillExceptionInfoEntity> excList = waybillInfoDao.findExcList(wayBillId);
            List<String> excDescList = new ArrayList<String>();
            if (excList != null && excList.size() > 0) {
                for (int i = 0; i < excList.size(); i++) {
                    excDescList.add(excList.get(i).getExceptionTypeDesc());
                }
            }
            String excDesc = StringUtils.join(excDescList.toArray(), ",");
            logisticsTrackInfo.setExceptionDesc(excDesc);
            //获取物流商名称和渠道名称
//            WaybillInfoVO wayInfo = waybillInfoService.findInfoById(wayBillId);
//            String shipmentName = wayInfo.getShipmentName();
//            String channelName = wayInfo.getChannelName();
//            logisticsTrackInfo.setShipChannel(shipmentName + "-" + channelName);
            String shipmentName = "";//物流商名称
            String channelName = "";//渠道名称
            try {
                Result result = shipmentService.getShipment(wayBillInfo.getOfferId());
                if (result.getCode() == 200) {
                    Map shipMentMap = (Map) result.getData();
                    if (shipMentMap != null) {
                        shipmentName = (String) shipMentMap.get("shipMentName");
                        channelName = (String) shipMentMap.get("channelName");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            logisticsTrackInfo.setShipChannel(shipmentName + "-" + channelName);
            fbarepInfo.setLogisticsTrackInfo(logisticsTrackInfo);

            //通过补货单号获取物流单号和物流异常说明
            fbarepInfo.setShipmentNumber(logisticsId);
            fbarepInfo.setShipmentExceptionDesc(excDesc);
        }

        return fbarepInfo;
    }

    public List<FbaDeliveryVO> deliveryFba(String[] fbaReplenishmentIds) {
        List<FbaDeliveryVO> deliveryList = new ArrayList<>();
        if (fbaReplenishmentIds != null && fbaReplenishmentIds.length > 0) {
            List<FbaMskuInfoEntity> mskuInfoList = fbaMskuInfoService.getMskuInfo(fbaReplenishmentIds);
            if (mskuInfoList.size() > 0) {
                for (String fbaReplenishmentId : fbaReplenishmentIds) {
                    FbaReplenishmentInfoVO infoVO = findById(fbaReplenishmentId);
                    FbaDeliveryVO delivery = new FbaDeliveryVO();
                    delivery.setShipmentChannel(infoVO.getLogisticsTrackInfo().getShipChannel());
                    delivery.setCreateDate(infoVO.getInfoEntity().getCreateTime());
                    delivery.setFbaReplenishmentId(infoVO.getInfoEntity().getFbaReplenishmentId());
                    delivery.setLabelRelEntityList(infoVO.getLabelList());
                    delivery.setProvinceCode(infoVO.getProvinceCode());
                    delivery.setShopName(infoVO.getShopName());
                    delivery.setReplenishmentBatch(infoVO.getInfoEntity().getBatchNumber());
                    delivery.setZipCode(infoVO.getZipCode());
                    delivery.setWarehouseName(infoVO.getWarehouseName());
                    deliveryList.add(delivery);
                }
            } else {
                for (String transferOrderId : fbaReplenishmentIds) {
                    if (transferOrderId != null || !transferOrderId.equals("")) {
                        System.out.println(transferOrderId);
                        TransferBasicInfoEntity entity = transferDao.findById(transferOrderId);
                        Result result = transferService.findById(transferOrderId);
                        TransferInfoReturnVo transferInfoReturnVo = (TransferInfoReturnVo) result.getData();
                        FbaDeliveryVO vo = new FbaDeliveryVO();
                        vo.setFbaReplenishmentId(transferOrderId);
                        vo.setCreateDate(formateStringToTimestamp(entity.getCreateTime()));
                        vo.setWarehouseName(transferInfoReturnVo.getWarehouseStartName());
                        //这个地方是吧目的仓放到ZipCode里面去了
                        vo.setZipCode(transferInfoReturnVo.getWarehouseEndName());
                        List<TransferLabelEntity> transferLabelEntityList = transferDao.findTransferLabelByTransferId(transferOrderId);
                        List<FbaLabelVO> labelVOS = new LinkedList<>();
                        for (TransferLabelEntity labelEntity : transferLabelEntityList) {
                            FbaLabelVO labelVO = new FbaLabelVO();
                            labelVO.setLabelCd(labelEntity.getLabelCd());
                            labelVO.setLabelName(labelEntity.getLabelName());
                            labelVO.setLabelColor(labelEntity.getLabelColor());
                            labelVOS.add(labelVO);
                        }
                        vo.setLabelRelEntityList(labelVOS);
                        deliveryList.add(vo);
                    }
                }
            }
        }
        return deliveryList;
    }

    @Override
    @Transactional(transactionManager = "retailReplenishmentTransactionManager")
    public void saveInfo(FbaInfoAddVO infoVO, String userId) {
        FbaReplenishmentInfoEntity infoEntity = new FbaReplenishmentInfoEntity();
        List<FbaMskuInfoVO> mskuInfoEntityList = infoVO.getMskuInfoList();
        List<FbaReplenishmentLabelRelEntity> labelRelEntityList = infoVO.getLabelRelEntityList();

        Timestamp currentTime = Time.getCurrentTimestamp();

        String randomValue = Toolbox.randomUUID();
        infoEntity.setOfferId(infoVO.getOfferId());
        infoEntity.setWarehouseId(infoVO.getWarehouseId());
        infoEntity.setShopId(infoVO.getShopId());
        infoEntity.setShipmentId(infoVO.getShipmentId());
        infoEntity.setChannelName(infoVO.getChannelName());
        infoEntity.setCreateTime(Time.getCurrentTimestamp());
        infoEntity.setCreateUser(userId);
        infoEntity.setModifyTime(currentTime);
        infoEntity.setModifyUser(userId);
        infoEntity.setDeleteStatus(0);//删除标识
        infoEntity.setStatusCd(0);//补货状态
        infoEntity.setFbaReplenishmentId(randomValue);
        infoEntity.setRandomValue(randomValue);
        infoEntity.setSubWarehouseId(infoVO.getSubWarehouseId());

        infoEntity.setReplenishmentSpecies(infoVO.getReplenishmentSpecies());
        infoEntity.setReplenishmentCount(infoVO.getReplenishmentCount());
        infoEntity.setRemark(infoVO.getRemark());

        //TODO 发货类型 判断是否能提交。提交后改变仓库预分配库存
        infoEntity.setDeliveringTypeCd(infoVO.getDeliveringTypeCd());

        infoDao.saveInfo(infoEntity);

        String fbaReplenishmentId = infoDao.getFbaReplenishmentId(randomValue);

        for (FbaMskuInfoVO mskuInfo : mskuInfoEntityList) {
            FbaMskuInfoEntity mskuInfoEntity = new FbaMskuInfoEntity();
            String mskuuuid = Toolbox.randomUUID();
            mskuInfoEntity.setReplenishmentCommodityId(mskuuuid);
            mskuInfoEntity.setFbaReplenishmentId(fbaReplenishmentId);
            mskuInfoEntity.setCommodityId(mskuInfo.getCommodityId());
            mskuInfoEntity.setShopId(infoVO.getShopId());
            mskuInfoEntity.setMskuId(mskuInfo.getMskuId());
            mskuInfoEntity.setCreateTime(currentTime);
            mskuInfoEntity.setCreateUser(userId);
            mskuInfoEntity.setModifyTime(currentTime);
            mskuInfoEntity.setModifyUser(userId);
            mskuInfoEntity.setDeleteStatus(0);//删除标识

            //保存补货商品装箱规格信息
            FbaMskuPackInfoEntity mskuPackInfo = new FbaMskuPackInfoEntity();
            List<FbaMskuPackQueryVO> mskupackList = mskuInfo.getMskupackList();
            int replenishmentNum = 0;
            for (FbaMskuPackQueryVO packQueryVO : mskupackList) {
                String mskuPackId = Toolbox.randomUUID();
                mskuPackInfo.setUuid(mskuPackId);
                mskuPackInfo.setReplenishmentCommodityId(mskuuuid);
                mskuPackInfo.setOuterBoxSpecificationLen(packQueryVO.getOuterBoxSpecificationLen());
                mskuPackInfo.setOuterBoxSpecificationWidth(packQueryVO.getOuterBoxSpecificationWidth());
                mskuPackInfo.setOuterBoxSpecificationHeight(packQueryVO.getOuterBoxSpecificationHeight());
                mskuPackInfo.setPackingQuantity(packQueryVO.getPackingQuantity());
                mskuPackInfo.setPackingWeight(packQueryVO.getPackingWeight());
                mskuPackInfo.setReplenishmentQuantity(packQueryVO.getReplenishmentQuantity());
                mskuPackInfo.setDeliveryNumber(0);
                mskuPackInfo.setPackingNumber(0);
                mskuPackInfo.setSignInQuantity(0);
                mskuPackInfo.setIsStandard(packQueryVO.getIsStandard());
                mskuPackInfo.setNumberOfBoxes(packQueryVO.getNumberOfBoxes());
                mskuPackInfo.setCreateUser(userId);
                mskuPackInfo.setCreateTime(currentTime);
                mskuPackInfo.setDeleteStatus(0);
                fbaMskuPackInfoDao.saveFbaMskuPack(mskuPackInfo);
                replenishmentNum = packQueryVO.getReplenishmentQuantity();
            }
            mskuInfoEntity.setReplenishmentQuantity(replenishmentNum);
            mskuInfoDao.saveMskuInfo(mskuInfoEntity);
        }

        //保存补货标签信息
        if (labelRelEntityList != null && labelRelEntityList.size() > 0) {
            for (FbaReplenishmentLabelRelEntity labelRelEntity : labelRelEntityList) {
                String slabeluuid = Toolbox.randomUUID();
                labelRelEntity.setUuid(slabeluuid);
                labelRelEntity.setFbaReplenishmentId(fbaReplenishmentId);
                labelRelEntity.setDeleteStatus(0);
                labelRelDao.saveLabelRel(labelRelEntity);
            }
        }
    }

    /**
     * 更新补货单的状态和操作人
     *
     * @param modifyUser,modifyTime,statusCd,fbaReplenishmentId
     */
    @Override
    public void updateStatus(String modifyUser, String modifyTime, int statusCd, String fbaReplenishmentId) {
        infoDao.updateStatus(modifyUser, modifyTime, statusCd, fbaReplenishmentId);
    }

    /**
     * 更新物流商和渠道名称
     *
     * @param shipmentId
     * @param channelName
     * @param fbaReplenishmentId
     */
    @Override
    public void updateShipAndChannel(String shipmentId, String channelName, String fbaReplenishmentId) {
        infoDao.updateShipAndChannel(shipmentId, channelName, fbaReplenishmentId);
    }

    /**
     * 取消补货单
     *
     * @param fbaReplenishmentId
     * @param cancelReason
     * @param userId
     */
    @Override
    @Transactional(transactionManager = "retailReplenishmentTransactionManager")
    public Result cancelReplen(String fbaReplenishmentId, String cancelReason, String userId) {
        Timestamp modifyTime = Time.getCurrentTimestamp();
        FbaReplenishmentInfoEntity infoEntity = new FbaReplenishmentInfoEntity();
        //取消补货单，改变补货单信息表状态
        infoEntity.setFbaReplenishmentId(fbaReplenishmentId);
        infoEntity.setModifyUser(userId);
        infoEntity.setModifyTime(modifyTime);
        infoEntity.setStatusCd(5);
        infoEntity.setCancelReason(cancelReason);
        infoDao.cancelReplen(infoEntity);
        //取消补货单，改变补货详单商品信息表状态
        FbaMskuInfoVO mskuInfoVO = new FbaMskuInfoVO();
        mskuInfoVO.setModifyUser(userId);
        mskuInfoVO.setModifyTime(modifyTime);
        mskuInfoVO.setDeleteStatus(1);
        mskuInfoVO.setFbaReplenishmentId(fbaReplenishmentId);
        mskuInfoDao.cancelMskuInfo(mskuInfoVO);
        //取消补货单，改变补货标签信息表状态
        labelRelDao.cancelLabelStatus(1, new Random().nextInt() + "", fbaReplenishmentId);
        String waybillId = waybillInfoDao.findWaybillIdByFbaId(fbaReplenishmentId);
        waybillInfoDao.deleteWaybill(waybillId, modifyTime, userId);
        waybillInfoDao.deleteFreight(waybillId, modifyTime, userId);
        waybillInfoDao.deleteRelation(waybillId, 1);
        return Result.success(200, "成功", "");
        //TODO 取消补货单，物流跟踪单自动取消
        //TODO 取消补货单，发货仓类型为本地仓货虚拟仓已推送至WMS系统的任务单也取消
        //TODO 取消补货单，对应的worktile任务标记为已完成
    }

    @Override
    @Transactional(transactionManager = "retailReplenishmentTransactionManager")
    public Result updateAmazonInfo(FbaAmazonVO amazonInfo) {
        FbaReplenishmentOutBillSyncVO wmsFbaVo = new FbaReplenishmentOutBillSyncVO();
        String batchNumber = infoDao.getBatchNumber(amazonInfo.getBatchNumber());
        if (batchNumber == null || "".equals(batchNumber)) {
            infoDao.updateAmazonInfo(amazonInfo);
            FbaReplenishmentInfoVO fbaVo = findById(amazonInfo.getFbaReplenishmentId());
            //同步wms参数信息
            //FbaReplenishmentOutBillSyncVO wmsFbaVo = new FbaReplenishmentOutBillSyncVO();
            List<GoodsInfoVO> goodsList = new ArrayList<>();
            List<PackInfoVo> boxGaugeList = new ArrayList<>();
            //同步wms补货单基本信息
            wmsFbaVo.setVoucherCode(fbaVo.getInfoEntity().getFbaReplenishmentId());
            wmsFbaVo.setVoucherType("FBH");
            wmsFbaVo.setVoucherCat(String.valueOf(fbaVo.getInfoEntity().getDeliveringTypeCd()));
            wmsFbaVo.setSectionCode(fbaVo.getInfoEntity().getSubWarehouseId());
            wmsFbaVo.setCreateTime(Time.getCurrentDateTime());
            wmsFbaVo.setApplicant(fbaVo.getInfoEntity().getCreateUser());
            wmsFbaVo.setReplenishmentBatch(amazonInfo.getBatchNumber());
            wmsFbaVo.setAddress(fbaVo.getInfoEntity().getAmazonWarehouseAddress());
            wmsFbaVo.setReferenceId(fbaVo.getInfoEntity().getRefercenceId());
            wmsFbaVo.setTargetWhName(fbaVo.getShopName());
            wmsFbaVo.setRemark(fbaVo.getInfoEntity().getRemark());
            wmsFbaVo.setBoxInfoUrl(boxInfoUrl + fbaVo.getInfoEntity().getPackageMark());
            wmsFbaVo.setBoxGaugeUrl(fbaVo.getInfoEntity().getPacklistFile());
            //同步wms补货单产品信息
            List<String> commodityIds = new ArrayList<>();
            Map<String, Object> mskuMap = new HashMap<>();
            List<FbaMskuInfoVO> mskuInfoVOList = fbaVo.getMskuInfoList();
            for (FbaMskuInfoVO vo : mskuInfoVOList) {
                commodityIds.add(vo.getCommodityId());
            }
            String[] ids = commodityIds.toArray(new String[commodityIds.size()]);
            Result mskuResult = mskuInfoService.getMskuInfoByIds(ids);
            LinkedHashMap hashMap = (LinkedHashMap) mskuResult.getData();
            List skuInfoList = (List<Map>) hashMap.get("mskuInfoBatch");
            if (skuInfoList != null && skuInfoList.size() > 0) {
                for (Object object : skuInfoList) {
                    Map finalMap = (Map) object;
                    mskuMap.put((String) finalMap.get("id"), finalMap);
                }
            }
            int i = 1;
            int numberBoxId = 1;
            //int unitQuantity = 0;
            //int totalQuantity = 0;
            for (FbaMskuInfoVO vo : mskuInfoVOList) {
                GoodsInfoVO goodsInfoVO = new GoodsInfoVO();

                Map voMap = (Map) mskuMap.get(vo.getCommodityId());
                goodsInfoVO.setLineNum(i);
                i++;
                goodsInfoVO.setGoodsCode((String) voMap.get("skuId"));
                goodsInfoVO.setGoodsName((String) voMap.get("productName"));
                goodsInfoVO.setFnCode((String) voMap.get("fnSkuId"));
                goodsInfoVO.setUnitQuantity(vo.getReplenishmentQuantity());
                goodsInfoVO.setTotalQuantity(vo.getReplenishmentQuantity());
                goodsInfoVO.setMsku((String) voMap.get("mskuName"));
                if (vo.getMskupackList().size() == 1) {
                    int num = vo.getMskupackList().get(0).getNumberOfBoxes();
                    for (int m = 1; m <= num; m++) {
                        PackInfoVo packInfoVo = new PackInfoVo();
                        packInfoVo.setBoxNumber(amazonInfo.getBatchNumber() + "U" + test(numberBoxId));
                        packInfoVo.setStandard(1);
                        packInfoVo.setUnit("PCS");
                        packInfoVo.setPackageCapacity(vo.getMskupackList().get(0).getPackingQuantity());
                        packInfoVo.setWeight(vo.getMskupackList().get(0).getPackingWeight());
                        packInfoVo.setSize(vo.getMskupackList().get(0).getOuterBoxSpecificationLen() * 10 + "*" + vo.getMskupackList().get(0).getOuterBoxSpecificationWidth() * 10 + "*" + vo.getMskupackList().get(0).getOuterBoxSpecificationHeight() * 10);
                        packInfoVo.setPackageQuantity(0);
                        packInfoVo.setTotalQuantity(vo.getMskupackList().get(0).getPackingQuantity());
                        //unitQuantity += vo.getMskupackList().get(0).getReplenishmentQuantity();
                        boxGaugeList.add(packInfoVo);
                        numberBoxId++;
                    }
                } else {
                    List<FbaMskuPackQueryVO> standardList = new ArrayList<>();
                    List<FbaMskuPackQueryVO> notStandardList = new ArrayList<>();
                    for (int j = 0; j < vo.getMskupackList().size(); j++) {
                        if (vo.getMskupackList().get(j).getIsStandard() == 1) {
                            standardList.add(vo.getMskupackList().get(j));
                        } else {
                            notStandardList.add(vo.getMskupackList().get(j));
                        }
                    }
                    int num = 0;
                    for (FbaMskuPackQueryVO standardVO : standardList) {
                        num = standardVO.getNumberOfBoxes();
                        for (int m = 1; m <= num; m++) {
                            PackInfoVo packInfoVo = new PackInfoVo();
                            packInfoVo.setBoxNumber(amazonInfo.getBatchNumber() + "U" + test(numberBoxId));
                            packInfoVo.setStandard(1);
                            packInfoVo.setUnit("PCS");
                            packInfoVo.setPackageCapacity(standardVO.getPackingQuantity());
                            packInfoVo.setWeight(standardVO.getPackingWeight());
                            packInfoVo.setSize(standardVO.getOuterBoxSpecificationLen() * 10 + "*" + standardVO.getOuterBoxSpecificationWidth() * 10 + "*" + standardVO.getOuterBoxSpecificationHeight() * 10);
                            packInfoVo.setPackageQuantity(0);
                            packInfoVo.setTotalQuantity(standardVO.getPackingQuantity());
                            boxGaugeList.add(packInfoVo);
                            numberBoxId++;
                        }
                    }
                    for (FbaMskuPackQueryVO standardVO : notStandardList) {
                        int box = standardVO.getNumberOfBoxes();


                        for (int n = num + 1; n <= num + box; n++) {
                            PackInfoVo packInfoVo = new PackInfoVo();
                            packInfoVo.setBoxNumber(amazonInfo.getBatchNumber() + "U" + test(numberBoxId));
                            packInfoVo.setStandard(0);
                            packInfoVo.setUnit("PCS");
                            packInfoVo.setPackageCapacity(0);
                            packInfoVo.setWeight(standardVO.getPackingWeight());
                            packInfoVo.setSize("0*0*0");
                            packInfoVo.setPackageQuantity(0);
                            packInfoVo.setTotalQuantity(0);
                            boxGaugeList.add(packInfoVo);
                            numberBoxId++;
                        }
                        num += box;
                    }
                }
                //goodsInfoVO.setUnitQuantity(unitQuantity);
                goodsInfoVO.setPackageQuantity(0);
                //goodsInfoVO.setTotalQuantity(unitQuantity);
                goodsInfoVO.setBoxGaugeList(boxGaugeList);
                goodsList.add(goodsInfoVO);
            }
            wmsFbaVo.setGoodsList(goodsList);


            //根据仓库类型更新补货单状态
            String warehouseType = getWarehouseType(amazonInfo.getFbaReplenishmentId());
            if ("A".equals(warehouseType)) {
                infoDao.updateStatusById(1, amazonInfo.getFbaReplenishmentId());
            } else {
                //如果是虚拟仓和海外仓的话，并且是第一次完成亚马逊信息的时候，更新补货单状态为6
                infoDao.updateStatusById(6, amazonInfo.getFbaReplenishmentId());
            }
        } else {
            String fbaReplementId = infoDao.getFbaReplementId(amazonInfo.getBatchNumber());
            //更改： if (amazonInfo.getFbaReplenishmentId() != fbaReplementId) {  这里我debug看的话两个值是一样的，这应该涉及地址问题，不能直接比对还要equals下
            if (amazonInfo.getFbaReplenishmentId() != null && !(amazonInfo.getFbaReplenishmentId()).equals(fbaReplementId)) {
                throw new RuntimeException("不能与其他未取消的补货单重复");
            }
            infoDao.updateAmazonInfo(amazonInfo);
            //根据仓库类型更新补货单状态， 这个第二次跟新的时候覆盖了之间的状态
//            String warehouseType = getWarehouseType(amazonInfo.getFbaReplenishmentId());
//            if ("A".equals(warehouseType)) {
//                infoDao.updateStatusById(1, amazonInfo.getFbaReplenishmentId());
//            }
            wmsFbaVo.setVoucherCode(amazonInfo.getFbaReplenishmentId());
        }
        return Result.success(wmsFbaVo);
    }

    public String test(int num) {
        DecimalFormat df = new DecimalFormat("000000");
        return df.format(num);
    }

    //根据补货单id获取发货仓类型
    public String getWarehouseType(String fbaReplenishmentId) {
        FbaReplenishmentInfoEntity fbaEntity = infoDao.findById(fbaReplenishmentId);
        String warehouseId = fbaEntity.getWarehouseId();
        String typeCd = "";
        try {
            Result wareResult = warehouseInfoService.getWarehouseName(warehouseId);
            LinkedHashMap data = (LinkedHashMap) wareResult.getData();
            if (data != null && !"".equals(data)) {
                typeCd = (String) data.get("typeCd");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return typeCd;
    }

    public void updateMskuPackInfo(List<FbaMskuSpecificationVO> mskuSpecificationList, String userId) {
        Timestamp currentTime = Time.getCurrentTimestamp();
        for (FbaMskuSpecificationVO mskuSpecificationVo : mskuSpecificationList) {
            String repCommodityId = mskuSpecificationVo.getReplenishmentCommodityId();
            int deleteStatus = 1;
            List<FbaMskuPackQueryVO> fbaMskuPackQueryVOS = fbaMskuPackInfoDao.findByFbaCommId(repCommodityId);
            fbaMskuPackInfoDao.deleteFbaMskuPack(deleteStatus, repCommodityId);
            List<FbaMskuPackUpdateVO> mskuPackUpdateList = mskuSpecificationVo.getMskuPackUpdateList();
            int replenishmentNum = 0;
            for (FbaMskuPackUpdateVO mskuPackVo : mskuPackUpdateList) {
                FbaMskuPackInfoEntity mskuPackEntity = new FbaMskuPackInfoEntity();
                mskuPackEntity.setUuid(Toolbox.randomUUID());
                mskuPackEntity.setReplenishmentCommodityId(repCommodityId);
                mskuPackEntity.setModifyTime(currentTime);
                mskuPackEntity.setModifyUser(userId);
                mskuPackEntity.setOuterBoxSpecificationLen(mskuPackVo.getOuterBoxSpecificationLen());
                mskuPackEntity.setOuterBoxSpecificationWidth(mskuPackVo.getOuterBoxSpecificationWidth());
                mskuPackEntity.setOuterBoxSpecificationHeight(mskuPackVo.getOuterBoxSpecificationHeight());
                mskuPackEntity.setPackingQuantity(mskuPackVo.getPackingQuantity());
                mskuPackEntity.setPackingWeight(mskuPackVo.getPackingWeight());
                mskuPackEntity.setNumberOfBoxes(mskuPackVo.getNumberOfBoxes());
                mskuPackEntity.setReplenishmentQuantity(mskuPackVo.getPackingQuantity() * mskuPackVo.getNumberOfBoxes());
                mskuPackEntity.setIsStandard(mskuPackVo.getIsStandard());
                mskuPackEntity.setDeleteStatus(0);
                replenishmentNum += mskuPackVo.getPackingQuantity() * mskuPackVo.getNumberOfBoxes();
                fbaMskuPackInfoDao.updateFbaMskuPack(mskuPackEntity);
            }
            fbaMskuPackInfoDao.updateMskuReplenishment(replenishmentNum, repCommodityId);
        }
    }

    @Override
    public void updateRepQuantity(FbaRepQuantityVO[] fbaRepQuantitys, String modifyUser, Timestamp modifyTime) {
        for (FbaRepQuantityVO fbaRepQuantity : fbaRepQuantitys) {
            infoDao.updateRepQuantity(fbaRepQuantity.getReplenishmentQuantity(), fbaRepQuantity.getCommodityId(), modifyUser, modifyTime);
        }
    }

    @Override
    public void updateStatusById(int statusCd, String fbaReplenishmentId) {
        infoDao.updateStatusById(statusCd, fbaReplenishmentId);
    }

    //计算FBA运费费估算表
    @Override
    public List<BillEstimateVO> findBillEst(String[] fbaReplenishmentId, String channelType) {
        List<FbaMskuInfoEntity> mskuInfoList = fbaMskuInfoService.getMskuInfo(fbaReplenishmentId);

        //这个接口是FBA补货单和调拨共用的接口，传入的fbaReplenishmentId有可能是FBA补货单的Id和调拨单的Id,如归是FBA补货单
        int flag = 0;
        if (mskuInfoList.size() == 0) {
            flag = 1;
            List<TransferOrderDetailsEntity> transferDetailList = transferDao.getDetailByCd(fbaReplenishmentId);
            for (TransferOrderDetailsEntity entity : transferDetailList) {
                FbaMskuInfoEntity fbaMskuInfo = new FbaMskuInfoEntity();
                fbaMskuInfo.setReplenishmentCommodityId(entity.getCommodityInfoCd());
                mskuInfoList.add(fbaMskuInfo);
            }
        }

        List<BillEstimateVO> billEscList = new ArrayList<BillEstimateVO>();
        BillEstimateVO billFirst = new BillEstimateVO();
        billFirst.setBillType("0");
        billEscList.add(billFirst);
        BillEstimateVO billSecond = new BillEstimateVO();
        billSecond.setBillType("1");
        billEscList.add(billSecond);

        for (BillEstimateVO billEstimate : billEscList) {
            String billTotalWeight = "0.00";
            List<PackSpecVO> packInfoList = new ArrayList<PackSpecVO>();

            // 循环遍历所有的商品
            for (FbaMskuInfoEntity mskuInfo : mskuInfoList) {

                List<FbaMskuPackQueryVO> packUpdateVO = new LinkedList<>();

                if (flag == 1) {
                    List<TransferOrderPackInfoEntity> transferPackInfoList = transferDao.getPackInfoByCd(mskuInfo.getReplenishmentCommodityId());
                    for (TransferOrderPackInfoEntity entity : transferPackInfoList) {
                        FbaMskuPackQueryVO mskuPack = new FbaMskuPackQueryVO();
                        mskuPack.setOuterBoxSpecificationHeight((int) entity.getOuterBoxSpecificationHeight());
                        mskuPack.setOuterBoxSpecificationLen((int) entity.getOuterBoxSpecificationLen());
                        mskuPack.setOuterBoxSpecificationWidth((int) entity.getOuterBoxSpecificationWidth());
                        mskuPack.setPackingWeight(entity.getWeight());
                        mskuPack.setNumberOfBoxes(entity.getNumberOfBoxes());
                        packUpdateVO.add(mskuPack);
                    }
                } else {
                    // 获取商品的装箱信息
                    packUpdateVO = fbaMskuPackInfoDao.findByFbaCommId(mskuInfo.getReplenishmentCommodityId());
                }

                for (FbaMskuPackQueryVO mskuPack : packUpdateVO) {
                    PackSpecVO packInfo = new PackSpecVO();
                    String outerBoxLen = String.valueOf(mskuPack.getOuterBoxSpecificationLen());
                    String outerBoxWeidth = String.valueOf(mskuPack.getOuterBoxSpecificationWidth());
                    String outerBoxHeight = String.valueOf(mskuPack.getOuterBoxSpecificationHeight());
                    String packSizeStr = outerBoxLen + "*" + outerBoxWeidth + "*" + outerBoxHeight;
                    BigDecimal packSizeBig = new BigDecimal(outerBoxLen).multiply(new BigDecimal(outerBoxWeidth)).multiply(new BigDecimal(outerBoxHeight));
                    Double packSize = packSizeBig.doubleValue();

                    // channelType = 1  空运
                    if ("1".equals(channelType)) {
                        // 空运
                        String conWeightStr = "";
                        if ("0".equals(billEstimate.getBillType())) {
                            conWeightStr = calcuBill(channelType, "0", packSize);
                            billEstimate.setBillTypeName("体积/5000");
                        } else {
                            conWeightStr = calcuBill(channelType, "1", packSize);
                            billEstimate.setBillTypeName("体积/6000");
                        }

                        String packWeight = roundUpStr(String.valueOf(mskuPack.getPackingWeight()));
                        String billWeight = "0.00";
                        String packType = "实重";
                        int compareResult = new BigDecimal(conWeightStr).compareTo(new BigDecimal(packWeight));
                        if (compareResult == 0 || compareResult == 1) {
                            billWeight = conWeightStr;
                            packType = "抛重";
                        } else {
                            billWeight = packWeight;
                        }
                        String numberBox = String.valueOf(mskuPack.getNumberOfBoxes());

                        BigDecimal totalWeight = new BigDecimal(billWeight).multiply(new BigDecimal(numberBox));
                        billTotalWeight = new BigDecimal(billTotalWeight).add(totalWeight).toString();
                        packInfo.setBillWeight(billWeight);
                        packInfo.setConWeight(conWeightStr);
                        packInfo.setNumberBox(numberBox);
                        packInfo.setPackSize(packSizeStr);
                        packInfo.setPackWeight(packWeight);
                        packInfo.setPackType(packType);
                    } else if ("2".equals(channelType)) {
                        // 海运

                        String conWeightStr = "";
                        String billWeight = "0.00";
                        String packType = "";
                        String numberBox = String.valueOf(mskuPack.getNumberOfBoxes());
                        String totalWeight = "实重";
                        if ("0".equals(billEstimate.getBillType())) {
                            conWeightStr = "/";
                            packType = "抛重";
                            billEstimate.setBillTypeName("CBM");
                            totalWeight = new BigDecimal(packSize).multiply(new BigDecimal(numberBox)).toString();
                            billWeight = "/";
                        } else {
                            conWeightStr = calcuBill(channelType, "1", packSize);
                            String packWeight = roundUpStr(String.valueOf(mskuPack.getPackingWeight()));
                            int compareResult = new BigDecimal(conWeightStr).compareTo(new BigDecimal(packWeight));
                            if (compareResult == 0 || compareResult == 1) {
                                billWeight = conWeightStr;
                                packType = "抛重";
                            } else {
                                billWeight = packWeight;
                            }
                            // modify by zhanwei_huang, bug is: MAT-1303
                            billEstimate.setBillTypeName("KGS");
//                            billEstimate.setBillTypeName("体积/6000");
                            totalWeight = new BigDecimal(billWeight).multiply(new BigDecimal(numberBox)).toString();
                        }
                        String packWeight = roundUpStr(String.valueOf(mskuPack.getPackingWeight()));
                        billTotalWeight = new BigDecimal(billTotalWeight).add(new BigDecimal(totalWeight)).toString();
                        packInfo.setBillWeight(billWeight);
                        packInfo.setConWeight(conWeightStr);
                        packInfo.setPackWeight(packWeight);
                        packInfo.setNumberBox(numberBox);
                        packInfo.setPackSize(packSizeStr);
                        packInfo.setPackType(packType);
                    } else {
                        // 陆运
                        String conWeightStr = "";
                        String billWeight = "0.00";
                        String packType = "实重";  // 称重类型
                        String numberBox = String.valueOf(mskuPack.getNumberOfBoxes());
                        String totalWeight = "";

                        if ("0".equals(billEstimate.getBillType())) {
                            conWeightStr = "/";
//                            packType = "抛重";
                            billEstimate.setBillTypeName("CBM");
                            totalWeight = new BigDecimal(packSize).multiply(new BigDecimal(numberBox)).toString();
                            billWeight = "/";
                        } else {
                            conWeightStr = calcuBill(channelType, "1", packSize);
                            String packWeight = roundUpStr(String.valueOf(mskuPack.getPackingWeight()));
                            int compareResult = new BigDecimal(conWeightStr).compareTo(new BigDecimal(packWeight));
                            if (compareResult == 0 || compareResult == 1) {
                                billWeight = conWeightStr;
                            } else {
                                billWeight = packWeight;

                            }
                            // modify by zhanwei_huang, bug is: MAT-1303
                            billEstimate.setBillTypeName("KGS");
//                            billEstimate.setBillTypeName("体积/6000");
                            totalWeight = new BigDecimal(billWeight).multiply(new BigDecimal(numberBox)).toString();
                        }

                        String packWeight = roundUpStr(String.valueOf(mskuPack.getPackingWeight()));
                        billTotalWeight = new BigDecimal(billTotalWeight).add(new BigDecimal(totalWeight)).toString();
                        packInfo.setBillWeight(billWeight);
                        packInfo.setConWeight(conWeightStr);
                        packInfo.setPackWeight(packWeight);
                        packInfo.setNumberBox(numberBox);
                        packInfo.setPackSize(packSizeStr);
                        packInfo.setPackType(packType);
                    }
                    packInfoList.add(packInfo);
                }

            }

            billEstimate.setPackSpecList(packInfoList);
            billEstimate.setBillTotalWeight(billTotalWeight);

        }

        return billEscList;
    }

    /**
     * 计算折算重量
     *
     * @param channelType
     * @param billType
     * @param packSize
     * @return
     */
    public String calcuBill(String channelType, String billType, Double packSize) {
        String conWeightStr = "/";
        //1代表空运、2代表海运
        if ("1".equals(channelType)) {
            //0代表体积/5000、1代表体积/6000
            if ("0".equals(billType)) {
                BigDecimal volume = new BigDecimal("5000");
                String packSizeStr = Double.toString(packSize);
                BigDecimal packSizeBig = new BigDecimal(packSizeStr);
                BigDecimal conWeight = packSizeBig.divide(volume, 3, BigDecimal.ROUND_HALF_UP);
                conWeightStr = conWeight.toString();
            } else {
                BigDecimal volume = new BigDecimal("6000");
                String packSizeStr = Double.toString(packSize);
                BigDecimal packSizeBig = new BigDecimal(packSizeStr);
                BigDecimal conWeight = packSizeBig.divide(volume, 4, BigDecimal.ROUND_HALF_UP);
                conWeightStr = conWeight.toString();
            }
        } else {
            //0代表CBM、1代表体积/6000
            if ("0".equals(billType)) {
                conWeightStr = "/";
            } else {
                BigDecimal volume = new BigDecimal("6000");
                String packSizeStr = Double.toString(packSize);
                BigDecimal packSizeBig = new BigDecimal(packSizeStr);
                BigDecimal conWeight = packSizeBig.divide(volume, 3, BigDecimal.ROUND_HALF_UP);
                conWeightStr = conWeight.toString();
            }
        }

        return roundUpStr(conWeightStr);
    }

    //将数字进行向上舍入
    public String roundUpStr(String str) {
        int indexOfPoint = str.indexOf(".");
        if (indexOfPoint != -1) {
            char point = str.charAt(indexOfPoint + 1);
            String tenStr = str.substring(0, indexOfPoint);
            String aimStr = "";
            if (point >= '5') {
                int tenDig = Integer.valueOf(tenStr) + 1;
                aimStr = String.valueOf(tenDig) + "." + 0;
            } else {
                aimStr = tenStr + "." + 5;
            }
            return aimStr;
        }
        return str;
    }

    //计算物流商列表中的金额
    public CalcuDeliveryVO calcuAmount(String offerId, String[] fbaIds, String channelTypeCd) {

        List<BillEstimateVO> billEstList = findBillEst(fbaIds, channelTypeCd);

        CalcuDeliveryVO calcuDeliveryVO = new CalcuDeliveryVO();
        Result res = shipmentService.getShipment(offerId);
        if (res == null || res.getCode() != 200 || res.getData() == null) {
            return null;
        }

        Map shipMap = (Map) res.getData();

        List<HashMap> destinationListMap = (List<HashMap>) shipMap.get("destinationList");

        List<HashMap> destinationList = destinationListMap;

        Object obj = shipMap.get("effectiveList");
        if (obj == null) {
            return null;
        }
        List<Map> effectiveMap = (List<Map>) obj;

        if (effectiveMap == null || effectiveMap.size() == 0) {
            return null;
        }

        List<String> effectiveList = new ArrayList<>();
        int maxDay = 0;//预计签收最大日期
        for (Map effmap : effectiveMap) {
            int startDays = (int) effmap.get("startDays");
            int endDays = (int) effmap.get("endDays");
            if (maxDay < endDays) {
                maxDay = endDays;
            }
            String effectiveStr = startDays + "-" + endDays;
            effectiveList.add(effectiveStr);
        }

        String chargeTypeName = (String) shipMap.get("chargeTypeName");

        // 如果计费类型是KGS时，转换成 体积/6000来运算
        // modify by zhanwei_huang, fixed bug MAT-1303
//        if ("KGS".equals(chargeTypeName)) {
//            chargeTypeName = "体积/6000";
//        }

        List<Map> historyMap = (List<Map>) shipMap.get("historyList");

        String chargeIntever = "";//计费区间
        String totalWeight = "0.00";//计费总重量
        Double unitPriceWithOil = 0.00;//含油价
        String estimateWeight = "0.00";
//        int i = 0;//下面for循环用

        for (BillEstimateVO billEstimateVO : billEstList) {
            // 循环遍历所有的场景
            if (chargeTypeName != null && chargeTypeName.equals(billEstimateVO.getBillTypeName())) {
                String billTotalWeightStr = billEstimateVO.getBillTotalWeight();
                estimateWeight = billTotalWeightStr;
                // 取第一个装箱规格的类型
                if (billTotalWeightStr == null) {
                    continue;
                }
                List<PackSpecVO> list = billEstimateVO.getPackSpecList();
                List<PackSpecVO> mlist = billEstimateVO.getPackSpecList();
                if (list != null && list.size() > 0) {
                    String pt = list.get(0).getPackType();
                    String weightTypeCd = "实重".equals(pt) ? "2" : "1";
                    calcuDeliveryVO.setWeighTypeCd(weightTypeCd);
                }
                Double billTotalWeight = (Double.valueOf(billTotalWeightStr));
                if (chargeTypeName.equals("CBM")) {
                    billTotalWeight = billTotalWeight / Math.pow(10, 6);
                    estimateWeight = billTotalWeight + "";
                }
                double deviation = 10000.00;
                for (Map map : historyMap) {
                    double startChargeSection = (double) map.get("startChargeSection");
                    Double endChargeSection = (Double) map.get("endChargeSection");
                    if (endChargeSection == null) {
                        endChargeSection = Double.MAX_VALUE;
                    }
                    if (startChargeSection <= (billTotalWeight) && (billTotalWeight) <= endChargeSection) {
                        unitPriceWithOil = (Double) map.get("unitPriceWithOil");
                        totalWeight = billTotalWeightStr;
                        chargeIntever = startChargeSection + "-" + endChargeSection;
                        break;
                    } else {
                        if (startChargeSection > billTotalWeight) {
                            double deviationValue = Math.abs(billTotalWeight - startChargeSection);
                            if (deviation > deviationValue) {
                                deviation = deviationValue;
                                unitPriceWithOil = (Double) map.get("unitPriceWithOil");
                                totalWeight = billTotalWeightStr;
                                chargeIntever = startChargeSection + "-" + endChargeSection;
                            }
                        }
                        if (endChargeSection < billTotalWeight) {
                            double deviationValue = Math.abs(billTotalWeight - endChargeSection);
                            if (deviation > deviationValue) {
                                deviation = deviationValue;
                                unitPriceWithOil = (Double) map.get("unitPriceWithOil");
                                totalWeight = billTotalWeightStr;
                                chargeIntever = startChargeSection + "-" + endChargeSection;
                            }
                        }
                    }
                }
            }
        }


        Double customsDeclarationFee = (Double) shipMap.get("customsDeclarationFee");//报关费
        if (customsDeclarationFee == null) {
            customsDeclarationFee = 0.0;
        }
        Double portFee = (Double) shipMap.get("portFee");//过港费
        if (portFee == null) {
            portFee = 0.0;
        }
        //含油价+过港费
        BigDecimal addResult = new BigDecimal(String.valueOf(unitPriceWithOil)).add(new BigDecimal(String.valueOf(portFee)));
        //(预估单价+过港费)*预估计费重
        BigDecimal muliptResult = addResult.multiply(new BigDecimal(estimateWeight));
        //((预估单价+过港费)*预估计费重)+报关费
        BigDecimal totalAmount = muliptResult.add(new BigDecimal(String.valueOf(customsDeclarationFee))).setScale(2, RoundingMode.UP);

        String amount = "0.00";
        if (!"0.00".equals(estimateWeight)) {
            amount = totalAmount.toString();
        }

        //运费估算信息
        FreightEstimateinfoEntity freightInfo = new FreightEstimateinfoEntity();
        //预估单价*预估计费重=预估运费
        BigDecimal freightTotalBig = new BigDecimal(String.valueOf(unitPriceWithOil)).multiply(new BigDecimal(estimateWeight)).setScale(2, RoundingMode.UP);
        //预估计费重*过港费
        BigDecimal annexMulResult = new BigDecimal(estimateWeight).multiply(new BigDecimal(String.valueOf(portFee)));
        //报关费+（预估计费重*过港费）=附加费
        BigDecimal annexCost = new BigDecimal(String.valueOf(customsDeclarationFee)).add(annexMulResult).setScale(2, RoundingMode.UP);
        //预估运费+附加费
        Double totalCost = freightTotalBig.add(annexCost).setScale(2, RoundingMode.UP).doubleValue();

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, +maxDay);
        Date maxdate = calendar.getTime();


        calcuDeliveryVO.setAnnexCost(annexCost.toString());//附加费=(预估计费重*过港费)+报关费
        calcuDeliveryVO.setChargeIntever(chargeIntever);
        calcuDeliveryVO.setEstimateDate(new java.sql.Date(maxdate.getTime()));
        calcuDeliveryVO.setFreightTotal(freightTotalBig.toString()); //预估运费=预估单价*预估计费重
        calcuDeliveryVO.setOfferId(offerId);
        calcuDeliveryVO.setCustomsDeclarationFee(customsDeclarationFee);//报关费
        calcuDeliveryVO.setPortFee(portFee);//过港费
        calcuDeliveryVO.setTotalAmount(amount);//总金额
        calcuDeliveryVO.setTotalWeight(totalWeight);//预估计费重
        calcuDeliveryVO.setUnitPriceWithOil(unitPriceWithOil.toString());//含油价 预估单价
        calcuDeliveryVO.setTotalCost(totalCost.toString());//费用合计=附加费+预估运费

        return calcuDeliveryVO;
    }

    public /*DeliveryConfirmVO*/DeliveryConSelectVO comfireDelivery(String fbaReplenishmentId) {
     /*   DeliveryConfirmVO deliveryConfirmVO = new DeliveryConfirmVO();
        String waybillId = infoDao.findWayBillIdByFbaId(fbaReplenishmentId);
        LogisticsInfoVO logInfo = getLogisticsByFbaId(fbaReplenishmentId);
        List<FreightEstimateinfoEntity> freightInfo = waybillInfoDao.findFreightInfo(waybillId);
        deliveryConfirmVO.setLogisticsInfo(logInfo);
        if(freightInfo!=null){
            deliveryConfirmVO.setFreightEstimateinfoEntity(freightInfo.get(0));
        }
        return deliveryConfirmVO;*/
        String offerId = infoDao.getOfferIdByFbaId(fbaReplenishmentId);
        if (StringUtils.isEmpty(offerId)) {
            return null;
        }
        DeliveryConSelectVO deliveryConSelectVO = new DeliveryConSelectVO();
        CalFreightVO calFreightVO = new CalFreightVO();
        String[] fbaIds = new String[]{fbaReplenishmentId};
        CalcuDeliveryVO calcuDeliveryVO = calcuAmount(offerId, fbaIds, null);
        calFreightVO.setAnnexCost(calcuDeliveryVO.getAnnexCost());
        calFreightVO.setEstimateDate(calcuDeliveryVO.getEstimateDate());
        calFreightVO.setFreightTotal(calcuDeliveryVO.getFreightTotal());
        calFreightVO.setTotalCost(calcuDeliveryVO.getTotalCost());
        calFreightVO.setWeighTypeCd("1");
        calFreightVO.setTotalWeight(calcuDeliveryVO.getTotalWeight());
        calFreightVO.setUnitPriceWithOil(calcuDeliveryVO.getUnitPriceWithOil());
        List<FbaDeliveryVO> fbaDeliveryList = deliveryFba(fbaIds);
        Result res = shipmentService.getShipment(offerId);
        Map mapOffer = (Map) res.getData();
        LogisticsInfoVO logInfo = getLogInfoByMap(mapOffer);
        deliveryConSelectVO.setCalFreightVO(calFreightVO);
        deliveryConSelectVO.setFbaDeliveryVOList(fbaDeliveryList);
        deliveryConSelectVO.setLogisticsInfoVO(logInfo);
        return deliveryConSelectVO;
    }

    public DeliverySelectVO selectDelivery(String fbaReplenishmentId) {
        String wayBillId = infoDao.findWayBillIdByFbaId(fbaReplenishmentId);
        if (wayBillId != null && !wayBillId.isEmpty()) {
            return null;
        }
        DeliverySelectVO selectInfo = new DeliverySelectVO();
        FbaReplenishmentInfoEntity fbaReplenishmentInfoEntity = infoDao.findById(fbaReplenishmentId);
        Boolean isAllow = true;
        if (wayBillId != null && !wayBillId.isEmpty()) {
            WaybillInfoEntity wayBillInfo = waybillInfoDao.findInfoById(wayBillId);
            int logisticsTypeCd = wayBillInfo.getLogisticsTypeCd();
            if (logisticsTypeCd != 4) {
                isAllow = false;
            }
        }
        selectInfo.setAllow(isAllow);
        String offerId = infoDao.getOfferIdByFbaId(fbaReplenishmentId);
        Boolean isSelect = true;
        if (offerId != null && !"".equals(offerId)) {
            selectInfo.setSelect(isSelect);
            //发货仓信息 通过发货仓id查询发货仓名称
            String warehouseId = fbaReplenishmentInfoEntity.getWarehouseId();
            String warehouseName = "";
            try {
                Result wareResult = warehouseInfoService.getWarehouseName(warehouseId);
                if (200 == wareResult.getCode()) {
                    LinkedHashMap data = (LinkedHashMap) wareResult.getData();
                    if (data != null && !"".equals(data)) {
                        warehouseName = (String) data.get("warehouseName");
                    }
                }
            } catch (Exception e) {

            }

            //TODO 获取补充Amazon货件信息提货方式
            //int pickupTypeCd = fbaReplenishmentInfoEntity.getPickupTypeCd();
            //String pickupTypeName = fbaReplenishmentPickupAttrService.findByPickupCd(pickupTypeCd).getPickupTypeName();

            //TODO 收货仓省份代码和邮编
            String provinceCode = "PHX3";
            String zipCode = "85043";
            selectInfo.setProvinceCode(provinceCode);
            selectInfo.setZipCode(zipCode);
            //通过店铺id获取店铺名称
            String shopName = "";
            String shopId = fbaReplenishmentInfoEntity.getShopId();
            Result shopResult = mskuInfoService.getShopName(shopId);
            if (200 == shopResult.getCode()) {
                LinkedHashMap data = (LinkedHashMap) shopResult.getData();
                if (data != null && !"".equals(data)) {
                    shopName = (String) data.get("storeName");
                }
            }
            //获取物流商名称
//            Result rShipment = companyService.getShipmentName(fbaReplenishmentInfoEntity.getShipmentId());
//            Map mShipment = (Map) rShipment.getData();
            List<String> destinationList = new ArrayList<>();//目的地
            String shipmentName = "";//物流商名称
            String channelName = "";//渠道名称
            try {
                Result rshipment = shipmentService.getShipment(fbaReplenishmentInfoEntity.getOfferId());
                LinkedHashMap shipment = (LinkedHashMap) rshipment.getData();
                if (shipment != null) {
                    shipmentName = (String) shipment.get("shipMentName");
                    channelName = (String) shipment.get("channelName");
                    List<Map> destionMapList = (List) shipment.get("destinationList");
                    for (Map destionMap : destionMapList) {
                        destinationList.add((String) destionMap.get("destinationName"));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

//            if (mShipment != null){
//                shipmentName = (String) mShipment.get("shipmentName");
//            }
            //设置补货单标签信息
            List<FbaLabelVO> labelList = findLabelById(fbaReplenishmentId);

            selectInfo.setLabelList(labelList);
            selectInfo.setShipmentName(shipmentName);
            selectInfo.setShopName(shopName);
            selectInfo.setWarehouseName(warehouseName);
            selectInfo.setBatchNumber(fbaReplenishmentInfoEntity.getBatchNumber());
            selectInfo.setChannelName(channelName);
            //selectInfo.setPickupTypeName(pickupTypeName);
            selectInfo.setFbaReplenishmentId(fbaReplenishmentInfoEntity.getFbaReplenishmentId());
            selectInfo.setCreateTime(fbaReplenishmentInfoEntity.getCreateTime());
            selectInfo.setShipmentId(fbaReplenishmentInfoEntity.getShipmentId());
            selectInfo.setOfferId(fbaReplenishmentInfoEntity.getOfferId());
            selectInfo.setDestinationList(destinationList);
        } else {
            isSelect = false;
            selectInfo.setSelect(isSelect);
        }
        return selectInfo;
    }

    /**
     * 判断FBA补货单是否能够合并
     *
     * @param fbaIds fba补货单号
     */
    public Map<String, String> mergeDelivery(String[] fbaIds) {
        Map<String, String> mergeMap = new HashMap<>();

        Set<String> warehouseIdSet = new HashSet<>();
        Set<String> zipCodeSet = new HashSet<>();

        int inDelivery = 0;//记录是否有在物流中的
        int hasLabel = 0;//记录有特性标签的总数
        int notLabel = 0;//记录没有特性标签的总数

        for (String fbaId : fbaIds) {
            // 获取补货单详细补货信息
            FbaReplenishmentInfoEntity fbaInfo = infoDao.findById(fbaId);
            warehouseIdSet.add(fbaInfo.getWarehouseId());
            if (warehouseIdSet.size() > 1) {
                // 发货仓数量大于1，不允许合并
                mergeMap.put("isAllow", "false");
                mergeMap.put("message", "要合并的补货单发货仓不一致不允许合并交运");
                return mergeMap;
            }

            zipCodeSet.add(fbaInfo.getAmazonWarehouseZipcode());
            if (zipCodeSet.size() > 1) {
                // 收获仓数量大于1，不允许合并
                mergeMap.put("isAllow", "false");
                mergeMap.put("message", "要合并的补货单收获仓邮编不一致不允许合并交运");
                return mergeMap;
            }

            // 获取FBA运单号
            String wayBillId = infoDao.findWayBillIdByFbaId(fbaId);
            if (wayBillId != null && !wayBillId.isEmpty()) {
                // 获取运单详细信息
                WaybillInfoEntity wayBillInfo = waybillInfoDao.findInfoById(wayBillId);
                int logisticsTypeCd = wayBillInfo.getLogisticsTypeCd();
                if (!(logisticsTypeCd == 4)) {
                    inDelivery++;
                }
            }

            List<FbaMskuInfoVO> fbaMskuInfoList = fbaMskuInfoService.findMskuByReplenId(fbaId);
            if (fbaMskuInfoList != null && fbaMskuInfoList.size() > 0) {
                for (FbaMskuInfoVO mskuInfoVO : fbaMskuInfoList) {
                    List<Map<String, Object>> labelList = mskuInfoVO.getMskuInfoVO().getStoreLabel();
                    if (labelList.size() == 0) {
                        notLabel++;
                    } else {
                        hasLabel++;
                    }
                }
            }
        }

        if (hasLabel != 0 && notLabel != 0) {
            mergeMap.put("isAllow", "false");
            mergeMap.put("message", "部分商品含有特性标签不允许合并交运");
            return mergeMap;
        }

        if (inDelivery > 0) {
            mergeMap.put("isAllow", "false");
            mergeMap.put("message", "已做交运的补货单未取消对应的物流跟踪单之前不允许再次交运");
            return mergeMap;
        }

        mergeMap.put("isAllow", "true");
        mergeMap.put("message", "可以合并交运");
        return mergeMap;
    }

    //通过补货单id获取物流商信息
    public LogisticsInfoVO getLogisticsByFbaId(String fbaId) {
        LogisticsInfoVO logInfo = new LogisticsInfoVO();
        FbaReplenishmentInfoEntity fbaReplenishmentInfoEntity = infoDao.findById(fbaId);
        String offerId = fbaReplenishmentInfoEntity.getOfferId();
        logInfo.setOfferId(offerId);
        try {
            Result res = shipmentService.getShipment(offerId);
            Map mapOffer = (Map) res.getData();
            logInfo = getLogInfoByMap(mapOffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return logInfo;
    }

    /**
     * 计费估算表
     *
     * @param fbaIds      补货单ID列表
     * @param channelType 渠道类型 1：空运，2：海运，3：陆运
     */
    public DeliveryVO getfbaAndBillInfo(String[] fbaIds, String channelType) {
        DeliveryVO deliveryVO = new DeliveryVO();
        List<BillEstimateVO> billEstimateVOList = findBillEst(fbaIds, channelType);
        List<FbaDeliveryVO> fbaDeliveryList = deliveryFba(fbaIds);
        deliveryVO.setFbaDeliveryVOList(fbaDeliveryList);
        deliveryVO.setBillEstimateVOList(billEstimateVOList);
        return deliveryVO;
    }

    //确认选择物流商
    public DeliveryConSelectVO getFreightInfo(String offerId,
                                              String[] fbaIds,
                                              String channelTypeCd) {

        DeliveryConSelectVO deliveryConSelectVO = new DeliveryConSelectVO();

        CalFreightVO calFreightVO = new CalFreightVO();

        CalcuDeliveryVO calcuDeliveryVO = calcuAmount(offerId, fbaIds, channelTypeCd);

        calFreightVO.setAnnexCost(calcuDeliveryVO.getAnnexCost());
        calFreightVO.setEstimateDate(calcuDeliveryVO.getEstimateDate());
        calFreightVO.setFreightTotal(calcuDeliveryVO.getFreightTotal());
        calFreightVO.setTotalCost(calcuDeliveryVO.getTotalCost());
        calFreightVO.setWeighTypeCd(calcuDeliveryVO.getWeighTypeCd());
        calFreightVO.setTotalWeight(calcuDeliveryVO.getTotalWeight());
        calFreightVO.setUnitPriceWithOil(calcuDeliveryVO.getUnitPriceWithOil());

        List<FbaDeliveryVO> fbaDeliveryList = deliveryFba(fbaIds);

        Result res = shipmentService.getShipment(offerId);
        Map mapOffer = (Map) res.getData();
        LogisticsInfoVO logInfo = getLogInfoByMap(mapOffer);

        deliveryConSelectVO.setCalFreightVO(calFreightVO);
        deliveryConSelectVO.setFbaDeliveryVOList(fbaDeliveryList);
        deliveryConSelectVO.setLogisticsInfoVO(logInfo);

        return deliveryConSelectVO;
    }

    @Override
    public Map check(String fbaReplenishmentId, String bacthNumber) {
        Map resultMap = new HashMap();
        Map<String, Integer> originalMap = new HashMap<>();
        Map<String, Integer> nowMap = new HashMap<>();
        Map<String, String> fnskuMap = new HashMap<>();
        Map<String, String> craFnskuMap = new HashMap<>();
        List<FbaMskuInfoVO> fbaMskuInfoVOList = mskuInfoDao.findMskuByReplenId(fbaReplenishmentId);
        List<String> fbaMskuList = new ArrayList<>();
        List<String> crawMskuList = new ArrayList<>();
        String shopId = infoDao.getShopId(fbaReplenishmentId);
        List<Map> paraMap = new ArrayList<>();
        for (FbaMskuInfoVO fbaMskuInfoVO : fbaMskuInfoVOList) {
            fbaMskuList.add(fbaMskuInfoVO.getMskuId());
            originalMap.put(shopId + fbaMskuInfoVO.getMskuId(), fbaMskuInfoVO.getReplenishmentQuantity());
        }
        Gson gson = new Gson();
        if (shopId == null) {
            resultMap.put("flag", false);
            return resultMap;
        }
        Result result = mskuOutsideService.getShopById(shopId);
        String sellerId = null;
        if (result.getCode() == 200) {
            Map map = (Map) result.getData();
            sellerId = (String) map.get("sellerNo");
        }
        Result crawResult = crawlerService.getShipmentInfo(bacthNumber, sellerId);
        if (crawResult.getCode() == 200) {
            Map map = (Map) crawResult.getData();
            if (map != null) {
                if ("Cancelled".equals((String) map.get("shipmentStatus"))) {
                    resultMap.put("flag", false);
                    resultMap.put("errorType", 4);
                    return resultMap;
                }
                List<Map> shipmentList = (List<Map>) map.get("shipmentInfoItemList");
                if (shipmentList != null) {
                    for (Map shipMap : shipmentList) {
                        String sellerSKU = (String) shipMap.get("sellerSKU");
                        String fnsku = (String) shipMap.get("fulfillmentNetworkSKU");
                        Integer num = (Integer) shipMap.get("quantityShipped");
                        List<String> tracingIds = (List<String>) shipMap.get("trackingIds");
                        crawMskuList.add(sellerSKU);
                        Integer lastNum = nowMap.get(shopId + sellerId);
                        craFnskuMap.put(shopId + sellerSKU, fnsku);
                        Map theMap = new HashMap();
                        map.put("mskuId", sellerSKU);
                        map.put("shopId", shopId);
                        map.put("fnsku", fnsku);
                        paraMap.add(map);
                        if (lastNum != null && num != null) {
                            nowMap.put(shopId + sellerSKU, num + lastNum);
                        } else {
                            nowMap.put(shopId + sellerSKU, num);
                        }
                    }
                }
            }
        }
        boolean flag = true;
        if (crawMskuList.size() <= 0) {
            resultMap.put("flag", false);
            resultMap.put("errorType", 1);
            return resultMap;
        }
        if (crawMskuList.size() != fbaMskuList.size()) {
            resultMap.put("flag", false);
            resultMap.put("errorType", 1);
            return resultMap;
        }
        for (String crawMsku : crawMskuList) {
            boolean hava = false;
            for (String msku : fbaMskuList) {
                if (crawMsku.equals(msku)) {
                    Integer num = originalMap.get(shopId + msku);
                    Integer nowNum = nowMap.get(shopId + msku);
                    if (num == null) {
                        if (num != nowNum) {
                            resultMap.put("flag", false);
                            resultMap.put("errorType", 2);
                            return resultMap;
                        }
                    }
                    if (!num.equals(nowNum)) {
                        resultMap.put("flag", false);
                        resultMap.put("errorType", 2);
                        return resultMap;
                    }
                    hava = true;
                }
            }
            if (!hava) {
                resultMap.put("flag", false);
                resultMap.put("errorType", 1);
                return resultMap;
            }
        }
        String string = gson.toJson(paraMap);
        Result checkResult = operationService.checkFnsku(gson.toJson(paraMap));
        if (checkResult.getCode() == 391) {
            resultMap.put("flag", false);
            resultMap.put("errorType", 3);
            return resultMap;
        }
        if (checkResult.getCode() != 200) {
            throw new RuntimeException("接口异常");
        }
        resultMap.put("flag", flag);
        return resultMap;
    }

    //根据物流商查询接口得到物流信息
    private LogisticsInfoVO getLogInfoByMap(Map mapOffer) {
        LogisticsInfoVO logInfo = new LogisticsInfoVO();
        if (mapOffer != null) {
            //渠道名称
            logInfo.setOfferId((String) mapOffer.get("offerId"));
            logInfo.setChannelName((String) mapOffer.get("channelName"));
            List<Map> effectiveList = (List<Map>) mapOffer.get("effectiveList");
            Map effectivemap = effectiveList.get(0);
            int startDays = (int) effectivemap.get("startDays");
            int endDays = (int) effectivemap.get("endDays");
            logInfo.setLogisticsOfferEffective(startDays + "-" + endDays);
            logInfo.setChargeTypeCd((String) mapOffer.get("chargeTypeName"));
            List<HashMap> destinationListMap = (List<HashMap>) mapOffer.get("destinationList");
            List<HashMap> destinationList = destinationListMap;
            logInfo.setDestinationList(destinationList);
            logInfo.setCustomsDeclarationFee((Double) mapOffer.get("customsDeclarationFee"));
            logInfo.setPortFee((Double) mapOffer.get("portFee"));
            String shipmentName = "";
            try {
                //获取物流商名称
                Result rShipment = companyService.getShipmentName((String) mapOffer.get("shipmentId"));
                Map mShipment = (Map) rShipment.getData();
                shipmentName = (String) mShipment.get("shipmentName");
                logInfo.setShipmentName(shipmentName);
            } catch (Exception e) {
                logInfo.setShipmentName("");
            }
        }
        return logInfo;
    }

    @Override
    public FbaReplenishmentInfoEntity download(String fbaReplenishmentId) {
        FbaReplenishmentInfoEntity entity = infoDao.findById(fbaReplenishmentId);
        return entity;
    }

    @Override
    //@Transactional
    public void changeReturn(FbaPackingDataReturnVO vo) {
        String fbaReplenishmentId = vo.getFbaReplenishmentId();
        List<String> replenishmentCommodityIdList = mskuInfoDao.findReplenishmentCommodityId(fbaReplenishmentId);
        List<FbaSkuInfoVO> skuEntityList = vo.getSkuEntityList();
        for (String replenishmentCommodityId : replenishmentCommodityIdList) {
            fbaMskuPackInfoDao.deleteFbaMskuPack(1, replenishmentCommodityId);
            for (FbaSkuInfoVO infoVO : skuEntityList) {
                List<ReturnPackInfoVO> packList = infoVO.getPackTypeList();
                int packingNumber = 0;
                double len = 0;
                double width = 0;
                double height = 0;
                double fbaWeight = 0;
                double fbaQuantity = 0;
                int numberOfBoxes = 0;
                for (ReturnPackInfoVO packInfoVo : packList) {
                    InsertPackVO insertPackVO = new InsertPackVO();
                    insertPackVO.setUuid(Toolbox.randomUUID());
                    insertPackVO.setReplenishmentCommodityId(replenishmentCommodityId);
                    insertPackVO.setOuterBoxSpecificationLen(packInfoVo.getOuterBoxSpecificationLen());
                    insertPackVO.setOuterBoxSpecificationWidth(packInfoVo.getOuterBoxSpecificationWidth());
                    insertPackVO.setOuterBoxSpecificationHeight(packInfoVo.getOuterBoxSpecificationHeight());
                    insertPackVO.setPackingQuantity(packInfoVo.getPackingQuantity());
                    insertPackVO.setPackingWeight(packInfoVo.getPackingWeight());
                    insertPackVO.setNumberOfBoxes(packInfoVo.getNumberOfBoxes());
                    insertPackVO.setPackingNumber(packInfoVo.getPackingQuantity() * packInfoVo.getNumberOfBoxes());
                    insertPackVO.setDeleteStatus(0);
                    packingNumber += packInfoVo.getPackingQuantity() * packInfoVo.getNumberOfBoxes();
                    fbaMskuPackInfoDao.updateFbaPack(insertPackVO);
                    if (packInfoVo.getNumberOfBoxes() > numberOfBoxes) {
                        len = packInfoVo.getOuterBoxSpecificationLen();
                        width = packInfoVo.getOuterBoxSpecificationWidth();
                        height = packInfoVo.getOuterBoxSpecificationHeight();
                        fbaWeight = packInfoVo.getPackingWeight();
                        fbaQuantity = packInfoVo.getPackingQuantity();
                    }
                }
                //更新产品规格信息
                Map productData = new HashMap();
                productData.put("skuId", infoVO.getSkuId());
                productData.put("fbaLength", len);
                productData.put("fbaWidth", width);
                productData.put("fbaHeight", height);
                productData.put("fbaWeight", fbaWeight);
                productData.put("fbaQuantity", fbaQuantity);
                productService.syncProductSpecifications(gson.toJson(productData), "matrixwms");
                replenishShippingDataDao.updateReplenishmentDeliveryNumber(0, packingNumber, replenishmentCommodityId);
            }
        }
        fbaReplenishmentInfoDao.updateStatus("wms", Time.getCurrentDateTime(), 6, fbaReplenishmentId);
    }

    @Override
    public void wmsReturnFbaDelivery(FbaReplenishmentOutReturnVO fbaReplenishmentOutReturnVO) {
        String fbaReplenishmentId = fbaReplenishmentOutReturnVO.getFbaReplenishmentId();
        List<String> replenishmentCommodityIdList = mskuInfoDao.findReplenishmentCommodityId(fbaReplenishmentId);
        List<FbaOutSkuVO> fbaOutSkuVOS = fbaReplenishmentOutReturnVO.getSkuList();
        InsertPackVO insertPackVO = new InsertPackVO();
        for (String commodityId : replenishmentCommodityIdList) {
            fbaMskuPackInfoDao.deleteFbaMskuPack(1, commodityId);
            for (FbaOutSkuVO fbaOutSkuVO : fbaOutSkuVOS) {
                List<ReturnPackInfoVO> packInfoVos = fbaOutSkuVO.getPackTypeList();
                int packingNumber = 0;
                int deliveryNumber = 0;
                for (ReturnPackInfoVO packInfoVo : packInfoVos) {
                    insertPackVO.setUuid(Toolbox.randomUUID());
                    insertPackVO.setReplenishmentCommodityId(commodityId);
                    insertPackVO.setOuterBoxSpecificationLen(packInfoVo.getOuterBoxSpecificationLen());
                    insertPackVO.setOuterBoxSpecificationWidth(packInfoVo.getOuterBoxSpecificationWidth());
                    insertPackVO.setOuterBoxSpecificationHeight(packInfoVo.getOuterBoxSpecificationHeight());
                    insertPackVO.setPackingQuantity(packInfoVo.getPackingQuantity());
                    insertPackVO.setPackingWeight(packInfoVo.getPackingWeight());
                    insertPackVO.setNumberOfBoxes(packInfoVo.getNumberOfBoxes());
                    insertPackVO.setPackingNumber(packInfoVo.getPackingQuantity() * packInfoVo.getNumberOfBoxes());
                    insertPackVO.setDeleteStatus(0);
                    deliveryNumber += packInfoVo.getDeliveryNumber();
                    packingNumber += packInfoVo.getPackingQuantity() * packInfoVo.getNumberOfBoxes();
                    insertPackVO.setDeliveryNumber(packInfoVo.getDeliveryNumber());
                    fbaMskuPackInfoDao.updateFbaPack(insertPackVO);
                }
                replenishShippingDataDao.updateReplenishmentDeliveryNumber(deliveryNumber, packingNumber, commodityId);
            }
            fbaReplenishmentInfoDao.updateStatus("wms", Time.getCurrentDateTime(), 3, fbaReplenishmentId);
            String waybillId = waybillInfoDao.findWaybillIdByFbaId(fbaReplenishmentId);
            waybillInfoDao.upWaybillStatus(waybillId, Time.getCurrentTimestamp(), "admin", 2);
        }
    }

    @Override
    public List<FbaMskuVo> findbyidBatch(String[] fbaReplenishmentIds) {
        List<FbaMskuVo> fbaMskuVoList = new ArrayList<>();
        for (String fbaReplenishmentId : fbaReplenishmentIds) {
            FbaReplenishmentInfoEntity fbaReplenishmentInfoEntity = infoDao.findById(fbaReplenishmentId);
            if (fbaReplenishmentInfoEntity == null) {
                continue;
            }
            FbaMskuVo fbaMskuVo = new FbaMskuVo();
            fbaMskuVo.setZipCode(fbaReplenishmentInfoEntity.getAmazonWarehouseZipcode());
            List<FbaMskuInfoVO> fbaMskuInfoList = fbaMskuInfoService.findMskuByReplenId(fbaReplenishmentId);
            fbaMskuVo.setFbaMskuInfoList(fbaMskuInfoList);
            fbaMskuVoList.add(fbaMskuVo);
        }
        return fbaMskuVoList;
    }

    public Timestamp formateStringToTimestamp(String date) {
        try {
            return Timestamp.valueOf(date);
        } catch (Exception e) {
            DateFormat dateSdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                return new Timestamp(dateSdf.parse(date).getTime());
            } catch (ParseException e1) {
                DateFormat dateTimeSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    return new Timestamp(dateTimeSdf.parse(date).getTime());
                } catch (Exception e2) {
                    System.out.println("转换不了");
                    return null;
                }
            }
        }
    }
}
