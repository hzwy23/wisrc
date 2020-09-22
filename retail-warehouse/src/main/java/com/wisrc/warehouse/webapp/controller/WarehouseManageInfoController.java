package com.wisrc.warehouse.webapp.controller;

import com.wisrc.warehouse.webapp.entity.WarehouseManageInfoEntity;
import com.wisrc.warehouse.webapp.service.WarehouseManageInfoService;
import com.wisrc.warehouse.webapp.utils.Result;
import com.wisrc.warehouse.webapp.utils.Time;
import com.wisrc.warehouse.webapp.vo.WarehouseManageInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.LinkedHashMap;

@Controller
@Api(tags = "仓库管理")
@RequestMapping(value = "/warehouse")
public class WarehouseManageInfoController {
    @Autowired
    private WarehouseManageInfoService warehouseManageInfoService;

    @RequestMapping(value = "/manage/info", method = RequestMethod.GET)
    @ApiOperation(value = "仓库基本信息查询", notes = "根据查询条件，查询仓库基本信息。仓库类型、状态和关键字查询", response = WarehouseManageInfoEntity.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "statusCd", value = "仓库状态(0-全部查询，1-正常，2-停用)", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "typeCd", value = "仓库类型", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "keyWord", value = "关键字", dataType = "String", paramType = "query")
    })
    @ResponseBody
    public Result findAll(@RequestParam(value = "pageNum", required = false) String pageNum,
                          @RequestParam(value = "pageSize", required = false) String pageSize,
                          @RequestParam(value = "statusCd", required = false, defaultValue = "-1") int statusCd,
                          @RequestParam(value = "typeCd", required = false) String typeCd,
                          @RequestParam(value = "keyWord", required = false) String keyWord) {
        try {
            int size = Integer.valueOf(pageSize);
            int num = Integer.valueOf(pageNum);
            if (statusCd == -1) {
                statusCd = 0;
            }

            if (size != 0 || num != 0 || statusCd != 0 || typeCd != null || keyWord != null) {
                LinkedHashMap list = warehouseManageInfoService.findAll(num, size, statusCd, typeCd, keyWord);
                return Result.success(list);
            }
            return Result.success(warehouseManageInfoService.findAll());
        } catch (Exception e) {
            if (statusCd == -1) {
                statusCd = 0;
            }
            return Result.success(warehouseManageInfoService.findAll(statusCd, typeCd, keyWord));
        }
    }


    @RequestMapping(value = "/manage/info", method = RequestMethod.PUT)
    @ApiOperation(value = "修改仓库信息", notes = "修改仓库名称和仓库类型", response = WarehouseManageInfoVO.class)
    @ApiImplicitParam(name = "warehouseId", value = "仓库编码", dataType = "string", paramType = "query", required = true)
    @ResponseBody
    public Result update(@Valid @RequestBody WarehouseManageInfoVO vo,
                         BindingResult result,
                         @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {
        if (result.hasErrors()) {
            return Result.failure(423, result.getAllErrors().get(0).getDefaultMessage(), vo);
        }
        WarehouseManageInfoEntity ele = toEntity(vo);
        ele.setModifyUser(userId);
        ele.setModifyTime(Time.getCurrentTime());
        return warehouseManageInfoService.changeName(ele);
    }


    @ApiOperation(value = "修改仓状态", notes = "修改仓状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "warehouseId", value = "仓库ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "statusCd", value = "仓库状态(1--正常，2--停用)", required = true, dataType = "int", paramType = "query"),
    })
    @RequestMapping(value = "/manage/status", method = RequestMethod.PUT)
    @ResponseBody
    public Result changStatus(@RequestParam(value = "warehouseId", required = true) String warehouseId,
                              @RequestParam(value = "statusCd", required = true) int statusCd,
                              @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {
        warehouseManageInfoService.changeStatus(warehouseId, statusCd, userId, Time.getCurrentTimestamp());
        return Result.success("修改仓库状态成功");
    }

    @ApiOperation(value = "新增仓库信息", notes = "仓库ID唯一索引，每新增一条仓库信息，都会为这条数据生成一个仓库ID. <br/>" +
            "仓库ID具有唯一性，通过仓库ID，可以获取，修改仓库的详细信息<br/>" +
            "新添加的仓库状态默认为正常", response = WarehouseManageInfoVO.class)
    @RequestMapping(value = "/manage/add", method = RequestMethod.POST)
    @ResponseBody
    public Result add(@Valid @RequestBody WarehouseManageInfoVO vo, BindingResult result,
                      @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {
        if (result.hasErrors()) {
            return Result.failure(423, result.getFieldError().getDefaultMessage(), vo);
        }
        WarehouseManageInfoEntity ele = toEntity(vo);
        ele.setWarehouseName(vo.getWarehouseName().trim());
        ele.setStatusCd(1);
        ele.setCreateUser(userId);
        ele.setModifyUser(userId);
        ele.setCreateTime(Time.getCurrentTimestamp());
        ele.setModifyTime(Time.getCurrentTime());
        try {
            return warehouseManageInfoService.add(ele);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return Result.failure(423, "依赖关系不存在，请联系管理员", ele);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.success(300, "仓库名已存在，请重新输入", vo);
        }
    }

    @RequestMapping(value = "/manage/info/byid", method = RequestMethod.GET)
    @ApiOperation(value = "通过ID查询仓库信息", notes = "通过ID查询仓库信息", response = WarehouseManageInfoVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "warehouseId", value = "仓库唯一编码", required = true, dataType = "String", paramType = "query")
    })
    @ResponseBody
    public Result findInfoById(@RequestParam(value = "warehouseId", required = true) String warehouseId) {

        return Result.success(warehouseManageInfoService.findById(warehouseId));
    }

    @RequestMapping(value = "/manage/info/idlist", method = RequestMethod.GET)
    @ApiOperation(value = "通过ID拼接字符串查询仓库信息", notes = "通过ID集合查询仓库信息列表", response = WarehouseManageInfoVO.class, consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public Result findByIdList(String warehouseId) {
        return Result.success(warehouseManageInfoService.findInfoList(warehouseId));
    }

    @RequestMapping(value = "/warehouse/not/fba", method = RequestMethod.GET)
    @ApiOperation(value = "查询所有正常状态的非FBA仓", notes = "供FBA增加补货选择起运仓调用")
    @ResponseBody
    public Result findAllNotFba() {
        return Result.success(warehouseManageInfoService.findAllNotFba());
    }


    private WarehouseManageInfoEntity toEntity(WarehouseManageInfoVO vo) {
        WarehouseManageInfoEntity entity = new WarehouseManageInfoEntity();
        BeanUtils.copyProperties(vo, entity);
        return entity;
    }
}
