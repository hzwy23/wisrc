package com.wisrc.purchase.webapp.controller;

import com.wisrc.purchase.webapp.entity.*;
import com.wisrc.purchase.webapp.service.ObjectStorageService;
import com.wisrc.purchase.webapp.service.OrderBasisInfoAttrService;
import com.wisrc.purchase.webapp.service.PurchcaseOrderBasisInfoService;
import com.wisrc.purchase.webapp.utils.Result;
import com.wisrc.purchase.webapp.utils.Time;
import com.wisrc.purchase.webapp.vo.*;
import com.wisrc.purchase.webapp.vo.swagger.AddBasisInfoModel;
import com.wisrc.purchase.webapp.vo.swagger.OrderBasisInfoModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.wisrc.purchase.webapp.utils.UploadFile.checkFile;

@Controller
@Api(tags = "采购订单")
@RequestMapping(value = "/purchase")
public class OrderBasisInfoController {
    private final Logger logger = LoggerFactory.getLogger(OrderBasisInfoController.class);
    @Autowired
    PurchcaseOrderBasisInfoService purchcaseOrderBasisInfoService;
    @Autowired
    ObjectStorageService objectStorageService;
    @Autowired
    OrderBasisInfoAttrService orderBasisInfoAttrService;
    @Autowired
    RedisTemplate redisTemplate;

