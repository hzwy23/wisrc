package com.wisrc.rules.webapp.service.impl;

import com.wisrc.rules.webapp.dto.orderExcept.OrderExceptDto;
import com.wisrc.rules.webapp.dto.orderExcept.OrderExceptListDto;
import com.wisrc.rules.webapp.service.OrderExceptService;
import com.wisrc.rules.webapp.vo.orderExcept.OrderExceptEditVo;
import com.wisrc.rules.webapp.vo.orderExcept.OrderExceptSaveVo;
import com.wisrc.rules.webapp.vo.orderExcept.OrderExceptVo;
import com.wisrc.rules.webapp.dao.CondColumnAttrDao;
import com.wisrc.rules.webapp.dao.ExceptTypeAttrDao;
import com.wisrc.rules.webapp.dao.OrderExceptDefineDao;
import com.wisrc.rules.webapp.entity.ExceptTypeAttrEntity;
import com.wisrc.rules.webapp.entity.OrderExceptDefineEntity;
import com.wisrc.rules.webapp.utils.Result;
import com.wisrc.rules.webapp.utils.Toolbox;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderExceptServiceImpl implements OrderExceptService {
    @Autowired
    private OrderExceptDefineDao orderExceptDefineDao;
    @Autowired
    private CondColumnAttrDao condColumnAttrDao;
    @Autowired
    private ExceptTypeAttrDao exceptTypeAttrDao;

    @Override
    @Transactional(transactionManager = "retailRulesTransactionManager")
    public Result saveOrderExcept(OrderExceptSaveVo orderExceptSaveVo, String userId) {
        for (OrderExceptVo orderExceptVo : orderExceptSaveVo.getOrderExcepts()) {
            OrderExceptDefineEntity orderExceptDefineEntity = new OrderExceptDefineEntity();
            BeanUtils.copyProperties(orderExceptVo, orderExceptDefineEntity);
            orderExceptDefineEntity.setExceptTypeCd(Toolbox.randomUUID());
            try {
                orderExceptDefineDao.saveOrderExcept(orderExceptDefineEntity);
            } catch (Exception e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.failure();
            }
        }

        return Result.success();
    }

    @Override
    @Transactional(transactionManager = "retailRulesTransactionManager")
    public Result editOrderExcept(OrderExceptEditVo orderExceptEditVo, String userId) {
        List<String> orderExceptIds;
        try {
            orderExceptIds = orderExceptDefineDao.orderExceptIds();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        for (OrderExceptVo orderExceptVo : orderExceptEditVo.getOrderExcepts()) {
            OrderExceptDefineEntity orderExceptDefineEntity = new OrderExceptDefineEntity();
            BeanUtils.copyProperties(orderExceptVo, orderExceptDefineEntity);
            if (orderExceptDefineEntity.getExceptTypeCd() != null) {
                try {
                    orderExceptDefineDao.editOrderExcept(orderExceptDefineEntity);
                } catch (Exception e) {
                    e.printStackTrace();
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.failure();
                }
                orderExceptIds.remove(orderExceptIds.indexOf(orderExceptDefineEntity.getExceptTypeCd()));
            } else {
                try {
                    orderExceptDefineEntity.setExceptTypeCd(Toolbox.randomUUID());
                    orderExceptDefineDao.saveOrderExcept(orderExceptDefineEntity);
                } catch (Exception e) {
                    e.printStackTrace();
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.failure();
                }
            }
        }

        if (orderExceptIds.size() > 0) {
            for (String orderExceptId : orderExceptIds) {
                try {
                    orderExceptDefineDao.deleteOrderExcept(orderExceptId);
                } catch (Exception e) {
                    e.printStackTrace();
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.failure();
                }
            }
        }

        return Result.success();
    }

    @Override
    public Result orderExceptList() {
        OrderExceptListDto result = new OrderExceptListDto();
        List<OrderExceptDto> orderExcepts = new ArrayList<>();
        List<OrderExceptDefineEntity> orderExceptList;
        try {
            orderExceptList = orderExceptDefineDao.orderExceptList();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
        for (OrderExceptDefineEntity orderExceptDefine : orderExceptList) {
            OrderExceptDto orderExceptDto = new OrderExceptDto();
            BeanUtils.copyProperties(orderExceptDefine, orderExceptDto);

            orderExcepts.add(orderExceptDto);
        }

        result.setOrderExcepts(orderExcepts);
        return Result.success(result);
    }

    @Override
    public Result condColumnSelector() {
        try {
            return Result.success(condColumnAttrDao.condColumnSelector());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }

    @Override
    public Map exceptTypeCheck() throws Exception {
        Map exceptTypeMap = new HashMap();

        List<ExceptTypeAttrEntity> exceptTypes = exceptTypeAttrDao.exceptTypeAll();
        for (ExceptTypeAttrEntity exceptType : exceptTypes) {
            exceptTypeMap.put(exceptType.getExceptTypeCd(), exceptType.getExceptTypeName());
        }

        return exceptTypeMap;
    }
}
