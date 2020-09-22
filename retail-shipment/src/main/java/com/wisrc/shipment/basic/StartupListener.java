package com.wisrc.shipment.basic;

import com.google.gson.Gson;
import com.wisrc.shipment.webapp.service.externalService.MskuService;
import com.wisrc.shipment.webapp.service.externalService.WmsService;
import com.wisrc.shipment.webapp.dao.ReturnWarehouseApplyDao;
import com.wisrc.shipment.webapp.entity.ReceiveWarehouseEnity;
import com.wisrc.shipment.webapp.entity.RemoveOrderDetail;
import com.wisrc.shipment.webapp.entity.RequestVO;
import com.wisrc.shipment.webapp.utils.Result;
import com.wisrc.shipment.webapp.utils.Time;
import com.wisrc.shipment.webapp.vo.GoodsInfoVO;
import com.wisrc.shipment.webapp.vo.RemoverOrderDetailVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class StartupListener implements ApplicationContextAware {
    @Autowired
    private ReturnWarehouseApplyDao returnWarehouseApplyDao;
    @Autowired
    private MskuService mskuService;
    @Autowired
    private WmsService wmsService;
    @Value("${wms.url}")
    private String url;
    protected static final Logger logger = LoggerFactory.getLogger(StartupListener.class);

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        RedisTemplate redisTemplate = (RedisTemplate) applicationContext.getBean("redisTemplate");
        Gson gson = new Gson();
        Thread thread = new Thread() {
            public void run() {
                while (true) {
                    String json = (String) redisTemplate.opsForList().rightPop("removeOrderInfo");
                    try {
                        if (json != null) {
                            List<GoodsInfoVO> list = new ArrayList<>();
                            logger.info(json);
                            RemoveOrderDetail removeOrderDetail = gson.fromJson(json, RemoveOrderDetail.class);
                            String returnApplyId = returnWarehouseApplyDao.getReturnId(removeOrderDetail.getOrderId());
                            if (returnApplyId == null) {
                                continue;
                            }
                            ReceiveWarehouseEnity receiveWarehouseEnity = returnWarehouseApplyDao.getReceiveWarehouseEnity(returnApplyId);
                            String shopId = returnWarehouseApplyDao.getShopIdById(returnApplyId);
                            if (receiveWarehouseEnity == null) {
                                continue;
                            }
                            RequestVO vo = new RequestVO();
                            vo.setMethod("wisen.wms.voucher.fbareturn.sync");
                            vo.setFormat("json");
                            vo.setTimestamp(Time.getCurrentDateTime());
                            RemoverOrderDetailVo removerOrderDetailVo = new RemoverOrderDetailVo();
                            long serial = redisTemplate.opsForValue().increment(returnApplyId + "returnWarehouseSerial", 1);
                            if (serial == 1) {
                                redisTemplate.expire(returnApplyId + "returnWarehouseSerial", 1000 * 60 * 60 * 24 * 90, TimeUnit.MILLISECONDS);
                                redisTemplate.opsForValue().set(returnApplyId + "returnWarehouseSerial", 1);
                            }
                            long num = serial;
                            int count = 0;
                            while (num > 0) {
                                num = num / 10;
                                count++;
                            }
                            if (count == 1) {
                                removerOrderDetailVo.setVoucherCode(returnApplyId + "-00" + serial);
                            } else if (count == 2) {
                                removerOrderDetailVo.setVoucherCode(returnApplyId + "-0" + serial);
                            } else {
                                removerOrderDetailVo.setVoucherCode(returnApplyId + "-" + serial);
                            }
                            removerOrderDetailVo.setVoucherType("TC");
                            removerOrderDetailVo.setSectionCode(receiveWarehouseEnity.getSubWarehouseId());
                            removerOrderDetailVo.setRemark("");
                            GoodsInfoVO goodsInfoVO = new GoodsInfoVO();
                            goodsInfoVO.setFnCode(removeOrderDetail.getFnsku());
                            goodsInfoVO.setLineNum(1);
                            Integer quantity = removeOrderDetail.getShippedQuantity();
                            if (quantity != null) {
                                Double totalQuantity = Double.parseDouble(quantity + "");
                                goodsInfoVO.setTotalQuantity(totalQuantity);
                                goodsInfoVO.setUnitQuantity(totalQuantity);
                            } else {
                                goodsInfoVO.setTotalQuantity(0.0);
                                goodsInfoVO.setUnitQuantity(0.0);
                            }
                            goodsInfoVO.setPackageQuantity(0.0);
                            Result mskuResult = mskuService.getMskuInfo(shopId, removeOrderDetail.getMsku(), "admin", 0);
                            if (mskuResult.getCode() == 200) {
                                Map theMap = (Map) mskuResult.getData();
                                if (theMap != null) {
                                    List<Map> mapList = (List<Map>) theMap.get("mskuList");
                                    if (mapList != null || mapList.size() >= 0) {
                                        Map map = mapList.get(0);
                                        String skuId = (String) map.get("skuId");
                                        String productName = (String) map.get("productName");
                                        goodsInfoVO.setGoodsName(productName);
                                        goodsInfoVO.setGoodsCode(skuId);
                                        list.add(goodsInfoVO);
                                        removerOrderDetailVo.setGoodsList(list);
                                        vo.setData(removerOrderDetailVo);
                                        logger.info(gson.toJson(vo));
                                        Result result = wmsService.returnWareSync(gson.toJson(vo));
                                        if (result.getCode() != 0) {
                                            throw new RuntimeException(result.getMsg());
                                        }
                                    }
                                }
                            } else {
                                throw new RuntimeException("推商品服务异常");
                            }
                        } else {
                            RedisConnection conn = RedisConnectionUtils.bindConnection(redisTemplate.getConnectionFactory(), true);
                            if (!conn.isClosed()) {
                                conn.close();
                            }
                            Thread.sleep(2000);
                        }
                    } catch (Exception e) {
                        redisTemplate.opsForList().leftPush("removeOrderInfo", json);
                    }
                }
            }
        };
        thread.start();
    }

}
