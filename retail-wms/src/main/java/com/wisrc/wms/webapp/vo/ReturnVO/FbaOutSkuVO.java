package com.wisrc.wms.webapp.vo.ReturnVO;

import com.wisrc.wms.webapp.vo.PackInfoVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class FbaOutSkuVO {
    @ApiModelProperty("行号")
    private String lineNumber;
    @ApiModelProperty("库存skuId")
    private String skuId;
    @ApiModelProperty("fnSkuId")
    private String fnSkuId;
    @ApiModelProperty("该sku对应的装箱规格")
    private List<PackInfoVO> packTypeList;


}
