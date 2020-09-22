package com.wisrc.order.webapp.service.impl;

import com.wisrc.order.webapp.dao.OrderBasicInfoDao;
import com.wisrc.order.webapp.dao.OrderCommodityInfoDao;
import com.wisrc.order.webapp.dao.OrderCommodityStatusAttrDao;
import com.wisrc.order.webapp.dao.ReSendOrderDao;
import com.wisrc.order.webapp.entity.*;
import com.wisrc.order.webapp.service.ReSendOrderService;
import com.wisrc.order.webapp.service.externalService.OperationService;
import com.wisrc.order.webapp.service.externalService.ProductService;
import com.wisrc.order.webapp.service.externalService.WarehouseService;
import com.wisrc.order.webapp.utils.Result;
import com.wisrc.order.webapp.utils.Time;
import com.wisrc.order.webapp.utils.UUIDutil;
import com.wisrc.order.webapp.vo.SaleOrderCommodityInfoVO;
import com.wisrc.order.webapp.vo.SendOrderInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(transactionManager = "retailOrderTransactionManager")
public class ReSendOrderServiceImpl implements ReSendOrderService {
    @Autowired
    private ReSendOrderDao reSendOrderDao;
    @Autowired
    private OperationService operationService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderBasicInfoDao orderBasicInfoDao;
    @Autowired
    private OrderCommodityInfoDao orderCommodityInfoDao;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private OrderCommodityStatusAttrDao orderCommodityStatusAttrDao;


    @Override
    public List<SendOrderInfoVo> getAllByIds(String[] orderIDs) {
        return null;
    }

    @Override
    public Result reSendOrders(ReSendOrderEnity reSendOrderEnity, String userId) {
        OrderBasicInfoEntity orderBasicInfoEntity = orderBasicInfoDao.findById(reSendOrderEnity.getOrderId());
        if (orderBasicInfoEntity == null) {
            return Result.success();
        }
        if (orderBasicInfoEntity.getStatusCd() != 4) {
            return Result.failure(390, "非已发货订单无法重发", null);
        }
        String time = Time.getCurrentDateTime();
        String uuid = UUIDutil.randomUUID();
        reSendOrderEnity.setRedeliveryId(uuid);
        reSendOrderEnity.setCreateUser(userId);
//      物流单号暂时不处理
        reSendOrderEnity.setCreateTime(time);
        reSendOrderEnity.setModifyUser(userId);
        reSendOrderEnity.setModifyTime(time);
        reSendOrderDao.insertOrder(reSendOrderEnity);
        List<ReSendOrderProductDetaiEnity> orderProductDetaiEnities = reSendOrderEnity.getProductDetailInfo();
        for (ReSendOrderProductDetaiEnity reSendOrderProductDetaiEnity : orderProductDetaiEnities) {
            Integer num = orderCommodityInfoDao.getNumByOrderIDAndCommodityId(reSendOrderEnity.getOrderId(), reSendOrderProductDetaiEnity.getCommodityId());
            if (num == null) {
                num = 0;
            }
            if (num < reSendOrderProductDetaiEnity.getRedeliveryQuantity()) {
                throw new RuntimeException("重发数不能大于商品数量");
            }
            String productUUid = UUIDutil.randomUUID();
//            自己的唯一标识
            reSendOrderProductDetaiEnity.setUuid(productUUid);
            reSendOrderProductDetaiEnity.setRedeliveryId(uuid);
            reSendOrderDao.insertPrdocutDetail(reSendOrderProductDetaiEnity);
        }
//        暂时缺少添加订单重发标签
        return Result.success();
    }

