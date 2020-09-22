package com.wisrc.shipment.webapp.controller;

import com.wisrc.shipment.webapp.entity.LogisticsOfferHistoryInfoEntity;
import com.wisrc.shipment.webapp.service.LogisticsOfferHistoryInfoService;
import com.wisrc.shipment.webapp.utils.Crypto;
import com.wisrc.shipment.webapp.utils.Result;
import com.wisrc.shipment.webapp.utils.Time;
import com.wisrc.shipment.webapp.vo.swagger.LogisticsOfferHistoryInfoModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@Api(tags = "价格详细表维护")
@RequestMapping(value = "/shipment")
public class LogisticsOfferHistoryController {
    @Autowired
    private LogisticsOfferHistoryInfoService logisticsOfferHistoryInfoService;

    @RequestMapping(value = "/logistics/defail/update", method = RequestMethod.PUT)
    @ApiOperation(value = "更新报价单详细信息", notes = "更新报价单详细过程，主键ID不能编辑，报价单ID不能编辑，需要根据报价单ID增加对应的历史报价记录。 更新程序执行过程中，将会更新物流商ID对应行的其他字段值<br/>" +
            "1.如果在提交更新请求的过程中，有字段值没有设置值，则将会被设置为空<br/>" +
            "2.如果指定的店铺ID不存在，则不会更新任何数据<br/>", response = LogisticsOfferHistoryInfoModel.class)
    public Result update(LogisticsOfferHistoryInfoEntity ele, @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {
        String offerId = ele.getOfferId();
        String modifyTime = Time.getCurrentDateTime();
        ele.setUuid(Crypto.sha(offerId, modifyTime, String.valueOf(ele.getStartChargeSection())));
        logisticsOfferHistoryInfoService.add(ele);
        return Result.success("编辑报价单详细信息成功");
    }

    @ApiOperation(value = "根据报价单ID批量调整报价详细表价格")
    @RequestMapping(value = "/logistics/defail/batch", method = RequestMethod.PUT)
    public Result batchPrice(@RequestParam(value = "offerId", required = true) String[] offerIds,
                             @RequestParam(value = "num", required = true) double num) {
        try {
            Result result = logisticsOfferHistoryInfoService.batchPrice(offerIds, num);
            if (result.getCode() == 200) {
                return Result.success("批量调整价格成功");
            } else {
                return Result.success("批量调整价格失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.success("批量调整价格失败");
        }
    }

   /* @ApiOperation(value = "根据价格表主键UUID删除报价详细表价格")
    @RequestMapping(value = "/logistics/delete", method = RequestMethod.PUT)
    public Result deletePrice(@RequestParam(value = "offerId", required = true) String uuid
                             ) {
        Result result = logisticsOfferHistoryInfoService.deletePrice(uuid);
        if(result.getCode()==200){
            return Result.success("逻辑删除价格成功");
        }else{
            return Result.success("逻辑删除价格失败");
        }
    }*/
}

