package com.wisrc.sales.webapp.service.impl;

import com.wisrc.sales.webapp.service.SalePlanCommonService;
import com.wisrc.sales.webapp.service.externalService.MskuService;
import com.wisrc.sales.webapp.service.externalService.SysManageService;
import com.wisrc.sales.webapp.utils.Result;
import com.wisrc.sales.webapp.vo.ExecutiveDirectorVO;
import com.wisrc.sales.webapp.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * SalePlanCommonServiceImpl
 *
 * @author MAJANNING
 * @date 2018/7/5
 */
@Service
public class SalePlanCommonServiceImpl implements SalePlanCommonService {
    @Autowired
    private MskuService mskuService;
    @Autowired
    private SysManageService sysManageService;

    /**
     * 获取msku相关信息
     *
     * @param mskuIdSet
     * @return
     */
    @Override
    public Map<String, Object> getMskuMap(Set<String> mskuIdSet) {
        String[] ids = new String[mskuIdSet.size()];
        mskuIdSet.toArray(ids);
        Map mskuMap = new HashMap();
        Result mskuResult = mskuService.getProduct(ids);
        if (mskuResult.getCode() == 200) {
            Map map = (Map) mskuResult.getData();
            List mskuList = (List) map.get("mskuInfoBatch");
            if (mskuList != null && mskuList.size() > 0) {
                for (Object object : mskuList) {
                    Map finalMap = (Map) object;
                    mskuMap.put((String) finalMap.get("id"), finalMap);
                }
            }
        }
        return mskuMap;
    }

    /**
     * 获取类目主管相关信息
     *
     * @param employeeSet
     * @return
     */
    @Override
    public Map<String, ExecutiveDirectorVO> getExecutiveDirector(HashSet<String> employeeSet) {
        //查询岗位相关信息
        Map<String, ExecutiveDirectorVO> executiveDirectorMap = new HashMap<>();
        String[] employeeIds = new String[employeeSet.size()];
        Result employeeResult = sysManageService.getStructuresByEmployeeIds(employeeSet.toArray(employeeIds));
        if (employeeResult.getCode() == 200) {
            List objects = (List) employeeResult.getData();
            for (Object o : objects) {
                Map empMap = (Map) o;
                String chargeEmployeeId = (String) empMap.get("employeeId");//负责人id
                if (executiveDirectorMap.get(chargeEmployeeId) == null) {
                    //如果有多个 那么取第一个 所以只放第一个 同一个负责人的sku主管相同
                    String chargeEmployeeName = (String) empMap.get("employeeName");//负责人
                    String executiveDirectorId = (String) empMap.get("executiveDirectorId"); //类目主管id
                    String executiveDirectorName = (String) empMap.get("executiveDirectorName");//类目主管
                    ExecutiveDirectorVO executiveDirectorVO =
                            new ExecutiveDirectorVO(chargeEmployeeId, chargeEmployeeName,
                                    executiveDirectorId, executiveDirectorName);
                    executiveDirectorMap.put(chargeEmployeeId, executiveDirectorVO);
                }
            }
        }
        return executiveDirectorMap;
    }

    /**
     * 获取用户相关信息
     *
     * @param userIdSet
     * @return 用户集合，键为userId
     * @see UserInfoVO
     */
    @Override
    public Map<String, UserInfoVO> getUserInfo(HashSet<String> userIdSet) {
        Map<String, UserInfoVO> userInfoVOMap = new HashMap<>();
        String[] ids = new String[userIdSet.size()];
        Result usersResult = sysManageService.getUsersByEmployeeIds(userIdSet.toArray(ids));
        if (usersResult.getCode() == 200) {
            List<Map<String, String>> dataList = (List<Map<String, String>>) usersResult.getData();
            for (Map<String, String> data : dataList) {
                String userId = data.get("userId");
                String userName = data.get("userName");
                String employeeId = data.get("employeeId");
                String employeeName = data.get("employeeName");
                String positionCd = data.get("positionCd");
                UserInfoVO userInfoVO = new UserInfoVO(userId, userName, employeeId, employeeName, positionCd);
                userInfoVOMap.put(userId, userInfoVO);
            }
        }
        return userInfoVOMap;
    }
}
