package com.wisrc.supplier.webapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisrc.supplier.webapp.dao.SupplierDocumentDao;
import com.wisrc.supplier.webapp.dao.SupplierManageDao;
import com.wisrc.supplier.webapp.entity.Supplier;
import com.wisrc.supplier.webapp.entity.SupplierAccount;
import com.wisrc.supplier.webapp.entity.SupplierInfo;
import com.wisrc.supplier.webapp.service.SupplierManageService;
import com.wisrc.supplier.webapp.dao.SupplierAccountDao;
import com.wisrc.supplier.webapp.entity.SupplierAnnex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

@Service
public class SupplierManageImpl implements SupplierManageService {

	@Autowired
	private SysServiceImpl sysServiceImpl;
	@Autowired
	private SupplierManageDao supplierManageDao;
	@Autowired
	private SupplierAccountDao supplierAccountDao;
	@Autowired
	private SupplierDocumentDao supplierDocumentDao;

	@Override
	public Map<String, Object> getSuppliers(String supplierId, String supplierName, String startTime, String endTime,
											Integer status, Integer currentPage, Integer pageSize) {
		// 列表转换
		PageHelper.startPage(currentPage, pageSize);
		List<Map<String, Object>> results = new ArrayList<>();
		List<Supplier> list = supplierManageDao.getSuppliers(supplierId, supplierName, startTime, endTime, status);
		PageInfo<Supplier> pageInfo = new PageInfo<>(list);
		for (Supplier supplier : pageInfo.getList()) {
			Map<String, Object> map = new HashMap<>();
			map.put("supplierId", supplier.getSupplierId());
			map.put("supplierName", supplier.getSupplierName());
			map.put("contacts", supplier.getContacts());
			map.put("telephone", supplier.getTelephone());
			map.put("status", supplier.getStatus());
			map.put("updateTime", supplier.getUpdateTime());
			map.put("createTime", supplier.getCreateTime());
			map.put("mender", sysServiceImpl.getUserInfo((String) supplier.getMender()));
			results.add(map);
		}
		// 返回结果
		Map<String, Object> map = new HashMap<>();
		map.put("suppliers", results);
		map.put("currentPage", pageInfo.getPageNum());
		map.put("pageSize", pageInfo.getPageSize());
		map.put("supplierCount", pageInfo.getTotal());
		return map;
	}

