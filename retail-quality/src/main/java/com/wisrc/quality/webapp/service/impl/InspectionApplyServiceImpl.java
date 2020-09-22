package com.wisrc.quality.webapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisrc.quality.webapp.dao.InspectionApplyInfoDao;
import com.wisrc.quality.webapp.dao.InspectionApplyProductDetailsInfoDao;
import com.wisrc.quality.webapp.entity.InspectionApplyInfoEntity;
import com.wisrc.quality.webapp.entity.InspectionApplyProductDetailsInfoEntity;
import com.wisrc.quality.webapp.entity.InspectionApplyProductDetailsStatusAttrEntity;
import com.wisrc.quality.webapp.entity.InspectionApplyTypeAttrEntity;
import com.wisrc.quality.webapp.service.*;
import com.wisrc.quality.webapp.utils.PageData;
import com.wisrc.quality.webapp.utils.Result;
import com.wisrc.quality.webapp.utils.Time;
import com.wisrc.quality.webapp.utils.Toolbox;
import com.wisrc.quality.webapp.vo.inspectionApply.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class InspectionApplyServiceImpl implements InspectionApplyService {

    @Autowired
    private InspectionApplyInfoDao inspectionDao;
    @Autowired
    private InspectionApplyProductDetailsInfoDao productDao;
    @Autowired
    private QualitySupplierService qualitySupplierService;
    @Autowired
    private QualityEmployeeService qualityEmployeeService;
    @Autowired
    private QualityProductHandleService qualityProductHandleService;
    @Autowired
    private PurchaseService purchaseService;

    public LinkedHashMap findAll() {
        List<InspectionApplyInfoEntity> infoList = inspectionDao.findByCond(null, null,
                null, null, null, null, null);
        List<InspectionQueryResultVO> queryList = getInspectionResult(infoList, null);

        return PageData.pack(-1, -1, "inspectionAppInfoList", queryList);
    }

    @Override
    public LinkedHashMap findByCond(int pageNum, int pageSize, String orderId, String employeeId,
                                    Date inspectionStartTime, Date inspectionEndTime, String statusCd,
                                    String inspectionType, String keyWord) {
        String[] supplierArr = changeStringToArr(keyWord, "supplier");

        PageHelper.startPage(pageNum, pageSize);
        List<InspectionApplyInfoEntity> infoList = inspectionDao.findByCond(orderId, employeeId, inspectionStartTime,
                inspectionEndTime, statusCd, inspectionType, supplierArr);

        List<InspectionQueryResultVO> queryList = getInspectionResult(infoList, statusCd);
        PageInfo pageInfo = new PageInfo(infoList);

        return PageData.pack(pageInfo.getTotal(), pageInfo.getPages(), "inspectionAppInfoList", queryList);
    }

    public List<InspectionQueryResultVO> getInspectionResult(List<InspectionApplyInfoEntity> infoList, String statusCdSearch) {
        List<InspectionQueryResultVO> resultVOList = new ArrayList<>();
        List<String> inspectionIds = new ArrayList<>();
        List<String> supplierIds = new ArrayList();
        List<String> employeeIds = new ArrayList();
        Map<String, String> supplierNameMap = new HashMap();
        Map<String, String> employeeNameMap = new HashMap();

        for (InspectionApplyInfoEntity infoEntity : infoList) {
            inspectionIds.add(infoEntity.getInspectionId());
            supplierIds.add(infoEntity.getSupplierId());
            employeeIds.add(infoEntity.getEmployeeId());
        }

        try {
            supplierNameMap = qualitySupplierService.getSupplierNameMap(supplierIds);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            employeeNameMap = qualityEmployeeService.getEmployeeNameMap(employeeIds);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map allProductAttr = findAllProductAttr();
        Map allApplyAttr = findAllApplyAttr();

        List<InspectionQueryResultVO> status1 = new ArrayList();
        List<InspectionQueryResultVO> status2 = new ArrayList();
        List<InspectionQueryResultVO> status3 = new ArrayList();
        for (InspectionApplyInfoEntity infoEntity : infoList) {
            InspectionQueryResultVO queryResultInfo = new InspectionQueryResultVO();
            String[] inspectionIdArr = new String[1];
            inspectionIdArr[0] = infoEntity.getInspectionId();
            int statusCd = getStatusCd(inspectionIdArr);
            //修复查询条件包含在其他状态的问题
            if (statusCdSearch != null && !statusCdSearch.equals(String.valueOf(statusCd))) {
                continue;
            }
            String statusName = (String) allProductAttr.get(statusCd);
            queryResultInfo.setInspectionId(infoEntity.getInspectionId());
            queryResultInfo.setApplyDate(infoEntity.getApplyDate());
            queryResultInfo.setEmployeeId(infoEntity.getEmployeeId());
            queryResultInfo.setEmployeeName(employeeNameMap.get(infoEntity.getEmployeeId()));
            queryResultInfo.setExpectInspectionTime(infoEntity.getExpectInspectionTime());
            queryResultInfo.setSupplierId(infoEntity.getSupplierId());
            queryResultInfo.setSupplierName(supplierNameMap.get(infoEntity.getSupplierId()));
            queryResultInfo.setSupplierAddr(infoEntity.getSupplierAddr());
            queryResultInfo.setOrderId(infoEntity.getOrderId());
            queryResultInfo.setInspectionTypeDesc((String) allApplyAttr.get(infoEntity.getInspectionTypeCd()));
            queryResultInfo.setStatusCd(statusCd);
            queryResultInfo.setStatusName(statusName);

            if (statusCd == 1) {
                status1.add(queryResultInfo);
            } else if (statusCd == 2) {
                status2.add(queryResultInfo);
            } else if (statusCd == 3) {
                status3.add(queryResultInfo);
            }
        }
        resultVOList.addAll(status1);
        resultVOList.addAll(status2);
        resultVOList.addAll(status3);
        return resultVOList;

    }

    @Override
    public InspectionDetailVO findById(String inspectionId) {
        InspectionDetailVO detailVO = new InspectionDetailVO();
        InspectionApplyInfoEntity applyInfo = inspectionDao.getApplyInfoById(inspectionId);
        List<InspectionApplyProductDetailsInfoEntity> applyProductList = productDao.findByInspectionId(inspectionId);

        List<String> supplierIds = new ArrayList();
        List<String> employeeIds = new ArrayList();
        List skuIds = new ArrayList();
        Map<String, String> supplierNameMap = new HashMap();
        Map<String, String> employeeNameMap = new HashMap();
        Map<String, String> productNameMap = new HashMap();

        supplierIds.add(applyInfo.getSupplierId());
        employeeIds.add(applyInfo.getEmployeeId());
        for (InspectionApplyProductDetailsInfoEntity productInfo : applyProductList) {
            skuIds.add(productInfo.getSkuId());
        }

        try {
            supplierNameMap = qualitySupplierService.getSupplierNameMap(supplierIds);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            employeeNameMap = qualityEmployeeService.getEmployeeNameMap(employeeIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            productNameMap = qualityProductHandleService.getProductCNNameMap(skuIds);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map allProductAttr = findAllProductAttr();
        Map allApplyAttr = findAllApplyAttr();

        detailVO.setInspectionId(inspectionId);
        detailVO.setSupplierId(applyInfo.getSupplierId());
        detailVO.setSupplierName(supplierNameMap.get(applyInfo.getSupplierId()));
        detailVO.setApplyDate(applyInfo.getApplyDate());
        detailVO.setEmployeeId(applyInfo.getEmployeeId());
        detailVO.setEmployeeName(employeeNameMap.get(applyInfo.getEmployeeId()));
        detailVO.setSupplierContactUser(applyInfo.getSupplierContactUser());
        detailVO.setSupplierPhone(applyInfo.getSupplierPhone());
        detailVO.setSupplierAddr(applyInfo.getSupplierAddr());
        detailVO.setOrderId(applyInfo.getOrderId());
        detailVO.setExpectInspectionTime(applyInfo.getExpectInspectionTime());
        detailVO.setRemark(applyInfo.getRemark());
        if (applyInfo.getInspectionTypeCd() == null) {
            detailVO.setInspectionTypeCd("");
            detailVO.setInspectionTypeDesc("");
        } else {
            detailVO.setInspectionTypeCd(String.valueOf(applyInfo.getInspectionTypeCd()));
            detailVO.setInspectionTypeDesc((String) allApplyAttr.get(Integer.valueOf(applyInfo.getInspectionTypeCd())));
        }

        Map<String, Map> quality = new HashMap();
        try {
            quality = purchaseService.getQuality(applyInfo.getOrderId(), skuIds);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<InspectionProductDetaiVO> productDetaiList = new ArrayList<>();
        for (InspectionApplyProductDetailsInfoEntity productInfo : applyProductList) {
            InspectionProductDetaiVO productDetail = new InspectionProductDetaiVO();
            productDetail.setInspectionProductId(productInfo.getInspectionProductId());
            productDetail.setSkuId(productInfo.getSkuId());
            productDetail.setSkuName(productNameMap.get(productInfo.getSkuId()));
            productDetail.setApplyInspectionQuantity(productInfo.getApplyInspectionQuantity());
            String inspectionCd = inspectionDao.findProductInspCd(applyInfo.getOrderId(), productInfo.getSkuId(), applyInfo.getInspectionId());
            productDetail.setInspectionCd(inspectionCd);//设置产品检验单号
            productDetail.setInspectionQuantity(productInfo.getInspectionQuantity());
            productDetail.setQualifiedQuantity(productInfo.getQualifiedQuantity());
            productDetail.setUnqualifiedQuantity(productInfo.getUnqualifiedQuantity());
            productDetail.setStatusCd(productInfo.getStatusCd());
            productDetail.setStatusDesc((String) allProductAttr.get(productInfo.getStatusCd()));
            if (quality.get(productInfo.getSkuId()) != null) {
                productDetail.setQuantity((Integer) quality.get(productInfo.getSkuId()).get("quantity"));
                productDetail.setSpareRate(Double.parseDouble(quality.get(productInfo.getSkuId()).get("spareRate").toString()));
            }

            productDetaiList.add(productDetail);
        }

        detailVO.setProductList(productDetaiList);

        return detailVO;
    }

    //获取所有产品状态信息
    public Map findAllProductAttr() {
        Map<Integer, String> productMap = new HashMap<>();

        List<InspectionApplyProductDetailsStatusAttrEntity> allProdStatus = productDao.findAllProdStatus();
        for (InspectionApplyProductDetailsStatusAttrEntity prodStatus : allProdStatus) {
            productMap.put(prodStatus.getStatusCd(), prodStatus.getStatusDesc());
        }

        return productMap;
    }

    //获取所有验货方式信息
    public Map findAllApplyAttr() {
        Map<Integer, String> ApplyTypeMap = new HashMap<>();

        List<InspectionApplyTypeAttrEntity> allApplyTypeDesc = inspectionDao.findAllApplyTypeDesc();
        for (InspectionApplyTypeAttrEntity applyTypeAttrEntity : allApplyTypeDesc) {
            ApplyTypeMap.put(applyTypeAttrEntity.getInspectionTypeCd(), applyTypeAttrEntity.getInspectionTypeDesc());
        }

        return ApplyTypeMap;
    }


    @Override
    @Transactional
    public void saveInspectionInfo(InspectionAddVO addVO, String userId) {
        InspectionApplyInfoEntity applyInfo = new InspectionApplyInfoEntity();
        List<InspectionProdAddVO> prodList = addVO.getProdList();

        Timestamp currentTime = Time.getCurrentTimestamp();
        String inspectionId;

        String maxId = inspectionDao.findMaxinspectionId();
        if (maxId == null) {
            maxId = "";
        }

        java.util.Date day = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yy");
        String nowDateStr = sdf.format(day);
        if (!"".equals(maxId) && nowDateStr.equals(maxId.substring(0, 2))) {
            Long replenmentInt = Long.parseLong(maxId) + 1;
            inspectionId = "PI" + replenmentInt;
        } else {
            inspectionId = "PI" + nowDateStr + "00001";
        }

        applyInfo.setInspectionId(inspectionId);
        addVO.toEntity(applyInfo, addVO);
        applyInfo.setCreateTime(currentTime);
        applyInfo.setCreateUser(userId);
        inspectionDao.saveInspectionInfo(applyInfo);

        int statusCd = 1;
        if (addVO.getInspectionTypeCd() != null) {
            if (addVO.getInspectionTypeCd() == 2) {
                statusCd = 2;
            } else if (addVO.getInspectionTypeCd() == 1 || addVO.getInspectionTypeCd() == 3) {
                statusCd = 3;
            }
        }

        for (InspectionProdAddVO productVO : prodList) {
            InspectionApplyProductDetailsInfoEntity productInfo = new InspectionApplyProductDetailsInfoEntity();
            String uuid = Toolbox.randomUUID();
            productInfo.setInspectionId(inspectionId);
            productInfo.setInspectionProductId(uuid);
            productInfo.setSkuId(productVO.getSkuId());
            productInfo.setStatusCd(statusCd);//设置状态，新建时根据验货方式设置状态
            productInfo.setApplyInspectionQuantity(productVO.getApplyInspectionQuantity());

            productDao.saveProductInfo(productInfo);
        }

    }

    @Override
    @Transactional
    public Result updateInspectionInfo(InspectionEditVO editVO, String userId) {
        try {
            InspectionApplyInfoEntity applyInfo = new InspectionApplyInfoEntity();
            List<InspectionProdEditVO> prodList = editVO.getProdList();

            String inspectionId = editVO.getInspectionId();

            Timestamp currentTime = Time.getCurrentTimestamp();

            Boolean canUpdateType = checkType(inspectionId);
            if (!canUpdateType) {
                return Result.failure(400, "存在已经验货的sku，不允许修改验货方式", "");
            }
            editVO.toEntity(applyInfo, editVO);
            applyInfo.setModifyTime(currentTime);
            applyInfo.setModifyUser(userId);
            inspectionDao.editInspectionInfo(applyInfo);

            //通过验货申请单查找验货产品信息
            List<InspectionApplyProductDetailsInfoEntity> existProd = productDao.findByInspectionId(inspectionId);
            Map id2Map = new HashMap();
            List productIds = new ArrayList();
            for (InspectionApplyProductDetailsInfoEntity existprodEntity : existProd) {
                id2Map.put(existprodEntity.getInspectionProductId(), existprodEntity.getStatusCd());
                if (existprodEntity.getStatusCd() != 3) {
                    productIds.add(existprodEntity.getInspectionProductId());
                }
            }

            //编辑产品
            for (InspectionProdEditVO prodeditVO : prodList) {
                //如果有状态则保存
                if (prodeditVO.getInspectionProductId() != null && productIds.indexOf(prodeditVO.getInspectionProductId()) != -1) {
                    productDao.editApplyInspection(prodeditVO);
                }
            }
            return Result.success("验货申请单修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failure(390, "验货申请单修改失败", null);
        }
    }

    @Override
    @Transactional
    public String updateInspecType(String inspectionId, int inspectionTypeCd, String userId) {
        Boolean canUpdateType = checkType(inspectionId);
        if (canUpdateType) {
            int statusCd = 1;
            InspectionApplyInfoEntity applyInfo = new InspectionApplyInfoEntity();
            Timestamp currentTime = Time.getCurrentTimestamp();
            applyInfo.setModifyTime(currentTime);
            applyInfo.setModifyUser(userId);
            applyInfo.setInspectionId(inspectionId);
            if (inspectionTypeCd != 0) {
                applyInfo.setInspectionTypeCd(inspectionTypeCd);
            } else {
                applyInfo.setInspectionTypeCd(null);
            }
            if (inspectionTypeCd == 0) {
                statusCd = 1;
            } else if (inspectionTypeCd == 2) {
                statusCd = 2;
            } else if (inspectionTypeCd == 1 || inspectionTypeCd == 3) {
                statusCd = 3;
            }
            productDao.updateStatus(inspectionId, statusCd);
            inspectionDao.updateType(applyInfo);

            return "更新验货方式成功";
        } else {
            return "存在已经验货的sku，不允许修改验货方式";
        }
    }

    public int getStatusCd(String[] inspectionIds) {
        List<Integer> statusCdList = new ArrayList<>();

        List<InspectionApplyProductDetailsInfoEntity> productInfoList = productDao.findByInspectionIds(inspectionIds);

        for (InspectionApplyProductDetailsInfoEntity productInfo : productInfoList) {
            statusCdList.add(productInfo.getStatusCd());
        }

        HashSet<Integer> convertSet = new HashSet<>(statusCdList);
        //只要有一个sku为待检验 那么整个单就是待检验 因为不存在没有sku的申请
        //这样来说 只要是两个不同的状态的 那么就是待检验
        if (1 != convertSet.size()) {
            return 2;
        } else {
            if (convertSet.contains(1)) {
                return 1;
            } else if (convertSet.contains(2)) {
                return 2;
            } else {
                return 3;
            }
        }
    }

    @Override
    @Transactional
    public void deleteInspection(String[] inspectionIds) {
        for (String inspectionId : inspectionIds) {
            productDao.deleteProductInfo(inspectionId);
            inspectionDao.deleteById(inspectionId);
        }
    }

    public LinkedHashMap findAllType() {
        List<InspectionApplyTypeAttrEntity> typeList = inspectionDao.findAllApplyTypeDesc();
        return PageData.pack(-1, -1, "typeList", typeList);
    }

    @Override
    public LinkedHashMap findAllStatus() {
        List<InspectionApplyProductDetailsStatusAttrEntity> allStatusList = productDao.findAllProdStatus();
        return PageData.pack(-1, -1, "typeList", allStatusList);
    }

    @Override
    public LinkedHashMap findByCond(int pageNum, int pageSize, String orderId, String supplierName, String skuId, String productNameCN, String inspectionId) {

        String[] supplierArr = changeStringToArr(supplierName, "supplier");
        String[] productArr = changeStringToArr(productNameCN, "product");

        PageHelper.startPage(pageNum, pageSize);
        List<InspectionDataVO> infoList = inspectionDao.findByOrderIds(orderId, supplierArr, skuId, productArr, inspectionId);
        PageInfo pageInfo = new PageInfo(infoList);

        List<InspectionDataVO> inspectionData = getInspectionData(infoList);

        return PageData.pack(pageInfo.getTotal(), pageInfo.getPages(), "inspectionData", inspectionData);

    }

    @Override
    public LinkedHashMap findByCond(String orderId, String supplierName, String skuId, String productNameCN, String inspectionId) {
        String[] supplierArr = changeStringToArr(supplierName, "supplier");
        String[] productArr = changeStringToArr(productNameCN, "product");

        List<InspectionDataVO> infoList = inspectionDao.findByOrderIds(orderId, supplierArr, skuId, productArr, inspectionId);
        List<InspectionDataVO> inspectionData = getInspectionData(infoList);

        return PageData.pack(inspectionData.size(), 1, "inspectionData", inspectionData);
    }

    @Override
    public LinkedHashMap findByCond(String orderId, String keyWords, String skuId) {
        String[] supplierArr = changeStringToArr(keyWords, "supplier");
        String[] productArr = changeStringToArr(keyWords, "product");

        List<InspectionDataVO> infoList = inspectionDao.findByWords(orderId, supplierArr, productArr, skuId);
        List<InspectionDataVO> inspectionData = getInspectionData(infoList);

        return PageData.pack(inspectionData.size(), 1, "inspectionData", inspectionData);
    }

    @Override
    public LinkedHashMap findByOrderId(OrderIdRequestVO orderIdRequestVO, String queryType) {
        List<OrderIdListVO> orderIdListVOS = orderIdRequestVO.getOrderIdListVOList();

        List<OrderIdQueryVO> queryInspectionList = new ArrayList<>();
        List<OrderIdQuerySumVO> querySumList = new ArrayList<>();
        if (orderIdListVOS != null && orderIdListVOS.size() > 0) {
            String[] orderIds = new String[orderIdListVOS.size()];
            String[] skuIds = new String[orderIdListVOS.size()];

            for (int i = 0; i < orderIdListVOS.size(); i++) {
                orderIds[i] = orderIdListVOS.get(i).getOrderId();
                skuIds[i] = orderIdListVOS.get(i).getSkuId();
            }
            if ("sum".equals(queryType)) {
                querySumList = inspectionDao.findInspecSumByOrderId(orderIds, skuIds);
            } else {
                queryInspectionList = inspectionDao.findInspecByOrderId(orderIds, skuIds);
            }
        }
        if ("sum".equals(queryType)) {
            return PageData.pack(-1, -1, "inspectionInfo", querySumList);
        } else {
            return PageData.pack(-1, -1, "inspectionInfo", queryInspectionList);
        }

    }


    public List<InspectionDataVO> getInspectionData(List<InspectionDataVO> dataList) {
        List<String> inspectionIds = new ArrayList<>();
        List<String> supplierIds = new ArrayList();
        List<String> employeeIds = new ArrayList();
        List<String> skuIds = new ArrayList<>();
        Map<String, String> supplierNameMap = new HashMap();
        Map<String, String> employeeNameMap = new HashMap();
        Map<String, String> productNameMap = new HashMap();

        for (InspectionDataVO infoEntity : dataList) {
            inspectionIds.add(infoEntity.getInspectionId());
            supplierIds.add(infoEntity.getSupplierId());
            employeeIds.add(infoEntity.getApplyPersonId());
            skuIds.add(infoEntity.getSkuId());
        }

        try {
            supplierNameMap = qualitySupplierService.getSupplierNameMap(supplierIds);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            employeeNameMap = qualityEmployeeService.getEmployeeNameMap(employeeIds);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            productNameMap = qualityProductHandleService.getProductCNNameMap(skuIds);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (InspectionDataVO infoEntity : dataList) {
            infoEntity.setApplyPersonName(employeeNameMap.get(infoEntity.getApplyPersonId()));
            infoEntity.setProductNameCN(productNameMap.get(infoEntity.getSkuId()));
            infoEntity.setSupplierName(supplierNameMap.get(infoEntity.getSupplierId()));
        }
        return dataList;

    }

    //是否能更新验货方式
    public Boolean checkType(String inspectionId) {
        List<Integer> inspectionQuanList = new ArrayList<>();
        List<InspectionApplyProductDetailsInfoEntity> productInfoList = productDao.findByInspectionId(inspectionId);
        for (InspectionApplyProductDetailsInfoEntity productInfo : productInfoList) {
            inspectionQuanList.add(productInfo.getStatusCd());
        }

        HashSet<Integer> convertSet = new HashSet<>(inspectionQuanList);

        if (3 == convertSet.size()) {
            return false;
        } else {
            return true;
        }
    }

    //将名称转换成id，方便查询
    public String[] changeStringToArr(String keyWord, String type) {
        List<String> aimsIds = new ArrayList<>();
        try {
            if (keyWord != null && !keyWord.isEmpty()) {
                if ("supplier".equals(type)) {
                    aimsIds = qualitySupplierService.getFindKeyDealted(keyWord);
                } else if ("product".equals(type)) {
                    aimsIds = qualityProductHandleService.getFindKeyDealted(keyWord);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] aimsArr = null;
        if (keyWord == null || "".equals(keyWord)) {
            return null;
        }
        if (aimsIds != null && aimsIds.size() > 0) {
            aimsArr = new String[aimsIds.size()];
            aimsIds.toArray(aimsArr);
        } else {
            aimsArr = new String[1];
            aimsArr[0] = "";
        }
        return aimsArr;

    }

    @Override
    public int getInspectionApplyInfo(String orderId) {
        return inspectionDao.getInspectionApplyInfo(orderId);
    }
}
