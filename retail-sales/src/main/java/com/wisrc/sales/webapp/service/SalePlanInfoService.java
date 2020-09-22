package com.wisrc.sales.webapp.service;

import com.wisrc.sales.webapp.vo.DetailSalePlanVO;

import java.util.LinkedHashMap;

public interface SalePlanInfoService {
    /*void add(List<AddSalePlanVO> voList) throws Exception;*/

    LinkedHashMap findByCond(int num, int size, String shopId, String msku, String asin, String stockSku,
                             String commodityName, String salesStatusCd, String userId);

    LinkedHashMap findByPage(int num, int size, String userId);

    LinkedHashMap findAll(String shopId, String msku, String asin, String stockSku,
                          String commodityName, String salesStatusCd, String userId);

    void add(DetailSalePlanVO vo);
}
