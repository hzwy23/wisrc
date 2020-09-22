package com.wisrc.purchase.webapp.AOP;

import com.google.gson.Gson;
import com.wisrc.purchase.webapp.service.externalService.ProductService;
import com.wisrc.purchase.webapp.service.externalService.SupplierService;
import com.wisrc.purchase.webapp.service.externalService.WmsService;
import com.wisrc.purchase.webapp.utils.Result;
import com.wisrc.purchase.webapp.vo.syncvo.ArrivalNoticeBillSyncVO;
import com.wisrc.purchase.webapp.vo.syncvo.GoodsInfoVO;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Aspect
@Component
public class ArrivalNoticeOrderSyncAOP {
    @Autowired
    private WmsService wmsService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private ProductService productService;

    @Autowired
    private Gson gson;

    @AfterReturning(value = "execution(public * com.wisrc.purchase.webapp.service.InspectionService.saveInspection(..))", returning = "result")
    public void aopAdd(Result result) {
        List skuIdList = new ArrayList();
        try {
            if (result.getCode() == 200) {
                ArrivalNoticeBillSyncVO wmsArrivalVO = (ArrivalNoticeBillSyncVO) result.getData();
                List<GoodsInfoVO> goodsList = wmsArrivalVO.getGoodsList();
                for (GoodsInfoVO vo : goodsList) {
                    skuIdList.add(vo.getGoodsCode());
                }
                Result resultSku = productService.getProductCN(skuIdList);
                Map skuMap = (Map) resultSku.getData();
                for (GoodsInfoVO vo : goodsList) {
                    vo.setGoodsName((String) skuMap.get(vo.getGoodsCode()));
                }
                Result resultMap = supplierService.getSupplierInfo(wmsArrivalVO.getSupplierCode());
                List<Object> supplierMap = (List<Object>) resultMap.getData();

                String supplierName = (String) ((Map) supplierMap.get(0)).get("supplierName");
                wmsArrivalVO.setSupplierName(supplierName);
                Result finalResult = wmsService.arrivalNoticeBillSync(gson.toJson(wmsArrivalVO));//到货通知单同步完成
                if (finalResult.getCode() != 0) {
                    throw new Exception(finalResult.getMsg());
                }
            }
        } catch (Exception e) {
            result.setMsg(result.getMsg() + "," + e.getMessage());
        }
    }
}
