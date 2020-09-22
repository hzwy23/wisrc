package com.wisrc.shipment.webapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisrc.shipment.webapp.entity.*;
import com.wisrc.shipment.webapp.service.LogisticsOfferBasisInfoService;
import com.wisrc.shipment.webapp.service.ReplenishmentRemindService;
import com.wisrc.shipment.webapp.dao.ReplenishmentRemindDao;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReplenishmentRemindServiceImpl implements ReplenishmentRemindService {

    @Autowired
    private MyFeignInfoService feignInfo;
    @Autowired
    private ReplenishmentRemindDao replenishmentRemindDao;
    @Autowired
    private LogisticsOfferBasisInfoService logisticsOfferBasisInfoService;

    @Override
    public Map<String, Object> getReplenishmentList(String shopId, Integer warningType, HashSet<String> mskuIds, String sort, Integer currentPage, Integer pageSize) {
        // 排序处理
        String sortKey = null;
        Integer sortVal = null;
        if (sort != null && sort.split(":").length > 1) {
            sortKey = sort.split(":")[0];
            sortVal = NumberUtils.toInt(sort.split(":")[1], 0);
            switch (sortKey) {
                case "fbaWarehouseStock":
                    sort = "fba_warehouse_stock";
                    break;
                case "fbaWayStock":
                    sort = "fba_way_stock";
                    break;
                case "estimatedDailySales":
                    sort = "estimated_daily_sales";
                    break;
                case "fbaAvailableDays":
                    sort = "fba_available_days";
                    break;
                case "safeStockDays":
                    sort = "safe_stock_days";
                    break;
                default:
                    sort = null;
                    break;
            }
            if (sort != null && sortVal < 1) {
                sort += " DESC";
            }
        } else {
            sort = null;
        }
        PageHelper.startPage(currentPage, pageSize);
        List<FbaReplenishmentRemindEntity> list = replenishmentRemindDao.selectRemind(shopId, warningType, mskuIds, sort);
        PageInfo<FbaReplenishmentRemindEntity> pageInfo = new PageInfo<>(list);
        List<Map<String, Object>> resList = new ArrayList<>();
        for (FbaReplenishmentRemindEntity remind : pageInfo.getList()) {
            Map<String, Object> item = new HashMap<>();
            item.put("replenishmentId", remind.getReplenishmentId());
            item.put("warningTypeCd", remind.getWarningTypeCd());
            item.put("fbaWarehouseStock", remind.getFbaWarehouseStock());
            item.put("fbaWarehouseAvailableDays", remind.getFbaWarehouseAvailableDays());
            item.put("fbaWayStock", remind.getFbaWayStock());
            item.put("fbaWayAvailableDays", remind.getFbaWayAvailableDays());
            item.put("estimatedDailySales", remind.getEstimatedDailySales());
            item.put("fbaAvailableDays", remind.getFbaAvailableDays());
            item.put("safeStockDays", remind.getSafeStockDays());
            item.put("replenishmentQuantity", remind.getReplenishmentQuantity());
            item.put("mskuId", remind.getMskuId());
            item.put("shopId", remind.getShopId());
            item.put("shopName", feignInfo.getShopNameById(remind.getShopId()));
            resList.add(item);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("list", resList);
        map.put("total", pageInfo.getTotal());
        map.put("pages", pageInfo.getPages());
        return map;
    }

    @Override
    public LinkedHashMap<String, Object> getReplenishmentInfo(String replenishmentId) {
        FbaReplenishmentRemindEntity remind = replenishmentRemindDao.selectRemindById(replenishmentId);
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        if (remind != null) {
            map.put("mskuId", remind.getMskuId());
            map.put("shopId", remind.getShopId());
            map.put("replenishmentQuantity", remind.getReplenishmentQuantity());
            map.put("estimatedWeight", remind.getEstimatedWeight());
            map.put("estimatedVolume", remind.getEstimatedVolume());
            map.put("mskuInfo", feignInfo.getMskuById(remind.getMskuId()));
            map.put("shopName", feignInfo.getShopNameById(remind.getShopId()));
        }
        return map;
    }

    @Override
    public boolean setWarningDays(String warningId, Integer days) {
        return replenishmentRemindDao.updateWarningDaysById(warningId, days);
    }

    @Override
    public boolean setSafeDays(String replenishmentId, Integer days) {
        return replenishmentRemindDao.updateSafeDaysById(replenishmentId, days);
    }

    @Override
    public List<FbaWarningTypeAttrEntity> getWarningTypeAttr() {
        return replenishmentRemindDao.selectWarningTypeAttr();
    }

    @Override
    public List<FbaWarningDaysEntity> getWarningDays() {
        return replenishmentRemindDao.selectWarningDays();
    }

    @Override
    public List<Map<String, Object>> getProposalScheme(String uuid, String replenishmentId) {
        feignInfo.setProductLabelAttr();
        List<Map<String, Object>> resList = new ArrayList<>();
        List<ProposalSchemeEntity> result = replenishmentRemindDao.selectSchemedById(uuid, replenishmentId);
        for (ProposalSchemeEntity scheme : result) {
            Map<String, Object> item = new HashMap<>();
            item.put("uuid", scheme.getUuid());
            item.put("replenishmentId", scheme.getReplenishmentId());
            item.put("deliveringAmount", scheme.getDeliveringAmount());
            item.put("originatingWarehouseId", scheme.getOriginatingWarehouseId());
            item.put("shipTypeCd", scheme.getShipTypeCd());
            item.put("shipmentId", scheme.getShipmentId());
            item.put("effectiveDays", scheme.getEffectiveDays());
            item.put("unitPrice", scheme.getUnitPrice());
            item.put("forecastFreight", scheme.getForecastFreight());
            item.put("totalPrice", scheme.getTotalPrice());
            item.put("shipTypeName", feignInfo.getShipTypeById(scheme.getShipTypeCd()));
            item.put("originatingWarehouseName", feignInfo.getWarehouseById(scheme.getOriginatingWarehouseId()));
            LogisticsOfferBasisInfoEntity basisInfo = logisticsOfferBasisInfoService.get(scheme.getShipmentId());
            Map<String, Object> shipmentInfo = new HashMap<>();
            shipmentInfo.put("shipMentName", basisInfo.getShipMentName());
            shipmentInfo.put("channelName", basisInfo.getChannelName());
            shipmentInfo.put("chargeTypeName", basisInfo.getChargeTypeName());
            shipmentInfo.put("remark", basisInfo.getRemark());
            shipmentInfo.put("historyList", basisInfo.getHistoryList());
            shipmentInfo.put("effectiveList", basisInfo.getEffectiveList());
            List<LogisticsAllowLabelRelEntity> labelList = basisInfo.getLabelList();
            for (LogisticsAllowLabelRelEntity labelItem : labelList) {
                labelItem.setLabelName(feignInfo.getProductLabelAttr(String.valueOf(labelItem.getLabelCd())));
            }
            shipmentInfo.put("labelList", labelList);
            item.put("shipmentInfo", shipmentInfo);
            resList.add(item);
        }
        return resList;
    }

}
