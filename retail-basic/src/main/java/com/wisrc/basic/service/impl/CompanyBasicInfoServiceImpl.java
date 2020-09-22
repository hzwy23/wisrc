package com.wisrc.basic.service.impl;

import com.wisrc.basic.dao.CompanyBasicInfoDao;
import com.wisrc.basic.entity.CompanyBasicInfoEntity;
import com.wisrc.basic.entity.CompanyCustomsInfoEntity;
import com.wisrc.basic.service.CompanyBasicInfoService;
import com.wisrc.basic.vo.CompanyBasicInfoAllVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CompanyBasicInfoServiceImpl implements CompanyBasicInfoService {
    @Autowired
    CompanyBasicInfoDao companyBasicInfoDao;

    @Override
    public CompanyBasicInfoEntity findBasicInfo() {
        return companyBasicInfoDao.findBasicInfo();
    }

    @Override
    public CompanyCustomsInfoEntity findCustomsInfo() {
        return companyBasicInfoDao.findCustomsInfo();
    }

    @Override
    public void addBasicInfo(CompanyBasicInfoEntity ele) {
        companyBasicInfoDao.addBasicInfo(ele);
    }

    @Override
    public void addCustomsInfo(CompanyCustomsInfoEntity ele) {
        companyBasicInfoDao.addCustomsInfo(ele);
    }

    @Override
    public void updateBasicInfo(CompanyBasicInfoEntity ele) {
        companyBasicInfoDao.updateBasicInfo(ele);
    }

    @Override
    public void updateCustomsInfo(CompanyCustomsInfoEntity ele) {
        companyBasicInfoDao.updateCustomsInfo(ele);
    }

    @Override
    public void saveInfo(CompanyBasicInfoAllVO vo) {
        CompanyBasicInfoEntity basicEntity = toBasicEntity(vo);
        CompanyCustomsInfoEntity CustomsEntity = toCystomsEntity(vo);
        int num = companyBasicInfoDao.findbars();
        //判断是新增信息还是修改保存信息
        if (0 == num) {
            String id = java.util.UUID.randomUUID().toString();
            basicEntity.setCompanyOnlyId(id);
            CustomsEntity.setSoleId(UUID.randomUUID().toString());
            CustomsEntity.setCompanyOnlyId(id);
            companyBasicInfoDao.addBasicInfo(basicEntity);
            companyBasicInfoDao.addCustomsInfo(CustomsEntity);
        } else {
            companyBasicInfoDao.updateBasicInfo(basicEntity);
            companyBasicInfoDao.updateCustomsInfo(CustomsEntity);
        }
    }


    private CompanyBasicInfoEntity toBasicEntity(CompanyBasicInfoAllVO vo) {
        CompanyBasicInfoEntity entity = new CompanyBasicInfoEntity();
        BeanUtils.copyProperties(vo, entity);
        return entity;
    }

    private CompanyCustomsInfoEntity toCystomsEntity(CompanyBasicInfoAllVO vo) {
        CompanyCustomsInfoEntity entity = new CompanyCustomsInfoEntity();
        BeanUtils.copyProperties(vo, entity);
        return entity;
    }
}
