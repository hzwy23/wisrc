package com.wisrc.purchase.webapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.wisrc.purchase.webapp.dao.OrderDetailsProductInfoDao;
import com.wisrc.purchase.webapp.dao.OrderTracingDao;
import com.wisrc.purchase.webapp.entity.*;
import com.wisrc.purchase.webapp.service.OrderTracingService;
import com.wisrc.purchase.webapp.service.externalService.*;
import com.wisrc.purchase.webapp.utils.DateUtil;
import com.wisrc.purchase.webapp.utils.PageData;
import com.wisrc.purchase.webapp.utils.Result;
import com.wisrc.purchase.webapp.vo.OrderTracingCompleteVo;
import com.wisrc.purchase.webapp.vo.OrderTracingEntryVo;
import com.wisrc.purchase.webapp.vo.OrderTracingReturnVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Transactional(transactionManager = "retailPurchaseOrderTransactionManager")
@Service
public class OrderTracingServiceImpl implements OrderTracingService {
    @Autowired
    private OrderTracingDao orderTracingDao;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private InspectionService inspectionService;
    @Autowired
    private MskuService mskuService;
    @Autowired
    private OrderDetailsProductInfoDao orderDetailsProductInfoDao;

    @Override
    public LinkedHashMap findTracingOrderByCond(String pageNum, String pageSize, String orderId, String startTime, String endTime, String employeeId, String supplierId, int tiicketOpenCd, int customsTypeCd, String sku, int deliveryTypeCd, String keywords, String skuName) throws ParseException {
        List<OrderTracingDetailEnity> tracingList = null;
        List<String> skuIdList = new ArrayList<>();
        if (StringUtils.isNotEmpty(skuName)) {
            Result mskuResult = productSkuService.getProductSkuInfo(null, null, skuName);
            if (mskuResult.getCode() == 200) {
                Map objectsMap = (Map) mskuResult.getData();
                List skuList = (List) objectsMap.get("productData");
                for (Object object : skuList) {
                    Map conditonMap = (Map) object;
                    if (conditonMap != null) {
                        if (StringUtils.isNotEmpty((String) conditonMap.get("sku"))) {
                            skuIdList.add((String) conditonMap.get("sku"));
                        }
                    }
                }
            }
        }
        String skuIds = "";
        for (String skuId : skuIdList) {
            skuIds += "'" + skuId + "'" + ",";
        }
        if (skuIds.endsWith(",")) {
            int index = skuIds.lastIndexOf(",");
            skuIds = skuIds.substring(0, index);
        }
        PageInfo info = new PageInfo();
//      存储产品中文名
        Map<String, String> productCnMap = new HashMap<>();
//      存储供应商名
        Map<String, String> suppliMap = new HashMap<>();
//      存储采购员名称
        Map<String, String> employers = new HashMap<>();
//      分页查询
        if (pageNum != null && pageSize != null) {
            int startPage = Integer.parseInt(pageNum);
            int size = Integer.parseInt(pageSize);
            if (skuName != null) {
                if (StringUtils.isEmpty(skuIds)) {
                    tracingList = null;
                } else {
                    PageHelper.startPage(startPage, size);
                    tracingList = orderTracingDao.findTracingOrderByCond(orderId, startTime, endTime, employeeId, supplierId, tiicketOpenCd, customsTypeCd, sku, deliveryTypeCd, keywords, skuIds);
                }
                info = new PageInfo(tracingList);
            } else {
                PageHelper.startPage(startPage, size);
                tracingList = orderTracingDao.findTracingOrderByCond(orderId, startTime, endTime, employeeId, supplierId, tiicketOpenCd, customsTypeCd, sku, deliveryTypeCd, keywords, null);
                info = new PageInfo(tracingList);
            }
        }
//      查询全部
        else {
            if (skuName != null) {
                if (StringUtils.isEmpty(skuIds)) {
                    tracingList = null;
                } else {
                    tracingList = orderTracingDao.findTracingOrderByCond(orderId, startTime, endTime, employeeId, supplierId, tiicketOpenCd, customsTypeCd, sku, deliveryTypeCd, keywords, skuIds);
                }
            } else {
                tracingList = orderTracingDao.findTracingOrderByCond(orderId, startTime, endTime, employeeId, supplierId, tiicketOpenCd, customsTypeCd, sku, deliveryTypeCd, keywords, null);
            }
        }
//      查询订单关联的其它订单数据
        dealTracingList(tracingList, productCnMap, suppliMap, employers);
        if (pageNum != null && pageSize != null) {
            return PageData.pack(info.getTotal(), info.getPages(), "orderBasisInfoList", tracingList);
        } else {
            return PageData.pack(-1, -1, "orderBasisInfoList", tracingList);
        }
    }

