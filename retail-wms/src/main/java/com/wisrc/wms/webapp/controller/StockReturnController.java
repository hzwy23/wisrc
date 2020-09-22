package com.wisrc.wms.webapp.controller;

import com.wisrc.wms.webapp.service.StockReturnService;
import com.wisrc.wms.webapp.utils.Result;
import com.wisrc.wms.webapp.vo.ReturnVO.StockReturnVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "库存信息回写")
public class StockReturnController {

    @Autowired
    private StockReturnService stockReturnService;

    @RequestMapping(value = "/stock/return", method = RequestMethod.POST)
    @ApiOperation(value = "库存信息回写")
    public Result addStock(List<StockReturnVO> list) {
        for (StockReturnVO vo : list) {
            stockReturnService.insertStock(vo);
        }
        return Result.success();
    }

/*
    @RequestMapping(value = "/enterout/warehouse/water/return",method = RequestMethod.POST)
    @ApiOperation(value = "出入库流水回写")
    public Result addEnterOutWater(List<OutEnterWaterReturnVO> list){
        for(OutEnterWaterReturnVO vo : list){
            stockReturnService.insertWater(vo);
        }
        return Result.success();
    }
*/

}
