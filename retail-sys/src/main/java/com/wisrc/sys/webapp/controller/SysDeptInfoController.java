package com.wisrc.sys.webapp.controller;


import com.wisrc.sys.webapp.vo.dept.DeptInfoSaveVo;
import com.wisrc.sys.webapp.vo.dept.DeptInfoVo;
import com.wisrc.sys.webapp.service.DeptInfoService;
import com.wisrc.sys.webapp.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/sys/auth")
@Api(tags = "部门管理")
public class SysDeptInfoController {

    @Autowired
    private DeptInfoService deptInfoService;

    @ApiOperation(value = "获取组织架构")
    @RequestMapping(value = "/organization", method = RequestMethod.GET)
    public Result getDeptMenu() {
        return deptInfoService.getDeptMenu();
    }

    @ApiOperation(value = "获取部门页面信息")
    @RequestMapping(value = "/dept", method = RequestMethod.GET)
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = true, dataType = "int", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "单页条数", required = true, dataType = "int", paramType = "query", defaultValue = "10")
    })
    public Result getDeptList(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        return deptInfoService.getDeptInfoList(pageNum, pageSize);
    }

    @ApiOperation(value = "保存部门")
    @RequestMapping(value = "/dept/save", method = RequestMethod.POST)
    public Result saveDeptInfo(@Valid @RequestBody DeptInfoSaveVo deptInfoVo, BindingResult bindingResult) {
        return deptInfoService.saveDeptInfo(deptInfoVo, bindingResult);
    }

    @ApiOperation(value = "获取单个部门")
    @RequestMapping(value = "/dept/{deptCd}", method = RequestMethod.GET)
    public Result getDeptInfo(@PathVariable("deptCd") String deptCd) {
        return deptInfoService.getDeptInfo(deptCd);
    }

    @ApiOperation(value = "编辑部门")
    @RequestMapping(value = "/dept/{deptCd}", method = RequestMethod.PUT)
    public Result editDeptInfo(@Valid @RequestBody DeptInfoVo deptInfoVo, @PathVariable("deptCd") String deptCd, BindingResult bindingResult) {
        return deptInfoService.editDeptInfo(deptInfoVo, deptCd, bindingResult);
    }

    @ApiOperation(value = "获取部门下拉列表")
    @RequestMapping(value = "/dept/select", method = RequestMethod.GET)
    @ApiImplicitParam(name = "deptNow", value = "当前所属部门", required = false, dataType = "String", paramType = "query")
    public Result deptSelect(String deptNow) {
        return deptInfoService.deptSelect(deptNow);
    }

    @Deprecated()
    @ApiOperation(value = "获取部门新Id")
    @RequestMapping(value = "/id/new", method = RequestMethod.GET)
    public Result getNewDeptId() {
//        return deptInfoService.getNewDeptId();
        return Result.failure(404, "接口错误，已经注销", null);
    }

    @ApiOperation(value = "删除部门")
    @RequestMapping(value = "/dept/{deptCd}", method = RequestMethod.DELETE)
    public Result deleteDeptInfo(@PathVariable("deptCd") String deptCd) {
        return deptInfoService.deleteDeptInfo(deptCd);
    }

    @ApiOperation(value = "获取运营部门", notes = "获取部门管理中，所有正常状态的运营部门")
    @RequestMapping(value = "/dept/operation", method = RequestMethod.GET)
    public Result getOperationDept() {
        return deptInfoService.getDeptOperation();
    }
}
