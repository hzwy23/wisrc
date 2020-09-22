package com.wisrc.sys.webapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisrc.sys.webapp.dao.WarehouseDao;
import com.wisrc.sys.webapp.entity.SysPrivilegeWarehouseEntity;
import com.wisrc.sys.webapp.service.WarehouseService;
import com.wisrc.sys.webapp.utils.Result;
import com.wisrc.sys.webapp.utils.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WarehouseServiceImpl implements WarehouseService {
    private final Logger logger = LoggerFactory.getLogger(WarehouseServiceImpl.class);

    @Autowired
    private WarehouseDao warehouseDao;

    @Override
    public Result getPrivilegeWarehouse(int pageNum, int pageSize, String authId) {
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<SysPrivilegeWarehouseEntity> repositoryList = warehouseDao.getPrivilegeWarehouse(authId);
            PageHelper.startPage(pageNum, pageSize);
            List<SysPrivilegeWarehouseEntity> repositoryListUnAuth = warehouseDao.getPrivilegeWarehouseUnAuth(authId);

            Map<String, Object> dataMap = new HashMap();
            PageInfo pageInfo = new PageInfo(repositoryList);
            long total = pageInfo.getTotal();
            int pages = pageInfo.getPages();
            Map<String, Object> map = new HashMap();
            map.put("total", total);
            map.put("pages", pages);
            map.put("repositoryList", repositoryList);

            pageInfo = new PageInfo(repositoryListUnAuth);
            long totalUn = pageInfo.getTotal();
            int pagesUn = pageInfo.getPages();
            Map<String, Object> mapUn = new HashMap();
            mapUn.put("total", totalUn);
            mapUn.put("pages", pagesUn);
            mapUn.put("repositoryList", repositoryListUnAuth);

            dataMap.put("authRepositoryList", map);
            dataMap.put("unauthRepositoryList", mapUn);
            return Result.success(dataMap);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("通过权限代码查找仓库信息出错", e);
            return Result.failure(ResultCode.FIND_FAILED);
        }
    }
}
