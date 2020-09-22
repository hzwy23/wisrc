package com.wisrc.warehouse.webapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisrc.warehouse.webapp.service.SkuInfoService;
import com.wisrc.warehouse.webapp.dao.BlitemDao;
import com.wisrc.warehouse.webapp.dao.BlitemListDao;
import com.wisrc.warehouse.webapp.dao.BlitemRemarkDao;
import com.wisrc.warehouse.webapp.entity.BlitemInfoEntity;
import com.wisrc.warehouse.webapp.entity.BlitemListInfoEntity;
import com.wisrc.warehouse.webapp.entity.BlitemRemarkEntity;
import com.wisrc.warehouse.webapp.service.BlitemService;
import com.wisrc.warehouse.webapp.service.externalService.EmployeeService;
import com.wisrc.warehouse.webapp.utils.PageData;
import com.wisrc.warehouse.webapp.utils.Result;
import com.wisrc.warehouse.webapp.utils.Time;
import com.wisrc.warehouse.webapp.utils.UUIDutil;
import com.wisrc.warehouse.webapp.vo.BlitemInfoVO;
import com.wisrc.warehouse.webapp.vo.BlitemListVO;
import com.wisrc.warehouse.webapp.vo.BlitemRemarkVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Service
public class BlitemServiceImpl implements BlitemService {

    //所有的产品idName映射
    private static Map<String, String> productIdName = null;
    //所有参评的idImgUrl映射
    private static Map<String, List<String>> productIdUrls = null;
    @Autowired
    private BlitemDao blitemDao;
    @Autowired
    private BlitemListDao blitemListDao;
    @Autowired
    private BlitemRemarkDao remarkDao;
    @Autowired
    private SkuInfoService skuInfoService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private RedisTemplate redisTemplate;
    //所有的用户idName映射
    private Map<String, String> userIdName = null;

    private void initData() {
        userIdName = new LinkedHashMap<>();
        productIdName = new LinkedHashMap<>();
        productIdUrls = new LinkedHashMap<>();
        /**
         * 先取出所有员工的id-name键值对
         */
        Result employeeResult = employeeService.getAllEmployee("admin");
        Map employeeData = (Map) employeeResult.getData();
        List<Object> employeeList = (List<Object>) employeeData.get("employeeInfoList");
        for (Object employee : employeeList) {
            Map employeeObj = (Map) employee;
            userIdName.put((String) employeeObj.get("employeeId"), (String) employeeObj.get("employeeName"));
        }

        //调用外部接口
        Result skuInfo = skuInfoService.getSkuInfo(null, null);
        Map skuData = (Map) skuInfo.getData();
        List<Object> data = (List<Object>) skuData.get("productData");
        for (Object product : data) {
            Map dataMap = (Map) product;
            List<String> urls = new ArrayList<>();
            productIdName.put((String) dataMap.get("sku"), (String) dataMap.get("skuNameZh"));
            List<Object> imgAttr = (List<Object>) dataMap.get("imageArr");
            for (Object o : imgAttr) {
                Map map = (Map) o;
                urls.add((String) map.get("imageUrl"));
            }
            productIdUrls.put((String) dataMap.get("sku"), urls);
        }
    }


    @Override
    public Result getList(Integer pageNum, Integer pageSize, String plitemId, String warehouseId, String skuId, String skuName, String startDate, String endDate) throws Exception {
        if (userIdName == null && productIdUrls == null && productIdName == null) {
            initData();
        }
        //接收盘点单基本信息要返回的结果
        List<BlitemInfoVO> blitemInfoVOS = null;
        //查询到都得所有产品id
        List<String> skuIds = new ArrayList<>();

        //调用外部接口
        Result skuInfo = skuInfoService.getSkuInfo(skuName, skuId);
        Map skuData = (Map) skuInfo.getData();
        List<Object> data = (List<Object>) skuData.get("productData");
        for (Object product : data) {
            Map dataMap = (Map) product;
            skuIds.add((String) dataMap.get("sku"));
        }

        if (pageNum != null && pageNum != 0 && pageSize != null && pageSize != 0) {
            PageHelper.startPage(pageNum, pageSize);
            List<BlitemInfoEntity> blitemInfoList = blitemDao.findAllByCon(plitemId, warehouseId, startDate, endDate, skuIds);
            PageInfo pageInfo = new PageInfo(blitemInfoList);
            blitemInfoVOS = new ArrayList<>();
            //给盘点单添加上备注信息
            blitemInfoVOS = getBlitemInfoVOS(blitemInfoVOS, blitemInfoList);
            //将用户id转化为用户名称
            transformIdToName(blitemInfoVOS, userIdName);
            return Result.success(PageData.pack(pageInfo.getTotal(), pageInfo.getPages(), "blitemInfoList", blitemInfoVOS));
        } else {
            List<BlitemInfoEntity> blitemInfoEntities = blitemDao.findAllByCon(plitemId, warehouseId, startDate, endDate, skuIds);
            blitemInfoVOS = new ArrayList<>();
            blitemInfoVOS = getBlitemInfoVOS(blitemInfoVOS, blitemInfoEntities);
            transformIdToName(blitemInfoVOS, userIdName);
            return Result.success(PageData.pack(blitemInfoVOS.size(), 1, "blitemInfoList", blitemInfoVOS));
        }
    }

