package com.wisrc.rules.webapp.controller;

import com.wisrc.rules.webapp.service.OrderExceptService;
import com.wisrc.rules.webapp.dto.orderExcept.OrderExceptListDto;
import com.wisrc.rules.webapp.utils.Result;
import com.wisrc.rules.webapp.vo.orderExcept.ExceptEditVo;
import com.wisrc.rules.webapp.vo.orderExcept.OrderExceptEditVo;
import com.wisrc.rules.webapp.vo.orderExcept.OrderExceptSaveVo;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/rules/abnormal")
@RestController
@Api(value = "/abnormal", tags = "异常订单规则", description = "规则匹配")
public class OrderExceptController {
    @Autowired
    private OrderExceptService orderExceptService;

    @RequestMapping(value = "/detail/save", method = RequestMethod.POST)
    @ApiOperation(value = "新增异常订单规则", notes = "")
    public Result saveOrderExcept(@Valid @RequestBody OrderExceptSaveVo orderExceptSaveVo, BindingResult bindingResult, @RequestHeader("X-AUTH-ID") @ApiIgnore String userId) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return Result.failure(400, "", errorList.get(0).getDefaultMessage());
        }
        return orderExceptService.saveOrderExcept(orderExceptSaveVo, userId);
    }

    @RequestMapping(value = "/detail", method = RequestMethod.PUT)
    @ApiOperation(value = "编辑异常订单规则", notes = "")
    public Result editOrderExcept(@Valid @RequestBody OrderExceptEditVo orderExceptEditVo, BindingResult bindingResult, @RequestHeader("X-AUTH-ID") @ApiIgnore String userId) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return Result.failure(400, "", errorList.get(0).getDefaultMessage());
        }
        try {
            Map<String, String> exceptTypeCheck = orderExceptService.exceptTypeCheck();
            List<String> exceptTypeList = new ArrayList<>();
            for (String id : exceptTypeCheck.keySet()) {
                exceptTypeList.add(id);
            }
            for (ExceptEditVo orderExcept : orderExceptEditVo.getOrderExcepts()) {
                int index = exceptTypeList.indexOf(orderExcept.getExceptTypeCd());
                if (index != -1 && exceptTypeCheck.get(orderExcept.getExceptTypeCd()).equals(orderExcept.getExceptTypeName())) {
                    exceptTypeList.remove(index);
                }
            }
            if (exceptTypeList.size() > 0) {
                return new Result(400, "异常类型缺少必填项", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
        return orderExceptService.editOrderExcept(orderExceptEditVo, userId);
    }

    @ApiResponses(value =
    @ApiResponse(code = 200, message = "OK", response = OrderExceptListDto.class)
    )
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "异常订单规则所有信息", notes = "")
    public Result orderExceptList() {
        return orderExceptService.orderExceptList();
    }

    @RequestMapping(value = "/selector/column", method = RequestMethod.GET)
    @ApiOperation(value = "异常字段选择项", notes = "")
    public Result condColumnSelector() {
        return orderExceptService.condColumnSelector();
    }
}
