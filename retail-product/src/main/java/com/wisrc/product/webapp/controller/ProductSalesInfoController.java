package com.wisrc.product.webapp.controller;

import com.wisrc.product.webapp.service.ProductSalesInfoService;
import com.wisrc.product.webapp.utils.Result;
import com.wisrc.product.webapp.utils.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "产品销售信息", tags = "产品销售信息")
@RequestMapping(value = "/product")
public class ProductSalesInfoController {
    private final Logger logger = LoggerFactory.getLogger(ProductSalesInfoController.class);
    @Autowired
    private ProductSalesInfoService productSalesService;

    @ApiOperation(value = "查询产品销售状态，安全库存，国际运输天数信息", notes = "查询产品销售状态，安全库存，国际运输天数信息")
    @ApiImplicitParam(name = "skuId", value = "SKU库存单元", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/sales/info/{skuId}", method = RequestMethod.GET)
    public Result findById(@PathVariable("skuId") String skuId) {
        try {
            return Result.success(productSalesService.findBySkuId(skuId));
        } catch (Exception e) {
            logger.warn("查询产品销售状态，安全库存，国际运输天数信息失败", e);
            return Result.failure(ResultCode.FIND_FAILED);
        }
    }

    @ApiOperation(value = "查询产品销售状态码表", notes = "查询产品销售状态码表")
    @RequestMapping(value = "/sales/info/attr", method = RequestMethod.GET)
    public Result getSalesAttr() {
        try {
            return Result.success(productSalesService.getSalesAttr());
        } catch (Exception e) {
            logger.warn("查询产品销售状态码表失败", e);
            return Result.failure(ResultCode.FIND_FAILED);
        }
    }

    @ApiOperation(value = "Get批量skuId查询产品销售状态，安全库存，国际运输天数信息", notes = "批量skuId查询产品销售状态，安全库存，国际运输天数信息")
    @RequestMapping(value = "/sales/batch", method = RequestMethod.GET)
    @ApiImplicitParam(name = "skuIds", required = true, paramType = "query", dataType = "List")
    public Result batchFindGet(@RequestParam("skuIds") List<String> skuIds) {
        return productSalesService.batchFind(skuIds);
    }

    @ApiOperation(value = "Post批量获取销售状态，安全库存，国际运输天数信息", notes = "Post批量获取销售状态，安全库存，国际运输天数信息")
    @RequestMapping(value = "/sales/info/batch", method = RequestMethod.POST)
    public Result batchFindPost(@RequestBody List<String> skuIds) {
        return productSalesService.batchFindPost(skuIds);
    }

}
