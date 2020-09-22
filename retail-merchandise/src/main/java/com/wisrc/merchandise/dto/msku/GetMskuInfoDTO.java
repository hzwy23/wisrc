package com.wisrc.merchandise.dto.msku;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class GetMskuInfoDTO {
    @ApiModelProperty(value = "批量获取的msku信息")
    private List<MskuInfoDTO> mskuInfoBatch;
}
