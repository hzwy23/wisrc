package com.wisrc.sys.webapp.service.impl;

import com.wisrc.sys.webapp.dao.SysResourceInfoDao;
import com.wisrc.sys.webapp.entity.SysResourceInfoEntity;
import com.wisrc.sys.webapp.service.MenusService;
import com.wisrc.sys.webapp.utils.Result;
import com.wisrc.sys.webapp.vo.MenuTreeVO;
import com.wisrc.sys.webapp.vo.MenusMetaVO;
import com.wisrc.sys.webapp.vo.MenusVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class MenusServiceImpl implements MenusService {

    private static final String ADMIN = "admin";
    private static final String ROOTSID = "-1";

    private final Logger logger = LoggerFactory.getLogger(MenusServiceImpl.class);

    // 防止遍历资源树时，垂岸了循环循环情况，最大深度为5，即最多支持五级菜单
    // 后续可以根据具体需求，调整这个值的大小
    private final int MAX_DEPT = 5;

    @Autowired
    private SysResourceInfoDao sysResourceInfoDao;

    @Override
    public Result getMenus(String userId) {
        logger.info("获取菜单信息，账号是：{}", userId);
        try {
            return Result.success(findAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.failure();
    }

    @Override
    public Result getMainMenus(String userId) {
        logger.debug("获取一级菜单信息，账号是：{}", userId);
        if (ADMIN.equals(userId)) {
            return Result.success(sysResourceInfoDao.findMainMenus());
        } else {
            List<SysResourceInfoEntity> result = getOwnerMenus(userId);
            for (SysResourceInfoEntity ele : result) {
                if (1 != ele.getMenuType()) {
                    result.remove(ele);
                }
            }
            // 补齐拥有菜单的父级菜单
            return Result.success(result);
        }
    }

    @Override
    public Result getRouters(String userId, String menuId) {
        List<SysResourceInfoEntity> list = new ArrayList<>();
        if (ADMIN.equals(userId)) {
            list = sysResourceInfoDao.findPage();
        } else {
            list = getOwnerMenus(userId);
        }
        List<MenusVO> result = new ArrayList<>();
        bfs(list, menuId, result);
        return Result.success(result);
    }

    @Override
    public Result getMenuDetails(String menuId) {
        SysResourceInfoEntity sysResourceInfoEntity = sysResourceInfoDao.getMenuDetails(menuId);
        return Result.success(sysResourceInfoEntity);
    }

    @Override
    public Result getMenusTree(String userId, boolean isNode) {
        // 获取到所有的菜单信息
        List<SysResourceInfoEntity> list;
        if (ADMIN.equals(userId)) {
            if (isNode) {
                list = sysResourceInfoDao.findAllNodes();
            } else {
                list = findAll();
            }
        } else {
            list = getOwnerMenus(userId);
            if (isNode) {
                for (SysResourceInfoEntity ele : list) {
                    if (ele.getMenuType() != 1 && ele.getMenuType() != 3) {
                        list.remove(ele);
                    }
                }
            }
        }

        // 获取顶点信息
        List<MenuTreeVO> result = getRoots(list);

        // 循环遍历，list列表中已经是剔除页面按钮和一级菜单之后的数据，
        // 剩余的数据全部都是菜单目录和页面按钮
        int idx = 0;
        while (list.size() > 0 && idx++ < MAX_DEPT) {
            for (MenuTreeVO ele : result) {
                treeBFS(list, ele);
            }
        }
        return Result.success(result);
    }

    @Override
    public Result getPageHandle(String menuId) {
        // 只有点击页面时，才会触发获取页面操作
        List<SysResourceInfoEntity> pageHandlers = sysResourceInfoDao.getPageHandle(menuId);
        return Result.success(pageHandlers);
    }

    @Override
    public Result addMenu(SysResourceInfoEntity sysResourceInfoEntity) {
        if (sysResourceInfoEntity.getMenuType() == 1) {
            sysResourceInfoEntity.setParentId("-1");
        } else if (sysResourceInfoEntity.getMenuType() != 4) {
            // 检查菜单的层级
            List<SysResourceInfoEntity> parentList = getParents(sysResourceInfoEntity.getParentId());
            if (parentList.size() >= 3) {
                return Result.failure(423, "模块子菜单层级不允许超过3级", parentList);
            }
        }
        sysResourceInfoDao.insert(sysResourceInfoEntity);
        return Result.success();
    }

    @Override
    @Transactional(transactionManager = "retailSystemTransactionManager")
    public Result deleteMenu(String menuId) {
        // 获取这个资源下级所有资源
        List<SysResourceInfoEntity> list = findAll();
        List<SysResourceInfoEntity> result = new ArrayList<>();
        dfs(list, menuId, result, MAX_DEPT);
        sysResourceInfoDao.delete(menuId);
        for (SysResourceInfoEntity ele : result) {
            sysResourceInfoDao.delete(ele.getMenuId());
        }
        //删除这个下级一级它自己所有的信息
        return Result.success();
    }

    @Override
    public Result updateMenu(SysResourceInfoEntity sysResourceInfoEntity) {

        // 当编辑的是一级菜单时，设置父级菜单为-1
        if (sysResourceInfoEntity.getMenuType() == 1) {
            sysResourceInfoEntity.setParentId("-1");
        }

        // 更新之前，先检查一下上级菜单
        // 获取当前菜单的所有子菜单
        List<SysResourceInfoEntity> subMenuList = new ArrayList<>();
        int dept = 1;
        dfs(findAll(), sysResourceInfoEntity.getMenuId(), subMenuList, dept);

        // 判断编辑后的上级菜单是否在当前菜单的下级，
        for (SysResourceInfoEntity ele : subMenuList) {
            if (sysResourceInfoEntity.getParentId().equals(ele.getMenuId())) {
                return Result.failure(390, "禁止将上级菜单设置到当前菜单的下级", sysResourceInfoEntity);
            }
        }

        if (sysResourceInfoDao.update(sysResourceInfoEntity)) {
            return Result.success();
        } else {
            return Result.failure();
        }
    }

    // 查询这个菜单的所有父级菜单
    @Override
    public List<SysResourceInfoEntity> getParents(String menuId) {
        if (ROOTSID.equals(menuId)) {
            return null;
        }
        // 获取所有的菜单信息
        List<SysResourceInfoEntity> list = findAll();
        List<SysResourceInfoEntity> ret = new ArrayList<>();
        dfsParent(list, menuId, ret);
        return ret;
    }

    private void dfsParent(List<SysResourceInfoEntity> all, String menuId, List<SysResourceInfoEntity> ret) {
        if (ROOTSID.equals(menuId)) {
            return;
        }

        for (SysResourceInfoEntity m : all) {
            if (menuId.equals(m.getMenuId())) {
                if (ret.contains(m)) {
                    return;
                } else {
                    ret.add(m);
                    dfsParent(all, m.getParentId(), ret);
                }
            }
        }
    }

    private List<SysResourceInfoEntity> findAll() {
        return sysResourceInfoDao.findAll();
    }

    // 获取层级信息
    private void bfs(List<SysResourceInfoEntity> all, String menuId, List<MenusVO> result) {
        Map<String, MenusVO> map = getRoots(all, menuId);
        // 获取子菜单信息
        for (String key : map.keySet()) {
            List<MenusVO> children = getChildren(all, key);
            MenusVO menusVO = map.get(key);
            menusVO.setChildren(children);
            result.add(menusVO);
        }
    }

    /**
     * 获取图顶点信息
     */
    private Map<String, MenusVO> getRoots(List<SysResourceInfoEntity> all, String menuId) {
        // 获取定点信息
        Map<String, MenusVO> map = new LinkedHashMap<>();
        for (SysResourceInfoEntity ele : all) {
            if (menuId.equals(ele.getParentId())) {
                MenusVO menusVO = mapStruct(ele);
                map.put(ele.getMenuId(), menusVO);
            }
        }
        return map;
    }

    private List<MenusVO> getChildren(List<SysResourceInfoEntity> all, String menuId) {
        List<MenusVO> result = new ArrayList<>();
        for (SysResourceInfoEntity ele : all) {
            if (menuId.equals(ele.getParentId())) {
                MenusVO menusVO = mapStruct(ele);
                result.add(menusVO);
            }
        }
        return result;
    }

    private MenusVO mapStruct(SysResourceInfoEntity ele) {
        MenusVO menusVO = new MenusVO();
        MenusMetaVO menusMetaVO = new MenusMetaVO();
        menusMetaVO.setTitle(ele.getMetaTitle());
        menusMetaVO.setIcon(ele.getMetaIcon());
        menusMetaVO.setNoCache(ele.isMetaNoCache());
        menusVO.setMeta(menusMetaVO);
        menusVO.setPath(ele.getPath());
        menusVO.setAlwaysShow(ele.isAlwaysShow());
        menusVO.setComponent(ele.getComponent());
        menusVO.setHidden(ele.isHidden());
        menusVO.setName(ele.getMenuName());
        menusVO.setRedirect(ele.getRedirect());
        menusVO.setMenuType(ele.getMenuType());
        return menusVO;
    }

    // 通过遍历，获取资源树指定节点下边所有的节点信息
    private void dfs(List<SysResourceInfoEntity> all, String menuId, List<SysResourceInfoEntity> result, int dept) {
        for (SysResourceInfoEntity ele : all) {
            if (4 != ele.getMenuType() &&
                    menuId.equals(ele.getParentId())) {
                result.add(ele);
                if (dept < MAX_DEPT) {
                    dfs(all, ele.getMenuId(), result, dept++);
                }
            }
        }
    }

    /**
     * 获取顶点信息，也就是一级菜单信息
     * 并且在列表中清除顶点信息和页面菜单信息
     */
    private List<MenuTreeVO> getRoots(List<SysResourceInfoEntity> list) {
        List<MenuTreeVO> mlist = new CopyOnWriteArrayList<>();
        for (SysResourceInfoEntity elm : list) {
            if (elm.getMenuType() == 1) {
                // 获取一级菜单列表
                mlist.add(toVo(elm));
                list.remove(elm);
            } else if (elm.getMenuType() == 4) {
                // menuType == 4 表示页面操作按钮，不需要在菜单栏中展示，所有踢出去
                list.remove(elm);
            }
        }
        return mlist;
    }


    /**
     * 循环遍历每一个菜单下所有的子菜单信息
     */
    private void treeBFS(List<SysResourceInfoEntity> all, MenuTreeVO menuTreeVO) {
        List<MenuTreeVO> children = new CopyOnWriteArrayList<>();
        for (SysResourceInfoEntity ele : all) {
            if (menuTreeVO.getId().equals(ele.getParentId())) {
                children.add(toVo(ele));
                all.remove(ele);
            }
        }
        if (children.size() == 0) {
            menuTreeVO.setChildren(null);
        } else {
            menuTreeVO.setChildren(children);
            for (MenuTreeVO c : children) {
                treeBFS(all, c);
            }
        }
    }

    // 对菜单进行补齐操作
    private void complementParentMenus(LinkedHashMap<String, SysResourceInfoEntity> map, Set<String> owner, String menuId) {
        if (ROOTSID.equals(menuId)) {
            return;
        }
        // 获取这个菜单的上级
        if (map.containsKey(menuId)) {
            String parentId = map.get(menuId).getParentId();
            if (!owner.contains(parentId)) {
                owner.add(parentId);
                complementParentMenus(map, owner, parentId);
            }
        }
    }

    private LinkedHashMap toMap(List<SysResourceInfoEntity> list) {
        LinkedHashMap map = new LinkedHashMap();
        for (SysResourceInfoEntity ele : list) {
            map.put(ele.getMenuId(), ele);
        }
        return map;
    }

    private MenuTreeVO toVo(SysResourceInfoEntity elm) {
        MenuTreeVO menuTreeVO = new MenuTreeVO();
        menuTreeVO.setId(elm.getMenuId());
        menuTreeVO.setMenuType(elm.getMenuType());
        menuTreeVO.setLabel(elm.getMetaTitle());
        menuTreeVO.setChildren(null);
        return menuTreeVO;
    }


    private List<SysResourceInfoEntity> getOwnerMenus(String userId) {
        // 获取所有的菜单信息
        List<SysResourceInfoEntity> list = findAll();

        // 获取这个用户拥有的菜单信息
        Set<String> owner = sysResourceInfoDao.findUserMenus(userId);

        Set<String> set = new HashSet<>(owner);

        for (String ele : set) {
            complementParentMenus(toMap(list), owner, ele);
        }

        for (SysResourceInfoEntity ele : list) {
            if (!owner.contains(ele.getMenuId())) {
                list.remove(ele);
            }
        }

        return list;
    }
}
