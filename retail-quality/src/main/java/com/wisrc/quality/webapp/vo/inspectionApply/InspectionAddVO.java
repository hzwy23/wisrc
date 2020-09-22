package com.wisrc.quality.webapp.vo.inspectionApply;

import com.wisrc.quality.webapp.entity.InspectionApplyInfoEntity;
import com.wisrc.quality.webapp.utils.DateUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class InspectionAddVO {

    @ApiModelProperty(value = "申请人id")
    @NotEmpty(message = "申请人id不能为空")
    private String employeeId;
    @ApiModelProperty(value = "采购订单id")
    @NotEmpty(message = "采购订单id不能为空")
    private String orderId;
    @ApiModelProperty(value = "申请日期")
    private String applyDate;
    @ApiModelProperty(value = "预计验货日期")
    @NotEmpty(message = "预计验货日期不能为空")
    private String expectInspectionTime;
    @ApiModelProperty(value = "供应商编码")
    private String supplierId;
    @ApiModelProperty(value = "供应商联系人")
    private String supplierContactUser;
    @ApiModelProperty(value = "供应商电话")
    private String supplierPhone;
    @ApiModelProperty(value = "供应商地址")
    private String supplierAddr;
    @ApiModelProperty(value = "验货方式编码")
    private Integer inspectionTypeCd;
    @ApiModelProperty(value = "备注")
    private String remark;

    @Valid
    private List<InspectionProdAddVO> prodList;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
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

    public Integer getInspectionTypeCd() {
        return inspectionTypeCd;
    }

    public void setInspectionTypeCd(Integer inspectionTypeCd) {
        this.inspectionTypeCd = inspectionTypeCd;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<InspectionProdAddVO> getProdList() {
        return prodList;
    }

    public void setProdList(List<InspectionProdAddVO> prodList) {
        this.prodList = prodList;
    }

    public void toEntity(InspectionApplyInfoEntity applyInfo, InspectionAddVO addVO) {
        if (addVO.getApplyDate() != null) {
            applyInfo.setApplyDate(new java.sql.Date(DateUtil.convertStrToDate(addVO.getApplyDate(), DateUtil.DEFAULT_SHORT_DATE_FORMAT).getTime()));
        }
        applyInfo.setEmployeeId(addVO.getEmployeeId());
        if (addVO.getExpectInspectionTime() != null) {
            applyInfo.setExpectInspectionTime(new java.sql.Date(DateUtil.convertStrToDate(addVO.getExpectInspectionTime(), DateUtil.DEFAULT_SHORT_DATE_FORMAT).getTime()));
        }
        applyInfo.setOrderId(addVO.getOrderId());
        if (addVO.getInspectionTypeCd() == null || addVO.getInspectionTypeCd() == 0) {
            applyInfo.setInspectionTypeCd(null);
        } else {
            applyInfo.setInspectionTypeCd(addVO.getInspectionTypeCd());
        }
        applyInfo.setRemark(addVO.getRemark());
        applyInfo.setSupplierAddr(addVO.getSupplierAddr());
        applyInfo.setSupplierId(addVO.getSupplierId());
        applyInfo.setSupplierContactUser(addVO.getSupplierContactUser());
        applyInfo.setSupplierPhone(addVO.getSupplierPhone());

    }
}
