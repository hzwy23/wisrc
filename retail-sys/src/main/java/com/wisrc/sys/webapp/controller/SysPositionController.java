package com.wisrc.sys.webapp.controller;

import com.wisrc.sys.webapp.vo.position.PositionInfoSaveVo;
import com.wisrc.sys.webapp.vo.position.PositionInfoVo;
import com.wisrc.sys.webapp.vo.position.PositionPageSelectorVo;
import com.wisrc.sys.webapp.vo.position.PositionPageVo;
import com.wisrc.sys.webapp.service.SysPositionService;
import com.wisrc.sys.webapp.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/sys/auth/position")
@Api(tags = "岗位管理")
public class SysPositionController {
    @Autowired
    private SysPositionService sysPositionService;

    @ApiOperation(value = "获取岗位页面信息")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Result getPositionPage(@Valid PositionPageVo positionPageVo, BindingResult bindingResult) {
        return sysPositionService.getPositionPage(positionPageVo, bindingResult);
    }

    @ApiOperation(value = "保存岗位")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result savePositionInfo(@Valid @RequestBody PositionInfoSaveVo positionInfoVo,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return Result.failure(400, errorList.get(0).getDefaultMessage(), positionInfoVo);
        }

        return sysPositionService.savePositionInfo(positionInfoVo);
    }

    @ApiOperation(value = "编辑岗位")
    @RequestMapping(value = "/{number}", method = RequestMethod.PUT)
    @ApiImplicitParam(value = "岗位编号", name = "number", paramType = "path", dataType = "String", required = true)
    public Result editPositionInfo(@Valid @RequestBody PositionInfoVo positionInfoVo,
                                   @PathVariable("number") String number, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return Result.failure(400, errorList.get(0).getDefaultMessage(), positionInfoVo);
        }
        return sysPositionService.editPositionInfo(positionInfoVo, number);
    }

    @ApiOperation(value = "岗位选择框")
    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public Result positionSelect(@Valid PositionPageSelectorVo positionPageSelectorVo, BindingResult bindingResult) {
        return sysPositionService.positionSelect(positionPageSelectorVo, bindingResult);
    }

    @Deprecated()
    @ApiOperation(value = "获取岗位新Id")
    @RequestMapping(value = "/id/new", method = RequestMethod.GET)
    public Result getNewDeptId() {
        return Result.failure(404, "接口错误，已经注销", null);
//        return sysPositionService.getNewPositionId();
    }

    @ApiOperation(value = "删除岗位")
    @RequestMapping(value = "/{number}", method = RequestMethod.DELETE)
    @ApiImplicitParam(value = "岗位编号", name = "number", paramType = "path", dataType = "String", required = true)
    public Result deletePositionInfo(@PathVariable("number") String number) {
        return sysPositionService.deletePositionInfo(number);
    }
}
