package com.wisrc.basic.vo.swagger;

import com.wisrc.basic.vo.CompanyBasicInfoAllVO;
import com.wisrc.basic.vo.CompanyBasicInfoVO;
import io.swagger.annotations.ApiModelProperty;

public class CompanyBasicInfoAllModel {
    @ApiModelProperty(value = "返回状态吗", position = 1)
    private int code;

    @ApiModelProperty(value = "返回消息", position = 2)
    private int msg;

    @ApiModelProperty(value = "公司基本信息", position = 3)
    private CompanyBasicInfoVO companyBasicInfoVO;

    @ApiModelProperty(value = "公司物流报关信息", position = 4)
    private CompanyBasicInfoAllVO companyBasicInfoAllVO;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
