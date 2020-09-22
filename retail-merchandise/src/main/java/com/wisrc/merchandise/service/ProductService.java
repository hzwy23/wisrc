package com.wisrc.merchandise.service;


import com.wisrc.merchandise.utils.Result;

import java.util.List;
import java.util.Map;

public interface ProductService {
    Result getProductInfl();

    List getProductSelector() throws Exception;

    List getFindKeyDealted(String findKey) throws Exception;

    Map getProductCN(List<String> skuId) throws Exception;

    List getProductPicture(List<String> skuId) throws Exception;

    List getByIdAndName(String id, String name) throws Exception;

    Map getProductSales(List<String> skuIds) throws Exception;
}


