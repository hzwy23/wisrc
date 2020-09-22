package com.wisrc.rules.webapp.dto.msku;

import lombok.Data;

@Data
public class MskuInfoDTO {
    private String id;
    private String mskuId;
    private String shopId;
    private String shopName;
    private String mskuName;
    private String skuId;
    private String parentAsin;
    private String groupId;
    private String userId;
    private Integer salesStatusCd;
    private String salesStatusDesc;
    private String updateTime;
    private Integer mskuStatusCd;
    private String asin;
    private String fnSkuId;
    private String shelfDate;
    private Integer deliveryMode;
    private String deliveryModeDesc;
    private String picture;
}
