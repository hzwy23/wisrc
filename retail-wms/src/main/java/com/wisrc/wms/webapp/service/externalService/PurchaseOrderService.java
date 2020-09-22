package com.wisrc.wms.webapp.service.externalService;

import com.wisrc.wms.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "retail-wms-purchase", url = "http://localhost:8080")
public interface PurchaseOrderService {
    /**
     * 到货通知单回写
     *
     * @param arrivalBillReturnVO
     * @return
     */
    @RequestMapping(value = "/purchase/arrival/wms/return", method = RequestMethod.PUT, consumes = "application/json")
    Result updatePurchaseDate(@RequestBody String arrivalBillReturnVO);

    /**
     * 商品上架回写
     *
     * @param entity
     * @return
     */
    @RequestMapping(value = "/purchase/entry/add", method = RequestMethod.POST, consumes = "application/json")
    Result addEntryBill(@RequestBody String entity);

    /**
     * 获取到货通知单信息
     *
     * @param arrivalId
     * @return
     */
    @RequestMapping(value = "/purchase/arrival/{arrivalId}", method = RequestMethod.GET)
    Result getArrivalInfoById(@PathVariable("arrivalId") String arrivalId);

    /**
     * 获取单活通知单的产品信息列表
     *
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/purchase/order/skuinfo", method = RequestMethod.GET)
    Result getSkuListInfoByOrderId(@RequestParam("orderId") String orderId);

    /**
     * 修改采购退货单状态
     *
     * @param returnBill
     * @param statusCd
     * @return
     */
    @RequestMapping(value = "/purchase/purchaseReturn/{returnBill}/{statusCd}", method = RequestMethod.PUT)
    Result updateStatus(@PathVariable("returnBill") String returnBill, @PathVariable("statusCd") Integer statusCd);

    /**
     * 修改采购拒收单状态
     *
     * @param rejectionId
     * @param statusCd
     * @return
     */
    @RequestMapping(value = "/purchase/purchaseRejection/{rejectionId}/{statusCd}", method = RequestMethod.PUT)
    Result changeRejectStatus(@PathVariable("rejectionId") String rejectionId,
                              @PathVariable("statusCd") Integer statusCd);

    /**
     * 根据到货通知单查询所有的采购入库单
     *
     * @param inspectionId
     * @return
     */
    @RequestMapping(value = "/purchase/entry/entryIds/{inspectionId}", method = RequestMethod.GET)
    Result findAllEntryIdByInspectionId(@PathVariable("inspectionId") String inspectionId);

    /**
     * 根据采购入库单和skuId查询对应的已经到货的信息
     *
     * @param entryId
     * @param skuId
     * @return
     */
    @RequestMapping(value = "/purchase/entry/entryProductInfo/{entryId}/{skuId}", method = RequestMethod.GET)
    Result getEntryProductByEntryIdAndSkuId(@PathVariable("entryId") String entryId,
                                            @PathVariable("skuId") String skuId);

    /**
     * 根据所有退货单号去查询退货单信息
     *
     * @param returnBill
     * @return
     */
    @RequestMapping(value = "/purchase/purchaseReturn/info/{returnBill}", method = RequestMethod.GET)
    Result getPurchaseReturnInfoById(@PathVariable("returnBill") String returnBill);

    /**
     * 根据采购入库id批量查
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/purchase/entry/entryProductInfo/batch", method = RequestMethod.GET)
    Result getEntryProductByEntryIds(@RequestParam("ids") List<String> ids);

    @RequestMapping(value = "/purchase/arrival/status/{arrivalId}/{skuId}/{statusCd}", method = RequestMethod.PUT)
    Result changeArrivalStatus(@PathVariable("arrivalId") String arrivalId,
                               @PathVariable("skuId") String skuId,
                               @PathVariable("statusCd") Integer status);

    /**
     * 根据到货通知单和skuID去查询出对应的状态
     *
     * @param arrivalId
     * @param skuId
     */
    @RequestMapping("/purchase/arrival/status/{arrivalId}/{skuId}")
    Result getStatusByArrivalIdAndSkuId(@PathVariable("arrivalId") String arrivalId,
                                        @PathVariable("skuId") String skuId);
}
