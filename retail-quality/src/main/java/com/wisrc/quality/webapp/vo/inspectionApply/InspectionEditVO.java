package com.wisrc.quality.webapp.vo.inspectionApply;

import com.wisrc.quality.webapp.entity.InspectionApplyInfoEntity;
import com.wisrc.quality.webapp.utils.DateUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class InspectionEditVO {

    @ApiModelProperty(value = "验货申请单号")
    @NotEmpty
    private String inspectionId;
    @ApiModelProperty(value = "预计验货日期")
    private String expectInspectionTime;
    @ApiModelProperty(value = "供应商联系人")
    private String supplierContactUser;
    @ApiModelProperty(value = "供应商电话")
    private String supplierPhone;
    @ApiModelProperty(value = "供应商地址")
    private String supplierAddr;
    @ApiModelProperty(value = "备注")
    private String remark;

    @Valid
    private List<InspectionProdEditVO> prodList;

    public String getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(String inspectionId) {
        this.inspectionId = inspectionId;
    }

    public String getSupplierContactUser() {
        return supplierContactUser;
    }

    public void setSupplierContactUser(String supplierContactUser) {
        this.supplierContactUser = supplierContactUser;
    }

    public String getSupplierPhone() {
        return supplierPhone;
    }

    public void setSupplierPhone(String supplierPhone) {
        this.supplierPhone = supplierPhone;
    }

    public String getSupplierAddr() {
        return supplierAddr;
    }

    public void setSupplierAddr(String supplierAddr) {
        this.supplierAddr = supplierAddr;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<InspectionProdEditVO> getProdList() {
        return prodList;
    }

    public void setProdList(List<InspectionProdEditVO> prodList) {
        this.prodList = prodList;
    }

    public void toEntity(InspectionApplyInfoEntity applyInfo, InspectionEditVO editVO) {
        applyInfo.setInspectionId(editVO.getInspectionId());
        if (editVO.getExpectInspectionTime() != null) {
            applyInfo.setExpectInspectionTime(new java.sql.Date(DateUtil.convertStrToDate(editVO.getExpectInspectionTime(), DateUtil.DEFAULT_SHORT_DATE_FORMAT).getTime()));
        }
        applyInfo.setRemark(editVO.getRemark());
        applyInfo.setSupplierAddr(editVO.getSupplierAddr());
        applyInfo.setSupplierContactUser(editVO.getSupplierContactUser());
        applyInfo.setSupplierPhone(editVO.getSupplierPhone());

    }
}
