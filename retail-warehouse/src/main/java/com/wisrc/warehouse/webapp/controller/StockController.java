package com.wisrc.warehouse.webapp.controller;

import com.wisrc.warehouse.webapp.service.CompositedOrderService;
import com.wisrc.warehouse.webapp.entity.StockEntity;
import com.wisrc.warehouse.webapp.entity.WarehouseOutEnterInfoEntity;
import com.wisrc.warehouse.webapp.service.WarehouseStockReturnService;
import com.wisrc.warehouse.webapp.service.StockService;
import com.wisrc.warehouse.webapp.service.WarehouseOutEnterInfoService;
import com.wisrc.warehouse.webapp.utils.FileNameUtil;
import com.wisrc.warehouse.webapp.utils.Result;
import com.wisrc.warehouse.webapp.vo.EnableStockVO;
import com.wisrc.warehouse.webapp.vo.OutEnterWaterReturnVO;
import com.wisrc.warehouse.webapp.vo.syncVO.StockReturnVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@Api(tags = "库存管理")
@RequestMapping(value = "/warehouse")
public class StockController {
    @Autowired
    private WarehouseStockReturnService warehouseStockReturnService;
    @Autowired
    private StockService stockService;
    @Autowired
    private WarehouseOutEnterInfoService warehouseOutEnterInfoService;

    @Autowired
    private CompositedOrderService compositedOrderService;

