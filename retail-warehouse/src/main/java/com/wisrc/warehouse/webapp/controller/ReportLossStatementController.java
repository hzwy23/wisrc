package com.wisrc.warehouse.webapp.controller;

import com.wisrc.warehouse.webapp.entity.ReportLossStatementStatusAttrEntity;
import com.wisrc.warehouse.webapp.service.ReportLossStatementService;
import com.wisrc.warehouse.webapp.utils.Result;
import com.wisrc.warehouse.webapp.vo.reportLossStatement.add.AddReportLossStatementVO;
import com.wisrc.warehouse.webapp.vo.reportLossStatement.get.GetProductVO;
import com.wisrc.warehouse.webapp.vo.reportLossStatement.set.ReviewVO;
import com.wisrc.warehouse.webapp.vo.reportLossStatement.show.ShowFnSkuStockVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api(tags = "报损单管理")
@RequestMapping(value = "/warehouse")
public class ReportLossStatementController {

    @Autowired
    private ReportLossStatementService reportLossStatementService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-ID", value = "用户信息", required = true, dataType = "String", paramType = "header")
    })
    @RequestMapping(value = "/reportloss", method = RequestMethod.POST)
    @ApiOperation(value = "新建报损单")
    public Result insert(@Valid @RequestBody AddReportLossStatementVO vo, BindingResult bindingResult, @RequestHeader("X-AUTH-ID") String userId) {
        return reportLossStatementService.insert(vo, bindingResult, userId);
    }

    @RequestMapping(value = "/reportloss/{reportLossStatementId}", method = RequestMethod.GET)
    @ApiOperation(value = "通过报损单号查询详情")
    public Result getByReportLossStatementId(@PathVariable("reportLossStatementId") String reportLossStatementId) {
        return reportLossStatementService.getByReportLossStatementId(reportLossStatementId);
    }

    //不对外开发该接口
//    @RequestMapping(value = "/reportloss/{reportLossStatementId}", method = RequestMethod.DELETE)
//    @ApiOperation(value = "删除报损单")
//    public Result delete(@PathVariable("reportLossStatementId") String reportLossStatementId) {
//        return reportLossStatementService.delete(reportLossStatementId);
//    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-ID", value = "用户信息", required = true, dataType = "String", paramType = "header")
    })
    @RequestMapping(value = "/reportloss/review", method = RequestMethod.PUT)
    @ApiOperation(value = "审核报损单")
    public Result review(@Valid ReviewVO reviewVO, BindingResult bindingResult, @RequestHeader("X-AUTH-ID") String userId) {
        return reportLossStatementService.review(reviewVO, bindingResult, userId);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "reportLossStatementId", value = "报损单号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "cancelReason", value = "取消原因", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "X-AUTH-ID", value = "用户信息", required = true, dataType = "String", paramType = "header")
    })
    @RequestMapping(value = "/reportloss/cancel", method = RequestMethod.PUT)
    @ApiOperation(value = "取消审核报损单")
    public Result update(
            @RequestParam("reportLossStatementId") String reportLossStatementId,
            @RequestHeader("X-AUTH-ID") String userId,
            String cancelReason
    ) {
        return reportLossStatementService.cancel(reportLossStatementId, cancelReason, userId);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "createTimeStart", value = "开始时间", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "createTimeEnd", value = "结束时间", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "statusCd", value = "状态编码", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "warehouseId", value = "仓库编码", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "keyWords", value = "关键字(报损单号/库存SKU/FnSKU/产品名称)", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "当前页(null或者小于1为全查询，不分页)", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "单页条数(null或者小于1为全查询，不分页)", required = false, dataType = "int", paramType = "query")
    })
    @RequestMapping(value = "/reportloss/fuzzy", method = RequestMethod.GET)
    @ApiOperation(value = "模糊查询审核报损单")
    public Result fuzzy(
            Integer statusCd,
            String createTimeStart,
            String createTimeEnd,
            String warehouseId,
            String keyWords,
            Integer pageNum,
            Integer pageSize
    ) {
        return reportLossStatementService.fuzzy(statusCd, createTimeStart, createTimeEnd, warehouseId, keyWords, pageNum, pageSize);
    }

    @RequestMapping(value = "/reportloss/status/attr", method = RequestMethod.GET)
    @ApiOperation(value = "报损单号状态码表")
    public Result getStatusAttr() {
        List<ReportLossStatementStatusAttrEntity> statusAttrList = reportLossStatementService.getStatusAttr();
        return Result.success(statusAttrList);
    }


    @RequestMapping(value = "/reportloss/product", method = RequestMethod.GET)
    @ApiOperation(value = "验证sku是否存在", response = ShowFnSkuStockVO.class)
    public Result getProduct(@Valid GetProductVO getProductVO, BindingResult bindingResult) {
        return reportLossStatementService.getProduct(getProductVO, bindingResult);
    }

}
