package com.wisrc.shipment.webapp.controller;

import com.wisrc.shipment.webapp.service.EarlyWarningLevelService;
import com.wisrc.shipment.webapp.utils.Result;
import com.wisrc.shipment.webapp.vo.earlyWarningLevelAttr.BatchEarlyWarningLevelAttrVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Api(tags = "预警等级设置")
@RequestMapping(value = "/shipment")
public class EarlyWarningLevelController {
    @Autowired
    private EarlyWarningLevelService earlyWarningLevelService;

    @RequestMapping(value = "/warninglevel", method = RequestMethod.PUT)
    @ApiOperation(value = "编辑预警等级")
    public Result update(@Valid @RequestBody BatchEarlyWarningLevelAttrVO vo, BindingResult bindingResult) {
        return earlyWarningLevelService.update(vo, bindingResult);
    }

    @RequestMapping(value = "/warninglevel", method = RequestMethod.GET)
    @ApiOperation(value = "查找所有预警等级")
    public Result findAll() {
        return earlyWarningLevelService.findAll();
    }

}
