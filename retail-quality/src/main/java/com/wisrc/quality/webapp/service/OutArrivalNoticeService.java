package com.wisrc.quality.webapp.service;

import com.wisrc.quality.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 外部到货通知单接口
 */
@FeignClient(value = "retail-quality-purchase-04", url = "http://localhost:8080")
public interface OutArrivalNoticeService {
    //模糊查询到货通知单
    @RequestMapping(value = "/purchase/arrival/", method = RequestMethod.GET)
    Result getArrivalFuzzy(@RequestParam("pageNum") Integer pageNum,
                           @RequestParam("pageSize") Integer pageSize,
                           @RequestParam("arrivalId") String arrivalId,
                           @RequestParam("applyStartDate") String applyStartDate,
                           @RequestParam("applyEndDate") String applyEndDate,
                           @RequestParam("employeeId") String employeeId,
                           @RequestParam("expectArrivalStartTime") String expectArrivalStartTime,
                           @RequestParam("expectArrivalEndTime") String expectArrivalEndTime,
                           @RequestParam("orderId") String orderId,
                           @RequestParam("skuId") String skuId,
                           @RequestParam("logisticsId") String logisticsId,
                           @RequestParam("findKey") String findKey);

    @RequestMapping(value = "/purchase/arrival/status/{arrivalId}/{sku}/{statusCd}", method = RequestMethod.PUT)
    Result changeStatus(@PathVariable("arrivalId") String arrivalId,
                        @PathVariable("sku") String sku,
                        @PathVariable("statusCd") Integer statusCd);
}


