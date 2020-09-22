package com.wisrc.replenishment.webapp.controller;

import com.wisrc.replenishment.webapp.dao.WaybillInfoDao;
import com.wisrc.replenishment.webapp.entity.TransferStatusEntity;
import com.wisrc.replenishment.webapp.service.TransferService;
import com.wisrc.replenishment.webapp.service.externalService.BasicService;
import com.wisrc.replenishment.webapp.utils.Result;
import com.wisrc.replenishment.webapp.vo.transferorder.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/replenishment/transfer")
@RestController
@Api(tags = "调拨单")
public class TransferController {

    @Autowired
    private TransferService transferService;
    private static Logger logger = LoggerFactory.getLogger(TransferController.class);
    @Autowired
    private WaybillInfoDao waybillInfoDao;
    @Autowired
    private BasicService basicService;

    @ApiOperation("新增调拨单")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result addTransferOrder(@RequestBody TransferOrderAddVo transferOrderAddVo,
                                   @RequestHeader(value = "X-AUTH-ID") String userId) {
        transferOrderAddVo.getTransferBasicInfoEntity().setCreateUser(userId);
        try {
            transferService.addTransferOrder(transferOrderAddVo);
            return Result.success();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Result.failure(390, "新增调拨单", e.getMessage());
        }
    }

    @RequestMapping(value = "/audit/{transferId}", method = RequestMethod.PUT)
    @ApiOperation("确认下单")
    public Result auditTransfer(@PathVariable("transferId") String transferId,
                                @RequestParam("warehouseStartId") String warehouseStartId,
                                @RequestParam("warehouseEndId") String warehouseEndId,
                                @RequestParam("subWarehouseEndId") String subWarehouseEndId,
                                @RequestHeader(value = "X-AUTH-ID") String userId) {
        try {
            transferService.auditTransfer(transferId, warehouseStartId, warehouseEndId, subWarehouseEndId, userId);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return Result.failure(390, e.getMessage(), "");
        }
    }

    @RequestMapping(value = "/adjust/packingInfo", method = RequestMethod.PUT)
    @ApiOperation("调整装箱信息")
    public Result adjustPackingInfo(@RequestBody List<PackChangeInfoVo> packChangeInfoVos) {
        try {
            transferService.changePackInfo(packChangeInfoVos);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(390, "调整装箱信息失败", e.getMessage());
        }
    }

