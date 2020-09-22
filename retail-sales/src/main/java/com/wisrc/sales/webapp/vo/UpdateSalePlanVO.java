package com.wisrc.sales.webapp.vo;

import java.util.List;

public class UpdateSalePlanVO {
    private double weight;
    private String planDate;
    private List<UpdateSalePlanCycleDetailsVo> voList;

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getPlanDate() {
        return planDate;
    }

    public void setPlanDate(String planDate) {
        this.planDate = planDate;
    }

    public List<UpdateSalePlanCycleDetailsVo> getVoList() {
        return voList;
    }

    public void setVoList(List<UpdateSalePlanCycleDetailsVo> voList) {
        this.voList = voList;
    }
}
