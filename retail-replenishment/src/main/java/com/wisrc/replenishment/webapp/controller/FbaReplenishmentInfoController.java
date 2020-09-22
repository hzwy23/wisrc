package com.wisrc.replenishment.webapp.controller;

import com.wisrc.replenishment.webapp.entity.ExceptionTypeAttrEntity;
import com.wisrc.replenishment.webapp.entity.FbaReplenishmentLabelRelEntity;
import com.wisrc.replenishment.webapp.entity.VReplenishmentMskuEntity;
import com.wisrc.replenishment.webapp.service.FbaMskuInfoService;
import com.wisrc.replenishment.webapp.service.FbaReplenishmentInfoService;
import com.wisrc.replenishment.webapp.service.FbaReplenishmentLabelRelService;
import com.wisrc.replenishment.webapp.service.UploadDownloadService;
import com.wisrc.replenishment.webapp.utils.Result;
import com.wisrc.replenishment.webapp.utils.ResultCode;
import com.wisrc.replenishment.webapp.utils.Time;
import com.wisrc.replenishment.webapp.utils.Toolbox;
import com.wisrc.replenishment.webapp.vo.*;
import com.wisrc.replenishment.webapp.vo.delivery.DeliverySelectVO;
import com.wisrc.replenishment.webapp.vo.swagger.FbaMskuUnderwayModel;
import io.swagger.annotations.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.*;

@RestController
@Api(tags = "补货单管理")
@RequestMapping(value = "/replenishment")
public class FbaReplenishmentInfoController {

    @Autowired
    private FbaReplenishmentInfoService fbaReplenishmentInfoService;

    @Autowired
    private FbaReplenishmentLabelRelService labelRelService;

    @Autowired
    private FbaMskuInfoService fbaMskuInfoService;

