package com.wisrc.warehouse.webapp.controller;

import com.wisrc.warehouse.webapp.service.HandmadeEnterWarehouseBillService;
import com.wisrc.warehouse.webapp.utils.Result;
import com.wisrc.warehouse.webapp.vo.AddEnterWarehouseBillVO;
import com.wisrc.warehouse.webapp.vo.SelectEnterWarehouseBillVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.LinkedHashMap;

@RestController
@Api(tags = "手工入库单")
@RequestMapping(value = "/warehouse")
public class HandmadeEnterWarehouseBillController {
    @Autowired
    private HandmadeEnterWarehouseBillService handmadeEnterWarehouseBillService;

    @RequestMapping(value = "/enter/bill", method = RequestMethod.POST)
    @ApiOperation(value = "新增手工入库单", response = AddEnterWarehouseBillVO.class)
    @ResponseBody
    public Result add(@RequestBody AddEnterWarehouseBillVO vo, @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {
        try {
            Result result = handmadeEnterWarehouseBillService.add(vo, userId);
            if (result.getCode() != 200) {
                throw new RuntimeException(result.getMsg());
            }
            return Result.success(vo);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(390, "新增失败", e.getMessage());
        }

    }

    @RequestMapping(value = "/enter/bill", method = RequestMethod.GET)
    @ApiOperation(value = "查询手工入库单", response = SelectEnterWarehouseBillVO.class)
    @ResponseBody
    public Result select(@RequestParam(value = "pageNum", required = false) String pageNum,
                         @RequestParam(value = "pageSize", required = false) String pageSize,
                         @RequestParam(value = "warehouseId", required = false) String warehouseId,
                         @RequestParam(value = "enterTypeCd", required = false) Integer enterTypeCd,
                         @RequestParam(value = "enterStatus", required = false) Integer status,
                         @RequestParam(value = "startTime", required = false) String startTime,
                         @RequestParam(value = "keyword", required = false) String keyword,
                         @RequestParam(value = "endTime", required = false) String endTime) {
        try {
            LinkedHashMap map = new LinkedHashMap<>();
            if (pageNum != null && pageSize != null) {
                int num = Integer.parseInt(pageNum);
                int size = Integer.parseInt(pageSize);
                map = handmadeEnterWarehouseBillService.getList(num, size, warehouseId, enterTypeCd, status, startTime, endTime, keyword);
                return Result.success(map);
            } else {
                map = handmadeEnterWarehouseBillService.getList(0, 0, warehouseId, enterTypeCd, status, startTime, endTime, keyword);
                return Result.success(map);
            }
        } catch (Exception e) {
            return Result.failure(390, "查询失败", e.getMessage());
        }

    }

    @RequestMapping(value = "/enter/bill/{enterBillId}", method = RequestMethod.GET)
    @ApiOperation(value = "根据入库单ID查询入库单详情")
    @ResponseBody
    public Result getDetail(@PathVariable("enterBillId") String enterBillId) {
        try {
            return handmadeEnterWarehouseBillService.getDetail(enterBillId);

        } catch (Exception e) {
            return Result.failure(390, "查询失败", e.getMessage());
        }

    }

    @RequestMapping(value = "/enter/remark/{enterBillId}", method = RequestMethod.POST)
    @ApiOperation(value = "根据入库单ID追加备注")
    @ResponseBody
    public Result addRemark(@PathVariable("enterBillId") String enterBillId, @RequestParam("remark") String remark, @RequestHeader(value = "X-AUTH-ID", required = false) String userId) {
        try {
            handmadeEnterWarehouseBillService.addRemark(enterBillId, remark, userId);
            return Result.success();
        } catch (Exception e) {
            return Result.failure(390, "增加备注失败", e.getMessage());
        }

    }


    @RequestMapping(value = "/enter/cancel/{enterBillId}", method = RequestMethod.DELETE)
    @ApiOperation("手工入库单取消")
    public Result cancelHandmadeBill(@PathVariable("enterBillId") String enterBillId, @RequestParam("cancelReason") String cancelReason) {
        try {
            handmadeEnterWarehouseBillService.cancel(enterBillId, cancelReason);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(390, "取消失败", e.getMessage());
        }
    }

    @RequestMapping(value = "/enter/allStatus", method = RequestMethod.GET)
    @ApiOperation("查询所有的状态")
    public Result getAllHandmadeStatus() {
        try {
            return handmadeEnterWarehouseBillService.getAllStatus();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(390, "获取状态失败", e.getMessage());
        }
    }
}
