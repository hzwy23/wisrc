package com.wisrc.purchase.webapp.service;

import com.wisrc.purchase.webapp.utils.Result;
import com.wisrc.purchase.webapp.vo.GetPurchaseRejectionNewVO;
import com.wisrc.purchase.webapp.vo.purchaseRejection.RejectionIdPara;
import com.wisrc.purchase.webapp.vo.purchaseRejection.add.AddPurchaseRejectionVO;
import com.wisrc.purchase.webapp.vo.purchaseRejection.get.GetPurchaseRejectionVO;
import com.wisrc.purchase.webapp.vo.purchaseRejection.set.SetPurchaseRejectionVO;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

public interface PurchaseRejectionService {
    Result add(@Valid AddPurchaseRejectionVO vo, BindingResult bindingResult, String userId);

    Result update(@Valid SetPurchaseRejectionVO vo, BindingResult bindingResult, String userId);

    Result changeStatus(String rejectionId, Integer statusCd);

    Result findById(String rejectionId);

    Result fuzzy(@Valid GetPurchaseRejectionVO vo, BindingResult bindingResult, Integer pageNum, Integer pageSize);

    Result delete(@Valid RejectionIdPara rejectionIdArray, BindingResult bindingResult);

    void export(HttpServletResponse response, RejectionIdPara rejectionIdArray) throws IOException;

    void fuzzyExport(HttpServletResponse response, GetPurchaseRejectionNewVO vo, BindingResult bindingResult, Integer pageNum, Integer pageSize) throws IOException;

    Result fuzzyNew(GetPurchaseRejectionNewVO vo, BindingResult bindingResult, Integer pageNum, Integer pageSize);
}
