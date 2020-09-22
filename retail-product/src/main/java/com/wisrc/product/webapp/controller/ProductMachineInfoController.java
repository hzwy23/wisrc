package com.wisrc.product.webapp.controller;

import com.wisrc.product.webapp.service.ProductMachineInfoService;
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
import java.util.Map;

@Api(value = "产品加工与包材料信息", tags = "产品加工与包材料信息")
@RestController
@RequestMapping(value = "/product")
public class ProductMachineInfoController {
    private final Logger logger = LoggerFactory.getLogger(ProductMachineInfoController.class);
    @Autowired
    private ProductMachineInfoService productMachineInfoService;


    @ApiOperation(value = "查询产品加工信息", notes = "查询产品加工信息")
    @ApiImplicitParam(name = "skuId", value = "SKU库存单元", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/machine/info/{skuId}", method = RequestMethod.GET)
    public Result findById(@PathVariable("skuId") String skuId) {
        try {
            return Result.success(productMachineInfoService.findById(skuId));
        } catch (Exception e) {
            logger.warn("查询产品加工信息失败", e);
            return Result.failure(ResultCode.FIND_FAILED);
        }
    }

    @ApiOperation(value = "获取sku下的包材", notes = "获取sku下的包材")
    @RequestMapping(value = "/machine/packing/material/{skuId}", method = RequestMethod.GET)
    public Result getPackingMaterial(@PathVariable("skuId") String skuId) {
        return productMachineInfoService.getPackingMaterial(skuId);
    }


    @ApiOperation(value = "用于检测sku对应包材是否充足", notes = "用于检测sku对应包材是否充足")
    @RequestMapping(value = "/machine/packing/material/check", method = RequestMethod.POST)
    public Result checkWarehouseNum(@RequestBody List<Map> mapList) {
        return productMachineInfoService.checkWarehouseNum(mapList);
    }

    @ApiOperation(value = "获取所有需要包材的sku", notes = "获取所有需要包材的sku")
    @RequestMapping(value = "/machine/packing/allNeedSku", method = RequestMethod.GET)
    public Result getAllSkuPackingMaterial() {
        try {
            Map map=productMachineInfoService.getAllPackingMaterial();
            return Result.success(map);
        }
        catch (Exception e){
            return Result.failure(390,"取所有需要包材的sku失败",e.getMessage());
        }
    }
}
