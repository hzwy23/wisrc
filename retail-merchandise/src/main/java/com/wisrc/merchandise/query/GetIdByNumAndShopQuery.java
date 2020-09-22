package com.wisrc.merchandise.query;

import lombok.Data;

import java.util.List;

@Data
public class GetIdByNumAndShopQuery {
    private List<MskuRelationQuery> numAndShopVo;
    private List mskuPrivilege;
    private String user;
}
