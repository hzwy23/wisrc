package com.wisrc.basic.vo;

import com.wisrc.basic.entity.CompanyBasicInfoEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotEmpty;

@Data
public class CompanyBasicInfoVO {
    @ApiModelProperty(value = "公司编号（Api测试新增默认输入1）", required = true)
    @NotEmpty
    private String companyOnlyId;
    @ApiModelProperty(value = "公司名称", required = true)
    @NotEmpty
    private String companyName;
    @ApiModelProperty(value = "公司英文名", required = true)
    @NotEmpty
    private String companyNameEn;
    @ApiModelProperty(value = "公司香港名", required = true)
    @NotEmpty
    private String companyNameHk;
    @ApiModelProperty(value = "公司地址", required = true)
    @NotEmpty
    private String companyAddress;
    @ApiModelProperty(value = "公司英文地址", required = true)
    @NotEmpty
    private String companyAddressEn;
    @ApiModelProperty(value = "公司香港地址", required = true)
    @NotEmpty
    private String companyAddressHk;
    @ApiModelProperty(value = "公司固话", required = true)
    @NotEmpty
    private String companyTelephone;
    @ApiModelProperty(value = "公司传真", required = false)
    private String companyFax;
    @ApiModelProperty(value = "纳税人识别号")
    @NotEmpty
    private String taxpayerIdentificationNumber;

    public static final CompanyBasicInfoVO toVO(CompanyBasicInfoEntity ele) {
        CompanyBasicInfoVO vo = new CompanyBasicInfoVO();
        BeanUtils.copyProperties(ele, vo);
        return vo;
    }

    public String getCompanyOnlyId() {
        return companyOnlyId;
    }

    public void setCompanyOnlyId(String companyOnlyId) {
        this.companyOnlyId = companyOnlyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyNameEn() {
        return companyNameEn;
    }

    public void setCompanyNameEn(String companyNameEn) {
        this.companyNameEn = companyNameEn;
    }

    public String getCompanyNameHk() {
        return companyNameHk;
    }

    public void setCompanyNameHk(String companyNameHk) {
        this.companyNameHk = companyNameHk;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyAddressEn() {
        return companyAddressEn;
    }

    public void setCompanyAddressEn(String companyAddressEn) {
        this.companyAddressEn = companyAddressEn;
    }

    public String getCompanyAddressHk() {
        return companyAddressHk;
    }

    public void setCompanyAddressHk(String companyAddressHk) {
        this.companyAddressHk = companyAddressHk;
    }

    public String getCompanyTelephone() {
        return companyTelephone;
    }

    public void setCompanyTelephone(String companyTelephone) {
        this.companyTelephone = companyTelephone;
    }

    public String getCompanyFax() {
        return companyFax;
    }

    public void setCompanyFax(String companyFax) {
        this.companyFax = companyFax;
    }

    @Override
    public String toString() {
        return "CompanyBasicInfoVO{" +
                "companyOnlyId='" + companyOnlyId + '\'' +
                ", companyName='" + companyName + '\'' +
                ", companyNameEn='" + companyNameEn + '\'' +
                ", companyNameHk='" + companyNameHk + '\'' +
                ", companyAddress='" + companyAddress + '\'' +
                ", companyAddressEn='" + companyAddressEn + '\'' +
                ", companyAddressHk='" + companyAddressHk + '\'' +
                ", companyTelephone='" + companyTelephone + '\'' +
                ", companyFax='" + companyFax + '\'' +
                '}';
    }
}
