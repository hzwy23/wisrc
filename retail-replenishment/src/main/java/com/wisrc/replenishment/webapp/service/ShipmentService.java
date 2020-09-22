package com.wisrc.replenishment.webapp.service;

import com.wisrc.replenishment.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "retail-replenishment-shipment-23", url = "http://localhost:8080")
public interface ShipmentService {
    /**
     * 根据报价单ID查询物流报价详细内容
     *
     * @param offerId
     * @return
     */
    @RequestMapping(value = "/shipment/logistics/{offerId}", method = RequestMethod.GET)
    Result getShipment(@RequestParam("offerId") String offerId);

    /**
     * 根据报价单ID集合查询物流报价详细内容列表
     *
     * @param offerId
     * @return
     */
    @RequestMapping(value = "/shipment/logistics/all", method = RequestMethod.GET)
    Result getShipmentList(@RequestParam("offerId") String offerId);

    @GetMapping(value = "/shipment/logistics/list")
    Result searchShipment(@RequestParam("pageNum") String pageNum,
                          @RequestParam("pageSize") String pageSize,
                          @RequestParam("channelTypeCd") String channelTypeCd, @RequestParam("labelCd") String labelCd,
                          @RequestParam("offerTypeCd") int offerTypeCd, @RequestParam("keyWord") String keyWord);

    @RequestMapping(value = "/shipment/logistics/OfferList", method = RequestMethod.GET)
    Result getOfferId(@RequestParam("shipMentId") String shipMentId,
                      @RequestParam("offerTypeCd") Integer offerTypeCd);
}
