package com.wisrc.replenishment.webapp.vo.waybill;

import com.wisrc.replenishment.webapp.entity.FreightEstimateinfoEntity;
import com.wisrc.replenishment.webapp.entity.WaybillRelEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Date;
import java.util.List;

@Api(tags = "新增物流跟踪单信息字段说明")
public class AddWaybillVO {
    @ApiModelProperty(value = "运单下单时间", position = 1)
    private Date waybillOrderDate;
    @ApiModelProperty(value = "备注信息", position = 2)
    private String remark;
    @ApiModelProperty(value = "预计签收日期", position = 3)
    private Date estimateDate;
    @ApiModelProperty(value = "物流报价单", position = 4)
    private String offerId;
    @ApiModelProperty(value = "物流跟踪单下的补货单集合", position = 5)
    private List<WaybillRelEntity> waybillRelEntityList;
    @ApiModelProperty(value = "运费估算信息", position = 6)
    private FreightEstimateinfoEntity freightEstimateinfoEntity;

    public List<WaybillRelEntity> getWaybillRelEntityList() {
        return waybillRelEntityList;
    }

    public void setWaybillRelEntityList(List<WaybillRelEntity> waybillRelEntityList) {
        this.waybillRelEntityList = waybillRelEntityList;
    }

    public Date getWaybillOrderDate() {
        return waybillOrderDate;
    }

    public void setWaybillOrderDate(Date waybillOrderDate) {
        this.waybillOrderDate = waybillOrderDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getEstimateDate() {
        return estimateDate;
    }

    public void setEstimateDate(Date estimateDate) {
        this.estimateDate = estimateDate;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public FreightEstimateinfoEntity getFreightEstimateinfoEntity() {
        return freightEstimateinfoEntity;
    }

    public void setFreightEstimateinfoEntity(FreightEstimateinfoEntity freightEstimateinfoEntity) {
        this.freightEstimateinfoEntity = freightEstimateinfoEntity;
    }


    @Override
    public String toString() {
        return "AddWaybillVO{" +
                "waybillRelEntityList=" + waybillRelEntityList +
                ", waybillOrderDate=" + waybillOrderDate +
                ", remark='" + remark + '\'' +
                ", estimateDate=" + estimateDate +
                ", offerId='" + offerId + '\'' +
                ", freightEstimateinfoEntity=" + freightEstimateinfoEntity +
                '}';
    }
}
