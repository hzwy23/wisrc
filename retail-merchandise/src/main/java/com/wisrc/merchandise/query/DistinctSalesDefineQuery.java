package com.wisrc.merchandise.query;

import lombok.Data;

import java.util.List;

@Data
public class DistinctSalesDefineQuery {
    private String shopId;
    private String groupId;
    private String manager;
    private String findKey;
    private List storeSkuDealted;
}
