package com.wisrc.purchase.webapp.controller;

import com.wisrc.purchase.webapp.service.PurchaseReturnService;
import com.wisrc.purchase.webapp.utils.Result;
import com.wisrc.purchase.webapp.vo.GetPurchaseReturnNewVo;
import com.wisrc.purchase.webapp.vo.purchaseReturn.add.AddPurchaseReturnVO;
import com.wisrc.purchase.webapp.vo.purchaseReturn.delete.ReturnBillPara;
import com.wisrc.purchase.webapp.vo.purchaseReturn.get.GetPurchaseReturnVO;
import com.wisrc.purchase.webapp.vo.purchaseReturn.set.SetPurchaseReturnVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@Api(tags = "采购退货管理")
@RequestMapping(value = "/purchase")
public class PurchaseReturnController {
    private final Logger logger = LoggerFactory.getLogger(PurchaseReturnController.class);
    @Autowired
    private PurchaseReturnService purchaseReturnService;

    @RequestMapping(value = "/purchaseReturn/info", method = RequestMethod.POST)
    @ApiOperation(value = "新增采购退货单", notes = "新增采购退货单")
    @ApiImplicitParam(name = "X-AUTH-ID", value = "当前用户", paramType = "header", dataType = "String", required = true)
    @ResponseBody
    public Result add(@Valid @RequestBody AddPurchaseReturnVO vo,
                      BindingResult bindingResult,
                      @RequestHeader("X-AUTH-ID") String userId) {
        try {
            return purchaseReturnService.add(vo, bindingResult, userId);
        } catch (Exception e) {
            return Result.failure(424, "新增采购退货单失败", e.getMessage());
        }
    }

    @RequestMapping(value = "/purchaseReturn/info", method = RequestMethod.PUT)
    @ApiOperation(value = "更新采购退货单", notes = "更新采购退货单")
    @ApiImplicitParam(name = "X-AUTH-ID", value = "当前用户", paramType = "header", dataType = "String", required = true)
    @ResponseBody
    public Result update(@Valid @RequestBody SetPurchaseReturnVO vo, BindingResult bindingResult, @RequestHeader("X-AUTH-ID") String userId) {
        try {
            return purchaseReturnService.update(vo, bindingResult, userId);
        } catch (Exception e) {
            return Result.failure(424, "更新采购退货单失败", e.getMessage());
        }
    }


    @ApiOperation(value = "根据returnBill查询采购退货单", notes = "根据returnBill查询采购退货单")
    @RequestMapping(value = "/purchaseReturn/info/{returnBill}", method = RequestMethod.GET)
    public Result findById(@PathVariable("returnBill") String returnBill) {
        try {
            return purchaseReturnService.findByReturnBill(returnBill);
        } catch (Exception e) {
            return Result.failure(424, "根据returnBill查询采购退货单失败", e.getMessage());
        }
    }

    @ApiOperation(value = "模糊查询采购退货单", notes = "模糊查询采购退货单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页（小于1或者null为不分页）", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "单页条数（小于1或者null为不分页）", required = false, dataType = "Integer", paramType = "query")
    })
    @RequestMapping(value = "/purchaseReturn/info/fuzzy", method = RequestMethod.GET)
    public Result fuzzy(@Valid GetPurchaseReturnVO vo, BindingResult bindingResult, Integer pageNum, Integer pageSize) {
        try {
            return purchaseReturnService.fuzzy(vo, bindingResult, pageNum, pageSize);
        } catch (Exception e) {
            return Result.failure(424, "模糊查询采购退货单失败", e.getMessage());
        }
    }

    @ApiOperation(value = "删除采购退货单", notes = "删除采购退货单")
    @RequestMapping(value = "/purchaseReturn/info/delete", method = RequestMethod.PUT)
    public Result delete(@Valid @RequestBody ReturnBillPara returnBillArray, BindingResult bindingResult, @RequestHeader("X-AUTH-ID") String userId) {
        try {
            return purchaseReturnService.delete(returnBillArray, bindingResult, userId);
        } catch (Exception e) {
            return Result.failure(424, "删除采购退货单失败", e.getMessage());
        }
    }

    @ApiOperation(value = "模糊查询导出采购退货单", notes = "模糊查询导出采购退货单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页（小于1或者null为不分页）", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "单页条数（小于1或者null为不分页）", required = false, dataType = "int", paramType = "query")
    })
    @RequestMapping(value = "/purchaseReturn/info/fuzzy/export", method = RequestMethod.GET)
    public void fuzzyExport(HttpServletResponse response, @Valid GetPurchaseReturnNewVo vo, BindingResult bindingResult, Integer pageNum, Integer pageSize) throws IOException {
        purchaseReturnService.fuzzyExport(response, vo, bindingResult, pageNum, pageSize);
    }

    @ApiOperation(value = "根据选中采购退货单导出", notes = "根据选中采购退货单导出")
    @RequestMapping(value = "/purchaseReturn/info/export", method = RequestMethod.GET)
    @ResponseBody
    public void export(HttpServletResponse response, @Valid ReturnBillPara returnBillArray, BindingResult bindingResult) throws IOException {
        purchaseReturnService.export(response, returnBillArray, bindingResult);
        return;
    }

    @ApiOperation(value = "修改退货单状态", notes = "退货单状态，1：待处理，2：已完成")
    @RequestMapping(value = "/purchaseReturn/{returnBill}/{statusCd}", method = RequestMethod.PUT)
    @ResponseBody
    public Result updateStatus(@PathVariable("statusCd") Integer statusCd, @PathVariable("returnBill") String returnBill) {
        return purchaseReturnService.changeStatus(statusCd, returnBill);
    }


    @ApiOperation(value = "新模糊查询采购退货单", notes = "模糊查询采购退货单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页（小于1或者null为不分页）", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "单页条数（小于1或者null为不分页）", required = false, dataType = "Integer", paramType = "query")
    })
    @RequestMapping(value = "/purchaseReturn/info/fuzzyNew", method = RequestMethod.GET)
    public Result fuzzy(@Valid GetPurchaseReturnNewVo vo, BindingResult bindingResult, Integer pageNum, Integer pageSize) {
        try {
            return purchaseReturnService.fuzzyNew(vo, bindingResult, pageNum, pageSize);
        } catch (Exception e) {
            return Result.failure(424, "新模糊查询采购退货单失败", e.getMessage());
        }
    }

}
