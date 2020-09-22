package com.wisrc.order.webapp.vo.saleNvoice;

import com.wisrc.order.webapp.utils.valid.PositiveDouble;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "物流费用导入信息")
public class FreightExcelVo {
    @ApiModelProperty(value = "发货物流渠道")
    private String channelName;

    @ApiModelProperty(value = "物流单号")
    private String logisticsId;

    @ApiModelProperty(value = "物流费用")
    @PositiveDouble(message = "物流费用必须为2位小数")
    private String freight;
}
