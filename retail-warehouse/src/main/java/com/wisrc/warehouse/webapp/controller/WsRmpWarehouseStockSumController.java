package com.wisrc.warehouse.webapp.controller;

import com.wisrc.warehouse.webapp.entity.MskuToSkuEntity;
import com.wisrc.warehouse.webapp.entity.WsRmpWarehouseStockSumEntity;
import com.wisrc.warehouse.webapp.service.WsRmpWarehouseStockSumService;
import com.wisrc.warehouse.webapp.utils.FileNameUtil;
import com.wisrc.warehouse.webapp.utils.Result;
import com.wisrc.warehouse.webapp.vo.DetailVO;
import com.wisrc.warehouse.webapp.vo.MskuFbaVO;
import com.wisrc.warehouse.webapp.vo.MskuShopVO;
import com.wisrc.warehouse.webapp.vo.stockVO.SkuVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
@Api(tags = "商品结存明细表导出")
@RequestMapping(value = "/warehouse")
public class WsRmpWarehouseStockSumController {
    @Autowired
    private WsRmpWarehouseStockSumService wsRmpWarehouseStockSumService;


    @RequestMapping(value = "/goods/list", method = RequestMethod.GET)
    @ApiOperation(value = "分页查询商品结存明细", response = WsRmpWarehouseStockSumEntity.class)
    public Result getStockList(@RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
                               @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                               @RequestParam(value = "date", required = false) String date,
                               @RequestParam(value = "shopName", required = false) String shopName,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "orderFlag", required = false, defaultValue = "0") int orderFlag) {
        LinkedHashMap map = wsRmpWarehouseStockSumService.getStockDetail(pageNum, pageSize, date, shopName, keyword, orderFlag);
        return Result.success(map);


    }


    @RequestMapping(value = "/export/detail", method = RequestMethod.GET)
    @ApiOperation(value = "导出商品结存明细表", notes = "通过选择本地的Excel模板，将查询出来的数据导入模板中并生成新的Excel文件<br/>", response = WsRmpWarehouseStockSumEntity.class)
    public Result exportDetail(HttpServletRequest request, HttpServletResponse response,
                               @RequestParam(value = "date", required = false) String date,
                               @RequestParam(value = "shopName", required = false) String shopName,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "orderFlag", required = false, defaultValue = "0") int orderFlag) throws Exception {
        LinkedHashMap map = wsRmpWarehouseStockSumService.getStockDetail(date, shopName, keyword, orderFlag);
        List<WsRmpWarehouseStockSumEntity> list = (List<WsRmpWarehouseStockSumEntity>) map.get("wsRmpWarehouseStockSumList");
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("商品结存明细表");
        sheet.setColumnWidth(0, 30 * 256);
        sheet.setColumnWidth(10, 20 * 256);
        sheet.setColumnWidth(11, 20 * 256);
        sheet.addMergedRegion(new CellRangeAddress(0, 2, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(0, 2, 1, 1));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 2, 3));
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 2, 2));
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 3, 3));
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 4, 4));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 5, 7));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 5, 6));
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 7, 7));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 8, 9));
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 8, 8));
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 9, 9));
        sheet.addMergedRegion(new CellRangeAddress(0, 2, 10, 10));
        sheet.addMergedRegion(new CellRangeAddress(0, 2, 11, 11));
        sheet.addMergedRegion(new CellRangeAddress(0, 2, 12, 12));


//第一行数据
        HSSFRow row = sheet.createRow(0);
        HSSFRow row1 = sheet.createRow(1);
        HSSFRow row2 = sheet.createRow(2);
        //表头单元格样式
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        //表格内容样式
        HSSFCellStyle contentStyle = workbook.createCellStyle();
//        contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//        contentStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        //全局字体
        HSSFFont globalFont = workbook.createFont();
        globalFont.setFontName("微软雅黑");
