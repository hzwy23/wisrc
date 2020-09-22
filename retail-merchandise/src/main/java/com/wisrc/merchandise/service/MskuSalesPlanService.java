package com.wisrc.merchandise.service;


import com.wisrc.merchandise.utils.Result;
import com.wisrc.merchandise.vo.CheckMskuShopVo;
import com.wisrc.merchandise.vo.MskuSalesPlanPageVo;
import com.wisrc.merchandise.vo.SalesMskuEditVo;
import com.wisrc.merchandise.vo.SalesMskuSaveVo;
import org.springframework.validation.BindingResult;

public interface MskuSalesPlanService {
    Result getSalesPlanList(MskuSalesPlanPageVo mskuSalesPlanPageVo);

    Result getSalesPlan(String id);

    Result saveSalesPlan(SalesMskuSaveVo salesMskuSaveVo, BindingResult bindingResult);

    Result editMskuSalesPlan(SalesMskuEditVo salesMskuEditVo, BindingResult bindingResult);

    Result deleteSalesPlan(String planId);

    Result deleteMskuSalesPlan(String id);

    Result checkMskuShop(CheckMskuShopVo mskuShop);
}


