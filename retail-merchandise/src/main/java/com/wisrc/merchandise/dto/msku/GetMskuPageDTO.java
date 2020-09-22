package com.wisrc.merchandise.dto.msku;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

@Data
@ApiModel
public class GetMskuPageDTO {
    @ApiModelProperty(value = "商品id")
    private String id;
    @ApiModelProperty(value = "商品编号")
    private String msku;
    @ApiModelProperty(value = "商品名称")
    private String mskuName;
    @ApiModelProperty(value = "产品名称")
    private String storeName;
    @ApiModelProperty(value = "产品编号")
    private String storeSku;
    @ApiModelProperty(value = "店铺名称")
    private String shopName;
    @ApiModelProperty(value = "小组编码")
    private String teamCd;
    @ApiModelProperty(value = "小组名称")
    private String teamName;
    @ApiModelProperty(value = "负责人员工号")
    private String employeeId;
    @ApiModelProperty(value = "负责人名称")
    private String manager;
    @ApiModelProperty(value = "销售状态")
    private Map salesStatus;
    @ApiModelProperty(value = "更新时间")
    private String updateTime;
    @ApiModelProperty(value = "商品状态")
    private Map mskuStatus;
    @ApiModelProperty(value = "墓志铭")
    private String epitaph;
    @ApiModelProperty(value = "图片")
    private String picture;
    @ApiModelProperty(value = "asin")
    private String asin;
    @ApiModelProperty(value = "fnsku")
    private String fnsku;
    @ApiModelProperty(value = "配送方式")
    private String delivery;
    @ApiModelProperty(value = "上架时间")
    private String shelfTime;
    @ApiModelProperty(value = "fba在仓")
    private Integer fbaOnWarehouseStockNum;
    @ApiModelProperty(value = "fba在途")
    private Integer fbaOnWayStockNum;
    @ApiModelProperty(value = "昨天销量")
    private Integer yesterdaySalesNum;
    @ApiModelProperty(value = "前天销量")
    private Integer dayBeforeYesterdaySalesNum;
    @ApiModelProperty(value = "上前销量")
    private Integer previousSalesNum;
    @ApiModelProperty(value = "佣金系数")
    private Double commission;
}
