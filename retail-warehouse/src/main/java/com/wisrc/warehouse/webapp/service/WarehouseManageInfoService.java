package com.wisrc.warehouse.webapp.service;

import com.wisrc.warehouse.webapp.entity.WarehouseManageInfoEntity;
import com.wisrc.warehouse.webapp.entity.WarehouseSeparateDetailsInfoEntity;
import com.wisrc.warehouse.webapp.utils.Result;

import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @name hguo
 * @date 2018-5-7
 */
public interface WarehouseManageInfoService {
    /**
     * 仓库管理信息查询
     *
     * @param pageNum  页码
     * @param pageSize 每页条数
     * @param statusCd 仓库状态
     * @param typeCd   仓库类型
     * @param keyWord  关键字
     */
    LinkedHashMap findAll(int pageNum, int pageSize, int statusCd, String typeCd, String keyWord);

    /**
     * 仓库管理信息查询
     *
     * @param statusCd 仓库状态
     * @param typeCd   仓库类型
     * @param keyWord  关键字
     */
    LinkedHashMap findAll(int statusCd, String typeCd, String keyWord);

    /**
     * 仓库管理信息查询
     *
     * @param pageNum  页码
     * @param pageSize 每页条数
     */
    LinkedHashMap findAll(int pageNum, int pageSize);


    LinkedHashMap findAll();

    /**
     * 修改仓库信息
     *
     * @param ele
     */
    Result changeName(WarehouseManageInfoEntity ele);

    /**
     * 改变仓库状态
     *
     * @param warehouseId 平台编码
     * @param statusCd    设定仓库的状态，1：正常，2：禁用
     */
    void changeStatus(String warehouseId, int statusCd, String createUser, Timestamp createTime);

    /**
     * 新增仓库信息
     *
     * @param ele 仓库详细信息
     */
    Result add(WarehouseManageInfoEntity ele);

    /**
     * 通过仓库ID查询仓库信息
     *
     * @param warehouseId
     * @return
     */
    WarehouseManageInfoEntity findById(String warehouseId);

    /**
     * 通过仓库id集合查询仓库信息
     *
     * @param warehouseId
     * @return
     */
    List<WarehouseManageInfoEntity> findInfoList(String warehouseId);


    List<WarehouseSeparateDetailsInfoEntity> findSubWarehouseInfo(String warehouseId);

    /**
     * 添加分仓信息
     */
    void addSubwarehouse(WarehouseSeparateDetailsInfoEntity ele);

    /**
     * 编辑分仓信息
     */
    Result updateSubwarehouse(WarehouseSeparateDetailsInfoEntity ele);

    /**
     * 删除分仓信息
     */
    Result deleteSubwarehouse(WarehouseSeparateDetailsInfoEntity ele);

    LinkedHashMap findAllNotFba();
}
