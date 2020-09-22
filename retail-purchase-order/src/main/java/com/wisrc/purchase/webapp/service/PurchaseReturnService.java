package com.wisrc.purchase.webapp.service;

import com.wisrc.purchase.webapp.entity.PurchaseReturnInfoEntity;
import com.wisrc.purchase.webapp.utils.Result;
import com.wisrc.purchase.webapp.vo.GetPurchaseReturnNewVo;
import com.wisrc.purchase.webapp.vo.purchaseReturn.add.AddPurchaseReturnVO;
import com.wisrc.purchase.webapp.vo.purchaseReturn.delete.ReturnBillPara;
import com.wisrc.purchase.webapp.vo.purchaseReturn.get.GetPurchaseReturnVO;
import com.wisrc.purchase.webapp.vo.purchaseReturn.set.SetPurchaseReturnVO;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

public interface PurchaseReturnService {
    Result add(@Valid AddPurchaseReturnVO vo, BindingResult bindingResult, String userId);

    Result changeStatus(Integer statusCd, String returnBill);

    Result update(@Valid SetPurchaseReturnVO vo, BindingResult bindingResult, String userId);

    Result findByReturnBill(String returnBill);

    Result handleReturnBill(PurchaseReturnInfoEntity pRIEntity);

    Result fuzzy(@Valid GetPurchaseReturnVO vo, BindingResult bindingResult, Integer pageNum, Integer pageSize);

    Result delete(@Valid ReturnBillPara returnBillArray, BindingResult bindingResult, String userId);

    void export(HttpServletResponse response, ReturnBillPara returnBillArray, BindingResult bindingResult) throws IOException;

    void fuzzyExport(HttpServletResponse response, GetPurchaseReturnNewVo vo, BindingResult bindingResult, Integer pageNum, Integer pageSize) throws IOException;

    Result fuzzyNew(GetPurchaseReturnNewVo vo, BindingResult bindingResult, Integer pageNum, Integer pageSize);
}
