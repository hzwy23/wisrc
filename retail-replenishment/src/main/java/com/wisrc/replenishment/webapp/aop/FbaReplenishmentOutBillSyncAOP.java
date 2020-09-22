package com.wisrc.replenishment.webapp.aop;

import com.google.gson.Gson;
import com.wisrc.replenishment.webapp.dao.FbaReplenishmentInfoDao;
import com.wisrc.replenishment.webapp.entity.FbaReplenishmentInfoEntity;
import com.wisrc.replenishment.webapp.service.FbaReplenishmentInfoService;
import com.wisrc.replenishment.webapp.service.WmsService;
import com.wisrc.replenishment.webapp.service.externalService.ProductService;
import com.wisrc.replenishment.webapp.service.externalService.WarehouseService;
import com.wisrc.replenishment.webapp.utils.Result;
import com.wisrc.replenishment.webapp.utils.Time;
import com.wisrc.replenishment.webapp.vo.FbaAmazonVO;
import com.wisrc.replenishment.webapp.vo.FbaMskuInfoVO;
import com.wisrc.replenishment.webapp.vo.FbaReplenishmentInfoVO;
import com.wisrc.replenishment.webapp.vo.wms.*;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Aspect
@Component
public class FbaReplenishmentOutBillSyncAOP {
    @Autowired
    private WmsService wmsService;
    @Autowired
    private FbaReplenishmentInfoDao fbaReplenishmentInfoDao;

    @Autowired
    private FbaReplenishmentInfoService fbaReplenishmentInfoService;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private ProductService productService;
    @Value("${boxInfoUrl}")
    private String boxInfoUrl;

