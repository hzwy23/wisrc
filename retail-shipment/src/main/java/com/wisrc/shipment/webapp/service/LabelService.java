package com.wisrc.shipment.webapp.service;

import com.wisrc.shipment.webapp.entity.ChangeLableEnity;
import com.wisrc.shipment.webapp.utils.Result;
import com.wisrc.shipment.webapp.vo.ChangeLableVo;
import com.wisrc.shipment.webapp.vo.LabelDetailDealVo;
import com.wisrc.shipment.webapp.vo.WarehouseProductVo;

import java.util.LinkedHashMap;

public interface LabelService {

    Result insert(ChangeLableEnity changeLableEnity);

    ChangeLableVo getLabelDetail(String changeLabelId);

    LinkedHashMap findByCond(String pageNum, String pageSize, String startTime, String endTime, String wareHouseId, int statusCd, String keyword);

    WarehouseProductVo getWarehouseAndProduct(String fnsku, String warehouseId);

    void updateDetail(LabelDetailDealVo labelDetailDealVo);

    void cancelChangeLabel(String changeLabelId, String reason, String userId);

    /**
     * 更新fnsku条码文件id
     *
     * @param changeLabelId
     * @param fnskuCodeFileId
     */
    void updateFnskuCodeFileId(String changeLabelId, String fnskuCodeFileId);
}
