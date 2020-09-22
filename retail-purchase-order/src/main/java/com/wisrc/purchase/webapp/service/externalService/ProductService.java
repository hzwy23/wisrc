package com.wisrc.purchase.webapp.service.externalService;


import com.wisrc.purchase.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "retail-purchase-product-15", url = "http://localhost:8080")
public interface ProductService {
    // 查询产品
    @RequestMapping(value = "/product/define/brieffuzzy", method = RequestMethod.GET)
    Result getProductInfl(@RequestParam("ignoreSkuIds") String[] ignoreSkuIds, @RequestParam("skuId") String skuId, @RequestParam("skuNameZh") String skuNameZh, @RequestParam("statusCd") Integer statusCd,
                          @RequestParam("classifyCd") String classifyCd, @RequestParam("typeCd") Integer typeCd, @RequestParam("salesStatusCd") Integer salesStatusCd, @RequestParam("pageNum") Integer pageNum,
                          @RequestParam("pageSize") Integer pageSize, @RequestParam("ignoreImages") Boolean ignoreImages, @RequestParam("keyword") String keyword);

    // 查询产品
    @RequestMapping(value = "/product/define/brieffuzzy", method = RequestMethod.GET)
    Result getProductInfo(@RequestParam("keyword") String keyword);

    // 批量获取产品图片
    @RequestMapping(value = "/product/images/batch", method = RequestMethod.GET)
    Result getProductPicture(@RequestParam("skuId") List<String> skuId);

    // 批量获取产品中文名
    @RequestMapping(value = "/product/define/product/name/batch", method = RequestMethod.GET)
    Result getProductCN(@RequestParam("skuId") List<String> skuId);

    @RequestMapping(value = "/product/define/brieffuzzy", method = RequestMethod.GET)
    Result getProduct(@RequestParam("pageNum") String pageNum, @RequestParam("pageSize") String pageSize);

    @PostMapping(value = "/product/productInfo/batch", consumes = "application/json")
    Result getProdList(@RequestBody String batchSkuId);

    // 批量skuId查询产品销售状态，安全库存，国际运输天数信息
    @RequestMapping(value = "/product/sales/batch", method = RequestMethod.GET)
    Result getProductSales(@RequestParam("skuIds") List<String> skuIds);

    /**
     * 根据skuId获取产品的包材信息
     *
     * @param skuId
     * @return
     */
    @RequestMapping("/product/machine/packing/material/{skuId}")
    Result getProductPackingInfo(@PathVariable("skuId") String skuId);

    @PostMapping(value = "/product/machine/packing/material/check", consumes = "application/json")
    Result checkWarehouseNum(@RequestBody String mapList);

    @RequestMapping("/product/machine/packing/allNeedSku")
    Result getAllSkuPackingMaterial();
}
