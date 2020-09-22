package com.wisrc.sys.webapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisrc.sys.webapp.dao.AuthInfoInfoDao;
import com.wisrc.sys.webapp.entity.SysPrivilegesInfoEntity;
import com.wisrc.sys.webapp.service.AuthInfoInfoService;
import com.wisrc.sys.webapp.utils.Result;
import com.wisrc.sys.webapp.utils.ResultCode;
import com.wisrc.sys.webapp.utils.Time;
import com.wisrc.sys.webapp.utils.Toolbox;
import com.wisrc.sys.webapp.vo.authInfo.AuthInfoVO;
import com.wisrc.sys.webapp.vo.authInfo.SetAuthInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthInfoInfoServiceImpl implements AuthInfoInfoService {

    private final Logger logger = LoggerFactory.getLogger(AuthInfoInfoServiceImpl.class);
    @Autowired
    private AuthInfoInfoDao authInfoInfoDao;

    @Override
    public Result getAuthInfo(Integer pageNum, Integer pageSize, AuthInfoVO authInfoVO) {
        try {
            SysPrivilegesInfoEntity sysPrivilegesInfoEntity = voToSysPrivilegesInfoEntity(authInfoVO);

            if (pageNum != null && pageSize != null) {
                PageHelper.startPage(pageNum, pageSize);
            }
            List<SysPrivilegesInfoEntity> list = authInfoInfoDao.getAuthInfo(sysPrivilegesInfoEntity);

            List<AuthInfoVO> authInfoList = poListToVOList(list);
            Map<String, Object> map = new HashMap<>();
            PageInfo pageInfo = new PageInfo(list);
            long total = pageInfo.getTotal();
            int pages = pageInfo.getPages();
            map.put("total", total);
            map.put("pages", pages);
            map.put("authInfoList", authInfoList);
            return Result.success(map);
        } catch (Exception e) {
            logger.error("数据权限信息查询错误", e);
            return Result.failure(ResultCode.FIND_FAILED);
        }
    }

//    @Override
//    public Result getNewPrivilegeCd() {
//        Result result = newPrivilegeCd();
//        return result;
//    }

//    public Result newPrivilegeCd() {
//        String maxId;
//        String newId;
//
//        try {
//            maxId = authInfoInfoDao.getMaxId();
//            int idNumber = Integer.parseInt(maxId) + 1;
//            if (idNumber >= 99999) {
//                return new Result(9999, "历史权限总数达到99999，无法新增新的授权编码，请联系管理员", null);
//            }
//            newId = "DA" + new DecimalFormat("00000").format(idNumber);
//            return Result.success(newId);
//        } catch (Exception e) {
//            logger.error("数据权限编号生成出错", e);
//            newId = "DA00001";
//            return Result.success(newId);
//        }
//    }

    @Override
    public Result findAll(String privilegeTypeAttr) {
        List<SysPrivilegesInfoEntity> list = null;
        if (privilegeTypeAttr != null && !privilegeTypeAttr.isEmpty()) {
            list = authInfoInfoDao.findByTypeAttr(privilegeTypeAttr);
        } else {
            list = authInfoInfoDao.findAll();
        }
        List<AuthInfoVO> authInfoList = poListToVOList(list);
        return Result.success(authInfoList);
    }

    /**
     * 添加新的权限代码
     * modify by zhanwei_huang ，修改ID自动生成规则
     */
    @Override
    public Result add(AuthInfoVO authInfoVO) {
        try {
            SysPrivilegesInfoEntity sysPrivilegesInfoEntity = voToSysPrivilegesInfoEntity(authInfoVO);
            sysPrivilegesInfoEntity.setStatusCd(1);
            sysPrivilegesInfoEntity.setPrivilegeCd(Toolbox.UUIDrandom());
            String current = Time.getCurrentTime();
            sysPrivilegesInfoEntity.setCreateTime(current);
            sysPrivilegesInfoEntity.setModifyTime(current);
            sysPrivilegesInfoEntity.setModifyUser(authInfoVO.getCreateUser());
            sysPrivilegesInfoEntity.setPrivilegeTypeAttr(authInfoVO.getPrivilegeTypeAttr());
            authInfoInfoDao.add(sysPrivilegesInfoEntity);
            return Result.success();
        } catch (Exception e) {
            return new Result(423, "添加数据权限代码失败，请联系管理员", e.getMessage());
        }
    }


    @Override
    @Transactional(transactionManager = "retailSystemTransactionManager")
    public Result delete(String authId) {
        //删除授权用户
        authInfoInfoDao.deleteUser(authId);
        //删除授权店铺
        authInfoInfoDao.deleteShop(authId);
        //删除授权供应商
        authInfoInfoDao.deleteSupplier(authId);
        //删除授权仓库
        authInfoInfoDao.deleteWarehohse(authId);
        //最后删除该条授权编码
        authInfoInfoDao.delete(authId);
        return Result.success();
    }

    @Override
    public Result update(SetAuthInfoVO authInfoVO, String userId) {

        SysPrivilegesInfoEntity entity = new SysPrivilegesInfoEntity();
        BeanUtils.copyProperties(authInfoVO, entity);
        entity.setPrivilegeCd(authInfoVO.getAuthId());
        entity.setPrivilegeName(authInfoVO.getAuthName());
        entity.setModifyUser(userId);
        entity.setModifyTime(Time.getCurrentTime());
        try {
            authInfoInfoDao.update(entity);
        } catch (DuplicateKeyException e) {
            return Result.failure(423, "权限代码名称重复，请重新输入", e.getMessage());
        }
        return Result.success();
    }

    @Override
    public void restrict(SysPrivilegesInfoEntity entity) {
        authInfoInfoDao.restrict(entity);
    }

    @Override
    public Result getPrivilegesById(String authId) {
        SysPrivilegesInfoEntity entity = authInfoInfoDao.getPrivilegesById(authId);
        return Result.success(entity);
    }

    //VO转PO
    private SysPrivilegesInfoEntity voToSysPrivilegesInfoEntity(AuthInfoVO authInfoVO) {
        SysPrivilegesInfoEntity entity = new SysPrivilegesInfoEntity();
        BeanUtils.copyProperties(authInfoVO, entity);
        entity.setPrivilegeName(authInfoVO.getAuthName());
        entity.setPrivilegeTypeAttr(authInfoVO.getPrivilegeTypeAttr());
        return entity;
    }

    //PO转VO
    private AuthInfoVO poToVO(SysPrivilegesInfoEntity entity) {
        AuthInfoVO authInfoVO = new AuthInfoVO();
        BeanUtils.copyProperties(entity, authInfoVO);
        authInfoVO.setAuthId(entity.getPrivilegeCd());
        authInfoVO.setAuthName(entity.getPrivilegeName());
        return authInfoVO;
    }

    private List<AuthInfoVO> poListToVOList(List<SysPrivilegesInfoEntity> list) {
        List<AuthInfoVO> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            AuthInfoVO authInfoVO = poToVO(list.get(i));
            result.add(authInfoVO);
        }
        return result;
    }
}
