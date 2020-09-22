package com.wisrc.basic.service;

import com.wisrc.basic.entity.ShipmentEnterpriseEntity;
import com.wisrc.basic.entity.ShipmentStatusAttrEntity;
import com.wisrc.basic.entity.ShipmentTypeAttrEntity;
import com.wisrc.basic.utils.Result;

import java.util.LinkedHashMap;
import java.util.List;

public interface ShipmentEnterpriseService {
    /**
     * 更改物流商状态
     *
     * @param shipmentId 物流商编码
     * @param statusCd   状态吗
     */
    void changeStatus(String shipmentId, int statusCd, String userId);

    /**
     * 新增物流商信息
     *
     * @param ele 物流商信息
     */
    void add(ShipmentEnterpriseEntity ele);

    /**
     * 查询物流商信息
     * * @param pageNum  页码
     *
     * @param pageSize 每页条数
     * @param statusCd 物流商状态
     * @param keyword  模糊查询关键字
     */
    LinkedHashMap findByCond(int pageNum, int pageSize, String statusCd, String keyword);

    /**
     * 查询物流商信息
     * * @param pageNum  页码
     *
     * @param pageSize 每页条数
     */
    LinkedHashMap findByPage(int pageNum, int pageSize);

    /**
     * 修改物流商信息
     *
     * @param ele 物流商信息
     */
    void updateById(ShipmentEnterpriseEntity ele);

    /**
     * 物流商状态码表信息
     *
     * @param statusCd 状态ID
     */
    List<ShipmentStatusAttrEntity> findStatusAttr(int statusCd);

    /**
     * 物流商类型码表信息
     *
     * @param shipmentType 类型ID
     */
    List<ShipmentTypeAttrEntity> findTypeAttr(int shipmentType);

    /**
     * 根据物流商ID查询物流商详细信息
     *
     * @param id 类型ID
     */
    ShipmentEnterpriseEntity findShipmentById(String id);

    List<ShipmentEnterpriseEntity> findByListId(String idList);

    LinkedHashMap findAll(String statusCd, String keyword);

    LinkedHashMap findByName(int statusCd, String shipmentName, String contact);

    LinkedHashMap findAllEnableShipment();

    Result shipmentCodeList();
}
