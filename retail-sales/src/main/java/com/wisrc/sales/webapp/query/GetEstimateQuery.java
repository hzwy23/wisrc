package com.wisrc.sales.webapp.query;

import lombok.Data;

import java.util.List;

@Data
public class GetEstimateQuery {
    private List<String> commodityIdList;
}
