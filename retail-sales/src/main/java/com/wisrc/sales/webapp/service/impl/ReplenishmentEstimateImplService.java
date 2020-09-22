package com.wisrc.sales.webapp.service.impl;

import com.wisrc.sales.webapp.dao.ReplenishmentEstimateDao;
import com.wisrc.sales.webapp.enity.EstimateDetailEnity;
import com.wisrc.sales.webapp.service.ReplenishmentEstimateService;
import com.wisrc.sales.webapp.utils.DateUtil;
import com.wisrc.sales.webapp.utils.Result;
import com.wisrc.sales.webapp.vo.replenishmentEstimate.get.GetReplenishmentEstimateListVO;
import com.wisrc.sales.webapp.vo.replenishmentEstimate.get.GetReplenishmentEstimateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReplenishmentEstimateImplService implements ReplenishmentEstimateService {
    @Autowired
    private ReplenishmentEstimateDao replenishmentEstimateDao;

    @Override
    public Result replenishment(@Valid GetReplenishmentEstimateListVO vo, BindingResult result) {
        if (result.hasErrors()) {
            return Result.failure(390, result.getAllErrors().get(0).getDefaultMessage(), null);
        }
        //获取安全天数与预警天数之内的销量预估总量
        int num;
        int stockNum;
        List<GetReplenishmentEstimateVO> rRVOlist = vo.getRRVOlist();
        List<GetReplenishmentEstimateVO> list = new ArrayList<>();
        for (GetReplenishmentEstimateVO o : rRVOlist) {
            //msku的某段时间销量预估之和
            num = replenishmentEstimateDao.getNum(o.getShopId(), o.getMskuId(), o.getStartTime(), o.getEndTime());
            //msku目前的在仓在途总量
            stockNum = o.getFbaOnWarehouseStockNum() + o.getFbaOnWayStockNum();
            //库存小于预估量
            if (stockNum <= num) {
                list.add(o);
            }
        }
        //将库存小于预估量的详细计算
        long start;
        long end;
        int stockSum;
        int sum;
        long temp;
        int days;
        Map resultMap = new HashMap();
        for (GetReplenishmentEstimateVO o : list) {
            //获取时间段内销量预估量列表
            List<EstimateDetailEnity> estimateDetailEnityList = getEstimateEnityByTime(o.getMskuId(), o.getShopId(), o.getStartTime(), o.getEndTime());
            if (estimateDetailEnityList == null) {
                break;
            }
            Map<String, Integer> map2 = new HashMap();
            start = DateUtil.convertStrToDate(o.getStartTime(), DateUtil.DEFAULT_SHORT_DATE_FORMAT).getTime();
            end = DateUtil.convertStrToDate(o.getEndTime(), DateUtil.DEFAULT_SHORT_DATE_FORMAT).getTime();
            //msku的目前的在仓加在途总量
            stockSum = o.getFbaOnWarehouseStockNum() + o.getFbaOnWayStockNum();
            //天数累加的销售预估量
            sum = 0;
            for (EstimateDetailEnity o1 : estimateDetailEnityList) {
                temp = DateUtil.convertStrToDate(o1.getEstimateDatailDate(), DateUtil.DEFAULT_SHORT_DATE_FORMAT).getTime();
                if (start <= temp && temp <= end) {
                    sum = o1.getEstimateNumber() + sum;
                }

                if (sum == o.getFbaOnWarehouseStockNum()) {
                    //等于含当天
                    days = 1 + (int) DateUtil.dateDiff("dd", DateUtil.convertStrToDate(o.getStartTime(), DateUtil.DEFAULT_SHORT_DATE_FORMAT), DateUtil.convertStrToDate(o1.getEstimateDatailDate(), DateUtil.DEFAULT_SHORT_DATE_FORMAT));
                    //在仓支持天数
                    map2.put("onWarehouseAvailableDay", days);
                } else if (sum > o.getFbaOnWarehouseStockNum()) {
                    days = (int) DateUtil.dateDiff("dd", DateUtil.convertStrToDate(o.getStartTime(), DateUtil.DEFAULT_SHORT_DATE_FORMAT), DateUtil.convertStrToDate(o1.getEstimateDatailDate(), DateUtil.DEFAULT_SHORT_DATE_FORMAT));
                    map2.put("onWarehouseAvailableDay", days);
                }
                if (sum == stockSum) {
                    //等于含当天
                    days = 1 + (int) DateUtil.dateDiff("dd", DateUtil.convertStrToDate(o.getStartTime(), DateUtil.DEFAULT_SHORT_DATE_FORMAT), DateUtil.convertStrToDate(o1.getEstimateDatailDate(), DateUtil.DEFAULT_SHORT_DATE_FORMAT));
                    //fba总支持天数（在仓加在途库存可支持的天数）
                    map2.put("allAvailableDay", days);
                    break;
                } else if (sum > stockSum) {
                    days = (int) DateUtil.dateDiff("dd", DateUtil.convertStrToDate(o.getStartTime(), DateUtil.DEFAULT_SHORT_DATE_FORMAT), DateUtil.convertStrToDate(o1.getEstimateDatailDate(), DateUtil.DEFAULT_SHORT_DATE_FORMAT));
                    map2.put("allAvailableDay", days);
                    break;
                }
                resultMap.put(o.getId(), map2);
            }
        }
        return Result.success(resultMap);
    }

    //获取时间段内销量预估量列表，
    private List<EstimateDetailEnity> getEstimateEnityByTime(String mskuId, String shopId, String startTime, String
            endTime) {
        String estimateId = replenishmentEstimateDao.getByMskuAndShopId(mskuId, shopId);
        if (estimateId == null) {
            return null;
        }
        List<EstimateDetailEnity> estimateDetailEnityList = replenishmentEstimateDao.getEstimateDetailByEstimateIdAndCond(estimateId, startTime, endTime);
        for (EstimateDetailEnity estimateDetailEnity : estimateDetailEnityList) {
            estimateDetailEnity.setEstimateDatailDate(DateUtil.convertDateToStr(estimateDetailEnity.getEstimateDate(), DateUtil.DEFAULT_SHORT_DATE_FORMAT));
        }
        return estimateDetailEnityList;
    }
}
