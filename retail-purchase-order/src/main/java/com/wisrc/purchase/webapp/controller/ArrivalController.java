package com.wisrc.purchase.webapp.controller;

import com.wisrc.purchase.webapp.service.ArrivalService;
import com.wisrc.purchase.webapp.utils.Result;
import com.wisrc.purchase.webapp.vo.inspection.ArrivalExcelVo;
import com.wisrc.purchase.webapp.vo.inspection.ArrivalPageVo;
import com.wisrc.purchase.webapp.vo.swagger.GetInspectionModel;
import com.wisrc.purchase.webapp.vo.swagger.InspectionPageModel;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RequestMapping("/purchase/arrivals")
@RestController
@Api(value = "/arrivals", tags = "到货通知信息管理")
public class ArrivalController {
    @Autowired
    private ArrivalService arrivalService;

    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = InspectionPageModel.class)
    })
    @ApiOperation(value = "到货通知单信息列表")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Result inspectionList(@Valid ArrivalPageVo arrivalPageVo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return new Result(400, errorList.get(0).getDefaultMessage(), arrivalPageVo);
        }
        return arrivalService.arrivalList(arrivalPageVo);
    }

    @ApiOperation(value = "导出到货通知单信息",
            notes = "")
    @RequestMapping(value = "/excel", method = RequestMethod.POST)
    public Result getInspection(@Valid @RequestBody ArrivalExcelVo arrivalExcelVo, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return new Result(400, "", errorList.get(0).getDefaultMessage());
        }
        return arrivalService.excel(arrivalExcelVo, request, response);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = GetInspectionModel.class),
    })
    @ApiOperation(value = "根据单号获取到货通知单信息",
            notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "arrivalId", value = "申请单号", paramType = "path", dataType = "String", required = true)
    })
    @RequestMapping(value = "/{arrivalId}", method = RequestMethod.GET)
    public Result getInspection(@PathVariable("arrivalId") String arrivalId) {
        return arrivalService.getArrival(arrivalId);
    }
}
