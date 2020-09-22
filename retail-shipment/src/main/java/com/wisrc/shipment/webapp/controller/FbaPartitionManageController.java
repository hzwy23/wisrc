package com.wisrc.shipment.webapp.controller;

import com.wisrc.shipment.webapp.service.FbaPartitionManageService;
import com.wisrc.shipment.webapp.entity.FbaPartitionManageEntity;
import com.wisrc.shipment.webapp.service.externalService.CodeCountryInfoService;
import com.wisrc.shipment.webapp.utils.Result;
import com.wisrc.shipment.webapp.utils.UUIDutil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.text.SimpleDateFormat;
import java.util.Date;


@RestController
@Api(tags = "FBA分区表")
@RequestMapping(value = "/shipment")
public class FbaPartitionManageController {

    @Autowired
    private FbaPartitionManageService fpas;

    @Autowired
    private CodeCountryInfoService ccis;

    @ApiOperation("新增FBA分区表")
    @RequestMapping(value = "/partition/table", method = RequestMethod.POST)
    public Result add(@RequestParam(value = "partitionName", required = true) String partitionName,
                      @RequestParam(value = "countryCd", required = true) String countryCd,
                      @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {

        System.out.println(fpas.getByNameAndCountry(partitionName, countryCd));
        if (fpas.getByNameAndCountry(partitionName, countryCd) != null) {
            return Result.failure(390, "存在相同的国家和分区编号", null);
        }

        FbaPartitionManageEntity entity = new FbaPartitionManageEntity();
        entity.setPartitionId(UUIDutil.randomUUID());
        entity.setPartitionName(partitionName);
        entity.setCountryCd(countryCd);
        entity.setCreateUser(userId);
        entity.setCreateTime(getNowTine());
        entity.setModifyUser(userId);
        entity.setModifyTime(getNowTine());
        //状态的看前端的设定
        entity.setStatusCd(1);

        System.out.println(entity);
        try {
            fpas.add(entity);
            return Result.success("新增成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }

    @ApiOperation(value = "分页条件查询")
    @RequestMapping(value = "/partition/table", method = RequestMethod.GET)
    public Result getAll(@RequestParam(value = "pageNum", required = false) String pageNum,
                         @RequestParam(value = "pageSize", required = false) String pageSize,
                         @RequestParam(value = "countryCd", required = false) String countryCd,
                         @RequestParam(value = "statusCd", required = false, defaultValue = "0") int statusCd) {
        try {
            if (pageNum != null || pageSize != null) {
                int num = Integer.valueOf(pageNum);
                int size = Integer.valueOf(pageSize);
                return Result.success(fpas.getAll(num, size, countryCd, statusCd));
            } else {
                return Result.success(fpas.search(countryCd, statusCd));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }

    @ApiOperation(value = "精确查询")
    @RequestMapping(value = "/partition/table/detail", method = RequestMethod.GET)
    public Result getById(@RequestParam(value = "partitionId") String partitionId) {
        try {
            FbaPartitionManageEntity entity = fpas.getById(partitionId);
            return Result.success(200, "查询成功", entity);
        } catch (Exception e) {
            return Result.failure();
        }
    }

    @ApiOperation(value = "修改FBA分区表")
    @RequestMapping(value = "/partition/table", method = RequestMethod.PUT)
    public Result update(FbaPartitionManageEntity fpme, @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {
        try {
            if (fpas.getByNameAndCountry(fpme.getPartitionName(), fpme.getCountryCd()) != null) {
                return Result.failure(390, "存在相同的国家和分区编号", null);
            }
            fpme.setModifyUser(userId);
            fpme.setModifyTime(getNowTine());
            fpas.update(fpme);
            return Result.success("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }

    @ApiOperation(value = "删除FBA分区表")
    @RequestMapping(value = "/partition/table", method = RequestMethod.DELETE)
    public Result delete(@RequestParam(value = "partitionId", required = true) String partitionId) {
        try {
            fpas.delete(partitionId);
            return Result.success("删除成功");
        } catch (Exception e) {
            return Result.failure();
        }
    }

    @ApiOperation(value = "查询所有国家信息")
    @RequestMapping(value = "/partition/countryInfo", method = RequestMethod.GET)
    public Result getCountryInfo() {
        try {
            return ccis.getAllCountryInfo();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }

    //得到当前时间
    public String getNowTine() {
        Date now = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(now);
    }
}
