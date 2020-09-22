package com.wisrc.wms.webapp.controller;

import com.google.gson.Gson;
import com.wisrc.wms.webapp.service.asnycService.AsyncServiceTask;
import com.wisrc.wms.webapp.utils.LocalStockUtil;
import com.wisrc.wms.webapp.utils.Result;
import com.wisrc.wms.webapp.vo.SkuInfoVO;
import com.wisrc.wms.webapp.vo.StockQueryVO;
import com.wisrc.wms.webapp.service.externalService.*;
import com.wisrc.wms.webapp.vo.ReturnVO.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "WMS单据回写")
public class WmsBillReturnController {
    @Autowired
    private Gson gson;

    @Autowired
    private AsyncServiceTask asyncServiceTask;

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private FbaReplenishmentService fbaReplenishmentService;

    @Autowired
    private LocalStockUtil localStockUtil;

    @Autowired
    private ProductService productService;

    @Autowired
    private WmsBillSyncController wmsBillSyncController;

    @Autowired
    private ReplenishmentService replenishmentService;

    @RequestMapping(value = "/transfer/pack/data/return", method = RequestMethod.POST)
    @ApiOperation(value = "调拨单装箱数据回写")
    public Result packDataReturn(@RequestBody TransferOrderPackBasicVO transferOrderPackBasicVO) {
        try {
            return replenishmentService.transferPackageInfoReturn(gson.toJson(transferOrderPackBasicVO));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(390, "调拨单装箱数据回写失败", "");
        }
    }

    @RequestMapping(value = "/transfer/out/return", method = RequestMethod.POST)
    @ApiOperation(value = "调拨出库回写")
    public Result transferOutReturn(@RequestBody TransferOutBasicVO transferOutBasicVO) {
        try {
            Result transferResult = replenishmentService.getTransferInfoById(transferOutBasicVO.getTransferOrderId());
            if (transferResult.getCode() == 200) {
                List<StockQueryVO> stockQueryVOS = new ArrayList<>();
                Map transferInfo = (Map) ((Map) transferResult.getData()).get("transferBasicInfoEntity");
                String warehouseId = (String) transferInfo.get("subWarehouseEndId");
                for (TransferOutProductVO transferOutProductVO : transferOutBasicVO.getSkuList()) {
                    StockQueryVO stockQueryVO = new StockQueryVO();
                    stockQueryVO.setSkuId(transferOutProductVO.getSkuId());
                    stockQueryVO.setWarehouseId(warehouseId);
                    stockQueryVOS.add(stockQueryVO);
                }
                try {
                    localStockUtil.localRefreshStockInfo(stockQueryVOS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return replenishmentService.transferOutReturn(gson.toJson(transferOutBasicVO));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(390, "调拨出库回写", "");
        }
    }

    @RequestMapping(value = "/fba/pack/data/return", method = RequestMethod.POST)
    @ApiOperation(value = "FBA装箱数据回写")
    public Result fbaPackDataReturn(@RequestBody FbaPackingDataReturnVO entity) {
        try {
            Result result = fbaReplenishmentService.returnPackData(gson.toJson(entity));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(390, e.getMessage(), null);
        }
    }

    @RequestMapping(value = "/fba/replenishment/out/return", method = RequestMethod.POST)
    @ApiOperation(value = "FBA补货出库回写")
    public Result fbaReplenishmentOutReturn(@RequestBody FbaReplenishmentOutReturnVO entity) {
        try {
            //TODO  准备数据进行手动刷新
            List<StockQueryVO> stockQueryVOS = new ArrayList<>();
            Result fbaReplenishmentInfo = fbaReplenishmentService.getFbaReplenishmentInfoById(entity.getFbaReplenishmentId());
            if (fbaReplenishmentInfo.getCode() == 200) {
                Map fbaInfoEntity = (Map) ((Map) fbaReplenishmentInfo.getData()).get("infoEntity");
                String warehouseId = (String) fbaInfoEntity.get("warehouseId");
                for (FbaOutSkuVO fbaOutSkuVO : entity.getSkuList()) {
                    StockQueryVO stockQueryVO = new StockQueryVO();
                    stockQueryVO.setSkuId(fbaOutSkuVO.getSkuId());
                    stockQueryVO.setWarehouseId(warehouseId);
                    stockQueryVOS.add(stockQueryVO);
                }
                try {
                    localStockUtil.localRefreshStockInfo(stockQueryVOS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //回写到erp数据
            Result result = fbaReplenishmentService.returnFbaDeliver(gson.toJson(entity));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(390, e.getMessage(), "");
        }
    }

    @RequestMapping(value = "/process/task/bill/return", method = RequestMethod.POST)
    @ApiOperation(value = "加工任务单出库状态回写")
    public Result processTaskBillReturn(@RequestBody OutWarehouseStatusReturnVO outWarehouseStatusReturnVOS) {
        try {
            Result result = warehouseService.changeStatus(outWarehouseStatusReturnVOS.getProcessTaskId(), outWarehouseStatusReturnVOS.getCode());
            return result;
        } catch (Exception e) {
            return Result.failure(390, e.getMessage(), "");
        }
    }


    @RequestMapping(value = "/arrival/return", method = RequestMethod.POST)
    @ApiOperation(value = "到货通知单回写")
    public Result arrivalBillReturn(@RequestBody ArrivalBillReturnVO entity) {
        try {
            Result result = purchaseOrderService.updatePurchaseDate(gson.toJson(entity));

            //通过到货通知单单号查询仓库
            Result result1 = purchaseOrderService.getArrivalInfoById(entity.getArrivalId());

            //TODO 手动刷新本地相关sku库存
            if (result.getCode() == 200 && result1.getCode() == 200) {
                List<StockQueryVO> queryVo = new LinkedList<>();
                for (SkuInfoVO one : entity.getArrivalSkuList()) {
                    StockQueryVO vo = new StockQueryVO();
                    vo.setSkuId(one.getSkuId());
                    vo.setWarehouseId((String) (((Map) result1.getData()).get("arrivalWarehouseId")));
                    queryVo.add(vo);
                }
                try {
                    localStockUtil.localRefreshStockInfo(queryVo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(390, e.getMessage(), null);
        }
    }

    @RequestMapping(value = "/up/return", method = RequestMethod.POST)
    @ApiOperation(value = "商品上架回写")
    public Result upReturn(@RequestBody SkuPutawayReturnVO skuPutawayReturnVO) {
        asyncServiceTask.skuPutawayAsync(skuPutawayReturnVO);
        return Result.success();
    }

    @RequestMapping(value = "/purchase/rejection/return", method = RequestMethod.POST)
    @ApiOperation(value = "采购拒收单回写")
    public Result purchaseRejectionReturn(@RequestBody RejectBillReturnVO rejectBillReturnVO) {
        try {
            Result result = purchaseOrderService.changeRejectStatus(rejectBillReturnVO.getRejectionId(), rejectBillReturnVO.getStatusCd());
            return result;
        } catch (Exception e) {
            return Result.failure(390, e.getMessage(), "");
        }
    }


    @RequestMapping(value = "/purchase/return/return", method = RequestMethod.POST)
    @ApiOperation(value = "采购退货单回写")
    public Result purchaseReturnReturn(@RequestBody PurchaseRefundBillReturnVO purchaseRefundBillReturnVO) {
        asyncServiceTask.purchaseRefundAsync(purchaseRefundBillReturnVO);
        return Result.success();
    }
}
