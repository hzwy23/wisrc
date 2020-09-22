package com.wisrc.replenishment.webapp.service.impl;

import com.google.gson.Gson;
import com.wisrc.replenishment.webapp.entity.BatchSkuId;
import com.wisrc.replenishment.webapp.service.ProductListService;
import com.wisrc.replenishment.webapp.service.ProductService;
import com.wisrc.replenishment.webapp.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ProductListServiceImpl implements ProductListService {
    @Autowired
    ProductService productService;

    @Override
    public Map getProductList(List<String> idlist) throws Exception {
        Map mskuInfomap = new HashMap();
        BatchSkuId batchSkuId = new BatchSkuId();
        batchSkuId.setSkuIdList(idlist);
        Gson gson = new Gson();

        Result mskuResult = productService.getProdList(gson.toJson(batchSkuId));

        if (mskuResult.getCode() != 200) {
            throw new Exception("产品查询接口发生错误");
        }
        List<Map> priductList = (List<Map>) mskuResult.getData();
        for (Map mskuInfoMap : priductList) {
            Map map = new HashMap();
            Map m = (Map) mskuInfoMap.get("declareInfo");
            Map defineMap = (Map) mskuInfoMap.get("define");
            map.put("declareLabelList", mskuInfoMap.get("declareLabelList"));
            map.put("skuNameZh", defineMap.get("skuNameZh"));

            mskuInfomap.put(m.get("skuId"), map);
        }
        return mskuInfomap;
    }
}
