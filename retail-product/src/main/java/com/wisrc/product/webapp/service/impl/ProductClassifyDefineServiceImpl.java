package com.wisrc.product.webapp.service.impl;

import com.wisrc.product.webapp.dao.ProductClassifyDefineDao;
import com.wisrc.product.webapp.entity.ProductClassifyDefineEntity;
import com.wisrc.product.webapp.service.ProductClassifyDefineService;
import com.wisrc.product.webapp.utils.Result;
import com.wisrc.product.webapp.utils.Time;
import com.wisrc.product.webapp.vo.FuzzyProductClassifyDefineVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductClassifyDefineServiceImpl implements ProductClassifyDefineService {
    private final Logger logger = LoggerFactory.getLogger(ProductClassifyDefineServiceImpl.class);
    @Autowired
    private ProductClassifyDefineDao productClassifyDefineDao;

    @Override
    public List<ProductClassifyDefineEntity> findAll() {
        return productClassifyDefineDao.findAll();
    }

    /**
     * 产品分诶信息是树形结构，存在上级节点信息，
     * 在获取产品分类信息时，需要对节点进行筛选
     * 筛选出某一个产品以及其下边所有的产品分类信息
     */
    @Override
    public List<ProductClassifyDefineEntity> findPosterity(String classifyCd) {
        // 获取所有的产品分类信息
        List<ProductClassifyDefineEntity> list = productClassifyDefineDao.findAll();

        // 筛选出某一个产品以及其下边所有的产品分类信息
        List<ProductClassifyDefineEntity> subList = new ArrayList<>();
        dfs(list, classifyCd, subList);
        return subList;
    }

    /**
     * 产品分诶信息是树形结构，存在上级节点信息，
     * 在获取产品分类信息时，需要对节点进行筛选
     * 筛选出某一个产品在集合树中其所有的产品分类信息
     */
    public List<ProductClassifyDefineEntity> getPosterity(String classifyCd, List<ProductClassifyDefineEntity> list) {
        // 筛选出某一个产品以及其下边所有的产品分类信息
        List<ProductClassifyDefineEntity> subList = new ArrayList<>();
        dfs(list, classifyCd, subList);
        return subList;
    }

    /**
     * 更新产品分类信息
     * 是否增加一个判断该分类是否已经被商品使用，这里的需求, 需求方还没确认,
     */
    @Override
    public Map<String, String> update(ProductClassifyDefineEntity ele) {
        Map<String, String> map = new HashMap<>();
        //判断是否有下级子类存在，存在则不能修改
        List<ProductClassifyDefineEntity> list = findPosterity(ele.getClassifyCd());
        if (list.size() > 1) {
            map.put("error", "1");
            map.put("errorMsg", "该分类等级下存在下级子类分类，不能执行修改");
            return map;
        }
        Integer level = ele.getLevelCd();
        if (level == null) {
            //前段没有传LevelCd值，那么就默认分类级别不变，也默认上级分类不变，只修改该分类的中文名，英文名
            //只更新分类的中文名与英文名
            return updatePart(ele);
        }
        if (level < 1) {
            map.put("error", "1");
            map.put("errorMsg", "分类级别是从1开始的正整数");
            return map;
        }
        if (level == 1) {
            ele.setParentCd("-1");
            String n = ele.getClassifyShortName();
            if (n == null || n.trim().isEmpty()) {
                map.put("error", "1");
                map.put("errorMsg", "一级分类必须存在分类简写");
                return map;
            }
        } else {
            if (ele.getParentCd() == null) {
                map.put("error", "1");
                map.put("errorMsg", "上级分类的ID不能为空");
                return map;
            }
            //判断上级分类是否正确
            ProductClassifyDefineEntity parent = productClassifyDefineDao.findByClassifyCdSingel(ele.getParentCd());
            if (parent != null) {
                if (parent.getClassifyCd().equals(ele.getClassifyCd())) {
                    map.put("error", "1");
                    map.put("errorMsg", "不能自己成为自己的上级分类");
                    return map;
                }
                int pLevel = parent.getLevelCd();
                if (pLevel >= level) {  //级别高，数字越小
                    map.put("error", "1");
                    map.put("errorMsg", "子类的分类级别必须低于父级分类");
                    return map;
                }
            } else {
                map.put("error", "1");
                map.put("errorMsg", "上级分类未找到！");
                return map;
            }


            //是否增加一个判断该分类是否已经被商品使用，这里的需求, 需求方还没确认
//            int size = productClassifyDefineDao.getProduct(ele.getClassifyCd());
//            if (size > 0) {
//                map.put("error", "1");
//                map.put("errorMsg", "该分类正在被商品使用中，不能更新操作！");
//                return map;
//            }

            //检查英文名或者中文名是否重复
            int nameUnique = productClassifyDefineDao.checkNameUnique(
                    parent.getClassifyCd(),
                    ele.getClassifyCd(),
                    ele.getClassifyNameCh(), ele.getClassifyNameEn());
            if (nameUnique != 0) {
                map.put("error", "1");
                map.put("errorMsg", "同一个分类下的子分类中文名称或者英文名称不能相同");
                return map;
            }

            String time = Time.getCurrentTime();
            ele.setUpdateTime(time);
            productClassifyDefineDao.update(ele);
            map.put("error", "0");
        }
        return map;
    }

    @Override
    public Map<String, String> insert(ProductClassifyDefineEntity ele) {
        Map<String, String> map = new HashMap<>();
        String currentTime = Time.getCurrentTime();
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        ele.setClassifyCd(uuid);
        ele.setCreateTime(currentTime);
        if (ele.getLevelCd() == null) {
            map.put("error", "1");
            map.put("errorMsg", "分类级别不存在，请正确选择");
            return map;
        }
        int level = ele.getLevelCd();
        if (level < 1) {
            map.put("error", "1");
            map.put("errorMsg", "分类级别从1开始的正整数");
            return map;
        } else if (level == 1) {
            //这里是一级分类
            String classifyShortName = ele.getClassifyShortName();
            if (classifyShortName == null || classifyShortName.trim().isEmpty()) {
                map.put("error", "1");
                map.put("errorMsg", "一类等级必须有分类简写");
                return map;
            } else {
                //检查分类简写的唯一性
                //检查产品分类的简写的字符位数（要求为三位大写字母）
                String upper = classifyShortName.toUpperCase();
                Boolean flag = upper.matches("^[A-Z]{3}$");
                if (!flag) {
                    map.put("error", "1");
                    map.put("errorMsg", "分类简写不符合要求，要求为（三位）大写字母组成");
                    return map;
                }
                int number = productClassifyDefineDao.checkUnique(upper);
                if (number != 0) {
                    map.put("error", "1");
                    map.put("errorMsg", "分类简写已经存在，请重新编辑!");
                    return map;
                }
                //检查英文名或者中文名是否重复
                int nameUnique = productClassifyDefineDao.checkNameUnique(
                        "-1", null,
                        ele.getClassifyNameCh(), ele.getClassifyNameEn());
                if (nameUnique != 0) {
                    map.put("error", "1");
                    map.put("errorMsg", "一级菜单不能存在相同的中文名称或者英文名称");
                    return map;
                }
                ele.setParentCd("-1");
                ele.setClassifyShortName(upper);
                productClassifyDefineDao.insert(ele);
                map.put("error", "0");
            }
        } else {
            if (ele.getParentCd() == null) {
                map.put("error", "1");
                map.put("errorMsg", "分类的直属上级分类ID不能为空");
                return map;
            }
            //执行判断，判断他的直属父类pid是与levelCd级别是否匹配
            ProductClassifyDefineEntity father = productClassifyDefineDao.findByClassifyCdSingel(ele.getParentCd());
            if (father == null) {
                map.put("error", "1");
                map.put("errorMsg", "找不到该分类的直属上级分类");
            }
            int levelCheck = level - 1;
            if (levelCheck != father.getLevelCd()) {
                map.put("error", "1");
                map.put("errorMsg", "对象的分类级别与上级分类不匹配");
            }

            //检查英文名或者中文名是否重复
            int nameUnique = productClassifyDefineDao.checkNameUnique(
                    ele.getParentCd(), null,
                    ele.getClassifyNameCh(), ele.getClassifyNameEn());
            if (nameUnique != 0) {
                map.put("error", "1");
                map.put("errorMsg", "同一个分类下的子分类中文名称或者英文名称不能相同");
                return map;
            }

            //这里需要确认需求!!!   下级分类是否携带分类简写（现代祖先的分类简写还是自己的间歇还是置为""）
            ele.setClassifyShortName("");

            productClassifyDefineDao.insert(ele);
            map.put("error", "0");

        }
        return map;
    }

    /**
     * 删除自己及其所有子类
     *
     * @param classifyCd 产品分类编码
     */
    @Override
    public void delete(String classifyCd) {
        List<ProductClassifyDefineEntity> list = findAll();
        List<ProductClassifyDefineEntity> ret = new ArrayList<>();
        dfs(list, classifyCd, ret);
        for (ProductClassifyDefineEntity m : ret) {
            productClassifyDefineDao.delete(m.getClassifyCd());
        }
    }


    /**
     * 如果classifyCd下的对象，存在下级分类则不能执行删除！
     *
     * @param classifyCd
     */
    @Override
    public Map<String, String> deleteSafe(String classifyCd) {
        Map<String, String> map = new HashMap<>();
        List<ProductClassifyDefineEntity> list = productClassifyDefineDao.findChildren(classifyCd);
        int size = productClassifyDefineDao.getProduct(classifyCd);
        if (size > 0) {
            map.put("error", "1");
            map.put("errorMsg", "该分类正在被商品使用中，不能执行删除操作！");
            return map;
        }

        if (list.size() > 0) {
            map.put("error", "1");
            map.put("errorMsg", "该分类对象存在下级分类，不能执行删除操作！");
        } else {
            productClassifyDefineDao.delete(classifyCd);
            map.put("error", "0");
        }
        return map;
    }

    /**
     * 根据classifyCd查找分类的信息
     *
     * @param classifyCd
     * @return
     */
    @Override
    public Map<String, Object> findById(String classifyCd) {
        Map<String, Object> map = productClassifyDefineDao.findByClassifyCd(classifyCd);
        if (map != null) {
            String parentCd = (String) map.get("parentCd");
            if (!parentCd.equals("-1")) {
                ProductClassifyDefineEntity entity = productClassifyDefineDao.findByClassifyCdSingel(parentCd);
                map.put("parentNameCh", entity.getClassifyNameCh());
                map.put("parentNameEn", entity.getClassifyNameEn());
            } else {
                map.put("parentNameCh", null);
                map.put("parentNameEn", null);
            }
        }
        return map;
    }


    @Override
    public ProductClassifyDefineEntity getRootClassify(String classifyCd) {
        //判断是否存在该分类
        ProductClassifyDefineEntity re = productClassifyDefineDao.findByClassifyCdSingel(classifyCd);
        if (re == null) {
            return re;
        }
        // 获取所有的产品分类信息
        List<ProductClassifyDefineEntity> list = productClassifyDefineDao.findAll();
        if (list == null) {
            return null;
        } else {
            //找祖先根节点
            ProductClassifyDefineEntity result = new ProductClassifyDefineEntity();
            //此处如果修改数据库父子的（子类的分类缩写与他的祖先一级分类保持一致）传承逻辑，既可不用递归, 可优化性能！！！
            ProductClassifyDefineEntity result2 = getRoot(list, classifyCd, result);
            return result2;
        }
    }

    /**
     * 递归找根节点
     */
    private ProductClassifyDefineEntity getRoot(List<ProductClassifyDefineEntity> list, String classifyCd, ProductClassifyDefineEntity result) {
        if (classifyCd.equals("-1") || classifyCd == null || classifyCd.isEmpty() || list.size() == 0 || list == null) {
            return result;
        } else {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getClassifyCd().equals(classifyCd)) {
                    result = list.get(i);
                    break;
                }
            }
            return getRoot(list, result.getParentCd(), result);
        }
    }

    @Override
    public Map findByClassifyCd(String classifyCd) {
        return productClassifyDefineDao.findByClassifyCd(classifyCd);
    }


    // 在整个产品分类树中遍历查询某个节点以及其下级节点数据
    // set参数用来存放已经遍历过的节点，一个节点不允许重复遍历
    private void dfs(List<ProductClassifyDefineEntity> list, String classifyCd, List<ProductClassifyDefineEntity> ret) {
        if (classifyCd == null || classifyCd.isEmpty()) {
            logger.error("遍历查询产品信息时垂岸产品分类编码为空");
            return;
        }
        for (ProductClassifyDefineEntity m : list) {
            if (classifyCd.equals(m.getParentCd())) {
                ret.add(m);
                dfs(list, m.getClassifyCd(), ret);
            } else if (classifyCd.equals(m.getClassifyCd()) && !ret.contains(m)) {
                ret.add(m);
            }
        }
    }


    @Override
    public List<ProductClassifyDefineEntity> findLevel(int levelCd) {
        return productClassifyDefineDao.findLevel(levelCd);
    }

    /**
     * 以模糊查询为基础数据，找出这些数据的一级分类
     * 再以一级分类（去重）分批装入基础数据为（家族）信息
     * 以一级分类作为分页，输出（不懂去问需求 ）
     *
     * @param pageNum
     * @param pageSize
     * @param ele
     * @return
     */
    @Override
    public Map<String, Object> fuzzyQueryFamilyNew(int pageNum, int pageSize, FuzzyProductClassifyDefineVO ele) {
        ProductClassifyDefineEntity productClassifyDefineEntity = new ProductClassifyDefineEntity();
        BeanUtils.copyProperties(ele, productClassifyDefineEntity);
        if (productClassifyDefineEntity.getClassifyNameCh() == null) {
            productClassifyDefineEntity.setClassifyNameCh("");
        }
        if (productClassifyDefineEntity.getClassifyNameEn() == null) {
            productClassifyDefineEntity.setClassifyNameEn("");
        }
        if (productClassifyDefineEntity.getClassifyShortName() == null) {
            productClassifyDefineEntity.setClassifyShortName("");
        }
        Map<String, ProductClassifyDefineEntity> tempMap = new HashMap<>();
        List<ProductClassifyDefineEntity> childrenList = new ArrayList<>();
        //模糊查找自己
        List<ProductClassifyDefineEntity> ownlist = productClassifyDefineDao.fuzzyQuery(productClassifyDefineEntity);
        //查找自己及其所有子类
        // 获取所有的产品分类信息
        List<ProductClassifyDefineEntity> allList = productClassifyDefineDao.findAll();
        for (int i = 0; i < ownlist.size(); i++) {
            //含本身！！！
            List<ProductClassifyDefineEntity> child = getPosterity(ownlist.get(i).getClassifyCd(), allList);
            childrenList.addAll(child);
        }
        //查找直系祖先
        List<ProductClassifyDefineEntity> fatherList = new ArrayList<>();
        //一级分类
        List<ProductClassifyDefineEntity> rootFather = new ArrayList<>();
        for (int i = 0; i < ownlist.size(); i++) {
            String parentCd = ownlist.get(i).getParentCd();
            if (parentCd.equals("-1")) {
                rootFather.add(ownlist.get(i));
            } else {
                fatherList = getAncestor(allList, ownlist.get(i).getParentCd(), fatherList);
                //最后一个就是一级分类
                if (fatherList.size() > 0) {
                    int orderNum = fatherList.size() - 1;
                    ProductClassifyDefineEntity root = fatherList.get(orderNum);
                    rootFather.add(root);
                }
            }
        }
        //去重start
        for (int i = 0; i < childrenList.size(); i++) {
            String sku = childrenList.get(i).getClassifyCd();
            tempMap.put(sku, childrenList.get(i));
        }
        for (int i = 0; i < fatherList.size(); i++) {
            String sku = fatherList.get(i).getClassifyCd();
            tempMap.put(sku, fatherList.get(i));
        }
        List<ProductClassifyDefineEntity> gatherList = new ArrayList<>();
        for (ProductClassifyDefineEntity value : tempMap.values()) {
            gatherList.add(value);
        }

        //针对一级分类的分类简写进行排序
        Collections.sort(rootFather, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                ProductClassifyDefineEntity pCD1 = (ProductClassifyDefineEntity) o1;
                ProductClassifyDefineEntity pCD2 = (ProductClassifyDefineEntity) o2;
                return pCD1.getClassifyShortName().compareTo(pCD2.getClassifyShortName());
            }
        });

        Map<String, ProductClassifyDefineEntity> rrMap = new LinkedHashMap<>();
        for (int i = 0; i < rootFather.size(); i++) {
            String sku = rootFather.get(i).getClassifyCd();
            rrMap.put(sku, rootFather.get(i));
        }
        List<ProductClassifyDefineEntity> newRootFather = new ArrayList<>();
        for (ProductClassifyDefineEntity value : rrMap.values()) {
            newRootFather.add(value);
        }
        //去重end
        int total = newRootFather.size();
        int pages = (total) % (pageSize) > 0 ? (total) / (pageSize) + 1 : (total) / (pageSize);
        int start = (pageNum - 1) * (pageSize);
        int end = (pageNum) * (pageSize) - 1;
        if (end > (total - 1)) {
            end = total - 1;
        }
        List<ProductClassifyDefineEntity> newList = new ArrayList<>();
        if (start == end) {
            newList.add(newRootFather.get(start));
        } else {
            newList = newRootFather.subList(start, (end + 1));
        }

        //分批放入集合
