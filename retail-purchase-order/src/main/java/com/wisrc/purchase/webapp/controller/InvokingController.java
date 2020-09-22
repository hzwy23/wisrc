package com.wisrc.purchase.webapp.controller;

import com.wisrc.purchase.webapp.dto.inspection.GetArrivalProductDto;
import com.wisrc.purchase.webapp.service.InspectionService;
import com.wisrc.purchase.webapp.service.PurchasePlanService;
import com.wisrc.purchase.webapp.utils.Result;
import com.wisrc.purchase.webapp.vo.inspection.ArrivalProductEditVo;
import com.wisrc.purchase.webapp.vo.inspection.GetArrivalProductVo;
import com.wisrc.purchase.webapp.vo.invoking.ArrivalSelectorVo;
import com.wisrc.purchase.webapp.vo.invoking.GetQuantityVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/purchase/invoking")
@RestController
@Api(value = "/invoking", description = "外部接口调用控制器", tags = "外部接口")
public class InvokingController {
    @Autowired
    private InspectionService inspectionService;
    @Autowired
    private PurchasePlanService purchasePlanService;

    @ApiOperation(value = "验货方式选择框", notes = "")
    @RequestMapping(value = "/inspection/type/selector", method = RequestMethod.GET)
    public Result inspectionTypeSelector() {
        return inspectionService.inspectionTypeSelector();
    }

    @ApiOperation(value = "运费分摊原则选择框", notes = "")
    @RequestMapping(value = "/amrt/type/selector", method = RequestMethod.GET)
    public Result freightAmrtTypeSelector() {
        return inspectionService.freightAmrtTypeSelector();
    }

    @ApiOperation(value = "验货申请/提货单状态", notes = "")
    @RequestMapping(value = "/inspect/product/status/selector", method = RequestMethod.GET)
    public Result productStatusSelector() {
        return inspectionService.productStatusSelector();
    }

    @ApiOperation(value = "获取到货单号选择项",
            notes = "")
    @RequestMapping(value = "/selector/arrival", method = RequestMethod.GET)
    public Result inspectionSelector(ArrivalSelectorVo arrivalSelectorVo) {
        return inspectionService.inspectionSelector(arrivalSelectorVo);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = GetArrivalProductDto.class)
    })
    @ApiOperation(value = "获取到货产品信息",
            notes = "")
    @RequestMapping(value = "/arrival/product", method = RequestMethod.GET)
    public Result getArrivalProduct(@Valid GetArrivalProductVo getArrivalProductVo) {
        return inspectionService.getArrivalProduct(getArrivalProductVo);
    }

    @ApiOperation(value = "采购计划状态选择项",
            notes = "")
    @RequestMapping(value = "/selector/plan", method = RequestMethod.GET)
    public Result planStatusSelector() {
        return purchasePlanService.planStatusSelector();
    }

    @ApiOperation(value = "产品验货单修改到货产品数据",
            notes = "")
    @RequestMapping(value = "/arrival/product", method = RequestMethod.PUT)
    public Result editArrivalProduct(@Valid @RequestBody ArrivalProductEditVo arrivalProductEditVo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return new Result(400, errorList.get(0).getDefaultMessage(), arrivalProductEditVo);
        }
        return inspectionService.editArrivalProduct(arrivalProductEditVo);
    }

    @ApiOperation(value = "通过订单号查询采购订单所需参数", notes = "")
    @RequestMapping(value = "/order/quantity", method = RequestMethod.GET)
    public Result getQuantity(@Valid GetQuantityVo getQuantityVo) {
        return inspectionService.getQuantity(getQuantityVo);
    }
}
