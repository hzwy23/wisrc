package com.wisrc.basic.service;

import com.wisrc.basic.entity.VersionAnnouncementEntity;
import com.wisrc.basic.entity.VersionModuleAttrEntity;
import com.wisrc.basic.utils.Result;
import com.wisrc.basic.vo.SelectVersionAnnouncementVO;

import java.util.List;
import java.util.Map;

public interface VersionAnnouncementService {

    Result add(VersionAnnouncementEntity entity);

    List<SelectVersionAnnouncementVO> findAll();

    List<VersionModuleAttrEntity> findModuleAttrAll();

    void deleteVersion(String versionNumber);

    Map<String, String> getVersionNumber12();
}