    private void dealTracingList(List<OrderTracingDetailEnity> tracingList, Map<String, String> productCnMap, Map<String, String> suppliMap, Map<String, String> employers) {
        HashSet<String> skuSet = new HashSet<>();
        HashSet<String> supplierSet = new HashSet<>();
        HashSet<String> employeeSet = new HashSet<>();
        HashMap<String, Object> inpectMap = new HashMap<>();
        List<OrderTracingCompleteVo> list = new ArrayList<>();
        List<Map> mapList = new ArrayList<>();
        Map<String, Object> objectMap = new HashMap<>();
        for (OrderTracingDetailEnity orderTracingDetailEnity : tracingList) {
            orderTracingDetailEnity.setDeliveryTypeCd(toStatus(orderTracingDetailEnity.getSkuId(), orderTracingDetailEnity.getOrderId()));
            Map<String, String> parameterMap = new HashMap<>();
            parameterMap.put("orderId", orderTracingDetailEnity.getOrderId());
            parameterMap.put("skuId", orderTracingDetailEnity.getSkuId());
            mapList.add(parameterMap);
        }
        objectMap.put("orderIdListVOList", mapList);
        Gson gson = new Gson();
        Result inspectionResult = inspectionService.getInspectionCompleteInfo(gson.toJson(objectMap));
        if (inspectionResult.getCode() == 200) {
            Map map = (Map) inspectionResult.getData();
            List<Map> resultList = (List<Map>) map.get("inspectionInfo");
            for (Map inspectionMap : resultList) {
                Integer completeNum = (Integer) inspectionMap.get("completeNum");
                String orderId = (String) inspectionMap.get("orderId");
                String skuId = (String) inspectionMap.get("skuId");
                inpectMap.put(orderId + skuId, completeNum);
            }
        }
        for (OrderTracingDetailEnity orderTracingDetailEnity : tracingList) {
            String skuId = orderTracingDetailEnity.getSkuId();
            String tracingId = orderTracingDetailEnity.getOrderId();
            OrderDetailsProductInfoEntity orderDetailsProductInfoEntity = orderDetailsProductInfoDao.findInfoByIdAndSkuId(skuId, tracingId);
            if (orderDetailsProductInfoEntity == null) {
                continue;
            }
            if (orderDetailsProductInfoEntity.getDeleteStatus() == 1) {
                orderTracingDetailEnity.setDeliveryTypeCd(4);
            }
            orderTracingDetailEnity.setAmountWithoutTax(orderDetailsProductInfoEntity.getAmountWithoutTax());
            orderTracingDetailEnity.setAmountWithTax(orderDetailsProductInfoEntity.getAmountWithTax());
//          拒收相关数据
            List<OrderTracingRejectionEnity> rejectionEnityList = orderTracingDao.getTracingRejection(skuId, tracingId);
//          退货相关数据
            List<OrderTracingReturnEnity> returnEnityList = orderTracingDao.getTracingReturnEnity(skuId, tracingId);
//          入库相关
            List<OrderTracingWareHouseEnity> orderTracingWareHouseList = orderTracingDao.getTracingWareHouseEnity(skuId, tracingId);
//          到货相关
            List<OrderTracingArrivalEnity> arrivalEnityList = orderTracingDao.getTracingArrival(skuId, tracingId);
//          查询交货日期和数量
            List<ProductDeliveryInfoEntity> deliveryInfoEntityList = orderTracingDao.getDelivery(orderTracingDetailEnity.getId());
            int rejectionNum = 0;
            int rejectionSpareNum = 0;
            int returnNum = 0;
            int returnSpareNum = 0;
            int wareHouseNum = 0;
            int wareHouseSpareNum = 0;
            int arrivalDeliveryNum = 0;
            int arrivalReceiveNum = 0;
            int finishQuantity = 0;
            if (inpectMap.get(orderTracingDetailEnity.getOrderId() + orderTracingDetailEnity.getSkuId()) != null) {
                finishQuantity = (int) inpectMap.get(orderTracingDetailEnity.getOrderId() + orderTracingDetailEnity.getSkuId());
            }
            for (OrderTracingRejectionEnity rejectionEnity : rejectionEnityList) {
                if (rejectionEnity.getRejectQuantity() == null) {
                    rejectionEnity.setRejectQuantity(0);
                }
                rejectionNum += rejectionEnity.getRejectQuantity();
                if (rejectionEnity.getSpareQuantity() == null) {
                    rejectionEnity.setSpareQuantity(0);
                }
                rejectionSpareNum += rejectionEnity.getSpareQuantity();
            }
            for (OrderTracingReturnEnity returnEnity : returnEnityList) {
                returnNum += returnEnity.getReturnQuantity();
                returnSpareNum += returnEnity.getSpareQuantity();
            }
            for (OrderTracingWareHouseEnity wareHouseEnity : orderTracingWareHouseList) {
                wareHouseNum += wareHouseEnity.getEntryNum();
                wareHouseSpareNum += wareHouseEnity.getEntryFrets();
            }
            for (OrderTracingArrivalEnity arrivalEnity : arrivalEnityList) {
                arrivalDeliveryNum += arrivalEnity.getDeliveryQuantity();
                arrivalReceiveNum += arrivalEnity.getReceiptQuantity();
            }
            orderTracingDetailEnity.setDeliveryDateAndTimeList(deliveryInfoEntityList);
            orderTracingDetailEnity.setDeliveryQuantity(arrivalDeliveryNum);
            orderTracingDetailEnity.setReceiptQuantity(arrivalReceiveNum);
            orderTracingDetailEnity.setFinishQuantity(finishQuantity);
            orderTracingDetailEnity.setRejectionQuantity(rejectionNum);
            orderTracingDetailEnity.setEntryNum(wareHouseNum);
            orderTracingDetailEnity.setReturnQuantity(returnNum);
            orderTracingDetailEnity.setLackNum(orderTracingDetailEnity.getQuantity() - wareHouseNum + returnNum);
            orderTracingDetailEnity.setSpareNum((int) (orderTracingDetailEnity.getQuantity() * orderTracingDetailEnity.getSpareRate() / 100));
            orderTracingDetailEnity.setWareHouesEntryFrets(wareHouseSpareNum);
            orderTracingDetailEnity.setReturnEntryFrets(returnSpareNum);
            orderTracingDetailEnity.setFinishQuantity(finishQuantity);
            orderTracingDetailEnity.setSpareQuantity(finishQuantity - arrivalDeliveryNum);
            orderTracingDetailEnity.setLackEntryFrets(orderTracingDetailEnity.getSpareNum() - wareHouseSpareNum + rejectionSpareNum + returnSpareNum);
            skuSet.add(orderTracingDetailEnity.getSkuId());
            supplierSet.add(orderTracingDetailEnity.getSupplierId());
            employeeSet.add(orderTracingDetailEnity.getEmployeeId());
        }
        StringBuilder supplierBuilder = new StringBuilder();
        for (String supplyId : supplierSet) {
            if (StringUtils.isNotEmpty(supplyId)) {
                supplierBuilder.append(supplyId + ",");
            }
        }
        Map parameproMap = new HashMap();
        Map employeeMap = new HashMap();
        parameproMap.put("skuIdList", skuSet);
//      通过上诉skuId查询商品库存名称
        Result productResult = productSkuService.getProductInfo(gson.toJson(parameproMap));
        if (productResult.getCode() == 200) {
            List objects = (List) productResult.getData();
            for (Object product : objects) {
                Map proMap = (Map) product;
                Map define = (Map) proMap.get("define");
                if (define != null) {
                    String skuId = (String) define.get("skuId");
                    String skuNameZh = (String) define.get("skuNameZh");
                    productCnMap.put(skuId, skuNameZh);
                }
            }
        }
//      外部供应商服务调用
        Result supplerResult = supplierService.getSupplierInfo(supplierBuilder.toString());
        if (supplerResult.getCode() == 200) {
            List<Map<String, Object>> suplist = (List<Map<String, Object>>) supplerResult.getData();
            for (Map supplierMap : suplist) {
                String supId = (String) supplierMap.get("supplierId");
                String supplierName = (String) supplierMap.get("supplierName");
                suppliMap.put(supId, supplierName);
            }
        }
//        外部员工服务调用
        String[] employees = new String[employeeSet.size()];
        Result employeeResult = employeeService.getEmployee(employeeSet.toArray(employees));
        if (employeeResult.getCode() == 200) {
            List objects = (List) employeeResult.getData();
            for (Object employee : objects) {
                Map employer = (Map) employee;
                String employeeId = (String) employer.get("employeeId");
                String employeeName = (String) employer.get("employeeName");
                employers.put(employeeId, employeeName);
            }
        }
        for (OrderTracingDetailEnity orderTracingDetailEnity : tracingList) {
            orderTracingDetailEnity.setSupplierName(suppliMap.get(orderTracingDetailEnity.getSupplierId()));
            orderTracingDetailEnity.setSkuNameZh(productCnMap.get(orderTracingDetailEnity.getSkuId()));
            orderTracingDetailEnity.setEmployeeName(employers.get(orderTracingDetailEnity.getEmployeeId()));
        }
    }

