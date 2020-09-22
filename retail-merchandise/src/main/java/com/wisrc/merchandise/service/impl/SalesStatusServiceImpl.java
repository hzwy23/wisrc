package com.wisrc.merchandise.service.impl;

import com.wisrc.merchandise.dao.MskuExtInfoDao;
import com.wisrc.merchandise.dao.MskuInfoDao;
import com.wisrc.merchandise.dto.msku.SalesStatusAutoDto;
import com.wisrc.merchandise.entity.GetMskuEditSales;
import com.wisrc.merchandise.service.AsinOutsideService;
import com.wisrc.merchandise.service.CodeService;
import com.wisrc.merchandise.service.CrawlerService;
import com.wisrc.merchandise.service.SalesStatusService;
import com.wisrc.merchandise.utils.Result;
import com.wisrc.merchandise.utils.Time;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class SalesStatusServiceImpl implements SalesStatusService {
    @Autowired
    private CodeService codeService;
    @Autowired
    private MskuInfoDao mskuInfoDao;
    @Autowired
    private MskuExtInfoDao mskuExtInfoDao;
    @Autowired
    private CrawlerService crawlerService;
    @Autowired
    private AsinOutsideService asinOutsideService;

    @Override
    public List<Map<String, Object>> getSalesStatusList() throws Exception {
        List<Map<String, Object>> salesStatus = new ArrayList<>();

        Map<Integer, String> statusList = codeService.getSalesStatus();
        for (Integer id : statusList.keySet()) {
            Map<String, Object> status = new HashMap<>();
            status.put("id", id);
            status.put("name", statusList.get(id));
            salesStatus.add(status);
        }

        return salesStatus;
    }

    @Override
    public Map<Integer, Object> getSalesStatusMap() throws Exception {
        Map<Integer, Object> salesStatus = new HashMap<>();

        Map<Integer, String> statusList = codeService.getSalesStatus();

        for (Integer id : statusList.keySet()) {
            Map statusMap = new HashMap();
            statusMap.put("id", id);
            statusMap.put("name", statusList.get(id));
            salesStatus.put(id, statusMap);
        }

        return salesStatus;
    }

    @Override
    public List<Map<String, Object>> mapToList(Map<String, Map> salesStatusMap) {
        List<Map<String, Object>> salesStatus = new ArrayList<>();

        for (Object id : salesStatusMap.keySet()) {
            Map statusMap = null;
            try {
                statusMap = salesStatusMap.get(id);
            } catch (Exception e) {
                e.printStackTrace();
                statusMap = null;
            }

            Map<String, Object> status = new HashMap<>();
            status.put("id", id);
            status.put("name", statusMap.get("name"));
            salesStatus.add(status);
        }

        return salesStatus;
    }

    @Override
    public Result getSelector() {
        List<Map<String, Object>> selector;
        try {
            selector = getSalesStatusList();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
        return Result.success(selector);
    }

    @Override
    public void editStatus() {
        try {
            List<SalesStatusAutoDto> autoList = new ArrayList();
            List<GetMskuEditSales> mskuEditSales = mskuInfoDao.getMskuAutoSales();
            for (GetMskuEditSales editSales : mskuEditSales) {
                SalesStatusAutoDto autoDto = new SalesStatusAutoDto();
                BeanUtils.copyProperties(editSales, autoDto);

                autoList.add(autoDto);
            }

            List halt = Arrays.asList(new int[]{1, 2, 3, 4});
            for (SalesStatusAutoDto auto : autoList) {
                try {
                    if (auto.getSalesStatusCd() == null) {
                        continue;
                    }
                    if (auto.getFbaOnWarehouseStockNum() == null) {
                        auto.setFbaOnWarehouseStockNum(0);
                    }
                    if (auto.getSalesStatusCd() == 0 && auto.getFbaOnWarehouseStockNum() > 0) {
                        auto.setSalesStatusCd(1);
                        if (auto.getStoreInTime() == null) {
                            auto.setStoreInTime(Time.getCurrentDate());
                            mskuInfoDao.editStoreInTime(auto);
                        }
                    }
                    if (halt.contains(auto.getSalesStatusCd())) {
                        //  判断是否为unavaliable
                        List keys = new ArrayList();
                        String key = "amazon.mws.url";
                        keys.add(key);
                        Map<String, String> url = codeService.getKey(keys);
                        Boolean unavaliable = asinOutsideService.checkAvaliable(auto.getAsin(), auto.getMarketplaceId(), url.get(key));
                        if (auto.getSalesStatusCd() != 4 && auto.getFbaOnWarehouseStockNum() > 0 && !unavaliable) {
                            auto.setSalesStatusCd(4);
                        } else if (auto.getSalesStatusCd() == 4 && unavaliable) {
                            auto.setSalesStatusCd(1);
                        }
                    }
                    if (auto.getSalesStatusCd() == 1) {
                        int days = (int) ((Time.getCurrentDate().getTime() - auto.getStoreInTime().getTime()) / (1000 * 3600 * 24));
                        if (days > 31) {
                            auto.setSalesStatusCd(2);
                            auto.setStoreInTime(Time.getCurrentDate());
                        }
                    }
                    if (auto.getSalesStatusCd() == 2) {
                        int days = (int) ((Time.getCurrentDate().getTime() - auto.getStoreInTime().getTime()) / (1000 * 3600 * 24));
                        if (days > 7) {
                            auto.setAvgSales(crawlerService.getAvgSales(auto.getMskuId(), auto.getShopId()));
                            if (auto.getAvgSales() <= 5) {
                                auto.setSalesStatusCd(3);
                            }
                        }
                    }
                    if (auto.getSalesStatusCd() == 3) {
                        if (auto.getAvgSales() == null) {
                            auto.setAvgSales(crawlerService.getAvgSales(auto.getMskuId(), auto.getShopId()));
                        }
                        if (auto.getAvgSales() == 0 && auto.getAvgStore() == 0) {
                            auto.setSalesStatusCd(5);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }

                mskuInfoDao.editSalesStatus(auto);

            }

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
