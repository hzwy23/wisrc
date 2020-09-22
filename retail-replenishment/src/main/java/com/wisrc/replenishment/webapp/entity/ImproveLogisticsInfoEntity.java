package com.wisrc.replenishment.webapp.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ImproveLogisticsInfoEntity {
    @ApiModelProperty(value = "运单号", required = true)
    private String waybillId;
    @ApiModelProperty(value = "物流单号/集装箱号")
    private String logisticsId;
    @ApiModelProperty(value = "签收日期")
    private String signInDate;
    @ApiModelProperty(value = "物流面单")
    private String logisticsSurfaceUrl;
    @ApiModelProperty(value = "检测报告")
    private String examiningReportUrl;
    @ApiModelProperty(value = "S/O No.")
    private String soNoUrl;
    @ApiModelProperty(value = "货运公司代号", required = true)
    private String logisticsTrackUrl;
    @ApiModelProperty(value = "货运公司代号编码", required = true)
    private Integer codeCd;
}
