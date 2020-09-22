package com.wisrc.basic.controller;

import com.wisrc.basic.entity.BrandModifyHistoryEntity;
import com.wisrc.basic.service.BrandModifyHistoryService;
import com.wisrc.basic.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "品牌历史信息")
@RequestMapping(value = "/basic")
public class BrandModifyHistoryController {
    private final Logger logger = LoggerFactory.getLogger(BrandModifyHistoryController.class);

    @Autowired
    private BrandModifyHistoryService brandModifyHistoryService;

    @RequestMapping(value = "/brand/history", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "brandId", value = "产品品牌编码", required = true, dataType = "String", paramType = "query"),
    })
    @ApiOperation(value = "根据品牌ID查询品牌历史信息", notes = "根据品牌ID查询品牌历史信息")
    public Result findById(@RequestParam("brandId") String brandId) {
        BrandModifyHistoryEntity entity = new BrandModifyHistoryEntity();
        entity.setBrandId(brandId);
        return brandModifyHistoryService.findByBrandId(entity);
    }

}
