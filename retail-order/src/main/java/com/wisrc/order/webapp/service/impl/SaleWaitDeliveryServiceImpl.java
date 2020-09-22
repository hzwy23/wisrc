package com.wisrc.order.webapp.service.impl;

import com.wisrc.order.webapp.dao.*;
import com.wisrc.order.webapp.entity.*;
import com.wisrc.order.webapp.service.SaleWaitDeliveryService;
import com.wisrc.order.webapp.utils.Result;
import com.wisrc.order.webapp.utils.Time;
import com.wisrc.order.webapp.utils.UUIDutil;
import com.wisrc.order.webapp.vo.SplitOrderInfoListVO;
import com.wisrc.order.webapp.vo.SplitOrderInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class SaleWaitDeliveryServiceImpl implements SaleWaitDeliveryService {
    @Autowired
    private OrderBasicInfoDao orderBasicInfoDao;
    @Autowired
    private SaleWaitDeliveryDao saleWaitDeliveryDao;
    @Autowired
    private OrderNvoiceInfoDao orderNvoiceInfoDao;
    @Autowired
    private OrderNvoiceRelationDao orderNvoiceRelationDao;
    @Autowired
    private OrderRemarkInfoDao orderRemarkInfoDao;
    @Autowired
    private LogInfoDao logInfoDao;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private OrderStatusAttrDao orderStatusAttrDao;

    @Override
    @Transactional(transactionManager = "retailOrderTransactionManager")
    public void updateWaitStatus(String orderId, OrderRemarkInfoEntity entity, String userId) {
        List<OrderStatusAttr> orderStatusAttrList = orderStatusAttrDao.findAll();
        Map<Integer, String> statusMap = new HashMap();
        for (OrderStatusAttr orderStatusAttr : orderStatusAttrList) {
            statusMap.put(orderStatusAttr.getStatusCd(), orderStatusAttr.getStatusName());
        }
        String invoiceNumber = getSPNoImpl("_orderManage_addSendOrder");
        OrderBasicInfoEntity basicInfoEntity = orderBasicInfoDao.findById(orderId);
        orderRemarkInfoDao.add(entity);
        saleWaitDeliveryDao.updateWaitStatus(orderId);
        OrderNvoiceInfo info = new OrderNvoiceInfo();
        info.setInvoiceNumber(invoiceNumber);
        info.setStatusCd(1);
        info.setCreateTime(Time.getCurrentDateTime());
        info.setCreateUser(userId);
        orderNvoiceInfoDao.add(info);
        OrderNvoiceRelation ele = new OrderNvoiceRelation();
        ele.setUuid(UUIDutil.randomUUID());
        ele.setInvoiceNumber(invoiceNumber);
        ele.setOrderId(orderId);
        ele.setCreateTime(Time.getCurrentDateTime());
        ele.setOriginalOrderId(basicInfoEntity.getOriginalOrderId());
        ele.setCreateUser(userId);
        orderNvoiceRelationDao.add(ele);
        String time = Time.getCurrentDateTime();
        UpdateDetailEnity updateDetailEnity = new UpdateDetailEnity();
        updateDetailEnity.setUpdateTime(time);
        updateDetailEnity.setUpdateUser(userId);
        updateDetailEnity.setOrderId(orderId);
        String updateDetail = "    [订单状态]" + "由: " + statusMap.get(basicInfoEntity.getStatusCd()) + " 修改为: " + "已作废";
        updateDetailEnity.setUpdateDetail(updateDetail);
        logInfoDao.insert(updateDetailEnity);
    }


    @Override
    @Transactional
    public Result splitOrder(SplitOrderInfoListVO vo, String userId) {
        //生成新订单ID
        String orderId = toId(vo.getOrderId());
        if ("1".equals(orderId)) {
            return Result.success(390, "拆分次数已到达上限", "");
        }
        //新增拆分出来的订单信息
        addSplitOrder(vo.getOrderId(), orderId, userId);
        //获取拆分商品ID与数量
        List<SplitOrderInfoVO> list = vo.getSplitOrderInfoVO();
        for (SplitOrderInfoVO v : list) {
            String uuid = v.getUuid();
            //获取商品信息
            OrderCommodityInfoEntity entity = saleWaitDeliveryDao.getEntity(uuid);
            //拆分后原订单商品数量修改
            int i = entity.getQuantity() - v.getQuantity();
            if (i > 0) {
                saleWaitDeliveryDao.updateNum(uuid, i, userId, Time.getCurrentTimestamp());
            } else if (i == 0) {
                saleWaitDeliveryDao.delete(uuid, userId, Time.getCurrentTimestamp());
            }
            //生成新订单商品信息
            entity.setUuid(UUIDutil.randomUUID());
            entity.setCreateTime(Time.getCurrentTimestamp().toString());
            entity.setCreateUser(userId);
            entity.setOrderId(orderId);
            entity.setQuantity(v.getQuantity());
            entity.setDeleteStatus(0);
            saleWaitDeliveryDao.add(entity);
        }
        String time = Time.getCurrentDateTime();
        UpdateDetailEnity updateDetailEnity = new UpdateDetailEnity();
        updateDetailEnity.setUpdateTime(time);
        updateDetailEnity.setUpdateUser(userId);
        updateDetailEnity.setOrderId(orderId);
        String updateDetail = "    [订单状态]" + "由: " + "异常处理" + " 修改为: " + "已拆分";
        updateDetailEnity.setUpdateDetail(updateDetail);
        logInfoDao.insert(updateDetailEnity);
        updateDetail = "    [订单拆分]" + "子订单为: " + orderId;
        updateDetailEnity.setUpdateDetail(updateDetail);
        logInfoDao.insert(updateDetailEnity);
        return Result.success(200, "订单拆分成功", "");
    }

    /**
     * 生成拆分ID
     *
     * @param id
     * @return
     */
    private String toId(String id) {
        String[] str = id.split("-");
        if (str.length == 2) {
            int num = Integer.parseInt(saleWaitDeliveryDao.maxId(str[0]));
            //订单拆分次数
            if (num > 99) {
                return "1";
            }
            return str[0] + "-" + (num + 1);
        }
        String maxId = saleWaitDeliveryDao.maxId(id);
        if (maxId != "" || maxId != null) {
            int num = Integer.parseInt(maxId);
            //订单拆分次数
            if (num > 99) {
                return "1";
            }
            return str[0] + "-" + (num + 1);
        }
        return id + "-1";
    }

    /**
     * 新增拆分的出来的订单信息
     */
    private void addSplitOrder(String orderOld, String orderNew, String userId) {
        OrderCustomerInfoEntity customerById = saleWaitDeliveryDao.getCustomerById(orderOld);
        OrderLogisticsInfo logistics = saleWaitDeliveryDao.getLogisticsById(orderOld);
        OrderBasicInfoEntity order = saleWaitDeliveryDao.getOrderById(orderOld);
        if (order != null) {
            order.setOrderId(orderNew);
            order.setCreateTime(Time.getCurrentDateTime());
            order.setCreateUser(userId);
            saleWaitDeliveryDao.addOrder(order);
        }
        if (logistics != null) {
            logistics.setOrderId(orderNew);
            saleWaitDeliveryDao.addLogistics(logistics);
        }
        if (customerById != null) {
            customerById.setOrderId(orderNew);
            saleWaitDeliveryDao.addCustomer(customerById);
        }
    }

    public String getSPNoImpl(String rediskey) {
        SimpleDateFormat sdf = new SimpleDateFormat("YYMMdd");
        String currDate = sdf.format(System.currentTimeMillis());
        String key = currDate + "_orderManage_addSendOrder";
        long maxId = redisTemplate.opsForValue().increment(key, 1);
        if (maxId == 1) {
            redisTemplate.expire(key, 1000 * 60 * 60 * 24, TimeUnit.MILLISECONDS);
        }
        return "SP" + currDate + maxId;
    }
}
