package com.wisrc.basic.service.impl;

import com.wisrc.basic.dao.VersionAnnouncementDao;
import com.wisrc.basic.entity.VersionAnnouncementEntity;
import com.wisrc.basic.entity.VersionModuleAttrEntity;
import com.wisrc.basic.service.VersionAnnouncementService;
import com.wisrc.basic.utils.Result;
import com.wisrc.basic.utils.Toolbox;
import com.wisrc.basic.vo.SelectVersionAnnouncementVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class VersionAnnouncementServiceImpl implements VersionAnnouncementService {

    @Autowired
    private VersionAnnouncementDao versionAnnouncementDao;

    @Override
    public Result add(VersionAnnouncementEntity entity) {
        entity.setUuid(Toolbox.UUIDrandom());
        versionAnnouncementDao.add(entity);
        return Result.success(200, "保存成功", entity);
    }

    @Override
    public List<SelectVersionAnnouncementVO> findAll() {
        List<VersionAnnouncementEntity> entityList = versionAnnouncementDao.findAll();
        List<SelectVersionAnnouncementVO> entityVoList = new LinkedList<>();

        List<VersionModuleAttrEntity> moduleAttrEntityList = versionAnnouncementDao.findModuleAttr();

        Map<Integer, String> moduleMap = new LinkedHashMap<>();
        for (VersionModuleAttrEntity module : moduleAttrEntityList) {
            moduleMap.put(module.getVersionModuleCd(), module.getVersionModuleName());
        }

        for (VersionAnnouncementEntity entity : entityList) {
            SelectVersionAnnouncementVO vo = new SelectVersionAnnouncementVO();
            BeanUtils.copyProperties(entity, vo);
            vo.setVersionModuleName(moduleMap.get(entity.getVersionModuleCd()));
            entityVoList.add(vo);
        }
        return entityVoList;
    }

    @Override
    public List<VersionModuleAttrEntity> findModuleAttrAll() {
        return versionAnnouncementDao.findModuleAttr();
    }

    @Override
    public void deleteVersion(String versionNumber) {
        versionAnnouncementDao.delete(versionNumber);
    }

    @Override
    public Map<String, String> getVersionNumber12() {
        Map<String, String> result = new LinkedHashMap<>();
        List<VersionAnnouncementEntity> entityList = versionAnnouncementDao.findAllByVersionNumber();
        if (entityList.size() > 2) {
            for (int i = 0; i < 2; i++) {
                VersionAnnouncementEntity entity = entityList.get(i);
                if (i == 0) {
                    result.put("firstNumber", entity.getVersionNumber());
                } else {
                    result.put("secondNumber", entity.getVersionNumber());
                }
            }
        }
        return result;
    }
}
