package com.wisrc.oss.webapp.controller;

import com.wisrc.oss.webapp.service.ObsObjectService;
import com.wisrc.oss.webapp.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api(tags = "文件上传与下载服务")
@RequestMapping(value = "/images")
public class FileUploadController {

    private final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @Autowired
    private ObsObjectService obsObjectService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ApiOperation(value = "上传文件", notes = "上传文件时，必须指定存储桶")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "obsName", value = "存储桶名称", dataType = "string", paramType = "query")
    })
    public Result upload(@RequestPart("file") MultipartFile file,
                         @RequestParam("obsName") String obsName) {
        String fileName = file.getOriginalFilename();
        String typ = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        logger.debug("文件名称是：{}, 文件类型是：{}", file, typ);

        try {
            InputStream inputStream = file.getInputStream();
            return obsObjectService.addObject(obsName, inputStream, typ);

        } catch (IOException e) {
            logger.error("上传文件失败，失败原因是：{}", e.getMessage());
            return Result.failure(423, "上传文件失败", e.getMessage());
        }
    }

    @RequestMapping(value = "/upload/stream", method = RequestMethod.POST)
    @ApiOperation(value = "上传文件", notes = "上传文件时，必须指定存储桶")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "obsName", value = "存储桶名称", dataType = "string", paramType = "query")
    })
    public Result upload(@RequestBody() InputStream file,
                         @RequestParam("fileType") String fileType,
                         @RequestParam("obsName") String obsName) {

        logger.debug("文件名称是：{}, 文件类型是：{}", file, fileType);

        return obsObjectService.addObject(obsName, file, fileType);

    }

    @RequestMapping(value = "/upload/bytes", method = RequestMethod.POST)
    @ApiOperation(value = "上传文件", notes = "上传文件时，必须指定存储桶")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "obsName", value = "存储桶名称", dataType = "string", paramType = "query")
    })
    public Result upload(@RequestBody byte[] file,
                         @RequestParam("fileType") String fileType,
                         @RequestParam("obsName") String obsName) {

        logger.debug("文件名称是：{}, 文件类型是：{}", file, fileType);

        return obsObjectService.addObject(obsName, new ByteArrayInputStream(file), fileType);

    }


    @RequestMapping(value = "/resource/{obsName}/{uuid}", method = RequestMethod.GET)
    @ApiOperation(value = "根据资源uuid，下载文件资源", notes = "根据存储桶名称，uuid值连接成地址，下载文件资源，uuid是上传文件时系统自动生成", produces = "image")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "obsName", value = "存储桶名称", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "uuid", value = "文件名称", required = true, paramType = "path", dataType = "String")
    })
    public void file(@PathVariable("obsName") String obsName,
                     @PathVariable("uuid") String uuid,
                     HttpServletResponse response) {
        if (obsName == null || uuid == null || obsName.equals("null") || uuid.equals("null")) {
            return;
        }
        Result result = obsObjectService.findFileByKey(obsName, uuid);
        if (result.getCode() != 200) {
            logger.error("获取文件信息失败，请联系管理员,失败信息是：{}", result.getMsg());
            BufferedImage bufferedImage = (BufferedImage) result.getData();
            try {
                response.setContentType("image/png");
                ImageIO.write(bufferedImage, "png", response.getOutputStream());
                response.getOutputStream().flush();
            } catch (IOException e) {
                logger.error("返回数据失败，失败信息是：{}", e.getMessage());
            }
            return;
        }

        try {
            Map<String, Object> map = (HashMap<String, Object>) result.getData();
            response.setContentType(map.get("contentType").toString());
            byte[] bytes = (byte[]) map.get("data");

            response.getOutputStream().write(bytes);
            response.getOutputStream().flush();
        } catch (IOException e) {
            logger.error("返回数据失败，失败信息是：{}", e.getMessage());
            return;
        }
    }
}
