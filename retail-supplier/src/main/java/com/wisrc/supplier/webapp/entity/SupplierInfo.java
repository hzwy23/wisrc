package com.wisrc.supplier.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Pattern;
import java.util.HashSet;

/**
 * 供应商信息
 *
 * @author linguosheng
 */
public class SupplierInfo {

	@ApiModelProperty(hidden = true)
	private String sId;

	@ApiModelProperty(value = "纳税人识别码")
	private String taxpayerIC;

	@Pattern(regexp = "^(13[0-9]|14[57]|15[0-3,5-9]|16[6]|17[013678]|18[0-9]|19[89])\\d{8}$|^$", message = "错误的手机格式")
	@ApiModelProperty(value = "手机号码")
	private String cellphone;

	@ApiModelProperty(value = "邮箱")
	private String mailbox;

	@ApiModelProperty(value = "QQ号")
	private String qQ;

	@ApiModelProperty(value = "供应商网站")
	private String webSite;

	@ApiModelProperty(value = "付款类型")
	private String paymentType;

	@ApiModelProperty(value = "是否开增值税发票 0 1")
	private Integer vatInvoice;

	@ApiModelProperty(value = "发票税点（%）")
	private Double taxPoint;

	@ApiModelProperty(value = "预期退税（%）")
	private Double drawback;

	@ApiModelProperty(value = "产品认证[]")
	private Object cCC = new HashSet<>();

	@ApiModelProperty(value = "研发能力[]")
	private Object rD = new HashSet<>();

	@ApiModelProperty(value = "公司性质")
	private String companyNature;

	@ApiModelProperty(value = "注册资金")
	private String registeredCapital;

	@ApiModelProperty(value = "产能")
	private String capacity;

	@ApiModelProperty(value = "质量体系")
	private String qualitySystem;

	@ApiModelProperty(value = "主要客户")
	private String mainCustomer;

	@ApiModelProperty(value = "主要产品")
	private String mainProducts;

	@ApiModelProperty(value = "主要市场")
	private String mainMarket;

	@ApiModelProperty(value = "企业人数")
	private String employees;

	@ApiModelProperty(value = "工厂面积")
	private String factorySize;

	@ApiModelProperty(value = "技术人数")
	private String technicians;

	@ApiModelProperty(value = "省份")
	private String province;

	@ApiModelProperty(value = "城市")
	private String city;

	@ApiModelProperty(value = "县区")
	private String county;

	@ApiModelProperty(value = "街道")
	private String street;

	public String getsId() {
		return sId;
	}

	public void setsId(String sId) {
		this.sId = sId;
	}

	public String getTaxpayerIC() {
		return taxpayerIC;
	}

	public void setTaxpayerIC(String taxpayerIC) {
		this.taxpayerIC = taxpayerIC;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getMailbox() {
		return mailbox;
	}

	public void setMailbox(String mailbox) {
		this.mailbox = mailbox;
	}

	public String getqQ() {
		return qQ;
	}

	public void setqQ(String qQ) {
		this.qQ = qQ;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Integer getVatInvoice() {
		return vatInvoice;
	}

	public void setVatInvoice(Integer vatInvoice) {
		this.vatInvoice = vatInvoice;
	}

	public Double getTaxPoint() {
		return taxPoint;
	}

	public void setTaxPoint(Double taxPoint) {
		this.taxPoint = taxPoint;
	}

	public Double getDrawback() {
		return drawback;
	}

	public void setDrawback(Double drawback) {
		this.drawback = drawback;
	}

	public Object getcCC() {
		return cCC;
	}

	public void setcCC(Object cCC) {
		this.cCC = cCC;
	}

	public Object getrD() {
		return rD;
	}

	public void setrD(Object rD) {
		this.rD = rD;
	}

	public String getCompanyNature() {
		return companyNature;
	}

	public void setCompanyNature(String companyNature) {
		this.companyNature = companyNature;
	}

	public String getRegisteredCapital() {
		return registeredCapital;
	}

	public void setRegisteredCapital(String registeredCapital) {
		this.registeredCapital = registeredCapital;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public String getQualitySystem() {
		return qualitySystem;
	}

	public void setQualitySystem(String qualitySystem) {
		this.qualitySystem = qualitySystem;
	}

	public String getMainCustomer() {
		return mainCustomer;
	}

	public void setMainCustomer(String mainCustomer) {
		this.mainCustomer = mainCustomer;
	}

	public String getMainProducts() {
		return mainProducts;
	}

	public void setMainProducts(String mainProducts) {
		this.mainProducts = mainProducts;
	}

	public String getMainMarket() {
		return mainMarket;
	}

	public void setMainMarket(String mainMarket) {
		this.mainMarket = mainMarket;
	}

	public String getEmployees() {
		return employees;
	}

	public void setEmployees(String employees) {
		this.employees = employees;
	}

	public String getFactorySize() {
		return factorySize;
	}

	public void setFactorySize(String factorySize) {
		this.factorySize = factorySize;
	}

	public String getTechnicians() {
		return technicians;
	}

	public void setTechnicians(String technicians) {
		this.technicians = technicians;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

}
