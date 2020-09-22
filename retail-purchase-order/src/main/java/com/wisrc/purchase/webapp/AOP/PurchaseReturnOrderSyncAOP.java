package com.wisrc.purchase.webapp.AOP;

import com.google.gson.Gson;
import com.wisrc.purchase.webapp.service.PurchaseReturnService;
import com.wisrc.purchase.webapp.service.externalService.ProductService;
import com.wisrc.purchase.webapp.service.externalService.SupplierService;
import com.wisrc.purchase.webapp.service.externalService.WmsService;
import com.wisrc.purchase.webapp.utils.Result;
import com.wisrc.purchase.webapp.utils.Time;
import com.wisrc.purchase.webapp.vo.purchaseReturn.show.PurchaseReturnDetailsVO;
import com.wisrc.purchase.webapp.vo.purchaseReturn.show.PurchaseReturnInfoVO;
import com.wisrc.purchase.webapp.vo.syncvo.GoodsInfoVO;
import com.wisrc.purchase.webapp.vo.syncvo.PurchaseRefundBillSyncVO;
import com.wisrc.purchase.webapp.vo.syncvo.ReturnVirtualOutDetailsVO;
import com.wisrc.purchase.webapp.vo.syncvo.ReturnVirtualOutSyncVO;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Aspect
@Component
public class PurchaseReturnOrderSyncAOP {
    @Autowired
    private WmsService wmsService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private ProductService productService;
    @Autowired
    private PurchaseReturnService purchaseReturnService;

    @Autowired
    private Gson gson;

