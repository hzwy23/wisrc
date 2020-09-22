package com.wisrc.shipment.webapp.entity;

import lombok.Data;

import java.sql.Timestamp;


@Data
public class SmartReplenishmentInfoEntity {
    private String uuid;
    private String id;
    private String shopId;
    private String shopName;
    private String mskuId;
    private String mskuName;
    private Integer fbaOnWarehouseStockNum;
    private Integer fbaOnWayStockNum;
    private Integer safetyStockDays;
    private String skuId;
    private String productName;
    private String employeeName;
    private Integer salesStatusCd;
    private String salesStatusDesc;
    private String picture;

    private Integer onWarehouseAvailableDay;
    private Integer onWayAvailableDay;
    private Integer allAvailableDay;

    private Timestamp createTime;
    private String earlyWarningLevelCd;

    private String earlyWarningLevelDesc;

    private String asin;
    private Integer mskuSafetyStockDays;

}
