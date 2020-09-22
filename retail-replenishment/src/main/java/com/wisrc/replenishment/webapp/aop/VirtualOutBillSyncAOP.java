package com.wisrc.replenishment.webapp.aop;

import com.google.gson.Gson;
import com.wisrc.replenishment.webapp.dao.FbaMskuPackInfoDao;
import com.wisrc.replenishment.webapp.dao.FbaReplenishmentInfoDao;
import com.wisrc.replenishment.webapp.dao.TransferDao;
import com.wisrc.replenishment.webapp.entity.TransferBasicInfoEntity;
import com.wisrc.replenishment.webapp.service.ProductService;
import com.wisrc.replenishment.webapp.service.externalService.WmsService;
import com.wisrc.replenishment.webapp.utils.Result;
import com.wisrc.replenishment.webapp.vo.OverseaSendVO;
import com.wisrc.replenishment.webapp.vo.ReplenishShippingDataListVO;
import com.wisrc.replenishment.webapp.vo.virtualBill.OutVirtualBillDetailsVO;
import com.wisrc.replenishment.webapp.vo.virtualBill.OutVirtualBillVO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Aspect
@Component
public class VirtualOutBillSyncAOP {

    @Autowired
    private WmsService wmsService;

    @Autowired
    private Gson gson;

    @Autowired
    private ProductService productService;

    @Autowired
    private FbaReplenishmentInfoDao fbaReplenishmentInfoDao;

    @Autowired
    private FbaMskuPackInfoDao fbaMskuPackInfoDao;

    @Autowired
    private TransferDao transferDao;

