package com.wisrc.warehouse.webapp.AOP;

import com.google.gson.Gson;
import com.wisrc.warehouse.webapp.service.externalService.ExternalProductService;
import com.wisrc.warehouse.webapp.service.externalService.WmsService;
import com.wisrc.warehouse.webapp.vo.AddProcessTaskBillVO;
import com.wisrc.warehouse.webapp.vo.reportLossStatement.add.AddProductInfoVO;
import com.wisrc.warehouse.webapp.vo.reportLossStatement.add.AddReportLossStatementVO;
import com.wisrc.warehouse.webapp.vo.syncVO.GoodsInfoVO;
import com.wisrc.warehouse.webapp.vo.syncVO.SaleOutBilSyncVO;
import com.wisrc.warehouse.webapp.vo.syncVO.ScrapBillSyncVO;
import com.wisrc.warehouse.webapp.vo.syncVO.WarehouseVo;
import com.wisrc.warehouse.webapp.entity.ProcessDetailEntity;
import com.wisrc.warehouse.webapp.entity.ProcessTaskBillEntity;
import com.wisrc.warehouse.webapp.entity.WarehouseManageInfoEntity;
import com.wisrc.warehouse.webapp.entity.WarehouseSeparateDetailsInfoEntity;
import com.wisrc.warehouse.webapp.utils.Result;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
@Aspect
public class WarehouseAddSyncAOP {
    private static Map<String, String> skuIdNam = null;
    @Autowired
    Gson gson;
    @Autowired
    private WmsService wmsService;
    @Autowired
    private ExternalProductService productService;

    private void initSkuDate() {
        skuIdNam = new LinkedHashMap<>();
        Result productSkuInfo = productService.getProductSkuInfo(null, null, null);
        if (productSkuInfo.getCode() != 200) {
            throw new RuntimeException("aop中初始化菜品数据失败");
        }
        List<Object> productList = (List<Object>) ((Map) productSkuInfo.getData()).get("productData");
        for (Object o : productList) {
            Map productData = (Map) o;
            skuIdNam.put((String) productData.get("sku"), (String) productData.get("skuNameZh"));
        }
    }

    /**
     * 新建仓库信息同步
     *
     * @param obj
     */
    @AfterReturning(returning = "obj", pointcut = "execution(public * com.wisrc.warehouse.webapp.service.impl.WarehouseManageInfoServiceImpl.add(..)) || execution(public * com.wisrc.warehouse.webapp.service.impl.WarehouseManageInfoServiceImpl.changeName(..))")
    public void warehouseInfoSync(Object obj) {
        try {
            //
            String whCode = null;
            String whName = null;
            String sectionCode = null;
            String sectionName = null;

            List<WarehouseVo> warehouseVos = new ArrayList<>();
            Result result = (Result) obj;
            if (result.getCode() == 200) {
                WarehouseManageInfoEntity warehouseManageInfoEntity = (WarehouseManageInfoEntity) result.getData();
                //主仓信息
                if (warehouseManageInfoEntity.getTypeCd().equals("B") || warehouseManageInfoEntity.getTypeCd().equals("C") || warehouseManageInfoEntity.getTypeCd().equals("F")) {
                    whCode = warehouseManageInfoEntity.getWarehouseId();
                    whName = warehouseManageInfoEntity.getWarehouseName();
                    List<WarehouseSeparateDetailsInfoEntity> subWarehouseList = warehouseManageInfoEntity.getSubWarehouseList();
                    if (warehouseManageInfoEntity.getSubWarehouseSupport() == 1) {
                        for (WarehouseSeparateDetailsInfoEntity subWarehouse : subWarehouseList) {//分仓信息
                            sectionCode = subWarehouse.getSubWarehouseId();
                            sectionName = subWarehouse.getSeparateWarehouseName();
                            WarehouseVo warehouseVo = new WarehouseVo();
                            warehouseVo.setWhCode(whCode);
                            warehouseVo.setWhName(whName);
                            warehouseVo.setSectionCode(sectionCode);
                            warehouseVo.setSectionName(sectionName);
                            warehouseVos.add(warehouseVo);
                        }
                    } else {
                        WarehouseVo warehouseVo = new WarehouseVo();
                        warehouseVo.setWhCode(whCode);
                        warehouseVo.setWhName(whName);
                        warehouseVo.setSectionCode(whCode);
                        warehouseVo.setSectionName(whName);
                        warehouseVos.add(warehouseVo);
                    }
                }
                //同步数据到WMS
                wmsService.warehouseSync(warehouseVos);
            }
        } catch (Exception e) {
            return;
        }
    }


