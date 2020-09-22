package com.wisrc.sys.webapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisrc.sys.webapp.dao.EmployeeInfoDao;
import com.wisrc.sys.webapp.dao.SysEmployeeInfoDao;
import com.wisrc.sys.webapp.dao.SysEmployeeStatusAttrDao;
import com.wisrc.sys.webapp.entity.*;
import com.wisrc.sys.webapp.service.EmployeeInfoService;
import com.wisrc.sys.webapp.utils.ArrayToInArguments;
import com.wisrc.sys.webapp.utils.Result;
import com.wisrc.sys.webapp.utils.ResultCode;
import com.wisrc.sys.webapp.vo.employee.AddEmployeeVO;
import com.wisrc.sys.webapp.vo.employee.SetEmployeeVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.text.DecimalFormat;
import java.util.*;

@Service
public class EmployeeInfoServiceImpl implements EmployeeInfoService {
    private final Logger logger = LoggerFactory.getLogger(EmployeeInfoServiceImpl.class);

    @Autowired
    private EmployeeInfoDao employeeInfoDao;

    @Autowired
    private SysEmployeeInfoDao sysEmployeeInfoDao;

    @Autowired
    private SysEmployeeStatusAttrDao sysEmployeeStatusAttrDao;


    @Override
    public Result find(Integer pageNum, Integer pageSize, GatherEntity gatherEntity) {
        Map<String, Object> map = new HashMap();
        if (pageNum != null && pageSize != null) {
            PageHelper.startPage(pageNum, pageSize);
        }
        List<LinkedHashMap<String, Object>> employeeInfoList = employeeInfoDao.find(gatherEntity);
        PageInfo pageInfo = new PageInfo(employeeInfoList);
        long total = pageInfo.getTotal();
        int pages = pageInfo.getPages();
        map.put("total", total);
        map.put("pages", pages);
        map.put("employeeInfoList", employeeInfoList);
        return Result.success(map);
    }

    @Override
    public Result update(SetEmployeeVO addEmployee, BindingResult bindingResult) {
        SysEmployeeInfoEntity entity = new SysEmployeeInfoEntity();
        BeanUtils.copyProperties(addEmployee, entity);
        employeeInfoDao.update(entity);
        return Result.success();
    }

    @Override
    public Result add(AddEmployeeVO addEmployee, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new Result(9999, bindingResult.getFieldError().getDefaultMessage(), null);
        }
        Result result = newEmployeeId();
        if (!addEmployee.getEmployeeId().equals(result.getData())) {
            return Result.failure(9999, "无效的员工编码employeeId", "无效的员工编码employeeId");
        }

