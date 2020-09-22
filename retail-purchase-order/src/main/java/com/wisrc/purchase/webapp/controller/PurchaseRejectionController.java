package com.wisrc.purchase.webapp.controller;

import com.wisrc.purchase.webapp.service.PurchaseRejectionService;
import com.wisrc.purchase.webapp.utils.Result;
import com.wisrc.purchase.webapp.vo.GetPurchaseRejectionNewVO;
import com.wisrc.purchase.webapp.vo.purchaseRejection.RejectionIdPara;
import com.wisrc.purchase.webapp.vo.purchaseRejection.add.AddPurchaseRejectionVO;
import com.wisrc.purchase.webapp.vo.purchaseRejection.get.GetPurchaseRejectionVO;
import com.wisrc.purchase.webapp.vo.purchaseRejection.set.SetPurchaseRejectionVO;
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
@Api(tags = "采购拒收单管理")
@RequestMapping(value = "/purchase")
public class PurchaseRejectionController {
    private final Logger logger = LoggerFactory.getLogger(PurchaseRejectionController.class);
    @Autowired
    private PurchaseRejectionService purchaseRejectionService;

    @RequestMapping(value = "/purchaseRejection/info", method = RequestMethod.POST)
    @ApiOperation(value = "新增采购拒收单", notes = "新增采购拒收单")
    @ApiImplicitParam(name = "X-AUTH-ID", value = "当前用户", paramType = "header", dataType = "String", required = true)
    @ResponseBody
    public Result add(@Valid @RequestBody AddPurchaseRejectionVO vo, BindingResult bindingResult,
                      @RequestHeader("X-AUTH-ID") String userId) {
        try {
            return purchaseRejectionService.add(vo, bindingResult, userId);
        } catch (Exception e) {
            return Result.failure(424, "新增采购拒收单失败", e.getMessage());
        }
    }

    @RequestMapping(value = "/purchaseRejection/info", method = RequestMethod.PUT)
    @ApiOperation(value = "更新采购拒收单", notes = "更新采购拒收单")
    @ApiImplicitParam(name = "X-AUTH-ID", value = "当前用户", paramType = "header", dataType = "String", required = true)
    @ResponseBody
    public Result update(@Valid @RequestBody SetPurchaseRejectionVO vo, BindingResult bindingResult,
                         @RequestHeader("X-AUTH-ID") String userId) {
        try {
            return purchaseRejectionService.update(vo, bindingResult, userId);
        } catch (Exception e) {
            return Result.failure(424, "更新采购拒收单失败", e.getMessage());
        }
    }


    @ApiOperation(value = "根据rejectionId查询采购拒收单", notes = "根据rejectionId查询采购拒收单")
    @RequestMapping(value = "/purchaseRejection/info/{rejectionId}", method = RequestMethod.GET)
    public Result findById(@PathVariable("rejectionId") String rejectionId) {
        try {
            return purchaseRejectionService.findById(rejectionId);
        } catch (Exception e) {
            return Result.failure(424, "根据rejectionId查询采购拒收单失败", e.getMessage());
        }
    }

    @ApiOperation(value = "模糊查询采购拒收单", notes = "模糊查询采购拒收单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页（小于1或者null为不分页）", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "单页条数（小于1或者null为不分页）", required = false, dataType = "Integer", paramType = "query")
    })
    @RequestMapping(value = "/purchaseRejection/info/fuzzy", method = RequestMethod.GET)
    public Result fuzzy(@Valid GetPurchaseRejectionVO vo, BindingResult bindingResult, Integer pageNum, Integer pageSize) {
        try {
            return purchaseRejectionService.fuzzy(vo, bindingResult, pageNum, pageSize);
        } catch (Exception e) {
            return Result.failure(424, "模糊查询采购拒收单失败", e.getMessage());
        }
    }

    @ApiOperation(value = "删除采购拒收单", notes = "删除采购拒收单")
    @RequestMapping(value = "/purchaseRejection/info/delete", method = RequestMethod.PUT)
    public Result delete(@Valid @RequestBody RejectionIdPara rejectionIdArray, BindingResult bindingResult) {
        try {
            return purchaseRejectionService.delete(rejectionIdArray, bindingResult);
        } catch (Exception e) {
            return Result.failure(424, "删除采购拒收单失败", e.getMessage());
        }
    }

    @ApiOperation(value = "模糊查询导出采购拒收单", notes = "模糊查询导出采购拒收单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页（小于1或者null为不分页）", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "单页条数（小于1或者null为不分页）", required = false, dataType = "int", paramType = "query")
    })
    @RequestMapping(value = "/purchaseRejection/info/fuzzy/export", method = RequestMethod.GET)
    public void fuzzyExport(HttpServletResponse response, @Valid GetPurchaseRejectionNewVO vo, BindingResult bindingResult, Integer pageNum, Integer pageSize) throws IOException {
        purchaseRejectionService.fuzzyExport(response, vo, bindingResult, pageNum, pageSize);
    }

    @ApiOperation(value = "根据选中采购拒收单号导出采购拒收单", notes = "根据选中采购拒收单号导出采购拒收单")
    @RequestMapping(value = "/purchaseRejection/info/export", method = RequestMethod.GET)
    public void export(HttpServletResponse response, RejectionIdPara rejectionIdArray) throws IOException {
        purchaseRejectionService.export(response, rejectionIdArray);
    }

    @ApiOperation(value = "根据采购拒收单修改采购拒收单的状态", notes = "根据采购拒收单修改采购拒收单的状态")
    @RequestMapping(value = "/purchaseRejection/{rejectionId}/{statusCd}", method = RequestMethod.PUT)
    public Result changeStatus(@PathVariable("rejectionId") String rejectionId, @PathVariable("statusCd") Integer statusCd) {
        try {
            purchaseRejectionService.changeStatus(rejectionId, statusCd);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(424, "改变采购拒收单状态失败，请刷新后重试", rejectionId);
        }

    }

    @ApiOperation(value = "新模糊查询采购拒收单", notes = "模糊查询采购拒收单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页（小于1或者null为不分页）", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "单页条数（小于1或者null为不分页）", required = false, dataType = "Integer", paramType = "query")
    })
    @RequestMapping(value = "/purchaseRejection/info/fuzzyNew", method = RequestMethod.GET)
    public Result fuzzyNew(@Valid GetPurchaseRejectionNewVO vo, BindingResult bindingResult, Integer pageNum, Integer pageSize) {
        try {
            return purchaseRejectionService.fuzzyNew(vo, bindingResult, pageNum, pageSize);
        } catch (Exception e) {
            return Result.failure(424, "新模糊查询采购拒收单失败", e.getMessage());
        }
    }

}
