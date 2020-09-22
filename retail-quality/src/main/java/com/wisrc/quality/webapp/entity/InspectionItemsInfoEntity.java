package com.wisrc.quality.webapp.entity;

import lombok.Data;

@Data
public class InspectionItemsInfoEntity {
    private String inspectionItemId; //系统唯一编码
    private String inspectionCd;  //产品检验单号
    private Integer itemsCd;//检验项目编码
    private Integer criAll;  //  cri合计
    private Integer majAll;  //   maj合计
    private Integer minAll;  //   min合计
    private Integer itemResultCd;  //   检验结果编码

    private Double criAQL;
    private Double criAc;
    private Double criRe;
    private Double majAQL;
    private Double majAc;
    private Double majRe;
    private Double minAQL;
    private Double minAc;
    private Double minRe;
}
