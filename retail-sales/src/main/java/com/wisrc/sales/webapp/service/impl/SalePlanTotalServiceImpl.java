package com.wisrc.sales.webapp.service.impl;

import com.wisrc.sales.webapp.dao.SalePlanInfoDao;
import com.wisrc.sales.webapp.service.SalePlanCommonService;
import com.wisrc.sales.webapp.service.SalePlanTotalService;
import com.wisrc.sales.webapp.service.externalService.SysManageService;
import com.wisrc.sales.webapp.utils.ArrayToInArguments;
import com.wisrc.sales.webapp.utils.Result;
import com.wisrc.sales.webapp.vo.ExecutiveDirectorVO;
import com.wisrc.sales.webapp.vo.SelectTotalInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SalePlanTotalServiceImpl implements SalePlanTotalService {
    @Autowired
    private SalePlanInfoDao salePlanInfoDao;

    @Autowired
    private SalePlanCommonService salePlanCommonService;

    @Autowired
    private SysManageService mskuService;

    // 调用MSKU权限接口，查询用户能够访问到的MSKU信息
    private String getAccessMsku(String userId) {
        Result ret = mskuService.getAccessMsku(userId);
        if (ret == null || ret.getCode() != 200) {
            return null;
        }

        List<String> commodityList = (List<String>) ret.getData();

        return ArrayToInArguments.toInArgs(commodityList);

    }

    @Override
    public List<SelectTotalInfoVO> getTotal(String shopId, String msku, String asin, String directorEmployeeId,
                                            String chargeEmployeeId, String startMonth, String endMonth, String salesStatusCd, String userId) {

        String commodityIdListStr = getAccessMsku(userId);

        List<SelectTotalInfoVO> list = salePlanInfoDao.getTotal(shopId, msku, startMonth, endMonth, commodityIdListStr);

        if (list.size() > 0) {
            getMskuInfo(list);
            if (asin != null && !"".equals(asin)) {
                for (int i = 0; i < list.size(); i++) {
                    if (!list.get(i).getAsin().contains(asin.trim())) {
                        list.remove(list.get(i));
                        i--;
                    }
                }
            }
            if (directorEmployeeId != null && !"".equals(directorEmployeeId)) {
                for (int i = 0; i < list.size(); i++) {
                    if (!list.get(i).getDirectorEmployeeId().contains(directorEmployeeId.trim())) {
                        list.remove(list.get(i));
                        i--;
                    }
                }
            }
            if (chargeEmployeeId != null && !"".equals(chargeEmployeeId)) {
                for (int i = 0; i < list.size(); i++) {
                    if (!list.get(i).getChargeEmployeeId().contains(chargeEmployeeId.trim())) {
                        list.remove(list.get(i));
                        i--;
                    }
                }
            }
        }
        //过滤状态
        if (null != salesStatusCd && !salesStatusCd.trim().isEmpty()) {
            list = list.parallelStream().filter(selectTotalInfoVO ->
                    selectTotalInfoVO.getCommodityStatusId().equals(salesStatusCd)
            ).collect(Collectors.toList());
        }

        /*       排序
        按照类目主管排序，
        同一类目主管按照负责人排序
        同一负责人按照店铺进行排序
        同一店铺按照MSKU进行排序
        同一MSKU按照月份倒序进行排序
        */
        Collections.sort(list, (o1, o2) -> {
            int result = compareString(o1.getDirectorEmployeeId(), o2.getDirectorEmployeeId());
            if (result != 0) {
                return result;
            }
            result = compareString(o1.getChargeEmployeeId(), o2.getChargeEmployeeId());
            if (result != 0) {
                return result;
            }
            result = compareString(o1.getShopId(), o2.getShopId());
            if (result != 0) {
                return result;
            }
            result = compareString(o1.getMskuId(), o2.getMskuId());
            if (result != 0) {
                return result;
            }
            //日期倒序
            return compareString(o2.getPlanDate(), o1.getPlanDate());
        });
        return list;
    }

    /**
     * 字符串排序,<code>null</code>和空视为相等
     *
     * @param st1
     * @param st2
     * @return
     */
    private int compareString(String st1, String st2) {
        if (st1 == null || st1.trim().isEmpty()) {
            st1 = "";
        }
        if (st2 == null || st2.trim().isEmpty()) {
            st2 = "";
        }
        return st1.compareTo(st2);
    }


    private void getMskuInfo(List<SelectTotalInfoVO> list) {
        HashSet<String> employeeSet = new HashSet<>();
        Set<String> mskuIdSet = new HashSet();
        for (SelectTotalInfoVO vo : list) {
            mskuIdSet.add(vo.getCommodityId());
        }
        Map<String, Object> mskuMap = salePlanCommonService.getMskuMap(mskuIdSet);
        for (SelectTotalInfoVO vo : list) {
            Map voMap = (Map) mskuMap.get(vo.getCommodityId());
            if (voMap != null) {
                vo.setAsin((String) voMap.get("asin"));
                vo.setShopName((String) voMap.get("shopName"));
                vo.setStockSku((String) voMap.get("skuId"));
                vo.setCommodityName((String) voMap.get("productName"));
                vo.setCommodityStatus((String) voMap.get("salesStatusDesc"));
                vo.setCommodityStatusId(voMap.get("salesStatusCd").toString());
                vo.setChargeEmployeeId((String) voMap.get("userId"));
            }
            employeeSet.add(vo.getChargeEmployeeId());
        }
        Map<String, ExecutiveDirectorVO> executiveDirectorVOMap = salePlanCommonService.getExecutiveDirector(employeeSet);
        for (SelectTotalInfoVO vo : list) {
            ExecutiveDirectorVO executiveDirectorVO = executiveDirectorVOMap.get(vo.getChargeEmployeeId());
            if (executiveDirectorVO == null) {
                //如果没有对应的人员，那么应该把id置空
                vo.setChargeEmployeeId(null);
                continue;
            }
            vo.setChargeEmployeeName(executiveDirectorVO.getChargeEmployeeName());
            vo.setDirectorEmployeeId(executiveDirectorVO.getExecutiveDirectorId());
            vo.setDirectorEmployeeName(executiveDirectorVO.getExecutiveDirectorName());
        }
    }
}
