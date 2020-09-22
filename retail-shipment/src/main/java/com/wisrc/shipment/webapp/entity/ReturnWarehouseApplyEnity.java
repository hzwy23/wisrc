package com.wisrc.shipment.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class ReturnWarehouseApplyEnity {
    @ApiModelProperty(value = "退仓申请单号", hidden = true)
    private String returnApplyId;
    @NotEmpty(message = "店铺不能为空")
    @ApiModelProperty(value = "店铺Id", hidden = false, required = true)
    private String shopId;
    private List<Integer> returnTypeCd;
    private String remark;
    @ApiModelProperty(value = "创建人", hidden = true)
    private String createUser;
    @ApiModelProperty(value = "申请人员工Id", hidden = true)
    private String employeeId;
    @ApiModelProperty(value = "创建时间", hidden = true)
    private String createTime;
    @ApiModelProperty(value = "更新时间", hidden = true)
    private String updateTime;
    @ApiModelProperty(value = "更新人", hidden = true)
    private String updateUser;
    @ApiModelProperty(value = "取消或未通过原因", hidden = true)
    private String reason;
    @ApiModelProperty(value = "申请单状态", hidden = true)
    private Integer statusCd;
    @Valid
    @NotEmpty
    private List<ProductDetail> productDetailList;

    public String getReturnApplyId() {
        return returnApplyId;
    }

    public void setReturnApplyId(String returnApplyId) {
        this.returnApplyId = returnApplyId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public List<Integer> getReturnTypeCd() {
        return returnTypeCd;
    }

    public void setReturnTypeCd(List<Integer> returnTypeCd) {
        this.returnTypeCd = returnTypeCd;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<ProductDetail> getProductDetailList() {
        return productDetailList;
    }

    public void setProductDetailList(List<ProductDetail> productDetailList) {
        this.productDetailList = productDetailList;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(Integer statusCd) {
        this.statusCd = statusCd;
    }


}
