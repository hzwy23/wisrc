package com.wisrc.purchase.webapp.AOP;

import com.google.gson.Gson;
import com.wisrc.purchase.webapp.service.externalService.SkuService;
import com.wisrc.purchase.webapp.service.externalService.SupplierService;
import com.wisrc.purchase.webapp.service.externalService.WmsService;
import com.wisrc.purchase.webapp.utils.Result;
import com.wisrc.purchase.webapp.utils.Time;
import com.wisrc.purchase.webapp.vo.purchaseRejection.add.AddPurchaseRejectionDetailsVO;
import com.wisrc.purchase.webapp.vo.purchaseRejection.add.AddPurchaseRejectionInfoVO;
import com.wisrc.purchase.webapp.vo.purchaseRejection.add.AddPurchaseRejectionVO;
import com.wisrc.purchase.webapp.vo.syncvo.GoodsInfoVO;
import com.wisrc.purchase.webapp.vo.syncvo.RejectBillSyncVO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Aspect
@Component
public class PurchaseRejectionBillSyncAOP {
    private static Map<String, String> supplierIdName = null;
    private static Map<String, String> skuIdName = null;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private SkuService skuService;
    @Autowired
    private WmsService wmsService;
    @Autowired
    private Gson gson;

    private void initData() {
        supplierIdName = new LinkedHashMap<>();
        skuIdName = new LinkedHashMap<>();
        Result supplierInfo = supplierService.getSupplierInfo(null);
        Result productSkuInfo = skuService.getProductSkuInfo(null, null, null);
        List<Object> supplierDatas = (List<Object>) supplierInfo.getData();
        List<Object> productDatas = (List<Object>) ((Map) productSkuInfo.getData()).get("productData");
        for (Object productData : productDatas) {
            Map data = (Map) productData;
            skuIdName.put((String) data.get("sku"), (String) data.get("skuNameZh"));
        }
        for (Object supplierData : supplierDatas) {
            Map data = (Map) supplierData;
            supplierIdName.put((String) data.get("supplierId"), (String) data.get("supplierName"));
        }
    }

    @AfterReturning(value = "execution(public * com.wisrc.purchase.webapp.service.impl.PurchaseRejectionImplService.add(..))", returning = "result")
    public void purchaseRejectionBillSync(JoinPoint joinPoint, Result result) {
        if (supplierIdName == null || skuIdName == null) {
            initData();
        }
        Object[] args = joinPoint.getArgs();
        //拒收单信息
        AddPurchaseRejectionVO addPurchaseRejectionVOS = (AddPurchaseRejectionVO) args[0];
        //拒收单基本信息
        AddPurchaseRejectionInfoVO purchaseRejectionInfo = addPurchaseRejectionVOS.getPurchaseRejectionInfo();
        //拒收产品信息
        List<AddPurchaseRejectionDetailsVO> purchaseRejectionDetailsList = addPurchaseRejectionVOS.getPurchaseRejectionDetailsList();
        //拒收单回写信息对象
        RejectBillSyncVO rejectBillSyncVO = new RejectBillSyncVO();
        List<GoodsInfoVO> goodsInfoVOList = new ArrayList<>();

        try {
            if (result.getCode() == 200) {
                //TODO 准备拒收单基本数据
                rejectBillSyncVO.setVoucherCode((String) result.getData());//拒收单号
                rejectBillSyncVO.setVoucherType("CGG");
                rejectBillSyncVO.setCreateTime(Time.getCurrentDateTime());
                rejectBillSyncVO.setPreDeliveryVocuherCode(purchaseRejectionInfo.getOrderId());
                rejectBillSyncVO.setSupplierCode(purchaseRejectionInfo.getSupplierCd());
                rejectBillSyncVO.setSupplierName(supplierIdName.get(purchaseRejectionInfo.getSupplierCd()));
                rejectBillSyncVO.setRemark(purchaseRejectionInfo.getRemark());

                //TODO 准备拒收详细货物信息
                int i = 1;
                for (AddPurchaseRejectionDetailsVO addPurchaseRejectionDetailsVO : purchaseRejectionDetailsList) {
                    GoodsInfoVO goodsInfoVO = new GoodsInfoVO();
                    goodsInfoVO.setLineNum(i);
                    goodsInfoVO.setGoodsCode(addPurchaseRejectionDetailsVO.getSkuId());
                    goodsInfoVO.setGoodsName(skuIdName.get(addPurchaseRejectionDetailsVO.getSkuId()));
                    goodsInfoVO.setPackageQuantity(0);
                    if (addPurchaseRejectionDetailsVO.getRejectQuantity() == null) {
                        addPurchaseRejectionDetailsVO.setRejectQuantity(0);
                    }
                    if (addPurchaseRejectionDetailsVO.getSpareQuantity() == null) {
                        addPurchaseRejectionDetailsVO.setSpareQuantity(0);
                    }
                    goodsInfoVO.setUnitQuantity(addPurchaseRejectionDetailsVO.getSpareQuantity() + addPurchaseRejectionDetailsVO.getRejectQuantity());
                    goodsInfoVO.setTotalQuantity(addPurchaseRejectionDetailsVO.getSpareQuantity() + addPurchaseRejectionDetailsVO.getRejectQuantity());
                    goodsInfoVOList.add(goodsInfoVO);
                    i++;
                }
                //TODO 同步信息到wms
                rejectBillSyncVO.setGoodsList(goodsInfoVOList);
                Result result1 = wmsService.rejectBillSync(gson.toJson(rejectBillSyncVO));
                if (result1.getCode() != 0) {
                    throw new RuntimeException("同步wms失败：" + result1.getMsg());
                }
            }
        } catch (Exception e) {
            result.setMsg(result.getMsg() + "," + e.getMessage());
        }
    }
}
