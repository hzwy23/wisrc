package com.wisrc.shipment.webapp.service.impl;

import com.wisrc.shipment.webapp.service.LogisticsChannelAttrService;
import com.wisrc.shipment.webapp.service.OperationService;
import com.wisrc.shipment.webapp.service.ProductService;
import com.wisrc.shipment.webapp.service.WarehouseService;
import com.wisrc.shipment.webapp.entity.LogisticsChannelAttrEntity;
import com.wisrc.shipment.webapp.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

// 服务间调用 单条查询，批量请前端处理
@Service
public class MyFeignInfoService {

    @Autowired
    private ProductService productService;
    @Autowired
    private OperationService operationService;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private LogisticsChannelAttrService logisticsChannelAttrService;

    private Map<String, Object> ProductLabelAttrMap;

    // 设置产品标签对应名称
    @SuppressWarnings("unchecked")
    public void setProductLabelAttr() {
        ProductLabelAttrMap = new HashMap<>();
        Result productRes = productService.getProductLabelAttr("admin");
        List<Map<String, Object>> productData = (List<Map<String, Object>>) productRes.getData();
        for (Map<String, Object> item : productData) {
            ProductLabelAttrMap.put(String.valueOf(item.get("labelCd")), item.get("labelDesc"));
        }
    }

    public String getProductLabelAttr(String key) {
        return (String) ProductLabelAttrMap.get(key);
    }

    // 返回编号对应仓库名称
    @SuppressWarnings("unchecked")
    public Object getWarehouseById(String key) {
        Result warehouseRes = warehouseService.getMerchase("1", "0", 0, 0);
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> warehouseData = (Map<String, Object>) warehouseRes.getData();
        List<Map<String, Object>> list = (List<Map<String, Object>>) warehouseData.get("warehouseManageInfoVOList");
        for (Map<String, Object> item : list) {
            map.put((String) item.get("warehouseId"), item.get("warehouseName"));
        }
        return map.get(key);
    }

    // 返回编号对应物流渠道类型
    public Object getShipTypeById(Integer key) {
        List<LogisticsChannelAttrEntity> logisticsAttrs = logisticsChannelAttrService.findList();
        Map<String, Object> map = new HashMap<>();
        for (LogisticsChannelAttrEntity item : logisticsAttrs) {
            map.put(String.valueOf(item.getChannelTypeCd()), item.getChannelTypeDesc());
        }
        return map.get(String.valueOf(key));
    }

    // 根据编号获取店铺名称
    @SuppressWarnings("unchecked")
    public Object getShopNameById(String shopId) {
        try {
            Result shopRes = operationService.getShopById(shopId);
            Map<String, Object> mskuData = (Map<String, Object>) shopRes.getData();
            return mskuData.get("storeName");
        } catch (Exception e) {
            return "";
        }
    }

    // 根据编号获取商品基本信息
    @SuppressWarnings("unchecked")
    public Object getMskuById(String mskuId) {
        Map<String, Object> map = new HashMap<>();
        Result mskuRes = operationService.getMskuInfo(mskuId);
        Map<String, Object> mskuData = (Map<String, Object>) mskuRes.getData();
        map.put("msku", mskuData.get("mskuId"));
        map.put("mskuName", mskuData.get("mskuName"));
        map.put("storeSku", mskuData.get("storeSku"));
        map.put("manager", mskuData.get("employee"));
        map.put("shopName", ((Map<String, Object>) mskuData.get("shop")).get("name"));
        map.put("salesStatus", ((Map<String, Object>) mskuData.get("salesStatus")).get("name"));
        Result imgRes = productService.getProductImg((String) mskuData.get("storeSku"));
        List<Map<String, Object>> imgData = (List<Map<String, Object>>) imgRes.getData();
        if (!imgData.isEmpty()) {
            map.put("imageUrl", imgData.get(0).get("imageUrl"));
        }
        return map;
    }

    // 根据商品销售状态和关键字获取对应编号
    @SuppressWarnings("unchecked")
    public HashSet<String> getMskuIds(String salesStatus, String keyword) {
        HashSet<String> mskuIds = new HashSet<>();
        if (salesStatus != null || keyword != null) {
            Result mskuRes = operationService.getMsku(1, 3000, salesStatus, keyword);
            Map<String, Object> mskuData = (Map<String, Object>) mskuRes.getData();
            List<Map<String, Object>> mskuList = (List<Map<String, Object>>) mskuData.get("mskuList");
            for (Map<String, Object> item : mskuList) {
                mskuIds.add((String) item.get("id"));
            }
            if (mskuIds.isEmpty()) {
                mskuIds.add("null");
            }
        }
        return mskuIds;
    }

}
