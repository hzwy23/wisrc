package com.wisrc.replenishment.webapp.service.impl;

import com.google.gson.Gson;
import com.wisrc.replenishment.webapp.dao.TransferDao;
import com.wisrc.replenishment.webapp.dao.WaybillInfoDao;
import com.wisrc.replenishment.webapp.entity.*;
import com.wisrc.replenishment.webapp.service.*;
import com.wisrc.replenishment.webapp.utils.PageData;
import com.wisrc.replenishment.webapp.utils.Result;
import com.wisrc.replenishment.webapp.utils.Time;
import com.wisrc.replenishment.webapp.utils.Toolbox;
import com.wisrc.replenishment.webapp.vo.transferorder.*;
import com.wisrc.replenishment.webapp.vo.waybill.LogisticsTrackInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhm
 */
@Service
public class TransferServiceImpl implements TransferService {
    @Autowired
    private TransferDao transferDao;
    @Autowired
    private ShipmentService shipmentService;
    @Autowired
    private WaybillInfoDao waybillInfoDao;
    @Autowired
    private ProductService productService;
    @Autowired
    private WarehouseInfoService warehouseInfoService;
    @Autowired
    private WarehouseListService warehouseListService;
    @Autowired
    private Gson gson;

    @Override
    @Transactional(transactionManager = "retailReplenishmentTransactionManager")
    public void addTransferOrder(TransferOrderAddVo transferOrderAddVo) {
        //调拨单的基本信息
        TransferBasicInfoEntity transferBasicInfoEntity = transferOrderAddVo.getTransferBasicInfoEntity();
        //调拨单的标签信息
        List<TransferLabelEntity> transferLabelEntities = transferOrderAddVo.getTransferLabelEntities();
        //调拨单的明细信息
        List<TransferOrderDetailsVo> transferOrderDetailsVos = transferOrderAddVo.getTransferOrderDetailsVos();

        String randomValue = Toolbox.randomUUID();
        //TODO 先保存调拨单的基本信息
        transferBasicInfoEntity.setCreateTime(Time.getCurrentDateTime());
        transferBasicInfoEntity.setStatusCd(1);
        transferBasicInfoEntity.setRandomValue(randomValue);
        //插入调拨单的基本信息
        transferDao.insertTransferBasic(transferBasicInfoEntity);

        //根据randomValue去查询出对应的调拨单号
        String transferId = transferDao.findTransferIdByRandomValue(randomValue);

        //TODO 插入调拨单的标签信息
        for (TransferLabelEntity transferLabelEntity : transferLabelEntities) {
            transferDao.insertTransferLabel(Toolbox.randomUUID(), transferId, transferLabelEntity.getLabelCd());
        }

        //TODO 插入调拨的商品信息
        for (TransferOrderDetailsVo transferOrderDetailsVo : transferOrderDetailsVos) {
            TransferOrderDetailsEntity transferOrderDetailsEntity = new TransferOrderDetailsEntity();
            String commodityInfoCd = Toolbox.randomUUID();
            BeanUtils.copyProperties(transferOrderDetailsVo, transferOrderDetailsEntity);
            transferOrderDetailsEntity.setCommodityInfoCd(commodityInfoCd);
            transferOrderDetailsEntity.setTransferOrderId(transferId);
            transferDao.insertTransferDetails(transferOrderDetailsEntity);

            //插入改调拨商品对应的装箱信息
            for (TransferOrderPackInfoEntity transferOrderPackInfoEntity : transferOrderDetailsVo.getPackInfoEntities()) {
                transferOrderPackInfoEntity.setUuid(Toolbox.randomUUID());
                transferOrderPackInfoEntity.setCommodityInfoCd(commodityInfoCd);
                transferOrderPackInfoEntity.setDeleteStatus(0);
                transferOrderPackInfoEntity.setIsStandard(1);
                transferDao.insertTransferPacking(transferOrderPackInfoEntity);
            }

        }
    }

    @Override
    public Result auditTransfer(String transferId, String warehouseStartId, String warehouseEndId, String subWarehouseEndId, String userId) {
        TransferBasicInfoEntity transferBasicInfoEntity = transferDao.findById(transferId);
        if (warehouseStartId.startsWith("C") || warehouseStartId.startsWith("B")) {
            transferDao.auditTransfer(transferId, warehouseStartId, warehouseEndId, subWarehouseEndId, userId, Time.getCurrentDateTime(), 3);
        } else {
            transferDao.auditTransfer(transferId, warehouseStartId, warehouseEndId, subWarehouseEndId, userId, Time.getCurrentDateTime(), 2);
        }
        return Result.success(200, "成功", transferBasicInfoEntity);
    }