    @RequestMapping(value = "/confirm/shipment", method = RequestMethod.PUT)
    @ApiOperation("调拨单确认交运")
    public Result confirmShipment(@RequestBody TransferWaybillAddVo transferWaybillAddVo,
                                  BindingResult result,
                                  @RequestHeader(value = "X-AUTH-ID") String userId) {
        if (result.hasErrors()) {
            return Result.failure(390, result.getAllErrors().get(0).getDefaultMessage(), transferWaybillAddVo);
        }
        try {
            return transferService.confirmShipment(transferWaybillAddVo, userId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return Result.failure(390, "确认交运失败", e.getMessage());
        }
    }

    @RequestMapping(value = "/{transferId}", method = RequestMethod.GET)
    @ApiOperation("查询调拨单详情")
    public Result getTransferInfoById(@PathVariable("transferId") String transferId) {
        try {
            return transferService.findById(transferId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return Result.failure(390, "查询调拨单详情失败", e.getMessage());
        }
    }

    @RequestMapping(value = "/pageList", method = RequestMethod.GET)
    @ApiOperation("分页查询调拨单记录")
    public Result getTransferPageInfo(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                                      @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                      @RequestParam(value = "warehouseStartId", required = false) String warehouseStartId,
                                      @RequestParam(value = "warehouseEndId", required = false) String warehouseEndId,
                                      @RequestParam(value = "startDate", required = false) String startDate,
                                      @RequestParam(value = "endDate", required = false) String endDate,
                                      @RequestParam(value = "shipmentId", required = false) String shipmentId,
                                      @RequestParam(value = "keyword", required = false) String keyword,
                                      @RequestParam(value = "status", required = false) Integer status) {
        try {
            return transferService.getPageTransferInfo(pageNum, pageSize, warehouseStartId, warehouseEndId, startDate, endDate, shipmentId, keyword, status);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return Result.failure(390, "分页查询失败", e.getMessage());
        }

    }

    @RequestMapping(value = "/allStatus", method = RequestMethod.GET)
    @ApiOperation("查询所有的调拨状态")
    public Result getAllStatus() {
        try {
            return transferService.getAllStatus();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return Result.failure(390, "获取所有状态值失败", e.getMessage());
        }
    }

    @RequestMapping(value = "/countOfStatus", method = RequestMethod.GET)
    @ApiOperation("各种状态的调拨单数量")
    public Result getTransferCountOfStatus() {

        try {
            return transferService.findCountOfStatus();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return Result.failure(390, "获取各种状态的调拨单数失败", e.getMessage());
        }
    }

    @RequestMapping(value = "/cancel/{transferOrderId}", method = RequestMethod.PUT)
    @ApiOperation("取消调拨单")
    public Result cancelTransferOrder(@RequestHeader(value = "X-AUTH-ID") String userId,
                                      @PathVariable("transferOrderId") String transferOrderId,
                                      @RequestParam(value = "cancelReason") String cancelReason) {
        try {
            return transferService.cancelTransfer(transferOrderId, userId, cancelReason);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return Result.failure(390, "取消调拨失败", e.getMessage());
        }
    }

    /**
     * 调拨单excel导出
     *
     * @param response
     * @param pageNum
     * @param pageSize
     * @param warehouseStartId
     * @param warehouseEndId
     * @param startDate
     * @param endDate
     * @param shipmentId
     * @param keyword
     * @param status
     */
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    @ApiOperation("导出调拨单excel")
    public void export(HttpServletResponse response,
                       @RequestParam(value = "pageNum", required = false) Integer pageNum,
                       @RequestParam(value = "pageSize", required = false) Integer pageSize,
                       @RequestParam(value = "warehouseStartId", required = false) String warehouseStartId,
                       @RequestParam(value = "warehouseEndId", required = false) String warehouseEndId,
                       @RequestParam(value = "startDate", required = false) String startDate,
                       @RequestParam(value = "endDate", required = false) String endDate,
                       @RequestParam(value = "shipmentId", required = false) String shipmentId,
                       @RequestParam(value = "keyword", required = false) String keyword,
                       @RequestParam(value = "status", required = false) Integer status) throws IOException {
        try {
            Result allStatus = transferService.getAllStatus();
            //调拨单的状态码和状态名称映射
            Map<Integer, String> statusToName = new HashMap<>();
            List<TransferStatusEntity> transferStatusEntities = (List<TransferStatusEntity>) allStatus.getData();
            for (TransferStatusEntity transferStatusEntity : transferStatusEntities) {
                statusToName.put(transferStatusEntity.getStatusCd(), transferStatusEntity.getStatusName());
            }

            Result allShipment = basicService.getAllShipment();
            Map<String, String> shipmentIdToName = new HashMap<>();
            if (allShipment.getCode() != 200) {
                throw new RuntimeException("调用外部接口失败");
            }
            Map shipmentMap = (Map) allShipment.getData();
            List<Map> shipmentEnterpriseEntityList = (List<Map>) shipmentMap.get("shipmentEnterpriseEntityList");
            for (Map map : shipmentEnterpriseEntityList) {
                shipmentIdToName.put((String) map.get("shipmentId"), (String) map.get("shipmentName"));
            }

            Result pageTransferInfo = transferService.getPageTransferInfo(pageNum, pageSize, warehouseStartId, warehouseEndId, startDate, endDate, shipmentId, keyword, status);
            Map map = (Map) pageTransferInfo.getData();
            List<TransferInfoReturnVo> transferInfoReturnVos = (List<TransferInfoReturnVo>) map.get("transferInfoList");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("调拨单信息表");
            sheet.setDefaultColumnWidth(20);
            String fileName = "transfer" + ".xls";
            String[] headers = {"调拨单号", "下单日期", "起始仓库", "目标仓库", "库存sku", "sku中文名", "FnSku编号", "发货日期", "调拨数量", "状态", "签收日期",
                    "签收数量", "物流商", "物流渠道", "物流单号", "预估运费", "备注"};

            //表头行
            HSSFRow headRow = sheet.createRow(0);
            //表头单元格样式
            HSSFCellStyle headerStyle = workbook.createCellStyle();
            //表格内容样式
            HSSFCellStyle contentStyle = workbook.createCellStyle();
//            contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//            contentStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

            //全局字体
            HSSFFont globalFont = workbook.createFont();
            globalFont.setFontName("微软雅黑");
//            headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
//            headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//            headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            headerStyle.setFont(globalFont);
            contentStyle.setFont(globalFont);

            //设置表头
            for (int i = 0; i < headers.length; i++) {
                HSSFCell cell = headRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }
            if (!CollectionUtils.isEmpty(transferInfoReturnVos)) {
                int rowNum = 1;
                for (TransferInfoReturnVo transferInfoReturnVo : transferInfoReturnVos) {
                    //合并单元格
                    int size = transferInfoReturnVo.getTransferOrderDetailsVos().size();
                    if (size > 1) {
                        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum + size - 1, 0, 0));
                        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum + size - 1, 1, 1));
                        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum + size - 1, 2, 2));
                        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum + size - 1, 3, 3));
                        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum + size - 1, 7, 7));
                        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum + size - 1, 9, 9));
                        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum + size - 1, 12, 12));
                        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum + size - 1, 13, 13));
                        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum + size - 1, 14, 14));
                        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum + size - 1, 15, 15));
                        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum + size - 1, 16, 16));
                    }
                    for (int i = 0; i < size; i++) {
                        HSSFRow row = sheet.createRow(rowNum + i);
                        if (i == 0) {
                            HSSFCell cell = row.createCell(0);
                            cell.setCellStyle(contentStyle);
                            cell.setCellValue(transferInfoReturnVo.getTransferBasicInfoEntity().getTransferOrderCd());
                            HSSFCell cell1 = row.createCell(1);
                            cell1.setCellStyle(contentStyle);
                            cell1.setCellValue(transferInfoReturnVo.getTransferBasicInfoEntity().getCreateTime());
                            HSSFCell cell2 = row.createCell(2);
                            cell2.setCellStyle(contentStyle);
                            cell2.setCellValue(transferInfoReturnVo.getWarehouseStartName());
                            HSSFCell cell3 = row.createCell(3);
                            cell3.setCellStyle(contentStyle);
                            cell3.setCellValue(transferInfoReturnVo.getWarehouseEndName());
                            HSSFCell cell12 = row.createCell(4);
                            cell12.setCellStyle(contentStyle);
                            cell12.setCellValue(transferInfoReturnVo.getTransferOrderDetailsVos().get(i).getSkuId());
                            HSSFCell cell13 = row.createCell(5);
                            cell13.setCellStyle(contentStyle);
                            cell13.setCellValue(transferInfoReturnVo.getTransferOrderDetailsVos().get(i).getSkuName());
                            HSSFCell cell14 = row.createCell(6);
                            cell14.setCellStyle(contentStyle);
                            cell14.setCellValue(transferInfoReturnVo.getTransferOrderDetailsVos().get(i).getFnSku());
                            HSSFCell cell4 = row.createCell(7);
                            cell4.setCellStyle(contentStyle);
                            cell4.setCellValue(transferInfoReturnVo.getTransferBasicInfoEntity().getDeliveryDate());
                            HSSFCell cell15 = row.createCell(8);
                            cell15.setCellStyle(contentStyle);
                            cell15.setCellValue(transferInfoReturnVo.getTransferOrderDetailsVos().get(i).getTransferQuantity());
                            HSSFCell cell5 = row.createCell(9);
                            cell5.setCellStyle(contentStyle);
                            cell5.setCellValue(statusToName.get(transferInfoReturnVo.getTransferBasicInfoEntity().getStatusCd()));
                            HSSFCell cell6 = row.createCell(10);
                            cell6.setCellStyle(contentStyle);
                            cell6.setCellValue(transferInfoReturnVo.getTransferBasicInfoEntity().getSignInDate());
                            HSSFCell cell16 = row.createCell(11);
                            cell16.setCellStyle(contentStyle);
                            cell16.setCellValue(transferInfoReturnVo.getTransferOrderDetailsVos().get(i).getSignInQuantity() == null ? 0 : transferInfoReturnVo.getTransferOrderDetailsVos().get(i).getSignInQuantity());
                            if (StringUtils.isEmpty(transferInfoReturnVo.getTransferBasicInfoEntity().getWaybillId())) {
                                HSSFCell cell7 = row.createCell(12);
                                cell7.setCellStyle(contentStyle);
                                cell7.setCellValue("");
                                HSSFCell cell8 = row.createCell(13);
                                cell8.setCellStyle(contentStyle);
                                cell8.setCellValue("");
                                HSSFCell cell9 = row.createCell(14);
                                cell9.setCellStyle(contentStyle);
                                cell9.setCellValue("");
                                HSSFCell cell10 = row.createCell(15);
                                cell10.setCellStyle(contentStyle);
                                cell10.setCellValue("");
                            } else {
                                HSSFCell cell7 = row.createCell(12);
                                cell7.setCellStyle(contentStyle);
                                cell7.setCellValue(shipmentIdToName.get(transferInfoReturnVo.getTransferBasicInfoEntity().getShipmentId()));
                                HSSFCell cell8 = row.createCell(13);
                                cell8.setCellStyle(contentStyle);
                                cell8.setCellValue(transferInfoReturnVo.getLogisticsTrackInfoVO().getShipChannel());
                                HSSFCell cell9 = row.createCell(14);
                                cell9.setCellStyle(contentStyle);
                                cell9.setCellValue(transferInfoReturnVo.getLogisticsTrackInfoVO().getLogisticsId());
                                HSSFCell cell10 = row.createCell(15);
                                cell10.setCellStyle(contentStyle);
                                cell10.setCellValue(waybillInfoDao.findDiscountedAmountByWaybillId(transferInfoReturnVo.getTransferBasicInfoEntity().getWaybillId()));
                            }
                            HSSFCell cell11 = row.createCell(16);
                            cell11.setCellStyle(contentStyle);
                            cell11.setCellValue(transferInfoReturnVo.getTransferBasicInfoEntity().getRemark());
                        } else {
                            HSSFCell cell12 = row.createCell(4);
                            cell12.setCellStyle(contentStyle);
                            cell12.setCellValue(transferInfoReturnVo.getTransferOrderDetailsVos().get(i).getSkuId());
                            HSSFCell cell13 = row.createCell(5);
                            cell13.setCellStyle(contentStyle);
                            cell13.setCellValue(transferInfoReturnVo.getTransferOrderDetailsVos().get(i).getSkuName());
                            HSSFCell cell14 = row.createCell(6);
                            cell14.setCellStyle(contentStyle);
                            cell14.setCellValue(transferInfoReturnVo.getTransferOrderDetailsVos().get(i).getFnSku());
                            HSSFCell cell15 = row.createCell(8);
                            cell15.setCellStyle(contentStyle);
                            cell15.setCellValue(transferInfoReturnVo.getTransferOrderDetailsVos().get(i).getTransferQuantity());
                            HSSFCell cell16 = row.createCell(10);
                            cell16.setCellStyle(contentStyle);
                            cell16.setCellValue(transferInfoReturnVo.getTransferBasicInfoEntity().getSignInDate());
                            HSSFCell cell17 = row.createCell(11);
                            cell17.setCellStyle(contentStyle);
                            cell17.setCellValue(transferInfoReturnVo.getTransferOrderDetailsVos().get(i).getSignInQuantity() == null ? 0 : transferInfoReturnVo.getTransferOrderDetailsVos().get(i).getSignInQuantity());
                        }
                    }
                    rowNum += size;
                }
            }
            response.reset();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            response.flushBuffer();
            workbook.write(response.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
            response.setCharacterEncoding("utf-8");
            PrintWriter writer = response.getWriter();
            writer.print("导出失败，请重试");
            writer.flush();
            writer.close();
        }
    }

    @RequestMapping(value = "/label/add", method = RequestMethod.POST)
    @ApiOperation("为调拨单添加标签")
    public Result addLabelForTransfer(@RequestBody List<AddLabelsVo> labelIds) {
        try {
            return transferService.addLabelForTransfer(labelIds);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return Result.failure(390, "为调拨单添加标签失败", e.getMessage());
        }
    }

    @RequestMapping(value = "/label/delete/{transferId}/{labelId}", method = RequestMethod.DELETE)
    @ApiOperation("删除调拨单的标签")
    public Result deleteLabelForTransfer(@PathVariable("transferId") String transferId,
                                         @PathVariable("labelId") String labelId) {
        try {
            return transferService.deleteLabel(transferId, labelId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return Result.failure(390, "删除标签失败", e.getMessage());
        }
    }
}
