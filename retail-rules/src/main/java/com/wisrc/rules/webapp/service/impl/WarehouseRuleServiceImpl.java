package com.wisrc.rules.webapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisrc.rules.webapp.dto.warehouseRule.GetWarehouseRuleDto;
import com.wisrc.rules.webapp.dto.warehouseRule.WarehouseRuleDto;
import com.wisrc.rules.webapp.dto.warehouseRule.WarehouseRulePageDto;
import com.wisrc.rules.webapp.dto.warehouseRule.WarehouseRuleSwitchDto;
import com.wisrc.rules.webapp.entity.*;
import com.wisrc.rules.webapp.service.WarehouseRuleService;
import com.wisrc.rules.webapp.service.RulesWarehouseService;
import com.wisrc.rules.webapp.vo.warehouseRule.WarehouseRuleEditVo;
import com.wisrc.rules.webapp.vo.warehouseRule.WarehouseRulePageVo;
import com.wisrc.rules.webapp.vo.warehouseRule.WarehouseRuleSaveVo;
import com.wisrc.rules.webapp.dao.WarehouseClassifyRelationDao;
import com.wisrc.rules.webapp.dao.WarehouseProductRelationDao;
import com.wisrc.rules.webapp.dao.WarehouseRuleDefineDao;
import com.wisrc.rules.webapp.dao.WarehouseShopRelationDao;
import com.wisrc.rules.webapp.query.WarehouseRuleDefineQuery;
import com.wisrc.rules.webapp.utils.Result;
import com.wisrc.rules.webapp.utils.ServiceUtils;
import com.wisrc.rules.webapp.utils.Time;
import com.wisrc.rules.webapp.utils.Toolbox;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WarehouseRuleServiceImpl implements WarehouseRuleService {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    private WarehouseRuleDefineDao warehouseRuleDefineDao;
    @Autowired
    private WarehouseClassifyRelationDao warehouseClassifyRelationDao;
    @Autowired
    private WarehouseProductRelationDao warehouseProductRelationDao;
    @Autowired
    private WarehouseShopRelationDao warehouseShopRelationDao;
    @Autowired
    private RulesWarehouseService rulesWarehouseService;

    @Override
    @Transactional(transactionManager = "retailRulesTransactionManager")
    public Result saveWarehouseRule(WarehouseRuleSaveVo warehouseRuleSaveVo, String userId) {
        String ruleId = Toolbox.randomUUID();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            WarehouseRuleDefineEntity warehouseRuleDefine = new WarehouseRuleDefineEntity();
            BeanUtils.copyProperties(warehouseRuleSaveVo, warehouseRuleDefine);
            warehouseRuleDefine.setRuleId(ruleId);
            warehouseRuleDefine.setStartDate(new Date(sdf.parse(warehouseRuleSaveVo.getStartDate()).getTime()));
            warehouseRuleDefine.setEndDate(new Date(sdf.parse(warehouseRuleSaveVo.getEndDate()).getTime()));
            warehouseRuleDefine.setStatusCd(1);
            warehouseRuleDefine.setCreateUser(userId);
            warehouseRuleDefine.setCreateTime(Time.getCurrentTimestamp());
            warehouseRuleDefine.setModifyUser(userId);
            warehouseRuleDefine.setModifyTime(Time.getCurrentTimestamp());
            warehouseRuleDefineDao.saveWarehouseRule(warehouseRuleDefine);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failure();
        }

        for (String classify : warehouseRuleSaveVo.getClassifyCds()) {
            WarehouseClassifyRelationEntity warehouseClassifyRelation = new WarehouseClassifyRelationEntity();
            warehouseClassifyRelation.setClassifyCd(classify);
            warehouseClassifyRelation.setUuid(Toolbox.randomUUID());
            warehouseClassifyRelation.setRuleId(ruleId);

            try {
                warehouseClassifyRelationDao.saveWarehouseClassifyRelation(warehouseClassifyRelation);
            } catch (Exception e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.failure();
            }
        }

        for (String skuId : warehouseRuleSaveVo.getSkuIds()) {
            WarehouseProductRelationEntity warehouseProductRelation = new WarehouseProductRelationEntity();
            warehouseProductRelation.setSkuId(skuId);
            warehouseProductRelation.setUuid(Toolbox.randomUUID());
            warehouseProductRelation.setRuleId(ruleId);

            try {
                warehouseProductRelationDao.saveWarehouseProductRelation(warehouseProductRelation);
            } catch (Exception e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.failure();
            }
        }

        for (String shopId : warehouseRuleSaveVo.getShopIds()) {
            WarehouseShopRelationEntity warehouseShopRelation = new WarehouseShopRelationEntity();
            warehouseShopRelation.setShopId(shopId);
            warehouseShopRelation.setUuid(Toolbox.randomUUID());
            warehouseShopRelation.setRuleId(ruleId);

            try {
                warehouseShopRelationDao.saveWarehouseShopRelation(warehouseShopRelation);
            } catch (Exception e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.failure();
            }
        }

        return Result.success();
    }

    @Override
    public Result warehouseRulePage(WarehouseRulePageVo warehouseRulePageVo) {
        try {
            WarehouseRulePageDto result = new WarehouseRulePageDto();
            List<WarehouseRulePageEntity> warehouseRuleList;
            List<WarehouseRuleDto> warehouseRules = new ArrayList<>();
            List warehouseIds = new ArrayList();
            Map<String, String> warehouseMap = new HashMap();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

            WarehouseRuleDefineQuery warehouseRuleDefineQuery = new WarehouseRuleDefineQuery();
            BeanUtils.copyProperties(warehouseRulePageVo, warehouseRuleDefineQuery);
            if (warehouseRulePageVo.getModifyEndTime() != null) {
                warehouseRuleDefineQuery.setModifyEndTime(sdf.format(ServiceUtils.multipyDay(new Date(sdf.parse(warehouseRulePageVo.getModifyEndTime()).getTime()), 1)));
            }
            try {
                if (warehouseRulePageVo.getPageNum() != null && warehouseRulePageVo.getPageSize() != null) {
                    PageHelper.startPage(warehouseRulePageVo.getPageNum(), warehouseRulePageVo.getPageSize());
                }
                warehouseRuleList = warehouseRuleDefineDao.warehouseRulePage(warehouseRuleDefineQuery);
            } catch (Exception e) {
                e.printStackTrace();
                return Result.failure();
            }

            // 获取仓库名称
            for (WarehouseRulePageEntity warehouseRulePageEntity : warehouseRuleList) {
                warehouseIds.add(warehouseRulePageEntity.getWarehouseId());
            }

            if (warehouseIds.size() > 0) {
                try {
                    warehouseMap = rulesWarehouseService.getWarehouseName(warehouseIds);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // 封装发货仓规则
            for (WarehouseRulePageEntity warehouseRulePageEntity : warehouseRuleList) {
                WarehouseRuleDto warehouseRuleDto = new WarehouseRuleDto();
                BeanUtils.copyProperties(warehouseRulePageEntity, warehouseRuleDto);
                warehouseRuleDto.setOfferChannel(warehouseMap.get(warehouseRulePageEntity.getWarehouseId()));

                warehouseRules.add(warehouseRuleDto);
            }

            PageInfo pageInfo = new PageInfo(warehouseRuleList);

            result.setWarehouseRules(warehouseRules);
            result.setTotal(pageInfo.getTotal());
            result.setPages(pageInfo.getPages());
            return Result.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }

    @Override
    @Transactional(transactionManager = "retailRulesTransactionManager")
    public Result editWarehouseRule(WarehouseRuleEditVo warehouseRuleEditVo, String userId, String ruleId) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            WarehouseRuleDefineEntity warehouseRuleDefine = new WarehouseRuleDefineEntity();
            BeanUtils.copyProperties(warehouseRuleEditVo, warehouseRuleDefine);
            warehouseRuleDefine.setRuleId(ruleId);
            warehouseRuleDefine.setStartDate(new Date(sdf.parse(warehouseRuleEditVo.getStartDate()).getTime()));
            warehouseRuleDefine.setEndDate(new Date(sdf.parse(warehouseRuleEditVo.getEndDate()).getTime()));
            warehouseRuleDefine.setModifyUser(userId);
            warehouseRuleDefine.setModifyTime(Time.getCurrentTimestamp());
            warehouseRuleDefineDao.editWarehouseRule(warehouseRuleDefine);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failure();
        }


        try {
            List<String> classifyIds = warehouseClassifyRelationDao.getWarehouseClassifyRelByRuleId(ruleId);
            for (String classify : warehouseRuleEditVo.getClassifyCds()) {
                WarehouseClassifyRelationEntity warehouseClassifyRelation = new WarehouseClassifyRelationEntity();
                warehouseClassifyRelation.setClassifyCd(classify);
                warehouseClassifyRelation.setUuid(Toolbox.randomUUID());
                warehouseClassifyRelation.setRuleId(ruleId);
                int index = classifyIds.indexOf(classify);
                if (index == -1) {
                    warehouseClassifyRelationDao.saveWarehouseClassifyRelation(warehouseClassifyRelation);
                } else {
                    classifyIds.remove(index);
                }
            }

            if (classifyIds.size() > 0) {
                for (String classify : classifyIds) {
                    warehouseClassifyRelationDao.deleteWarehouseClassifyRelation(ruleId, classify);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failure();
        }

        try {
            List<String> skuIds = warehouseProductRelationDao.getProdRelByRuleId(ruleId);
            for (String skuId : warehouseRuleEditVo.getSkuIds()) {
                WarehouseProductRelationEntity warehouseProductRel = new WarehouseProductRelationEntity();
                warehouseProductRel.setSkuId(skuId);
                warehouseProductRel.setUuid(Toolbox.randomUUID());
                warehouseProductRel.setRuleId(ruleId);
                int index = skuIds.indexOf(skuId);
                if (index == -1) {
                    warehouseProductRelationDao.saveWarehouseProductRelation(warehouseProductRel);
                } else {
                    skuIds.remove(index);
                }
            }

            if (skuIds.size() > 0) {
                for (String skuId : skuIds) {
                    warehouseProductRelationDao.deleteWarehouseProductRelation(ruleId, skuId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failure();
        }

        try {
            List<String> shopIds = warehouseShopRelationDao.getShopRelByRuleId(ruleId);
            for (String shopId : warehouseRuleEditVo.getShopIds()) {
                WarehouseShopRelationEntity warehouseShopRelation = new WarehouseShopRelationEntity();
                warehouseShopRelation.setShopId(shopId);
                warehouseShopRelation.setUuid(Toolbox.randomUUID());
                warehouseShopRelation.setRuleId(ruleId);
                int index = shopIds.indexOf(shopId);
                if (index == -1) {
                    warehouseShopRelationDao.saveWarehouseShopRelation(warehouseShopRelation);
                } else {
                    shopIds.remove(index);
                }
            }

            if (shopIds.size() > 0) {
                for (String shopId : shopIds) {
                    warehouseShopRelationDao.deleteWarehouseShopRelation(ruleId, shopId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failure();
        }

        return Result.success();
    }

    @Override
    public Result getWarehouseRule(String ruleId) {
        GetWarehouseRuleDto warehouseRule = new GetWarehouseRuleDto();
        GetWarehouseRule getWarehouseRule;
        Map<String, String> warehouseMap = new HashMap<>();

        try {
            getWarehouseRule = warehouseRuleDefineDao.getWarehouseRule(ruleId);

        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        try {
            List warehouseIds = new ArrayList();
            warehouseIds.add(getWarehouseRule.getWarehouseId());
            warehouseMap = rulesWarehouseService.getWarehouseName(warehouseIds);
        } catch (Exception e) {
            e.printStackTrace();
        }

        BeanUtils.copyProperties(getWarehouseRule, warehouseRule);
        warehouseRule.setOfferChannel(ServiceUtils.idAndName(getWarehouseRule.getWarehouseId(), warehouseMap.get(getWarehouseRule.getWarehouseId())));
        warehouseRule.setStartDate(sdf.format(getWarehouseRule.getStartDate()));
        warehouseRule.setEndDate(sdf.format(getWarehouseRule.getEndDate()));

        try {
            List<String> classifyIds = warehouseClassifyRelationDao.getWarehouseClassifyRelByRuleId(ruleId);
            warehouseRule.setClassifyCds(classifyIds);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }


        try {
            List<String> skuIds = warehouseProductRelationDao.getProdRelByRuleId(ruleId);
            warehouseRule.setSkuIds(skuIds);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        try {
            List<String> shopIds = warehouseShopRelationDao.getShopRelByRuleId(ruleId);
            warehouseRule.setShopIds(shopIds);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        return Result.success(warehouseRule);
    }

    @Override
    @Transactional(transactionManager = "retailRulesTransactionManager")
    public Result warehouseRuleSwitch(WarehouseRuleSwitchDto warehouseRuleSwitchDto, String ruleId) {
        Integer status;
        int changeStatus = 1;

        try {
            status = warehouseRuleDefineDao.getStatus(ruleId);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        WarehouseRuleDefineEntity warehouseRule = new WarehouseRuleDefineEntity();

        if (status == 1) {
            changeStatus = 2;
        } else {
            BeanUtils.copyProperties(warehouseRuleSwitchDto, warehouseRule);
        }

        warehouseRule.setRuleId(ruleId);
        warehouseRule.setStatusCd(changeStatus);

        try {
            warehouseRuleDefineDao.ruleSwitch(warehouseRule);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failure();
        }

        return Result.success();
    }
}