    @Override
    @Transactional(transactionManager = "retailReplenishmentTransactionManager")
    public void changePackInfo(List<PackChangeInfoVo> packChangeInfoVos) {
        for (PackChangeInfoVo packChangeInfoVo : packChangeInfoVos) {
            String commodityId = transferDao.findCommodityByTransferIdAndSkuId(packChangeInfoVo.getTransferOrderCd(), packChangeInfoVo.getSkuId());
            //逻辑删除该商品的所有装箱数据
            transferDao.deletePackInfoByCommodityId(commodityId);

            //插入所有新的装箱信息
            int transferNumber = 0;
            for (TransferOrderPackInfoEntity transferOrderPackInfoEntity : packChangeInfoVo.getPackInfoEntities()) {
                transferNumber += transferOrderPackInfoEntity.getPackingQuantity() * transferOrderPackInfoEntity.getNumberOfBoxes();
                transferOrderPackInfoEntity.setUuid(Toolbox.randomUUID());
                transferOrderPackInfoEntity.setCommodityInfoCd(commodityId);
                transferOrderPackInfoEntity.setDeleteStatus(0);
                transferDao.insertTransferPacking(transferOrderPackInfoEntity);
            }
            transferDao.updateTransferQuantity(transferNumber, commodityId);
        }
    }

    @Override
    @Transactional(transactionManager = "retailReplenishmentTransactionManager")
    public Result confirmShipment(TransferWaybillAddVo transferWaybillAddVo, String userId) {

        //TODO 首先生成物流交运单的基本信息
        String randomValue = Toolbox.randomUUID();
        WaybillInfoEntity waybillInfoEntity = new WaybillInfoEntity();
        BeanUtils.copyProperties(transferWaybillAddVo, waybillInfoEntity);
        waybillInfoEntity.setRandomValue(randomValue);
        waybillInfoEntity.setCreateUser(userId);
        waybillInfoEntity.setCreateTime(Time.getCurrentTimestamp());
        waybillInfoEntity.setWaybillOrderDate(Time.getCurrentDate());
        waybillInfoEntity.setDeleteStatus(0);
        //新生成默认为1--待发货
        waybillInfoEntity.setLogisticsTypeCd(1);
        waybillInfoDao.addInfo(waybillInfoEntity);
        //根据随机数查出物流单id
        String waybillId = waybillInfoDao.findWaybillIdByRandomValue(randomValue);

        Result shipment = shipmentService.getShipment(transferWaybillAddVo.getOfferId());
        if (shipment == null || shipment.getCode() != 200) {
            return shipment;
        }
        Map map = (Map) shipment.getData();
        // 获取物流商ID
        String shipmentId = (String) map.get("shipmentId");

        //TODO 更新调拨单的物流信息，并修改物流交运单的状态
        transferDao.updateWaybillAndLogistics(transferWaybillAddVo.getTransferOrderCd(), waybillId,
                transferWaybillAddVo.getOfferId(),
                shipmentId, userId, Time.getCurrentDateTime());

        //新增运费估算信息
        FreightEstimateinfoEntity freightEstimateinfoEntity = transferWaybillAddVo.getFreightEstimateinfoEntity();
        freightEstimateinfoEntity.setWaybillId(waybillId);
        freightEstimateinfoEntity.setCreateTime(Time.getCurrentTimestamp());
        freightEstimateinfoEntity.setCreateUser(userId);
        freightEstimateinfoEntity.setDeleteStatus(0);
        waybillInfoDao.addFreighInfo(freightEstimateinfoEntity);
        Map<String, Object> ret = new LinkedHashMap<>();
        ret.put("waybillId", waybillId);
        ret.put("arguments", transferWaybillAddVo);
        return Result.success(ret);
    }

