package com.wisrc.product.webapp.service;

import com.wisrc.product.webapp.utils.Result;

import java.lang.reflect.InvocationTargetException;

public interface ProductModifyHistoryService {
    Result getHistory(String skuId);

    void historyInsert(Object object, String createTime) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException;

    void historyUpdate(Object oldObj, Object newObj, String updateTime,String modifyUserId) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException;


    void deleteBySkuId(String skuId);

    void historyUpdateList(String skuId, String createUser, String oldString, String newString, String time, String column, boolean b);

}
