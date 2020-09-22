package com.wisrc.replenishment.webapp.vo.waybill;

import com.wisrc.replenishment.webapp.entity.CustomsInfoEntity;
import com.wisrc.replenishment.webapp.entity.FreightEstimateinfoEntity;
import com.wisrc.replenishment.webapp.entity.LogisticsTrackInfoEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@Api(tags = "跟踪单详情信息")
public class WaybillDetailsVO {
    @ApiModelProperty("调拨单还是补货单的标识，1标识补货单，2标识调拨单")
    private Integer flag;
    @ApiModelProperty(value = "跟踪单基本信息")
    private WaybillInfoVO infoEn;
    @ApiModelProperty(value = "报关单单基本信息")
    private CustomsInfoEntity customsInfo;
    @ApiModelProperty(value = "运费估算信息")
    private FreightEstimateinfoEntity estimateInfo;
    @ApiModelProperty(value = "物流跟踪记录信息")
    private List<LogisticsTrackInfoEntity> logisticsList;
    @ApiModelProperty(value = "物流跟踪单商品详情")
    private List<WaybillMskuInfoVO> waybillMskuInfoList;
    @ApiModelProperty(value = "运单号缺少相关信息")
    private List<String> ifInfoList;
    @ApiModelProperty(value = "少发货数据")
    private int isLackShipment;
    @ApiModelProperty(value = "少物流数据")
    private int isLackLogistics;
    @ApiModelProperty(value = "少报关信息")
    private int isLackCustomsDeclare;
    @ApiModelProperty(value = "少清关信息")
    private int isLackCustomsClearance;

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public CustomsInfoEntity getCustomsInfo() {
        return customsInfo;
    }

    public void setCustomsInfo(CustomsInfoEntity customsInfo) {
        this.customsInfo = customsInfo;
    }

    public int getIsLackShipment() {
        return isLackShipment;
    }

    public void setIsLackShipment(int isLackShipment) {
        this.isLackShipment = isLackShipment;
    }

    public int getIsLackLogistics() {
        return isLackLogistics;
    }

    public void setIsLackLogistics(int isLackLogistics) {
        this.isLackLogistics = isLackLogistics;
    }

    public int getIsLackCustomsDeclare() {
        return isLackCustomsDeclare;
    }

    public void setIsLackCustomsDeclare(int isLackCustomsDeclare) {
        this.isLackCustomsDeclare = isLackCustomsDeclare;
    }

    public int getIsLackCustomsClearance() {
        return isLackCustomsClearance;
    }

    public void setIsLackCustomsClearance(int isLackCustomsClearance) {
        this.isLackCustomsClearance = isLackCustomsClearance;
    }

    public List<String> getIfInfoList() {
        return ifInfoList;
    }

    public void setIfInfoList(List<String> ifInfoList) {
        this.ifInfoList = ifInfoList;
    }

    public List<WaybillMskuInfoVO> getWaybillMskuInfoList() {
        return waybillMskuInfoList;
    }

    public void setWaybillMskuInfoList(List<WaybillMskuInfoVO> waybillMskuInfoList) {
        this.waybillMskuInfoList = waybillMskuInfoList;
    }

    public WaybillInfoVO getInfoEn() {
        return infoEn;
    }

    public void setInfoEn(WaybillInfoVO infoEn) {
        this.infoEn = infoEn;
    }

    public FreightEstimateinfoEntity getEstimateInfo() {
        return estimateInfo;
    }

    public void setEstimateInfo(FreightEstimateinfoEntity estimateInfo) {
        this.estimateInfo = estimateInfo;
    }

    public List<LogisticsTrackInfoEntity> getLogisticsList() {
        return logisticsList;
    }

    public void setLogisticsList(List<LogisticsTrackInfoEntity> logisticsList) {
        this.logisticsList = logisticsList;
    }

}
