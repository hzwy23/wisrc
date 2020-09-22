package com.wisrc.shipment.webapp.controller;

import com.wisrc.shipment.webapp.entity.*;
import com.wisrc.shipment.webapp.service.*;
import com.wisrc.shipment.webapp.entity.exception.MyException;
import com.wisrc.shipment.webapp.utils.Result;
import com.wisrc.shipment.webapp.utils.Time;
import com.wisrc.shipment.webapp.vo.LogisticsOfferVo;
import com.wisrc.shipment.webapp.vo.ParametersCheck;
import com.wisrc.shipment.webapp.vo.swagger.LogisticsOfferBasisInfoModel;
import com.wisrc.shipment.webapp.vo.swagger.LogisticsOfferHistoryInfoModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

@RestController
@Api(tags = "物流商报价管理")
@RequestMapping(value = "/shipment")
public class LogisticsOfferBasisInfoController {
    @Autowired
    private LogisticsOfferBasisInfoService logisticsOfferBasisInfoService;
    @Autowired
    private LogisticsOfferEffectiveService logisticsOfferEffectiveService;
    @Autowired
    private LogisticsOfferHistoryInfoService logisticsOfferHistoryInfoService;
    @Autowired
    private LogisticsAllowLabelRelService logisticsAllowLabelRelService;
    @Autowired
    private LogisicsOfferDestinationService logisicsOfferDestinationService;
    @Autowired
    private LittlePackOfferDetailsService littlePackOfferDetailsService;


