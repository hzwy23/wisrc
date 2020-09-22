package com.wisrc.replenishment.webapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisrc.replenishment.webapp.dao.*;
import com.wisrc.replenishment.webapp.dto.WayBillInfo.*;
import com.wisrc.replenishment.webapp.dto.code.CodeTemplateConfEntity;
import com.wisrc.replenishment.webapp.entity.*;
import com.wisrc.replenishment.webapp.query.waybillInfo.WayBillInfoQuery;
import com.wisrc.replenishment.webapp.service.*;
import com.wisrc.replenishment.webapp.service.externalService.LogisticsService;
import com.wisrc.replenishment.webapp.service.externalService.ProductService;
import com.wisrc.replenishment.webapp.service.externalService.PythonService;
import com.wisrc.replenishment.webapp.utils.*;
import com.wisrc.replenishment.webapp.vo.*;
import com.wisrc.replenishment.webapp.vo.transferorder.TransferDeclareVo;
import com.wisrc.replenishment.webapp.vo.transferorder.TransferInfoReturnVo;
import com.wisrc.replenishment.webapp.vo.transferorder.TransferOrderDetailsVo;
import com.wisrc.replenishment.webapp.vo.waybill.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class WaybillInfoServiceImpl implements WaybillInfoService {
    @Autowired
    private ProductService productService;
    @Autowired
    private TransferDao transferDao;
    @Autowired
    private TransferService transferService;
    @Autowired
    private WaybillInfoDao waybillInfoDao;
    @Autowired
    private ShipmentService shipmentService;
    @Autowired
    private FbaMskuInfoService fbaMskuInfoService;
    @Autowired
    private WarehouseInfoService warehouseInfoService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private WarehouseListService warehouseListService;
    @Autowired
    private ReplenishmentShipmentListService replenishmentShipmentListService;
    @Autowired
    private MskuInfoListService mskuInfoListService;
    @Autowired
    private FbaReplenishmentInfoDao fbaReplenishmentInfoDao;
    @Autowired
    private FbaMskuPackInfoDao fbaMskuPackInfoDao;
    @Autowired
    private ReplenishShippingDataDao replenishShippingDataDao;
    @Autowired
    private WaybillExceptionInfoDao waybillExceptionInfoDao;
    @Autowired
    private FbaReplenishmentInfoService replenishmentInfoService;
    @Autowired
    private CustomsInfoDao customsInfoDao;
    @Autowired
    private PythonService pythonService;
    @Autowired
    LogisticsService logisticsService;

    @Autowired
    private WaybillReplenishmentRelDao waybillReplenishmentRelDao;
    @Autowired
    private UploadDownloadService uploadDownloadService;
    @Autowired
    private ReplenishmentCodeService replenishmentCodeService;


    /**
     * @param Keyword 关键字，支持物流单号，批次号，库存SKU，库存SKU名称，FBA运单号
     */
    @Override
    public Result findInfoFine(String startPage, String pageSize,
                               Date waybillOrderDateBegin,
                               Date waybillOrderDateEnd,
                               String shipmentId, Integer customsCd,
                               String warehouseId, String employeeId,
                               Integer logisticsTypeCd, String Keyword,
                               String isLackLogistics, String isLackShipment,
                               String isLackCustomsDeclare, String isLackCustomsClearance,
                               String waybillId, String batchNumber, String fbaReplenishmentId,
                               String skuId, String productName) {
        String retMsg = "";
        try {
            String mskuListStr = null;
            // 获取所有的mskuid信息
            if (Keyword != null) {
                try {
                    List mskuIds = mskuIds = mskuInfoListService.getMskuList(employeeId, Keyword, Keyword);
                    mskuListStr = ArrayToInArguments.toInArgs(mskuIds);

                } catch (Exception e) {
                    e.printStackTrace();
                    retMsg += "[获取mskuid失败，未对skuid，sku名称，负责人进行过滤]";
                }
            }

            // 根据物流商筛选渠道信息
            Result shipmentResult = null;
            List offerIds = new ArrayList();
            if (shipmentId != null) {
                try {
                    // 获取FBA渠道报价信息
                    shipmentResult = shipmentService.getOfferId(shipmentId, 1);
                    if (shipmentResult.getCode() != 200) {
                        return Result.failure(400, shipmentResult.getMsg(), "");
                    }
                    List<Map> shipmentData = (List) shipmentResult.getData();

                    for (Map shipment : shipmentData) {
                        offerIds.add(shipment.get("offerId"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return Result.failure(400, "物流模块连接发生错误", "");
                }
            }

            int num = 0, page = 10;
            if (startPage != null && pageSize != null) {
                try {
                    num = Integer.valueOf(startPage);
                    page = Integer.valueOf(pageSize);
                } catch (Exception e) {
                    retMsg += "[分页参数错误，默认只查询10条数据]";
                    num = 0;
                    page = 10;
                }
            }

            //查询跟踪单基本信息
            PageHelper.startPage(num, page);
            List<WaybillInfoEntity> enList = waybillInfoDao.search(waybillOrderDateBegin, waybillOrderDateEnd,
                    offerIds, customsCd, warehouseId,
                    logisticsTypeCd, Keyword, waybillId,
                    batchNumber, fbaReplenishmentId,
                    mskuListStr, isLackLogistics, isLackShipment,
                    isLackCustomsDeclare, isLackCustomsClearance);

            PageInfo info = new PageInfo(enList);
            WaybillInfoListVO vo = new WaybillInfoListVO();
            vo.setWaybillIfInfoVO(lackInformationNum());
            vo.setWaybillInfoVOList(PageData.pack(info.getTotal(), info.getPages(), "waybillInfoList", tovoList(enList)));
            return Result.success(200, retMsg, vo);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }


    @Override
    public WaybillInfoVO findInfoById(String waybillId) {
        WaybillInfoVO vo = new WaybillInfoVO();
        WaybillInfoEntity ele = waybillInfoDao.findInfoById(waybillId);
        try {
            Result res = shipmentService.getShipment(ele.getOfferId());
            Map mapOffer = (Map) res.getData();
            if (mapOffer != null) {
                vo.setShipmentId((String) mapOffer.get("shipmentId"));
                //渠道名称
                vo.setChannelName((String) mapOffer.get("channelName"));
                //提货方式
                vo.setPickupTypeCd((Integer) mapOffer.get("pickupTypeCd"));
                vo.setPickupTypeName((String) mapOffer.get("pickupTypeName"));
                try {
                    //获取物流商名称
                    Result rShipment = companyService.getShipmentName((String) mapOffer.get("shipmentId"));
                    Map mShipment = (Map) rShipment.getData();
                    if (mShipment != null) {
                        vo.setShipmentName((String) mShipment.get("shipmentName"));
                    }
                } catch (Exception e) {
                    vo.setShipmentName("");
                }
            }
            vo.setLogisticsId(ele.getLogisticsId());
            //获取运单号异常信息列表
            vo.setExceptionList(waybillInfoDao.findExcList(ele.getWaybillId()));
        } catch (Exception e) {
        }
        return vo;
    }

    @Override
    public WaybillDetailsVO findDetailsById(String waybillId) {
        WaybillDetailsVO vo = new WaybillDetailsVO();
        //查询跟踪单基本信息
        WaybillInfoEntity infoEle = waybillInfoDao.findInfoById(waybillId);
        if (infoEle == null) {
            return null;
        }
        CustomsInfoEntity customsInfo = customsInfoDao.get(waybillId);
        vo.setCustomsInfo(customsInfo);
        vo.setIsLackCustomsDeclare(infoEle.getIsLackCustomsDeclare());
        vo.setIsLackCustomsClearance(infoEle.getIsLackCustomsClearance());
        vo.setIsLackLogistics(infoEle.getIsLackLogistics());
        vo.setIsLackShipment(infoEle.getIsLackShipment());
        vo.setFlag(1);
        List<WaybillReplenishmentEntity> repList = waybillInfoDao.findReplenishmentList(waybillId);
        if (repList.size() == 1 && repList.get(0) == null) {
            vo.setFlag(2);
        }
        //如果是补货单的话
        if (vo.getFlag() == 1) {

            if (repList.size() == 1) {
                infoEle.setWarehouseId(repList.get(0).getWarehouseId());
            } else {
                infoEle.setWarehouseId("");
            }

            //查询物流跟踪单跟踪单商品信息
            List<WaybillMskuInfoVO> MskuVO = (List<WaybillMskuInfoVO>) findMskuVO(waybillId).get("fba");
            if (MskuVO != null) {
                //查询出该运单对应的fba补货单信息
                FbaReplenishmentInfoVO fbaEntity = replenishmentInfoService.findById(MskuVO.get(0).getFbaReplenishmentId());
                infoEle.setAmazonWarehouseAddress(fbaEntity.getInfoEntity().getAmazonWarehouseAddress());
                infoEle.setAmazonWarehouseZipcode(fbaEntity.getInfoEntity().getAmazonWarehouseZipcode());
                infoEle.setWarehouseCode(fbaEntity.getInfoEntity().getCollectGoodsWarehouseId());
            }

            vo.setWaybillMskuInfoList(MskuVO);
        } else {
            //如果是调拨单的话
            String transferId = transferDao.findTransferIdByWaybillId(waybillId);
            Result transferInfoResult = transferService.findById(transferId);
            TransferInfoReturnVo transferInfoReturnVo = (TransferInfoReturnVo) transferInfoResult.getData();
            infoEle.setWarehouseId(transferInfoReturnVo.getTransferBasicInfoEntity().getWarehouseStartId());
            infoEle.setWarehouseCode(transferInfoReturnVo.getWarehouseEndName());
            Result startWarehouse = warehouseInfoService.getWarehouseName(transferInfoReturnVo.getTransferBasicInfoEntity().getWarehouseEndId());
            Map wareMap = (Map) startWarehouse.getData();
            StringBuilder addr = new StringBuilder();
            if (wareMap.get("detailsAddr") != null) {
                addr.append((String) wareMap.get("detailsAddr") + ",");
            }
            if (wareMap.get("cityName") != null) {
                addr.append((String) wareMap.get("cityName") + ",");
            }
            if (wareMap.get("provinceName") != null) {
                addr.append((String) wareMap.get("provinceName") + ",");
            }
            if (wareMap.get("countryCd") != null) {
                addr.append((String) wareMap.get("countryCd") + ",");
            }
            if (wareMap.get("zipCode") != null) {
                addr.append((String) wareMap.get("zipCode") + ",");
            }
            if (addr.length() > 0) {
                infoEle.setReceiveAddr(addr.toString().substring(0, addr.length() - 1));
            } else {
                infoEle.setReceiveAddr("");
            }
            List<WaybillMskuInfoVO> mskuInfoVOS = new ArrayList<>();
            WaybillMskuInfoVO waybillMskuInfoVO = new WaybillMskuInfoVO();
            List<FbaMskuInfoVO> fbaMskuInfoVOS = new ArrayList<>();
            for (TransferOrderDetailsVo transferOrderDetailsVo : transferInfoReturnVo.getTransferOrderDetailsVos()) {
                waybillMskuInfoVO.setFbaReplenishmentId(transferId);
                waybillMskuInfoVO.setBatchNumber(transferId);

                FbaMskuInfoVO fbaMskuInfoVO = new FbaMskuInfoVO();
                List<FbaMskuPackQueryVO> packQueryVOS = new ArrayList<>();
                //TODO 补充报关资料字段
                TransferDeclareEntity transferDeclareEntity = transferDao.findDeclareInfoByTransferIdAndSkuId(transferId, transferOrderDetailsVo.getSkuId());
                if (transferDeclareEntity != null) {
                    fbaMskuInfoVO.setDeclarationElements(transferDeclareEntity.getDeclarationElements());
                    fbaMskuInfoVO.setDeclareUnitPrice(transferDeclareEntity.getDeclareUnitPrice());
                    fbaMskuInfoVO.setMskuUnitCd(Integer.parseInt(transferDeclareEntity.getSkuUnitCd()));
                }


                fbaMskuInfoVO.setFbaReplenishmentId(transferId);
                fbaMskuInfoVO.setReplenishmentQuantity(transferOrderDetailsVo.getTransferQuantity());
                fbaMskuInfoVO.setPackingNumber(transferOrderDetailsVo.getPackingQuantity() == null ? 0 : transferOrderDetailsVo.getPackingQuantity());
                fbaMskuInfoVO.setDeliveryNumber(transferOrderDetailsVo.getDeliveryQuantity() == null ? 0 : transferOrderDetailsVo.getDeliveryQuantity());
                fbaMskuInfoVO.setSignInQuantity(transferOrderDetailsVo.getSignInQuantity() == null ? 0 : transferOrderDetailsVo.getSignInQuantity());
                MskuInfoVO mskuInfoVO = new MskuInfoVO();

                mskuInfoVO.setStoreSku(transferOrderDetailsVo.getSkuId());
                mskuInfoVO.setStoreName(transferOrderDetailsVo.getSkuName());
                mskuInfoVO.setFnSKU(transferOrderDetailsVo.getFnSku());
                mskuInfoVO.setPicture(transferOrderDetailsVo.getUrl() == null || transferOrderDetailsVo.getUrl().size() == 0 ? null : transferOrderDetailsVo.getUrl().get(0));
                Result productResult = productService.getProductInfoBySkuId(transferOrderDetailsVo.getSkuId());
                if (productResult.getCode() != 200) {
                    throw new RuntimeException("调用外部接口失败");
                }
                Map productData = (Map) productResult.getData();
                Map data = (Map) productData.get("declareInfo");
                mskuInfoVO.setDeclareNameEn((String) data.get("declareNameEn"));
                mskuInfoVO.setDeclareNameZh((String) data.get("declareNameZh"));
                mskuInfoVO.setCustomsNumber((String) data.get("customsNumber"));
                //该产品的装箱规格
                for (TransferOrderPackInfoEntity transferOrderPackInfoEntity : transferOrderDetailsVo.getPackInfoEntities()) {
                    FbaMskuPackQueryVO packQueryVO = new FbaMskuPackQueryVO();
                    packQueryVO.setUuid(transferOrderPackInfoEntity.getUuid());
                    packQueryVO.setOuterBoxSpecificationHeight((int) transferOrderPackInfoEntity.getOuterBoxSpecificationHeight());
                    packQueryVO.setOuterBoxSpecificationLen((int) transferOrderPackInfoEntity.getOuterBoxSpecificationLen());
                    packQueryVO.setOuterBoxSpecificationWidth((int) transferOrderPackInfoEntity.getOuterBoxSpecificationWidth());
                    packQueryVO.setPackingQuantity(transferOrderPackInfoEntity.getPackingQuantity());
                    packQueryVO.setNumberOfBoxes(transferOrderPackInfoEntity.getNumberOfBoxes());
                    packQueryVO.setPackingWeight(transferOrderPackInfoEntity.getWeight());
                    packQueryVO.setIsStandard(transferOrderPackInfoEntity.getIsStandard() == null ? 1 : transferOrderPackInfoEntity.getIsStandard());
                    packQueryVO.setDeliveryNumber(transferOrderPackInfoEntity.getDeliveryQuantity() == null ? 0 : transferOrderPackInfoEntity.getDeliveryQuantity());
                    packQueryVOS.add(packQueryVO);
                }
                fbaMskuInfoVO.setMskuInfoVO(mskuInfoVO);
                fbaMskuInfoVO.setMskupackList(packQueryVOS);
                fbaMskuInfoVOS.add(fbaMskuInfoVO);
            }
            waybillMskuInfoVO.setMskuList(fbaMskuInfoVOS);
            mskuInfoVOS.add(waybillMskuInfoVO);
            vo.setWaybillMskuInfoList(mskuInfoVOS);
        }
        //物流跟踪信息
        List<LogisticsTrackInfoEntity> logList = null;
        if (infoEle.getLogisticsId() != null) {
            //查询跟踪单物流跟踪信息
            logList = waybillInfoDao.findLogisticsList(infoEle.getLogisticsId());
        }
        // 新增运单号缺少相关信息
        List<String> ifInfoList = new ArrayList<>();
        // 查看头程运费预估信息
        List<FreightEstimateinfoEntity> freList = waybillInfoDao.findFreightInfo(waybillId);
        if (freList.size() > 0) {
            vo.setEstimateInfo(freList.get(0));
            //判断是否少报关信息
            if (freList.get(0).getCustomsTypeCd() == 1) {
                if (1 != infoEle.getIsLackCustomsDeclare()) {
                    ifInfoList.add("少报关信息");
                }
            }
        }
        vo.setInfoEn(tovo(infoEle));
        String offerId = infoEle.getOfferId();
        //货运公司代号
        if (infoEle.getLogisticsTrackUrl() == null || infoEle.getLogisticsTrackUrl().equals("")) {
            try {
                String logisticsTrackUrl = getLogisticsTrackUrl(offerId);
                vo.getInfoEn().setLogisticsTrackUrl(logisticsTrackUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //Result result = pythonService.getPythonData(vo.getInfoEn().getBatchNumberList().get(0),"1");
        //Map map= (Map) result.getData();
        if (vo.getInfoEn().getPickupTypeCd() == 2) {
            if (vo.getInfoEn().getLogisticsId() != null) {
                for (int i = 0; i < vo.getInfoEn().getBatchNumberList().size(); i++) {
                    LogisticsTrackInfoEntity log = new LogisticsTrackInfoEntity();
                    log.setLogisticsId(vo.getInfoEn().getLogisticsId());
                    log.setSignTime(Time.getCurrentDateTime());
                    log.setStatus(1);
                    logList.add(log);
                }
            }
        }
        vo.setLogisticsList(logList);
        //判断是否少相关信息
        //如果是海外仓或者虚拟仓，那么添加应该添加少发货数据  本地仓和fba不用
        if (getWarehouseType(infoEle.getWarehouseId())) {
            if (infoEle.getIsLackShipment() != 1) {
                ifInfoList.add("少发货数据");
            }
        }
//      缺少清关发票的修改为不含亚马逊自提，代码2
        if (infoEle.getIsLackLogistics() != 1 && vo.getInfoEn().getPickupTypeCd() != 2) {
            ifInfoList.add("少物流信息");
        }
//      缺少清关发票的修改为不含海外仓，代码B
        if (infoEle.getIsLackCustomsClearance() != 1 && (infoEle.getWarehouseId().charAt(0) != 'B')) {

            ifInfoList.add("少清关发票");
        }
        vo.setIfInfoList(ifInfoList);
        return vo;
    }

    private String getLogisticsTrackUrl(String offerId) throws Exception {
        Result result = logisticsService.getLogistics(offerId);
        if (result.getCode() != 200) {
            throw new Exception("报价单详情接口出错！");
        }
        Map map = (Map) result.getData();
        return (String) map.get("logisticsTrackUrl");
    }


    @Override
    public String findWaybillId() {
        return waybillInfoDao.findWaybillId();
    }


    /**
     * 新增无法交运单
     *
     * @param user 用户ID
     * @param vo   物流交运信息
     */
    @Override
    @Transactional(transactionManager = "retailReplenishmentTransactionManager")
    public Result addWaybillInfo(AddWaybillVO vo, String user) {
        List<WaybillRelEntity> list = vo.getWaybillRelEntityList();


        boolean isDelivery = true;
        for (WaybillRelEntity ele : list) {
            String fbaReplenishmentId = ele.getFbaReplenishmentId();
            // 查看FBA补货单是否有补货批次号
            FbaReplenishmentInfoEntity fbaReplenishmentInfoEntity = fbaReplenishmentInfoDao.getBatchNumberByReplenishmentId(fbaReplenishmentId);
            if (fbaReplenishmentInfoEntity == null || fbaReplenishmentInfoEntity.getBatchNumber() == null) {
                return Result.failure(423, "请先录入补货批次号，才进行物流交运", fbaReplenishmentInfoEntity);
            }
            //获取补货单状态
            int statusCd = fbaReplenishmentInfoDao.getFbaStatusCd(fbaReplenishmentId);
            if (statusCd != 6) {
                return Result.failure(423, "补货单状态不是待选择渠道，不允许交运", "");
            }

            //如果没有数据，说明是新增交运，也就是新增物流单
            if (waybillInfoDao.ifDeliveries(fbaReplenishmentId) > 0) {
                isDelivery = false;
            }
        }

        // 如果已经交运了，则不允许再次交运
        if (isDelivery) {
            return add(vo, user);
        }
        return Result.failure(424, "FBA补货单已经发货，不能再次交运", vo);
    }

    /*
    查询运单下商品详情信息
     */
    @Override
    public Map findMskuVO(String waybillId) {
        Map<String, Object> result = new HashMap<>();
        //查询跟踪单商品信息
        List<WaybillMskuInfoVO> MskuVO = new ArrayList<>();
        //查询跟踪单下补货单的信息
        List<WaybillReplenishmentEntity> replenishmentList = waybillInfoDao.findReplenishmentList(waybillId);
        if (replenishmentList.size() == 1 && replenishmentList.get(0) == null) {
            //调拨单生成的交运单
            String transferId = transferDao.findTransferIdByWaybillId(waybillId);
            Result transferResult = transferService.findById(transferId);
            TransferInfoReturnVo transferInfoReturnVo = (TransferInfoReturnVo) transferResult.getData();
            List<TransferDeclareVo> transferDeclareVos = new ArrayList<>();
            for (TransferOrderDetailsVo transferOrderDetailsVo : transferInfoReturnVo.getTransferOrderDetailsVos()) {
                TransferDeclareVo transferDeclareVo = new TransferDeclareVo();
                BeanUtils.copyProperties(transferOrderDetailsVo, transferDeclareVo);
                Result productResult = productService.getProductInfoBySkuId(transferOrderDetailsVo.getSkuId());
                Map productMap = (Map) productResult.getData();
                Map declareMap = (Map) productMap.get("declareInfo");
                transferDeclareVo.setCustomsNumber((String) declareMap.get("customsNumber"));
                transferDeclareVo.setDeclareNameEn((String) declareMap.get("declareNameEn"));
                transferDeclareVo.setDeclareNameZh((String) declareMap.get("declareNameZh"));
                TransferDeclareEntity transferDeclareEntity = transferDao.findDeclareInfoByTransferIdAndSkuId(transferId, transferOrderDetailsVo.getSkuId());
                transferDeclareVo.setDeclarationElements(transferDeclareEntity.getDeclarationElements());
                transferDeclareVo.setDeclareUnitPrice(transferDeclareEntity.getDeclareUnitPrice());
                transferDeclareVo.setSkuUnitCd(transferDeclareEntity.getSkuUnitCd());
                transferDeclareVos.add(transferDeclareVo);
            }
            result.put("transferWaybillDeclare", transferDeclareVos);
        } else {
            //补货单生成的交运单
            for (WaybillReplenishmentEntity ele : replenishmentList) {
                WaybillMskuInfoVO msVO = new WaybillMskuInfoVO();
                List<FbaMskuInfoVO> mskuList = fbaMskuInfoService.findMskuByReplenId(ele.getFbaReplenishmentId());
                msVO.setFbaReplenishmentId(ele.getFbaReplenishmentId());
                msVO.setBatchNumber(ele.getBatchNumber());
                msVO.setShopName(mskuList.get(0).getShopName());
                msVO.setMskuList(mskuList);
                MskuVO.add(msVO);
            }
            result.put("fba", MskuVO);
        }

        return result;
    }


    /**
     * 一个物流交运单可能对应着多个FBA补货单，所以，这块需要调整
     *
     * @param waybillId 物流交运单号
     * @param user      用户ID
     */
    @Override
    @Transactional(transactionManager = "retailReplenishmentTransactionManager")
    public void deleteWaybill(String waybillId, String user) {
        Timestamp time = Time.getCurrentTimestamp();
        waybillInfoDao.deleteWaybill(waybillId, time, user);
        waybillInfoDao.deleteFreight(waybillId, time, user);
        waybillInfoDao.deleteRelation(waybillId, 1);

        // modify by zhanwei_huang ,fixed bug. 可能获取到多个物流教育单号
        String[] fbaReplenishmentIdList = fbaReplenishmentInfoDao.FbaId(waybillId);
        //取消可能对应的调拨单
        transferDao.cancelShipment(waybillId, time, user);
        for (String fbaReplenishmentId : fbaReplenishmentIdList) {
            String warehouseId = fbaReplenishmentInfoDao.getWarehouseIdByFbaId(fbaReplenishmentId);
            //如果是本地仓发货时（且当关联的物流交运单取消交运之后，FBA补货单还原至待选择渠道状态）
            if (!getWarehouseType(warehouseId)) {
                fbaReplenishmentInfoDao.updateStatusById(6, fbaReplenishmentId);
            }
        }
    }


    /**
     * 完善更新物流信息
     *
     * @param vo
     */
    @Override
    public void updateWaybill(PerfectWaybillInfoVO vo) {
        waybillInfoDao.updateWaybill(vo);
    }

    @Override
    @Transactional(transactionManager = "retailReplenishmentTransactionManager")
    public void addException(WaybillExceptionVO vo, String userId) {
        String waybillId = vo.getWaybillId();
        List<WaybillExceptionInfoEntity> eleList = vo.getWaybillExceptionInfoEntityList();
        waybillInfoDao.deleteException(waybillId);
        for (WaybillExceptionInfoEntity ele : eleList) {
            ele.setUuid(UUID.randomUUID().toString());
            ele.setWaybillId(waybillId);
            ele.setModifyTime(new Timestamp(System.currentTimeMillis()));
            ele.setModifyUser(userId);
            waybillInfoDao.addException(ele);
        }
    }

    /**
     * 生成物流跟踪号 D+年月日+三位数流水号
     * 例如：D20180518001
     *
     * @return
     */
    private String toId() {
        String waybillId = waybillInfoDao.findWaybillId();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd"); // 时间字符串产生方式
        String date = format.format(new Date(System.currentTimeMillis()));
        String uid_pfix = "D"; // 截取年份
        if (waybillId == null || "".equals(waybillId)) {
            waybillId = uid_pfix + date + "001";
        } else {
            String uid_end = "";
            try {
                uid_end = waybillId.substring(0, 8);
            } catch (Exception e) {
                return "D" + date + (int) ((Math.random() * 9 + 1) * 10000);
            }
            if (date.equals(uid_end)) {
                long endNum = Long.parseLong(waybillId);
                long tmpNum = endNum + 1;
                waybillId = uid_pfix + tmpNum;
            } else {
                waybillId = uid_pfix + date + "001";
            }
        }
        return waybillId;
    }

    /**
     * 查询跟踪单关联的其他信息
     *
     * @param ele
     * @return
     */
    private WaybillInfoVO tovo(WaybillInfoEntity ele) {
        WaybillInfoVO vo = new WaybillInfoVO();
        BeanUtils.copyProperties(ele, vo);
        //查询补货批次，发货数量，发货仓集合
        List<WaybillReplenishmentEntity> alist = waybillInfoDao.findReplenishmentList(ele.getWaybillId());
        //发货总量
        int num = 0;
        //补货批次集合
        List<String> list = new ArrayList<>();
        if (alist.size() > 0 && alist.get(0) != null) {
            for (WaybillReplenishmentEntity rEle : alist) {
                vo.setPickupTypeCd(rEle.getPickupTypeCd());
                vo.setPickupTypeName(rEle.getPickupTypeName());
                vo.setWarehouseId(rEle.getWarehouseId());
                num += rEle.getDeliveringQuantity();
                list.add(rEle.getBatchNumber());
                ele.setWarehouseId(rEle.getWarehouseId());
            }
            vo.setWarehouseId(ele.getWarehouseId());
        }
        try {
            Result result = warehouseInfoService.getWarehouseName(ele.getWarehouseId());
            Map map = (Map) result.getData();
            if (map != null) {
                vo.setWarehouseName((String) map.get("warehouseName"));
            } else {
                vo.setWarehouseName("");
            }
        } catch (Exception e) {
            vo.setWarehouseName("");
        }
        try {
            Result res = shipmentService.getShipment(ele.getOfferId());
            Map mapOffer = (Map) res.getData();
            if (mapOffer != null) {
                vo.setShipmentId((String) mapOffer.get("shipmentId"));
                vo.setChannelName((String) mapOffer.get("channelName"));
                try {
                    //获取物流商名称
                    Result rShipment = companyService.getShipmentName((String) mapOffer.get("shipmentId"));
                    Map mShipment = (Map) rShipment.getData();
                    if (mShipment != null) {
                        vo.setShipmentName((String) mShipment.get("shipmentName"));
                    }
                } catch (Exception e) {
                }
                //获取物流时效信息   数据拼接
                List<Map> effectiveList = (List<Map>) mapOffer.get("effectiveList");
                if (effectiveList.size() > 0) {
                    List<String> effList = new ArrayList<>();
                    for (Map m : effectiveList) {
                        effList.add(m.get("startDays") + "-" + m.get("endDays"));
                    }
                    vo.setEffectiveList(effList);
                }
            }
        } catch (Exception e) {
        }
        List<FreightEstimateinfoEntity> freightInfo = waybillInfoDao.findFreightInfo(ele.getWaybillId());
        if (freightInfo.size() == 1) {
            vo.setCustomsTypeCd(freightInfo.get(0).getCustomsTypeCd());
        }
        vo.setBatchNumberList(list);
        vo.setDeliveringQuantity(num);
        //获取运单号异常信息列表
        vo.setExceptionList(waybillInfoDao.findExcList(ele.getWaybillId()));
        return vo;
    }

    /**
     * 查询跟踪单关联的其他信息列表（优化方案）
     *
     * @param eleList
     * @return
     */
    private List<WaybillInfoVO> tovoList(List<WaybillInfoEntity> eleList) {
        List<WaybillInfoVO> voList = new ArrayList<>();
        List<String> warehouseList = new ArrayList<>();
        List<String> offerIdList = new ArrayList<>();
        for (WaybillInfoEntity ele : eleList) {
            warehouseList.add(ele.getWarehouseId());
            if (ele.getWarehouseCode().startsWith("A") || ele.getWarehouseCode().startsWith("B") || ele.getWarehouseCode().startsWith("C") || ele.getWarehouseCode().startsWith("F")) {
                warehouseList.add(ele.getWarehouseCode());
            }
            offerIdList.add(ele.getOfferId());
        }
        Map warehouseNameList = new HashMap();
        Map shipmentList = new HashMap();
        try {
            //获取仓库名称集合
            warehouseNameList = warehouseListService.getWarehouseNameList(warehouseList);
        } catch (Exception e) {

        }
        try {
            //获取物流商报价信息集合
            shipmentList = replenishmentShipmentListService.getShipmentList(offerIdList);
        } catch (Exception e) {

        }
        for (WaybillInfoEntity ele : eleList) {
            WaybillInfoVO vo = new WaybillInfoVO();
            BeanUtils.copyProperties(ele, vo);
            List<String> fbaIds = fbaReplenishmentInfoDao.findFbaByWaybillId(ele.getWaybillId());
            int replementNum = 0;
            int signNum = 0;
            int deliverNum = 0;
            List<String> list = new ArrayList<>();
            if (fbaIds.size() >= 1 && fbaIds.get(0) != null) {
                for (String fbaId : fbaIds) {
                    SumNum sumNum = waybillInfoDao.getSumNum(fbaId);
                    if (sumNum == null) {
                        continue;
                    }
                    if (sumNum.getReplenishmentNumber() != null) {
                        replementNum += sumNum.getReplenishmentNumber();
                    }
                    if (sumNum.getSignNumber() != null) {
                        signNum += sumNum.getSignNumber();
                    }
                    if (sumNum.getDeliveryNumber() != null) {
                        deliverNum += sumNum.getDeliveryNumber();
                    }
                }
                //查询补货批次，发货数量，发货仓集合
                List<WaybillReplenishmentEntity> alist = waybillInfoDao.findReplenishmentList(ele.getWaybillId());
                //发货总量
                int num = 0;
                //补货批次集合
                for (WaybillReplenishmentEntity rEle : alist) {
                    if (rEle != null) {
                        vo.setWarehouseId(rEle.getWarehouseId());
                        num += rEle.getDeliveringQuantity();
                        list.add(rEle.getBatchNumber());
                    }
                }
                vo.setWarehouseId(ele.getWarehouseId());
                vo.setWarehouseName((String) warehouseNameList.get(ele.getWarehouseId()));
            } else {
                String transferId = transferDao.findTransferIdByWaybillId(ele.getWaybillId());
                SumNum sumNum = transferDao.getSumByTransferId(transferId);
                replementNum = sumNum.getReplenishmentNumber();
                signNum = sumNum.getSignNumber() == null ? 0 : sumNum.getSignNumber();
                deliverNum = sumNum.getDeliveryNumber() == null ? 0 : sumNum.getDeliveryNumber();
                list.add(transferId);
                vo.setWarehouseId(ele.getWarehouseId());
                vo.setWarehouseName((String) warehouseNameList.get(ele.getWarehouseId()));
                vo.setWarehouseCode((String) warehouseNameList.get(ele.getWarehouseCode()));
            }
            vo.setOfferId(ele.getOfferId());
            Map shipmentMap = (Map) shipmentList.get(ele.getOfferId());
            if (shipmentMap != null) {
                //获取物流商名称
                vo.setShipmentId((String) shipmentMap.get("shipmentId"));
                vo.setShipmentName((String) shipmentMap.get("shipmentName"));
                //获取渠道名称
                vo.setChannelName((String) shipmentMap.get("channelName"));
                //获取物流时效
                vo.setEffectiveList((List<String>) shipmentMap.get("effList"));
            }
            //获取头程运费信息
            List<FreightEstimateinfoEntity> freightInfo = waybillInfoDao.findFreightInfo(ele.getWaybillId());
            if (freightInfo.size() == 1) {
                vo.setCustomsTypeCd(freightInfo.get(0).getCustomsTypeCd());
            }
            vo.setBatchNumberList(list);
          /*  if (sumNum != null) {
                vo.setReplenishmentNumber(sumNum.getReplenishmentNumber());
                vo.setDeliveryNumber(sumNum.getDeliveryNumber());
                vo.setSignNumber(sumNum.getSignNumber());
            }*/
            vo.setReplenishmentNumber(replementNum);
            vo.setDeliveringQuantity(deliverNum);
            vo.setSignNumber(signNum);
            vo.setDeliveryNumber(deliverNum);
            //获取运单号异常信息列表
            vo.setExceptionList(waybillInfoDao.findExcList(ele.getWaybillId()));
            voList.add(vo);
        }
        return voList;
    }

    //查询异常运单各个异常信息数量
    private WaybillIfInfoVO lackInformationNum() {
        WaybillIfInfoVO vo = new WaybillIfInfoVO();
        vo.setClearanceNum(waybillInfoDao.clearanceNum());
        vo.setDeliveryNum(waybillInfoDao.deliveryNum());
        vo.setIcustomsInfoNum(waybillInfoDao.icustomsInfoNum());
        vo.setLogisticsInfoNum(waybillInfoDao.logisticsInfoNum());
        return vo;
    }

    /**
     * 新增物流跟踪单
     */
    private Result add(AddWaybillVO vo, String user) {
        //系统生成运单号
        String randomValue = Toolbox.randomUUID();
        //新增物流跟踪单基本信息
        WaybillInfoEntity infoEle = new WaybillInfoEntity();
        BeanUtils.copyProperties(vo, infoEle);
        infoEle.setRandomValue(randomValue);
        infoEle.setCreateTime(Time.getCurrentTimestamp());
        infoEle.setWaybillOrderDate(Time.getCurrentDate());
        infoEle.setCreateUser(user);
        infoEle.setDeleteStatus(0);

        Result shipment = shipmentService.getShipment(vo.getOfferId());
        if (shipment == null || shipment.getCode() != 200) {
            return shipment;
        }
        Map map = (Map) shipment.getData();
        // 获取物流商ID
        String shipmentId = (String) map.get("shipmentId");
        //新生成默认为1--待发货
        infoEle.setLogisticsTypeCd(1);
        List<WaybillRelEntity> relList = vo.getWaybillRelEntityList();

        // 新增物流交运单
        waybillInfoDao.addInfo(infoEle);


        //根据随机数查出物流单id
        String id = waybillInfoDao.findWaybillIdByRandomValue(randomValue);


//        String fbaReplenishmentId = relList.get(0).getFbaReplenishmentId();

        //新增物流跟踪单合并后的补货单集合
        for (WaybillRelEntity ele : relList) {
            ele.setUuid(UUID.randomUUID().toString());
            ele.setWaybillId(id);
            waybillInfoDao.addRel(ele);

            // 更新物流交运单与FBA补货单关系表
            fbaReplenishmentInfoDao.updateStatusById(2, ele.getFbaReplenishmentId());

            // 更新FBA补货单信息
            waybillInfoDao.updateFba(ele.getFbaReplenishmentId(), shipmentId, vo.getOfferId());

        }

        //新增运费估算信息
        FreightEstimateinfoEntity freEle = vo.getFreightEstimateinfoEntity();
        freEle.setWaybillId(id);
        freEle.setCreateTime(Time.getCurrentTimestamp());
        freEle.setCreateUser(user);
        freEle.setDeleteStatus(0);
        waybillInfoDao.addFreighInfo(freEle);

        Map<String, Object> ret = new LinkedHashMap<>();
        ret.put("waybillId", id);
        ret.put("arguments", vo);
        return Result.success(ret);
    }

    /**
     * 修改交运信息
     */
    String update(AddWaybillVO vo, String user) {
        FreightEstimateinfoEntity entity = vo.getFreightEstimateinfoEntity();
        List<WaybillRelEntity> list = vo.getWaybillRelEntityList();
        entity.setModifyUser(user);
        entity.setModifyTime(Time.getCurrentTimestamp());
        //更新新的运单运费表
        waybillInfoDao.updateFreighInfo(entity);
        // 修改原物流跟踪单物流商渠道ID
        waybillInfoDao.updateOfferId(entity.getWaybillId(), vo.getOfferId());
        Result shipment = shipmentService.getShipment(vo.getOfferId());
        Map map = (Map) shipment.getData();
        for (WaybillRelEntity e : list) {
            // 修改对应补货单物流商渠道ID
            waybillInfoDao.updateFbaOfferId(e.getFbaReplenishmentId(), (String) map.get("shipmentId"), (String) map.get("channelName"));
        }
        String fbaReplenishmentId = list.get(0).getFbaReplenishmentId();
        String shipmentId = (String) map.get("shipmentId");
        waybillInfoDao.updateFba(fbaReplenishmentId, shipmentId, vo.getOfferId());
        return "";
    }

    /**
     * 确定签收
     */
    @Override
    @Transactional(transactionManager = "retailReplenishmentTransactionManager")
    public Result confirmAcceptance(WaybillAcceptanceVO vo, String userId) {
        String waybillId = vo.getWaybillId();
        List<WaybillReplenishmentEntity> replenishmentList = waybillInfoDao.findReplenishmentList(waybillId);
        if (replenishmentList.size() == 1 && replenishmentList.get(0) == null) {
            //调拨单的签收
            String transferId = transferDao.findTransferIdByWaybillId(waybillId);
            Date signInDate = vo.getSignInDate();
            for (WaybillMskuNumVO waybillMskuNumVO : vo.getList()) {
                //更新调拨单装箱规格层的签收数据
                transferDao.updateSignInfoByUUid(waybillMskuNumVO.getUuid(), waybillMskuNumVO.getSignInQuantity());
            }
            for (WaybillMskuNumVO waybillMskuNumVO : vo.getList()) {
                String CommodityInfoCd = transferDao.getCommodityInfoCdByUUid(waybillMskuNumVO.getUuid());
                List<TransferOrderPackInfoEntity> packingList = transferDao.findPackInfoByCommodityId(CommodityInfoCd);
                int signNum = 0;
                for (TransferOrderPackInfoEntity transferOrderPackInfoEntity : packingList) {
                    signNum += transferOrderPackInfoEntity.getSignQuantity();
                }
                //更新调拨单商品层的签收数据
                transferDao.updateSignInfoByCommodityInfoCd(CommodityInfoCd, signNum);
            }
            //修改调拨单状态
            transferDao.sign(userId, transferId, signInDate, Time.getCurrentDateTime());

            //修改交运单的状态
            waybillInfoDao.updatesignInDate(vo.getWaybillId(), vo.getSignInDate());
            return Result.success();
        } else {
            //补货单的签收
            WaybillInfoEntity infoById = waybillInfoDao.findInfoById(waybillId);
            try {
                if (infoById.getLogisticsTypeCd() != 2) {
                    return Result.success(390, waybillId + "运单不是待签收状态，不能进行此操作", "");
                }
                if (replenishmentList != null && replenishmentList.size() > 0) {
                    List<WaybillMskuNumVO> list = vo.getList();

                    for (WaybillMskuNumVO mskuNumVO : list) {
                        fbaMskuPackInfoDao.updateSignInQuantity(mskuNumVO);
                        String replenishmentId = fbaMskuPackInfoDao.getReplenishmentId(mskuNumVO.getUuid());
                        List<WaybillMskuNumVO> detailVoList = fbaMskuPackInfoDao.getDetail(replenishmentId);
                        int signQuantity = 0;
                        for (WaybillMskuNumVO voList : detailVoList) {
                            signQuantity += voList.getSignInQuantity();
                        }
                        fbaMskuPackInfoDao.updateMskuSignInQuantity(signQuantity, replenishmentId);
                    }
                    waybillInfoDao.upWaybillStatus(waybillId, Time.getCurrentTimestamp(), userId, 3);
                    for (WaybillReplenishmentEntity ele : replenishmentList) {
                        fbaReplenishmentInfoDao.updateStatusById(4, ele.getFbaReplenishmentId());
                    }
                }
                waybillInfoDao.updatesignInDate(vo.getWaybillId(), vo.getSignInDate());
                return Result.success(200, "确定签收成功", "");
            } catch (Exception e) {
                return Result.success(390, "确定签收异常", "");
            }
        }

    }

    /**
     * 确定发货
     */
    @Override
    @Transactional(transactionManager = "retailReplenishmentTransactionManager")
    public Result confirmShipments(List<ReplenishShippingDataListVO> list, String waybillId, String userId) {
        List<WaybillReplenishmentEntity> replenishmentList = waybillInfoDao.findReplenishmentList(waybillId);
        if (replenishmentList.size() == 1 && replenishmentList.get(0) == null) {
            //调拨单确认发货
            String transferId = transferDao.findTransferIdByWaybillId(waybillId);
            for (ReplenishShippingDataListVO replenishShippingDataListVO : list) {
                int packingNumber = 0;
                int deliveryNumber = 0;
                String commodityInfoCd = transferDao.findCommodityInfoCdByCdAndSku(transferId, replenishShippingDataListVO.getSkuId());
                transferDao.deletePackInfoByCommodityId(commodityInfoCd);
                for (ReplenishShippingDataVO replenishShippingDataVO : replenishShippingDataListVO.getList()) {
                    TransferOrderPackInfoEntity transferOrderPackInfoEntity = new TransferOrderPackInfoEntity();
                    transferOrderPackInfoEntity.setUuid(Toolbox.randomUUID());
                    transferOrderPackInfoEntity.setCommodityInfoCd(commodityInfoCd);
                    transferOrderPackInfoEntity.setOuterBoxSpecificationHeight(replenishShippingDataVO.getOuterBoxSpecificationHeight());
                    transferOrderPackInfoEntity.setOuterBoxSpecificationLen(replenishShippingDataVO.getOuterBoxSpecificationLen());
                    transferOrderPackInfoEntity.setOuterBoxSpecificationWidth(replenishShippingDataVO.getOuterBoxSpecificationWidth());
                    transferOrderPackInfoEntity.setWeight(replenishShippingDataVO.getPackingWeight());
                    transferOrderPackInfoEntity.setPackingQuantity(replenishShippingDataVO.getPackingQuantity());
                    transferOrderPackInfoEntity.setNumberOfBoxes(replenishShippingDataVO.getNumberOfBoxes());
                    transferOrderPackInfoEntity.setDeleteStatus(0);
                    transferOrderPackInfoEntity.setDeliveryQuantity((int) replenishShippingDataVO.getDeliveryNumber());
                    transferDao.insertTransferPacking(transferOrderPackInfoEntity);
                    packingNumber += replenishShippingDataVO.getNumberOfBoxes() * replenishShippingDataVO.getPackingQuantity();
                    deliveryNumber += replenishShippingDataVO.getNumberOfBoxes() * replenishShippingDataVO.getPackingQuantity();
                }
                transferDao.upDeliveryInfo(transferId, replenishShippingDataListVO.getSkuId(), packingNumber, deliveryNumber);
            }
            /**
             * 调拨单确认发货
             */
            transferDao.delivery(transferId, userId, Time.getCurrentDateTime());

            waybillInfoDao.upWaybillStatus(waybillId, Time.getCurrentTimestamp(), userId, 2);

            return Result.success();

        } else {
            for (ReplenishShippingDataListVO ele : list) {
                String replenishmentCommodityId = ele.getReplenishmentCommodityId();
                replenishShippingDataDao.delete(replenishmentCommodityId);
                List<ReplenishShippingDataVO> voList = ele.getList();
                for (ReplenishShippingDataVO vo : voList) {
                    vo.setUuid(UUID.randomUUID().toString().replace("-", ""));
                    vo.setReplenishmentCommodityId(replenishmentCommodityId);
                    vo.setPackingNumber(vo.getNumberOfBoxes() * vo.getPackingQuantity());
                    vo.setDeliveryNumber(vo.getNumberOfBoxes() * vo.getPackingQuantity());
                    replenishShippingDataDao.insertReplenishmentMskuInfo(vo);
                }
                int deliveryNumber = 0;
                int packingNumber = 0;
                for (ReplenishShippingDataVO vo : voList) {
                    deliveryNumber += vo.getNumberOfBoxes() * vo.getPackingQuantity();
                    packingNumber += vo.getNumberOfBoxes() * vo.getPackingQuantity();
                }
                replenishShippingDataDao.updateReplenishmentDeliveryNumber(deliveryNumber, packingNumber, ele.getReplenishmentCommodityId());
                waybillInfoDao.updateReplenishment(replenishmentCommodityId);
            }
            WaybillInfoEntity infoById = waybillInfoDao.findInfoById(waybillId);
       /* if (infoById.getLogisticsTypeCd() != 1) {
            return Result.success(390, waybillId + "运单不是待发货状态，不能进行此操作", "");
        }*/
            if (replenishmentList != null && replenishmentList.size() > 0 && getWarehouseType(replenishmentList.get(0).getWarehouseId())) {
                waybillInfoDao.upWaybillStatus(waybillId, Time.getCurrentTimestamp(), userId, 2);
                for (WaybillReplenishmentEntity ele : replenishmentList) {
                    fbaReplenishmentInfoDao.updateStatusById(3, ele.getFbaReplenishmentId());
                }

                return Result.success(200, waybillId + "确定发货成功", "");
            } else {
                return Result.failure(390, waybillId + "订单仓库类型不支持确认发货，只支持虚拟仓和海外仓", "");
            }
        }
    }

    @Override
    public Result getException(String waybillId) {
        try {
            List<GetWaybillExceptionDto> result = new ArrayList<>();
            List<GetWaybillException> exceptions = waybillExceptionInfoDao.getException(waybillId);
            for (GetWaybillException getException : exceptions) {
                GetWaybillExceptionDto getWaybillException = new GetWaybillExceptionDto();
                BeanUtils.copyProperties(getException, getWaybillException);

                result.add(getWaybillException);
            }

            return Result.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }

    @Override
    public void updateRemark(String waybillId, String remark) {
        waybillInfoDao.updateRemark(waybillId, remark);
    }

    @Override
    public void waybillExcel(HttpServletResponse response, HttpServletRequest request, Integer startPage, Integer pageSize, Date waybillOrderDateBegin, Date waybillOrderDateEnd, String shipmentId,
                             Integer customsCd, String warehouseId, String employeeId, Integer logisticsTypeCd, String Keyword, String isLackLogistics, String isLackShipment, String isLackCustomsDeclare,
                             String isLackCustomsClearance, String waybillId, String batchNumber, String fbaReplenishmentId, String skuId, String productName) {
        try {
            List<LogisticWaybillExcelDto> result = new ArrayList<>();

            List mskuIds = new ArrayList();
            if (Keyword != null) {
                try {
                    mskuIds = mskuInfoListService.getMskuList(employeeId, Keyword, Keyword);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception("商品模块连接发生错误");
                }
            }

            // 根据物流商筛选渠道信息
            List offerIds = new ArrayList();
            if (shipmentId != null) {
                try {
                    Result shipmentResult = shipmentService.getOfferId(shipmentId, 1);
                    if (shipmentResult.getCode() != 200) {
                        throw new Exception(shipmentResult.getMsg());
                    }
                    List<Map> shipmentData = (List) shipmentResult.getData();

                    for (Map shipment : shipmentData) {
                        offerIds.add(shipment.get("offerId"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception("物流模块连接发生错误");
                }
            }

            WayBillInfoQuery wayBillInfoQuery = new WayBillInfoQuery();
            wayBillInfoQuery.setWaybillOrderDateBegin(waybillOrderDateBegin);
            wayBillInfoQuery.setWaybillOrderDateEnd(waybillOrderDateEnd);
            wayBillInfoQuery.setCustomsCd(customsCd);
            wayBillInfoQuery.setWarehouseId(warehouseId);
            wayBillInfoQuery.setMskuIds(mskuIds);
            wayBillInfoQuery.setLogisticsTypeCd(logisticsTypeCd);
            wayBillInfoQuery.setIsLackLogistics(isLackLogistics);
            wayBillInfoQuery.setIsLackShipment(isLackShipment);
            wayBillInfoQuery.setIsLackCustomsClearanc(isLackCustomsClearance);
            wayBillInfoQuery.setIsLackCustomsDeclare(isLackCustomsDeclare);
            wayBillInfoQuery.setWaybillId(waybillId);
            wayBillInfoQuery.setBatchNumber(batchNumber);
            wayBillInfoQuery.setFbaReplenishmentId(fbaReplenishmentId);
            wayBillInfoQuery.setOfferIds(offerIds);
            if (startPage != null && pageSize != null) {
                PageHelper.startPage(startPage, pageSize);
            }
            List<LogisticWaybillExcel> lweList = waybillInfoDao.waybillExcel(wayBillInfoQuery);

            List<String> offerIdList = new ArrayList<>();
            List waybillIds = new ArrayList();
            for (LogisticWaybillExcel ele : lweList) {
                offerIdList.add(ele.getOfferId());
                waybillIds.add(ele.getWaybillId());
            }

            // 关联fba数据
            List<String> commodityIds = new ArrayList();
            Map<String, List<ExcelMskuAllDto>> mskuMap = new HashMap();
            try {
                if (waybillIds.size() > 0) {
                    List<GetFbaByWaybill> fbaList = waybillReplenishmentRelDao.getFbaByWaybill(waybillIds);
                    for (GetFbaByWaybill fba : fbaList) {
                        commodityIds.add(fba.getCommodityId());

                        ExcelMskuAllDto mskuExcel = new ExcelMskuAllDto();
                        BeanUtils.copyProperties(fba, mskuExcel);
                        mskuExcel.setWarehouseDelivery(fba.getOuterBoxSpecificationLen() + "*" + fba.getOuterBoxSpecificationWidth() + "*" + fba.getOuterBoxSpecificationHeight() + "CM" + " " + fba.getPackingWeight() + "KG");
                        mskuExcel.setRelationId(fba.getBatchNumber());

                        if (mskuMap.get(fba.getWaybillId()) == null) {
                            mskuMap.put(fba.getWaybillId(), new ArrayList<>());
                        }
                        mskuMap.get(fba.getWaybillId()).add(mskuExcel);
                    }

                }
            } catch (BeansException e) {

            }

            // 关联商品信息
            Map<String, MskuInfoVO> mskuInfoMap = new HashMap<>();
            try {
                if (commodityIds.size() > 0) {
                    mskuInfoMap = mskuInfoListService.getMskuInfoList(commodityIds);
                }
            } catch (Exception e) {

            }
            for (String waybillIdKey : mskuMap.keySet()) {
                List<ExcelMskuAllDto> mskuExcels = mskuMap.get(waybillIdKey);
                for (ExcelMskuAllDto mskuExcel : mskuExcels) {
                    MskuInfoVO mskuInfo = mskuInfoMap.get(mskuExcel.getCommodityId());
                    BeanUtils.copyProperties(mskuInfo, mskuExcel);
                    mskuExcel.setEmployee(mskuInfo.getManager());
                    mskuExcel.setRelationPlace(mskuInfo.getShopName());
                    mskuExcel.setMskuId(mskuInfo.getMsku());
                }
            }


            Map<String, Map> shipmentList = new HashMap();
            try {
                //获取物流商报价信息集合
                shipmentList = replenishmentShipmentListService.getShipmentList(offerIdList);
            } catch (Exception e) {

            }

            List<LogisticWaybillAllDto> excelAllData = new ArrayList<>();
            List distinctId = new ArrayList();
            for (LogisticWaybillExcel ele : lweList) {
                if (distinctId.contains(ele.getWaybillId())) {
                    continue;
                } else {
                    distinctId.add(ele.getWaybillId());
                }
                LogisticWaybillAllDto logisticWaybill = new LogisticWaybillAllDto();
                BeanUtils.copyProperties(ele, logisticWaybill);
                if (shipmentList.get(logisticWaybill.getOfferId()) != null) {
                    Double unitAnnexCost = (logisticWaybill.getAnnexCost() - logisticWaybill.getOtherFee()) / logisticWaybill.getTotalWeight();
                    if (!unitAnnexCost.isInfinite() && !unitAnnexCost.isNaN()) {
                        unitAnnexCost = new BigDecimal(unitAnnexCost).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
                    }
                    logisticWaybill.setUnitAnnexCost(unitAnnexCost);
                    logisticWaybill.setTotalUnitPrice(logisticWaybill.getUnitPrice() + logisticWaybill.getUnitAnnexCost());
                    logisticWaybill.setEffectiveList(((List<String>) shipmentList.get(logisticWaybill.getOfferId()).get("effList")).get(0));
                    logisticWaybill.setChannelName(shipmentList.get(logisticWaybill.getOfferId()).get("shipmentName") + "-" + shipmentList.get(logisticWaybill.getOfferId()).get("channelName"));
                    logisticWaybill.setMskuList(mskuMap.get(logisticWaybill.getWaybillId()));
                } else {
                    logisticWaybill.setOfferId(null);
                }
                logisticWaybill.setMskuList(mskuMap.get(logisticWaybill.getWaybillId()));
                excelAllData.add(logisticWaybill);
            }

            for (LogisticWaybillAllDto excel : excelAllData) {
                LogisticWaybillExcelDto dto = new LogisticWaybillExcelDto();
                BeanUtils.copyProperties(excel, dto);
                List<LogisticExcelMskuDto> mskuExcelList = new ArrayList<>();
                if (excel.getMskuList() != null) {
                    for (ExcelMskuAllDto mskuDto : excel.getMskuList()) {
                        LogisticExcelMskuDto mskuExcel = new LogisticExcelMskuDto();
                        BeanUtils.copyProperties(mskuDto, mskuExcel);

                        mskuExcelList.add(mskuExcel);
                    }
                }

                dto.setMskuList(mskuExcelList);
                result.add(dto);
            }

            String fileName = "Matrix物流追踪表";
            CodeTemplateConfEntity temp = replenishmentCodeService.getCodeTemplateConfById("7427eaf2c1ee4f3ba252ebf38eccb96a");
            byte[] excelBytes = uploadDownloadService.downloadFile(temp.getObsName(), temp.getAddr());
            Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(excelBytes));
            Sheet sheet = workbook.getSheetAt(0);

            int sheetNo = 0;
            /**  循环创建下面行中的数据   */
            for (int i = 0; i < result.size(); i++) {
                /** 循环创建行 */
                Row row = sheet.createRow(sheetNo += 1);
                /** 获取集合中元素 */
                Object obj = result.get(i);
                /**
                 * 把obj对象中数据作为Excel中的一行数据,
                 * obj对象中的属性就是一行中的列
                 * 利用反射获取所有的Field
                 * */
                int mskuSize = 0;
                int mskuLength = 0;
                Field[] fields = obj.getClass().getDeclaredFields();
                /** 迭代Field */
                int j = 0;
                for (; j < fields.length; j++) {
                    /** 创建Cell */
                    Cell cell = row.createCell(j + mskuSize);
                    /** 设置Cell样式 */
                    CellStyle style = workbook.createCellStyle();
//                    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
//                    style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
                    cell.setCellStyle(style);
                    /** 获取该字段名称 */
                    String fieldName = fields[j].getName();
                    /** 转化成get方法 */
                    String getMethodName = "get" + StringUtils.capitalize(fieldName);
                    /** 获取方法 */
                    Method method = obj.getClass().getDeclaredMethod(getMethodName);
                    /** 调用get方法该字段的值 */
                    Object res = method.invoke(obj);

                    if (j == 3) {
                        List mskuList = (List) res;
                        mskuSize = 11 - 1;
                        if (mskuList == null) {
                            continue;
                        }
                        for (int n = 0; n < mskuList.size(); n++) {
                            Object msku = mskuList.get(n);
                            Row mskuRow = row;
                            if (n != 0) {
                                mskuRow = sheet.createRow(sheetNo);
                            }
                            Field[] mskuFields = msku.getClass().getDeclaredFields();
                            /** 迭代Field */
                            for (int k = 0; k < mskuFields.length; k++) {
                                /** 创建Cell */
                                Cell mskuCell = mskuRow.createCell(j + k);
                                /** 设置Cell样式 */
                                mskuCell.setCellStyle(style);
                                /** 获取该字段名称 */
                                String mskuName = mskuFields[k].getName();
                                /** 转化成get方法 */
                                String getMskuMethodName = "get" + StringUtils.capitalize(mskuName);
                                /** 获取方法 */
                                Method mskuMethod = msku.getClass().getDeclaredMethod(getMskuMethodName);
                                /** 调用get方法该字段的值 */
                                Object mskuRes = mskuMethod.invoke(msku);
                                /** 设置该Cell中的内容 */
                                mskuCell.setCellValue(mskuRes == null ? "" : mskuRes.toString());
                            }
                            if (n != mskuList.size() - 1) {
                                sheetNo += 1;
                            }
                            mskuLength = mskuList.size();
                        }
                    } else {
                        /** 设置该Cell中的内容 */
                        cell.setCellValue(res == null ? "" : res.toString());
                    }
                }
                if (mskuLength > 1) {
                    for (int a = 0; a < j + mskuSize; a++) {
                        if (a < 3 || a > 3 + mskuSize) {
                            sheet.addMergedRegion(new CellRangeAddress(sheetNo - mskuLength + 1, sheetNo, a, a));
                        }
                    }
                }
            }

            /** 获取当前浏览器 */
            String userAgent = request.getHeader("user-agent");
            /** 文件名是中文转码 */
            if (userAgent.toLowerCase().indexOf("msie") != -1) {
                fileName = URLDecoder.decode(fileName, "gbk"); // utf-8 --> gbk
                fileName = new String(fileName.getBytes("gbk"), "iso8859-1"); // gbk --> iso8859-1
            } else {
                fileName = new String(fileName.getBytes("utf-8"), "iso8859-1"); // utf-8 --> iso8859-1
            }
            /** 以流的形式下载文件 */
            response.setContentType("application/octet-stream");
            /** 告诉浏览器文件名 */
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
            /** 强制将缓冲区中的数据发送出去 */
            response.flushBuffer();
            /** 向浏览器输出Excel */
            workbook.write(response.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //根据补货单id获取发货仓类型是
    public boolean getWarehouseType(String warehouseId) {
        String typeCd = "";
        boolean b = false;
        try {
            Result result = warehouseInfoService.getWarehouseName(warehouseId);
            LinkedHashMap data = (LinkedHashMap) result.getData();
            if (data != null && !"".equals(data)) {
                typeCd = (String) data.get("typeCd");
            }
        } catch (Exception e) {
        }
        if ("B".equals(typeCd) || "C".equals(typeCd)) {
            b = true;
        }
        return b;
    }

}
