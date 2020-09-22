package com.wisrc.warehouse.webapp.controller;

import com.wisrc.warehouse.webapp.entity.WarehouseDocumentTypeEntity;
import com.wisrc.warehouse.webapp.entity.WarehouseOutEnterInfoEntity;
import com.wisrc.warehouse.webapp.service.WarehouseDocumentTypeService;
import com.wisrc.warehouse.webapp.service.WarehouseManageInfoService;
import com.wisrc.warehouse.webapp.service.WarehouseOutEnterInfoService;
import com.wisrc.warehouse.webapp.utils.FileNameUtil;
import com.wisrc.warehouse.webapp.utils.Result;
import com.wisrc.warehouse.webapp.vo.swagger.WarehouseInfoResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@Api(tags = "出入库流水")
@RequestMapping(value = "/warehouse")
public class WarehouseOutEnterInfoController {
    @Autowired
    private WarehouseOutEnterInfoService warehouseOutEnterInfoService;
    @Autowired
    private WarehouseManageInfoService warehouseManageInfoService;
    @Autowired
    private WarehouseDocumentTypeService warehouseDocumentTypeService;

    @RequestMapping(value = "/outenter/info", method = RequestMethod.GET)
    @ApiOperation(value = "查询出入库基本信息", notes = "根据查询条件查询出入库基本信息<br/>" +
            "当pageNum和pageSize为有效数字时，将会分页查询.没有则默认为展示第1页的10条数据<br/>" +
            "当返回值中total和pages为-1时，表示默认全表查询。<br/>" +
            "关键字查询是按照  库存SKU/产品名称/单号/备注 4个字段全局查询。<br/>" +
            "如果有匹配的，全部查询出来！", response = WarehouseInfoResponseModel.class)
    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "documentType", value = "单据类型(0-全部查询，其他类型待业务确定更新)", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "outEnterType", value = "出入类型(0-全部查询，其他类型待业务确定更新)", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "warehouseId", value = "仓库ID", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "createTimeBegin", value = "操作时间开始", dataType = "Timestamp", paramType = "query"),
            @ApiImplicitParam(name = "createTimeEnd", value = "操作时间结束", dataType = "Timestamp", paramType = "query"),
            @ApiImplicitParam(name = "keyWord", value = "关键字", dataType = "int", paramType = "query")
    })*/
    @ResponseBody
    public Result findAll(@RequestParam(value = "pageNum", required = false) String pageNum,
                          @RequestParam(value = "pageSize", required = false) String pageSize,
                          @RequestParam(value = "documentType", required = false) String documentType,
                          @RequestParam(value = "outEnterType", required = false, defaultValue = "0") int outEnterType,
                          @RequestParam(value = "warehouseId", required = false) String warehouseId,
                          @RequestParam(value = "createTimeBegin", required = false) Timestamp createTimeBegin,
                          @RequestParam(value = "createTimeEnd", required = false) Timestamp createTimeEnd,
                          @RequestParam(value = "keyWord", required = false) String keyWord) {
        try {
            if (pageNum != null && pageSize != null) {
                int size = Integer.valueOf(pageSize);
                int num = Integer.valueOf(pageNum);
                LinkedHashMap list = warehouseOutEnterInfoService.getWarehouseOutEnterInfoAll(num, size, documentType, outEnterType, warehouseId, createTimeBegin, createTimeEnd, keyWord);
                return Result.success(list);
            } else {
                List<WarehouseOutEnterInfoEntity> mlist = warehouseOutEnterInfoService.getWarehouseOutEnterInfoAll();
                return Result.success(mlist);
            }
        } catch (Exception e) {
            return Result.failure(390, e.getMessage(), "");
        }

    }

    /**
     * 导出报表
     *
     * @return
     */
    @RequestMapping(value = "/outenter/export", method = RequestMethod.GET)
    @ApiOperation(value = "出入库信息导出")
    @ResponseBody
    public void export(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "pageNum", required = false, defaultValue = "1") String pageNum,
                       @RequestParam(value = "pageSize", required = false, defaultValue = "10") String pageSize,
                       @RequestParam(value = "documentType", required = false) String documentType,
                       @RequestParam(value = "outEnterType", required = false, defaultValue = "0") int outEnterType,
                       @RequestParam(value = "warehouseId", required = false) String warehouseId,
                       @RequestParam(value = "createTimeBegin", required = false) Timestamp createTimeBegin,
                       @RequestParam(value = "createTimeEnd", required = false) Timestamp createTimeEnd,
                       @RequestParam(value = "keyWord", required = false) String keyWord) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int size = Integer.valueOf(pageSize);
        int num = Integer.valueOf(pageNum);
        LinkedHashMap map = warehouseOutEnterInfoService.getWarehouseOutEnterInfoAll(num, size, documentType, outEnterType, warehouseId, createTimeBegin, createTimeEnd, keyWord);
        List<WarehouseOutEnterInfoEntity> list = (List<WarehouseOutEnterInfoEntity>) map.get("warehouseOutEnterInfoList");
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("出入库信息表");
        //新增数据行，并且设置单元格数据
        int rowNum = 1;

        String[] headers = {"库存SKU", "产品名称", "FnSku", "仓库", "库位", "入库批次", "生产批次", "变更前", "变更数量", "变更后", "来源单据", "单据类型", " 操作时间", "操作人", "备注"};

        HSSFRow row = sheet.createRow(0);
        //在excel表中添加表头

        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        List<WarehouseDocumentTypeEntity> documentTypeList = warehouseDocumentTypeService.findAll();
        Map<String, String> documentTypeMap = new HashMap<>();
        for (WarehouseDocumentTypeEntity eleType : documentTypeList) {
            documentTypeMap.put(eleType.getDocumentType(), eleType.getDocumentTypeName());
        }
        if (null != list) {
            //在表中存放查询到的数据放入对应的列
            for (int i = 0; i < list.size(); i++) {
                HSSFRow row1 = sheet.createRow(rowNum);
                //修改导出Excel名称字段不对应bug
                //仓库Sku
                row1.createCell(0).setCellValue(list.get(i).getSkuId());
                //产品名称
                row1.createCell(1).setCellValue(list.get(i).getSkuName());
                //FnSku
                row1.createCell(2).setCellValue(list.get(i).getFnSkuId());
                //仓库
                row1.createCell(3).setCellValue(list.get(i).getWarehouseName());
                //库位
                row1.createCell(4).setCellValue(list.get(i).getWarehousePosition());
                //入库批次
                row1.createCell(5).setCellValue(list.get(i).getEnterBatch());
                //生产批次
                row1.createCell(6).setCellValue(list.get(i).getProductionBatch());
                //变更前数量
                row1.createCell(7).setCellValue(list.get(i).getChangeAgoNum());
                //变更数量
                if (1 == list.get(i).getOutEnterType()) {
                    row1.createCell(8).setCellValue(list.get(i).getChangeNum());
                } else {
                    row1.createCell(8).setCellValue(list.get(i).getChangeNum());
                }
                //变更后数量
                row1.createCell(9).setCellValue(list.get(i).getChangeLaterNum());
                //来源单据
                row1.createCell(10).setCellValue(list.get(i).getProductionBatch());
                //单据类型
                row1.createCell(11).setCellValue(documentTypeMap.get(list.get(i).getDocumentType()));
                //操作时间
                row1.createCell(12).setCellValue(sdf.format(list.get(i).getCreateTime()));
                //操作人
                row1.createCell(13).setCellValue(list.get(i).getCreateUser());
                //备注
                row1.createCell(14).setCellValue(list.get(i).getRemark());

                /** 修改前内容
                 *  row1.createCell(0).setCellValue(list.get(i).getSkuId());
                 *                 row1.createCell(1).setCellValue(list.get(i).getSkuName());
                 *                 row1.createCell(2).setCellValue(list.get(i).getFnSkuId());
                 *                 row1.createCell(3).setCellValue(list.get(i).getWarehouseName());
                 *                 row1.createCell(4).setCellValue(list.get(i).getWarehousePosition());
                 *                 row1.createCell(5).setCellValue(list.get(i).getEnterBatch());
                 *                 row1.createCell(6).setCellValue(list.get(i).getProductionBatch());
                 *                 row1.createCell(7).setCellValue(list.get(i).getChangeAgoNum());
                 *                 if (1 == list.get(i).getOutEnterType()) {
                 *                     row1.createCell(8).setCellValue("-" + list.get(i).getChangeNum());
                 *                 } else {
                 *                     row1.createCell(8).setCellValue("+" + list.get(i).getChangeNum());
                 *                 }
                 *                 row1.createCell(9).setCellValue(list.get(i).getChangeLaterNum());
                 *                 row1.createCell(10).setCellValue(list.get(i).getSourceId());
                 *                 row1.createCell(11).setCellValue((String) documentTypeMap.get(list.get(i).getDocumentType()));
                 *                 row1.createCell(12).setCellValue(sdf.format(list.get(i).getCreateTime()));
                 *                 row1.createCell(13).setCellValue(list.get(i).getCreateUser());
                 *                 row1.createCell(14).setCellValue(list.get(i).getRemark());
                 */
                rowNum++;
            }
        } else {
            HSSFRow row1 = sheet.createRow(0);
        }
        response.setContentType("application/octet-stream");
        String fileName = "出入库流水"
                + new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis()) + ".xls";
        String exportName = FileNameUtil.exportTo(fileName, request);
        response.setHeader("Content-disposition", "attachment;filename=\"" + exportName + "\"");
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }

}
