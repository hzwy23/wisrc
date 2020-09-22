package com.wisrc.shipment.webapp.vo.earlyWarningLevelAttr;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class BatchEarlyWarningLevelAttrVO {
    @NotNull
    @ApiModelProperty(value = "预警", required = true)
    private List<EarlyWarningLevelAttrVO> warningList;

}
