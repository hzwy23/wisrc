package com.wisrc.merchandise.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisrc.merchandise.dao.MskuInfoDao;
import com.wisrc.merchandise.dao.MskuSalesPlanDao;
import com.wisrc.merchandise.dto.msku.MskuPlanPageDTO;
import com.wisrc.merchandise.dto.mskuSalesPlan.GetSalesPlanDTO;
import com.wisrc.merchandise.dto.mskuSalesPlan.MskuSalesPlanPageDTO;
import com.wisrc.merchandise.dto.mskuSalesPlan.PlanPageDTO;
import com.wisrc.merchandise.dto.mskuSalesPlan.SalesPlanPageDto;
import com.wisrc.merchandise.entity.MskuPageEntity;
import com.wisrc.merchandise.entity.MskuSalesDefineEntity;
import com.wisrc.merchandise.entity.MskuSalesPlanEntity;
import com.wisrc.merchandise.entity.MskuSalesPlanPageEntity;
import com.wisrc.merchandise.query.DistinctSalesDefineQuery;
import com.wisrc.merchandise.service.MskuSalesPlanService;
import com.wisrc.merchandise.service.ProductService;
import com.wisrc.merchandise.service.SalesStatusService;
import com.wisrc.merchandise.utils.Result;
import com.wisrc.merchandise.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.wisrc.merchandise.utils.Crypto.sha;

@Service
public class MskuSalesPlanServiceImpl implements MskuSalesPlanService {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    private MskuSalesPlanDao mskuSalesPlanDao;
    @Autowired
    private SalesStatusService salesStatusService;
    @Autowired
    private MskuInfoDao mskuInfoDao;
    @Autowired
    private ProductService productService;

