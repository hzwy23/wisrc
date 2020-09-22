package com.wisrc.sys.webapp.service.impl;


import com.wisrc.sys.webapp.dao.SysSecUserDao;
import com.wisrc.sys.webapp.entity.SysSecUserEntity;
import com.wisrc.sys.webapp.service.PasswordService;
import com.wisrc.sys.webapp.utils.Result;
import com.wisrc.sys.webapp.utils.ResultCode;
import com.wisrc.sys.webapp.vo.ModifyPasswordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordServiceImpl implements PasswordService {

    @Autowired
    private SysSecUserDao sysSecUserDao;

    @Override
    public Result modifyPassword(ModifyPasswordVO modifyPasswordVO) {
        // 校验两次输入密码是否一直
        if (!confirmValid(modifyPasswordVO)) {
            return Result.failure(ResultCode.PASSWORD_NOT_EQUALS);
        }

        // 校验用户密码是否正确
        if (!validPassword(modifyPasswordVO)) {
            return Result.failure(ResultCode.PASSWORD_ERROR);
        }

        // 开始修改密码
        SysSecUserEntity sysSecUserEntity = voToEntity(modifyPasswordVO);
        try {
            sysSecUserDao.editSysSecUser(sysSecUserEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ResultCode.UNKNOW_ERROR, "修改用户密码失败");
        }
        return Result.success("密码修改成功");
    }

    private boolean confirmValid(ModifyPasswordVO vo) {
        if (vo.getNewPassword().equals(vo.getConfirmPassword())) {
            return true;
        }
        return false;
    }

    private boolean validPassword(ModifyPasswordVO modifyPasswordVO) {
        try {
            SysSecUserEntity valid = sysSecUserDao.checkPassword(modifyPasswordVO.getUserId());
            if (valid.getUserId() == null || valid.getPassword() == null) {
                return false;
            } else if (!modifyPasswordVO.getUserId().equals(valid.getUserId())
                    || !modifyPasswordVO.getOldPassword().equals(valid.getPassword())) {
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private SysSecUserEntity voToEntity(ModifyPasswordVO modifyPasswordVO) {
        SysSecUserEntity sysSecUserEntity = new SysSecUserEntity();
        sysSecUserEntity.setUserId(modifyPasswordVO.getUserId());
        sysSecUserEntity.setPassword(modifyPasswordVO.getConfirmPassword());
        sysSecUserEntity.setErrorCnt(0);
        return sysSecUserEntity;
    }
}