    @Override
    public int getCount(String orderId, String startTime, String endTime, String employeeId, String supplierId, int tiicketOpenCd, int customsTypeCd, String sku, int deliveryTypeCd, String keywords, String skuName) throws ParseException {
        List<String> skuIdList = new ArrayList<>();
        if (StringUtils.isNotEmpty(skuName)) {
            Result mskuResult = productSkuService.getProductSkuInfo(1, 100000, skuName);
            if (mskuResult.getCode() == 200) {
                Map objectsMap = (Map) mskuResult.getData();
                List skuList = (List) objectsMap.get("productData");
                for (Object object : skuList) {
                    Map conditonMap = (Map) object;
                    if (conditonMap != null) {
                        if (StringUtils.isNotEmpty((String) conditonMap.get("sku"))) {
                            skuIdList.add((String) conditonMap.get("sku"));
                        }
                    }
                }
            }
        }
        String skuIds = "";
        for (String skuId : skuIdList) {
            skuIds += "'" + skuId + "'" + ",";
        }
        if (skuIds.endsWith(",")) {
            int index = skuIds.lastIndexOf(",");
            skuIds = skuIds.substring(0, index);
        }
        List<OrderTracingDetailEnity> tracingList = null;
        if (skuName != null) {
            if (StringUtils.isEmpty(skuIds)) {
                return 0;
            } else {
                return orderTracingDao.getCount(orderId, startTime, endTime, employeeId, supplierId, tiicketOpenCd, customsTypeCd, sku, deliveryTypeCd, keywords, skuIds);
            }
        } else {
            return orderTracingDao.getCount(orderId, startTime, endTime, employeeId, supplierId, tiicketOpenCd, customsTypeCd, sku, deliveryTypeCd, keywords, null);
        }
    }