        try {
            SysEmployeeInfoEntity entity = new SysEmployeeInfoEntity();
            BeanUtils.copyProperties(addEmployee, entity);
            entity.setStatusCd(1);
            employeeInfoDao.add(entity);
            return Result.success();
        } catch (Exception e) {
            logger.error("新增员工失败", e);
            return Result.failure(ResultCode.CREATE_FAILED);
        }
    }

    @Override
    public Result getEmployeeDetail(String employeeId) {
        LinkedHashMap<String, Object> map = employeeInfoDao.getEmployee(employeeId);
        return Result.success(map);
    }

    @Override
    public Result getNewEmployeeId() {
        Result result = newEmployeeId();
        return result;
    }

    public Result newEmployeeId() {
        String maxId;
        String newId;
        try {
            //sql语句报错 数据库为空
            maxId = employeeInfoDao.getMaxId();
            int idNumber = Integer.parseInt(maxId) + 1;
            if (idNumber >= 9999) {
                return new Result(9999, "员工总数达到9999，无法添加新员工，请联系管理员", null);
            }
            newId = "E" + new DecimalFormat("00000").format(idNumber);
            return Result.success(newId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("员工编号生成出错", e);
            newId = "E00001";
            return Result.success(newId);
        }
    }

    @Override
    public void changeStatus(String employeeId, Integer statusCd) {
        Map<String, Object> map = new HashMap<>();
        map.put("employeeId", employeeId);
        map.put("statusCd", statusCd);
        employeeInfoDao.changeStatus(map);
    }

    @Override
    public Result getDeptAncestor(String deptCd) {
        SysDeptInfoEntity entity = employeeInfoDao.getSysDeptInfoById(deptCd);
        if (entity == null) {
            return new Result(9999, "没有找到该deptCd编码下的部门信息", null);
        } else {
            List<SysDeptInfoEntity> ancestorList = new ArrayList<>();
            String parentCd = entity.getParentCd();
            //本身就是根节点
            if (parentCd == null || parentCd.equals("-1") || parentCd.isEmpty()) {
                List<String> deptAncestorVOList = new ArrayList<>();
                deptAncestorVOList.add(deptCd);
                return Result.success(deptAncestorVOList);
            } else { //本身不是根节点
                //去全部部门信息
                List<SysDeptInfoEntity> list = employeeInfoDao.getSysDeptInfo();
                //递归该部门的直系祖先部门
                try {
                    ancestorList = getAncestor(list, parentCd, ancestorList);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //换个顺序  祖先排头
                Collections.reverse(ancestorList);
                //补上自己
                ancestorList.add(entity);
                return Result.success(toVOList(ancestorList));
            }
        }
    }

    @Override
    public Result getUnAccount() {
        List<Map> list = employeeInfoDao.getUnAccount();
        return Result.success(list);
    }

    @Override
    public SysEmployeeInfoEntity getById(String employeeId) {
        SysEmployeeInfoEntity entity = employeeInfoDao.getById(employeeId);
        return entity;
    }

    @Override
    public Result statusSelector() {
        List statusMap = new ArrayList();
        List<SysEmployeeStatusAttrEntity> statusResult;

        try {
            statusResult = sysEmployeeStatusAttrDao.getSysEmployeeStatusAttr();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        for (SysEmployeeStatusAttrEntity statusAttr : statusResult) {
            Map status = idAndName(statusAttr.getStatusCd(), statusAttr.getStatusDesc());
            statusMap.add(status);
        }

        return Result.success(statusMap);
    }

    @Override
    public List<SysEmployeeInfoEntity> search(String[] ids) {
        StringBuffer sqlText = new StringBuffer("(");
        for (String ele : ids) {
            sqlText.append("'").append(ele).append("',");
        }
        sqlText.replace(sqlText.length() - 1, sqlText.length(), ")");
        return sysEmployeeInfoDao.search(sqlText.toString());
    }

    @Override
    public Result findAllOperationEmployee(Integer statusCd, String deptCd, String positionCd, String[] employeeIdList) {
        String employeeIdListStr = ArrayToInArguments.toInArgs(employeeIdList);
        try {
            return Result.success(employeeInfoDao.findOperationEmployee(statusCd, deptCd, positionCd, employeeIdListStr));
        } catch (Exception e) {
            return Result.failure(423, "查询业务人员信息失败，请联系管理员", null);
        }
    }

    @Override
    public List<VUserCategoryEntity> searchCategory(String userId, String[] employeeIdList, String positionCd, String upEmployeeId, String executiveDirectorId, String upPositionCd) {
        try {
            return employeeInfoDao.searchCategory(userId, ArrayToInArguments.toInArgs(employeeIdList), positionCd, upEmployeeId, executiveDirectorId, upPositionCd);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    //PO封装前端VO
    private List<String> toVOList(List<SysDeptInfoEntity> ancestorList) {
        List<String> deptAncestorVOList = new ArrayList<>();
        for (SysDeptInfoEntity entity : ancestorList) {
            deptAncestorVOList.add(entity.getDeptCd());
        }
        return deptAncestorVOList;
    }


    /**
     * 以某个classifyCd为节点
     * 获取其直系祖先
     *
     * @param list
     * @param fatherList
     */
    private List<SysDeptInfoEntity> getAncestor(List<SysDeptInfoEntity> list, String parentCd, List<SysDeptInfoEntity> fatherList) {
        if (parentCd == null || parentCd.equals("-1") || parentCd.isEmpty() || list.size() == 0 || list == null) {
            return fatherList;
        } else {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getDeptCd().equals(parentCd)) {
                    fatherList.add(list.get(i));
                    getAncestor(list, list.get(i).getParentCd(), fatherList);
                }
            }
        }
        return fatherList;
    }

    public Map idAndName(Object id, String name) {
        Map returnMap = new HashMap();
        returnMap.put("id", id);
        returnMap.put("name", name);
        return returnMap;
    }
}
