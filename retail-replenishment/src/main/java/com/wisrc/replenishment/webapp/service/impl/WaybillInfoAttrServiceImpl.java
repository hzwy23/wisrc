package com.wisrc.replenishment.webapp.service.impl;

import com.wisrc.replenishment.webapp.dao.WaybillInfoAttrDao;
import com.wisrc.replenishment.webapp.entity.*;
import com.wisrc.replenishment.webapp.service.WaybillInfoAttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WaybillInfoAttrServiceImpl implements WaybillInfoAttrService {
    @Autowired
    WaybillInfoAttrDao waybillInfoAttrDao;

    @Override
    public List<CustomsAttrEntity> findCustomsAttr() {
        return waybillInfoAttrDao.findCustomsAttr();
    }

    @Override
    public List<ExceptionTypeAttrEntity> findExceptionAttr() {
        return waybillInfoAttrDao.findExceptionAttr();
    }

    @Override
    public List<LogisticsTypeAttrEntity> findLogisticsAttr() {
        return waybillInfoAttrDao.findLogisticsAttr();
    }

    @Override
    public List<WeightTypeAttrEntity> findWeightAttr() {
        return waybillInfoAttrDao.findWeightAttr();
    }

    @Override
    public List<DeclareMskuUnitAttrEntity> findUnitAttr(Integer unitCd) {
        if (unitCd != null) {
            return waybillInfoAttrDao.findUnitAttrById(unitCd);
        }
        return waybillInfoAttrDao.findUnitAttr();
    }
}