    @Override
    public List<SaleOrderCommodityInfoVO> getAllById(String orderId) {
        HashSet<String> proIdSet = new HashSet<>();
        HashMap<String, Map> productMap = new HashMap<>();
        HashMap<String, String> comSkuMap = new HashMap<>();
        HashMap<String, Map> wareHouseMap = new HashMap<>();
        HashMap<Integer, String> commodityStatusMap = new HashMap<>();
        List<String> skuIdList = new ArrayList<>();
        List<SaleOrderCommodityInfoVO> saleOrderCommodityInfoVOList = new ArrayList<>();
        List<OrderCommodityInfoEntity> saleOrderCommodityInfoVOS = orderCommodityInfoDao.findByOrderId(orderId);
        List<OrderCommodityStatusAttrEntity> list = orderCommodityStatusAttrDao.findAll();
        for (OrderCommodityStatusAttrEntity orderCommodityStatusAttrEntity : list) {
            commodityStatusMap.put(orderCommodityStatusAttrEntity.getStatusCd(), orderCommodityStatusAttrEntity.getStatusName());
        }
        if (saleOrderCommodityInfoVOS.size() <= 0) {
            return saleOrderCommodityInfoVOList;
        }
        for (OrderCommodityInfoEntity orderCommodityInfoEntity : saleOrderCommodityInfoVOS) {
            proIdSet.add(orderCommodityInfoEntity.getCommodityId());
        }
        if (proIdSet.size() > 0) {
            String[] proIds = proIdSet.toArray(new String[proIdSet.size()]);
            Result cleaarResult = operationService.getProduct(proIds);
            if (cleaarResult.getCode() == 200) {
//              通过商品id查询仓库商品信息（sku编号，商品图片）
                Map<String, Object> map = (Map) cleaarResult.getData();
                List objects = (List) map.get("mskuInfoBatch");
                for (Object product : objects) {
                    Map proMap = (Map) product;
                    String skuId = (String) proMap.get("skuId");
                    String id = (String) proMap.get("id");
                    comSkuMap.put(id, skuId);
                    productMap.put(id, proMap);
                }
            }
        }
        for (String commodityId : proIdSet) {
            if (comSkuMap.get(commodityId) != null) {
                skuIdList.add(comSkuMap.get(commodityId));
            }
        }
        String[] skuIds = skuIdList.toArray(new String[proIdSet.size()]);
        Result wareHouseResult = warehouseService.getWareHouserInfo(skuIds);
        if (wareHouseResult.getCode() == 200) {
            List<Object> wareList = (List<Object>) wareHouseResult.getData();
            for (Object object : wareList) {
                Map houseMap = (Map) object;
                String warehouseId = (String) houseMap.get("warehouseId");
                String skuId = (String) houseMap.get("skuId");
                wareHouseMap.put(warehouseId + skuId, houseMap);
            }
        }
        for (OrderCommodityInfoEntity orderCommodityInfoEntity : saleOrderCommodityInfoVOS) {
            String commodityId = orderCommodityInfoEntity.getCommodityId();
            String warehouseId = orderCommodityInfoEntity.getWarehouseId();
            Map saleOrderProMap = productMap.get(commodityId);
            String commodityName = null;
            String picture = null;
            String skuId = null;
            if(saleOrderProMap!=null){
                commodityName = (String) saleOrderProMap.get("mskuName");
                picture = (String) saleOrderProMap.get("picture");
                skuId = (String) saleOrderProMap.get("skuId");
            }
            Map wareMap = wareHouseMap.get(warehouseId + skuId);
            SaleOrderCommodityInfoVO saleOrderCommodityInfoVO = new SaleOrderCommodityInfoVO();
            if (wareMap != null) {
                String skuNameZh = (String) wareMap.get("skuName");
                Integer enableStock = (Integer) wareMap.get("enableStockNum");
                String warehouseName = (String) wareMap.get("warehouseName");
                saleOrderCommodityInfoVO.setEnableStock(enableStock);
                saleOrderCommodityInfoVO.setSkuNameZh(skuNameZh);
                saleOrderCommodityInfoVO.setWarehouseName(warehouseName);
            }
            saleOrderCommodityInfoVO.setCommodityId(commodityId);
            saleOrderCommodityInfoVO.setAttributeDesc(orderCommodityInfoEntity.getAttributeDesc());
            saleOrderCommodityInfoVO.setCommodityName(commodityName);
            saleOrderCommodityInfoVO.setPicture(picture);
            saleOrderCommodityInfoVO.setUuid(orderCommodityInfoEntity.getUuid());
            saleOrderCommodityInfoVO.setOrderId(orderCommodityInfoEntity.getOrderId());
            saleOrderCommodityInfoVO.setSkuId(comSkuMap.get(orderCommodityInfoEntity.getCommodityId()));
            saleOrderCommodityInfoVO.setUnitPrice(orderCommodityInfoEntity.getUnitPrice());
            saleOrderCommodityInfoVO.setUnitPriceCurrency(orderCommodityInfoEntity.getUnitPriceCurrency());
            saleOrderCommodityInfoVO.setQuantity(orderCommodityInfoEntity.getQuantity());
            saleOrderCommodityInfoVO.setWarehouseId(warehouseId);
            saleOrderCommodityInfoVO.setStatusDesc(commodityStatusMap.get(orderCommodityInfoEntity.getStatusCd()));
            saleOrderCommodityInfoVOList.add(saleOrderCommodityInfoVO);
        }
        return saleOrderCommodityInfoVOList;
    }
}
