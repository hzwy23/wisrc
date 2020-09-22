package com.wisrc.warehouse.webapp.controller;

import com.wisrc.warehouse.webapp.service.BlitemRemarkService;
import com.wisrc.warehouse.webapp.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "盘点单备注")
@RequestMapping(value = "/warehouse")
public class BlitemRemarkController {

    @Autowired
    private BlitemRemarkService remarkService;

    @RequestMapping(value = "/blitem/remark/add", method = RequestMethod.POST)
    @ApiOperation("添加盘点单备注")
    public Result post(@RequestParam("blitemId") String blitemId,
                       @RequestParam("remark") String remark,
                       @RequestHeader("X-AUTH-ID") String userId) {
        try {
            remarkService.save(blitemId, remark, userId);
            return Result.success("添加成功");
        } catch (Exception e) {
            return Result.failure(390, e.getMessage(), "");
        }
    }


}
