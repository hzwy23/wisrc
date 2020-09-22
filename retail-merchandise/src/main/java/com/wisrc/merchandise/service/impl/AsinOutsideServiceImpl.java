package com.wisrc.merchandise.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.wisrc.merchandise.service.AsinOutsideService;
import com.wisrc.merchandise.service.CodeService;
import com.wisrc.merchandise.utils.OutsideUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class AsinOutsideServiceImpl implements AsinOutsideService {
    @Autowired
    private CodeService codeService;

    private OutsideUtil outsideUtil = new OutsideUtil();

    public JSONObject checkMsku(String msku, String sellerId, String url) throws Exception {
//        String url = propertiesDao.getKey("amazon.mws.url");
        String strURL = url + "/inventoryInfo/getInventorySupply?msku=" + msku + "&sellerId=" + sellerId;

        Map<String, String> header = new HashMap<>();
        header.put("userAgent", "smartdo.scc");

        return outsideUtil.relayRequest(null, null, strURL, header);
    }

    public Date getShelfTime(String asin, String url) throws Exception {
//        String url = propertiesDao.getKey("amazon.keepa.url");
        String strURL = url + "/saleTime/new/first/" + asin;

        Map<String, String> header = new HashMap<>();
        header.put("userAgent", "smartdo.scc");

        JSONObject shelfTimeJson = outsideUtil.relayRequest(null, null, strURL, header);
        Date shelfTime = shelfTimeJson.getDate("data");

        return shelfTime;
    }

    public JSONObject getParentAsin(String asin, String marketPlaceId, String url) throws Exception {
        String strURL = url + "/productInfo/getProductCategory?asin=" + asin + "&marketPlaceId=" + marketPlaceId;

        Map<String, String> header = new HashMap<>();
        header.put("userAgent", "smartdo.scc");

        return outsideUtil.relayRequest(null, null, strURL, header);
    }

    public Timestamp getShelfTimestamp(String asin) {
        Timestamp time = null;

        List keys = new ArrayList<>();
        keys.add("amazon.mws.asin");
        Map<String, String> urls;
        try {
            urls = codeService.getKey(keys);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        String url = urls.get("amazon.mws.asin");

        try {
            Date shelfTime = getShelfTime(asin, url);
            time = new Timestamp(shelfTime.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            time = null;
        }

        return time;
    }

    public Boolean checkAvaliable(String asin, String marketPlaceId, String url) throws Exception {
        String strURL = url + "/logistics/api/productInfo/isSales?asin=" + asin + "&marketplaceId=" + marketPlaceId;

        Map<String, String> header = new HashMap<>();

        JSONObject json = outsideUtil.relayRequest(null, null, strURL, header);
        Boolean check = json.getBoolean("data");
        return check;
    }
}