    @Override
    @Transactional(transactionManager = "retailWarehouseTransactionManager")
    public Result save(BlitemInfoEntity blitemInfoEntity, List<BlitemListVO> blitemListInfoEntities, String remark, String userId) {
        //获取盘点单的唯一单号
        String blitemId = getBiltemId("addBlitemId");
        blitemInfoEntity.setBlitemId(blitemId);
        //获取创建盘点单的用户id
        List<String> useridList = new ArrayList<>();
        useridList.add(userId);
        Result employee = employeeService.getEmployee(useridList);
        List<Object> employees = (List<Object>) employee.getData();
        Map employyeMap = (Map) employees.get(0);
        String employeeId = (String) employyeMap.get("employeeId");
        blitemInfoEntity.setCreateUser(employeeId);
        blitemInfoEntity.setCreateTime(Time.getCurrentTime());
        blitemInfoEntity.setStatusCd(1);
        blitemInfoEntity.setDeleteStatus(0);
        blitemInfoEntity.setOperationUser(employeeId);
        blitemInfoEntity.setOperationDate(Time.getCurrentTime());
        blitemDao.add(blitemInfoEntity);//添加盘点单的基本信息

        //添加备注
        BlitemRemarkEntity remarkEntity = new BlitemRemarkEntity();
        remarkEntity.setUuid(UUIDutil.randomUUID());
        remarkEntity.setBlitemId(blitemId);
        remarkEntity.setRemarkContent(remark);
        remarkEntity.setRemarkUser(employeeId);
        remarkEntity.setRemarkTime(Time.getCurrentTime());
        remarkDao.add(remarkEntity);

        //添加详细的盘点产品信息
        for (BlitemListInfoEntity listInfo : blitemListInfoEntities) {
            BlitemListInfoEntity entity = new BlitemListInfoEntity();
            BeanUtils.copyProperties(listInfo, entity);
            entity.setUuid(UUIDutil.randomUUID());
            entity.setBlitemId(blitemId);
            entity.setDeleteStatus(0);
            entity.setCreateUser(employeeId);
            entity.setCreateTime(Time.getCurrentTime());
            entity.setModifyUser(employeeId);
            entity.setModifyTime(Time.getCurrentTime());
            blitemListDao.add(entity);
        }

        return Result.success("新建成功");
    }

    @Override
    public Result updateBlitemStatus(String blitemId, String statusCd, String remark, String userId) {
        //获取修改盘点单的用户id
        List<String> useridList = new ArrayList<>();
        useridList.add(userId);
        Result employee = employeeService.getEmployee(useridList);
        List<Object> employees = (List<Object>) employee.getData();
        Map employyeMap = (Map) employees.get(0);
        String employeeId = (String) employyeMap.get("employeeId");
        BlitemRemarkEntity remarkEntity = new BlitemRemarkEntity();
        remarkEntity.setBlitemId(blitemId);
        remarkEntity.setRemarkContent(remark);
        remarkEntity.setRemarkTime(Time.getCurrentTime());
        remarkEntity.setUuid(UUIDutil.randomUUID());
        remarkEntity.setRemarkUser(employeeId);
        remarkDao.add(remarkEntity);
        blitemDao.updateStatus(blitemId, statusCd, employeeId, Time.getCurrentTime());
        return Result.success("更新成功");
    }

