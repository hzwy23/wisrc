package com.wisrc.basic.controller;

import com.wisrc.basic.vo.swagger.CompanyBasicInfoAllModel;
import com.wisrc.basic.vo.swagger.CompanyBasicModel;
import com.wisrc.basic.vo.swagger.CompanyCustomsInfoModel;
import com.wisrc.basic.service.CompanyBasicInfoService;
import com.wisrc.basic.utils.Result;
import com.wisrc.basic.vo.CompanyBasicInfoAllVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/basic")
@Api(tags = "公司档案")
public class CompanyBasicInfoController {
    @Autowired
    CompanyBasicInfoService companyBasicInfoService;

    @RequestMapping(value = "/companyinfo/save", method = RequestMethod.POST)
    @ApiOperation(value = "公司档案保存功能", notes = "当公司档案有信息的时候属于修改保存，当第一次添加的时候属于新增", response = CompanyBasicInfoAllModel.class)
    @ResponseBody
    public Result saveShipment(@Valid CompanyBasicInfoAllVO vo, BindingResult result) {
        if (result.hasErrors()) {
            return Result.failure(390, "公司档案保存失败", result.getAllErrors().get(0).getDefaultMessage());
            //   return Result.failure(390,result.getAllErrors().get(0).toString(), result.getAllErrors());
        } else {
            companyBasicInfoService.saveInfo(vo);
            return Result.success("公司档案保存成功");
        }
    }

    @RequestMapping(value = "/companyinfo/basic", method = RequestMethod.GET)
    @ApiOperation(value = "公司基本信息", notes = "展示公司基本信息（之前以后信息才会展示，目前业务有且只有一条信息，不能通过条件查询，如：公司ID）", response = CompanyBasicModel.class)
    @ResponseBody
    public Result findBasic() {
        return Result.success(200, "成功", companyBasicInfoService.findBasicInfo());
    }

    @RequestMapping(value = "/companyinfo/customs", method = RequestMethod.GET)
    @ApiOperation(value = "公司物流报关信息", notes = "展示公司物流报关信息（之前以后信息才会展示，目前业务有且只有一条信息，不能通过条件查询，如：公司ID）", response = CompanyCustomsInfoModel.class)
    @ResponseBody
    public Result findCustoms() {
        return Result.success(200, "成功", companyBasicInfoService.findCustomsInfo());
    }

}
