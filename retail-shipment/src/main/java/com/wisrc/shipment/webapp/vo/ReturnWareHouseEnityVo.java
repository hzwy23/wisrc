package com.wisrc.shipment.webapp.vo;

import com.wisrc.shipment.webapp.entity.ReceiveWarehouseEnity;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class ReturnWareHouseEnityVo {
    @ApiModelProperty(value = "退仓申请单号", hidden = true)
    private String returnApplyId;
    private String shopId;
    private List<String> removeOrderList;
    private List<String> returnTypeName;
    private List<String> returnTypeCd;
    private String remark;
    @ApiModelProperty(value = "取消或未通过原因", hidden = true)
    private String reason;
    @ApiModelProperty(value = "收货仓Id", hidden = true)
    private String warehouseId;
    @ApiModelProperty(value = "收货仓名", hidden = true)
    private String receiveWarehouseName;
    @ApiModelProperty(value = "收货Id", hidden = true)
    private String receiveWarehouseId;
    @ApiModelProperty(value = "发货仓名", hidden = true)
    private String sendWarehouseName = "Amazon";
    @ApiModelProperty(value = "申请时间", hidden = true)
    private String createTime;
    @ApiModelProperty(value = "申请人Id", hidden = true)
    private String employeeId;
    @ApiModelProperty(value = "店铺名", hidden = true)
    private String shopName;
    @ApiModelProperty(value = "状态", hidden = true)
    private Integer statusCd;
    @ApiModelProperty(value = "状态描述", hidden = true)
    private String statusCdDesc;
    @ApiModelProperty(value = "收货仓明细", hidden = true)
    private ReceiveWarehouseEnity wareHouseDetail;
    private List<ProductDetaiVo> productDetaiVoList;

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

    public List<String> getRemoveOrderList() {
        return removeOrderList;
    }

    public void setRemoveOrderList(List<String> removeOrderList) {
        this.removeOrderList = removeOrderList;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public List<ProductDetaiVo> getProductDetaiVoList() {
        return productDetaiVoList;
    }

    public void setProductDetaiVoList(List<ProductDetaiVo> productDetaiVoList) {
        this.productDetaiVoList = productDetaiVoList;
    }

    public String getReceiveWarehouseName() {
        return receiveWarehouseName;
    }

    public void setReceiveWarehouseName(String receiveWarehouseName) {
        this.receiveWarehouseName = receiveWarehouseName;
    }

    public String getSendWarehouseName() {
        return sendWarehouseName;
    }

    public void setSendWarehouseName(String sendWarehouseName) {
        this.sendWarehouseName = sendWarehouseName;
    }

    public List<String> getReturnTypeName() {
        return returnTypeName;
    }

    public void setReturnTypeName(List<String> returnTypeName) {
        this.returnTypeName = returnTypeName;
    }

    public Integer getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(Integer statusCd) {
        this.statusCd = statusCd;
    }

    public String getStatusCdDesc() {
        return statusCdDesc;
    }

    public void setStatusCdDesc(String statusCdDesc) {
        this.statusCdDesc = statusCdDesc;
    }

    public ReceiveWarehouseEnity getWareHouseDetail() {
        return wareHouseDetail;
    }

    public void setWareHouseDetail(ReceiveWarehouseEnity wareHouseDetail) {
        this.wareHouseDetail = wareHouseDetail;
    }

    public List<String> getReturnTypeCd() {
        return returnTypeCd;
    }

    public void setReturnTypeCd(List<String> returnTypeCd) {
        this.returnTypeCd = returnTypeCd;
    }

    public String getReceiveWarehouseId() {
        return receiveWarehouseId;
    }

    public void setReceiveWarehouseId(String receiveWarehouseId) {
        this.receiveWarehouseId = receiveWarehouseId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }


}
