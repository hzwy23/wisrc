package com.wisrc.rules.webapp.service;

import com.wisrc.rules.webapp.utils.Result;
import com.wisrc.rules.webapp.vo.logisticsRule.LogisticsRuleEditVo;
import com.wisrc.rules.webapp.vo.logisticsRule.LogisticsRulePageVo;
import com.wisrc.rules.webapp.vo.logisticsRule.LogisticsRuleSaveVo;
import com.wisrc.rules.webapp.vo.logisticsRule.LogisticsRuleSwitchVo;

public interface LogisticsRuleService {
    Result saveLogisticsRule(LogisticsRuleSaveVo logisticsRuleSaveVo, String userId);

    Result logisticsRulePage(LogisticsRulePageVo logisticsRulePageVo);

    Result countrySelector();

    Result classifySelector();

    Result warehouseSelector();

    Result characteristicSelector();

    Result getLogisticsRule(String ruleId);

    Result editLogisticsRule(String ruleId, LogisticsRuleEditVo logisticsRuleEditVo, String userId);

    Result logisticsRuleSwitch(LogisticsRuleSwitchVo logisticsRuleSwitchVo, String ruleId, String userId);

    Result logisticsRuleStatus();

    Result priorityNumberSelector();

    Result channelSelector();

    Result currencySelector();
}
