package com.wisrc.quality.webapp.service.impl;

import com.wisrc.quality.webapp.service.QualityProductHandleService;
import com.wisrc.quality.webapp.service.ProductService;
import com.wisrc.quality.webapp.utils.ObjectHandler;
import com.wisrc.quality.webapp.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class QualityProductHandleServiceImpl implements QualityProductHandleService {
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
        List findkeyDealted = new ArrayList();

        Result product1 = productService.getProductInfl(findKey, null, 1, 99999);
        Result product2 = productService.getProductInfl(null, findKey, 1, 99999);
        if (product1 != null && product1.getCode() == 200) {
            findkeyDealted = getStoreSkuList(product1);
            if (product2 != null && product2.getCode() == 200) {
                List<String> productList2 = getStoreSkuList(product2);
                for (String storeSku : productList2) {
                    if (findkeyDealted.indexOf(storeSku) == -1) {
                        findkeyDealted.add(storeSku);
                    }
                }
            }
        }
        if (findkeyDealted.size() > 0) {
            return findkeyDealted;
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
}
