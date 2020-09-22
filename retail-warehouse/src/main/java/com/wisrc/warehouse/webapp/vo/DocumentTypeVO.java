package com.wisrc.warehouse.webapp.vo;

import com.wisrc.warehouse.webapp.entity.WarehouseDocumentTypeEntity;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

public class DocumentTypeVO {
    @ApiModelProperty(value = "出入库单据类型", required = true)
    private int documentType;
    @ApiModelProperty(value = "出入库单据类型名称", required = true)
    private String documentTypeName;

    public static final DocumentTypeVO toVO(WarehouseDocumentTypeEntity ele) {
        DocumentTypeVO vo = new DocumentTypeVO();
        BeanUtils.copyProperties(ele, vo);
        return vo;
    }

    public int getDocumentType() {
        return documentType;
    }

    public void setDocumentType(int documentType) {
        this.documentType = documentType;
    }

    public String getDocumentTypeName() {
        return documentTypeName;
    }

    public void setDocumentTypeName(String documentTypeName) {
        this.documentTypeName = documentTypeName;
    }

}
