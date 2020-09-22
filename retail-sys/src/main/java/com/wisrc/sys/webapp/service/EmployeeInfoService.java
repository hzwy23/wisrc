package com.wisrc.sys.webapp.service;

import com.wisrc.sys.webapp.vo.employee.AddEmployeeVO;
import com.wisrc.sys.webapp.vo.employee.SetEmployeeVO;
import com.wisrc.sys.webapp.entity.GatherEntity;
import com.wisrc.sys.webapp.entity.SysEmployeeInfoEntity;
import com.wisrc.sys.webapp.entity.VUserCategoryEntity;
import com.wisrc.sys.webapp.utils.Result;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface EmployeeInfoService {
    Result find(Integer pageNum, Integer pageSize, GatherEntity gatherEntity);

    Result update(SetEmployeeVO addEmployee, BindingResult bindingResult);

    Result add(AddEmployeeVO addEmployee, BindingResult bindingResult);

    Result getEmployeeDetail(String employeeId);

    Result getNewEmployeeId();

    void changeStatus(String employeeId, Integer statusCd);

    Result getDeptAncestor(String deptCd);

    Result getUnAccount();

    SysEmployeeInfoEntity getById(String employeeId);

    Result statusSelector();

    List<SysEmployeeInfoEntity> search(String[] ids);

    Result findAllOperationEmployee(Integer statusCd, String deptCd, String positionCd, String[] employeeIdList);

    List<VUserCategoryEntity> searchCategory(String userId, String[] employeeIdList,
                                             String positionCd, String upEmployeeId,
                                             String executiveDirectorId, String upPositionCd);
}
