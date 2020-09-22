package com.wisrc.replenishment.webapp.service;

import com.wisrc.replenishment.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@FeignClient(value = "retail-replenishment-operation-19", url = "http://localhost:8080")
public interface MskuOutsideService {
    // 获取销售状态选择框
    @RequestMapping(value = "/operation/sales/status/selector", method = RequestMethod.GET)
    Result getSaleStatusSelector() throws Exception;

    // 获取店铺选择框
    @RequestMapping(value = "/operation/shop/selector", method = RequestMethod.GET)
    Result getShopSelector() throws Exception;

    // 查看销售计划
    @RequestMapping(value = "/operation/merchandise/sales/plan/plans", method = RequestMethod.GET)
    Result getSalesPlan(@RequestParam("id") String id) throws Exception;

    // 获取商品页面信息
    @RequestMapping(value = "/operation/merchandise/msku", method = RequestMethod.GET)
    Result getMskuPage(@RequestParam("shopId") String shopId, @RequestParam("manager") String manager,
                       @RequestParam("salesStatus") Integer salesStatus, @RequestParam("findKey") String findKey,
                       @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize,
                       @RequestParam("salesStatusList") List<String> salesStatusList,
                       @RequestParam("doesDelete") Integer doesDelete,
                       @RequestHeader(value = "X-AUTH-ID") String userId) throws Exception;

    @RequestMapping(value = "/operation/shop/{shopId}", method = RequestMethod.GET)
    Result getShopById(@PathVariable("shopId") String shopId);
}
