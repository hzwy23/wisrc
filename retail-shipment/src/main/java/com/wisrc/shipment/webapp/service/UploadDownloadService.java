package com.wisrc.shipment.webapp.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * UploadDownloadService
 *
 * @author MAJANNING
 * @date 2018/7/18
 */
@FeignClient(value = "retail-shipment-images-06", url = "http://localhost:8080")
public interface UploadDownloadService {
    @RequestMapping(value = "/images/resource/{obsName}/{uuid}")
    byte[] downloadFile(@PathVariable("obsName") String obsName, @PathVariable("uuid") String uuid);
}
