package com.wisrc.merchandise.controller;


import com.wisrc.merchandise.service.impl.ProductServiceImpl;
import com.wisrc.merchandise.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/operation/store")
@Controller
@Api(tags = "产品控制器")
public class StoreController {
//    @Autowired
//    private ProductOutside productOutside;

    @Autowired
    private ProductServiceImpl productService;

    @GetMapping("/selector")
    @ApiOperation(value = "查询产品信息", notes = "")
    @ResponseBody
    public Result storeSelector() {
        return productService.getProductInfl();
    }

//    @PostMapping("/picture")
//    @ApiOperation(value = "查询产品图片", notes = "")
//    @ResponseBody
//    public Result storePicture(@RequestParam(value = "storeSkuId", required = true) List<String> storeSkuId) {
//        return productOutside.getProductPicture(storeSkuId);
//    }
//
//    @PostMapping("/cn")
//    @ApiOperation(value = "查询产品名称", notes = "")
//    @ResponseBody
//    public Result storeCN(@RequestParam(value = "storeSkuId", required = true) List<String> storeSkuId) {
//        return productOutside.getProductCN(storeSkuId);
//    }
}