    // 拦截新增
    @AfterReturning(value = "execution(public * com.wisrc.purchase.webapp.service.PurchaseReturnService.add(..))", returning = "result")
    public void aopAdd(Result result) {
        List skuIdList = new ArrayList();
        try {
            if (result.getCode() == 200) {
                PurchaseRefundBillSyncVO wmsReturnVO = (PurchaseRefundBillSyncVO) result.getData();
                //需求新增：MAT-1500 采购退货单退本地仓的时候还是调原本的接口，退虚拟仓额时候调虚拟出库接口
                if (wmsReturnVO.getSectionCode().startsWith("A")) {
                    List<GoodsInfoVO> goodsList = wmsReturnVO.getGoodsList();
                    for (GoodsInfoVO vo : goodsList) {
                        skuIdList.add(vo.getGoodsCode());
                    }
                    Result resultSku = productService.getProductCN(skuIdList);
                    if (resultSku.getCode() != 200) {
                        throw new RuntimeException("调用外部产品服务出错:" + resultSku.getMsg());
                    }
                    Map skuMap = (Map) resultSku.getData();
                    for (GoodsInfoVO vo : goodsList) {
                        vo.setGoodsName((String) skuMap.get(vo.getGoodsCode()));
                    }
                    Result resultMap = supplierService.getSupplierInfo(wmsReturnVO.getSupplierCode());
                    if (resultMap.getCode() != 200) {
                        throw new RuntimeException("调用外部供应商服务出错:" + resultMap.getMsg());
                    }
                    Map supplierMap = (Map) (((List<Object>) resultMap.getData()).get(0));
                    String supplierName = (String) supplierMap.get("supplierName");

                    wmsReturnVO.setSupplierName(supplierName);
                    Result finalResult = wmsService.purchaseReturnOrderSync(gson.toJson(wmsReturnVO));
                    if (finalResult.getCode() != 0) {
                        throw new RuntimeException("同步wms失败：" + finalResult.getMsg());
                    }
                    //如果是虚拟仓(没有海外仓)
                } else if (wmsReturnVO.getSectionCode().startsWith("C")) {
                    //得到产品信息
                    List<GoodsInfoVO> goodsList = wmsReturnVO.getGoodsList();
                    for (GoodsInfoVO vo : goodsList) {
                        skuIdList.add(vo.getGoodsCode());
                    }
                    Result resultSku = productService.getProductCN(skuIdList);
                    if (resultSku.getCode() != 200) {
                        throw new RuntimeException("虚拟单调用外部产品服务出错:" + resultSku.getMsg());
                    }
                    Map skuMap = (Map) resultSku.getData();

                    //写入基础信息
                    ReturnVirtualOutSyncVO basicSyncVO = new ReturnVirtualOutSyncVO();
                    basicSyncVO.setVoucherCode(wmsReturnVO.getVoucherCode());
                    basicSyncVO.setVoucherType("SGC");
                    basicSyncVO.setSectionCode(wmsReturnVO.getSectionCode());
                    basicSyncVO.setCreateTime(wmsReturnVO.getCreateTime());
                    basicSyncVO.setRemark(wmsReturnVO.getRemark());

                    //写入产品信息
                    List<ReturnVirtualOutDetailsVO> productListSyncVO = new LinkedList<>();
                    for (GoodsInfoVO productEntity : wmsReturnVO.getGoodsList()) {
                        ReturnVirtualOutDetailsVO productSyncVO = new ReturnVirtualOutDetailsVO();
                        productSyncVO.setGoodsCode(productEntity.getGoodsCode());
                        productSyncVO.setLineNum(productEntity.getLineNum() + "");
                        productSyncVO.setGoodsName((String) skuMap.get(productSyncVO.getGoodsCode()));
                        productSyncVO.setUnitQuantity(productEntity.getUnitQuantity() + "");
                        productSyncVO.setPackageQuantity("0");
                        productSyncVO.setTotalQuantity(productEntity.getTotalQuantity() + "");
                        productListSyncVO.add(productSyncVO);
                    }
                    basicSyncVO.setGoodsList(productListSyncVO);

                    Result virtualOutResult = wmsService.returnVirtualOutSync(gson.toJson(basicSyncVO));

                    if (virtualOutResult.getCode() != 0) {
                        throw new RuntimeException("虚拟单同步wms失败：" + virtualOutResult.getMsg());
                    } else {
                        //改变单据状态变为已完成
                        purchaseReturnService.changeStatus(2, wmsReturnVO.getVoucherCode());

                        //如果这个产品有包材的话把这个包材虚拟入库
                        Result returnResult = purchaseReturnService.findByReturnBill(wmsReturnVO.getVoucherCode());
                        if (returnResult.getCode() == 200) {
                            //这个虚拟入库的字段和虚拟出库的字段是一样的，这里就没有新建的VO，直接用虚拟出库的VO,但是这个地方实际是包材虚拟入库操作
                            ReturnVirtualOutSyncVO packageVirtualEnterVO = new ReturnVirtualOutSyncVO();
                            packageVirtualEnterVO.setVoucherCode(wmsReturnVO.getVoucherCode());
                            packageVirtualEnterVO.setVoucherType("CGT");

                            packageVirtualEnterVO.setCreateTime(Time.getCurrentDateTime());
                            PurchaseReturnInfoVO purchaseReturnInfo = (PurchaseReturnInfoVO) ((Map) returnResult.getData()).get("purchaseReturnInfo");

                            packageVirtualEnterVO.setSectionCode(purchaseReturnInfo.getWarehouseId());
                            List<PurchaseReturnDetailsVO> returnProducts = (List<PurchaseReturnDetailsVO>) ((Map) returnResult.getData()).get("purchaseRejectionDetailsList");

                            int i = 1;
                            List<ReturnVirtualOutDetailsVO> goodsInfoVOS = new LinkedList<>();
                            for (PurchaseReturnDetailsVO returnProduct : returnProducts) {
                                Result skuPackingInfo = productService.getProductPackingInfo(returnProduct.getSkuId());
                                if ((int) ((Map) skuPackingInfo.getData()).get("packingFlag") == 1) {
                                    Map skuPackingDetails = (Map) skuPackingInfo.getData();
                                    List<Object> packingSkuList = (List<Object>) skuPackingDetails.get("packingMaterialList");
                                    for (Object o : packingSkuList) {
                                        Map map = (Map) o;
                                        ReturnVirtualOutDetailsVO goodsInfoVO = new ReturnVirtualOutDetailsVO();
                                        goodsInfoVO.setLineNum(i++ + "");
                                        goodsInfoVO.setGoodsCode((String) map.get("dependencySkuId"));
                                        goodsInfoVO.setGoodsName((String) map.get("skuNameZh"));
                                        String quantity = String.valueOf((returnProduct.getReturnQuantity() + returnProduct.getSpareQuantity()) * (int) map.get("quantity"));
                                        goodsInfoVO.setUnitQuantity(quantity);
                                        goodsInfoVO.setPackageQuantity("0");
                                        goodsInfoVO.setTotalQuantity(quantity);
                                        goodsInfoVOS.add(goodsInfoVO);
                                    }
                                }
                                i++;
                            }
                            packageVirtualEnterVO.setGoodsList(goodsInfoVOS);
                            if (packageVirtualEnterVO.getGoodsList().size() > 0) {
                                Result virtualEnterResult = wmsService.virtualEnterBillSync(gson.toJson(packageVirtualEnterVO));
                                if (virtualEnterResult.getCode() != 0) {
                                    result.setMsg("包材虚拟入库同步失败");
                                    result.setData(virtualEnterResult.getMsg());
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg(result.getMsg() + "," + e.getMessage());
        }
    }
}

