package com.wisrc.shipment.webapp.controller;

import com.google.gson.Gson;
import com.wisrc.shipment.basic.StartupListener;
import com.wisrc.shipment.webapp.dao.ReturnWarehouseApplyDao;
import com.wisrc.shipment.webapp.entity.ReceiveWarehouseEnity;
import com.wisrc.shipment.webapp.entity.RemoveOrderDetail;
import com.wisrc.shipment.webapp.entity.RequestVO;
import com.wisrc.shipment.webapp.service.externalService.MskuService;
import com.wisrc.shipment.webapp.service.externalService.WmsService;
import com.wisrc.shipment.webapp.utils.Result;
import com.wisrc.shipment.webapp.utils.Time;
import com.wisrc.shipment.webapp.vo.GoodsInfoVO;
import com.wisrc.shipment.webapp.vo.RemoverOrderDetailVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "手动同步wms服务")
@RestController
@RequestMapping(value = "/shipment")
public class WmsController {
    @Autowired
    private ReturnWarehouseApplyDao returnWarehouseApplyDao;
    @Autowired
    private MskuService mskuService;
    @Autowired
    private WmsService wmsService;
    @Autowired
    private RedisTemplate redisTemplate;
    protected static final Logger logger = LoggerFactory.getLogger(StartupListener.class);

    @ApiOperation(value = "手动触发退仓", notes = "换标详细")
    @RequestMapping(value = "/WMS/ReturnManual", method = RequestMethod.GET)
    public Result getLabelDetail(@RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "default") @ApiIgnore String userId) {
        Gson gson = new Gson();
        Thread thread = new Thread() {
            public void run() {
                while (true) {
                    String json = (String) redisTemplate.opsForList().rightPop("removeOrderInfo");
                    try {
                        if (json != null) {
                            List<GoodsInfoVO> list = new ArrayList<>();
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
                            Thread.currentThread().interrupt();
                        }
                    } catch (Exception e) {
                        logger.info(e.getMessage());
                        redisTemplate.opsForList().leftPush("removeOrderInfo", json);
                    }
                }
            }
        };
        thread.start();
        return Result.success("手动触发退仓");
    }

    @ApiOperation(value = "获取所有需要退仓的信息")
    @RequestMapping(value = "/WMS/ReturnManualInfo", method = RequestMethod.GET)
    public Result updateFnskuCodeFileId() {
        try {
            long size = redisTemplate.opsForList().size("removeOrderInfo");
            List lisr = redisTemplate.opsForList().range("removeOrderInfo", 0, size);
            return Result.success(lisr);
        } catch (Exception e) {
            return Result.failure(390, "获取所有需要退仓的信息失败", e.getMessage());
        }
    }

    @RequestMapping(value = "/redis/delete/{key}", method = RequestMethod.GET)
    @ApiOperation(value = "手动删除生产redis某个key数据")
    @ResponseBody
    public Result getTest(@PathVariable("key") String key) {
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            return Result.failure(390, "手动删除生产redis失败", e.getMessage());
        }
        return Result.success("清楚redis数据成功");
    }
}
