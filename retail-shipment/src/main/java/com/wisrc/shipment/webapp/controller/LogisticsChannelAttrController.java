package com.wisrc.shipment.webapp.controller;

import com.wisrc.shipment.webapp.service.LogisticsChannelAttrService;
import com.wisrc.shipment.webapp.entity.LogisticsChannelAttrEntity;
import com.wisrc.shipment.webapp.utils.Result;
import com.wisrc.shipment.webapp.vo.swagger.LogisticsChannelAttrModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "物流报价渠道类型码表")
@RequestMapping(value = "/shipment")
public class LogisticsChannelAttrController {
    @Autowired
    private LogisticsChannelAttrService logisticsChannelAttrService;

    @RequestMapping(value = "channel/list", method = RequestMethod.GET)
    @ApiOperation(value = "查询物流报价渠道类型", notes = "查询物流报价渠道类型<br/>" +
            "0表示物流报价渠道类型都查询<br/>" +
            "1表示空运<br/>" +
            "2表示海运", response = LogisticsChannelAttrModel.class)
    public Result findList() {
        List<LogisticsChannelAttrEntity> list = logisticsChannelAttrService.findList();
        return Result.success(list);
    }
}
