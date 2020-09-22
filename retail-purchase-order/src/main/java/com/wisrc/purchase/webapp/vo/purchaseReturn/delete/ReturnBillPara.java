package com.wisrc.purchase.webapp.vo.purchaseReturn.delete;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@ApiModel
public class ReturnBillPara {
    @NotEmpty(message = "退货单号[returnBillList]集合不能为空")
    @ApiModelProperty(value = "采购拒收单号集合")
    private List<String> returnBillList;
}
