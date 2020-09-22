package com.wisrc.merchandise.entity;

import lombok.Data;

@Data
public class GetMskuEntity {
    private String id;
    private String mskuId;
    private String mskuName;
    private String shopId;
    private String skuId;
    private Integer salesStatusCd;
    private String fnSkuId;
}
