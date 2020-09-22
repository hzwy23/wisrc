package com.wisrc.product.webapp.service;


import com.wisrc.product.webapp.entity.ProductDefineEntity;
import com.wisrc.product.webapp.utils.Result;
import com.wisrc.product.webapp.vo.GetProductInfoVO;
import com.wisrc.product.webapp.vo.productDefine.SetBatchCostPriceVO;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

public interface ProductDefineService {

    /**
     * 查询所有的产品信息
     */
    Result findAll();



    /**
     * 删除产品定义信息
     */
    void delete(String skuId);

    /**
     * 禁用或启动产品
     */
    void changeStatus(String skuId, int statusCd);

    /**
     * 查询某个SKU详细信息
     */
    ProductDefineEntity findBySkuId(String skuId);

    /**
     * 新增产品定义信息
     */
    Map<String, String> insert(ProductDefineEntity ele, String classifyShortName);

    Map<String, String> getName(String m);

    void update(ProductDefineEntity productDefineEntity, String time, String userId) throws Exception;

    List<String> getAllSKU();

    Map<String, Object> fuzzyQueryNew(Integer pageNum, Integer pageSize, GetProductInfoVO getProductInfoVO, List<String> classifyCdList);

    Result updateCostPrice(@Valid @RequestBody SetBatchCostPriceVO vo, BindingResult bindingResult);

    Result findPackingMaterial();

    Result getDefineBatch(List<String> skuIds);

    void skuExcel (GetProductInfoVO getProductInfoVO,
                   List<String> classifyCdList,
                   HttpServletResponse response,
                   HttpServletRequest request);
}
