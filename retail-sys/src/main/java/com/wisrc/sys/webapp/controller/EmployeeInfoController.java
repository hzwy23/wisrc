package com.wisrc.sys.webapp.controller;

import com.wisrc.sys.webapp.vo.employee.AddEmployeeVO;
import com.wisrc.sys.webapp.vo.employee.EmployeeParaVO;
import com.wisrc.sys.webapp.vo.employee.SetEmployeeVO;
import com.wisrc.sys.webapp.vo.swagger.VUserCategoryVO;
import com.wisrc.sys.webapp.entity.GatherEntity;
import com.wisrc.sys.webapp.entity.VUserCategoryEntity;
import com.wisrc.sys.webapp.service.EmployeeInfoService;
import com.wisrc.sys.webapp.utils.Result;
import com.wisrc.sys.webapp.utils.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api(tags = "员工信息")
@RequestMapping(value = "/sys")
public class EmployeeInfoController {

    private final Logger logger = LoggerFactory.getLogger(EmployeeInfoController.class);

    @Autowired
    private EmployeeInfoService employeeInfoService;

    @RequestMapping(value = "/employee", method = RequestMethod.POST)
    @ApiOperation(value = "新增员工")
    @ApiImplicitParam(value = "X-AUTH-ID", name = "X-AUTH-ID", paramType = "header", dataType = "String", required = true)
    public Result add(@RequestHeader("X-AUTH-ID") String userId, @Valid AddEmployeeVO addEmployee, BindingResult bindingResult) {
        return employeeInfoService.add(addEmployee, bindingResult);
    }

