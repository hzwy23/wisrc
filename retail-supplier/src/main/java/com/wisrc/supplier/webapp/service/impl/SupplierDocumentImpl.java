package com.wisrc.supplier.webapp.service.impl;

import com.wisrc.supplier.webapp.dao.SupplierDocumentDao;
import com.wisrc.supplier.webapp.service.SupplierDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class SupplierDocumentImpl implements SupplierDocumentService {

    @Autowired
    private SupplierDocumentDao supplierDocumentDao;

    @Override
    @Transactional
    public Map<String, Object> setSupplierAnnex(String name, Integer id) {
        Map<String, Object> map = new HashMap<>();
        map.put("success", supplierDocumentDao.setSupplierAnnex(name, id));
        return map;
    }

    @Override
    @Transactional
    public Map<String, Object> delSupplierAnnex(Integer id) {
        Map<String, Object> map = new HashMap<>();
        map.put("success", supplierDocumentDao.delSupplierAnnex(id));
        return map;
    }

}
