package com.wisrc.quality.webapp.controller;

import com.wisrc.quality.webapp.entity.InspectionApplyTypeAttrEntity;
import com.wisrc.quality.webapp.service.InspectionApplyService;
import com.wisrc.quality.webapp.utils.Result;
import com.wisrc.quality.webapp.vo.inspectionApply.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.sql.Date;
import java.util.LinkedHashMap;

@RestController
@Api(tags = "验货申请管理")
@RequestMapping(value = "/quality")
public class InspectionApplyController {

    @Autowired
    private InspectionApplyService inspectionApplyService;

    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功，返回值数据结构，请参考Example Value。 " +
                    "当返回值中pageNum和pageSize为-1时，表示全表查询，未分页",
                    response = InspectionQueryResultVO.class),
            @ApiResponse(code = 401, message = "权限不足", response = Result.class),
            @ApiResponse(code = 404, message = "请求的资源不存在", response = Result.class)
    })
    @ApiOperation(value = "获取验货申请单列表", notes = "根据条件获取验货单申请列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "开始页数", dataType = "String", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "一页的总数", dataType = "String", paramType = "query", defaultValue = "10"),
            @ApiImplicitParam(name = "orderId", value = "采购订单号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "employeeId", value = "申请人", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "inspectionStartTime", value = "预计验货开始时间", dataType = "Date", paramType = "query"),
            @ApiImplicitParam(name = "inspectionEndTime", value = "预计验货结束时间", dataType = "Date", paramType = "query"),
            @ApiImplicitParam(name = "statusCd", value = "状态", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "inspectionTypeCd", value = "验货方式", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "keyWord", value = "关键字", dataType = "String", paramType = "query"),

    })
    @RequestMapping(value = "/apply", method = RequestMethod.GET)
    public Result findByCond(@RequestParam(value = "pageNum", required = false) String pageNum, @RequestParam(value = "pageSize", required = false) String pageSize,
                             @RequestParam(value = "orderId", required = false) String orderId, @RequestParam(value = "employeeId", required = false) String employeeId,
                             @RequestParam(value = "inspectionStartTime", required = false) Date inspectionStartTime,
                             @RequestParam(value = "inspectionEndTime", required = false) Date inspectionEndTime,
                             @RequestParam(value = "statusCd", required = false) String statusCd, @RequestParam(value = "inspectionTypeCd", required = false) String inspectionTypeCd,
                             @RequestParam(value = "keyWord", required = false) String keyWord) {
        if (pageNum == null || pageSize == null) {
            return Result.success(inspectionApplyService.findAll());
        }
        try {
            int num = Integer.valueOf(pageNum);
            int size = Integer.valueOf(pageSize);
            LinkedHashMap map = inspectionApplyService.findByCond(num, size, orderId, employeeId, inspectionStartTime, inspectionEndTime, statusCd, inspectionTypeCd, keyWord);
            return Result.success(map);
        } catch (NumberFormatException e) {
            return Result.failure(200, "pageNum或pageSize值异常，分页值只能是数字，默认查询全部补货单数据", inspectionApplyService.findAll());
        }
    }

    @ApiOperation(value = "查看详情", notes = "根据验货单id查出验货单详细信息", response = InspectionDetailVO.class)
    @RequestMapping(value = "/apply/{inspectionId}", method = RequestMethod.GET)
    public Result findById(@PathVariable("inspectionId") String inspectionId) {
        try {
            InspectionDetailVO map = inspectionApplyService.findById(inspectionId);
            return Result.success(map);
        } catch (Exception e) {
            return Result.failure(390, "查询详情失败", null);
        }
    }

    @ApiOperation(value = "新建验货申请", notes = "新增验货单申请接口")
    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    public Result saveInspection(@Valid @RequestBody InspectionAddVO addVO, BindingResult result,
                                 @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "") @ApiIgnore String userId) {

        if (result.hasErrors()) {
            return Result.failure(390, result.getAllErrors().get(0).getDefaultMessage(), result.getAllErrors());
        }

        try {
            inspectionApplyService.saveInspectionInfo(addVO, userId);
            return Result.success("新建验货申请成功");
        } catch (Exception e) {
            return Result.failure(390, "新建验货申请失败", null);
        }

    }

    // 编辑验货单
    @ApiOperation(value = "编辑验货申请", notes = "编辑验货申请单接口")
    @RequestMapping(value = "/apply", method = RequestMethod.PUT)
    public Result updateInspection(@Valid @RequestBody InspectionEditVO editVO, BindingResult result,
                                   @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "") @ApiIgnore String userId) {
        if (result.hasErrors()) {
            return Result.failure(390, result.getAllErrors().get(0).getDefaultMessage(), result.getAllErrors());
        }

        return inspectionApplyService.updateInspectionInfo(editVO, userId);
    }

    @ApiOperation(value = "更新验货方式", notes = "单独更新验货方式、有任何一项sku的验货数量有值，不允许修改验货方式。")
    @RequestMapping(value = "/apply/type", method = RequestMethod.PUT)
    public Result updateInspecType(@RequestParam(value = "inspectionId", required = true) String inspectionId,
                                   @RequestParam(value = "inspectionTypeCd", required = true) String inspectionTypeCd,
                                   @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "") @ApiIgnore String userId) {

        try {
            int inspectionCd = Integer.valueOf(inspectionTypeCd);
            String msg = inspectionApplyService.updateInspecType(inspectionId, inspectionCd, userId);
            return Result.success(msg);
        } catch (NumberFormatException e) {
            String msg = inspectionApplyService.updateInspecType(inspectionId, 0, userId);
            return Result.success(msg);
        }

    }

    @ApiOperation(value = "根据验货申请单id删除验货申请单", notes = "物理删除验货申请单")
    @RequestMapping(value = "/apply", method = RequestMethod.DELETE)
    public Result deleteInspection(@RequestParam("inspectionIds") String[] inspectionIds) {
        try {
            if (inspectionApplyService.getStatusCd(inspectionIds) == 1) {
                inspectionApplyService.deleteInspection(inspectionIds);
                return Result.success("验货申请单删除成功");
            } else {
                return Result.failure(390, "只有状态为未开始的才能删除，请重新选择", null);
            }
        } catch (Exception e) {
            return Result.failure(390, "验货申请单删除失败", null);
        }
    }

    @ApiOperation(value = "获取所有验货方式", notes = "空白验货方式则状态为未开始、工厂验货则状态为待检验、免检或仓库验货则状态为已完成,当返回值中total和pages为-1时，表示全表查询",
            response = InspectionApplyTypeAttrEntity.class)
    @RequestMapping(value = "apply/type", method = RequestMethod.GET)
    public Result findAllInsType() {
        try {
            LinkedHashMap allType = inspectionApplyService.findAllType();
            return Result.success(allType);
        } catch (Exception e) {
            return Result.failure(390, "查询所有验货方式失败", null);
        }
    }

    @ApiOperation(value = "获取所有状态", notes = "获取所有状态,当返回值中total和pages为-1时，表示全表查询",
            response = InspectionApplyTypeAttrEntity.class)
    @RequestMapping(value = "/apply/status", method = RequestMethod.GET)
    public Result findAllStatus() {
        try {
            LinkedHashMap allStatus = inspectionApplyService.findAllStatus();
            return Result.success(allStatus);
        } catch (Exception e) {
            return Result.failure(390, "查询所有状态失败", null);
        }
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "开始页数", dataType = "String", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "一页的总数", dataType = "String", paramType = "query", defaultValue = "10"),
            @ApiImplicitParam(name = "orderId", value = "采购订单号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "supplierName", value = "供应商名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "skuId", value = "skuId", dataType = "Date", paramType = "query"),
            @ApiImplicitParam(name = "productNameCN", value = "产品中文名", dataType = "Date", paramType = "query"),
            @ApiImplicitParam(name = "inspectionId", value = "验货单Id", dataType = "String", paramType = "query"),
    })
    @ApiOperation(value = "外部接口调用，通过产品、订单等查询所需数据,当pageNum和pageSize为null时表示不分页")
    @RequestMapping(value = "/apply/data", method = RequestMethod.GET)
    public Result getInspectionData(@RequestParam(value = "pageNum", required = false) String pageNum,
                                    @RequestParam(value = "pageSize", required = false) String pageSize,
                                    @RequestParam(value = "orderId", required = false) String orderId,
                                    @RequestParam(value = "supplierName", required = false) String supplierName,
                                    @RequestParam(value = "skuId", required = false) String skuId,
                                    @RequestParam(value = "productNameCN", required = false) String productNameCN,
                                    @RequestParam(value = "inspectionId", required = false) String inspectionId) {
        // 如果参数为空，则查询直接查询全部
        if (pageNum == null || pageSize == null) {
            return Result.success(inspectionApplyService.findByCond(orderId, supplierName, skuId, productNameCN, inspectionId));
        }

        try {
            int num = Integer.valueOf(pageNum);
            int size = Integer.valueOf(pageSize);
            return Result.success(inspectionApplyService.findByCond(num, size, orderId, supplierName, skuId, productNameCN, inspectionId));
        } catch (NumberFormatException e) {
            return Result.success(200, "pageNum或pageSize值异常，分页值只能是数字，默认查询全部店铺数据", inspectionApplyService.findByCond(orderId, supplierName, skuId, productNameCN, inspectionId));
        }
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "采购订单号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "keyWords", value = "供应商名称或者产品中文名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "skuId", value = "skuId", dataType = "Date", paramType = "query"),
    })
    @ApiOperation(value = "外部接口调用，通过产品、订单等查询所需数据")
    @RequestMapping(value = "/apply/words", method = RequestMethod.GET)
    public Result getInspectionDataByWords(@RequestParam(value = "orderId", required = false) String orderId,
                                           @RequestParam(value = "keyWords", required = false) String keyWords,
                                           @RequestParam(value = "skuId", required = false) String skuId) {

        try {
            return Result.success(inspectionApplyService.findByCond(orderId, keyWords, skuId));
        } catch (NumberFormatException e) {
            return Result.success(390, "获取验货单数据失败", null);
        }
    }

    @ApiOperation(value = "通过采购订单和产品id集合查出验货申请信息", response = OrderIdListVO.class)
    @RequestMapping(value = "/apply/merchandise/msku", method = RequestMethod.POST)
    public Result getInsByOrderIdList(@RequestBody OrderIdRequestVO orderIdRequestVO) {
        try {
            LinkedHashMap resultList = inspectionApplyService.findByOrderId(orderIdRequestVO, "query");
            return Result.success(resultList);
        } catch (Exception e) {
            return Result.failure(390, "获取验货单数据失败", null);
        }
    }

    @ApiOperation(value = "通过采购订单和产品id集合查出完工数", response = OrderIdQuerySumVO.class)
    @RequestMapping(value = "apply/merchandise/skusum", method = RequestMethod.POST)
    public Result getCompleteByOrderList(@RequestBody OrderIdRequestVO orderIdRequestVO) {
        try {
            LinkedHashMap resultList = inspectionApplyService.findByOrderId(orderIdRequestVO, "sum");
            return Result.success(resultList);
        } catch (Exception e) {
            return Result.failure(390, "获取验货单数据失败", null);
        }
    }

    @ApiOperation(value = "通过采购订单查验货申请单")
    @RequestMapping(value = "/apply/by/orderId", method = RequestMethod.GET)
    public Result getInspectionApplyInfo(@RequestParam("orderId") String orderId) {
        try {
            int a = inspectionApplyService.getInspectionApplyInfo(orderId);
            return Result.success(a);
        } catch (Exception e) {
            return Result.failure(390, "获取验货单数据失败", null);
        }
    }
}
