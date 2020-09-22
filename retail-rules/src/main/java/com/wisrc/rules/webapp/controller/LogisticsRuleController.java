package com.wisrc.rules.webapp.controller;

import com.wisrc.rules.webapp.dto.logisticsRule.GetLogisticsRuleDto;
import com.wisrc.rules.webapp.dto.logisticsRule.LogisticsRulePageDto;
import com.wisrc.rules.webapp.service.LogisticsRuleService;
import com.wisrc.rules.webapp.service.RulesMskuService;
import com.wisrc.rules.webapp.utils.Result;
import com.wisrc.rules.webapp.vo.logisticsRule.LogisticsRuleEditVo;
import com.wisrc.rules.webapp.vo.logisticsRule.LogisticsRulePageVo;
import com.wisrc.rules.webapp.vo.logisticsRule.LogisticsRuleSaveVo;
import com.wisrc.rules.webapp.vo.logisticsRule.LogisticsRuleSwitchVo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RequestMapping(value = "/rules/logistic")
@RestController
@Api(value = "/logistic", tags = "物流规则", description = "规则匹配")
public class LogisticsRuleController {
    @Autowired
    private LogisticsRuleService logisticsRuleService;
    @Autowired
    private RulesMskuService rulesMskuService;

    @RequestMapping(value = "/detail/save", method = RequestMethod.POST)
    @ApiOperation(value = "新增物流规则", notes = "")
    public Result saveLogisticsRule(@Valid @RequestBody LogisticsRuleSaveVo logisticsRuleSaveVo, BindingResult bindingResult, @RequestHeader("X-AUTH-ID") @ApiIgnore String userId) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return Result.failure(400, "", errorList.get(0).getDefaultMessage());
        }
        return logisticsRuleService.saveLogisticsRule(logisticsRuleSaveVo, userId);
    }

    @ApiResponses(value =
    @ApiResponse(code = 200, message = "OK", response = LogisticsRulePageDto.class)
    )
    @ApiOperation(value = "物流规则页面信息", notes = "")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Result logisticsRulePage(@Valid LogisticsRulePageVo logisticsRulePageVo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return Result.failure(400, "", errorList.get(0).getDefaultMessage());
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        if (logisticsRulePageVo.getModifyStartTime() != null && logisticsRulePageVo.getModifyEndTime() != null) {
            try {
                if (sdf.parse(logisticsRulePageVo.getModifyStartTime()).getTime() > sdf.parse(logisticsRulePageVo.getModifyEndTime()).getTime()) {
                    return new Result(400, "错误更新时间", null);
                }
            } catch (ParseException e) {
                e.printStackTrace();
                return new Result(400, "错误更新时间", null);
            }
        }
        return logisticsRuleService.logisticsRulePage(logisticsRulePageVo);
    }

    @ApiOperation(value = "国家选择项", notes = "")
    @RequestMapping(value = "/selector/country", method = RequestMethod.GET)
    public Result countrySelector() {
        return logisticsRuleService.countrySelector();
    }

    @ApiOperation(value = "产品分类选择项", notes = "")
    @RequestMapping(value = "/selector/classify", method = RequestMethod.GET)
    public Result classifySelector() {
        return logisticsRuleService.classifySelector();
    }

    @ApiOperation(value = "仓库选择项", notes = "")
    @RequestMapping(value = "/selector/warehouse", method = RequestMethod.GET)
    public Result warehouseSelector() {
        return logisticsRuleService.warehouseSelector();
    }

    @ApiOperation(value = "店铺选择项", notes = "")
    @RequestMapping(value = "/selector/shop", method = RequestMethod.GET)
    public Result shopSelector() {
        return rulesMskuService.getShopSelector();
    }

    @ApiOperation(value = "产品特性选择项", notes = "")
    @RequestMapping(value = "/selector/characteristic", method = RequestMethod.GET)
    public Result characteristicSelector() {
        return logisticsRuleService.characteristicSelector();
    }

    @ApiOperation(value = "物流规则状态选择项", notes = "")
    @RequestMapping(value = "/selector/status", method = RequestMethod.GET)
    public Result logisticsRuleStatus() {
        return logisticsRuleService.logisticsRuleStatus();
    }

    @ApiResponses(value =
    @ApiResponse(code = 200, message = "OK", response = GetLogisticsRuleDto.class)
    )
    @ApiOperation(value = "获取物流规则信息", notes = "")
    @ApiImplicitParams(value =
    @ApiImplicitParam(value = "物流规则ids", name = "ruleId", paramType = "path", dataType = "String", required = true)
    )
    @RequestMapping(value = "/detail/{ruleId}", method = RequestMethod.GET)
    public Result getLogisticsRule(@PathVariable("ruleId") String ruleId) {
        return logisticsRuleService.getLogisticsRule(ruleId);
    }

    @ApiOperation(value = "编辑物流规则信息", notes = "")
    @ApiImplicitParams(value =
    @ApiImplicitParam(value = "物流规则ids", name = "ruleId", paramType = "path", dataType = "String", required = true)
    )
    @RequestMapping(value = "/detail/{ruleId}", method = RequestMethod.PUT)
    public Result editLogisticsRule(@PathVariable("ruleId") String ruleId, @Valid @RequestBody LogisticsRuleEditVo logisticsRuleEditVo, BindingResult bindingResult, @RequestHeader("X-AUTH-ID") @ApiIgnore String userId) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return Result.failure(400, "", errorList.get(0).getDefaultMessage());
        }
        return logisticsRuleService.editLogisticsRule(ruleId, logisticsRuleEditVo, userId);
    }

    @ApiOperation(value = "物流规则启动/停用", notes = "")
    @RequestMapping(value = "/switch", method = RequestMethod.GET)
    public Result logisticsRuleSwitch(LogisticsRuleSwitchVo logisticsRuleSwitchVo, @RequestParam("ruleId") String ruleId, @RequestHeader("X-AUTH-ID") @ApiIgnore String userId) {
        return logisticsRuleService.logisticsRuleSwitch(logisticsRuleSwitchVo, ruleId, userId);
    }

    @ApiOperation(value = "优先级选择项", notes = "")
    @RequestMapping(value = "/selector/priority", method = RequestMethod.GET)
    public Result priorityNumberSelector() {
        return logisticsRuleService.priorityNumberSelector();
    }

    @ApiOperation(value = "发货物流渠道选择项", notes = "")
    @RequestMapping(value = "/selector/channel", method = RequestMethod.GET)
    public Result channelSelector() {
        return logisticsRuleService.channelSelector();
    }

    @ApiOperation(value = "币种选择项", notes = "")
    @RequestMapping(value = "/selector/currency", method = RequestMethod.GET)
    public Result currencySelector() {
        return logisticsRuleService.currencySelector();
    }
}
