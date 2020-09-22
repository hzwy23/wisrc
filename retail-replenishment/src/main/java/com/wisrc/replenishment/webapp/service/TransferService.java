package com.wisrc.replenishment.webapp.service;

import com.wisrc.replenishment.webapp.utils.Result;
import com.wisrc.replenishment.webapp.vo.transferorder.AddLabelsVo;
import com.wisrc.replenishment.webapp.vo.transferorder.PackChangeInfoVo;
import com.wisrc.replenishment.webapp.vo.transferorder.TransferOrderAddVo;
import com.wisrc.replenishment.webapp.vo.transferorder.TransferWaybillAddVo;

import java.util.List;

public interface TransferService {
    /**
     * 添加调拨单
     *
     * @param transferOrderAddVo
     */
    void addTransferOrder(TransferOrderAddVo transferOrderAddVo);

    /**
     * 审核调拨单
     *
     * @param transferId
     * @param userId
     */
    Result auditTransfer(String transferId, String warehouseStartId, String warehouseEndId, String subWarehouseEndId, String userId);

    /**
     * 修改装箱规格
     *
     * @param packChangeInfoVos
     */
    void changePackInfo(List<PackChangeInfoVo> packChangeInfoVos);

    /**
     * 调拨单确认交运
     *
     * @param transferWaybillAddVo
     * @param userId
     * @return
     */
    Result confirmShipment(TransferWaybillAddVo transferWaybillAddVo, String userId);

    /**
     * 根据调拨单号去查询所有的调拨单信息
     *
     * @param transferId
     * @return
     */
    Result findById(String transferId);

    /**
     * 条件分页查询
     *
     * @param pageNum
     * @param pageSize
     * @param warehouseStartId
     * @param warehouseEndId
     * @param startDate
     * @param endDate
     * @param shipmentId
     * @param keyword
     * @param status
     * @return
     */
    Result getPageTransferInfo(Integer pageNum, Integer pageSize, String warehouseStartId, String warehouseEndId, String startDate, String endDate, String shipmentId, String keyword, Integer status) throws Exception;


    /**
     * 查询所有的状态值
     *
     * @return
     */
    Result getAllStatus();

    /**
     * 获取各种调拨单的数量
     *
     * @return
     */
    Result findCountOfStatus();

    /**
     * 取消调拨单
     *
     * @param transferOrderId
     * @param userId
     * @param cancelReason
     * @return
     */
    Result cancelTransfer(String transferOrderId, String userId, String cancelReason);

    /**
     * 为调拨单添加标签
     *
     * @param labelsVos
     * @return
     */
    Result addLabelForTransfer(List<AddLabelsVo> labelsVos);

    /**
     * 删除调拨单的标签
     *
     * @param transferId
     * @param labelId
     * @return
     */
    Result deleteLabel(String transferId, String labelId);

    /**
     * 通过运单号查询调拨单号
     *
     * @param waybillId
     * @return
     */
    String getTransferInfo(String waybillId);
}
