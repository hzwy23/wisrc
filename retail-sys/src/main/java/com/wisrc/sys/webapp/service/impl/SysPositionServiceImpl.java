package com.wisrc.sys.webapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisrc.sys.webapp.dao.SysDeptInfoDao;
import com.wisrc.sys.webapp.dao.SysPositionInfoDao;
import com.wisrc.sys.webapp.entity.GatherEntity;
import com.wisrc.sys.webapp.entity.SysDeptInfoEntity;
import com.wisrc.sys.webapp.entity.SysPositionInfoEntity;
import com.wisrc.sys.webapp.service.EmployeeInfoService;
import com.wisrc.sys.webapp.service.SysPositionService;
import com.wisrc.sys.webapp.utils.ObjectHandler;
import com.wisrc.sys.webapp.utils.Result;
import com.wisrc.sys.webapp.utils.Toolbox;
import com.wisrc.sys.webapp.vo.position.PositionInfoSaveVo;
import com.wisrc.sys.webapp.vo.position.PositionInfoVo;
import com.wisrc.sys.webapp.vo.position.PositionPageSelectorVo;
import com.wisrc.sys.webapp.vo.position.PositionPageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysPositionServiceImpl implements SysPositionService {
    @Autowired
    private SysPositionInfoDao sysPositionInfoDao;
    @Autowired
    private EmployeeInfoService employeeInfoService;

    @Autowired
    private SysDeptInfoDao sysDeptInfoDao;

    @Override
    public Result getPositionPage(PositionPageVo positionPageVo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return Result.failure(400, "", errorList.get(0).getDefaultMessage());
        }

        Map result = new HashMap<>();
        List jobList = new ArrayList<>();
        List<SysPositionInfoEntity> positionInfoResult = null;
        List deptCds = new ArrayList();
        List parentJobCds = new ArrayList();
        Map<String, Map> parentJobMap = new HashMap<>();
        Map<String, Map> deptMap = new HashMap<>();

        try {
            PageHelper.startPage(positionPageVo.getPageNum(), positionPageVo.getPageSize());
            positionInfoResult = sysPositionInfoDao.getSysPositionPage(positionPageVo.getJobId(), positionPageVo.getJobName(), positionPageVo.getValue());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        PageInfo pageInfo = new PageInfo(positionInfoResult);

        if (pageInfo.getTotal() >= positionPageVo.getPageNum()) {
            for (SysPositionInfoEntity positionInfo : positionInfoResult) {
                deptCds.add(positionInfo.getDeptCd());
                parentJobCds.add(positionInfo.getParentCd());
            }

            if (parentJobCds.size() > 0) {
                parentJobMap = relationParentPosition(parentJobCds);
            }

            if (deptCds.size() > 0) {
                deptMap = relationDept(deptCds);
            }

            for (SysPositionInfoEntity positionInfo : positionInfoResult) {
                Map job = new HashMap();
                job.put("number", positionInfo.getPositionCd());
                job.put("executiveDirectorAttr", positionInfo.getExecutiveDirectorAttr());
                job.put("name", positionInfo.getPositionName());
                job.put("parent", parentJobMap.get(positionInfo.getParentCd()));
                job.put("department", deptMap.get(positionInfo.getDeptCd()));
                jobList.add(job);
            }
        }

        result.put("jobList", jobList);
        result.put("total", pageInfo.getTotal());
        result.put("pages", pageInfo.getPages());
        return Result.success(result);
    }

    @Override
    public Result savePositionInfo(PositionInfoSaveVo positionInfoVo) {

        SysPositionInfoEntity sysPositionInfoEntity = posionInfoVoToEntity(positionInfoVo);
        sysPositionInfoEntity.setPositionCd(Toolbox.UUIDrandom());
        sysPositionInfoEntity.setStatusCd(4);

        try {
            sysPositionInfoDao.saveSysPositionInfo(sysPositionInfoEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        return Result.success();
    }

    @Override
    public Result editPositionInfo(PositionInfoVo positionInfoVo, String number) {

        SysPositionInfoEntity sysPositionInfoEntity = posionInfoVoToEntity(positionInfoVo);
        sysPositionInfoEntity.setPositionCd(number);

        try {
            sysPositionInfoDao.editSysPositionInfo(sysPositionInfoEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        return Result.success();
    }

    @Override
    public Result positionSelect(PositionPageSelectorVo positionPageSelectorVo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return Result.failure(400, "", errorList.get(0).getDefaultMessage());
        }

        Map result = new HashMap();
        List positionSelector = new ArrayList();
        List<SysPositionInfoEntity> positionResults;
        List deptCds = new ArrayList();
        List parentJobCds = new ArrayList();
        Map<String, Map> parentJobMap = new HashMap<>();
        Map<String, Map> deptMap = new HashMap<>();

        try {
            PageHelper.startPage(positionPageSelectorVo.getPageNum(), positionPageSelectorVo.getPageSize());
            positionResults = sysPositionInfoDao.getPositionNoChild(positionPageSelectorVo);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        PageInfo pageInfo = new PageInfo(positionResults);

        for (SysPositionInfoEntity positionInfo : positionResults) {
            deptCds.add(positionInfo.getDeptCd());
            parentJobCds.add(positionInfo.getParentCd());
        }

        if (parentJobCds.size() > 0) {
            parentJobMap = relationParentPosition(parentJobCds);
        }

        if (deptCds.size() > 0) {
            deptMap = relationDept(deptCds);
        }

        for (SysPositionInfoEntity positionInfo : positionResults) {
            Map job = new HashMap();
            job.put("number", positionInfo.getPositionCd());
            job.put("name", positionInfo.getPositionName());
            job.put("executiveDirectorAttr", positionInfo.getExecutiveDirectorAttr());
            job.put("parent", parentJobMap.get(positionInfo.getParentCd()));
            job.put("department", deptMap.get(positionInfo.getDeptCd()));
            positionSelector.add(job);
        }

        result.put("positionSelector", positionSelector);
        result.put("total", pageInfo.getTotal());
        result.put("pages", pageInfo.getPages());
        return Result.success(result);
    }


    @Override
    public Result deletePositionInfo(String positionCd) {
        try {
            List<SysPositionInfoEntity> position = sysPositionInfoDao.getPositionParent(positionCd);
            if (position.size() > 0) {
                return new Result(400, "无法删除，存在下级岗位", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        try {
            GatherEntity gatherEntity = new GatherEntity();
            gatherEntity.setPositionCd(positionCd);
            Result employeeResult = employeeInfoService.find(1, 10, gatherEntity);
            Map employeePage = (Map) employeeResult.getData();
            List employees = ObjectHandler.objectToList(employeePage.get("employeeInfoList"));
            if (employees.size() > 0) {
                return new Result(400, "无法删除，存在关联人员", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        try {
            sysPositionInfoDao.deleteSysPositionInfo(positionCd);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        return Result.success();
    }

    public SysPositionInfoEntity posionInfoVoToEntity(PositionInfoVo positionInfoVo) {
        SysPositionInfoEntity sysPositionInfoEntity = new SysPositionInfoEntity();
        sysPositionInfoEntity.setPositionName(positionInfoVo.getName());
        sysPositionInfoEntity.setParentCd(positionInfoVo.getParent());
        sysPositionInfoEntity.setDeptCd(positionInfoVo.getDepartment());
        sysPositionInfoEntity.setExecutiveDirectorAttr(positionInfoVo.getExecutiveDirectorAttr());
        return sysPositionInfoEntity;
    }

    public Map relationParentPosition(List parentJobCds) {
        Map<String, Map> parentJobMap = new HashMap<>();

        try {
            List<SysPositionInfoEntity> parentJobResult = sysPositionInfoDao.getSysPositionBatch(parentJobCds);
            for (SysPositionInfoEntity positionInfo : parentJobResult) {
                Map job = new HashMap();
                job.put("number", positionInfo.getPositionCd());
                job.put("name", positionInfo.getPositionName());
                parentJobMap.put(positionInfo.getPositionCd(), job);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parentJobMap;
    }

    public Map relationDept(List deptCds) {
        Map<String, Map> deptMap = new HashMap<>();

        try {
            List<SysDeptInfoEntity> deptInfoResult = sysDeptInfoDao.getSysDeptInfoBatch(deptCds);
            for (SysDeptInfoEntity deptInfo : deptInfoResult) {
                Map dept = new HashMap();
                dept.put("number", deptInfo.getDeptCd());
                dept.put("name", deptInfo.getDeptName());
                deptMap.put(deptInfo.getDeptCd(), dept);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return deptMap;
    }
}
