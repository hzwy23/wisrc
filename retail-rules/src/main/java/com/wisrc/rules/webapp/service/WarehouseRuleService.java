package com.wisrc.rules.webapp.service;

import com.wisrc.rules.webapp.dto.warehouseRule.WarehouseRuleSwitchDto;
import com.wisrc.rules.webapp.utils.Result;
import com.wisrc.rules.webapp.vo.warehouseRule.WarehouseRuleEditVo;
import com.wisrc.rules.webapp.vo.warehouseRule.WarehouseRulePageVo;
import com.wisrc.rules.webapp.vo.warehouseRule.WarehouseRuleSaveVo;

public interface WarehouseRuleService {
    Result saveWarehouseRule(WarehouseRuleSaveVo warehouseRuleSaveVo, String userId);

    Result warehouseRulePage(WarehouseRulePageVo warehouseRulePageVo);

    Result editWarehouseRule(WarehouseRuleEditVo warehouseRuleEditVo, String userId, String ruleId);

    Result getWarehouseRule(String ruleId);

    Result warehouseRuleSwitch(WarehouseRuleSwitchDto warehouseRuleSwitchDto, String ruleId);
}
