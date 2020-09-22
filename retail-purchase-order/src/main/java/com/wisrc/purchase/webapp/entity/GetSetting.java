package com.wisrc.purchase.webapp.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetSetting extends PurchaseSettingEntity {
    @ApiModelProperty(value = "计算周期描述")// 天、周
    private String calculateCycleDesc;
    @ApiModelProperty(value = "计算量度描述")
    private String calculateCycleWeekDesc;// 一、二、三、四、五、六、七
}
