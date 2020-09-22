package com.wisrc.merchandise.dto.msku;

import lombok.Data;

import java.sql.Date;

@Data
public class SalesStatusAutoDto {
    private String id;
    private String mskuId;
    private String shopId;
    private Integer salesStatusCd;
    private Integer fbaOnWarehouseStockNum;
    private Date storeInTime;
    private Integer avgSales;
    private Integer avgStore;
    private String asin;
    private String marketplaceId;
}