	@Override
	public Map<String, Object> getSupplierInfo(String sId) {
		// 返回结果
		Map<String, Object> map = new HashMap<>();
		// 获取供应商信息表
		List<Supplier> suppliers = supplierManageDao.getSupplier(sId);

		// 获取供应商附件信息表
		List<SupplierAnnex> supplierAnnexs = supplierDocumentDao.getSupplierAnnex(sId);
		// 获取供应商账号信息表
		List<SupplierAccount> supplierAccounts = supplierAccountDao.getSupplierAccount(sId);

		Supplier supplier = new Supplier();

		if (suppliers.size() != 0) {
			supplier = suppliers.get(0);
			// 附件转化
			supplier.setSupplierVoucher(null);
			for (SupplierAnnex supplierAnnex : supplierAnnexs) {
				if (supplierAnnex.getType() == 0) {
					supplier.setSupplierVoucher(supplierAnnex);
				} else {
					supplier.getSupplierEnclosure().add(supplierAnnex);
				}
			}
			// 帐号转化
			supplier.setMender((String) sysServiceImpl.getUserInfo(supplier.getMender()));
			supplier.setSupplierAccounts(supplierAccounts);
		}

		// 获取供应商详细信息表
		List<SupplierInfo> supplierInfos = supplierManageDao.getSupplierInfo(sId);

		if (supplier != null && supplierInfos.size() != 0) {
			SupplierInfo supplierInfo = supplierInfos.get(0);
			// 产品凭证转化
			String cCCS = (String) supplierInfo.getcCC();
			supplierInfo.setcCC(new ArrayList<String>());
			if (cCCS.length() > 2) {
				String cCC = cCCS.substring(1, cCCS.length() - 1);
				supplierInfo.setcCC(Arrays.asList(cCC.split(", ")));
			}
			// 研发能力转化
			String rDS = (String) supplierInfo.getrD();
			supplierInfo.setrD(new ArrayList<String>());
			if (rDS.length() > 2) {
				String rD = rDS.substring(1, rDS.length() - 1);
				supplierInfo.setrD(Arrays.asList(rD.split(", ")));
			}
			supplier.setSupplierInfo(supplierInfo);
		}

		map.put("success", true);
		map.put("code", 200);
		map.put("supplier", supplier);
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> addSupplierInfo(Supplier supplier) {
		Map<String, Object> map = new HashMap<>();
		map.put("success", false); //设置success默认为false
		try {
			SupplierInfo supplierInfo = supplier.getSupplierInfo();
			String taxpayerIC = supplierInfo.getTaxpayerIC();
			if ((supplierInfo.getVatInvoice() == 1)) {
				//如果要求开增值税发票 那么
				// 纳税人识别号必填
				if (taxpayerIC == null || taxpayerIC.trim().isEmpty()) {
					map.put("msg", "开增值税发票时纳税人识别号必填");
					return map;
				}
				//发票税点必填
				if (supplierInfo.getTaxPoint() == null) {
					map.put("msg", "开增值税发票时发票税点必填");
					return map;
				}
				//预期退税必填
				if (supplierInfo.getDrawback() == null) {
					map.put("msg", "开增值税发票时预期退税必填");
					return map;
				}
			}
			//如果前端给的是一个空字符串 那么置为null
			if (taxpayerIC != null && taxpayerIC.trim().isEmpty()) {
				taxpayerIC = null;
				supplierInfo.setTaxpayerIC(null);
			}
			// 不为空的情况下 需要检查纳税人识别号是否唯一 因为是添加，供应商id不传
			if (taxpayerIC != null && supplierManageDao.checkTaxpayerICUnique(null, taxpayerIC) != 0) {
				map.put("msg", "该纳税人识别号已经被使用，请检查");
				return map;
			}
			// 编号生成
			String sId = "SPL00001";
			List<String> supplierId = supplierManageDao.getSupplierId();
			if (supplierId.size() != 0) {
				Integer num = Integer.valueOf(supplierId.get(0).substring(3)) + 1;
				sId = "SPL" + String.format("%5d", num).replace(" ", "0");
			}
			// 基础添加
			supplier.setSupplierId(sId);
			supplier.setStatus(1);
			supplier.setUpdateTime(new Date());
			supplier.setCreateTime(new Date());
			supplierManageDao.addSupplier(supplier);
			// 详情添加
			supplierInfo.setsId(sId);
			if (supplierInfo.getVatInvoice() == null || supplierInfo.getVatInvoice() == 0) {
				supplierInfo.setTaxPoint(null);
				supplierInfo.setDrawback(null);
			}
			List<String> cccList = (List<String>) supplierInfo.getcCC();
			HashSet<Object> cccSet = new HashSet<>(cccList);
			List<String> rdList = (List<String>) supplierInfo.getrD();
			HashSet<Object> rdSet = new HashSet<>(rdList);
			supplierInfo.setcCC(cccSet.toString());
			supplierInfo.setrD(rdSet.toString());
			supplierManageDao.addSupplierInfo(supplierInfo);
			// 附件添加
			SupplierAnnex supplierVoucher = supplier.getSupplierVoucher();
			List<SupplierAnnex> SupplierAnnex = supplier.getSupplierEnclosure();
			if (supplierVoucher.getName() != null) {
				SupplierAnnex.add(supplierVoucher);
			}
			for (SupplierAnnex annex : SupplierAnnex) {
				annex.setsId(sId);
				supplierDocumentDao.addSupplierAnnex(annex);
			}
			map.put("success", true);
			map.put("supplier", supplier);
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			map.put("success", false);
			map.put("msg", "系统异常，请联系管理员");
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Map<String, Object> setSupplierInfo(Supplier supplier) {
		Map<String, Object> map = new HashMap<>();
		map.put("success", false);
		try {
			// 更新前需要确定不是停用状态
			List<Supplier> suppliers = supplierManageDao.getSupplier(supplier.getSupplierId());
			// 如果供应id没有对应的数据，提示失败
			if (suppliers.size() != 1) {
				map.put("msg", "不存在的供应商编号，请检查");
				return map;
			}
			Supplier supplier1 = suppliers.get(0);
			// status 为0 为停用
			// 如果为停用，提示失败
			if (supplier1.getStatus() == 0) {
				map.put("msg", "该供应商为停用状态，无法修改");
				return map;
			}
			SupplierInfo supplierInfo = supplier.getSupplierInfo();
			String taxpayerIC = supplierInfo.getTaxpayerIC();
			if ((supplierInfo.getVatInvoice() == 1)) {
				//如果要求开增值税发票
				// 纳税人识别号必填
				if (taxpayerIC == null || taxpayerIC.trim().isEmpty()) {
					map.put("msg", "开增值税发票时纳税人识别号必填");
					return map;
				}
				//发票税点必填
				if (supplierInfo.getTaxPoint() == null) {
					map.put("msg", "开增值税发票时发票税点必填");
					return map;
				}
				//预期退税必填
				if (supplierInfo.getDrawback() == null) {
					map.put("msg", "开增值税发票时预期退税必填");
					return map;
				}
			}
			//如果前端给的是一个空字符串 那么置为null
			if (taxpayerIC != null && taxpayerIC.trim().isEmpty()) {
				taxpayerIC = null;
				supplierInfo.setTaxpayerIC(null);
			}
			// 不为空的情况下 需要检查纳税人识别号是否唯一
			if (taxpayerIC != null && supplierManageDao.checkTaxpayerICUnique(supplier.getSupplierId(), taxpayerIC) != 0) {
				map.put("msg", "该纳税人识别号已经被使用，请检查");
				return map;
			}
			// 基础更新
			supplier.setUpdateTime(new Date());
			supplierManageDao.setSupplier(supplier);
			// 详情更新
			supplierInfo.setsId(supplier.getSupplierId());
			if (supplierInfo.getVatInvoice() == null || supplierInfo.getVatInvoice() == 0) {
				supplierInfo.setTaxPoint(null);
				supplierInfo.setDrawback(null);
			}
			List<String> cccList = (List<String>) supplierInfo.getcCC();
			HashSet<Object> cccSet = new HashSet<>(cccList);
			List<String> rdList = (List<String>) supplierInfo.getrD();
			HashSet<Object> rdSet = new HashSet<>(rdList);
			supplierInfo.setcCC(cccSet.toString());
			supplierInfo.setrD(rdSet.toString());
			supplierManageDao.setSupplierInfo(supplierInfo);
			// 附件添加
			SupplierAnnex supplierVoucher = supplier.getSupplierVoucher();
			List<SupplierAnnex> SupplierAnnex = supplier.getSupplierEnclosure();
			if (supplierVoucher.getName() != null) {
				SupplierAnnex.add(supplierVoucher);
			}
			for (SupplierAnnex annex : SupplierAnnex) {
				annex.setsId(supplier.getSupplierId());
				supplierDocumentDao.addSupplierAnnex(annex);
			}
			map.put("success", true);
			map.put("supplier", supplier);
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			map.put("success", false);
			map.put("msg", "系统异常，请联系管理员");
		}
		return map;
	}

	@Override
	@Transactional
	public Map<String, Object> setSupplierStatus(Integer status, String[] supplierId) {
		Map<String, Object> map = new HashMap<>();
		for (String sId : supplierId) {
			map.put(sId, supplierManageDao.setSupplierStatus(sId, status));
		}
		map.put("success", true);
		return map;
	}

}
