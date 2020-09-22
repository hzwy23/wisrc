package com.wisrc.product.webapp.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 产品管理首页模糊查询实体
 */
@Data
public class ProductShowEntity {

    //注：   //这里开发时前后端字段定义不统一 ，可能实体skuId，数据库 sku_id,   注意sql语句中字段的使用！！！！

    private String sku;  //这里开发时前后端字段定义不统一 ，可能实体skuId，数据库 sku_id,   注意sql语句
    private String skuNameZh;
    private String skuNameEn;
    private String machineFlag;
    private Integer status;  //这里开发时前后端字段定义不统一 ，可能实体statusCd，数据库 status_cd,   注意sql语句
    private String classifyNameCh;
    private String creator;  //这里开发时前后端字段定义不统一 ，可能实体createUser，数据库 create_user  注意sql语句
    private Date dataTime;   //这里开发时前后端字段定义不统一 ，可能实体createTime，数据库 create_time  注意sql语句
    private List<Map<String, Object>> processArr;
    private List<Map<String, Object>> imageArr;

    private Integer typeCd;
    private String typeDesc;
    private String purchaseReferencePrice;
    private String salesStatusCd;
    private String salesStatusDesc;
    private Double costPrice;
    private Integer packingFlag;

}
