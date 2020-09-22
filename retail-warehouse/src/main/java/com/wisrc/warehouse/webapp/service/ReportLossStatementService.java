package com.wisrc.warehouse.webapp.service;

import com.wisrc.warehouse.webapp.entity.ReportLossStatementStatusAttrEntity;
import com.wisrc.warehouse.webapp.utils.Result;
import com.wisrc.warehouse.webapp.vo.reportLossStatement.add.AddReportLossStatementVO;
import com.wisrc.warehouse.webapp.vo.reportLossStatement.get.GetProductVO;
import com.wisrc.warehouse.webapp.vo.reportLossStatement.set.ReviewVO;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.List;

public interface ReportLossStatementService {
    Result insert(@Valid AddReportLossStatementVO vo, BindingResult bindingResult, String userId);

    Result getByReportLossStatementId(String reportLossStatementId);

    Result review(@Valid ReviewVO reviewVO, BindingResult bindingResult, String userId);

    Result cancel(String reportLossStatementId, String cancelReason, String userId);

    Result fuzzy(Integer statusCd, String createTimeStart, String createTimeEnd, String warehouseCd, String keyWords, Integer pageNum, Integer pageSize);

    List<ReportLossStatementStatusAttrEntity> getStatusAttr();

    Result delete(String reportLossStatementId);


    Result getProduct(@Valid GetProductVO getProductVO, BindingResult bindingResult);
}
