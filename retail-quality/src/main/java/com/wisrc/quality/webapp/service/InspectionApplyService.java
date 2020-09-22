package com.wisrc.quality.webapp.service;

import com.wisrc.quality.webapp.utils.Result;
import com.wisrc.quality.webapp.vo.inspectionApply.InspectionAddVO;
import com.wisrc.quality.webapp.vo.inspectionApply.InspectionDetailVO;
import com.wisrc.quality.webapp.vo.inspectionApply.InspectionEditVO;
import com.wisrc.quality.webapp.vo.inspectionApply.OrderIdRequestVO;

import java.sql.Date;
import java.util.LinkedHashMap;

public interface InspectionApplyService {

    LinkedHashMap findAll();

    LinkedHashMap findByCond(int pageNum, int pageSize, String orderId, String employeeId, Date inspectionStartTime, Date inspectionEndTime, String statusCd, String inspectionType, String keyWord);

    InspectionDetailVO findById(String inspectionId);

    void saveInspectionInfo(InspectionAddVO addVO, String userId);

    Result updateInspectionInfo(InspectionEditVO editVO, String userId);

    void deleteInspection(String[] inspectionIds);

    //更新验货方式
    String updateInspecType(String inspectionId, int inspectionTypeCd, String userId);

    //根据验货单id获取状态
    int getStatusCd(String[] inspectionIds);

    //获取所有验货方式
    LinkedHashMap findAllType();

    //获取所有状态
    LinkedHashMap findAllStatus();

    //产品检验接口
    LinkedHashMap findByCond(int pageNum, int pageSize, String orderId, String supplierName, String skuId, String productNameCN, String inspectionId);

    //产品检验接口
    LinkedHashMap findByCond(String orderId, String supplierName, String skuId, String productNameCN, String inspectionId);

    //产品检验接口
    LinkedHashMap findByCond(String orderId, String keyWords, String skuId);

    LinkedHashMap findByOrderId(OrderIdRequestVO orderIdRequestVO, String queryType);


    int getInspectionApplyInfo(String orderId);
}
