package com.wisrc.product.webapp.controller;

import com.wisrc.product.webapp.service.ProductAccessoryService;
import com.wisrc.product.webapp.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "产品配件管理", tags = "产品配件管理")
@RequestMapping(value = "/product")
public class ProductAccessoryController {
    private final Logger logger = LoggerFactory.getLogger(ProductAccessoryController.class);
    @Autowired
    private ProductAccessoryService productAccessoryService;

    @ApiOperation(value = "查询所有曾经与现在使用过的配件码表", notes = "查询所有的配件码表")
    @RequestMapping(value = "/accessory/attr", method = RequestMethod.GET)
    public Result getAll() {
        return Result.success(productAccessoryService.getAll());
    }

//    @ApiOperation(value = "查询现在在使用的配件码表", notes = "查询现在在使用的配件码表")
//    @RequestMapping(value = "/accessory/attr", method = RequestMethod.GET)
//    public Result getNowUse() {
//        return Result.success(productAccessoryService.getAll());
//    }


    @ApiOperation(value = "查询产品【基础】配件码表（用作页面生成初始标签选择项）", notes = "查询产品【基础】配件码表（用作页面生成初始标签选择项）")
    @RequestMapping(value = "/accessory/attr/basic", method = RequestMethod.GET)
    public Result getBasic() {
        return Result.success(productAccessoryService.getBasic());
    }

    @ApiOperation(value = "查询某产品的配件")
    @RequestMapping(value = "/accessory/attr/{skuId}", method = RequestMethod.GET)
    public Result getBasicAndCustomBySkuId(@PathVariable("skuId") String skuId) {
        return Result.success(productAccessoryService.getBasicAndCustomBySkuId(skuId));
    }


}
