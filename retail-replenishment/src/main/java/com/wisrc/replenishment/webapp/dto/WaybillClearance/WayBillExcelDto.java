package com.wisrc.replenishment.webapp.dto.WaybillClearance;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class WayBillExcelDto {
    @ApiModelProperty(value = "公司名称（卖方）")
    private String sellCompanyName;
    @ApiModelProperty(value = "联系人")
    private String sellContact;
    @ApiModelProperty(value = "发货地址")
    private String sendAddress;
    @ApiModelProperty(value = "公司名称（买方）")
    private String buyCompanyName;
    @ApiModelProperty(value = "vat税号")
    private String vatNo;
    @ApiModelProperty(value = "收货地址")
    private String receiveAddress;
    @ApiModelProperty(value = "发票备注")
    private String invoiceRemark;
    @ApiModelProperty(value = "申报总金额")
    private String declareAmount;
    @ApiModelProperty(value = "创建日期")
    private String createTime;
    @ApiModelProperty(value = "清关信息")
    private List<ClearanceExcelDto> customClearances;
}
