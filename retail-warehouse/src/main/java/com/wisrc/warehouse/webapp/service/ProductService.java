package com.wisrc.warehouse.webapp.service;

import com.wisrc.warehouse.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "retail-warehouse-product-09", url = "http://localhost:8080")
public interface ProductService {


    /**
     * 获取产品特征规格
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/product/define/product/name/batch", method = RequestMethod.GET)
    Result getProductInfo(@RequestParam("skuId") String[] skuIds);

    /**
     * 获取所有产品的图片信息
     *
     * @return
     */
    @RequestMapping(value = "/product/images/all", method = RequestMethod.GET)
    Result getAllImgs();

    /**
     * 根据产品名称模糊查询产品
     *
     * @param skuNameZh
     * @return
     */
    @RequestMapping(value = "/product/define/brieffuzzy", method = RequestMethod.GET)
    Result getSkuInfoBySkuName(@RequestParam("skuNameZh") String skuNameZh);
}
