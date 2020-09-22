package com.wisrc.replenishment.webapp.controller;

import com.wisrc.replenishment.webapp.entity.LogisticOfferEnity;
import com.wisrc.replenishment.webapp.entity.ShipmentEnity;
import com.wisrc.replenishment.webapp.entity.ShipmentInfoEnity;
import com.wisrc.replenishment.webapp.service.ExternalService;
import com.wisrc.replenishment.webapp.service.FbaReplenishmentInfoService;
import com.wisrc.replenishment.webapp.utils.Result;
import com.wisrc.replenishment.webapp.utils.ResultCode;
import com.wisrc.replenishment.webapp.vo.FbaMskuVo;
import com.wisrc.replenishment.webapp.vo.delivery.DeliverySelectVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "外部接口")
@RequestMapping(value = "/replenishment")
public class ReplenishmentExternalController {
    @Autowired
    private ExternalService externalService;

    @Autowired
    private FbaReplenishmentInfoService fbaReplenishmentInfoService;

    @ApiOperation(value = "获取所有已交运但未完结的补货单的物流单号和报价渠道", notes = "获取所有已交运但未完结的补货单的物流单号和物流商类型", response = DeliverySelectVO.class)
    @RequestMapping(value = "/external/logisticOffer", method = RequestMethod.GET)
    public Result selectDelivery() {
        try {
            List<LogisticOfferEnity> logisticOfferEnityList = externalService.getAllLogisticOffer();
            return Result.success(logisticOfferEnityList);
        } catch (Exception e) {
            return Result.failure(390, "获取所有已交运但未完结的补货单的物流单号和报价渠道失败", null);
        }

    }

    @ApiOperation(value = "更新补货单物流信息", notes = "更新补货单物流信息", response = DeliverySelectVO.class)
    @RequestMapping(value = "/external/updateWaybill", method = RequestMethod.POST)
    public Result updateWaybill(@RequestBody List<Map> tracingRecordMapList) {
        try {
            externalService.updateWaybill(tracingRecordMapList);
            return Result.success();
        } catch (Exception e) {
            return Result.failure(390, "更新补货单物流信息失败", e.getMessage());
        }

    }

    @ApiOperation(value = "获取状态不是已取消和已签收状态的货件编码", notes = "更新补货单物流信息", response = DeliverySelectVO.class)
    @RequestMapping(value = "/external/getShipmentEnity", method = RequestMethod.GET)
    public Result getShipmentEnity() {
        try {
            List<ShipmentEnity> shipmentEnityList = externalService.getShipmentEnity();
            return Result.success(shipmentEnityList);
        } catch (Exception e) {
            return Result.failure(390, "获取状态不是已取消和已签收状态的货件编码失败", null);
        }

    }

    @ApiOperation(value = "批量更新签收数量", notes = "批量更新签收数量", response = DeliverySelectVO.class)
    @RequestMapping(value = "/external/batchUpdateSignNum", method = RequestMethod.POST)
    public Result batchUpdateSignNum(@RequestBody List<ShipmentInfoEnity> shipmentInfoEnityList) {
        try {
            externalService.batchUpdateSignNum(shipmentInfoEnityList);
            return Result.success();
        } catch (Exception e) {
            return Result.failure(390, "批量更新签收数量失败", e.getMessage());
        }

    }

    @ApiOperation(value = "根据运单号获取物流记录", notes = "根据运单号获取物流记录", response = DeliverySelectVO.class)
    @RequestMapping(value = "/external/getWaybillTransferInfo/{waybillId}", method = RequestMethod.GET)
    public Result getWaybillTransferInfo(@PathVariable(value = "waybillId") String waybillId) {
        try {
            return externalService.getWaybillTransferInfo(waybillId);
        } catch (Exception e) {
            return Result.failure(390, "根据运单号获取物流记录失败", null);
        }

    }

    @ApiOperation(value = "更新运单状态", notes = "更新运单状态", response = DeliverySelectVO.class)
    @RequestMapping(value = "/external/updateStatus", method = RequestMethod.PUT)
    public Result updateStatus(@RequestBody String[] waybillIds) {
        try {
            return externalService.updateStatus(waybillIds);
        } catch (Exception e) {
            return Result.failure(390, "更新运单状态", e.getMessage());
        }
    }

    @ApiOperation(value = "获取所有未签收的自提货件编码和运单", notes = "获取所有已交运但未完结的补货单的物流单号和物流商类型", response = DeliverySelectVO.class)
    @RequestMapping(value = "/external/shipmentInfo", method = RequestMethod.GET)
    public Result getShipment() {
        try {
            List<Map> mapList = externalService.getShipment();
            return Result.success(mapList);
        } catch (Exception e) {
            return Result.failure(390, "获取所有已交运但未完结的补货单的物流单号和报价渠道失败", e.getMessage());
        }

    }

    @ApiOperation(value = "通过补货单id获取补货单详情信息", notes = "获取补货单详细信息，可能没有Amazon相关信息，装箱规格中的装箱尺寸需要前端处理，用外箱长、宽、高拼接成需要的数据")
    @RequestMapping(value = "/fba/fbaInfoBatch", method = RequestMethod.GET)
    public Result findbyidBatch(@RequestParam String[] fbaReplenishmentIds) {
        try {
            List<FbaMskuVo> fbaMskuVoList = fbaReplenishmentInfoService.findbyidBatch(fbaReplenishmentIds);
            return Result.success(fbaMskuVoList);
        } catch (Exception e) {
            return Result.failure(ResultCode.INVALID_ARGUMENT, "通过补货单id获取补货单详情信息错误");
        }
    }

}
