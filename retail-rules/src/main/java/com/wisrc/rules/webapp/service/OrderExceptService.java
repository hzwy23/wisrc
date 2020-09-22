package com.wisrc.rules.webapp.service;

import com.wisrc.rules.webapp.utils.Result;
import com.wisrc.rules.webapp.vo.orderExcept.OrderExceptEditVo;
import com.wisrc.rules.webapp.vo.orderExcept.OrderExceptSaveVo;

import java.util.Map;

public interface OrderExceptService {
    Result saveOrderExcept(OrderExceptSaveVo orderExceptSaveVo, String userId);

    Result editOrderExcept(OrderExceptEditVo orderExceptEditVo, String userId);

    Result orderExceptList();

    Result condColumnSelector();

    Map exceptTypeCheck() throws Exception;
}
