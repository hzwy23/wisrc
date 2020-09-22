package com.wisrc.warehouse.webapp.controller;

import com.wisrc.warehouse.webapp.entity.WarehouseStatusAttrEntity;
import com.wisrc.warehouse.webapp.entity.WarehouseTypeAttrEntity;
import com.wisrc.warehouse.webapp.service.ManageInfoAttrService;
import com.wisrc.warehouse.webapp.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "仓库管理码表接口")
@RequestMapping(value = "/warehouse")
public class WarehouseManagerAttrController {
    @Autowired
    private ManageInfoAttrService warehouseAttrService;

    @RequestMapping(value = "/manage/status", method = RequestMethod.GET)
    @ApiOperation(value = "仓库状态", notes = "仓库状态的码表查询功能<br/> 码表类型参数还未确定", response = WarehouseStatusAttrEntity.class)
    @ResponseBody
    public Result getStatusAttr() {
        List<WarehouseStatusAttrEntity> result = warehouseAttrService.getStatusAttr();
        return Result.success(result);
    }

    @RequestMapping(value = "/manage/type", method = RequestMethod.GET)
    @ApiOperation(value = "仓库类型", notes = "仓库类型的码表查询功能<br/> 码表类型参数还未确定", response = WarehouseTypeAttrEntity.class)
    @ResponseBody
    public Result getTypeAttr() {
        List<WarehouseTypeAttrEntity> result = warehouseAttrService.getTypeAttr();
        return Result.success(result);
    }
}
