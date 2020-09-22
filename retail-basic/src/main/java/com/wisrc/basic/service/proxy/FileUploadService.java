package com.wisrc.basic.service.proxy;

import com.wisrc.basic.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(value = "retail-images", url = "http://localhost:8080")
public interface FileUploadService {
    /**
     * 上传文件，制定储存桶
     *
     * @param file
     * @param fileType
     * @param obsName
     * @return
     */
    @RequestMapping(value = "/images/upload/bytes", method = RequestMethod.POST)
    Result uploadFile(@RequestBody String file, @RequestParam("fileType") String fileType, @RequestParam("obsName") String obsName);

    /**
     * 根据存储桶名称，uuid值连接成地址，下载文件资源，uuid是上传文件时系统自动生成
     *
     * @param obsName
     * @param uuid
     * @return
     */
    @RequestMapping(value = "/images/resource/{obsName}/{uuid}", method = RequestMethod.GET)
    byte[] downloadFile(@PathVariable("obsName") String obsName,
                        @PathVariable("uuid") String uuid);

}
