package com.wisrc.merchandise.service;

import com.wisrc.merchandise.entity.BasicPlatformInfoEntity;
import com.wisrc.merchandise.utils.Result;
import org.springframework.dao.DuplicateKeyException;

import java.util.LinkedHashMap;
import java.util.List;

public interface BasicPlatformInfoService {
    /**
     * 查询所有的平台信息
     *
     * @return LinkedHashMap 平台信息列表
     */
    LinkedHashMap findAll(int pageNum, int pageSize);

    /**
     * 根据平台编码和平台状态进行搜索
     *
     * @param pageNum      页码
     * @param pageSize     每页条数
     * @param platformName 平台名称
     * @param statusCd     平台状态
     */
    LinkedHashMap search(int pageNum, int pageSize, String platformName, String statusCd);


    List<BasicPlatformInfoEntity> findAll();

    /**
     * 根据平台ID号，查询平台详细信息
     *
     * @param platId 平台编码
     * @return 平台详细信息
     */
    BasicPlatformInfoEntity findById(String platId);

    /**
     * 更新平台基本信息
     *
     * @param ele 平台详细信息
     */
    void updateById(BasicPlatformInfoEntity ele) throws DuplicateKeyException;

    /**
     * 删除某一个指定的平台
     *
     * @param platId 平台基础信息
     */
    Result delete(String platId);

    /**
     * 批量删除平台信息
     *
     * @param list 平台编码列表，每一个元素，表示一个平台编码
     */
    @Deprecated
    Result deleteList(List<String> list);

    /**
     * 改变平台的状态属性
     *
     * @param platId   平台编码
     * @param statusCd 设定平台的状态，1：正常，2：禁用
     */
    Result changeStatus(String platId, int statusCd);


    /**
     * 新增平台信息
     *
     * @param ele 平台详细信息
     */
    Result add(BasicPlatformInfoEntity ele);

    /**
     * 查询平台下所有的站点
     *
     * @param platName 平台名称
     */
    List<BasicPlatformInfoEntity> findSiteById(String platName);
}
