package com.wisrc.basic.service.impl;

import com.wisrc.basic.dao.CompanyBasicInfoAttrDao;
import com.wisrc.basic.entity.*;
import com.wisrc.basic.service.CompanyBasicInfoAttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyBasicInfoAttrServiceImpl implements CompanyBasicInfoAttrService {
    @Autowired
    private CompanyBasicInfoAttrDao CompanyBasicInfoAttrDao;

    @Override
    public List<DeliveryPortAttrEntity> findDeliveryPortAttr() {
        return CompanyBasicInfoAttrDao.findDeliveryPortAttr();
    }

    @Override
    public List<ExemptionModeAttrEntity> findExemptionModeAttr() {
        return CompanyBasicInfoAttrDao.findExemptionModeAttr();
    }

    @Override
    public List<ExemptingNatureAttrEntity> findExemptingNatureAttr() {
        return CompanyBasicInfoAttrDao.findExemptingNatureAttr();
    }

    @Override
    public List<MonetarySystemAttrEntity> findMonetarySystemAttr() {
        return CompanyBasicInfoAttrDao.findMonetarySystemAttr();
    }

    @Override
    public List<PackingTypeAttrEntity> findPackingTypeAttr() {
        return CompanyBasicInfoAttrDao.findPackingTypeAttr();
    }

    @Override
    public List<TradeModeAttrEntity> findTradeModeAttr() {
        return CompanyBasicInfoAttrDao.findTradeModeAttr();
    }

    @Override
    public List<TransactionModeAttrEntity> findTransactionModeAttr() {
        return CompanyBasicInfoAttrDao.findTransactionModeAttr();
    }
}
