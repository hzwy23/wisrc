package com.wisrc.replenishment.webapp.service;

import com.wisrc.replenishment.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(value = "retail-replenishment-shop-18", url = "http://localhost:8080")
public interface MskuInfoService {

    /**
     * 通过mskuId获取商品相关信息
     *
     * @return
     */
    @GetMapping(value = "/operation/merchase/{mskuId}")
    Result getMskuInfo(@RequestParam("mskuId") String mskuId);

    /**
     * 根据商品id集合查询商品相关信息
     *
     * @param ids
     * @return
     */
    @GetMapping(value = "/operation/merchandise/msku/batch")
    Result getMskuInfoByIds(@RequestParam("ids") String[] ids);

    /**
     * 通过店铺Id获取店铺名称
     *
     * @param shopId
     * @return
     */
    @GetMapping(value = "/operation/shop/{shopId}")
    Result getShopName(@RequestParam("shopId") String shopId);

    /**
     * 根据产品id,产品名称模糊查询商品相关信息
     *
     * @return
     */
    @GetMapping(value = "/operation/merchandise/msku/search")
    Result getMskuBySku(@RequestParam("asin") String asin, @RequestParam("skuid") String skuId, @RequestParam("productName") String productName);

    @RequestMapping(value = "/operation/shop", method = RequestMethod.GET)
    Result getShop(@RequestParam("statusCd") String statusCd);

    /**
     * 根据产品id,产品名称模糊查询商品相关信息
     *
     * @return
     */
    @GetMapping(value = "/operation/merchandise/msku/key")
    Result getMskuByKey(@RequestParam("employeeId") String employeeId,
                        @RequestParam("skuId") String skuId,
                        @RequestParam("productName") String productName);
}
