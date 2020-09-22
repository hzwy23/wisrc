package com.wisrc.sales.webapp.enity;

import com.wisrc.sales.webapp.vo.RemarkVo;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import java.util.Date;

@Data
public class EstimateDetailEnity {
    @ApiModelProperty(value = "销售预估date类型时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date estimateDate;
    @ApiModelProperty(value = "销售预估数量")
    @Min(value = 0, message = "数量必须为正整数")
    private int estimateNumber;
    @ApiModelProperty(value = "销售预估外键id", required = false, hidden = true)
    private String estimateId;
    @ApiModelProperty(value = "销售预估详细主键id", required = false, hidden = true)
    private String uuid;
    @ApiModelProperty(value = "销售预估String类型时间", required = false, hidden = true)
    private String estimateDatailDate;
    @ApiModelProperty(value = "备注列表", required = false, hidden = true)
    private List<RemarkVo> remarkList;

    @ApiModelProperty(value = "更新标识", required = false, hidden = true)
    private Integer updateFlag;
    // 生效日期
    private Timestamp effectiveDate;
    // 时效日期
    private Timestamp expirationDate;


    public int getEstimateNumber() {
        return estimateNumber;
    }

    public void setEstimateNumber(int estimateNumber) {
        this.estimateNumber = estimateNumber;
    }

    public String getEstimateId() {
        return estimateId;
    }

    public void setEstimateId(String estimateId) {
        this.estimateId = estimateId;
    }

    public String getEstimateDatailDate() {
        return estimateDatailDate;
    }

    public void setEstimateDatailDate(String estimateDatailDate) {
        this.estimateDatailDate = estimateDatailDate;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getEstimateDate() {
        return estimateDate;
    }

    public void setEstimateDate(Date estimateDate) {
        this.estimateDate = estimateDate;
    }

    public List<RemarkVo> getRemarkList() {
        return remarkList;
    }

    public void setRemarkList(List<RemarkVo> remarkList) {
        this.remarkList = remarkList;
    }
}
