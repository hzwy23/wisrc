package com.wisrc.replenishment.webapp.query.waybillInfo;

import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class WayBillInfoQuery {
    private Date waybillOrderDateBegin;
    private Date waybillOrderDateEnd;
    private List<String> offerIds;
    private Integer customsCd;
    private String warehouseId;
    private Integer logisticsTypeCd;
    private String Keyword;
    private String waybillId;
    private String batchNumber;
    private String fbaReplenishmentId;
    private List mskuIds;
    private String isLackLogistics;
    private String isLackShipment;
    private String isLackCustomsDeclare;
    private String isLackCustomsClearanc;
}