    @Override
    public List<OrderTracingDetailEnity> findTracingOrderListByCond(int pageNum, int pageSize, String orderId, String startTime, String endTime, String employeeId, String supplierId, int tiicketOpenCd, int customsTypeCd, String sku, int deliveryTypeCd, String keywords, String skuName) throws ParseException {
        List<OrderTracingDetailEnity> tracingList = null;
        List<String> skuIdList = new ArrayList<>();
        if (StringUtils.isNotEmpty(skuName)) {
            Result mskuResult = productSkuService.getProductSkuInfo(1, 100000, skuName);
            if (mskuResult.getCode() == 200) {
                Map objectsMap = (Map) mskuResult.getData();
                List skuList = (List) objectsMap.get("productData");
                for (Object object : skuList) {
                    Map conditonMap = (Map) object;
                    if (conditonMap != null) {
                        if (StringUtils.isNotEmpty((String) conditonMap.get("sku"))) {
                            skuIdList.add((String) conditonMap.get("sku"));
                        }
                    }
                }
            }
        }
        String skuIds = "";
        for (String skuId : skuIdList) {
            skuIds += "'" + skuId + "'" + ",";
        }
        if (skuIds.endsWith(",")) {
            int index = skuIds.lastIndexOf(",");
            skuIds = skuIds.substring(0, index);
        }
        java.util.Date beginTime = null;
        java.util.Date overTime = null;
        Date sqlBeginDate = null;
        Date sqlEndDate = null;
        Map<String, String> productCnMap = new HashMap<>();
        Map<String, String> suppliMap = new HashMap<>();
        Map<String, String> employers = new HashMap<>();
        if (startTime != null) {
            beginTime = new SimpleDateFormat("yyyy-MM-dd").parse(startTime);
            sqlBeginDate = new java.sql.Date(beginTime.getTime());
        }
        if (overTime != null) {
            overTime = new SimpleDateFormat("yyyy-MM-dd").parse(endTime);
            sqlEndDate = new java.sql.Date(overTime.getTime());
        }
        if (sku != null) {
            PageHelper.startPage(pageNum, pageSize);
            tracingList = orderTracingDao.findTracingOrderByCond(orderId, startTime, endTime, employeeId, supplierId, tiicketOpenCd, customsTypeCd, sku, deliveryTypeCd, keywords, skuIds);
        } else {
            PageHelper.startPage(pageNum, pageSize);
            tracingList = orderTracingDao.findTracingOrderByCond(orderId, startTime, endTime, employeeId, supplierId, tiicketOpenCd, customsTypeCd, sku, deliveryTypeCd, keywords, null);
        }
        dealTracingList(tracingList, productCnMap, suppliMap, employers);
        return tracingList;
    }

