package com.wisrc.quality.webapp.vo.productInspectionInfo.add;

import com.wisrc.quality.webapp.vo.inspectionItemsInfoVO.add.AddInspectionItemsInfoVO;
import com.wisrc.quality.webapp.vo.inspectionPersonnelInfo.add.AddInspectionPersonnelInfoVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel
public class AddProductInspectionInfoVO {

    @NotNull(message = "验货方式不能为空")
    @ApiModelProperty(value = "验货方式", required = true)
    private Integer inspectionMethodCd;

    @NotNull(message = "检验状态不能为空")
    @ApiModelProperty(value = "状态", required = true)
    private Integer inspectionStatusCd;

    @NotBlank(message = "日期不能为空")
    @ApiModelProperty(value = "日期", required = true)
    private String inspectionDate;

    @NotBlank(message = "采购订单号不能为空")
    @ApiModelProperty(value = "采购订单号", required = true)
    private String purchaseOrderId;

    @NotNull(message = "检验类型不能为空")
    @ApiModelProperty(value = "检验类型编码", required = true)
    private Integer inspectionTypeCd;

    @NotNull(message = "检验次数不能为空")
    @Range(min = 1, message = "检验次数应该为大于0的整数")
    @ApiModelProperty(value = "检验次数", required = true)
    private Integer testTimes;

    @ApiModelProperty(value = "检验水平编码")
    private Integer inspectionLevelCd;

    @ApiModelProperty(value = "抽样方案编码")
    private Integer samplingPlanCd;

    @NotNull(message = "申请检验数量不能为空")
    @ApiModelProperty(value = "申请检验数量", required = true)
    private Integer applyInspectionQuantity;

    @NotNull(message = "实际检验数量不能为空")
    @ApiModelProperty(value = "实际检验数量", required = true)
    private Integer actualInspectionQuantity;

    @Range(min = 1, max = 2, message = "更改理由参数不合法")
    @ApiModelProperty(value = "更改理由编码")
    private Integer changeReasonCd;

    @NotNull(message = "抽样数参数不能为空")
    @ApiModelProperty(value = "抽样数", required = true)
    private Integer sampleQuantity;

    @ApiModelProperty(value = "结论编码")
    private Integer conclusionCd;

    @ApiModelProperty(value = "最终判定编码")
    private Integer finalDetermineCd;

    @ApiModelProperty(value = "合格数量")
    private Integer qualifiedQuantity;

    @ApiModelProperty(value = "不合格数量")
    private Integer unqualifiedQuantity;

    @ApiModelProperty(value = "附件地址")
    private String annexAddress;

    @ApiModelProperty(value = "附件名称")
    private String annexName;

    @ApiModelProperty(value = "品质消耗数")
    private Integer qualityConsumption;

    @ApiModelProperty(value = "备注")
    private String remark;

    @NotNull(message = "单据来源类型不能为空")
    @ApiModelProperty(value = "单据来源类型", required = true)
    private Integer sourceTypeCd;

    @NotNull(message = "来源单据不能为空")
    @ApiModelProperty(value = "来源单编码", required = true)
    private String sourceDocumentCd;

    @NotEmpty(message = "库存sku不能为空")
    @ApiModelProperty(value = "库存sku", required = true)
    private String skuId;


    @Valid
    @NotEmpty(message = "检验人员不能为空")
    @ApiModelProperty(value = "检验人员", required = true)
    private List<AddInspectionPersonnelInfoVO> inspectionPersonList;

    @Valid
    @NotEmpty(message = "检验记录不能为空")
    @ApiModelProperty(value = "检验记录", required = true)
    private List<AddInspectionItemsInfoVO> inspectionItemsList;

    //==========
    @ApiModelProperty(value = "外箱长度")
    private Double cartonLength;
    @ApiModelProperty(value = "外箱宽度度")
    private Double cartonWidth;
    @ApiModelProperty(value = "外箱高度")
    private Double cartonHeight;
    @ApiModelProperty(value = "毛重")
    private Double crossWeight;
    //==========

    //================
    @ApiModelProperty(value = "到货通知单唯一编码（数据来源为到货通知单时，该参数为必填参数）")
    private String arrivalProductId;
    //================
}
