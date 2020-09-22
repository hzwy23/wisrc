package com.wisrc.replenishment.webapp.aop;

import com.google.gson.Gson;
import com.wisrc.replenishment.webapp.dao.FbaReplenishmentInfoDao;
import com.wisrc.replenishment.webapp.dao.WaybillInfoDao;
import com.wisrc.replenishment.webapp.entity.FbaReplenishmentInfoEntity;
import com.wisrc.replenishment.webapp.entity.ImproveLogisticsInfoEntity;
import com.wisrc.replenishment.webapp.entity.TransferOrderPackInfoEntity;
import com.wisrc.replenishment.webapp.entity.WaybillRelEntity;
import com.wisrc.replenishment.webapp.service.CompanyService;
import com.wisrc.replenishment.webapp.service.TransferService;
import com.wisrc.replenishment.webapp.service.WaybillInfoService;
import com.wisrc.replenishment.webapp.service.WmsService;
import com.wisrc.replenishment.webapp.service.externalService.BasicService;
import com.wisrc.replenishment.webapp.service.externalService.WarehouseService;
import com.wisrc.replenishment.webapp.utils.Result;
import com.wisrc.replenishment.webapp.vo.transferorder.TransferInfoReturnVo;
import com.wisrc.replenishment.webapp.vo.transferorder.TransferOrderDetailsVo;
import com.wisrc.replenishment.webapp.vo.transferorder.TransferWaybillAddVo;
import com.wisrc.replenishment.webapp.vo.waybill.AddWaybillVO;
import com.wisrc.replenishment.webapp.vo.waybill.WaybillDetailsVO;
import com.wisrc.replenishment.webapp.vo.wms.LogisticsDeclareDocSyncVO;
import com.wisrc.replenishment.webapp.vo.wms.TransferDocSyncVO;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Aspect
@Component
public class DeclareDocSyncAop {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private FbaReplenishmentInfoDao infoDao;
    @Autowired
    private WaybillInfoDao waybillInfoDao;
    @Autowired
    private WmsService wmsService;
    @Autowired
    private WaybillInfoService waybillInfoService;
    @Autowired
    private BasicService basicService;
    @Autowired
    private TransferService transferService;
    @Autowired
    private WarehouseService warehouseService;

    @Value("${customsInfoUrl}")
    private String customsInfoUrl;
    @Value("${clearanceInvoiceUrl}")
    private String clearanceInvoiceUrl;
    @Value("${logisticsInfoUrl}")
    private String logisticsInfoUrl;
    @Value("${boxDetailUrl}")
    private String boxDetailUrl;

