package com.wisrc.product.webapp.service.impl;

import com.wisrc.product.webapp.controller.ProductDefineController;
import com.wisrc.product.webapp.dao.ProductSpecificationsInfoDao;
import com.wisrc.product.webapp.entity.ProductSpecificationsInfoEntity;
import com.wisrc.product.webapp.service.ProductModifyHistoryService;
import com.wisrc.product.webapp.service.ProductSpecificationsInfoService;
import com.wisrc.product.webapp.utils.Time;
import com.wisrc.product.webapp.vo.fba.FBASpecificationsInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductSpecificationsInfoServiceImp implements ProductSpecificationsInfoService {
    private final Logger logger = LoggerFactory.getLogger(ProductDefineController.class);
    @Autowired
    private ProductSpecificationsInfoDao productSpecificationsInfoDao;
    @Autowired
    private ProductModifyHistoryService productModifyHistoryService;

    @Override
    public void insert(ProductSpecificationsInfoEntity ele) {
        String time = Time.getCurrentTime();
        ele.setCreateTime(time);
        productSpecificationsInfoDao.insert(ele);
    }

    @Override
    public void delete(String skuId) {
        productSpecificationsInfoDao.delete(skuId);
    }

    @Override
    public ProductSpecificationsInfoEntity findById(String skuId) {
        return productSpecificationsInfoDao.findById(skuId);
    }


    @Override
    public ProductSpecificationsInfoEntity findBySkuId(String skuId) {
        return productSpecificationsInfoDao.findBySkuId(skuId);
    }

    @Override
    public void update(ProductSpecificationsInfoEntity ele, String time, String userId) {
        String skuId = ele.getSkuId();
        ProductSpecificationsInfoEntity old = productSpecificationsInfoDao.findBySkuId(skuId);
        ele.setUpdateTime(time);
        if (old == null) {
            productSpecificationsInfoDao.insert(ele);
        } else {
            productSpecificationsInfoDao.update(ele);
        }
        //==============添加历史纪录
        try {
            if (old == null) {
                old = new ProductSpecificationsInfoEntity();
                old.setSkuId(skuId);
            }
            productModifyHistoryService.historyUpdate(old, ele, time, userId);
        } catch (Exception e) {
            logger.error("保存历史纪录出错！", e);
            throw new RuntimeException("保存历史纪录出错");
        }
    }

    @Override
    public void updateFba(ProductSpecificationsInfoEntity productSpecificationsInfoEntity, String time, String userId) {
        String skuId = productSpecificationsInfoEntity.getSkuId();
        ProductSpecificationsInfoEntity old = productSpecificationsInfoDao.findBySkuId(skuId);
        productSpecificationsInfoEntity.setUpdateTime(time);
        productSpecificationsInfoEntity.setUpdateUser(userId);
        productSpecificationsInfoDao.updateFba(productSpecificationsInfoEntity);

        //==============添加历史纪录
        try {
            //因为productSpecificationsInfoDao.updateFba(productSpecificationsInfoEntity)方法，只更新部分字段
            //所以 历史纪录的新值与旧值的设置需要额外设置
            //FBASpecificationsInfoVO是新旧两个值字段存在对比的VO对象
            FBASpecificationsInfoVO oldObj = new FBASpecificationsInfoVO();
            if (old != null) {
                BeanUtils.copyProperties(old, oldObj);
            } else {
                oldObj.setSkuId(skuId);
            }
            FBASpecificationsInfoVO newObj = new FBASpecificationsInfoVO();
            BeanUtils.copyProperties(productSpecificationsInfoEntity, newObj);
            productModifyHistoryService.historyUpdate(oldObj, newObj, time, userId);
        } catch (Exception e) {
            logger.error("保存历史纪录出错！", e);
            throw new RuntimeException("保存历史纪录出错");
        }
    }

}
