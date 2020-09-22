package com.wisrc.replenishment.webapp.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface MskuInfoListService {

    /**
     * 根据商品Id集合查询商品信息
     *
     * @param idlist
     * @return
     * @throws Exception
     */
    Map getMskuInfoList(List<String> idlist) throws Exception;

    /**
     * 根据产品id,产品名称模糊查询商品相关信息
     *
     * @return
     * @throws Exception
     */
    List getMskuList(String employeeId, String skuId, String productName) throws Exception;

    Set<String> getCommitIdList(String asin, String skuId, String productName);
}
