package com.wisrc.quality.webapp.service;

import com.wisrc.quality.webapp.utils.Result;
import com.wisrc.quality.webapp.vo.productInspectionInfo.add.AddProductInspectionInfoVO;
import com.wisrc.quality.webapp.vo.productInspectionInfo.get.GetProductInspectionInfoVO;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

public interface ProductInspectionInfoService {
    Result insert(@Valid AddProductInspectionInfoVO addProductInspectionInfoVO, String userId);

    Result update(String inspectionCd, @Valid AddProductInspectionInfoVO addProductInspectionInfoVO);

    Result getByInspectionCd(String inspectionCd);

    Result deleteByInspectionCd(String inspectionCd);

    Result fuzzyFind(GetProductInspectionInfoVO vo, Integer pageNum, Integer pageSize);

    Map fuzzyFindTwo(List<String> skuIds, String arrivalId);

    int getInspectionProductInfo(String orderId);
}
