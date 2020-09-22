package com.wisrc.basic.service;

import com.wisrc.basic.entity.BrandInfoEntity;
import com.wisrc.basic.entity.BrandModifyHistoryEntity;
import com.wisrc.basic.utils.Result;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

@Service
public interface BrandModifyHistoryService {
    Result findByBrandId(BrandModifyHistoryEntity entity);

    void historyUpdate(Object oldObj, Object newObj, String updateTime) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException;

    void historyInsert(BrandInfoEntity entity, String createTime) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException;
}
