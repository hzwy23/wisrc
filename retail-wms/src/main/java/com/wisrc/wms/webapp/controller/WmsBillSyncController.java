package com.wisrc.wms.webapp.controller;

import com.google.gson.Gson;
import com.wisrc.wms.webapp.service.externalService.ReplenishmentService;
import com.wisrc.wms.webapp.utils.LocalStockUtil;
import com.wisrc.wms.webapp.utils.Result;
import com.wisrc.wms.webapp.utils.Time;
import com.wisrc.wms.webapp.vo.GoodsInfoVO;
import com.wisrc.wms.webapp.vo.RequestVO;
import com.wisrc.wms.webapp.vo.ResponseBean;
import com.wisrc.wms.webapp.vo.StockQueryVO;
import com.wisrc.wms.webapp.vo.BillSyncVO.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "WMS单据同步")
@PropertySource("classpath:config/wms.properties")
public class WmsBillSyncController {
    @Autowired
    RestTemplate restTemplate;
    @Value("${wms.url}")
    private String URL;
    @Value("${wms.format}")
    private String FORMAT;
    @Value("${wms.sync.purchaseOrder}")
    private String purchaseOrder;
    @Value("${wms.sync.virtualEnterBill}")
    private String virtualEnterBill;
    @Value("${wms.sync.arrivalNoticeBill}")
    private String arrivalNoticeBill;
    @Value("${wms.sync.arrivalQualityCheck}")
    private String arrivalQualityCheck;
    @Value("${wms.sync.rejectBill}")
    private String rejectBill;
    @Value("${wms.sync.virtualOutBill}")
    private String virtualOutBill;
    @Value("${wms.sync.saleOutBill}")
    private String saleOutBill;
    @Value("${wms.sync.fbaReplenishmentOutBill}")
    private String fbaReplenishmentOutBill;
    @Value("${wms.sync.purchaseRefundBill}")
    private String purchaseRefundBill;
    @Value("${wms.sync.scrapBill}")
    private String scrapBill;
    @Value("${wms.sync.cancelReplen}")
    private String cancelReplen;
    @Value("${wms.sync.handMadeEnterWarehouse}")
    private String handMadeEnterWarehouse;
    @Value("${wms.sync.handMadeOutWarehouse}")
    private String handMadeOutWarehouse;
    @Value("${wms.sync.logisticsOrDeclare}")
    private String logisticsOrDeclare;
    @Value("${wms.sync.transferout}")
    private String transferOut;
    @Value("${wms.sync.transferDoc}")
    private String transferDoc;

    @Autowired
    private LocalStockUtil localStockUtil;
    @Autowired
    private Gson gson;
    @Autowired
    private ReplenishmentService replenishmentService;