    @Override
    public Result getBlitemInfoVoByBlitemId(String blitemId) {
        if (userIdName == null && productIdUrls == null && productIdName == null) {
            initData();
        }
        BlitemInfoVO blitemInfoVO = new BlitemInfoVO();
        //获取盘点单的基本信息
        BlitemInfoEntity blitemInfoEntity = blitemDao.findByBlitemId(blitemId);
        BeanUtils.copyProperties(blitemInfoEntity, blitemInfoVO);
        //获取产品备注信息
        List<BlitemRemarkEntity> remarkEntities = remarkDao.findAllByBlitemId(blitemId);
        //带名字的产品备注信息
        List<BlitemRemarkVO> remarkVOS = new ArrayList<>();
        //给每个备注信息添加上备注人名称
        for (BlitemRemarkEntity remarkEntity : remarkEntities) {
            BlitemRemarkVO remarkVO = new BlitemRemarkVO();
            BeanUtils.copyProperties(remarkEntity, remarkVO);
            remarkVO.setRemarkUserName(userIdName.get(remarkEntity.getRemarkUser()));
            remarkVOS.add(remarkVO);
        }
        //给盘点单添加备注信息
        blitemInfoVO.setRemarks(remarkVOS);

        //获取出基本的盘点单产品信息
        List<BlitemListInfoEntity> blitemListInfos = blitemListDao.findAllByBlitemId(blitemId);
        List<BlitemListVO> blitemListVOS = new ArrayList<>();
        //给产品信息加上产品名称和图片地址
        for (BlitemListInfoEntity blitemListInfo : blitemListInfos) {
            BlitemListVO blitemListVO = new BlitemListVO();
            BeanUtils.copyProperties(blitemListInfo, blitemListVO);
            blitemListVO.setSkuName(productIdName.get(blitemListInfo.getSkuId()));
            blitemListVO.setImgUrls(productIdUrls.get(blitemListInfo.getSkuId()));
            blitemListVOS.add(blitemListVO);
        }
        //给盘点单添加盘点商品信息
        blitemInfoVO.setBlitemListInfos(blitemListVOS);
        return Result.success(blitemInfoVO);
    }

    private void transformIdToName(List<BlitemInfoVO> blitemInfoVOS, Map<String, String> userIdName) {
        for (BlitemInfoVO blitemInfoVO : blitemInfoVOS) {
            blitemInfoVO.setCreateUserName(userIdName.get(blitemInfoVO.getCreateUser()));
            blitemInfoVO.setAuditUserName(userIdName.get(blitemInfoVO.getAuditUser()));
            blitemInfoVO.setOperationUserName(userIdName.get(blitemInfoVO.getOperationUser()));
        }
    }

    private List<BlitemInfoVO> getBlitemInfoVOS(List<BlitemInfoVO> blitemInfoVOS, List<BlitemInfoEntity> blitemInfoList) {
        for (BlitemInfoEntity blitemInfoEntity : blitemInfoList) {
            BlitemInfoVO blitemInfoVO = new BlitemInfoVO();
            BeanUtils.copyProperties(blitemInfoEntity, blitemInfoVO);
            List<BlitemRemarkEntity> remarks = remarkDao.findAllByBlitemId(blitemInfoEntity.getBlitemId());
            List<BlitemRemarkVO> remarkVOS = new ArrayList<>();
            for (BlitemRemarkEntity remark : remarks) {
                BlitemRemarkVO blitemRemarkVO = new BlitemRemarkVO();
                BeanUtils.copyProperties(remark, blitemRemarkVO);
                blitemRemarkVO.setRemarkUserName(userIdName.get(remark.getRemarkUser()));
                remarkVOS.add(blitemRemarkVO);
            }
            blitemInfoVO.setRemarks(remarkVOS);
            blitemInfoVOS.add(blitemInfoVO);
        }
        return blitemInfoVOS;
    }

    private String getBiltemId(String rediskey) {
        SimpleDateFormat sdf = new SimpleDateFormat("YYMMdd");
        String currDate = sdf.format(System.currentTimeMillis());
        String key = currDate + rediskey;
        int count = 0;
        long maxId = redisTemplate.opsForValue().increment(key, 1);
        if (maxId == 1) {
            redisTemplate.expire(key, 1000 * 60 * 60 * 24, TimeUnit.MILLISECONDS);
        }
        if (maxId > 999) {
            throw new RuntimeException("订单号已达最大");
        }
        long num = maxId;
        while (num > 0) {
            num = num / 10;
            count++;
        }
        if (count == 1) {
            return "ST" + currDate + "00" + maxId;
        }
        if (count == 2) {
            return "ST" + currDate + "0" + maxId;
        }
        if (count == 3) {
            return "ST" + currDate + maxId;
        }

        return "ST" + currDate + maxId;
    }
}
