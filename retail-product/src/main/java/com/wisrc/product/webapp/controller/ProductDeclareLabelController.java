package com.wisrc.product.webapp.controller;

import com.wisrc.product.webapp.service.ProductDeclareLabelService;
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

import java.util.Map;


@Api(value = "产品申报标签信息", tags = "产品申报标签信息")
@RestController
@RequestMapping(value = "/product")
public class ProductDeclareLabelController {
    private final Logger logger = LoggerFactory.getLogger(ProductDeclareLabelController.class);
    @Autowired
    private ProductDeclareLabelService productDeclareLabelService;


    //不对外单独提供该接口
//    @ApiOperation(value = "删除产品申报标签信息", notes = "删除产品申报标签信息")
//    @ApiImplicitParam(name = "skuId", value = "SKU库存单元", required = true, dataType = "String", paramType = "path")
//    @RequestMapping(value = "/declareLabel/{skuId}", method = RequestMethod.DELETE)
//    public Result delete(@PathVariable("skuId") String skuId) {
//        try {
//            productDeclareLabelService.deleteBySkuId(skuId);
//            return Result.success();
//        } catch (Exception e) {
//            logger.warn("删除产品申报标签信息失败", e);
//            return Result.failure(ResultCode.DELETE_FAILED);
//        }
//    }


//============================== 标签新改 start ===========================================================

    @Deprecated
    @ApiOperation(value = "通过skuId查询产品申报标签信息（旧），不推荐使用，建议使用 【新改造v1】接口，目前为了兼容性考虑，仍在运行中，后期会关闭该接口")
    @ApiImplicitParam(name = "skuId", value = "SKU库存单元", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/declareLabel/{skuId}", method = RequestMethod.GET)
    public Result outdated(@PathVariable("skuId") String skuId) {
        try {
            Map<String, Object> map = productDeclareLabelService.getBySkuId(skuId);
            return Result.success(map);
        } catch (Exception e) {
            logger.warn("查询产品申报标签信息失败", e);
            return Result.failure(ResultCode.FIND_FAILED);
        }
    }

    @Deprecated
    @ApiOperation(value = "通过skuId查询产品申报标签信息，该接口命名不规范！建议使用 【新改造v1】接口，目前为了兼容性考虑，仍在运行中，后期会关闭该接口"
    )
    @ApiImplicitParam(name = "skuId", value = "SKU库存单元", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/declareLabel/new/{skuId}", method = RequestMethod.GET)
    public Result newFindBySkuId(@PathVariable("skuId") String skuId) {
        try {
            return Result.success(productDeclareLabelService.newFindBySkuId(skuId));
        } catch (Exception e) {
            logger.warn("查询产品申报标签信息失败", e);
            return Result.failure(ResultCode.FIND_FAILED);
        }
    }

    @ApiOperation(value = "通过skuId查询产品申报标签信息【新改造v1】，推荐使用"
    )
    @ApiImplicitParam(name = "skuId", value = "SKU库存单元", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/declareLabel/v1/{skuId}", method = RequestMethod.GET)
    public Result newFindBySkuIdv1(@PathVariable("skuId") String skuId) {
        try {
            return Result.success(productDeclareLabelService.newFindBySkuId(skuId));
        } catch (Exception e) {
            logger.warn("查询产品申报标签信息失败", e);
            return Result.failure(ResultCode.FIND_FAILED);
        }
    }


}