    @Override
    public Result findById(String transferId) {
        //返回的完整调拨单信息
        TransferInfoReturnVo transferInfoReturnVo = new TransferInfoReturnVo();
        //返回调拨单对应的产品信息
        List<TransferOrderDetailsVo> transferOrderDetailsVos = new ArrayList<>();
        //返回调拨单对应的标签信息
        List<TransferLabelEntity> transferLabelEntities = new ArrayList<>();
        //调拨单对应的物流信息
        LogisticsTrackInfoVO logisticsTrackInfoVO = new LogisticsTrackInfoVO();

        //第一步查询出调拨单的基本信息
        TransferBasicInfoEntity transferBasicInfoEntity = transferDao.findById(transferId);
        //补充显示的仓库名字
        Result warehouseStartName = warehouseInfoService.getWarehouseName(transferBasicInfoEntity.getWarehouseStartId());
        if (warehouseStartName.getCode() != 200) {
            throw new RuntimeException("调用仓库的外部接口错误:" + warehouseStartName.getMsg());
        }
        if (warehouseStartName.getData() != null) {
            transferInfoReturnVo.setWarehouseStartName((String) ((Map) warehouseStartName.getData()).get("warehouseName"));
        }
        Result warehouseEndName = warehouseInfoService.getWarehouseName(transferBasicInfoEntity.getWarehouseEndId());
        if (warehouseEndName.getCode() != 200) {
            throw new RuntimeException("调用仓库的外部接口错误:" + warehouseEndName.getMsg());
        }
        if (warehouseEndName.getData() != null) {
            transferInfoReturnVo.setWarehouseEndName((String) ((Map) warehouseEndName.getData()).get("warehouseName"));
        }

        //获取单调拨单的产品信息
        List<TransferOrderDetailsEntity> transferOrderDetailsEntities = transferDao.findTransferDetailsById(transferId);
        //补充展示需要的缺少字段
        completeTransferDetails(transferOrderDetailsVos, transferOrderDetailsEntities);

        //TODO 准备调拨单对应的标签信息
        transferLabelEntities = transferDao.findTransferLabelByTransferId(transferId);

        //TODO 查询出对应的物流信息
        if (StringUtils.isNotEmpty(transferBasicInfoEntity.getWaybillId())) {
            completeTransferLogisticInfo(logisticsTrackInfoVO, transferBasicInfoEntity.getWaybillId());
        }
        transferInfoReturnVo.setTransferBasicInfoEntity(transferBasicInfoEntity);
        transferInfoReturnVo.setTransferOrderDetailsVos(transferOrderDetailsVos);
        transferInfoReturnVo.setTransferLabelEntities(transferLabelEntities);
        transferInfoReturnVo.setLogisticsTrackInfoVO(logisticsTrackInfoVO);
        return Result.success(transferInfoReturnVo);
    }

