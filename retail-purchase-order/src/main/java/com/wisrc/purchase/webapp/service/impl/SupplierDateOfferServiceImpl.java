package com.wisrc.purchase.webapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisrc.purchase.webapp.dao.PurchaseDateOfferLogDao;
import com.wisrc.purchase.webapp.dao.SupplierDateOfferDao;
import com.wisrc.purchase.webapp.dto.supplierOffer.SupplierOfferPageDto;
import com.wisrc.purchase.webapp.entity.PurchaseDateOfferLogEntity;
import com.wisrc.purchase.webapp.entity.SupplierDateOfferEntity;
import com.wisrc.purchase.webapp.entity.TeamStatusAttrEntity;
import com.wisrc.purchase.webapp.query.supplierOffer.SupplierOfferPageQuery;
import com.wisrc.purchase.webapp.service.PurchaseEmployeeService;
import com.wisrc.purchase.webapp.service.ProductHandleService;
import com.wisrc.purchase.webapp.service.SupplierDateOfferService;
import com.wisrc.purchase.webapp.service.SupplierService;
import com.wisrc.purchase.webapp.utils.PageData;
import com.wisrc.purchase.webapp.utils.Result;
import com.wisrc.purchase.webapp.vo.product.GetProductInfoVO;
import com.wisrc.purchase.webapp.vo.supplierDateOffer.SupplierDateOfferPageVo;
import com.wisrc.purchase.webapp.vo.supplierDateOffer.SupplierDateOfferVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SupplierDateOfferServiceImpl implements SupplierDateOfferService {
    @Autowired
    SupplierDateOfferDao supplierDateOfferDao;
    @Autowired
    ProductHandleService productHandleService;
    @Autowired
    SupplierService supplierService;
    @Autowired
    PurchaseEmployeeService purchaseEmployeeService;

    @Autowired
    private PurchaseDateOfferLogDao purchaseDateOfferLogDao;


    @Override
    public LinkedHashMap findInfo(int startPage, int pageSize, String employeeId, String supplierId, String skuId, int statusCd) {
        PageHelper.startPage(startPage, pageSize);
        List<SupplierDateOfferEntity> EnList = supplierDateOfferDao.findInfo(employeeId, supplierId, skuId, statusCd);
        PageInfo info = new PageInfo(EnList);
        return PageData.pack(info.getTotal(), info.getPages(), "supplierDateOfferList", toVoList(EnList));
    }

    @Override
    public LinkedHashMap findInfo(String employeeId, String supplierId, String skuId, int statusCd) {
        List<SupplierDateOfferEntity> EnList = supplierDateOfferDao.findInfo(employeeId, supplierId, skuId, statusCd);
        return PageData.pack(EnList.size(), 1, "supplierDateOfferList", toVoList(EnList));
    }

    @Override
    public Result supplierOfferPage(SupplierDateOfferPageVo supplierDateOfferPageVo) {
        try {
            SupplierOfferPageDto supplierOfferDto = new SupplierOfferPageDto();
            SupplierOfferPageQuery supplierOfferPageQuery = new SupplierOfferPageQuery();

            BeanUtils.copyProperties(supplierDateOfferPageVo, supplierOfferPageQuery);

            if (supplierDateOfferPageVo.getSkuId() != null || supplierDateOfferPageVo.getProductName() != null) {
                GetProductInfoVO getProductInfoVO = new GetProductInfoVO();
                getProductInfoVO.setSkuId(supplierDateOfferPageVo.getSkuId());
                getProductInfoVO.setSkuNameZh(supplierDateOfferPageVo.getProductName());
                List skuIds = productHandleService.getProduct(getProductInfoVO);
                supplierOfferPageQuery.setSkuIds(skuIds);
            }

            if (supplierDateOfferPageVo.getSupplierName() != null) {
                List supplierIds = supplierService.getFindKeyDealted(supplierDateOfferPageVo.getSupplierName());
                supplierOfferPageQuery.setSupplierIds(supplierIds);
            }

            if (supplierDateOfferPageVo.getPageNum() != null && supplierDateOfferPageVo.getPageSize() != null) {
                PageHelper.startPage(supplierDateOfferPageVo.getPageNum(), supplierDateOfferPageVo.getPageSize());
            }
            List<SupplierDateOfferEntity> supplierOfferList = supplierDateOfferDao.supplierOfferPage(supplierOfferPageQuery);
            PageInfo pageInfo = new PageInfo(supplierOfferList);

            supplierOfferDto.setSupplierDateOfferList(toVoList(supplierOfferList));
            supplierOfferDto.setTotal(pageInfo.getTotal());
            supplierOfferDto.setPages(pageInfo.getPages());

            return Result.success(supplierOfferDto);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }

    @Override
    public SupplierDateOfferVO findInfoById(String supplierOfferId) {
        SupplierDateOfferEntity infoById = supplierDateOfferDao.findInfoById(supplierOfferId);
        return toVo(infoById);
    }

    @Override
    public SupplierDateOfferEntity findInfoBySupplier(String supplierId) {
        return supplierDateOfferDao.findInfoBySupplier(supplierId);
    }

    @Override
    @Transactional(transactionManager = "retailPurchaseOrderTransactionManager")
    public Result updateInfo(SupplierDateOfferEntity supplierDateOfferEntity) {
        supplierDateOfferDao.updateInfo(supplierDateOfferEntity);
        return Result.success("修改采购报价信息成功");
    }

    @Override
    @Transactional(transactionManager = "retailPurchaseOrderTransactionManager")
    public Result delInfo(SupplierDateOfferEntity supplierDateOfferEntity) {
        supplierDateOfferDao.delInfo(supplierDateOfferEntity);
        return Result.success();
    }

    @Override
    @Transactional(transactionManager = "retailPurchaseOrderTransactionManager")
    public Result upEmployee(SupplierDateOfferEntity supplierDateOfferEntity) {
        supplierDateOfferDao.upEmployee(supplierDateOfferEntity);
        return Result.success();
    }

    @Override
    @Transactional(transactionManager = "retailPurchaseOrderTransactionManager")
    public Result upStatus(SupplierDateOfferEntity supplierDateOfferEntity) {
        supplierDateOfferDao.upStatus(supplierDateOfferEntity);
        return Result.success();
    }

    @Override
    @Transactional(transactionManager = "retailPurchaseOrderTransactionManager")
    public Result upDate(SupplierDateOfferEntity supplierDateOfferEntity) {
        supplierDateOfferDao.upDate(supplierDateOfferEntity);
        return Result.success();
    }

    @Override
    public Result insertInfo(SupplierDateOfferEntity supplierDateOfferEntity) {
        supplierDateOfferEntity.setSupplierOfferId(toId());
        supplierDateOfferDao.insertInfo(supplierDateOfferEntity);
        return Result.success(supplierDateOfferEntity.getSupplierOfferId());
    }

    @Override
    @Transactional(transactionManager = "retailPurchaseOrderTransactionManager")
    public void deleteInfo(String supplierOfferId) {
        supplierDateOfferDao.deleteInfo(supplierOfferId);
    }

    @Override
    public List<TeamStatusAttrEntity> findTeamAttr() {
        return supplierDateOfferDao.findTeamAttr();
    }

    @Override
    public int findRepeat(String supplierId, String skuId) {
        return supplierDateOfferDao.findRepeat(supplierId, skuId);
    }

    @Override
    public List<PurchaseDateOfferLogEntity> findLogsById(String supplierOfferId) {
        List<PurchaseDateOfferLogEntity> offerLogList = purchaseDateOfferLogDao.findById(supplierOfferId);

        List userIds = new ArrayList();
        List employeeIds = new ArrayList();
        for (PurchaseDateOfferLogEntity offerLog : offerLogList) {
            userIds.add(offerLog.getHandleUser());
            if (offerLog.getModifyColumn().toString().equals("采购员")) {
                employeeIds.add(offerLog.getOldValue());
                employeeIds.add(offerLog.getNewValue());
            }
        }

        // 转换操作人编号为名称
        Map<String, String> userMap = new HashMap();
        try {
            userMap = purchaseEmployeeService.getUserBatch(userIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 转换被修改采购人值编号为名称
        Map<String, String> employeeMap = new HashMap();
        try {
            employeeMap = purchaseEmployeeService.getEmployeeNameMap(employeeIds);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (PurchaseDateOfferLogEntity offerLog : offerLogList) {
            offerLog.setHandleUser(userMap.get(offerLog.getHandleUser()));
            if (offerLog.getModifyColumn().toString().equals("采购员")) {
                offerLog.setOldValue(employeeMap.get(offerLog.getOldValue()));
                offerLog.setNewValue(employeeMap.get(offerLog.getNewValue()));
            }
        }

        return offerLogList;
    }

    /**
     * ele转vo  并且各个ID转name(list)
     *
     * @param EnList
     * @return
     */
    private List<SupplierDateOfferVO> toVoList(List<SupplierDateOfferEntity> EnList) {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<SupplierDateOfferVO> voList = new ArrayList<>();
        List supplierIds = new ArrayList();
        List employeeIds = new ArrayList();
        List skuIds = new ArrayList();
        Map<String, String> supplierNameMap = new HashMap();
        Map<String, String> employeeNameMap = new HashMap();
        Map<String, String> productNameMap = new HashMap();
        for (SupplierDateOfferEntity ele : EnList) {
            supplierIds.add(ele.getSupplierId());
            employeeIds.add(ele.getEmployeeId());
            skuIds.add(ele.getSkuId());
        }
        if (EnList != null && EnList.size() > 0) {
            try {
                supplierNameMap = supplierService.getSupplierNameMap(supplierIds);
                employeeNameMap = purchaseEmployeeService.getEmployeeNameMap(employeeIds);
                productNameMap = productHandleService.getProductCNNameMap(skuIds);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        List userIds = new ArrayList();
        for (SupplierDateOfferEntity ele : EnList) {
            userIds.add(ele.getModifyUser());
        }
        Map<String, String> userMap = new HashMap();
        try {
            userMap = purchaseEmployeeService.getUserBatch(userIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (SupplierDateOfferEntity ele : EnList) {
            SupplierDateOfferVO vo = SupplierDateOfferVO.toVO(ele);
            vo.setModifyUser(userMap.get(ele.getModifyUser()));
            vo.setEmployeeName(employeeNameMap.get(vo.getEmployeeId()));
            vo.setSkuNameZh(productNameMap.get(vo.getSkuId()));
            vo.setSupplierName(supplierNameMap.get(vo.getSupplierId()));
            if (ele.getModifyTime() != null) {
                vo.setModifyTime(sdf.format(ele.getModifyTime()));
            }
            voList.add(vo);
        }
        return voList;
    }


    /**
     * ele转vo  并且各个ID转name
     *
     * @param
     * @return
     */
    private SupplierDateOfferVO toVo(SupplierDateOfferEntity ele) {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List supplierIds = new ArrayList();
        List employeeIds = new ArrayList();
        List skuIds = new ArrayList();
        Map<String, String> supplierNameMap = new HashMap();
        Map<String, String> employeeNameMap = new HashMap();
        Map<String, String> productNameMap = new HashMap();
        supplierIds.add(ele.getSupplierId());
        employeeIds.add(ele.getEmployeeId());
        skuIds.add(ele.getSkuId());
        try {
            supplierNameMap = supplierService.getSupplierNameMap(supplierIds);
            employeeNameMap = purchaseEmployeeService.getEmployeeNameMap(employeeIds);
            productNameMap = productHandleService.getProductCNNameMap(skuIds);
        } catch (Exception e) {
        }
        SupplierDateOfferVO vo = SupplierDateOfferVO.toVO(ele);
        vo.setEmployeeName(employeeNameMap.get(vo.getEmployeeId()));
        vo.setSkuNameZh(productNameMap.get(vo.getSkuId()));
        vo.setSupplierName(supplierNameMap.get(vo.getSupplierId()));
        if (ele.getModifyTime() != null) {
            vo.setModifyTime(sdf.format(ele.getModifyTime()));
        }
        return vo;
    }

    /**
     * 生成报价单号 PQ+年+五位数
     * 例如：PO1800001
     *
     * @return
     */
    private String toId() {
        String supplierOfferId = supplierDateOfferDao.findMaxId();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd"); // 时间字符串产生方式
        String uid_pfix = "PQ"; // 截取年份
        if (supplierOfferId == null || supplierOfferId == "") {
            supplierOfferId = uid_pfix + format.format(new Date(System.currentTimeMillis())).substring(2, 4) + "00001";
        } else {
            int endNum = Integer.parseInt(supplierOfferId);
            int tmpNum = endNum + 1;
            supplierOfferId = uid_pfix + tmpNum;
        }
        return supplierOfferId;// 当前时间
    }
}
