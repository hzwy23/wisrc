package com.wisrc.product.webapp.controller;

import com.wisrc.product.webapp.service.ProductModifyHistoryService;
import com.wisrc.product.webapp.service.ProductService;
import com.wisrc.product.webapp.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "历史纪录", tags = "历史纪录")
@RequestMapping(value = "/product")
public class ProductModifyHistoryController {
    private final Logger logger = LoggerFactory.getLogger(ProductModifyHistoryController.class);
    @Autowired
    private ProductModifyHistoryService productModifyHistoryService;
    @Autowired
    private ProductService productService;


    @ApiOperation(value = "获取历史纪录", notes = "获取历史纪录")
    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public Result getHistory(String skuId) {
        try {
            return productModifyHistoryService.getHistory(skuId);
        } catch (Exception e) {
            logger.warn("获取历史纪录失败", e);
            return new Result(9999, "获取历史纪录失败", e);
        }
    }

}
