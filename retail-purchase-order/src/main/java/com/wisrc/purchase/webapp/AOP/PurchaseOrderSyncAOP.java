package com.wisrc.purchase.webapp.AOP;

import com.google.gson.Gson;
import com.wisrc.purchase.webapp.service.externalService.ProductSkuService;
import com.wisrc.purchase.webapp.service.externalService.SupplierService;
import com.wisrc.purchase.webapp.service.externalService.WmsService;
import com.wisrc.purchase.webapp.utils.Result;
import com.wisrc.purchase.webapp.vo.AddDetailsProdictAllVO;
import com.wisrc.purchase.webapp.vo.OrderBasisInfoVO;
import com.wisrc.purchase.webapp.vo.syncvo.GoodsInfoVO;
import com.wisrc.purchase.webapp.vo.syncvo.PurchaseOrderVO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Aspect
@Component
public class PurchaseOrderSyncAOP {

    @Autowired
    private WmsService wmsService;
    @Autowired
    private ProductSkuService skuService;

    @Autowired
    private SupplierService supplierService;

    private Map<String, String> supplierIdName;


    @AfterReturning(returning = "obj", pointcut = "execution(public * com.wisrc.purchase.webapp.service.impl.PurchcaseOrderBasisInfoServiceImpl.addOrderInfo(..)) || execution(public * com.wisrc.purchase.webapp.service.impl.PurchcaseOrderBasisInfoServiceImpl.updateOrder(..))")
    public void purchaseOrderSync(JoinPoint joinPoint, Result obj) {
        //获取传入参数
        Object[] args = joinPoint.getArgs();
        //订单附加信息
        List<AddDetailsProdictAllVO> addDetailsProdictAllVOS = (List<AddDetailsProdictAllVO>) args[0];
        //订单基本信息
        OrderBasisInfoVO basisInfo = (OrderBasisInfoVO) args[1];
        //传给WMS的同步采购订单数据
        PurchaseOrderVO purchaseOrderVO = new PurchaseOrderVO();
        List<GoodsInfoVO> goodsList = new ArrayList<>();
        try {
            if (obj.getCode() == 200) {
                //同步过去的采购订单的商品信息
                Map<String, List> map = new HashMap();
                List skuIdList = new ArrayList();
                for (AddDetailsProdictAllVO vo : addDetailsProdictAllVOS) {
                    skuIdList.add(vo.getSkuId());
                }
                map.put("skuIdList", skuIdList);
                Gson gson = new Gson();
                Result result = skuService.getProductInfo(gson.toJson(map));
                if (result.getCode() != 200) {
                    throw new RuntimeException("调用外部产品接口出错");
                }
                List<Object> objects = (List<Object>) result.getData();
                Map resultMap = (Map) objects.get(0);
                //Map skuInfoMap = (Map) resultMap.get("define");
                Map declareInfoMap = (Map) resultMap.get("declareInfo");
                StringBuilder remark = new StringBuilder();
                int i = 1;
                for (AddDetailsProdictAllVO vo : addDetailsProdictAllVOS) {
                    GoodsInfoVO goods = new GoodsInfoVO();
                    goods.setLineNum(i);
                    goods.setGoodsCode(vo.getSkuId());
                    goods.setGoodsName(vo.getSkuName());
                    goods.setProductModel((String) declareInfoMap.get("model"));
                    goods.setUnitQuantity(Math.floor((100 + vo.getSpareRate()) / 100 * vo.getQuantity()));
                    goods.setPackageQuantity(0);
                    goods.setTotalQuantity(Math.floor((100 + vo.getSpareRate()) / 100 * vo.getQuantity()));
                    goods.setUnit((String) declareInfoMap.get("issuingOffice"));
                    goods.setPackageCapacity(vo.getProducPackingInfoEn().getPackingRate());
                    goods.setGrossWeight(vo.getProducPackingInfoEn().getGrossWeight());
                    goods.setLength(vo.getProducPackingInfoEn().getPackLong());
                    goods.setWidth(vo.getProducPackingInfoEn().getPackWide());
                    goods.setHeight(vo.getProducPackingInfoEn().getPackHigh());
                    goods.setReceiptRate(vo.getSpareRate());
                    goodsList.add(goods);
                    remark.append(vo.getSkuName() + ":" + vo.getQuantity() + "," + vo.getSpareRate() + "%" + "  ");
                    i++;
                }
                //同步过去的采购订单基本信息

                purchaseOrderVO.setVoucherCode(basisInfo.getOrderId());
                purchaseOrderVO.setVoucherType("CG");
                purchaseOrderVO.setCreateTime(basisInfo.getCreateTime().toString());
                purchaseOrderVO.setSupplierCode(basisInfo.getSupplierId());
                Result supplierInfo = supplierService.getSupplierInfo(basisInfo.getSupplierId());
                if (supplierInfo.getCode() != 200) {
                    throw new RuntimeException("调用rep供应商信息接口错误");
                }
                purchaseOrderVO.setSupplierName((String) ((Map) ((List<Object>) (supplierInfo.getData())).get(0)).get("supplierName"));
                purchaseOrderVO.setRemark(remark.toString());
                purchaseOrderVO.setGoodsList(goodsList);
                Result resultWms = wmsService.purchaseOrderSync(gson.toJson(purchaseOrderVO));
                if (resultWms.getCode() != 0) {
                    throw new RuntimeException(resultWms.getMsg());
                }
            }
        } catch (Exception e) {
            obj.setMsg(obj.getMsg() + "," + e.getMessage());
        }

    }
}
