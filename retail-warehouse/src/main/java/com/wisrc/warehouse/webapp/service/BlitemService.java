package com.wisrc.warehouse.webapp.service;

import com.wisrc.warehouse.webapp.entity.BlitemInfoEntity;
import com.wisrc.warehouse.webapp.utils.Result;
import com.wisrc.warehouse.webapp.vo.BlitemListVO;

import java.util.List;


public interface BlitemService {

    /**
     * p盘点单查询
     *
     * @param pageNum
     * @param pageSize
     * @param blitemId
     * @param warehouseId
     * @param skuId
     * @param skuName
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    Result getList(Integer pageNum, Integer pageSize, String blitemId, String warehouseId, String skuId, String skuName, String startDate, String endDate) throws Exception;

    /**
     * 添加盘点单
     *
     * @param blitemInfoEntity
     * @param blitemListInfoEntities
     * @param remark
     * @return
     */
    Result save(BlitemInfoEntity blitemInfoEntity, List<BlitemListVO> blitemListInfoEntities, String remark, String userId);

    Result getBlitemInfoVoByBlitemId(String blitemId);

    Result updateBlitemStatus(String blitemId, String statusCd, String remark, String userId);
}
