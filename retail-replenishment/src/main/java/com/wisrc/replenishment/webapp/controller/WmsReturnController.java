package com.wisrc.replenishment.webapp.controller;

import com.wisrc.replenishment.webapp.dao.FbaReplenishmentInfoDao;
import com.wisrc.replenishment.webapp.dao.WaybillInfoDao;
import com.wisrc.replenishment.webapp.service.FbaReplenishmentInfoService;
import com.wisrc.replenishment.webapp.service.WmsReturnService;
import com.wisrc.replenishment.webapp.utils.Result;
import com.wisrc.replenishment.webapp.vo.wms.FbaPackingDataReturnVO;
import com.wisrc.replenishment.webapp.vo.wms.FbaReplenishmentOutReturnVO;
import com.wisrc.replenishment.webapp.vo.wms.TransferOrderPackBasicVO;
import com.wisrc.replenishment.webapp.vo.wms.TransferOutBasicVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Api(tags = "erp接收并处理wms回写FBA补货单数据和调拨单回写的数据")
@RequestMapping(value = "/replenishment")
public class WmsReturnController {
    @Autowired
    private FbaReplenishmentInfoService fbaReplenishmentInfoService;
    @Autowired
    private FbaReplenishmentInfoDao fbaReplenishmentInfoDao;
    @Autowired
    private WaybillInfoDao waybillInfoDao;
    @Autowired
    private WmsReturnService wmsReturnService;

    @RequestMapping(value = "/change/pack", method = RequestMethod.PUT)
    public Result returnPackData(@RequestBody FbaPackingDataReturnVO vo) {
        fbaReplenishmentInfoService.changeReturn(vo);
        return Result.success();
    }

    @RequestMapping(value = "/wms/fbaDeliver", method = RequestMethod.POST)
    @ApiOperation("处理wms回写fba发货数据")
    public Result wmsFbaDeliver(@RequestBody FbaReplenishmentOutReturnVO replenishmentOutReturnVO) {
        try {
            fbaReplenishmentInfoService.wmsReturnFbaDelivery(replenishmentOutReturnVO);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(390, e.getMessage(), "");
        }
    }

    @RequestMapping(value = "/wms/query", method = RequestMethod.GET)
    @ApiOperation("wms通过fbaId查询出运单id、物流单地址，装箱清单地址")
    public Result wmsQueryController(@RequestParam("fbaReplenishmentId") String fbaReplenishmentId) {
        try {
            String waybillId = waybillInfoDao.findWaybillIdByFbaId(fbaReplenishmentId);
            Map<String, String> res = new HashMap<>();
            res.put("waybillId", waybillId);
            String packListUrl = fbaReplenishmentInfoDao.findWmsInfoById(fbaReplenishmentId);
            String logisticsUrl = waybillInfoDao.findLogisticsUrlById(waybillId);
            res.put("packListUrl", packListUrl);
            res.put("logisticsUrl", logisticsUrl);
            return Result.success(res);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(390, e.getMessage(), "");
        }
    }

    @ApiOperation(value = "调拨单装箱数据回写")
    @RequestMapping(value = "/wms/transfer/packInfo/return", method = RequestMethod.POST)
    public Result wmsTransferPackInfoReturn(@RequestBody TransferOrderPackBasicVO TransferOrderPackBasicVO) {
        try {
            wmsReturnService.transferPackInfoReturn(TransferOrderPackBasicVO);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(390, "调拨单装箱数据回写失败", e.getMessage());
        }
    }

    @ApiOperation(value = "调拨单出库回写")
    @RequestMapping(value = "/wms/transfer/out/return", method = RequestMethod.POST)
    public Result wmsTransferOutReturn(@RequestBody TransferOutBasicVO transferOutBasicVO) {
        try {
            wmsReturnService.transferOutReturn(transferOutBasicVO);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.success(390, "调拨单出库回写失败", e.getMessage());
        }
    }
}
