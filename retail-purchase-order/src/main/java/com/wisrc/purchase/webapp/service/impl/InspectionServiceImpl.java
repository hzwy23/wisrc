package com.wisrc.purchase.webapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisrc.purchase.webapp.dao.*;
import com.wisrc.purchase.webapp.dto.inspection.*;
import com.wisrc.purchase.webapp.entity.*;
import com.wisrc.purchase.webapp.query.InspectionPageQuery;
import com.wisrc.purchase.webapp.service.*;
import com.wisrc.purchase.webapp.service.externalService.WsRmpWarehouseStockSumService;
import com.wisrc.purchase.webapp.utils.Result;
import com.wisrc.purchase.webapp.utils.ServiceUtils;
import com.wisrc.purchase.webapp.utils.Time;
import com.wisrc.purchase.webapp.utils.Toolbox;
import com.wisrc.purchase.webapp.vo.AddDetailsProdictAllVO;
import com.wisrc.purchase.webapp.vo.inspection.ArrivalProductEditVo;
import com.wisrc.purchase.webapp.vo.inspection.GetArrivalProductVo;
import com.wisrc.purchase.webapp.vo.inspection.InspectionExcelVo;
import com.wisrc.purchase.webapp.vo.inspection.InspectionPageVo;
import com.wisrc.purchase.webapp.vo.invoking.ArrivalSelectorVo;
import com.wisrc.purchase.webapp.vo.invoking.GetQuantityVo;
import com.wisrc.purchase.webapp.vo.returnVO.ArrivalBillReturnVO;
import com.wisrc.purchase.webapp.vo.returnVO.SkuInfoVO;
import com.wisrc.purchase.webapp.vo.syncvo.ArrivalNoticeBillSyncVO;
import com.wisrc.purchase.webapp.vo.syncvo.GoodsInfoVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class InspectionServiceImpl implements InspectionService {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    DecimalFormat df = new DecimalFormat("#.00");
    @Autowired
    private ArrivalBasisInfoDao arrivalBasisInfoDao;
    @Autowired
    private OrderTracingDao orderTracingDao;
    @Autowired
    private OrderDetailsProductInfoDao orderDetailsProductInfoDao;
    @Autowired
    private ArrivalProductDetailsInfoDao arrivalProductDetailsInfoDao;
    @Autowired
    private ProductHandleService productHandleService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private PurchaseEmployeeService purchaseEmployeeService;
    @Autowired
    private InspectionTypeAttrDao inspectionTypeAttrDao;
    @Autowired
    private ArrivalFreightAmrtAttrDao arrivalFreightAmrtAttrDao;
    @Autowired
    private InspectionProductDetailsStatusDao inspectionProductDetailsStatusDao;
    @Autowired
    private PurchcaseOrderBasisInfoService purchcaseOrderBasisInfoService;
    @Autowired
    private WsRmpWarehouseStockSumService wsRmpWarehouseStockSumService;

    @Override
    @Transactional(transactionManager = "retailPurchaseOrderTransactionManager")
    public Result saveInspection(InspectionDto inspectionVo, String userId) {
        WsRmpWarehouseStockSumEntity wsRmpWarehouseStockSumEntity = new WsRmpWarehouseStockSumEntity();
        ArrivalNoticeBillSyncVO wmsArrivalVO = new ArrivalNoticeBillSyncVO();
        List<GoodsInfoVO> goodsList = new ArrayList<>();

        ArrivalBasisInfoEntity inspection = new ArrivalBasisInfoEntity();
        String newId = Toolbox.randomUUID();
        BeanUtils.copyProperties(inspectionVo, inspection);
        inspection.setArrivalId(newId);
        inspection.setCreateTime(Time.getCurrentTimestamp());
        inspection.setCreateUser(userId);
        inspection.setModifyTime(Time.getCurrentTimestamp());
        inspection.setModifyUser(userId);
        inspection.setDeleteStatus(0);

        arrivalBasisInfoDao.saveInspectionBasisInfo(inspection);
        newId = arrivalBasisInfoDao.getInspectionIdByOrder(newId);

        int i = 1;
        StringBuffer stringBuffer = new StringBuffer();
        for (ArrivalProductDetailsInfoEntity inspectionProduct : inspectionVo.getStoreSkuGroup()) {
            GoodsInfoVO goodsInfoVO = new GoodsInfoVO();
            inspectionProduct.setArrivalProductId(Toolbox.randomUUID());
            inspectionProduct.setArrivalId(newId);
            goodsInfoVO.setLineNum(i);
            goodsInfoVO.setGoodsCode(inspectionProduct.getSkuId());
            if (inspectionProduct.getInspectionQuantity() == null) {
                inspectionProduct.setInspectionQuantity(0);
            }
            if (inspectionProduct.getReceiptQuantity() == null) {
                inspectionProduct.setReceiptQuantity(0);
            }
            inspectionProduct.setStatusCd(1);
            goodsInfoVO.setUnitQuantity(inspectionProduct.getDeliveryQuantity() + inspectionProduct.getDeliverySpareQuantity());
            goodsInfoVO.setPackageQuantity(0);
            goodsInfoVO.setTotalQuantity(inspectionProduct.getDeliveryQuantity() + inspectionProduct.getDeliverySpareQuantity());
            goodsList.add(goodsInfoVO);
            arrivalProductDetailsInfoDao.saveInspectionProductDetailsInfo(inspectionProduct);
            stringBuffer.append(inspectionProduct.getSkuId() + ":" + inspectionProduct.getDeliveryQuantity() + ",");
            i++;
        }
        wmsArrivalVO.setVoucherCode(newId);
        wmsArrivalVO.setVoucherType("DH");
        wmsArrivalVO.setCreateTime(Time.getCurrentDateTime());
        wmsArrivalVO.setCreateUser(userId);
        wmsArrivalVO.setPreDeliveryVocuherCode(inspectionVo.getPurchaseOrderId());
        wmsArrivalVO.setSupplierCode(inspectionVo.getSupplierId());
        wmsArrivalVO.setWhCode(inspectionVo.getArrivalWarehouseId());
        String remark = stringBuffer.toString();
        remark = remark.substring(0, remark.length() - 1);
        wmsArrivalVO.setRemark(remark);
        wmsArrivalVO.setGoodsList(goodsList);


        return Result.success(wmsArrivalVO);
    }

    @Override
    @Transactional(transactionManager = "retailPurchaseOrderTransactionManager")
    public Result editInspection(InspectionDto inspectionVo, String arrivalId, String userId) {
        List<String> arrivalProductIds = new ArrayList<>();

        ArrivalBasisInfoEntity inspection = new ArrivalBasisInfoEntity();
        BeanUtils.copyProperties(inspectionVo, inspection);
        inspection.setArrivalId(arrivalId);
        inspection.setModifyUser(userId);
        inspection.setModifyTime(Time.getCurrentTimestamp());

        arrivalBasisInfoDao.editInspectionBasisInfo(inspection);

        List<ArrivalProductDetailsInfoEntity> arrivalProducts = arrivalProductDetailsInfoDao.getProductEditDetails(arrivalId);
        Map<String, Map> idToInspection = new HashMap();
        for (ArrivalProductDetailsInfoEntity arrivalProduct : arrivalProducts) {
            arrivalProductIds.add(arrivalProduct.getArrivalProductId());
            Map statusMap = new HashMap();
            statusMap.put("inspectionQuantity", arrivalProduct.getInspectionQuantity());
            statusMap.put("statusCd", arrivalProduct.getStatusCd());
            idToInspection.put(arrivalProduct.getArrivalProductId(), statusMap);
        }

        for (ArrivalProductDetailsInfoEntity inspectionProduct : inspectionVo.getStoreSkuGroup()) {
            int index = arrivalProductIds.indexOf(inspectionProduct.getArrivalProductId());
            if (index == -1) {
                inspectionProduct.setArrivalId(arrivalId);
                inspectionProduct.setArrivalProductId(UUID.randomUUID().toString().replace("-", "").toLowerCase());
                inspectionProduct.setDeleteStatus(0);
                inspectionProduct.setStatusCd(1);
                arrivalProductDetailsInfoDao.saveInspectionProductDetailsInfo(inspectionProduct);
            } else {
                arrivalProductDetailsInfoDao.editInspectionProductDetailsInfo(inspectionProduct);
                if (idToInspection.get(inspectionProduct.getArrivalProductId()) != null && inspectionProduct.getReceiptQuantity() != null
                        && (Integer) idToInspection.get(inspectionProduct.getArrivalProductId()).get("statusCd") == 2
                        && idToInspection.get(inspectionProduct.getArrivalProductId()).get("inspectionQuantity") == inspectionProduct.getReceiptQuantity()) {
                    arrivalProductDetailsInfoDao.editInspectionProductStatus(3, inspectionProduct.getArrivalProductId(), Time.getCurrentDate());
                }
                arrivalProductIds.remove(index);
            }
        }

        if (arrivalProductIds.size() > 0) {
            arrivalProductDetailsInfoDao.deleteInspectionBasisInfoBatch(arrivalProductIds);
        }

        return Result.success();
    }

    @Override
    @Transactional(transactionManager = "retailPurchaseOrderTransactionManager")
    public Result deleteInspectionProductDetails(List inspectionProductIds) {
        List deleteInspection = new ArrayList();
        List<ArrivalProductDetailsInfoEntity> inspectionProducts;
        Map<String, List<String>> productResultMap = new HashMap();
        Map<String, List<String>> productDeleteMap = new HashMap();

        // 获取到货通知单单号
        try {
            inspectionProducts = arrivalProductDetailsInfoDao.getInspectionIdByProductIds(inspectionProductIds);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        // 对到货单产品和待删除产品按单进行分类
        for (ArrivalProductDetailsInfoEntity inspectionProduct : inspectionProducts) {
            if (productResultMap.get(inspectionProduct.getArrivalProductId()) == null)
                productResultMap.put(inspectionProduct.getArrivalId(), new ArrayList<>());
            productResultMap.get(inspectionProduct.getArrivalId()).add(inspectionProduct.getArrivalProductId());
            if (inspectionProductIds.indexOf(inspectionProduct.getArrivalProductId()) != -1)
                if (productDeleteMap.get(inspectionProduct.getArrivalProductId()) == null)
                    productDeleteMap.put(inspectionProduct.getArrivalId(), new ArrayList<>());
            productDeleteMap.get(inspectionProduct.getArrivalId()).add(inspectionProduct.getArrivalProductId());
        }

        // 如果到货单产品和待删除产品数目一致则表示到货单也删除
        for (String inspectionId : productDeleteMap.keySet()) {
            if (productDeleteMap.get(inspectionId).size() == productResultMap.get(inspectionId).size()) {
                deleteInspection.add(inspectionId);
            }
        }

        // 删除到货单产品
        try {
            arrivalProductDetailsInfoDao.deleteInspectionBasisInfo(inspectionProductIds);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failure();
        }

        // 删除到货单
        if (deleteInspection.size() > 0) {
            try {
                arrivalBasisInfoDao.deleteInspectionByIds(deleteInspection);
            } catch (Exception e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.failure();
            }
        }

        return Result.success();
    }

    @Override
    @Transactional(transactionManager = "retailPurchaseOrderTransactionManager")
    public Result deleteArrival(List arrivalIds) {
        // 删除到货单
        if (arrivalIds.size() > 0) {
            List<String> arrivalProductIds;

            // 获取到货通知单详情号
            try {
                arrivalProductIds = arrivalProductDetailsInfoDao.getProductByArrival(arrivalIds);
            } catch (Exception e) {
                e.printStackTrace();
                return Result.failure();
            }

            // 删除到货单产品
            try {
                arrivalProductDetailsInfoDao.deleteInspectionBasisInfo(arrivalProductIds);
            } catch (Exception e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.failure();
            }

            try {
                arrivalBasisInfoDao.deleteInspectionByIds(arrivalIds);
            } catch (Exception e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.failure();
            }
        }

        return Result.success();
    }

    @Override
    public Result inspectionList(InspectionPageVo inspectionPageVo) {
        InspectionPageDto result = new InspectionPageDto();
        List<InspectionProductResponseDto> inspectionProductList = new ArrayList();
        InspectionPageQuery inspectionPageQuery = new InspectionPageQuery();
        List<InspectionPageEntity> inspectionPageResult;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            BeanUtils.copyProperties(inspectionPageVo, inspectionPageQuery);
            if (inspectionPageVo.getApplyStartDate() != null)
                inspectionPageQuery.setApplyStartDate(new java.sql.Date(sdf.parse(inspectionPageVo.getApplyStartDate()).getTime()));
            if (inspectionPageVo.getApplyEndDate() != null)
                inspectionPageQuery.setApplyEndDate(new java.sql.Date(sdf.parse(inspectionPageVo.getApplyEndDate()).getTime()));
            if (inspectionPageVo.getExpectArrivalStartTime() != null)
                inspectionPageQuery.setExpectArrivalStartTime(new java.sql.Date(sdf.parse(inspectionPageVo.getExpectArrivalStartTime()).getTime()));
            if (inspectionPageVo.getExpectArrivalEndTime() != null)
                inspectionPageQuery.setExpectArrivalEndTime(new java.sql.Date(sdf.parse(inspectionPageVo.getExpectArrivalEndTime()).getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
            return Result.failure(423, "参数解析失败", inspectionPageVo);
        }

        // 根据关键字关联产品筛选商品
        if (inspectionPageVo.getFindKey() != null) {
            try {
                inspectionPageQuery.setSkuIds(productHandleService.getFindKeyDealted(inspectionPageVo.getFindKey()));
            } catch (Exception e) {
                e.printStackTrace();
                return Result.failure();
            }

            try {
                inspectionPageQuery.setSupplierIds(supplierService.getFindKeyDealted(inspectionPageVo.getFindKey()));
            } catch (Exception e) {
                e.printStackTrace();
                return Result.failure();
            }
        }

        try {
            if (inspectionPageVo.getPageNum() != null || inspectionPageVo.getPageSize() != null) {
                PageHelper.startPage(inspectionPageVo.getPageNum(), inspectionPageVo.getPageSize());
            }

            inspectionPageResult = arrivalProductDetailsInfoDao.getInspectionPage(inspectionPageQuery);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
        PageInfo pageInfo = new PageInfo(inspectionPageResult);

        // 关联外部参数并封装返回值
        if (inspectionPageResult.size() > 0) {
            List skuIds = new ArrayList();
            List supplierIds = new ArrayList();
            List employeeIds = new ArrayList();
            Map<String, String> productNameMap = new HashMap();
            Map<String, String> supplierNameMap = new HashMap();
            Map<String, String> employeeNameMap = new HashMap();

            for (InspectionPageEntity getInspectionProduct : inspectionPageResult) {
                skuIds.add(getInspectionProduct.getSkuId());
                supplierIds.add(getInspectionProduct.getSupplierId());
                employeeIds.add(getInspectionProduct.getEmployeeId());
            }

            try {
                productNameMap = productHandleService.getProductCNNameMap(skuIds);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                supplierNameMap = supplierService.getSupplierNameMap(supplierIds);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                employeeNameMap = purchaseEmployeeService.getEmployeeNameMap(employeeIds);
            } catch (Exception e) {
                e.printStackTrace();
            }

            for (InspectionPageEntity getInspectionProduct : inspectionPageResult) {
                InspectionProductResponseDto inspectionProduct = new InspectionProductResponseDto();
                BeanUtils.copyProperties(getInspectionProduct, inspectionProduct);
                if (getInspectionProduct.getEstimateArrivalDate() != null) {
                    inspectionProduct.setExpectArrivalTime(getInspectionProduct.getEstimateArrivalDate().toString());
                }

                inspectionProduct.setOrderId(getInspectionProduct.getPurchaseOrderId());
                inspectionProduct.setEmployeeName(employeeNameMap.get(getInspectionProduct.getEmployeeId()));
                inspectionProduct.setSupplierName(supplierNameMap.get(getInspectionProduct.getSupplierId()));
                inspectionProduct.setProductName(productNameMap.get(getInspectionProduct.getSkuId()));
                inspectionProduct.setApplyDate(sdf.format(getInspectionProduct.getApplyDate()));
                inspectionProduct.setStatusCd(getInspectionProduct.getStatusCd());
                if (getInspectionProduct.getEstimateArrivalDate() != null)
                    inspectionProduct.setExpectArrivalTime(sdf.format(getInspectionProduct.getEstimateArrivalDate()));

                inspectionProductList.add(inspectionProduct);
            }
        }

        result.setInspectionProductList(inspectionProductList);
        result.setTotal(pageInfo.getTotal());
        result.setPages(pageInfo.getPages());
        return Result.success(result);
    }

    @Override
    public Result getInspection(String inspectionId) {
        GetInspectionEntity getInspection = new GetInspectionEntity();
        List<GetInspectionProduct> getInspectionProductList = new ArrayList<>();
        GetInspectionDto result = new GetInspectionDto();
        List<GetInspectionProductDto> inspectionProductResult = new ArrayList<>();
        List skuIds = new ArrayList();
        List supplierIds = new ArrayList();
        List employeeIds = new ArrayList();
        Map<String, String> productNameMap = new HashMap();
        Map<String, String> supplierNameMap = new HashMap();
        Map<String, String> employeeNameMap = new HashMap();

        try {
            getInspection = arrivalBasisInfoDao.getInspectionById(inspectionId);
            getInspectionProductList = arrivalProductDetailsInfoDao.getInspectionProductByInspectionId(inspectionId);
            if (getInspection == null) {
                return Result.failure();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        // 获取sku关联信息
        for (GetInspectionProduct getInspectionProduct : getInspectionProductList) {
            skuIds.add(getInspectionProduct.getSkuId());
        }
        supplierIds.add(getInspection.getSupplierId());
        employeeIds.add(getInspection.getEmployeeId());

        try {
            productNameMap = productHandleService.getProductCNNameMap(skuIds);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            supplierNameMap = supplierService.getSupplierNameMap(supplierIds);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            employeeNameMap = purchaseEmployeeService.getEmployeeNameMap(employeeIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<AddDetailsProdictAllVO> skuDetailList = purchcaseOrderBasisInfoService.findDetailsList(getInspection.getPurchaseOrderId());
        Map<String, Map<String, Double>> skuDetailMap = new HashMap();
        for (AddDetailsProdictAllVO skuDetail : skuDetailList) {
            Map<String, Double> sku = new HashMap();
            sku.put("spareRate", skuDetail.getSpareRate());
            sku.put("packVolume", skuDetail.getProducPackingInfoEn().getPackVolume());
            sku.put("grossWeight", skuDetail.getProducPackingInfoEn().getGrossWeight());
            skuDetailMap.put(skuDetail.getSkuId(), sku);
        }

        // 封装sku信息
        for (GetInspectionProduct getInspectionProduct : getInspectionProductList) {
            GetInspectionProductDto inspectionProduct = new GetInspectionProductDto();
            BeanUtils.copyProperties(getInspectionProduct, inspectionProduct);
            inspectionProduct.setProductName(productNameMap.get(getInspectionProduct.getSkuId()));
            inspectionProduct.setStatus(getInspectionProduct.getStatusCd());
            if (getInspectionProduct.getFreight() != null) {
                inspectionProduct.setFreight(getInspectionProduct.getFreight().toString());
            }
            Map<String, Double> skuDetail = skuDetailMap.get(getInspectionProduct.getSkuId());
            if (skuDetailMap.get(getInspectionProduct.getSkuId()) != null) {
                inspectionProduct.setSpareRate(skuDetail.get("spareRate"));
                inspectionProduct.setPackVolume(skuDetail.get("packVolume"));
                inspectionProduct.setGrossWeight(skuDetail.get("grossWeight"));
            }
//          退货相关数据
            List<OrderTracingReturnEnity> returnEnityList = orderTracingDao.getTracingReturnEnity(getInspectionProduct.getSkuId(), getInspection.getPurchaseOrderId());
//          入库相关
            List<OrderTracingWareHouseEnity> orderTracingWareHouseList = orderTracingDao.getTracingWareHouseEnity(getInspectionProduct.getSkuId(), getInspection.getPurchaseOrderId());
            OrderDetailsProductInfoEntity orderDetailsProductInfoEntity = orderDetailsProductInfoDao.getByOrderIdAndSkuId(getInspectionProduct.getSkuId(), getInspection.getPurchaseOrderId());
            int returnNum = 0;
            int wareHouseNum = 0;
            int quantity = 0;
            if (orderDetailsProductInfoEntity != null) {
                quantity = orderDetailsProductInfoEntity.getQuantity();
            }
            for (OrderTracingReturnEnity returnEnity : returnEnityList) {
                returnNum += returnEnity.getReturnQuantity();
            }
            for (OrderTracingWareHouseEnity wareHouseEnity : orderTracingWareHouseList) {
                wareHouseNum += wareHouseEnity.getEntryNum();
            }
            inspectionProduct.setWarehouseOweNum(quantity - wareHouseNum + returnNum);
            inspectionProductResult.add(inspectionProduct);
        }

        // 封装验货通知
        BeanUtils.copyProperties(getInspection, result);
        result.setOrderId(getInspection.getPurchaseOrderId());
        if (getInspection.getEstimateArrivalDate() != null) {
            result.setExpectArrivalTime(getInspection.getEstimateArrivalDate().getTime());
        }
        result.setHaulageTime(getInspection.getHaulageDays());
        result.setFreightApportionCd(ServiceUtils.idAndName(getInspection.getFreightApportionCd(), getInspection.getFreightApportionDesc()));
        result.setApplyDate(getInspection.getApplyDate().getTime());
        if (getInspection.getFreight() != null) result.setFreight(getInspection.getFreight().doubleValue());
        if (getInspection.getEstimateArrivalDate() != null) {
            result.setExpectArrivalTime(getInspection.getEstimateArrivalDate().getTime());
        }
        result.setEmployee(ServiceUtils.idAndName(getInspection.getEmployeeId(), employeeNameMap.get(getInspection.getEmployeeId())));
        result.setSupplier(ServiceUtils.idAndName(getInspection.getSupplierId(), supplierNameMap.get(getInspection.getSupplierId())));

        result.setInspectionProduct(inspectionProductResult);
        return Result.success(result);
    }

    @Override
    public Result excel(InspectionExcelVo inspectionExcelVo, HttpServletRequest request, HttpServletResponse response) {
        InspectionExcelDto result = new InspectionExcelDto();
        List<InspectionProductExcelResponseDto> pageList = new ArrayList<>();
        List<InspectionPageEntity> inspectionPageResult;

        try {
            inspectionPageResult = arrivalProductDetailsInfoDao.getInspectionPageInId(inspectionExcelVo.getArrivalProductId());
            if (inspectionPageResult.size() > 0) {
                List<InspectionProductExcelResponseDto> inspectionProductList = new ArrayList();
                List skuIds = new ArrayList();
                List supplierIds = new ArrayList();
                List employeeIds = new ArrayList();
                Map<String, String> productNameMap = new HashMap();
                Map<String, String> supplierNameMap = new HashMap();
                Map<String, String> employeeNameMap = new HashMap();

                for (InspectionPageEntity getInspectionProduct : inspectionPageResult) {
                    skuIds.add(getInspectionProduct.getSkuId());
                    supplierIds.add(getInspectionProduct.getSupplierId());
                    employeeIds.add(getInspectionProduct.getEmployeeId());
                }

                try {
                    productNameMap = productHandleService.getProductCNNameMap(skuIds);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    supplierNameMap = supplierService.getSupplierNameMap(supplierIds);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    employeeNameMap = purchaseEmployeeService.getEmployeeNameMap(employeeIds);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                for (InspectionPageEntity getInspectionProduct : inspectionPageResult) {
                    InspectionProductExcelResponseDto inspectionProduct = new InspectionProductExcelResponseDto();
                    BeanUtils.copyProperties(getInspectionProduct, inspectionProduct);
                    if (getInspectionProduct.getEstimateArrivalDate() != null)
                        inspectionProduct.setExpectArrivalTime(getInspectionProduct.getEstimateArrivalDate().toString());
                    inspectionProduct.setOrderId(getInspectionProduct.getPurchaseOrderId());
                    inspectionProduct.setEmployeeName(employeeNameMap.get(getInspectionProduct.getEmployeeId()));
                    inspectionProduct.setSupplierName(supplierNameMap.get(getInspectionProduct.getSupplierId()));
                    inspectionProduct.setProductName(productNameMap.get(getInspectionProduct.getSkuId()));
                    inspectionProduct.setApplyDate(sdf.format(getInspectionProduct.getApplyDate()));

                    inspectionProductList.add(inspectionProduct);
                }
                pageList = inspectionProductList;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        result.setInspectionProductList(pageList);
        return Result.success(result);
    }

    @Override
    public Result inspectionTypeSelector() {
        Map result = new HashMap();
        List inspectionTypeSelector = new ArrayList();
        List<InspectionTypeAttrEntity> inspectionTypeResult;
        try {
            inspectionTypeResult = inspectionTypeAttrDao.getInspectionTypeAttr();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        for (InspectionTypeAttrEntity inspectionTypeAttrEntity : inspectionTypeResult) {
            Map inspectionType = ServiceUtils.idAndName(inspectionTypeAttrEntity.getInspectionTypeCd(), inspectionTypeAttrEntity.getInspectionTypeDesc());
            inspectionTypeSelector.add(inspectionType);
        }

        result.put("inspectionTypeSelector", inspectionTypeSelector);
        return Result.success(result);
    }

    @Override
    public Result freightAmrtTypeSelector() {
        Map result = new HashMap();
        List freightAmrtTypeSelector = new ArrayList();
        List<ArrivalFreightAmrtAttrEntity> freightAmrtTypeResult;
        try {
            freightAmrtTypeResult = arrivalFreightAmrtAttrDao.freightAmrtTypeSelector();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        for (ArrivalFreightAmrtAttrEntity freightAmrtTypeAttrEntity : freightAmrtTypeResult) {
            Map freightAmrtType = ServiceUtils.idAndName(freightAmrtTypeAttrEntity.getFreightApportionCd(), freightAmrtTypeAttrEntity.getFreightApportionDesc());
            freightAmrtTypeSelector.add(freightAmrtType);
        }

        result.put("freightAmrtTypeSelector", freightAmrtTypeSelector);
        return Result.success(result);
    }

    @Override
    public Result productStatusSelector() {
        Map result = new HashMap();
        List detailsStatus = new ArrayList();
        List<InspectionProductDetailsStatusAttrEntity> detailsStatusResult;

        try {
            detailsStatusResult = inspectionProductDetailsStatusDao.detailsStatusSelector();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        for (InspectionProductDetailsStatusAttrEntity getDetailsStatus : detailsStatusResult) {
            detailsStatus.add(ServiceUtils.idAndName(getDetailsStatus.getStatusCd(), getDetailsStatus.getStatusDesc()));
        }

        result.put("detailsStatus", detailsStatus);
        return Result.success(result);
    }

    @Override
    public Result inspectionSelector(ArrivalSelectorVo arrivalSelectorVo) {
        try {
            List<String> inspectionSelector = arrivalBasisInfoDao.getInspectionSelector(arrivalSelectorVo.getPurchaseOrderId());
            return Result.success(inspectionSelector);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }

    @Override
    public Result getArrivalProduct(GetArrivalProductVo getArrivalProductVo) {
        GetArrivalProductPageDto result = new GetArrivalProductPageDto();
        List<GetArrivalProductDto> arrivalProductList = new ArrayList<>();

        InspectionPageQuery inspectionPageQuery = new InspectionPageQuery();
        BeanUtils.copyProperties(getArrivalProductVo, inspectionPageQuery);
        // 根据关键字关联产品筛选商品
        if (getArrivalProductVo.getProductNameCN() != null) {
            try {
                inspectionPageQuery.setSkuIds(productHandleService.getByNameToIds(getArrivalProductVo.getProductNameCN()));
            } catch (Exception e) {
                e.printStackTrace();
                return Result.failure();
            }
        }

        if (getArrivalProductVo.getSupplierName() != null) {
            try {
                inspectionPageQuery.setSupplierIds(supplierService.getFindKeyDealted(getArrivalProductVo.getSupplierName()));
            } catch (Exception e) {
                e.printStackTrace();
                return Result.failure();
            }
        }

        // 查询返回数据
        List<InspectionPageEntity> arrivalProducts;
        try {
            if (getArrivalProductVo.getPageNum() != null && getArrivalProductVo.getPageSize() != null) {
                PageHelper.startPage(getArrivalProductVo.getPageNum(), getArrivalProductVo.getPageSize());
            }
            arrivalProducts = arrivalProductDetailsInfoDao.getArrivalProduct(inspectionPageQuery);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
        if (arrivalProducts.size() == 0) {
            return Result.success(result);
        }

        // 获取关联数据
        List supplierIds = new ArrayList();
        List products = new ArrayList();
        List employeeIds = new ArrayList();
        for (InspectionPageEntity arrivalProduct : arrivalProducts) {
            supplierIds.add(arrivalProduct.getSupplierId());
            products.add(arrivalProduct.getSkuId());
            employeeIds.add(arrivalProduct.getEmployeeId());
        }

        Map<String, String> productNameMap = new HashMap<>();
        try {
            productNameMap = productHandleService.getProductCNNameMap(products);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, String> supplierNameMap = new HashMap<>();
        try {
            supplierNameMap = supplierService.getSupplierNameMap(supplierIds);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, String> employeeNameMap = new HashMap<>();
        try {
            employeeNameMap = purchaseEmployeeService.getEmployeeNameMap(employeeIds);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 封装返回数据
        for (InspectionPageEntity arrivalProduct : arrivalProducts) {
            GetArrivalProductDto getArrivalProductDto = new GetArrivalProductDto();
            BeanUtils.copyProperties(arrivalProduct, getArrivalProductDto);
            if (arrivalProduct.getEstimateArrivalDate() != null) {
                getArrivalProductDto.setExpectArrivalTime(sdf.format(arrivalProduct.getEstimateArrivalDate()));
            }
            getArrivalProductDto.setOrderId(arrivalProduct.getPurchaseOrderId());
            getArrivalProductDto.setProductName(productNameMap.get(arrivalProduct.getSkuId()));
            getArrivalProductDto.setSupplierName(supplierNameMap.get(arrivalProduct.getSupplierId()));
            getArrivalProductDto.setEmployeeName(employeeNameMap.get(arrivalProduct.getEmployeeId()));
            getArrivalProductDto.setReceiptQuantity(arrivalProduct.getReceiptQuantity());
            getArrivalProductDto.setReceiptSpareQuantity(arrivalProduct.getReceiptSpareQuantity());
            arrivalProductList.add(getArrivalProductDto);
        }

        PageInfo pageInfo = new PageInfo(arrivalProducts);
        result.setArrivalProducts(arrivalProductList);
        result.setPages(pageInfo.getPages());
        result.setTotal(pageInfo.getTotal());
        return Result.success(result);
    }

    @Override
    @Transactional(transactionManager = "retailPurchaseOrderTransactionManager")
    public Result editArrivalProduct(ArrivalProductEditVo arrivalProductEditVo) {
        try {
            ArrivalProductDetailsInfoEntity arrivalProductDetails = new ArrivalProductDetailsInfoEntity();
            BeanUtils.copyProperties(arrivalProductEditVo, arrivalProductDetails);
            arrivalProductDetailsInfoDao.editArrivalProduct(arrivalProductDetails);
            ArrivalProductDetailsInfoEntity arrivalProduct = arrivalProductDetailsInfoDao.getInspection(arrivalProductDetails.getArrivalProductId());
            Map<String, Map> idToInspection = new HashMap();
            if (arrivalProduct.getInspectionQuantity() == null && arrivalProductDetails.getReceiptQuantity() != null
                    && arrivalProduct.getInspectionQuantity() == arrivalProductDetails.getReceiptQuantity() && arrivalProduct.getStatusCd() == 2) {
                arrivalProductDetailsInfoDao.editInspectionProductStatus(3, arrivalProductDetails.getArrivalProductId(), Time.getCurrentDate());
            }
            return Result.success();
        } catch (BeansException e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failure();
        }
    }

    @Override
    public Result changeStatus(String arrivalId, String skuId, Integer statusCd) {
        try {
            arrivalBasisInfoDao.changeStatus(arrivalId, skuId, statusCd, Time.getCurrentTimestamp());
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(425, "修改到货通知单失败，请刷新后重试", arrivalId + ":" + skuId);
        }
    }

    @Override
    public Result getQuantity(GetQuantityVo getQuantityVo) {
        try {
            List<GetQuantityDto> getQuantity = new ArrayList<>();
            List<OrderDetailsProductInfoEntity> orderDetails = orderDetailsProductInfoDao.getQuantity(getQuantityVo.getOrderId(), getQuantityVo.getSkuId());
            for (OrderDetailsProductInfoEntity orderDetail : orderDetails) {
                GetQuantityDto getQuantityDto = new GetQuantityDto();
                BeanUtils.copyProperties(orderDetail, getQuantityDto);

                getQuantity.add(getQuantityDto);
            }

            return Result.success(getQuantity);
        } catch (BeansException e) {
            e.printStackTrace();
            return Result.failure();
        }
    }

    public Integer getOweNum(String orderId, String skuId) {
        List<OrderTracingReturnEnity> returnEnityList = orderTracingDao.getTracingReturnEnity(skuId, orderId);
        List<OrderTracingWareHouseEnity> orderTracingWareHouseList = orderTracingDao.getTracingWareHouseEnity(skuId, orderId);
        OrderDetailsProductInfoEntity orderDetailsProductInfoEntity = orderDetailsProductInfoDao.getByOrderIdAndSkuId(skuId, orderId);
        int returnNum = 0;
        int wareHouseNum = 0;
        int quantity = 0;
        if (orderDetailsProductInfoEntity != null) {
            quantity = orderDetailsProductInfoEntity.getQuantity();
        }
        for (OrderTracingReturnEnity returnEnity : returnEnityList) {
            returnNum += returnEnity.getReturnQuantity();
        }
        for (OrderTracingWareHouseEnity wareHouseEnity : orderTracingWareHouseList) {
            wareHouseNum += wareHouseEnity.getEntryNum();
        }
        return quantity - wareHouseNum + returnNum;
    }


    @Override
    @Transactional(transactionManager = "retailPurchaseOrderTransactionManager")
    public Result updateWmsReturnData(ArrivalBillReturnVO entity) {
        //到货单号
        String arrivalId = entity.getArrivalId();
        List<SkuInfoVO> arrivalSkuList = entity.getArrivalSkuList();
        for (SkuInfoVO skuInfoVO : arrivalSkuList) {
            int deliveryQuantity = 0;
            try {
                deliveryQuantity = arrivalProductDetailsInfoDao.getDeliveryQuantity(arrivalId, skuInfoVO.getSkuId());
            } catch (Exception e) {
                throw new RuntimeException("到货通知单不存在，或者到货通知单里面不存在sku");
            }
            if (skuInfoVO.getQuantity() > deliveryQuantity) {
                orderDetailsProductInfoDao.updateWmsData(arrivalId,
                        skuInfoVO.getSkuId(), deliveryQuantity, (skuInfoVO.getQuantity() - deliveryQuantity));
                arrivalBasisInfoDao.changeStatus(arrivalId, skuInfoVO.getSkuId(), 2, Time.getCurrentTimestamp());
            } else {
                orderDetailsProductInfoDao.updateWmsData(arrivalId, skuInfoVO.getSkuId(), skuInfoVO.getQuantity(), 0);
                arrivalBasisInfoDao.changeStatus(arrivalId, skuInfoVO.getSkuId(), 2, Time.getCurrentTimestamp());
            }
        }
        return Result.success();
    }

    public String inspectionIdRule() {
        String id = "AN" + new SimpleDateFormat("yy").format(new Date());
        return id;
    }

    @Override
    @Transactional(transactionManager = "retailPurchaseOrderTransactionManager")
    public String getInspectionIdByOrder(String randomValue) {
        return arrivalBasisInfoDao.getInspectionIdByOrder(randomValue);
    }

    @Override
    public Integer getStatusByArrivalIdAndSkuId(String arrivalId, String skuId) {
        return inspectionProductDetailsStatusDao.getStatusByArrivalIdAndSkuId(arrivalId, skuId);
    }
}
