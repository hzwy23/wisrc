package com.wisrc.warehouse.webapp.service.externalService;

import com.wisrc.warehouse.webapp.vo.syncVO.WarehouseVo;
import com.wisrc.warehouse.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "retail-warehouse-wms-06", url = "http://localhost:8080")
public interface WmsService {
    /**
     * 仓库信息同步到wms
     *
     * @param warehouseVoList
     * @return
     */
    @RequestMapping(value = "/wms/add/warehouse", method = RequestMethod.POST, consumes = "application/json")
    Result warehouseSync(@RequestBody List<WarehouseVo> warehouseVoList);

    /**
     * 报损单同步到wms
     *
     * @param entity
     * @return
     */
    @RequestMapping(value = "/wms/scrapBill/sync", method = RequestMethod.POST, consumes = "application/json")
    Result scrapSync(@RequestBody String entity);


    /**
     * 销售出库单同步
     *
     * @param saleOutBilSyncVO
     * @return
     */
    @RequestMapping(value = "/wms/saleOutBill/sync", method = RequestMethod.POST, consumes = "application/json")
    Result saleOutBillSync(@RequestBody String saleOutBilSyncVO);

    /**
     * 手工入库单同步
     *
     * @param entity
     * @return
     */
    @RequestMapping(value = "/wms/handMadeEnterWarehouse/sync", method = RequestMethod.POST, consumes = "application/json")
    Result addEnterWarehouseBillSync(@RequestBody String entity);

    /**
     * 手工出库单同步
     *
     * @param entity
     * @return
     */
    @RequestMapping(value = "/wms/handMadeOutWarehouse/sync", method = RequestMethod.POST, consumes = "application/json")
    Result addOutWarehouseBillSync(@RequestBody String entity);

    /**
     * 加工任务单同步
     *
     * @param entity
     * @return
     */
    @RequestMapping(value = "/wms/virtualEnterBill/sync", method = RequestMethod.POST, consumes = "application/json")
    Result processTaskBillSync(@RequestBody String entity);


    /**
     * FBA补货单信息同步到wms
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/wms/fbaReplenishmentOutBill/sync", method = RequestMethod.POST, consumes = "application/json")
    Result syncFbaReplenishBill(@RequestBody String entity);

    @RequestMapping(value = "/wms/virtualEnterBill/sync", method = RequestMethod.POST, consumes = "application/json")
    Result virtualEnterBillSync(@RequestBody String entity);

    /**
     * 调拨单同步wms
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "/wms/transfer/sync", method = RequestMethod.POST, consumes = "application/json")
    Result transferOutSync(@RequestBody String vo);


    /**
     * 手工入库单同步到wms（到货通知单接口）
     *
     * @param entity
     * @return
     */
    @RequestMapping(value = "/wms/arrivalNoticeBill/sync", method = RequestMethod.POST, consumes = "application/json")
    Result handmadeBillSync(@RequestBody String entity);
}

