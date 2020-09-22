package com.wisrc.warehouse.webapp.service.impl;

import com.wisrc.warehouse.webapp.service.SkuDetailService;
import com.wisrc.warehouse.webapp.vo.stockVO.*;
import com.wisrc.warehouse.webapp.dao.SkuDetailDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkuDetailServiceImpl implements SkuDetailService {
    @Autowired
    private SkuDetailDao skuDetailDao;

    @Override
    public List<ProxyVirtual> getSkuStockOversea(String skuId, String date) {
        return skuDetailDao.getSkuStockOversea(skuId, date);
    }

    @Override
    public List<ProxyVirtual> getLocalStockDetail(String skuId, String date) {
        return skuDetailDao.getLocalStockDetail(skuId, date);
    }

    @Override
    public List<FbaOnwayDetailVO> getFbaOnwayDetail(String skuId, String date, String mskuId) {
        return skuDetailDao.getFbaOnwayDetail(skuId, date, mskuId);
    }

    @Override
    public List<OverseaTransferDetailVO> getTransferDetail(String skuId, String date) {
        return skuDetailDao.getTransferDetail(skuId, date);
    }

    @Override
    public List<LocalOnwayDetailVO> getLocalOnwayDetail(String skuId, String date) {
        return skuDetailDao.getLocalOnwayDetail(skuId, date);
    }

    @Override
    public List<ProductDetailVO> getProductDetail(String skuId, String date) {
        return skuDetailDao.getProductDetail(skuId, date);
    }

    @Override
    public List<FbaReturnDetailVO> getFbaReturnDetail(String skuId, String date, String mskuId) {
        return skuDetailDao.getFbaReturnDetail(skuId, date, mskuId);
    }
}
