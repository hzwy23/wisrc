package com.wisrc.warehouse.webapp.vo.swagger;

import com.wisrc.warehouse.webapp.vo.DocumentTypeVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class DocumentResponseModel {
    @ApiModelProperty(value = "返回状态吗", position = 1)
    private int code;

    @ApiModelProperty(value = "返回消息", position = 2)
    private int msg;

    @ApiModelProperty(value = "出入库单据类型", position = 3)
    private DocumentTypeVO documentTypeVOList;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getMsg() {
        return msg;
    }

    public void setMsg(int msg) {
        this.msg = msg;
    }

    public DocumentTypeVO getDocumentTypeVOList() {
        return documentTypeVOList;
    }

    public void setDocumentTypeVOList(DocumentTypeVO documentTypeVOList) {
        this.documentTypeVOList = documentTypeVOList;
    }
}
