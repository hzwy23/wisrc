package com.wisrc.warehouse.webapp.entity;

public class WarehouseDocumentTypeEntity {
    private String documentType;
    private String documentTypeName;

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentTypeName() {
        return documentTypeName;
    }

    public void setDocumentTypeName(String documentTypeName) {
        this.documentTypeName = documentTypeName;
    }

    @Override
    public String toString() {
        return "WarehouseDocumentTypeEntity{" +
                "documentType=" + documentType +
                ", documentTypeName='" + documentTypeName + '\'' +
                '}';
    }
}
