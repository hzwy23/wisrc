package com.wisrc.replenishment.webapp.vo.transferorder;

import com.wisrc.replenishment.webapp.entity.TransferBasicInfoEntity;
import com.wisrc.replenishment.webapp.entity.TransferLabelEntity;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 调拨单添加vo
 */
public class TransferOrderAddVo {
    @ApiModelProperty("调拨单基本信息")
    private TransferBasicInfoEntity transferBasicInfoEntity;
    @ApiModelProperty("调拨单标签信息列表")
    private List<TransferLabelEntity> transferLabelEntities;
    @ApiModelProperty("调拨商品信息列表")
    private List<TransferOrderDetailsVo> transferOrderDetailsVos;

    public TransferBasicInfoEntity getTransferBasicInfoEntity() {
        return transferBasicInfoEntity;
    }

    public void setTransferBasicInfoEntity(TransferBasicInfoEntity transferBasicInfoEntity) {
        this.transferBasicInfoEntity = transferBasicInfoEntity;
    }

    public List<TransferLabelEntity> getTransferLabelEntities() {
        return transferLabelEntities;
    }

    public void setTransferLabelEntities(List<TransferLabelEntity> transferLabelEntities) {
        this.transferLabelEntities = transferLabelEntities;
    }

    public List<TransferOrderDetailsVo> getTransferOrderDetailsVos() {
        return transferOrderDetailsVos;
    }

    public void setTransferOrderDetailsVos(List<TransferOrderDetailsVo> transferOrderDetailsVos) {
        this.transferOrderDetailsVos = transferOrderDetailsVos;
    }
}