    @Override
    public Result getPageTransferInfo(Integer pageNum, Integer pageSize, String warehouseStartId, String warehouseEndId, String startDate, String endDate, String shipmentId, String keyword, Integer status) throws Exception {
        List<TransferBasicInfoEntity> transferBasicInfoEntities = transferDao.findTransferInfoByCondition(warehouseStartId, warehouseEndId, startDate, endDate, shipmentId, status);
        if (transferBasicInfoEntities.size() == 0) {
            return Result.success(transferBasicInfoEntities);
        }
        List<TransferInfoReturnVo> transferInfoReturnVos = new ArrayList<>();
        Set<String> skuSet = new HashSet<>();
        Set<String> warehouseSet = new HashSet<>();
        Set<String> offerIdSet = new HashSet<>();

        //产品id和名称的映射
        Map<String, String> skuIdToName = new HashMap<>();
        //产品id和产品图片url的映射
        Map<String, List<String>> skuIdToImgUrls = new HashMap<>();
        //产品id和产品标签的映射
        Map<String, List<String>> skuIdToLabels = new HashMap<>();
        //仓库id和仓库名称的映射
        Map<String, String> warehouseIdToName = new HashMap<>();

        for (TransferBasicInfoEntity transferBasicInfoEntity : transferBasicInfoEntities) {
            warehouseSet.add(transferBasicInfoEntity.getWarehouseStartId());
            warehouseSet.add(transferBasicInfoEntity.getWarehouseEndId());
            if (StringUtils.isNotEmpty(transferBasicInfoEntity.getOfferId())) {
                offerIdSet.add(transferBasicInfoEntity.getOfferId());
            }
            List<TransferOrderDetailsEntity> transferDetailsById = transferDao.findTransferDetailsById(transferBasicInfoEntity.getTransferOrderCd());
            for (TransferOrderDetailsEntity transferOrderDetailsEntity : transferDetailsById) {
                skuSet.add(transferOrderDetailsEntity.getSkuId());
            }
        }
        String[] skuIds = skuSet.toArray(new String[skuSet.size()]);
        String[] warehouseIds = warehouseSet.toArray(new String[skuSet.size()]);

        //查询出所有sku对应的name和imgUrl
        Map<String, String[]> skuIdsParam = new HashMap<>();
        skuIdsParam.put("skuIdList", skuIds);
        Result prodList = productService.getProdList(gson.toJson(skuIdsParam));
        if (prodList.getCode() != 200) {
            throw new RuntimeException("251行代码错误：" + prodList.getMsg());
        }
        List<Map> prodListMap = (List<Map>) prodList.getData();
        for (Map map : prodListMap) {
            Map skuDefine = (Map) map.get("define");
            skuIdToName.put((String) skuDefine.get("skuId"), (String) skuDefine.get("skuNameZh"));
            List<Map> skuImgListMap = (List<Map>) map.get("imagesList");
            List<String> urls = new ArrayList<>();
            for (Map skuImgMap : skuImgListMap) {
                urls.add((String) skuImgMap.get("imageUrl"));
            }
            skuIdToImgUrls.put((String) skuDefine.get("skuId"), urls);

            List<Map> skuLabels = (List<Map>) map.get("declareLabelList");
            List<String> labels = new ArrayList<>();
            for (Map skuLabel : skuLabels) {
                labels.add((String) skuLabel.get("labelText"));
            }
            skuIdToLabels.put((String) skuDefine.get("skuId"), labels);
        }

        //查询出所有的仓库id对应的名字
        warehouseIdToName = warehouseListService.getWarehouseNameList(Arrays.asList(warehouseIds));

        formReturnData(transferInfoReturnVos, transferBasicInfoEntities, skuIdToName, skuIdToImgUrls, skuIdToLabels, warehouseIdToName);

        if (pageNum == null && pageSize == null) {
            //查询全部
            if (StringUtils.isNotEmpty(keyword)) {
                List<TransferInfoReturnVo> result = removeSomeByKeyword(transferInfoReturnVos, keyword);
                return Result.success(PageData.pack(result.size(), 1, "transferInfoList", result));
            } else {
                return Result.success(PageData.pack(transferInfoReturnVos.size(), 1, "transferInfoList", transferInfoReturnVos));
            }
        } else {
            //分页查询
            if (StringUtils.isNotEmpty(keyword)) {
                List<TransferInfoReturnVo> result1 = removeSomeByKeyword(transferInfoReturnVos, keyword);
                List<TransferInfoReturnVo> result = new ArrayList<>();
                int i = 1;
                for (TransferInfoReturnVo transferInfoReturnVo : result1) {
                    if (i > (pageNum - 1) * pageSize && i <= pageNum * pageSize) {
                        result.add(transferInfoReturnVo);
                    }
                    i++;
                }
                return Result.success(PageData.pack(result1.size(), (result1.size() / pageSize) + 1, "transferInfoList", result));
            } else {
                List<TransferInfoReturnVo> result = new ArrayList<>();
                int i = 1;
                for (TransferInfoReturnVo transferInfoReturnVo : transferInfoReturnVos) {
                    if (i > (pageNum - 1) * pageSize && i <= pageNum * pageSize) {
                        result.add(transferInfoReturnVo);
                    }
                    i++;
                }
                return Result.success(PageData.pack(transferInfoReturnVos.size(), (transferInfoReturnVos.size() / pageSize) + 1, "transferInfoList", result));
            }
        }
    }

    @Override
    public Result getAllStatus() {
        List<TransferStatusEntity> allStatus = transferDao.findAllStatus();
        return Result.success(allStatus);
    }

