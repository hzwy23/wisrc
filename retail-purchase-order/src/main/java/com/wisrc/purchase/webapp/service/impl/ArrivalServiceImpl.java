package com.wisrc.purchase.webapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisrc.purchase.webapp.dao.ArrivalBasisInfoDao;
import com.wisrc.purchase.webapp.dao.ArrivalProductDetailsInfoDao;
import com.wisrc.purchase.webapp.dao.OrderDetailsProductInfoDao;
import com.wisrc.purchase.webapp.dao.OrderTracingDao;
import com.wisrc.purchase.webapp.dto.inspection.*;
import com.wisrc.purchase.webapp.entity.*;
import com.wisrc.purchase.webapp.query.ArrivalPageQuery;
import com.wisrc.purchase.webapp.service.*;
import com.wisrc.purchase.webapp.utils.Result;
import com.wisrc.purchase.webapp.utils.ServiceUtils;
import com.wisrc.purchase.webapp.vo.AddDetailsProdictAllVO;
import com.wisrc.purchase.webapp.vo.inspection.ArrivalExcelVo;
import com.wisrc.purchase.webapp.vo.inspection.ArrivalPageVo;
import com.wisrc.purchase.webapp.vo.product.GetProductInfoVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArrivalServiceImpl implements ArrivalService {
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
    private PurchcaseOrderBasisInfoService purchcaseOrderBasisInfoService;

    @Override
    public Result arrivalList(ArrivalPageVo arrivalPageVo) {
        ArrivalPageDto result = new ArrivalPageDto();
        List<ArrivalProductResponseDto> inspectionProductList = new ArrayList();
        ArrivalPageQuery inspectionPageQuery = new ArrivalPageQuery();
        List<ArrivalPageEntity> inspectionPageResult;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            BeanUtils.copyProperties(arrivalPageVo, inspectionPageQuery);
            if (arrivalPageVo.getApplyStartDate() != null)
                inspectionPageQuery.setApplyStartDate(new java.sql.Date(sdf.parse(arrivalPageVo.getApplyStartDate()).getTime()));
            if (arrivalPageVo.getApplyEndDate() != null)
                inspectionPageQuery.setApplyEndDate(new java.sql.Date(sdf.parse(arrivalPageVo.getApplyEndDate()).getTime()));
            if (arrivalPageVo.getExpectArrivalStartTime() != null)
                inspectionPageQuery.setExpectArrivalStartTime(new java.sql.Date(sdf.parse(arrivalPageVo.getExpectArrivalStartTime()).getTime()));
            if (arrivalPageVo.getExpectArrivalEndTime() != null)
                inspectionPageQuery.setExpectArrivalEndTime(new java.sql.Date(sdf.parse(arrivalPageVo.getExpectArrivalEndTime()).getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
            return Result.failure(423, "参数解析失败", "");
        }

        // 根据关键字关联产品筛选商品
        if (arrivalPageVo.getSkuId() != null || arrivalPageVo.getProductName() != null) {
            try {
                GetProductInfoVO getProductInfoVO = new GetProductInfoVO();
                getProductInfoVO.setSkuId(arrivalPageVo.getSkuId());
                getProductInfoVO.setSkuNameZh(arrivalPageVo.getProductName());

                inspectionPageQuery.setSkuIds(productHandleService.getProduct(getProductInfoVO));
            } catch (Exception e) {
                e.printStackTrace();
                return Result.failure();
            }
        }

        if (arrivalPageVo.getFindKey() != null) {
            try {
                inspectionPageQuery.setSupplierIds(supplierService.getFindKeyDealted(arrivalPageVo.getFindKey()));
            } catch (Exception e) {
                e.printStackTrace();
                return Result.failure();
            }
        }

        try {
            if (arrivalPageVo.getPageNum() != null || arrivalPageVo.getPageSize() != null) {
                PageHelper.startPage(arrivalPageVo.getPageNum(), arrivalPageVo.getPageSize());
            }

            inspectionPageResult = arrivalProductDetailsInfoDao.getArrivalPage(inspectionPageQuery);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
        PageInfo pageInfo = new PageInfo(inspectionPageResult);

        // 关联外部参数并封装返回值
        if (inspectionPageResult.size() > 0) {
            List supplierIds = new ArrayList();
            List employeeIds = new ArrayList();
            Map<String, String> supplierNameMap = new HashMap();
            Map<String, String> employeeNameMap = new HashMap();

            for (ArrivalPageEntity getInspectionProduct : inspectionPageResult) {
                supplierIds.add(getInspectionProduct.getSupplierId());
                employeeIds.add(getInspectionProduct.getEmployeeId());
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

            for (ArrivalPageEntity getInspectionProduct : inspectionPageResult) {
                ArrivalProductResponseDto inspectionProduct = new ArrivalProductResponseDto();
                BeanUtils.copyProperties(getInspectionProduct, inspectionProduct);
                if (getInspectionProduct.getEstimateArrivalDate() != null) {
                    inspectionProduct.setExpectArrivalTime(getInspectionProduct.getEstimateArrivalDate().toString());
                }

                inspectionProduct.setOrderId(getInspectionProduct.getPurchaseOrderId());
                inspectionProduct.setEmployeeName(employeeNameMap.get(getInspectionProduct.getEmployeeId()));
                inspectionProduct.setSupplierName(supplierNameMap.get(getInspectionProduct.getSupplierId()));
                inspectionProduct.setApplyDate(sdf.format(getInspectionProduct.getApplyDate()));
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
    public Result excel(ArrivalExcelVo arrivalExcelVo, HttpServletRequest request, HttpServletResponse response) {
        ArrivalExcelDto result = new ArrivalExcelDto();
        List<ArrivalProductResponseDto> inspectionProductList = new ArrayList();
        List<ArrivalPageEntity> inspectionPageResult;

        try {
            inspectionPageResult = arrivalProductDetailsInfoDao.getArrivalPageInId(arrivalExcelVo.getArrivalId());
            if (inspectionPageResult.size() > 0) {
                List supplierIds = new ArrayList();
                List employeeIds = new ArrayList();
                Map<String, String> supplierNameMap = new HashMap();
                Map<String, String> employeeNameMap = new HashMap();

                for (ArrivalPageEntity getInspectionProduct : inspectionPageResult) {
                    supplierIds.add(getInspectionProduct.getSupplierId());
                    employeeIds.add(getInspectionProduct.getEmployeeId());
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

                for (ArrivalPageEntity getInspectionProduct : inspectionPageResult) {
                    ArrivalProductResponseDto inspectionProduct = new ArrivalProductResponseDto();
                    BeanUtils.copyProperties(getInspectionProduct, inspectionProduct);
                    if (getInspectionProduct.getEstimateArrivalDate() != null)
                        inspectionProduct.setExpectArrivalTime(getInspectionProduct.getEstimateArrivalDate().toString());
                    inspectionProduct.setOrderId(getInspectionProduct.getPurchaseOrderId());
                    inspectionProduct.setEmployeeName(employeeNameMap.get(getInspectionProduct.getEmployeeId()));
                    inspectionProduct.setSupplierName(supplierNameMap.get(getInspectionProduct.getSupplierId()));
                    inspectionProduct.setApplyDate(sdf.format(getInspectionProduct.getApplyDate()));

                    inspectionProductList.add(inspectionProduct);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        result.setInspectionProductList(inspectionProductList);
        return Result.success(result);
    }

    @Override
    public Result getArrival(String arrivalId) {
        GetInspectionEntity getInspection = new GetInspectionEntity();
        List<GetArrivalProduct> getInspectionProductList = new ArrayList<>();
        GetArrivalDto result = new GetArrivalDto();
        List<GetArrivalProductTwoDto> inspectionProductResult = new ArrayList<>();
        List skuIds = new ArrayList();
        List supplierIds = new ArrayList();
        List employeeIds = new ArrayList();
        Map<String, String> productNameMap = new HashMap();
        Map<String, String> supplierNameMap = new HashMap();
        Map<String, String> employeeNameMap = new HashMap();

        try {
            getInspection = arrivalBasisInfoDao.getInspectionById(arrivalId);
            getInspectionProductList = arrivalProductDetailsInfoDao.getArrivalProductByInspectionId(arrivalId);
            if (getInspection == null) {
                return Result.failure();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        // 获取sku关联信息
        for (GetArrivalProduct getInspectionProduct : getInspectionProductList) {
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
        for (GetArrivalProduct getInspectionProduct : getInspectionProductList) {
            GetArrivalProductTwoDto inspectionProduct = new GetArrivalProductTwoDto();
            BeanUtils.copyProperties(getInspectionProduct, inspectionProduct);
            inspectionProduct.setProductName(productNameMap.get(getInspectionProduct.getSkuId()));
            inspectionProduct.setStatus(getInspectionProduct.getStatusDesc());
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

        // 封装到货通知
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
}
