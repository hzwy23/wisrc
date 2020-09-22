package com.wisrc.product.webapp.service;

import com.wisrc.product.webapp.utils.Result;
import com.wisrc.product.webapp.vo.productInfo.NewAddProductInfoVO;
import com.wisrc.product.webapp.vo.productInfo.NewSetProductInfoVO;
import com.wisrc.product.webapp.vo.productInfo.get.BatchSkuId;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.Map;

public interface ProductInfoService {

    void delete(String skuId);

    Result newInsert(@Valid NewAddProductInfoVO newAddProductInfoVO, String userId);

    Result updateNew(@Valid NewSetProductInfoVO newSetProductInfoVO, String userId);

    Map<String, Object> newFindBySkuId(String skuId);

    Result findByBatchSkuId(@Valid BatchSkuId batchSkuId, BindingResult bindingResult);

    Result getAccessoryAndPacing(String skuId);


}
