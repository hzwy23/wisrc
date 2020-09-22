package com.wisrc.replenishment.webapp.service;

import com.wisrc.replenishment.webapp.vo.wms.TransferOrderPackBasicVO;
import com.wisrc.replenishment.webapp.vo.wms.TransferOutBasicVO;

public interface WmsReturnService {

    /**
     * 调拨单装箱数据回写
     *
     * @param transferOrderPackBasicVO
     */
    void transferPackInfoReturn(TransferOrderPackBasicVO transferOrderPackBasicVO);

    /**
     * 调拨单出库回写
     *
     * @param transferOutBasicVO
     */
    void transferOutReturn(TransferOutBasicVO transferOutBasicVO);
}
