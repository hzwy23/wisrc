package com.wisrc.basic.controller;

import com.wisrc.basic.entity.VersionAnnouncementEntity;
import com.wisrc.basic.service.VersionAnnouncementService;
import com.wisrc.basic.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "版本更新公告")
@RequestMapping(value = "/basic")
public class VersionAnnouncementController {

    @Autowired
    private VersionAnnouncementService versionAnnouncementService;

    @ApiOperation(value = "新增版本公告")
    @RequestMapping(value = "/version/announcement/add", method = RequestMethod.POST)
    public Result add(@RequestBody VersionAnnouncementEntity entity) {
        try {
            return versionAnnouncementService.add(entity);
        } catch (Exception e) {
            return Result.failure(390, "新增失败", e.getMessage());
        }
    }

    @ApiOperation(value = "版本公告列表")
    @RequestMapping(value = "/version/announcement", method = RequestMethod.GET)
    public Result findAll() {
        try {
            return Result.success(200, "成功", versionAnnouncementService.findAll());
        } catch (Exception e) {
            return Result.failure(390, "失败", e.getMessage());
        }
    }

    @ApiOperation(value = "版本模块码表列表")
    @RequestMapping(value = "/version/module/attr", method = RequestMethod.GET)
    public Result findModuleAttr() {
        try {
            return Result.success(200, "成功", versionAnnouncementService.findModuleAttrAll());
        } catch (Exception e) {
            return Result.failure(390, "失败", e.getMessage());
        }
    }

    @ApiOperation(value = "删除版本公告")
    @ApiImplicitParam(name = "versionNumber", value = "版本号", required = true, dataType = "String", paramType = "query")
    @RequestMapping(value = "/version/announcement/delete", method = RequestMethod.DELETE)
    public Result deleteVersion(@Param("versionNumber") String versionNumber) {
        try {
            versionAnnouncementService.deleteVersion(versionNumber);
            return Result.success(200, "删除成功", "");
        } catch (Exception e) {
            return Result.failure(390, "删除失败", e.getMessage());
        }
    }

    @ApiOperation(value = "得到当前的版本号和上次的版本号")
    @RequestMapping(value = "/version/announcement/version/number", method = RequestMethod.GET)
    public Result getVersionNumber() {
        try {
            return Result.success(200, "success", versionAnnouncementService.getVersionNumber12());
        } catch (Exception e) {
            return Result.failure(390, "failure", e.getMessage());
        }
    }
}
