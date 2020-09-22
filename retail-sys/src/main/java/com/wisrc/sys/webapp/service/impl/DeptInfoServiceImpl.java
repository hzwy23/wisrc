package com.wisrc.sys.webapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisrc.sys.webapp.dao.SysDeptInfoDao;
import com.wisrc.sys.webapp.dao.SysPositionInfoDao;
import com.wisrc.sys.webapp.entity.SysDeptInfoEntity;
import com.wisrc.sys.webapp.entity.SysPositionInfoEntity;
import com.wisrc.sys.webapp.service.DeptInfoService;
import com.wisrc.sys.webapp.utils.ObjectHandler;
import com.wisrc.sys.webapp.utils.Result;
import com.wisrc.sys.webapp.utils.Toolbox;
import com.wisrc.sys.webapp.vo.dept.DeptInfoSaveVo;
import com.wisrc.sys.webapp.vo.dept.DeptInfoVo;
import com.wisrc.sys.webapp.vo.dept.DeptTreeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;


@Service
public class DeptInfoServiceImpl implements DeptInfoService {
    // 设置部门层级最大循环次数
    private final int MAX_DEPT = 100;
    @Autowired
    private SysDeptInfoDao sysDeptInfoDao;
    @Autowired
    private SysPositionInfoDao sysPositionInfoDao;

