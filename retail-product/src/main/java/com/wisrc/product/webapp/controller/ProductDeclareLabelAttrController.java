package com.wisrc.product.webapp.controller;

import com.wisrc.product.webapp.entity.ProductDeclareLabelAttrEntity;
import com.wisrc.product.webapp.service.ProductDeclareLabelAttrService;
import com.wisrc.product.webapp.utils.Result;
import com.wisrc.product.webapp.vo.productDeclareLabelAttr.BatchLabelCd;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RestController
@Api(value = "产品申报标签信息", tags = "产品申报标签信息")
@RequestMapping(value = "/product")
public class ProductDeclareLabelAttrController {
    private final Logger logger = LoggerFactory.getLogger(ProductDeclareLabelAttrController.class);
    @Autowired
    private ProductDeclareLabelAttrService service;

    @RequestMapping(value = "/declareLabelAttr/info", method = RequestMethod.GET
    )
    @ApiOperation(value = "获取所有曾经或现在使用过的标签码表信息", notes = "获取所有曾经或现在使用过的标签码表信息")
    public Result findAll() {
        return service.findAll();
    }

    @RequestMapping(value = "/declareLabelAttr/info/basic", method = RequestMethod.GET
    )
    @ApiOperation(value = "获取所有基础标签码表信息（无自定义标签）", notes = "获取所有基础标签码表信息（无自定义标签）")
    public Result getBasic() {
        return service.getBasic();
    }


    @ApiOperation(value = "通过编码查询标标签码表信息", notes = "通过编码查询标标签码表信息")
    @ApiImplicitParam(name = "labelCd", value = "标签编码", required = true, dataType = "int", paramType = "path")
    @RequestMapping(value = "/declareLabelAttr/info/{labelCd}", method = RequestMethod.GET)
    public Result findByLabelCd(@PathVariable("labelCd") int labelCd) {
        ProductDeclareLabelAttrEntity re = service.findByLabelCd(labelCd);
        return Result.success(re);
    }

    @ApiOperation(value = "通过编码查询码表信息", notes = "通过编码查询码表信息")
    @RequestMapping(value = "/declareLabelAttr/info/batch", method = RequestMethod.POST)
    public Result findByBatchLabelCd(@Valid @RequestBody BatchLabelCd batchLabelCd, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new Result(9999, bindingResult.getFieldError().getDefaultMessage(), null);
        }
        Set<Integer> set = new HashSet(batchLabelCd.getLabelCdList());
        List<ProductDeclareLabelAttrEntity> list = new ArrayList<>();
        for (Integer o : set) {
            ProductDeclareLabelAttrEntity re = service.findByLabelCd(o);
            if (re != null) {
                list.add(re);
            }
        }
        return Result.success(list);
    }


}
