package com.wisrc.wms.webapp.vo.ReturnVO;


import io.swagger.annotations.ApiModelProperty;

/**
 * 出库状态回写ERP
 */
public class OutWarehouseStatusReturnVO {
    @ApiModelProperty("单据编号")
    private String processTaskId;
    @ApiModelProperty("状态")
    private String code;

    public String getProcessTaskId() {
        return processTaskId;
    }

    public void setProcessTaskId(String processTaskId) {
        this.processTaskId = processTaskId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
