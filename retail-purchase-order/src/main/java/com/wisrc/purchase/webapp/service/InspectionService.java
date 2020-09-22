package com.wisrc.purchase.webapp.service;


import com.wisrc.purchase.webapp.dto.inspection.InspectionDto;
import com.wisrc.purchase.webapp.utils.Result;
import com.wisrc.purchase.webapp.vo.inspection.ArrivalProductEditVo;
import com.wisrc.purchase.webapp.vo.inspection.GetArrivalProductVo;
import com.wisrc.purchase.webapp.vo.inspection.InspectionExcelVo;
import com.wisrc.purchase.webapp.vo.inspection.InspectionPageVo;
import com.wisrc.purchase.webapp.vo.invoking.ArrivalSelectorVo;
import com.wisrc.purchase.webapp.vo.invoking.GetQuantityVo;
import com.wisrc.purchase.webapp.vo.returnVO.ArrivalBillReturnVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface InspectionService {
    Result saveInspection(InspectionDto inspectionSaveVo, String userId);

    Result editInspection(InspectionDto inspectionVo, String arrivalId, String userId);

    Result deleteInspectionProductDetails(List inspectionProductIds);

    Result inspectionList(InspectionPageVo inspectionPageVo);

    Result getInspection(String inspectionId);

    Result excel(InspectionExcelVo inspectionExcelVo, HttpServletRequest request, HttpServletResponse response);

    Result inspectionTypeSelector();

    Result freightAmrtTypeSelector();

    Result productStatusSelector();

    Result inspectionSelector(ArrivalSelectorVo arrivalSelectorVo);

    Result getArrivalProduct(GetArrivalProductVo getArrivalProductVo);

    Result editArrivalProduct(ArrivalProductEditVo arrivalProductEditVo);

    /**
     * 修改到货通知单状态
     *
     * @param arrivalId 到货通知单详情号
     * @param statusCd  状态码
     */
    Result changeStatus(String arrivalId, String skuId, Integer statusCd);

    Integer getOweNum(String orderId, String skuId);

    Result updateWmsReturnData(ArrivalBillReturnVO entity);

    String getInspectionIdByOrder(String randomValue);

    Integer getStatusByArrivalIdAndSkuId(String arrivalId, String skuId);

    Result deleteArrival(List arrivalIds);

    Result getQuantity(GetQuantityVo getQuantityVo);
}
