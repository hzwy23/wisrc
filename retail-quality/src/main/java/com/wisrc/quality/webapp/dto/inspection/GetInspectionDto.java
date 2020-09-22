package com.wisrc.quality.webapp.dto.inspection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@ApiModel(description = "获取验货表单")
public class GetInspectionDto {
    @ApiModelProperty(value = "验货产品")
    List<GetInspectionProductDto> InspectionProduct;
    @ApiModelProperty(value = "申请单号")
    private String inspectionId;
    @ApiModelProperty(value = "日期")
    private long applyDate;
    @ApiModelProperty(value = "申请人")
    private Map employee;
    @ApiModelProperty(value = "采购订单号")
    private String orderId;
    @ApiModelProperty(value = "运费")
    private Double freight;
    @ApiModelProperty(value = "运费分摊原则")
    private Map amrtType;
    @ApiModelProperty(value = "预计验货时间")
    private long expectInspectionTime;
    @ApiModelProperty(value = "验货方式")
    private Map inspectionType;
    @ApiModelProperty(value = "供应商")
    private Map supplier;
    @ApiModelProperty(value = "供应商联系人")
    private String supplierContactUser;
    @ApiModelProperty(value = "电话")
    private String supplierPhone;
    @ApiModelProperty(value = "供应商地址")
    private String supplierAddr;
    @ApiModelProperty(value = "备注")
    private String remark;
}