    @Around(value = "execution(public * com.wisrc.replenishment.webapp.service.FbaReplenishmentInfoService.updateAmazonInfo(..))")
    public Result addFba(ProceedingJoinPoint joinPoint) {

        FbaAmazonVO fbaAmazonVO = (FbaAmazonVO) joinPoint.getArgs()[0];
        FbaReplenishmentInfoEntity fbaReplenishmentInfoEntity = fbaReplenishmentInfoDao.getBatchNumberByReplenishmentId(fbaAmazonVO.getFbaReplenishmentId());
        Gson gson = new Gson();
        if (fbaReplenishmentInfoEntity.getBatchNumber() == null && fbaReplenishmentInfoEntity.getWarehouseId().startsWith("A")) {
            Result obj = null;
            if (fbaReplenishmentInfoEntity.getDeliveringTypeCd() == 1) {//普通发货
                try {
                    obj = (Result) joinPoint.proceed();
                } catch (Throwable throwable) {
                    throw new RuntimeException("执行出错");
                }
                if (obj != null && obj.getCode() == 200) {
                    FbaReplenishmentOutBillSyncVO entity = (FbaReplenishmentOutBillSyncVO) obj.getData();
                    wmsService.syncFbaReplenishBill(gson.toJson(entity));
                    if (StringUtils.isNotEmpty(fbaAmazonVO.getPacklistFile())) {//如果同步亚马逊货件信息的时候传了装箱信息则同步到wms
                        LogisticsDeclareDocSyncVO logisticsDeclareDocSyncVO = new LogisticsDeclareDocSyncVO();
                        logisticsDeclareDocSyncVO.setVoucherCode(entity.getVoucherCode());
                        logisticsDeclareDocSyncVO.setBoxDetailUrl(boxInfoUrl + fbaAmazonVO.getPacklistFile());
                        wmsService.logisticsDeclareSync(gson.toJson(logisticsDeclareDocSyncVO));
                    }
                    return Result.success();
                } else {
                    return Result.failure();
                }

            } else {//组装发货
                //准备加工任务单的数据
                AddProcessTaskBillVO addProcessTaskBillVO = new AddProcessTaskBillVO();

                //先取出改fba补货单的详细信息
                FbaReplenishmentInfoVO byId = fbaReplenishmentInfoService.findById(fbaAmazonVO.getFbaReplenishmentId());
                for (FbaMskuInfoVO mskuInfo : byId.getMskuInfoList()) {
                    //判断当前商品是否需要加工
                    List<Object> con = new ArrayList();
                    Map<String, String> entity = new HashMap<>();
                    entity.put("skuId", mskuInfo.getMskuInfoVO().getStoreSku());
                    entity.put("warehouseId", byId.getInfoEntity().getWarehouseId());
                    con.add(entity);
                    int enableStockNum = 0;
                    Result stockResult = warehouseService.getStockBySkuIdAndWarehouseId(gson.toJson(con));
                    if (stockResult.getCode() == 200) {
                        if (((List<Object>) stockResult.getData()).size() > 0) {
                            Map data = (Map) ((List<Object>) stockResult.getData()).get(0);
                            enableStockNum = (int) data.get("enableStockNum");
                        }
                    }
                    int processNum = 0;
                    processNum = mskuInfo.getReplenishmentQuantity() - enableStockNum;
                    if (processNum > 0) {
                        //加工任务单的基本数据
                        ProcessTaskBillEntity processTaskBillEntity = new ProcessTaskBillEntity();
                        //TODO 加工任务单的基本信息
                        processTaskBillEntity.setFbaReplenishmentId(fbaAmazonVO.getFbaReplenishmentId());
                        processTaskBillEntity.setStatusCd(1);
                        processTaskBillEntity.setProcessDate(Time.getCurrentDateTime());
                        processTaskBillEntity.setProcessLaterSku(mskuInfo.getMskuInfoVO().getStoreSku());
                        processTaskBillEntity.setProcessNum(processNum);
                        processTaskBillEntity.setWarehouseId(byId.getInfoEntity().getWarehouseId());
                        processTaskBillEntity.setBatch("");
                        processTaskBillEntity.setRemark("系统自动生成");
                        processTaskBillEntity.setCreateUser("admin");
                        //TODO 准备该sku需要的加工原料信息
                        //加工任务单的sku详情
                        List<ProcessDetailEntity> processDetailEntities = new ArrayList<>();
                        //获取该sku的加工原料信息
                        Result skuMachineInfo = productService.getSkuMachineInfo(mskuInfo.getMskuInfoVO().getStoreSku());
                        List<Object> machineInfoList = (List<Object>) skuMachineInfo.getData();
                        for (Object o : machineInfoList) {
                            ProcessDetailEntity processDetailEntity = new ProcessDetailEntity();
                            Map data = (Map) o;
                            processDetailEntity.setSkuId((String) data.get("dependencySkuId"));
                            processDetailEntity.setUnitNum((Integer) data.get("quantity"));
                            processDetailEntity.setTotalAmount(((Integer) data.get("quantity")) * processNum);
                            processDetailEntity.setWarehouseId(byId.getInfoEntity().getWarehouseId());
                            processDetailEntity.setBatch("");
                            processDetailEntities.add(processDetailEntity);
                        }
                        addProcessTaskBillVO.setEntity(processTaskBillEntity);
                        addProcessTaskBillVO.setEntityList(processDetailEntities);
                        warehouseService.addProcessBill(gson.toJson(addProcessTaskBillVO));
                    }

                }
                try {
                    joinPoint.proceed();
                    return Result.success();
                } catch (Throwable throwable) {
                    throw new RuntimeException("执行失败");
                }
            }
        } else {
            try {
                Result obj = (Result) joinPoint.proceed();
                if (obj != null && obj.getCode() == 200) {
                    if (StringUtils.isNotEmpty(fbaAmazonVO.getPacklistFile())) {//如果同步亚马逊货件信息的时候传了装箱信息则同步到wms
                        FbaReplenishmentOutBillSyncVO entity = (FbaReplenishmentOutBillSyncVO) obj.getData();
                        LogisticsDeclareDocSyncVO logisticsDeclareDocSyncVO = new LogisticsDeclareDocSyncVO();
                        logisticsDeclareDocSyncVO.setVoucherCode(entity.getVoucherCode());
                        logisticsDeclareDocSyncVO.setBoxDetailUrl(boxInfoUrl + fbaAmazonVO.getPacklistFile());
                        wmsService.logisticsDeclareSync(gson.toJson(logisticsDeclareDocSyncVO));
                    }
                }
                return Result.success();
            } catch (Throwable throwable) {
                throw new RuntimeException("执行失败");
            }
        }
    }
}

