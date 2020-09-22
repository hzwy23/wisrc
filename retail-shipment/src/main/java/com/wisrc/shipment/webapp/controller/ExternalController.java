package com.wisrc.shipment.webapp.controller;

import com.wisrc.shipment.webapp.service.LogisticsOfferBasisInfoService;
import com.wisrc.shipment.webapp.service.ReturnWarehouseApplyService;
import com.wisrc.shipment.webapp.entity.LogisticsOfferBasisInfoEntity;
import com.wisrc.shipment.webapp.entity.RemoveOrderEnity;
import com.wisrc.shipment.webapp.utils.Result;
import com.wisrc.shipment.webapp.vo.swagger.LogisticsOfferBasisInfoModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "外部接口调用")
@RequestMapping(value = "/shipment")
public class ExternalController {

    @Autowired
    private LogisticsOfferBasisInfoService logisticsOfferBasisInfoService;

    @Autowired
    private ReturnWarehouseApplyService returnWarehouseApplyService;

    @ApiOperation(value = "批量查询报价单对应的物流商id", notes = "根据报价单ID批量查询报价单部分信息。", response = LogisticsOfferBasisInfoModel.class)
    @RequestMapping(value = "logistics/shipMentId/batch", method = RequestMethod.GET)
    public Result getShipMentId(String[] offerIds) {
        try {
            List<Map<String, String>> offerList = logisticsOfferBasisInfoService.getShipmentIdByOfferIDs(offerIds);
            return Result.success(offerList);
        } catch (Exception e) {
            return Result.failure(390, "报价单批量查询异常", e);
        }
    }

    @ApiOperation(value = "获取状态为已通过的退货单的移除订单", notes = "获取状态为已通过的退货单的移除订单")
    @RequestMapping(value = "/external/removeOrder", method = RequestMethod.GET)
    public Result getRemoveOrder() {
        try {
            List<RemoveOrderEnity> removeOrderEnityList = returnWarehouseApplyService.getRemoveOrderEnity();
            return Result.success(removeOrderEnityList);
        } catch (Exception e) {
            return Result.failure(390, "获取状态为已通过的退货单的移除订单异常", e);
        }
    }

    @ApiOperation(value = "获取特定条件的物流商", notes = "获取特定条件的物流商")
    @RequestMapping(value = "/external/getShipmentByCond/{fbaReplenishmentId}", method = RequestMethod.GET)
    public Result getShipmentByCond(@PathVariable("fbaReplenishmentId") String fbaReplenishmentId) {
        try {
            List<RemoveOrderEnity> removeOrderEnityList = returnWarehouseApplyService.getRemoveOrderEnity();
            return Result.success(removeOrderEnityList);
        } catch (Exception e) {
            return Result.failure(390, "获取特定条件的物流商异常", e);
        }
    }


    @ApiOperation(value = "fna分页模糊查询正常物流报价表", notes = "根据分页和模糊调价查询物流报价内容，包括物流时效内容，计费区间内容，报价标签内容。", response = LogisticsOfferBasisInfoEntity.class)
    @RequestMapping(value = "/external/logistics/list", method = RequestMethod.GET)
    public Result findLogisticByFba(@RequestParam(value = "pageNum", required = true) String pageNum,
                                    @RequestParam(value = "pageSize", required = true) String pageSize,
                                    @RequestParam(value = "offerTypeCd", defaultValue = "0", required = false) int offerTypeCd,
                                    @RequestParam(value = "channleTypeCd", defaultValue = "0", required = false) int channleTypeCd,
                                    @RequestParam(value = "labelCd", required = false) int[] labelCd,
                                    @RequestParam(value = "keyword", required = false) String keyword,
                                    @RequestParam(value = "fbaReplenishmentId", required = true) String[] fbaReplenishmentId) {

        try {
            if ("".equals(keyword)) {
                keyword = null;
            }
            String labelcds = null;
            StringBuilder labelBuilder = new StringBuilder();
            if (labelCd != null && labelCd.length > 0) {
                for (int id : labelCd) {
                    labelBuilder.append(id + ",");
                }
                labelcds = labelBuilder.toString();
                if (labelcds.endsWith(",")) {
                    int index = labelcds.lastIndexOf(",");
                    labelcds = labelcds.substring(0, index);
                }
            }
            LinkedHashMap map = logisticsOfferBasisInfoService.findLogisticByFba(pageNum, pageSize, offerTypeCd, fbaReplenishmentId, channleTypeCd, labelcds, keyword);
            return Result.success(map);
        } catch (Exception e) {
            return Result.failure(390, "fna分页模糊查询正常物流报价表异常", e.getMessage());
        }

    }

}
