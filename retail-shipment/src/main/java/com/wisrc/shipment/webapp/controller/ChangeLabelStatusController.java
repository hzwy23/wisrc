package com.wisrc.shipment.webapp.controller;

import com.wisrc.shipment.webapp.service.ChangeLabelStatusService;
import com.wisrc.shipment.webapp.entity.ChangeLabelStatusEnity;
import com.wisrc.shipment.webapp.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(tags = "换标状态码表")
@RestController
@RequestMapping(value = "/shipment")
public class ChangeLabelStatusController {
    @Autowired
    private ChangeLabelStatusService changeLabelStatusService;

    @ApiOperation(value = "获取所有换标状态", notes = "获取所有换标状态")
    @RequestMapping(value = "/label/statusAll", method = RequestMethod.GET)
    public Result getLabelDetail(@RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "default") @ApiIgnore String userId) {
        try {
            List<ChangeLabelStatusEnity> changeLabelStatusEnityList = changeLabelStatusService.getAll();
            return Result.success(changeLabelStatusEnityList);
        } catch (Exception e) {
            return Result.failure(390, "获取所有换标状态失败", e.getMessage());
        }
    }
}
