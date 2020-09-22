package com.wisrc.replenishment.webapp.vo;

import io.swagger.annotations.ApiModelProperty;

public class CustomsOrderVO {
    @ApiModelProperty(value = "运单号", required = true)
    private String waybillId;
    @ApiModelProperty(value = "报关单号")
    private String customsDeclareNumber;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "报关资料电子档")
    private String customsInfoDoc;
    @ApiModelProperty(value = "报关单电子档")
    private String declarationNumberDoc;
    @ApiModelProperty(value = "放行通知书")
    private String letterReleaseDoc;

    public String getWaybillId() {
        return waybillId;
    }

    public void setWaybillId(String waybillId) {
        this.waybillId = waybillId;
    }

    public String getCustomsDeclareNumber() {
        return customsDeclareNumber;
    }

    public void setCustomsDeclareNumber(String customsDeclareNumber) {
        this.customsDeclareNumber = customsDeclareNumber;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCustomsInfoDoc() {
        return customsInfoDoc;
    }

    public void setCustomsInfoDoc(String customsInfoDoc) {
        this.customsInfoDoc = customsInfoDoc;
    }

    public String getDeclarationNumberDoc() {
        return declarationNumberDoc;
    }

    public void setDeclarationNumberDoc(String declarationNumberDoc) {
        this.declarationNumberDoc = declarationNumberDoc;
    }

    public String getLetterReleaseDoc() {
        return letterReleaseDoc;
    }

    public void setLetterReleaseDoc(String letterReleaseDoc) {
        this.letterReleaseDoc = letterReleaseDoc;
    }
}
