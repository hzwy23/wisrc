package com.wisrc.purchase.webapp.service;

import com.wisrc.purchase.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(value = "retail-purchase-images-03", url = "http://localhost:8080")
public interface ObjectStorageService {
    /**
     * 获取采购订单上传文件
     *
     * @return
     */
    @RequestMapping(value = "/images/upload", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Result uploadFile(@RequestPart("file") MultipartFile file, @RequestParam("obsName") String obsName);

    /**
     * 采购订单条款上传bytes形式
     *
     * @param bytes
     * @param obsName  存储桶名称
     * @param fileType 这个字段是文件的拓展名
     * @return
     */
    @RequestMapping(value = "/images/upload/bytes", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result uploadFileByte(@RequestPart("file") byte[] bytes,
                          @RequestParam("obsName") String obsName,
                          @RequestParam("fileType") String fileType);


    @RequestMapping(value = "/images/resource/{obsName}/{uuid}", method = RequestMethod.GET)
    String downloadFile(@PathVariable("obsName") String obsName, @PathVariable("uuid") String uuid);

    /**
     * 公共渲染pdf服务
     *
     * @param htmlContentVO
     * @return
     */
    @RequestMapping(value = "/images/generatePdf/fromHTML", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    byte[] renderPDF(@RequestPart("htmlContentVO") String htmlContentVO);
}
