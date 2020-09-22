package com.wisrc.replenishment.webapp.service;

import com.wisrc.replenishment.webapp.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "retail-replenishment-basic-13", url = "http://localhost:8080")
public interface CompanyService {
    /**
     * 获取公司报关信息
     *
     * @return
     */
    @GetMapping("/basic/companyinfo/customs")
    Result getCompanyCustomsInfo();

    /**
     * 获取公司信息
     *
     * @return
     */
    @GetMapping("/basic/companyinfo/basic")
    Result getCompanyInfo();

    /**
     * 获取物流商名称
     *
     * @return
     */
    @GetMapping("/basic/shipment/{id}")
    Result getShipmentName(@PathVariable("id") String id);

    /**
     * 获取征免性质码表
     *
     * @return
     */
    @GetMapping("/basic/exempting/nature/attr")
    Result natureAttr();

    /**
     * 获取征免方式码表
     *
     * @return
     */
    @GetMapping("/basic/exemption/mode/attr")
    Result modeAttr();

    /**
     * 获取贸易方式码表
     *
     * @return
     */
    @GetMapping("/basic/trade/mode/attr")
    Result tradeAttr();

    /**
     * 获取包装类型码表
     *
     * @return
     */
    @GetMapping("/basic/packing/type/attr")
    Result packingAttr();

    /**
     * 获取货币制度码表
     *
     * @return
     */
    @GetMapping("/basic/monetary/system/attr")
    Result monetaryAttr();

    /**
     * 获取交易模式码表
     *
     * @return
     */
    @GetMapping("/basic/transaction/mode/attr")
    Result transactionAttr();
}
