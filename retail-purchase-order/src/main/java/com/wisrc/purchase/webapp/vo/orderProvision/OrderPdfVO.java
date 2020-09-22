package com.wisrc.purchase.webapp.vo.orderProvision;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderPdfVO {
    @ApiModelProperty(value = "采购订单号")
    private String orderId;
    @ApiModelProperty(value = "单据日期")
    private Date billDate;
    @ApiModelProperty(value = "签署日期")
    private Date signedOnDate;
    @ApiModelProperty(value = "公司地址")
    private String companyAddress;
    @ApiModelProperty(value = "公司名称")
    private String companyName;
    @ApiModelProperty(value = "公司固话")
    private String companyTelephone;
    @ApiModelProperty(value = "供应商ID")
    private String supplierId;
    @ApiModelProperty(value = "供应商名称")
    private String supplierName;
    @ApiModelProperty(value = "不含税金额")
    private double amountWithoutTax;
    @ApiModelProperty(value = "含税金额")
    private double amountWithTax;
    @ApiModelProperty(value = "条款")
    private String orderProvision;
    @ApiModelProperty(value = "供应商联系人")
    private String supplierPeople;
    @ApiModelProperty(value = "供应商电话")
    private String supplierPhone;
    @ApiModelProperty(value = "供应商传真")
    private String supplierFax;
    @ApiModelProperty(value = "供应商地址")
    private String supplierAddress;
    @ApiModelProperty(value = "运费")
    private String freight;
    @ApiModelProperty(value = "定金率")
    private double depositRate;
    @ApiModelProperty(value = "合计）")
    private String totalAmount;
    @ApiModelProperty(value = "合计中文）")
    private String totalAmountCN;
    @ApiModelProperty(value = "采购员")
    private String purchaser;

    private List<OrderPdfProdictVO> list;

}