    @RequestMapping(value = "/stock/list", method = RequestMethod.GET)
    @ApiOperation(value = "分页查询库存记录", notes = "1.通过warehouseId选择仓库查询对应仓库管理<br/>" +
            "2.通过keyword参数为关键字模糊查询，同时匹配库存SKU和产品名称，选填。<br/>", response = StockEntity.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "warehouseId", value = "下拉框选择仓库", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "keyword", value = "模糊查询关键字", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = false, defaultValue = "1", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "单页条数（限制最大值为100）", required = false, defaultValue = "10", dataType = "int", paramType = "query")
    })
    public Result getStockList(@RequestParam(value = "pageNum", required = false) String pageNum,
                               @RequestParam(value = "pageSize", required = false) String pageSize,
                               @RequestParam(value = "warehouseId", required = false) String warehouseId,
                               @RequestParam(value = "keyword", required = false) String keyword) {
        List<String> skuIds = new ArrayList<>();

        try {
            if (pageNum != null && pageSize != null) {
                int num = Integer.valueOf(pageNum);
                int size = Integer.valueOf(pageSize);
                return stockService.getStockList(num, size, warehouseId, keyword);
            } else {
                List<StockEntity> list = stockService.findAll(warehouseId, keyword);
                return Result.success(list);
            }
        } catch (NumberFormatException e) {
            return Result.failure();
        }
    }


    @RequestMapping(value = "/stock/skuIds", method = RequestMethod.GET)
    @ApiOperation(value = "根据库存SKU批量查询商品库存分布情况")
    public Result getStockBySku(@RequestParam("skuIds") String[] skuIds) {

        try {
            List skuIdList = Arrays.asList(skuIds);
            List<StockEntity> list = stockService.getStockBySku(skuIdList);
            return Result.success(list);

        } catch (NumberFormatException e) {
            return Result.failure();
        }

    }

    @RequestMapping(value = "/stock/cond", method = RequestMethod.POST)
    @ApiOperation(value = "根据库存SKU和仓库ID查询商品数量")
    public Result getStock(@RequestBody List<EnableStockVO> voList) {
        List<StockEntity> list = new ArrayList<>();
        try {
            for (EnableStockVO vo : voList) {
                StockEntity entity = stockService.getStockByCond(vo.getSkuId(), vo.getWarehouseId());
                if (entity != null) {
                    list.add(entity);
                }
            }
            return Result.success(list);
        } catch (NumberFormatException e) {
            return Result.failure();
        }
    }


    @RequestMapping(value = "/stock/export", method = RequestMethod.GET)
    @ApiOperation(value = "导出库存管理信息表")
    public void export(HttpServletRequest request, HttpServletResponse response,
                       @RequestParam(value = "warehouseId", required = false) String warehouseId,
                       @RequestParam(value = "keyword", required = false) String keyword) throws Exception {
        try {
            List<StockEntity> list = stockService.findAll(warehouseId, keyword);
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("库存信息表");
            String[] excelHeader = {"库存SKU", "产品名称", "仓库", "总库存量", "在途库存", "在仓库存", "可用库存", "最近出入时间"};
            int rowNum = 1;
            HSSFRow row = sheet.createRow(0);
            for (int i = 0; i < excelHeader.length; i++) {
                HSSFCell cell = row.createCell(i);
                HSSFRichTextString text = new HSSFRichTextString(excelHeader[i]);
                cell.setCellValue(text);
            }
            if (list.size() > 0) {
                //在表中存放查询到的数据放入对应的列
                for (int i = 0; i < list.size(); i++) {
                    HSSFRow row1 = sheet.createRow(rowNum);
                    StockEntity ele = list.get(i);
                    if (ele != null) {
                        row1.createCell(0).setCellValue(ele.getSkuId());
                        row1.createCell(1).setCellValue(ele.getSkuName());
                        row1.createCell(2).setCellValue(ele.getWarehouseName());
                        if (ele.getTotalSum() != null) {
                            row1.createCell(3).setCellValue(ele.getTotalSum());
                        }
                        if (ele.getOnWayStock() != null) {
                            row1.createCell(4).setCellValue(ele.getOnWayStock());
                        }
                        if (ele.getSumStock() != null) {
                            row1.createCell(5).setCellValue(ele.getSumStock());
                        }
                        if (ele.getEnableStockNum() != null) {
                            row1.createCell(6).setCellValue(ele.getEnableStockNum());
                        }
                        if (ele.getLastInoutTime() != null) {
                            //yyyy-MM-dd HH:mm:ss  -> yyyy-MM-dd
                            row1.createCell(7).setCellValue(ele.getLastInoutTime().substring(0, 10));
                        } else {
                            row1.createCell(7).setCellValue("--");
                        }
                    }
                    rowNum++;
                }
                String fileName = "产品库存"
                        + new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis()) + ".xls";
                response.setContentType("application/octet-stream");
                String exportName = FileNameUtil.exportTo(fileName, request);
                response.setHeader("Content-disposition", "attachment;filename=\"" + exportName + "\"");
                response.flushBuffer();
                workbook.write(response.getOutputStream());
            } else {
                response.getWriter().write("No Result");
            }
        } catch (
                NumberFormatException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "/stock/water", method = RequestMethod.GET)
    @ApiOperation(value = "查询库存出入流水")
    public Result getStockWater(@RequestParam(value = "skuId", required = true) String skuId,
                                @RequestParam(value = "warehouseId", required = true) String warehouseId,
                                @RequestParam(value = "pageNum", required = false) String pageNum,
                                @RequestParam(value = "pageSize", required = false) String pageSize) {
        List<WarehouseOutEnterInfoEntity> list = new ArrayList<>();
        try {
            if (pageNum != null && pageSize != null) {
                int num = Integer.parseInt(pageNum);
                int size = Integer.parseInt(pageSize);
                list = warehouseOutEnterInfoService.getStockWaterByPage(num, size, skuId, warehouseId);
            } else {
                list = warehouseOutEnterInfoService.getStockWater(skuId, warehouseId);
            }
            return Result.success(list);
        } catch (NumberFormatException e) {
            return Result.failure();
        }
    }

    @RequestMapping(value = "/sync/stock", method = RequestMethod.POST)
    @ApiOperation(value = "同步WMS和亚马逊库存数据")
    public Result getSyncStock(@RequestBody List<StockReturnVO> voList) {
        try {
            warehouseStockReturnService.refreshLocalStock(voList);
            return Result.success();
        } catch (Exception e) {
            return Result.failure();
        }

    }


    @RequestMapping(value = "/sync/water", method = RequestMethod.POST)
    @ApiOperation(value = "同步WMS流水数据")
    public Result getSyncWater(@RequestBody List<OutEnterWaterReturnVO> voList) {
        try {
            warehouseStockReturnService.insertWater(voList);
            return Result.success();
        } catch (Exception e) {
            return Result.failure();
        }

    }


    @ApiOperation(value = "查询组装产品的子产品是否足够")
    @RequestMapping(value = "/stock/composited/order", method = RequestMethod.GET)
    public Result getCompositedProduct(@RequestParam(value = "skuId") String skuId,
                                       @RequestParam(value = "warehouseId") String warehouseId,
                                       @RequestParam("number") Integer number) {
        try {
            return compositedOrderService.getInventory(skuId, warehouseId, number);
        } catch (Exception e) {
            return Result.failure(390, "查询失败", e.getMessage());
        }
    }

    @ApiOperation("根据fnsku去判断库存问题，创建调拨单，补货单专用,不会判断组装不组装")
    @RequestMapping(value = "/stock/judge/byFnSku", method = RequestMethod.GET)
    public Result judgeStockByFnskuAndFnskuId(@RequestParam("fnSku") String fnSku,
                                              @RequestParam("warehouseId") String warehouseId,
                                              @RequestParam("number") Integer number) {
        try {
            return compositedOrderService.judgeStockByFnSkuAndFnSkuId(fnSku, warehouseId, number);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(390, "判断失败", e.getMessage());
        }
    }

    @ApiOperation(value = "根据skuId和fnCode和subWarehouseId来查库存", notes = "根据库存SKU和FN码和分仓编号查询，如果FN编码没有，可以不传这个参数")
    @RequestMapping(value = "/stock/subWarehouse/orFnCode", method = RequestMethod.GET)
    public Result getStockInfo(@RequestParam("skuId") String skuId,
                               @RequestParam(value = "fnCode", required = false) String fnCode,
                               @RequestParam("subWarehouseId") String subWarehouseId) {
        try {
            return Result.success(200, "查询成功", stockService.getStockInfo(skuId, fnCode, subWarehouseId));
        } catch (Exception e) {
            return Result.failure(390, "查询失败", e.getMessage());
        }
    }
}
