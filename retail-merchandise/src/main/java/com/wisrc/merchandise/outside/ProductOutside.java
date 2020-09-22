package com.wisrc.merchandise.outside;


import com.wisrc.merchandise.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "retail-merchandise-product", url = "http://localhost:8080")
public interface ProductOutside {
    // 查询产品
    @RequestMapping(value = "/product/define/brieffuzzy", method = RequestMethod.GET)
    Result getProductInfl(@RequestParam("skuId") String skuId, @RequestParam("skuNameZh") String skuNameZh, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

    // 查询产品
    @RequestMapping(value = "/product/define/brieffuzzy", method = RequestMethod.GET)
    Result getProductInfo(@RequestParam("keyword") String keyword, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

    // 批量获取产品图片
    @RequestMapping(value = "/product/images/batch", method = RequestMethod.GET)
    Result getProductPicture(@RequestParam("skuId") List<String> skuId);

    // 批量获取产品中文名
    @RequestMapping(value = "/product/define/product/name/batch", method = RequestMethod.GET)
    Result getProductCN(@RequestParam("skuId") List<String> skuId);

    // 批量获取产品中文名
    @RequestMapping(value = "/product/define/product/name/list", method = RequestMethod.POST)
    Result getProductCNNew(@RequestBody List<String> skuId);

    // 批量skuId查询产品销售状态，安全库存，国际运输天数信息
    @RequestMapping(value = "/product/sales/batch", method = RequestMethod.GET)
    Result getProductSales(@RequestParam("skuIds") List<String> skuIds);

    /**
     * 根据skuID查询出产品信息
     * @param skuId
     * @return
     */
    @RequestMapping("/product/productInfo/{skuId}")
    Result getSkuInfoBySkuId(@PathVariable("skuId") String skuId);
}
