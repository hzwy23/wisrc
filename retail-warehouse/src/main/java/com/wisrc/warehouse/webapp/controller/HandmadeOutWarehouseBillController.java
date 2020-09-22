package com.wisrc.warehouse.webapp.controller;

import com.wisrc.warehouse.webapp.service.HandmadeOutWarehouseBillService;
import com.wisrc.warehouse.webapp.utils.Result;
import com.wisrc.warehouse.webapp.vo.AddOutWarehouseBillVO;
import com.wisrc.warehouse.webapp.vo.OutWarehouseBillDetailVO;
import com.wisrc.warehouse.webapp.vo.SelectEnterWarehouseBillVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.LinkedHashMap;

@RestController
@Api(tags = "手工出库单")
@RequestMapping(value = "/warehouse")
public class HandmadeOutWarehouseBillController {
    @Autowired
    private HandmadeOutWarehouseBillService handmadeOutWarehouseBillService;

    @RequestMapping(value = "/out/bill", method = RequestMethod.POST)
    @ApiOperation(value = "新增手工出库单", response = AddOutWarehouseBillVO.class)
    @ResponseBody
    public Result add(@RequestBody AddOutWarehouseBillVO vo, @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {
        try {
            Result result = handmadeOutWarehouseBillService.add(vo, userId);
            if (result.getCode() != 200) {
                return result;
            }
            return Result.success(vo);
        } catch (Exception e) {
            return Result.failure(423, "新增手工出库单失败", e.getMessage());
        }
    }

    @RequestMapping(value = "/out/bill", method = RequestMethod.GET)
    @ApiOperation(value = "查询手工出库单", response = SelectEnterWarehouseBillVO.class)
    @ResponseBody
    public Result select(@RequestParam(value = "pageNum", required = false) String pageNum,
                         @RequestParam(value = "pageSize", required = false) String pageSize,
                         @RequestParam(value = "outBillId", required = false) String outBillId,
                         @RequestParam(value = "warehouseId", required = false) String warehouseId,
                         @RequestParam(value = "outTypeCd", required = false, defaultValue = "0") int outTypeCd,
                         @RequestParam(value = "skuId", required = false) String skuId,
                         @RequestParam(value = "skuName", required = false) String skuName,
                         @RequestParam(value = "startTime", required = false) String startTime,
                         @RequestParam(value = "endTime", required = false) String endTime) {
        try {
            LinkedHashMap map = new LinkedHashMap<>();
            if (pageNum != null && pageSize != null) {
                int num = Integer.parseInt(pageNum);
                int size = Integer.parseInt(pageSize);
                map = handmadeOutWarehouseBillService.getList(num, size, outBillId, warehouseId, outTypeCd, skuId, skuName, startTime, endTime);
                return Result.success(map);
            } else {
                map = handmadeOutWarehouseBillService.getList(outBillId, warehouseId, outTypeCd, skuId, skuName, startTime, endTime);
                return Result.success(map);
            }
        } catch (Exception e) {
            return Result.failure(390, e.getMessage(), "");
        }

    }

    @RequestMapping(value = "/out/bill/{outBillId}", method = RequestMethod.GET)
    @ApiOperation(value = "根据出库单ID查询出库单详情")
    @ResponseBody
    public Result getDetail(@PathVariable("outBillId") String outBillId) {
        try {
            OutWarehouseBillDetailVO vo = handmadeOutWarehouseBillService.getDetail(outBillId);
            return Result.success(vo);
        } catch (Exception e) {
            return Result.failure(390, e.getMessage(), "");
        }

    }

    @RequestMapping(value = "/out/remark/{outBillId}", method = RequestMethod.GET)
    @ApiOperation(value = "根据出库单ID查询出库单详情")
    @ResponseBody
    public Result addRemark(@PathVariable("outBillId") String outBillId, @RequestParam("remark") String remark) {
        try {
            handmadeOutWarehouseBillService.addRemark(outBillId, remark);
            return Result.success();
        } catch (Exception e) {
            return Result.failure(390, e.getMessage(), "");
        }

    }
}
