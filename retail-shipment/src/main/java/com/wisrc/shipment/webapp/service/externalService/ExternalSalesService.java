package com.wisrc.shipment.webapp.service.externalService;

import com.wisrc.shipment.webapp.utils.Result;
import com.wisrc.shipment.webapp.vo.replenishmentEstimateListVO.get.GetReplenishmentEstimateListVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 销售
 */
@FeignClient(value = "retail-shipment-sales", url = "http://localhost:8080")
public interface ExternalSalesService {
    //批量判断时间段内msku的预估数量与库存数量关系
    @RequestMapping(value = "/sales/replenishment/estimate", method = RequestMethod.POST)
    Result replenishment(@RequestBody GetReplenishmentEstimateListVO vo);


}
