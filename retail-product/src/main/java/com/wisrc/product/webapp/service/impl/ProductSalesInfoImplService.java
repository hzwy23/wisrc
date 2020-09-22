package com.wisrc.product.webapp.service.impl;

import com.wisrc.product.webapp.dao.ProductSalesInfoDao;
import com.wisrc.product.webapp.entity.ProductSalesInfoEntity;
import com.wisrc.product.webapp.entity.ProductSalesStatusCdAttr;
import com.wisrc.product.webapp.service.ProductModifyHistoryService;
import com.wisrc.product.webapp.service.ProductSalesInfoService;
import com.wisrc.product.webapp.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductSalesInfoImplService implements ProductSalesInfoService {
    private final Logger logger = LoggerFactory.getLogger(ProductSalesInfoImplService.class);
    @Autowired
    private ProductSalesInfoDao productSalesInfoDao;
    @Autowired
    private ProductModifyHistoryService productModifyHistoryService;

    @Override
    public void add(ProductSalesInfoEntity productSalesEntity) {
        productSalesInfoDao.add(productSalesEntity);
    }

    @Override
    public void update(ProductSalesInfoEntity productSalesEntity, String time, String userId) {
        ProductSalesInfoEntity old = productSalesInfoDao.findBySkuId(productSalesEntity.getSkuId());
        if (old == null) {
            productSalesInfoDao.add(productSalesEntity);
        } else {
            productSalesInfoDao.update(productSalesEntity);
        }

        //==============添加历史纪录
        try {
            if (old == null) {
                old = new ProductSalesInfoEntity();
                old.setSkuId(productSalesEntity.getSkuId());
            }
            productModifyHistoryService.historyUpdate(old, productSalesEntity, time,userId);
        } catch (Exception e) {
            logger.error("保存历史纪录出错！", e);
            throw new RuntimeException("保存历史纪录出错");
        }

    }

    @Override
    public ProductSalesInfoEntity findBySkuId(String skuId) {
        ProductSalesInfoEntity productSalesInfoEntity = productSalesInfoDao.findBySkuId(skuId);
        return productSalesInfoEntity;
    }

    @Override
    public List<ProductSalesStatusCdAttr> getSalesAttr() {
        List<ProductSalesStatusCdAttr> attrList = productSalesInfoDao.getSalesAttr();
        return attrList;
    }

    @Override
    public Result batchFind(List<String> skuIds) {
        if (skuIds == null || skuIds.size() == 0) {
            return new Result(9997, "批量查询参数skuIds非法", null);
        }
        String para = listToString(skuIds);
        Map<String, String> paraMap = new HashMap();
        paraMap.put("skuIds", para);
        List<LinkedHashMap> list = productSalesInfoDao.batchFind(paraMap);
        List<ProductSalesStatusCdAttr> attrList = productSalesInfoDao.getSalesAttr();
        Map salesStatusAttrMap = new HashMap();
        for (ProductSalesStatusCdAttr o : attrList) {
            salesStatusAttrMap.put(o.getSalesStatusCd(), o.getSalesStatusDesc());
        }
        Map result = new HashMap();
        for (LinkedHashMap o : list) {
            o.put("salesStatusDesc", salesStatusAttrMap.get(o.get("salesStatusCd")));
            result.put(o.get("skuId"), o);
        }
        return Result.success(result);
    }

    @Override
    public Result batchFindPost(List<String> skuIds) {
        if (skuIds.size() == 0) {
            return Result.success(null);
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < skuIds.size(); i++) {
            if (i == 0) {
                sb.append("(" + "'" + skuIds.get(i) + "'");
            } else {
                sb.append("," + "'" + skuIds.get(i) + "'");
            }
            if (i == (skuIds.size() - 1)) {
                sb.append(")");
            }
        }
        String condition = sb.toString();
        ProductSalesInfoEntity entity = new ProductSalesInfoEntity();
        entity.setSkuId(condition);
        List<ProductSalesInfoEntity> list = productSalesInfoDao.batchFindPost(entity);
        return Result.success(list);
    }

    private String listToString(List<String> list) {
        String result;
        if (list == null || list.size() == 0) {
            result = "('')";
        } else {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < list.size(); i++) {
                String temp = list.get(i);
                if (i == 0) {
                    sb.append("(" + "'" + temp + "'");
                } else {
                    sb.append("," + "'" + temp + "'");
                }

                if (i == (list.size() - 1)) {
                    sb.append(")");
                }
            }
            result = sb.toString();
        }
        return result;
    }

}
