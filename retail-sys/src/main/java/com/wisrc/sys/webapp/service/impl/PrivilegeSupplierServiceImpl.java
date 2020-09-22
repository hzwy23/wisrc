package com.wisrc.sys.webapp.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageHelper;
import com.wisrc.sys.webapp.dao.PrivilegeSupplierDao;
import com.wisrc.sys.webapp.entity.GatherEntity;
import com.wisrc.sys.webapp.entity.SysPrivilegeSupplierEntity;
import com.wisrc.sys.webapp.service.proxy.OutSupplierService;
import com.wisrc.sys.webapp.service.PrivilegeSupplierService;
import com.wisrc.sys.webapp.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PrivilegeSupplierServiceImpl implements PrivilegeSupplierService {
    private static final String pageSizePara = "0"; //与接口方约定使用"0"  为不分页全部查询
    private static final String currentPage = "0";
    private final Logger logger = LoggerFactory.getLogger(PrivilegeSupplierServiceImpl.class);
    @Autowired
    private PrivilegeSupplierDao privilegeSupplierDao;
    @Autowired
    private OutSupplierService outSupplierService;

    /**
     * Date转Sring
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        return dateString;
    }

    @Override
    public Result getPrivilegeSupplier(int pageNum, int pageSize, String authId, String supplierId, String supplierName) {
        PageHelper.startPage(pageNum, pageSize);
        List<GatherEntity> supplierList = privilegeSupplierDao.getPrivilegeSupplierAuth(authId);
        //取出全部数据
        Object responseObj = null;
        try {
            responseObj = outSupplierService.getSupplierFuzzy(supplierId, supplierName, pageSizePara, currentPage);
        } catch (Exception e) {
            logger.error("调取外部接口出错", e);
            return new Result(9999, "查询供应商接口出错！", e);
        }
        List<Object> resultList = new ArrayList<>();
        JSONObject jsonObject = JSONUtil.parseObj(responseObj);
        Object suppliersListObj = jsonObject.get("suppliers");
        JSONArray array = JSONUtil.parseArray(suppliersListObj);
        for (GatherEntity obj : supplierList) {
            for (Object obj2 : array) {
                JSONObject json = JSONUtil.parseObj(obj2);
                String supplierIdStr = json.getStr("supplierId");
                if (supplierIdStr.equals(obj.getSupplierCd())) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("supplierId", json.get("supplierId"));
                    map.put("supplierName", json.get("supplierName"));
                    map.put("authUser", obj.getUserName());
                    map.put("authTime", getStringDate(obj.getCreateTime()));
                    resultList.add(map);
                    break;
                }
            }
        }
        //根据前端打包VO分页
        Map<String, Object> map = new HashMap();
        int total = resultList.size();
        if (pageSize == 0) {
            map.put("total", resultList.size());
            map.put("pages", 1);
            map.put("authSupplierList", resultList);
            return Result.success(map);
        }
        int pages = (total) % (pageSize) > 0 ? (total) / (pageSize) + 1 : (total) / (pageSize);
        int start = (pageNum - 1) * (pageSize);
        int end = (pageNum) * (pageSize) - 1;
        if (end > (total - 1)) {
            end = total - 1;
        }
        List<Object> newList = new ArrayList<>();
        if (start == end) {
            newList.add(resultList.get(start));
        } else {
            newList = resultList.subList(start, (end + 1));
        }
        map.put("total", total);
        map.put("pages", pages);
        map.put("authSupplierList", newList);
        return Result.success(map);
    }

    @Override
    @Transactional(transactionManager = "retailSystemTransactionManager")
    public Result insert(List<SysPrivilegeSupplierEntity> list) {
        List<SysPrivilegeSupplierEntity> ret = new ArrayList<>();
        for (SysPrivilegeSupplierEntity entity : list) {
            try {
                privilegeSupplierDao.insert(entity);
            } catch (DuplicateKeyException e) {
                //重复授权就是更新创建人与创建时间
                privilegeSupplierDao.update(entity);
                ret.add(entity);
            }
        }
        return Result.success(200, "添加供应商授权信息成功", ret);
    }

    //模糊分页查找获取未授权供应商
    @Override
    public Result getPrivilegeShopUnauth(int pageNum, int pageSize, String authId, String supplierId, String supplierName) {

        //拥有该授权的供应商集合
        List<GatherEntity> supplierList = privilegeSupplierDao.getPrivilegeSupplierAuth(authId);
        //调用接口 获取你的模糊查询接口  传模糊参数 参数
        Object responseObj = null;
        try {
            responseObj = outSupplierService.getSupplierFuzzy(supplierId, supplierName, pageSizePara, currentPage);

        } catch (Exception e) {
            logger.error("调取外部供应商接口出错", e);
            return new Result(9999, "查询供应商接口出错！", e);
        }
        JSONObject jsonObject = JSONUtil.parseObj(responseObj);
        Object storeInfoListObj = jsonObject.get("suppliers");
        JSONArray array = JSONUtil.parseArray(storeInfoListObj);

        List<Object> tempList = new ArrayList<>();
        //该授权下无店铺，即所有店铺为未授权
        if (supplierList.size() == 0) {
            for (Object obj : array) {
                JSONObject json = JSONUtil.parseObj(obj);
                Map<String, Object> map = new HashMap<>();
                map.put("supplierId", json.get("supplierId"));
                map.put("supplierName", json.get("supplierName"));
                tempList.add(map);
            }
        } else {
            Set<String> localSet = new HashSet<>();
            Set<String> outSet = new HashSet<>();
            for (GatherEntity obj : supplierList) {
                localSet.add(obj.getSupplierCd());
            }
            for (Object obj : array) {
                JSONObject json = JSONUtil.parseObj(obj);
                outSet.add(json.getStr("supplierId"));
            }
            outSet.removeAll(localSet);
            for (Object obj : array) {
                JSONObject json = JSONUtil.parseObj(obj);
                String supplierIdStr = json.getStr("supplierId");
                for (String sId : outSet) {
                    if (sId.equals(supplierIdStr)) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("supplierId", json.get("supplierId"));
                        map.put("supplierName", json.get("supplierName"));
                        tempList.add(map);
                    }
                }
            }
        }
        if (pageSize != 0) {
            //根据前端打包VO分页
            int total = tempList.size();
            int pages = (total) % (pageSize) > 0 ? (total) / (pageSize) + 1 : (total) / (pageSize);
            int start = (pageNum - 1) * (pageSize);
            int end = (pageNum) * (pageSize) - 1;
            if (end > (total - 1)) {
                end = total - 1;
            }
            List<Object> newList = new ArrayList<>();
            if (start == end) {
                newList.add(tempList.get(start));
            } else {
                newList = tempList.subList(start, (end + 1));
            }
            Map<String, Object> map = new HashMap();
            map.put("total", total);
            map.put("pages", pages);
            map.put("unAuthSupplierList", newList);
            return Result.success(map);
        } else {
            Map<String, Object> map = new HashMap();
            map.put("total", tempList.size());
            map.put("pages", 1);
            map.put("unAuthSupplierList", tempList);
            return Result.success(map);
        }
    }

    @Override
    public Result deletePrivilegeSupplierId(SysPrivilegeSupplierEntity entity) {
        privilegeSupplierDao.deletePrivilegeSupplierId(entity);
        return Result.success();
    }
}


