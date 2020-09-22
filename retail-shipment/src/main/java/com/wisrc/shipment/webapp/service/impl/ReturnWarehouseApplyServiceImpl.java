package com.wisrc.shipment.webapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.wisrc.shipment.basic.Properties;
import com.wisrc.shipment.webapp.entity.*;
import com.wisrc.shipment.webapp.service.ProductService;
import com.wisrc.shipment.webapp.service.ReturnWarehouseApplyService;
import com.wisrc.shipment.webapp.dao.ReturnWarehouseApplyDao;
import com.wisrc.shipment.webapp.service.externalService.*;
import com.wisrc.shipment.webapp.utils.PageData;
import com.wisrc.shipment.webapp.utils.Result;
import com.wisrc.shipment.webapp.utils.Time;
import com.wisrc.shipment.webapp.utils.UUIDutil;
import com.wisrc.shipment.webapp.vo.ProductDetaiVo;
import com.wisrc.shipment.webapp.vo.ReturnWareHouseEnityVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(transactionManager = "retailShipmentTransactionManager")
public class ReturnWarehouseApplyServiceImpl implements ReturnWarehouseApplyService {
    @Autowired
    private ReturnWarehouseApplyDao returnWarehouseApplyDao;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SysManageService sysManageService;
    @Autowired
    private MskuService mskuService;
    @Autowired
    private WarehouseInfoService warehouseInfoService;
    @Autowired
    private OperationService operationService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CrawlerService crawlerService;

    @Override
    public Result insert(ReturnWarehouseApplyEnity returnWarehouseApplyEnity) {
        String[] useridList = new String[]{returnWarehouseApplyEnity.getCreateUser()};
        Result employeeResult = sysManageService.getEmployeeBatch(useridList);
        String time = Time.getCurrentDateTime();
        returnWarehouseApplyEnity.setCreateTime(time);
        returnWarehouseApplyEnity.setUpdateTime(time);
        returnWarehouseApplyEnity.setUpdateUser(returnWarehouseApplyEnity.getCreateUser());
        boolean update = false;
        if (employeeResult.getCode() == 200) {
            List<Map> mapList = (List<Map>) employeeResult.getData();
            returnWarehouseApplyEnity.setEmployeeId((String) mapList.get(0).get("employeeId"));
        }
        if (returnWarehouseApplyEnity.getReturnApplyId() != null) {
            returnWarehouseApplyDao.updateReturnWarehouse(returnWarehouseApplyEnity);
            returnWarehouseApplyDao.deleteDetailById(returnWarehouseApplyEnity.getReturnApplyId());
            returnWarehouseApplyDao.deleteTypeRelById(returnWarehouseApplyEnity.getReturnApplyId());
            update = true;
        }
        if (!update) {
            String returnApplyId = getReturnNoImpl(Properties.return_warehouse_apply);
            returnWarehouseApplyEnity.setReturnApplyId(returnApplyId);
            returnWarehouseApplyEnity.setStatusCd(1);
            returnWarehouseApplyDao.addReturnBasis(returnWarehouseApplyEnity);
        }
        List<ProductDetail> productDetailList = returnWarehouseApplyEnity.getProductDetailList();
        for (ProductDetail productDetail : productDetailList) {
            productDetail.setReturnApplyId(returnWarehouseApplyEnity.getReturnApplyId());
            productDetail.setUuid(UUIDutil.randomUUID());
            returnWarehouseApplyDao.addProductDetail(productDetail);
        }
        List<Integer> returnTypeCd = returnWarehouseApplyEnity.getReturnTypeCd();
        for (Integer typeCd : returnTypeCd) {
            returnWarehouseApplyDao.addReasonTypeRel(typeCd, returnWarehouseApplyEnity.getReturnApplyId());
        }
        return Result.success();
    }

