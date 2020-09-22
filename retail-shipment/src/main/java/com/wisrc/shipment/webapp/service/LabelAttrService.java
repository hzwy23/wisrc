package com.wisrc.shipment.webapp.service;

import com.wisrc.shipment.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "retail-shipment-product-01", url = "http://localhost:8080")
public interface LabelAttrService {

    @GetMapping("/declareLabelAttr/info/{labelCd}")
    Result getLabelName(@PathVariable("labelCd") int labelCd);

}
