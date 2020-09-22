package com.wisrc.purchase.webapp.service;

import com.wisrc.purchase.webapp.utils.Result;
import com.wisrc.purchase.webapp.vo.orderProvision.OrderPdfVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "retail-purchase-images-02", url = "http://localhost:8080")
public interface PdfHtmlService {
    @PostMapping(value = "/images/pdf/obs", consumes = "application/json")
    Result getProdList(@RequestParam("obsName") String obsName);

    @RequestMapping(value = "/images/pdf/obs", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Result uploadFile(@RequestParam("obsName") String obsName, @RequestParam("fd") String fd, @RequestBody OrderPdfVO vo);
}
