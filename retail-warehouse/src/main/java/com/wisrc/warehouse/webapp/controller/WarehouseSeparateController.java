package com.wisrc.warehouse.webapp.controller;


import com.wisrc.warehouse.webapp.entity.WarehouseSeparateDetailsInfoEntity;
import com.wisrc.warehouse.webapp.service.WarehouseManageInfoService;
import com.wisrc.warehouse.webapp.utils.Result;
import com.wisrc.warehouse.webapp.utils.Time;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@Api(tags = "仓库管理")
@RequestMapping(value = "/warehouse")
public class WarehouseSeparateController {
    @Autowired
    private WarehouseManageInfoService warehouseManageInfoService;


    @RequestMapping(value = "/manage/subwarehouse/info", method = RequestMethod.GET)
    @ApiImplicitParam(name = "warehouseId", value = "仓库ID", dataType = "string", paramType = "query", required = true)
    public Result findSubWarehouseInfo(@RequestParam("warehouseId") String warehouseId) {
        return Result.success(warehouseManageInfoService.findSubWarehouseInfo(warehouseId));
    }

    @RequestMapping(value = "/manage/subwarehouse/info", method = RequestMethod.POST)
    public Result addSubWarehouse(@Valid @RequestBody WarehouseSeparateDetailsInfoEntity warehouseSeparateDetailsInfoEntity,
                                  BindingResult bindingResult,
                                  @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "default") @ApiIgnore String userId) {
        if (bindingResult.hasErrors()) {
            return Result.failure(423, bindingResult.getFieldError().getDefaultMessage(), warehouseSeparateDetailsInfoEntity);
        }
        warehouseSeparateDetailsInfoEntity.setCreateUser(userId);
        warehouseSeparateDetailsInfoEntity.setModifyUser(userId);
        try {
            warehouseManageInfoService.addSubwarehouse(warehouseSeparateDetailsInfoEntity);
        } catch (Exception e) {
            return Result.failure(423, "添加分仓失败，请刷新后重试", warehouseSeparateDetailsInfoEntity);
        }
        return Result.success();
    }

    @RequestMapping(value = "/manage/subwarehouse/info", method = RequestMethod.PUT)
    public Result updateSubWarehouse(@Valid @RequestBody WarehouseSeparateDetailsInfoEntity warehouseSeparateDetailsInfoEntity,
                                     BindingResult bindingResult,
                                     @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "default") @ApiIgnore String userId) {
        if (bindingResult.hasErrors()) {
            return Result.failure(423, bindingResult.getFieldError().getDefaultMessage(), warehouseSeparateDetailsInfoEntity);
        }
        warehouseSeparateDetailsInfoEntity.setCreateUser(userId);
        warehouseSeparateDetailsInfoEntity.setModifyUser(userId);
        warehouseSeparateDetailsInfoEntity.setModifyTime(Time.getCurrentTime());
        return warehouseManageInfoService.updateSubwarehouse(warehouseSeparateDetailsInfoEntity);
    }

    @ResponseBody
    @RequestMapping(value = "/manage/subwarehouse/info/{subWarehouseId}", method = RequestMethod.DELETE)
    public Result deleteSubWarehouse(@PathVariable("subWarehouseId") String subWarehouseId,
                                     @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "default") @ApiIgnore String userId) {
        WarehouseSeparateDetailsInfoEntity warehouseSeparateDetailsInfoEntity = new WarehouseSeparateDetailsInfoEntity();
        warehouseSeparateDetailsInfoEntity.setCreateUser(userId);
        warehouseSeparateDetailsInfoEntity.setModifyUser(userId);
        warehouseSeparateDetailsInfoEntity.setSubWarehouseId(subWarehouseId);
        return warehouseManageInfoService.deleteSubwarehouse(warehouseSeparateDetailsInfoEntity);
    }

}
