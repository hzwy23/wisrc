package com.wisrc.order.webapp.service;

import com.wisrc.order.webapp.entity.ReSendReasonEnity;

import java.util.List;

public interface ReSendReasonService {
    /**
     * 新增重新发货原因
     *
     * @param reasonEnity
     */
    void addReason(ReSendReasonEnity reasonEnity);

    /**
     * 查询所有发货原因
     */
    List<ReSendReasonEnity> getAllReason();

    /**
     * 根据id删除发货原因
     */
    void deleteReason(String reasonId);
}