    @ApiOperation(value = "新增FBA物流商报价信息", notes = "当新增物流商报价信息时，报价时效，报价区间，报价历史均需要新增相映的若干条数据 <br/>" +
            "报价ID作为外键约束存入其他表中。", response = LogisticsOfferBasisInfoModel.class)
    @RequestMapping(value = "/logistics/offer/Fba", method = RequestMethod.POST)
    @Transactional(transactionManager = "retailShipmentTransactionManager")
    public Result add(@RequestBody @Validated LogicOfferBasisInfoFbaEnity ele, BindingResult result,
                      @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "default") @ApiIgnore String userId) throws SQLException {
        if (result.hasErrors()) {
            return Result.failure(390, result.getAllErrors().get(0).getDefaultMessage(), result.getAllErrors());
        }
        List<LogisticsOfferEffectiveEntity> effeList = ele.getEffectiveList();
        List<LogisticsOfferHistoryInfoEntity> historyList = ele.getHistoryList();
        List<LogisticsAllowLabelRelEntity> labelList = ele.getLabelList();
        List<LogisticsOfferDestinationEnity> destList = ele.getDestinationList();
        ParametersCheck parameters = new ParametersCheck();
        parameters.setHisList(historyList);
//      校验区间是否重复
        Result hisListResult = checkHistoryList(historyList);
        if (hisListResult.getCode() != 200) {
            return hisListResult;
        }
//      检验时效
        Result effectResult = checkEffectList(effeList);
        if (effectResult.getCode() != 200) {
            return effectResult;
        }
        String offerId = UUID.randomUUID().toString();
        ele.setOfferId(offerId);
        ele.setModifyTime(Time.getCurrentDateTime());
        ele.setModifyUser(userId);
        ele.setDeleteStatus(0);
        try {
            logisticsOfferBasisInfoService.addFba(ele);
        } catch (MyException e) {
            throw new MyException(390, "重复", null);
        }
        for (LogisticsOfferDestinationEnity dest : destList) {
            dest.setShipmentId(ele.getShipmentId());
            dest.setChannelName(ele.getChannelName());
        }
        saveMutiplyList(labelList, effeList, destList, offerId);
        String modifyTime = Time.getCurrentDateTime();
        for (LogisticsOfferHistoryInfoEntity history : historyList) {
            history.setUuid(UUID.randomUUID().toString());
            history.setOfferId(offerId);
            history.setModifyTime(modifyTime);
            history.setModifyUser(userId);
            history.setDeleteStatus(0);
            logisticsOfferHistoryInfoService.add(history);
        }
        return Result.success("新增FBA物流报价信息");
    }

    /**
     * 检验FBA时效区间是否合法
     *
     * @param effeList 时效区间信息
     * @return
     */
    private Result checkEffectList(List<LogisticsOfferEffectiveEntity> effeList) {
        if (effeList == null || effeList.size() > 10 || effeList.size() <= 0) {
            return Result.failure(390, "时效信息不能为空或大于10", null);
        }
        for (int i = 0; i < effeList.size(); i++) {
            Integer preFirstStart = effeList.get(i).getStartDays();
            Integer preFirstEnd = effeList.get(i).getEndDays();
            if (preFirstEnd == null) {
                preFirstEnd = Integer.MAX_VALUE;
            }
            if (preFirstEnd < preFirstStart) {
                return Result.failure(390, "未按要求传递时效", null);
            }
            for (int j = i + 1; j < effeList.size(); j++) {
                Integer endDays = effeList.get(j).getEndDays();
                if (endDays == null) {
                    endDays = Integer.MAX_VALUE;
                }
                boolean condition1 = endDays > preFirstStart & endDays <= preFirstEnd;
                boolean conditon2 = preFirstStart >= effeList.get(j).getStartDays() & preFirstStart < endDays;
                if (condition1 || conditon2) {
                    return Result.failure(390, "有效时间区间有交集", null);
                }
            }
        }
        return Result.success();
    }

    /**
     * 检验FBA报价历史区间是否合法
     *
     * @param historyList 报价单信息
     * @return
     */
    private Result checkHistoryList(List<LogisticsOfferHistoryInfoEntity> historyList) {
        if (historyList == null || historyList.size() > 10 || historyList.size() <= 0) {
            return Result.failure(390, "区间信息不能为空或大于10个", null);
        }
        for (int i = 0; i < historyList.size(); i++) {
            Double preFirstStart = historyList.get(i).getStartChargeSection();
            Double preFirstEnd = historyList.get(i).getEndChargeSection();
            if (preFirstEnd == null) {
                preFirstEnd = Double.MAX_VALUE;
            }
            if (preFirstEnd != null && preFirstStart == null) {
                return Result.failure(390, "未按要求传递价格表", null);
            }
            if (preFirstEnd != null) {
                if (preFirstEnd < preFirstStart) {
                    return Result.failure(390, "未按要求传递价格表", null);
                }
            }
            for (int j = i + 1; j < historyList.size(); j++) {
                Double endChargeSection = historyList.get(j).getEndChargeSection();
                if (historyList.get(j).getEndChargeSection() == null) {
                    endChargeSection = Double.MAX_VALUE;
                }
                boolean condition1 = endChargeSection > preFirstStart & endChargeSection <= preFirstEnd;
                boolean conditon2 = preFirstStart >= historyList.get(j).getStartChargeSection() & preFirstStart < endChargeSection;
                if (condition1 || conditon2) {
                    return Result.failure(390, "区间有交集", null);
                }
            }
        }
        return Result.success();
    }

    @ApiOperation(value = "新增小包物流商报价信息", notes = "当新增物流商报价信息时，报价时效，报价区间,均需要新增相映的若干条数据 <br/>" +
            "报价ID作为外键约束存入其他表中。", response = LogisticsOfferBasisInfoEntity.class)
    @RequestMapping(value = "/logistics/offer/littlePacket", method = RequestMethod.POST)
    @Transactional(transactionManager = "retailShipmentTransactionManager")
    public Result addlittlePacket(@RequestBody @Validated LogicOfferBasisInfoLittilEnity ele, BindingResult result,
                                  @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "default") @ApiIgnore String userId) {
        if (result.hasErrors()) {
            return Result.failure(390, result.getAllErrors().get(0).getDefaultMessage(), result.getAllErrors());
        }
        List<LogisticsOfferEffectiveEntity> effeList = ele.getEffectiveList();
        List<LogisticsAllowLabelRelEntity> labelList = ele.getLabelList();
        List<LogisticsOfferDestinationEnity> destList = ele.getDestinationList();
        List<LittlePackOfferDetailsEntity> littlePacketList = ele.getLittlePacketList();
        String offerId = UUID.randomUUID().toString();
        ele.setOfferId(offerId);
        ele.setModifyTime(Time.getCurrentDateTime());
        ele.setModifyUser(userId);
        ele.setDeleteStatus(0);
//      检验时效区间是否合法
        Result effectResult = checkEffectList(effeList);
        if (effectResult.getCode() != 200) {
            return effectResult;
        }
//      检验小包价格表信息
        Result litttlePackResult = checkLittPacketList(littlePacketList);
        if (litttlePackResult.getCode() != 200) {
            return litttlePackResult;
        }
        try {
            logisticsOfferBasisInfoService.addLittle(ele);
        } catch (MyException e) {
            throw new MyException(390, "重复", null);
        }
        for (LogisticsOfferDestinationEnity dest : destList) {
            dest.setShipmentId(ele.getShipmentId());
            dest.setChannelName(ele.getChannelName());
        }
        saveMutiplyList(labelList, effeList, destList, offerId);
        for (LittlePackOfferDetailsEntity littlePacket : littlePacketList) {
            littlePacket.setUuid(UUID.randomUUID().toString());
            littlePacket.setOfferId(offerId);
            littlePacket.setDeleteStatus(0);
            littlePackOfferDetailsService.saveLittle(littlePacket);
        }
        return Result.success("新增小包物流报价信息");
    }


    /**
     * 检验小包价格表区间是否合法
     *
     * @param littlePacketList 报价单信息
     * @return
     */
    private Result checkLittPacketList(List<LittlePackOfferDetailsEntity> littlePacketList) {
        for (int i = 0; i < littlePacketList.size(); i++) {
            Double preFirstStart = littlePacketList.get(i).getStartChargeSection();
            Double preFirstEnd = littlePacketList.get(i).getEndChargeSection();
            if (preFirstEnd != null && preFirstStart == null) {
                return Result.failure(390, "未按要求传递价格表", null);
            }
            if (preFirstEnd != null) {
                if (preFirstEnd < preFirstStart) {
                    return Result.failure(390, "未按要求传递价格表", null);
                }
            }
            for (int j = i + 1; j < littlePacketList.size(); j++) {
                boolean condition1 = littlePacketList.get(j).getEndChargeSection() > preFirstStart & littlePacketList.get(j).getEndChargeSection() <= preFirstEnd;
                boolean conditon2 = preFirstStart >= littlePacketList.get(j).getStartChargeSection() & preFirstStart < littlePacketList.get(j).getEndChargeSection();
                if (condition1 || conditon2) {
                    return Result.failure(390, "区间有交集", null);
                }
            }
        }
        return Result.success();
    }

    //抽取部分通用代码
    private void saveMutiplyList(List<LogisticsAllowLabelRelEntity> labelList, List<LogisticsOfferEffectiveEntity> effeList, List<LogisticsOfferDestinationEnity> destList, String offerId) {
        if (labelList != null) {
            for (LogisticsAllowLabelRelEntity label : labelList) {
                label.setUuid(UUID.randomUUID().toString());
                label.setOfferId(offerId);
                label.setDeleteStatus(0);
                logisticsAllowLabelRelService.add(label);
            }
        }
        if (destList != null) {
            for (LogisticsOfferDestinationEnity dest : destList) {
                dest.setUuid(UUID.randomUUID().toString());
                dest.setOfferId(offerId);
                dest.setDeleteStatus(0);
                logisicsOfferDestinationService.add(dest);
            }
        }
        if (effeList != null) {
            for (LogisticsOfferEffectiveEntity effe : effeList) {
                effe.setUuid(UUID.randomUUID().toString());
                effe.setOfferId(offerId);
                effe.setDeleteStatus(0);
                logisticsOfferEffectiveService.add(effe);
            }
        }
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码", required = false, defaultValue = "1", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = false, defaultValue = "10", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "offerId", value = "报价ID", required = true, dataType = "String", paramType = "query")
    })
    @ApiOperation(value = "根据报价ID查询历史报价", notes = "通过传入offerId查询历史报价表的关联数据，返回所有的历史报价数据。", response = LogisticsOfferHistoryInfoModel.class)
    @RequestMapping(value = "logistics/history", method = RequestMethod.GET)
    public Result findHistoryOffer(@RequestParam(value = "pageNum", required = false, defaultValue = "1") String pageNum,
                                   @RequestParam(value = "pageSize", required = false, defaultValue = "10") String pageSize,
                                   @RequestParam(value = "offerId", required = true) String offerId) {
        int num = Integer.valueOf(pageNum);
        int size = Integer.valueOf(pageSize);
        LinkedHashMap map = logisticsOfferHistoryInfoService.findHistory(num, size, offerId);
        return Result.success(map);
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码(不传查全部数据)", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数(不传查全部数据)", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "channelTypeCd", value = "渠道类型ID", required = false, defaultValue = "0", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "labelCd", value = "标签ID", required = false, defaultValue = "0", dataType = "int", allowMultiple = true, paramType = "query"),
            @ApiImplicitParam(name = "deleteStatus", value = "删除状态(2为全部)", required = false, defaultValue = "2", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "offerTypeCd", value = "报价类型代号", required = false, defaultValue = "0", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "keyword", value = "关键字", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "zipcode", value = "关键字", required = false, dataType = "String", paramType = "query")

    })

    @ApiOperation(value = "分页模糊查询物流报价表", notes = "根据分页和模糊调价查询物流报价内容，包括物流时效内容，计费区间内容，报价标签内容。", response = LogisticsOfferBasisInfoEntity.class)
    @RequestMapping(value = "logistics/list", method = RequestMethod.GET)
    public Result findByCond(@RequestParam(value = "pageNum", required = false) String pageNum,
                             @RequestParam(value = "pageSize", required = false) String pageSize,
                             @RequestParam(value = "channelTypeCd", defaultValue = "0", required = false) int channelTypeCd,
                             @RequestParam(value = "labelCd", required = false) int[] labelCd,
                             @RequestParam(value = "deleteStatus", defaultValue = "2", required = false) int deleteStatus,
                             @RequestParam(value = "offerTypeCd", required = false, defaultValue = "0") int offerTypeCd,
                             @RequestParam(value = "keyword", required = false) String keyword,
                             @RequestParam(value = "zipcode", required = false) String zipcode) {

        if ("".equals(keyword)) {
            keyword = null;
        }
        try {
            String labelcds = null;
            StringBuilder labelBuilder = new StringBuilder();
            if (labelCd != null && labelCd.length > 0) {
                for (int id : labelCd) {
                    labelBuilder.append(id + ",");
                }
                labelcds = labelBuilder.toString();
                if (labelcds.endsWith(",")) {
                    int index = labelcds.lastIndexOf(",");
                    labelcds = labelcds.substring(0, index);
                }
            }
            LinkedHashMap map = logisticsOfferBasisInfoService.findByCond(pageNum, pageSize, channelTypeCd, labelcds, deleteStatus, keyword, offerTypeCd, zipcode);
            return Result.success(map);
        } catch (NumberFormatException e) {
            return Result.failure(390, "报价单详情模糊查询查询异常", e.getMessage());
        }

    }


    @ApiOperation(value = "报价单详情", notes = "根据报价单ID查询物流报价详细内容，包括物流时效内容，计费区间内容，报价标签内容。", response = LogisticsOfferBasisInfoEntity.class)
    @RequestMapping(value = "logistics/{offerId}", method = RequestMethod.GET)
    public Result findOfferById(@PathVariable("offerId") String offerId) {
        LogisticsOfferBasisInfoEntity ele = null;
        try {
            ele = logisticsOfferBasisInfoService.get(offerId);
        } catch (Exception e) {
            return Result.failure(390, "报价单详情查询异常", e);
        }
        return Result.success(ele);
    }

    @ApiOperation(value = "获取某物流商下有效报价", notes = "根据shipmentId,报价类型查询该物流商下所有未删除物流报价", response = LogisticsOfferBasisInfoEntity.class)
    @RequestMapping(value = "logistics/OfferList", method = RequestMethod.GET)
    public Result findOfferByshipId(@RequestParam(value = "shipMentId") String shipMentId, @RequestParam(value = "offerTypeCd", defaultValue = "0") int offerTypeCd) {
        try {
            List<LogisticsOfferVo> listVo = logisticsOfferBasisInfoService.findByShipmentId(shipMentId, offerTypeCd);
            return Result.success(listVo);
        } catch (Exception e) {
            return Result.failure(390, "查询物流商报价单异常", e.getMessage());
        }
    }


    @ApiOperation(value = "根据offerId修改Fba报价单", notes = "根据报价单ID查询物流报价详细内容，包括物流时效内容，计费区间内容，报价标签内容。", response = LogisticsOfferBasisInfoEntity.class)
    @RequestMapping(value = "/logistics/offer/Fba", method = RequestMethod.PUT)
    @Transactional(transactionManager = "retailShipmentTransactionManager")
    public Result updateFbaById(@RequestBody @Validated LogicOfferBasisInfoFbaEnity ele, BindingResult result,
                                @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "default") @ApiIgnore String userId) {

        if (result.hasErrors()) {
            return Result.failure(390, result.getAllErrors().get(0).getDefaultMessage(), result.getAllErrors());
        }
        if (ele.getOfferId() == null) {
            return Result.failure(390, "offerId不能为空", null);
        }
        List<LogisticsOfferEffectiveEntity> effeList = ele.getEffectiveList();
        List<LogisticsOfferHistoryInfoEntity> historyList = ele.getHistoryList();
        List<LogisticsAllowLabelRelEntity> labelList = ele.getLabelList();
        List<LogisticsOfferDestinationEnity> destList = ele.getDestinationList();

        //检验是否修改价格,false表示修改，true表示未修改
        LogisticsOfferBasisInfoEntity logisticsOfferBasisInfoEntity = logisticsOfferBasisInfoService.get(ele.getOfferId());
        boolean flag = checkChange(historyList, logisticsOfferBasisInfoEntity);

//      校验区间是否重复
        Result hisListResult = checkHistoryList(historyList);
        if (hisListResult.getCode() != 200) {
            return hisListResult;
        }
//      检验时效
        Result effectResult = checkEffectList(effeList);
        if (hisListResult.getCode() != 200) {
            return effectResult;
        }
        Integer deleteStatus = logisticsOfferBasisInfoService.getStatusByOfferId(ele.getOfferId());
        if (deleteStatus == 1) {
            throw new MyException(391, "已删除无法修改", null);
        }
        logisticsOfferEffectiveService.physicalDeleteEffect(ele.getOfferId());
        if (!flag) {
            logisticsOfferHistoryInfoService.physicalDeleteHis(ele.getOfferId());
        }
        logisticsAllowLabelRelService.physicalDeleteLable(ele.getOfferId());
        logisicsOfferDestinationService.physicalDeleteDest(ele.getOfferId());
        ele.setModifyUser(userId);
        ele.setModifyTime(Time.getCurrentDateTime());
        try {
            logisticsOfferBasisInfoService.updateFbaByFbaId(ele);
        } catch (MyException e) {
            throw new MyException(390, "自定义异常", null);
        }
        if (!flag) {
            String modifyTime = Time.getCurrentDateTime();
            for (LogisticsOfferHistoryInfoEntity history : historyList) {
                history.setUuid(UUID.randomUUID().toString());
                history.setOfferId(ele.getOfferId());
                history.setModifyTime(modifyTime);
                history.setModifyUser(userId);
                history.setDeleteStatus(0);
                logisticsOfferHistoryInfoService.add(history);
            }
        }
        for (LogisticsOfferDestinationEnity dest : destList) {
            dest.setShipmentId(ele.getShipmentId());
            dest.setChannelName(ele.getChannelName());
        }
        saveMutiplyList(labelList, effeList, destList, ele.getOfferId());
        return Result.success("编辑fba报价单信息成功");
    }

    private boolean checkChange(List<LogisticsOfferHistoryInfoEntity> historyList, LogisticsOfferBasisInfoEntity logisticsOfferBasisInfoEntity) {
        //如果增加了區間，肯定要成為歷史
        List<LogisticsOfferHistoryInfoEntity> logisticsOfferHistoryInfoEntityList = logisticsOfferBasisInfoEntity.getHistoryList();
        if (historyList.size() != logisticsOfferHistoryInfoEntityList.size()) {
            return false;
        }
        Map<String, String> map = new HashMap<>();
        for (LogisticsOfferHistoryInfoEntity logisticsOfferHistoryInfoEntity : historyList) {
            Double start = logisticsOfferHistoryInfoEntity.getStartChargeSection();
            Double end = logisticsOfferHistoryInfoEntity.getEndChargeSection();
            Double unitPrice = logisticsOfferHistoryInfoEntity.getUnitPrice();
            Double unitPriceWithOil = logisticsOfferHistoryInfoEntity.getUnitPriceWithOil();
            map.put(start + "" + end, unitPrice + "" + unitPriceWithOil);
        }
        for (LogisticsOfferHistoryInfoEntity logisticsOfferHistoryInfoEntity : logisticsOfferHistoryInfoEntityList) {
            Double start = logisticsOfferHistoryInfoEntity.getStartChargeSection();
            Double end = logisticsOfferHistoryInfoEntity.getEndChargeSection();
            Double unitPrice = logisticsOfferHistoryInfoEntity.getUnitPrice();
            Double unitPriceWithOil = logisticsOfferHistoryInfoEntity.getUnitPriceWithOil();
            String result = map.get(start + "" + end);
            if (result == null) {
                return false;
            }
            if (!result.equals(unitPrice + "" + unitPriceWithOil)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检验更新时渠道名是否重复
     *
     * @param parameters 需要校验的参数
     * @param enity      数据库存在的数据
     * @return
     */
    private Result checkUpdateParameter(ParametersCheck parameters, LogisticsOfferBasisInfoEntity enity) {
        if (enity.getChannelName().equals(parameters.getChannelName())) {
            return Result.success();
        }
        if (parameters.getChannelName() != null) {
            String channleName = parameters.getChannelName();
            List<LogisticsOfferBasisInfoEntity> logbasisList = logisticsOfferBasisInfoService.findByChannelName(channleName);
            if (logbasisList.size() > 0) {
                return Result.failure(390, "渠道名称重复", null);
            }
        }

        return Result.success();
    }

    @ApiOperation(value = "根据offerId修改小包报价单", notes = "根据报价单ID查询物流报价详细内容，包括物流时效内容，计费区间内容，报价标签内容。", response = LogisticsOfferBasisInfoEntity.class)
    @RequestMapping(value = "/logistics/offer/littlePacket", method = RequestMethod.PUT)
    @Transactional(transactionManager = "retailShipmentTransactionManager")
    public Result updateLittleById(@RequestBody @Validated LogicOfferBasisInfoLittilEnity ele, BindingResult result,
                                   @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {

        if (result.hasErrors()) {
            return Result.failure(390, result.getAllErrors().get(0).getDefaultMessage(), result.getAllErrors());
        }
        if (ele.getOfferId() == null) {
            return Result.failure(390, "offerId不能为空", null);
        }
        List<LogisticsOfferEffectiveEntity> effeList = ele.getEffectiveList();
        List<LittlePackOfferDetailsEntity> littlePacketList = ele.getLittlePacketList();
        List<LogisticsAllowLabelRelEntity> labelList = ele.getLabelList();
        List<LogisticsOfferDestinationEnity> destList = ele.getDestinationList();
//        检验时效区间是否合法
        Result effectResult = checkEffectList(effeList);
        if (effectResult.getCode() != 200) {
            return effectResult;
        }
//      检验小包价格表信息
        Result litttlePackResult = checkLittPacketList(littlePacketList);
        if (litttlePackResult.getCode() != 200) {
            return litttlePackResult;
        }
        littlePackOfferDetailsService.physicalDeleteDetail(ele.getOfferId());
        logisticsOfferEffectiveService.physicalDeleteEffect(ele.getOfferId());
        logisticsAllowLabelRelService.physicalDeleteLable(ele.getOfferId());
        logisicsOfferDestinationService.physicalDeleteDest(ele.getOfferId());
        ele.setModifyUser(userId);
        ele.setModifyTime(Time.getCurrentDateTime());
        int deleteStatus = logisticsOfferBasisInfoService.getStatusByOfferId(ele.getOfferId());
        if (deleteStatus == 1) {
            throw new MyException(391, "已删除无法修改", null);
        }
        try {
            logisticsOfferBasisInfoService.updateLittleById(ele);
        } catch (MyException e) {
            throw new MyException(390, "重复", null);
        }
        for (LogisticsOfferDestinationEnity dest : destList) {
            dest.setShipmentId(ele.getShipmentId());
            dest.setChannelName(ele.getChannelName());
        }
        saveMutiplyList(labelList, effeList, destList, ele.getOfferId());
        for (LittlePackOfferDetailsEntity littlePacket : littlePacketList) {
            littlePacket.setUuid(UUID.randomUUID().toString());
            littlePacket.setOfferId(ele.getOfferId());
            littlePacket.setDeleteStatus(0);
            littlePackOfferDetailsService.saveLittle(littlePacket);
        }
        return Result.success("编辑小包报价信息成功");
    }

    @ApiOperation(value = "逻辑删除物流报价", notes = "逻辑删除物流报价，状态：0：正常，1：已删除")
    @RequestMapping(value = "logistics/delete", method = RequestMethod.DELETE)
    public Result delete(@RequestParam("offerId") String offerId,
                         @RequestHeader(value = "X-AUTH-ID", defaultValue = "default") @ApiIgnore String userId) {
        logisticsOfferBasisInfoService.delete(offerId, userId);
        return Result.success("逻辑删除物流报价成功");
    }

    @ApiOperation(value = "删除某物流商所有报价单", notes = "逻辑删除物流商物流报价，状态：0：正常，1：已删除")
    @RequestMapping(value = "logistics/deleteByShipID", method = RequestMethod.DELETE)
    public Result deleteByShipId(@RequestParam("shipmentId") String shipmentId,
                                 @RequestHeader(value = "X-AUTH-ID", defaultValue = "default") @ApiIgnore String userId) {
        try {
            logisticsOfferBasisInfoService.deleteByshipMentId(shipmentId, userId);
        } catch (Exception e) {
            return Result.failure(309, "删除失败", e.getMessage());
        }
        return Result.success("逻辑删除物流报价成功");
    }


    @ApiOperation(value = "参数校验", notes = "检验具体业务参数")
    @RequestMapping(value = "logistics/check", method = RequestMethod.POST)
    public Result checkParameters(@RequestBody ParametersCheck parameters,
                                  @RequestHeader(value = "X-AUTH-ID", defaultValue = "default") @ApiIgnore String userId) {
        Result result = checkParameter(parameters);
        if (result.getCode() != 200) {
            return result;
        }
        return Result.success("ok");
    }

    /**
     * 检验新增时渠道名是否重复
     *
     * @param parameters 需要校验的参数
     * @return
     */
    private Result checkParameter(ParametersCheck parameters) {
        if (parameters.getChannelName() != null) {
            String channleName = parameters.getChannelName();
            List<LogisticsOfferBasisInfoEntity> logbasisList = logisticsOfferBasisInfoService.findByChannelName(channleName);
            if (logbasisList.size() > 0) {
                return Result.failure(390, "渠道名称重复", null);
            }
        }

        return Result.success();
    }

    @RequestMapping(value = "logistics/all", method = RequestMethod.GET)
    public Result getShipmentPrice(String offerId) {
        try {
            List<LogisticsOfferBasisInfoEntity> list = logisticsOfferBasisInfoService.getShipmentPrice(offerId);
            return Result.success(list);
        } catch (Exception e) {
            return Result.failure(500, e.getMessage(), "");
        }

    }


    @ApiOperation(value = "批量报价单部分详情", notes = "根据报价单ID批量查询报价单部分信息。", response = LogisticsOfferBasisInfoEntity.class)
    @RequestMapping(value = "logistics/batch", method = RequestMethod.GET)
    public Result findOfferById(String[] offerIds) {
        try {
            List<LogisticsOfferBasisInfoEntity> offerList = logisticsOfferBasisInfoService.getOfferList(offerIds);
            return Result.success(offerList);
        } catch (Exception e) {
            return Result.failure(390, "报价单批量查询异常", e.getMessage());
        }
    }


    @ApiOperation(value = "获取所有正常报价渠道", notes = "获取所有正常报价渠道的基本信息(报价id,渠道名等)", response = SimpleOfferBasisInfoEnity.class)
    @RequestMapping(value = "logistics/offers/{offerTypeCd}", method = RequestMethod.GET)
    public Result listAllOffers(@PathVariable("offerTypeCd") int offerTypeCd) {
        try {
            List<SimpleOfferBasisInfoEnity> eleList = logisticsOfferBasisInfoService.getAllOffers(offerTypeCd);
            return Result.success(eleList);
        } catch (Exception e) {
            return Result.failure(390, "报价单详情查询异常", e.getMessage());
        }
    }

    @ApiOperation(value = "获取所有正常报价渠道的物流商类型", notes = "获取所有正常报价渠道的基本信息(报价id,渠道名等)", response = SimpleOfferBasisInfoEnity.class)
    @RequestMapping(value = "logistics/offersCarrier/{offerTypeCd}", method = RequestMethod.GET)
    public Result listAllCarrier(@PathVariable("offerTypeCd") int offerTypeCd) {
        try {
            List<CarrierEnity> carrierEnityList = logisticsOfferBasisInfoService.getAllCarriers(offerTypeCd);
            return Result.success(carrierEnityList);
        } catch (Exception e) {
            return Result.failure(390, "获取所有正常报价渠道的物流商类型异常", e.getMessage());
        }
    }

    @ApiOperation(value = "获取所有未删除的渠道名", notes = "获取所有未删除的渠道名)")
    @RequestMapping(value = "logistics/listAllChannleName", method = RequestMethod.GET)
    public Result listAllChannleName() {
        try {
            List<ChannelNameEnity> channelNameEnityList = logisticsOfferBasisInfoService.getAllChannleName();
            return Result.success(channelNameEnityList);
        } catch (Exception e) {
            return Result.failure(390, "获取所有未删除的渠道名", e.getMessage());
        }
    }
}
