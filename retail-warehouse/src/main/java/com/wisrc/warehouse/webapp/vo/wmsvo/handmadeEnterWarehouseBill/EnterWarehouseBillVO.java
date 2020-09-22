package com.wisrc.warehouse.webapp.vo.wmsvo.handmadeEnterWarehouseBill;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class EnterWarehouseBillVO {

    @ApiModelProperty("商品清单")
    List<EnterWarehouseBillDetailsVO> goodsList;
    @ApiModelProperty("手工入库单单号")
    private String voucherCode;
    @ApiModelProperty("单据类型")
    private String voucherType;
    @ApiModelProperty("分仓编号")
    private String sectionCode;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("备注")
    private String remark;

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<EnterWarehouseBillDetailsVO> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<EnterWarehouseBillDetailsVO> goodsList) {
        this.goodsList = goodsList;
    }

    @Override
    public String toString() {
        return "EnterWarehouseBillVO{" +
                "voucherCode='" + voucherCode + '\'' +
                ", voucherType='" + voucherType + '\'' +
                ", sectionCode='" + sectionCode + '\'' +
                ", createTime='" + createTime + '\'' +
                ", remark='" + remark + '\'' +
                ", goodsList=" + goodsList +
                '}';
    }
}

