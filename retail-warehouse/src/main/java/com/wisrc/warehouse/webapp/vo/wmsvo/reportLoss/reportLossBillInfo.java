package com.wisrc.warehouse.webapp.vo.wmsvo.reportLoss;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel
public class reportLossBillInfo {

    List<reportLossBillDetail> goodsList;
    @ApiModelProperty(value = "单据编号", required = true)
    private String voucherCode;
    @ApiModelProperty(value = "仓库编号", required = true)
    private String whCode;
    @ApiModelProperty(value = "分仓编号", required = true)
    private String sectionCode;
    @ApiModelProperty(value = "备注")
    private String remark;

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public String getWhCode() {
        return whCode;
    }

    public void setWhCode(String whCode) {
        this.whCode = whCode;
    }

    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<reportLossBillDetail> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<reportLossBillDetail> goodsList) {
        this.goodsList = goodsList;
    }
}
