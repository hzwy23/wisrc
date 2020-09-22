package com.wisrc.product.webapp.service.impl;

import com.wisrc.product.webapp.dao.ProductDetailsInfoDao;
import com.wisrc.product.webapp.entity.ProductDetailsInfoEntity;
import com.wisrc.product.webapp.service.ProductDetailsInfoService;
import com.wisrc.product.webapp.service.ProductModifyHistoryService;
import com.wisrc.product.webapp.utils.Time;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductDetailsInfoServiceImp implements ProductDetailsInfoService {
    private final Logger logger = LoggerFactory.getLogger(ProductDetailsInfoServiceImp.class);
    @Autowired
    private ProductDetailsInfoDao productDetailsInfoDao;
    @Autowired
    private ProductModifyHistoryService productModifyHistoryService;

    @Override
    public void insert(ProductDetailsInfoEntity ele) {
        String time = Time.getCurrentTime();
        ele.setCreateTime(time);
        productDetailsInfoDao.insert(ele);

    }

    @Override
    public void delete(String skuId) {
        productDetailsInfoDao.delete(skuId);
    }

    @Override
    public ProductDetailsInfoEntity findById(String skuId) {
        return productDetailsInfoDao.findById(skuId);
    }


    @Override
    public ProductDetailsInfoEntity findBySkuId(String skuId) {
        return productDetailsInfoDao.findBySkuId(skuId);
    }

    @Override
    public void update(ProductDetailsInfoEntity ele, String time, String modifyUserId) {
        String skuId = ele.getSkuId();
        ProductDetailsInfoEntity old = productDetailsInfoDao.findBySkuId(skuId);
        ele.setUpdateTime(time);
        if (old == null) {
            productDetailsInfoDao.insert(ele);
            old = new ProductDetailsInfoEntity();
            old.setSkuId(skuId);
        } else {
            productDetailsInfoDao.update(ele);
        }

        //==============添加历史纪录
        try {
            if (old == null) {
                old = new ProductDetailsInfoEntity();
                old.setSkuId(skuId);
            }
            productModifyHistoryService.historyUpdate(old, ele, time, modifyUserId);
        } catch (Exception e) {
            logger.error("save history failed, error info is:！", e.getMessage());
        }
        //==============添加历史纪录
    }

}