    @RequestMapping(value = "/purchase/order/sync", method = RequestMethod.POST)
    @ApiOperation(value = "采购订单同步")
    public Result purchaseOrderSync(@RequestBody PurchaseOrderVO entity) {
        try {
            RequestVO vo = new RequestVO();
            vo.setMethod(purchaseOrder);
            vo.setFormat(FORMAT);
            vo.setTimestamp(Time.getCurrentDateTime());
            vo.setData(entity);
            ResponseBean result = restTemplate.postForObject(URL, vo, ResponseBean.class);
            return Result.success(result.getCode(), result.getMessage(), result.getData());
        } catch (RestClientException e) {
            try {
                Thread.sleep(5000);
                purchaseOrderSync(entity);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            return Result.failure(300, e.getMessage(), "");
        }
    }

    @RequestMapping(value = "/virtualEnterBill/sync", method = RequestMethod.POST)
    @ApiOperation("虚拟入库单同步")
    public Result virtualEnterBillSync(@RequestBody VirtualEnterBillSyncVO entity) {
        try {
            RequestVO vo = new RequestVO();
            vo.setMethod(virtualEnterBill);
            vo.setFormat(FORMAT);
            vo.setTimestamp(Time.getCurrentDateTime());
            vo.setData(entity);
            ResponseBean result = restTemplate.postForObject(URL, vo, ResponseBean.class);
            List<StockQueryVO> list = new ArrayList();
            if (result.getCode() == 0) {
                for (GoodsInfoVO goodsInfoVO : entity.getGoodsList()) {
                    StockQueryVO stockQueryVO = new StockQueryVO();
                    stockQueryVO.setWarehouseId(entity.getSectionCode());
                    stockQueryVO.setSkuId(goodsInfoVO.getGoodsCode());
                    list.add(stockQueryVO);
                }
                try {
                    localStockUtil.localRefreshStockInfo(list);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return Result.success(result.getCode(), result.getMessage(), result.getData());
        } catch (RestClientException e) {
            try {
                Thread.sleep(5000);
                virtualEnterBillSync(entity);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            return Result.failure(300, e.getMessage(), "");
        }
    }

    @RequestMapping(value = "/arrivalNoticeBill/sync", method = RequestMethod.POST)
    @ApiOperation("到货通知单同步")
    public Result arrivalNoticeBillSync(@RequestBody ArrivalNoticeBillSyncVO entity) {
        try {
            RequestVO vo = new RequestVO();
            vo.setMethod(arrivalNoticeBill);
            vo.setFormat(FORMAT);
            vo.setTimestamp(Time.getCurrentDateTime());
            vo.setData(entity);
            ResponseBean result = restTemplate.postForObject(URL, vo, ResponseBean.class);
            return Result.success(result.getCode(), result.getMessage(), result.getData());
        } catch (RestClientException e) {
            try {
                Thread.sleep(5000);
                arrivalNoticeBillSync(entity);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            return Result.failure(300, e.getMessage(), "");
        }
    }

    @RequestMapping(value = "/arrivalQualityCheck/sync", method = RequestMethod.POST)
    @ApiOperation("到货通知单质检结果同步")
    public Result arrivalQualityCheckSync(@RequestBody ArrivalNoticeQualityCheckSyncVO entity) {
        try {
            RequestVO vo = new RequestVO();
            vo.setMethod(arrivalQualityCheck);
            vo.setFormat(FORMAT);
            vo.setTimestamp(Time.getCurrentDateTime());
            vo.setData(entity);
            ResponseBean result = restTemplate.postForObject(URL, vo, ResponseBean.class);
            return Result.success(result.getCode(), result.getMessage(), result.getData());
        } catch (RestClientException e) {
            try {
                Thread.sleep(5000);
                arrivalQualityCheckSync(entity);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            return Result.failure(300, e.getMessage(), "");
        }
    }

    @RequestMapping(value = "/rejectBill/sync", method = RequestMethod.POST)
    @ApiOperation("拒收单同步")
    public Result rejectBillSync(@RequestBody RejectBillSyncVO entity) {
        try {
            RequestVO vo = new RequestVO();
            vo.setMethod(rejectBill);
            vo.setFormat(FORMAT);
            vo.setTimestamp(Time.getCurrentDateTime());
            vo.setData(entity);
            ResponseBean result = restTemplate.postForObject(URL, vo, ResponseBean.class);
            return Result.success(result.getCode(), result.getMessage(), result.getData());
        } catch (RestClientException e) {
            try {
                Thread.sleep(5000);
                rejectBillSync(entity);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            return Result.failure(300, e.getMessage(), "");
        }
    }

    @RequestMapping(value = "/virtualOutBill/sync", method = RequestMethod.POST)
    @ApiOperation("虚拟出库单同步")
    public Result virtualOutBillSync(@RequestBody VirtualOutBillSync entity) {
        try {
            RequestVO vo = new RequestVO();
            vo.setMethod(virtualOutBill);
            vo.setFormat(FORMAT);
            vo.setTimestamp(Time.getCurrentDateTime());
            vo.setData(entity);
            ResponseBean result = restTemplate.postForObject(URL, vo, ResponseBean.class);
            return Result.success(result.getCode(), result.getMessage(), result.getData());
        } catch (RestClientException e) {
            try {
                Thread.sleep(5000);
                virtualOutBillSync(entity);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            return Result.failure(300, e.getMessage(), "");
        }
    }

    @RequestMapping(value = "/saleOutBill/sync", method = RequestMethod.POST)
    @ApiOperation("销售出库单同步")
    public Result saleOutBillSync(@RequestBody SaleOutBilSynclVO entity) {
        try {
            RequestVO vo = new RequestVO();
            vo.setMethod(saleOutBill);
            vo.setFormat(FORMAT);
            vo.setTimestamp(Time.getCurrentDateTime());
            vo.setData(entity);
            ResponseBean result = restTemplate.postForObject(URL, vo, ResponseBean.class);
            return Result.success(result.getCode(), result.getMessage(), result.getData());
        } catch (RestClientException e) {
            try {
                Thread.sleep(5000);
                saleOutBillSync(entity);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            return Result.failure(300, e.getMessage(), "");
        }
    }

    @RequestMapping(value = "/fbaReplenishmentOutBill/sync", method = RequestMethod.POST)
    @ApiOperation("fba补货出库单同步")
    public Result fbaReplenishmentOutBillSync(@RequestBody FbaReplenishmentOutBillSyncVO entity) {
        try {
            RequestVO vo = new RequestVO();
            vo.setMethod(fbaReplenishmentOutBill);
            vo.setFormat(FORMAT);
            vo.setTimestamp(Time.getCurrentDateTime());
            vo.setData(entity);
            ResponseBean result = restTemplate.postForObject(URL, vo, ResponseBean.class);
            return Result.success(result.getCode(), result.getMessage(), result.getData());
        } catch (RestClientException e) {
            try {
                Thread.sleep(5000);
                fbaReplenishmentOutBillSync(entity);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            return Result.failure(300, e.getMessage(), "");
        }
    }

    @RequestMapping(value = "/purchaseRefundBill/sync", method = RequestMethod.POST)
    @ApiOperation("采购退货单同步")
    public Result purchaseRefundBillSync(@RequestBody PurchaseRefundBillSyncVO entity) {
        try {
            RequestVO vo = new RequestVO();
            vo.setMethod(purchaseRefundBill);
            vo.setFormat(FORMAT);
            vo.setTimestamp(Time.getCurrentDateTime());
            vo.setData(entity);
            ResponseBean result = restTemplate.postForObject(URL, vo, ResponseBean.class);
            return Result.success(result.getCode(), result.getMessage(), result.getData());
        } catch (RestClientException e) {
            try {
                Thread.sleep(5000);
                purchaseRefundBillSync(entity);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            return Result.failure(300, e.getMessage(), "");
        }
    }

    //修改
    @RequestMapping(value = "/scrapBill/sync", method = RequestMethod.POST)
    @ApiOperation("报损单同步")
    public Result scrapBillSync(@RequestBody ScrapBillSyncVO entity) {
        try {
            RequestVO vo = new RequestVO();
            vo.setMethod(scrapBill);
            vo.setFormat(FORMAT);
            vo.setTimestamp(Time.getCurrentDateTime());
            vo.setData(entity);
            ResponseBean result = restTemplate.postForObject(URL, vo, ResponseBean.class);
            return Result.success(result.getCode(), result.getMessage(), result.getData());
        } catch (RestClientException e) {
            try {
                Thread.sleep(5000);
                scrapBillSync(entity);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            return Result.failure(300, e.getMessage(), "");
        }
    }

    @ApiOperation(value = "取消单同步")
    @RequestMapping(value = "/cancelReplen", method = RequestMethod.POST)
    public Result cancelReplen(@RequestBody FbaCancelReplenInfoVO entity) {
        try {
            RequestVO vo = new RequestVO();
            vo.setMethod(cancelReplen);
            vo.setFormat(FORMAT);
            vo.setTimestamp(Time.getCurrentDateTime());
            vo.setData(entity);
            ResponseBean result = restTemplate.postForObject(URL, vo, ResponseBean.class);
            //手动刷新sku和库存
            if (result.getCode() == 0) {
                Result fbaResult = replenishmentService.getFbaReplenishmentById(entity.getVoucherCode());
                if (fbaResult.getCode() == 200) {
                    List mskuInfoList = (List) ((Map) fbaResult.getData()).get("mskuInfoList");
                    List<StockQueryVO> queryVo = new LinkedList<>();
                    for (Object one : mskuInfoList) {
                        StockQueryVO sqVo = new StockQueryVO();
                        Object mskuInfo = ((Map) one).get("mskuInfoVO");
                        sqVo.setSkuId((String) ((Map) mskuInfo).get("mskuName"));
                        sqVo.setWarehouseId((String) ((Map) fbaResult.getData()).get("warehouseId"));
                    }
                    try {
                        localStockUtil.localRefreshStockInfo(queryVo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return Result.success(result.getCode(), result.getMessage(), result.getData());
        } catch (Exception e) {
            try {
                Thread.sleep(5000);
                cancelReplen(entity);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            return Result.failure(390, e.getMessage(), "");
        }
    }

    @ApiOperation(value = "手工入库单同步")
    @RequestMapping(value = "/handMadeEnterWarehouse/sync", method = RequestMethod.POST)
    public Result addEnterWarehouseSync(@RequestBody EnterWarehouseBillVO entity) {
        try {
            RequestVO vo = new RequestVO();
            vo.setMethod(virtualEnterBill);
            vo.setFormat(FORMAT);
            vo.setTimestamp(Time.getCurrentDateTime());
            vo.setData(entity);
            ResponseBean result = restTemplate.postForObject(URL, vo, ResponseBean.class);
            Result result1 = Result.success(result.getCode(), result.getMessage(), result.getData());
            //手动刷新库存sku
            if (result1.getCode() == 0) {
                List<StockQueryVO> queryVo = new LinkedList<>();
                for (EnterWarehouseBillDetailsVO one : entity.getGoodsList()) {
                    StockQueryVO sqVO = new StockQueryVO();
                    sqVO.setSkuId(one.getGoodsCode());
                    sqVO.setWarehouseId(entity.getSectionCode());
                    queryVo.add(sqVO);
                }
                try {
                    localStockUtil.localRefreshStockInfo(queryVo);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
            return result1;
        } catch (RestClientException e) {
            try {
                Thread.sleep(5000);
                addEnterWarehouseSync(entity);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            return Result.failure(300, e.getMessage(), "");
        }
    }

    @ApiOperation(value = "手工出库单同步")
    @RequestMapping(value = "/handMadeOutWarehouse/sync", method = RequestMethod.POST)
    public Result addOutWarehouseSync(@RequestBody OutWarehouseBillVO entity) {
        try {
            RequestVO vo = new RequestVO();
            vo.setMethod(virtualOutBill);
            vo.setFormat(FORMAT);
            vo.setTimestamp(Time.getCurrentDateTime());
            vo.setData(entity);
            ResponseBean result = restTemplate.postForObject(URL, vo, ResponseBean.class);
            Result result1 = Result.success(result.getCode(), result.getMessage(), result.getData());
            //手动刷新库存sku
            if (result1.getCode() == 0) {
                List<StockQueryVO> queryVo = new LinkedList<>();
                for (OutWarehouseBillDetailsVO one : entity.getGoodsList()) {
                    StockQueryVO sqVO = new StockQueryVO();
                    sqVO.setSkuId(one.getGoodsCode());
                    sqVO.setWarehouseId(entity.getSectionCode());
                }
                try {
                    localStockUtil.localRefreshStockInfo(queryVo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return result1;
        } catch (RestClientException e) {
            try {
                Thread.sleep(5000);
                addOutWarehouseSync(entity);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            return Result.failure(300, e.getMessage(), "");
        }
    }

    @ApiOperation(value = "物流/报关资料同步")
    @RequestMapping(value = "/logistics/declare/sync", method = RequestMethod.POST)
    public Result logisticsDeclareSync(@RequestBody LogisticsDeclareDocSyncVO entity) {
        try {
            RequestVO vo = new RequestVO();
            vo.setMethod(logisticsOrDeclare);
            vo.setFormat(FORMAT);
            vo.setTimestamp(Time.getCurrentDateTime());
            vo.setData(entity);
            ResponseBean result = restTemplate.postForObject(URL, vo, ResponseBean.class);
            Result result1 = Result.success(result.getCode(), result.getMessage(), result.getData());
            return result1;
        } catch (RestClientException e) {
            try {
                Thread.sleep(5000);
                logisticsDeclareSync(entity);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            return Result.failure(300, e.getMessage(), "");
        }
    }

    @ApiOperation(value = "调拨单出库单同步")
    @RequestMapping(value = "/transfer/sync", method = RequestMethod.POST)
    public Result transferSync(@RequestBody TransferOrderSyncVO entity) {
        try {
            RequestVO vo = new RequestVO();
            vo.setMethod(transferOut);
            vo.setFormat(FORMAT);
            vo.setTimestamp(Time.getCurrentDateTime());
            vo.setData(entity);
            ResponseBean responseBean = restTemplate.postForObject(URL, vo, ResponseBean.class);
            Result result = Result.success(responseBean.getCode(), responseBean.getMessage(), responseBean.getData());
            return result;
        } catch (RestClientException e) {
            return Result.failure(300, e.getMessage(), "");
        }
    }

    @ApiOperation(value = "调拨单虚拟入库同步")
    @RequestMapping(value = "/transfer/virtual/enter", method = RequestMethod.POST)
    public Result transferVirtualEnterSync(@RequestBody TransferVirtualSyncVO entity) {
        try {
            RequestVO vo = new RequestVO();
            vo.setMethod(virtualEnterBill);
            vo.setFormat(FORMAT);
            vo.setTimestamp(Time.getCurrentDateTime());
            vo.setData(entity);
            ResponseBean responseBean = restTemplate.postForObject(URL, vo, ResponseBean.class);
            Result result = Result.success(responseBean.getCode(), responseBean.getMessage(), responseBean.getData());
            return result;
        } catch (RestClientException e) {
            try {
                Thread.sleep(5000);
                transferVirtualEnterSync(entity);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            return Result.failure(300, e.getMessage(), "");
        }
    }

    @ApiOperation(value = "调拨单报关资料同步")
    @RequestMapping(value = "/transdoc/sync", method = RequestMethod.POST)
    public Result transferDocSync(@RequestBody TransferDocSyncVO entity) {
        try {
            RequestVO vo = new RequestVO();
            vo.setMethod(transferDoc);
            vo.setFormat(FORMAT);
            vo.setTimestamp(Time.getCurrentDateTime());
            vo.setData(entity);
            ResponseBean responseBean = restTemplate.postForObject(URL, vo, ResponseBean.class);
            Result result = Result.success(responseBean.getCode(), responseBean.getMessage(), responseBean.getData());
            return result;
        } catch (RestClientException e) {
            return Result.failure(300, e.getMessage(), "");
        }
    }

    @RequestMapping(value = "/returnWare/sync", method = RequestMethod.POST)
    @ApiOperation(value = "退仓单同步")
    public Result returnWareSync(@RequestBody RequestVO vo) {
        try {
            ResponseBean result = restTemplate.postForObject(URL, vo, ResponseBean.class);
            if (result.getCode() != 0) {
                return Result.failure(390, "推送wms失败", result.getMessage());
            }
            return Result.success(result.getCode(), result.getMessage(), result.getData());
        } catch (RestClientException e) {
            return Result.failure(390, "退仓同步异常", e.getMessage());
        }
    }

    @RequestMapping(value = "/changeLabel/sync", method = RequestMethod.POST)
    @ApiOperation(value = "换标单同步")
    public Result ChangeLabekSync(@RequestBody RequestVO vo) {
        try {
            ResponseBean result = restTemplate.postForObject(URL, vo, ResponseBean.class);
            if (result.getCode() != 0) {
                return Result.failure(390, "推送wms失败", result.getMessage());
            }
            return Result.success(result.getCode(), result.getMessage(), result.getData());
        } catch (RestClientException e) {
            return Result.failure(390, "换标单同步同步异常", e.getMessage());
        }
    }
}
