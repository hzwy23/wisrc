package com.wisrc.replenishment.webapp.controller;

import com.wisrc.replenishment.webapp.entity.CustomsInfoEntity;
import com.wisrc.replenishment.webapp.service.CustomsInfoService;
import com.wisrc.replenishment.webapp.service.MskuInfoService;
import com.wisrc.replenishment.webapp.service.ReplenishShippingDataService;
import com.wisrc.replenishment.webapp.service.WaybillInfoService;
import com.wisrc.replenishment.webapp.utils.Result;
import com.wisrc.replenishment.webapp.vo.*;
import com.wisrc.replenishment.webapp.vo.transferorder.TransferDeclareVo;
import com.wisrc.replenishment.webapp.vo.waybill.WaybillMskuInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@Api(tags = "完善运单报关资料")
@RequestMapping(value = "/replenishment")
public class CustomsInfoController {
    @Autowired
    private CustomsInfoService customsInfoService;
    @Autowired
    private MskuInfoService mskuInfoService;
    @Autowired
    private WaybillInfoService waybillInfoService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ReplenishShippingDataService replenishShippingDataService;

    @ApiOperation(value = "新增报关资料信息---完善报关资料部分")
    @RequestMapping(value = "/customs/info", method = RequestMethod.POST)
    public Result addCustomsInfo(@RequestBody CompanyCustomsInfoVO vo) {
        try {
            String waybillId = vo.getWaybillId();
            CustomsInfoEntity entity = customsInfoService.get(waybillId);
            if (entity == null) {
                vo.setCustomsDeclareId(getBillId("getCustomsDeclareId"));
                customsInfoService.addCustomsInfo(vo);
                return Result.success(vo);
            } else {
                return Result.failure(390, "该运单报关资料已存在", vo);
            }

        } catch (Exception e) {
            return Result.failure(500, e.getMessage(), vo);
        }

    }

    @ApiOperation(value = "修改报关资料信息")
    @RequestMapping(value = "/customs/info", method = RequestMethod.PUT)
    public Result updateCustomsInfo(@RequestBody CompanyCustomsInfoVO vo) {

        String waybillId = vo.getWaybillId();
        CustomsInfoEntity entity = customsInfoService.get(waybillId);
        if (entity != null) {
            customsInfoService.updateCustomsInfo(vo);
            return Result.success(vo);
        } else {
            return Result.failure(500, "没有该运单的报关资料，请新增", vo);
        }
    }


