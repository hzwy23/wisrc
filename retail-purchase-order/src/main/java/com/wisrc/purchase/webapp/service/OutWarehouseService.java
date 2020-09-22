package com.wisrc.purchase.webapp.service;

import com.wisrc.purchase.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "retail-purchase-warehouse-23", url = "http://localhost:8080  ")
public interface OutWarehouseService {

    //======接口说明start=====>>
    //statusCd	仓库类型(0-全部查询，1-正常，2-停用)
    //typeCd	仓库状态(0-全部查询，123为测试数据)
    //模糊获取供应商信息列表
    @RequestMapping(value = "/warehouse/manage/info", method = RequestMethod.GET)
    Result getWarehouseFuzzy(@RequestParam("statusCd") Integer statusCd, @RequestParam("typeCd") Integer typeCd, @RequestParam("keyWord") String keyWord);

    @RequestMapping(value = "/warehouse/manage/info/idlist", method = RequestMethod.GET)
    Result getWarehouseBatch(@RequestParam("warehouseId") String idList);

    @RequestMapping(value = "/warehouse/detail/total", method = RequestMethod.GET)
    Result getTotal(@RequestParam("date") String date, @RequestParam("skuIds") List<String> skuIds);

    @RequestMapping("/warehouse/manage/info/byid")
    Result getWarehouseInfoById(@RequestParam("warehouseId") String warehouseId);

    @RequestMapping("/warehouse/stock/skuIds/total")
    Result getWarehouseTotal(@RequestParam("skuIds") String[] skuIds);

    @RequestMapping(value = "/warehouse/stock/skuIdAndDate/total", method = RequestMethod.POST, consumes = "application/json")
    Result getWare(@RequestBody String paramaterList);
}
