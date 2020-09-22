package com.wisrc.product.webapp.service.impl;

import com.wisrc.product.webapp.dao.ProductDeclareInfoDao;
import com.wisrc.product.webapp.entity.ProductDeclareInfoEntity;
import com.wisrc.product.webapp.service.ProductDeclareInfoService;
import com.wisrc.product.webapp.service.ProductModifyHistoryService;
import com.wisrc.product.webapp.utils.Time;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductDeclareInfoServiceImp implements ProductDeclareInfoService {
    private final Logger logger = LoggerFactory.getLogger(ProductDeclareInfoServiceImp.class);
    @Autowired
    private ProductDeclareInfoDao productDeclareInfoDao;
    @Autowired
    private ProductModifyHistoryService productModifyHistoryService;

    @Override
    public void insert(ProductDeclareInfoEntity ele) {
        String time = Time.getCurrentTime();
        ele.setCreateTime(time);
        productDeclareInfoDao.insert(ele);
    }

    @Override
    public void delete(String skuId) {
        productDeclareInfoDao.delete(skuId);
    }

    @Override
    public ProductDeclareInfoEntity findById(String skuId) {
        return productDeclareInfoDao.findById(skuId);
    }


    @Override
    public ProductDeclareInfoEntity findBySkuId(String skuId) {
        return productDeclareInfoDao.findBySkuId(skuId);
    }

    @Override
    public void update(ProductDeclareInfoEntity ele, String time, String userId) {
        String skuId = ele.getSkuId();
        ProductDeclareInfoEntity old = productDeclareInfoDao.findBySkuId(skuId);
        ele.setUpdateTime(time);
        if (old == null) {
            productDeclareInfoDao.insert(ele);
        } else {
            productDeclareInfoDao.update(ele);
        }

        //==============添加历史纪录
        try {
            if (old == null) {
                old = new ProductDeclareInfoEntity();
                old.setSkuId(skuId);
            }
            productModifyHistoryService.historyUpdate(old, ele, time, userId);
        } catch (Exception e) {
            logger.error("保存历史纪录出错！", e);
            throw new RuntimeException("保存历史纪录出错");
        }
    }

}
