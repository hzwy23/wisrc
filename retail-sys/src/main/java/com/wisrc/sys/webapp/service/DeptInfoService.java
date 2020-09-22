package com.wisrc.sys.webapp.service;

import com.wisrc.sys.webapp.vo.dept.DeptInfoSaveVo;
import com.wisrc.sys.webapp.vo.dept.DeptInfoVo;
import com.wisrc.sys.webapp.utils.Result;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface DeptInfoService {
    Result getDeptMenu();

    List<String> getChildren(String deptId);

    Result getDeptInfoList(Integer pageNum, Integer pageSize);

    Result saveDeptInfo(DeptInfoSaveVo deptInfoVo, BindingResult bindingResult);

    Result editDeptInfo(DeptInfoVo deptInfoVo, String deptCd, BindingResult bindingResult);

    Result getDeptInfo(String deptCd);

    Result deptSelect(String deptNow);

    Result deleteDeptInfo(String deptCd);

    Result getDeptOperation();
}
