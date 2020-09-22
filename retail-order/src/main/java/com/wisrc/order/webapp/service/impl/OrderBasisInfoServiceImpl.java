package com.wisrc.order.webapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.wisrc.order.webapp.dao.*;
import com.wisrc.order.webapp.entity.*;
import com.wisrc.order.webapp.service.OrderBasisInfoService;
import com.wisrc.order.webapp.service.externalService.*;
import com.wisrc.order.webapp.utils.*;
import com.wisrc.order.webapp.vo.OrderInfoVO;
import com.wisrc.order.webapp.vo.ProductAndWareHouseVo;
import com.wisrc.order.webapp.vo.WareHouseVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(transactionManager = "retailOrderTransactionManager")
public class OrderBasisInfoServiceImpl implements OrderBasisInfoService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private OrderBasicInfoDao orderBasicInfoDao;
    @Autowired
    private OrderCustomerInfoDao orderCustomerInfoDao;
    @Autowired
    private OrderRemarkInfoDao orderRemarkInfoDao;
    @Autowired
    private OrderLogisticsInfoDao orderLogisticsInfoDao;
    @Autowired
    private OrderCommodityInfoDao orderCommodityInfoDao;
    @Autowired
    private OrderNvoiceInfoDao orderNvoiceInfoDao;
    @Autowired
    private OrderNvoiceRelationDao orderNvoiceRelationDao;
    @Autowired
    private OperationService operationService;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private ProductService productService;
    @Autowired
    private RuleService ruleService;
    @Autowired
    private MskuService mskuService;
    @Autowired
    private SysManageService sysManageService;
    @Autowired
    private OrderLabelInfoDao orderLabelInfoDao;
    @Autowired
    private LogInfoDao logInfoDao;
    @Autowired
    private OrderStatusAttrDao orderStatusAttrDao;
    @Autowired
    private OrderCommodityStatusAttrDao orderCommodityStatusAttrDao;


    @Override
    public void addOrder(OrderBasicInfoEntity orderBasicInfoEntity) {
        Map allRuleMap = new HashMap();
        Result ruleResult = ruleService.getExceptions();
        if (ruleResult.getCode() == 200) {
            Map map = (Map) ruleResult.getData();
            if (map != null) {
                List<Object> list = (List<Object>) map.get("orderExcepts");
                if (list != null) {
                    for (Object object : list) {
                        Map ruleMap = (Map) object;
                        allRuleMap.put(ruleMap.get("exceptTypeCd"), ruleMap);
                    }
                }
            }
        }
        String orderId = getSONoImpl(com.wisrc.order.basic.Properties.order_manage_add_order);
        Map manualMap = (Map) allRuleMap.get("c9549410d33a45449a6a96db1091ed28");
        int manualStatusCd = (int) manualMap.get("statusCd");
        orderBasicInfoEntity.setStatusCd(2);
        if (orderBasicInfoEntity.getManualCreation() == 1 && manualStatusCd != 2) {
            orderBasicInfoEntity.setStatusCd(1);
            OrderLabelInfo orderLabelInfo = new OrderLabelInfo();
            orderLabelInfo.setUuid(UUIDutil.randomUUID());
            orderLabelInfo.setOrderId(orderId);
            orderLabelInfo.setExceptTypeCd("c9549410d33a45449a6a96db1091ed28");
            orderLabelInfoDao.insert(orderLabelInfo);
        }
        checkOrderBasisInfo(orderBasicInfoEntity, orderLabelInfoDao, allRuleMap);
        List<OrderCommodityInfoEntity> commodityInfoEntityList = orderBasicInfoEntity.getProductDetailList();
        for (OrderCommodityInfoEntity commodityDto : commodityInfoEntityList) {
            if (commodityDto.getEnableStock() == null) {
                commodityDto.setEnableStock(0);
            }
            if (commodityDto.getQuantity() > commodityDto.getEnableStock()) {
                orderBasicInfoEntity.setStatusCd(1);
            }
        }
        String time = Time.getCurrentDateTime();
        orderBasicInfoEntity.setOrderId(orderId);
        orderBasicInfoEntity.setCreateTime(time);
        orderBasicInfoEntity.setModifyTime(time);
        orderBasicInfoEntity.setModifyUser(orderBasicInfoEntity.getCreateUser());
        orderBasicInfoDao.insert(orderBasicInfoEntity);
        OrderCustomerInfoEntity orderCustomerInfoEntity = orderBasicInfoEntity.getCustomsInfo();
        orderCustomerInfoEntity.setOrderId(orderId);
        orderCustomerInfoDao.insert(orderCustomerInfoEntity);
        OrderLogisticsInfo orderLogisticsInfo = orderBasicInfoEntity.getShipMentInfo();
        orderLogisticsInfo.setOrderId(orderId);
        orderLogisticsInfoDao.insert(orderLogisticsInfo);
        Double totalWeight = 0.0;
        for (OrderCommodityInfoEntity commodityDto : commodityInfoEntityList) {
            String uuid = UUIDutil.randomUUID();
            commodityDto.setUuid(uuid);
            commodityDto.setOrderId(orderId);
            commodityDto.setCreateUser(orderBasicInfoEntity.getCreateUser());
            commodityDto.setModifyUser(orderBasicInfoEntity.getCreateUser());
            commodityDto.setCreateTime(time);
            commodityDto.setModifyTime(time);
            commodityDto.setStatusCd(1);
            orderCommodityInfoDao.insert(commodityDto);
            totalWeight += commodityDto.getWeight();
        }
        OrderNvoiceInfo orderNvoiceInfo = new OrderNvoiceInfo();
        String invoiceNumber = getSONoImpl(com.wisrc.order.basic.Properties.order_manage_add_send_order);
        orderNvoiceInfo.setInvoiceNumber(invoiceNumber);
        orderNvoiceInfo.setCreateUser(orderBasicInfoEntity.getCreateUser());
        orderNvoiceInfo.setCreateTime(time);
        orderNvoiceInfo.setFreight(orderBasicInfoEntity.getFreight());
        orderNvoiceInfo.setLogisticsId(orderLogisticsInfo.getLogisticsId());
        orderNvoiceInfo.setTotalWeight(totalWeight);
        orderNvoiceInfo.setShipmentId(orderLogisticsInfo.getShipMentId());
        orderNvoiceInfo.setStatusCd(1);
        OrderNvoiceRelation orderNvoiceRelation = new OrderNvoiceRelation();
        String uuid = UUIDutil.randomUUID();
        orderNvoiceRelation.setUuid(uuid);
        orderNvoiceRelation.setInvoiceNumber(invoiceNumber);
        orderNvoiceRelation.setCreateTime(time);
        orderNvoiceRelation.setCreateUser(orderBasicInfoEntity.getCreateUser());
        orderNvoiceRelation.setOriginalOrderId(orderBasicInfoEntity.getOriginalOrderId());
        orderNvoiceRelation.setOrderId(orderBasicInfoEntity.getOrderId());
        if (orderBasicInfoEntity.getStatusCd() == 2) {
            orderNvoiceInfoDao.add(orderNvoiceInfo);
            orderNvoiceRelationDao.add(orderNvoiceRelation);
        }
        UpdateDetailEnity updateDetailEnity = new UpdateDetailEnity();
        updateDetailEnity.setUpdateUser(orderBasicInfoEntity.getCreateUser());
        updateDetailEnity.setUpdateTime(time);
        updateDetailEnity.setOrderId(orderId);
        logInfoDao.insert(updateDetailEnity);
//      此处缺少同步发货单到wms
    }

    private void checkOrderBasisInfo(OrderBasicInfoEntity orderBasicInfoEntity, OrderLabelInfoDao orderLabelInfoDao, Map allRuleMap) {
        Map overWeightMap = (Map) allRuleMap.get("bf008a3733194900b5429436afc769fe");
        int totalWeight = 0;
        List<OrderCommodityInfoEntity> orderCommodityInfoEntityList = orderBasicInfoEntity.getProductDetailList();
        for (OrderCommodityInfoEntity orderCommodityInfoEntity : orderCommodityInfoEntityList) {
            totalWeight += orderCommodityInfoEntity.getWeight();
        }
        OrderLogisticsInfo orderLogisticsInfo = orderBasicInfoEntity.getShipMentInfo();
        if (orderLogisticsInfo.getOfferId() == null) {
            orderBasicInfoEntity.setStatusCd(1);
        }
        if (overWeightMap != null) {
            int weightStatus = (int) overWeightMap.get("statusCd");
            if (weightStatus != 2) {
                String condValue = (String) overWeightMap.get("condValue");
                condValue = condValue.substring(1);
                int limitWeight = Integer.parseInt(condValue);
                if (totalWeight > limitWeight) {
                    orderBasicInfoEntity.setStatusCd(1);
                    OrderLabelInfo orderLabelInfo = new OrderLabelInfo();
                    orderLabelInfo.setUuid(UUIDutil.randomUUID());
                    orderLabelInfo.setOrderId(orderBasicInfoEntity.getOrderId());
                    orderLabelInfo.setExceptTypeCd("bf008a3733194900b5429436afc769fe");
                    orderLabelInfoDao.insert(orderLabelInfo);
                }
            }
        }
        Map overMoneyMap = (Map) allRuleMap.get("8bc050ff01454200a32b6eff3c0abe6f");
        List<OrderCommodityInfoEntity> commodityInfoEntityList = orderBasicInfoEntity.getProductDetailList();
        double totalMoney = 0;
        for (OrderCommodityInfoEntity orderCommodityInfoEntity : commodityInfoEntityList) {
            double money = orderCommodityInfoEntity.getUnitPrice() * orderCommodityInfoEntity.getQuantity();
            totalMoney += MoneyUtil.changeMonry(orderCommodityInfoEntity.getUnitPriceCurrency(), money);
        }
        if (overMoneyMap != null) {
            int moneyStatus = (int) overWeightMap.get("statusCd");
            if (moneyStatus != 2) {
                String condValue = (String) overMoneyMap.get("condValue");
                condValue = condValue.substring(1);
                double limitMoney = Double.parseDouble(condValue);
                if (totalMoney > limitMoney) {
                    orderBasicInfoEntity.setStatusCd(1);
                    OrderLabelInfo orderLabelInfo = new OrderLabelInfo();
                    orderLabelInfo.setUuid(UUIDutil.randomUUID());
                    orderLabelInfo.setOrderId(orderBasicInfoEntity.getOrderId());
                    orderLabelInfo.setExceptTypeCd("8bc050ff01454200a32b6eff3c0abe6f");
                    orderLabelInfoDao.insert(orderLabelInfo);
                }
            }
        }
        Map butRemarkMap = (Map) allRuleMap.get("0fe82a46e6ab44ce8b0d0a8c8c0a6362");
        if (butRemarkMap != null) {
            int remarkStatus = (int) butRemarkMap.get("statusCd");
            if (remarkStatus != 2) {
                String remark = orderBasicInfoEntity.getCustomsInfo().getBuyerMessage();
                if (!StringUtils.isEmpty(remark)) {
                    orderBasicInfoEntity.setStatusCd(1);
                    OrderLabelInfo orderLabelInfo = new OrderLabelInfo();
                    orderLabelInfo.setUuid(UUIDutil.randomUUID());
                    orderLabelInfo.setOrderId(orderBasicInfoEntity.getOrderId());
                    orderLabelInfo.setExceptTypeCd("0fe82a46e6ab44ce8b0d0a8c8c0a6362");
                    orderLabelInfoDao.insert(orderLabelInfo);
                }
            }
        }
    }

    @Override
    public OrderBasicInfoEntity getOrderInfo(String orderId) {
        OrderBasicInfoEntity orderBasicInfoEntity = orderBasicInfoDao.findById(orderId);
        HashMap<Integer, String> commodityStatusMap = new HashMap<>();
        Map<String, String> comskuIdMap = new HashMap<>();
        Set<String> proIdSet = new HashSet();
        Map<String, Map> productMap = new HashMap<>();
        Map<String, Map> wareHouseMap = new HashMap<>();
        List<Map> wareHouseList = new ArrayList<>();
        if (orderBasicInfoEntity == null) {
            return null;
        }
        List<OrderCommodityStatusAttrEntity> list = orderCommodityStatusAttrDao.findAll();
        for (OrderCommodityStatusAttrEntity orderCommodityStatusAttrEntity : list) {
            commodityStatusMap.put(orderCommodityStatusAttrEntity.getStatusCd(), orderCommodityStatusAttrEntity.getStatusName());
        }
        OrderCustomerInfoEntity customerInfoEntity = orderCustomerInfoDao.findAllById(orderId);
        OrderLogisticsInfo orderLogisticsInfo = orderLogisticsInfoDao.findAllById(orderId);
        List<OrderCommodityInfoEntity> orderCommodityInfoEntityList = orderCommodityInfoDao.findByOrderId(orderId);
        List<OrderRemarkInfoEntity> orderRemarkInfoEntityList = orderRemarkInfoDao.getByOrderId(orderId);
        orderBasicInfoEntity.setCustomsInfo(customerInfoEntity);
        orderBasicInfoEntity.setShipMentInfo(orderLogisticsInfo);
        orderBasicInfoEntity.setOrderRemarkInfoEntityList(orderRemarkInfoEntityList);
        orderBasicInfoEntity.setProductDetailList(orderCommodityInfoEntityList);
//      缺少商品库存信息字段
        if (orderCommodityInfoEntityList != null && orderCommodityInfoEntityList.size() > 0) {
            for (OrderCommodityInfoEntity orderCommodityInfoEntity : orderCommodityInfoEntityList) {
                proIdSet.add(orderCommodityInfoEntity.getCommodityId());
            }
        }
        if (proIdSet.size() > 0) {
            String[] proIds = proIdSet.toArray(new String[proIdSet.size()]);
            Result proResult = operationService.getProduct(proIds);
            if (proResult.getCode() == 200) {
//              通过商品id查询仓库商品信息（sku编号，商品图片）
                Map<String, Object> map = (Map) proResult.getData();
                List objects = (List) map.get("mskuInfoBatch");
                for (Object product : objects) {
                    Map proMap = (Map) product;
                    String skuId = (String) proMap.get("skuId");
                    String id = (String) proMap.get("id");
                    productMap.put(id, proMap);
                    comskuIdMap.put(id, skuId);
                }
            }
        }
        for (OrderCommodityInfoEntity orderCommodityInfoEntity : orderCommodityInfoEntityList) {
            Map map = new HashMap();
            map.put("skuId", comskuIdMap.get(orderCommodityInfoEntity.getCommodityId()));
            map.put("warehouseId", orderCommodityInfoEntity.getWarehouseId());
            wareHouseList.add(map);
        }
        Gson gson = new Gson();
        Result wareResult = warehouseService.getWareHouserInfoBatch(gson.toJson(wareHouseList));
        if (wareResult.getCode() == 200) {
            List<Object> wareResultList = (List<Object>) wareResult.getData();
            for (Object object : wareResultList) {
                Map finalWareHouseMap = (Map) object;
                if (finalWareHouseMap != null) {
                    String warehouseId = (String) finalWareHouseMap.get("warehouseId");
                    String skuId = (String) finalWareHouseMap.get("skuId");
                    wareHouseMap.put(warehouseId + skuId, finalWareHouseMap);
                }
            }
        }
        for (OrderCommodityInfoEntity orderCommodityInfoEntity : orderCommodityInfoEntityList) {
            orderCommodityInfoEntity.setStatusDesc(commodityStatusMap.get(orderCommodityInfoEntity.getStatusCd()));
            Map<String, Object> commodityMap = productMap.get(orderCommodityInfoEntity.getCommodityId());
            if (commodityMap != null) {
                orderCommodityInfoEntity.setSkuId((String) commodityMap.get("skuId"));
                orderCommodityInfoEntity.setSkuNameZh((String) commodityMap.get("productName"));
                orderCommodityInfoEntity.setMskuName((String) commodityMap.get("mskuName"));
                orderCommodityInfoEntity.setMskuId((String) commodityMap.get("mskuId"));
                orderCommodityInfoEntity.setPicture((String) commodityMap.get("picture"));
                String comSkuId = comskuIdMap.get(orderCommodityInfoEntity.getCommodityId());
                Map houseMap = wareHouseMap.get(orderCommodityInfoEntity.getWarehouseId() + comSkuId);
                if (houseMap != null) {
                    orderCommodityInfoEntity.setEnableStock((Integer) houseMap.get("enableStockNum"));
                    orderCommodityInfoEntity.setWarehouseName((String) houseMap.get("warehouseName"));
                }
            }
        }
        List<UpdateDetailEnity> updateDetailEnityList = logInfoDao.getByOrderId(orderId);
        orderBasicInfoEntity.setUpdateDetailEnityList(updateDetailEnityList);
        return orderBasicInfoEntity;
    }

    @Override
    public Result deleteOrderById(String orderId, String userId) {
        OrderBasicInfoEntity basicInfoEntity = orderBasicInfoDao.findById(orderId);
        List<OrderStatusAttr> orderStatusAttrList = orderStatusAttrDao.findAll();
        Map<Integer, String> statusMap = new HashMap();
        for (OrderStatusAttr orderStatusAttr : orderStatusAttrList) {
            statusMap.put(orderStatusAttr.getStatusCd(), orderStatusAttr.getStatusName());
        }
        if (basicInfoEntity == null) {
            return Result.success();
        } else {
            int statusCd = basicInfoEntity.getStatusCd();
            if (statusCd != 1 && statusCd != 2 && statusCd == 3) {
                return Result.failure(390, "只能作废待处理,待配货,配货中的订单", null);
            }
            orderBasicInfoDao.changeStatus(orderId, 6);
            List<OrderNvoiceRelation> orderNvoiceRelations = orderNvoiceRelationDao.findByOrderId(orderId);
            if (orderNvoiceRelations != null) {
                for (OrderNvoiceRelation orderNvoiceRelation : orderNvoiceRelations) {
                    String invoiceNumber = orderNvoiceRelation.getInvoiceNumber();
                    orderNvoiceInfoDao.changeStatus(4, invoiceNumber);
                }
            }
            String time = Time.getCurrentDateTime();
            UpdateDetailEnity updateDetailEnity = new UpdateDetailEnity();
            updateDetailEnity.setUpdateTime(time);
            updateDetailEnity.setUpdateUser(userId);
            updateDetailEnity.setOrderId(orderId);
            String updateDetail = "    [订单状态]" + "由: " + statusMap.get(basicInfoEntity.getStatusCd()) + " 修改为: " + "已作废";
            updateDetailEnity.setUpdateDetail(updateDetail);
            logInfoDao.insert(updateDetailEnity);
            return Result.success();
        }
    }

    @Override
    public Result activeOrder(String orderId, int ifSend, String userId) {
        OrderBasicInfoEntity orderBasicInfoEntity = orderBasicInfoDao.findById(orderId);
        List<OrderStatusAttr> orderStatusAttrList = orderStatusAttrDao.findAll();
        Map<Integer, String> statusMap = new HashMap();
        for (OrderStatusAttr orderStatusAttr : orderStatusAttrList) {
            statusMap.put(orderStatusAttr.getStatusCd(), orderStatusAttr.getStatusName());
        }
        if (orderBasicInfoEntity.getStatusCd() != 6) {
            return Result.failure(390, "非作废订单无法激活", null);
        }
        List<OrderCommodityInfoEntity> commodityInfoEntityList = orderCommodityInfoDao.findByOrderId(orderId);
        String time = Time.getCurrentDateTime();
        Double totalWeight = 0.0;
        if (commodityInfoEntityList != null) {
            for (OrderCommodityInfoEntity commodityDto : commodityInfoEntityList) {
                totalWeight += commodityDto.getWeight();
            }
        }
        OrderLogisticsInfo orderLogisticsInfo = orderLogisticsInfoDao.findAllById(orderId);
        if (orderLogisticsInfo == null) {
            orderLogisticsInfo = new OrderLogisticsInfo();
        }
        OrderNvoiceInfo orderNvoiceInfo = new OrderNvoiceInfo();
        String invoiceNumber = getSONoImpl("_orderManage_addSendOrder");
        orderNvoiceInfo.setInvoiceNumber(invoiceNumber);
        orderNvoiceInfo.setCreateUser(userId);
        orderNvoiceInfo.setCreateTime(time);
        orderNvoiceInfo.setFreight(orderBasicInfoEntity.getFreight());
        orderNvoiceInfo.setLogisticsId(orderLogisticsInfo.getLogisticsId());
        orderNvoiceInfo.setTotalWeight(totalWeight);
        orderNvoiceInfo.setShipmentId(orderLogisticsInfo.getShipMentId());
        orderNvoiceInfo.setStatusCd(1);
        OrderNvoiceRelation orderNvoiceRelation = new OrderNvoiceRelation();
        String uuid = UUIDutil.randomUUID();
        orderNvoiceRelation.setUuid(uuid);
        orderNvoiceRelation.setInvoiceNumber(invoiceNumber);
        orderNvoiceRelation.setCreateTime(time);
        orderNvoiceRelation.setCreateUser(userId);
        orderNvoiceRelation.setOriginalOrderId(orderBasicInfoEntity.getOriginalOrderId());
        orderNvoiceRelation.setOrderId(orderBasicInfoEntity.getOrderId());
        //      异常规则判断
        boolean ifException = checkOrder(orderId);
        if (!ifException && ifSend == 1) {
            List<OrderNvoiceRelation> orderNvoiceRelations = orderNvoiceRelationDao.findByOrderId(orderId);
            if (orderNvoiceRelations != null) {
                for (OrderNvoiceRelation novoiceRelation : orderNvoiceRelations) {
                    orderNvoiceInfoDao.changeStatus(1, novoiceRelation.getInvoiceNumber());
                }
            } else {
                orderNvoiceInfoDao.add(orderNvoiceInfo);
                orderNvoiceRelationDao.add(orderNvoiceRelation);
            }
            orderBasicInfoDao.changeStatus(orderId, 2);
        } else {
            orderBasicInfoDao.changeStatus(orderId, 1);
        }
        UpdateDetailEnity updateDetailEnity = new UpdateDetailEnity();
        updateDetailEnity.setUpdateTime(time);
        updateDetailEnity.setUpdateUser(userId);
        updateDetailEnity.setOrderId(orderId);
        String updateDetail = "    [订单状态]" + "由: " + statusMap.get(orderBasicInfoEntity.getStatusCd()) + " 修改为: " + "已激活";
        updateDetailEnity.setUpdateDetail(updateDetail);
        logInfoDao.insert(updateDetailEnity);
        return Result.success();
    }

    @Override
    public ProductAndWareHouseVo getProAndWareHouse(String shopId, String mskuId) {
        Result proResult = operationService.getMskuInfo(shopId, mskuId);
        ProductAndWareHouseVo productAndWareHouseVo = new ProductAndWareHouseVo();
        List<WareHouseVo> wareHouseVos = new ArrayList<>();
        if (proResult.getCode() == 200) {
            Map map = (Map) proResult.getData();
            List<Object> mskuList = (List<Object>) map.get("mskuList");
            if (mskuList != null && mskuList.size() > 0) {
                Map mskuMap = (Map) mskuList.get(0);
                productAndWareHouseVo.setCommodityId((String) mskuMap.get("id"));
                productAndWareHouseVo.setMskuName((String) mskuMap.get("mskuName"));
                productAndWareHouseVo.setSkuNameZh((String) mskuMap.get("productName"));
                productAndWareHouseVo.setPicture((String) mskuMap.get("picture"));
                productAndWareHouseVo.setSkuId((String) mskuMap.get("skuId"));
            }
        }
        if (productAndWareHouseVo.getCommodityId() == null) {
            return productAndWareHouseVo;
        }
        String[] ids = new String[]{productAndWareHouseVo.getSkuId()};
        Result wareHouseResult = warehouseService.getWareHouserInfo(ids);
        if (wareHouseResult.getCode() == 200) {
            List<Object> wareList = (List<Object>) wareHouseResult.getData();
            for (Object object : wareList) {
                Map houseMap = (Map) object;
                String warehouseId = (String) houseMap.get("warehouseId");
                String warehouseName = (String) houseMap.get("warehouseName");
                Integer enableStockNum = (Integer) houseMap.get("enableStockNum");
                WareHouseVo wareHouseVo = new WareHouseVo();
                wareHouseVo.setEnableStockNum(enableStockNum);
                wareHouseVo.setWarehouseName(warehouseName);
                wareHouseVo.setWarehouseId(warehouseId);
                wareHouseVos.add(wareHouseVo);
            }
        }
        Result specificationsResult = productService.getProductSpecifications(productAndWareHouseVo.getSkuId());
        if (specificationsResult.getCode() == 200) {
            if (specificationsResult.getData() != null) {
                Map speciMap = (Map) specificationsResult.getData();
                Double weight = (Double) speciMap.get("weight");
                productAndWareHouseVo.setUnitWeight(weight);
            }
        }
        productAndWareHouseVo.setWareHouseList(wareHouseVos);
        return productAndWareHouseVo;
    }

    @Override
    public LinkedHashMap getOrder(String pageNum,
                                  String pageSize,
                                  String orderId,
                                  String originalOrderId,
                                  String platId,
                                  String shopId,
                                  String createTime,
                                  String exceptTypeCd,
                                  String mskuId,
                                  String mskuName,
                                  int statusCd,
                                  String countryCd) throws Exception {

        List<OrderInfoVO> list = null;

        List<String> commodityIds = new ArrayList<>();

        if (StringUtils.isNotEmpty(mskuName)) {
            Result mskuResult = mskuService.getMskuByCond(mskuName);
            if (mskuResult.getCode() == 200) {
                List<Object> mskuList = (List<Object>) mskuResult.getData();
                for (Object object : mskuList) {
                    Map mskuMap = (Map) object;
                    commodityIds.add((String) mskuMap.get("id"));
                }
            }
        }
        String comIds = "";
        for (String skuId : commodityIds) {
            comIds += "'" + skuId + "'" + ",";
        }
        if (comIds.endsWith(",")) {
            int index = comIds.lastIndexOf(",");
            comIds = comIds.substring(0, index);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date createDate = null;
        if (createTime != null) {
            createDate = new Date(sdf.parse(createTime).getTime());
        }
        if (pageNum != null && pageSize != null) {
            // 分页查询
            if (StringUtils.isNotEmpty(mskuName)) {
                if (StringUtils.isEmpty(comIds)) {
                    list = new ArrayList<>();
                    return PageData.pack(0, 1, "SaleOrderInfoList", list);
                } else {
                    PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
                    list = orderBasicInfoDao.findByCond(orderId, originalOrderId, platId, shopId, createDate, exceptTypeCd, statusCd, comIds, countryCd);
                }
            } else {
                PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
                list = orderBasicInfoDao.findByCond(orderId, originalOrderId, platId, shopId, createDate, exceptTypeCd, statusCd, null, countryCd);
            }
            PageInfo pageInfo = new PageInfo(list);
            for (OrderInfoVO orderInfoVO : list) {
                List<OrderRemarkInfoEntity> remarkInfoEntities = orderRemarkInfoDao.getByOrderId(orderInfoVO.getOrderId());
                List<OrderLabelInfo> orderLabelInfoList = orderLabelInfoDao.getById(orderInfoVO.getOrderId());
                orderInfoVO.setOrderLabelInfoList(orderLabelInfoList);
                orderInfoVO.setRemarkInfoEntityList(remarkInfoEntities);
            }
            return PageData.pack(pageInfo.getTotal(), pageInfo.getPages(), "SaleOrderInfoList", list);
        } else {
            // 全表查询
            if (StringUtils.isNotEmpty(mskuName)) {
                if (StringUtils.isEmpty(comIds)) {
                    list = new ArrayList<>();
                    return PageData.pack(0, 1, "SaleOrderInfoList", list);
                } else {
                    list = orderBasicInfoDao.findByCond(orderId, originalOrderId, platId, shopId, createDate, exceptTypeCd, statusCd, comIds, countryCd);
                }
            } else {
                list = orderBasicInfoDao.findByCond(orderId, originalOrderId, platId, shopId, createDate, exceptTypeCd, statusCd, null, countryCd);
            }
            for (OrderInfoVO orderInfoVO : list) {
                List<OrderRemarkInfoEntity> remarkInfoEntities = orderRemarkInfoDao.getByOrderId(orderInfoVO.getOrderId());
                orderInfoVO.setRemarkInfoEntityList(remarkInfoEntities);
            }
            return PageData.pack(-1, -1, "SaleOrderInfoList", list);
        }
    }

    private boolean checkOrder(String orderId) {
        OrderLogisticsInfo orderLogisticsInfo = orderLogisticsInfoDao.findAllById(orderId);
        if (orderLogisticsInfo.getOfferId() == null) {
            return true;
        }
        List<OrderLabelInfo> list = orderLabelInfoDao.getById(orderId);
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }

    public String getSONoImpl(String rediskey) {
        SimpleDateFormat sdf = new SimpleDateFormat("YYMMdd");
        String currDate = sdf.format(System.currentTimeMillis());
        String key = currDate + rediskey;
        int count = 0;
        long maxId = redisTemplate.opsForValue().increment(key, 1);
        if (maxId == 1) {
            redisTemplate.expire(key, 1000 * 60 * 60 * 24, TimeUnit.MILLISECONDS);
        }
        if (maxId > 999999) {
            throw new RuntimeException("订单号已达最大");
        }
        long num = maxId;
        while (num > 0) {
            num = num / 10;
            count++;
        }
        if (count == 1) {
            return "SO" + currDate + "00000" + maxId;
        }
        if (count == 2) {
            return "SO" + currDate + "0000" + maxId;
        }
        if (count == 3) {
            return "SO" + currDate + "000" + maxId;
        }
        if (count == 4) {
            return "SO" + currDate + "00" + maxId;
        }
        if (count == 5) {
            return "SO" + currDate + "0" + maxId;
        }
        return "SO" + currDate + maxId;
    }
}
