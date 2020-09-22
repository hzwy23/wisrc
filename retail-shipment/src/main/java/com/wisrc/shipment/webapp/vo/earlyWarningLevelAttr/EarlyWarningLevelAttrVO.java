package com.wisrc.shipment.webapp.vo.earlyWarningLevelAttr;

import com.wisrc.shipment.webapp.utils.Time;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
public class EarlyWarningLevelAttrVO {

    @ApiModelProperty(value = "预警等级编码")
    private String earlyWarningLevelCd;

    @NotBlank
    @ApiModelProperty(value = "预警等级描述")
    private String earlyWarningLevelDesc;

    @Range(min = 0, message = "内部处理（天）不能为负数")
    @ApiModelProperty(value = "内部处理（天）")
    private Integer internalProcessingDays;

    @Range(min = 0, message = "头程运输（天）不能为负数")
    @ApiModelProperty(value = "头程运输（天）")
    private Integer headTransportationDays;

    @Range(min = 0, message = "签收入仓(天)不能为负数")
    @ApiModelProperty(value = "签收入仓(天)")
    private Integer signedIncomeWarehouseDays;

    @Range(min = 0, message = "时效天数(天)不能为负数")
    @ApiModelProperty(value = "时效天数(天)")
    private Integer agingDays;

    @NotNull
    @ApiModelProperty(value = "是否启用（0：不启用，1：启用）", required = true)
    private Integer enableFlag;

    @ApiModelProperty(hidden = true)
    private String createTime;

    public void setCreateTime(Timestamp createTime) {
        if (createTime == null) {
            this.createTime = "";
        } else {
            this.createTime = Time.format(createTime);
        }
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}

