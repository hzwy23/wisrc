package com.wisrc.replenishment.webapp.controller;

import com.wisrc.replenishment.webapp.dto.WayBillInfo.GetWaybillExceptionDto;
import com.wisrc.replenishment.webapp.entity.WaybillRelEntity;
import com.wisrc.replenishment.webapp.service.ReplenishShippingDataService;
import com.wisrc.replenishment.webapp.service.WaybillInfoAttrService;
import com.wisrc.replenishment.webapp.service.WaybillInfoService;
import com.wisrc.replenishment.webapp.utils.Result;
import com.wisrc.replenishment.webapp.vo.ReplenishShippingDataListVO;
import com.wisrc.replenishment.webapp.vo.waybill.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@RestController
@Api(tags = "物流跟踪单")
@RequestMapping(value = "/replenishment")
public class WaybillInfoController {
    @Autowired
    WaybillInfoService waybillInfoService;
    @Autowired
    WaybillInfoAttrService waybillInfoAttrService;
    @Autowired
    private ReplenishShippingDataService replenishShippingDataService;

    @ApiOperation(value = "获取物流跟踪单信息列表",
            notes = "获取物流跟踪单信息列表", response = WaybillInfoVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", defaultValue = "1", value = "页码", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "pageSize", defaultValue = "5", value = "每页条数", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "waybillOrderDateBegin", value = "下单日期(开始)", paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "waybillOrderDateEnd", value = "下单日期(结束)", paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "shipmentId", value = "物流商", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "customsCd", defaultValue = "0", value = "报关类型", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "warehouseId", value = "发货仓", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "employeeId", value = "负责人", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "logisticsTypeCd", defaultValue = "0", value = "状态", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "Keyword", value = "关键字", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "isLackLogistics", value = "少物流信息(0表示触发此条件)", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "isLackShipment", value = "少发货数(0表示触发此条件)", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "isLackCustomsDeclare", value = "少报关资料(0表示触发此条件)", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "isLackCustomsClearance", value = "少清关发票(0表示触发此条件)", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "waybillId", value = "物流交运单", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "batchNumber", value = "补货批次", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fbaReplenishmentId", value = "补货单ID", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "skuId", value = "产品ID", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "productName", value = "产品名称", paramType = "query", dataType = "String")
    })
    @RequestMapping(value = "/waybill/info/fine", method = RequestMethod.GET)
    @ResponseBody
    public Result findAllFine(@RequestParam(value = "pageNum", required = false) String pageNum,
                              @RequestParam(value = "pageSize", required = false) String pageSize,
                              @RequestParam(value = "waybillOrderDateBegin", required = false) Date waybillOrderDateBegin,
                              @RequestParam(value = "waybillOrderDateEnd", required = false) Date waybillOrderDateEnd,
                              @RequestParam(value = "shipmentId", required = false) String shipmentId,
                              @RequestParam(value = "customsCd", required = false) Integer customsCd,
                              @RequestParam(value = "warehouseId", required = false) String warehouseId,
                              @RequestParam(value = "employeeId", required = false) String employeeId,
                              @RequestParam(value = "logisticsTypeCd", required = false) Integer logisticsTypeCd,
                              @RequestParam(value = "Keyword", required = false) String Keyword,
                              @RequestParam(value = "isLackLogistics", required = false) String isLackLogistics,
                              @RequestParam(value = "isLackShipment", required = false) String isLackShipment,
                              @RequestParam(value = "isLackCustomsDeclare", required = false) String isLackCustomsDeclare,
                              @RequestParam(value = "isLackCustomsClearance", required = false) String isLackCustomsClearance,
                              @RequestParam(value = "waybillId", required = false) String waybillId,
                              @RequestParam(value = "batchNumber", required = false) String batchNumber,
                              @RequestParam(value = "fbaReplenishmentId", required = false) String fbaReplenishmentId,
                              @RequestParam(value = "skuId", required = false) String skuId,
                              @RequestParam(value = "productName", required = false) String productName) {
        try {
            return waybillInfoService.findInfoFine(pageNum, pageSize, waybillOrderDateBegin, waybillOrderDateEnd, shipmentId, customsCd, warehouseId, employeeId, logisticsTypeCd, Keyword, isLackLogistics, isLackShipment, isLackCustomsDeclare, isLackCustomsClearance, waybillId, batchNumber, fbaReplenishmentId, skuId, productName);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }


    @ApiOperation(value = "根据跟踪单ID查询物流跟踪单详细信息",
            notes = "根据跟踪单ID查询物流跟踪单详细信息", response = WaybillDetailsVO.class)
    @RequestMapping(value = "/waybill/infobyid", method = RequestMethod.GET)
    @ResponseBody
    public Result findInfoById(@RequestParam(value = "waybillId", required = true) String waybillId) {
        try {
            return Result.success(200, "成功", waybillInfoService.findDetailsById(waybillId));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.success(390, "查询物流单详情信息异常", "");
        }
    }


    @RequestMapping(value = "/waybill/add", method = RequestMethod.POST)
    @ApiOperation(value = "新增修改物流跟踪单", notes = "新增修改物流跟踪单", response = WaybillDetailsVO.class)
    @ResponseBody
    public Result addBasisInfo(@Valid @RequestBody AddWaybillVO detailsVO, BindingResult result, @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {
        if (result.hasErrors()) {
            return Result.failure(390, result.getAllErrors().get(0).getDefaultMessage(), detailsVO);
            //       return Result.failure(390,result.getAllErrors().get(0).toString(), result.getAllErrors());
        }

        List<WaybillRelEntity> list = detailsVO.getWaybillRelEntityList();
        if (list == null && list.size() == 0) {
            return Result.failure(423, "FBA补货单号为空，不能进行物流交运", detailsVO);
        }

        try {
            return waybillInfoService.addWaybillInfo(detailsVO, userId);
        } catch (Exception e) {
            return Result.success(390, "物流跟踪单新增或修改信息异常", "");
        }
    }

    @RequestMapping(value = "/waybill/delete", method = RequestMethod.PUT)
    @ApiOperation(value = "删除物流跟踪单", notes = "删除物流跟踪单")
    @ResponseBody
    public Result deleteInfo(@RequestParam(value = "waybillId", required = true) String waybillId,
                             @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {

        try {
            waybillInfoService.deleteWaybill(waybillId, userId);
            return Result.success(200, "删除物流跟踪单成功", "");
        } catch (Exception e) {
            return Result.success(390, "删除物流跟踪单异常", "");
        }
    }

    @RequestMapping(value = "/waybill/confirm/shipments", method = RequestMethod.PUT)
    @ApiOperation(value = "确定发货", notes = "确定发货")
    @ResponseBody
    public Result confirmShipments(@RequestBody List<ReplenishShippingDataListVO> list, @RequestParam(value = "waybillId", required = true) String waybillId, @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {
        try {
            return waybillInfoService.confirmShipments(list, waybillId, userId);
        } catch (Exception e) {
            return Result.success(390, "确定发货异常", "");
        }
    }


    /*@RequestMapping(value = "/waybill/confirm/oversea", method = RequestMethod.PUT)
    @ApiOperation(value = "虚拟仓/海外仓确定发货", notes = "确定发货")
    @ResponseBody
    public Result confirmOverseaShipments(@RequestBody List<OverseaSendVO> list, @RequestParam(value = "waybillId", required = true) String waybillId, @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {
        try {
            return waybillInfoService.confirmShipments(list, waybillId, userId);
        } catch (Exception e) {
            return Result.success(390, "确定发货异常", "");
        }
    }*/


    @RequestMapping(value = "/waybill/confirm/acceptance", method = RequestMethod.PUT)
    @ApiOperation(value = "确定签收", notes = "确定签收", response = WaybillAcceptanceVO.class)
    @ResponseBody
    public Result confirmAcceptance(@RequestBody WaybillAcceptanceVO vo, @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {
        try {
            return waybillInfoService.confirmAcceptance(vo, userId);
            // Result.success(200, "更新物流信息成功", "");
        } catch (Exception e) {
            return Result.success(390, "更新物流信息异常", "");
        }
    }

    @RequestMapping(value = "/waybill/perfect/update", method = RequestMethod.PUT)
    @ApiOperation(value = "完善物流信息", notes = "完善更新物流信息")
    @ResponseBody
    public Result updateInfo(@RequestBody PerfectWaybillInfoVO vo, @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {
        vo.setModifyTime(new Timestamp(System.currentTimeMillis()));
        vo.setModifyUser(userId);
        try {
            waybillInfoService.updateWaybill(vo);
            return Result.success(200, "更新物流信息成功", "");
        } catch (Exception e) {
            return Result.success(390, "更新物流信息异常", "");
        }
    }

    @RequestMapping(value = "/waybill/exception", method = RequestMethod.POST)
    @ApiOperation(value = "新增标记异常", notes = "新增物流跟踪单标记异常", response = WaybillExceptionVO.class)
    @ResponseBody
    public Result addException(@RequestBody WaybillExceptionVO exceptionVO, BindingResult result, @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {
        try {
            waybillInfoService.addException(exceptionVO, userId);
            return Result.success(200, "新增标记异常成功", "");
        } catch (Exception e) {
            return Result.success(390, "物流跟踪单新增信息异常", "");
        }
    }

    @ApiOperation(value = "更新备注说明")
    @RequestMapping(value = "/waybill/remark", method = RequestMethod.PUT)
    public Result updateRemark(@RequestParam("waybillId") String waybillId,
                               @RequestParam("remark") String remark) {
        try {
            waybillInfoService.updateRemark(waybillId, remark);
            return Result.success(200, "更新成功", "");
        } catch (Exception e) {
            return Result.failure(390, "更新失败", e.getMessage());
        }
    }

    /**
     * 导出报表
     *
     * @return
     */
    @RequestMapping(value = "/waybill/export", method = RequestMethod.GET)
    @ApiOperation(value = "导出物流跟踪单信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", defaultValue = "1", value = "页码", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "pageSize", defaultValue = "5", value = "每页条数", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "waybillOrderDateBegin", value = "下单日期(开始)", paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "waybillOrderDateEnd", value = "下单日期(结束)", paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "shipmentId", value = "物流商", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "customsCd", defaultValue = "0", required = false, value = "报关类型", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "warehouseId", value = "发货仓", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "employeeId", value = "负责人", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "logisticsTypeCd", defaultValue = "0", required = false, value = "状态", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "Keyword", value = "关键字", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "isLackLogistics", value = "少物流信息(0表示触发此条件)", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "isLackShipment", value = "少发货数(0表示触发此条件)", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "isLackCustomsDeclare", value = "少报关资料(0表示触发此条件)", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "isLackCustomsClearance", value = "少清关发票(0表示触发此条件)", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "waybillId", value = "物流交运单", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "batchNumber", value = "补货批次", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fbaReplenishmentId", value = "补货单ID", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "skuId", value = "产品ID", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "productName", value = "产品名称", paramType = "query", dataType = "String")
    })
    @ResponseBody
    public void export(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "pageNum", required = false) String pageNum,
                       @RequestParam(value = "pageSize", required = false) String pageSize,
                       @RequestParam(value = "waybillOrderDateBegin", required = false) Date waybillOrderDateBegin,
                       @RequestParam(value = "waybillOrderDateEnd", required = false) Date waybillOrderDateEnd,
                       @RequestParam(value = "shipmentId", required = false) String shipmentId,
                       @RequestParam(value = "customsCd", required = false) Integer customsCd,
                       @RequestParam(value = "warehouseId", required = false) String warehouseId,
                       @RequestParam(value = "employeeId", required = false) String employeeId,
                       @RequestParam(value = "logisticsTypeCd", required = false) Integer logisticsTypeCd,
                       @RequestParam(value = "Keyword", required = false) String Keyword,
                       @RequestParam(value = "isLackLogistics", required = false) String isLackLogistics,
                       @RequestParam(value = "isLackShipment", required = false) String isLackShipment,
                       @RequestParam(value = "isLackCustomsDeclare", required = false) String isLackCustomsDeclare,
                       @RequestParam(value = "isLackCustomsClearance", required = false) String isLackCustomsClearance,
                       @RequestParam(value = "waybillId", required = false) String waybillId,
                       @RequestParam(value = "batchNumber", required = false) String batchNumber,
                       @RequestParam(value = "fbaReplenishmentId", required = false) String fbaReplenishmentId,
                       @RequestParam(value = "skuId", required = false) String skuId,
                       @RequestParam(value = "productName", required = false) String productName) throws Exception {
        waybillInfoService.waybillExcel(response, request, null, null, waybillOrderDateBegin, waybillOrderDateEnd, shipmentId, customsCd, warehouseId, employeeId, logisticsTypeCd, Keyword, isLackLogistics, isLackShipment, isLackCustomsDeclare, isLackCustomsClearance, waybillId, batchNumber, fbaReplenishmentId, skuId, productName);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = GetWaybillExceptionDto.class)
    })
    @ApiOperation(value = "获取标记异常信息")
    @RequestMapping(value = "waybill/exception/{waybillId}", method = RequestMethod.GET)
    @ApiImplicitParams(value =
            {@ApiImplicitParam(paramType = "path", dataType = "String", name = "waybillId", value = "交运单编号", required = true)
            })
    public Result getException(@PathVariable("waybillId") String waybillId) {
        return waybillInfoService.getException(waybillId);
    }

    private String toNotNull(Object o) {
        if (o != null) {
            return o.toString();
        }
        return "";
    }

    private String addSymbol(String str) {
        if (str != "") {
            str += ",";
        }
        return str;
    }
}
