package com.wisrc.merchandise.entity;

import lombok.Data;

@Data
public class MskuStockSalesInfoEntity {
    private String id;
    private String shopId;
    private String mskuId;
    private Integer fbaOnWarehouseStockNum;
    private Integer fbaOnWayStockNum;
    private Integer yesterdaySalesNum;
    private Integer dayBeforeYesterdaySalesNum;
    private Integer previousSalesNum;
}
