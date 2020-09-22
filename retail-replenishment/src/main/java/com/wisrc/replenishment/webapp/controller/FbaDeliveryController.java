package com.wisrc.replenishment.webapp.controller;

import com.wisrc.replenishment.webapp.service.FbaReplenishmentInfoService;
import com.wisrc.replenishment.webapp.utils.Result;
import com.wisrc.replenishment.webapp.vo.delivery.CalcuDeliveryVO;
import com.wisrc.replenishment.webapp.vo.delivery.DeliveryConSelectVO;
import com.wisrc.replenishment.webapp.vo.delivery.DeliveryConfirmVO;
import com.wisrc.replenishment.webapp.vo.delivery.DeliveryVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * 物流交运controller
 */
@RestController
@Api(tags = "交运信息")
@RequestMapping(value = "/replenishment")
public class FbaDeliveryController {

    @Autowired
    private FbaReplenishmentInfoService fbaReplenishmentInfoService;

    @ApiOperation(value = "计费估算表01", notes = "根据补货单id集合算出计费估算表个", response = DeliveryVO.class)
    @RequestMapping(value = "/delivery/bill", method = RequestMethod.GET)
    public Result BillEstimate(@RequestParam("fbaIds") String[] fbaIds,
                               @RequestParam("channelType") String channelType) {
        try {
            DeliveryVO deliveryVO = fbaReplenishmentInfoService.getfbaAndBillInfo(fbaIds, channelType);
            return Result.success(deliveryVO);
        } catch (Exception e) {
            return Result.failure(390, "查询计费估算表信息失败", e.getMessage());
        }
    }

    @ApiOperation(value = "计算物流商金额信息", notes = "计算出物流商金额、计费区间等信息", response = DeliveryConSelectVO.class)
    @RequestMapping(value = "/delivery/calcul", method = RequestMethod.GET)
    public Result getTotalAmount(@RequestParam(value = "fbaIds") String[] fbaIds,
                                 @RequestParam("channelType") String channelType,
                                 @RequestParam(value = "offerId") String offerId) {

        // 计算物流商金额信息
        try {
            CalcuDeliveryVO calcuDeliveryVO = fbaReplenishmentInfoService.calcuAmount(offerId, fbaIds, channelType);
            HashMap calculMap = new HashMap();
            calculMap.put("offerId", calcuDeliveryVO.getOfferId());
            calculMap.put("unitPriceWithOil", calcuDeliveryVO.getUnitPriceWithOil());
            calculMap.put("chargeIntever", calcuDeliveryVO.getChargeIntever());
            calculMap.put("totalAmount", calcuDeliveryVO.getTotalAmount());
            return Result.success(calculMap);
        } catch (Exception e) {
            return Result.failure(390, "计算物流商金额信息错误", e.getMessage());
        }

    }

    @ApiOperation(value = "选择物流商接口", notes = "筛选出物流商列表，选择一个物流商", response = DeliveryConSelectVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(value = "渠道类型", name = "channelType", dataType = "string", paramType = "query"),
            @ApiImplicitParam(value = "报价ID", name = "offerId", dataType = "string", paramType = "query")
    })
    @RequestMapping(value = "/delivery/freight", method = RequestMethod.GET)
    public Result getFreight(@RequestParam(value = "fbaIds") String[] fbaIds,
                             @RequestParam("channelType") String channelType,
                             @RequestParam(value = "offerId") String offerId) {

        try {
            DeliveryConSelectVO freightInfo = fbaReplenishmentInfoService.getFreightInfo(offerId, fbaIds, channelType);
            return Result.success(freightInfo);
        } catch (Exception e) {
            return Result.failure(390, "选择出现错误", e.getMessage());
        }
    }

    @ApiOperation(value = "继续交运接口", notes = "点击继续交运查询计费估算表查出计费估算信息", response = DeliveryConfirmVO.class)
    @RequestMapping(value = "/delivery/continue", method = RequestMethod.GET)
    public Result continueDel(@RequestParam(value = "fbaReplenishmentId") String fbaReplenishmentId) {
        try {
            /*DeliveryConfirmVO deliveryConfirmVO = fbaReplenishmentInfoService.comfireDelivery(fbaReplenishmentId);*/
            DeliveryConSelectVO freightInfo = fbaReplenishmentInfoService.comfireDelivery(fbaReplenishmentId);
            return Result.success(freightInfo);
        } catch (Exception e) {
            return Result.failure(390, "查询计费估算信息失败", e.getMessage());
        }
    }
}
