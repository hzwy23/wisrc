package com.wisrc.product.webapp.controller;

import com.wisrc.product.webapp.service.ProductInfoService;
import com.wisrc.product.webapp.utils.Result;
import com.wisrc.product.webapp.utils.ResultCode;
import com.wisrc.product.webapp.vo.productInfo.NewAddProductInfoVO;
import com.wisrc.product.webapp.vo.productInfo.NewSetProductInfoVO;
import com.wisrc.product.webapp.vo.productInfo.get.BatchSkuId;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;


@Api(value = "产品汇总信息", tags = "产品汇总信息")
@RestController
@RequestMapping(value = "/product")
public class ProductInfoController {
    private final Logger logger = LoggerFactory.getLogger(ProductDefineController.class);
    @Autowired
    private ProductInfoService productInfoService;

    @ApiOperation(value = "新增产品汇总信息（标签新改）", notes = "新增产品汇总信息（标签新改）")
    @RequestMapping(value = "/productInfo", method = RequestMethod.POST)
    @ApiImplicitParam(name = "X-AUTH-ID", value = "用户ID", required = true, dataType = "String", paramType = "header")
    public Result addNew(@Valid @RequestBody NewAddProductInfoVO newAddProductInfoVO, BindingResult bindingResult, @RequestHeader("X-AUTH-ID") String userId) {
        try {
            if (bindingResult.hasErrors()) {
                return new Result(9999, bindingResult.getFieldError().getDefaultMessage(), null);
            } else {
                return productInfoService.newInsert(newAddProductInfoVO, userId);
            }
        } catch (Exception e) {
            logger.warn("新增产品汇总信息失败！", e);
            return Result.failure(ResultCode.CREATE_FAILED);
        }
    }


    @ApiOperation(value = "更新产品汇总信息（标签新改）", notes = "更新产品汇总信息")
    @RequestMapping(value = "/productInfo", method = RequestMethod.PUT)
    @ApiImplicitParam(name = "X-AUTH-ID", value = "用户ID", required = true, dataType = "String", paramType = "header")
    public Result updateNew(@Valid @RequestBody NewSetProductInfoVO newSetProductInfoVO, BindingResult bindingResult, @RequestHeader("X-AUTH-ID") String userId) {
        try {
            if (bindingResult.hasErrors()) {
                return new Result(9999, bindingResult.getFieldError().getDefaultMessage(), null);
            } else {
                return productInfoService.updateNew(newSetProductInfoVO, userId);
            }
        } catch (Exception e) {
            logger.warn("更新产品汇总信息失败", e);
            return Result.failure(ResultCode.UPDATE_FAILED);
        }
    }

    @ApiOperation(value = "查询单个产品汇总信息（标签新改）", notes = "查询单个产品汇总信息（标签新改）")
    @ApiImplicitParam(name = "skuId", value = "SKU库存单元", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/productInfo/{skuId}", method = RequestMethod.GET)
    public Result findBySkuIdNew(@PathVariable("skuId") String skuId) {
        try {
            Map<String, Object> map = productInfoService.newFindBySkuId(skuId);
            if (map.get("error").equals("0")) {
                return Result.success(map);
            } else {
                return Result.failure(ResultCode.FIND_FAILED);
            }
        } catch (Exception e) {
            logger.warn("查询单个产品汇总信息失败", e);
            return Result.failure(ResultCode.FIND_FAILED);
        }
    }

    @ApiOperation(value = "批量查询产品汇总信息（标签新改）", notes = "批量查询产品汇总信息（标签新改）")
    @RequestMapping(value = "/productInfo/batch", method = RequestMethod.POST)
    public Result findByBatchSkuId(@Valid @RequestBody BatchSkuId batchSkuId, BindingResult bindingResult) {
        return productInfoService.findByBatchSkuId(batchSkuId, bindingResult);
    }

    @ApiOperation(value = "sku查询配件与装箱信息", notes = "sku查询配件与装箱信息")
    @RequestMapping(value = "/productInfo/accessory/packing", method = RequestMethod.GET)
    public Result getAccessoryAndPacing(String skuId) {
        return productInfoService.getAccessoryAndPacing(skuId);
    }


}