    @Autowired
    private UploadDownloadService uploadDownloadService;

    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功，返回值数据结构，请参考Example Value。 " +
                    "当返回值中pageNum和pageSize为-1时，表示全表查询，未分页",
                    response = FbaReplenishmentInfoVO.class),
            @ApiResponse(code = 401, message = "权限不足", response = Result.class),
            @ApiResponse(code = 404, message = "请求的资源不存在", response = Result.class)
    })
    @ApiOperation(value = "获取补货单信息列表", notes = "根据所选条件获取补货单信息列表,")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "开始页数", dataType = "String", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "一页的总数", dataType = "String", paramType = "query", defaultValue = "10"),
            @ApiImplicitParam(name = "shopId", value = "补货店铺", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "warehouseId", value = "发货仓", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "createBeginTime", value = "下单开始时间", dataType = "Date", paramType = "query"),
            @ApiImplicitParam(name = "createEndTime", value = "下单结束时间", dataType = "Date", paramType = "query"),
            @ApiImplicitParam(name = "shipmentId", value = "物流渠道", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "manager", value = "负责人", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "labelCd", value = "标签", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "statusCd", value = "补货单状态", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "keyWord", value = "关键字", dataType = "String", paramType = "query"),

    })
    @RequestMapping(value = "/fba", method = RequestMethod.GET)
    public Result findByCond(@RequestParam(value = "pageNum", required = false) String pageNum,
                             @RequestParam(value = "pageSize", required = false) String pageSize,
                             @RequestParam(value = "shopId", required = false) String shopId,
                             @RequestParam(value = "warehouseId", required = false) String warehouseId,
                             @RequestParam(value = "createBeginTime", required = false) Date createBeginTime,
                             @RequestParam(value = "createEndTime", required = false) Date createEndTime,
                             @RequestParam(value = "shipmentId", required = false) String shipmentId,
                             @RequestParam(value = "manager", required = false) String manager,
                             @RequestParam(value = "labelCd", required = false) String labelCd,
                             @RequestParam(value = "statusCd", required = false) String statusCd,
                             @RequestParam(value = "keyWord", required = false) String keyWord,
                             @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "admin") @ApiIgnore String userId) {

        // 如果分页参数为空，则查询直接查询全部
        if (pageNum == null || pageSize == null) {
            return Result.success(fbaReplenishmentInfoService.findAll(shopId, warehouseId, createBeginTime, createEndTime, statusCd, shipmentId, manager, labelCd, keyWord, userId));
        }
        try {
            int num = Integer.valueOf(pageNum);
            int size = Integer.valueOf(pageSize);
            LinkedHashMap map = fbaReplenishmentInfoService.findInfoByCond(num, size, shopId, warehouseId, createBeginTime, createEndTime, statusCd, shipmentId, manager, labelCd, keyWord, userId);
            return Result.success(map);
        } catch (NumberFormatException e) {
            return Result.success(200, "pageNum或pageSize值异常，分页值只能是数字，默认查询全部补货单数据", fbaReplenishmentInfoService.findAll(shopId, warehouseId, createBeginTime, createEndTime, statusCd, shipmentId, manager, labelCd, keyWord, userId));
        }

    }

    @ApiOperation(value = "获取各状态的数量")
    @RequestMapping(value = "/fba/number", method = RequestMethod.GET)
    public Result findStatusNumber() {
        FbaStatusNumberVO statusNumber = fbaReplenishmentInfoService.findStatusNumber();
        return Result.success(statusNumber);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功，返回值数据结构，请参考Example Value",
                    response = FbaReplenishmentInfoVO.class),
            @ApiResponse(code = 401, message = "权限不足", response = Result.class),
            @ApiResponse(code = 404, message = "请求的资源不存在", response = Result.class)
    })
    @ApiOperation(value = "通过补货单id获取补货单详情信息", notes = "获取补货单详细信息，可能没有Amazon相关信息，装箱规格中的装箱尺寸需要前端处理，用外箱长、宽、高拼接成需要的数据")
    @RequestMapping(value = "/fba/{fbaReplenishmentId}", method = RequestMethod.GET)
    public Result findbyid(@PathVariable("fbaReplenishmentId") String fbaReplenishmentId) {
        try {
            FbaReplenishmentInfoVO fbaInfoVo = fbaReplenishmentInfoService.findById(fbaReplenishmentId);
            return Result.success(fbaInfoVo);
        } catch (Exception e) {
            return Result.failure(ResultCode.INVALID_ARGUMENT, "查询补货单详情错误");
        }
    }


    @ApiOperation(value = "新增补货单信息", notes = "当新增补货单信息时，补货单商品详细信息、补货标签信息、补货状态码表都需要新增相应的若干信息")
    @RequestMapping(value = "/fba", method = RequestMethod.POST)
    public Result saveInfo(@Valid @RequestBody FbaInfoAddVO infoVO, BindingResult result, @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "") @ApiIgnore String userId) {
        if (result.hasErrors()) {
            return Result.failure(390, result.getAllErrors().get(0).getDefaultMessage(), result.getAllErrors());
        }
        try {
            if (infoVO.getMskuInfoList() == null || infoVO.getMskuInfoList().size() <= 0) {
                return Result.failure(390, "请选择产品信息", null);
            }
            fbaReplenishmentInfoService.saveInfo(infoVO, userId);
            return Result.success("新增补货单信息成功");
        } catch (Exception e) {
            return Result.failure(390, "新增补货单信息失败", null);
        }
    }

    @ApiOperation(value = "设置标签信息", notes = "设置标签是在原有的标签后追加，不能与原有的标签重复。", response = Result.class)
    @RequestMapping(value = "fba/label", method = RequestMethod.POST)
    public Result setLabel(@RequestBody FbaSetLabelVO setLabelVOS[], BindingResult result) {
        if (result.hasErrors()) {
            return Result.failure(390, result.getAllErrors().get(0).getDefaultMessage(), result.getAllErrors());
        }
        if (setLabelVOS.length == 0) {
            return Result.failure(390, "请选择要追加的标签", null);
        }
        List<String> requestLabelList = new ArrayList<String>();
        String fbaReplenishmentId = "";
        for (FbaSetLabelVO labelVO : setLabelVOS) {
            requestLabelList.add(String.valueOf(labelVO.getLabelCd()));
            fbaReplenishmentId = labelVO.getFbaReplenishmentId();
        }
        List<String> existLabelList = labelRelService.findlabelCdByFbaId(fbaReplenishmentId);
        boolean addLabel = true;
        if (existLabelList.size() > 0) {
            exist:
            for (int i = 0; i < existLabelList.size(); i++) {
                for (int j = 0; j < requestLabelList.size(); j++) {
                    if (existLabelList.get(i).equals(requestLabelList.get(j))) {
                        addLabel = false;
                        break exist;
                    }
                }
            }
        }

        if (!addLabel) {
            return Result.failure(390, "不能设置重复的标签", null);
        } else {
            for (FbaSetLabelVO labelVO : setLabelVOS) {
                FbaReplenishmentLabelRelEntity labelRelEntity = new FbaReplenishmentLabelRelEntity();
                labelRelEntity.setUuid(Toolbox.randomUUID());
                labelRelEntity.setDeleteStatus(0);
                labelRelEntity.setFbaReplenishmentId(fbaReplenishmentId);
                labelRelEntity.setLabelCd(labelVO.getLabelCd());
                labelRelEntity.setLabelName(labelVO.getLabelName());
                labelRelService.saveLabelRel(labelRelEntity);
            }
        }
        return Result.success("设置标签成功");
    }

    @ApiOperation(value = "移除标签", notes = "传入标签实体的uuid，根据uuid移除标签")
    @RequestMapping(value = "/fba/label", method = RequestMethod.PUT)
    public Result removeLabel(@RequestParam("uuid") String uuid) {
        try {
            labelRelService.removeLabel(1, new Random().nextInt() + "", uuid);
            return Result.success("移除标签成功");
        } catch (Exception e) {
            return Result.failure(390, "移除标签失败", null);
        }
    }

    @ApiOperation(value = "取消补货单", notes = "需要传入补货单id，取消原因和操作人id", response = Result.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fbaReplenishmentId", value = "补货单ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "cancelReason", value = "取消原因,不能超过250个字", required = true, dataType = "String", paramType = "query"),
    })
    @RequestMapping(value = "/fba/cancel", method = RequestMethod.PUT)
    public Result cnacelReplenisment(@RequestParam(value = "fbaReplenishmentId") String fbaReplenishmentId,
                                     @RequestParam(value = "cancelReason") String cancelReason,
                                     @RequestHeader(value = "X-AUTH-ID", defaultValue = "default") @ApiIgnore String userId) {

        try {
            Result result = fbaReplenishmentInfoService.cancelReplen(fbaReplenishmentId, cancelReason, userId);
            if (result.getCode() != 200) {
                return Result.failure(390, "取消失败", result.getMsg());
            }
            return Result.success("取消补货成功");
        } catch (Exception e) {
            return Result.failure(ResultCode.INVALID_ARGUMENT, "取消补货失败");
        }
    }

    @ApiOperation(value = "更新亚马逊货件信息", notes = "需要传入补货单id，填写亚马逊表单实体", response = Result.class)
    @RequestMapping(value = "/fba/amazon", method = RequestMethod.PUT)
    public Result addAmazonInfo(@Valid @RequestBody FbaAmazonVO amazonVO, BindingResult result, @RequestHeader(value = "X-AUTH-ID", defaultValue = "default") @ApiIgnore String userId) {
        if (result.hasErrors()) {
            return Result.failure(390, result.getAllErrors().get(0).getDefaultMessage(), result.getAllErrors());
        }
        amazonVO.setBatchNumber(amazonVO.getBatchNumber().trim());
        amazonVO.setModifyUser(userId);
        amazonVO.setModifyTime(Time.getCurrentTimestamp());
        try {
            return fbaReplenishmentInfoService.updateAmazonInfo(amazonVO);
            //return Result.success("更新亚马逊货件信息成功");

        } catch (RuntimeException e) {
            return Result.failure(390, "请重新填写补货批次号，不能与其他未取消的补货批次重复", e.getMessage());
        } catch (Exception e) {
            return Result.failure(390, "更新亚马逊货件信息失败", null);
        }
    }

    @ApiOperation(value = "检查亚马逊货件信息", notes = "检查亚马逊货件信息", response = Result.class)
    @RequestMapping(value = "/fba/amazon/check/{fbaReplenishmentId}/{bacthNumber}", method = RequestMethod.GET)
    public Result checkBatchNumber(@PathVariable("fbaReplenishmentId") String fbaReplenishmentId, @PathVariable("bacthNumber") String bacthNumber) {
        try {
            Map map = fbaReplenishmentInfoService.check(fbaReplenishmentId, bacthNumber);
            return Result.success(map);
        } catch (Exception e) {
            return Result.failure(390, "服务异常请稍后重试", e.getMessage());
        }
    }

    @ApiOperation(value = "调整装箱规格")
    @RequestMapping(value = "/fba/specification", method = RequestMethod.PUT)
    public Result updateMskuPack(@Valid @RequestBody FbaMskuSpecificationVO fbaMskuSpecificationVO[], BindingResult result, @RequestHeader(value = "X-AUTH-ID", defaultValue = "default") @ApiIgnore String userId) {
        if (result.hasErrors()) {
            return Result.failure(390, result.getAllErrors().get(0).getDefaultMessage(), result.getAllErrors());
        }
        try {
            List<FbaMskuSpecificationVO> fbaMskuSpecificationList = Arrays.asList(fbaMskuSpecificationVO);
            fbaReplenishmentInfoService.updateMskuPackInfo(fbaMskuSpecificationList, userId);
            return Result.success("调整装箱规格成功");
        } catch (Exception e) {
            return Result.failure(390, "调整装箱规格失败", null);
        }
    }

