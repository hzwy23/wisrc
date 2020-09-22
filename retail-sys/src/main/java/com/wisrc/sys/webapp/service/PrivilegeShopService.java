package com.wisrc.sys.webapp.service;

import com.wisrc.sys.webapp.entity.SysPrivilegeShopEntity;
import com.wisrc.sys.webapp.utils.Result;

import java.util.List;

public interface PrivilegeShopService {

    Result getPrivilegeShop(int pageNum, int pageSize, String authId, String platformName, String storeName, String mskuId);

    Result insert(List<SysPrivilegeShopEntity> entity);

    Result deletePrivilegeShop(String commodityId, String privilegeCd);

    Result getPrivilegeShopUnauth(int pageNum, int pageSize, String authId, String platformName, String storeName, String mskuId);

    Result searchUserCommodity(String userId, String[] commodityIdList,
                               String[] roleIdList,
                               String[] privilegeCdList,
                               String deptCd,
                               String[] positionCdList,
                               String[] employeeIdList);
}
