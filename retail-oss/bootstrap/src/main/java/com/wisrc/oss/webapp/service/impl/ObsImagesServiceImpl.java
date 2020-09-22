package com.wisrc.oss.webapp.service.impl;

import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import com.obs.services.model.HeaderResponse;
import com.obs.services.model.ObjectListing;
import com.obs.services.model.ObsObject;
import com.wisrc.oss.webapp.service.ObsImagesService;
import com.wisrc.crawler.webapp.utils.Result;
import com.wisrc.crawler.webapp.utils.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ObsImagesServiceImpl implements ObsImagesService {

    private final Logger logger = LoggerFactory.getLogger(ObsImagesServiceImpl.class);

    @Value("${obs.endPoint}")
    private String endPoint;

    @Value("${obs.ak}")
    private String ak;

    @Value("${obs.sk}")
    private String sk;

    @Override
    public Result createObs(String obsName) {
        try {
            ObsClient obsClient = connect();
            obsClient.createBucket(obsName);
            obsClient.close();
            return Result.success();
        } catch (IOException e) {
            logger.error("创建存储桶失败，失败原因时：{}", e.getMessage());
        } catch (ObsException e) {
            logger.error("创建存储桶失败，失败原因时：{}", e.getMessage());
        }
        return Result.failure(ResultCode.UNKNOW_ERROR);
    }

    @Override
    public Result findAll(String obsName) {
        List<String> list = new ArrayList<>();
        try {
            ObsClient obsClient = connect();
            ObjectListing objectListing = obsClient.listObjects(obsName);

            for (ObsObject obsObject : objectListing.getObjects()) {
                list.add(obsObject.getBucketName());
            }
            obsClient.close();
            return Result.success(list);
        } catch (IOException e) {
            logger.error("关闭连接失败，失败信息时：{}", e.getMessage());
        } catch (ObsException e) {
            logger.error("连接华为存储桶失败，失败原因是：{}", e.getMessage());
        }
        return Result.failure(ResultCode.UNKNOW_ERROR);

    }

    @Override
    public Result deleteObs(String obsName) {
        try {
            ObsClient obsClient = connect();
            HeaderResponse headerResponse = obsClient.deleteBucket(obsName);
            logger.info("删除操作，返回信息是：{}", headerResponse.toString());
            obsClient.close();
            return Result.success();
        } catch (ObsException e) {
            logger.error("连接华为存储桶失败,失败原因是: {}", e.getMessage());
        } catch (IOException e) {
            logger.error("关闭连接失败，失败原因是：", e.getMessage());
        }
        return Result.failure();
    }

    @Override
    public boolean isExists(String obsName) {
        ObsClient obsClient = null;
        try {
            obsClient = connect();
            return obsClient.headBucket(obsName);
        } catch (ObsException e) {
            logger.error("连接华为存储桶失败，失败原因是：{}", e.getMessage());
        } finally {
            try {
                obsClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private ObsClient connect() throws ObsException {
        return new ObsClient(ak, sk, endPoint);
    }
}
