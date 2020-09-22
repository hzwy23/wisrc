package com.wisrc.sales.webapp.vo;

import com.wisrc.sales.webapp.entity.SalePlanCycleDetailsEntity;
import com.wisrc.sales.webapp.entity.SalePlanInfoEntity;

import java.util.List;

public class AddSalePlanVO {
    private String planDate;
    private double weight;
    private SalePlanInfoEntity entity;
    private List<SalePlanCycleDetailsEntity> list;

    public String getPlanDate() {
        return planDate;
    }

    public void setPlanDate(String planDate) {
        this.planDate = planDate;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public SalePlanInfoEntity getEntity() {
        return entity;
    }

    public void setEntity(SalePlanInfoEntity entity) {
        this.entity = entity;
    }

    public List<SalePlanCycleDetailsEntity> getList() {
        return list;
    }

    public void setList(List<SalePlanCycleDetailsEntity> list) {
        this.list = list;
    }
}