    @Override
    public Result findCountOfStatus() {
        Map<String, Integer> resultMap = new HashMap<>();
        //待确认数
        Integer waitConfirmCount = transferDao.findCountOfWaitConfirm();
        //待装箱数
        Integer waitPackCount = transferDao.findCountOfWaitPack();
        //待选择渠道数
        Integer waitSelectChannelCount = transferDao.findCountOfWaitSelectChannel();
        //待发货数
        Integer waitDeliveryCount = transferDao.findCountOfWaitDelivery();
        //待签收数量
        Integer waitAssignCount = transferDao.findCountOfWaitAssign();
        resultMap.put("waitConfirmCount", waitConfirmCount == null ? 0 : waitConfirmCount);
        resultMap.put("waitPackCount", waitPackCount == null ? 0 : waitPackCount);
        resultMap.put("waitSelectChannelCount", waitSelectChannelCount == null ? 0 : waitSelectChannelCount);
        resultMap.put("waitDeliveryCount", waitDeliveryCount == null ? 0 : waitDeliveryCount);
        resultMap.put("waitAssignCount", waitAssignCount == null ? 0 : waitAssignCount);
        return Result.success(resultMap);
    }

    @Override
    public Result cancelTransfer(String transferOrderId, String userId, String cancelReason) {
        transferDao.cancelTransfer(transferOrderId, userId, Time.getCurrentDateTime(), cancelReason);
        return Result.success();
    }

    @Override
    @Transactional(transactionManager = "retailReplenishmentTransactionManager")
    public Result addLabelForTransfer(List<AddLabelsVo> labelsVos) {
        for (AddLabelsVo labelsVo : labelsVos) {
            for (Integer integer : labelsVo.getLabels()) {
                transferDao.addLabelForTransferId(Toolbox.randomUUID(), labelsVo.getTransferOrderId(), integer);
            }
        }
        return Result.success();
    }

    @Override
    public Result deleteLabel(String transferId, String labelId) {
        transferDao.deleteLabel(transferId, labelId);
        return Result.success();
    }

    /**
     * 根据关键字过滤掉不符合的数据
     *
     * @param transferInfoReturnVos
     * @param keyword
     */
    private List<TransferInfoReturnVo> removeSomeByKeyword(List<TransferInfoReturnVo> transferInfoReturnVos, String keyword) {
        if (keyword.startsWith("D")) {
            List<TransferInfoReturnVo> result = new ArrayList<>(transferInfoReturnVos);
            for (TransferInfoReturnVo transferInfoReturnVo : transferInfoReturnVos) {
                if (StringUtils.isEmpty(transferInfoReturnVo.getTransferBasicInfoEntity().getWaybillId())) {
                    result.remove(transferInfoReturnVo);
                } else if (!transferInfoReturnVo.getTransferBasicInfoEntity().getWaybillId().contains(keyword)) {
                    result.remove(transferInfoReturnVo);
                }
            }
            return result;
        } else if (keyword.startsWith("A")) {
            List<TransferInfoReturnVo> result = new ArrayList<>(transferInfoReturnVos);
            for (TransferInfoReturnVo transferInfoReturnVo : transferInfoReturnVos) {
                if (!transferInfoReturnVo.getTransferBasicInfoEntity().getTransferOrderCd().contains(keyword)) {
                    result.remove(transferInfoReturnVo);
                }
            }
            return result;
        } else if (isContainChinese(keyword)) {
            List<TransferInfoReturnVo> result = new ArrayList<>();
            for (TransferInfoReturnVo transferInfoReturnVo : transferInfoReturnVos) {
                for (TransferOrderDetailsVo transferOrderDetailsVo : transferInfoReturnVo.getTransferOrderDetailsVos()) {
                    if (transferOrderDetailsVo.getSkuName() == null) {
                        continue;
                    }
                    if (transferOrderDetailsVo.getSkuName().contains(keyword)) {
                        result.add(transferInfoReturnVo);
                        break;
                    }
                }
            }
            return result;
        } else if (keyword.contains("-")) {
            List<TransferInfoReturnVo> result = new ArrayList<>(transferInfoReturnVos);
            Set<TransferInfoReturnVo> returnVos = new HashSet<>();
            for (TransferInfoReturnVo transferInfoReturnVo : transferInfoReturnVos) {
                for (TransferOrderDetailsVo transferOrderDetailsVo : transferInfoReturnVo.getTransferOrderDetailsVos()) {
                    if (transferOrderDetailsVo.getSkuId().contains(keyword)) {
                        returnVos.add(transferInfoReturnVo);
                        break;
                    }
                }
            }
            return Arrays.asList(returnVos.toArray(new TransferInfoReturnVo[returnVos.size()]));
        } else {
            List<TransferInfoReturnVo> result = new ArrayList<>(transferInfoReturnVos);
            Set<TransferInfoReturnVo> returnVos = new HashSet<>();
            for (TransferInfoReturnVo transferInfoReturnVo : transferInfoReturnVos) {
                for (TransferOrderDetailsVo transferOrderDetailsVo : transferInfoReturnVo.getTransferOrderDetailsVos()) {
                    if (transferOrderDetailsVo.getFnSku().contains(keyword)) {
                        returnVos.add(transferInfoReturnVo);
                        break;
                    }
                }
                if (StringUtils.isEmpty(transferInfoReturnVo.getLogisticsTrackInfoVO().getLogisticsId())) {
                    continue;
                } else if (transferInfoReturnVo.getLogisticsTrackInfoVO().getLogisticsId().contains(keyword)) {
                    returnVos.add(transferInfoReturnVo);
                }
            }
            return Arrays.asList(returnVos.toArray(new TransferInfoReturnVo[returnVos.size()]));
        }
    }

