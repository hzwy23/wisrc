package com.wisrc.purchase.webapp.AOP;

import com.google.gson.Gson;
import com.wisrc.purchase.webapp.service.OutWarehouseService;
import com.wisrc.purchase.webapp.service.externalService.ProductService;
import com.wisrc.purchase.webapp.service.externalService.SkuService;
import com.wisrc.purchase.webapp.service.externalService.WmsService;
import com.wisrc.purchase.webapp.utils.Result;
import com.wisrc.purchase.webapp.utils.Time;
import com.wisrc.purchase.webapp.vo.StockQueryVO;
import com.wisrc.purchase.webapp.vo.entrywarehouse.EntryWarehouseAllVO;
import com.wisrc.purchase.webapp.vo.entrywarehouse.EntryWarehouseProductVO;
import com.wisrc.purchase.webapp.vo.syncvo.GoodsInfoVO;
import com.wisrc.purchase.webapp.vo.syncvo.VirtualEnterBillSyncVO;
import com.wisrc.purchase.webapp.vo.syncvo.VirtualOutBillSync;
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
public class EntryWarehouseSyncAOP {

    private static Map<String, String> skuIdName = null;
    @Autowired
    private WmsService wmsService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OutWarehouseService warehouseService;
    @Autowired
    private SkuService skuService;
    @Autowired
    private Gson gson;

    private void initData() {
        skuIdName = new LinkedHashMap<>();
        Result productSkuInfo = skuService.getProductSkuInfo(null, null, null);
        List<Object> productDatas = (List<Object>) ((Map) productSkuInfo.getData()).get("productData");
        for (Object productData : productDatas) {
            Map data = (Map) productData;
            skuIdName.put((String) data.get("sku"), (String) data.get("skuNameZh"));
        }
    }

