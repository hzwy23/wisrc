package com.wisrc.quality.webapp.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 产品检验单信息
 */
@Data
public class ProductInspectionInfoEntity {
    private String inspectionCd;// 产品检验单号
    private Integer inspectionMethodCd; // 验货方式编码

    private Integer inspectionStatusCd; //   检验状态编码
    private Date inspectionDate;  //日期

    private Integer inspectionTypeCd; //   检验类型编码
    private Integer testTimes; //   第几次检验
    private Integer inspectionLevelCd; //   检验水平编码
    private Integer samplingPlanCd; //    抽样方案编码
    private Integer applyInspectionQuantity; //   申请检验数量
    private Integer actualInspectionQuantity; //   实际检验数量
    private Integer changeReasonCd; //    更改理由编码
    private Integer sampleQuantity; //   抽样数
    private Integer conclusionCd; //   结论编码
    private Integer finalDetermineCd; //最终判定编码
    private Integer qualifiedQuantity; //    合格数量
    private Integer unqualifiedQuantity; //   不合格数量
    private String annexAddress;//     附件地址
    private String annexName; // 附件名称
    private Integer qualityConsumption; //   品质消耗数
    private String remark; //    备注
    private Integer sourceTypeCd; //    单据来源类型
    private String purchaseOrderId;//   采购订单号
    private String sourceDocumentCd; //    来源单据编码
    private String skuId; //    库存sku
    private String createUser; //
    private String createTime; //
    private String updateTime; //
    private Integer deleteStatus; //   删除标记

    //=========
    //外箱长度
    private Double cartonLength;
    //外箱宽度
    private Double cartonWidth;
    //外箱高度
    private Double cartonHeight;
    //毛重
    private Double crossWeight;
    //=============

    //================
    @ApiModelProperty(value = "到货通知单唯一编码（数据来源为到货通知单时，该参数为必填参数）")
    private String arrivalProductId;
    //================

}
