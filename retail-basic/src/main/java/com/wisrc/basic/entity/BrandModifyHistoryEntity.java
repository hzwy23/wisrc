package com.wisrc.basic.entity;

import lombok.Data;

@Data
public class BrandModifyHistoryEntity {
    private String uuid;
    private String brandId;
    private String modifyUser;
    private String modifyTime;
    private String columnName;
    private String oldValue;
    private String newValue;
    private Integer handleTypeCd;
}
