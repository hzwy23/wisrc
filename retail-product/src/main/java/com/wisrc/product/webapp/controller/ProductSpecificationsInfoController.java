package com.wisrc.product.webapp.controller;

import com.wisrc.product.webapp.entity.ProductSpecificationsInfoEntity;
import com.wisrc.product.webapp.service.ProductSpecificationsInfoService;
import com.wisrc.product.webapp.utils.Result;
import com.wisrc.product.webapp.utils.ResultCode;
import com.wisrc.product.webapp.utils.Time;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Api(value = "产品特征规格", tags = "产品特征规格")
@RestController
@RequestMapping(value = "/product")
public class ProductSpecificationsInfoController {
    private final Logger logger = LoggerFactory.getLogger(ProductSpecificationsInfoController.class);
    @Autowired
    private ProductSpecificationsInfoService productSpecificationsInfoService;


    //不对外单独提供该接口
//    @ApiOperation(value = "删除产品规格信息", notes = "删除产品规格信息")
//    @ApiImplicitParam(name = "skuId", value = "SKU库存单元", required = true, dataType = "String", paramType = "path")
//    @RequestMapping(value = "/specifications/info/{skuId}", method = RequestMethod.DELETE)
//    public Result delete(@PathVariable("skuId") String skuId) {
//        try {
//            productSpecificationsInfoService.delete(skuId);
//            return Result.success();
//        } catch (Exception e) {
//            logger.warn("删除产品规格信息失败", e);
//            return new Result(ResultCode.DELETE_FAILED);
//        }
//    }

    @ApiOperation(value = "查询产品规格信息", notes = "查询产品规格信息")
    @ApiImplicitParam(name = "skuId", value = "SKU库存单元", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/specifications/info/{skuId}", method = RequestMethod.GET)
    public Result findById(@PathVariable("skuId") String skuId) {
        try {
            return Result.success(productSpecificationsInfoService.findById(skuId));
        } catch (Exception e) {
            logger.warn("查询产品规格信息失败", e);
            return new Result(ResultCode.FIND_FAILED);
        }
    }

    @ApiOperation(value = "同步产品规格信息", notes = "同步产品规格信息,只能修改fba长、fba宽、fba高信息")
    @RequestMapping(value = "/specifications/update", method = RequestMethod.POST)
    public Result updateById(@RequestBody ProductSpecificationsInfoEntity productSpecificationsInfoEntity, @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "matrixwms") String userId) {
        try {
            String time = Time.getCurrentTime();
            productSpecificationsInfoService.updateFba(productSpecificationsInfoEntity, time, userId);
            return Result.success();
        } catch (Exception e) {
            logger.warn("同步产品规格信息失败", e);
            return Result.failure();
        }
    }
}
