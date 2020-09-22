package com.wisrc.oss.webapp.service;

import com.wisrc.crawler.webapp.utils.Result;

import java.io.InputStream;

public interface ObsObjectService {

    Result addObject(String obsName, InputStream inputStream, String typ);

    Result findFileByKey(String obsName, String key);

}
