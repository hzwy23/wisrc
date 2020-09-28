package com.wisrc.oss.webapp.service.impl;

import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import com.obs.services.model.ObsObject;
import com.obs.services.model.PutObjectResult;
import com.wisrc.oss.webapp.service.ObsObjectService;
import com.wisrc.oss.webapp.utils.Result;
import com.wisrc.oss.webapp.utils.ResultCode;
import com.wisrc.oss.webapp.utils.Toolbox;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class ObsObjectServiceImpl implements ObsObjectService {

    private final Logger logger = LoggerFactory.getLogger(ObsObjectServiceImpl.class);

    // 本地图片
    private BufferedImage localImageBuffer;

    @Value("${obs.endPoint}")
    private String endPoint;

    @Value("${obs.ak}")
    private String ak;

    @Value("${obs.sk}")
    private String sk;

    @Override
    public Result addObject(String obsName, InputStream inputStream, String typ) {
        StringBuffer uuid = new StringBuffer(Toolbox.randomUUID());
        String id = uuid.append(".").append(typ).toString();

        ObsClient obsClient = null;
        try {
            obsClient = new ObsClient(ak, sk, endPoint);

            PutObjectResult putObjectRequest = obsClient.putObject(obsName, id, inputStream);

            logger.debug(putObjectRequest.toString());

            LinkedHashMap<String, String> map = new LinkedHashMap<>();
            map.put("obsName", obsName);
            map.put("uuid", id);
            return Result.success(map);
        } catch (ObsException e) {
            logger.error("上传图片失败，失败原因是：{}", e.getMessage());
            return Result.failure(423, "上传文件失败", e.getMessage());
        } finally {
            try {
                obsClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Result findFileByKey(String obsName, String key) {

        try {
            ObsClient obsClient = new ObsClient(ak, sk, endPoint);
            ObsObject obsObject = obsClient.getObject(obsName, key);
            long size = obsObject.getMetadata().getContentLength();

            // TODO
            // 如果数据超出了int类型的范围，则会出现丢失数据的情况
            // 后续根据具体情况优化
            byte[] bytes = new byte[(int) size];

            IOUtils.read(obsObject.getObjectContent(), bytes);

            Map<String, Object> map = new HashMap<>();
            map.put("contentType", obsObject.getMetadata().getContentType());
            map.put("data", bytes);
            return Result.success(map);

        } catch (IOException e) {
            logger.error("下载文件失败，失败详细信息是：{}", e.getMessage());
        } catch (ObsException e) {
            logger.error("获取文件资源失败，失败原因是：{}", e.getMessage());
        }
        return Result.failure(ResultCode.UNKNOW_ERROR, localImageBuffer);
    }

    @PostConstruct
    public void getLocalImage() {
        logger.info("读取本地友情图示图片");

        try {

            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("static/no_image.png");

            this.localImageBuffer = ImageIO.read(inputStream);

        } catch (IOException e) {

            e.printStackTrace();

        }
    }
}
