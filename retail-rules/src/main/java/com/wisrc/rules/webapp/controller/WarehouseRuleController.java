package com.wisrc.rules.webapp.controller;

import com.wisrc.rules.webapp.service.WarehouseRuleService;
import com.wisrc.rules.webapp.dto.warehouseRule.GetWarehouseRuleDto;
import com.wisrc.rules.webapp.dto.warehouseRule.WarehouseRulePageDto;
import com.wisrc.rules.webapp.dto.warehouseRule.WarehouseRuleSwitchDto;
import com.wisrc.rules.webapp.utils.Result;
import com.wisrc.rules.webapp.vo.warehouseRule.WarehouseRuleEditVo;
import com.wisrc.rules.webapp.vo.warehouseRule.WarehouseRulePageVo;
import com.wisrc.rules.webapp.vo.warehouseRule.WarehouseRuleSaveVo;
import com.wisrc.rules.webapp.vo.warehouseRule.WarehouseRuleSwitchVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RequestMapping(value = "/rules/warehouse")
@RestController
@Api(value = "/warehouse", tags = "发货仓规则", description = "规则匹配")
public class WarehouseRuleController {
    @Autowired
    private WarehouseRuleService warehouseRuleService;

    @RequestMapping(value = "/detail/save", method = RequestMethod.POST)
    @ApiOperation(value = "新增发货仓规则", notes = "")
    public Result saveWarehouseRule(@Valid @RequestBody WarehouseRuleSaveVo warehouseRuleSaveVo, BindingResult bindingResult, @RequestHeader("X-AUTH-ID") @ApiIgnore String userId) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return Result.failure(400, "", errorList.get(0).getDefaultMessage());
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try {
            if (sdf.parse(warehouseRuleSaveVo.getStartDate()).getTime() > sdf.parse(warehouseRuleSaveVo.getEndDate()).getTime()) {
                return Result.failure(400, "无效有效期", "");
            }
        } catch (Exception e) {
            return Result.failure(400, "无效有效期", "");
        }
        return warehouseRuleService.saveWarehouseRule(warehouseRuleSaveVo, userId);
    }

    @ApiResponses(value =
    @ApiResponse(code = 200, message = "OK", response = WarehouseRulePageDto.class)
    )
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "发货仓规则页面信息", notes = "")
    public Result warehouseRulePage(@Valid WarehouseRulePageVo warehouseRulePageVo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return Result.failure(400, "", errorList.get(0).getDefaultMessage());
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date startTime = null;
            Date endTime = null;
            if (warehouseRulePageVo.getModifyStartTime() != null) {
                startTime = sdf.parse(warehouseRulePageVo.getModifyStartTime());
            }
            if (warehouseRulePageVo.getModifyEndTime() != null) {
                endTime = sdf.parse(warehouseRulePageVo.getModifyEndTime());
            }
            if (warehouseRulePageVo.getModifyStartTime() != null && warehouseRulePageVo.getModifyEndTime() != null) {
                if (startTime.getTime() > endTime.getTime()) {
                    return Result.failure(400, "无效最后更新时间", "");
                }
            }
        } catch (Exception e) {
            return Result.failure(400, "无效最后更新时间", "");
        }
        return warehouseRuleService.warehouseRulePage(warehouseRulePageVo);
    }

    @RequestMapping(value = "/detail/{ruleId}", method = RequestMethod.PUT)
    @ApiOperation(value = "编辑发货仓规则", notes = "")
    public Result editWarehouseRule(@Valid @RequestBody WarehouseRuleEditVo warehouseRuleEditVo, BindingResult bindingResult, @RequestHeader("X-AUTH-ID") @ApiIgnore String userId, @PathVariable("ruleId") String ruleId) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return Result.failure(400, "", errorList.get(0).getDefaultMessage());
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try {
            if (sdf.parse(warehouseRuleEditVo.getStartDate()).getTime() > sdf.parse(warehouseRuleEditVo.getEndDate()).getTime()) {
                return Result.failure(400, "无效有效期", "");
            }
        } catch (Exception e) {
            return Result.failure(400, "无效有效期", "");
        }
        return warehouseRuleService.editWarehouseRule(warehouseRuleEditVo, userId, ruleId);
    }

    @ApiResponses(value =
    @ApiResponse(code = 200, message = "OK", response = GetWarehouseRuleDto.class)
    )
    @RequestMapping(value = "/detail/{ruleId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取发货仓规则", notes = "")
    public Result getWarehouseRule(@PathVariable("ruleId") String ruleId) {
        return warehouseRuleService.getWarehouseRule(ruleId);
    }

    @RequestMapping(value = "/switch/{ruleId}", method = RequestMethod.PUT)
    @ApiOperation(value = "发货仓规则启用/禁用", notes = "")
    public Result warehouseRuleSwitch(@RequestBody WarehouseRuleSwitchVo warehouseRuleSwitchVo, @PathVariable("ruleId") String ruleId) {
        WarehouseRuleSwitchDto warehouseRuleSwitchDto = new WarehouseRuleSwitchDto();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        if (warehouseRuleSwitchVo.getStartDate() != null || warehouseRuleSwitchVo.getEndDate() != null) {
            try {
                Date startDate = sdf.parse(warehouseRuleSwitchVo.getStartDate());
                Date endDate = sdf.parse(warehouseRuleSwitchVo.getEndDate());
                if (startDate != null && endDate != null) {
                    if (startDate.getTime() > endDate.getTime()) {
                        return Result.failure(400, "无效时间", "");
                    }
                }
                warehouseRuleSwitchDto.setStartDate(new java.sql.Date(startDate.getTime()));
                warehouseRuleSwitchDto.setEndDate(new java.sql.Date(endDate.getTime()));
            } catch (Exception e) {
                e.printStackTrace();
                return Result.failure(400, "无效时间", "");
            }
        }

        return warehouseRuleService.warehouseRuleSwitch(warehouseRuleSwitchDto, ruleId);
    }
}
