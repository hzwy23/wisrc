package com.wisrc.product.webapp.entity;

import lombok.Data;

@Data
public class CustomLabelEntity {

    private String uuid;
    private String skuId;

    private Integer labelCd;
    private String labelText;
    private String labelDesc;
    private Integer typeCd;
}
