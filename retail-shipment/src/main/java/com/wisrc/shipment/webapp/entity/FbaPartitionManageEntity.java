package com.wisrc.shipment.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

public class FbaPartitionManageEntity {

    @ApiModelProperty(value = "分区编号")
    private String partitionId;

    @ApiModelProperty(value = "分区名称")
    private String partitionName;

    @ApiModelProperty(value = "所属国家")
    private String countryCd;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("更改人")
    private String modifyUser;

    @ApiModelProperty("更改时间")
    private String modifyTime;

    @ApiModelProperty("状态")
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

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public int getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(int statusCd) {
        this.statusCd = statusCd;
    }

    @Override
    public String toString() {
        return "FbaPartitionManageEntity{" +
                "partitionId='" + partitionId + '\'' +
                ", partitionName='" + partitionName + '\'' +
                ", countryCd='" + countryCd + '\'' +
                ", createUser='" + createUser + '\'' +
                ", createTime='" + createTime + '\'' +
                ", modifyUser='" + modifyUser + '\'' +
                ", modifyTime='" + modifyTime + '\'' +
                ", statusCd=" + statusCd +
                '}';
    }
}
