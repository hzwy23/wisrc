package com.wisrc.replenishment.webapp.service.impl;

import com.wisrc.replenishment.webapp.dao.FbaReplenishmentLabelRelDao;
import com.wisrc.replenishment.webapp.entity.FbaReplenishmentLabelRelEntity;
import com.wisrc.replenishment.webapp.service.FbaReplenishmentLabelRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("labelRelService")
public class FbaReplenishmentLabelRelServiceImpl implements FbaReplenishmentLabelRelService {

    @Autowired
    private FbaReplenishmentLabelRelDao labelRelDao;

    @Override
    public void saveLabelRel(FbaReplenishmentLabelRelEntity labelRelEntity) {
        labelRelDao.saveLabelRel(labelRelEntity);
    }

    @Override
    public void updateLabelStatus(String deleteStatus, String uuid) {
        labelRelDao.updateLabelStatus(deleteStatus, uuid);
    }

    @Override
    public List<String> findlabelCdByFbaId(String fbaReplenishmentId) {
        List<String> list = labelRelDao.findlabelCdByFbaId(fbaReplenishmentId);
        return list;
    }

    @Override
    public void cancelLabelStatus(int deleteStatus, String deleteRandom, String fbaReplenishmentId) {
        labelRelDao.cancelLabelStatus(deleteStatus, deleteRandom, fbaReplenishmentId);
    }

    @Override
    public List<FbaReplenishmentLabelRelEntity> findLabelEntity(String fbaReplenishmentId) {
        return labelRelDao.findLabelEntity(fbaReplenishmentId);
    }

    @Override
    public void removeLabel(int deleteStatus, String deleteRandom, String uuid) {
        labelRelDao.removeLabel(deleteStatus, deleteRandom, uuid);
    }
}