    @Override
    public List<OrderTracingEntryVo> getEntry(String orderId, String skuId) {
        List<OrderTracingEntryVo> voList = new ArrayList<>();
        List<OrderTracingRejectionEnity> rejectionEnityList = orderTracingDao.getTracingRejection(skuId, orderId);
        for (OrderTracingRejectionEnity rejectionEnity : rejectionEnityList) {
            OrderTracingEntryVo vo = new OrderTracingEntryVo();
            vo.setBillId(rejectionEnity.getRejectionId());
            vo.setBillType("采购拒收单");
            vo.setRejectQuantity(rejectionEnity.getRejectQuantity());
            java.util.Date rejectDate = DateUtil.convertStrToDate(rejectionEnity.getRejectionDate(), DateUtil.DEFAULT_SHORT_DATE_FORMAT);
            vo.setDate(DateUtil.convertDateToStr(rejectDate, DateUtil.DEFAULT_SHORT_DATE_FORMAT));
            voList.add(vo);
        }
//      到货相关数据
        List<OrderTracingArrivalEnity> arrivalEnityList = orderTracingDao.getTracingArrival(skuId, orderId);
        for (OrderTracingArrivalEnity arrivalEnity : arrivalEnityList) {
            OrderTracingEntryVo vo = new OrderTracingEntryVo();
            vo.setBillId(arrivalEnity.getArrivalId());
            vo.setBillType("到货通知单");
            vo.setDate(DateUtil.convertDateToStr(arrivalEnity.getApplyDate(), DateUtil.DEFAULT_SHORT_DATE_FORMAT));
            vo.setReceiptQuantity(arrivalEnity.getReceiptQuantity());
            voList.add(vo);
        }
//      入库相关
        List<OrderTracingWareHouseEnity> orderTracingWareHouseList = orderTracingDao.getTracingWareHouseEnity(skuId, orderId);
        for (OrderTracingWareHouseEnity wareHouseEnity : orderTracingWareHouseList) {
            OrderTracingEntryVo vo = new OrderTracingEntryVo();
            vo.setBillId(wareHouseEnity.getEntryId());
            vo.setBillType("采购入库单");
            vo.setEntryNum(wareHouseEnity.getEntryNum());
            vo.setDate(DateUtil.convertDateToStr(wareHouseEnity.getEntryTime(), DateUtil.DEFAULT_SHORT_DATE_FORMAT));
            voList.add(vo);
        }
        return voList;
    }

