package com.wisrc.merchandise.outside;

import com.wisrc.merchandise.dto.WarehouseManageInfoDTO;
import com.wisrc.merchandise.dto.warehouse.FbaOnWayQuantityAndOnStockQuantityDTO;
import com.wisrc.merchandise.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "retail-merchandise-warehouse", url = "http://localhost:8080")
public interface WarehouseService {

    /**
     * 创建FBA补货仓
     */
    @RequestMapping(value = "/warehouse/manage/add", method = RequestMethod.POST, consumes = "application/json")
    Result addFbaWarehouse(@RequestBody WarehouseManageInfoDTO ele,
                           @RequestHeader(value = "X-AUTH-ID", required = false) String userId);


    @RequestMapping(value = "/warehouse/manage/status", method = RequestMethod.PUT)
    Result deleteWarehouse(@RequestParam(value = "warehouseId") String warehouseId,
                           @RequestParam(value = "statusCd") int statusCd);

    /**
     * 通过shop_id和msku、fnsku的数组获取msku在途和在仓的实时数据
     */
    @RequestMapping(value = "/warehouse/msku/fba", method = RequestMethod.POST, consumes = "application/json")
    Result getFbaOnWayQuantityAndOnStockQuantityListByShopIdAndMskuList(
            @RequestBody List<FbaOnWayQuantityAndOnStockQuantityDTO> fbaOnWayQuantityAndOnStockQuantityDTOS);
}