    @RequestMapping(value = "/order/info", method = RequestMethod.GET)
    @ApiOperation(value = "查询采购订单信息", notes = "采购订单信息列表", response = OrderBasisInfoModel.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "orderId", value = "采购订单号", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "employeeId", value = "采购员", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "deliveryTypeCd", defaultValue = "0", value = "交货状态(0-全选（默认），1-待交货，2-部分交货，3-完成)", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "keyword", value = "关键词(供应商名称)", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "tiicketOpenCd", defaultValue = "0", value = "开票（0-全选（默认），1-不开票，2-开票）", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "customsTypeCd", defaultValue = "0", value = "报关（0-全选（默认），1-我司报关，2-工厂报关，3-不报关）", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "billDateBegin", value = "订单日期开始", paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "billDateEnd", value = "订单日期结束", paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "deliveryTimeBegin", value = "交货日期开始", paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "deliveryTimeEnd", value = "交货日期结束", paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "supplierId", value = "供应商ID", paramType = "query", dataType = "String")
    })
    @ResponseBody
    public Result findBasisInfo(@RequestParam(value = "pageNum", required = false) String pageNum,
                                @RequestParam(value = "pageSize", required = false) String pageSize,
                                @RequestParam(value = "orderId", required = false) String orderId,
                                @RequestParam(value = "employeeId", required = false) String employeeId,
                                @RequestParam(value = "deliveryTypeCd", required = false, defaultValue = "-1") int deliveryTypeCd,
                                @RequestParam(value = "keyword", required = false) String keyword,
                                @RequestParam(value = "tiicketOpenCd", required = false, defaultValue = "-1") int tiicketOpenCd,
                                @RequestParam(value = "customsTypeCd", required = false, defaultValue = "-1") int customsTypeCd,
                                @RequestParam(value = "billDateBegin", required = false) Date billDateBegin,
                                @RequestParam(value = "billDateEnd", required = false) Date billDateEnd,
                                @RequestParam(value = "deliveryTimeBegin", required = false) Date deliveryTimeBegin,
                                @RequestParam(value = "deliveryTimeEnd", required = false) Date deliveryTimeEnd,
                                @RequestParam(value = "supplierId", required = false) String supplierId) {
        try {
            int size = Integer.valueOf(pageSize);
            int num = Integer.valueOf(pageNum);
            if (deliveryTypeCd == -1) {
                deliveryTypeCd = 0;
            }
            if (tiicketOpenCd == -1) {
                tiicketOpenCd = 0;
            }
            if (customsTypeCd == -1) {
                customsTypeCd = 0;
            }
            LinkedHashMap result = purchcaseOrderBasisInfoService.findBasisInfo(num, size, orderId, employeeId, deliveryTypeCd, keyword, tiicketOpenCd, customsTypeCd, billDateBegin, billDateEnd, deliveryTimeBegin, deliveryTimeEnd, supplierId);
            return Result.success(200, "分页查询成功", result);
        } catch (Exception e) {
            if (deliveryTypeCd == -1) {
                deliveryTypeCd = 0;
            }
            if (tiicketOpenCd == -1) {
                tiicketOpenCd = 0;
            }
            if (customsTypeCd == -1) {
                customsTypeCd = 0;
            }
            return Result.success(200, "不分页条件查询成功", purchcaseOrderBasisInfoService.findBasisInfo(orderId, employeeId, deliveryTypeCd, keyword, tiicketOpenCd, customsTypeCd, billDateBegin, billDateEnd, deliveryTimeBegin, deliveryTimeEnd, supplierId));
        }

    }

    @RequestMapping(value = "/order/neet/one", method = RequestMethod.GET)
    @ApiOperation(value = "根据条件模糊查询订单信息(特定接口调用（1）)", notes = "特定接口调用（1）", response = OrderNeetVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "orderId", value = "采购订单号", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "supplierName", value = "供应商名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "skuId", value = "skuId", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "productNameCN", value = "产品中文名", paramType = "query", dataType = "String")
    })
    @ResponseBody
    public Result findBasisInfo(@RequestParam(value = "pageNum", required = false) String pageNum,
                                @RequestParam(value = "pageSize", required = false) String pageSize,
                                @RequestParam(value = "orderId", required = false) String orderId,
                                @RequestParam(value = "supplierName", required = false) String supplierName,
                                @RequestParam(value = "skuId", required = false) String skuId,
                                @RequestParam(value = "productNameCN", required = false) String productNameCN) {
        try {
            int size = Integer.valueOf(pageSize);
            int num = Integer.valueOf(pageNum);
            LinkedHashMap result = purchcaseOrderBasisInfoService.findBasisNeet(num, size, orderId, supplierName, skuId, productNameCN);
            return Result.success(200, "分页查询成功", result);
        } catch (Exception e) {
            LinkedHashMap result = purchcaseOrderBasisInfoService.findBasisNeet(orderId, supplierName, skuId, productNameCN);
            return Result.success(200, "不分页查询成功", result);
        }
    }

    @RequestMapping(value = "/order/neet/two", method = RequestMethod.GET)
    @ApiOperation(value = "根据条件模糊查询订单信息(特定接口调用（2）)", notes = "特定接口调用（2）", response = OrderNeetVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "orderId", value = "采购订单号", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "skuId", value = "skuId", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "keyWords", value = "关键字（供应商名称/产品中文名关键字\n" +
                    "(这里一个关键词，需要匹配两项。供应商名称/产品中文名关键字\n" +
                    "关系为   “或“   (“or”))）", paramType = "query", dataType = "String")
    })
    @ResponseBody
    public Result findBasisInfo(@RequestParam(value = "pageNum", required = false) String pageNum,
                                @RequestParam(value = "pageSize", required = false) String pageSize,
                                @RequestParam(value = "orderId", required = false) String orderId,
                                @RequestParam(value = "keyWords", required = false) String keyWords,
                                @RequestParam(value = "skuId", required = false) String skuId) {
        try {
            int size = Integer.valueOf(pageSize);
            int num = Integer.valueOf(pageNum);
            LinkedHashMap result = purchcaseOrderBasisInfoService.findBasisNeet(num, size, keyWords, skuId, orderId);
            return Result.success(200, "分页查询成功", result);
        } catch (Exception e) {
            LinkedHashMap result = purchcaseOrderBasisInfoService.findBasisNeet(keyWords, skuId, orderId);
            return Result.success(200, "不分页查询成功", result);
        }
    }

    @RequestMapping(value = "/order/infoall", method = RequestMethod.GET)
    @ApiOperation(value = "查询所有采购订单id列表", notes = "所有采购订单id列表", response = OrderBasisInfoModel.class)
    @ResponseBody
    public Result findAll() {
        List<String> list = purchcaseOrderBasisInfoService.findAll();
        return Result.success(200, "成功", list);
    }

    @RequestMapping(value = "/order/add", method = RequestMethod.POST)
    @ApiOperation(value = "采购订单新增", notes = "采购订单新增（根据业务的需求，新增按钮点击后保存所有板块信息，包括交货日期与数量List集合，集装箱信息）", response = AddBasisInfoModel.class)
    @ResponseBody
    public Result addBasisInfo(@RequestBody OrderBasisInfoAllVO orderAllVO,
                               BindingResult result,
                               @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {
        if (result.hasErrors()) {
            return Result.failure(390, "订单信息保存失败", result.getAllErrors().get(0).getDefaultMessage());
        }
        OrderBasisInfoVO orderVO = orderAllVO.getOrderVO();
        List<AddDetailsProdictAllVO> eleAllVOList = orderAllVO.getEleVOList();
        int num = 0;
        if (orderVO.getOrderId() != null || orderVO.getOrderId() != "") {
            num = purchcaseOrderBasisInfoService.findNum(orderVO.getOrderId());
        }
        if (num > 0) {
            return Result.success("订单信息保存失败，订单ID已存在！");
        }
        //如果传入的订单号是空的话按照业务规则自动生成
        if (orderVO.getOrderId() != null || orderVO.getOrderId() != "") {
            orderVO.setOrderId(toId());
        }
        orderVO.setCreateUser(userId);
        orderVO.setCreateTime(Time.getCurrentTimestamp());
        try {
            Result result1 = purchcaseOrderBasisInfoService.addOrderInfo(eleAllVOList, orderVO);
            return result1;
        } catch (Exception e) {
            return Result.failure(390, e.getMessage(), "");
        }

    }


    @RequestMapping(value = "/order/update", method = RequestMethod.PUT)
    @ApiOperation(value = "采购订单修改", notes = "采购订单修改（根据业务的需求，编辑后保存后修改所有板块信息，包括新增的产品相关信息，其中交货日期与数量List集合，集装箱信息也包含在内）", response = AddBasisInfoModel.class)
    @ResponseBody
    public Result updateBasisInfo(@RequestBody OrderBasisInfoAllVO eleAllVOList, BindingResult result, @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {
        if (result.hasErrors()) {
            return Result.failure(390, "订单信息修改失败", result.getAllErrors().get(0).getDefaultMessage());
            //       return Result.failure(390,result.getAllErrors().get(0).toString(), result.getAllErrors());
        }
        OrderBasisInfoVO orderVO = eleAllVOList.getOrderVO();
        List<AddDetailsProdictAllVO> eleVOList = eleAllVOList.getEleVOList();
        orderVO.setCreateUser(userId);
        orderVO.setCreateTime(new Timestamp(System.currentTimeMillis()));
        try {
            purchcaseOrderBasisInfoService.updateOrder(eleVOList, orderVO);
            return Result.success("订单信息修改成功");
        } catch (Exception e) {
            return Result.failure(390, e.getMessage(), e.getMessage());
        }
    }

    @RequestMapping(value = "/order/delete", method = RequestMethod.PUT)
    @ApiOperation(value = "删除订单信息(ID)", notes = "删除订单信息")
    @ResponseBody
    public Result deleteInfo(@RequestParam(value = "orderId", required = true) String orderId, @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {
        try {
            return purchcaseOrderBasisInfoService.updateStatus(orderId);
        } catch (Exception e) {
            return Result.success(390, "订单信息删除异常", "");
        }
    }


    @RequestMapping(value = "/order/delete/list", method = RequestMethod.PUT)
    @ApiOperation(value = "删除订单信息(数组)", notes = "删除订单信息(数组)(数组类型String[]例如： 1,2,3,4)", consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public Result deleteInfoByList(String[] idList, @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {
        try {
            purchcaseOrderBasisInfoService.updateStatus(idList);
            return Result.success("订单信息删除成功");
        } catch (Exception e) {
            return Result.success(390, "订单信息删除异常", "");
        }
    }

    @RequestMapping(value = "/order/delete/product", method = RequestMethod.GET)
    @ApiOperation(value = "标记中止产品", notes = "标记中止产品")
    @ResponseBody
    public Result deleteProductStatus(@RequestParam(value = "id", required = true) String id, @RequestHeader(value = "X-AUTH-ID", required = false) @ApiIgnore String userId) {
        try {
            purchcaseOrderBasisInfoService.updateProductStatus(id, 1);
            return Result.success("标记中止产品成功");
        } catch (Exception e) {
            return Result.success(390, "标记中止产品异常", "");
        }
    }

    @RequestMapping(value = "/order/supplier/skuids", method = RequestMethod.GET)
    @ApiOperation(value = "根据库存sku批量查询最近一次采购订单的供应商(数组)", notes = "根据库存sku批量查询最近一次采购订单的供应商(数组)", consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public Result findSupplier(@RequestParam("skuIds") String[] skuIds) {
        try {
            Map supplier = purchcaseOrderBasisInfoService.findSupplier(skuIds);
            return Result.success(200, "查询成功", supplier);
        } catch (Exception e) {
            return Result.failure(390, "查询失败", "");
        }
    }

    @RequestMapping(value = "/order/infobyid", method = RequestMethod.GET)
    @ApiOperation(value = "通过订单号查询订单信息", notes = "通过订单号查询订单信息（用于编辑）", response = OrderBasisInfoAllVO.class)
    @ResponseBody
    public Result selectInfo(@RequestParam(value = "orderId", required = true) String orderId) {

        OrderBasisInfoAllVO orderBasisInfoAllVO = purchcaseOrderBasisInfoService.findInfoAllVoById(orderId);
        return Result.success(200, "成功", orderBasisInfoAllVO);
    }

    @RequestMapping(value = "/order/sku", method = RequestMethod.GET)
    @ApiOperation(value = "采购订单下SKU编码")
    @ResponseBody
    public Result skuById(@RequestParam(value = "orderId", required = true) String orderId) {
        return Result.success(purchcaseOrderBasisInfoService.skuById(orderId));
    }


    @RequestMapping(value = "/order/skuinfo", method = RequestMethod.GET)
    @ApiOperation(value = "采购订单下SKU列表信息", response = AddDetailsProdictAllVO.class)
    @ResponseBody
    public Result skuSkuInfoById(@RequestParam(value = "orderId", required = true) String orderId) {
        return Result.success(purchcaseOrderBasisInfoService.findDetailsList(orderId));
    }


//    @ApiOperation(value = "获取采购订单附件下载路径")
//    @RequestMapping(value = "/download/orderinfo",method = RequestMethod.GET)
//    public Result downloadCustoms(@RequestParam(value = "orderId",required = true) String orderId,@RequestParam(value = "obsName",required = true) String obsName) {
//        //String obsName = "smart-do-product-temp";
//        try {
//            CustomsInfoEntity entity = customsInfoService.get(waybillId);
//            String customsInfoDoc = entity.getCustomsInfoDoc();
//            String declarationNumberDoc = entity.getDeclarationNumberDoc();
//            String letterReleaseDoc = entity.getLetterReleaseDoc();
//            objectStorageService.downloadFile(obsName,customsInfoDoc);
//            Map<String,String> map = new HashMap<String,String>();
//            map.put("customsInfoDoc",customsInfoDoc);
//            map.put("declarationNumberDoc",declarationNumberDoc);
//            map.put("letterReleaseDoc",letterReleaseDoc);
//            return  Result.success("开发中");
//        } catch (Exception e) {
//            return  Result.failure(500,e.getMessage(),"");
//        }
//
//    }

    @ApiOperation(value = "采购订单附件上传")
    @RequestMapping(value = "/upload/order", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public Result uploadOrder(@RequestParam(name = "file", required = false) MultipartFile orderInfoDocFile, @RequestParam(value = "obsName", required = false) String obsName, @RequestParam(value = "orderId", required = false) String orderId) throws Exception {
        StringBuilder sb = new StringBuilder();
        OrderAttachmentInfoEntity ele = new OrderAttachmentInfoEntity();
        try {
            if (orderInfoDocFile != null) {
                if (checkFile(orderInfoDocFile)) {
                    Result result = objectStorageService.uploadFile(orderInfoDocFile, obsName);
                    Map map = (Map) result.getData();
                    String url = (String) map.get("uuid");
                    ele.setOrderId(orderId);
                    ele.setUuid(UUID.randomUUID().toString());
                    ele.setAttachmentUrl(url);
                    purchcaseOrderBasisInfoService.addOrderAttachment(ele);
                    sb.append("订单附件上传成功");
                } else {
                    sb.append("订单附件上传失败，文件类型不对或文件大于10M，");
                }
            }
            return Result.success(200, sb.toString(), ele);
        } catch (Exception e) {
            return Result.failure(500, e.getMessage(), ele);
        }
    }

    /**
     * 导出报表
     *
     * @return
     */
    @RequestMapping(value = "/order/export", method = RequestMethod.GET)
    @ApiOperation(value = "导出采购订单信息")
    @ResponseBody
    public void exportInfo(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "pageNum", required = false, defaultValue = "1") String pageNum,
                           @RequestParam(value = "pageSize", required = false, defaultValue = "10") String pageSize,
                           @RequestParam(value = "orderId", required = false) String orderId,
                           @RequestParam(value = "employeeId", required = false) String employeeId,
                           @RequestParam(value = "deliveryTypeCd", required = true) int deliveryTypeCd,
                           @RequestParam(value = "keyWork", required = false) String keyWork,
                           @RequestParam(value = "tiicketOpenCd", required = true) int tiicketOpenCd,
                           @RequestParam(value = "customsTypeCd", required = true) int customsTypeCd,
                           @RequestParam(value = "billDateBegin", required = false) Date billDateBegin,
                           @RequestParam(value = "billDateEnd", required = false) Date billDateEnd,
                           @RequestParam(value = "deliveryTimeBegin", required = false) Date deliveryTimeBegin,
                           @RequestParam(value = "deliveryTimeEnd", required = false) Date deliveryTimeEnd,
                           @RequestParam(value = "ids", required = false) String[] ids) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int size = 0;
        int num = 0;
        try {
            size = Integer.valueOf(pageSize);
            num = Integer.valueOf(pageNum);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        LinkedHashMap map = new LinkedHashMap();
        if (ids == null) {
            map = purchcaseOrderBasisInfoService.findBasisInfo(num, size, orderId, employeeId, deliveryTypeCd, keyWork, tiicketOpenCd, customsTypeCd, billDateBegin, billDateEnd, deliveryTimeBegin, deliveryTimeEnd, null);
        } else {
            map = purchcaseOrderBasisInfoService.findBasisByIds(ids);
        }
        List<OrderBasisInfoEntity> list = (List<OrderBasisInfoEntity>) map.get("orderBasisInfoList");
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("采购订单信息表");
        List<CustomsTypeAtrEntity> customsTypeAtrEntities = orderBasisInfoAttrService.cusomsAttr();
        List<DeliveryTypeAttrEntity> deliveryTypeAttrEntities = orderBasisInfoAttrService.deliveryAttr();
        List<TiicketOpenAttrEntity> tiicketOpenAttrEntities = orderBasisInfoAttrService.tiicketAttr();
        Map cusomsAttr = new HashMap();
        Map deliveryAttr = new HashMap();
        Map tiicketAttr = new HashMap();
        for (CustomsTypeAtrEntity e : customsTypeAtrEntities) {
            cusomsAttr.put(e.getCustomsTypeCd(), e.getCustomsTypeDesc());
        }
        for (DeliveryTypeAttrEntity e : deliveryTypeAttrEntities) {
            deliveryAttr.put(e.getDeliveryTypeCd(), e.getDeliveryTypeDesc());
        }
        for (TiicketOpenAttrEntity e : tiicketOpenAttrEntities) {
            tiicketAttr.put(e.getTiicketOpenCd(), e.getTiicketOpenDesc());
        }
        String fileName = "orderInfoTable" + ".xls";//设置要导出的文件的名字
        //新增数据行，并且设置单元格数据

        int rowNum = 1;

        String[] headers = {"采购订单号", "单据日期", "供应商名称", "采购员", "不含税金额", "含税金额", "是否开票", "是否报关", " 付款条款", "交货状态"};
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
                row1.createCell(0).setCellValue(list.get(i).getOrderId());
                row1.createCell(1).setCellValue(sdf.format(list.get(i).getBillDate()));
                row1.createCell(2).setCellValue(list.get(i).getSupplierName());
                row1.createCell(3).setCellValue(list.get(i).getEmployeeName());
                row1.createCell(4).setCellValue(list.get(i).getAmountWithoutTax());
                row1.createCell(5).setCellValue(list.get(i).getAmountWithTax());
                row1.createCell(6).setCellValue((String) tiicketAttr.get(list.get(i).getTiicketOpenCd()));
                row1.createCell(7).setCellValue((String) cusomsAttr.get(list.get(i).getCustomsTypeCd()));
                row1.createCell(8).setCellValue(list.get(i).getPaymentProvision());
                row1.createCell(9).setCellValue((String) deliveryAttr.get(list.get(i).getDeliveryTypeCd()));
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

    /**
     * 生成订单号 PO+年+五位数
     * 例如：PO1800001
     *
     * @return
     */
    private String toId() {
        String OrderId = purchcaseOrderBasisInfoService.findOrderId();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd"); // 时间字符串产生方式
        String uid_pfix = "PO"; // 截取年份
        if (OrderId == null || OrderId == "") {
            OrderId = uid_pfix + format.format(new Date(System.currentTimeMillis())).substring(2, 4) + "00001";
        } else {
            String uid_end = OrderId.substring(2, 9);
            int endNum = Integer.parseInt(uid_end);
            int tmpNum = endNum + 1;
            OrderId = uid_pfix + tmpNum;
        }

        return OrderId;// 当前时间
    }
   /* private String toId() {
        SimpleDateFormat sdf = new SimpleDateFormat("YYMMdd");
        String currDate = sdf.format(System.currentTimeMillis());
        String key=currDate+Properties.purchase_order_add_order;
        int count = 0;
        long maxId=redisTemplate.opsForValue().increment(key,1);
        if (maxId == 1) {
            redisTemplate.expire(key, 1000*60*60*24, TimeUnit.MILLISECONDS);
        }
        if(maxId>999999){
            throw new RuntimeException("订单号已达最大");
        }
        long num=maxId;
        while(num > 0){
            num=num / 10;
            count++;
        }
        if(count==1){
            return "PO" + currDate +"00000"+ maxId;
        }
        if(count==2){
            return "PO" + currDate +"0000"+ maxId;
        }
        if(count==3){
            return "PO" + currDate +"000"+ maxId;
        }
        if(count==4){
            return "PO" + currDate +"00"+ maxId;
        }
        if(count==5){
            return "PO" + currDate +"0"+ maxId;
        }
        return "PO" + currDate + maxId;
    *//*    String OrderId = orderBasisInfoService.findOrderId();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd"); // 时间字符串产生方式
        String uid_pfix = "PO"; // 截取年份
        if (OrderId == null || OrderId == "") {
            OrderId = uid_pfix + format.format(new Date(System.currentTimeMillis())).substring(2, 4) + "00001";
        } else {
            String uid_end = OrderId.substring(2, 9);
            int endNum = Integer.parseInt(uid_end);
            int tmpNum = endNum + 1;
            OrderId = uid_pfix + tmpNum;
        }

        return OrderId;// 当前时间*//*
    }*/

    @ApiOperation(value = "订单备注修改", notes = "订单备注修改)")
    @RequestMapping(value = "/order/remark", method = RequestMethod.PUT)
    @ResponseBody
    public Result updateRemark(@RequestBody RemarkVo remarkVo) {
        try {
            purchcaseOrderBasisInfoService.updateRemark(remarkVo);
            return Result.success("订单备注修改成功");
        } catch (Exception e) {
            return Result.success(390, "订单备注修改异常", "");
        }
    }
}
