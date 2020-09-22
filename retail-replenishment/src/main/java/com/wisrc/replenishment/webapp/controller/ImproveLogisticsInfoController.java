package com.wisrc.replenishment.webapp.controller;

import com.wisrc.replenishment.webapp.entity.ImproveLogisticsInfoEntity;
import com.wisrc.replenishment.webapp.service.ImproveLogisticsInfoService;
import com.wisrc.replenishment.webapp.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "完善物流信息")
@RequestMapping(value = "/replenishment")
public class ImproveLogisticsInfoController {
    @Autowired
    private ImproveLogisticsInfoService improveLogisticsInfoService;


    @ApiOperation(value = "物流信息资料上传", response = ImproveLogisticsInfoEntity.class)
    @RequestMapping(value = "/upload/logistics", method = RequestMethod.POST)
    public Result uploadLogistics(@RequestBody ImproveLogisticsInfoEntity ele) throws Exception {
        try {
            improveLogisticsInfoService.ImproveLogisticsInfo(ele);
            improveLogisticsInfoService.updateLogistics(ele.getWaybillId());
            return Result.success(ele);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(500, e.getMessage(), ele);
        }
    }

}
