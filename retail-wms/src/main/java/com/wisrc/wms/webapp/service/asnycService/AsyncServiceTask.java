package com.wisrc.wms.webapp.service.asnycService;

import com.google.gson.Gson;
import com.wisrc.wms.webapp.controller.WmsBillSyncController;
import com.wisrc.wms.webapp.service.externalService.FbaReplenishmentService;
import com.wisrc.wms.webapp.service.externalService.ProductService;
import com.wisrc.wms.webapp.service.externalService.PurchaseOrderService;
import com.wisrc.wms.webapp.utils.LocalStockUtil;
import com.wisrc.wms.webapp.utils.Result;
import com.wisrc.wms.webapp.utils.Time;
import com.wisrc.wms.webapp.utils.UUIDutil;
import com.wisrc.wms.webapp.vo.BillSyncVO.VirtualEnterBillSyncVO;
import com.wisrc.wms.webapp.vo.GoodsInfoVO;
import com.wisrc.wms.webapp.vo.ReturnVO.PurchaseRefundBillReturnVO;
import com.wisrc.wms.webapp.vo.ReturnVO.SkuPutawayReturnVO;
import com.wisrc.wms.webapp.vo.SkuInfoVO;
import com.wisrc.wms.webapp.vo.StockQueryVO;
import com.wisrc.wms.webapp.vo.outsitVO.EntryWarehouseAllVO;
import com.wisrc.wms.webapp.vo.outsitVO.EntryWarehouseProductVO;
import com.wisrc.wms.webapp.vo.outsitVO.EntryWarehouseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class AsyncServiceTask {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private FbaReplenishmentService fbaReplenishmentService;

    @Autowired
    private WmsBillSyncController wmsBillSyncController;

    @Autowired
    private Gson gson;

    @Autowired
    private LocalStockUtil localStockUtil;

    @Autowired
    private ProductService productService;

    /**
     * 商品上架异步执行方法
     *
     * @param skuPutawayReturnVO
     */
    @Async
    public void skuPutawayAsync(SkuPutawayReturnVO skuPutawayReturnVO) {
        String entryId = getBillId("wmsEntryId");
        List<EntryWarehouseProductVO> entryWarehouseProductVOS = new ArrayList<>();
        //采购入库单完整信息
        EntryWarehouseAllVO entryWarehouseAllVO = new EntryWarehouseAllVO();
        //采购入库单的基本信息
        EntryWarehouseVO entryWarehouseVO = new EntryWarehouseVO();
        entryWarehouseVO.setEntryId(entryId);
        try {
            //TODO 根据到货通知单id去查询到货通知的基本信息
            Result arrivalInfo = purchaseOrderService.getArrivalInfoById(skuPutawayReturnVO.getArrivalId());
            //到货通知单数据
            Map arrivalData = (Map) arrivalInfo.getData();
            List<Object> inspectionList = (List<Object>) arrivalData.get("inspectionProduct");//该到货通知单的所有的sku信息
            //TODO 准备采购入库订单的基本数据
            entryWarehouseVO.setEntryTime(Time.getCurrentDateTime());
            entryWarehouseVO.setEntryUser((String) ((Map) arrivalData.get("employee")).get("id"));
            entryWarehouseVO.setWarehouseId(skuPutawayReturnVO.getWarehouseId());
            entryWarehouseVO.setInspectionId(skuPutawayReturnVO.getArrivalId());//到货通知单号
            entryWarehouseVO.setSupplierCd((String) ((Map) arrivalData.get("supplier")).get("id"));
            entryWarehouseVO.setSupplierDeliveryNum("");//供应商送货单号
            entryWarehouseVO.setOrderId((String) arrivalData.get("orderId"));
            entryWarehouseVO.setPackWarehouseId((String) arrivalData.get("packWarehouseId"));
            entryWarehouseVO.setRemark("wms回写自动生成");

            //TODO 根据到货通知单号去查询所有的采购入库单号
            List<String> entryIds = null;
            Result allEntryIdByInspectionId = purchaseOrderService.findAllEntryIdByInspectionId(skuPutawayReturnVO.getArrivalId());
            if (allEntryIdByInspectionId.getCode() == 200) {
                entryIds = (List<String>) allEntryIdByInspectionId.getData();
            }
            List<Object> entryBillList = new ArrayList<>();
            //根据所有的采购入库单号去查询出所有的采购入库单明细
            if (entryIds.size() > 0) {
                Result entryProductByEntryIds = purchaseOrderService.getEntryProductByEntryIds(entryIds);
                entryBillList = (List<Object>) entryProductByEntryIds.getData();
            }

            //将基本信息加入插入数据对象中
            entryWarehouseAllVO.setEntryWarehouseVO(entryWarehouseVO);

            //TODO 添加入库商品明细
            for (SkuInfoVO skuInfoVO : skuPutawayReturnVO.getArrivalSkuList()) {
                EntryWarehouseProductVO productVO = new EntryWarehouseProductVO();
                productVO.setUuid(UUIDutil.randomUUID());
                productVO.setEntryId(entryId);
                productVO.setSkuId(skuInfoVO.getSkuId());
                productVO.setBatch(skuInfoVO.getBatch());

                //TODO 根据采购订单去获取订单商品信息
                Result orderSkuListResult = purchaseOrderService.getSkuListInfoByOrderId((String) arrivalData.get("orderId"));
                if (orderSkuListResult.getCode() == 200) {
                    List<Object> orderListObject = (List<Object>) orderSkuListResult.getData();
                    for (Object obj : orderListObject) {
                        Map skuData = (Map) obj;
                        if (skuData.get("skuId").equals(skuInfoVO.getSkuId())) {
                            int deliveryQuantity = 0;//到货通知到的提货数量
                            int totalEntryNum = 0;
                            for (String id : entryIds) {//获取到所有的入库总数
                                for (Object o : entryBillList) {
                                    Map data = (Map) o;
                                    if (id.equals((String) data.get("entryId")) && skuData.get("skuId").equals((String) data.get("skuId"))) {
                                        totalEntryNum += (int) data.get("entryNum");
                                    }
                                }
                            }
                            //TODO 获取到到货通知单对应的该sku的提货数量
                            for (Object o : inspectionList) {
                                Map inspectionData = (Map) o;
                                if (inspectionData.get("skuId").equals(skuInfoVO.getSkuId())) {
                                    deliveryQuantity = (int) inspectionData.get("deliveryQuantity");
                                }
                            }
                            if ((totalEntryNum + skuInfoVO.getQuantity()) > deliveryQuantity) {
                                productVO.setEntryNum(skuInfoVO.getQuantity() - ((totalEntryNum + skuInfoVO.getQuantity()) - deliveryQuantity));
                                productVO.setEntryFrets((totalEntryNum + skuInfoVO.getQuantity()) - deliveryQuantity);
                            } else {
                                productVO.setEntryNum(skuInfoVO.getQuantity());
                                productVO.setEntryFrets(0);
                            }
                            productVO.setUnitPriceWithoutTax((Double) skuData.get("unitPriceWithoutTax"));
                            productVO.setAmountWithoutTax(productVO.getEntryNum() * (Double) skuData.get("unitPriceWithoutTax"));
                            productVO.setTaxRate((Double) skuData.get("taxRate"));
                            productVO.setUnitPriceWithTax((Double) skuData.get("unitPriceWithTax"));
                            productVO.setAmountWithTax(productVO.getEntryNum() * (Double) skuData.get("unitPriceWithTax"));
                        }
                    }
                } else {//调用外部查询采购订单包含的商品接口失败
                    throw new RuntimeException("调用外部查询采购订单包含的商品接口失败");
                }
                entryWarehouseProductVOS.add(productVO);
            }
            entryWarehouseAllVO.setEntryWarehouseProductVO(entryWarehouseProductVOS);
            Result result = purchaseOrderService.addEntryBill(gson.toJson(entryWarehouseAllVO));

            if (result.getCode() != 200) {
                throw new RuntimeException(result.getMsg());
            }

            for (SkuInfoVO skuInfoVO : skuPutawayReturnVO.getArrivalSkuList()) {
                Result statusResult = purchaseOrderService.getStatusByArrivalIdAndSkuId(skuPutawayReturnVO.getArrivalId(), skuInfoVO.getSkuId());
                Integer status = null;
                if (statusResult.getCode() == 200) {
                    status = (Integer) statusResult.getData();
                }
                if (status != null && status.equals(3)) {
                    purchaseOrderService.changeArrivalStatus(skuPutawayReturnVO.getArrivalId(), skuInfoVO.getSkuId(), 4);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 退货单回写异步执行方法
     *
     * @param purchaseRefundBillReturnVO
     */
    @Async
    public void purchaseRefundAsync(PurchaseRefundBillReturnVO purchaseRefundBillReturnVO) {
        String returnBill = purchaseRefundBillReturnVO.getReturnBill();
        String deleteStatus = purchaseRefundBillReturnVO.getDeleteStatus();
        Integer statusCd = Integer.parseInt(deleteStatus);

        Result result1 = purchaseOrderService.updateStatus(returnBill, statusCd);
        if (result1.getCode() == 200 && statusCd == 0) { //已经回写成功，进行虚拟包材入库（退包材）
            //TODO 根据退货单号去查询出退货单信息
            Result purchaseReturnInfoById = purchaseOrderService.getPurchaseReturnInfoById(purchaseRefundBillReturnVO.getReturnBill());
            String remark = "包材退回，成品sku：";
            VirtualEnterBillSyncVO virtualEnterBillSyncVO = new VirtualEnterBillSyncVO();
            List<GoodsInfoVO> goodsInfoVOS = new ArrayList<>();

            if (purchaseReturnInfoById.getCode() == 200) {
                virtualEnterBillSyncVO.setVoucherCode(purchaseRefundBillReturnVO.getReturnBill());
                virtualEnterBillSyncVO.setVoucherType("CGT");
                virtualEnterBillSyncVO.setCreateTime(Time.getCurrentDateTime());
                virtualEnterBillSyncVO.setSectionCode((String) ((Map) ((Map) purchaseReturnInfoById.getData()).get("purchaseReturnInfo")).get("packWarehouseId"));
                List<Object> returnProducts = (List<Object>) ((Map) purchaseReturnInfoById.getData()).get("purchaseRejectionDetailsList");
                int i = 1;
                for (Object returnProduct : returnProducts) {
                    Map data = (Map) returnProduct;
                    remark += (String) data.get("skuId");
                    Result skuPackingInfo = productService.getPackingInfoBySkuId((String) data.get("skuId"));
                    if ((int) ((Map) skuPackingInfo.getData()).get("packingFlag") == 1) {
                        Map skuPackingData = (Map) skuPackingInfo.getData();
                        List<Object> packingSkuList = (List<Object>) skuPackingData.get("packingMaterialList");
                        for (Object o : packingSkuList) {
                            Map map = (Map) o;
                            GoodsInfoVO goodsInfoVO = new GoodsInfoVO();
                            goodsInfoVO.setLineNum(i);
                            goodsInfoVO.setGoodsCode((String) map.get("dependencySkuId"));
                            goodsInfoVO.setGoodsName((String) map.get("skuNameZh"));
                            goodsInfoVO.setUnitQuantity((int) map.get("quantity") * ((int) data.get("returnQuantity") + (int) data.get("spareQuantity")));
                            goodsInfoVO.setPackageQuantity(0);
                            goodsInfoVO.setTotalQuantity((int) map.get("quantity") * ((int) data.get("returnQuantity") + (int) data.get("spareQuantity")));
                            goodsInfoVOS.add(goodsInfoVO);
                            i++;
                        }
                    }
                }
                virtualEnterBillSyncVO.setRemark(remark);
                virtualEnterBillSyncVO.setGoodsList(goodsInfoVOS);
            }
            //手动刷新
            List<StockQueryVO> queryVo = new LinkedList<>();
            for (SkuInfoVO one : purchaseRefundBillReturnVO.getSkuEntityList()) {
                StockQueryVO vo = new StockQueryVO();
                vo.setSkuId(one.getSkuId());
                vo.setWarehouseId((String) ((Map) ((Map) purchaseReturnInfoById.getData()).get("purchaseReturnInfo")).get("warehouseId"));
                queryVo.add(vo);
            }
            try {
                //虚拟出库单同步到wms
                if (virtualEnterBillSyncVO.getGoodsList().size() > 0) {
                    wmsBillSyncController.virtualEnterBillSync(virtualEnterBillSyncVO);
                    localStockUtil.localRefreshStockInfo(queryVo);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getBillId(String rediskey) {
        SimpleDateFormat sdf = new SimpleDateFormat("YY");
        String currDate = sdf.format(System.currentTimeMillis());
        String string0 = "";
        String key = currDate + rediskey;
        long maxId = redisTemplate.opsForValue().increment(key, 1);
        if (maxId == 1) {
            redisTemplate.expire(key, 1000 * 60 * 60 * 24, TimeUnit.MILLISECONDS);
        }
        if (maxId > 99999) {
            throw new RuntimeException("订单号已达最大");
        }
        for (int i = 0; i < (5 - String.valueOf(maxId).length()); i++) {
            string0 += "0";
        }
        return "PSL" + currDate + string0 + maxId;
    }
}
