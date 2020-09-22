package com.wisrc.warehouse.webapp.service;

import com.wisrc.warehouse.webapp.entity.ProcessTaskBillEntity;
import com.wisrc.warehouse.webapp.entity.ProcessTaskStatusAttrEntity;
import com.wisrc.warehouse.webapp.utils.Result;
import com.wisrc.warehouse.webapp.vo.AddProcessTaskBillVO;

import java.util.LinkedHashMap;
import java.util.List;

public interface ProcessTaskBillService {
    Result add(AddProcessTaskBillVO vo);

    List<ProcessTaskBillEntity> getProcessTaskBillAll();

    LinkedHashMap getProcessTaskBillAll(int startPage, int pageSize);

    List<ProcessTaskBillEntity> search(String processStartDate, String processEnd, String processLaterSku, int statusCd, String warehouseId, String createUser, String productName);

    LinkedHashMap getProcessTaskBillAll(int startPage, int pageSize, String processStartDate, String processEndDate, String processLaterSku, int statusCd, String warehouseId, String createUser, String productName);

    LinkedHashMap getProcessTaskBillByProcessTaskId(String processTaskId);

    List<ProcessTaskStatusAttrEntity> getStatusList();

    ProcessTaskStatusAttrEntity getStatusDetail(String statusCd);

    void update(String processTaskId);

    Result changeStatus(String processTaskId, String status);

    List<ProcessTaskBillEntity> getStatusCdByfbaId(String fbaReplenishmentId);
}