    @Override
    public Result getDeptMenu() {
        int i = 0;
        Map<String, List> result = new HashMap();
        Map<String, List<Integer>> pos = new HashMap();
        List<Map<String, Object>> organization = new ArrayList<>();
        int count = 0;
        try {
            List<SysDeptInfoEntity> deptInfoList = sysDeptInfoDao.getSysDeptInfo();
            if (deptInfoList == null ) {
                return Result.success();
            }
            while (deptInfoList.size() != 0) {
                count++;
                // 若没有上级部门，则部门为最上级部门
                SysDeptInfoEntity deptInfo = deptInfoList.get(i);
                if (deptInfo == null || deptInfo.getParentCd() == null || deptInfo.getParentCd().isEmpty()) {
                    List parentCd = new ArrayList();
                    parentCd.add(deptInfo.getParentCd());
                    Map<String, Object> dept = new HashMap();
                    dept.put("value", deptInfo.getDeptCd());
                    dept.put("label", deptInfo.getDeptName());
                    dept.put("deptTypeAttr", deptInfo.getDeptTypeAttr());
                    dept.put("parentCd", parentCd);
                    organization.add(dept);

                    List<Integer> index = new ArrayList();
                    index.add(organization.size() - 1);
                    pos.put(deptInfo.getDeptCd(), index);
                    deptInfoList.remove(i);
                    i--;
                } else {
                    // 当上级部门存在，则嵌套进数据里，否则保留到下一次的重新循环
                    List<Integer> posArr = pos.get(deptInfo.getParentCd());
                    if (posArr != null) {
                        Map obj = organization.get(posArr.get(0));
                        for (int j = 1; j < posArr.size(); j++) {
                            obj = (Map) ObjectHandler.objectToList(obj.get("children")).get(posArr.get(j));
                        }

                        List parentCd = new ArrayList();
                        for (Object parent : ObjectHandler.objectToList(obj.get("parentCd"))) {
                            if (parent != null) parentCd.add(parent);
                        }
                        parentCd.add(deptInfo.getParentCd());
                        Map dept = new HashMap();
                        dept.put("value", deptInfo.getDeptCd());
                        dept.put("label", deptInfo.getDeptName());
                        dept.put("parentCd", parentCd);
                        dept.put("deptTypeAttr", deptInfo.getDeptTypeAttr());

                        if (obj.get("children") == null) obj.put("children", new ArrayList<Map>());
                        List children = ObjectHandler.objectToList(obj.get("children"));
                        children.add(dept);

                        if (pos.get(deptInfo.getDeptCd()) == null) {
                            pos.put(deptInfo.getDeptCd(), new ArrayList<>());
                            for (int num : pos.get(deptInfo.getParentCd())) {
                                pos.get(deptInfo.getDeptCd()).add(num);
                            }
                        }
                        pos.get(deptInfo.getDeptCd()).add(children.size() - 1);

                        deptInfoList.remove(i);
                        i--;
                    }
                }
                i++;
                if (i > deptInfoList.size() - 1) {
                    i = 0;
                }
                if (count > MAX_DEPT) {
                    break;
                }
            }

            result.put("organization", organization);

            return Result.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }

    @Override
    public List<String> getChildren(String deptId) {
        return sysDeptInfoDao.getDeptChild(deptId);
    }

    @Override
    public Result getDeptInfoList(Integer pageNum, Integer pageSize) {
        Map result = new HashMap();
        List deptInfoList = new ArrayList();
        List<SysDeptInfoEntity> deptInfoResult;

        try {
            PageHelper.startPage(pageNum, pageSize);
            deptInfoResult = sysDeptInfoDao.getSysDeptInfoPage();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        PageInfo pageInfo = new PageInfo(deptInfoResult);

        if (pageInfo.getTotal() >= pageNum) {
            for (SysDeptInfoEntity deptInfo : deptInfoResult) {
                Map dept = new HashMap();
                dept.put("deptCd", deptInfo.getDeptCd());
                dept.put("deptName", deptInfo.getDeptName());
                dept.put("parentCd", deptInfo.getParentCd());
                dept.put("deptTypeAttr", deptInfo.getDeptTypeAttr());
                deptInfoList.add(dept);
            }
        }

        result.put("deptInfoList", deptInfoList);
        result.put("total", pageInfo.getTotal());
        result.put("pages", pageInfo.getPages());
        return Result.success(result);
    }

    @Override
    public Result saveDeptInfo(DeptInfoSaveVo deptInfoVo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return Result.failure(400, errorList.get(0).getDefaultMessage(), "");
        }

        SysDeptInfoEntity deptInfo = deptInfoVoToEntity(deptInfoVo);
        deptInfo.setDeptCd(Toolbox.UUIDrandom());
        deptInfo.setStatusCd(4);
        deptInfo.setDeptTypeAttr(deptInfoVo.getDeptTypeAttr());

        try {
            sysDeptInfoDao.saveSysDeptInfo(deptInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        return Result.success();
    }

    @Override
    public Result editDeptInfo(DeptInfoVo deptInfoVo, String deptCd, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return Result.failure(400, "", errorList.get(0).getDefaultMessage());
        }

        SysDeptInfoEntity deptInfo = deptInfoVoToEntity(deptInfoVo);
        deptInfo.setDeptTypeAttr(deptInfoVo.getDeptTypeAttr());
        deptInfo.setDeptCd(deptCd);

        try {
            checkDeptParent(deptInfo);
        } catch (Exception e) {
            return new Result(400, "无效父级部门", "");
        }

        try {
            sysDeptInfoDao.editSysDeptInfo(deptInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
        return Result.success();
    }

    @Override
    public Result getDeptInfo(String deptCd) {
        Map dept = new HashMap();
        SysDeptInfoEntity deptInfo;
        List deptParentList = new ArrayList();
        try {
            deptInfo = sysDeptInfoDao.getDeptInfo(deptCd);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        try {
            List<SysDeptInfoEntity> deptParentResult = sysDeptInfoDao.getDeptParentLine(deptCd);
            String parentCd = null;
            int count = 0;
            while (deptParentResult.size() > 0) {
                count++;
                for (int m = 0; m < deptParentResult.size(); m++) {
                    SysDeptInfoEntity getDeptLine = deptParentResult.get(m);
                    if (getDeptLine.getParentCd() == null || parentCd.equals(getDeptLine.getParentCd())) {
                        Map deptLine = new HashMap();
                        deptLine.put("id", getDeptLine.getDeptCd());
                        deptLine.put("name", getDeptLine.getDeptName());
                        deptParentList.add(deptLine);
                        parentCd = getDeptLine.getDeptCd().toString();
                        deptParentResult.remove(m);
                        break;
                    }
                }

                if (count > MAX_DEPT) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        dept.put("deptCd", deptInfo.getDeptCd());
        dept.put("deptName", deptInfo.getDeptName());
        dept.put("parentCd", deptInfo.getParentCd());
        dept.put("parentLine", deptParentList);
        dept.put("deptTypeAttr", deptInfo.getDeptTypeAttr());

        return Result.success(dept);
    }

    @Override
    public Result deptSelect(String deptNow) {

        CopyOnWriteArrayList<SysDeptInfoEntity> list = sysDeptInfoDao.getSysDeptInfo();
        // 后去所有的部门信息

        if (deptNow != null && !deptNow.isEmpty()) {
            // 剔除这个部门的所有下级部门
            eliminateSubDept(list, deptNow);
        }

        // 组装成树形结构返回
        List<DeptTreeVO> result = new ArrayList<>();

        Map<String, SysDeptInfoEntity> roots = getRoots(list);

        toTree(list, roots, result);

        return Result.success(result);
    }


    @Override
    public Result deleteDeptInfo(String deptCd) {
        try {
            List<SysDeptInfoEntity> deptCheck = sysDeptInfoDao.getDeptParent(deptCd);
            if (deptCheck.size() > 0) {
                return new Result(400, "无法删除，存在下级部门", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        try {
            List<SysPositionInfoEntity> positionCheck = sysPositionInfoDao.getPositionByDeptCd(deptCd);
            if (positionCheck.size() > 0) {
                return new Result(400, "无法删除，存在关联岗位", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        try {
            sysDeptInfoDao.deleteSysDeptInfo(deptCd);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        return Result.success();
    }

    @Override
    public Result getDeptOperation() {
        try {
            return Result.success(sysDeptInfoDao.getOperationDept());
        } catch (Exception e) {
            return Result.failure(423, "查询运营部门信息失败，请联系管理员", null);
        }
    }

    public SysDeptInfoEntity deptInfoVoToEntity(DeptInfoVo deptInfoVo) {
        SysDeptInfoEntity sysDeptInfoEntity = new SysDeptInfoEntity();
        sysDeptInfoEntity.setDeptName(deptInfoVo.getDeptName());
        sysDeptInfoEntity.setParentCd(deptInfoVo.getParentDept());
        return sysDeptInfoEntity;
    }

    public void checkDeptParent(SysDeptInfoEntity deptInfo) throws Exception {
        List<String> deptChild = sysDeptInfoDao.getDeptChild(deptInfo.getDeptCd());
        if (deptChild.indexOf(deptInfo.getParentCd()) != -1) {
            throw new Exception();
        }
    }

    private void eliminateSubDept(List<SysDeptInfoEntity> list, String deptCd) {
        Map<String, SysDeptInfoEntity> map = new HashMap<>();
        getSubList(list, deptCd, map);
        for (SysDeptInfoEntity ele : list) {
            if (map.containsKey(ele.getDeptCd())) {
                list.remove(ele);
            } else if (deptCd.equals(ele.getDeptCd())) {
                list.remove(ele);
            }
        }
    }

    private void getSubList(List<SysDeptInfoEntity> list, String deptCd, Map<String, SysDeptInfoEntity> result) {
        for (SysDeptInfoEntity ele : list) {
            if (deptCd.equals(ele.getParentCd())) {
                if (!result.containsKey(ele)) {
                    result.put(ele.getDeptCd(), ele);
                    getSubList(list, ele.getDeptCd(), result);
                }
            }
        }
    }

    private void getChild(List<SysDeptInfoEntity> list, String deptCd, Map<String, SysDeptInfoEntity> result) {
        for (SysDeptInfoEntity ele : list) {
            if (deptCd.equals(ele.getParentCd())) {
                if (!result.containsKey(ele)) {
                    result.put(ele.getDeptCd(), ele);
                }
            }
        }
    }

    private void toTree(List<SysDeptInfoEntity> list, Map<String, SysDeptInfoEntity> roots, List<DeptTreeVO> result) {
        // 获取第一层根节点, 开始循环遍历
//        Map<String,SysDeptInfoEntity> nextDept = new HashMap<>();
        for (String rootId : roots.keySet()) {
            DeptTreeVO deptVo = new DeptTreeVO();
            deptVo.setLabel(roots.get(rootId).getDeptName());
            deptVo.setValue(rootId);
            result.add(deptVo);
            Map<String, SysDeptInfoEntity> nextDept = getCurrentDeptInfo(list, deptVo);
            bfs(list, nextDept, deptVo);
        }
    }

    private void bfs(List<SysDeptInfoEntity> list, Map<String, SysDeptInfoEntity> roots, DeptTreeVO deptTreeVO) {
        deptTreeVO.setChildren(new ArrayList<DeptTreeVO>());

        for (String rootId : roots.keySet()) {
            DeptTreeVO deptVo = new DeptTreeVO();
            deptVo.setLabel(roots.get(rootId).getDeptName());
            deptVo.setValue(rootId);
            Map<String, SysDeptInfoEntity> nextDept = getCurrentDeptInfo(list, deptVo);
            deptTreeVO.getChildren().add(deptVo);
            if (nextDept.size() > 0) {
                bfs(list, nextDept, deptVo);
            }
        }
    }

    // 获取当前层的所有节点, 并且返回这个节点下所有的子节点
    private Map<String, SysDeptInfoEntity> getCurrentDeptInfo(List<SysDeptInfoEntity> list, DeptTreeVO one) {
        Map<String, SysDeptInfoEntity> map = new HashMap<>();
        getChild(list, one.getValue(), map);
        List<DeptTreeVO> currentList = new ArrayList<>();
        for (String val : map.keySet()) {
            SysDeptInfoEntity current = map.get(val);
            DeptTreeVO tmp = new DeptTreeVO();
            tmp.setLabel(current.getDeptName());
            tmp.setValue(current.getDeptCd());
            currentList.add(tmp);
        }
        if (currentList.size() == 0) {
            one.setChildren(null);
        } else {
            one.setChildren(currentList);
        }
        return map;
    }

    /**
     * 获取一级部门
     */
    private Map<String, SysDeptInfoEntity> getRoots(List<SysDeptInfoEntity> list) {
        Map<String, SysDeptInfoEntity> map = new HashMap<>();
        Map<String, String> dept = new HashMap<>();

        for (SysDeptInfoEntity ele : list) {
            dept.put(ele.getDeptCd(), ele.getParentCd());
        }
        for (SysDeptInfoEntity ele : list) {
            if (!dept.containsKey(ele.getParentCd())) {
                map.put(ele.getDeptCd(), ele);
            }
        }
        return map;
    }
}