//        List<List<ProductClassifyDefineEntity>> resultList = new ArrayList<>();
//        for (int i = 0; i < newList.size(); i++) {
//            List<ProductClassifyDefineEntity> singleList = getPosterity(newList.get(i).getClassifyCd(), gatherList);
//            resultList.add(singleList);
//        }

        //应前端要求改为整体打包
        List<ProductClassifyDefineEntity> resultList = new ArrayList<>();
        for (int i = 0; i < newList.size(); i++) {
            List<ProductClassifyDefineEntity> singleList = getPosterity(newList.get(i).getClassifyCd(), gatherList);
            resultList.addAll(singleList);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("pages", pages);
        map.put("resultList", resultList);
        return map;

    }

    @Override
    public Map<String, String> updatePart(ProductClassifyDefineEntity entity) {
        Map<String, String> map = new HashMap<>();
        Map pcde = findById(entity.getClassifyCd());
        if (pcde != null) {
            String parentCd = (String) pcde.get("parentCd");
            //检查英文名或者中文名是否重复
            int nameUnique = productClassifyDefineDao.checkNameUnique(parentCd, entity.getClassifyCd(),
                    entity.getClassifyNameCh(), entity.getClassifyNameEn());
            if (nameUnique != 0) {
                map.put("error", "1");
                map.put("errorMsg", "同一个分类下的子分类中文名称或者英文名称不能相同");
                return map;
            }
        }
        String time = Time.getCurrentTime();
        entity.setUpdateTime(time);
        //只更新分类的中文名与英文名
        productClassifyDefineDao.updatePart(entity);
        map.put("error", "0");
        return map;
    }

    @Override
    public Result findItselfAndAncestor(String classifyCd) {
        ProductClassifyDefineEntity pc = productClassifyDefineDao.findByClassifyCdSingel(classifyCd);
        List<ProductClassifyDefineEntity> allList = productClassifyDefineDao.findAll();
        List<ProductClassifyDefineEntity> resultList = new ArrayList<>();
        resultList.add(pc);
        resultList = getAncestor(allList, pc.getParentCd(), resultList);
        Collections.reverse(resultList); // 倒序排列
        return Result.success(resultList);
    }


    /**
     * 以某个父ID开始查询
     * 获取其直系祖先
     *
     * @param list
     * @param parentCd
     * @param resultList
     */
    private List<ProductClassifyDefineEntity> getAncestor(List<ProductClassifyDefineEntity> list, String parentCd, List<ProductClassifyDefineEntity> resultList) {
        if (parentCd.equals("-1") || parentCd == null || parentCd.isEmpty() || list.size() == 0 || list == null) {
            return resultList;
        } else {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getClassifyCd().equals(parentCd)) {
                    resultList.add(list.get(i));
                    getAncestor(list, list.get(i).getParentCd(), resultList);
                    break;
                }
            }
        }
        return resultList;
    }

}