    /**
     * 判断字符串中是否包含字符串
     *
     * @param str
     * @return
     */
    private boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 组装返回数据
     *
     * @param transferInfoReturnVos
     * @param transferBasicInfoEntities
     * @param skuIdToName
     * @param skuIdToImgUrls
     * @param skuIdToLabels
     * @param warehouseIdToName
     */
    private void formReturnData(List<TransferInfoReturnVo> transferInfoReturnVos, List<TransferBasicInfoEntity> transferBasicInfoEntities, Map<String, String> skuIdToName, Map<String, List<String>> skuIdToImgUrls, Map<String, List<String>> skuIdToLabels, Map<String, String> warehouseIdToName) {
        for (TransferBasicInfoEntity transferBasicInfoEntity : transferBasicInfoEntities) {
            //返回的调拨单数据
            TransferInfoReturnVo transferInfoReturnVo = new TransferInfoReturnVo();
            //返回的调拨单明细信息
            List<TransferOrderDetailsVo> transferOrderDetailsVos = new ArrayList<>();
            //返回的调拨单标签信息
            List<TransferLabelEntity> transferLabelEntities = new ArrayList<>();
            //返回调拨单的物流信息
            LogisticsTrackInfoVO logisticsTrackInfoVO = new LogisticsTrackInfoVO();

            transferInfoReturnVo.setTransferBasicInfoEntity(transferBasicInfoEntity);
            transferInfoReturnVo.setWarehouseStartName(warehouseIdToName.get(transferBasicInfoEntity.getWarehouseStartId()));
            transferInfoReturnVo.setWarehouseEndName(warehouseIdToName.get(transferBasicInfoEntity.getWarehouseEndId()));

            //准备调拨单的明细数据
            List<TransferOrderDetailsEntity> transferDetails = transferDao.findTransferDetailsById(transferBasicInfoEntity.getTransferOrderCd());
            for (TransferOrderDetailsEntity transferDetail : transferDetails) {
                TransferOrderDetailsVo transferOrderDetailsVo = new TransferOrderDetailsVo();
                BeanUtils.copyProperties(transferDetail, transferOrderDetailsVo);
                //准备该产品对应的装箱信息
                List<TransferOrderPackInfoEntity> packInfoEntities = transferDao.findPackInfoByCommodityId(transferDetail.getCommodityInfoCd());
                transferOrderDetailsVo.setPackInfoEntities(packInfoEntities);
                transferOrderDetailsVo.setSkuName(skuIdToName.get(transferDetail.getSkuId()));
                transferOrderDetailsVo.setSkuLabels(skuIdToLabels.get(transferDetail.getSkuId()));
                transferOrderDetailsVo.setUrl(skuIdToImgUrls.get(transferDetail.getSkuId()));
                transferOrderDetailsVos.add(transferOrderDetailsVo);
            }

            //准备调拨单的标签数据
            transferLabelEntities = transferDao.findTransferLabelByTransferId(transferBasicInfoEntity.getTransferOrderCd());

            //准备调拨单的物流信息
            if (StringUtils.isNotEmpty(transferBasicInfoEntity.getWaybillId())) {
                completeTransferLogisticInfo(logisticsTrackInfoVO, transferBasicInfoEntity.getWaybillId());
            }

            transferInfoReturnVo.setLogisticsTrackInfoVO(logisticsTrackInfoVO);
            transferInfoReturnVo.setTransferLabelEntities(transferLabelEntities);
            transferInfoReturnVo.setTransferOrderDetailsVos(transferOrderDetailsVos);
            transferInfoReturnVos.add(transferInfoReturnVo);
        }
    }

