package com.wisrc.purchase.webapp.controller;

import com.wisrc.purchase.webapp.entity.EntryWarehouseEntity;
import com.wisrc.purchase.webapp.entity.EntryWarehouseProductEntity;
import com.wisrc.purchase.webapp.entity.ProductMachineInfoEntity;
import com.wisrc.purchase.webapp.service.PurchaseEmployeeService;
import com.wisrc.purchase.webapp.service.EntryWarehouseService;
import com.wisrc.purchase.webapp.service.SupplierService;
import com.wisrc.purchase.webapp.service.WarehouseService;
import com.wisrc.purchase.webapp.utils.Result;
import com.wisrc.purchase.webapp.vo.EntryWarehouseExportVo;
import com.wisrc.purchase.webapp.vo.entrywarehouse.EntryWarehouseAllVO;
import com.wisrc.purchase.webapp.vo.entrywarehouse.EntryWarehouseProductVO;
import com.wisrc.purchase.webapp.vo.entrywarehouse.EntryWarehouseVO;
import com.wisrc.purchase.webapp.vo.entrywarehouse.ReturnEntryPara;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Api(tags = "采购入库单")
@RequestMapping(value = "/purchase")
public class EntryWarehouseController {
    private final Logger logger = LoggerFactory.getLogger(OrderBasisInfoController.class);
    @Autowired
    EntryWarehouseService entryWarehouseService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private PurchaseEmployeeService purchaseEmployeeService;
    @Autowired
    private WarehouseService warehouseService;