    @Override
    public Result getSalesPlanList(MskuSalesPlanPageVo mskuSalesPlanPageVo) {
        List<MskuSalesPlanPageDTO> SalesPlanList = new ArrayList<>();
        List<String> planIds = new ArrayList();
        Map<String, List<PlanPageDTO>> id2plans = new HashMap<>();
        Map<String, String> planId2id = new HashMap<>();
        Map salesStatus = new HashMap();
        SalesPlanPageDto results = new SalesPlanPageDto();
        List<MskuSalesPlanPageEntity> salesPlans = null;
        List<MskuPageEntity> mskuList = null;
        List storeSkuDealted = null;
        List<Map<String, Object>> salesStatuses = null;

        // 根据关键字关联产品筛选商品
        if (mskuSalesPlanPageVo.getFindKey() != null) {
            try {
                storeSkuDealted = productService.getFindKeyDealted(mskuSalesPlanPageVo.getFindKey());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            DistinctSalesDefineQuery distinctSalesDefineQuery = new DistinctSalesDefineQuery();
            BeanUtils.copyProperties(mskuSalesPlanPageVo, distinctSalesDefineQuery);
            distinctSalesDefineQuery.setStoreSkuDealted(storeSkuDealted);

            PageHelper.startPage(mskuSalesPlanPageVo.getCurrentPage(), mskuSalesPlanPageVo.getPageSize());
            List<String> ids = mskuSalesPlanDao.distinctSalesDefine(distinctSalesDefineQuery);
            if (ids.size() > 0) {
                mskuList = mskuInfoDao.getMskuByPlan(ids);
                salesPlans = mskuSalesPlanDao.getSalesPlanByIds(ids);

                for (MskuSalesPlanPageEntity idAndPlanId : salesPlans) {
                    planId2id.put(idAndPlanId.getPlanId(), idAndPlanId.getId());
                    planIds.add(idAndPlanId.getPlanId());
                }
            } else {
                mskuList = new ArrayList<>();
                salesPlans = new ArrayList<>();
            }

            salesStatus = salesStatusService.getSalesStatusMap();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        PageInfo pageInfo = new PageInfo(mskuList);
        long totalNum = pageInfo.getTotal();

        // 循环销售计划，把相同商品的销售计划组合到一起
        for (MskuSalesPlanEntity mskuSalesPlanEntity : salesPlans) {
            String expiryDate = null;

            if (mskuSalesPlanEntity.getExpiryDate() != null)
                expiryDate = df.format(mskuSalesPlanEntity.getExpiryDate());

            PlanPageDTO plan = new PlanPageDTO();
            BeanUtils.copyProperties(mskuSalesPlanEntity, plan);
            plan.setSalesStatus((Map) salesStatus.get(mskuSalesPlanEntity.getSalesStatusCd()));
            plan.setStartDate(df.format(mskuSalesPlanEntity.getStartDate()));
            plan.setExpiryDate(expiryDate);
            plan.setExptDailySales(mskuSalesPlanEntity.getExpectedDailySales());

            if (id2plans.get(planId2id.get(mskuSalesPlanEntity.getPlanId())) != null) {
                id2plans.get(planId2id.get(mskuSalesPlanEntity.getPlanId())).add(plan);
            } else {
                List<PlanPageDTO> plans = new ArrayList<>();
                plans.add(plan);
                id2plans.put(planId2id.get(mskuSalesPlanEntity.getPlanId()), plans);
            }
        }

        // 封装商品信息
        for (MskuPageEntity msku : mskuList) {
            String shelfTime;
            try {
                shelfTime = df.format(msku.getShelfTime());
            } catch (Exception e) {
                shelfTime = null;
            }

            MskuSalesPlanPageDTO salesPlan = new MskuSalesPlanPageDTO();
            BeanUtils.copyProperties(msku, salesPlan);
            salesPlan.setMsku(msku.getMskuId());
            salesPlan.setTeam(msku.getShopName());
            salesPlan.setManager(msku.getUserId());
            salesPlan.setASIN(msku.getAsin());
            salesPlan.setPlans(id2plans.get(msku.getId()));
            salesPlan.setShelfTime(shelfTime);

            SalesPlanList.add(salesPlan);
        }

        // 获取销售计划状态码表
        try {
            salesStatuses = salesStatusService.getSalesStatusList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        List teamStatus = getTeamStatus();

        results.setSalesStatus(salesStatuses);
        results.setTeamStatus(teamStatus);
        results.setPlanList(SalesPlanList);
        results.setTotalNum(totalNum);

        return new Result(200, "", results);
    }

    @Override
    public Result getSalesPlan(String id) {
        List<MskuPlanPageDTO> mskuPlan = new ArrayList<>();
        GetSalesPlanDTO results = new GetSalesPlanDTO();
        Map salesStatuses = new HashMap();

        List<MskuSalesPlanPageEntity> planList = null;
        try {
            planList = mskuSalesPlanDao.getSalesPlanById(id);
            salesStatuses = salesStatusService.getSalesStatusMap();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        for (MskuSalesPlanEntity mskuSalesPlanEntity : planList) {
            String expiryDate = null;
            String startDate = null;

            if (mskuSalesPlanEntity.getExpiryDate() != null) {
                expiryDate = df.format(mskuSalesPlanEntity.getExpiryDate());
            }
            if (mskuSalesPlanEntity.getStartDate() != null) {
                startDate = df.format(mskuSalesPlanEntity.getStartDate());
            }

            MskuPlanPageDTO salesPlan = new MskuPlanPageDTO();
            BeanUtils.copyProperties(mskuSalesPlanEntity, salesPlan);
            salesPlan.setSalesStatus(String.valueOf(((Map) salesStatuses.get(mskuSalesPlanEntity.getSalesStatusCd())).get("name")));
            salesPlan.setStartDate(startDate);
            salesPlan.setExpiryDate(expiryDate);
            salesPlan.setExpectedDailySales(mskuSalesPlanEntity.getExpectedDailySales());
            mskuPlan.add(salesPlan);
        }

        results.setSalesPlan(mskuPlan);
        return new Result(200, "", results);
    }

    @Override
    @Transactional(transactionManager = "retailMerchandiseTransactionManager")
    public Result saveSalesPlan(SalesMskuSaveVo salesMskuSaveVo, BindingResult bindingResult) {
        List errList = BindingErr(bindingResult);
        if (errList != null && errList.size() > 0) {
            return new Result(400, "", errList);
        }

        MskuSalesDefineEntity mskuSalesDefineEntity = new MskuSalesDefineEntity();

        String id = sha(salesMskuSaveVo.getMsku(), salesMskuSaveVo.getShopId());
        mskuSalesDefineEntity.setId(id);

        String msg = checkDateSection(salesMskuSaveVo.getPlans());
        if (msg != null) {
            return new Result(400, "", msg);
        }

        for (MskuSalesPlanSaveVo plan : salesMskuSaveVo.getPlans()) {
            MskuSalesPlanEntity mskuSalesPlanEntity = new MskuSalesPlanEntity();

            String planId;
            try {
                planId = String.valueOf(mskuSalesPlanDao.getNewId());
            } catch (Exception e) {
                planId = "1";
            }
            mskuSalesPlanEntity.setPlanId(planId);
            mskuSalesPlanEntity.setExpectedDailySales(plan.getExptDailySales());
            mskuSalesPlanEntity.setGuidePrice(plan.getGuidePrice());
            mskuSalesPlanEntity.setSalesStatusCd(plan.getSalesStatus());
            mskuSalesPlanEntity.setStartDate(plan.getStartDate());
            if (plan.getExpiryDate() != null) {
                mskuSalesPlanEntity.setExpiryDate(plan.getExpiryDate());
            }
            mskuSalesDefineEntity.setPlanId(planId);
            mskuSalesDefineEntity.setMskuStatusCd(1);

            try {
                mskuSalesPlanDao.savePlanDefine(mskuSalesDefineEntity);
                mskuSalesPlanDao.saveSalesPlan(mskuSalesPlanEntity);
            } catch (Exception e) {
                e.printStackTrace();
                return Result.failure();
            }
        }

        return new Result(200, "", null);
    }

    @Override
    public Result editMskuSalesPlan(SalesMskuEditVo salesMskuEditVo, BindingResult bindingResult) {
        List errList = BindingErr(bindingResult);
        if (errList != null && errList.size() > 0) {
            return new Result(400, "", errList);
        }

        String msg = checkDateSection(salesMskuEditVo.getPlans());
        if (msg != null) {
            return new Result(400, "", msg);
        }

        List ids = mskuSalesPlanDao.getPlanEditId(salesMskuEditVo.getId());
        for (MskuSalesPlanEditVo plan : salesMskuEditVo.getPlans()) {
            String planId = plan.getPlanId();
            MskuSalesPlanEntity mskuSalesPlanEntity = new MskuSalesPlanEntity();

            mskuSalesPlanEntity.setPlanId(planId);
            mskuSalesPlanEntity.setExpectedDailySales(plan.getExptDailySales());
            mskuSalesPlanEntity.setGuidePrice(plan.getGuidePrice());
            mskuSalesPlanEntity.setSalesStatusCd(plan.getSalesStatus());
            mskuSalesPlanEntity.setStartDate(plan.getStartDate());
            if (plan.getExpiryDate() != null) {
                mskuSalesPlanEntity.setExpiryDate(plan.getExpiryDate());
            }

            int index = ids.indexOf(plan.getPlanId());
            if (index != -1) {
                try {
                    mskuSalesPlanDao.editMskuSalesPlan(mskuSalesPlanEntity);
                    ids.remove(index);
                } catch (Exception e) {
                    e.printStackTrace();
                    return Result.failure();
                }
            } else {
                try {
                    String newPlanId = String.valueOf(mskuSalesPlanDao.getNewId());
                    MskuSalesDefineEntity mskuSalesDefineEntity = new MskuSalesDefineEntity();
                    mskuSalesDefineEntity.setPlanId(newPlanId);
                    mskuSalesDefineEntity.setId(salesMskuEditVo.getId());
                    mskuSalesDefineEntity.setMskuStatusCd(1);
                    mskuSalesPlanDao.savePlanDefine(mskuSalesDefineEntity);
                    mskuSalesPlanEntity.setPlanId(newPlanId);
                    mskuSalesPlanDao.saveSalesPlan(mskuSalesPlanEntity);
                } catch (Exception e) {
                    e.printStackTrace();
                    return Result.failure();
                }
            }
        }

        try {
            if (ids.size() > 0) mskuSalesPlanDao.deleteMskuSalesPlanInPlanId(ids);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        return new Result(200, "", null);
    }

    @Override
    public Result deleteSalesPlan(String planId) {
        MskuSalesDefineEntity mskuSalesDefineEntity = new MskuSalesDefineEntity();
        mskuSalesDefineEntity.setPlanId(planId);
        mskuSalesDefineEntity.setMskuStatusCd(3);
        try {
            mskuSalesPlanDao.deleteMskuSalesPlan(mskuSalesDefineEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        return new Result(200, "", null);
    }

    @Override
    public Result deleteMskuSalesPlan(String id) {
        MskuSalesDefineEntity mskuSalesDefineEntity = new MskuSalesDefineEntity();
        mskuSalesDefineEntity.setId(id);
        mskuSalesDefineEntity.setMskuStatusCd(3);
        try {
            mskuSalesPlanDao.deleteMskuSalesPlanById(mskuSalesDefineEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        return new Result(200, "获取参数id：" + id, null);
    }

    @Override
    public Result checkMskuShop(CheckMskuShopVo mskuShop) {
        Map result = new HashMap();

        try {
            String id = sha(mskuShop.getMskuId(), mskuShop.getShopId());
            MskuPageEntity mskuInfoEntity = mskuInfoDao.getMsku(id);
            List planId = mskuSalesPlanDao.getPlanEditId(id);
            if (planId.size() > 0) {
                return new Result(400, "", "MSKU编号重复");
            }
            if (mskuInfoEntity == null) {
                return new Result(400, "", "MSKU编号错误");
            }

            Map mskuChecked = new HashMap();
            mskuChecked.put("asin", mskuInfoEntity.getAsin());
            result.put("mskuChecked", mskuChecked);
            return Result.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }

    // 检查多个时间是否有错
    public String checkDateSection(List<? extends MskuSalesPlanSaveVo> plans) {
        List<List<Date>> dateTime = new ArrayList();
        for (MskuSalesPlanSaveVo plan : plans) {
            for (int m = 0; m < dateTime.size(); m++) {
                List<Date> dateSection = dateTime.get(m);
                if (dateSection.get(1) == null) {
                    // 如果结束时间大于永久有效的开始时间
                    if (plan.getExpiryDate() == null) {
                        return "日期有重复区间";
                    } else if (plan.getExpiryDate() != null && plan.getExpiryDate().getTime() > dateSection.get(0).getTime()) {
                        return "日期有重复区间";
                    }
                } else {
                    if (plan.getExpiryDate() == null) {
                        if (dateSection.get(1).getTime() > plan.getStartDate().getTime()) {
                            return "日期有重复区间";
                        }
                    } else if (!(plan.getExpiryDate().getTime() < dateSection.get(0).getTime() || plan.getStartDate().getTime() > dateSection.get(1).getTime())) {
                        return "日期有重复区间";
                    }
                }
            }

            if (plan.getExpiryDate() != null && plan.getStartDate().getTime() > plan.getExpiryDate().getTime()) {
                return "日期开始时间大于结束时间";
            }

            List dateList = new ArrayList();
            dateList.add(plan.getStartDate());
            dateList.add(plan.getExpiryDate());
            dateTime.add(dateList);
        }

        return null;
    }

    public List getTeamStatus() {
        List<Map<String, Object>> teamStatus = new ArrayList<>();

        Map teamTamp = idAndName(1, "test");
        teamStatus.add(teamTamp);

        Map teamTamp2 = idAndName(2, "test2");
        teamStatus.add(teamTamp2);

        return teamStatus;
    }

    public List BindingErr(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errList = new ArrayList<>();
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList) {
                errList.add(error.getDefaultMessage());
                break;
            }
            return errList;
        }
        return null;
    }

    public Map idAndName(Object id, String name) {
        Map returnMap = new HashMap();
        returnMap.put("id", id);
        returnMap.put("name", name);
        return returnMap;
    }
}
