package com.wisrc.purchase.webapp.vo.entrywarehouse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Api
public class ReturnEntryPara {
    @NotEmpty(message = "入库单[returnEntryList]集合不能为空")
    @ApiModelProperty(value = "采购入库单号集合")
    private List<String> returnEntryList;

    public List<String> getReturnEntryList() {
        return returnEntryList;
    }

    public void setReturnEntryList(List<String> returnEntryList) {
        this.returnEntryList = returnEntryList;
    }
}
