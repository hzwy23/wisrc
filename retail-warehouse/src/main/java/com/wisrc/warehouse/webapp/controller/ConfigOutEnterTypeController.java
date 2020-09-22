package com.wisrc.warehouse.webapp.controller;

import com.wisrc.warehouse.webapp.entity.EnterWarehouseTypeEntity;
import com.wisrc.warehouse.webapp.entity.OutWarehouseTypeEntity;
import com.wisrc.warehouse.webapp.service.EnterWarehouseTypeService;
import com.wisrc.warehouse.webapp.service.OutWarehouseTypeService;
import com.wisrc.warehouse.webapp.utils.Result;
import com.wisrc.warehouse.webapp.utils.Time;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@Api(tags = "配置出入库类型")
@RequestMapping(value = "/warehouse")
public class ConfigOutEnterTypeController {
    @Autowired
    private EnterWarehouseTypeService enterWarehouseTypeService;
    @Autowired
    private OutWarehouseTypeService outWarehouseTypeService;

    @RequestMapping(value = "/enter/config", method = RequestMethod.POST)
    @ApiOperation(value = "新增入库类型")
    public Result addEnter(@RequestBody EnterWarehouseTypeEntity entity, @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {
        try {
            entity.setCreateUser(userId);
            entity.setCreateTime(Time.getCurrentTime());
            entity.setDeleteStatus(0);
            enterWarehouseTypeService.addEnter(entity);
            return Result.success();
        } catch (DuplicateKeyException e) {
            return Result.failure(300, "入库类型已存在", "");
        }
    }


    @RequestMapping(value = "/enter/config", method = RequestMethod.GET)
    @ApiOperation(value = "查询入库类型")
    public Result selectAllEnter() {
        try {
            List<EnterWarehouseTypeEntity> list = enterWarehouseTypeService.getList();
            return Result.success(list);
        } catch (Exception e) {
            return Result.failure(390, e.getMessage(), "");
        }
    }

    @RequestMapping(value = "/enter/config", method = RequestMethod.PUT)
    @ApiOperation(value = "修改入库类型")
    public Result updateEnter(@RequestBody EnterWarehouseTypeEntity entity) {
        try {
            enterWarehouseTypeService.update(entity);
            return Result.success();
        } catch (Exception e) {
            return Result.failure();
        }
    }

    @Deprecated
    @RequestMapping(value = "/enter/config/{enterTypeCd}", method = RequestMethod.PUT)
    @ApiOperation(value = "逻辑删除入库类型，已经废弃的，请使用configEnterStatus")
    public Result deleteEnter(@PathVariable("enterTypeCd") int enterTypeCd) {
        try {
            enterWarehouseTypeService.configStatus(enterTypeCd, "disable");
            return Result.success();
        } catch (Exception e) {
            return Result.failure();
        }
    }

    @RequestMapping(value = "/enter/config/{enterTypeCd}/{action:(?:enable|disable)}", method = RequestMethod.PUT)
    @ApiOperation(value = "配置入库类型状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "enterTypeCd", value = "入库类型id", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "action", value = "入库类型配置状态", dataType = "String", paramType = "path",
                    allowableValues = "enable,disable", required = true),
    })
    public Result configEnterStatus(@PathVariable("enterTypeCd") int enterTypeCd,
                                    @PathVariable("action") String action) {
        try {
            enterWarehouseTypeService.configStatus(enterTypeCd, action);
            return Result.success();
        } catch (Exception e) {
            return Result.failure();
        }
    }

    @RequestMapping(value = "/out/config", method = RequestMethod.POST)
    @ApiOperation(value = "新增出库类型")
    public Result addOut(@RequestBody OutWarehouseTypeEntity entity, @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {
        try {
            entity.setCreateUser(userId);
            entity.setCreateTime(Time.getCurrentTime());
            entity.setDeleteStatus(0);
            outWarehouseTypeService.addOut(entity);
            return Result.success();
        } catch (DuplicateKeyException e) {
            return Result.failure(300, "出库类型已存在", "");
        }
    }


    @RequestMapping(value = "/out/config", method = RequestMethod.GET)
    @ApiOperation(value = "查询所有出库类型")
    public Result selectAllOut() {
        try {
            List<OutWarehouseTypeEntity> list = outWarehouseTypeService.getList();
            return Result.success(list);
        } catch (Exception e) {
            return Result.failure(390, e.getMessage(), "");
        }
    }


    @RequestMapping(value = "/out/config", method = RequestMethod.PUT)
    @ApiOperation(value = "修改出库类型")
    public Result updateOut(@RequestBody OutWarehouseTypeEntity entity) {
        try {
            outWarehouseTypeService.update(entity);
            return Result.success();
        } catch (Exception e) {
            return Result.failure();
        }
    }

    @Deprecated
    @RequestMapping(value = "/out/config/{outTypeCd}", method = RequestMethod.PUT)
    @ApiOperation(value = "逻辑删除出库类型，已经废弃的，请使用configOutStatus")
    public Result deleteOut(@PathVariable("outTypeCd") int outTypeCd) {
        try {
            outWarehouseTypeService.configStatus(outTypeCd, "disable");
            return Result.success();
        } catch (Exception e) {
            return Result.failure();
        }
    }

    @RequestMapping(value = "/out/config/{outTypeCd}/{action:(?:enable|disable)}", method = RequestMethod.PUT)
    @ApiOperation(value = "配置出库类型状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "outTypeCd", value = "出库类型id", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "action", value = "出库类型配置状态", dataType = "String", paramType = "path",
                    allowableValues = "enable,disable", required = true),
    })
    public Result configOutStatus(@PathVariable("outTypeCd") int outTypeCd,
                                  @PathVariable("action") String action) {
        try {
            outWarehouseTypeService.configStatus(outTypeCd, action);
            return Result.success();
        } catch (Exception e) {
            return Result.failure();
        }
    }

}
