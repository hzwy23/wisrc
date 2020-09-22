package com.wisrc.replenishment.webapp.dto.logisticsPlan;

import com.wisrc.replenishment.webapp.vo.PageVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class GetMskuPageDto extends PageVo {
    @ApiModelProperty(value = "所属店铺", required = false)
    private String shopId;

    @ApiModelProperty(value = "负责人", required = false)
    private String employeeId;

    @ApiModelProperty(value = "销售状态", required = false)
    private Integer salesStatusCd;

    @ApiModelProperty(value = "关键字（ASIN/MSKU/库存SKU/产品名称）", required = false)
    private String findKey;

    @ApiModelProperty(value = "销售状态查询范围")
    private List salesStatusList;

    @ApiModelProperty(value = "是否获取已删除数据", required = false)
    private Integer doesDelete;
}
