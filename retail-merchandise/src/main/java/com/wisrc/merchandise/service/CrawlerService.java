package com.wisrc.merchandise.service;

import com.wisrc.merchandise.dto.crawler.MskuCrawlerDto;

public interface CrawlerService {
    Integer getAvgSales(String mskuId, String shopId) throws Exception;

    MskuCrawlerDto getMskuCrewler(String mskuId, String sellerId) throws Exception;
}
