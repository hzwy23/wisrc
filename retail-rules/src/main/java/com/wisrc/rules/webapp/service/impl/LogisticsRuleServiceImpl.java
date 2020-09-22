package com.wisrc.rules.webapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisrc.rules.webapp.dao.*;
import com.wisrc.rules.webapp.dto.logisticsRule.GetLogisticsRuleDto;
import com.wisrc.rules.webapp.dto.logisticsRule.LogisticsRuleDto;
import com.wisrc.rules.webapp.dto.logisticsRule.LogisticsRulePageDto;
import com.wisrc.rules.webapp.entity.*;
import com.wisrc.rules.webapp.query.LogisticsRulePageQuery;
import com.wisrc.rules.webapp.service.*;
import com.wisrc.rules.webapp.utils.Result;
import com.wisrc.rules.webapp.utils.ServiceUtils;
import com.wisrc.rules.webapp.utils.Time;
import com.wisrc.rules.webapp.utils.Toolbox;
import com.wisrc.rules.webapp.vo.logisticsRule.LogisticsRuleEditVo;
import com.wisrc.rules.webapp.vo.logisticsRule.LogisticsRulePageVo;
import com.wisrc.rules.webapp.vo.logisticsRule.LogisticsRuleSaveVo;
import com.wisrc.rules.webapp.vo.logisticsRule.LogisticsRuleSwitchVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LogisticsRuleServiceImpl implements LogisticsRuleService {
    SimpleDateFormat sdfPage = new SimpleDateFormat("yyyy/M/dd HH:mm");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    private LogisticsInvoiceRuleDao logisticsInvoiceRuleDao;
    @Autowired
    private RulesShipmentService rulesShipmentService;
    @Autowired
    private RulesCodeService rulesCodeService;
    @Autowired
    private ProdService prodService;
    @Autowired
    private RulesWarehouseService rulesWarehouseService;
    @Autowired
    private LogisticsZipCodeRelDao logisticsZipCodeRelDao;
    @Autowired
    private LogisticsShopRelDao logisticsShopRelDao;
    @Autowired
    private LogisticsWarehouseRelDao logisticsWarehouseRelDao;
    @Autowired
    private LogisticsClassifyRelDao logisticsClassifyRelDao;
    @Autowired
    private LogisticsLabelRelDao logisticsLabelRelDao;
    @Autowired
    private LogisticsCountryRelDao logisticsCountryRelDao;
    @Autowired
    private LogisticsProductRelDao logisticsProductRelDao;
    @Autowired
    private LogisticsOfferRelDao logisticsOfferRelDao;
    @Autowired
    private RuleStatusAttrDao ruleStatusAttrDao;

    @Override
    @Transactional(transactionManager = "retailRulesTransactionManager", rollbackFor = Exception.class)
    public Result saveLogisticsRule(LogisticsRuleSaveVo logisticsRuleSaveVo, String userId) {
        String roleId = Toolbox.randomUUID();

        LogisticsInvoiceRuleDefineEntity saleInvoiceLogisticsRule = new LogisticsInvoiceRuleDefineEntity();
        BeanUtils.copyProperties(logisticsRuleSaveVo, saleInvoiceLogisticsRule);
        saleInvoiceLogisticsRule.setRuleId(roleId);
        saleInvoiceLogisticsRule.setStatusCd(1);
        if (logisticsRuleSaveVo.getMaxTotalAmount() != null)
            saleInvoiceLogisticsRule.setMaxTotalAmount(new BigDecimal(logisticsRuleSaveVo.getMaxTotalAmount()));
        if (logisticsRuleSaveVo.getMinTotalAmount() != null)
            saleInvoiceLogisticsRule.setMinTotalAmount(new BigDecimal(logisticsRuleSaveVo.getMinTotalAmount()));
        if (logisticsRuleSaveVo.getMaxWeight() != null)
            saleInvoiceLogisticsRule.setMaxWeight(new BigDecimal(logisticsRuleSaveVo.getMaxWeight()));
        if (logisticsRuleSaveVo.getMinWeight() != null)
            saleInvoiceLogisticsRule.setMinWeight(new BigDecimal(logisticsRuleSaveVo.getMinWeight()));
        if (logisticsRuleSaveVo.getStartDate() != null)
            saleInvoiceLogisticsRule.setStartDate(new Date(logisticsRuleSaveVo.getStartDate().getTime()));
        if (logisticsRuleSaveVo.getEndDate() != null)
            saleInvoiceLogisticsRule.setEndDate(new Date(logisticsRuleSaveVo.getEndDate().getTime()));
        saleInvoiceLogisticsRule.setCreateUser(userId);
        saleInvoiceLogisticsRule.setCreateTime(Time.getCurrentTimestamp());
        saleInvoiceLogisticsRule.setModifyUser(userId);
        saleInvoiceLogisticsRule.setModifyTime(Time.getCurrentTimestamp());

        try {
            logisticsInvoiceRuleDao.saveLogisticsRule(saleInvoiceLogisticsRule);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failure();
        }

        // 保存产品分类
        for (String classifyId : logisticsRuleSaveVo.getClassifyCds()) {
            LogisticsClassifyRelEntity logisticsClassifyRelEntity = new LogisticsClassifyRelEntity();
            logisticsClassifyRelEntity.setUuid(Toolbox.randomUUID());
            logisticsClassifyRelEntity.setRuleId(roleId);
            logisticsClassifyRelEntity.setClassifyCd(classifyId);

            try {
                logisticsClassifyRelDao.saveSaleInvoiceLogisticsClassifyRel(logisticsClassifyRelEntity);
            } catch (Exception e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.failure();
            }
        }

        // 保存仓库
        for (String warehouseId : logisticsRuleSaveVo.getWarehouseIds()) {
            LogisticsWarehouseRelEntity logisticsWarehouseRelEntity = new LogisticsWarehouseRelEntity();
            logisticsWarehouseRelEntity.setUuid(Toolbox.randomUUID());
            logisticsWarehouseRelEntity.setRuleId(roleId);
            logisticsWarehouseRelEntity.setWarehouseId(warehouseId);

            try {
                logisticsWarehouseRelDao.saveSaleLogisticsWarehouseRel(logisticsWarehouseRelEntity);
            } catch (Exception e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.failure();
            }
        }

        // 保存店铺
        for (String shopId : logisticsRuleSaveVo.getShopIds()) {
            LogisticsShopRelEntity logisticsShopRelEntity = new LogisticsShopRelEntity();
            logisticsShopRelEntity.setUuid(Toolbox.randomUUID());
            logisticsShopRelEntity.setRuleId(roleId);
            logisticsShopRelEntity.setShopId(shopId);

            try {
                logisticsShopRelDao.saveSaleLogisticsShopRel(logisticsShopRelEntity);
            } catch (Exception e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.failure();
            }
        }

        // 保存国家
        for (String countryId : logisticsRuleSaveVo.getCountryCds()) {
            LogisticsCountryRelEntity logisticsCountryRelEntity = new LogisticsCountryRelEntity();
            logisticsCountryRelEntity.setUuid(Toolbox.randomUUID());
            logisticsCountryRelEntity.setRuleId(roleId);
            logisticsCountryRelEntity.setCountryCd(countryId);

            try {
                logisticsCountryRelDao.saveSaleLogisticsCountryRel(logisticsCountryRelEntity);
            } catch (Exception e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.failure();
            }
        }

        // 保存邮政编码
        List<String> countryIds = logisticsRuleSaveVo.getZipCodes();
        for (String countryId : countryIds) {
            LogisticsZipCodeRelEntity logisticsZipCodeRelEntity = new LogisticsZipCodeRelEntity();
            logisticsZipCodeRelEntity.setUuid(Toolbox.randomUUID());
            logisticsZipCodeRelEntity.setRuleId(roleId);
            logisticsZipCodeRelEntity.setZipCode(countryId);

            try {
                logisticsZipCodeRelDao.saveSaleLogisticsZipCodeRel(logisticsZipCodeRelEntity);
            } catch (Exception e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.failure();
            }
        }

        // 保存物流渠道
        for (String offerId : logisticsRuleSaveVo.getOfferIds()) {
            LogisticsOfferRelEntity logisticsOfferRelEntity = new LogisticsOfferRelEntity();
            logisticsOfferRelEntity.setUuid(Toolbox.randomUUID());
            logisticsOfferRelEntity.setRuleId(roleId);
            logisticsOfferRelEntity.setOfferId(offerId);

            try {
                logisticsOfferRelDao.saveSaleLogisticsOfferRel(logisticsOfferRelEntity);
            } catch (Exception e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.failure();
            }
        }

        // 保存特性标签
        for (Integer labelId : logisticsRuleSaveVo.getLabelCds()) {
            LogisticsLabelRelEntity logisticsLabelRelEntity = new LogisticsLabelRelEntity();
            logisticsLabelRelEntity.setUuid(Toolbox.randomUUID());
            logisticsLabelRelEntity.setRuleId(roleId);
            logisticsLabelRelEntity.setLabelCd(labelId);

            try {
                logisticsLabelRelDao.saveSaleLogisticsLabelRel(logisticsLabelRelEntity);
            } catch (Exception e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.failure();
            }
        }

        // 保存库存SKU
        List<String> skuIds = logisticsRuleSaveVo.getSkuIds();
        for (String skuId : skuIds) {
            LogisticsProductRelEntity logisticsProductRelEntity = new LogisticsProductRelEntity();
            logisticsProductRelEntity.setUuid(Toolbox.randomUUID());
            logisticsProductRelEntity.setRuleId(roleId);
            logisticsProductRelEntity.setSkuId(skuId);

            try {
                logisticsProductRelDao.saveSaleInvoiceLogisticsProductRel(logisticsProductRelEntity);
            } catch (Exception e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.failure();
            }
        }

        return Result.success();
    }

    @Override
    public Result logisticsRulePage(LogisticsRulePageVo logisticsRulePageVo) {
        try {
            LogisticsRulePageDto result = new LogisticsRulePageDto();
            List<LogisticsRuleDto> logisticsRules = new ArrayList<>();
            List<LogisticsRulePageEntity> logisticsRuleResult;
            List shipmentIds = new ArrayList();
            Map<String, String> channelNameMap = new HashMap<>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

            LogisticsRulePageQuery logisticsRulePageQuery = new LogisticsRulePageQuery();
            BeanUtils.copyProperties(logisticsRulePageVo, logisticsRulePageQuery);
            if (logisticsRulePageVo.getModifyEndTime() != null) {
                logisticsRulePageQuery.setModifyEndTime(sdf.format(ServiceUtils.multipyDay(new Date(sdf.parse(logisticsRulePageVo.getModifyEndTime()).getTime()), 1)));
            }
            try {
                if (logisticsRulePageVo.getPageNum() != null && logisticsRulePageVo.getPageSize() != null) {
                    PageHelper.startPage(logisticsRulePageVo.getPageNum(), logisticsRulePageVo.getPageSize());
                }
                logisticsRuleResult = logisticsInvoiceRuleDao.logisticsRulePage(logisticsRulePageQuery);
            } catch (Exception e) {
                e.printStackTrace();
                return Result.failure();
            }

            for (LogisticsRulePageEntity logisticsRule : logisticsRuleResult) {
                shipmentIds.add(logisticsRule.getOfferId());
            }

            if (shipmentIds.size() > 0) {
                try {
                    channelNameMap = rulesShipmentService.getChannelName(shipmentIds);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            for (LogisticsRulePageEntity logisticsRule : logisticsRuleResult) {
                LogisticsRuleDto logisticsRuleDto = new LogisticsRuleDto();
                BeanUtils.copyProperties(logisticsRule, logisticsRuleDto);
                logisticsRuleDto.setOfferChannel(channelNameMap.get(logisticsRule.getOfferId()));
                logisticsRuleDto.setModifyTime(sdfPage.format(logisticsRule.getModifyTime()));

                logisticsRules.add(logisticsRuleDto);
            }

            PageInfo pageInfo = new PageInfo(logisticsRuleResult);
            result.setLogisticsRules(logisticsRules);
            result.setTotal(pageInfo.getTotal());
            result.setPages(pageInfo.getPages());
            return Result.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }

    @Override
    public Result countrySelector() {
        List countrySelector;
        try {
            countrySelector = rulesCodeService.getCountrySelector();
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(400, e.getMessage(), null);
        }
        return Result.success(countrySelector);
    }

    @Override
    public Result classifySelector() {
        List classifySelector;

        try {
            classifySelector = prodService.classifySelector();
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(400, e.getMessage(), null);
        }

        return Result.success(classifySelector);
    }

    @Override
    public Result warehouseSelector() {
        List warehouseSelector;

        try {
            warehouseSelector = rulesWarehouseService.warehouseSelector();
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(400, e.getMessage(), null);
        }

        return Result.success(warehouseSelector);
    }

    @Override
    public Result characteristicSelector() {
        List characteristicSelector;

        try {
            characteristicSelector = rulesCodeService.characteristicSelector();
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(400, e.getMessage(), null);
        }

        return Result.success(characteristicSelector);
    }

    @Override
    public Result getLogisticsRule(String ruleId) {
        GetLogisticsRuleDto result = new GetLogisticsRuleDto();

        try {
            LogisticsInvoiceRuleDefineEntity logisticsRule = logisticsInvoiceRuleDao.getSaleInvoiceLogisticsRule(ruleId);
            BeanUtils.copyProperties(logisticsRule, result);
            if (logisticsRule.getMaxTotalAmount() != null)
                result.setMaxTotalAmount(logisticsRule.getMaxTotalAmount().doubleValue());
            if (logisticsRule.getMinTotalAmount() != null)
                result.setMinTotalAmount(logisticsRule.getMinTotalAmount().doubleValue());
            if (logisticsRule.getMaxWeight() != null) result.setMaxWeight(logisticsRule.getMaxWeight().doubleValue());
            if (logisticsRule.getMinWeight() != null) result.setMinWeight(logisticsRule.getMinWeight().doubleValue());

            result.setZipCode(logisticsZipCodeRelDao.getZipCodeByRuleId(ruleId));
            result.setShopIds(logisticsShopRelDao.getShopByRuleId(ruleId));
            result.setWarehouseIds(logisticsWarehouseRelDao.getWarehouseByRuleId(ruleId));
            result.setClassifyIds(logisticsClassifyRelDao.getClassifyByRuleId(ruleId));
            result.setLabelIds(logisticsLabelRelDao.getLabelByRuleId(ruleId));
            result.setCountryIds(logisticsCountryRelDao.getCountryBuRuleId(ruleId));
            result.setSkuIds(logisticsProductRelDao.getProductByRuleId(ruleId));
            result.setOfferIds(logisticsOfferRelDao.getOfferIdByRuleId(ruleId));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        return Result.success(result);
    }

    @Override
    @Transactional(transactionManager = "retailRulesTransactionManager", rollbackFor = Exception.class)
    public Result editLogisticsRule(String ruleId, LogisticsRuleEditVo logisticsRuleEditVo, String userId) {
        LogisticsInvoiceRuleDefineEntity LogisticsInvoiceRuleDefineEntity = new LogisticsInvoiceRuleDefineEntity();
        BeanUtils.copyProperties(logisticsRuleEditVo, LogisticsInvoiceRuleDefineEntity);
        LogisticsInvoiceRuleDefineEntity.setRuleId(ruleId);
        if (logisticsRuleEditVo.getMaxTotalAmount() != null)
            LogisticsInvoiceRuleDefineEntity.setMaxTotalAmount(new BigDecimal(logisticsRuleEditVo.getMaxTotalAmount()));
        if (logisticsRuleEditVo.getMinTotalAmount() != null)
            LogisticsInvoiceRuleDefineEntity.setMinTotalAmount(new BigDecimal(logisticsRuleEditVo.getMinTotalAmount()));
        if (logisticsRuleEditVo.getMaxWeight() != null)
            LogisticsInvoiceRuleDefineEntity.setMaxWeight(new BigDecimal(logisticsRuleEditVo.getMaxWeight()));
        if (logisticsRuleEditVo.getMinWeight() != null)
            LogisticsInvoiceRuleDefineEntity.setMinWeight(new BigDecimal(logisticsRuleEditVo.getMinWeight()));
        if (logisticsRuleEditVo.getStartDate() != null)
            LogisticsInvoiceRuleDefineEntity.setStartDate(new Date(logisticsRuleEditVo.getStartDate().getTime()));
        if (logisticsRuleEditVo.getEndDate() != null)
            LogisticsInvoiceRuleDefineEntity.setEndDate(new Date(logisticsRuleEditVo.getEndDate().getTime()));
        LogisticsInvoiceRuleDefineEntity.setModifyUser(userId);
        LogisticsInvoiceRuleDefineEntity.setModifyTime(Time.getCurrentTimestamp());

        try {
            // 编辑物流规则
            logisticsInvoiceRuleDao.editLogisticsRule(LogisticsInvoiceRuleDefineEntity);

            // 编辑邮政编码关系
            List<String> zipCodes = logisticsZipCodeRelDao.getZipCodeByRuleId(ruleId);
            if (logisticsRuleEditVo.getZipCodes() != null) {
                for (String zipCodeVo : logisticsRuleEditVo.getZipCodes()) {
                    int index = zipCodes.indexOf(zipCodeVo);
                    if (index == -1) {
                        LogisticsZipCodeRelEntity zipCodeRelEntity = new LogisticsZipCodeRelEntity();
                        zipCodeRelEntity.setUuid(Toolbox.randomUUID());
                        zipCodeRelEntity.setRuleId(ruleId);
                        zipCodeRelEntity.setZipCode(zipCodeVo);
                        logisticsZipCodeRelDao.saveSaleLogisticsZipCodeRel(zipCodeRelEntity);
                    } else {
                        zipCodes.remove(index);
                    }
                }
            }
            for (String zipCode : zipCodes) {
                LogisticsZipCodeRelEntity zipCodeEntity = new LogisticsZipCodeRelEntity();
                zipCodeEntity.setRuleId(ruleId);
                zipCodeEntity.setZipCode(zipCode);
                logisticsZipCodeRelDao.deleteSaleLogisticsZipCodeRel(zipCodeEntity);
            }

            // 编辑店铺关系
            List<String> shopIds = logisticsShopRelDao.getShopByRuleId(ruleId);
            if (logisticsRuleEditVo.getShopIds() != null) {
                for (String shopIdVo : logisticsRuleEditVo.getShopIds()) {
                    int index = shopIds.indexOf(shopIdVo);
                    if (index == -1) {
                        LogisticsShopRelEntity shopRelEntity = new LogisticsShopRelEntity();
                        shopRelEntity.setUuid(Toolbox.randomUUID());
                        shopRelEntity.setRuleId(ruleId);
                        shopRelEntity.setShopId(shopIdVo);
                        logisticsShopRelDao.saveSaleLogisticsShopRel(shopRelEntity);
                    } else {
                        shopIds.remove(index);
                    }
                }
            }
            for (String shopId : shopIds) {
                LogisticsShopRelEntity shopRelEntity = new LogisticsShopRelEntity();
                shopRelEntity.setRuleId(ruleId);
                shopRelEntity.setShopId(shopId);
                logisticsShopRelDao.deleteSaleLogisticsShopRel(shopRelEntity);
            }

            // 编辑仓库关系
            List<String> warehouses = logisticsWarehouseRelDao.getWarehouseByRuleId(ruleId);
            if (logisticsRuleEditVo.getWarehouseIds() != null) {
                for (String warehouseVo : logisticsRuleEditVo.getWarehouseIds()) {
                    int index = warehouses.indexOf(warehouseVo);
                    if (index == -1) {
                        LogisticsWarehouseRelEntity warehouseRelEntity = new LogisticsWarehouseRelEntity();
                        warehouseRelEntity.setUuid(Toolbox.randomUUID());
                        warehouseRelEntity.setRuleId(ruleId);
                        warehouseRelEntity.setWarehouseId(warehouseVo);
                        logisticsWarehouseRelDao.saveSaleLogisticsWarehouseRel(warehouseRelEntity);
                    } else {
                        warehouses.remove(index);
                    }
                }
            }
            for (String warehouse : warehouses) {
                LogisticsWarehouseRelEntity warehouseRelEntity = new LogisticsWarehouseRelEntity();
                warehouseRelEntity.setRuleId(ruleId);
                warehouseRelEntity.setWarehouseId(warehouse);
                logisticsWarehouseRelDao.deleteSaleLogisticsWarehouseRel(warehouseRelEntity);
            }

            // 编辑产品分类关系
            List<String> classifyRules = logisticsClassifyRelDao.getClassifyByRuleId(ruleId);
            if (logisticsRuleEditVo.getClassifyCds() != null) {
                for (String classifyVo : logisticsRuleEditVo.getClassifyCds()) {
                    int index = classifyRules.indexOf(classifyVo);
                    if (index == -1) {
                        LogisticsClassifyRelEntity classifyRelEntity = new LogisticsClassifyRelEntity();
                        classifyRelEntity.setUuid(Toolbox.randomUUID());
                        classifyRelEntity.setRuleId(ruleId);
                        classifyRelEntity.setClassifyCd(classifyVo);
                        logisticsClassifyRelDao.saveSaleInvoiceLogisticsClassifyRel(classifyRelEntity);
                    } else {
                        classifyRules.remove(index);
                    }
                }
            }
            for (String classifyRule : classifyRules) {
                LogisticsClassifyRelEntity classifyRelEntity = new LogisticsClassifyRelEntity();
                classifyRelEntity.setRuleId(ruleId);
                classifyRelEntity.setClassifyCd(classifyRule);
                logisticsClassifyRelDao.deleteSaleInvoiceLogisticsClassifyRel(classifyRelEntity);
            }

            // 编辑产品标签关系
            List<Integer> labelRules = logisticsLabelRelDao.getLabelByRuleId(ruleId);
            if (logisticsRuleEditVo.getLabelCds() != null) {
                for (Integer labelVo : logisticsRuleEditVo.getLabelCds()) {
                    int index = labelRules.indexOf(labelVo);
                    if (index == -1) {
                        LogisticsLabelRelEntity labelRelEntity = new LogisticsLabelRelEntity();
                        labelRelEntity.setUuid(Toolbox.randomUUID());
                        labelRelEntity.setRuleId(ruleId);
                        labelRelEntity.setLabelCd(labelVo);
                        logisticsLabelRelDao.saveSaleLogisticsLabelRel(labelRelEntity);
                    } else {
                        labelRules.remove(index);
                    }
                }
            }
            for (Integer labelRule : labelRules) {
                LogisticsLabelRelEntity labelRelEntity = new LogisticsLabelRelEntity();
                labelRelEntity.setRuleId(ruleId);
                labelRelEntity.setLabelCd(labelRule);
                logisticsLabelRelDao.deleteSaleLogisticsLabelRel(labelRelEntity);
            }

            // 编辑国家关系
            List<String> countryRules = logisticsCountryRelDao.getCountryBuRuleId(ruleId);
            if (logisticsRuleEditVo.getCountryCds() != null) {
                for (String countryVo : logisticsRuleEditVo.getCountryCds()) {
                    int index = countryRules.indexOf(countryVo);
                    if (index == -1) {
                        LogisticsCountryRelEntity countryRelEntity = new LogisticsCountryRelEntity();
                        countryRelEntity.setUuid(Toolbox.randomUUID());
                        countryRelEntity.setRuleId(ruleId);
                        countryRelEntity.setCountryCd(countryVo);
                        logisticsCountryRelDao.saveSaleLogisticsCountryRel(countryRelEntity);
                    } else {
                        countryRules.remove(index);
                    }
                }
            }
            for (String countryRule : countryRules) {
                LogisticsCountryRelEntity countryRelEntity = new LogisticsCountryRelEntity();
                countryRelEntity.setRuleId(ruleId);
                countryRelEntity.setCountryCd(countryRule);
                logisticsCountryRelDao.deleteSaleLogisticsCountryRel(countryRelEntity);
            }

            // 编辑产品关系
            List<String> productRules = logisticsProductRelDao.getProductByRuleId(ruleId);
            if (logisticsRuleEditVo.getSkuIds() != null) {
                for (String productVo : logisticsRuleEditVo.getSkuIds()) {
                    int index = productRules.indexOf(productVo);
                    if (index == -1) {
                        LogisticsProductRelEntity productRelEntity = new LogisticsProductRelEntity();
                        productRelEntity.setUuid(Toolbox.randomUUID());
                        productRelEntity.setRuleId(ruleId);
                        productRelEntity.setSkuId(productVo);
                        logisticsProductRelDao.saveSaleInvoiceLogisticsProductRel(productRelEntity);
                    } else {
                        productRules.remove(index);
                    }
                }
            }
            for (String productRule : productRules) {
                LogisticsProductRelEntity productRelEntity = new LogisticsProductRelEntity();
                productRelEntity.setRuleId(ruleId);
                productRelEntity.setSkuId(productRule);
                logisticsProductRelDao.deleteSaleInvoiceLogisticsProductRel(productRelEntity);
            }

            // 编辑物流渠道关系
            List<String> offerRules = logisticsOfferRelDao.getOfferIdByRuleId(ruleId);
            if (logisticsRuleEditVo.getOfferIds() != null) {
                for (String offerVo : logisticsRuleEditVo.getOfferIds()) {
                    int index = offerRules.indexOf(offerVo);
                    if (index == -1) {
                        LogisticsOfferRelEntity offerRelEntity = new LogisticsOfferRelEntity();
                        offerRelEntity.setUuid(Toolbox.randomUUID());
                        offerRelEntity.setRuleId(ruleId);
                        offerRelEntity.setOfferId(offerVo);
                        logisticsOfferRelDao.saveSaleLogisticsOfferRel(offerRelEntity);
                    } else {
                        offerRules.remove(index);
                    }
                }
            }
            for (String offerRule : offerRules) {
                LogisticsOfferRelEntity offerRelEntity = new LogisticsOfferRelEntity();
                offerRelEntity.setRuleId(ruleId);
                offerRelEntity.setOfferId(offerRule);
                logisticsOfferRelDao.deleteSaleLogisticsOfferRel(offerRelEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failure();
        }

        return Result.success();
    }

    @Override
    public Result logisticsRuleStatus() {
        try {
            return Result.success(ruleStatusAttrDao.logisticsRuleStatus());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }

    @Override
    public Result logisticsRuleSwitch(LogisticsRuleSwitchVo logisticsRuleSwitchVo, String ruleId, String userId) {
        Integer status;
        Integer changerStatus = 2;
        try {
            status = logisticsInvoiceRuleDao.getRuleStatus(ruleId);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        Map ruleStatusMap = new HashMap();
        try {
            for (RuleStatusAttrEntity logisticsRuleStatusAttr : ruleStatusAttrDao.logisticsRuleStatus()) {
                ruleStatusMap.put(logisticsRuleStatusAttr.getStatusCd(), logisticsRuleStatusAttr.getStatusDesc());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        LogisticsInvoiceRuleDefineEntity logisticsInvoiceRuleDefineEntity = new LogisticsInvoiceRuleDefineEntity();
        logisticsInvoiceRuleDefineEntity.setRuleId(ruleId);
        logisticsInvoiceRuleDefineEntity.setModifyUser(userId);
        logisticsInvoiceRuleDefineEntity.setModifyTime(Time.getCurrentTimestamp());
        if (status == 2) {
            changerStatus = 1;
            try {
                logisticsInvoiceRuleDefineEntity.setStartDate(new Date(sdf.parse(logisticsRuleSwitchVo.getStartDate()).getTime()));
                logisticsInvoiceRuleDefineEntity.setEndDate(new Date(sdf.parse(logisticsRuleSwitchVo.getEndDate()).getTime()));
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(400, "请填写有效期", "");
            }
        } else if (status == 3) {
            return new Result(400, "无效请求", "");
        }
        logisticsInvoiceRuleDefineEntity.setStatusCd(changerStatus);
        try {
            logisticsInvoiceRuleDao.logisticsRuleSwitch(logisticsInvoiceRuleDefineEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        return Result.success(ruleStatusMap.get(changerStatus));
    }

    @Override
    public Result priorityNumberSelector() {
        List result = new ArrayList();
        for (int m = 1; m <= 100; m++) {
            result.add(ServiceUtils.idAndName(m, String.valueOf(m)));
        }

        return Result.success(result);
    }

    @Override
    public Result channelSelector() {
        try {
            return Result.success(rulesShipmentService.channelSelector());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }

    @Override
    public Result currencySelector() {
        try {
            return Result.success(rulesCodeService.getCurrencySelector());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }
}
