package com.wisrc.replenishment.webapp.vo.wms;


import java.util.List;

public class AddProcessTaskBillVO {
    private ProcessTaskBillEntity entity;
    private List<ProcessDetailEntity> entityList;

    public ProcessTaskBillEntity getEntity() {
        return entity;
    }

    public void setEntity(ProcessTaskBillEntity entity) {
        this.entity = entity;
    }

    public List<ProcessDetailEntity> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<ProcessDetailEntity> entityList) {
        this.entityList = entityList;
    }
}