    @RequestMapping(value = "/employee", method = RequestMethod.PUT)
    @ApiOperation(value = "编辑员工")
    @ApiImplicitParam(value = "X-AUTH-ID", name = "X-AUTH-ID", paramType = "header", dataType = "String", required = true)
    public Result update(@RequestHeader("X-AUTH-ID") String userId, @Valid SetEmployeeVO addEmployee, BindingResult bindingResult) {
        return employeeInfoService.update(addEmployee, bindingResult);
    }

    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    @ApiOperation(value = "模糊查询员工")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "X-AUTH-ID", name = "X-AUTH-ID", paramType = "header", dataType = "String", required = true),
            @ApiImplicitParam(name = "deptCd", value = "部门编号", paramType = "query", dataType = "String", required = false),
            @ApiImplicitParam(name = "deptName", value = "部门名称", paramType = "query", dataType = "String", required = false),
            @ApiImplicitParam(name = "positionCd", value = "岗位编号", paramType = "query", dataType = "String", required = false),
            @ApiImplicitParam(name = "positionName", value = "岗位名称", paramType = "query", dataType = "String", required = false),
            @ApiImplicitParam(name = "employeeId", value = "员工编号", paramType = "query", dataType = "int", required = false),
            @ApiImplicitParam(name = "employeeName", value = "员工名称", paramType = "query", dataType = "int", required = false),
            @ApiImplicitParam(name = "statusCd", value = "员工状态", paramType = "query", dataType = "int", required = false),
            @ApiImplicitParam(name = "pageNum", value = "当前页", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "单页条数", paramType = "query", dataType = "int", required = false),
    })
    public Result update(@RequestHeader("X-AUTH-ID") String userId, Integer pageNum, Integer pageSize, @Valid EmployeeParaVO employeeParaVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new Result(9999, bindingResult.getFieldError().getDefaultMessage(), null);
        }
        GatherEntity gatherEntity = new GatherEntity();
        BeanUtils.copyProperties(employeeParaVO, gatherEntity);
        return employeeInfoService.find(pageNum, pageSize, gatherEntity);
    }

    @RequestMapping(value = "/employee/{employeeId}", method = RequestMethod.GET)
    @ApiOperation(value = "ID查询员工")
    @ApiImplicitParam(value = "X-AUTH-ID", name = "X-AUTH-ID", paramType = "header", dataType = "String", required = true)
    public Result getEmployee(@PathVariable("employeeId") String employeeId, @RequestHeader("X-AUTH-ID") String userId) {
        return employeeInfoService.getEmployeeDetail(employeeId);
    }

    @RequestMapping(value = "/employee/id/generator", method = RequestMethod.GET)
    @ApiOperation(value = "员工编号生成")
    @ApiImplicitParam(value = "X-AUTH-ID", name = "X-AUTH-ID", paramType = "header", dataType = "String", required = true)
    public Result getNewEmployeeId(@RequestHeader("X-AUTH-ID") String userId) {
        return employeeInfoService.getNewEmployeeId();
    }

    @ApiOperation(value = "改变员工状态", notes = "改变员工状态")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "X-AUTH-ID", name = "X-AUTH-ID", paramType = "header", dataType = "String", required = true),
            @ApiImplicitParam(name = "employeeId", value = "SKU库存单元", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "statusCd", value = "产品状态", required = true, dataType = "int", paramType = "path")
    })
    @RequestMapping(value = "/define/{employeeId}/{statusCd}", method = RequestMethod.PUT)
    public Result changeStatus(@PathVariable("employeeId") String employeeId, @PathVariable("statusCd") Integer statusCd, @RequestHeader("X-AUTH-ID") String userId) {
        try {
            employeeInfoService.changeStatus(employeeId, statusCd);
            return Result.success();
        } catch (Exception e) {
            logger.warn("改变产品状态失败", e);
            return Result.failure(ResultCode.FIND_FAILED);
        }
    }

    @ApiOperation(value = "获取所属部门的直系祖先ID集合", notes = "获取所属部门的直系祖先ID集合")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "X-AUTH-ID", name = "X-AUTH-ID", paramType = "header", dataType = "String", required = true),
            @ApiImplicitParam(name = "deptCd", value = "部门编码", required = true, dataType = "String", paramType = "path")
    })
    @RequestMapping(value = "/employeeId/department/ancestor/{deptCd}", method = RequestMethod.PUT)
    public Result getDeptAncestor(@PathVariable("deptCd") String deptCd, @RequestHeader("X-AUTH-ID") String userId) {
        return employeeInfoService.getDeptAncestor(deptCd);
    }

    @RequestMapping(value = "/employee/unAccount", method = RequestMethod.GET)
    @ApiOperation(value = "查询没有分配账号的员工")
    @ApiImplicitParam(value = "X-AUTH-ID", name = "X-AUTH-ID", paramType = "header", dataType = "String", required = true)
    public Result unAccount(@RequestHeader("X-AUTH-ID") String userId) {
        return employeeInfoService.getUnAccount();
    }

    @RequestMapping(value = "/employee/batch", method = RequestMethod.GET)
    @ApiOperation(value = "批量查询员工")
    public Result batchId(String[] batchEmployeeId) {
        if (batchEmployeeId == null || batchEmployeeId.length == 0) {
            return Result.failure(423, "参数解析失败，请再次阅读API接口文档", batchEmployeeId);
        }
        return Result.success(employeeInfoService.search(batchEmployeeId));
    }

    @RequestMapping(value = "/employee/selector/status", method = RequestMethod.GET)
    @ApiOperation(value = "人员状态选择框")
    public Result statusSelector() {
        return employeeInfoService.statusSelector();
    }


    @RequestMapping(value = "/employee/operation", method = RequestMethod.GET)
    public Result getOperationEmployee(@RequestParam(value = "statusCd", required = false, defaultValue = "1") Integer statusCd,
                                       @RequestParam(value = "deptCd", required = false) String deptCd,
                                       @RequestParam(value = "positionCd", required = false) String positionCd,
                                       @RequestParam(value = "employeeIdList", required = false) String[] employeeIdList) {
        return employeeInfoService.findAllOperationEmployee(statusCd, deptCd, positionCd, employeeIdList);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "账号ID", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "positionCd", value = "岗位ID", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "upEmployeeId", value = "上级领导员工ID", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "executiveDirectorId", value = "类目主管ID", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "upPositionCd", value = "上级领导岗位", required = false, dataType = "string", paramType = "query"),
    })
    @ApiOperation(value = "员工信息搜索", notes = "查询员工的岗位，上级领导，泪目主管信息", response = VUserCategoryVO.class)
    @RequestMapping(value = "/employee/structure", method = RequestMethod.GET)
    public Result searchCategoryEmployee(String userId, String[] employeeIdList,
                                         String positionCd, String upEmployeeId,
                                         String executiveDirectorId, String upPositionCd) {
        List<VUserCategoryEntity> list = employeeInfoService.searchCategory(userId, employeeIdList, positionCd, upEmployeeId, executiveDirectorId, upPositionCd);
        return Result.success(list);
    }
}
