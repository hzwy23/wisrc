package com.wisrc.replenishment.webapp.service.impl;

import com.google.gson.Gson;
import com.wisrc.replenishment.webapp.dao.FbaMskuInfoDao;
import com.wisrc.replenishment.webapp.dao.LogisticsBillDao;
import com.wisrc.replenishment.webapp.dao.TransferDao;
import com.wisrc.replenishment.webapp.dto.WaybillClearance.ClearanceExcelDto;
import com.wisrc.replenishment.webapp.dto.WaybillClearance.WayBillExcelDto;
import com.wisrc.replenishment.webapp.entity.*;
import com.wisrc.replenishment.webapp.query.logisticBill.FindDetailQuery;
import com.wisrc.replenishment.webapp.query.logisticBill.HistoryQuery;
import com.wisrc.replenishment.webapp.service.*;
import com.wisrc.replenishment.webapp.utils.Crypto;
import com.wisrc.replenishment.webapp.utils.Result;
import com.wisrc.replenishment.webapp.utils.Time;
import com.wisrc.replenishment.webapp.vo.CustomsClerlanceVo;
import com.wisrc.replenishment.webapp.vo.ProductDetailsEnityVO;
import com.wisrc.replenishment.webapp.vo.fbaMskuInfoPortionVo;
import com.wisrc.replenishment.webapp.vo.transferorder.TransferInfoReturnVo;
import com.wisrc.replenishment.webapp.vo.transferorder.TransferOrderDetailsVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FontFormatting;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class LogisticsBillServiceImpl implements LogisticsBillService {
    @Autowired
    private TransferService transferService;
    @Autowired
    private TransferDao transferDao;
    @Autowired
    private LogisticsBillDao billDao;
    @Autowired
    private ClearanceServcervice clearanceServcer;
    @Autowired
    private ClearanProductService clearanProductService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private WarehouseInfoService warehouseInfoService;
    @Autowired
    private ProductService productService;
    @Autowired
    private FbaMskuInfoDao mskuInfoDao;

    @Override
    @Transactional(transactionManager = "retailReplenishmentTransactionManager")
    public void addBillInfo(LogisticsBillEnity billEnity) {
        List<ProductDetailsEnity> proList = billEnity.getProductDetailList();
        dealProList(proList, billEnity);
        billDao.updateWayBillInfo(1, billEnity.getWaybillId());
    }

    @Override
    public LogisticsBillEnity findBillDetail(CustomsClerlanceVo cusVo) {
        LogisticsBillEnity billEnity = billDao.findBillByWayBiilId(cusVo.getWaybillId());
        if (billEnity == null) {
            return billEnity;
        }
        List<ProductDetailsEnity> proList = new ArrayList<>();
        for (ProductDetailsEnityVO pro : cusVo.getProductList()) {
            ProductDetailsEnity enity = billDao.findDatailByShopIdAndMskuID(pro.getShopId(), pro.getMskuId());
            if (enity != null) {
                proList.add(enity);
            }
        }
        billEnity.setProductDetailList(proList);
        return billEnity;
    }

    @Override
    public void deleteByWayBIllId(String wayBillId) {
        billDao.deletByWayBillId(wayBillId);
    }

    @Override
    public void deleteClearanceProduct(String shopId, String mskuId) {
        billDao.deleteClearanceProduct(shopId, mskuId);
    }

    @Override
    public LogisticsBillEnity findClearanceDetail(String wayBillId) {
        LogisticsBillEnity billEnity = billDao.findBillByWayBiilId(wayBillId);
        if (billEnity == null) {
            billEnity = new LogisticsBillEnity();
            Result companyResult = companyService.getCompanyInfo();
            Map<String, Object> companyMap = (Map<String, Object>) companyResult.getData();
            billEnity.setSellCompanyName((String) companyMap.get("companyNameEn"));
            billEnity.setBuyCompanyName((String) companyMap.get("companyNameHk"));
            Result customResult = companyService.getCompanyCustomsInfo();
            Map<String, Object> customMap = (Map<String, Object>) customResult.getData();
            billEnity.setSellContact((String) customMap.get("contactsEn"));
            billEnity.setVatNo((String) customMap.get("vatCd"));
//          暂时写死收货仓地址
            billEnity.setReceiveAddress("Amazon.com.dedc LLC" + "402 John Dodd Rd" + "Spartanburg, SC" + "US, 29303");
        }
//      用于存储查询出的产品信息数据，避免重复查询
        Map<String, String> pictureMap = new HashMap();
        Map<String, String> clearanceMap = new HashMap();
        Map<String, String> skuNameMap = new HashMap<>();
        Map<String, String> productEnMap = new HashMap();
//      通过运单id查询关联补货单id
        List<String> replenishmentIDs = billDao.findByShipwWaybillId(wayBillId);
        List<ProductDetailsEnity> proList = new ArrayList<>();
        if (replenishmentIDs.size() > 0) {
            for (String repleId : replenishmentIDs) {
                List<ProductDetailsEnity> productDetailsEnitys = billDao.findDatailByRepleId(repleId);
                List<String> proIds = new ArrayList<>();
                List<String> skuIds = new ArrayList<>();
                for (ProductDetailsEnity product : productDetailsEnitys) {
                    if (clearanceMap.get(product.getCommodityId()) == null) {
                        if (!proList.contains(product.getCommodityId())) {
                            proIds.add(product.getCommodityId());
                        }
                    }
                }
                if (proIds.size() > 0) {
                    String[] ids = proIds.toArray(new String[proIds.size()]);
                    Gson gson = new Gson();
                    Result cleaarResult = clearanceServcer.getProduct(ids);
                    if (cleaarResult.getCode() == 200) {
//                  通过商品id查询仓库商品信息（sku编号，商品图片）
                        Map<String, Object> map = (Map) cleaarResult.getData();
                        List objects = (List) map.get("mskuInfoBatch");
                        for (Object product : objects) {
                            Map proMap = (Map) product;
                            String skuId = (String) proMap.get("skuId");
                            skuIds.add(skuId);
                            String id = (String) proMap.get("id");
                            String picture = (String) proMap.get("picture");
                            String name = (String) proMap.get("mskuName");
                            clearanceMap.put(id, skuId);
                            pictureMap.put(id, picture);
                            skuNameMap.put(id, name);
                        }
                    }
                    Map parameproMap = new HashMap();
                    parameproMap.put("skuIdList", skuIds);
//              通过上诉skuId查询商品库存名称
                    Result productResult = clearanProductService.getProductInfo(gson.toJson(parameproMap));
                    if (productResult.getCode() == 200) {
                        List objects = (List) productResult.getData();
                        for (Object product : objects) {
                            Map proMap = (Map) product;
                            Map classify = (Map) proMap.get("classify");
                            Map specificationsInfo = (Map) proMap.get("specificationsInfo");
                            String skuId = (String) specificationsInfo.get("skuId");
                            String classifyNameEn = (String) classify.get("classifyNameEn");
                            productEnMap.put(skuId, classifyNameEn);
                        }
                    }
                }
                for (ProductDetailsEnity product : productDetailsEnitys) {
                    if (billEnity.getSendAddress() == null) {
                        Result warehouseResult = warehouseInfoService.getWarehouseName(product.getWarehouseId());
                        Map wareMap = (Map) warehouseResult.getData();
                        String detailsAddr = "";
                        String cityName = "";
                        String provinceName = "";
                        String zipCode = "";
                        if (wareMap.get("detailsAddr") != null) {
                            detailsAddr = (String) wareMap.get("detailsAddr");
                        }
                        if (wareMap.get("cityName") != null) {
                            cityName = (String) wareMap.get("cityName");
                        }
                        if (wareMap.get("provinceName") != null) {
                            provinceName = (String) wareMap.get("provinceName");
                        }
                        if (wareMap.get("zipCode") != null) {
                            zipCode = (String) wareMap.get("zipCode");
                        }
                        billEnity.setSendAddress(detailsAddr + "," + cityName + "," + provinceName + "," + zipCode);
                    }
                    List<String> fbaReplenishimentIds = billDao.findByShipwWaybillId(wayBillId);
                    //String fbaReplenishimentId = billDao.getFbareplenishimentIdByWayBillId(wayBillId);
                    for (String fbaReplenishimentId : fbaReplenishimentIds) {
                        fbaMskuInfoPortionVo fbaMskuInfoPortionVo = mskuInfoDao.getInfoByFbaIdAndMskuId(fbaReplenishimentId, product.getMskuId());
                        if (fbaMskuInfoPortionVo != null) {
                            if (fbaMskuInfoPortionVo.getClearanceName() != null || fbaMskuInfoPortionVo.getCountryOfOrigin() != null || fbaMskuInfoPortionVo.getPurposeDesc() != null || fbaMskuInfoPortionVo.getTextureOfMateria() != null) {
                                Result prodDetailResult = productService.getProdDetails(clearanceMap.get(product.getCommodityId()));
                                if (prodDetailResult.getCode() == 200) {
                                    HashMap prodDetail = (HashMap) prodDetailResult.getData();
                                    product.setHSId((String) ((Map) prodDetail.get("declareInfo")).get("customsNumber"));
                                }
                                product.setClearanceName(fbaMskuInfoPortionVo.getClearanceName());
                                product.setOriginPlace(fbaMskuInfoPortionVo.getCountryOfOrigin());
                                product.setProperty(fbaMskuInfoPortionVo.getTextureOfMateria());
                                product.setUseWay(fbaMskuInfoPortionVo.getPurposeDesc());
                            }
                        } else {
                            Result prodDetailResult = productService.getProdDetails(clearanceMap.get(product.getCommodityId()));
                            if (prodDetailResult.getCode() == 200) {
                                HashMap prodDetail = (HashMap) prodDetailResult.getData();
                                product.setHSId((String) ((Map) prodDetail.get("declareInfo")).get("customsNumber"));
                                product.setClearanceName((String) ((Map) prodDetail.get("declareInfo")).get("declareNameEn"));
                                product.setOriginPlace((String) ((Map) prodDetail.get("declareInfo")).get("originPlace"));
                                product.setProperty((String) ((Map) prodDetail.get("declareInfo")).get("materials"));
                                product.setUseWay((String) ((Map) prodDetail.get("declareInfo")).get("typicalUse"));
                            }
                        }
                    }
                    ProductDetailsEnity enity = billDao.findDatailByShopIdAndMskuID(product.getShopId(), product.getMskuId());
                    product.setSkuId(clearanceMap.get(product.getCommodityId()));
                    product.setSkuEnName(productEnMap.get(product.getSkuId()));
                    product.setImageUrl(pictureMap.get(product.getCommodityId()));
                    product.setMskuName(skuNameMap.get(product.getCommodityId()));
                    if (enity != null) {
                        //product.setUseWay(enity.getUseWay());
                        // product.setProperty(enity.getProperty());
                        //product.setOriginPlace(enity.getOriginPlace());
                        //product.setClearanceName(enity.getClearanceName());
                        product.setDeclareUnitPrice(enity.getDeclareUnitPrice());
                        product.setClearanceSubtotal(enity.getDeclareUnitPrice() * product.getReplenishmentQuantity());
                    }
                    proList.add(product);
                }
            }
        } else {
            //该交运单对应的是调拨单
            String transferId = transferDao.findTransferIdByWaybillId(wayBillId);
            Result transferInfo = transferService.findById(transferId);
            TransferInfoReturnVo transferInfoReturnVo = (TransferInfoReturnVo) transferInfo.getData();
            Result startWarehouse = warehouseInfoService.getWarehouseName(transferInfoReturnVo.getTransferBasicInfoEntity().getWarehouseStartId());
            Map wareMap = (Map) startWarehouse.getData();
            StringBuilder addr = new StringBuilder();
            if (wareMap.get("detailsAddr") != null) {
                addr.append((String) wareMap.get("detailsAddr") + ",");
            }
            if (wareMap.get("cityName") != null) {
                addr.append((String) wareMap.get("cityName") + ",");
            }
            if (wareMap.get("provinceName") != null) {
                addr.append((String) wareMap.get("provinceName") + ",");
            }
            if (wareMap.get("countryCd") != null) {
                addr.append((String) wareMap.get("countryCd") + ",");
            }
            if (wareMap.get("zipCode") != null) {
                addr.append((String) wareMap.get("zipCode") + ",");
            }
            if (addr.length() > 0) {
                if (StringUtils.isEmpty(billEnity.getSendAddress())) {
                    billEnity.setSendAddress(addr.toString().substring(0, addr.length() - 1));
                }
            } else {
                if (StringUtils.isEmpty(billEnity.getSendAddress())) {
                    billEnity.setSendAddress("");
                }
            }
            Result endWarhouse = warehouseInfoService.getWarehouseName(transferInfoReturnVo.getTransferBasicInfoEntity().getWarehouseEndId());
            addr = new StringBuilder();
            Map wareMap1 = (Map) endWarhouse.getData();
            if (wareMap1.get("detailsAddr") != null) {
                addr.append((String) wareMap1.get("detailsAddr") + ",");
            }
            if (wareMap1.get("cityName") != null) {
                addr.append((String) wareMap1.get("cityName") + ",");
            }
            if (wareMap1.get("provinceName") != null) {
                addr.append((String) wareMap1.get("provinceName") + ",");
            }
            if (wareMap.get("countryCd") != null) {
                addr.append((String) wareMap.get("countryCd") + ",");
            }
            if (wareMap1.get("zipCode") != null) {
                addr.append((String) wareMap1.get("zipCode") + ",");
            }
            if (addr.length() > 0) {
                billEnity.setReceiveAddress(addr.toString().substring(0, addr.length() - 1));
            } else {
                billEnity.setReceiveAddress("");
            }
            for (TransferOrderDetailsVo transferOrderDetailsVo : transferInfoReturnVo.getTransferOrderDetailsVos()) {
                ProductDetailsEnity productDetailsEnity = new ProductDetailsEnity();
                productDetailsEnity.setReplenishmentQuantity(transferOrderDetailsVo.getTransferQuantity());
                productDetailsEnity.setSkuId(transferOrderDetailsVo.getSkuId());
                productDetailsEnity.setSkuName(transferOrderDetailsVo.getSkuName());
                productDetailsEnity.setFnSku(transferOrderDetailsVo.getFnSku());
                productDetailsEnity.setWarehouseId(transferInfoReturnVo.getTransferBasicInfoEntity().getWarehouseStartId());
                productDetailsEnity.setImageUrl(transferOrderDetailsVo.getUrl().size() == 0 ? null : transferOrderDetailsVo.getUrl().get(0));
                Result prodDetails = productService.getProdDetails(transferOrderDetailsVo.getSkuId());
                if (prodDetails.getCode() == 200) {
                    productDetailsEnity.setHSId((String) ((Map) ((Map) prodDetails.getData()).get("declareInfo")).get("customsNumber"));
                }
                TransferClearanceEntity transferClearanceEntity = transferDao.findClearanceByTransferIdAndSkuId(transferId, transferOrderDetailsVo.getSkuId());
                if (transferClearanceEntity != null) {
                    productDetailsEnity.setClearanceName(transferClearanceEntity.getClearanceName());
                    productDetailsEnity.setOriginPlace(transferClearanceEntity.getCountryOfOrigin());
                    productDetailsEnity.setProperty(transferClearanceEntity.getTextureOfMateria());
                    productDetailsEnity.setUseWay(transferClearanceEntity.getPurposeDesc());
                    productDetailsEnity.setDeclareUnitPrice(transferClearanceEntity.getClearanceUnitPrice());
                } else {
                    if (prodDetails.getCode() == 200) {
                        Map prodDetail = (Map) prodDetails.getData();
                        productDetailsEnity.setClearanceName((String) ((Map) prodDetail.get("declareInfo")).get("declareNameEn"));
                        productDetailsEnity.setOriginPlace((String) ((Map) prodDetail.get("declareInfo")).get("originPlace"));
                        productDetailsEnity.setProperty((String) ((Map) prodDetail.get("declareInfo")).get("materials"));
                        productDetailsEnity.setUseWay((String) ((Map) prodDetail.get("declareInfo")).get("typicalUse"));
                    }
                }
                proList.add(productDetailsEnity);
            }
        }
        billEnity.setProductDetailList(proList);
        return billEnity;
    }

    @Override
    public void waybillExcel(String wayBillId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        WayBillExcelDto excelData = new WayBillExcelDto();

        WaybillExcelEntity waybillExcel = billDao.getWaybillExcel(wayBillId);
        List<ClearanceExcelEntity> clearanceExcelList = billDao.getClearanceExcel(wayBillId);
        BeanUtils.copyProperties(waybillExcel, excelData);
        excelData.setDeclareAmount(String.valueOf(waybillExcel.getDeclareAmount()));
        if (clearanceExcelList.size() > 0) {

            if (waybillExcel == null) {
                throw new Exception("缺少清关发票信息");
            }

            if (waybillExcel.getCreateTime() != null) {
                excelData.setCreateTime(sdf.format(waybillExcel.getCreateTime()));
            }
            excelData.setDeclareAmount(decimalFormatDealer(waybillExcel.getDeclareAmount()));
            if (excelData.getDeclareAmount() != null) {
                excelData.setDeclareAmount("$" + excelData.getDeclareAmount());
            }

            Map<String, Double> declareUnitPriceMap = new HashMap();
            FindDetailQuery findDetail = new FindDetailQuery();
            List<HistoryQuery> historyList = new ArrayList<>();
            for (ClearanceExcelEntity clearance : clearanceExcelList) {
                HistoryQuery history = new HistoryQuery();
                history.setMskuId(clearance.getMskuId());
                history.setShopId(clearance.getShopId());

                historyList.add(history);
            }
            findDetail.setHistoryKey(historyList);
            if (findDetail.getHistoryKey() != null && findDetail.getHistoryKey().size() > 0) {
                List<ProductDetailsEnity> detailList = billDao.findDatail(findDetail);
                for (ProductDetailsEnity detail : detailList) {
                    declareUnitPriceMap.put(Crypto.join(detail.getMskuId(), detail.getShopId()), detail.getDeclareUnitPrice());
                }
            }

            List<ClearanceExcelDto> clearances = new ArrayList<>();
            for (int m = 0; m < clearanceExcelList.size(); m++) {
                ClearanceExcelEntity clearance = clearanceExcelList.get(m);
                ClearanceExcelDto clearanceExcel = new ClearanceExcelDto();
                BeanUtils.copyProperties(clearance, clearanceExcel);
                clearanceExcel.setNum(m + 1);
                clearanceExcel.setClearanceUnitPrice(decimalFormatDealer(clearance.getClearanceUnitPrice()));
                if (clearanceExcel.getClearanceSubtotal() != null) {
                    clearanceExcel.setClearanceSubtotal("$" + clearanceExcel.getClearanceSubtotal());
                }
                if (clearance.getReplenishmentQuantity() == null) {
                    clearanceExcel.setReplenishmentQuantity("0");
                } else {
                    clearanceExcel.setReplenishmentQuantity(String.valueOf(clearance.getReplenishmentQuantity()));
                }
                clearanceExcel.setClearanceSubtotal(decimalFormatDealer(declareUnitPriceMap.get(Crypto.join(clearance.getMskuId(), clearance.getShopId())) * clearance.getReplenishmentQuantity()));

                clearances.add(clearanceExcel);
            }
            excelData.setCustomClearances(clearances);
        } else {
            List<ClearanceExcelEntity> clearanceExcelDtos = transferDao.findClearanceDownloadInfo(wayBillId);
            List<ClearanceExcelDto> clearanceExcelDtos1 = new ArrayList<>();
            int num = 1;
            for (ClearanceExcelEntity clearanceExcelDto : clearanceExcelDtos) {
                ClearanceExcelDto clearanceExcelDto1 = new ClearanceExcelDto();
                BeanUtils.copyProperties(clearanceExcelDto, clearanceExcelDto1);
                clearanceExcelDto1.setNum(num);
                Result prodDetails = productService.getProdDetails(clearanceExcelDto.getSkuId());
                Map prodDetail = (Map) prodDetails.getData();
                clearanceExcelDto1.setCustomsNumber((String) ((Map) prodDetail.get("declareInfo")).get("customsNumber"));
                clearanceExcelDto1.setClearanceSubtotal(String.valueOf(clearanceExcelDto.getReplenishmentQuantity() * clearanceExcelDto.getClearanceUnitPrice()));
                clearanceExcelDto1.setReplenishmentQuantity(String.valueOf(clearanceExcelDto.getReplenishmentQuantity()));
                clearanceExcelDto1.setClearanceUnitPrice(String.valueOf(clearanceExcelDto.getClearanceUnitPrice()));
                clearanceExcelDtos1.add(clearanceExcelDto1);
            }
            excelData.setCustomClearances(clearanceExcelDtos1);
        }

        // 导出Excel
        int i = 0;
        String fileName = "商业发票";
        /** 创建工作簿 */
        HSSFWorkbook workbook = new HSSFWorkbook();
        /** 创建工作单 */
        HSSFSheet sheet = workbook.createSheet("商业发票");
        /** 卖方公司名称 */
        HSSFRow row = sheet.createRow(i);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue(excelData.getSellCompanyName());
        /** 发货地址*/
        row = sheet.createRow(i += 1);
        cell = row.createCell(0);
        cell.setCellValue(excelData.getSendAddress());
        // 联系人
        row = sheet.createRow(i += 1);
        cell = row.createCell(0);
        cell.setCellValue("Contact Name:" + excelData.getSellContact());
        //
        row = sheet.createRow(i += 1);
        row.setHeightInPoints(72);
        cell = row.createCell(0);
        cell.setCellValue("Commercial Invoice");

        // 买方公司名称（英文）和 创建日期
        HSSFCellStyle cellStyle = workbook.createCellStyle();
//        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        Font fontStyle = workbook.createFont(); // 字体样式
        fontStyle.setBold(true); // 加粗
        cellStyle.setFont(fontStyle);

        row = sheet.createRow(i += 1);
        cell = row.createCell(0);
        cell.setCellValue("To:");

        cell = row.createCell(1);
        cell.setCellValue(excelData.getBuyCompanyName());
        cell = row.createCell(7);
        cell.setCellValue("Order Date:");

        cell = row.createCell(8);
        cell.setCellValue(excelData.getCreateTime());
        // 收货地址
        row = sheet.createRow(i += 1);
        cell = row.createCell(1);
        cell.setCellValue(excelData.getReceiveAddress());
        cell = row.createCell(7);
        cell.setCellValue("Air waybill No:");
        // vat税号
        row = sheet.createRow(i += 1);
        cell = row.createCell(1);
        cell.setCellValue("VAT No.:" + excelData.getVatNo());
        cell = row.createCell(7);
        cell.setCellValue("Transport mode:");
        //
        row = sheet.createRow(i += 1);
        cell = row.createCell(7);
        cell.setCellValue("Shipment terms:");

        String[] titles = new String[]{"No.", "HS CODE", "DESCRIPTION", "Origin", "Material", "Usage", "Qty\r\n(PCS)", "Unit Price\r\n(PCS)", "Amout"};
        row = sheet.createRow(i += 3);
        row.setHeightInPoints(41);
        /** 循环创建第一行中的Cell（单元格） */
        for (int m = 0; m < titles.length; m++) {
            /** 创建Cell */
            cell = row.createCell(m);
            /** 设置单元格中的内容 */
            cell.setCellValue(titles[m]);

            HSSFCellStyle cellStyle1 = workbook.createCellStyle();
            Font fontStyle0 = workbook.createFont(); // 字体样式
            fontStyle0.setBold(true); // 加粗
            cellStyle1.setFont(fontStyle0);
//            cellStyle1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//            cellStyle1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//            cellStyle1.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);//上边框
//            cellStyle1.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
//            if (m != 0) {
//                cellStyle1.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
//            }
//            if (m != 8) {
//                cellStyle1.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
//            }

            cell.setCellStyle(cellStyle1);
        }
        /**  循环创建下面行中的数据   */
        List<?> data = excelData.getCustomClearances();
        for (int n = 0; n < data.size(); n++) {
            /** 循环创建行 */
            row = sheet.createRow(i += 1);
            row.setHeightInPoints(41);
            /** 获取集合中元素 */
            Object obj = data.get(n);
            /**
             * 把obj对象中数据作为Excel中的一行数据,
             * obj对象中的属性就是一行中的列
             * 利用反射获取所有的Field
             * */
            Field[] fields = obj.getClass().getDeclaredFields();
            /** 迭代Field */
            for (int j = 0; j < fields.length; j++) {
                /** 创建Cell */
                cell = row.createCell(j);
                /** 获取该字段名称 */
                String fieldName = fields[j].getName();
                /** 转化成get方法 */
                String getMethodName = "get" + StringUtils.capitalize(fieldName);
                /** 获取方法 */
                Method method = obj.getClass().getDeclaredMethod(getMethodName);
                /** 调用get方法该字段的值 */
                Object res = method.invoke(obj);
                /** 设置该Cell中的内容 */
                cell.setCellValue(res == null ? "" : res.toString());

                HSSFCellStyle cellStyle1 = workbook.createCellStyle();
//                cellStyle1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//                cellStyle1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//                cellStyle1.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
//                cellStyle1.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
//                if (j != 0) {
//                    cellStyle1.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
//                }
//                if (j != 8) {
//                    cellStyle1.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
//                }
                cell.setCellStyle(cellStyle1);
            }
        }
        // 申报总金额
        row = sheet.createRow(i += 1);
        int endLine = i;
        row.setHeightInPoints(24);
        cell = row.createCell(7);
        cell.setCellValue("Total(USD):");
        cellStyle = workbook.createCellStyle();
//        cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
//        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        fontStyle = workbook.createFont(); // 字体样式
        fontStyle.setFontHeightInPoints((short) 11);
        fontStyle.setBold(true); // 加粗
        cellStyle.setFont(fontStyle);
        cell.setCellStyle(cellStyle);
        cell = row.createCell(8);
        cell.setCellValue(excelData.getDeclareAmount());
        cellStyle = workbook.createCellStyle();
//        cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
//        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        fontStyle = workbook.createFont(); // 字体样式
        cellStyle.setFont(fontStyle);
        cell.setCellStyle(cellStyle);

        row = sheet.createRow(i += 4);
        row.setHeightInPoints(24);
        CellRangeAddress craddress1 = new CellRangeAddress(i, i, 7, 8);
        sheet.addMergedRegion(craddress1);
        cell = row.createCell(7);
        cell.setCellValue("Your Signature:");
        cellStyle = workbook.createCellStyle();
//        cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
//        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        fontStyle = workbook.createFont(); // 字体样式
        cellStyle.setFont(fontStyle);
        cell.setCellStyle(cellStyle);

        row = sheet.createRow(i += 1);
        row.setHeightInPoints(24);
        CellRangeAddress craddress2 = new CellRangeAddress(i, i, 7, 8);
        sheet.addMergedRegion(craddress2);
        cell = row.createCell(7);
        cell.setCellValue(excelData.getInvoiceRemark());
        cellStyle = workbook.createCellStyle();
//        cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
//        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        fontStyle = workbook.createFont(); // 字体样式
        fontStyle.setBold(true); // 加粗
        cellStyle.setFont(fontStyle);
        cell.setCellStyle(cellStyle);

        /** excel样式 */
        // 字体样式
        for (int m = 0; m < 4; m++) {
            // 合并单元格
            CellRangeAddress cra = new CellRangeAddress(m, m, 0, 8);
            sheet.addMergedRegion(cra);

            HSSFCellStyle cellStyle0 = workbook.createCellStyle();
            Font fontStyle0 = workbook.createFont(); // 字体样式
            fontStyle0.setBold(true); // 加粗
            if (m == 0) {
                fontStyle0.setFontHeightInPoints((short) 14);
            }
            if (m == 3) {
                fontStyle0.setUnderline(FontFormatting.U_DOUBLE);
                fontStyle0.setFontHeightInPoints((short) 20);
            }
            cellStyle0.setFont(fontStyle0);
//            cellStyle0.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//            cellStyle0.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            HSSFRow row1 = sheet.getRow(m);
            HSSFCell cell1 = row1.getCell(0);
            cell1.setCellStyle(cellStyle0);
        }
        int failCount = 0;
        for (int m = 0; m < 3; m++) {
            HSSFRow row1 = sheet.getRow(m);
            row1.setHeightInPoints(24);
            if (m == 1 || m == 2) {
                row1.setHeightInPoints(14);
            }
        }
        for (int m = 4; ; m++) {
            if (failCount > 5) break;
            HSSFRow row1 = sheet.getRow(m);
            if (row1 == null) {
                row1 = sheet.createRow(m);
                failCount++;
            }
            if (endLine < m || m < 10) {
                // 行高
                row1.setHeightInPoints(24);
            }

            if (m < 8) {
                // 合并单元格
                CellRangeAddress cra = new CellRangeAddress(m, m, 1, 5);
                sheet.addMergedRegion(cra);

                for (int n = 0; n < 9; n++) {
                    HSSFCellStyle cellStyle0 = workbook.createCellStyle();
//                    cellStyle0.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                    if (m == 4) {
                        if (n == 0 || n == 8) {
                            fontStyle.setBold(true); // 加粗
                            cellStyle0.setFont(fontStyle);
                        }
                    }
                    cell = row1.getCell(n);
//                    cellStyle0.setAlignment(HSSFCellStyle.ALIGN_LEFT);
//                    if (n == 7) {
//                        cellStyle0.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
//                    }
                    if (cell != null) {
                        cell.setCellStyle(cellStyle0);
                    }
                }
            }
        }
        // 列宽
        sheet.setColumnWidth(0, 7 * 256);
        sheet.setColumnWidth(1, 11 * 256);
        sheet.setColumnWidth(2, 21 * 256);
        sheet.setColumnWidth(3, 8 * 256);
        sheet.setColumnWidth(4, 11 * 256);
        sheet.setColumnWidth(5, 13 * 256);
        sheet.setColumnWidth(6, 9 * 256);
        sheet.setColumnWidth(7, 13 * 256);
        sheet.setColumnWidth(8, 10 * 256);


        /** 获取当前浏览器 */
        String userAgent = request.getHeader("user-agent");
        /** 文件名是中文转码 */
        if (userAgent.toLowerCase().indexOf("msie") != -1) {
            fileName = URLDecoder.decode(fileName, "gbk"); // utf-8 --> gbk
            fileName = new String(fileName.getBytes("gbk"), "iso8859-1"); // gbk --> iso8859-1
        } else {
            fileName = new String(fileName.getBytes("utf-8"), "iso8859-1"); // utf-8 --> iso8859-1
        }
        response.setCharacterEncoding(System.getProperties().getProperty("file.encoding"));
        /** 以流的形式下载文件 */
        response.setContentType("application/vnd.ms-excel;");
        /** 告诉浏览器文件名 */
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");
        /** 强制将缓冲区中的数据发送出去 */
        response.flushBuffer();
        /** 向浏览器输出Excel */
        workbook.write(response.getOutputStream());
    }


    /**
     * 新增或修改时方法进行合并
     *
     * @param proList   产品详细
     * @param billEnity 清关发票信息
     */
    public void dealProList(List<ProductDetailsEnity> proList, LogisticsBillEnity billEnity) {
        int totalProductNumber = 0;
        double totalPrice = 0;
        List<String> replenishmentIDs = billDao.findByShipwWaybillId(billEnity.getWaybillId());
        if (replenishmentIDs.size() > 0) {
            LogisticsBillEnity bill = billDao.findBillByWayBiilId(billEnity.getWaybillId());
            String time = Time.getCurrentDateTime();
            String userId = billEnity.getUserId();
            for (ProductDetailsEnity product : proList) {
                totalProductNumber += product.getReplenishmentQuantity();
                double singleProductTotal = product.getReplenishmentQuantity() * product.getDeclareUnitPrice();
                totalPrice += product.getDeclareUnitPrice() * product.getReplenishmentQuantity();
                product.setUuid(UUID.randomUUID().toString());
                product.setCreateUser(userId);
                product.setCreateTime(time);
                ProductDetailsEnity pro = billDao.findDatailByShopIdAndMskuID(product.getShopId(), product.getMskuId());
                if (pro != null) {
//              检查数据是否相同，如果相同则不需要更新
                    boolean isNeedUpdate = checkIsNeeedUpdate(product, pro);
                    if (!isNeedUpdate) {
                        billDao.updateBillProductHis(product);
                    }
                } else {
                    billDao.addHisProDetail(product);
                }
                for (String replenishmentID : replenishmentIDs) {
//              更新补货详细商品信息表中的清关信息
                    Map map = new HashMap<>();
                    map.put("replenishmentID", replenishmentID);
                    map.put("mskuId", product.getMskuId());
                    map.put("singleProductTotal", singleProductTotal);
                    map.put("originPlace", product.getOriginPlace());
                    map.put("property", product.getProperty());
                    map.put("declareUnitPrice", product.getDeclareUnitPrice());
                    map.put("userWay", product.getUseWay());
                    map.put("clearanceName", product.getClearanceName());
                    billDao.updateMskInfo(map);
                }
            }
            billEnity.setDeclareKindCnt(proList.size());
            billEnity.setDeclareAmount(totalPrice);
            billEnity.setDeclareQuantity(totalProductNumber);
            if (bill != null) {
                billDao.updateBillEnity(billEnity);
            } else {
                billDao.addBill(billEnity);
            }
        } else {
            String transferId = transferDao.findTransferIdByWaybillId(billEnity.getWaybillId());
            Result transferResult = transferService.findById(transferId);
            TransferInfoReturnVo transferInfoReturnVo = (TransferInfoReturnVo) transferResult.getData();
            LogisticsBillEnity bill = billDao.findBillByWayBiilId(billEnity.getWaybillId());
            for (ProductDetailsEnity product : proList) {
                totalProductNumber += product.getReplenishmentQuantity();
                double singleProductTotal = product.getReplenishmentQuantity() * product.getDeclareUnitPrice();
                totalPrice += product.getDeclareUnitPrice() * product.getReplenishmentQuantity();
                product.setUuid(UUID.randomUUID().toString());
                product.setCreateUser(billEnity.getUserId());
                product.setCreateTime(Time.getCurrentDateTime());
                for (TransferOrderDetailsVo transferOrderDetailsVo : transferInfoReturnVo.getTransferOrderDetailsVos()) {
                    if (transferOrderDetailsVo.getSkuId().equals(product.getSkuId())) {
                        Map map = new HashMap();
                        map.put("transferId", transferId);
                        map.put("skuId", transferOrderDetailsVo.getSkuId());
                        map.put("countryOfOrigin", product.getOriginPlace());
                        map.put("textureOfMateria", product.getProperty());
                        map.put("purposeDesc", product.getUseWay());
                        map.put("clearanceName", product.getClearanceName());
                        map.put("clearanceUnitPrice", product.getDeclareUnitPrice());
                        transferDao.updateClearanceInfo(map);
                    }
                }
            }
            billEnity.setDeclareKindCnt(proList.size());
            billEnity.setDeclareAmount(totalPrice);
            billEnity.setDeclareQuantity(totalProductNumber);
            if (bill == null) {
                billDao.addBill(billEnity);
            } else {
                billDao.updateBillEnity(billEnity);
            }
        }
    }

    /**
     * 检查是否为相同数据
     *
     * @param product 需校验的商品
     * @param pro     数据库商品
     */
    private boolean checkIsNeeedUpdate(ProductDetailsEnity product, ProductDetailsEnity pro) {
        return pro.equals(product);
    }

    private String decimalFormatDealer(Double num) {
        DecimalFormat df = new DecimalFormat("#.00");
        if (num == null) return null;
        String number = df.format(num);
        if (num < 1 && num > -1) {
            number = "0" + number;
        }
        return number;
    }
}
