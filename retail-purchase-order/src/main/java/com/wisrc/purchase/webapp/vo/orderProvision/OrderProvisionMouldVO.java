package com.wisrc.purchase.webapp.vo.orderProvision;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

@Api(tags = "条款模板")
public class OrderProvisionMouldVO {
    @ApiModelProperty(value = "唯一标识（修改时才传参）")
    private String uuid;
    @ApiModelProperty(value = "条款说明")
    private String explainName;
    @ApiModelProperty(value = "条款内容")
    private String provisionContent;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getExplainName() {
        return explainName;
    }

    public void setExplainName(String explainName) {
        this.explainName = explainName;
    }

    public String getProvisionContent() {
        return provisionContent;
    }

    public void setProvisionContent(String provisionContent) {
        this.provisionContent = provisionContent;
    }
}
