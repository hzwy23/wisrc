package com.wisrc.shipment.webapp.vo;

import io.swagger.annotations.ApiModelProperty;

public class SelectFbaPartitionManageVO {

    @ApiModelProperty(value = "分区编号")
    private String partitionId;

    @ApiModelProperty(value = "分区名称")
    private String partitionName;

    @ApiModelProperty(value = "国家")
    private String countryCd;

    @ApiModelProperty(value = "状态")
    private int statusCd;


    public String getPartitionId() {
        return partitionId;
    }

    public void setPartitionId(String partitionId) {
        this.partitionId = partitionId;
    }

    public String getPartitionName() {
        return partitionName;
    }

    public void setPartitionName(String partitionName) {
        this.partitionName = partitionName;
    }

    public String getCountryCd() {
        return countryCd;
    }

    public void setCountryCd(String countryCd) {
        this.countryCd = countryCd;
    }

    public int getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(int statusCd) {
        this.statusCd = statusCd;
    }
}