    @ApiOperation(value = "补充发货数据", response = ReplenishShippingDataVO.class)
    @RequestMapping(value = "/customs/info/data", method = RequestMethod.PUT)
    public Result replenishShippingData(@RequestBody List<ReplenishShippingDataListVO> list, BindingResult result,
                                        @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "") @ApiIgnore String userId) {
        if (result.hasErrors()) {
            return Result.failure(390, result.getAllErrors().get(0).getDefaultMessage(), result.getAllErrors());
        }
        try {
            for (int i = 0; i < list.size(); i++) {
                replenishShippingDataService.replenishShippingData(list.get(i));
            }
            return Result.success(list);
        } catch (Exception e) {
            return Result.failure(500, e.getMessage(), list);
        }

    }


    @ApiOperation(value = "根据运单ID获取报关资料信息", response = SelectDeclareCustomVO.class)
    @RequestMapping(value = "/customs/info/{waybillId}", method = RequestMethod.GET)
    public Result get(@PathVariable String waybillId) {
        SelectDeclareCustomVO entity = customsInfoService.getVO(waybillId);
        if (entity == null) {
            return Result.success(null);
        }
        try {
            Map map = waybillInfoService.findMskuVO(waybillId);
            if (map.get("fba") != null) {
                entity.setMskuInfoVOList((List<WaybillMskuInfoVO>) map.get("fba"));
            } else {
                entity.setTransferDeclareVos((List<TransferDeclareVo>) map.get("transferWaybillDeclare"));
            }
            return Result.success(entity);
        } catch (Exception e) {
            return Result.success(entity);
        }
    }

    /*@ApiOperation(value = "根据UUID获取报关资料产品信息",response = CustomsListInfoVO.class)
    @RequestMapping(value = "/customs/product/info/{uuid}",method = RequestMethod.GET)
    public Result getCustomsMskuInfo(@PathVariable String uuid) {
        try {
            CustomsListInfoVO entity = customsInfoService.getCustomsMskuInfo(uuid);
            double grossWeight = entity.getNumberOfBoxes() * entity.getPackingWeight();
            entity.setGrossWeight(grossWeight);
            entity.setNetWeight(grossWeight - entity.getNumberOfBoxes() * 0.5);
            String mskuId = entity.getMskuId();
            Result result = mskuInfoService.getMskuInfo(mskuId);
            MskuInfoVO mskuInfo = new MskuInfoVO();
            Map map = (Map)result.getData();
            if (map != null && !"".equals(map)) {
                String storeSku = (String) map.get("storeSku");
                String mskuName = (String) map.get("mskuName");
                String storeName = "";
                String FnSKU = (String) map.get("fnsku");
                String ASIN = (String) map.get("asin");
                String salesStatus = "";
                LinkedHashMap salesStatusMap = (LinkedHashMap) map.get("salesStatus");
                salesStatus = (String) salesStatusMap.get("name");
                String manager = (String) map.get("employee");
                mskuInfo.setMsku(mskuId);
                mskuInfo.setStoreSku(storeSku);
                mskuInfo.setMskuName(mskuName);
                mskuInfo.setStoreName(storeName);
                mskuInfo.setFnSKU(FnSKU);
                mskuInfo.setASIN(ASIN);
                mskuInfo.setSalesStatus(salesStatus);
                mskuInfo.setManager(manager);
            }
            entity.setMskuInfoVO(mskuInfo);
            return Result.success(entity);
        } catch (Exception e) {
            return Result.failure();
        }
    }*/


    @ApiOperation(value = "根据UUID获取补充发货产品信息", response = ImproveSendDataVO.class)
    @RequestMapping(value = "/customs/product/data/{uuid}", method = RequestMethod.GET)
    public Result getImproveMskuInfo(@PathVariable String uuid) {
        try {
            ImproveSendDataVO entity = customsInfoService.getImproveMskuInfo(uuid);
            String mskuId = entity.getMskuId();
            String fbaReplenishmentId = entity.getFbaReplenishmentId();
            String batchNumber = customsInfoService.getBatchNumber(fbaReplenishmentId);
            Result result = mskuInfoService.getMskuInfo(mskuId);
            MskuInfoVO mskuInfo = new MskuInfoVO();
            String storeSku = null;
            String mskuName = null;
            Map map = (Map) result.getData();
            if (map != null && !"".equals(map)) {
                storeSku = (String) map.get("storeSku");
                mskuName = (String) map.get("mskuName");
                mskuInfo.setMsku(mskuId);
                mskuInfo.setStoreSku(storeSku);
                mskuInfo.setMskuName(mskuName);
            }
            entity.setReplenishmentBatch(batchNumber);
            entity.setStoreSku(storeSku);
            entity.setMskuName(mskuName);
            return Result.success(entity);
        } catch (Exception e) {
            return Result.failure();
        }
    }

    public String getBillId(String rediskey) {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMM");
        String currDate = sdf.format(System.currentTimeMillis());
        String key = currDate + rediskey;
        int count = 0;
        long maxId = redisTemplate.opsForValue().increment(key, 1);
        if (maxId == 1) {
            redisTemplate.expire(key, 1000 * 60 * 60 * 24, TimeUnit.MILLISECONDS);
        }
        if (maxId > 999) {
            throw new RuntimeException("订单号已达最大");
        }
        long num = maxId;
        while (num > 0) {
            num = num / 10;
            count++;
        }
        if (count == 1) {
            return "ZGTS" + currDate + "00" + maxId;
        }
        if (count == 2) {
            return "ZGTS" + currDate + "0" + maxId;
        }
        if (count == 3) {
            return "ZGTS" + currDate + maxId;
        }

        return "ZGTS" + currDate + maxId;
    }
}
