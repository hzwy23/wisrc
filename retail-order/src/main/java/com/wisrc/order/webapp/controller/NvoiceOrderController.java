package com.wisrc.order.webapp.controller;

import com.wisrc.order.webapp.service.NvoiceOrderService;
import com.wisrc.order.webapp.utils.Result;
import com.wisrc.order.webapp.vo.NvoiceOrderInfoDto;
import com.wisrc.order.webapp.vo.NvoiceOrderPageDto;
import com.wisrc.order.webapp.vo.saleNvoice.SaleNvoicePageVo;
import com.wisrc.order.webapp.vo.saleNvoice.SaleNvoiceSaveVo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/order/invoice")
@Api(value = "/invoice", description = "销售管理", tags = "发货单管理")
public class NvoiceOrderController {
    @Autowired
    private NvoiceOrderService nvoiceOrderService;

    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = NvoiceOrderPageDto.class)
    })
    @ApiOperation(value = "发货单页面信息", notes = "")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Result saleNvoicePage(@Valid SaleNvoicePageVo saleNvoicePageVo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return Result.failure(400, "", errorList.get(0).getDefaultMessage());
        }
        return nvoiceOrderService.saleNvoicePage(saleNvoicePageVo);
    }

    @ApiOperation(value = "状态选择项", notes = "")
    @RequestMapping(value = "/selector/status", method = RequestMethod.GET)
    public Result invoiceStatus() {
        return nvoiceOrderService.invoiceStatus();
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = NvoiceOrderInfoDto.class)
    })
    @ApiOperation(value = "获取发货单信息", notes = "")
    @RequestMapping(value = "/detail/{invoiceNumber}", method = RequestMethod.GET)
    public Result getSaleNvoice(@PathVariable("invoiceNumber") String invoiceNumber) {
        return nvoiceOrderService.getSaleNvoice(invoiceNumber);
    }

    @ApiOperation(value = "物流费用导入模板", notes = "")
    @RequestMapping(value = "/excel", method = RequestMethod.GET)
    public Result excelModel() {
        return nvoiceOrderService.excelModel();
    }

    @ApiOperation(value = "物流费用excel上传", notes = "")
    @RequestMapping(value = "/excel", method = RequestMethod.POST)
    public Result excelModel(@RequestParam("freightExcel") MultipartFile freightExcel) {
        return nvoiceOrderService.excelHandle(freightExcel);
    }

    @ApiOperation(value = "新建发货单", notes = "")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result saveSaleNvoice(@Valid @RequestBody SaleNvoiceSaveVo saleNvoiceSaveVo, BindingResult bindingResult, @RequestHeader("X-AUTH-ID") @ApiIgnore String userId) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return Result.failure(400, "", errorList.get(0).getDefaultMessage());
        }
        return nvoiceOrderService.saveSaleNvoice(saleNvoiceSaveVo, userId);
    }

    @ApiOperation(value = "通过订单ids,查询出每个订单id对应的发货单,并返回一组发货单详细", notes = "")
    @ApiImplicitParams(value =
    @ApiImplicitParam(value = "订单ids", name = "ids", paramType = "query", dataType = "List", required = true)
    )
    @RequestMapping(value = "/outside/invoice", method = RequestMethod.GET)
    public Result nvoiceOrderByIds(@RequestParam("ids") List<String> ids) {
        return nvoiceOrderService.nvoiceOrderByIds(ids);
    }

    @ApiOperation(value = "通过一组订单orderIds批量生成发货单，每一个订单id生成一张发货单", notes = "")
    @ApiImplicitParams(value =
    @ApiImplicitParam(value = "订单ids", name = "ids", paramType = "query", dataType = "List", required = true)
    )
    @RequestMapping(value = "/outside/invoice/save", method = RequestMethod.GET)
    public Result saveNvoiceOrderByIds(@RequestParam("ids") List<String> ids, @RequestHeader("X-AUTH-ID") @ApiIgnore String userId) {
        return nvoiceOrderService.saveNvoiceOrderByIds(ids, userId);
    }
}
