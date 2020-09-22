package com.wisrc.purchase.webapp.controller;

import com.wisrc.purchase.webapp.dto.purchasePlan.GetPurchasePlanDto;
import com.wisrc.purchase.webapp.dto.purchasePlan.PlanTimeDto;
import com.wisrc.purchase.webapp.dto.purchasePlan.PurchasePlanPageReturnDto;
import com.wisrc.purchase.webapp.entity.GetSetting;
import com.wisrc.purchase.webapp.service.PurchasePlanService;
import com.wisrc.purchase.webapp.utils.Result;
import com.wisrc.purchase.webapp.vo.purchasePlan.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.regex.Pattern;

@RequestMapping("/purchase/plan")
@RestController
@Api(value = "/plan", tags = "采购计划管理", description = "采购管理")
public class PurchasePlanController {
    @Autowired
    private PurchasePlanService purchasePlanService;

    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = PurchasePlanPageReturnDto.class)
    })
    @ApiOperation(value = "采购计划信息列表",
            notes = "")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Result purchasePlanPage(@Valid PurchasePlanPageVo purchasePlanPageVo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return new Result(400, "", errorList.get(0).getDefaultMessage());
        }
        return purchasePlanService.purchasePlanPage(purchasePlanPageVo);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = GetPurchasePlanDto.class)
    })
    @ApiOperation(value = "获取采购计划信息",
            notes = "")
    @RequestMapping(value = "/detail/{uuid}", method = RequestMethod.GET)
    public Result getPurchasePlan(@PathVariable("uuid") String uuid) {
        return purchasePlanService.getPurchasePlan(uuid);
    }

    @ApiOperation(value = "确认计划",
            notes = "")
    @RequestMapping(value = "/decide", method = RequestMethod.POST)
    public Result decidePlan(@Valid @RequestBody PlanBatchVo planBatchVo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return new Result(400, "", errorList.get(0).getDefaultMessage());
        }
        return purchasePlanService.decidePlan(planBatchVo.getUuids());
    }

    @ApiOperation(value = "取消计划",
            notes = "")
    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public Result cancelPlan(@Valid @RequestBody PlanBatchVo planBatchVo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return new Result(400, "", errorList.get(0).getDefaultMessage());
        }
        return purchasePlanService.cancelPlan(planBatchVo.getUuids());
    }

    @ApiOperation(value = "导出计划",
            notes = "输入参数与页面接口同步，但页码和单页数量参数不会生效")
    @RequestMapping(value = "/excel", method = RequestMethod.GET)
    public Result excelPlan(PurchasePlanPageVo purchasePlanPageVo, HttpServletResponse response,
                            HttpServletRequest request) {
        return purchasePlanService.excelPlan(purchasePlanPageVo, response, request);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = GetSetting.class)
    })
    @ApiOperation(value = "获取默认设置",
            notes = "")
    @RequestMapping(value = "/setting", method = RequestMethod.GET)
    public Result purchaseSetting() {
        return purchasePlanService.purchaseSetting();
    }

    @ApiOperation(value = "修改默认设置",
            notes = "")
    @RequestMapping(value = "/setting", method = RequestMethod.PUT)
    public Result purchaseSetting(@Valid @RequestBody PurchaseSettingEditVo purchaseSettingEditVo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return new Result(400, "", errorList.get(0).getDefaultMessage());
        }
        String reg = "^[0-9]{2}:[0-9]{2}$";
        if (!Pattern.compile(reg).matcher(purchaseSettingEditVo.getDatetime()).matches()) {
            return Result.failure(400, "错误时间格式", "");
        }
        return purchasePlanService.editPurchaseSetting(purchaseSettingEditVo);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = PlanTimeDto.class)
    })
    @ApiOperation(value = "更改时间信息",
            notes = "")
    @RequestMapping(value = "/time/{uuid}", method = RequestMethod.GET)
    public Result getPlanTime(@PathVariable("uuid") String uuid) {
        return purchasePlanService.getPlanTime(uuid);
    }

    @ApiOperation(value = "更改时间",
            notes = "")
    @RequestMapping(value = "/time/{uuid}", method = RequestMethod.PUT)
    public Result getPlanTime(@PathVariable("uuid") String uuid, @Valid @RequestBody PlanTimeEditVo planTimeEditVo, BindingResult bindingResult, @RequestHeader("X-AUTH-ID") @ApiIgnore String userId) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return new Result(400, "", errorList.get(0).getDefaultMessage());
        }
        return purchasePlanService.editPlanTime(uuid, planTimeEditVo, userId);
    }

    @ApiOperation(value = "保存备注",
            notes = "")
    @RequestMapping(value = "/remark/{uuid}", method = RequestMethod.POST)
    public Result editRemark(@Valid @RequestBody PlanReamrkVo planReamrkVo, BindingResult bindingResult, @PathVariable("uuid") String uuid, @RequestHeader("X-AUTH-ID") @ApiIgnore String userId) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return new Result(400, "", errorList.get(0).getDefaultMessage());
        }
        return purchasePlanService.saveRemark(planReamrkVo.getRemark(), uuid, userId);
    }

    @ApiOperation(value = "获取默认设置日期跨度",
            notes = "")
    @RequestMapping(value = "/selector/days", method = RequestMethod.GET)
    public Result dayWeekSelector() {
        return purchasePlanService.dayWeekSelector();
    }

    @ApiOperation(value = "获取默认设置每周周数",
            notes = "")
    @RequestMapping(value = "/selector/week", method = RequestMethod.GET)
    public Result weekAttrSelector() {
        return purchasePlanService.weekAttrSelector();
    }
}
