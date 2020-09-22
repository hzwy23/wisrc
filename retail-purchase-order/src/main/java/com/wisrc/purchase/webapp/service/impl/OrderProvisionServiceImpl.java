package com.wisrc.purchase.webapp.service.impl;

import com.wisrc.purchase.webapp.dao.OrderProvisionDao;
import com.wisrc.purchase.webapp.entity.DynamicFieldsAttrEntity;
import com.wisrc.purchase.webapp.entity.OrderProvisionEntity;
import com.wisrc.purchase.webapp.entity.ProvisionMouldEntity;
import com.wisrc.purchase.webapp.service.*;
import com.wisrc.purchase.webapp.utils.Result;
import com.wisrc.purchase.webapp.utils.Time;
import com.wisrc.purchase.webapp.vo.orderProvision.OrderProvisionMouldVO;
import com.wisrc.purchase.webapp.vo.orderProvision.OrderProvisionVO;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class OrderProvisionServiceImpl implements OrderProvisionService {
    @Autowired
    OrderProvisionDao orderProvisionDao;
    @Autowired
    ObjectStorageService objectStorageService;
    @Autowired
    SupplierService supplierService;
    @Autowired
    PurchcaseOrderBasisInfoService purchcaseOrderBasisInfoService;
    @Autowired
    SupplierOutsideService supplierOutsideService;

    /**
     * 创建文件
     *
     * @throws IOException
     */
    public static boolean creatTxtFile(String name) throws IOException {
        boolean flag = false;
        File filename = new File("C:\\Users\\Public\\Documents\\" + name + ".txt");
        if (!filename.exists()) {
            filename.createNewFile();
            flag = true;
        }
        return flag;
    }

    /**
     * 写文件
     *
     * @param newStr 新内容
     * @throws IOException
     */
    public static boolean writeTxtFile(File file, String newStr) throws IOException {
        // 先读取原有文件内容，然后进行写入操作
        boolean flag = false;
        String filein = newStr + "\r\n";
        String temp = "";

        FileOutputStream fos = null;
        PrintWriter pw = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(newStr.getBytes("UTF-8"));
            flag = true;
        } catch (IOException e1) {
            // TODO 自动生成 catch 块
            throw e1;
        } finally {
            if (pw != null) {
                pw.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
        return flag;
    }

    private static MultipartFile createFileItem(String filePath) {
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        String textFieldName = "file";
        int num = filePath.lastIndexOf(".");
        String extFile = filePath.substring(num);
        FileItem item = factory.createItem(textFieldName, "text/plain", true,
                "MyFileName" + extFile);
        File newfile = new File(filePath);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        try {
            FileInputStream fis = new FileInputStream(newfile);
            OutputStream os = item.getOutputStream();
            while ((bytesRead = fis.read(buffer, 0, 8192))
                    != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MultipartFile multipartFile = new CommonsMultipartFile(item);
        return multipartFile;
    }

    /**
     * 查询订单条款信息ById
     *
     * @param orderId
     * @return
     */
    @Override
    public OrderProvisionVO findOrderProvision(String orderId) {
        OrderProvisionEntity orderProvision = orderProvisionDao.findOrderProvision(orderId);
        OrderProvisionVO vo = new OrderProvisionVO();
        if (orderProvision != null) {
            String str = objectStorageService.downloadFile("matrix-purchase-order", orderProvision.getProvisionUrl());
            /**    旧版的业务逻辑
             OrderBasisInfoVO basisInfoById = orderBasisInfoService.findBasisInfoByIdNeet(orderId);
             Map map = new HashMap();
             try {
             Map supplierInfo = supplierOutsideService.getSupplierInfo(basisInfoById.getSupplierId());
             map = (Map)supplierInfo.get("supplier");
             }catch (Exception e){
             }
             List<Map> m = (List<Map>)map.get("supplierAccounts");
             if(m!=null && str!=null && m.size()>0){
             str=str.replaceAll("供应商收款人", (String)m.get(0).get("payee"));
             str=str.replaceAll("供应商开户银行",(String)m.get(0).get("bank"));
             str=str.replaceAll("供应商银行账户", (String)m.get(0).get("account"));
             }
             */
            BeanUtils.copyProperties(orderProvision, vo);
            vo.setProvisionContent(str);
        }
        return vo;
    }

    /**
     * 查询条款模板列表
     *
     * @return
     */
    @Override
    public List<ProvisionMouldEntity> findProvisionMould() {

        return orderProvisionDao.findProvisionMould();
    }

    /**
     * 查询条款模板列表ById
     *
     * @return
     */
    @Override
    public OrderProvisionMouldVO findProvisionMouldById(String uuid) {
        ProvisionMouldEntity provisionMouldById = orderProvisionDao.findProvisionMouldById(uuid);
        String str = objectStorageService.downloadFile("matrix-purchase-order", provisionMouldById.getMouldUrl());
        OrderProvisionMouldVO vo = new OrderProvisionMouldVO();
        vo.setProvisionContent(str);
        vo.setUuid(uuid);
        vo.setExplainName(provisionMouldById.getExplainName());
        return vo;
    }

    /**
     * 查询在光标位置插入字段码表信息列表
     *
     * @return
     */
    @Override
    public List<DynamicFieldsAttrEntity> findDynamicFieldsAttr() {
        return orderProvisionDao.findDynamicFieldsAttr();
    }

    /**
     * 新增订单条款信息
     *
     * @param
     */
    @Override
    @Transactional(transactionManager = "retailPurchaseOrderTransactionManager")
    public void addOrderProvision(OrderProvisionVO vo) {
        if (vo.getProvisionContent() == null) {
            vo.setProvisionContent("");
        }
        //上传模板.txt文件
        byte[] bytes = getProvisionContentBytes(vo.getProvisionContent());
        Result result = objectStorageService.uploadFileByte(bytes, "matrix-purchase-order", "txt");
        Map map = (Map) result.getData();
        OrderProvisionEntity en = new OrderProvisionEntity();
        BeanUtils.copyProperties(vo, en);
        en.setDeleteStatus(0);
        en.setProvisionUrl((String) map.get("uuid"));
        en.setUuid(UUID.randomUUID().toString());
        orderProvisionDao.addOrderProvision(en);
    }

    /**
     * 新增条款模板
     */
    @Override
    @Transactional(transactionManager = "retailPurchaseOrderTransactionManager")
    public void addProvisionMould(OrderProvisionMouldVO vo, String userId) {
        String provisionContent = vo.getProvisionContent();
        byte[] bytes = getProvisionContentBytes(provisionContent);
        Result result = objectStorageService.uploadFileByte(bytes, "matrix-purchase-order", "txt");
        Map map = (Map) result.getData();
        ProvisionMouldEntity provisionMouldEntity = new ProvisionMouldEntity();
        provisionMouldEntity.setCreateUser(userId);
        provisionMouldEntity.setCreateTime(Time.getCurrentTimestamp());
        provisionMouldEntity.setDeleteStatus(0);
        provisionMouldEntity.setExplainName(vo.getExplainName());
        provisionMouldEntity.setUuid(UUID.randomUUID().toString());
        provisionMouldEntity.setMouldUrl((String) map.get("uuid"));
        orderProvisionDao.addProvisionMould(provisionMouldEntity);
    }

    /**
     * 获取条款内容字节码
     *
     * @param provisionContent
     * @return
     */
    private byte[] getProvisionContentBytes(String provisionContent) {
        if (provisionContent == null) {
            provisionContent = "";
        }

        byte[] bytes = new byte[0];
        try {
            bytes = provisionContent.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //防止空的内容导致的上传失败
        if (bytes == null || bytes.length == 0) {
            bytes = " ".getBytes();
        }
        return bytes;
    }

    /**
     * 逻辑删除订单条款信息
     *
     * @param
     */
    @Override
    public void delOrderProvision(OrderProvisionEntity orderProvisionEntity) {
        orderProvisionDao.delOrderProvision(orderProvisionEntity);
    }

    /**
     * 逻辑删除条款模板
     *
     * @param
     */
    @Override
    public void delProvisionMould(ProvisionMouldEntity provisionMouldEntity) {
        orderProvisionDao.delProvisionMould(provisionMouldEntity);
    }

    /**
     * 修改订单条款信息
     *
     * @param
     */
    @Override
    @Transactional(transactionManager = "retailPurchaseOrderTransactionManager")
    public void updateOrderProvision(OrderProvisionVO vo) {
        //先逻辑删除此订单之前的条款信息
        OrderProvisionEntity orderProvisionEntity = new OrderProvisionEntity();
        BeanUtils.copyProperties(vo, orderProvisionEntity);
        orderProvisionEntity.setDeleteStatus(1);
        orderProvisionDao.delOrderProvision(orderProvisionEntity);
        //上传模板.txt文件
        String provisionContent = vo.getProvisionContent();
        byte[] bytes = getProvisionContentBytes(provisionContent);
        Result result = objectStorageService.uploadFileByte(bytes, "matrix-purchase-order", "txt");
        Map map = (Map) result.getData();
        OrderProvisionEntity en = new OrderProvisionEntity();
        BeanUtils.copyProperties(vo, en);
        en.setDeleteStatus(0);
        en.setProvisionUrl((String) map.get("uuid"));
        en.setUuid(UUID.randomUUID().toString());
        orderProvisionDao.addOrderProvision(en);
    }

    /**
     * 修改条款模板
     */
    @Override
    @Transactional(transactionManager = "retailPurchaseOrderTransactionManager")
    public void updateProvisionMould(OrderProvisionMouldVO vo, String userId) {

        //先逻辑删除之前的模板
        ProvisionMouldEntity del = new ProvisionMouldEntity();
        del.setUuid(vo.getUuid());
        del.setDeleteStatus(1);
        del.setModifyTime(Time.getCurrentTimestamp());
        del.setModifyUser(userId);
        orderProvisionDao.delProvisionMould(del);
        //上传模板.txt文件
        String provisionContent = vo.getProvisionContent();
        byte[] bytes = getProvisionContentBytes(provisionContent);
        Result result = objectStorageService.uploadFileByte(bytes, "matrix-purchase-order", "txt");
        Map map = (Map) result.getData();
        ProvisionMouldEntity provisionMouldEntity = new ProvisionMouldEntity();
        provisionMouldEntity.setCreateUser(userId);
        provisionMouldEntity.setCreateTime(Time.getCurrentTimestamp());
        provisionMouldEntity.setDeleteStatus(0);
        provisionMouldEntity.setExplainName(vo.getExplainName());
        provisionMouldEntity.setUuid(UUID.randomUUID().toString());
        provisionMouldEntity.setMouldUrl((String) map.get("uuid"));
        orderProvisionDao.addProvisionMould(provisionMouldEntity);
    }
}
