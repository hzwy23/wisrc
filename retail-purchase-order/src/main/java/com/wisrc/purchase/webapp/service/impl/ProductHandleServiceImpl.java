package com.wisrc.purchase.webapp.service.impl;

import com.google.gson.Gson;
import com.wisrc.purchase.webapp.entity.BatchSkuId;
import com.wisrc.purchase.webapp.service.ProductHandleService;
import com.wisrc.purchase.webapp.service.externalService.ProductService;
import com.wisrc.purchase.webapp.utils.ObjectHandler;
import com.wisrc.purchase.webapp.utils.Result;
import com.wisrc.purchase.webapp.vo.product.GetProductInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductHandleServiceImpl implements ProductHandleService {
    @Autowired
    private ProductService productService;

    @Override
    public Map getProductCNNameMap(List skuId) throws Exception {
        Map productNameMap;

        Result productResult = productService.getProductCN(skuId);
        if (productResult.getCode() != 200) {
            throw new Exception("产品接口发生错误");
        }

        productNameMap = (Map) productResult.getData();

        return productNameMap;
    }

    @Override
    public List getFindKeyDealted(String findKey) throws Exception {
        Result product1 = productService.getProductInfo(findKey);
        List findkeyDealted = getStoreSkuList(product1);
        if (findkeyDealted.size() > 0) {
            return findkeyDealted;
        } else {
            return null;
        }
    }

    @Override
    public List getProduct(GetProductInfoVO getProductInfoVO) throws Exception {
        List getByNameToIds = new ArrayList();
        Result product = productService.getProductInfl(getProductInfoVO.getIgnoreSkuIds(), getProductInfoVO.getSkuId(), getProductInfoVO.getSkuNameZh(), getProductInfoVO.getStatusCd(),
                getProductInfoVO.getClassifyCd(), getProductInfoVO.getTypeCd(), getProductInfoVO.getStatusCd(),
                getProductInfoVO.getPageNum(), getProductInfoVO.getPageSize(), getProductInfoVO.getIgnoreImages(), getProductInfoVO.getKeyword());
        if (product.getCode() != 200) {
            throw new Exception(product.getMsg());
        }
        List<String> productList = getStoreSkuList(product);
        for (String storeSku : productList) {
            getByNameToIds.add(storeSku);
        }
        return getByNameToIds;
    }

    @Override
    public List getByNameToIds(String name) throws Exception {
        List getByNameToIds = new ArrayList();
        Result product = productService.getProductInfl(null, null, name, null, null, null, null,
                null, null, null, null);
        if (product != null && product.getCode() == 200) {
            List<String> productList = getStoreSkuList(product);
            for (String storeSku : productList) {
                getByNameToIds.add(storeSku);
            }
        }
        if (getByNameToIds.size() > 0) {
            return getByNameToIds;
        } else {
            return null;
        }
    }


    public List getStoreSkuList(Result productResult) throws Exception {
        List result = new ArrayList();

        Map<String, Object> productFound = ObjectHandler.LinkedHashMapToMap(productResult.getData());
        List<Map> productData = (List) productFound.get("productData");
        for (Map product : productData) {
            result.add(product.get("sku"));
        }

        return result;
    }

    @Override
    public Map getProductList(List<String> idlist) {
        Map mskuInfomap = new HashMap();
        BatchSkuId batchSkuId = new BatchSkuId();
        batchSkuId.setSkuIdList(idlist);
        Gson gson = new Gson();

        Result mskuResult = productService.getProdList(gson.toJson(batchSkuId));

        if (mskuResult.getCode() != 200) {
            throw new RuntimeException("产品查询接口发生错误");
        }
        List<Map> priductList = (List<Map>) mskuResult.getData();
        for (Map mskuInfoMap : priductList) {
            Map map = new HashMap();
            Map m = (Map) mskuInfoMap.get("declareInfo");
            map.put("issuingOffice", m.get("issuingOffice"));
            map.put("declareNameZh", m.get("declareNameZh"));
            map.put("model", m.get("model"));
            map.put("packLength", m.get("packLength"));
            map.put("packHeight", m.get("packHeight"));
            map.put("packWidth", m.get("packWidth"));

            mskuInfomap.put(m.get("skuId"), map);
        }
        return mskuInfomap;
    }

    @Override
    public Map getProductSales(List skuIds) throws Exception {
        Result salesResult = productService.getProductSales(skuIds);
        if (salesResult.getCode() != 200) {
            throw new Exception(salesResult.getMsg());
        }

        return (Map) salesResult.getData();
    }

    @Override
    public Map getProductCN(List skuId) throws Exception {
        Result storeCN = productService.getProductCN(skuId);
        return ObjectHandler.LinkedHashMapToMap(storeCN.getData());
    }

    @Override
    public List getProductPicture(List skuId) throws Exception {
        Result productPicture = productService.getProductPicture(skuId);
        return (List) productPicture.getData();
    }
}
