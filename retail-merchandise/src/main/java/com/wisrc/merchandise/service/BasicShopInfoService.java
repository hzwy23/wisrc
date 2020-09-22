package com.wisrc.merchandise.service;

import com.wisrc.merchandise.entity.BasicShopInfoEntity;
import com.wisrc.merchandise.utils.Result;

import java.util.LinkedHashMap;
import java.util.List;

public interface BasicShopInfoService {
    /**
     * 查询所有的店铺信息
     *
     * @return List<ShopInfoVO> 返回店铺信息列表
     */
    LinkedHashMap findAll(int startPage, int pageSize);


    LinkedHashMap findAll();


    LinkedHashMap searchAndPage(String platformName, String shopId, String shopName, String statusCd, int pageNum, int pageSize);


    LinkedHashMap search(String platformName, String shopId, String shopName, String statusCd);


    /**
     * 查询店铺信息
     *
     * @param shopId 店铺编码
     */
    BasicShopInfoEntity findById(String shopId);

    /**
     * 删除店铺信息
     *
     * @param shopId 店铺编码
     */
    void delete(String shopId);

    /**
     * 批量删除店铺信息
     *
     * @param list 店铺信息列表，每个元素，都是一个店铺编码
     */
    void deleteList(List<String> list);

    /**
     * 更新店铺信息
     *
     * @param ele 店铺信息
     */
    void update(BasicShopInfoEntity ele);

    /**
     * 更改店铺状态
     *
     * @param shopId   店铺编码
     * @param statusCd 状态吗
     */
    void changeStatus(String shopId, int statusCd);


    /**
     * 新增店铺信息
     *
     * @param ele 店铺基础信息
     */
    Result add(BasicShopInfoEntity ele);

    String getWarehouseByShop(String shopId);
}
