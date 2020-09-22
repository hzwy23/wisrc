package com.wisrc.sys.webapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisrc.sys.webapp.dao.AccountInfoDao;
import com.wisrc.sys.webapp.entity.*;
import com.wisrc.sys.webapp.service.AccountInfoService;
import com.wisrc.sys.webapp.service.DeptInfoService;
import com.wisrc.sys.webapp.service.RoleService;
import com.wisrc.sys.webapp.utils.MD5;
import com.wisrc.sys.webapp.utils.PageData;
import com.wisrc.sys.webapp.utils.Result;
import com.wisrc.sys.webapp.vo.SysRoleUserVO;
import com.wisrc.sys.webapp.vo.account.AccountVO;
import com.wisrc.sys.webapp.vo.account.SetAccountVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.regex.Pattern;

@Service
public class AccountInfoServiceImpl implements AccountInfoService {

    private static Pattern NUMBER_PATTERN = Pattern.compile("[0-9]+");
    private final Logger logger = LoggerFactory.getLogger(AccountInfoServiceImpl.class);


    @Autowired
    private AccountInfoDao accountInfoDao;

    @Autowired
    private DeptInfoService deptInfoService;

    @Autowired
    private RoleService roleService;

    private static boolean check(String str) {
        if (str.indexOf("A") == 0) {
            return false;
        }
        //截取掉str从首字母起长度为beginIndex的字符串，将剩余字符串赋值给str；
        String tempStr = str.substring(1);
        //是整数返回true,否则返回false
        return NUMBER_PATTERN.matcher(tempStr).matches();
    }

    @Override
    public LinkedHashMap getUserInfo(int pageNum, int pageSize, AccountVO accountVO) {

        AccountEntity accountEntity = new AccountEntity();

        BeanUtils.copyProperties(accountVO, accountEntity);

        // 获取所有子部门信息
        String deptChildren = getDeptChildren(accountVO.getOrganizeId());

        accountEntity.setDeptCd(deptChildren);

        PageHelper.startPage(pageNum, pageSize);

        List<SysUserInfoEntity> accountlist = accountInfoDao.findUsers(accountEntity);

        PageInfo pageInfo = new PageInfo(accountlist);

        // 获取用户id列表
        Set<String> set = getIdList(accountlist);
        // 获取用户的角色信息
        LinkedHashMap<String, List<SysRoleUserVO>> roleMap = roleService.findRolesByUserIdList(set);

        for (SysUserInfoEntity ele : accountlist) {
            List<SysRoleUserVO> v = roleMap.get(ele.getUserId());
            if (v == null) {
                ele.setRoleList(new ArrayList<>());
            } else {
                ele.setRoleList(v);
            }
        }

        return PageData.pack(pageInfo.getTotal(), pageInfo.getPages(), "userInfoList", accountlist);
    }


    @Override
    @Transactional(transactionManager = "retailSystemTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public Result add(AccountEntity accountEntity) {
        //判断该员工是否存在
        SysEmployeeInfoEntity sysEmployeeInfoEntity = accountInfoDao.getEmployee(accountEntity.getEmployeeId());
        if (sysEmployeeInfoEntity == null) {
            return new Result(9999, "员工ID【" + accountEntity.getEmployeeId() + "】不存在！", null);
        } else {
            try {
                accountInfoDao.insert(accountEntity);
            } catch (DuplicateKeyException e) {
                return new Result(9999, "该账号已存在！", null);
            }
            SysSecUserEntity sysSecUserEntity = new SysSecUserEntity();
            sysSecUserEntity.setUserId(accountEntity.getUserId());
            sysSecUserEntity.setPassword(MD5.encrypt32(accountEntity.getPassword()));
            //插入默认密码
            accountInfoDao.insertPassWord(sysSecUserEntity);
        }
        return Result.success();
    }

    @Override
    public Result update(SetAccountVO setAccountVO) {
        AccountEntity accountEntity = new AccountEntity();
        BeanUtils.copyProperties(setAccountVO, accountEntity);
        accountInfoDao.update(accountEntity);
        return Result.success();
    }

    @Override
    public void restrict(AccountEntity accountEntity) {
        accountInfoDao.restrict(accountEntity);
    }

    @Override
    public Result getUserById(String userId) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUserId(userId);
        AccountEntity account = accountInfoDao.getUserById(accountEntity);
        if (account == null) {
            return Result.success();
        } else {
            Map<String, String> paraMap = new HashMap<>();
            String userIdPara = "('" + userId + "')";
            paraMap.put("userIdPara", userIdPara);
            List<RoleAndUserEntity> roleAndUserInfoList = accountInfoDao.getRoleInfoList(paraMap);
            List<Map<String, Object>> list = new ArrayList<>();
            for (RoleAndUserEntity o : roleAndUserInfoList) {
                if (userId.equals(o.getUserId())) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("roleId", o.getRoleId());
                    map.put("roleName", o.getRoleName());
                    list.add(map);
                }
            }
            account.setRoleList(list);
            return Result.success(account);
        }
    }

    /**
     * 获取某一个部门的搜有下级部门信息
     *
     * @param deptCd 部门编码
     * @return String 返回所有的子部门信息，使用逗号分割，并使用括号包裹
     */
    private String getDeptChildren(String deptCd) {
        if (deptCd == null) {
            return null;
        }
        List<String> children = deptInfoService.getChildren(deptCd);
        // 获取这个部门下所有的子部门信息，级联查询
        if (children != null && children.size() > 0) {
            StringBuilder sb = new StringBuilder("(");
            for (String m : children) {
                sb.append("'").append(m).append("'").append(",");
            }
            sb.replace(sb.length() - 1, sb.length(), ")");

            String args = sb.toString();
            if (args.length() < 4) {
                return null;
            }
            return args;
        }
        return null;
    }

    private Set<String> getIdList(List<SysUserInfoEntity> list) {
        Set<String> set = new HashSet<>();
        for (SysUserInfoEntity ele : list) {
            set.add(ele.getUserId());
        }
        return set;
    }
}
