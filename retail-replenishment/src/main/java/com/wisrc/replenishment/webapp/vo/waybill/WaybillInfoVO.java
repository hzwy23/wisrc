package com.wisrc.replenishment.webapp.vo.waybill;

import com.wisrc.replenishment.webapp.entity.WaybillExceptionInfoEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
@Api(tags = "跟踪单基本信息列表")
public class WaybillInfoVO {
    @ApiModelProperty(value = "运单编码")
    private String waybillId;
    @ApiModelProperty(value = "补货批次")
    private List<String> batchNumberList;
    @ApiModelProperty(value = "发货数量")
    private int deliveringQuantity;
    @ApiModelProperty(value = "运单下单时间")
    private Date waybillOrderDate;
    @ApiModelProperty(value = "发货仓ID")
    private String warehouseId;
    @ApiModelProperty(value = "发货仓名称")
    private String warehouseName;
    @ApiModelProperty(value = "物流商ID")
    private String shipmentId;
    @ApiModelProperty(value = "物流商名称")
    private String shipmentName;
    @ApiModelProperty(value = "物流报价单")
    private String offerId;
    @ApiModelProperty(value = "渠道名称")
    private String channelName;
    @ApiModelProperty(value = "提货方式编码")
    private int pickupTypeCd;
    @ApiModelProperty(value = "提货方式名称")
    private String pickupTypeName;
    @ApiModelProperty(value = "物流时效列表")
    private List<String> effectiveList;
    @ApiModelProperty(value = "物流单号")
    private String logisticsId;
    @ApiModelProperty(value = "报关类型")
    private int customsTypeCd;
    @ApiModelProperty(value = "签收日期")
    private Date signInDate;
    @ApiModelProperty(value = "运单物流状态")
    private int logisticsTypeCd;
    @ApiModelProperty(value = "备注信息")
    private String remark;
    @ApiModelProperty(value = "预计签收日期")
    private Date estimateDate;
    @ApiModelProperty(value = "补货数量")
    private int replenishmentNumber;
    @ApiModelProperty(value = "发货数量")
    private int deliveryNumber;
    @ApiModelProperty(value = "签收数量")
    private int signNumber;
    @ApiModelProperty(value = "亚马逊收货仓地址")
    private String amazonWarehouseAddress;
    @ApiModelProperty(value = "亚马逊收货仓邮编")
    private String amazonWarehouseZipcode;
    @ApiModelProperty(value = "收货仓编码")
    private String warehouseCode;
    @ApiModelProperty("当这个单子是调拨单对应的运单时，该字段存收货仓地址")
    private String receiveAddr;
    @ApiModelProperty(value = "物流面单(附件地址)")
    private String logisticsSurfaceUrl;
    @ApiModelProperty(value = "检测报告(附件地址)")
    private String examiningReportUrl;
    @ApiModelProperty(value = "soNoUrl")
    private String soNoUrl;
    @ApiModelProperty(value = "运单异常信息列表")
    private List<WaybillExceptionInfoEntity> exceptionList;

    @ApiModelProperty(value = "货运公司代号")
    private String logisticsTrackUrl;
    @ApiModelProperty(value = "货运公司代号编码")
    private Integer codeCd;


    @Override
    public String toString() {
        return "WaybillInfoVO{" +
                "waybillId='" + waybillId + '\'' +
                ", batchNumberList=" + batchNumberList +
                ", deliveringQuantity=" + deliveringQuantity +
                ", waybillOrderDate=" + waybillOrderDate +
                ", warehouseId='" + warehouseId + '\'' +
                ", warehouseName='" + warehouseName + '\'' +
                ", shipmentId='" + shipmentId + '\'' +
                ", shipmentName='" + shipmentName + '\'' +
                ", offerId='" + offerId + '\'' +
                ", channelName='" + channelName + '\'' +
                ", pickupTypeCd='" + pickupTypeCd + '\'' +
                ", pickupTypeName='" + pickupTypeName + '\'' +
                ", effectiveList=" + effectiveList +
                ", logisticsId='" + logisticsId + '\'' +
                ", customsTypeCd=" + customsTypeCd +
                ", signInDate=" + signInDate +
                ", logisticsTypeCd=" + logisticsTypeCd +
                ", remark='" + remark + '\'' +
                ", estimateDate=" + estimateDate +
                ", exceptionList=" + exceptionList +
                '}';
    }
}