    @AfterReturning(value = "execution(public * com.wisrc.replenishment.webapp.service.WaybillInfoService.confirmShipments(..))", returning = "result")
    public void syncMethod(JoinPoint jp, Result result) {
        try {
            if (result.getCode() == 200) {
                Object args[] = jp.getArgs();
                List<ReplenishShippingDataListVO> argVO = (List<ReplenishShippingDataListVO>) args[0];
                String wayBillId = (String) args[1];
                TransferBasicInfoEntity basicInfoEntity = transferDao.getTransferBasicInfoByWayBillId(wayBillId);
                //if(basicInfoEntity == null) {
                int i = 1;
                String id = null;
                for (ReplenishShippingDataListVO detailVO : argVO) {
                    for (OverseaSendVO sendVO : detailVO.getSendVOList()) {
                        OutVirtualBillVO resultVO = new OutVirtualBillVO();
                        String remark = fbaReplenishmentInfoDao.getRemarkById(detailVO.getReplenishmentCommodityId());
                        String fbaReplenishmentId = fbaMskuPackInfoDao.getFbaIdByUuid(detailVO.getReplenishmentCommodityId());
                        if (fbaReplenishmentId == null) {
                            resultVO.setVoucherType("DB");
                        } else {
                            resultVO.setVoucherType("FBH");
                        }
                        if (fbaReplenishmentId == null) {
                            fbaReplenishmentId = basicInfoEntity.getTransferOrderCd();
                        }

                        if (i < 10) {
                            id = fbaReplenishmentId + "-0" + i;
                        } else {
                            id = fbaReplenishmentId + "-" + i;
                        }

                        resultVO.setVoucherCode(id);
                        resultVO.setSectionCode(sendVO.getWarehouseId());
                        resultVO.setRemark(remark);

                        OutVirtualBillDetailsVO resultDetailVO = new OutVirtualBillDetailsVO();

                        String productName = null;
                        Result productResult = productService.getProdDetails(sendVO.getSkuId());
                        if (productResult.getCode() == 200) {
                            productName = (String) ((Map) ((Map) productResult.getData()).get("define")).get("skuNameZh");
                        }

                        resultDetailVO.setLineNum(i + "");
                        resultDetailVO.setGoodsCode(sendVO.getSkuId());
                        if (fbaReplenishmentId.startsWith("P")) {
                            //如果是补货单
                            if (sendVO.getWarehouseId().startsWith("B")) {
                                //如果起运仓是海外仓
                                resultDetailVO.setFnCode(sendVO.getFnSkuId());
                            }
                        } else {
                            //如果是调拨单
                            resultDetailVO.setFnCode(sendVO.getFnSkuId());
                        }
                        if (fbaReplenishmentId.substring(0, 1).equals("P")) {
                            resultDetailVO.setProductionBatchNo(sendVO.getBatch());
                        }
                        resultDetailVO.setGoodsName(productName);
                        resultDetailVO.setUnitQuantity(sendVO.getSendNumber() + "");
                        resultDetailVO.setPackageQuantity(0 + "");
                        resultDetailVO.setTotalQuantity(sendVO.getSendNumber() + "");

                        i++;

                        List<OutVirtualBillDetailsVO> resultDetailList = new LinkedList<>();
                        resultDetailList.add(resultDetailVO);
                        resultVO.setGoodsList(resultDetailList);

                        Result result1 = wmsService.virtualOutSync(gson.toJson(resultVO));
                        System.out.println(result1);
                    }
                }
                 /*}
               else {
                    //调拨单虚拟出库
                    if(basicInfoEntity.getWarehouseStartId().startsWith("B") || basicInfoEntity.getWarehouseStartId().startsWith("C")) {
                        //写入基础信息
                        TransferVirtualSyncVO basicInfoSyncVO = new TransferVirtualSyncVO();
                        basicInfoSyncVO.setVoucherCode(basicInfoEntity.getTransferOrderCd());
                        basicInfoSyncVO.setVoucherType("DB");
                        basicInfoSyncVO.setSectionCode(basicInfoEntity.getWarehouseStartId());
                        basicInfoSyncVO.setRemark(basicInfoEntity.getRemark());

                        List<TransferOrderDetailsEntity> detailsListEntity = transferDao.findTransferDetailsById(basicInfoEntity.getTransferOrderCd());

                        //得到产品名称
                        String[] skuIds = new String[detailsListEntity.size()];
                        for (int i = 0; i < detailsListEntity.size(); i++) {
                            skuIds[i] = detailsListEntity.get(i).getSkuId();
                        }
                        Map<String, String> productMap = new LinkedHashMap<>();
                        Result productResult = productService.getProductInfoById(gson.toJson(skuIds));
                        System.out.println(productResult);
                        if (productResult.getCode() == 200) {
                            List productList = (ArrayList)(productResult.getData());
                            for (Object entity : productList) {
                                productMap.put((String) ((Map) entity).get("skuId"), (String) ((Map) entity).get("skuNameZh"));
                            }
                        }

                        int i = 1;
                        List<TransferVirvualDetailSyncVO> productSyncListVO = new LinkedList<>();
                        for(TransferOrderDetailsEntity detailsEntity : detailsListEntity) {
                            //写入产品信息
                            TransferVirvualDetailSyncVO detailSyncVO = new TransferVirvualDetailSyncVO();
                            detailSyncVO.setLineNum(i + "");
                            i++;
                            detailSyncVO.setGoodsCode(detailsEntity.getSkuId());
                            detailSyncVO.setFnCode(detailsEntity.getFnSku());
                            detailSyncVO.setGoodsName(productMap.get(detailsEntity.getSkuId()));
                            detailSyncVO.setUnitQuantity(detailsEntity.getDeliveryQuantity()+"");
                            detailSyncVO.setPackageQuantity("0");
                            detailSyncVO.setTotalQuantity(detailsEntity.getDeliveryQuantity()+"");
                            productSyncListVO.add(detailSyncVO);
                        }
                        basicInfoSyncVO.setGoodsList(productSyncListVO);
                        Result wmsResult = wmsService.virtualOutSync(gson.toJson(basicInfoSyncVO));
                        System.out.println(wmsResult);
                        if(wmsResult.getCode() != 0) {
                            result.setMsg("调拨单虚拟出库同步错误");
                            result.setData(wmsResult.getMsg());
                        }
                    }
                }*/
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg("同步失败");
            result.setData(e.getMessage());
        }
    }
}
