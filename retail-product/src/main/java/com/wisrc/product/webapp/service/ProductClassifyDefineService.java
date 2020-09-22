package com.wisrc.product.webapp.service;

import com.wisrc.product.webapp.entity.ProductClassifyDefineEntity;
import com.wisrc.product.webapp.utils.Result;
import com.wisrc.product.webapp.vo.FuzzyProductClassifyDefineVO;

import java.util.List;
import java.util.Map;

/**
 * 产品分类信息服务类
 *
 * @author zhanwei_huang
 * @since 0.0.1
 */
public interface ProductClassifyDefineService {
    /**
     * 查询搜有的产品分类信息
     */
    List<ProductClassifyDefineEntity> findAll();

    /**
     * 查看指定茶品分类这个节点下所有的产品分类信息
     */
    List<ProductClassifyDefineEntity> findPosterity(String classifyCd);

    /**
     * 更新产品分类信息
     */
    Map<String, String> update(ProductClassifyDefineEntity ele);

    /**
     * 新增产品分类信息
     */
    Map<String, String> insert(ProductClassifyDefineEntity ele);

    /**
     * 删除指定产品分类信息，删除这个节点的同时，也会将这个节点下边的所有产品分类信息删除
     *
     * @param classifyCd 产品分类编码
     */
    void delete(String classifyCd);

    ProductClassifyDefineEntity getRootClassify(String classifyCd);

    Map findByClassifyCd(String classifyCd);

    List<ProductClassifyDefineEntity> findLevel(int levelCd);

    Map<String, String> deleteSafe(String classifyCd);

    Map<String, Object> findById(String classifyCd);

    Map<String, Object> fuzzyQueryFamilyNew(int pageNum, int pageSize, FuzzyProductClassifyDefineVO ele);

    Map<String, String> updatePart(ProductClassifyDefineEntity productClassifyDefineEntity);

    Result findItselfAndAncestor(String classifyCd);
}
