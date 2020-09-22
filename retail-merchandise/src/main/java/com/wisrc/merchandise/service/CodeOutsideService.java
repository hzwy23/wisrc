package com.wisrc.merchandise.service;

import com.wisrc.merchandise.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "retail-merchandise-code", url = "http://localhost:8080")
public interface CodeOutsideService {
    // 获取物流费用导入模板
    @RequestMapping(value = "/code/upload/file/{itemId}", method = RequestMethod.GET)
    Result getCodeTemplateConfById(@PathVariable("itemId") String itemId) throws Exception;

    // 获取国家选择项
    @RequestMapping(value = "/code/codeCountryInfo", method = RequestMethod.GET)
    Result getCountrySelector() throws Exception;

    // 获取产品特性选择项
    @RequestMapping(value = "/code/codePCLabel", method = RequestMethod.GET)
    Result getCharacteristicSelector() throws Exception;

    // 获取销售状态码表
    @RequestMapping(value = "/code/codeSalesStatus", method = RequestMethod.GET)
    Result getSalesStatus() throws Exception;

    // 获取固定参数
    @RequestMapping(value = "/code/properties", method = RequestMethod.GET)
    Result getKey(@RequestParam("keys") List<String> keys) throws Exception;
}
