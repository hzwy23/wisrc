package com.wisrc.replenishment.webapp.service;

import com.wisrc.replenishment.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(value = "retail-replenishment-images-25", url = "http://localhost:8080")
public interface UploadDownloadService {
    /**
     * 获取公司报关信息
     *
     * @return
     */
    @RequestMapping(value = "/images/upload", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Result uploadFile(@RequestPart("file") MultipartFile file, @RequestParam("obsName") String obsName);

    @RequestMapping(value = "/images/resource/{obsName}/{uuid}")
    byte[] downloadFile(@PathVariable("obsName") String obsName,
                        @PathVariable("uuid") String uuid);

}
