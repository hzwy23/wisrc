package com.wisrc.purchase.webapp.vo;

import com.wisrc.purchase.webapp.entity.OrderAttachmentInfoEntity;
import com.wisrc.purchase.webapp.utils.valid.PositiveDouble;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@ApiModel
@Data
public class OrderBasisInfoVO {
    @ApiModelProperty(value = "采购订单号", position = 1)
    private String orderId;
    @ApiModelProperty(value = "单据日期", required = true, position = 2)
    private Date billDate;
    @ApiModelProperty(value = "供应商ID", position = 3)
    private String supplierId;
    @ApiModelProperty(value = "供应商名称", position = 4)
    private String supplierName;
    @ApiModelProperty(value = "采购员ID", position = 5)
    private String employeeId;
    @ApiModelProperty(value = "采购员名字")
    private String employeeName;
    @ApiModelProperty(value = "不含税金额", position = 6)
    @PositiveDouble(message = "不含税金额必须为2位小数")
    private double amountWithoutTax;
    @ApiModelProperty(value = "含税金额", position = 7)
    @PositiveDouble(message = "含税金额必须为2位小数")
    private double amountWithTax;
    @ApiModelProperty(value = "是否开票类型", position = 8)
    @NotNull
    private int tiicketOpenCd;
    @ApiModelProperty(value = "报关状态", position = 9)
    @NotNull
    private int customsTypeCd;
    @ApiModelProperty(value = "付款条款", position = 10)
    private String paymentProvision;
    @ApiModelProperty(value = "交货状态", position = 11)
    private int deliveryTypeCd;
    @ApiModelProperty(value = "单据录入人", hidden = true)
    private String createUser;
    @ApiModelProperty(value = "单据录入日期", hidden = true)
    private Timestamp createTime;
    @ApiModelProperty(value = "供应商联系人", position = 11)
    private String supplierPeople;
    @ApiModelProperty(value = "供应商电话", position = 12)
    private String supplierPhone;
    @ApiModelProperty(value = "供应商传真", position = 13)
    private String supplierFax;
    @ApiModelProperty(value = "运费", position = 14)
    @PositiveDouble(message = "运费必须为2位小数")
    private double freight;
    @ApiModelProperty(value = "定金率", position = 15)
    @PositiveDouble(message = "定金率必须为2位小数")
    private double depositRate;
    @ApiModelProperty(value = "合计")
    private int totalAmount;
    @ApiModelProperty(value = "订单条款")
    private String provisionContent;
    @ApiModelProperty(value = "供应商收款人")
    private String payee;
    @ApiModelProperty(value = "供应商开户银行")
    private String bank;
    @ApiModelProperty(value = "供应商银行账户")
    private String account;
    @ApiModelProperty(value = "附件相关信息", position = 16)

    private List<OrderAttachmentInfoEntity> oderAttachmentInfoEntityList;

    @ApiModelProperty(value = "备注")
    private String remark;
}
