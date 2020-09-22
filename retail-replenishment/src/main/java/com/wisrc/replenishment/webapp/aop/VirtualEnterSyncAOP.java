package com.wisrc.replenishment.webapp.aop;

import com.google.gson.Gson;
import com.wisrc.replenishment.webapp.dao.TransferDao;
import com.wisrc.replenishment.webapp.entity.TransferBasicInfoEntity;
import com.wisrc.replenishment.webapp.entity.TransferOrderDetailsEntity;
import com.wisrc.replenishment.webapp.service.externalService.ProductService;
import com.wisrc.replenishment.webapp.service.externalService.WmsService;
import com.wisrc.replenishment.webapp.utils.Result;
import com.wisrc.replenishment.webapp.vo.transferOut.TransferVirtualSyncVO;
import com.wisrc.replenishment.webapp.vo.transferOut.TransferVirvualDetailSyncVO;
import com.wisrc.replenishment.webapp.vo.waybill.WaybillAcceptanceVO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Aspect
@Component
public class VirtualEnterSyncAOP {

    @Autowired
    private TransferDao transferDao;

    @Autowired
    private WmsService wmsService;

    @Autowired
    private ProductService productService;

    @Autowired
    private Gson gson;

    @AfterReturning(value = "execution(public * com.wisrc.replenishment.webapp.service.WaybillInfoService.confirmAcceptance(..))", returning = "result")
    public void transferVirtualSync(JoinPoint joinPoint, Result result) {
        //调拨单虚拟入库同步
        try {
            if (result.getCode() == 200) {
                WaybillAcceptanceVO waybillAcceptanceVO = (WaybillAcceptanceVO) joinPoint.getArgs()[0];
                String wayBillId = waybillAcceptanceVO.getWaybillId();
                TransferBasicInfoEntity basicInfoEntity = transferDao.getTransferBasicInfoByWayBillId(wayBillId);
                if (basicInfoEntity != null) {
                    if (basicInfoEntity.getWarehouseEndId().startsWith("B") || basicInfoEntity.getWarehouseEndId().startsWith("C")) {
                        //写入基础信息
                        TransferVirtualSyncVO basicInfoSyncVO = new TransferVirtualSyncVO();
                        basicInfoSyncVO.setVoucherCode(basicInfoEntity.getTransferOrderCd());
                        basicInfoSyncVO.setVoucherType("DB");
                        basicInfoSyncVO.setSectionCode(basicInfoEntity.getSubWarehouseEndId());
                        basicInfoSyncVO.setRemark(basicInfoEntity.getRemark());

                        List<TransferOrderDetailsEntity> detailsListEntity = transferDao.findTransferDetailsById(basicInfoEntity.getTransferOrderCd());

                        //得到产品名称
                        String[] skuIds = new String[detailsListEntity.size()];
                        for (int i = 0; i < detailsListEntity.size(); i++) {
                            skuIds[i] = detailsListEntity.get(i).getSkuId();
                        }
                        Map<String, String> productMap = new LinkedHashMap<>();
                        Result productResult = productService.getProductInfoById(gson.toJson(skuIds));
                        if (productResult.getCode() == 200) {
                            List productList = (ArrayList) (productResult.getData());
                            for (Object entity : productList) {
                                productMap.put((String) ((Map) entity).get("skuId"), (String) ((Map) entity).get("skuNameZh"));
                            }
                        }

                        int i = 0;
                        List<TransferVirvualDetailSyncVO> productSyncListVO = new LinkedList<>();
                        for (TransferOrderDetailsEntity detailsEntity : detailsListEntity) {
                            //写入产品信息
                            TransferVirvualDetailSyncVO detailSyncVO = new TransferVirvualDetailSyncVO();
                            detailSyncVO.setLineNum(i++ + "");
                            detailSyncVO.setGoodsCode(detailsEntity.getSkuId());
                            detailSyncVO.setFnCode(detailsEntity.getFnSku());
                            detailSyncVO.setGoodsName(productMap.get(detailsEntity.getSkuId()));
                            detailSyncVO.setUnitQuantity(detailsEntity.getSignInQuantity() + "");
                            detailSyncVO.setPackageQuantity("0");
                            detailSyncVO.setTotalQuantity(detailsEntity.getSignInQuantity() + "");
                            productSyncListVO.add(detailSyncVO);
                        }
                        basicInfoSyncVO.setGoodsList(productSyncListVO);
                        Result wmsResult = wmsService.transferVirtualEnterSync(gson.toJson(basicInfoSyncVO));
                        if (wmsResult.getCode() != 0) {
                            result.setMsg("调拨单虚拟入库同步错误");
                            result.setData(wmsResult.getMsg());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg("调拨单虚拟入库方法错误");
            result.setData(e.getMessage());
        }
    }
}
