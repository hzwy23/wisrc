package com.wisrc.sales.webapp.service;

import com.wisrc.sales.webapp.vo.ExecutiveDirectorVO;
import com.wisrc.sales.webapp.vo.UserInfoVO;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * SalePlanCommonService
 *
 * @author MAJANNING
 * @date 2018/7/5
 */
public interface SalePlanCommonService {
    /**
     * 获取msku相关信息
     *
     * @param mskuIdSet
     * @return
     */
    Map<String, Object> getMskuMap(Set<String> mskuIdSet);

    /**
     * 获取类目主管相关信息
     *
     * @param employeeSet
     * @return
     */
    Map<String, ExecutiveDirectorVO> getExecutiveDirector(HashSet<String> employeeSet);

    /**
     * 获取用户相关信息
     *
     * @param userIdSet
     * @return 用户集合，键为userId
     */
    Map<String, UserInfoVO> getUserInfo(HashSet<String> userIdSet);
}
