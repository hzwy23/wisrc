package com.wisrc.quality.webapp.service;


import java.util.List;
import java.util.Map;


public interface QualityProductHandleService {
    Map getProductCNNameMap(List skuId) throws Exception;

    List getFindKeyDealted(String findKey) throws Exception;
}
