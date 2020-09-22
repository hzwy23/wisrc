package com.wisrc.warehouse.webapp.vo;

import com.wisrc.warehouse.webapp.entity.WarehouseManageInfoEntity;
import com.wisrc.warehouse.webapp.entity.WarehouseSeparateDetailsInfoEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel
public class WarehouseManageInfoVO {
    @ApiModelProperty(value = "仓库ID", required = false, hidden = true)
    private String warehouseId;
    @ApiModelProperty(value = "仓库名称", position = 1)
    @NotEmpty(message = "仓库名称不能为空")
    private String warehouseName;

    @ApiModelProperty(value = "仓库类型(A--本地仓，B--海外仓，C--虚拟仓, F--FBA仓)", position = 2)
    @NotNull(message = "仓库类型不能为空")
    private String typeCd;

    @ApiModelProperty(value = "国家简称")
    private String countryCd;
    @ApiModelProperty(value = "省份名称")
    private String provinceName;

    @ApiModelProperty(value = "城市名称")
    private String cityName;

    @ApiModelProperty(value = "街道详细地址")
    private String detailsAddr;

    @ApiModelProperty(value = "邮编")
    private String zipCode;

    @ApiModelProperty(value = "仓库联系人")
    private String warehouseContact;

    @ApiModelProperty(value = "仓库联系电话")
    private String warehousePhone;

    @ApiModelProperty(value = "备注信息")
    private String remark;

    @ApiModelProperty(value = "是否分仓，0：未分仓，1：分仓")
    private int subWarehouseSupport;

    @ApiModelProperty(value = "分仓列表信息")
    private List<WarehouseSeparateDetailsInfoEntity> subWarehouseList;

    public static final WarehouseManageInfoVO toVO(WarehouseManageInfoEntity ele) {
        WarehouseManageInfoVO vo = new WarehouseManageInfoVO();
        BeanUtils.copyProperties(ele, vo);
        return vo;
    }

    public List<WarehouseSeparateDetailsInfoEntity> getSubWarehouseList() {
        return subWarehouseList;
    }

    public void setSubWarehouseList(List<WarehouseSeparateDetailsInfoEntity> subWarehouseList) {
        this.subWarehouseList = subWarehouseList;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getCountryCd() {
        return countryCd;
    }

    public void setCountryCd(String countryCd) {
        this.countryCd = countryCd;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDetailsAddr() {
        return detailsAddr;
    }

    public void setDetailsAddr(String detailsAddr) {
        this.detailsAddr = detailsAddr;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getWarehouseContact() {
        return warehouseContact;
    }

    public void setWarehouseContact(String warehouseContact) {
        this.warehouseContact = warehouseContact;
    }

    public String getWarehousePhone() {
        return warehousePhone;
    }

    public void setWarehousePhone(String warehousePhone) {
        this.warehousePhone = warehousePhone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getSubWarehouseSupport() {
        return subWarehouseSupport;
    }

    public void setSubWarehouseSupport(int subWarehouseSupport) {
        this.subWarehouseSupport = subWarehouseSupport;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getTypeCd() {
        return typeCd;
    }

    public void setTypeCd(String typeCd) {
        this.typeCd = typeCd;
    }

    @Override
    public String toString() {
        return "WarehouseManageInfoVO{" +
                "countryCd='" + countryCd + '\'' +
                ", provinceName='" + provinceName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", detailsAddr='" + detailsAddr + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", warehouseContact='" + warehouseContact + '\'' +
                ", warehousePhone='" + warehousePhone + '\'' +
                ", remark='" + remark + '\'' +
                ", subWarehouseSupport=" + subWarehouseSupport +
                ", warehouseName='" + warehouseName + '\'' +
                ", typeCd='" + typeCd + '\'' +
                '}';
    }
}
