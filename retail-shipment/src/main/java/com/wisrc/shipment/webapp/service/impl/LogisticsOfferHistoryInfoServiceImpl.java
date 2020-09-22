package com.wisrc.shipment.webapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisrc.shipment.webapp.entity.LogisticsOfferHistoryInfoEntity;
import com.wisrc.shipment.webapp.service.LogisticsOfferHistoryInfoService;
import com.wisrc.shipment.webapp.dao.LogisticsOfferHistoryInfoDao;
import com.wisrc.shipment.webapp.utils.Crypto;
import com.wisrc.shipment.webapp.utils.PageData;
import com.wisrc.shipment.webapp.utils.Result;
import com.wisrc.shipment.webapp.utils.Time;
import com.wisrc.shipment.webapp.vo.LogisticHistoryChargeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class LogisticsOfferHistoryInfoServiceImpl implements LogisticsOfferHistoryInfoService {
    @Autowired
    private LogisticsOfferHistoryInfoDao logisticsOfferHistoryInfoDao;

    @Override
    public void add(LogisticsOfferHistoryInfoEntity ele) {
        logisticsOfferHistoryInfoDao.add(ele);
    }

    @Override
    public void delete(String offerId, String userId) {
        String modifyTime = Time.getCurrentDateTime();
        logisticsOfferHistoryInfoDao.delete(offerId, userId, modifyTime);
    }

    @Override
    public void physicalDeleteHis(String offerId) {
        logisticsOfferHistoryInfoDao.physicalDelete(offerId);
    }

    @Override
    public LinkedHashMap findHistory(int pageNum, int pageSize, String offerId) {
        List<String> headers = logisticsOfferHistoryInfoDao.findHeader(offerId);

        PageHelper.startPage(pageNum, pageSize * headers.size());
        List<LogisticHistoryChargeVO> list = logisticsOfferHistoryInfoDao.findHistoryOffer(offerId);
        PageInfo pageInfo = new PageInfo(list);
        int total = list.size();

        List<LinkedHashMap> ret = new ArrayList<>();
        String dt = null;
        for (int i = 0; i < total; i++) {
            LinkedHashMap one = new LinkedHashMap();
            for (int j = i; j < total; j++) {
                LogisticHistoryChargeVO ele = list.get(j);
                if (dt == null) {
                    // 第一行
                    dt = ele.getModifyTime();
                    one.put("updateTime", dt);
                    one.put(ele.getSection(), ele.getUnitPriceWithOil());
                    if (j == total - 1) {
                        ret.add(one);
                        j = total;
                        i = total;
                        break;
                    }
                } else if (!dt.equals(ele.getModifyTime()) || j == total - 1) {
                    // 新的一行
                    dt = null;
                    if (j == total - 1) {
                        one.put(ele.getSection(), ele.getUnitPriceWithOil());
                        i = total;
                        j = total;
                    } else {
                        i = j - 1;
                    }
                    ret.add(one);
                    break;
                } else {
                    // 接上一行
                    one.put(ele.getSection(), ele.getUnitPriceWithOil());
                }
            }
        }
        List<LinkedHashMap> result = new ArrayList<>();
        int i = 0;
        for (LinkedHashMap linkedHashMap : ret) {
            if (i >= (pageNum - 1) * pageSize && i <= (pageNum * pageSize) - 1) {
                result.add(linkedHashMap);
            }
            i++;
        }
        return PageData.pack(ret.size(), (ret.size() / pageSize) + 1, "logisticsOfferHistoryInfoEntityList", result);
    }

    @Override
    public Result batchPrice(String[] offerIds, double num) {
//        List<LogisticHistoryChargeVO> list = logisticsOfferHistoryInfoDao.batchPrice(offerIds);
        //查询审核信息
        String offerIdsStr = getOfferIds(offerIds);
        List<LogisticHistoryChargeVO> list = logisticsOfferHistoryInfoDao.batchPriceV2(offerIdsStr);
        for (LogisticHistoryChargeVO vo : list) {
            logisticsOfferHistoryInfoDao.deletePrice(vo.getUuid());
        }
        String modifyTime = Time.getCurrentDateTime();

        try {
            if (list != null) {
                for (LogisticHistoryChargeVO vo : list) {
                    String startChargeSection = vo.getSection().split("-")[0];
                    String str = vo.getSection().split("-")[1];
                    String endChargeSection = str.substring(0, str.indexOf(" "));
                    LogisticsOfferHistoryInfoEntity history = new LogisticsOfferHistoryInfoEntity();
                    history.setUuid(Crypto.sha(vo.getOfferId(), modifyTime, startChargeSection));
                    history.setOfferId(vo.getOfferId());
                    history.setModifyTime(modifyTime);
                    history.setStartChargeSection(Double.parseDouble(startChargeSection));
                    history.setEndChargeSection(Double.parseDouble(endChargeSection));
                    history.setUnitPrice(Double.parseDouble(vo.getUnitPrice()));
                    history.setUnitPriceWithOil(Double.parseDouble(vo.getUnitPrice()) * (1 + num / 100));
                    history.setDeleteStatus(0);
                    logisticsOfferHistoryInfoDao.add(history);
                }
            }
            return Result.success();
        } catch (NumberFormatException e) {
            return Result.failure();
        }
    }

    private String getOfferIds(String[] offerIds) {
        String offerIdsStr = "";
        for (String o : offerIds) {
            offerIdsStr += "'" + o + "'" + ",";
        }
        if (offerIdsStr.endsWith(",")) {
            int index = offerIdsStr.lastIndexOf(",");
            offerIdsStr = offerIdsStr.substring(0, index);
        }
        offerIdsStr = "(" + offerIdsStr + ")";
        if (offerIdsStr.equals("()")) {
            offerIdsStr = "('')";
        }
        return offerIdsStr;
    }

    /*@Override
    public Result deletePrice(String uuid) {
        try {
            logisticsOfferHistoryInfoDao.deletePrice(uuid);
            return Result.success();
        } catch (Exception e) {
            return Result.failure();
        }
    }*/
}
