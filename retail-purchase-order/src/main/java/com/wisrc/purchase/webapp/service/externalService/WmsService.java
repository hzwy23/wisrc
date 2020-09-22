package com.wisrc.purchase.webapp.service.externalService;

import com.wisrc.purchase.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "retail-purchase-wms-19", url = "http://localhost:8080")
public interface WmsService {
    /**
     * 采购订单同步
     *
     * @param purchaseOrderVO
     * @return
     */
    @RequestMapping(value = "/wms/purchase/order/sync", method = RequestMethod.POST, consumes = "application/json")
    Result purchaseOrderSync(@RequestBody String purchaseOrderVO);

    /**
     * 采购退货单同步
     *
     * @param purchaseRefundBillSyncVO
     * @return
     */
    @RequestMapping(value = "/wms/purchaseRefundBill/sync", method = RequestMethod.POST, consumes = "application/json")
    Result purchaseReturnOrderSync(@RequestBody String purchaseRefundBillSyncVO);

    /**
     * 到货通知单同步
     *
     * @param arrivalNoticeBillSyncVO
     * @return
     */
    @RequestMapping(value = "/wms/arrivalNoticeBill/sync", method = RequestMethod.POST, consumes = "application/json")
    Result arrivalNoticeBillSync(@RequestBody String arrivalNoticeBillSyncVO);

    /**
     * 采购拒收单同步
     *
     * @param rejectBill
     * @return
     */
    @RequestMapping(value = "/wms/rejectBill/sync", method = RequestMethod.POST, consumes = "application/json")
    Result rejectBillSync(@RequestBody String rejectBill);

    /**
     * 虚拟入库单同步
     *
     * @param virtualEnterBillSyncVO
     * @return
     */
    @RequestMapping(value = "/wms/virtualEnterBill/sync", method = RequestMethod.POST, consumes = "application/json")
    Result virtualEnterBillSync(@RequestBody String virtualEnterBillSyncVO);

    /**
     * 虚拟出库单同步
     *
     * @param virtualOutBillSync
     * @return
     */
    @RequestMapping(value = "/wms/virtualOutBill/sync", method = RequestMethod.POST, consumes = "application/json")
    Result virtualOutBillSync(@RequestBody String virtualOutBillSync);

    /**
     * 手动刷新stock数据
     *
     * @param stockQueryVOS
     * @return
     */
    @RequestMapping(value = "/wms/stock/refresh", method = RequestMethod.POST, consumes = "application/json")
    Result refreshStockData(@RequestBody String stockQueryVOS);

    @RequestMapping(value = "/wms/handMadeOutWarehouse/sync", method = RequestMethod.POST, consumes = "application/json")
    Result returnVirtualOutSync(@RequestBody String vo);
}
