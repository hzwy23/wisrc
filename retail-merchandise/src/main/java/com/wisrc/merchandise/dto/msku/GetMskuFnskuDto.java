package com.wisrc.merchandise.dto.msku;

import lombok.Data;

@Data
public class GetMskuFnskuDto {
    private String id;
    private String mskuId;
    private String mskuName;
    private String shopId;
    private String skuId;
    private String productName;
    private String salesStatus;
    private String fnsku;
    private String picture;
}
