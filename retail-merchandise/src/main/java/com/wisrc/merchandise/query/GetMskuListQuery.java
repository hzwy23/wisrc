package com.wisrc.merchandise.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Data
public class GetMskuListQuery {
    private String shopId;
    private String employeeIdListStr;
    private String manager;
    private Integer deliveryMode;
    private Integer salesStatus;
    private Integer mskuStatus;
    private String findKey;
    private List storeSkuDealted;
    private List sellerSkuDealted;
    private String prop;
    private String order;
    private List mskuPrivilege;
    private String userId;
}
