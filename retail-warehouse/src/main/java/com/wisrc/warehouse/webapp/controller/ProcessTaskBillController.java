package com.wisrc.warehouse.webapp.controller;

import com.wisrc.warehouse.webapp.service.ProcessTaskBillService;
import com.wisrc.warehouse.webapp.utils.Result;
import com.wisrc.warehouse.webapp.vo.AddProcessTaskBillVO;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@Api(tags = "加工任务单")
@RequestMapping(value = "/warehouse")
public class ProcessTaskBillController {
    @Autowired
    private ProcessTaskBillService processTaskBillService;

    @RequestMapping(value = "/process/task", method = RequestMethod.POST)
    @ApiOperation(value = "添加加工任务单", response = AddProcessTaskBillVO.class)
    public Result add(@RequestBody AddProcessTaskBillVO vo, @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {
        vo.getEntity().setCreateUser(userId);
        try {
            Result result = processTaskBillService.add(vo);
            return result;
        } catch (Exception e) {
            return Result.failure(390, "", e.getMessage());
        }
    }


    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功", response = AddProcessTaskBillVO.class),
            @ApiResponse(code = 401, message = "权限不足", response = Result.class),
            @ApiResponse(code = 404, message = "请求资源不存在", response = Result.class),
            @ApiResponse(code = 500, message = "访问程序错误", response = Result.class)
    })


    @RequestMapping(value = "process/bill", method = RequestMethod.GET)
    @ApiOperation(value = "模糊搜索查询加工任务单列表")
    public Result getAll(@RequestParam(value = "pageNum", required = false) String pageNum,
                         @RequestParam(value = "pageSize", required = false) String pageSize,
                         @RequestParam(value = "processDateStart", required = false) String processStartDate,
                         @RequestParam(value = "processDateEnd", required = false) String processEndDate,
                         @RequestParam(value = "processLaterSku", required = false) String processLaterSku,
                         @RequestParam(value = "statusCd", required = false, defaultValue = "0") Integer statusCd,
                         @RequestParam(value = "warehouseId", required = false) String warehouseId,
                         @RequestParam(value = "createUser", required = false) String createUser,
                         @RequestParam(value = "productName", required = false) String productName) {

        if (processStartDate != null || processEndDate != null || processLaterSku != null || statusCd != null || warehouseId != null || createUser != null || productName != null) {
            try {
                int num = 0;
                int size = 0;
                if (StringUtils.isNotEmpty(pageNum) && StringUtils.isNotEmpty(pageSize)) {
                    num = Integer.valueOf(pageNum);
                    size = Integer.valueOf(pageSize);
                }
                return Result.success(processTaskBillService.getProcessTaskBillAll(num, size, processStartDate, processEndDate, processLaterSku, statusCd, warehouseId, createUser, productName));
            } catch (Exception e) {
                return Result.success(processTaskBillService.search(processStartDate, processEndDate, processLaterSku, statusCd, warehouseId, createUser, productName));
            }
        } else if (pageNum == null || pageSize == null) {
            return Result.success(processTaskBillService.getProcessTaskBillAll());
        } else {
            try {
                int num = Integer.valueOf(pageNum);
                int size = Integer.valueOf(pageSize);
                return Result.success(processTaskBillService.getProcessTaskBillAll(num, size));
            } catch (Exception e) {
                return Result.success(200, "参数异常", processTaskBillService.getProcessTaskBillAll());
            }
        }
    }

    // @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "default") @ApiIgnore String userId

    @ApiImplicitParam(name = "processTaskId", value = "加工任务单号", paramType = "query", dataType = "String", required = true)
    @RequestMapping(value = "process/bill/detail", method = RequestMethod.GET)
    @ApiOperation(value = "精确查询一个加工任务单")
    public Result getById(@RequestParam(value = "processTaskId") String processTaskId) {
        try {
            return Result.success(processTaskBillService.getProcessTaskBillByProcessTaskId(processTaskId));
        } catch (Exception e) {
            return Result.failure();
        }
    }

    @RequestMapping(value = "process/status/list", method = RequestMethod.GET)
    @ApiOperation(value = "查询加工任务单状态码表list")
    public Result getStatusList() {
        try {
            return Result.success(processTaskBillService.getStatusList());
        } catch (Exception e) {
            return Result.failure();
        }
    }

    @RequestMapping(value = "process/status/{statusCd}", method = RequestMethod.GET)
    @ApiOperation(value = "查询加工任务单状态码表list")
    public Result getStatusDetail(@PathVariable("statusCd") String statusCd) {
        try {
            return Result.success(processTaskBillService.getStatusDetail(statusCd));
        } catch (Exception e) {
            return Result.failure();
        }
    }

    @RequestMapping(value = "process/status/canceling", method = RequestMethod.PUT)
    @ApiOperation(value = "取消加工")
    public Result cancelProcess(@RequestParam(value = "processTaskId", required = true) String processTaskId) {
        try {
            processTaskBillService.update(processTaskId);
            return Result.success("取消成功");
        } catch (Exception e) {
            return Result.failure();
        }
    }

    @RequestMapping(value = "process/status/batch/canceling", method = RequestMethod.PUT)
    @ApiOperation(value = "批量取消加工")
    public Result batchCancelProcess(@RequestParam(value = "processTaskId", required = true) String[] processTaskIds) {
        try {
            for (int i = 0; i < processTaskIds.length; i++) {
                processTaskBillService.update(processTaskIds[i]);
            }
            return Result.success("取消成功");
        } catch (Exception e) {
            return Result.failure();
        }
    }

    @RequestMapping(value = "/process/status/{processTaskId}/{status}", method = RequestMethod.PUT)
    @ApiOperation("修改产品加工单状态")
    public Result changeStatus(@PathVariable("processTaskId") String processTaskId, @PathVariable("status") String status) {
        try {
            Result result = processTaskBillService.changeStatus(processTaskId, status);
            return result;
        } catch (Exception e) {
            return Result.failure(390, e.getMessage(), "");
        }
    }

    @RequestMapping(value = "/process/status/fbaReplenishment", method = RequestMethod.GET)
    @ApiOperation("通过FBA补货单号得到组装加工产品的状态信息")
    public Result getStatusByfbaId(@RequestParam("fbaReplenishmentId") String fbaReplenishmentId) {
        try {
            return Result.success(200, "成功", processTaskBillService.getStatusCdByfbaId(fbaReplenishmentId));
        } catch (Exception e) {
            e.printStackTrace();
            ;
            return Result.failure(390, e.getMessage(), "");
        }
    }
}
