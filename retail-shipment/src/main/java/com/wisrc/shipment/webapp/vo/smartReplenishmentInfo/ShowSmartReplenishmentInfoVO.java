package com.wisrc.shipment.webapp.vo.smartReplenishmentInfo;

import com.wisrc.shipment.webapp.utils.Time;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ShowSmartReplenishmentInfoVO {
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

    private String createTime;
    private String earlyWarningLevelCd;

    private String earlyWarningLevelDesc;

    private String asin;
    private Integer mskuSafetyStockDays;

    public void setCreateTime(Timestamp createTime) {
        if (createTime == null) {
            this.createTime = "";
        } else {
            this.createTime = Time.format(createTime);
        }
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

}