    @AfterReturning(value = "execution(public * com.wisrc.purchase.webapp.service.impl.EntryWarehouseServiceImpl.addInfo(..))", returning = "obj")
    public void entryWarehouseSync(JoinPoint joinPoint, Result obj) {
        if (skuIdName == null) {
            initData();
        }
        Object[] args = joinPoint.getArgs();
        EntryWarehouseAllVO entryWarehouseAllVO = (EntryWarehouseAllVO) args[0];
        //回传给wms的数据
        VirtualOutBillSync virtualOutBillSync = new VirtualOutBillSync();

        //回写给wms的数据
        VirtualEnterBillSyncVO virtualEnterBillSyncVO = new VirtualEnterBillSyncVO();


        //回写回去的物料信息
        List<GoodsInfoVO> goodsInfoVOS = new ArrayList<>();

        StringBuffer remark = new StringBuffer("包材消耗，成品sku编号:");

        List<EntryWarehouseProductVO> entryWarehouseProductVO = entryWarehouseAllVO.getEntryWarehouseProductVO();
        try {
            if (obj.getCode() == 200) {
                //采购入库单号
                String entryId = (String) obj.getData();
                //同步到wms的基本数据
                virtualOutBillSync.setVoucherCode(entryId);
                virtualOutBillSync.setVoucherType("CGR");
                virtualOutBillSync.setCreateTime(Time.getCurrentDateTime());
                virtualOutBillSync.setSectionCode(entryWarehouseAllVO.getEntryWarehouseVO().getPackWarehouseId());

                virtualEnterBillSyncVO.setVoucherCode(entryId);
                virtualEnterBillSyncVO.setVoucherType("CGR");
                virtualEnterBillSyncVO.setCreateTime(Time.getCurrentDateTime());
                virtualEnterBillSyncVO.setSectionCode(entryWarehouseAllVO.getEntryWarehouseVO().getWarehouseId());
                virtualEnterBillSyncVO.setRemark(entryWarehouseAllVO.getEntryWarehouseVO().getRemark());


                //TODO 根据仓库id获取到仓库信息
                Result warehouseInfoById = warehouseService.getWarehouseInfoById(entryWarehouseAllVO.getEntryWarehouseVO().getWarehouseId());
                if (warehouseInfoById.getCode() != 200) {
                    throw new RuntimeException(warehouseInfoById.getMsg());
                }
                Map warehouseData = (Map) warehouseInfoById.getData();
                String warehouseType = (String) warehouseData.get("typeCd");
                if (warehouseType.equals("C")) {//如果是虚拟仓的采购入库单，就推虚拟入库单到wms那边
                    int i = 1;
                    for (EntryWarehouseProductVO warehouseProductVO : entryWarehouseProductVO) {
                        GoodsInfoVO goodsInfoVO = new GoodsInfoVO();
                        goodsInfoVO.setLineNum(i);
                        goodsInfoVO.setGoodsCode(warehouseProductVO.getSkuId());
                        goodsInfoVO.setGoodsName(skuIdName.get(warehouseProductVO.getSkuId()));
                        goodsInfoVO.setUnitQuantity(warehouseProductVO.getEntryNum() + warehouseProductVO.getEntryFrets());
                        goodsInfoVO.setPackageQuantity(0);
                        goodsInfoVO.setTotalQuantity(warehouseProductVO.getEntryNum() + warehouseProductVO.getEntryFrets());
                        goodsInfoVOS.add(goodsInfoVO);
                        i++;
                    }
                    virtualEnterBillSyncVO.setGoodsList(goodsInfoVOS);
                    Result result = wmsService.virtualEnterBillSync(gson.toJson(virtualEnterBillSyncVO));
                    if (result.getCode() != 0) {
                        throw new RuntimeException("wms同步失败：" + result.getMsg());
                    }
                    return;
                }


                //存储手动刷新数据
                List<StockQueryVO> stockQueryVOS = new ArrayList<>();

                int i = 1;
                for (EntryWarehouseProductVO warehouseProductVO : entryWarehouseProductVO) {
                    String skuId = warehouseProductVO.getSkuId();
                    if (remark.toString().endsWith(":")) {
                        remark.append(skuId);
                    } else {
                        remark = remark.append("," + skuId);
                    }
                    Result productPackingInfo = productService.getProductPackingInfo(skuId);
                    Map packingMap = (Map) productPackingInfo.getData();
                    int flag = (int) packingMap.get("packingFlag");
                    if (flag == 1) {//产品需要包材
                        List<Object> packingMaterialList = (List<Object>) packingMap.get("packingMaterialList");
                        for (Object o : packingMaterialList) {
                            Map data = (Map) o;

                            //准备本地刷新数据
                            StockQueryVO stockQueryVO = new StockQueryVO();
                            stockQueryVO.setWarehouseId(entryWarehouseAllVO.getEntryWarehouseVO().getPackWarehouseId());
                            stockQueryVO.setSkuId((String) data.get("dependencySkuId"));


                            GoodsInfoVO goodsInfoVO = new GoodsInfoVO();
                            goodsInfoVO.setLineNum(i);
                            goodsInfoVO.setGoodsCode((String) data.get("dependencySkuId"));
                            goodsInfoVO.setGoodsName((String) data.get("skuNameZh"));
                            goodsInfoVO.setUnitQuantity((warehouseProductVO.getEntryNum() + warehouseProductVO.getEntryFrets()) * ((int) data.get("quantity")));
                            goodsInfoVO.setPackageQuantity(0);
                            goodsInfoVO.setTotalQuantity((warehouseProductVO.getEntryNum() + warehouseProductVO.getEntryFrets()) * ((int) data.get("quantity")));
                            goodsInfoVOS.add(goodsInfoVO);
                            i++;
                        }
                    }
                }
                virtualOutBillSync.setGoodsList(goodsInfoVOS);
                virtualOutBillSync.setRemark(remark.toString());
                if (virtualOutBillSync.getGoodsList().size() > 0) {
                    Result result = wmsService.virtualOutBillSync(gson.toJson(virtualOutBillSync));
                    if (result.getCode() != 0) {
                        throw new RuntimeException(result.getMsg());
                    } else {
                        //这里？？？
                        wmsService.refreshStockData(gson.toJson(stockQueryVOS));
                    }
                }
            }
        } catch (Exception e) {
            obj.setMsg(obj.getMsg() + "," + e.getMessage());
        }
    }
}
