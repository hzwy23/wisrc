package com.wisrc.replenishment.webapp.service.impl;

import com.wisrc.replenishment.webapp.service.MskuInfoListService;
import com.wisrc.replenishment.webapp.service.MskuInfoService;
import com.wisrc.replenishment.webapp.utils.Result;
import com.wisrc.replenishment.webapp.vo.MskuInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MskuInfoListServiceImpl implements MskuInfoListService {

    @Autowired
    private MskuInfoService mskuInfoService;

    @Override
    public Map getMskuInfoList(List<String> mskuIdlist) throws Exception {
        Map mskuInfomap = new HashMap();
        String[] ids = mskuIdlist.toArray(new String[mskuIdlist.size()]);

        Result mskuResult = mskuInfoService.getMskuInfoByIds(ids);

        if (mskuResult.getCode() != 200) {
            throw new Exception("商品查询接口发生错误");
        }
        LinkedHashMap hashMap = (LinkedHashMap) mskuResult.getData();
        List<Map> shipmentList = (List<Map>) hashMap.get("mskuInfoBatch");
        List<String> skuIds = new ArrayList<>();
        for (Map mskuInfoMap : shipmentList) {
            MskuInfoVO mskuInfo = new MskuInfoVO();
            mskuInfo.setManager((String) mskuInfoMap.get("employee"));
            mskuInfo.setSalesStatus((String) mskuInfoMap.get("salesStatusDesc"));
            mskuInfo.setASIN((String) mskuInfoMap.get("asin"));
            mskuInfo.setFnSKU((String) mskuInfoMap.get("fnSkuId"));
            mskuInfo.setMskuName((String) mskuInfoMap.get("mskuName"));
            mskuInfo.setStoreSku((String) mskuInfoMap.get("skuId"));
            mskuInfo.setPicture((String) mskuInfoMap.get("picture"));
            mskuInfo.setStoreName((String) mskuInfoMap.get("shopName"));
            mskuInfo.setShopName((String) mskuInfoMap.get("shopName"));
            mskuInfo.setMsku((String) mskuInfoMap.get("mskuId"));
            mskuInfo.setProductName((String) mskuInfoMap.get("productName"));
            skuIds.add((String) mskuInfoMap.get("skuId"));
            mskuInfomap.put(mskuInfoMap.get("id"), mskuInfo);

        }
        mskuInfomap.put("skuIds", skuIds);
        return mskuInfomap;
    }

    /**
     * 根据产品id,产品名称模糊查询商品相关信息
     *
     * @param employeeId  员工号
     * @param skuId       库存SKu
     * @param productName 库存SKU名称
     * @return
     * @throws Exception
     */
    @Override
    public List getMskuList(String employeeId, String skuId, String productName) throws Exception {
        Result mskuResult = mskuInfoService.getMskuByKey(employeeId, skuId, productName);
        if (mskuResult.getCode() != 200) {
            throw new Exception("商品查询接口发生错误");
        }
        List<Map> shipmentList = (List<Map>) mskuResult.getData();
        List<String> mskuIds = new ArrayList<>();
        for (Map mskuInfoMap : shipmentList) {
            if (mskuInfoMap.get("mskuId") != null) {
                mskuIds.add((String) mskuInfoMap.get("mskuId"));
            }
        }
        return mskuIds;
    }

    public Set<String> getCommitIdList(String asin, String skuId, String productName) {
        Set<String> commodityIds = new HashSet<>();
        try {
            Result mskuResult1 = mskuInfoService.getMskuBySku(asin, null, null);
            Result mskuResult2 = mskuInfoService.getMskuBySku(null, skuId, null);
            Result mskuResult3 = mskuInfoService.getMskuBySku(null, null, productName);
            List<Map> shipmentList1 = (List<Map>) mskuResult1.getData();
            List<Map> shipmentList2 = (List<Map>) mskuResult2.getData();
            List<Map> shipmentList3 = (List<Map>) mskuResult3.getData();
            for (Map mskuInfoMap : shipmentList1) {
                if (mskuInfoMap.get("id") != null) {
                    commodityIds.add((String) mskuInfoMap.get("id"));
                }
            }
            for (Map mskuInfoMap : shipmentList2) {
                if (mskuInfoMap.get("id") != null) {
                    commodityIds.add((String) mskuInfoMap.get("id"));
                }
            }
            for (Map mskuInfoMap : shipmentList3) {
                if (mskuInfoMap.get("id") != null) {
                    commodityIds.add((String) mskuInfoMap.get("id"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return commodityIds;
    }
}
