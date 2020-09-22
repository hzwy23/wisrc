package com.wisrc.supplier.webapp.service.impl;

import com.wisrc.supplier.webapp.entity.SupplierAccount;
import com.wisrc.supplier.webapp.dao.SupplierAccountDao;
import com.wisrc.supplier.webapp.service.SupplierAccountService;
import com.wisrc.supplier.webapp.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class SupplierAccountImpl implements SupplierAccountService {

    @Autowired
    private SysServiceImpl sysServiceImpl;
    @Autowired
    private SupplierAccountDao supplierAccountDao;

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> getSupplierAccounts(String supplierId, String supplierName, Integer types,
                                                   Integer pageSize, Integer currentPage) {
        // 结果转换
        Map<String, Object> supplierAccounts = new HashMap<>();
        for (Map<String, Object> supplierAccount : supplierAccountDao.getSupplierAccounts(supplierId, supplierName)) {
            String supplierIdKey = (String) supplierAccount.get("supplierId");
            if (supplierAccounts.get(supplierIdKey) == null) {
                Map<String, Object> item = new HashMap<>();
                item.put("supplierId", supplierIdKey);
                item.put("supplierName", supplierAccount.get("supplierName"));
                item.put("updateTime", supplierAccount.get("updateTime"));
                item.put("mender", sysServiceImpl.getUserInfo((String) supplierAccount.get("mender")));
                Map<Integer, Integer> statusMap = new HashMap<>();
                statusMap.put(0, 0);
                statusMap.put(1, 0);
                statusMap.put(2, 0);
                if (supplierAccount.get("auditStatus") != null) {
                    Integer auditStatus = (Integer) supplierAccount.get("auditStatus");
                    statusMap.put(auditStatus, 1);
                }
                item.put("statusMap", statusMap);
                supplierAccounts.put(supplierIdKey, item);
            } else {
                Map<String, Object> supplierAccountsMap = (Map<String, Object>) supplierAccounts.get(supplierIdKey);
                Map<Integer, Integer> statusMap = (Map<Integer, Integer>) supplierAccountsMap.get("statusMap");
                Integer auditStatus = (Integer) supplierAccount.get("auditStatus");
                statusMap.put(auditStatus, statusMap.get(auditStatus) + 1);
                supplierAccountsMap.put("statusMap", statusMap);
            }
        }
        List<Map<String, Object>> list = new ArrayList<>();
        for (String key : supplierAccounts.keySet()) {
            Map<String, Object> item = (Map<String, Object>) supplierAccounts.get(key);
            Map<Integer, Integer> statusMap = (Map<Integer, Integer>) item.get("statusMap");
            if (types != null && statusMap.get(types) != null && statusMap.get(types) > 0) {
                list.add(item);
            } else if (types == null) {
                list.add(item);
            }
        }
        // 编号倒序
        Collections.sort(list, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                String name1 = (String) o1.get("supplierId");
                String name2 = (String) o2.get("supplierId");
                return name2.compareTo(name1);
            }
        });
        // 分页查询
        Object[] array = list.toArray();
        List<Object> pagination = new ArrayList<>();
        for (int i = (currentPage - 1) * pageSize; i < currentPage * pageSize; i++) {
            System.out.println(i);
            if (i >= array.length) {
                break;
            }
            pagination.add(array[i]);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("pageSize", pageSize);
        map.put("currentPage", currentPage);
        map.put("supplierAccounts", pagination);
        map.put("supplierAccountCount", list.size());
        return map;
    }

    @Override
    @Transactional
    public boolean addSupplierAccount(SupplierAccount account) {
        account.setAuditStatus(0);
        account.setUpdateTime(new Date());
        account.setCreateTime(new Date());
        return supplierAccountDao.addSupplierAccount(account);
    }

    @Override
    @Transactional
    public boolean setSupplierAccount(SupplierAccount account) {
        account.setAuditStatus(0);
        account.setUpdateTime(new Date());
        return supplierAccountDao.setSupplierAccount(account);
    }

    @Override
    @Transactional
    public Result verifySupplierAccount(Integer id, Integer auditStatus, String auditInfo) {
        if (supplierAccountDao.verifySupplierAccount(id, auditStatus, auditInfo)) {
            return Result.success();
        }
        return Result.failure(500, "请检查编号", null);
    }

    @Override
    @Transactional
    public Map<String, Object> delSupplierAccount(Integer id) {
        Map<String, Object> map = new HashMap<>();
        map.put("success", supplierAccountDao.delSupplierAccount(id));
        return map;
    }

}
