package com.wisrc.replenishment.webapp.vo.transferorder;

import com.wisrc.replenishment.webapp.entity.TransferBasicInfoEntity;
import com.wisrc.replenishment.webapp.entity.TransferLabelEntity;
import com.wisrc.replenishment.webapp.vo.waybill.LogisticsTrackInfoVO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Objects;

public class TransferInfoReturnVo {
    @ApiModelProperty("起运仓名称")
    private String warehouseStartName;
    @ApiModelProperty("目的仓名称")
    private String warehouseEndName;
    @ApiModelProperty("调拨单基本信息")
    private TransferBasicInfoEntity transferBasicInfoEntity;
    @ApiModelProperty("调拨单产品明细信息")
    private List<TransferOrderDetailsVo> transferOrderDetailsVos;
    @ApiModelProperty("调拨单标签信息")
    private List<TransferLabelEntity> transferLabelEntities;
    @ApiModelProperty("调拨单的物流信息")
    private LogisticsTrackInfoVO logisticsTrackInfoVO;

    public String getWarehouseEndName() {
        return warehouseEndName;
    }

    public void setWarehouseEndName(String warehouseEndName) {
        this.warehouseEndName = warehouseEndName;
    }

    public String getWarehouseStartName() {

        return warehouseStartName;
    }

    public void setWarehouseStartName(String warehouseStartName) {
        this.warehouseStartName = warehouseStartName;
    }

    public TransferBasicInfoEntity getTransferBasicInfoEntity() {
        return transferBasicInfoEntity;
    }

    public void setTransferBasicInfoEntity(TransferBasicInfoEntity transferBasicInfoEntity) {
        this.transferBasicInfoEntity = transferBasicInfoEntity;
    }

    public List<TransferOrderDetailsVo> getTransferOrderDetailsVos() {
        return transferOrderDetailsVos;
    }

    public void setTransferOrderDetailsVos(List<TransferOrderDetailsVo> transferOrderDetailsVos) {
        this.transferOrderDetailsVos = transferOrderDetailsVos;
    }

    public List<TransferLabelEntity> getTransferLabelEntities() {
        return transferLabelEntities;
    }

    public void setTransferLabelEntities(List<TransferLabelEntity> transferLabelEntities) {
        this.transferLabelEntities = transferLabelEntities;
    }

    public LogisticsTrackInfoVO getLogisticsTrackInfoVO() {
        return logisticsTrackInfoVO;
    }

    public void setLogisticsTrackInfoVO(LogisticsTrackInfoVO logisticsTrackInfoVO) {
        this.logisticsTrackInfoVO = logisticsTrackInfoVO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransferInfoReturnVo that = (TransferInfoReturnVo) o;
        return Objects.equals(warehouseStartName, that.warehouseStartName) &&
                Objects.equals(warehouseEndName, that.warehouseEndName) &&
                Objects.equals(transferBasicInfoEntity, that.transferBasicInfoEntity) &&
                Objects.equals(transferOrderDetailsVos, that.transferOrderDetailsVos) &&
                Objects.equals(transferLabelEntities, that.transferLabelEntities) &&
                Objects.equals(logisticsTrackInfoVO, that.logisticsTrackInfoVO);
    }

    @Override
    public int hashCode() {

        return Objects.hash(warehouseStartName, warehouseEndName, transferBasicInfoEntity, transferOrderDetailsVos, transferLabelEntities, logisticsTrackInfoVO);
    }
}
