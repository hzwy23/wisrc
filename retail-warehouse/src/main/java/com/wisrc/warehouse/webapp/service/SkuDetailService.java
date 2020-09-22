package com.wisrc.warehouse.webapp.service;

import com.wisrc.warehouse.webapp.vo.stockVO.*;

import java.util.List;

public interface SkuDetailService {
    List<ProxyVirtual> getSkuStockOversea(String skuId, String date);

    List<ProxyVirtual> getLocalStockDetail(String skuId, String date);

    List<FbaOnwayDetailVO> getFbaOnwayDetail(String skuId, String date, String mskuId);

    List<OverseaTransferDetailVO> getTransferDetail(String skuId, String date);

    List<LocalOnwayDetailVO> getLocalOnwayDetail(String skuId, String date);

    List<ProductDetailVO> getProductDetail(String skuId, String date);

    List<FbaReturnDetailVO> getFbaReturnDetail(String skuId, String date, String mskuId);
}