    @Override
    public List<OrderTracingReturnVo> getReturn(String orderId, String skuId) {
        List<OrderTracingReturnVo> voList = new ArrayList<>();
        List<OrderTracingReturnEnity> returnEnityList = orderTracingDao.getTracingReturnEnity(skuId, orderId);
        for (OrderTracingReturnEnity returnEnity : returnEnityList) {
            OrderTracingReturnVo vo = new OrderTracingReturnVo();
            vo.setBillId(returnEnity.getReturnBill());
            vo.setBillType("采购退货单");
            vo.setRejectQuantity(returnEnity.getReturnQuantity());
            vo.setDate(DateUtil.convertDateToStr(returnEnity.getCreateDate(), DateUtil.DEFAULT_SHORT_DATE_FORMAT));
            voList.add(vo);
        }
        return voList;
    }

    @Override
    public List<OrderTracingCompleteVo> getStore(String orderId, String skuId) {
        List<OrderTracingCompleteVo> list = new ArrayList<>();
        List<Map> mapList = new ArrayList<>();
        Map<String, Object> objectMap = new HashMap<>();
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put("orderId", orderId);
        parameterMap.put("skuId", skuId);
        mapList.add(parameterMap);
        objectMap.put("orderIdListVOList", mapList);
        Gson gson = new Gson();
        Result inspectionResult = inspectionService.getInspectionInfo(gson.toJson(objectMap));
        if (inspectionResult.getCode() == 200) {
            Map map = (Map) inspectionResult.getData();
            List<Map> resultList = (List<Map>) map.get("inspectionInfo");
            for (Map inspectionMap : resultList) {
                OrderTracingCompleteVo vo = new OrderTracingCompleteVo();
                Integer applyInspectionQuantity = (Integer) inspectionMap.get("applyInspectionQuantity");
                Integer inspectionTypeCd = (Integer) inspectionMap.get("inspectionTypeCd");
                Integer qualifiedQuantity = (Integer) inspectionMap.get("qualifiedQuantity");
                String inspectionId = (String) inspectionMap.get("inspectionId");
                String applyDate = (String) inspectionMap.get("applyDate");
                if (inspectionTypeCd == 1 || inspectionTypeCd == 3) {
                    vo.setCompleteNum(applyInspectionQuantity);
                } else {
                    vo.setCompleteNum(qualifiedQuantity);
                }
                vo.setDate(applyDate);
                vo.setBillId(inspectionId);
                vo.setBillType("验货申请单");
                list.add(vo);
            }
        }
        List<OrderTracingArrivalEnity> arrivalEnityList = orderTracingDao.getTracingArrival(skuId, orderId);
        for (OrderTracingArrivalEnity arrivalEnity : arrivalEnityList) {
            OrderTracingCompleteVo vo = new OrderTracingCompleteVo();
            vo.setBillId(arrivalEnity.getArrivalId());
            vo.setBillType("到货通知单");
            vo.setDate(DateUtil.convertDateToStr(arrivalEnity.getApplyDate(), DateUtil.DEFAULT_SHORT_DATE_FORMAT));
            vo.setPickNum(arrivalEnity.getDeliveryQuantity());
            list.add(vo);
        }
        return list;
    }

