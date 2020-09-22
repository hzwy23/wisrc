package com.wisrc.merchandise.service.impl;

import com.wisrc.merchandise.dto.crawler.MskuCrawlerDto;
import com.wisrc.merchandise.service.CrawlerOutsideService;
import com.wisrc.merchandise.service.CrawlerService;
import com.wisrc.merchandise.utils.Result;
import com.wisrc.merchandise.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CrawlerServiceImpl implements CrawlerService {
    @Autowired
    private CrawlerOutsideService crawlerOutsideService;

    public Integer getAvgSales(String mskuId, String shopId) throws Exception {
        Result sevenDataReslt = crawlerOutsideService.getAvgSales(mskuId, shopId);
        if (sevenDataReslt.getCode() != 200) {
            throw new Exception(sevenDataReslt.getMsg());
        }
        List<Map> sevenDataList = (List) sevenDataReslt.getData();
        if (sevenDataList.size() == 0) {
            return 0;
        }
        int total = 0;
        int num = 0;
        for (Map sevenData : sevenDataList) {
            Integer lastNum = (Integer) sevenData.get("lastNum");
            total += lastNum;
            num++;
        }

        try {
            int result = total / num;
            return result;
        } catch (Exception e) {
            return 0;
        }
    }

    public MskuCrawlerDto getMskuCrewler(String mskuId, String sellerId) throws Exception {
        MskuCrawlerDto mskuCrawlerDto = new MskuCrawlerDto();

        Result crawlerResult = crawlerOutsideService.getMskuCrewler(mskuId, sellerId);
        if (crawlerResult.getCode() != 200) {
            throw new Exception(crawlerResult.getMsg());
        }

        Map crawlerData = ServiceUtils.LinkedHashMapToMap(crawlerResult.getData());

        mskuCrawlerDto.setAsin((String) crawlerData.get("asin"));
        mskuCrawlerDto.setFnsku((String) crawlerData.get("fnsku"));

        return mskuCrawlerDto;
    }
}
