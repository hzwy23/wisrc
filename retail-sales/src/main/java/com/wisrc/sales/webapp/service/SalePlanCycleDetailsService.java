package com.wisrc.sales.webapp.service;

import com.wisrc.sales.webapp.entity.SalePlanCycleDetailsEntity;
import com.wisrc.sales.webapp.vo.AddSalePlanVO;
import com.wisrc.sales.webapp.vo.SelectSalePlanDetailVO;
import com.wisrc.sales.webapp.vo.SelectSalePlanListVO;
import com.wisrc.sales.webapp.vo.UpdateSalePlanVO;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

public interface SalePlanCycleDetailsService {
    void add(SalePlanCycleDetailsEntity ele);

    List<AddSalePlanVO> getDetail(String salePlanId, int sign, String userId);

    void update(List<UpdateSalePlanVO> voList, String salePlanId, String userId);

    Workbook getResult(List<SelectSalePlanListVO> voList) throws Exception;

    List<SelectSalePlanDetailVO> getRecord(String planDate, String commodityId);

    void updateEntity(SalePlanCycleDetailsEntity detailVO);

    List<SalePlanCycleDetailsEntity> getDateById(String salePlanId);

    void deleteDetail(String salePlanId, List planDates);
}