//        headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
//        headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//        headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        headerStyle.setFont(globalFont);
        contentStyle.setFont(globalFont);
        HSSFCell cell00 = row.createCell(0);
        cell00.setCellValue("库存SKU/产品名称");
        cell00.setCellStyle(headerStyle);
        HSSFCell cell01 = row.createCell(1);
        cell01.setCellValue("生产中");
        cell01.setCellStyle(headerStyle);
        HSSFCell cell02 = row.createCell(2);
        cell02.setCellValue("本地仓");
        cell02.setCellStyle(headerStyle);
        HSSFCell cell04 = row.createCell(4);
        cell04.setCellValue("虚拟仓");
        cell04.setCellStyle(headerStyle);
        HSSFCell cell05 = row.createCell(5);
        cell05.setCellValue("海外仓");
        cell05.setCellStyle(headerStyle);
        HSSFCell cell08 = row.createCell(8);
        cell08.setCellValue("FBA仓");
        cell08.setCellStyle(headerStyle);
        HSSFCell cell010 = row.createCell(10);
        cell010.setCellValue("FnSku编号");
        cell010.setCellStyle(headerStyle);
        HSSFCell cell011 = row.createCell(11);
        cell011.setCellValue("店铺");
        cell011.setCellStyle(headerStyle);
        HSSFCell cell012 = row.createCell(12);
        cell012.setCellValue("合计");
        cell012.setCellStyle(headerStyle);
        HSSFCell cell12 = row1.createCell(2);
        cell12.setCellValue("在途");
        cell12.setCellStyle(headerStyle);
        HSSFCell cell13 = row1.createCell(3);
        cell13.setCellValue("在仓");
        cell13.setCellStyle(headerStyle);
        HSSFCell cell14 = row1.createCell(4);
        cell14.setCellValue("在仓");
        cell14.setCellStyle(headerStyle);
        HSSFCell cell15 = row1.createCell(5);
        cell15.setCellValue("在途");
        cell15.setCellStyle(headerStyle);
        HSSFCell cell17 = row1.createCell(7);
        cell17.setCellValue("在仓");
        cell17.setCellStyle(headerStyle);
        HSSFCell cell18 = row1.createCell(8);
        cell18.setCellValue("在途");
        cell18.setCellStyle(headerStyle);
        HSSFCell cell19 = row1.createCell(9);
        cell19.setCellValue("在仓");
        cell19.setCellStyle(headerStyle);
        HSSFCell cell25 = row2.createCell(5);
        cell25.setCellValue("国内调拨");
        cell25.setCellStyle(headerStyle);
        HSSFCell cell26 = row2.createCell(6);
        cell26.setCellValue("FBA退仓");
        cell26.setCellStyle(headerStyle);


        int rowNum = 3;
        try {
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    HSSFRow rowN = sheet.createRow(rowNum);
                    HSSFCell cell0Num = rowN.createCell(0);
                    cell0Num.setCellValue(list.get(i).getSkuId() + "/" + list.get(i).getSkuName());
                    cell0Num.setCellStyle(contentStyle);
                    HSSFCell cell1Num = rowN.createCell(1);
                    cell1Num.setCellValue(list.get(i).getProductQty());
                    cell1Num.setCellStyle(contentStyle);
                    HSSFCell cell2Num = rowN.createCell(2);
                    cell2Num.setCellValue(list.get(i).getLocalOnwayQty());
                    cell2Num.setCellStyle(contentStyle);
                    HSSFCell cell3Num = rowN.createCell(3);
                    cell3Num.setCellValue(list.get(i).getLocalStockQty());
                    cell3Num.setCellStyle(contentStyle);
                    HSSFCell cell4Num = rowN.createCell(4);
                    cell4Num.setCellValue(list.get(i).getVirtualStockQty());
                    cell4Num.setCellStyle(contentStyle);
                    HSSFCell cell5Num = rowN.createCell(5);
                    cell5Num.setCellValue(list.get(i).getOverseasTransportQty());
                    cell5Num.setCellStyle(contentStyle);
                    HSSFCell cell7Num = rowN.createCell(7);
                    cell7Num.setCellValue(list.get(i).getOverseasStockQty());
                    cell7Num.setCellStyle(contentStyle);
                    HSSFCell cell12Num = rowN.createCell(12);
                    cell12Num.setCellValue(list.get(i).getTotalQty());
                    cell12Num.setCellStyle(contentStyle);

                    if (list.get(i).getMskuList().size() - 1 > 0) {
                        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum + list.get(i).getMskuList().size() - 1, 0, 0));
                        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum + list.get(i).getMskuList().size() - 1, 1, 1));
                        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum + list.get(i).getMskuList().size() - 1, 2, 2));
                        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum + list.get(i).getMskuList().size() - 1, 3, 3));
                        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum + list.get(i).getMskuList().size() - 1, 4, 4));
                        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum + list.get(i).getMskuList().size() - 1, 5, 5));
                        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum + list.get(i).getMskuList().size() - 1, 7, 7));
                        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum + list.get(i).getMskuList().size() - 1, 12, 12));
                    }

                    for (int j = 0; j < list.get(i).getMskuList().size(); j++) {
                        HSSFRow rowNj = sheet.createRow(rowNum + j);
                        if (j == 0) {
                            HSSFCell cell0rowNj = rowNj.createCell(0);
                            cell0rowNj.setCellValue(list.get(i).getSkuId() + "/" + list.get(i).getSkuName());
                            cell0rowNj.setCellStyle(contentStyle);
                            HSSFCell cell1rowNj = rowNj.createCell(1);
                            cell1rowNj.setCellValue(list.get(i).getProductQty());
                            cell1rowNj.setCellStyle(contentStyle);
                            HSSFCell cell2rowNj = rowNj.createCell(2);
                            cell2rowNj.setCellValue(list.get(i).getLocalOnwayQty());
                            cell2rowNj.setCellStyle(contentStyle);
                            HSSFCell cell3rowNj = rowNj.createCell(3);
                            cell3rowNj.setCellValue(list.get(i).getLocalStockQty());
                            cell3rowNj.setCellStyle(contentStyle);
                            HSSFCell cell4rowNj = rowNj.createCell(4);
                            cell4rowNj.setCellValue(list.get(i).getVirtualStockQty());
                            cell4rowNj.setCellStyle(contentStyle);
                            HSSFCell cell5rowNj = rowNj.createCell(5);
                            cell5rowNj.setCellValue(list.get(i).getOverseasTransportQty());
                            cell5rowNj.setCellStyle(contentStyle);
                            HSSFCell cell7rowNj = rowNj.createCell(7);
                            cell7rowNj.setCellValue(list.get(i).getOverseasStockQty());
                            cell7rowNj.setCellStyle(contentStyle);
                            HSSFCell cell12rowNj = rowNj.createCell(12);
                            cell12rowNj.setCellValue(list.get(i).getTotalQty());
                            cell12rowNj.setCellStyle(contentStyle);
                            HSSFCell cell6rowNj = rowNj.createCell(6);
                            cell6rowNj.setCellValue(list.get(i).getMskuList().get(j).getFbaReturnQty());
                            cell6rowNj.setCellStyle(contentStyle);
                            HSSFCell cell8rowNj = rowNj.createCell(8);
                            cell8rowNj.setCellValue(list.get(i).getMskuList().get(j).getFbaTransportQty());
                            cell8rowNj.setCellStyle(contentStyle);
                            HSSFCell cell9rowNj = rowNj.createCell(9);
                            cell9rowNj.setCellValue(list.get(i).getMskuList().get(j).getFbaStockQty());
                            cell9rowNj.setCellStyle(contentStyle);
                            HSSFCell cell10rowNj = rowNj.createCell(10);
                            cell10rowNj.setCellValue(list.get(i).getMskuList().get(j).getFnSkuId());
                            cell10rowNj.setCellStyle(contentStyle);
                            HSSFCell cell11rowNj = rowNj.createCell(11);
                            cell11rowNj.setCellValue(list.get(i).getMskuList().get(j).getShopName());
                            cell11rowNj.setCellStyle(contentStyle);
                        } else {
                            HSSFCell cellNj6 = rowNj.createCell(6);
                            cellNj6.setCellValue(list.get(i).getMskuList().get(j).getFbaReturnQty());
                            cellNj6.setCellStyle(contentStyle);
                            HSSFCell cellNj8 = rowNj.createCell(8);
                            cellNj8.setCellValue(list.get(i).getMskuList().get(j).getFbaTransportQty());
                            cellNj8.setCellStyle(contentStyle);
                            HSSFCell cellNj9 = rowNj.createCell(9);
                            cellNj9.setCellValue(list.get(i).getMskuList().get(j).getFbaStockQty());
                            cellNj9.setCellStyle(contentStyle);
                            HSSFCell cellNj10 = rowNj.createCell(10);
                            cellNj10.setCellValue(list.get(i).getMskuList().get(j).getFnSkuId());
                            cellNj10.setCellStyle(contentStyle);
                            HSSFCell cellNj11 = rowNj.createCell(11);
                            cellNj11.setCellValue(list.get(i).getMskuList().get(j).getShopName());
                            cellNj11.setCellStyle(contentStyle);
                        }
                    }

                    if (list.get(i).getMskuList().size() == 0) {
                        rowNum++;
                    } else {
                        rowNum += list.get(i).getMskuList().size();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setContentType("application/octet-stream");
        //修复 导出名称应为商品结存明细yyyyMMddHHmmss
        String fileName = "商品结存明细"
                + new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis()) + ".xls";
        String exportName = FileNameUtil.exportTo(fileName, request);
        response.setHeader("Content-disposition", "attachment;filename=\"" + exportName + "\"");
        response.flushBuffer();
        workbook.write(response.getOutputStream());
        return Result.success(200, "导出成功", list);
    }

    @RequestMapping(value = "detail/total", method = RequestMethod.GET)
    public Result getTotal(@RequestParam("date") String date, @RequestParam("skuIds") List skuIds) {
        LinkedHashMap list = wsRmpWarehouseStockSumService.getTotal(date, skuIds);
        return Result.success(list);
    }

    @RequestMapping(value = "/onway/detail", method = RequestMethod.GET)
    public Result getDetail(@RequestParam("skuId") String skuId, @RequestParam("warehouseId") String warehouseId) {
        List<DetailVO> list = wsRmpWarehouseStockSumService.getDetail(skuId, warehouseId);
        return Result.success(list);
    }


    @RequestMapping(value = "/msku/fba", method = RequestMethod.POST)
    @ApiOperation(value = "根据msku批量查询FBA在仓，在途实时数据")
    public Result getMskuFba(@RequestBody List<MskuShopVO> mskuShopVOList) {
        List<MskuFbaVO> list = wsRmpWarehouseStockSumService.getMskuFba(mskuShopVOList);
        return Result.success(list);
    }


    @RequestMapping(value = "/refresh/stock/total", method = RequestMethod.POST)
    public Result refreshStockTotal() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dataDt = sdf.format(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
        try {
            wsRmpWarehouseStockSumService.deleteRecord(dataDt);
            List<WsRmpWarehouseStockSumEntity> list = wsRmpWarehouseStockSumService.getAllRecord();
            for (WsRmpWarehouseStockSumEntity entity : list) {
                SkuVO vo = new SkuVO();
                int totalQty = 0;
                vo.setDataDt(dataDt);
                vo.setSkuId(entity.getSkuId());
                vo.setSkuName(entity.getSkuName());
                vo.setProductQty(entity.getProductQty());
                vo.setTransportQty(entity.getLocalOnwayQty());
                vo.setPanyuStockQty(entity.getLocalStockQty());
                vo.setVirtualStockQty(entity.getVirtualStockQty());
                vo.setOverseasTransportQty(entity.getOverseasTransportQty());
                vo.setOverseasStockQty(entity.getOverseasStockQty());
                totalQty = vo.getProductQty() + vo.getTransportQty() + vo.getPanyuStockQty() + vo.getVirtualStockQty() + vo.getOverseasTransportQty() + vo.getOverseasStockQty();
                for (MskuToSkuEntity msku : entity.getMskuList()) {
                    totalQty += msku.getFbaReturnQty() + msku.getFbaTransportQty() + msku.getFbaStockQty();
                    msku.setDataDt(dataDt);
                    wsRmpWarehouseStockSumService.addMsukStock(msku);
                }
                vo.setTotalQty(totalQty);
                wsRmpWarehouseStockSumService.addStockSum(vo);
            }
            return Result.success("库存汇总刷新成功");
        } catch (Exception e) {
            return Result.success("库存汇总刷新失败");
        }
    }
}
