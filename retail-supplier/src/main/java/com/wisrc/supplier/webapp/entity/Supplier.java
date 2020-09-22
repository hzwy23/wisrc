package com.wisrc.supplier.webapp.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 供应商基础
 *
 * @author linguosheng
 */
@ApiModel(value = "供应商")
public class Supplier {

    @ApiModelProperty(name = "supplierId", value = "供应商编号")
    private String supplierId;

    @ApiModelProperty(name = "supplierName", value = "供应商名称")
    private String supplierName;

    @ApiModelProperty(name = "contacts", value = "联系人")
    private String contacts;

    @ApiModelProperty(name = "telephone", value = "固定电话")
    private String telephone;

    @ApiModelProperty(name = "status", value = "状态：0失效 1正常")
    private Integer status;

    @ApiModelProperty(hidden = true)
    private String mender;

    @ApiModelProperty(hidden = true)
    private Date updateTime;

    @ApiModelProperty(hidden = true)
    private Date createTime;
    @Valid
    private SupplierInfo supplierInfo = new SupplierInfo();
    @Valid
    private SupplierAnnex supplierVoucher = new SupplierAnnex();

    private List<SupplierAnnex> supplierEnclosure = new ArrayList<>();

    private List<SupplierAccount> supplierAccounts = new ArrayList<>();

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMender() {
        return mender;
    }

    public void setMender(String mender) {
        this.mender = mender;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public SupplierInfo getSupplierInfo() {
        return supplierInfo;
    }

    public void setSupplierInfo(SupplierInfo supplierInfo) {
        this.supplierInfo = supplierInfo;
    }

    public SupplierAnnex getSupplierVoucher() {
        return supplierVoucher;
    }

    public void setSupplierVoucher(SupplierAnnex supplierVoucher) {
        this.supplierVoucher = supplierVoucher;
    }

    public List<SupplierAnnex> getSupplierEnclosure() {
        return supplierEnclosure;
    }

    public void setSupplierEnclosure(List<SupplierAnnex> supplierEnclosure) {
        this.supplierEnclosure = supplierEnclosure;
    }

    public List<SupplierAccount> getSupplierAccounts() {
        return supplierAccounts;
    }

    public void setSupplierAccounts(List<SupplierAccount> supplierAccounts) {
        this.supplierAccounts = supplierAccounts;
    }

}