    @RequestMapping(value = "/entry/info", method = RequestMethod.GET)
    @ApiOperation(value = "查询采购入库单信息", notes = "采购入库单信息列表", response = EntryWarehouseVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", defaultValue = "1", value = "页码", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "pageSize", defaultValue = "10", value = "每页条数", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "entryId", value = "采购入库单号", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "entryTimeBegin", value = "入库开始日期", paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "entryTimeEnd", value = "入库结束日期", paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "supplierDeliveryNum", value = "供应商送货单号", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "inspectionId", value = "验货申请/提货单号", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "entryUser", value = "入库人", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "warehouseId", value = "入库仓库", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "supplierId", value = "供应商", paramType = "query", dataType = "String")
    })
    @ResponseBody
    public Result findInfoAll(@RequestParam(value = "pageNum", required = false) String pageNum,
                              @RequestParam(value = "pageSize", required = false) String pageSize,
                              @RequestParam(value = "entryId", required = false) String entryId,
                              @RequestParam(value = "entryTimeBegin", required = false) Date entryTimeBegin,
                              @RequestParam(value = "entryTimeEnd", required = false) Date entryTimeEnd,
                              @RequestParam(value = "supplierDeliveryNum", required = false) String supplierDeliveryNum,
                              @RequestParam(value = "inspectionId", required = false) String inspectionId,
                              @RequestParam(value = "entryUser", required = false) String entryUser,
                              @RequestParam(value = "warehouseId", required = false) String warehouseId,
                              @RequestParam(value = "supplierId", required = false) String supplierId) {
        try {
            int size = Integer.valueOf(pageSize);
            int num = Integer.valueOf(pageNum);
            LinkedHashMap result = entryWarehouseService.infoList(num, size, entryId, entryTimeBegin, entryTimeEnd, supplierDeliveryNum, inspectionId, entryUser, warehouseId, supplierId);
            return Result.success(200, "分页查询成功", result);
        } catch (Exception e) {
            return Result.success(200, "不分页查询成功", entryWarehouseService.infoList(entryId, entryTimeBegin, entryTimeEnd, supplierDeliveryNum, inspectionId, entryUser, warehouseId, supplierId));
        }
    }

    @RequestMapping(value = "/entry/add", method = RequestMethod.POST)
    @ApiOperation(value = "采购入库单新增", notes = "采购入库单新增新增", response = EntryWarehouseAllVO.class)
    @ResponseBody
    public Result addEntryInfo(@RequestBody @Valid EntryWarehouseAllVO entryAllVO, BindingResult result,
                               @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {
        if (result.hasErrors()) {
            return Result.failure(670, "订单入库单创建失败：" + result.getAllErrors().get(0).getDefaultMessage(), null);
        }
        return entryWarehouseService.addInfo(entryAllVO, userId);
    }

    @RequestMapping(value = "/entry/update", method = RequestMethod.PUT)
    @ApiOperation(value = "采购入库单修改", notes = "采购入库单修改", response = EntryWarehouseAllVO.class)
    @ResponseBody
    public Result updateBasisInfo(@RequestBody @Valid EntryWarehouseAllVO entryAllVO, BindingResult result, @RequestParam(value = "entryId", required = true) String entryId, @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {
        if (result.hasErrors()) {
            return Result.failure(390, "订单入库单修改失败", result.getAllErrors().get(0).getDefaultMessage());
            //       return Result.failure(390,result.getAllErrors().get(0).toString(), result.getAllErrors());
        }
        // 校验 入库数量、入备品数至少有一项大于0
        List<EntryWarehouseProductVO> entryWarehouseProductVOList = entryAllVO.getEntryWarehouseProductVO();
        StringBuilder message = new StringBuilder("入库通知单产品");
        for (int i = 0; i < entryWarehouseProductVOList.size(); i++) {
            EntryWarehouseProductVO entryWarehouseProductVO = entryWarehouseProductVOList.get(i);
            if (entryWarehouseProductVO.getEntryNum() + entryWarehouseProductVO.getEntryFrets() <= 0) {
                message.append("第").append(i + 1).append("项").append("、");
            }
        }
        if (message.length() > 8) {
            message = message.deleteCharAt(message.length() - 1);
            message.append("入库数量和入备品数至少需要一项大于0");
            return new Result(670, message.toString(), null);
        }

        EntryWarehouseEntity en = new EntryWarehouseEntity();
        BeanUtils.copyProperties(entryAllVO.getEntryWarehouseVO(), en);
        en.setEntryId(entryId);
        en.setModifyTime(new Timestamp(System.currentTimeMillis()));
        en.setModifyUser(userId);
        List<EntryWarehouseProductEntity> prEnList = new ArrayList<>();
        for (EntryWarehouseProductVO prvo : entryAllVO.getEntryWarehouseProductVO()) {
            EntryWarehouseProductEntity ele = new EntryWarehouseProductEntity();
            BeanUtils.copyProperties(prvo, ele);
            prEnList.add(ele);
        }
        try {
            return entryWarehouseService.updateInfoById(en, prEnList);
        } catch (Exception e) {
            return Result.success("系统异常，采购入库单修改失败");
        }
    }

    @RequestMapping(value = "/entry/delete", method = RequestMethod.PUT)
    @ApiOperation(value = "删除入库信息", notes = "删除入库信息")
    @ResponseBody
    public Result deleteInfo(@RequestParam(value = "entryId", required = true) String entryId, @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {
        entryWarehouseService.updateStatusById(entryId, userId);
        return Result.success("入库单信息删除成功");
    }

    @RequestMapping(value = "/entry/infobyid", method = RequestMethod.GET)
    @ApiOperation(value = "通过入库单查询入库单信息", notes = "通过入库单查询入库单信息（用于编辑）")
    @ResponseBody
    public Result selectInfo(@RequestParam(value = "entryId", required = true) String entryId) {
        return Result.success(200, "成功", entryWarehouseService.findInfoById(entryId));
    }

    @ApiOperation(value = "删除采购入库单", notes = "批量删除采购入库单")
    @RequestMapping(value = "/entry/info/delete", method = RequestMethod.PUT)
    @ResponseBody
    public Result delete(@Valid @RequestBody ReturnEntryPara returnEntryArray, BindingResult bindingResult, @RequestHeader("X-AUTH-ID") String userId) {
        if (bindingResult.hasErrors()) {
            return new Result(9999, bindingResult.getFieldError().getDefaultMessage(), null);
        }
        try {
            entryWarehouseService.delete(returnEntryArray, userId);
            return Result.success("删除采购入库单成功");
        } catch (Exception e) {
            e.getMessage();
            return new Result(9999, "删除采购入库单异常", null);
        }
    }

    /**
     * 导出报表
     *
     * @return
     */
    @RequestMapping(value = "/entry/export", method = RequestMethod.GET)
    @ApiOperation(value = "导出采购入库单信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", defaultValue = "1", value = "页码", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "pageSize", defaultValue = "10", value = "每页条数", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "entryId", value = "采购入库单号", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "entryTimeBegin", value = "入库开始日期", paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "entryTimeEnd", value = "入库结束日期", paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "supplierDeliveryNum", value = "供应商送货单号", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "inspectionId", value = "验货申请/提货单号", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "entryUser", value = "入库人", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "warehouseId", value = "入库仓库", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "orderId", value = "采购订单", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "skuId", value = "sku", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "productName", value = "产品中文名", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "supplierName", value = "供应商名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "ids", value = " ID数组集合", paramType = "query", dataType = "String[]")
    })
    @ResponseBody
    public void findInfoAll(HttpServletResponse response, @RequestParam(value = "pageNum", required = false) String pageNum,
                            @RequestParam(value = "pageSize", required = false) String pageSize,
                            @RequestParam(value = "entryId", required = false) String entryId,
                            @RequestParam(value = "entryTimeBegin", required = false) Date entryTimeBegin,
                            @RequestParam(value = "entryTimeEnd", required = false) Date entryTimeEnd,
                            @RequestParam(value = "supplierDeliveryNum", required = false) String supplierDeliveryNum,
                            @RequestParam(value = "inspectionId", required = false) String inspectionId,
                            @RequestParam(value = "entryUser", required = false) String entryUser,
                            @RequestParam(value = "warehouseId", required = false) String warehouseId,
                            @RequestParam(value = "orderId", required = false) String orderId,
                            @RequestParam(value = "skuId", required = false) String skuId,
                            @RequestParam(value = "productName", required = false) String productName,
                            @RequestParam(value = "supplierName", required = false) String supplierName,
                            @RequestParam(value = "ids", required = false) String[] ids) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        LinkedHashMap map = new LinkedHashMap();
       /* if (ids == null) {
            if(pageSize!=null&&pageNum!=null){
                int size = Integer.valueOf(pageSize);
                int num = Integer.valueOf(pageNum);
                map = entryWarehouseService.infoListNew(num, size, entryId, entryTimeBegin, entryTimeEnd, supplierDeliveryNum, inspectionId, entryUser, warehouseId, supplierName,orderId,skuId,productName);
            }
            else {
                map = entryWarehouseService.infoListNew(entryId, entryTimeBegin, entryTimeEnd, supplierDeliveryNum, inspectionId, entryUser, warehouseId, supplierName,orderId,skuId,productName);
            }
        } else {
            map = entryWarehouseService.findInfoIds(ids);
        }*/
        List<EntryWarehouseExportVo> entryWarehouseExportVoList = entryWarehouseService.infoListNewExport(entryId, entryTimeBegin, entryTimeEnd, supplierDeliveryNum, inspectionId, entryUser, warehouseId, supplierName, orderId, skuId, productName);
        List<EntryWarehouseExportVo> list = toVoInfo(entryWarehouseExportVoList);
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("采购入库单信息表");
        for (int i = 0; i <= 24; i++) {
            sheet.setColumnWidth(i, 20 * 256);
        }

        String fileName = "entryInfoTable" + ".xls";//设置要导出的文件的名字
        //新增数据行，并且设置单元格数据

        int rowNum = 1;

        String[] headers = {"采购入库单号", "入库日期", "入库人", "入库仓库", "到货通知单号", "供应商名称", "供应商送货单号", "采购订单号", "备注", "库存sku", "产品中文名", "入库数量", "入备品数", "批次"
                , "不含税单价", "不含税金额", "税率(%)", "含税单价", "含税金额", "需要包材", "包材仓库", "包材sku", "包材名称", "每产品数量", "消耗数量"};
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
                HSSFCellStyle cellStyle = workbook.createCellStyle();
                cellStyle.setWrapText(true);
                row1.createCell(0).setCellValue(list.get(i).getEntryId());
                row1.createCell(1).setCellValue(list.get(i).getEntryTime());
                row1.createCell(2).setCellValue(list.get(i).getEmployeeName());
                row1.createCell(3).setCellValue(list.get(i).getWarehouseName());
                row1.createCell(4).setCellValue(list.get(i).getInspectionId());
                row1.createCell(5).setCellValue(list.get(i).getSupplierName());
                row1.createCell(6).setCellValue(list.get(i).getSupplierDeliveryNum());
                row1.createCell(7).setCellValue(list.get(i).getOrderId());
                row1.createCell(8).setCellValue(list.get(i).getRemark());
                row1.createCell(9).setCellValue(list.get(i).getSkuId());
                row1.createCell(10).setCellValue(list.get(i).getSkuName());
                row1.createCell(11).setCellValue(list.get(i).getEntryNum());
                row1.createCell(12).setCellValue(list.get(i).getEntryFrets());
                row1.createCell(13).setCellValue(list.get(i).getBatch());
                row1.createCell(14).setCellValue(list.get(i).getUnitPriceWithoutTax());
                row1.createCell(15).setCellValue(list.get(i).getAmountWithoutTax());
                row1.createCell(16).setCellValue(list.get(i).getTaxRate());
                row1.createCell(17).setCellValue(list.get(i).getUnitPriceWithTax());
                row1.createCell(18).setCellValue(list.get(i).getAmountWithTax());
                row1.createCell(19).setCellValue(list.get(i).getIsNeedPacking());
                Cell cell20 = row1.createCell(20);
                CellStyle cellStyle2 = workbook.createCellStyle();
//                cellStyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//                cellStyle2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                cell20.setCellStyle(cellStyle2);
                cell20.setCellValue(list.get(i).getPackWarehouseName());
                if (list.get(i).getIsNeedPacking() != null && list.get(i).getIsNeedPacking().equals("Y")) {
                    List<ProductMachineInfoEntity> productMachineInfoEntityList = list.get(i).getProductMachineInfoEntityList();
                    StringBuilder stringBuilderSku = new StringBuilder();
                    StringBuilder stringBuilderSkuName = new StringBuilder();
                    StringBuilder stringBuilderNum = new StringBuilder();
                    StringBuilder stringBuilderUseNum = new StringBuilder();
                    for (ProductMachineInfoEntity productMachineInfoEntity : productMachineInfoEntityList) {
                        int size = 1;
                        if (productMachineInfoEntityList.size() <= 1) {
                            stringBuilderSku.append(productMachineInfoEntity.getDependencySkuId());
                            stringBuilderSkuName.append(productMachineInfoEntity.getSkuNameZh());
                            stringBuilderNum.append(productMachineInfoEntity.getQuantity());
                            stringBuilderUseNum.append(productMachineInfoEntity.getQuantity() * (list.get(i).getEntryNum() + list.get(i).getEntryFrets()));
                        } else {
                            if (size == productMachineInfoEntityList.size()) {
                                stringBuilderSku.append(productMachineInfoEntity.getDependencySkuId());
                                stringBuilderSkuName.append(productMachineInfoEntity.getSkuNameZh());
                                stringBuilderNum.append(productMachineInfoEntity.getQuantity());
                                stringBuilderUseNum.append(productMachineInfoEntity.getQuantity() * (list.get(i).getEntryNum() + list.get(i).getEntryFrets()));
                            }
                            stringBuilderSku.append(productMachineInfoEntity.getDependencySkuId() + "\r\n");
                            stringBuilderSkuName.append(productMachineInfoEntity.getSkuNameZh() + "\r\n");
                            stringBuilderNum.append(productMachineInfoEntity.getQuantity() + "\r\n");
                            stringBuilderUseNum.append(productMachineInfoEntity.getQuantity() * (list.get(i).getEntryNum() + list.get(i).getEntryFrets()) + "\r\n");
                        }
                        size++;
                    }
                    Cell cell21 = row1.createCell(21);
                    cell21.setCellStyle(cellStyle);
                    cell21.setCellValue(stringBuilderSku.toString());
                    Cell cell22 = row1.createCell(22);
                    cell22.setCellStyle(cellStyle);
                    cell22.setCellValue(stringBuilderSkuName.toString());
                    Cell cell23 = row1.createCell(23);
                    cell23.setCellStyle(cellStyle);
                    cell23.setCellValue(stringBuilderNum.toString());
                    Cell cell24 = row1.createCell(24);
                    cell24.setCellStyle(cellStyle);
                    cell24.setCellValue(stringBuilderUseNum.toString());
                }
                rowNum++;
            }
        } else {
            HSSFRow row1 = sheet.createRow(0);
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());

    }

    @RequestMapping(value = "/entry/entryIds/{inspectionId}", method = RequestMethod.GET)
    @ApiOperation(value = "根据到货通知到查询所有的采购入库单号")
    @ResponseBody
    public Result findAllEntryIdByInspectionId(@PathVariable("inspectionId") String inspectionId) {
        List<String> entryIds = null;
        //xxx
        try {
            entryIds = entryWarehouseService.findAllEntryIdByInspectionId(inspectionId);
            return Result.success(entryIds);
        } catch (Exception e) {
            return Result.failure(390, e.getMessage(), "");
        }
    }

    @RequestMapping(value = "/entry/entryProductInfo/{entryId}/{skuId}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("根据入库id和skuId查询采购入库信息")
    public Result getEntryProductByEntryIdAndSkuId(@PathVariable("entryId") String entryId,
                                                   @PathVariable("skuId") String skuId) {
        try {
            EntryWarehouseProductEntity entryWarehouseProductEntity = entryWarehouseService.getEntryProductByEntryIdAndSkuId(entryId, skuId);
            return Result.success(entryWarehouseProductEntity);
        } catch (Exception e) {
            return Result.failure(390, e.getMessage(), "");
        }

    }

    @RequestMapping(value = "/entry/entryProductInfo/batch", method = RequestMethod.GET)
    @ResponseBody
    public Result getEntryProductByIds(@RequestParam("ids") List<String> ids) {
        try {
            List<EntryWarehouseProductEntity> list = entryWarehouseService.getEntryProductByEntryIds(ids);
            return Result.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(390, e.getMessage(), "");
        }
    }

    @RequestMapping(value = "/entry/infoNew", method = RequestMethod.GET)
    @ApiOperation(value = "新查询采购入库单信息", notes = "采购入库单信息列表", response = EntryWarehouseVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", defaultValue = "1", value = "页码", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "pageSize", defaultValue = "10", value = "每页条数", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "entryId", value = "采购入库单号", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "entryTimeBegin", value = "入库开始日期", paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "entryTimeEnd", value = "入库结束日期", paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "supplierDeliveryNum", value = "供应商送货单号", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "inspectionId", value = "验货申请/提货单号", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "entryUser", value = "入库人", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "warehouseId", value = "入库仓库", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "orderId", value = "采购订单", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "skuId", value = "sku", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "productName", value = "产品中文名", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "supplierName", value = "供应商", paramType = "query", dataType = "String")
    })
    @ResponseBody
    public Result findInfoAllNew(@RequestParam(value = "pageNum", required = false) String pageNum,
                                 @RequestParam(value = "pageSize", required = false) String pageSize,
                                 @RequestParam(value = "entryId", required = false) String entryId,
                                 @RequestParam(value = "entryTimeBegin", required = false) Date entryTimeBegin,
                                 @RequestParam(value = "entryTimeEnd", required = false) Date entryTimeEnd,
                                 @RequestParam(value = "supplierDeliveryNum", required = false) String supplierDeliveryNum,
                                 @RequestParam(value = "inspectionId", required = false) String inspectionId,
                                 @RequestParam(value = "entryUser", required = false) String entryUser,
                                 @RequestParam(value = "warehouseId", required = false) String warehouseId,
                                 @RequestParam(value = "orderId", required = false) String orderId,
                                 @RequestParam(value = "skuId", required = false) String skuId,
                                 @RequestParam(value = "productName", required = false) String productName,
                                 @RequestParam(value = "supplierName", required = false) String supplierName) {
        try {
            int size = Integer.valueOf(pageSize);
            int num = Integer.valueOf(pageNum);
            LinkedHashMap result = entryWarehouseService.infoListNew(num, size, entryId, entryTimeBegin, entryTimeEnd, supplierDeliveryNum, inspectionId, entryUser, warehouseId, supplierName, orderId, skuId, productName);
            return Result.success(200, "分页查询成功", result);
        } catch (Exception e) {
            return Result.success(200, "不分页查询成功", entryWarehouseService.infoListNew(entryId, entryTimeBegin, entryTimeEnd, supplierDeliveryNum, inspectionId, entryUser, warehouseId, supplierName, orderId, skuId, productName));
        }
    }

    private List<EntryWarehouseExportVo> toVoInfo(List<EntryWarehouseExportVo> list) {
        List supplierIds = new ArrayList();
        List employeeIds = new ArrayList();
        List warehouseIds = new ArrayList();
        Map<String, String> supplierNameMap = new HashMap();
        Map<String, String> employeeNameMap = new HashMap();
        Map<String, String> warehouseNameMap = new HashMap();
        for (EntryWarehouseExportVo vo : list) {
            supplierIds.add(vo.getSupplierCd());
            supplierIds.add(vo.getPackWarehouseId());
            employeeIds.add(vo.getEntryUser());
            warehouseIds.add(vo.getWarehouseId());
            warehouseIds.add(vo.getPackWarehouseId());
        }
        try {
            supplierNameMap = supplierService.getSupplierNameMap(supplierIds);
            employeeNameMap = purchaseEmployeeService.getEmployeeNameMap(employeeIds);
            warehouseNameMap = warehouseService.getWarehouseNameMap(warehouseIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (EntryWarehouseExportVo vo : list) {
            vo.setSupplierName(supplierNameMap.get(vo.getSupplierCd()));
            vo.setEmployeeName(employeeNameMap.get(vo.getEntryUser()));
            vo.setWarehouseName(warehouseNameMap.get(vo.getWarehouseId()));
            vo.setPackWarehouseName(supplierNameMap.get(vo.getPackWarehouseId()));
            vo.setPackWarehouseName(warehouseNameMap.get(vo.getPackWarehouseId()));
        }
        return list;
    }
}


