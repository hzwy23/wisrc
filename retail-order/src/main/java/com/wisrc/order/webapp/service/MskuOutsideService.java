package com.wisrc.order.webapp.service;

import com.wisrc.order.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(value = "retail-operation-07", url = "http://localhost:8080")
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
    Result getMskuPage(@RequestParam("shopId") String shopId, @RequestParam("manager") String manager, @RequestParam("salesStatus") Integer salesStatus, @RequestParam("findKey") String findKey,
                       @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) throws Exception;

    // 查看销售计划
    @RequestMapping(value = "/operation/merchandise/msku/batch", method = RequestMethod.GET)
    Result getMskuInfo(@RequestParam("ids") List<String> ids) throws Exception;

    // 根据商品编号和名称获取商品id
    @RequestMapping(value = "/operation/merchandise/msku/ids", method = RequestMethod.GET)
    Result getIdByMskuIdAndName(@RequestParam("mskuId") String mskuId, @RequestParam("mskuName") String mskuName) throws Exception;
}
