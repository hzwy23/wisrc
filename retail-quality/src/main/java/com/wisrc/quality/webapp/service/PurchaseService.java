package com.wisrc.quality.webapp.service;

import java.util.List;
import java.util.Map;

public interface PurchaseService {
    Map getQuality(String orderId, List skuIds) throws Exception;
}
