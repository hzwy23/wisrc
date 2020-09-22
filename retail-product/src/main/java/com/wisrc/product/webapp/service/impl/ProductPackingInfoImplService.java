package com.wisrc.product.webapp.service.impl;

import com.wisrc.product.webapp.dao.ProductPackingInfoDao;
import com.wisrc.product.webapp.entity.ProductPackingInfoEntity;
import com.wisrc.product.webapp.service.ProductModifyHistoryService;
import com.wisrc.product.webapp.service.ProductPackingInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProductPackingInfoImplService implements ProductPackingInfoService {
    private final Logger logger = LoggerFactory.getLogger(ProductPackingInfoImplService.class);

    @Autowired
    private ProductPackingInfoDao dao;
    @Autowired
    private ProductModifyHistoryService productModifyHistoryService;

    @Override
    public void insert(ProductPackingInfoEntity pPIEntity) {
        dao.insert(pPIEntity);
    }

    @Override
    public void update(ProductPackingInfoEntity pPIEntity, String time, String userId) {
        String skuId = pPIEntity.getSkuId();
        ProductPackingInfoEntity old = findBySkuId(skuId);
        if (old == null) {
            dao.insert(pPIEntity);
        } else {
            dao.update(pPIEntity);
        }

        //==============添加历史纪录
        try {
            if (old == null) {
                old = new ProductPackingInfoEntity();
                old.setSkuId(skuId);
            }
            productModifyHistoryService.historyUpdate(old, pPIEntity, time, userId);
        } catch (Exception e) {
            logger.error("保存历史纪录出错！", e);
            throw new RuntimeException("保存历史纪录出错");
        }
    }

    @Override
    public ProductPackingInfoEntity findBySkuId(String skuId) {
        return dao.findBySkuId(skuId);
    }

}
