package com.wisrc.merchandise.service.impl;

import com.wisrc.merchandise.dao.MskuStatusDao;
import com.wisrc.merchandise.entity.MskuStatusAttrEntity;
import com.wisrc.merchandise.service.MskuStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MskuStatusServiceImpl implements MskuStatusService {
    @Autowired
    private MskuStatusDao mskuStatusDao;

    @Override
    public List<Map<String, Object>> getMskuStatusList() {
        List<Map<String, Object>> mskuStatus = new ArrayList();

        List<MskuStatusAttrEntity> statusList = mskuStatusDao.getMskuStatus();
        for (MskuStatusAttrEntity sta : statusList) {
            Map<String, Object> status = new HashMap<>();
            status.put("id", sta.getMskuStatusCd());
            status.put("name", sta.getMskuStatusDesc());
            mskuStatus.add(status);
        }

        return mskuStatus;
    }

    @Override
    public Map<Integer, Object> getMskuStatusMap() {
        Map<Integer, Object> mskuStatus = new HashMap<>();

        List<MskuStatusAttrEntity> statusList = mskuStatusDao.getMskuStatus();
        for (MskuStatusAttrEntity sta : statusList) {
            Map statusMap = new HashMap();
            statusMap.put("id", sta.getMskuStatusCd());
            statusMap.put("name", sta.getMskuStatusDesc());
            mskuStatus.put(sta.getMskuStatusCd(), statusMap);
        }

        return mskuStatus;
    }

    @Override
    public List<Map<String, Object>> mapToList(Map<String, Map> mskuStatusMap) {
        List<Map<String, Object>> mskuStatus = new ArrayList();

        for (Object id : mskuStatusMap.keySet()) {
            Map statusMap = null;
            try {
                statusMap = mskuStatusMap.get(id);
            } catch (Exception e) {
                e.printStackTrace();
                statusMap = null;
            }

            Map<String, Object> status = new HashMap<>();
            status.put("id", id);
            status.put("name", statusMap.get("name"));
            mskuStatus.add(status);
        }

        return mskuStatus;
    }
}
