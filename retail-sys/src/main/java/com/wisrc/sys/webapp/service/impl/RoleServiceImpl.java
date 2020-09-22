package com.wisrc.sys.webapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisrc.sys.webapp.dao.SysRoleInfoDao;
import com.wisrc.sys.webapp.dao.SysRoleMenusDao;
import com.wisrc.sys.webapp.dao.SysRoleUserDao;
import com.wisrc.sys.webapp.entity.SysRoleInfoEntity;
import com.wisrc.sys.webapp.entity.SysRoleMenusEntity;
import com.wisrc.sys.webapp.service.RoleService;
import com.wisrc.sys.webapp.utils.Crypto;
import com.wisrc.sys.webapp.utils.Result;
import com.wisrc.sys.webapp.utils.ResultCode;
import com.wisrc.sys.webapp.vo.SysRoleUserVO;
import com.wisrc.sys.webapp.vo.role.RoleAuthorizeVo;
import com.wisrc.sys.webapp.vo.role.RoleInfoPageVo;
import com.wisrc.sys.webapp.vo.role.RoleInfoVo;
import com.wisrc.sys.webapp.vo.role.RoleSwitchVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class RoleServiceImpl implements RoleService {

    private final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private SysRoleInfoDao sysRoleInfoDao;
    @Autowired
    private SysRoleUserDao sysRoleUserDao;
    @Autowired
    private SysRoleMenusDao sysRoleMenusDao;

    @Override
    public Result getRoles(RoleInfoPageVo roleInfoPageVo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return new Result(400, "", errorList.get(0).getDefaultMessage());
        }

        Map result = new HashMap();
        List roles = new ArrayList();
        List roleIds = new ArrayList();
        List<SysRoleInfoEntity> rolesList = new ArrayList<>();

        if (roleInfoPageVo.getAccountId() != null) {
            try {
                roleIds = sysRoleUserDao.getRoleIdByUserId(roleInfoPageVo.getAccountId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            PageHelper.startPage(roleInfoPageVo.getPageNum(), roleInfoPageVo.getPageSize());
            rolesList = sysRoleInfoDao.getSysRoleInfoPage(roleInfoPageVo.getRoleId(), roleInfoPageVo.getRoleName(), roleInfoPageVo.getStatusCd(), roleIds);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        PageInfo pageInfo = new PageInfo(rolesList);

        for (SysRoleInfoEntity sysRoleInfoEntity : rolesList) {
            Map role = new HashMap();
            role.put("roleId", sysRoleInfoEntity.getRoleId());
            role.put("roleName", sysRoleInfoEntity.getRoleName());
            role.put("statusCd", sysRoleInfoEntity.getStatusCd());
            role.put("createUser", sysRoleInfoEntity.getCreateUser());
            role.put("createTime", df.format(sysRoleInfoEntity.getCreateTime()));
            roles.add(role);
        }

        result.put("roleInfoList", roles);
        result.put("total", pageInfo.getTotal());
        result.put("pages", pageInfo.getPages());
        return new Result(200, "", result);
    }

    @Override
    public Result getRoleById(String roleId) {
        Map result = new HashMap();

        SysRoleInfoEntity sysRoleInfoEntity;
        try {
            sysRoleInfoEntity = sysRoleInfoDao.getSysRoleInfoById(roleId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(400, e.getMessage(), null);
        }

        try {
            result.put("roleId", sysRoleInfoEntity.getRoleId());
            result.put("roleName", sysRoleInfoEntity.getRoleName());
            result.put("statusCd", sysRoleInfoEntity.getStatusCd());
            result.put("createUser", sysRoleInfoEntity.getCreateUser());
            result.put("createTime", df.format(sysRoleInfoEntity.getCreateTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Result(200, "", result);
    }

    @Override
    public Result saveRole(RoleInfoVo roleInfoVo, String userId, String roleId, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return Result.failure(400, "", errorList.get(0).getDefaultMessage());
        }

        SysRoleInfoEntity sysRoleInfoEntity = new SysRoleInfoEntity();
        BeanUtils.copyProperties(roleInfoVo, sysRoleInfoEntity);

        try {
            sysRoleInfoEntity.setRoleId(roleId);
            sysRoleInfoEntity.setCreateUser(userId);
            sysRoleInfoEntity.setStatusCd(1);
            sysRoleInfoEntity.setCreateTime(new Timestamp(new Date().getTime()));
            sysRoleInfoDao.saveSysRoleInfo(sysRoleInfoEntity);
        } catch (DuplicateKeyException e) {
            return Result.failure(ResultCode.ID_DUPLICATE);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(400, e.getMessage(), null);
        }
        return new Result(200, "", null);
    }

    @Override
    public Result editRole(RoleInfoVo roleInfoVo, String roleId, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return Result.failure(400, "", errorList.get(0).getDefaultMessage());
        }

        SysRoleInfoEntity sysRoleInfoEntity = new SysRoleInfoEntity();
        BeanUtils.copyProperties(roleInfoVo, sysRoleInfoEntity);

        try {
            sysRoleInfoEntity.setRoleId(roleId);
            sysRoleInfoDao.editSysRoleInfo(sysRoleInfoEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(400, e.getMessage(), null);
        }
        return new Result(200, "", null);
    }

    @Override
    public Result deleteRole(String roleId) {
        try {
            sysRoleInfoDao.deleteSysRoleInfo(roleId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(400, e.getMessage(), null);
        }
        return new Result(200, "", null);
    }

    @Override
    public Result getNewRoleId() {
        String maxId = null;
        String id = null;
        try {
            maxId = sysRoleInfoDao.getMaxRoleId();
            int idNumber = Integer.parseInt(maxId) + 1;
            id = "Q" + new DecimalFormat("0000").format(idNumber);
        } catch (Exception e) {
            e.printStackTrace();
            id = "Q0001";
        }

        return Result.success(id);
    }

    @Override
    public Result banAllRole(List roleIds) {
        try {
            sysRoleInfoDao.banSysRoleInfoBatch(roleIds);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        return Result.success();
    }

    @Override
    public Result roleSwitch(String roleId, RoleSwitchVo roleSwitchVo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return Result.failure(400, "", errorList.get(0).getDefaultMessage());
        }

        int statusCd = roleSwitchVo.getStatusCd();
        if (statusCd == 1) {
            statusCd = 2;
        } else {
            statusCd = 1;
        }

        try {
            sysRoleInfoDao.sysRoleInfoSwitch(roleId, statusCd);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        return Result.success();
    }

    @Override
    @Transactional(transactionManager = "retailSystemTransactionManager")
    public Result roleAuthorize(String roleId, RoleAuthorizeVo roleAuthorizeVo) {
        for (String ele : roleAuthorizeVo.getDelMenusId()) {
            sysRoleMenusDao.deleteSysRoleMenusByMenuIds(roleId, ele);
        }
        SysRoleMenusEntity sys = new SysRoleMenusEntity();
        sys.setRoleId(roleId);
        for (String ele : roleAuthorizeVo.getAddMenusId()) {
            sys.setMenuId(ele);
            sys.setUuid(Crypto.sha(roleId, ele));
            sysRoleMenusDao.saveSysRoleMenus(sys);
        }
        return Result.success();
    }

    /**
     * 查询指定用户对应的角色详细信息
     */
    public LinkedHashMap<String, List<SysRoleUserVO>> findRolesByUserIdList(Set<String> idList) {
        if (idList == null || idList.size() == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder("(");
        for (String m : idList) {
            sb.append("'").append(m).append("'").append(",");
        }
        sb.replace(sb.length() - 1, sb.length(), ")");
        String args = sb.toString();
        if (args.length() <= 4) {
            return null;
        } else {
            List<SysRoleUserVO> list = sysRoleUserDao.findAllByIdList(args);
            LinkedHashMap<String, List<SysRoleUserVO>> map = new LinkedHashMap<>();
            for (SysRoleUserVO ele : list) {
                if (map.containsKey(ele.getUserId())) {
                    map.get(ele.getUserId()).add(ele);
                } else {
                    map.put(ele.getUserId(), new ArrayList<SysRoleUserVO>() {{
                        add(ele);
                    }});
                }
            }
            return map;
        }
    }
}
