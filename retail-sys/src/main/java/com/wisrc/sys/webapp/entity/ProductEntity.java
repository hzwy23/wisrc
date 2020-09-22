package com.wisrc.sys.webapp.entity;

import lombok.Data;

@Data
public class ProductEntity {
    private String typeId;
    private String typeName;
    private String parentId;
    private String parentName;
}
