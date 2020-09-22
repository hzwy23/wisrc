package com.wisrc.code.webapp.service.impl;

import com.wisrc.code.webapp.dao.PropertiesDao;
import com.wisrc.code.webapp.entity.PropertiesEntity;
import com.wisrc.code.webapp.service.PropertiesService;
import com.wisrc.code.webapp.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PropertiesServiceImpl implements PropertiesService {
    @Autowired
    private PropertiesDao propertiesDao;

    @Override
    public Result getKey(List keys) {
        try {
            Map result = new HashMap();
            List<PropertiesEntity> pripertiesList = propertiesDao.getKey(keys);
            for (PropertiesEntity properties : pripertiesList) {
                result.put(properties.getName(), properties.getValue());
            }
            return Result.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }
}
