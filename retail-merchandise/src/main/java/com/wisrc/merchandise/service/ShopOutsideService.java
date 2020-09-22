package com.wisrc.merchandise.service;


import com.wisrc.merchandise.utils.Result;

public interface ShopOutsideService {
    Result getShopSelector();

    String getSellerId(String shopId) throws Exception;
}


