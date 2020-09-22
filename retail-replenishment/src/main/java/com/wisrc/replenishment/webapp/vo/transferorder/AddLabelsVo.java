package com.wisrc.replenishment.webapp.vo.transferorder;

import java.util.List;

public class AddLabelsVo {
    private String transferOrderId;
    List<Integer> labels;

    public String getTransferOrderId() {
        return transferOrderId;
    }

    public void setTransferOrderId(String transferOrderId) {
        this.transferOrderId = transferOrderId;
    }

    public List<Integer> getLabels() {
        return labels;
    }

    public void setLabels(List<Integer> labels) {
        this.labels = labels;
    }
}
