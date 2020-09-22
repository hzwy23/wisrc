package com.wisrc.supplier.webapp.service;

import java.util.Map;

public interface SupplierDocumentService {

    // 供应商文件 更新
    Map<String, Object> setSupplierAnnex(String name, Integer id);

    // 供应商文件 删除
    Map<String, Object> delSupplierAnnex(Integer id);

}
