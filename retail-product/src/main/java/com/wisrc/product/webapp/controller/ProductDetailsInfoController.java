package com.wisrc.product.webapp.controller;

import com.wisrc.product.webapp.entity.ProductDetailsInfoEntity;
import com.wisrc.product.webapp.service.ProductDetailsInfoService;
import com.wisrc.product.webapp.utils.Result;
import com.wisrc.product.webapp.utils.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "产品信息描述", tags = "产品信息描述")
@RestController
@RequestMapping(value = "/product")
public class ProductDetailsInfoController {
    private final Logger logger = LoggerFactory.getLogger(ProductDetailsInfoController.class);
    @Autowired
    private ProductDetailsInfoService productDetailsInfoService;


    //不对外单独提供该接口
//    @ApiOperation(value = "删除产品信息描述", notes = "删除产品信息描述")
//    @ApiImplicitParam(name = "skuId", value = "SKU库存单元", required = true, dataType = "String", paramType = "path")
//    @RequestMapping(value = "/details/info/{skuId}", method = RequestMethod.DELETE)
//    public Result delete(@PathVariable("skuId") String skuId) {
//        try {
//            productDetailsInfoService.delete(skuId);
//            return Result.success();
//        } catch (Exception e) {
//            logger.warn("删除产品信息失败", e);
//            return Result.failure(ResultCode.DELETE_FAILED);
//        }
//    }

    @ApiOperation(value = "查询产品详细信息", notes = "查询产品详细信息")
    @ApiImplicitParam(name = "skuId", value = "SKU库存单元", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/details/{skuId}", method = RequestMethod.GET)
    public Result findById(@PathVariable("skuId") String skuId) {
        try {
            ProductDetailsInfoEntity entity = productDetailsInfoService.findById(skuId);
            return Result.success(entity);
        } catch (Exception e) {
            logger.warn("查询产品详细信息失败", e);
            return Result.failure(ResultCode.FIND_FAILED);
        }
    }


}
