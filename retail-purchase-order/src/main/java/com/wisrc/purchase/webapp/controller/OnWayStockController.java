package com.wisrc.purchase.webapp.controller;

import com.wisrc.purchase.webapp.service.OnWayStockService;
import com.wisrc.purchase.webapp.utils.Result;
import com.wisrc.purchase.webapp.vo.stockVO.LocalOnWayProductVO;
import com.wisrc.purchase.webapp.vo.stockVO.LocalOnWayTransferVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "在途库存接口")
@RequestMapping(value = "/purchase")
public class OnWayStockController {
    @Autowired
    private OnWayStockService onWayStockService;

    @ApiOperation(value = "本地仓-在途-生产中")
    @RequestMapping(value = "/local/production", method = RequestMethod.GET)
    public Result localWarehouseOnWayProduct(@RequestParam("skuId") String skuId) {
        List<LocalOnWayProductVO> voList = onWayStockService.get(skuId);
        return Result.success(voList);
    }

    @ApiOperation(value = "本地仓-在途-运输中")
    @RequestMapping(value = "/local/transfer")
    public Result localWarehouseOnWayTransfer(@RequestParam("skuId") String skuId) {
        List<LocalOnWayTransferVO> voList = onWayStockService.getOnWayTransfer(skuId);
        return Result.success(voList);
    }
}
