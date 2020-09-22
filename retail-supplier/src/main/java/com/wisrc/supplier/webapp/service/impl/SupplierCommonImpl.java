package com.wisrc.supplier.webapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisrc.supplier.webapp.entity.Supplier;
import com.wisrc.supplier.webapp.entity.SupplierAccount;
import com.wisrc.supplier.webapp.service.SupplierCommonService;
import com.wisrc.supplier.webapp.dao.SupplierCommonDao;
import com.wisrc.supplier.webapp.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SupplierCommonImpl implements SupplierCommonService {

    @Autowired
    private SysServiceImpl sysServiceImpl;
    @Autowired
    private SupplierCommonDao supplierCommonDao;

    @Override
    public List<Map<String, Object>> getSuppliersById(String[] supplierId) {
        List<Map<String, Object>> results = new ArrayList<>();
        List<Supplier> suppliers = supplierCommonDao.getSuppliersById(supplierId);
        for (Supplier supplier : suppliers) {
            Map<String, Object> map = new HashMap<>();
            map.put("supplierId", supplier.getSupplierId());
            map.put("supplierName", supplier.getSupplierName());
            map.put("contacts", supplier.getContacts());
            map.put("telephone", supplier.getTelephone());
            map.put("status", supplier.getStatus());
            map.put("updateTime", supplier.getUpdateTime());
            map.put("createTime", supplier.getCreateTime());
            map.put("mender", sysServiceImpl.getUserInfo((String) supplier.getMender()));
            results.add(map);
        }
        return results;
    }

    @Override
    public Result getSupplierAccount(String sId, Integer auditStatus, Integer type, String bank,
                                     String payee) {
        PageHelper.startPage(1, 0);
        List<SupplierAccount> supplierAccount = supplierCommonDao.getSupplierAccount(sId, auditStatus, type, bank, payee);
        PageInfo<SupplierAccount> pageInfo = new PageInfo<>(supplierAccount);
        Map<String, Object> map = new HashMap<>();
        map.put("total", pageInfo.getTotal());
        map.put("pages", pageInfo.getPages());
        map.put("accountData", pageInfo.getList());
        return Result.success(map);
    }

}