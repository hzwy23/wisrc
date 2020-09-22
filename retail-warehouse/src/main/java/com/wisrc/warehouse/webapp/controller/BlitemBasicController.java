package com.wisrc.warehouse.webapp.controller;

import com.wisrc.warehouse.webapp.entity.BlitemInfoEntity;
import com.wisrc.warehouse.webapp.service.BlitemService;
import com.wisrc.warehouse.webapp.utils.Result;
import com.wisrc.warehouse.webapp.vo.BlitemInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "盘点单基本信息")
@RequestMapping(value = "/warehouse")
public class BlitemBasicController {

    @Autowired
    private BlitemService blitemService;

    @RequestMapping(value = "/blitem/list", method = RequestMethod.GET)
    @ApiOperation(value = "模糊查询盘点单信息", response = BlitemInfoVO.class)
    public Result list(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                       @RequestParam(value = "pageSize", required = false) Integer pageSize,
                       @RequestParam(value = "blitemId", required = false) String blitemId,
                       @RequestParam(value = "warehouseId", required = false) String warehouseId,
                       @RequestParam(value = "skuId", required = false) String skuId,
                       @RequestParam(value = "skuName", required = false) String skuName,
                       @RequestParam(value = "startDate", required = false) String startDate,
                       @RequestParam(value = "endDate", required = false) String endDate) {
        //查询盘点单基本信息
        try {
            Result result = blitemService.getList(pageNum, pageSize, blitemId, warehouseId, skuId, skuName, startDate, endDate);
            return result;
        } catch (Exception e) {
            return Result.failure(390, e.getMessage(), null);
        }
    }

    @RequestMapping(value = "/blitem/add", method = RequestMethod.POST)
    @ApiOperation("新建盘点单")
    public Result save(@RequestBody BlitemInfoVO blitemInfoVO,
                       @RequestHeader("X-AUTH-ID") String userId) {
        Result result = null;
        BlitemInfoEntity blitemInfoEntity = new BlitemInfoEntity();
        BeanUtils.copyProperties(blitemInfoVO, blitemInfoEntity);
        try {
            result = blitemService.save(blitemInfoEntity, blitemInfoVO.getBlitemListInfos(), blitemInfoVO.getRemark(), userId);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(390, e.getMessage(), "");
        }
    }

    @RequestMapping(value = "/blitem/get/{blitemId}", method = RequestMethod.GET)
    @ApiOperation(value = "根据盘点单id查询盘点单的详细信息", response = BlitemInfoVO.class)
    public Result getByBlitemId(@PathVariable("blitemId") String blitemId) {
        try {
            Result result = blitemService.getBlitemInfoVoByBlitemId(blitemId);
            return result;
        } catch (Exception e) {
            return Result.failure(390, e.getMessage(), null);
        }
    }

    @RequestMapping(value = "/blitem/{blitemId}/{statusCd}", method = RequestMethod.PUT)
    @ApiOperation("修改盘点单状态")
    public Result updateStatus(@PathVariable("blitemId") String blitemId,
                               @PathVariable("statusCd") String statusCd,
                               @RequestParam("remark") String remark,
                               @RequestHeader(value = "X-AUTH-ID", required = true) String userId) {

        try {
            Result result = blitemService.updateBlitemStatus(blitemId, statusCd, remark, userId);
            return result;
        } catch (Exception e) {
            return Result.failure(390, e.getMessage(), null);
        }
    }

}