    /**
     * 补充展示需要的调拨单明细信息
     *
     * @param logisticsTrackInfoVO
     */
    private void completeTransferLogisticInfo(LogisticsTrackInfoVO logisticsTrackInfoVO, String waybillId) {
        List<FreightEstimateinfoEntity> freightInfo = waybillInfoDao.findFreightInfo(waybillId);
        if (!CollectionUtils.isEmpty(freightInfo)) {
            logisticsTrackInfoVO.setEstimateDate(freightInfo.get(0).getEstimateDate());
        }
        WaybillInfoEntity wayBillInfo = waybillInfoDao.findInfoById(waybillId);
        String logisticsId = wayBillInfo.getLogisticsId();
        List<LogisticsTrackInfoEntity> logisticsList = waybillInfoDao.findLogisticsList(logisticsId);
        logisticsTrackInfoVO.setLogisticsId(logisticsId);
        logisticsTrackInfoVO.setLogisticsInfoList(logisticsList);
        Date signInDate = wayBillInfo.getSignInDate();
        logisticsTrackInfoVO.setSignInDate(signInDate);

        List<WaybillExceptionInfoEntity> excList = waybillInfoDao.findExcList(waybillId);
        List<String> excDescList = new ArrayList<String>();
        if (excList != null && excList.size() > 0) {
            for (int i = 0; i < excList.size(); i++) {
                excDescList.add(excList.get(i).getExceptionTypeDesc());
            }
        }
        String excDesc = StringUtils.join(excDescList.toArray(), ",");
        logisticsTrackInfoVO.setExceptionDesc(excDesc);
        //获取物流商名称和渠道名称
        String shipmentName = "";
        String channelName = "";
        Result result = shipmentService.getShipment(wayBillInfo.getOfferId());
        if (result.getCode() == 200) {
            Map shipMentMap = (Map) result.getData();
            if (shipMentMap != null) {
                shipmentName = (String) shipMentMap.get("shipMentName");
                channelName = (String) shipMentMap.get("channelName");
            }
        }
        logisticsTrackInfoVO.setShipChannel(shipmentName + "-" + channelName);
    }

    /**
     * 补充展示需要的调拨单明细
     *
     * @param transferOrderDetailsVos
     * @param transferOrderDetailsEntities
     */
    private void completeTransferDetails(List<TransferOrderDetailsVo> transferOrderDetailsVos, List<TransferOrderDetailsEntity> transferOrderDetailsEntities) {
        for (TransferOrderDetailsEntity entity : transferOrderDetailsEntities) {
            TransferOrderDetailsVo transferOrderDetailsVo = new TransferOrderDetailsVo();
            BeanUtils.copyProperties(entity, transferOrderDetailsVo);
            //查询出该明细对应的装箱规格
            List<TransferOrderPackInfoEntity> transferOrderPackInfoEntities = transferDao.findPackInfoByCommodityId(entity.getCommodityInfoCd());
            transferOrderDetailsVo.setPackInfoEntities(transferOrderPackInfoEntities);

            //TODO 补充完整明细sku信息
            Result prodResult = productService.getProdDetails(entity.getSkuId());
            if (prodResult.getCode() != 200) {
                throw new RuntimeException("调用查询产品接口错误:" + prodResult.getMsg());
            }
            Map prodData = (Map) prodResult.getData();
            Map defineData = (Map) prodData.get("define");
            transferOrderDetailsVo.setSkuName((String) defineData.get("skuNameZh"));

            List<Map> proLabels = (List<Map>) prodData.get("declareLabelList");
            List<String> labels = new ArrayList<>();
            for (Map proLabel : proLabels) {
                labels.add((String) proLabel.get("labelText"));
            }
            transferOrderDetailsVo.setSkuLabels(labels);

            List<Map> imgs = (List<Map>) prodData.get("imagesList");
            List<String> imgUrls = new ArrayList<>();
            for (Map img : imgs) {
                imgUrls.add((String) img.get("imageUrl"));
            }
            transferOrderDetailsVo.setUrl(imgUrls);

            transferOrderDetailsVos.add(transferOrderDetailsVo);
        }
    }

    @Override
    public String getTransferInfo(String waybillId) {
        return transferDao.getTransferInfo(waybillId);
    }
}