    /**
     * 报损单同步
     *
     * @param joinPoint
     * @return
     */
    @AfterReturning(value = "execution(public * com.wisrc.warehouse.webapp.service.impl.ReportLossStatementImplService.insert(..))", returning = "result")
    public void scrapBillSync(JoinPoint joinPoint, Result result) {
        if (skuIdNam == null) {
            initSkuDate();
        }
        Object[] args = joinPoint.getArgs();
        AddReportLossStatementVO addReportLossStatementVO = (AddReportLossStatementVO) args[0];
        //同步过去的数据
        ScrapBillSyncVO scrapBillSyncVO = new ScrapBillSyncVO();

        List<GoodsInfoVO> goodsInfoVOList = new ArrayList<>();
        try {
            if (result.getCode() == 200) {
                scrapBillSyncVO.setVoucherCode((String) result.getData());
                scrapBillSyncVO.setSectionCode(addReportLossStatementVO.getWarehouseId());
                scrapBillSyncVO.setVoucherType("BS");
                scrapBillSyncVO.setRemark(addReportLossStatementVO.getReportLossReason());
                int i = 1;
                for (AddProductInfoVO addProductInfoVO : addReportLossStatementVO.getProductInfoList()) {
                    GoodsInfoVO goodsInfoVO = new GoodsInfoVO();
                    if (addReportLossStatementVO.getLabelFlag() == 1) {
                        goodsInfoVO.setGoodsCode(addProductInfoVO.getFnSku());
                    } else {
                        goodsInfoVO.setGoodsCode(addProductInfoVO.getSkuId());
                    }
                    goodsInfoVO.setGoodsName(skuIdNam.get(addProductInfoVO.getSkuId()));
                    goodsInfoVO.setUnitQuantity(addProductInfoVO.getReportedLossAmount());
                    goodsInfoVO.setTotalQuantity(addProductInfoVO.getReportedLossAmount());
                    goodsInfoVO.setPackageQuantity(0);
                    goodsInfoVO.setLineNum(i);
                    goodsInfoVOList.add(goodsInfoVO);
                    i++;
                }
                scrapBillSyncVO.setGoodsList(goodsInfoVOList);
                Result result1 = wmsService.scrapSync(gson.toJson(scrapBillSyncVO));
                if (result1.getCode() != 0) {
                    throw new RuntimeException("wms同步失败" + "," + result.getMsg());
                }
                result.setMsg(result.getMsg() + "  wms同步成功");
            }
        } catch (Throwable throwable) {
            result.setMsg(throwable.getMessage());
        }
    }

    /**
     * 销售出库单同步
     *
     * @param obj
     */
    @AfterReturning(value = "execution(public * com.wisrc.warehouse.webapp.service.impl.ProcessTaskBillServiceImpl.add(..))", returning = "obj")
    public void saleOutBillSync(Result obj) {
        //同步到wms的数据
        SaleOutBilSyncVO saleOutBilSyncVO = new SaleOutBilSyncVO();
        List<GoodsInfoVO> goodsInfoVOList = new ArrayList<>();
        try {
            if (skuIdNam == null) {
                initSkuDate();
            }
            if (obj.getCode() == 200) {
                //原本的加工任务单汇总信息
                AddProcessTaskBillVO addProcessTaskBillVO = (AddProcessTaskBillVO) obj.getData();
                //加工任务单的基本信息
                ProcessTaskBillEntity entity = addProcessTaskBillVO.getEntity();
                //加工任务单的明细信息
                List<ProcessDetailEntity> processDetailEntities = addProcessTaskBillVO.getEntityList();
                //TODO 准备传回wms的基本数据
                saleOutBilSyncVO.setVoucherCode(entity.getProcessTaskId());
                saleOutBilSyncVO.setVoucherType("JG");
                saleOutBilSyncVO.setSectionCode(entity.getWarehouseId());//仓库编号
                saleOutBilSyncVO.setRemark(entity.getRemark());
                //TODO 准备传回wms的详细物料数据
                int i = 1;
                for (ProcessDetailEntity processDetailEntity : processDetailEntities) {
                    GoodsInfoVO goodsInfoVO = new GoodsInfoVO();
                    goodsInfoVO.setLineNum(i);
                    goodsInfoVO.setGoodsCode(processDetailEntity.getSkuId());
                    goodsInfoVO.setGoodsName(skuIdNam.get(processDetailEntity.getSkuId()));
                    goodsInfoVO.setUnitQuantity(processDetailEntity.getTotalAmount());
                    goodsInfoVO.setPackageQuantity(0);
                    goodsInfoVO.setTotalQuantity(processDetailEntity.getTotalAmount());
                    goodsInfoVOList.add(goodsInfoVO);
                    i++;
                }
                saleOutBilSyncVO.setGoodsList(goodsInfoVOList);
                Result result = wmsService.saleOutBillSync(gson.toJson(saleOutBilSyncVO));
                if (result.getCode() != 0) {
                    throw new RuntimeException("同步错误：" + result.getMsg());
                }
                obj.setMsg(obj.getMsg() + "," + result.getMsg());
            }
        } catch (Exception e) {
            obj.setMsg(obj.getMsg() + "," + e.getMessage());
        }
    }
}
