package com.wisrc.replenishment.webapp.controller;


import com.wisrc.replenishment.webapp.dao.LogisticsBillDao;
import com.wisrc.replenishment.webapp.entity.LogisticsBillEnity;
import com.wisrc.replenishment.webapp.service.LogisticsBillService;
import com.wisrc.replenishment.webapp.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@Api(tags = "清关发票管理")
@RequestMapping(value = "/replenishment")
public class CustomsClearanceController {
    @Autowired
    private LogisticsBillService billService;
    @Autowired
    private LogisticsBillDao billDao;

    @ApiOperation(value = "完善清关发票信息", notes = "完善清关发票信息同时完善清单商品信息详细部分数据<br/>" +
            "报价ID作为外键约束存入其他表中。", response = LogisticsBillEnity.class)
    @RequestMapping(value = "/clearance", method = RequestMethod.POST)
    public Result add(@RequestBody @Valid LogisticsBillEnity ele, BindingResult result,
                      @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "default") @ApiIgnore String userId) {
     /*   if (result.hasErrors()) {
            return Result.failure(390, result.getAllErrors().get(0).getDefaultMessage(), result.getAllErrors());
        }*/
        ele.setUserId(userId);
        try {
            billService.addBillInfo(ele);
        } catch (Exception e) {
            return Result.failure(390, "完善完善清关信息异常", e.getMessage());
        }
        return Result.success("清关发票完善成功");
    }


    @ApiOperation(value = "查询清单发票信息", notes = "通过传入的运单号商品号店铺号查询发票详细信息。", response = LogisticsBillEnity.class)
    @RequestMapping(value = "/clearance/{wayBillId}", method = RequestMethod.GET)
    public Result findHistoryOffer(@PathVariable(value = "wayBillId") String wayBillId,
                                   @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "default") @ApiIgnore String userId) {
        LogisticsBillEnity billEnity = null;
        try {
            billEnity = billService.findClearanceDetail(wayBillId);
        } catch (Exception e) {
            return Result.failure(390, "查询清关信息异常", e);
        }
        return Result.success(billEnity);
    }

    @ApiOperation(value = "物理删除清单发票信息", notes = "通过传入的运单号商品号店铺号查询发票详细信息。", response = LogisticsBillEnity.class)
    @RequestMapping(value = "/clearance/{wayBillId}", method = RequestMethod.DELETE)
    public Result deleteClearance(@PathVariable(value = "wayBillId") String wayBillId) {
        try {
            billService.deleteByWayBIllId(wayBillId);
        } catch (Exception e) {
            return Result.failure(390, "查询清关信息异常", e);
        }
        return Result.success("删除清单发票信息成功");
    }


    @ApiOperation(value = "物理删除清单商品历史信息", notes = "通过传入的运单号商品号店铺号查询发票详细信息。", response = LogisticsBillEnity.class)
    @RequestMapping(value = "/clearance/{shopId}/{mskuId}", method = RequestMethod.DELETE)
    public Result deleteClearanceProduct(@PathVariable(value = "shopId") String shopId, @PathVariable(value = "mskuId") String mskuId) {
        try {
            billService.deleteClearanceProduct(shopId, mskuId);
        } catch (Exception e) {
            return Result.failure(390, "删除清单商品历史信息异常", e);
        }
        return Result.success("删除清单发票信息成功");
    }

    @ApiOperation(value = "清关发票信息下载")
    @RequestMapping(value = "/clearance/downLoad/{wayBillId}", method = RequestMethod.GET)
    public Result downLoad(HttpServletRequest request, HttpServletResponse response, @PathVariable(value = "wayBillId") String wayBillId) {
        try {
            billService.waybillExcel(wayBillId, request, response);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(390, e.getMessage(), e);
        }
        return Result.success("清关发票信息下载成功");
    }

    private String toNotNull(Object o) {
        if (o != null) {
            return o.toString();
        }
        return "";
    }
}
