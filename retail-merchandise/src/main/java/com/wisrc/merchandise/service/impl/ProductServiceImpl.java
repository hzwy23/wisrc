package com.wisrc.merchandise.service.impl;


import com.wisrc.merchandise.dto.ProductSelectorDTO;
import com.wisrc.merchandise.outside.ProductOutside;
import com.wisrc.merchandise.service.ProductService;
import com.wisrc.merchandise.utils.ObjectHandler;
import com.wisrc.merchandise.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductOutside productOutside;

    @Override
    public Result getProductInfl() {
        try {
            Map result = new HashMap();
            List productSelectors = getProductSelector();
            result.put("storeMskuStatus", productSelectors);
            return new Result(200, "", result);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(400, "", e.getMessage());
        }
    }

    @Override
    public List getProductSelector() throws Exception {
        try {
            Result result = productOutside.getProductInfl(null, null, 1, 99999);
            if (result.getCode() != 200) {
                throw new Exception(result.getMsg());
            } else {
                Map<String, Object> productFound = ObjectHandler.LinkedHashMapToMap(result.getData());
                List productSelectors = new ArrayList();
                List<Map> productData = ObjectHandler.objectToList(productFound.get("productData"));
                for (Map product : productData) {
                    ProductSelectorDTO productSelector = new ProductSelectorDTO();
                    productSelector.setId(String.valueOf(product.get("sku")));
                    productSelector.setName(String.valueOf(product.get("skuNameZh")));
                    productSelectors.add(productSelector);
                }
                return productSelectors;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List getFindKeyDealted(String findKey) throws Exception {
        Result product1 = productOutside.getProductInfo(findKey, 1, 99999);
        List findkeyDealted = getStoreSkuList(product1);
        if (findkeyDealted.size() > 0) {
            return findkeyDealted;
        } else {
            return null;
        }
    }

    @Override
    public Map getProductCN(List<String> skuId) throws Exception {
        Result storeCN = productOutside.getProductCNNew(skuId);
        return ObjectHandler.LinkedHashMapToMap(storeCN.getData());
    }

    @Override
    public List getProductPicture(List<String> skuId) throws Exception {
        log.debug("SKU list is: {}", skuId);
        Result productPicture = productOutside.getProductPicture(skuId);
        return ObjectHandler.objectToList(productPicture.getData());
    }

    @Override
    public List getByIdAndName(String id, String name) throws Exception {
        Result product = productOutside.getProductInfl(id, name, 1, 99999);
        return getStoreSkuList(product);
    }

    @Override
    public Map getProductSales(List<String> skuIds) throws Exception {
        Result salesResult = productOutside.getProductSales(skuIds);
        if (salesResult.getCode() != 200) {
            throw new Exception(salesResult.getMsg());
        }

        return (Map) salesResult.getData();
    }


    public List getStoreSkuList(Result productResult) throws Exception {
        List result = new ArrayList();

        Map<String, Object> productFound = ObjectHandler.LinkedHashMapToMap(productResult.getData());
        List<Map> productData = ObjectHandler.objectToList(productFound.get("productData"));
        for (Map product : productData) {
            result.add(product.get("sku"));
        }

        return result;
    }
}
