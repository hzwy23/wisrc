package com.wisrc.replenishment.webapp.service.impl;

import com.wisrc.replenishment.webapp.dao.*;
import com.wisrc.replenishment.webapp.dto.WaybillClearance.*;
import com.wisrc.replenishment.webapp.dto.code.CodeTemplateConfEntity;
import com.wisrc.replenishment.webapp.entity.*;
import com.wisrc.replenishment.webapp.service.*;
import com.wisrc.replenishment.webapp.utils.CnUpperCaser;
import com.wisrc.replenishment.webapp.utils.ExcelTools;
import com.wisrc.replenishment.webapp.utils.Result;
import com.wisrc.replenishment.webapp.utils.ServiceUtils;
import com.wisrc.replenishment.webapp.vo.*;
import com.wisrc.replenishment.webapp.vo.transferorder.TransferDeclareVo;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CustomsInfoServiceImpl implements CustomsInfoService {
    @Autowired
    private TransferDao transferDao;
    @Autowired
    private TransferService transferService;
    @Autowired
    private CustomsInfoDao customsInfoDao;
    @Autowired
    private WaybillInfoDao waybillInfoDao;
    @Autowired
    private ReplenishShippingDataDao replenishShippingDataDao;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private LogisticsBillDao billDao;
    @Autowired
    private UploadDownloadService uploadDownloadService;
    @Autowired
    private ReplenishmentCodeService replenishmentCodeService;
    @Autowired
    private WaybillInfoService waybillInfoService;
    @Autowired
    private WaybillInfoAttrDao waybillInfoAttrDao;

    @Override
    public String getBatchNumber(String fbaReplenishmentId) {
        return customsInfoDao.getBatchNumber(fbaReplenishmentId);
    }

    @Override
    public ImproveSendDataVO getImproveMskuInfo(String uuid) {
        return customsInfoDao.getImproveMskuInfo(uuid);
    }

    @Override
    public CustomsListInfoVO getCustomsMskuInfo(String uuid) {
        return customsInfoDao.getCustomsMskuInfo(uuid);
    }

    @Override
    public CustomsInfoEntity get(String waybillId) {
        return customsInfoDao.get(waybillId);
    }

    @Override
    public SelectDeclareCustomVO getVO(String waybillId) {
        return customsInfoDao.getVO(waybillId);
    }

    @Override
    @Transactional(transactionManager = "retailReplenishmentTransactionManager")
    public void addCustomsInfo(CompanyCustomsInfoVO vo) {
        customsInfoDao.addCustomsInfo(vo);
        List<CustomsProductListVO> voList = vo.getProductListVOList();
        if (vo.getFlag() == 1) {
            for (CustomsProductListVO productListVO : voList) {
                ReplenishShippingDataVO shippingDataVO = replenishShippingDataDao.get(productListVO.getReplenishmentCommodityId());
                double grossWeight = shippingDataVO.getNumberOfBoxes() * shippingDataVO.getPackingWeight();
                productListVO.setGrossWeight(grossWeight);
                productListVO.setNetWeight(grossWeight - shippingDataVO.getNumberOfBoxes() * 0.5);
                productListVO.setDeclareSubtotal(productListVO.getDeclareUnitPrice() * shippingDataVO.getNumberOfBoxes() * shippingDataVO.getPackingQuantity());
                replenishShippingDataDao.updateCustomsMskuInfo(productListVO);
            }
        } else {
            String transferId = transferDao.findTransferIdByWaybillId(vo.getWaybillId());
            for (CustomsProductListVO customsProductListVO : voList) {
                transferDao.updateDeclareInfo(transferId, customsProductListVO.getSkuId(), customsProductListVO.getDeclareUnitPrice(), customsProductListVO.getMskuUnitCd(), customsProductListVO.getDeclarationElements());
            }
        }
        //修改运单缺少报关资料字段
        waybillInfoDao.updateCustoms(vo.getWaybillId());
    }

    @Override
    public void addCustomsOrder(CustomsOrderVO vo) {
        customsInfoDao.addCustomsOrder(vo);
    }

    @Override
    @Transactional(transactionManager = "retailReplenishmentTransactionManager")
    public void updateCustomsInfo(CompanyCustomsInfoVO vo) {
        customsInfoDao.updateCustomsInfo(vo);
        List<CustomsProductListVO> voList = vo.getProductListVOList();
        if (vo.getFlag() == 1) {
            for (CustomsProductListVO productListVO : voList) {
                ReplenishShippingDataVO shippingDataVO = replenishShippingDataDao.get(productListVO.getReplenishmentCommodityId());
                double grossWeight = shippingDataVO.getNumberOfBoxes() * shippingDataVO.getPackingWeight();
                productListVO.setGrossWeight(grossWeight);
                productListVO.setNetWeight(grossWeight - shippingDataVO.getNumberOfBoxes() * 0.5);
                productListVO.setDeclareSubtotal(productListVO.getDeclareUnitPrice() * shippingDataVO.getNumberOfBoxes() * shippingDataVO.getPackingQuantity());
                replenishShippingDataDao.updateCustomsMskuInfo(productListVO);
            }
        } else {
            String transferId = transferDao.findTransferIdByWaybillId(vo.getWaybillId());
            for (CustomsProductListVO customsProductListVO : voList) {
                transferDao.updateDeclareInfo(transferId, customsProductListVO.getSkuId(), customsProductListVO.getDeclareUnitPrice(), customsProductListVO.getMskuUnitCd(), customsProductListVO.getDeclarationElements());
            }
        }
    }


    @Override
    public Result customExcel(HttpServletRequest request, HttpServletResponse response, String wayBillId) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/dd");
            SimpleDateFormat sdfCN = new SimpleDateFormat("yyyy年M月dd日");
            List<ReplenishmentExcelDto> replenishments = new ArrayList<>();

            // 公司档案
            Result companyResult = companyService.getCompanyInfo();
            if (companyResult.getCode() != 200) {
                throw new Exception(companyResult.getMsg());
            }
            Map companyMap = (Map) companyResult.getData();
            String companyName = (String) companyMap.get("companyName");
            String companyNameEn = (String) companyMap.get("companyNameEn");
            String companyNameHk = (String) companyMap.get("companyNameHk");
            String companyAddress = (String) companyMap.get("companyAddress");
            String companyAddressEn = (String) companyMap.get("companyAddressEn");
            String companyTelephone = (String) companyMap.get("companyTelephone");
            String companyFax = (String) companyMap.get("companyFax");
            String taxpayerIdentificationNumber = (String) companyMap.get("taxpayerIdentificationNumber");

            // 贸易方式
            Result tradeResult = companyService.tradeAttr();
            if (tradeResult.getCode() != 200) {
                throw new Exception(tradeResult.getMsg());
            }
            List<Map> tradeList = (List) tradeResult.getData();
            Map<Integer, String> tradeMap = new HashMap();
            for (Map trade : tradeList) {
                tradeMap.put((Integer) trade.get("tradeModeCd"), (String) trade.get("tradeModeName"));
            }

            // 征免性质
            Result natureResult = companyService.natureAttr();
            if (natureResult.getCode() != 200) {
                throw new Exception(natureResult.getMsg());
            }
            List<Map> natureList = (List) natureResult.getData();
            Map<Integer, String> natureMap = new HashMap();
            for (Map nature : natureList) {
                natureMap.put((Integer) nature.get("exemptingNatureCd"), (String) nature.get("exemptingNatureName"));
            }

            // 包装类型
            Result packingResult = companyService.packingAttr();
            if (packingResult.getCode() != 200) {
                throw new Exception(packingResult.getMsg());
            }
            List<Map> packingList = (List) packingResult.getData();
            Map<Integer, String> packingMap = new HashMap();
            for (Map packing : packingList) {
                packingMap.put((Integer) packing.get("packingTypeCd"), (String) packing.get("packingTypeName"));
            }

            // 公司报关信息
            Result customsResult = companyService.getCompanyCustomsInfo();
            if (customsResult.getCode() != 200) {
                throw new Exception(customsResult.getMsg());
            }
            Map customsMap = (Map) customsResult.getData();
            String customsEmployee = (String) customsMap.get("contacts") + customsMap.get("cellphone");
            String customsDeclareId = (String) customsMap.get("customsCode");

            // 货币制度
            Result monetaryResult = companyService.monetaryAttr();
            if (monetaryResult.getCode() != 200) {
                throw new Exception(monetaryResult.getMsg());
            }
            List<Map> monetaryList = (List) monetaryResult.getData();
            Map<Integer, String> monetaryMap = new HashMap();
            for (Map monetary : monetaryList) {
                monetaryMap.put((Integer) monetary.get("monetarySystemCd"), (String) monetary.get("monetarySystemName"));
            }

            // 征免方式
            Result modeResult = companyService.modeAttr();
            if (modeResult.getCode() != 200) {
                throw new Exception(modeResult.getMsg());
            }
            List<Map> modeList = (List) modeResult.getData();
            Map<Integer, String> modeMap = new HashMap();
            for (Map mode : modeList) {
                modeMap.put((Integer) mode.get("exemptionModeCd"), (String) mode.get("exemptionModeName"));
            }

            // 交易模式
            Result transactionResult = companyService.transactionAttr();
            if (transactionResult.getCode() != 200) {
                throw new Exception(transactionResult.getMsg());
            }
            List<Map> transactionList = (List) transactionResult.getData();
            Map<Integer, String> transactionMap = new HashMap();
            for (Map transaction : transactionList) {
                transactionMap.put((Integer) transaction.get("transactionModeCd"), (String) transaction.get("transactionModeName"));
            }

            // 数据封装
            CustomsExcelEntity customsExcel = customsInfoDao.customsExcel(wayBillId);
            List<ReplenishmentExcelEntity> replenishmentList = billDao.getReplenishmentExcel(wayBillId);
            if (replenishmentList.size() == 1 && replenishmentList.get(0) == null || replenishmentList.size() == 0) {
                List<TransferDeclareVo> transferList = (List) waybillInfoService.findMskuVO(wayBillId).get("transferWaybillDeclare");
                List<DeclareMskuUnitAttrEntity> unitAttrList = waybillInfoAttrDao.findUnitAttr();
                Map<Integer, String> unitAttrMap = new HashMap();
                for (DeclareMskuUnitAttrEntity unitAttr : unitAttrList) {
                    unitAttrMap.put(unitAttr.getMskuUnitCd(), unitAttr.getMskuUnitName());
                }
                for (TransferDeclareVo transfer : transferList) {
                    if (transfer.getPackInfoEntities() != null) {
                        for (TransferOrderPackInfoEntity pack : transfer.getPackInfoEntities()) {
                            ReplenishmentExcelEntity replenish = new ReplenishmentExcelEntity();
                            BeanUtils.copyProperties(transfer, replenish);

                            replenish.setNumberOfBoxes(pack.getNumberOfBoxes());
                            replenish.setPackingWeight(pack.getWeight());
                            replenish.setPackingQuantity(pack.getPackingQuantity());
                            try {
                                replenish.setMskuUnitName(unitAttrMap.get(Integer.parseInt(transfer.getSkuUnitCd())));
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }

                            replenishmentList.add(replenish);
                        }
                    }
                }
            }
            for (ReplenishmentExcelEntity replenishmentExcelEntity : replenishmentList) {
                if (replenishmentExcelEntity.getNumberOfBoxes() == null) {
                    replenishmentExcelEntity.setNumberOfBoxes(0);
                }
                if (replenishmentExcelEntity.getPackingWeight() == null) {
                    replenishmentExcelEntity.setPackingWeight((double) 0);
                }
                if (replenishmentExcelEntity.getDeclareUnitPrice() == null) {
                    replenishmentExcelEntity.setDeclareUnitPrice((double) 0);
                }
                if (replenishmentExcelEntity.getPackingQuantity() == null) {
                    replenishmentExcelEntity.setPackingQuantity(0);
                }
                // 毛重 = 装箱重量 * 装箱箱数
                replenishmentExcelEntity.setGrossWeight(replenishmentExcelEntity.getPackingWeight() * replenishmentExcelEntity.getNumberOfBoxes());
                // 净重 = 毛重 - 装箱数 * 0.5
                replenishmentExcelEntity.setNetWeight(replenishmentExcelEntity.getGrossWeight() - replenishmentExcelEntity.getNumberOfBoxes() * 0.5);
                // 小计（subtotal） = 申报单价（declareUnitPrice） * 装箱箱数（numberOfBoxes） * 装箱量（packingQuantity）
                replenishmentExcelEntity.setDeclareSubtotal(replenishmentExcelEntity.getDeclareUnitPrice() * replenishmentExcelEntity.getNumberOfBoxes() * replenishmentExcelEntity.getPackingQuantity());
            }

            if (customsExcel == null) {
                return Result.failure(400, "缺少报关资料，无法下载", "");
            }

            // 报关单
            CustomsExcelDto excel = new CustomsExcelDto();
            BeanUtils.copyProperties(customsExcel, excel);
            excel.setShipper(companyName);
            excel.setReceiver(companyNameHk);
            excel.setUnit(companyName);
            excel.setTradeType(tradeMap.get(customsExcel.getTradeTypeCd()));
            excel.setExemptingNature(natureMap.get(customsExcel.getExemptingNatureCd()));
            excel.setPackType(packingMap.get(customsExcel.getPackTypeCd()));
            excel.setDealType(transactionMap.get(customsExcel.getDealTypeCd()));
            excel.setTaxpayerIdentificationNumber(taxpayerIdentificationNumber);
            if (customsExcel.getTransportTypeCd() == 0) {

            }
            if (customsExcel.getTransportTypeCd() == 1) {
                excel.setTransportType("航空运输");
            } else if (customsExcel.getTransportTypeCd() == 2) {
                excel.setTransportType("海运");
            } else if (customsExcel.getTransportTypeCd() == 3) {
                excel.setTransportType("陆运");
            }

            // 发票
            InvoiceExcelDto invoiceExcel = new InvoiceExcelDto();
            List<ReplenishInvoiceExcelDto> RIExcel = new ArrayList<>();
            BeanUtils.copyProperties(customsExcel, invoiceExcel);
            invoiceExcel.setBuyer(companyNameHk);
            if (customsExcel.getCustomsDeclarationDate() != null) {
                invoiceExcel.setCustomsDeclarationDate(sdf.format(customsExcel.getCustomsDeclarationDate()));
            }
            invoiceExcel.setMoneyType(monetaryMap.get(customsExcel.getMoneyTypeCd()));

            // 装箱单
            PackingExcelDto packingExcel = new PackingExcelDto();
            List<ReplenishPackingExcelDto> RPExcel = new ArrayList<>();
            BeanUtils.copyProperties(customsExcel, packingExcel);
            packingExcel.setCustomer(companyNameHk);
            if (customsExcel.getCustomsDeclarationDate() != null) {
                packingExcel.setCustomsDeclarationDate(sdf.format(customsExcel.getCustomsDeclarationDate()));
            }

            // 合同
            ContractExcelDto contractExcel = new ContractExcelDto();
            List<ReplenishContractExcelDto> RCExcel = new ArrayList<>();
            BeanUtils.copyProperties(customsExcel, contractExcel);
            contractExcel.setName(companyName + " " + companyNameEn);
            contractExcel.setAddress(companyAddress + "\r\n" + companyAddressEn);
            contractExcel.setCompanyTelephone(companyTelephone);
            contractExcel.setCompanyFax(companyFax);
            if (customsExcel.getCustomsDeclarationDate() != null) {
                contractExcel.setDate(sdf.format(ServiceUtils.multipyDay(customsExcel.getCustomsDeclarationDate(), -60)));
            }
            contractExcel.setPackType(packingMap.get(customsExcel.getPackTypeCd()));
            if (customsExcel.getCustomsDeclarationDate() != null)
                contractExcel.setTrafficDate(sdf.format(ServiceUtils.multipyDay(customsExcel.getCustomsDeclarationDate(), 20)));

            // 委托书
            AttorneyExcelDto attorneyExcel = new AttorneyExcelDto();
            BeanUtils.copyProperties(customsExcel, attorneyExcel);
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            c.clear();
            c.set(Calendar.YEAR, year);
            c.roll(Calendar.DAY_OF_YEAR, -1);
            attorneyExcel.setLastDate(c.getTime());
            if (customsExcel.getCustomsDeclarationDate() != null) {
                attorneyExcel.setCustomsDeclarationDate(sdfCN.format(customsExcel.getCustomsDeclarationDate()));
            }
            attorneyExcel.setCompanyName(companyName);
            attorneyExcel.setTradeType(tradeMap.get(customsExcel.getTradeTypeCd()));

            double grossWeightTotal = 0;
            double netWeightTotal = 0;
            int numberOfBoxesTotal = 0;
            int customsCountTotal = 0;
            double declareSubtotalTotal = 0;
            Integer maxCustomsCount = null;
            for (int m = 0; m < replenishmentList.size(); m++) {
                int num = m + 1;
                ReplenishmentExcelEntity replenishment = replenishmentList.get(m);
                // 报关单
                ReplenishmentExcelDto replenishmentExcel = new ReplenishmentExcelDto();
                BeanUtils.copyProperties(replenishment, replenishmentExcel);
                replenishmentExcel.setNum(num);
                replenishmentExcel.setDestinationCountry(customsExcel.getDestinationCountry());
                replenishmentExcel.setMoneyType(monetaryMap.get(customsExcel.getMoneyTypeCd()));
                replenishmentExcel.setExemptionMode(modeMap.get(customsExcel.getExemptionModeCd()));

                // 发票
                ReplenishInvoiceExcelDto replenishInvoiceExcel = new ReplenishInvoiceExcelDto();
                BeanUtils.copyProperties(replenishment, replenishInvoiceExcel);
                replenishInvoiceExcel.setNum(num);

                // 装箱单
                ReplenishPackingExcelDto replenishPackingExcel = new ReplenishPackingExcelDto();
                BeanUtils.copyProperties(replenishment, replenishPackingExcel);
                replenishPackingExcel.setNum(num);

                // 合同
                ReplenishContractExcelDto replenishContractExcel = new ReplenishContractExcelDto();
                BeanUtils.copyProperties(replenishment, replenishContractExcel);
                replenishContractExcel.setNum(num);
                replenishContractExcel.setDeclareSubtotal(replenishment.getDeclareSubtotal());
                replenishContractExcel.setMoneyType(monetaryMap.get(customsExcel.getMoneyTypeCd()));

                int customsCount = replenishment.getNumberOfBoxes() * replenishment.getPackingQuantity();
                replenishmentExcel.setCustomsCount(customsCount);
                replenishInvoiceExcel.setCustomsCount(customsCount);
                replenishContractExcel.setCustomsCount(customsCount);
                replenishPackingExcel.setCustomsCount(customsCount);
                if (maxCustomsCount == null || maxCustomsCount < customsCount || attorneyExcel.getClearanceName() == null) {
                    maxCustomsCount = customsCount;
                    attorneyExcel.setClearanceName(replenishment.getDeclarationElements().split("\\|")[0]);
                    attorneyExcel.setCustomsNumber(replenishment.getCustomsNumber());
                }

                customsCountTotal += customsCount;
                if (replenishment.getGrossWeight() != null) {
                    grossWeightTotal += replenishment.getGrossWeight();
                }
                if (replenishment.getNetWeight() != null) {
                    netWeightTotal += replenishment.getNetWeight();
                }
                if (replenishment.getNumberOfBoxes() != null) {
                    numberOfBoxesTotal += replenishment.getNumberOfBoxes();
                }
                if (replenishment.getDeclareSubtotal() != null) {
                    declareSubtotalTotal += replenishment.getDeclareSubtotal();
                }

                replenishments.add(replenishmentExcel);
                RIExcel.add(replenishInvoiceExcel);
                RPExcel.add(replenishPackingExcel);
                RCExcel.add(replenishContractExcel);
            }

            excel.setGrossWeightTotal(grossWeightTotal);
            excel.setNetWeightTotal(netWeightTotal);
            excel.setReplenishmentExcel(replenishments);
            excel.setDeclareKindCnt(String.valueOf(numberOfBoxesTotal));
            invoiceExcel.setReplenishmentExcel(RIExcel);
            invoiceExcel.setDeclareAllSubtotal(declareSubtotalTotal);
            packingExcel.setNumberOfBoxesTotal(numberOfBoxesTotal);
            packingExcel.setCustomsCountTotal(customsCountTotal);
            packingExcel.setGrossWeightTotal(grossWeightTotal);
            packingExcel.setNetWeightTotal(netWeightTotal);
            packingExcel.setReplenishmentExcel(RPExcel);
            contractExcel.setReplenishmentExcel(RCExcel);
            contractExcel.setTotal(declareSubtotalTotal);
            contractExcel.setTotalCN(CnUpperCaser.number2CNMontrayUnit(new BigDecimal(contractExcel.getTotal())));
            contractExcel.setMoneyType(monetaryMap.get(customsExcel.getMoneyTypeCd()));
            attorneyExcel.setTotal(String.valueOf(monetaryMap.get(customsExcel.getMoneyTypeCd()) + " " + declareSubtotalTotal));


            CodeTemplateConfEntity temp = replenishmentCodeService.getCodeTemplateConfById("3bb2f8aba9084ca896a0354f8876213c");
            byte[] excelBytes = uploadDownloadService.downloadFile(temp.getObsName(), temp.getAddr());
            Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(excelBytes));

            sheet1(workbook.getSheetAt(0), excel);
            sheet2(workbook.getSheetAt(1), invoiceExcel);
            sheet3(workbook.getSheetAt(2), packingExcel);
            sheet4(workbook.getSheetAt(3), contractExcel);
            sheet5(workbook.getSheetAt(4), attorneyExcel);


            String fileName = "报关资料";
            String suffix = ".xlsx";
            /** 获取当前浏览器 */
            String userAgent = request.getHeader("user-agent");
            /** 文件名是中文转码 */
            if (userAgent.toLowerCase().indexOf("msie") != -1) {
                fileName = URLDecoder.decode(fileName, "gbk"); // utf-8 --> gbk
                fileName = new String(fileName.getBytes("gbk"), "iso8859-1"); // gbk --> iso8859-1
            } else {
                fileName = new String(fileName.getBytes("utf-8"), "iso8859-1"); // utf-8 --> iso8859-1
            }
            /** 以流的形式下载文件 */
            response.setContentType("application/octet-stream");
            /** 告诉浏览器文件名 */
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName + suffix);
            /** 强制将缓冲区中的数据发送出去 */
            response.flushBuffer();
            /** 向浏览器输出Excel */
            workbook.write(response.getOutputStream());

            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }


    public void sheet1(Sheet sheet, CustomsExcelDto data) {
        int replenishmentCount = data.getReplenishmentExcel().size();
        int skip = 2;
        int formStart = 19;
        ExcelTools.forCopyRow(sheet, formStart + 1, skip, replenishmentCount);
        /** 获取最后一行的编号 */
        int totalNum = sheet.getLastRowNum();
        /** 迭代读取Excel中一行数据 */
        for (int i = 1; i <= totalNum; i++) {
            Row row = sheet.getRow(i);
            /** 获取该行列的迭代器 */
            Iterator<Cell> cells = row.cellIterator();
            while (cells.hasNext()) {
                /** 获取列  */
                Cell cell = cells.next();
                /** 获取当前列的索引号 */
                int index = cell.getColumnIndex();


                if (i == 3) {
                    if (index == 1) {
                        cell.setCellValue(data.getTaxpayerIdentificationNumber());
                    }
                } else if (i == 4) {
                    if (index == 0) {
                        cell.setCellValue(data.getShipper());
                    }
                } else if (i == 6) {
                    if (index == 0) {
                        cell.setCellValue(data.getReceiver());
                    } else if (index == 3) {
                        cell.setCellValue(data.getTransportType());
                    }
                } else if (i == 7) {
                    if (index == 1) {
                        cell.setCellValue(data.getTaxpayerIdentificationNumber());
                    }
                } else if (i == 8) {
                    if (index == 0) {
                        cell.setCellValue(data.getUnit());
                    } else if (index == 3) {
                        cell.setCellValue(data.getTradeType());
                    } else if (index == 6) {
                        cell.setCellValue(data.getExemptingNature());
                    }
                } else if (i == 10) {
                    if (index == 0) {
                        cell.setCellValue(data.getCustomsDeclareId());
                    } else if (index == 3) {
                        cell.setCellValue(data.getTradeCountry());
                    } else if (index == 6) {
                        cell.setCellValue(data.getDestinationCountry());
                    }
                } else if (i == 12) {
                    if (index == 0) {
                        cell.setCellValue(data.getPackType());
                    } else if (index == 3) {
                        cell.setCellValue(data.getDeclareKindCnt());
                    } else if (index == 5) {
                        cell.setCellValue(data.getGrossWeightTotal());
                    } else if (index == 7) {
                        cell.setCellValue(data.getNetWeightTotal());
                    } else if (index == 9) {
                        cell.setCellValue(data.getDealType());
                    }
                }

                if (i > formStart && replenishmentCount > 0) {
                    int num = data.getReplenishmentExcel().size() - replenishmentCount;
                    if (i % 2 == 0) {
                        if (index == 0) {
                            cell.setCellValue(data.getReplenishmentExcel().get(num).getNum());
                        } else if (index == 1) {
                            cell.setCellValue(data.getReplenishmentExcel().get(num).getCustomsNumber());
                        } else if (index == 2) {
                            cell.setCellValue(data.getReplenishmentExcel().get(num).getDeclarationElements());
                        } else if (index == 4) {
                            cell.setCellValue(data.getReplenishmentExcel().get(num).getNetWeight());
                        } else if (index == 6) {
                            cell.setCellValue(data.getReplenishmentExcel().get(num).getDeclareUnitPrice());
                        } else if (index == 7) {
                            cell.setCellValue(data.getReplenishmentExcel().get(num).getDeclareSubtotal());
                        } else if (index == 8) {
                            cell.setCellValue(data.getReplenishmentExcel().get(num).getMoneyType());
                        } else if (index == 10) {
                            cell.setCellValue(data.getReplenishmentExcel().get(num).getDestinationCountry());
                        } else if (index == 11) {
                            cell.setCellValue(data.getGoodsSource());
                        } else if (index == 12) {
                            cell.setCellValue(data.getReplenishmentExcel().get(num).getExemptionMode());
                        }
                    } else {
                        if (index == 4) {
                            if (data.getReplenishmentExcel().get(num).getCustomsCount() != null)
                                cell.setCellValue(data.getReplenishmentExcel().get(num).getCustomsCount());
                        } else if (index == 5) {
                            cell.setCellValue(data.getReplenishmentExcel().get(num).getMskuUnitName());
                        }
                    }
                }
            }
            if (i > formStart - 1 + skip && replenishmentCount > 0) {
                formStart = new Integer(i);
                replenishmentCount--;
            }
        }
    }

    public void sheet2(Sheet sheet, InvoiceExcelDto data) {
        int replenishmentCount = data.getReplenishmentExcel().size();
        int skip = 2;
        int formStart = 5;
        ExcelTools.forCopyRow(sheet, formStart + 1, skip, replenishmentCount);
        /** 获取最后一行的编号 */
        int totalNum = sheet.getLastRowNum();
        /** 迭代读取Excel中一行数据 */
        for (int i = 1; i <= totalNum; i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                break;
            }
            /** 获取该行列的迭代器 */
            Iterator<Cell> cells = row.cellIterator();
            while (cells.hasNext()) {
                /** 获取列  */
                Cell cell = cells.next();
                /** 获取当前列的索引号 */
                int index = cell.getColumnIndex();


                if (i == 2) {
                    if (index == 2) {
                        cell.setCellValue(data.getBuyer());
                    } else if (index == 6) {
                        cell.setCellValue(data.getCustomsDeclareId());
                    }
                } else if (i == 3) {
                    if (index == 6) {
                        cell.setCellValue(data.getCustomsDeclarationDate());
                    }
                }

                if (i > formStart && replenishmentCount > 0) {
                    int num = data.getReplenishmentExcel().size() - replenishmentCount;
                    if (i % skip == 0) {
                        if (index == 0) {
                            cell.setCellValue(data.getReplenishmentExcel().get(num).getNum());
                        } else if (index == 2) {
                            cell.setCellValue(data.getReplenishmentExcel().get(num).getDeclarationElements());
                        } else if (index == 3) {
                            if (data.getReplenishmentExcel().get(num).getCustomsCount() != null)
                                cell.setCellValue(data.getReplenishmentExcel().get(num).getCustomsCount());
                        } else if (index == 4) {
                            cell.setCellValue(data.getReplenishmentExcel().get(num).getMskuUnitName());
                        } else if (index == 5) {
                            cell.setCellValue(data.getReplenishmentExcel().get(num).getDeclareUnitPrice());
                        } else if (index == 6) {
                            cell.setCellValue(data.getReplenishmentExcel().get(num).getDeclareSubtotal());
                        }
                    }
                }

                if (i == 6 + data.getReplenishmentExcel().size() * skip) {
                    if (index == 6) {
                        cell.setCellValue(data.getMoneyType());
                    } else if (index == 7) {
                        cell.setCellValue(data.getDeclareAllSubtotal());
                    }
                }
            }
            if (i > formStart - 1 + skip && replenishmentCount > 0) {
                formStart = new Integer(i);
                replenishmentCount--;
            }
        }
    }

    public void sheet3(Sheet sheet, PackingExcelDto data) {
        int replenishmentCount = data.getReplenishmentExcel().size();
        int skip = 2;
        int formStart = 7;
        ExcelTools.forCopyRow(sheet, formStart + 1, skip, replenishmentCount);
        /** 获取最后一行的编号 */
        int totalNum = sheet.getLastRowNum();
        /** 迭代读取Excel中一行数据 */
        for (int i = 1; i <= totalNum; i++) {
            Row row = sheet.getRow(i);
            /** 获取该行列的迭代器 */
            Iterator<Cell> cells = row.cellIterator();
            while (cells.hasNext()) {
                /** 获取列  */
                Cell cell = cells.next();
                /** 获取当前列的索引号 */
                int index = cell.getColumnIndex();


                if (i == 2) {
                    if (index == 6) {
                        cell.setCellValue(data.getCustomsDeclarationDate());
                    }
                } else if (i == 3) {
                    if (index == 1) {
                        cell.setCellValue(data.getCustomer());
                    } else if (index == 6) {
                        cell.setCellValue(data.getCustomsDeclareId());
                    }
                } else if (i == 4) {
                    if (index == 6) {
                        cell.setCellValue(data.getCustomsDeclareId());
                    }
                } else if (i == 6) {
                    if (index == 2) {
                        cell.setCellValue(data.getDestinationCountry());
                    }
                }

                if (i > formStart && replenishmentCount > 0) {
                    int num = data.getReplenishmentExcel().size() - replenishmentCount;
                    if (i % 2 == 0) {
                        if (index == 0) {
                            cell.setCellValue(data.getReplenishmentExcel().get(num).getNum());
                        } else if (index == 1) {
                            cell.setCellValue(data.getReplenishmentExcel().get(num).getDeclarationElements());
                        } else if (index == 2) {
                            if (data.getReplenishmentExcel().get(num).getNumberOfBoxes() != null)
                                cell.setCellValue(data.getReplenishmentExcel().get(num).getNumberOfBoxes());
                        } else if (index == 3) {
                            if (data.getReplenishmentExcel().get(num).getCustomsCount() != null) {
                                cell.setCellValue(data.getReplenishmentExcel().get(num).getCustomsCount());
                            }
                        } else if (index == 5) {
                            cell.setCellValue(data.getReplenishmentExcel().get(num).getGrossWeight());
                        } else if (index == 6) {
                            cell.setCellValue(data.getReplenishmentExcel().get(num).getNetWeight());
                        }
                    }
                }

                if (i == 8 + data.getReplenishmentExcel().size() * skip) {
                    if (index == 2) {
                        cell.setCellValue(data.getNumberOfBoxesTotal());
                    } else if (index == 3) {
                        cell.setCellValue(data.getCustomsCountTotal());
                    } else if (index == 5) {
                        cell.setCellValue(data.getGrossWeightTotal());
                    } else if (index == 6) {
                        cell.setCellValue(data.getNetWeightTotal());
                    }
                }
            }
            if (i > formStart - 1 + skip && replenishmentCount > 0) {
                formStart = new Integer(i);
                replenishmentCount--;
            }
        }
    }

    public void sheet4(Sheet sheet, ContractExcelDto data) {
        int replenishmentCount = data.getReplenishmentExcel().size();
        int skip = 2;
        int formStart = 15;
        ExcelTools.forCopyRow(sheet, formStart + 1, skip, replenishmentCount);
        /** 获取最后一行的编号 */
        int totalNum = sheet.getLastRowNum();
        /** 迭代读取Excel中一行数据 */
        for (int i = 1; i <= totalNum; i++) {
            Row row = sheet.getRow(i);
            /** 获取该行列的迭代器 */
            Iterator<Cell> cells = row.cellIterator();
            while (cells.hasNext()) {
                /** 获取列  */
                Cell cell = cells.next();
                /** 获取当前列的索引号 */
                int index = cell.getColumnIndex();


                if (i == 2) {
                    if (index == 2) {
                        cell.setCellValue(data.getName());
                    }
                } else if (i == 4) {
                    if (index == 2) {
                        cell.setCellValue(data.getAddress());
                    }
                } else if (i == 6) {
                    if (index == 2) {
                        cell.setCellValue(data.getCompanyTelephone());
                    } else if (index == 4) {
                        cell.setCellValue(data.getCompanyFax());
                    } else if (index == 6) {
                        cell.setCellValue(data.getCustomsDeclareId());
                    }
                } else if (i == 8) {
                    if (index == 2) {
                        cell.setCellValue(data.getCompanyName());
                    } else if (index == 6) {
                        cell.setCellValue(data.getDate());
                    }
                } else if (i == 10) {
                    if (index == 2) {
                        cell.setCellValue(data.getCompanyAddress());
                    } else if (index == 6) {
                        cell.setCellValue(data.getSignAddress());
                    }
                }

                if (i > formStart && replenishmentCount > 0) {
                    int num = data.getReplenishmentExcel().size() - replenishmentCount;
                    if (i % 2 == 0) {
                        if (index == 0) {
                            cell.setCellValue(data.getReplenishmentExcel().get(num).getNum());
                        } else if (index == 1) {
                            cell.setCellValue(data.getReplenishmentExcel().get(num).getDeclarationElements());
                        } else if (index == 3) {
                            if (data.getReplenishmentExcel().get(num).getCustomsCount() != null)
                                cell.setCellValue(data.getReplenishmentExcel().get(num).getCustomsCount());
                        } else if (index == 4) {
                            cell.setCellValue(data.getReplenishmentExcel().get(num).getMskuUnitName());
                        } else if (index == 5) {
                            cell.setCellValue(data.getReplenishmentExcel().get(num).getDeclareUnitPrice());
                        } else if (index == 6) {
                            cell.setCellValue(data.getReplenishmentExcel().get(num).getMoneyType());
                        } else if (index == 7) {
                            cell.setCellValue(data.getReplenishmentExcel().get(num).getDeclareSubtotal());
                        }
                    }
                }

                if (i == 17 + data.getReplenishmentExcel().size() * skip) {
                    if (index == 6) {
                        cell.setCellValue(data.getMoneyType());
                    } else if (index == 7) {
                        cell.setCellValue(data.getTotal());
                    }
                } else if (i == 19 + data.getReplenishmentExcel().size() * skip) {
                    if (index == 2) {
                        cell.setCellValue(data.getTotalCN());
                    }
                } else if (i == 21 + data.getReplenishmentExcel().size() * skip) {
                    if (index == 2) {
                        cell.setCellValue(data.getPackType());
                    }
                } else if (i == 23 + data.getReplenishmentExcel().size() * skip) {
                    if (index == 2) {
                        cell.setCellValue(data.getTrafficDate());
                    }
                } else if (i == 25 + data.getReplenishmentExcel().size() * skip) {
                    if (index == 3) {
                        cell.setCellValue(data.getDestinationCountry());
                    }
                }
            }
            if (i > formStart - 1 + skip && replenishmentCount > 0) {
                formStart = new Integer(i);
                replenishmentCount--;
            }
        }
    }

    public void sheet5(Sheet sheet, AttorneyExcelDto data) {
        /** 获取最后一行的编号 */
        int totalNum = sheet.getLastRowNum();

        /** 迭代读取Excel中一行数据 */
        for (int i = 1; i <= totalNum; i++) {
            Row row = sheet.getRow(i);
            /** 获取该行列的迭代器 */
            Iterator<Cell> cells = row.cellIterator();
            while (cells.hasNext()) {
                /** 获取列  */
                Cell cell = cells.next();
                /** 获取当前列的索引号 */
                int index = cell.getColumnIndex();


                if (i == 9) {
                    if (index == 0) {
                        String value = cell.getStringCellValue();
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(data.getLastDate());
                        value = value.replaceAll("yyyy", String.valueOf(cal.get(Calendar.YEAR)));
                        value = value.replaceAll("MM", String.valueOf(cal.get(Calendar.MONTH) + 1));
                        value = value.replaceAll("dd", String.valueOf(cal.get(Calendar.DATE)));
                        cell.setCellValue(value);
                    }
                } else if (i == 16) {
                    if (index == 7) {
                        cell.setCellValue(data.getCustomsDeclarationDate());
                    }
                } else if (i == 22) {
                    if (index == 1) {
                        cell.setCellValue(data.getCompanyName());
                    }
                } else if (i == 23) {
                    if (index == 1) {
                        cell.setCellValue(data.getClearanceName());
                    }
                } else if (i == 24) {
                    if (index == 1) {
                        cell.setCellValue(data.getCustomsNumber());
                    }
                } else if (i == 25) {
                    if (index == 1) {
                        cell.setCellValue(data.getTotal());
                    }
                } else if (i == 28) {
                    if (index == 1) {
                        cell.setCellValue(data.getTradeType());
                    }
                } else if (i == 29) {
                    if (index == 1) {
                        cell.setCellValue(data.getGoodsSource());
                    }
                }
            }
        }
    }
}
