package com.wisrc.purchase.webapp.query;

import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class GetEstimateNumQuery {
    private Date today;
    private List<SkuEstimateDateQuery> skuDate;
    private List salesStatus;
}