    private int toOrderStatus(String orderId) {
        //动态验证产品状态
        try {
            List<OrderDetailsProductInfoEntity> eles = orderDetailsProductInfoDao.findInfoById(orderId);
            //此订单下sku对应的状态有多少条数据
            int a = 0;//为1--待交货的条数
            int b = 0;//为2--部分交货的条数
            int c = 0;//为3--完成 和4--中止的条数
            for (OrderDetailsProductInfoEntity ele : eles) {
                if (ele.getDeleteStatus() == 1) {
                    c += 1;
                    continue;
                }
                String skuId = ele.getSkuId();
                EntrySumNumEntity entrySumNumEntity = orderDetailsProductInfoDao.EntrySumNumEntity(skuId, orderId);
                EntrySumNumReturn entrySumNumReturn = orderDetailsProductInfoDao.EntrySumNumReturn(skuId, orderId);
                int quantity = 0;
                int sumEntry = 0;
                int sumReturn = 0;
                if (entrySumNumEntity != null) {
                    quantity = entrySumNumEntity.getQuantity();
                    sumEntry = entrySumNumEntity.getSumEntry();
                }
                if (entrySumNumReturn != null) {
                    sumReturn = entrySumNumReturn.getSumReturn();
                }
                if (entrySumNumEntity == null && entrySumNumReturn == null) {
                    a += 1;
                    continue;
                }

                //欠货数量
                int lessQuantity = quantity - (sumEntry + sumReturn);
                if (lessQuantity == quantity) {
                    a += 1;
                    continue;
                }
                if (lessQuantity > 0 && lessQuantity < quantity) {
                    b += 1;
                    continue;
                } else if (lessQuantity <= 0) {
                    c += 1;
                    continue;
                }

            }
            if (a != 0 && b == 0 && c == 0) {//当订单产品所有状态都是1--待交货，订单状态为1--待交货
                return 1;
            } else if (a == 0 && b == 0 && c != 0) {//当订单产品所有状态都是3--完成或者4--中止时候订单状态为 3--完成
                return 3;
            } else if (b != 0) {//2-部分交货
                return 2;
            }
        } catch (Exception e) {
            return 0;
        }
        return 1;
    }

    private int toStatus(String skuId, String orderId) {
        //动态验证产品状态
        try {
            EntrySumNumEntity entrySumNumEntity = orderDetailsProductInfoDao.EntrySumNumEntity(skuId, orderId);
            EntrySumNumReturn entrySumNumReturn = orderDetailsProductInfoDao.EntrySumNumReturn(skuId, orderId);
            int quantity = 0;
            int sumEntry = 0;
            int sumReturn = 0;
            if (entrySumNumEntity != null) {
                quantity = entrySumNumEntity.getQuantity();
                sumEntry = entrySumNumEntity.getSumEntry();
            }
            if (entrySumNumReturn != null) {
                sumReturn = entrySumNumReturn.getSumReturn();
            }
            if (entrySumNumEntity == null && entrySumNumReturn == null) {
                return 1;
            }
            //欠货数量
            int lessQuantity = quantity - (sumEntry + sumReturn);
            if (lessQuantity == quantity) {
                return 1;
            }
            if (lessQuantity > 0) {
                return 2;
            } else if (lessQuantity <= 0 && lessQuantity < quantity) {
                return 3;
            } else {
                return 0;
            }
        } catch (Exception e) {
            return 0;
        }
    }

}
