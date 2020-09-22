package com.wisrc.product.webapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisrc.product.webapp.entity.*;
import com.wisrc.product.webapp.service.ProductDefineService;
import com.wisrc.product.webapp.service.ProductModifyHistoryService;
import com.wisrc.product.webapp.utils.ExcelTools;
import com.wisrc.product.webapp.utils.Result;
import com.wisrc.product.webapp.utils.Time;
import com.wisrc.product.webapp.vo.GetProductInfoVO;
import com.wisrc.product.webapp.vo.ProductMachineInfoDetailVO;
import com.wisrc.product.webapp.vo.productDefine.CostPriceVO;
import com.wisrc.product.webapp.vo.productDefine.SetBatchCostPriceVO;
import com.wisrc.product.webapp.vo.productInfo.add.ProductImagesVO;
import com.wisrc.product.webapp.dao.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ProductDefineServiceImpl implements ProductDefineService {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(ProductDefineServiceImpl.class);
    @Autowired
    private ProductDefineDao productDefineDao;
    @Autowired
    private ProductClassifyDefineDao productClassifyDefineDao;
    @Autowired
    private ProductImagesDao productImagesDao;
    @Autowired
    private ProductMachineInfoDao productMachineInfoDao;
    @Autowired
    private ProductDetailsInfoDao productDetailsInfoDao;
    @Autowired
    private ProductSpecificationsInfoDao productSpecificationsInfoDao;
    @Autowired
    private ProductDeclareInfoDao productDeclareInfoDao;
    @Autowired
    private ProductModifyHistoryDao productModifyHistoryDao;
    @Autowired
    private ProductModifyHistoryService productModifyHistoryService;

    /**
     * 格式化产品简写，名称简写要求1~5个大写字母，当用户输入的大写字母不足5位的时候，SKU中名称简写部分在后面补0
     *
     * @param str
     * @param strLength
     * @return
     */
    private static String addZeroForNum(String str, int strLength) {
        int strLen = str.length();
        StringBuffer sb = null;
        while (strLen < strLength) {
            sb = new StringBuffer();
            //右补0
            sb.append(str).append("0");
            str = sb.toString();
            strLen = str.length();
        }
        return str;
    }

    public static boolean check(String str) {
        //循环遍历字符串
        for (int i = 0; i < str.length(); i++) {
            char cha = str.charAt(i);
            boolean flag = (cha >= 'A' && cha <= 'Z') || (cha >= 'a' && cha <= 'z') || Character.isDigit(str.charAt(i));
            if (!flag) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Result findAll() {
        List<ProductDefineEntity> list = productDefineDao.findAll();
        return Result.success(list);
    }

    /**
     * 插入产品定义信息
     * 产品ID在这个环节生成
     */
    @Override
    public Map<String, String> insert(ProductDefineEntity ele, String classifyShortName) {

        double costPrice = 0.00;
        ele.setCostPrice(costPrice);

        Map<String, String> map = new HashMap<>();
        map.put("error", "0");
        String skuShortName = ele.getSkuShortName();
        int length = skuShortName.length();
        if (length > 5 || length < 1) {
            map.put("error", "1");
            map.put("errorMsg", "产品分类简写长度不合法");
            return map;
        }
        boolean flag = check(skuShortName);
        if (!flag) {
            map.put("error", "1");
            map.put("errorMsg", "产品分类简写含有非法字符");
            return map;
        }
        ele.setCreateTime(Time.getCurrentTime());
        ele.setSkuShortName(ele.getSkuShortName().toUpperCase());
        String skuShortNameFrom = addZeroForNum(ele.getSkuShortName(), 5).toUpperCase();
        ele.setSkuId(classifyShortName);
        ele.setStatusCd(1);

        String randomValue = UUID.randomUUID().toString().replace("-", "");
//        //流水号
//        int maxSize = productDefineDao.getMaxSize(classifyShortName);
//        int size = maxSize + 1;
//        if (size >= 99999) {
//            map.put("error", "1");
//            map.put("errorMsg", "单级别分类最大数量达到最大值，建议新开分类");
//            return map;
//        }
//
//        DecimalFormat df = new DecimalFormat("00000");
//        String oderNumber = df.format(size);
        //生成sku id号
        ele.setSkuId(randomValue);
        //两约束条件
        ele.setClassifyShortName(classifyShortName);
//        ele.setSize(size);
        try {
            productDefineDao.insert(ele);
        } catch (Exception e) {
            logger.info("新增库存SKU信息失败", e.getMessage());
            map.put("error", "1");
            map.put("errorMsg", "新增库存SKU信息失败，请联系管理员");
            return map;
        }
        // 获取新增的skuID信息
        String skuId = productDefineDao.getSkuId(randomValue);
        ele.setSkuId(skuId);
        //==============添加历史纪录
        try {
            productModifyHistoryService.historyInsert(ele, ele.getCreateTime());
        } catch (Exception e) {
            logger.error("保存历史纪录出错！", e);
            map.put("error", "1");
            map.put("errorMsg", "保存历史纪录出错！");
            throw new RuntimeException("保存历史纪录出错");
        }
        //==============
        return map;
    }

    @Override
    public Map<String, Object> fuzzyQueryNew(Integer pageNum,
                                             Integer pageSize,
                                             GetProductInfoVO getProductInfoVO,
                                             List<String> classifyCdList) {

        String classifyCdPara = createStrPara(classifyCdList);

        Map<String, String> mapPara = createMapPara(getProductInfoVO, classifyCdPara);

        // 转换模糊查询，需要过滤掉的sku
        if (getProductInfoVO.getIgnoreSkuIds() != null) {
            StringBuffer ignoreSkuIds = new StringBuffer("(");
            for (String ele : getProductInfoVO.getIgnoreSkuIds()) {
                ignoreSkuIds.append("'").append(ele).append("',");
            }
            ignoreSkuIds.setCharAt(ignoreSkuIds.length() - 1, ')');
            if (ignoreSkuIds.length() < 4) {
                // 不用设置忽略sku
                mapPara.put("ignoreSkuIds", null);
            } else {
                mapPara.put("ignoreSkuIds", ignoreSkuIds.toString());
            }
        }

        List<ProductShowEntity> productShowList = new ArrayList<>();
        if (pageNum == null || pageNum < 1 || pageSize == null || pageSize < 1) {
            // 不执行分页
            productShowList = productDefineDao.findFuzzyNew(mapPara);
        } else {
            // 执行分页
            PageHelper.startPage(pageNum, pageSize);
            productShowList = productDefineDao.findFuzzyNew(mapPara);
        }

        if (productShowList == null || productShowList.size() == 0) {
            //没有对应数据
            Map<String, Object> map = new HashMap();
            map.put("total", 0);
            map.put("pages", 1);
            map.put("productData", productShowList);
            return map;
        } else {
            //处理
            if (getProductInfoVO.getIgnoreImages()) {
                // 不获取图片信息
                PageInfo pageInfo = new PageInfo(productShowList);
                long total = pageInfo.getTotal();
                int pages = pageInfo.getPages();
                Map<String, Object> map = new HashMap();
                map.put("total", total);
                map.put("pages", pages);
                map.put("productData", productShowList);
                return map;
            }
            return solve(productShowList);
        }
    }

    @Override
    @Transactional(transactionManager = "retailProductTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public Result updateCostPrice(@Valid @RequestBody SetBatchCostPriceVO vo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new Result(9999, bindingResult.getFieldError().getDefaultMessage(), null);
        }
        List<CostPriceVO> costPriceVOList = vo.getCostPriceVOList();
        ProductDefineEntity entity = new ProductDefineEntity();
        for (CostPriceVO o : costPriceVOList) {
            entity.setSkuId(o.getSkuId());
            entity.setCostPrice(o.getCostPrice());
            productDefineDao.updateCostPrice(entity);
        }
        return Result.success();
    }

    @Override
    public Result findPackingMaterial() {
        Integer typeCd = 2;
        List<ProductDefineEntity> list = productDefineDao.getSkuByTypeCd(typeCd);
        return Result.success(list);
    }

    @Override
    public Result getDefineBatch(List<String> skuIds) {
        if (skuIds.size() == 0) {
            return Result.success(null);
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < skuIds.size(); i++) {
            if (i == 0) {
                sb.append("(" + "'" + skuIds.get(i) + "'");
            } else {
                sb.append("," + "'" + skuIds.get(i) + "'");
            }
            if (i == (skuIds.size() - 1)) {
                sb.append(")");
            }
        }
        String condition = sb.toString();
        ProductDefineEntity entity = new ProductDefineEntity();
        entity.setSkuId(condition);
        List<ProductDefineEntity> list = productDefineDao.getDefineInfoBatch(entity);
        return Result.success(list);
    }

    //处理
    private Map<String, Object> solve(List<ProductShowEntity> productShowList) {

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < productShowList.size(); i++) {
            String skuId = productShowList.get(i).getSku();
            if (i == 0) {
                sb.append("(" + "'" + skuId + "'");
            } else {
                sb.append("," + "'" + skuId + "'");
            }

            if (i == (productShowList.size() - 1)) {
                sb.append(")");
            }
        }
        String condition = sb.toString();
        if (productShowList.size() == 0) {
            condition = "(\"\")";
        }

        Map<String, Object> sqlMap = new HashMap<>();
        sqlMap.put("condition", condition);
        sqlMap.put("typeCd", 1);
        List<ProductImagesEntity> pImagesList = productDefineDao.getImagesList(sqlMap);
        List<ProductMachineInfoDetailEntity> pMachineList = productDefineDao.getMachineList(sqlMap);

        //包装
        List<Map<String, Object>> resultReal = new ArrayList<>();
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < productShowList.size(); i++) {
            ProductShowEntity entity = productShowList.get(i);
            String skuId = entity.getSku();

            List<ProductImagesVO> productImagesVOList = new ArrayList<>();
            if (pImagesList != null) {
                for (int j = 0; j < pImagesList.size(); j++) {
                    String pp = pImagesList.get(j).getSkuId();
                    if (pp.equals(skuId)) {
                        ProductImagesVO productImagesVO = new ProductImagesVO();
                        //包装成前端数据
                        BeanUtils.copyProperties(pImagesList.get(j), productImagesVO);
                        productImagesVOList.add(productImagesVO);
                    }
                }
            }

            List<ProductMachineInfoDetailVO> productMachineInfoDetailVOList = new ArrayList<>();
            if (pMachineList != null) {
                for (int j = 0; j < pMachineList.size(); j++) {
                    String qq = pMachineList.get(j).getSkuId();
                    if (qq.equals(skuId)) {
                        ProductMachineInfoDetailVO productMachineInfoDetailVO = new ProductMachineInfoDetailVO();
                        BeanUtils.copyProperties(pMachineList.get(j), productMachineInfoDetailVO);
                        productMachineInfoDetailVOList.add(productMachineInfoDetailVO);
                    }
                }
            }

            Map<String, Object> remap = new HashMap<String, Object>();
            remap.put("sku", skuId);
            remap.put("skuNameZh", entity.getSkuNameZh());
            remap.put("skuNameEn", entity.getSkuNameZh());
            remap.put("status", entity.getStatus());
            remap.put("classifyNameCh", entity.getClassifyNameCh());
            remap.put("creator", entity.getCreator());
            remap.put("machineFlag", entity.getMachineFlag());
            remap.put("typeCd", entity.getTypeCd());
            remap.put("typeDesc", entity.getTypeDesc());
            remap.put("purchaseReferencePrice", entity.getPurchaseReferencePrice());
            remap.put("salesStatusCd", entity.getSalesStatusCd());
            remap.put("salesStatusDesc", entity.getSalesStatusDesc());
            remap.put("costPrice", entity.getCostPrice());
            remap.put("packingFlag", entity.getPackingFlag());


            try {
                String time = myFmt.format(entity.getDataTime());
                remap.put("dataTime", time);
            } catch (Exception e) {
                logger.info("产品的fuzzyQueryNew接口中产品的创建日期为空，转换失败！");
                remap.put("dataTime", "");
            }

            remap.put("imageArr", productImagesVOList);
            remap.put("processArr", productMachineInfoDetailVOList);
            resultReal.add(remap);
        }

        PageInfo pageInfo = new PageInfo(productShowList);
        long total = pageInfo.getTotal();
        int pages = pageInfo.getPages();
        Map<String, Object> map = new HashMap();
        map.put("total", total);
        map.put("pages", pages);
        map.put("productData", resultReal);
        return map;
    }

    private Map<String, String> createMapPara(GetProductInfoVO entity, String str) {
        Map<String, String> map = new HashMap<>();
        map.put("skuId", entity.getSkuId());
        map.put("skuNameZh", entity.getSkuNameZh());
        map.put("classifyCd", entity.getClassifyCd());
        if (entity.getStatusCd() == null) {
            map.put("statusCd", null);
        } else {
            map.put("statusCd", entity.getStatusCd().toString());
        }
        map.put("classifyCdPara", str);
        map.put("keyword", entity.getKeyword());
        if (entity.getSalesStatusCd() == null) {
            map.put("salesStatusCd", null);
        } else {
            map.put("salesStatusCd", entity.getSalesStatusCd().toString());
        }
        if (entity.getTypeCd() == null) {
            map.put("typeCd", null);
        } else {
            map.put("typeCd", entity.getTypeCd().toString());
        }
        return map;
    }

    private String createStrPara(List<String> list) {
        StringBuffer sb = new StringBuffer("(");
        for (String ele : list) {
            sb.append("'").append(ele).append("',");
        }
        sb.setCharAt(sb.length() - 1, ')');
        if (sb.length() < 4) {
            return null;
        }
        return sb.toString();
    }

    @Override
    public Map<String, String> getName(String m) {
        return productDefineDao.getName(m);
    }


    @Override
    public void update(ProductDefineEntity ele, String time, String userId) throws Exception {
        ProductDefineEntity old = productDefineDao.findBySkuId(ele.getSkuId());
        if (old == null) {
            throw new Exception("不存在该sku产品");
        }
        int length = ele.getSkuShortName().length();
        if (length > 5 || length < 1) {
            throw new Exception("分类简写长度不合法");
        }
        boolean flag = check(ele.getSkuShortName());
        if (!flag) {
            throw new Exception("分类简写不合法");
        }

        ele.setSkuShortName(ele.getSkuShortName().toUpperCase());
        ele.setUpdateTime(time);
        productDefineDao.update(ele);

        //==============添加历史纪录
        try {
            //是否加工 这个操作不用记录 ，因为他是通过加工产品的变化体现的
            //是否包装材料 这个操作不用记录 ，因为他是通过包装材料的变化体现的
            old.setMachineFlag(0);
            old.setPackingFlag(0);
            ProductDefineEntity newEntity = new ProductDefineEntity();
            BeanUtils.copyProperties(ele, newEntity);
            newEntity.setMachineFlag(0);
            newEntity.setPackingFlag(0);
            productModifyHistoryService.historyUpdate(old, newEntity, time, userId);
        } catch (Exception e) {
            logger.error("保存历史纪录出错！", e);
            throw new RuntimeException("保存历史纪录出错");
        }
        //==============
    }

    @Override
    public List<String> getAllSKU() {
        return productDefineDao.getAllSKU();
    }

    @Override
    public void delete(String skuId) {
        productDefineDao.delete(skuId);
    }

    @Override
    public void changeStatus(String skuId, int statusCd) {
        Map<String, Object> map = new HashMap<>();
        map.put("skuId", skuId);
        map.put("statusCd", statusCd);
        productDefineDao.changeStatus(map);
    }

    @Override
    public ProductDefineEntity findBySkuId(String skuId) {
        return productDefineDao.findBySkuId(skuId);
    }

    @Override
    public void skuExcel (GetProductInfoVO getProductInfoVO,
                     List<String> classifyCdList,
                          HttpServletResponse response,
                          HttpServletRequest request) {
        try {
            String classifyCdPara = createStrPara(classifyCdList);

            Map<String, String> mapPara = createMapPara(getProductInfoVO, classifyCdPara);

            // 转换模糊查询，需要过滤掉的sku
            if (getProductInfoVO.getIgnoreSkuIds() != null) {
                StringBuffer ignoreSkuIds = new StringBuffer("(");
                for (String ele : getProductInfoVO.getIgnoreSkuIds()) {
                    ignoreSkuIds.append("'").append(ele).append("',");
                }
                ignoreSkuIds.setCharAt(ignoreSkuIds.length() - 1, ')');
                if (ignoreSkuIds.length() < 4) {
                    // 不用设置忽略sku
                    mapPara.put("ignoreSkuIds", null);
                } else {
                    mapPara.put("ignoreSkuIds", ignoreSkuIds.toString());
                }
            }

            List<MachineExcel> machineList = productDefineDao.machineExcelData(mapPara);

            String[] title = {"库存SKU", "产品中文名", "加工/包材SKU", "加工/包材产品名称", "数量", "类型(加工/包材)"};
            ExcelTools.exportExcel("加工及包材", title, machineList, "产品管理 - 加工及包材", response, request);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
