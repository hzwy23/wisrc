package com.wisrc.code.webapp.controller;

import com.wisrc.code.webapp.entity.CodeProvinceCityInfoEntity;
import com.wisrc.code.webapp.service.CodeCountryInfoService;
import com.wisrc.code.webapp.utils.Result;
import com.wisrc.code.webapp.utils.Time;
import com.wisrc.code.webapp.utils.Toolbox;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@Api(tags = "国家信息码表")
@RequestMapping(value = "/code")
public class CodeCountryInfoController {
    private final Logger logger = LoggerFactory.getLogger(CodeCountryInfoController.class);

    @Autowired
    private CodeCountryInfoService codeCountryInfoService;

    @ApiOperation(value = "获取所有国家信息表信息", notes = "")
    @RequestMapping(value = "/codeCountryInfo", method = RequestMethod.GET)
    public Result findAll() {
        return codeCountryInfoService.findAll();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "countryCd", value = "国家编码", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "countryName", value = "国家名称", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "countryEnglish", value = "国家英文名", paramType = "query", dataType = "String", required = true),
    })
    @ApiOperation(value = "新增国家信息表信息", notes = "")
    @RequestMapping(value = "/codeCountryInfo", method = RequestMethod.POST)
    public Result insert(@RequestParam("countryCd") String countryCd,
                         @RequestParam("countryName") String countryName,
                         @RequestParam("countryEnglish") String countryEnglish,
                         @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {
        return codeCountryInfoService.insert(countryCd, countryName, countryEnglish, userId);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "countryCd", value = "国家编码", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "countryName", value = "国家名称", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "countryEnglish", value = "国家英文名", paramType = "query", dataType = "String", required = true),
    })
    @ApiOperation(value = "编辑国家信息表信息", notes = "")
    @RequestMapping(value = "/codeCountryInfo", method = RequestMethod.PUT)
    public Result update(@RequestParam("countryCd") String countryCd,
                         @RequestParam("countryName") String countryName,
                         @RequestParam("countryEnglish") String countryEnglish,
                         @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {
        return codeCountryInfoService.update(countryCd, countryName, countryEnglish, userId);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "countryCd", value = "国家编码", paramType = "path", dataType = "String", required = true),
    })
    @ApiOperation(value = "countryCd删除国家信息表信息", notes = "")
    @RequestMapping(value = "/codeCountryInfo/{countryCd}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable("countryCd") String countryCd) {
        return codeCountryInfoService.delete(countryCd);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "countryCd", value = "国家编码", paramType = "query", dataType = "String", required = false),
            @ApiImplicitParam(name = "countryName", value = "国家名称", paramType = "query", dataType = "String", required = false),
            @ApiImplicitParam(name = "countryEnglish", value = "国家英文名", paramType = "query", dataType = "String", required = false),
            @ApiImplicitParam(name = "pageNum", value = "当前页（小于1或者null为不分页）", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "单页条数（小于1或者null为不分页）", required = false, dataType = "int", paramType = "query")
    })
    @ApiOperation(value = "模糊查询国家信息表信息", notes = "")
    @RequestMapping(value = "/codeCountryInfo/fuzzy", method = RequestMethod.GET)
    public Result fuzzyQuery(String countryCd,
                             String countryName,
                             String countryEnglish,
                             Integer pageNum,
                             Integer pageSize) {
        return codeCountryInfoService.fuzzyQuery(countryCd, countryName, countryEnglish, pageNum, pageSize);
    }


    @RequestMapping(value = "/area", method = RequestMethod.GET)
    public Result findDetails(@RequestParam(value = "pageSize", required = false) String pageSize,
                              @RequestParam(value = "pageNum", required = false) String pageNum,
                              @RequestParam(value = "countryCd", required = false) String countryCd,
                              @RequestParam(value = "keyword", required = false) String keyword) {
        try {
            int num = Integer.valueOf(pageNum);
            int size = Integer.valueOf(pageSize);
            return Result.success(codeCountryInfoService.getAllPagging(num, size, keyword, countryCd));
        } catch (Exception e) {
            return Result.success(codeCountryInfoService.getAll(keyword, countryCd));
        }
    }

    @ApiOperation(value = "新增省市信息", notes = "新增省份或城市信息")
    @RequestMapping(value = "/area", method = RequestMethod.POST)
    public Result addProvinceCity(@Valid @RequestBody CodeProvinceCityInfoEntity ele,
                                  BindingResult bindingResult,
                                  @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {
        if (bindingResult.hasErrors()) {
            return Result.failure(423, bindingResult.getFieldError().getDefaultMessage(), ele);
        }

        if (ele.getTypeCd() == 1) {
            // 新增省份
            if (ele.getProvinceNameCn() == null || ele.getProvinceNameCn().trim().isEmpty()) {
                return new Result(9997, "国家中文名countryName不合法", null);
            }
            ele.setCityNameCn("");
            ele.setCityNameEn("");
        } else if (ele.getTypeCd() == 2) {
            if (ele.getCityNameCn() == null ||
                    ele.getCityNameCn().trim().isEmpty()) {
                return Result.failure(423, "城市中文名不能为空", ele);
            }
        }
        String time = Time.getCurrentDateTime();
        ele.setModifyUser(userId);
        ele.setCreateTime(time);
        ele.setModifyTime(time);
        ele.setCreateUser(userId);
        ele.setUuid(Toolbox.randomUUID());
        try {
            codeCountryInfoService.insert(ele);
            return Result.success();
        } catch (DuplicateKeyException e) {
            return Result.failure(423, "区域信息录入重复", ele);
        } catch (Exception e) {
            if (ele.getTypeCd() == 1) {
                return Result.failure(424, "国家简称不存在", ele);
            } else {
                return Result.failure(424, "省份信息不存在", ele);
            }
        }
    }

    @ApiOperation(value = "更新省市信息", notes = "根据id更新省会或省市信息")
    @RequestMapping(value = "/area", method = RequestMethod.PUT)
    public Result updateProvinceCity(@Valid @RequestBody CodeProvinceCityInfoEntity ele,
                                     BindingResult bindingResult,
                                     @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {
        if (bindingResult.hasErrors()) {
            return Result.failure(423, bindingResult.getFieldError().getDefaultMessage(), ele);
        }

        // typeCd == 1 更新省份
        if (ele.getTypeCd() == 1) {
            if (ele.getProvinceNameCn() == null || ele.getProvinceNameCn().trim().isEmpty()) {
                return new Result(9997, "国家中文名countryName不合法", null);
            }
            ele.setCityNameEn("");
            ele.setCityNameCn("");
        } else if (ele.getTypeCd() == 2) {
            // typeCd == 2 更新城市
            if (ele.getCityNameCn() == null ||
                    ele.getCityNameCn().trim().isEmpty()) {
                return Result.success(423, "城市中文名不能为空", ele);
            }
        }
        ele.setModifyTime(Time.getCurrentDateTime());
        ele.setModifyUser(userId);
        try {
            codeCountryInfoService.update(ele);
            return Result.success();
        } catch (DuplicateKeyException e) {
            return Result.failure(423, "重复的区域信息", ele);
        }

    }

    @ApiOperation(value = "删除省市信息", notes = "根据id删除省会或城市信息")
    @RequestMapping(value = "/area/{uuid}/{typeCd}", method = RequestMethod.DELETE)
    public Result deleteProvinceCity(@PathVariable("uuid") String uuid,
                                     @PathVariable("typeCd") int typeCd) {
        return codeCountryInfoService.deleteProvinceCode(uuid, typeCd);
    }

    @ApiOperation(value = "获取省份信息", notes = "获取指定国家下所有的省会信息")
    @RequestMapping(value = "/area/{countryCd}", method = RequestMethod.GET)
    public Result getProvinces(@PathVariable("countryCd") String countryCd,
                               @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {
        return codeCountryInfoService.getProvince(countryCd);
    }

}
