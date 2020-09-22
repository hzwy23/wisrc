package com.wisrc.wms.webapp.controller;


import com.wisrc.wms.webapp.dao.StockReturnDao;
import com.wisrc.wms.webapp.service.StockReturnService;
import com.wisrc.wms.webapp.utils.LocalStockUtil;
import com.wisrc.wms.webapp.utils.Result;
import com.wisrc.wms.webapp.utils.Time;
import com.wisrc.wms.webapp.vo.ReturnVO.OutEnterWaterReturnVO;
import com.wisrc.wms.webapp.vo.ReturnVO.StockReturnVO;
import com.wisrc.wms.webapp.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "从WMS获取相关信息")
@PropertySource("classpath:config/wms.properties")
public class WmsStockController {
    @Value("${wms.url}")
    private String url;

    @Value("${wms.format}")
    private String format;

    @Value("${wms.stock.stock}")
    private String stock;

    @Value("${wms.stock.zone}")
    private String zone;

    @Value("${wms.stock.loc}")
    private String loc;

    @Value("${wms.stock.flow}")
    private String flow;
    @Autowired
    private StockReturnService stockReturnService;

    @Autowired
    private StockReturnDao stockReturnDao;
    @Autowired
    private LocalStockUtil localStockUtil;

    @RequestMapping(value = "/get/warehouse/stock", method = RequestMethod.POST)
    @ApiOperation(value = "获取库存信息")
    public Result getWarehouseStock(@RequestBody Entity entity) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            RequestVO vo = new RequestVO();
            vo.setMethod(stock);
            vo.setFormat(format);
            vo.setTimestamp(Time.getCurrentDateTime());
            vo.setData(entity);
            ResponseBean result = restTemplate.postForObject(url, vo, ResponseBean.class);
            return Result.success(result.getData());
        } catch (Exception e) {
            try {
                Thread.sleep(5000);
                getWarehouseStock(entity);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            return Result.failure(300, e.getMessage(), entity);
        }
    }


    @RequestMapping(value = "/get/warehouse/zone", method = RequestMethod.POST)
    @ApiOperation(value = "获取库区信息")
    public Result getWarehouseZone(@RequestBody Entity entity) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            RequestVO vo = new RequestVO();
            vo.setMethod(zone);
            vo.setFormat(format);
            vo.setTimestamp(Time.getCurrentDateTime());
            vo.setData(entity);
            ResponseBean result = restTemplate.postForObject(url, vo, ResponseBean.class);
            return Result.success(result);
        } catch (Exception e) {
            try {
                Thread.sleep(5000);
                getWarehouseZone(entity);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            return Result.failure(300, e.getMessage(), "");
        }
    }

    @RequestMapping(value = "/get/warehouse/location", method = RequestMethod.POST)
    @ApiOperation(value = "获取库位信息")
    public Result getWarehouseLoc(@RequestBody Entity entity) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            RequestVO vo = new RequestVO();
            vo.setMethod(loc);
            vo.setFormat(format);
            vo.setTimestamp(Time.getCurrentDateTime());
            vo.setData(entity);
            ResponseBean result = restTemplate.postForObject(url, vo, ResponseBean.class);
            return Result.success(result);
        } catch (Exception e) {
            try {
                Thread.sleep(5000);
                getWarehouseLoc(entity);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            return Result.failure(300, e.getMessage(), "");
        }
    }

    @RequestMapping(value = "/get/warehouse/stock/water", method = RequestMethod.GET)
    @ApiOperation(value = "刷新所有流水信息")
    public Result getWatercourse() {
        try {
            WaterEntity entity = new WaterEntity();
            Integer waterMaxId = stockReturnDao.getWaterMaxId();
            if (waterMaxId == null) {
                waterMaxId = 0;
            }
            entity.setId(waterMaxId);
            RestTemplate restTemplate = new RestTemplate();
            RequestVO vo = new RequestVO();
            vo.setMethod(flow);
            vo.setFormat(format);
            vo.setTimestamp(Time.getCurrentDateTime());
            vo.setData(entity);
            ResponseBean result = restTemplate.postForObject(url, vo, ResponseBean.class);
            List<OutEnterWaterReturnVO> outEnterWaterReturnVOS = new ArrayList<>();
            if (result.getCode() == 0) {
                List<Object> list = (List<Object>) result.getData();
                for (Object o : list) {
                    Map data = (Map) o;
                    OutEnterWaterReturnVO outEnterWaterReturnVO = new OutEnterWaterReturnVO();
                    outEnterWaterReturnVO.setDetailId((int) data.get("id"));
                    outEnterWaterReturnVO.setWarehouseId((String) data.get("erpWhCode"));
                    outEnterWaterReturnVO.setSkuId((String) data.get("goodsCode"));
                    outEnterWaterReturnVO.setSkuName((String) data.get("goodsName"));
                    outEnterWaterReturnVO.setFnSkuId((String) data.get("fnCode"));
                    outEnterWaterReturnVO.setWarehousePosition((String) data.get("locCode"));
                    outEnterWaterReturnVO.setEnterBatch((String) data.get("inBatchNo"));
                    outEnterWaterReturnVO.setProductionBatch((String) data.get("productionBatchNo"));
                    if ((data.get("globalAvailableNum")) instanceof Double) {
                        outEnterWaterReturnVO.setEnableSumStockNum(((Double) data.get("globalAvailableNum")).intValue());
                    } else {
                        outEnterWaterReturnVO.setEnableSumStockNum((Integer) data.get("globalAvailableNum"));
                    }
                    if (data.get("globalTotalQuantity") instanceof Double) {
                        outEnterWaterReturnVO.setSumStockNum(((Double) data.get("globalTotalQuantity")).intValue());
                    } else {
                        outEnterWaterReturnVO.setSumStockNum((Integer) data.get("globalTotalQuantity"));
                    }
                    int beforNum = 0;
                    int afterNum = 0;
                    if (data.get("locTotalQuantity") instanceof Double) {
                        outEnterWaterReturnVO.setChangeAgoNum(((Double) data.get("locTotalQuantity")).intValue());
                        beforNum = ((Double) data.get("locTotalQuantity")).intValue();
                    } else {
                        outEnterWaterReturnVO.setChangeAgoNum((Integer) data.get("locTotalQuantity"));
                        beforNum = (Integer) data.get("locTotalQuantity");
                    }
                    if (data.get("afterLocTotalQuantity") instanceof Double) {
                        outEnterWaterReturnVO.setChangeLaterNum(((Double) data.get("afterLocTotalQuantity")).intValue());
                        afterNum = ((Double) data.get("afterLocTotalQuantity")).intValue();
                    } else {
                        outEnterWaterReturnVO.setChangeLaterNum((Integer) data.get("afterLocTotalQuantity"));
                        afterNum = (Integer) data.get("afterLocTotalQuantity");
                    }
                    outEnterWaterReturnVO.setChangeNum(afterNum - beforNum);

                    outEnterWaterReturnVO.setSourceId((String) data.get("voucherCode"));
                    outEnterWaterReturnVO.setDocumentType((String) data.get("voucherType"));
                    outEnterWaterReturnVO.setCreateTime((String) data.get("operatingTime"));
                    outEnterWaterReturnVO.setCreateUser((String) data.get("userName"));
                    outEnterWaterReturnVO.setRemark((String) data.get("remark"));
                    outEnterWaterReturnVOS.add(outEnterWaterReturnVO);
                }
            }
            stockReturnService.insertWater(outEnterWaterReturnVOS);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                Thread.sleep(5000);
                getWatercourse();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            return Result.failure(300, e.getMessage(), "");
        }
    }

    @RequestMapping(value = "/stock/refresh", method = RequestMethod.POST)
    @ApiOperation(value = "手动刷新stock数据")
    public Result refreshStockData(@RequestBody List<StockQueryVO> stockQueryVOS) {
        return localStockUtil.localRefreshStockInfo(stockQueryVOS);
    }

    @RequestMapping(value = "/stock/AutoRefresh", method = RequestMethod.GET)
    @ApiOperation("刷新所有的库存信息")
    public Result autoRefreshSockData() {

        try {
            RestTemplate restTemplate = new RestTemplate();
            Entity entity = new Entity();
            RequestVO vo = new RequestVO();
            vo.setMethod(stock);
            vo.setFormat(format);
            vo.setTimestamp(Time.getCurrentDateTime());
            vo.setData(entity);
            ResponseBean result = restTemplate.postForObject(url, vo, ResponseBean.class);
            List<Object> stockDataList = (List<Object>) result.getData();
            List<StockReturnVO> stockReturnVOS = new ArrayList<>();
            if (result.getCode() == 0) {
                for (Object data : stockDataList) {
                    Map map = (Map) data;
                    StockReturnVO stockReturnVO = new StockReturnVO();
                    stockReturnVO.setWarehouseId((String) map.get("erpWhCode"));
                    stockReturnVO.setWarehouseName((String) map.get("erpWhName"));
                    stockReturnVO.setSubWarehouseId((String) map.get("erpSectionCode"));
                    stockReturnVO.setSubWarehouseName((String) map.get("erpSectionName"));
                    stockReturnVO.setWarehouseZoneId((String) map.get("zoneCode"));
                    stockReturnVO.setWarehouseZoneName((String) map.get("zoneName"));
                    stockReturnVO.setWarehousePositionId((String) map.get("locCode"));
                    stockReturnVO.setFnSkuId((String) map.get("fnCode"));
                    stockReturnVO.setSkuId((String) map.get("goodsCode"));
                    stockReturnVO.setSkuName((String) map.get("goodsName"));
                    stockReturnVO.setEnterBatch((String) map.get("inBatchNo"));
                    stockReturnVO.setProductionBatch((String) map.get("productionBatchNo"));
                    stockReturnVO.setSumStock((Integer) map.get("totalQuantity"));
                    stockReturnVO.setEnableStockNum((Integer) map.get("availableNum"));
                    stockReturnVO.setFreezeStockNum((Integer) map.get("frozenNum"));
                    stockReturnVO.setAssignedNum((Integer) map.get("allocatedNum"));
                    stockReturnVO.setWaitUpNum((Integer) map.get("paNum"));
                    stockReturnVO.setReplenishmentWaitDownNum((Integer) map.get("rpOutNum"));
                    stockReturnVO.setReplenishmentWaitUpNum((Integer) map.get("rpInNum"));
                    stockReturnVO.setbId((Integer) map.get("balanceId"));
                    stockReturnVOS.add(stockReturnVO);
                }
            } else {
                throw new RuntimeException("wms查询库存数据失败：" + result.getMessage());
            }
            return stockReturnService.refreshLocalStock(stockReturnVOS);
        } catch (Exception e) {
            return Result.failure(390, e.getMessage(), "");
        }
    }
}
