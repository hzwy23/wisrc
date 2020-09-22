package com.wisrc.purchase.webapp.service;


import com.wisrc.purchase.webapp.vo.product.GetProductInfoVO;

import java.util.List;
import java.util.Map;


public interface ProductHandleService {
    Map getProductCNNameMap(List skuId) throws Exception;

    List getFindKeyDealted(String findKey) throws Exception;

    List getByNameToIds(String name) throws Exception;

    Map getProductList(List<String> idlist);

    Map getProductSales(List skuIds) throws Exception;

    Map getProductCN(List skuId) throws Exception;

    List getProductPicture(List skuId) throws Exception;

    List getProduct(GetProductInfoVO getProductInfoVO) throws Exception;
}