    @Override
    public LinkedHashMap findByCond(String pageNum, String pageSize, String shopId, String employeeId, int statusCd, String keyword, String productName, String startTime, String endTime) {
        Set<String> conditnIdSet = new HashSet<>();
        Set<String> commoditySet = new HashSet<>();
        Set<String> warehouseSet = new HashSet<>();
        Set<String> conditnIdNewSet = new HashSet<>();
        Map<String, String> wareIdNameMap = new HashMap<>();
        Map<String, Object> productMap = new HashMap<>();
        List<ReturnWareHouseEnityVo> wareHouseEnityVos;
        List<Map> parameterMapList = new ArrayList<>();
        if (StringUtils.isNotEmpty(productName)) {
            Result mskuResult = mskuService.getMskuByCond(null, null, productName);
            if (mskuResult.getCode() == 200) {
                List<Object> objects = (List<Object>) mskuResult.getData();
                for (Object object : objects) {
                    Map conditonMap = (Map) object;
                    if (conditonMap != null) {
                        if (StringUtils.isNotEmpty((String) conditonMap.get("id"))) {
                            conditnIdNewSet.add((String) conditonMap.get("id"));
                        }
                    }
                }
            }
        }
        if (keyword != null) {
            Result mskuResult = mskuService.getMskuByCond(null, keyword, null);
            if (mskuResult.getCode() == 200) {
                List<Object> objects = (List<Object>) mskuResult.getData();
                for (Object object : objects) {
                    Map conditonMap = (Map) object;
                    if (conditonMap != null) {
                        if (StringUtils.isNotEmpty((String) conditonMap.get("id"))) {
                            conditnIdSet.add((String) conditonMap.get("id"));
                        }
                    }
                }
            }
            Result fnskuLikeResult = mskuService.getMskuInfoByFnskuLike(keyword);
            if (mskuResult.getCode() == 200) {
                List<Object> objects = (List<Object>) fnskuLikeResult.getData();
                for (Object object : objects) {
                    Map conditonMap = (Map) object;
                    if (conditonMap != null) {
                        if (StringUtils.isNotEmpty((String) conditonMap.get("id"))) {
                            conditnIdSet.add((String) conditonMap.get("id"));
                        }
                    }
                }
            }
        }
        String comodityNewIds = "";
        for (String commodityId : conditnIdNewSet) {
            comodityNewIds += "'" + commodityId + "'" + ",";
        }
        if (StringUtils.isEmpty(comodityNewIds)) {
            comodityNewIds = null;
        }
        if (comodityNewIds != null && comodityNewIds.endsWith(",")) {
            int index = comodityNewIds.lastIndexOf(",");
            comodityNewIds = comodityNewIds.substring(0, index);
        }
        String comodityIds = "";
        for (String commodityId : conditnIdSet) {
            comodityIds += "'" + commodityId + "'" + ",";
        }
        if (StringUtils.isEmpty(comodityIds)) {
            comodityIds = null;
        }
        if (comodityIds != null && comodityIds.endsWith(",")) {
            int index = comodityIds.lastIndexOf(",");
            comodityIds = comodityIds.substring(0, index);
        }
        if (pageNum != null && pageSize != null) {
            int page = Integer.parseInt(pageNum);
            int size = Integer.parseInt(pageSize);
            PageHelper.startPage(page, size);
            wareHouseEnityVos = returnWarehouseApplyDao.findBycond(shopId, employeeId, statusCd, keyword, comodityIds, startTime, endTime, productName, comodityNewIds);
        } else {
            wareHouseEnityVos = returnWarehouseApplyDao.findBycond(shopId, employeeId, statusCd, keyword, comodityIds, startTime, endTime, productName, comodityNewIds);
        }
        Map inAndOutMap = new HashMap();
        PageInfo pageInfo = new PageInfo(wareHouseEnityVos);
        for (ReturnWareHouseEnityVo returnWareHouseEnityVo : wareHouseEnityVos) {
            List<ProductDetaiVo> productDetaiVoList = returnWarehouseApplyDao.getDetail(returnWareHouseEnityVo.getReturnApplyId());
            List<String> removeOrderList = returnWarehouseApplyDao.getRemoveOrder(returnWareHouseEnityVo.getReturnApplyId());
            for (ProductDetaiVo productDetaiVo : productDetaiVoList) {
                commoditySet.add(productDetaiVo.getCommodityId());
            }
            ReceiveWarehouseEnity receiveWarehouseEnity = returnWarehouseApplyDao.getReceiveWarehouseEnity(returnWareHouseEnityVo.getReturnApplyId());
            if (receiveWarehouseEnity != null) {
                returnWareHouseEnityVo.setReceiveWarehouseId(receiveWarehouseEnity.getWarehouseId());
                warehouseSet.add(receiveWarehouseEnity.getWarehouseId());
            }
            returnWareHouseEnityVo.setProductDetaiVoList(productDetaiVoList);
            returnWareHouseEnityVo.setRemoveOrderList(removeOrderList);
        }
        Set<String> skuSet = new HashSet<>();
        if (commoditySet.size() > 0) {
            String[] ids = new String[commoditySet.size()];
            Result mskuResult = mskuService.getProduct(commoditySet.toArray(ids));
            if (mskuResult.getCode() == 200) {
                Map map = (Map) mskuResult.getData();
                if (map != null) {
                    List<Object> objectList = (List<Object>) map.get("mskuInfoBatch");
                    for (Object object : objectList) {
                        Map proMap = (Map) object;
                        productMap.put((String) proMap.get("id"), proMap);
                        skuSet.add((String) proMap.get("skuId"));
                    }
                }
            }
        }
        if (warehouseSet.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String warehouseId : warehouseSet) {
                stringBuilder.append(warehouseId + ",");
            }
            Result wareResult = warehouseInfoService.getWarehouseNameList(stringBuilder.toString());
            if (wareResult.getCode() == 200) {
                List<Object> wareList = (List) wareResult.getData();
                for (Object object : wareList) {
                    Map waremap = (Map) object;
                    wareIdNameMap.put((String) waremap.get("warehouseId"), (String) waremap.get("warehouseName"));
                }
            }
        }
        Gson gson = new Gson();
        Map imageMap = new HashMap();
        Result proResult = productService.getProductImage(gson.toJson(skuSet));
        if (proResult.getCode() == 200) {
            List<Map> origiList = (List<Map>) proResult.getData();
            if (origiList != null && origiList.size() > 0) {
                for (Map origiMap : origiList) {
                    String sku = (String) origiMap.get("skuId");
                    List<Map> imageList = (List<Map>) origiMap.get("image");
                    if (imageList != null && imageList.size() > 0) {
                        String image = (String) imageList.get(0).get("imageUrl");
                        imageMap.put(sku, image);
                    }
                }
            }
        }
        for (ReturnWareHouseEnityVo returnWareHouseEnityVo : wareHouseEnityVos) {
            List<ProductDetaiVo> productDetaiVoList = returnWareHouseEnityVo.getProductDetaiVoList();
            returnWareHouseEnityVo.setReceiveWarehouseName(wareIdNameMap.get(returnWareHouseEnityVo.getReceiveWarehouseId()));
            for (ProductDetaiVo productDetaiVo : productDetaiVoList) {
                Map proMskuMap = (Map) productMap.get(productDetaiVo.getCommodityId());
                if (proMskuMap != null) {
                    String skuId = (String) proMskuMap.get("skuId");
                    productDetaiVo.setMskuName((String) proMskuMap.get("mskuName"));
                    productDetaiVo.setProductName((String) proMskuMap.get("productName"));
                    productDetaiVo.setAsin((String) proMskuMap.get("asin"));
                    productDetaiVo.setFnsku((String) proMskuMap.get("fnSkuId"));
                    productDetaiVo.setSkuId(skuId);
                    productDetaiVo.setPicture((String) imageMap.get(skuId));
                    returnWareHouseEnityVo.setShopName((String) proMskuMap.get("shopName"));
                }
            }
            Map map = new HashMap();
            map.put("returnApplyId", returnWareHouseEnityVo.getReturnApplyId());
            map.put("removeOrderIdList", returnWareHouseEnityVo.getRemoveOrderList());
            parameterMapList.add(map);
        }
        String str = gson.toJson(parameterMapList);
        Result result = crawlerService.removeLocalOrderInfoBatch(gson.toJson(parameterMapList));
        if (result.getCode() == 200) {
            inAndOutMap = (Map) result.getData();
        }
        for (ReturnWareHouseEnityVo returnWareHouseEnityVo : wareHouseEnityVos) {
            String returnApplyId = returnWareHouseEnityVo.getReturnApplyId();
            String shop = returnWareHouseEnityVo.getShopId();
            List<ProductDetaiVo> productDetaiVoList = returnWareHouseEnityVo.getProductDetaiVoList();
            for (ProductDetaiVo productDetaiVo : productDetaiVoList) {
                String id = productDetaiVo.getMskuId() + shop + returnApplyId;
                String fnId = productDetaiVo.getFnsku() + shop + returnApplyId;
                Integer outWarehouseNum = (Integer) inAndOutMap.get(fnId + "out");
                productDetaiVo.setOutWarehouseNum(outWarehouseNum);
                Integer signNum = (Integer) inAndOutMap.get(id + "in");
                productDetaiVo.setSignNum(signNum);
            }
        }
        if (pageNum != null && pageSize != null) {
            return PageData.pack(pageInfo.getTotal(), pageInfo.getPages(), "returnApplyList", wareHouseEnityVos);
        } else {
            return PageData.pack(-1, -1, "returnApplyList", wareHouseEnityVos);
        }
    }

    @Override
    public ReturnWareHouseEnityVo getWarehouseDetail(String returnApplyId) {
        Set<String> commoditySet = new HashSet<>();
        Map<String, Object> productMap = new HashMap<>();
        ReturnWareHouseEnityVo returnWareHouseEnityVo = returnWarehouseApplyDao.getReturnWareHouseById(returnApplyId);
        if (returnWareHouseEnityVo == null) {
            return returnWareHouseEnityVo;
        }
        List<String> returnTypeNameList = returnWarehouseApplyDao.getReturnTypeName(returnApplyId);
        List<String> removeOrderList = returnWarehouseApplyDao.getRemoveOrder(returnApplyId);
        String statusDesc = returnWarehouseApplyDao.getStatusDesc(returnWareHouseEnityVo.getStatusCd());
        ReceiveWarehouseEnity receiveWarehouseEnity = returnWarehouseApplyDao.getReceiveWarehouseEnity(returnApplyId);
        returnWareHouseEnityVo.setReturnTypeName(returnTypeNameList);
        returnWareHouseEnityVo.setRemoveOrderList(removeOrderList);
        returnWareHouseEnityVo.setStatusCdDesc(statusDesc);
        returnWareHouseEnityVo.setWareHouseDetail(receiveWarehouseEnity);
        List<ProductDetaiVo> productDetaiVoList = returnWarehouseApplyDao.getDetail(returnApplyId);
        for (ProductDetaiVo productDetaiVo : productDetaiVoList) {
            commoditySet.add(productDetaiVo.getCommodityId());
        }
        Set<String> skuSet = new HashSet();
        if (commoditySet.size() > 0) {
            String[] ids = new String[commoditySet.size()];
            Result mskuResult = mskuService.getProduct(commoditySet.toArray(ids));
            if (mskuResult.getCode() == 200) {
                Map map = (Map) mskuResult.getData();
                if (map != null) {
                    List<Object> objectList = (List<Object>) map.get("mskuInfoBatch");
                    for (Object object : objectList) {
                        Map proMap = (Map) object;
                        productMap.put((String) proMap.get("id"), proMap);
                        skuSet.add((String) proMap.get("skuId"));
                    }
                }
            }
        }
        Gson gson = new Gson();
        Map imageMap = new HashMap();
        Result proResult = productService.getProductImage(gson.toJson(skuSet));
        if (proResult.getCode() == 200) {
            List<Map> origiList = (List<Map>) proResult.getData();
            if (origiList != null && origiList.size() > 0) {
                for (Map origiMap : origiList) {
                    String sku = (String) origiMap.get("skuId");
                    List<Map> imageList = (List<Map>) origiMap.get("image");
                    if (imageList != null && imageList.size() > 0) {
                        String image = (String) imageList.get(0).get("imageUrl");
                        imageMap.put(sku, image);
                    }
                }
            }
        }
        for (ProductDetaiVo productDetaiVo : productDetaiVoList) {
            Map proMskuMap = (Map) productMap.get(productDetaiVo.getCommodityId());
            if (proMskuMap != null) {
                String skuId = (String) proMskuMap.get("skuId");
                productDetaiVo.setMskuName((String) proMskuMap.get("mskuName"));
                productDetaiVo.setProductName((String) proMskuMap.get("productName"));
                productDetaiVo.setAsin((String) proMskuMap.get("asin"));
                productDetaiVo.setFnsku((String) proMskuMap.get("fnSkuId"));
                productDetaiVo.setSkuId(skuId);
                productDetaiVo.setPicture((String) imageMap.get(skuId));
                returnWareHouseEnityVo.setShopName((String) proMskuMap.get("shopName"));
            }
        }
        List<Map> parameterMapList = new ArrayList<>();
        Map map = new HashMap();
        Map inAndOutMap = new HashMap();
        map.put("returnApplyId", returnWareHouseEnityVo.getReturnApplyId());
        map.put("removeOrderIdList", removeOrderList);
        parameterMapList.add(map);
        Result result = crawlerService.removeLocalOrderInfoBatch(gson.toJson(parameterMapList));
        if (result.getCode() == 200) {
            inAndOutMap = (Map) result.getData();
        }
        for (ProductDetaiVo productDetaiVo : productDetaiVoList) {
            String shop = returnWareHouseEnityVo.getShopId();
            String id = productDetaiVo.getMskuId() + shop + returnApplyId;
            String fnId = productDetaiVo.getFnsku() + shop + returnApplyId;
            Integer outWarehouseNum = (Integer) inAndOutMap.get(fnId + "out");
            productDetaiVo.setOutWarehouseNum(outWarehouseNum);
            Integer signNum = (Integer) inAndOutMap.get(id + "in");
            productDetaiVo.setSignNum(signNum);
        }
        returnWareHouseEnityVo.setProductDetaiVoList(productDetaiVoList);
        return returnWareHouseEnityVo;
    }

    @Override
    public ReturnWareHouseEnityVo getWarehouseSimpleDetail(String returnApplyId) {
        Set<String> commoditySet = new HashSet<>();
        Map<String, Object> productMap = new HashMap<>();
        ReturnWareHouseEnityVo returnWareHouseEnityVo = returnWarehouseApplyDao.getReturnWareHouseById(returnApplyId);
        if (returnWareHouseEnityVo == null) {
            return returnWareHouseEnityVo;
        }
        List<String> returnTypeCdList = returnWarehouseApplyDao.getReturnTypeCdList(returnApplyId);
        returnWareHouseEnityVo.setReturnTypeCd(returnTypeCdList);
        List<ProductDetaiVo> productDetaiVoList = returnWarehouseApplyDao.getDetail(returnApplyId);
        for (ProductDetaiVo productDetaiVo : productDetaiVoList) {
            commoditySet.add(productDetaiVo.getCommodityId());
        }
        if (commoditySet.size() > 0) {
            String[] ids = new String[commoditySet.size()];
            Result mskuResult = mskuService.getProduct(commoditySet.toArray(ids));
            if (mskuResult.getCode() == 200) {
                Map map = (Map) mskuResult.getData();
                if (map != null) {
                    List<Object> objectList = (List<Object>) map.get("mskuInfoBatch");
                    for (Object object : objectList) {
                        Map proMap = (Map) object;
                        productMap.put((String) proMap.get("id"), proMap);
                    }
                }
            }
        }
        for (ProductDetaiVo productDetaiVo : productDetaiVoList) {
            Map proMskuMap = (Map) productMap.get(productDetaiVo.getCommodityId());
            if (proMskuMap != null) {
                productDetaiVo.setMskuName((String) proMskuMap.get("mskuName"));
                productDetaiVo.setProductName((String) proMskuMap.get("productName"));
                productDetaiVo.setAsin((String) proMskuMap.get("asin"));
                productDetaiVo.setFnsku((String) proMskuMap.get("fnSkuId"));
                productDetaiVo.setSkuId((String) proMskuMap.get("skuId"));
                productDetaiVo.setPicture((String) proMskuMap.get("picture"));
            }
        }
        returnWareHouseEnityVo.setProductDetaiVoList(productDetaiVoList);
        return returnWareHouseEnityVo;
    }

    @Override
    public void deleteApply(String returnApplyId, String reason, String userId) {
        returnWarehouseApplyDao.changeStatusApply(returnApplyId, reason, 4, userId);
    }

    @Override
    public void addRemoveOrder(String returnApplyId, String[] orderIds) {
        returnWarehouseApplyDao.deleteRemoveOrder(returnApplyId);
        for (String orderId : orderIds) {
            returnWarehouseApplyDao.addRemoveOrder(returnApplyId, orderId);
        }
    }

    /**
     * 添加收货仓库
     * 需要检查如果是分仓情况，那么一定需要添加分仓
     * 一定是海外仓，代号B
     *
     * @param receiveWarehouseEnity
     */
    @Override
    @Transactional(transactionManager = "retailShipmentTransactionManager")
    public void addReceiveWarehouse(ReceiveWarehouseEnity receiveWarehouseEnity) {
        ReceiveWarehouseEnity receiveWarehouse = returnWarehouseApplyDao.getReceiveWarehouseEnity(receiveWarehouseEnity.getReturnApplyId());
        Result warehouseResult = warehouseInfoService.getWarehouseName(receiveWarehouseEnity.getWarehouseId());
        Object data = warehouseResult.getData();
        if (receiveWarehouseEnity.getWarehouseId().charAt(0) != 'B') {
            throw new IllegalStateException("仓库类型错误，请选择海外仓");
        }
        if (warehouseResult.getCode() != 200 || data == null) {
            throw new IllegalStateException("不存在的仓库，请重新选择");
        }
        Map warehouseMap = (Map) data;
        //检查仓库状态
        Integer statusCd = (Integer) warehouseMap.get("statusCd");
        if (statusCd != 1) {
            throw new IllegalStateException("请选择正常状态的仓库");
        }
        //检查是否支持分仓
        //如果支持分仓库，那么一定选择的分仓编号一定在subWarehouseList中
        Integer subWarehouseSupport = (Integer) warehouseMap.get("subWarehouseSupport");
        List<Map<String, Object>> subWarehouseList = (List<Map<String, Object>>) warehouseMap.get("subWarehouseList");
        if (subWarehouseSupport == 0 && receiveWarehouseEnity.getSubWarehouseId() != null) {
            throw new IllegalStateException("该仓库不支持分仓，请将分仓置空");
        }
        //支持分仓
        if (subWarehouseSupport == 1) {
            if (subWarehouseList == null || subWarehouseList.stream().noneMatch(stringStringMap -> (Integer.valueOf(stringStringMap.get("deleteStatus") + "") == 0))) {
                throw new IllegalStateException("该仓库支持分仓，但分仓列表为空，请添加");
            } else {
                boolean match = subWarehouseList.stream().anyMatch(stringStringMap ->
                        stringStringMap.get("subWarehouseId").equals(
                                receiveWarehouseEnity.getSubWarehouseId()) &&
                                (Integer.valueOf(stringStringMap.get("deleteStatus") + "") == 0));
                if (!match) {
                    throw new IllegalStateException("选择的分仓不属于该仓库中正常状态的分仓");
                }
            }
        }
        if (receiveWarehouse == null) {
            returnWarehouseApplyDao.addReceiveWarehouse(receiveWarehouseEnity);
        } else {
            returnWarehouseApplyDao.updateReceiveWarehouse(receiveWarehouseEnity);
        }
    }

    @Override
    public void checkReturnWarehouseApply(ReturnWarehouseCheckEnity returnWarehouseApplyEnity) {
        returnWarehouseApplyDao.changeStatusApply(returnWarehouseApplyEnity.getReturnApplyId(), returnWarehouseApplyEnity.getReason(), returnWarehouseApplyEnity.getStatusCd(), returnWarehouseApplyEnity.getUpdateUser());
    }

    @Override
    public ProductDetaiVo getProdetail(String shopId, String mskuId) {
        Result proResult = operationService.getMskuInfo(shopId, mskuId);
        ProductDetaiVo productDetaiVo = new ProductDetaiVo();
        if (proResult.getCode() == 200) {
            Map map = (Map) proResult.getData();
            List<Object> mskuList = (List<Object>) map.get("mskuList");
            if (mskuList != null && mskuList.size() > 0) {
                Map mskuMap = (Map) mskuList.get(0);
                productDetaiVo.setCommodityId((String) mskuMap.get("id"));
                productDetaiVo.setMskuName((String) mskuMap.get("mskuName"));
                productDetaiVo.setProductName((String) mskuMap.get("productName"));
                productDetaiVo.setPicture((String) mskuMap.get("picture"));
                productDetaiVo.setSkuId((String) mskuMap.get("skuId"));
                productDetaiVo.setAsin((String) mskuMap.get("asin"));
                productDetaiVo.setAsin((String) mskuMap.get("fnsku"));
            }
        }
        return productDetaiVo;
    }

    @Override
    public List<PackingDetailEnity> getPackingDetailList(String[] skuIds) {
        List<PackingDetailEnity> packingDetailEnityList = new ArrayList<>();
        Map declareInfoMap = new HashMap();
        Map parameproMap = new HashMap();
        Map declareLabelListmap = new HashMap();
        Map specificationsInfoMap = new HashMap();
        Map labelMap = new HashMap();
        List<String> labelDesc = new ArrayList<>();
        parameproMap.put("skuIdList", skuIds);
        Gson gson = new Gson();
//              通过上诉skuId查询商品库存名称
        Result productResult = productService.getProductInfo(gson.toJson(parameproMap));
        if (productResult.getCode() == 200) {
            List objects = (List) productResult.getData();
            for (Object product : objects) {
                Map proMap = (Map) product;
                Map declareInfo = (Map) proMap.get("declareInfo");

                Map specificationsInfo = (Map) proMap.get("specificationsInfo");
                String skuId = (String) specificationsInfo.get("skuId");
                declareInfoMap.put(skuId, declareInfo);
                declareLabelListmap.put(skuId, proMap.get("declareLabelList"));
                specificationsInfoMap.put(skuId, proMap.get("specificationsInfo"));

            }
        }
        Result lableResult = productService.getProductLabelAttr("admin");
        if (lableResult.getCode() == 200) {
            List lableList = (List) lableResult.getData();
            for (Object object : lableList) {
                Map lable = (Map) object;
                labelMap.put(lable.get("labelCd"), lable.get("labelDesc"));
            }
        }
        for (String skuId : skuIds) {
            PackingDetailEnity packingDetailEnity = new PackingDetailEnity();
            Map skuDeclareInfoMap = (Map) declareInfoMap.get(skuId);
            Map skuspecificationsInfoMap = (Map) specificationsInfoMap.get(skuId);
            List<Map> labelCds = (List) declareLabelListmap.get(skuId);
            for (Map labelCdMap : labelCds) {
                labelDesc.add((String) labelMap.get(labelCdMap.get("labelCd")));
            }
            if (skuDeclareInfoMap != null) {
                packingDetailEnity.setBrand((String) skuDeclareInfoMap.get("brands"));
                packingDetailEnity.setMaterial((String) skuDeclareInfoMap.get("materials"));
                packingDetailEnity.setModel((String) skuDeclareInfoMap.get("model"));
                packingDetailEnity.setLabelDesc(labelDesc);
                packingDetailEnity.setSkuId(skuId);
                packingDetailEnityList.add(packingDetailEnity);
                packingDetailEnity.setLength((Double) skuspecificationsInfoMap.get("length"));
                packingDetailEnity.setWidth((Double) skuspecificationsInfoMap.get("width"));
                packingDetailEnity.setHeight((Double) skuspecificationsInfoMap.get("height"));
                packingDetailEnity.setSingleWeight((Double) skuspecificationsInfoMap.get("netWeight"));
            }
        }
        return packingDetailEnityList;
    }

    @Override
    public List<String> getRemoveOrderIdList(String returnApplyId) {
        List<String> list = returnWarehouseApplyDao.getRemoveOrderIdList(returnApplyId);
        return list;
    }

    @Override
    public List<RemoveOrderEnity> getRemoveOrderEnity() {
        return returnWarehouseApplyDao.getRemoveOrderEnity(2);
    }

    @Override
    public List<Map> getAllRemoveOrderDetail(String returnApplyId) {
        List<RemoveOrderEnity> removeOrderEnityList = returnWarehouseApplyDao.getShopAndRemoveIds(returnApplyId);
        List<Map<String, String>> tracingMapList = new ArrayList<>();
        Map<String, String> tracingTimeMap = new HashMap();
        Gson gson = new Gson();
        if (removeOrderEnityList.size() > 0) {
            String orderList = gson.toJson(removeOrderEnityList);
            Result result = crawlerService.getRemoveOrderInfoList(gson.toJson(removeOrderEnityList));
            if (result.getCode() == 200) {
                List<Map> list = (List<Map>) result.getData();
                for (Map removeOrderInfoEnity : list) {
                    List<Map> removeOrderDetailEnityList = (List<Map>) removeOrderInfoEnity.get("removeOrderShipmentInfoList");
                    for (Map removeOrderDetailEnity : removeOrderDetailEnityList) {
                        String trackingNumber = (String) removeOrderDetailEnity.get("trackingNumber");
                        String carrier = (String) removeOrderDetailEnity.get("carrier");
                        Map<String, String> map = new HashMap();
                        map.put("trackingNumber", trackingNumber);
                        map.put("carrier", carrier);
                        tracingMapList.add(map);
                    }
                }
                Result result1 = crawlerService.geTracingRecordList(gson.toJson(tracingMapList));
                if (result1.getCode() == 200) {
                    List<Map> tracingRecordEnityList = (List<Map>) result1.getData();
                    if (tracingRecordEnityList != null && tracingRecordEnityList.size() > 0) {
                        for (Map tracingRecordEnity : tracingRecordEnityList) {
                            tracingTimeMap.put((String) tracingRecordEnity.get("trackingNumber"), (String) tracingRecordEnity.get("signTime"));
                        }
                    }
                }
                for (Map removeOrderInfoEnity : list) {
                    List<Map> removeOrderDetailEnityList = (List<Map>) removeOrderInfoEnity.get("removeOrderShipmentInfoList");
                    for (Map map : removeOrderDetailEnityList) {
                        map.put("signTime", tracingTimeMap.get(map.get("trackingNumber")));
                    }
                }
                return list;
            }
        }
        return null;
    }


    public String getReturnNoImpl(String rediskey) {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");
        String currDate = sdf.format(System.currentTimeMillis());
        String key = currDate + rediskey;
        int count = 0;
        long maxId = redisTemplate.opsForValue().increment(key, 1);
        if (maxId == 1) {
            redisTemplate.expire(key, 1000 * 60 * 60 * 24, TimeUnit.MILLISECONDS);
        }
        if (maxId > 999) {
            throw new RuntimeException("今日退仓申请已达最大");
        }
        long num = maxId;
        while (num > 0) {
            num = num / 10;
            count++;
        }
        if (count == 1) {
            return "R" + currDate + "00" + maxId;
        }
        if (count == 2) {
            return "R" + currDate + "0" + maxId;
        }
        if (count == 3) {
            return "R" + currDate + maxId;
        }
        return null;
    }
}
