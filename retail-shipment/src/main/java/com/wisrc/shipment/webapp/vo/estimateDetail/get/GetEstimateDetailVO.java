package com.wisrc.shipment.webapp.vo.estimateDetail.get;

import lombok.Data;

@Data
public class GetEstimateDetailVO {
    private String id;
    private String shopId;
    private String mskuId;
    private String startTime;
    private String endTime;
    private Integer fbaOnWarehouseStockNum;
    private Integer fbaOnWayStockNum;
}