    @AfterReturning(value = "execution(public * com.wisrc.replenishment.webapp.service.WaybillInfoService.addWaybillInfo(..))", returning = "ret")
    public void declareDocSync(/*ProceedingJoinPoint pj,*/Result ret) {
        Gson gson = new Gson();
        if (ret != null && ret.getCode() == 200) {

            Map map = (Map) ret.getData();
            String wayBillId = (String) map.get("waybillId");
            AddWaybillVO vo = (AddWaybillVO) map.get("arguments");
            List<WaybillRelEntity> list = vo.getWaybillRelEntityList();
            //String fbaReplenishmentId = list.get(0).getFbaReplenishmentId();
            try {

                WaybillDetailsVO waybillInfo = waybillInfoService.findDetailsById(wayBillId);
                //物流商编号
                String shipmentId = waybillInfo.getInfoEn().getShipmentId();
                Result shipmentResult = basicService.shipmentById(shipmentId);
                int shipmentType = 0;
                if (shipmentResult.getCode() == 200) {
                    Map data = (Map) shipmentResult.getData();
                    shipmentType = (int) data.get("shipmentType");
                }
                //报关类型
                int customsTypeCd = waybillInfo.getInfoEn().getCustomsTypeCd();

                for (WaybillRelEntity relEntity : list) {
                    LogisticsDeclareDocSyncVO syncVO = new LogisticsDeclareDocSyncVO();
                    String fbaReplenishmentId = relEntity.getFbaReplenishmentId();
                    FbaReplenishmentInfoEntity fbaReplenishmentInfoEntity = infoDao.findById(fbaReplenishmentId);
                    if (fbaReplenishmentInfoEntity != null && fbaReplenishmentInfoEntity.getShipmentId() != null) {
                        Result warehouseResult = companyService.getShipmentName(fbaReplenishmentInfoEntity.getShipmentId());
                        Map shipmentMap = (Map) warehouseResult.getData();
                        String shipmentName = (String) shipmentMap.get("shipmentName");
                        syncVO.setShipmentName(shipmentName);
                    }
                    if (!(customsTypeCd == 3)) {
                        syncVO.setCustomsInfoUrl(customsInfoUrl + wayBillId);
                    }
                    if (shipmentType != 0 && shipmentType == 1) {
                        syncVO.setClearanceInvoiceUrl(clearanceInvoiceUrl + wayBillId);
                        syncVO.setLogisticsInfoUrl(logisticsInfoUrl + "15789e0f29144e06bfd2720b01eee4e3.png");
                    }
                    syncVO.setBoxDetailUrl(boxDetailUrl + "9f50dc1c4c28492d97ca9c2a2db294cd.png");
                    syncVO.setVoucherCode(fbaReplenishmentId);
                    Result result = wmsService.logisticsDeclareSync(gson.toJson(syncVO));
                    if (result.getCode() != 0) {
                        result.setCode(423);
                        result.setMsg("报关资料同步失败：" + result.getMsg());
                        ret.setMsg(ret.getMsg() + result.getMsg());
                    }
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                ret.setMsg(ret.getMsg() + throwable.getMessage());
            }
        }
    }

    @After(value = "execution(public * com.wisrc.replenishment.webapp.service.impl.ImproveLogisticsInfoServiceImpl.ImproveLogisticsInfo(..))")
    public void syncLogisticsInfoUrl(JoinPoint joinPoint) {
        Gson gson = new Gson();
        ImproveLogisticsInfoEntity improveLogisticsInfoEntity = (ImproveLogisticsInfoEntity) joinPoint.getArgs()[0];
        List<String> fbaIdByWayBillId = waybillInfoDao.findFbaIdByWayBillId(improveLogisticsInfoEntity.getWaybillId());
        if (fbaIdByWayBillId != null && fbaIdByWayBillId.size() > 0) {
            if (StringUtils.isNotEmpty(improveLogisticsInfoEntity.getLogisticsSurfaceUrl())) {
                for (String fbaId : fbaIdByWayBillId) {
                    LogisticsDeclareDocSyncVO logisticsDeclareDocSyncVO = new LogisticsDeclareDocSyncVO();
                    logisticsDeclareDocSyncVO.setVoucherCode(fbaId);
                    logisticsDeclareDocSyncVO.setLogisticsInfoUrl(logisticsInfoUrl + improveLogisticsInfoEntity.getLogisticsSurfaceUrl());
                    try {
                        wmsService.logisticsDeclareSync(gson.toJson(logisticsDeclareDocSyncVO));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            String transferOrderCd = transferService.getTransferInfo(improveLogisticsInfoEntity.getWaybillId());
            TransferDocSyncVO syncVO = new TransferDocSyncVO();
            syncVO.setVoucherCode(transferOrderCd);
            syncVO.setLogisticsInfoUrl(logisticsInfoUrl + improveLogisticsInfoEntity.getLogisticsSurfaceUrl());
            syncVO.setSoNo(improveLogisticsInfoEntity.getSoNoUrl());
            try {
                wmsService.transferDocSync(gson.toJson(syncVO));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //调拨单同步wms
    @AfterReturning(value = "execution(public * com.wisrc.replenishment.webapp.service.TransferService.confirmShipment(..))", returning = "ret")
    public void declareTransferDocSync(Result ret) {
        Gson gson = new Gson();
        if (ret != null && ret.getCode() == 200) {

            Map map = (Map) ret.getData();
            String wayBillId = (String) map.get("waybillId");
            TransferWaybillAddVo vo = (TransferWaybillAddVo) map.get("arguments");
            //List<WaybillRelEntity> list = vo.getWaybillRelEntityList();
            //String fbaReplenishmentId = list.get(0).getFbaReplenishmentId();
            try {
                String transferOrderCd = vo.getTransferOrderCd();
                TransferDocSyncVO syncVO = new TransferDocSyncVO();
                TransferInfoReturnVo infoVo = new TransferInfoReturnVo();
                WaybillDetailsVO waybillInfo = waybillInfoService.findDetailsById(wayBillId);
                Result entityResult = transferService.findById(transferOrderCd);
                if (entityResult.getCode() == 200) {
                    infoVo = (TransferInfoReturnVo) entityResult.getData();
                }
                int numbox = 0;
                for (TransferOrderDetailsVo detailsVo : infoVo.getTransferOrderDetailsVos()) {
                    for (TransferOrderPackInfoEntity packEntity : detailsVo.getPackInfoEntities()) {
                        numbox += packEntity.getNumberOfBoxes();
                    }
                }
                syncVO.setPackageTotal(numbox);
                //仓库信息
                Result warehouseData = warehouseService.getWarehouseById(infoVo.getTransferBasicInfoEntity().getWarehouseStartId());
                if (warehouseData.getCode() == 200) {
                    Map warehouseMap = (Map) warehouseData.getData();
                    syncVO.setWhTown((String) warehouseMap.get("detailsAddr"));
                    syncVO.setWhProvince((String) warehouseMap.get("provinceName"));
                    syncVO.setWhCity((String) warehouseMap.get("cityName"));
                    syncVO.setWhZipCode((String) warehouseMap.get("zipCode"));
                }
                Result warehouseEndData = warehouseService.getWarehouseById(infoVo.getTransferBasicInfoEntity().getWarehouseEndId());
                if (warehouseEndData.getCode() == 200) {
                    Map warehouseEndMap = (Map) warehouseEndData.getData();
                    String warehouseType = (String) warehouseEndMap.get("typeCd");
                    if (warehouseType.equals("B")) {
                        syncVO.setTargetWhName("海外仓");
                    } else if (warehouseType.equals("A")) {
                        syncVO.setTargetWhName(infoVo.getTransferBasicInfoEntity().getWarehouseEndId());
                    } else if (warehouseType.equals("C")) {
                        syncVO.setTargetWhName("虚拟仓");
                    }

                }
                //物流商编号
                String shipmentId = waybillInfo.getInfoEn().getShipmentId();
                Result shipmentResult = basicService.shipmentById(shipmentId);
                int shipmentType = 0;
                if (shipmentResult.getCode() == 200) {
                    Map data = (Map) shipmentResult.getData();
                    shipmentType = (int) data.get("shipmentType");
                }
                //报关类型
                int customsTypeCd = waybillInfo.getInfoEn().getCustomsTypeCd();


                syncVO.setVoucherCode(transferOrderCd);
                //FbaReplenishmentInfoEntity fbaReplenishmentInfoEntity = infoDao.findById(fbaReplenishmentId);
                if (infoVo.getTransferBasicInfoEntity() != null && infoVo.getTransferBasicInfoEntity().getShipmentId() != null) {
                    Result warehouseResult = companyService.getShipmentName(infoVo.getTransferBasicInfoEntity().getShipmentId());
                    Map shipmentMap = (Map) warehouseResult.getData();
                    String shipmentName = (String) shipmentMap.get("shipmentName");
                    syncVO.setLogisticsName(shipmentName);
                }
                if (!(customsTypeCd == 3)) {
                    syncVO.setCustomsInfoUrl(customsInfoUrl + wayBillId);
                }
                if (shipmentType != 0 && shipmentType == 1) {
                    //syncVO.setClearanceInvoiceUrl(clearanceInvoiceUrl + wayBillId);
                    syncVO.setLogisticsInfoUrl(logisticsInfoUrl + "15789e0f29144e06bfd2720b01eee4e3.png");
                }
                Result result = wmsService.transferDocSync(gson.toJson(syncVO));
                if (result.getCode() != 0) {
                    result.setCode(423);
                    result.setMsg("报关资料同步失败：" + result.getMsg());
                    ret.setMsg(ret.getMsg() + result.getMsg());
                }

            } catch (Throwable throwable) {
                throwable.printStackTrace();
                ret.setMsg(ret.getMsg() + throwable.getMessage());
            }
        }
    }

}
