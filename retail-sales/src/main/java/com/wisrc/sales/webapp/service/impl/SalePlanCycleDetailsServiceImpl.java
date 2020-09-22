package com.wisrc.sales.webapp.service.impl;

import com.wisrc.sales.webapp.dao.SalePlanCycleDetailsDao;
import com.wisrc.sales.webapp.dao.SalePlanInfoDao;
import com.wisrc.sales.webapp.entity.SalePlanCycleDetailsEntity;
import com.wisrc.sales.webapp.entity.SalePlanInfoEntity;
import com.wisrc.sales.webapp.service.SalePlanCommonService;
import com.wisrc.sales.webapp.service.SalePlanCycleDetailsService;
import com.wisrc.sales.webapp.service.externalService.MskuService;
import com.wisrc.sales.webapp.utils.Result;
import com.wisrc.sales.webapp.utils.Time;
import com.wisrc.sales.webapp.vo.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SalePlanCycleDetailsServiceImpl implements SalePlanCycleDetailsService {
    @Autowired
    private MskuService mskuService;
    @Autowired
    private SalePlanInfoDao salePlanInfoDao;
    @Autowired
    private SalePlanCycleDetailsDao salePlanCycleDetailsDao;
    @Autowired
    private SalePlanCommonService salePlanCommonService;

    @Override
    public void add(SalePlanCycleDetailsEntity ele) {
        salePlanCycleDetailsDao.add(ele);
    }

    @Override
    public List<AddSalePlanVO> getDetail(String salePlanId, int sign, String userId) {
        List<AddSalePlanVO> voList = new ArrayList<>();
        List<String> list = salePlanCycleDetailsDao.getPlanDate(salePlanId);
        for (int i = 0; i < list.size(); i++) {
            AddSalePlanVO vo = new AddSalePlanVO();
            SalePlanInfoEntity entity = salePlanInfoDao.get(salePlanId);
            getMskuInfo(entity, userId);
            //List<List<SalePlanCycleDetailsEntity>> doubleList = new ArrayList<>();
            List<SalePlanCycleDetailsEntity> entityList = new ArrayList<>();
            List<SelectSalePlanDetailVO> detailList = new ArrayList<>();
            if (sign == 0) {
                detailList = salePlanCycleDetailsDao.getDetail(salePlanId, list.get(i));
            } else {
                detailList = salePlanCycleDetailsDao.getDetailNoTotal(salePlanId, list.get(i));
            }
            for (SelectSalePlanDetailVO detailVO : detailList) {
                SalePlanCycleDetailsEntity detailsEntity = new SalePlanCycleDetailsEntity();
                detailsEntity.setUuid(detailVO.getUuid());
                detailsEntity.setSalePlanId(detailVO.getSalePlanId());
                detailsEntity.setWeight(detailVO.getWeight());
                detailsEntity.setPlanDate(detailVO.getPlanDate());
                detailsEntity.setSaleCycle(detailVO.getSaleCycle());
                detailsEntity.setCostPrice(detailVO.getCostPrice());
                detailsEntity.setSalePrice(detailVO.getSalePrice());
                detailsEntity.setDaySaleNum(detailVO.getDaySaleNum());
                detailsEntity.setSaleTime(detailVO.getSaleTime());
                detailsEntity.setSaleAmount(detailVO.getSaleAmount());
                detailsEntity.setEstimateRefundableRate(detailVO.getEstimateRefundableRate());
                detailsEntity.setCommissionCoefficient(detailVO.getCommissionCoefficient());
                detailsEntity.setCommission(detailVO.getCommission());
                detailsEntity.setFulfillmentCost(detailVO.getFulfillmentCost());
                detailsEntity.setMarketingCost(detailVO.getMarketingCost());
                detailsEntity.setMarketingCostRatio(detailVO.getMarketingCostRatio());
                detailsEntity.setTestCost(detailVO.getTestCost());
                detailsEntity.setTestCostRatio(detailVO.getTestCostRatio());
                detailsEntity.setAdvertisementCost(detailVO.getAdvertisementCost());
                detailsEntity.setAdvertisementCostRatio(detailVO.getAdvertisementCostRatio());
                detailsEntity.setCouponCost(detailVO.getCouponCost());
                detailsEntity.setCouponCostRatio(detailVO.getCouponCostRatio());
                detailsEntity.setDealCost(detailVO.getDealCost());
                detailsEntity.setDealCostRatio(detailVO.getDealCostRatio());
                detailsEntity.setOutsidePromotionCost(detailVO.getOutsidePromotionCost());
                detailsEntity.setOutsidePromotionCostRatio(detailVO.getOutsidePromotionCostRatio());
                detailsEntity.setFirstUnitPrice(detailVO.getFirstUnitPrice());
                detailsEntity.setFirstFreight(detailVO.getFirstFreight());
                detailsEntity.setBreakageCost(detailVO.getBreakageCost());
                detailsEntity.setRealSaleAmount(detailVO.getRealSaleAmount());
                detailsEntity.setTotalCost(detailVO.getTotalCost());
                detailsEntity.setGrossProfit(detailVO.getGrossProfit());
                detailsEntity.setGrossRate(detailVO.getGrossRate());
                detailsEntity.setDayGrossRate(detailVO.getDayGrossRate());
                entityList.add(detailsEntity);
            }
            //doubleList.add(entityList);
            vo.setWeight(detailList.get(0).getWeight());
            vo.setPlanDate(list.get(i));
            vo.setEntity(entity);
            vo.setList(entityList);
            voList.add(vo);
        }
        return voList;
    }

    @Override
    public void update(List<UpdateSalePlanVO> voList, String salePlanId, String userId) {
        //List<AddSalePlanVO> salePlanVOList = getDetail(salePlanId, 1);
        SalePlanInfoEntity entity = salePlanInfoDao.get(salePlanId);
        entity.setModifyTime(Time.getCurrentDateTime());
        entity.setModifyUser(userId);
        salePlanInfoDao.update(entity);
        for (UpdateSalePlanVO vo : voList) {
            List<UpdateSalePlanCycleDetailsVo> detailsVoList = vo.getVoList();
            for (UpdateSalePlanCycleDetailsVo detailsVo : detailsVoList) {
                detailsVo.setWeight(vo.getWeight());
                salePlanCycleDetailsDao.update(detailsVo);
            }
                    /*for (UpdateSalePlanCycleDetailsVo updateDetailVo : updateSalePlanCycleDetailsVoList) {
                        List<SalePlanCycleDetailsEntity> finalList = new ArrayList<>();
                        for (SalePlanCycleDetailsEntity detailsEntity : detailsVoList) {
                            detailsEntity.setWeight(vo.getWeight());
                            if (updateDetailVo.getUuid().equals(detailsEntity.getUuid())) {
                                detailsEntity.setCostPrice(updateDetailVo.getCostPrice());
                                detailsEntity.setSalePrice(updateDetailVo.getSalePrice());
                                detailsEntity.setDaySaleNum(updateDetailVo.getDaySaleNum());
                                detailsEntity.setSaleTime(updateDetailVo.getSaleTime());
                                detailsEntity.setEstimateRefundableRate(updateDetailVo.getEstimateRefundableRate());
                                detailsEntity.setCommissionCoefficient(updateDetailVo.getCommissionCoefficient());
                                detailsEntity.setTestCost(updateDetailVo.getTestCost());
                                detailsEntity.setAdvertisementCost(updateDetailVo.getAdvertisementCost());
                                detailsEntity.setCouponCost(updateDetailVo.getCouponCost());
                                detailsEntity.setDealCost(updateDetailVo.getDealCost());
                                detailsEntity.setOutsidePromotionCost(updateDetailVo.getOutsidePromotionCost());
                                detailsEntity.setFirstUnitPrice(updateDetailVo.getFirstUnitPrice());

                                detailsEntity.setSaleAmount(detailsEntity.getDaySaleNum() * detailsEntity.getSaleTime() * detailsEntity.getSalePrice());
                                detailsEntity.setCommission(detailsEntity.getSaleAmount() * detailsEntity.getCommissionCoefficient() * (1 + detailsEntity.getEstimateRefundableRate() * 0.2));
                                if ((detailsEntity.getWeight() / 0.4536 + 0.25) > 2) {
                                    detailsEntity.setFulfillmentCost((4.18 + (int) (detailsEntity.getWeight() / 0.4536 + 0.25 - 2) * 0.39) * detailsEntity.getDaySaleNum() * detailsEntity.getSaleTime());
                                } else if ((detailsEntity.getWeight() / 0.4536 + 0.25) < 1) {
                                    detailsEntity.setFulfillmentCost(3.19 * detailsEntity.getDaySaleNum() * detailsEntity.getSaleTime());
                                } else {
                                    detailsEntity.setFulfillmentCost(4.18 * detailsEntity.getDaySaleNum() * detailsEntity.getSaleTime());
                                }
                                detailsEntity.setMarketingCost(detailsEntity.getTestCost() + detailsEntity.getAdvertisementCost() + detailsEntity.getCouponCost() + detailsEntity.getDealCost() + detailsEntity.getOutsidePromotionCost());
                                detailsEntity.setMarketingCostRatio(detailsEntity.getMarketingCost() / detailsEntity.getSaleAmount());
                                detailsEntity.setTestCostRatio(detailsEntity.getTestCost() / detailsEntity.getSaleAmount());
                                detailsEntity.setAdvertisementCostRatio(detailsEntity.getAdvertisementCost() / detailsEntity.getSaleAmount());
                                detailsEntity.setCouponCostRatio(detailsEntity.getCouponCost() / detailsEntity.getSaleAmount());
                                detailsEntity.setDealCostRatio(detailsEntity.getDealCost() / detailsEntity.getSaleAmount());
                                detailsEntity.setOutsidePromotionCostRatio(detailsEntity.getOutsidePromotionCost() / detailsEntity.getSaleAmount());
                                detailsEntity.setFirstFreight(detailsEntity.getFirstUnitPrice() * detailsEntity.getWeight() * detailsEntity.getDaySaleNum() * detailsEntity.getSaleTime());
                                detailsEntity.setBreakageCost((detailsEntity.getCostPrice() * detailsEntity.getDaySaleNum() * detailsEntity.getSaleTime() + detailsEntity.getFirstFreight()) * detailsEntity.getEstimateRefundableRate() * 0.66);
                                detailsEntity.setRealSaleAmount(detailsEntity.getSaleAmount() * (1 - detailsEntity.getEstimateRefundableRate()));
                                detailsEntity.setTotalCost(detailsEntity.getCostPrice() * detailsEntity.getDaySaleNum() * detailsEntity.getSaleTime() + detailsEntity.getCommission() + detailsEntity.getFulfillmentCost() + detailsEntity.getMarketingCost() + detailsEntity.getFirstFreight() + detailsEntity.getBreakageCost());
                                detailsEntity.setGrossProfit(detailsEntity.getTotalCost() - detailsEntity.getRealSaleAmount());
                                detailsEntity.setGrossRate(detailsEntity.getGrossProfit() / detailsEntity.getRealSaleAmount());
                                detailsEntity.setDayGrossRate(detailsEntity.getGrossProfit() / detailsEntity.getSaleTime());
                                salePlanCycleDetailsDao.update(detailsEntity);
                            }
                            //finalList.add(detailsEntity);
                        }
                        //SalePlanCycleDetailsEntity updateVo = SalePlanInfoServiceImpl.countTotal(finalList);
                        //salePlanCycleDetailsDao.updateTotal(updateVo);

                    }*/
            //}
            //}
        }
    }

    @Override
    public void updateEntity(SalePlanCycleDetailsEntity detailVO) {
        salePlanCycleDetailsDao.updateEntity(detailVO);
    }

    @Override
    public Workbook getResult(List<SelectSalePlanListVO> voList) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();
        //List planIdList = new ArrayList();
        String[] excelHeader = {"类目主管", "负责人", "店铺", "MSKU", "ASIN", "商品状态", "库存SKU", "产品名称", "月份", "周期", "重量(KG)", "成本价($)", "售价($)",
                "预计日销量", "销售时长", "销售额($)", "预计退款率", "佣金系数", "佣金", "FulfillmentCost（$）", "营销费用（$）", "营销费用占比", "测评费用（$）", "测评费用占比",
                "广告费用（$）", "广告费用占比", "优惠券费用（$）", "优惠券费用占比", "Deal费用（$）", "Deal费用占比", "站外推广费用（$）", "站外推广费用占比", "头程单价（$）", "头程运费（$）",
                "破损成本（$）", "实际销售额（$）", "总成本费用（$）", "毛利润（$）", "毛利率", "日均毛利润（$）"};
        if (voList.size() > 0) {
            for (int j = 0; j < voList.size(); j++) {
                List<SelectSalePlanDetailVO> list = salePlanCycleDetailsDao.getResult(voList.get(j).getSalePlanId());
                getMskuInfo(list);
                XSSFSheet sheet = workbook.createSheet("销售计划表" + j);
                int rowNum = 1;
                XSSFRow row = sheet.createRow(0);
                for (int i = 0; i < excelHeader.length; i++) {
                    XSSFCell cell = row.createCell(i);
                    XSSFRichTextString text = new XSSFRichTextString(excelHeader[i]);
                    cell.setCellValue(text);
                }

                String directorEmployeeName = voList.get(j).getDirectorEmployeeName();
                if (directorEmployeeName == null) {
                    directorEmployeeName = "";
                }
                String chargeEmployeeName = voList.get(j).getChargeEmployeeName();
                if (chargeEmployeeName == null) {
                    chargeEmployeeName = "";
                }
                if (list.size() > 0) {
                    //在表中存放查询到的数据放入对应的列
                    for (int i = 0; i < list.size(); i++) {
                        XSSFRow row1 = sheet.createRow(rowNum);
                        row1.createCell(0).setCellValue(directorEmployeeName);
                        row1.createCell(1).setCellValue(chargeEmployeeName);
                        row1.createCell(2).setCellValue(list.get(i).getShopName());
                        row1.createCell(3).setCellValue(list.get(i).getMskuId());
                        row1.createCell(4).setCellValue(list.get(i).getAsin());
                        row1.createCell(5).setCellValue(list.get(i).getCommodityStatus());
                        row1.createCell(6).setCellValue(list.get(i).getStockSku());
                        row1.createCell(7).setCellValue(list.get(i).getCommodityName());
                        row1.createCell(8).setCellValue(list.get(i).getPlanDate());
                        row1.createCell(9).setCellValue(list.get(i).getSaleCycle());
                        row1.createCell(10).setCellValue(list.get(i).getWeight());
                        row1.createCell(11).setCellValue(list.get(i).getCostPrice());
                        row1.createCell(12).setCellValue(list.get(i).getSalePrice());
                        row1.createCell(13).setCellValue(list.get(i).getDaySaleNum());
                        row1.createCell(14).setCellValue(list.get(i).getSaleTime());
                        row1.createCell(15).setCellValue(list.get(i).getSaleAmount());
                        row1.createCell(16).setCellValue(list.get(i).getEstimateRefundableRate());
                        row1.createCell(17).setCellValue(list.get(i).getCommissionCoefficient());
                        row1.createCell(18).setCellValue(list.get(i).getCommission());
                        row1.createCell(19).setCellValue(list.get(i).getFulfillmentCost());
                        row1.createCell(20).setCellValue(list.get(i).getMarketingCost());
                        row1.createCell(21).setCellValue(list.get(i).getMarketingCostRatio());
                        row1.createCell(22).setCellValue(list.get(i).getTestCost());
                        row1.createCell(23).setCellValue(list.get(i).getTestCostRatio());
                        row1.createCell(24).setCellValue(list.get(i).getAdvertisementCost());
                        row1.createCell(25).setCellValue(list.get(i).getAdvertisementCostRatio());
                        row1.createCell(26).setCellValue(list.get(i).getCouponCost());
                        row1.createCell(27).setCellValue(list.get(i).getCouponCostRatio());
                        row1.createCell(28).setCellValue(list.get(i).getDealCost());
                        row1.createCell(29).setCellValue(list.get(i).getDealCostRatio());
                        row1.createCell(30).setCellValue(list.get(i).getOutsidePromotionCost());
                        row1.createCell(31).setCellValue(list.get(i).getOutsidePromotionCostRatio());
                        row1.createCell(32).setCellValue(list.get(i).getFirstUnitPrice());
                        row1.createCell(33).setCellValue(list.get(i).getFirstFreight());
                        row1.createCell(34).setCellValue(list.get(i).getBreakageCost());
                        row1.createCell(35).setCellValue(list.get(i).getRealSaleAmount());
                        row1.createCell(36).setCellValue(list.get(i).getTotalCost());
                        row1.createCell(37).setCellValue(list.get(i).getGrossProfit());
                        row1.createCell(38).setCellValue(list.get(i).getGrossRate());
                        row1.createCell(39).setCellValue(list.get(i).getDayGrossRate());
                        rowNum++;
                    }
                }
            }
        } else {
            XSSFSheet sheet = workbook.createSheet("销售计划表");
            XSSFRow row = sheet.createRow(0);
            for (int i = 0; i < excelHeader.length; i++) {
                XSSFCell cell = row.createCell(i);
                XSSFRichTextString text = new XSSFRichTextString(excelHeader[i]);
                cell.setCellValue(text);
            }
        }
        return workbook;
    }

    @Override
    public List<SelectSalePlanDetailVO> getRecord(String planDate, String commodityId) {
        return salePlanCycleDetailsDao.getRecord(planDate, commodityId);
    }

    @Override
    public List<SalePlanCycleDetailsEntity> getDateById(String salePlanId) {
        return salePlanCycleDetailsDao.getDateById(salePlanId);
    }

    @Override
    public void deleteDetail(String salePlanId, List planDates) {
        salePlanCycleDetailsDao.deleteDetail(salePlanId, planDates);
    }

    private void getMskuInfo(List<SelectSalePlanDetailVO> list) {
        Map<String, Object> mskuMap = new HashMap<>();
        Set<String> set = new HashSet();
        for (SelectSalePlanDetailVO vo : list) {
            set.add(vo.getCommodityId());
        }
        String[] ids = new String[set.size()];
        Result mskuResult = mskuService.getProduct(set.toArray(ids));
        Map map = (Map) mskuResult.getData();
        List mskuList = (List) map.get("mskuInfoBatch");
        if (mskuList != null && mskuList.size() > 0) {
            for (Object object : mskuList) {
                Map finalMap = (Map) object;
                mskuMap.put((String) finalMap.get("id"), finalMap);
            }
        }
        for (SelectSalePlanDetailVO vo : list) {
            Map voMap = (Map) mskuMap.get(vo.getCommodityId());
            vo.setAsin((String) voMap.get("asin"));
            vo.setShopName((String) voMap.get("shopName"));
            vo.setStockSku((String) voMap.get("skuId"));
            vo.setCommodityName((String) voMap.get("productName"));
            vo.setCommodityStatus((String) voMap.get("salesStatusDesc"));
        }
    }

    private void getMskuInfo(SalePlanInfoEntity vo, String userId) {
        HashSet<String> employeeSet = new HashSet<>();
        Set<String> mskuIdSet = new HashSet();
        mskuIdSet.add(vo.getCommodityId());
        Map<String, Object> mskuMap = salePlanCommonService.getMskuMap(mskuIdSet);
        Map voMap = (Map) mskuMap.get(vo.getCommodityId());
        vo.setAsin((String) voMap.get("asin"));
        vo.setShopName((String) voMap.get("shopName"));
        vo.setStockSku((String) voMap.get("skuId"));
        vo.setCommodityName((String) voMap.get("productName"));
        vo.setCommodityStatus((String) voMap.get("salesStatusDesc"));
        vo.setChargeEmployeeId((String) voMap.get("userId"));
        employeeSet.add(vo.getChargeEmployeeId());
        Map<String, ExecutiveDirectorVO> executiveDirectorVOMap = salePlanCommonService.getExecutiveDirector(employeeSet);
        ExecutiveDirectorVO executiveDirectorVO = executiveDirectorVOMap.get(vo.getChargeEmployeeId());
        if (executiveDirectorVO == null) {
            //如果没有对应的人员，那么应该把id置空
            vo.setChargeEmployeeId(null);
            return;
        }
        vo.setChargeEmployeeName(executiveDirectorVO.getChargeEmployeeName());
        vo.setDirectorEmployeeId(executiveDirectorVO.getExecutiveDirectorId());
        vo.setDirectorEmployeeName(executiveDirectorVO.getExecutiveDirectorName());
    }
}
