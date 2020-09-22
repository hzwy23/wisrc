package com.wisrc.wms.webapp.controller;

import com.wisrc.wms.webapp.utils.Result;
import com.wisrc.wms.webapp.utils.Time;
import com.wisrc.wms.webapp.vo.RequestVO;
import com.wisrc.wms.webapp.vo.ResponseBean;
import com.wisrc.wms.webapp.vo.BasicSyncVO.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@Api(tags = "WMS基础信息同步")
@PropertySource("classpath:config/wms.properties")
public class WmsBasicInfoController {
    @Value("${wms.format}")
    private final String format = "json";
    @Value("${wms.url}")
    private String url;
    @Value("${wms.basic.warehouse}")
    private String warehouse;

    @Value("${wms.basic.productType}")
    private String productType;

    @Value("${wms.basic.addProduct}")
    private String addProduct;

    @Value("${wms.basic.addSupplier}")
    private String addSupplier;

    @Value("${wms.basic.addCustoms}")
    private String addCustoms;

    @RequestMapping(value = "/add/warehouse", method = RequestMethod.POST)
    @ApiOperation(value = "新增仓库信息")
    public Result addWarehouse(@RequestBody List<WarehouseVo> entityList) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            RequestVO vo = new RequestVO();
            vo.setMethod(warehouse);
            vo.setFormat(format);
            vo.setTimestamp(Time.getCurrentDateTime());
            vo.setData(entityList);
            ResponseBean result = restTemplate.postForObject(url, vo, ResponseBean.class);
            return Result.success(result);
        } catch (RestClientException e) {
            try {
                Thread.sleep(5000);
                addWarehouse(entityList);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            return Result.failure(300, e.getMessage(), "");
        }
    }

    @RequestMapping(value = "/add/product/type", method = RequestMethod.POST)
    @ApiOperation(value = "新增产品类别信息")
    public Result addProductType(@RequestBody List<ProductTypeVO> entityList) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            RequestVO vo = new RequestVO();
            vo.setMethod(productType);
            vo.setFormat(format);
            vo.setTimestamp(Time.getCurrentDateTime());
            vo.setData(entityList);
            ResponseBean result = restTemplate.postForObject(url, vo, ResponseBean.class);
            return Result.success(result);
        } catch (RestClientException e) {
            try {
                Thread.sleep(5000);
                addProductType(entityList);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            return Result.failure(300, e.getMessage(), "");
        }
    }


    @RequestMapping(value = "/add/product", method = RequestMethod.POST)
    @ApiOperation(value = "新增产品信息")
    public Result addProduct(@RequestBody List<ProductInfoVO> entityList) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            RequestVO vo = new RequestVO();
            vo.setMethod(addProduct);
            vo.setFormat(format);
            vo.setTimestamp(Time.getCurrentDateTime());
            vo.setData(entityList);
            ResponseBean result = restTemplate.postForObject(url, vo, ResponseBean.class);
            return Result.success(result);
        } catch (RestClientException e) {
            try {
                Thread.sleep(5000);
                addProduct(entityList);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            return Result.failure(300, e.getMessage(), "");
        }
    }

    @RequestMapping(value = "/add/supplier", method = RequestMethod.POST)
    @ApiOperation(value = "新增供应商信息")
    public Result addSupplier(@RequestBody List<SupplierInfoVO> entityList) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            RequestVO vo = new RequestVO();
            vo.setMethod(addSupplier);
            vo.setFormat(format);
            vo.setTimestamp(Time.getCurrentDateTime());
            vo.setData(entityList);
            ResponseBean result = restTemplate.postForObject(url, vo, ResponseBean.class);
            return Result.success(result);
        } catch (RestClientException e) {
            try {
                Thread.sleep(5000);
                addSupplier(entityList);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            return Result.failure(300, e.getMessage(), "");
        }
    }

    @RequestMapping(value = "/add/customs", method = RequestMethod.POST)
    @ApiOperation(value = "新增客户信息")
    public Result addCustoms(@RequestBody List<CustomsInfoVO> entityList) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            RequestVO vo = new RequestVO();
            vo.setMethod(addCustoms);
            vo.setFormat(format);
            vo.setTimestamp(Time.getCurrentDateTime());
            vo.setData(entityList);
            ResponseBean result = restTemplate.postForObject(url, vo, ResponseBean.class);
            return Result.success(result);
        } catch (RestClientException e) {
            try {
                Thread.sleep(5000);
                addCustoms(entityList);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            return Result.failure(300, e.getMessage(), "");
        }
    }

}
