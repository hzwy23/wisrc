package com.wisrc.replenishment.webapp.controller;

import com.wisrc.replenishment.webapp.entity.FbaReplenishmentLabelAttrEntity;
import com.wisrc.replenishment.webapp.service.FbaReplenishmentLabelAttrService;
import com.wisrc.replenishment.webapp.utils.Result;
import com.wisrc.replenishment.webapp.vo.swagger.FbaReplenishmentLabelAttrModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "标签信息管理")
@RequestMapping(value = "/replenishment")
public class FbaReplenishmentLabelAttrController {

    @Autowired
    private FbaReplenishmentLabelAttrService labelAttrServie;

    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功，返回值数据结构，请参考Example Value。 ",
                    response = FbaReplenishmentLabelAttrModel.class),
            @ApiResponse(code = 401, message = "权限不足", response = Result.class),
            @ApiResponse(code = 404, message = "请求的资源不存在", response = Result.class)
    })
    @ApiOperation(value = "获取标签信息列表", notes = "获取所有标签信息")
    @RequestMapping(value = "/labelattr", method = RequestMethod.GET)
    public Result findAll() {
        List<FbaReplenishmentLabelAttrEntity> labelAttrList = labelAttrServie.findAll();
        return Result.success(labelAttrList);
    }

    @ApiOperation(value = "更新标签信息", notes = "传入标签实体，更新标签信息")
    @RequestMapping(value = "/labelattr", method = RequestMethod.PUT)
    public Result updateLabel(@RequestBody FbaReplenishmentLabelAttrEntity labelAttrEntity) {
        labelAttrServie.updateLabelAttr(labelAttrEntity);
        return Result.success("更新标签成功");
    }

    @ApiOperation(value = "根据标签代码查找标签信息", notes = "根据标签代码查找标签信息", response = FbaReplenishmentLabelAttrEntity.class)
    @RequestMapping(value = "/labelattr/{labelCd}", method = RequestMethod.GET)
    public Result findById(@PathVariable("labelCd") int labelCd) {
        return Result.success(labelAttrServie.findById(labelCd));
    }

    @ApiOperation(value = "根据标签代码删除标签", notes = "根据标签代码labelCd删除所选择的标签")
    @RequestMapping(value = "/labelattr/{labelCd}", method = RequestMethod.PUT)
    public Result deleteById(@PathVariable("labelCd") int labelCd) {
        labelAttrServie.deleteLabelAttr(labelCd);
        return Result.success("删除标签成功");
    }

    @ApiOperation(value = "新增标签信息", notes = "传入标签实体，保存标签信息")
    @RequestMapping(value = "/labelattr", method = RequestMethod.POST)
    public Result saveLabel(@RequestBody FbaReplenishmentLabelAttrEntity labelAttrEntity) {
        labelAttrServie.saveLabelAttr(labelAttrEntity);
        return Result.success("保存标签成功");
    }


}