//    @ApiOperation(value = "修改补货数量", notes = "传入实体集合修改补货数量，记录操作人和操作时间",response = Result.class)
//    @RequestMapping(value = "/fba/quantity", method = RequestMethod.PUT)
//    public Result updateRepQuantity(@Valid @RequestBody FbaRepQuantityVO[] fbaRepQuantitys,BindingResult result,
//                                    @RequestHeader(value = "X-AUTH-ID", defaultValue = "default") @ApiIgnore String userId) {
//        if (result.hasErrors()) {
//            return Result.failure(390, result.getAllErrors().get(0).getDefaultMessage(), result.getAllErrors());
//        }
//        Timestamp modifyTime = Time.getCurrentTimestamp();
//        try {
//            fbaReplenishmentInfoService.updateRepQuantity(fbaRepQuantitys, userId, modifyTime);
//            return Result.success("修改补充货量信息成功");
//        } catch (Exception e) {
//            return Result.failure(390,"修改补充货量信息失败",null);
//        }
//    }

    @ApiOperation(value = "选择物流交运", notes = "判断是否在新增补货单时选择了物流商渠道，跳转到不同的页面", response = DeliverySelectVO.class)
    @RequestMapping(value = "/fba/select", method = RequestMethod.GET)
    public Result selectDelivery(@RequestParam(value = "fbaReplenishmentId") String fbaReplenishmentId) {
        try {
            DeliverySelectVO selectVO = fbaReplenishmentInfoService.selectDelivery(fbaReplenishmentId);
            if (selectVO == null) {
                return Result.failure(310, "该补货单已经交运,请刷新页面", null);
            }
            return Result.success(selectVO);
        } catch (Exception e) {
            return Result.failure(390, "交运失败,没有该补货单", null);
        }

    }

    @ApiOperation(value = "合并交运", notes = "判断是否能合并交运,isAllow为true时表示可以合并交运，为false时表示不能合并交运")
    @RequestMapping(value = "/fba/merge", method = RequestMethod.GET)
    public Result mergeDelivery(@RequestParam("fbaIds") String[] fbaIds) {

        if (fbaIds.length <= 1) {
            Map<String, String> mergeMap = new HashMap<>();
            mergeMap.put("isAllow", "fasle");
            mergeMap.put("message", "请选择两条或以上数据进行合并");
            return Result.success(mergeMap);
        }

        try {
            Map<String, String> mergeMap = fbaReplenishmentInfoService.mergeDelivery(fbaIds);
            return Result.success(mergeMap);
        } catch (Exception e) {
            return Result.failure(390, "合并交运错误，有不存在的补货单", null);
        }
    }

    /**
     * 导出报表
     *
     * @return
     */
    @ApiOperation(value = "导出补货单信息", notes = "按条件导出，如果未勾选则导出当前页面数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", defaultValue = "1", value = "页码", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", defaultValue = "10", value = "每页条数", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "shopId", value = "补货店铺", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "warehouseId", value = "发货仓", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "createBeginTime", value = "创建日期(开始)", paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "createEndTime", value = "创建日期(结束)", paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "statusCd", value = "状态", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "shipmentId", value = "物流商", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "manager", value = "负责人", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "labelCd", value = "标签", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "keyWord", value = "关键字", paramType = "query", dataType = "String")
    })
    @RequestMapping(value = "/fba/export", method = RequestMethod.GET)
    public void export(HttpServletRequest request, HttpServletResponse response,
                       @RequestParam(value = "pageNum", required = false) String pageNum,
                       @RequestParam(value = "pageSize", required = false) String pageSize,
                       @RequestParam(value = "shopId", required = false) String shopId,
                       @RequestParam(value = "warehouseId", required = false) String warehouseId,
                       @RequestParam(value = "createBeginTime", required = false) Date createBeginTime,
                       @RequestParam(value = "createEndTime", required = false) Date createEndTime,
                       @RequestParam(value = "fbaIds", required = false) String[] fbaIds,
                       @RequestParam(value = "shipmentId", required = false) String shipmentId,
                       @RequestParam(value = "manager", required = false) String manager,
                       @RequestParam(value = "labelCd", required = false) String labelCd,
                       @RequestParam(value = "statusCd", required = false) String statusCd,
                       @RequestParam(value = "keyWord", required = false) String keyWord,
                       @RequestHeader(value = "X-AUTH-ID") @ApiIgnore String userId) throws Exception {
        LinkedHashMap map = new LinkedHashMap();
        int size = 0;
        int num = 0;
        try {
            size = Integer.valueOf(pageSize);
            num = Integer.valueOf(pageNum);
            if (fbaIds == null || fbaIds.length == 0) {
                map = fbaReplenishmentInfoService.findInfoByCond(num, size, shopId, warehouseId, createBeginTime, createEndTime, statusCd, shipmentId, manager, labelCd, keyWord, userId);
            } else {
                map = fbaReplenishmentInfoService.findInfoByCond(num, size, shopId, warehouseId, createBeginTime, createEndTime, fbaIds, statusCd, shipmentId, manager, labelCd, keyWord, userId);
            }
            List<FbaQueryResultVO> list = (List<FbaQueryResultVO>) map.get("fbaReplenmentInfoList");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("补货单信息表");
            String fileName = "fbaInfo" + ".xls";//设置要导出的文件的名字
            //新增数据行，并且设置单元格数据
            int rowNum = 1;
            //商品信息的行数
            int mskuRowNum = 1;

            String[] headers = {"补货单号", "创建日期", "补货批次", "补货店铺", "标签", "发货仓", "收货仓省份代码", "收货仓邮编", "MSKU编号", "库存MSKU编号", "MSKU名称", "库存MSKU名称", "FnSKU", "ASIN", "产品特性", "销售状态",
                    "商品负责人", "补货数量", "装箱数量", "发货数量", "物流商-渠道", "物流单号", "物流异常说明"};
            //headers表示excel表中第一行的表头

            HSSFRow row = sheet.createRow(0);
            //在excel表中添加表头

            for (int i = 0; i < headers.length; i++) {
                HSSFCell cell = row.createCell(i);
                HSSFRichTextString text = new HSSFRichTextString(headers[i]);
                cell.setCellValue(text);
            }
            if (null != list) {
                //在表中存放查询到的数据放入对应的列
                for (int i = 0; i < list.size(); i++) {
                    HSSFRow row1 = sheet.createRow(rowNum);
                    List<FbaMskuInfoVO> mskuList = list.get(i).getMskuList();
                    int firstRow = rowNum;
                    int lastRow = 0;
                    if (mskuList != null && mskuList.size() > 0) {
                        for (int k = 0; k < mskuList.size(); k++) {
                            HSSFRow mskuRow = sheet.createRow(mskuRowNum);
                            mskuRow.createCell(8).setCellValue(toNotNull(mskuList.get(k).getMskuId()));
                            mskuRow.createCell(9).setCellValue(toNotNull(mskuList.get(k).getMskuInfoVO().getStoreSku()));
                            mskuRow.createCell(10).setCellValue(toNotNull(mskuList.get(k).getMskuInfoVO().getMskuName()));
                            mskuRow.createCell(11).setCellValue(toNotNull(mskuList.get(k).getMskuInfoVO().getStoreName()));
                            mskuRow.createCell(12).setCellValue(toNotNull(mskuList.get(k).getMskuInfoVO().getFnSKU()));
                            mskuRow.createCell(13).setCellValue(toNotNull(mskuList.get(k).getMskuInfoVO().getASIN()));
                            List<Map<String, Object>> storeLabel = mskuList.get(k).getMskuInfoVO().getStoreLabel();
                            StringBuilder storeLabelstr = new StringBuilder();
                            int labelCount = 1; //记录标签总数
                            if (storeLabel != null && storeLabel.size() > 0) {
                                for (Map<String, Object> labelmap : storeLabel) {
                                    if (labelCount == storeLabel.size()) {
                                        storeLabelstr.append(labelmap.get("labelText"));
                                    } else {
                                        storeLabelstr.append(labelmap.get("labelText") + ",");
                                    }
                                    labelCount++;
                                }
                            }
                            mskuRow.createCell(14).setCellValue(toNotNull(storeLabelstr));
                            List<FbaMskuPackQueryVO> mskupackList = mskuList.get(k).getMskupackList();
                            mskuRow.createCell(15).setCellValue(toNotNull(mskuList.get(k).getMskuInfoVO().getSalesStatus()));
                            mskuRow.createCell(16).setCellValue(toNotNull(mskuList.get(k).getMskuInfoVO().getManager()));
                            //                        mskuRow.createCell(17).setCellValue(toNotNull(mskupackList.get(0).getReplenishmentQuantity()));
                            //                        mskuRow.createCell(18).setCellValue(toNotNull(mskupackList.get(0).getPackingQuantity()));
                            //                        mskuRow.createCell(19).setCellValue(toNotNull(mskupackList.get(0).getDeliveryNumber()));
                            mskuRow.createCell(17).setCellValue(toNotNull(""));
                            mskuRow.createCell(18).setCellValue(toNotNull(""));
                            mskuRow.createCell(19).setCellValue(toNotNull(""));
                            mskuRowNum++;
                            lastRow = rowNum++;
                        }
                    } else {
                        HSSFRow mskuRow = sheet.createRow(mskuRowNum);
                        mskuRow.createCell(8).setCellValue("");
                        mskuRow.createCell(9).setCellValue("");
                        mskuRow.createCell(10).setCellValue("");
                        mskuRow.createCell(11).setCellValue("");
                        mskuRow.createCell(12).setCellValue("");
                        mskuRow.createCell(13).setCellValue("");
                        mskuRow.createCell(14).setCellValue("");
                        mskuRow.createCell(15).setCellValue("");
                        mskuRow.createCell(16).setCellValue("");
                        mskuRow.createCell(17).setCellValue("");
                        mskuRow.createCell(18).setCellValue("");
                        mskuRow.createCell(19).setCellValue("");
                    }
                    if (firstRow < lastRow) {
                        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, 0, 0));
                        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, 1, 1));
                        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, 2, 2));
                        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, 3, 3));
                        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, 4, 4));
                        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, 5, 5));
                        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, 6, 6));
                        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, 7, 7));
                        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, 20, 20));
                        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, 21, 21));
                        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, 22, 22));
                    }
                    row1.createCell(0).setCellValue(list.get(i).getFbaReplenishmentId());
                    row1.createCell(1).setCellValue(toNotNull(list.get(i).getCreateDate()));

                    row1.createCell(2).setCellValue(toNotNull(list.get(i).getBatchNumber()));
                    row1.createCell(3).setCellValue(toNotNull(list.get(i).getMskuList().get(0).getShopName()));
                    List<FbaLabelVO> labelList = list.get(i).getLabelList();
                    StringBuilder labelStr = new StringBuilder();
                    int fbaLabelCount = 1;
                    if (labelList != null && labelList.size() > 0) {
                        for (int j = 0; j < labelList.size(); j++) {
                            if (labelList.size() == fbaLabelCount) {
                                labelStr.append(labelList.get(j).getLabelName());
                            } else {
                                labelStr.append(labelList.get(j).getLabelName() + ",");
                            }
                            fbaLabelCount++;
                        }
                    }
                    row1.createCell(4).setCellValue(toNotNull(labelStr));
                    row1.createCell(5).setCellValue(toNotNull(list.get(i).getWarehouseName()));
                    row1.createCell(6).setCellValue(toNotNull(list.get(i).getProvinceCode()));
                    row1.createCell(7).setCellValue(toNotNull(list.get(i).getZipCode()));

                    row1.createCell(20).setCellValue(toNotNull(list.get(i).getShipChannelName()));
                    row1.createCell(21).setCellValue(toNotNull(list.get(i).getLogisticsId()));
                    List<ExceptionTypeAttrEntity> exceptionTypeDescList = list.get(i).getExceptionTypeDescList();
                    StringBuilder exceptionstr = new StringBuilder();
                    int exceptionCount = 1;
                    if (exceptionTypeDescList != null && exceptionTypeDescList.size() > 0) {
                        for (int j = 0; j < exceptionTypeDescList.size(); j++) {
                            if (exceptionTypeDescList.size() == exceptionCount) {
                                exceptionstr.append(exceptionTypeDescList.get(j).getExceptionTypeDesc());
                            } else {
                                exceptionstr.append(exceptionTypeDescList.get(j).getExceptionTypeDesc() + ",");
                            }
                        }
                    }
                    row1.createCell(22).setCellValue(toNotNull(exceptionstr));
                }
            } else {
                HSSFRow row1 = sheet.createRow(0);
            }
            response.reset();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            response.flushBuffer();
            workbook.write(response.getOutputStream());
        } catch (Exception e) {
            response.setCharacterEncoding("GBK");
            PrintWriter writer = response.getWriter();
            writer.print("导出失败，请重试");
            writer.flush();
            writer.close();
        }
    }

    private String toNotNull(Object o) {
        if (o != null) {
            return o.toString();
        }
        return "";
    }


    /*@ApiOperation(value = "下载FnSku条码")
    @RequestMapping(value = "/download/fnsku/{fbaReplenishmentId}", method = RequestMethod.GET)
    public Result downLoad(HttpServletResponse response, @PathVariable(value = "fbaReplenishmentId") String fbaReplenishmentId) {

            FbaReplenishmentInfoEntity entity = fbaReplenishmentInfoService.download(fbaReplenishmentId);
            byte[] fnSku = uploadDownloadService.downloadFile("matrix-code", entity.getFnskuCode());
            response.setContentType("application/octet-stream");
            try {
                InputStream ins = new ByteArrayInputStream(fnSku);
                OutputStream os = response.getOutputStream();
                byte[] b = new byte[1024];
                int len;
                while ((len = ins.read(b)) > 0) {
                    os.write(b, 0, len);
                }
                os.flush();
                os.close();
                ins.close();
            } catch (Exception e) {
                return Result.failure(390, "fnSku条码下载异常", e);
            }
            return Result.success("fnSku条码下载成功");

    }*/

    /**
     * 取FBA在途总数量
     *
     * @param mskuId
     * @return
     */
    @RequestMapping(value = "/fba/OnWayNum/{mskuId}", method = RequestMethod.GET)
    public Result getFbaOnWayNum(@RequestParam("mskuId") String mskuId) {
        try {
            int fbaTransportQty = fbaMskuInfoService.getFbaOnWayNum(mskuId);
            return Result.success(fbaTransportQty);
        } catch (Exception e) {
            return Result.failure();
        }
    }

    /**
     * 取FBA在途总数量
     *
     * @param commodityIdList
     * @return
     */
    @ApiOperation(value = "查询FBA在途数据信息", response = FbaMskuUnderwayModel.class)
    @RequestMapping(value = "/fba/msku/underway", method = RequestMethod.POST)
    public Result getFbaOnWayNum(String[] commodityIdList) {
        if (commodityIdList == null || commodityIdList.length == 0) {
            return Result.failure(423, "参数为空", commodityIdList);
        }
        try {
            List<VReplenishmentMskuEntity> list = fbaMskuInfoService.findUnderway(commodityIdList);
            return Result.success(list);
        } catch (Exception e) {
            return Result.failure(424, "查询商品在途库存出现异常，请联系管理员", commodityIdList);
        }
    }
}
