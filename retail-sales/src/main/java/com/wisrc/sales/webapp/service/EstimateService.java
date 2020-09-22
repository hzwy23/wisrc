package com.wisrc.sales.webapp.service;

import com.wisrc.sales.webapp.enity.EstimateDetailEnity;
import com.wisrc.sales.webapp.enity.EstimateEnity;
import com.wisrc.sales.webapp.enity.RemarkEnity;
import com.wisrc.sales.webapp.enity.UpdateRemarkEnity;
import com.wisrc.sales.webapp.utils.Result;
import com.wisrc.sales.webapp.vo.EstimateEnityVo;
import com.wisrc.sales.webapp.vo.MskuParameterVo;
import com.wisrc.sales.webapp.vo.saleEstimate.SaleEstimatePageVo;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface EstimateService {
    /**
     * 新增销售预估
     */
    Result saveEstimate(EstimateEnity estimateEnity);

    Result findByCond(SaleEstimatePageVo SaleEstimatePageVo, String userId);

    Result getEstimateEnityById(String estimateId, String userId);

    Result getEstimateEnityById(String userId, String createUser, String commodityId, String estimateMonth, Date asOfDate);

    Result getProductDeatail(String shopId, String mskuId, String userId);

    Result updateEstimateAndDetail(EstimateEnityVo estimateEnityVo);

    Map getTotalNum(String commodityId, String startTime, String endTime);

    void insertRemark(RemarkEnity remarkEnity);

    void insertUpdateRemark(UpdateRemarkEnity updateRemarkEnity);

    List<EstimateDetailEnity> getEstimateEnityByTime(String mskuId, String shopId, String startTime, String endTime);

    Map getBatchEstimateDetail(List<MskuParameterVo> mskuParameterVoList);
}
