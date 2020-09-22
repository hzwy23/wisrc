package com.wisrc.replenishment.webapp.service.impl;

import com.wisrc.replenishment.webapp.dao.FbaReplenishmentLabelAttrDao;
import com.wisrc.replenishment.webapp.entity.FbaReplenishmentLabelAttrEntity;
import com.wisrc.replenishment.webapp.service.FbaReplenishmentLabelAttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "labelAttrServie")
public class FbaReplenishmentLabelAttrServiceImpl implements FbaReplenishmentLabelAttrService {

    @Autowired
    private FbaReplenishmentLabelAttrDao labelAttrDao;

    @Override
    public List<FbaReplenishmentLabelAttrEntity> findAll() {
        List<FbaReplenishmentLabelAttrEntity> labelAttrList = labelAttrDao.findAll();

        return labelAttrList;
    }

    @Override
    public FbaReplenishmentLabelAttrEntity findById(int labelCd) {
        return labelAttrDao.findById(labelCd);
    }

    @Override
    public void saveLabelAttr(FbaReplenishmentLabelAttrEntity labelAttr) {
        labelAttr.setDeleteStatus(0);
        labelAttrDao.saveLabelAttr(labelAttr);
    }

    @Override
    public void updateLabelAttr(FbaReplenishmentLabelAttrEntity labelAttr) {
        labelAttrDao.updateLabelAttr(labelAttr);
    }

    @Override
    public void deleteLabelAttr(int labelCd) {
        labelAttrDao.deleteLabelAttr(1, labelCd);
    }
}
