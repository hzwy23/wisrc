package com.wisrc.merchandise.service;

import com.alibaba.fastjson.JSONObject;

import java.sql.Timestamp;
import java.util.Date;

public interface AsinOutsideService {
    JSONObject checkMsku(String msku, String sellerId, String url) throws Exception;

    Date getShelfTime(String asin, String url) throws Exception;

    Timestamp getShelfTimestamp(String asin);

    JSONObject getParentAsin(String asin, String marketPlaceId, String url) throws Exception;

    Boolean checkAvaliable(String asin, String marketPlaceId, String url) throws Exception;
}
