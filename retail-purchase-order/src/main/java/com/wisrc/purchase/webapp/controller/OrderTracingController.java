package com.wisrc.purchase.webapp.controller;

import com.wisrc.purchase.webapp.entity.*;
import com.wisrc.purchase.webapp.service.OrderBasisInfoAttrService;
import com.wisrc.purchase.webapp.service.OrderTracingService;
import com.wisrc.purchase.webapp.utils.DateUtil;
import com.wisrc.purchase.webapp.utils.Result;
import com.wisrc.purchase.webapp.vo.OrderTracingCompleteVo;
import com.wisrc.purchase.webapp.vo.OrderTracingEntryVo;
import com.wisrc.purchase.webapp.vo.OrderTracingReturnVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@Api(tags = "采购订单追踪")
@RequestMapping(value = "/purchase")
public class OrderTracingController {

    @Autowired
    private OrderTracingService orderTracingService;
    @Autowired
    private OrderBasisInfoAttrService orderBasisInfoAttrService;
    private static String exportName = null;

    static {
        try {
            exportName = "attachment;filename=" + URLEncoder.encode("采购订单追踪表", "UTF-8") + ".xlsx";
        } catch (UnsupportedEncodingException e) {
        }
    }

    @RequestMapping(value = "/track", method = RequestMethod.GET)
    @ApiOperation(value = "采购订单追踪表", notes = "采购订单追踪表信息", response = OrderTracingDetailEnity.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", defaultValue = "10", value = "条数", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "pageNum", defaultValue = "1", value = "页码", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "orderId", value = "订单编号关键词", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "employeeId", value = "采购员", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "supplierId", value = "供应商", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "tiicketOpenCd", value = "开票（0-全选（默认），1-不开票，2-开票）", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "customsTypeCd", defaultValue = "0", value = "报关（0-全选（默认），1-我司报关，2-工厂报关，3-不报关）", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "sku", value = "sku关键词", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "deliveryTypeCd", value = "交货状态（0-全选（默认），1-待交货，2-部分交货,3-全部交货,4-中止", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "keywords", value = "付款条款/备注关键词", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "skuName", value = "产品名称", paramType = "query", dataType = "String"),
    })
    @ResponseBody
    public Result findOrderTracingInfo(@RequestParam(value = "pageSize", required = false) String pageSize,
                                       @RequestParam(value = "pageNum", required = false) String pageNum,
                                       @RequestParam(value = "orderId", required = false) String orderId,
                                       @RequestParam(value = "startTime", required = false) String startTime,
                                       @RequestParam(value = "endTime", required = false) String endTime,
                                       @RequestParam(value = "employeeId", required = false) String employeeId,
                                       @RequestParam(value = "supplierId", required = false) String supplierId,
                                       @RequestParam(value = "tiicketOpenCd", required = false, defaultValue = "0") int tiicketOpenCd,
                                       @RequestParam(value = "customsTypeCd", required = false, defaultValue = "0") int customsTypeCd,
                                       @RequestParam(value = "sku", required = false) String sku,
                                       @RequestParam(value = "deliveryTypeCd", required = false, defaultValue = "0") int deliveryTypeCd,
                                       @RequestParam(value = "keywords", required = false) String keywords,
                                       @RequestParam(value = "skuName", required = false) String skuName,
                                       @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "default") @ApiIgnore String userId) throws ParseException {
        try {
            LinkedHashMap tracingList = orderTracingService.findTracingOrderByCond(pageNum, pageSize, orderId, startTime, endTime, employeeId, supplierId, tiicketOpenCd, customsTypeCd, sku, deliveryTypeCd, keywords, skuName);
            return Result.success(tracingList);
        } catch (Exception e) {
            return Result.failure(390, "采购订单追踪表信息查询失败", e.getMessage());
        }
    }

    @RequestMapping(value = "/track/export", method = RequestMethod.GET)
    @ApiOperation(value = "采购订单追踪表", notes = "采购订单追踪表信息", response = OrderTracingDetailEnity.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单编号关键词", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "employeeId", value = "采购员", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "supplierId", value = "供应商", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "tiicketOpenCd", value = "开票（0-全选（默认），1-不开票，2-开票）", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "customsTypeCd", defaultValue = "0", value = "报关（0-全选（默认），1-我司报关，2-工厂报关，3-不报关）", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "sku", value = "sku关键词", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "deliveryTypeCd", value = "交货状态（0-全选（默认），1-待交货，2-部分交货,3-全部交货,4-中止", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "keywords", value = "付款条款/备注关键词", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "skuName", value = "产品名称", paramType = "query", dataType = "String")
    })
    @ResponseBody
    public void export(HttpServletResponse response,
                       @RequestParam(value = "orderId", required = false) String orderId,
                       @RequestParam(value = "startTime", required = false) String startTime,
                       @RequestParam(value = "endTime", required = false) String endTime,
                       @RequestParam(value = "employeeId", required = false) String employeeId,
                       @RequestParam(value = "supplierId", required = false) String supplierId,
                       @RequestParam(value = "tiicketOpenCd", required = false, defaultValue = "0") int tiicketOpenCd,
                       @RequestParam(value = "customsTypeCd", required = false, defaultValue = "0") int customsTypeCd,
                       @RequestParam(value = "sku", required = false) String sku,
                       @RequestParam(value = "deliveryTypeCd", required = false, defaultValue = "0") int deliveryTypeCd,
                       @RequestParam(value = "keywords", required = false) String keywords,
                       @RequestParam(value = "skuName", required = false) String skuName,
                       @RequestHeader(value = "X-AUTH-ID", required = false, defaultValue = "default") @ApiIgnore String userId) {
        try {
            OutputStream out = response.getOutputStream();
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", exportName);
            String[] headers = {"采购订单号", "订单日期", "SKU", "产品中文名", "采购员", "供应商名称", "付款方式", "订单备注", "报关", "是否开票", "运费", "金额合计",
                    "不含税单价", "不含税金额", "税率", "含税单价", "含税金额", "备注", "状态", "交货期", "订单数量", "完工数", "提货数", "工厂库存", "收货数", "拒收数", "入库数", "退货数",
                    "尚欠数量", "备品率", "订单备品数", "备品入库数", "备品退货数", "欠备品数"};
            SXSSFWorkbook workbook = new SXSSFWorkbook(50);
//            HSSFWorkbook workbook = new HSSFWorkbook();
//            HSSFSheet sheet = workbook.createSheet("订单追踪信息表");
            Sheet sheet = workbook.createSheet();
            Row row = sheet.createRow(0);   // 创建第一行对象
            Map<String, String> customsMap = new HashMap<>();
            Map<String, String> ticketMap = new HashMap<>();
            Map<String, String> deliveryTypetMap = new HashMap<>();
            List<CustomsTypeAtrEntity> customsTypeAtrEntities = orderBasisInfoAttrService.cusomsAttr();
            List<TiicketOpenAttrEntity> tiicketOpenAttrEntities = orderBasisInfoAttrService.tiicketAttr();
            List<DeliveryTypeAttrEntity> deliveryTypeAttrEntities = orderBasisInfoAttrService.deliveryAttr();
            for (CustomsTypeAtrEntity cusEnity : customsTypeAtrEntities) {
                customsMap.put(cusEnity.getCustomsTypeCd() + "", cusEnity.getCustomsTypeDesc());
            }
            for (TiicketOpenAttrEntity ticEnity : tiicketOpenAttrEntities) {
                ticketMap.put(ticEnity.getTiicketOpenCd() + "", ticEnity.getTiicketOpenDesc());
            }
            for (DeliveryTypeAttrEntity delEnity : deliveryTypeAttrEntities) {
                deliveryTypetMap.put(delEnity.getDeliveryTypeCd() + "", delEnity.getDeliveryTypeDesc());
            }
            if (StringUtils.isEmpty(skuName)) {
                skuName = null;
            }
            int count = orderTracingService.getCount(orderId, startTime, endTime, employeeId, supplierId, tiicketOpenCd, customsTypeCd, sku, deliveryTypeCd, keywords, skuName);
            int exportTimes = 0;
            if (count % 10 == 0) {
                exportTimes = count / 10;
            } else {
                exportTimes = count / 10 + 1;
            }
            for (int i = 0; i < headers.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(headers[i]);
            }
            for (int i = 0; i < exportTimes; i++) {

                List<OrderTracingDetailEnity> tracingList = orderTracingService.findTracingOrderListByCond(i + 1, 10, orderId, startTime, endTime, employeeId, supplierId, tiicketOpenCd, customsTypeCd, sku, deliveryTypeCd, keywords, skuName);
                int len = tracingList.size() < 10 ? tracingList.size() : 10;
                for (int j = 0; j < len; j++) {
                    Row tracingRow = sheet.createRow(i * 10 + j + 1);
                    OrderTracingDetailEnity tracEnity = tracingList.get(j);
                    String deliverTimeAndNum = "";
                    for (ProductDeliveryInfoEntity entity : tracEnity.getDeliveryDateAndTimeList()) {
                        deliverTimeAndNum += DateUtil.convertDateToStr(entity.getDeliveryTime(), DateUtil.DEFAULT_SHORT_DATE_FORMAT) + "|" + entity.getNumber() + "\r\n";
                    }
                    tracingRow.createCell(0).setCellValue(tracEnity.getOrderId());
                    String biilDate = DateUtil.convertDateToStr(tracEnity.getBillDate(), DateUtil.DEFAULT_SHORT_DATE_FORMAT);
                    tracingRow.createCell(1).setCellValue(biilDate);
                    tracingRow.createCell(2).setCellValue(tracEnity.getSkuId());
                    tracingRow.createCell(3).setCellValue(tracEnity.getSkuNameZh());
                    tracingRow.createCell(4).setCellValue(tracEnity.getEmployeeName());
                    tracingRow.createCell(5).setCellValue(tracEnity.getSupplierName());
                    tracingRow.createCell(6).setCellValue(tracEnity.getPaymentProvision());
                    tracingRow.createCell(7).setCellValue(tracEnity.getOrderRemark());
                    tracingRow.createCell(8).setCellValue(customsMap.get(tracEnity.getCustomsTypeCd() + ""));
                    tracingRow.createCell(9).setCellValue(ticketMap.get(tracEnity.getTiicketOpenCd() + ""));
                    tracingRow.createCell(10).setCellValue(tracEnity.getFreight());
                    tracingRow.createCell(11).setCellValue(tracEnity.getTotalAmount());
                    tracingRow.createCell(12).setCellValue(tracEnity.getUnitPriceWithoutTax());
                    tracingRow.createCell(13).setCellValue(tracEnity.getAmountWithoutTax());
                    tracingRow.createCell(14).setCellValue(tracEnity.getTaxRate());
                    tracingRow.createCell(15).setCellValue(tracEnity.getUnitPriceWithTax());
                    tracingRow.createCell(16).setCellValue(tracEnity.getAmountWithTax());
                    tracingRow.createCell(17).setCellValue(tracEnity.getRemark());
                    tracingRow.createCell(18).setCellValue(deliveryTypetMap.get(tracEnity.getDeliveryTypeCd() + ""));
                    CellStyle s = workbook.createCellStyle();
                    s.setWrapText(true);
                    Cell cell18 = tracingRow.createCell(19);
//                    cell18.setCellStyle(s);
                    cell18.setCellValue(new HSSFRichTextString(deliverTimeAndNum));
                    tracingRow.createCell(20).setCellValue(tracEnity.getQuantity());
                    tracingRow.createCell(21).setCellValue(tracEnity.getFinishQuantity());
                    tracingRow.createCell(22).setCellValue(tracEnity.getDeliveryQuantity());
                    tracingRow.createCell(23).setCellValue(tracEnity.getSpareQuantity());
                    tracingRow.createCell(24).setCellValue(tracEnity.getReceiptQuantity());
                    tracingRow.createCell(25).setCellValue(tracEnity.getRejectionQuantity());
                    tracingRow.createCell(26).setCellValue(tracEnity.getEntryNum());
                    tracingRow.createCell(27).setCellValue(tracEnity.getReturnQuantity());
                    tracingRow.createCell(28).setCellValue(tracEnity.getLackNum());
                    tracingRow.createCell(29).setCellValue(tracEnity.getSpareRate());
                    tracingRow.createCell(30).setCellValue(tracEnity.getSpareNum());
                    tracingRow.createCell(31).setCellValue(tracEnity.getWareHouesEntryFrets());
                    tracingRow.createCell(32).setCellValue(tracEnity.getReturnEntryFrets());
                    tracingRow.createCell(33).setCellValue(tracEnity.getLackEntryFrets());
                }
                tracingList.clear();
            }
            workbook.write(out);
        } catch (Exception e) {
            try {
                response.getOutputStream().print("导出失败");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                response.getOutputStream().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping(value = "/track/entry/{orderId}/{skuId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取某商品入库，拒收，收货数相关明细", notes = "获取某商品入库，拒收，收货数相关明细")
    @ResponseBody
    public Result getEntry(@PathVariable("orderId") String orderId, @PathVariable("skuId") String skuId, @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {
        try {
            List<OrderTracingEntryVo> list = orderTracingService.getEntry(orderId, skuId);
            return Result.success(list);
        } catch (Exception e) {
            return Result.success(390, "查询异常", e.getMessage());
        }
    }

    @RequestMapping(value = "/track/return/{orderId}/{skuId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取某商品退货数明细", notes = "获取某商品退货数明细")
    @ResponseBody
    public Result getRejection(@PathVariable("orderId") String orderId, @PathVariable("skuId") String skuId, @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {
        try {
            List<OrderTracingReturnVo> list = orderTracingService.getReturn(orderId, skuId);
            return Result.success(list);
        } catch (Exception e) {
            return Result.success(390, "查询异常", e.getMessage());
        }
    }

    @RequestMapping(value = "/track/store/{orderId}/{skuId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取工厂库存明细", notes = "获取工厂库存明细")
    @ResponseBody
    public Result getStore(@PathVariable("orderId") String orderId, @PathVariable("skuId") String skuId, @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {
        try {
            List<OrderTracingCompleteVo> list = orderTracingService.getStore(orderId, skuId);
            return Result.success(list);
        } catch (Exception e) {
            return Result.success(390, "查询异常", e.getMessage());
        }
    }
}
